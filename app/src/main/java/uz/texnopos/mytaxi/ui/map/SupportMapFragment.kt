package uz.texnopos.mytaxi.ui.map

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.maps.android.ktx.addMarker
import uz.texnopos.mytaxi.R
import uz.texnopos.mytaxi.utils.bitmapFromVector
import uz.texnopos.mytaxi.utils.format

class SupportMapFragment : SupportMapFragment(), OnMapReadyCallback {

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.clear()

        var center = googleMap.cameraPosition.target
        val marker = googleMap.addMarker {
            position(center)
            icon(bitmapFromVector(R.drawable.ic_map_pin))
        }
        googleMap.setOnMyLocationButtonClickListener {
            true
        }
        googleMap.uiSettings.apply {
            isCompassEnabled = false
            isRotateGesturesEnabled = false
            isMyLocationButtonEnabled = false
        }


        googleMap.setOnCameraMoveListener {
            center = googleMap.cameraPosition.target
            marker?.position = center
            marker?.title = "lat=${center.latitude.format(2)} long=${center.longitude.format(2)}"
        }
    }


}