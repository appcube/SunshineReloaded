package info.appcube.sunshine.api

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by artjom on 16.03.16.
 */
class WeatherForecast {
    @SerializedName("temp")
    var temperature: WeatherTemperature = WeatherTemperature()

    var weather: List<WeatherDescription> = ArrayList()

    @SerializedName("dt")
    var timestamp: Long = 0
}