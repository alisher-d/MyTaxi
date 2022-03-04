package uz.texnopos.mytaxi.ui.detail

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.directions.route.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.internal.OnConnectionFailedListener
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import uz.texnopos.mytaxi.BuildConfig.MAPS_API_KEY
import uz.texnopos.mytaxi.R
import uz.texnopos.mytaxi.databinding.FragmentTripDetailBinding
import uz.texnopos.mytaxi.ui.map.SupportMapFragment
import uz.texnopos.mytaxi.utils.onClick
import uz.texnopos.mytaxi.utils.toast


class TripDetailFragment : Fragment(R.layout.fragment_trip_detail), RoutingListener,
    OnConnectionFailedListener {
    private val binding by viewBinding(FragmentTripDetailBinding::bind)
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NestedScrollView>

    private lateinit var googleMap: GoogleMap

    private lateinit var start: LatLng
    private lateinit var end: LatLng
    private var polylines: ArrayList<Polyline> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            val mapFragment =
                childFragmentManager.findFragmentById(R.id.tripDetailMap) as SupportMapFragment
            mapFragment.getMapAsync(mapFragment)
            mapFragment.onMapReady { googleMap ->
                this@TripDetailFragment.googleMap = googleMap
                start = LatLng(39.634501, 66.970867)
                end = LatLng(42.526917, 59.633511)
                findRoutes(start, end)
            }

            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            btnBack.onClick {
                requireActivity().onBackPressed()
            }
            val peekHeight = (displayMetrics.heightPixels * 0.6).toInt()
            bottomSheetBehavior.peekHeight = peekHeight

        }
    }

    override fun onRoutingFailure(p0: RouteException?) {
        TODO("Not yet implemented")
    }

    override fun onRoutingStart() {
        toast("Finding Route...")
    }

    override fun onRoutingSuccess(route: ArrayList<Route>?, shortestRouteIndex: Int) {
//        val center = CameraUpdateFactory.newLatLng(start)
//        val zoom = CameraUpdateFactory.zoomTo(16f)
        polylines.clear()
        val polyOptions = PolylineOptions()
        var polylineStartLatLng: LatLng? = null
        var polylineEndLatLng: LatLng? = null


        polylines = ArrayList()
        //add route(s) to the map using polyline
        //add route(s) to the map using polyline
        for (i in 0 until route!!.size) {
            if (i == shortestRouteIndex) {
                polyOptions.color(resources.getColor(R.color.purple_200))
                polyOptions.width(7f)
                polyOptions.addAll(route.get(shortestRouteIndex).points)
                val polyline: Polyline = googleMap.addPolyline(polyOptions)
                polylineStartLatLng = polyline.points[0]
                val k = polyline.points.size
                polylineEndLatLng = polyline.points[k - 1]
                polylines.add(polyline)
            } else {
            }
        }

        //Add Marker on route starting position

        //Add Marker on route starting position
        val startMarker = MarkerOptions()
        startMarker.position(polylineStartLatLng!!)
        startMarker.title("My Location")
        googleMap.addMarker(startMarker)

        //Add Marker on route ending position

        //Add Marker on route ending position
        val endMarker = MarkerOptions()
        endMarker.position(polylineEndLatLng!!)
        endMarker.title("Destination")
        googleMap.addMarker(endMarker)
    }

    override fun onRoutingCancelled() {
        toast("Cancelled")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        toast("${p0.errorMessage}")
    }

    fun findRoutes(Start: LatLng?, End: LatLng?) {
        if (Start == null || End == null) {
            toast("Unable to get location")
        } else {
            val routing = Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(Start, End)
                .key(MAPS_API_KEY)
                .build()
            routing.execute()
        }
    }
}