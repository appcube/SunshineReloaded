package info.appcube.sunshine.api

import info.appcube.sunshine.call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * Created by artjom on 16.03.16.
 */

object WeatherServiceContoller : ApiProtocol {

    lateinit var service: WeatherService;

    fun init(apiUrl : String, apiKey : String) {
        val client = OkHttpClient().newBuilder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        client.addInterceptor({ chain ->
            val url = chain!!.request().url().newBuilder()
                    .addEncodedQueryParameter("appid", apiKey)
                    .addEncodedQueryParameter("lang", Locale.getDefault().country)
                    .build()
            val request = chain.request().newBuilder().url(url).build()
            chain.proceed(request)
        })
        client.addInterceptor(loggingInterceptor)
        val retrofit = Retrofit.Builder()
                .baseUrl(apiUrl)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(WeatherService::class.java);
    }

    override fun loadData(city: String, onResponse: (WeatherForecastResponse?) -> Unit, onFailure: (Throwable?) -> Unit) {
        service.getWeatherForecast(location = city).call(onResponse, onFailure)
    }
}