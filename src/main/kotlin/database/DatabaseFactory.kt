package database

import BusStop
import BusStopTable
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

    fun saveData(busStop: BusStop) {
        transaction {
            addLogger(StdOutSqlLogger)
            BusStopTable.insert {
                it[lat] = busStop.lat
                it[lon] = busStop.lon
                it[address] = busStop.address
            }
        }
    }

    fun getData(): List<BusStop> {
        return transaction {
            addLogger(StdOutSqlLogger)
            BusStopTable.selectAll().distinct().map {
                BusStop(
                    it[BusStopTable.id].value,
                    it[BusStopTable.lat],
                    it[BusStopTable.lon],
                    it[BusStopTable.address]
                )
            }
        }
    }
}