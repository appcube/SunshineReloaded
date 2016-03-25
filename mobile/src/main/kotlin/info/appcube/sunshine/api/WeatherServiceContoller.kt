package info.appcube.sunshine.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

/**
 * Created by artjom on 16.03.16.
 */

object WeatherServiceContoller {

    lateinit var service: WeatherService;


    fun init(apiUrl : String, apiKey : String) {
        var client = OkHttpClient().newBuilder()
        var loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        client.addInterceptor({ chain ->
            var url = chain!!.request().url().newBuilder()
                    .addEncodedQueryParameter("appid", apiKey)
                    .addEncodedQueryParameter("lang", "en")
                    .build()
            var request = chain.request().newBuilder().url(url).build()
            chain.proceed(request)
        })
        client.addInterceptor(loggingInterceptor)
        val retrofit = Retrofit.Builder()
                .baseUrl(apiUrl)
                .client(client.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(WeatherService::class.java);
    }

    fun loadData(city: String) : Observable<WeatherForecastResponse> {
        return service.getWeatherForecast(location = city);
    }
}