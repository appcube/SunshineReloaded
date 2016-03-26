package info.appcube.sunshine

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import java.text.DateFormat
import java.util.*

/**
 * Created by artjom on 23.03.16.
 */

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ImageView.setWeatherIcon(weatherId: Int, colored: Boolean = true) {
    // Based on weather code data found at:
    // http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
    when(weatherId) {
        in 200..232, 781 -> setImageResource(if (colored) R.drawable.art_storm else R.drawable.ic_storm)
        in 300..321 -> setImageResource(if (colored) R.drawable.art_light_rain else R.drawable.ic_light_rain)
        in 500..531 -> setImageResource(if (colored) R.drawable.art_rain else R.drawable.ic_rain)
        in 600..622 -> setImageResource(if (colored) R.drawable.art_snow else R.drawable.ic_snow)
        in 701..761 -> setImageResource(if (colored) R.drawable.art_fog else R.drawable.ic_fog)
        800 -> setImageResource(if (colored) R.drawable.art_clear else R.drawable.ic_clear)
        801 -> setImageResource(if (colored) R.drawable.art_light_clouds else R.drawable.ic_light_clouds)
        in 802..804 -> setImageResource(if (colored) R.drawable.art_clouds else R.drawable.ic_clouds)
    }
}

fun Long.unixTimestampAsShortDate() : String {
    return DateFormat.getDateInstance(DateFormat.SHORT).format(Date(this * 1000))
}

fun Double.asTemperature() : String {
    return "%.1f °C".format(this)
}
