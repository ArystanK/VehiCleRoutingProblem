interface VehicleRoutingProblem {
    suspend fun solve(
        numberOfRoutes: Int,
        distMatrix: Map<Int, Map<Int, Double>>,
    ): List<Int>
}