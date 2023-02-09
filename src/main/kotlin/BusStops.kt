import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

data class BusStop(
    val id: Int,
    val lat: Double,
    val lon: Double,
    val address: String,
)

object BusStopTable : IntIdTable("bus_stops") {
    val lat = double("lat")
    val lon = double("lon")
    val address = text("address")
}