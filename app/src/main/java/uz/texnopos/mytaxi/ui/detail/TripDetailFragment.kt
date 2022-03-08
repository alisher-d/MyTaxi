package uz.texnopos.mytaxi.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.addPolyline
import dagger.hilt.android.AndroidEntryPoint
import uz.texnopos.mytaxi.R
import uz.texnopos.mytaxi.data.SharedPref
import uz.texnopos.mytaxi.data.model.Destination
import uz.texnopos.mytaxi.databinding.FragmentTripDetailBinding
import uz.texnopos.mytaxi.helper.SupportMapFragment
import uz.texnopos.mytaxi.ui.map.MapTypeDialog
import uz.texnopos.mytaxi.utils.*
import javax.inject.Inject


@AndroidEntryPoint
class TripDetailFragment : Fragment(R.layout.fragment_trip_detail) {
    private val binding by viewBinding(FragmentTripDetailBinding::bind)
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NestedScrollView>
    private val viewModel by viewModels<TripDetailVM>()

    @Inject
    lateinit var pref: SharedPref

    private lateinit var googleMap: GoogleMap
    private lateinit var destination: Destination
    private val mapTypeDialog by lazy { MapTypeDialog() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        destination = arguments?.let { TripDetailFragmentArgs.fromBundle(it).destination }!!
        binding.apply {
            val mapFragment =
                childFragmentManager.findFragmentById(R.id.tripDetailMap) as SupportMapFragment
            mapFragment.getMapAsync(mapFragment)
            mapFragment.onMapReady {
                googleMap = it
                googleMap.mapType = pref.mapType
                googleMap.uiSettings.apply {
                    isCompassEnabled = false
                }

                observe()
                viewModel.findRoutes(destination)
            }

            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

            tvFrom.text = destination.whereFrom
            tvTo.text = destination.whereTo

            btnBack.onClick {
                requireActivity().onBackPressed()
            }

            btnDeleteData.onClick {
                requireActivity().onBackPressed()
            }

            btnMapType.onClick {
                mapTypeDialog.show()
            }

            val peekHeight = (resources.displayMetrics.heightPixels * 0.6).toInt()
            bottomSheetBehavior.peekHeight = peekHeight

        }
    }

    private fun MapTypeDialog.show() {
        if (!isAdded) show(this@TripDetailFragment.requireActivity().supportFragmentManager, TAG)
        mapTypeOnClick {
            if (it != googleMap.mapType) {
                googleMap.mapType = it
                pref.mapType = it
            }
            dismiss()
        }
    }
    private fun observe() {
        viewModel.destination.observe(viewLifecycleOwner) {
            when (it) {
                is State.ErrorState -> toast(it.exception?.localizedMessage ?: "Что-то не так")
                is State.LoadingState -> toast("Поиск маршрута...")
                is State.SuccessState -> {

                    val shortestRouteIndex = it.data.shortestRouteIndex
                    val route = it.data.route?.get(shortestRouteIndex)

                    val polyline = googleMap.addPolyline {
                        width(8f)
                        color(getColorRes(R.color.poly_color))
                        addAll(route!!.points)
                    }

                    val k = polyline.points.size
                    val startLatLng = polyline.points[0]
                    val middleLatLng = polyline.points[k / 2]
                    val endLatLng = polyline.points[k - 1]

                    val cameraUpdateMiddle = newLatLngZoom(middleLatLng ?: destination.start, 12f)
                    googleMap.animateCamera(cameraUpdateMiddle)

                    googleMap.addMarker {
                        title("Откуда?")
                        snippet(destination.whereFrom)
                        icon(bitmapFromVector(R.drawable.ic_location_red_14))
                        position(startLatLng!!)
                    }

                    googleMap.addMarker {
                        title("Куда?")
                        snippet(destination.whereTo)
                        icon(bitmapFromVector(R.drawable.ic_location_blue_14))
                        position(endLatLng!!)
                    }
                }
            }
        }
    }
}