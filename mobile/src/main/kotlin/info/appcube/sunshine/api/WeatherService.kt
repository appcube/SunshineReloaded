package info.appcube.sunshine.api

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by artjom on 15.03.16.
 */
interface WeatherService {

    @GET("forecast/daily?units=metric&mode=json")
    fun getWeatherForecast(@Query("q") location: String): Observable<WeatherForecastResponse>
}
