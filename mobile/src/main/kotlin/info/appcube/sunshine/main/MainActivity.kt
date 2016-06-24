package info.appcube.sunshine.main

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import info.appcube.sunshine.ForecastAdapter
import info.appcube.sunshine.R
import info.appcube.sunshine.api.WeatherForecastResponse
import info.appcube.sunshine.api.WeatherServiceContoller
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainProtocol.MainView {
    val presenter = MainPresenter(this, "Bonn", WeatherServiceContoller)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WeatherServiceContoller.init(getString(R.string.api_url), getString(R.string.api_key))
        refreshLayout.setOnRefreshListener { presenter.loadData() }
        presenter.loadData()
    }

    override fun showData(data: WeatherForecastResponse?) {
        refreshLayout.post({ refreshLayout.isRefreshing = false })
        forecastList.adapter = ForecastAdapter(data)
        forecastList.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    override fun showError() {
        refreshLayout.post({ refreshLayout.isRefreshing = false })
        Snackbar.make(refreshLayout, getString(R.string.error_while_loading), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry), { presenter.loadData() })
                .show()
    }

    override fun showLoadingProgress() {
        refreshLayout.post( {refreshLayout.isRefreshing = true} )
    }
}
