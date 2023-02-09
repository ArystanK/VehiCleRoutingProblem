import genetic_algorithm.VehicleRoutingProblemGeneticAlgorithm
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.math.ceil
import kotlin.math.pow

fun main() = runBlocking {
    val result = File("RESULT.txt")
    result.writeText("")
    val solution = VehicleRoutingProblemGeneticAlgorithm().solve(
        numberOfRoutes = 10,
        distMatrix = generateDistanceMatrix(BusStopsRepositoryImplementation().getBusStops())
    )
    println(solution.size)
    solution.chunked(ceil(solution.size / 10.0).toInt()).forEach {
        result.appendText("$it,\n")
    }
}

fun generateDistanceMatrix(busStops: List<BusStop>): Map<Int, Map<Int, Double>> {
    return busStops.associate { outer ->
        outer.id to busStops.associate { inner ->
            inner.id to (outer.lat - inner.lat).pow(2) + (outer.lon - inner.lon).pow(2)
        }
    }
}