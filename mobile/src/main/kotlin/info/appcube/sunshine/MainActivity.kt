package info.appcube.sunshine

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import info.appcube.sunshine.api.WeatherForecastResponse
import info.appcube.sunshine.api.WeatherServiceContoller
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity(), Observer<WeatherForecastResponse> {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WeatherServiceContoller.init(getString(R.string.api_url), getString(R.string.api_key))

        loadData()

        refreshLayout.setOnRefreshListener { loadData() }
    }

    private fun loadData() {
        refreshLayout.post( {refreshLayout.isRefreshing = true} )
        WeatherServiceContoller.loadData("Bonn")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this)
    }

    override fun onNext(t: WeatherForecastResponse?) {
        var adapter = ForecastAdapter(t!!)
        forecastList.adapter = adapter
        forecastList.layoutManager = LinearLayoutManager(this)
    }

    override fun onCompleted() {
        refreshLayout.post( {refreshLayout.isRefreshing = false } )
    }

    override fun onError(e: Throwable?) {
        refreshLayout.post( {refreshLayout.isRefreshing = false } )
        Snackbar.make(refreshLayout, getString(R.string.error_while_loading), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.retry), { loadData() })
            .show()
    }
}
