package uz.texnopos.mytaxi.ui.map

import android.annotation.SuppressLint
import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.texnopos.mytaxi.utils.State
import uz.texnopos.mytaxi.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class MapVM @Inject constructor(
    private val geocoder: Geocoder,
    private val fusedLocationClient: FusedLocationProviderClient,
) : ViewModel() {
    var cancellationTokenSource = CancellationTokenSource()

    private var _address = MutableLiveData<String>()
    val address get() = _address

    private var _myLocation = MutableLiveData<State<LatLng>>()
    val myLocation get() = _myLocation

    fun getAddressByCoordinate(latLng: LatLng) {
        try {
            if (isConnected()) {
                val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                viewModelScope.launch {
                    _address.value = withContext(IO) {
                        try {
                            if (addresses.size > 0) addresses[0].getAddressLine(0)
                            else "Не указан"
                        } catch (e: Exception) {
                            e.localizedMessage
                        }
                    }!!
                }
            } else _address.value = "Проблема с сетью"
        } catch (e: Exception) {
            _address.value = e.localizedMessage
        }
    }

    @SuppressLint("MissingPermission")
    fun requestCurrentLocation() {
        _myLocation.value = State.LoadingState
        fusedLocationClient.getCurrentLocation(
            PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val location = task.result
                    val coordinate = LatLng(location.latitude, location.longitude)
                    _myLocation.value = State.SuccessState(coordinate)
                } else {
                    _myLocation.value = State.ErrorState(task.exception)
                }
            }
    }
}