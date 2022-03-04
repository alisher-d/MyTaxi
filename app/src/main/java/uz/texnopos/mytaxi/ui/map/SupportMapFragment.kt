package uz.texnopos.mytaxi.ui.map

import android.location.Location
import android.location.LocationListener
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class SupportMapFragment : SupportMapFragment(), OnMapReadyCallback, LocationListener {

    private var map: (googleMap: GoogleMap) -> Unit = {}
    fun onMapReady(map: (googleMap: GoogleMap) -> Unit) {
        this.map = map
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map.invoke(googleMap)
    }

    override fun onLocationChanged(location: Location) {

    }


}