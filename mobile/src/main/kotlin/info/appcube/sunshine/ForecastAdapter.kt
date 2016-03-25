package info.appcube.sunshine

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import info.appcube.sunshine.api.WeatherForecast
import info.appcube.sunshine.api.WeatherForecastResponse
import kotlinx.android.synthetic.main.list_item_forecast.view.*
import kotlinx.android.synthetic.main.list_item_forecast_today.view.*

/**
 * Created by artjom on 23.03.16.
 */
class ForecastAdapter(val data : WeatherForecastResponse) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    enum class ViewType {
        TODAY, OTHER_DAY
    }

    abstract class ViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(forecast : WeatherForecast)
    }

    class OtherDayViewHolder (view : View) : ViewHolder(view) {
        override fun bind(forecast: WeatherForecast) {
            if(forecast.weather.size > 0) {
                var entry = forecast.weather[0];
                itemView.icon.setWeatherIcon(entry.id, false)
                itemView.description.text = entry.description;
            }
            itemView.date.text = forecast.timestamp.unixTimestampAsShortDate()
            itemView.lowTemp.text = forecast.temperature.min.asTemperature()
            itemView.highTemp.text = forecast.temperature.max.asTemperature()
        }

    }

    class TodayViewHolder (view : View) : ViewHolder(view) {
        override fun bind(forecast: WeatherForecast) {
            if(forecast.weather.size > 0) {
                var entry = forecast.weather[0];
                itemView.iconToday.setWeatherIcon(entry.id)
                itemView.descriptionToday.text = entry.description;
            }
            itemView.lowTempToday.text = forecast.temperature.min.asTemperature()
            itemView.highTempToday.text = forecast.temperature.max.asTemperature()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) ViewType.TODAY.ordinal else ViewType.OTHER_DAY.ordinal
    }

    override fun getItemCount(): Int {
        return data.forecasts.count();
    }

    override fun onBindViewHolder(holder: ForecastAdapter.ViewHolder?, position: Int) {
        holder?.bind(data.forecasts[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ForecastAdapter.ViewHolder? {
        var view = parent!!.inflate(if (viewType == ViewType.TODAY.ordinal)
            R.layout.list_item_forecast_today else R.layout.list_item_forecast)

        return if (viewType == ViewType.TODAY.ordinal) TodayViewHolder(view) else OtherDayViewHolder(view)
    }
}