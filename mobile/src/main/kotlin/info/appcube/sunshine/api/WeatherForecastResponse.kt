package info.appcube.sunshine.api

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by artjom on 16.03.16.
 */
class WeatherForecastResponse {
    @SerializedName("list")
    var forecasts: List<WeatherForecast> = ArrayList()
}