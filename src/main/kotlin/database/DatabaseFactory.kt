package database

import BusStop
import BusStopTable
import BusStopTable.address
import BusStopTable.lat
import BusStopTable.lon
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    private fun connect(): Database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/vehicle_routing_problem",
        password = "qwerty",
        user = "postgres",
        driver = "org.postgresql.Driver"
    )

    private fun initializeDatabase() {
        transaction {
            addLogger(StdOutSqlLogger)
        }
    }

    init {
        connect()
        initializeDatabase()
    }

    fun saveData(busStops: List<BusStop>) {
        transaction {
            addLogger(StdOutSqlLogger)
            BusStopTable.batchInsert(busStops) {
                this[lat] = it.lat
                this[lon] = it.lon
                this[address] = it.address
            }
        }
    }

    fun getData(): List<BusStop> {
        return transaction {
            addLogger(StdOutSqlLogger)
            BusStopTable.selectAll().distinct().map {
                BusStop(
                    it[BusStopTable.id].value,
                    it[lat],
                    it[lon],
                    it[address]
                )
            }
        }
    }
}