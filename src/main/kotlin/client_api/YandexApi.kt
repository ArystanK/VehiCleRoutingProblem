package client_api

import BusStopsDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

const val request =
    "https://search-maps.yandex.ru/v1/" +
            "?apikey=1a18a6ef-f125-40ad-b5e0-ff8c415455bf" +
            "&text=public%20transport%20stop" +
            "&lang=en_KZ" +
            "&bbox=71.4997283355343,51.143921836838146~71.36570975991546,51.05627231299449" +
            "&rspn=1" +
            "&results=500"

class YandexApi {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                }
            )
        }
    }

    suspend fun getBusStops(): BusStopsDto {
        return client.get(request).body()
    }
}