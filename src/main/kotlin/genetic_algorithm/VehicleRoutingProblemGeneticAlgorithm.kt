package genetic_algorithm

import VehicleRoutingProblem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.runBlocking
import randomList
import kotlin.random.Random

class VehicleRoutingProblemGeneticAlgorithm : VehicleRoutingProblem {
    private suspend fun geneticAlgorithm(
        numberOfRoutes: Int,
        distMatrix: Map<Int, Map<Int, Double>>,
        populationSize: Int,
        numGenerations: Int,
        mutationRate: Double = 0.1,
    ): List<Int> {
        // Initialize the population
        val population = (0 until populationSize).map { randomRoute(distMatrix.keys) }

        return (0 until numGenerations).asFlow()
            .buffer(Channel.UNLIMITED)
            .flowOn(Dispatchers.Default)
            .fold(population) { currentPopulation, _ ->
                // Evaluate the fitness of each route
                val fitness = currentPopulation.map { evaluateFitness(it, distMatrix) }

                // Select the best routes
                val bestRoutes = selectBest(currentPopulation, fitness)

                // Perform graph.crossover
                val offspring = crossover(bestRoutes)

                // Perform mutation
                mutate(offspring, mutationRate, numberOfRoutes)
            }.minBy {
                evaluateFitness(it, distMatrix) * it.size
            }
    }

    private fun randomRoute(nodes: Collection<Int>): List<Int> =
        nodes.toList().randomList()

    private fun evaluateFitness(
        route: List<Int>,
        distMatrix: Map<Int, Map<Int, Double>>,
    ): Double {
        return route.windowed(2)
            .sumOf {
                distMatrix[it[0]]?.get(it[1]) ?: Double.MAX_VALUE
            }
    }

    private fun selectBest(population: List<List<Int>>, fitness: List<Double>): List<List<Int>> {
        val bestFitness = fitness.min()
        return population.zip(fitness).filter { it.second == bestFitness }.map { it.first }
    }

    private fun crossover(bestRoutes: List<List<Int>>): List<List<Int>> {
        return bestRoutes.flatMap { parent1 ->
            bestRoutes.map { parent2 ->
                parent1
                    .zip(parent2)
                    .map {
                        if (Math.random() < 0.5) it.first else it.second
                    }
            }
        }
    }

    private fun mutate(population: List<List<Int>>, mutationRate: Double, n: Int): List<List<Int>> {
        return population.map { route ->
            when {
                Math.random() >= mutationRate -> route
                Math.random() < 0.5 -> route + listOf(Random.nextInt(n))
                else -> route.dropLast(1)
            }
        }
    }

    override suspend fun solve(numberOfRoutes: Int, distMatrix: Map<Int, Map<Int, Double>>): List<Int> {
        return geneticAlgorithm(numberOfRoutes, distMatrix, distMatrix.size / numberOfRoutes, 1000)
    }
}
