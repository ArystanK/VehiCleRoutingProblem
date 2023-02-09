import database.DatabaseFactory

class BusStopsRepositoryImplementation : BusStopsRepository {
    override fun getBusStops(): List<BusStop> {
        return DatabaseFactory.getData()
    }
}