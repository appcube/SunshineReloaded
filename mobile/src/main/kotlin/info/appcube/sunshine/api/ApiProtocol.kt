package info.appcube.sunshine.api

/**
 * Created by artjom on 25.06.16.
 */
interface ApiProtocol {
    fun loadData(city: String, onResponse: (WeatherForecastResponse?) -> Unit, onFailure: (Throwable?) -> Unit)
}