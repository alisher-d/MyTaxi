package uz.texnopos.mytaxi.ui.map

import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.texnopos.mytaxi.utils.isConnected

class MapViewModel(private val geocoder: Geocoder) : ViewModel() {

    private var _address = MutableLiveData<String>()
    val address get() = _address

    fun getAddressByCoordinate(latLng: LatLng) {
        try {
            if (isConnected()) {
                val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                viewModelScope.launch {
                    _address.value = withContext(IO) {
                        try {
                            if (addresses.size > 0) addresses[0].getAddressLine(0)
                            else "Not specified"
                        } catch (e: Exception) {
                            e.localizedMessage
                        }
                    }!!
                }
            } else _address.value = "Network issue"
        } catch (e: Exception) {
            e.localizedMessage
        }

    }
}