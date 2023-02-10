interface BusStopsRepository {
    suspend fun getBusStops(): List<BusStop>
}