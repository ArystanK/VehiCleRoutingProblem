import client_api.YandexApi
import database.DatabaseFactory

class BusStopsRepositoryImplementation : BusStopsRepository {
    private val client by lazy { YandexApi() }
    override suspend fun getBusStops(): List<BusStop> {
        var data = DatabaseFactory.getData()
        if (data.isEmpty()) {
            data = client.getBusStops().features.map {
                BusStop(
                    lat = it.geometry.coordinates.last(),
                    lon = it.geometry.coordinates.first(),
                    address = it.properties.CompanyMetaData.address
                )
            }
            DatabaseFactory.saveData(data)
        }
        return DatabaseFactory.getData()
    }
}