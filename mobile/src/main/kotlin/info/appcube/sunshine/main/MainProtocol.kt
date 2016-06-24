package info.appcube.sunshine.main

import info.appcube.sunshine.api.WeatherForecastResponse

/**
 * Created by artjom on 25.06.16.
 */
interface MainProtocol {
    interface MainView {
        fun showData(data: WeatherForecastResponse?)
        fun showError()
        fun showLoadingProgress()
    }

    interface MainListener {
        fun loadData()
    }
}