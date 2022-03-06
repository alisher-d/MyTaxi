package uz.texnopos.mytaxi.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.directions.route.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.internal.OnConnectionFailedListener
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.texnopos.mytaxi.BuildConfig
import uz.texnopos.mytaxi.data.model.Destination
import uz.texnopos.mytaxi.utils.State
import javax.inject.Inject

@HiltViewModel
class TripDetailVM @Inject constructor() : ViewModel(), RoutingListener,
    OnConnectionFailedListener {
    private var _destination = MutableLiveData<State<uz.texnopos.mytaxi.data.model.Routing>>()
    val destination get() = _destination

    fun findRoutes(destination: Destination) {
        val routing = Routing.Builder()
            .travelMode(AbstractRouting.TravelMode.DRIVING)
            .withListener(this)
            .alternativeRoutes(true)
            .waypoints(destination.start, destination.end)
            .key(BuildConfig.MAPS_API_KEY)
            .build()
        routing.execute()
    }

    override fun onRoutingFailure(p0: RouteException?) {
        _destination.value = State.ErrorState(p0)
    }

    override fun onRoutingStart() {
        _destination.value = State.LoadingState
    }

    override fun onRoutingSuccess(route: ArrayList<Route>?, shortestRouteIndex: Int) {
        _destination.value =
            State.SuccessState(uz.texnopos.mytaxi.data.model.Routing(route, shortestRouteIndex))
    }

    override fun onRoutingCancelled() {
        _destination.value = State.ErrorState(Exception("Отменено"))
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        _destination.value = State.ErrorState(Exception(p0.errorMessage))
    }
}