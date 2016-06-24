package info.appcube.sunshine.main

import info.appcube.sunshine.api.ApiProtocol

/**
 * Created by artjom on 25.06.16.
 */
class MainPresenter(val view: MainProtocol.MainView, val city: String, val api: ApiProtocol) : MainProtocol.MainListener {

    override fun loadData() {
        view.showLoadingProgress()
        api.loadData(city,
                {
                    response ->
                    view.showData(response)
                },
                {
                    error ->
                    view.showError()
                }
        )
    }
}