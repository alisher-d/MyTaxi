package uz.texnopos.mytaxi.ui.map

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import uz.texnopos.mytaxi.BuildConfig
import uz.texnopos.mytaxi.R
import uz.texnopos.mytaxi.data.SharedPref
import uz.texnopos.mytaxi.databinding.FragmentMapBinding
import uz.texnopos.mytaxi.helper.EventObserver
import uz.texnopos.mytaxi.helper.SupportMapFragment
import uz.texnopos.mytaxi.utils.*
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {
    private val binding by viewBinding(FragmentMapBinding::bind)
    private var timer: CountDownTimer? = null
    private val viewModel by viewModels<MapVM>()
    private lateinit var googleMap: GoogleMap
    private lateinit var center: LatLng
    private val mapTypeDialog by lazy { MapTypeDialog() }
    @Inject
    lateinit var pref: SharedPref
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) permissionApprovedSnackBar() else permissionDeniedSnackBar()
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(mapFragment)
        observers()
        mapFragment.onMapReady {
            googleMap = it
            googleMap.uiSettings.apply {
                isCompassEnabled = false
                isRotateGesturesEnabled = false
                isMyLocationButtonEnabled = false
            }
            googleMap.mapType = pref.mapType

            if (!this::center.isInitialized)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NUKUS, 5f))

            center = googleMap.cameraPosition.target
            viewModel.getAddressByCoordinate(center)


            googleMap.setOnCameraMoveListener {
                center = googleMap.cameraPosition.target
                timer?.cancel()
                timer = object : CountDownTimer(750, 750) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        viewModel.getAddressByCoordinate(center)
                    }
                }.start()
            }
        }

        binding.apply {
            btnMyLocation.onClick {
                if (isLocationEnabled()) locationRequest()
                else showAlert()
            }
            btnMapType.onClick {
                mapTypeDialog.show()
            }
        }
    }


    private fun observers() {
        viewModel.address.observe(viewLifecycleOwner) {
            binding.tvAddressName.text = it
        }
        viewModel.myLocation.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is State.ErrorState -> {
                    isSearchingLocation(false)
                    toast("??????????????: ${it.exception}")
                }
                is State.LoadingState -> {
                    isSearchingLocation(true)
                }
                is State.SuccessState -> {
                    isSearchingLocation(false)
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(it.data, 18f)
                    if (this::googleMap.isInitialized) googleMap.animateCamera(cameraUpdate)
                }
            }
        })
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle(getString(R.string.enable_location))
            .setMessage(getString(R.string.enable_location_message))
            .setPositiveButton(getString(R.string.location_settings)) { _, _ ->
                val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(myIntent)
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
        dialog.show()
    }

    private fun MapTypeDialog.show() {
        if (!this.isResumed) show(this@MapFragment.requireActivity().supportFragmentManager, TAG)
        mapTypeOnClick {
            if (it != googleMap.mapType) {
                googleMap.mapType = it
                pref.mapType = it
            }
            dismiss()
        }
    }

    private fun locationRequest() {
        if (hasPermission(ACCESS_FINE_LOCATION)) viewModel.requestCurrentLocation()
        else requestPermission.launch(ACCESS_FINE_LOCATION)
    }

    private fun permissionApprovedSnackBar() {
        Snackbar.make(binding.root, R.string.permission_approved_explanation, LENGTH_LONG).show()
    }

    private fun permissionDeniedSnackBar() {
        Snackbar.make(
            binding.root,
            R.string.fine_permission_denied_explanation,
            LENGTH_LONG
        )
            .setAction(R.string.settings) { launchSettings() }
            .setActionTextColor(getColorRes(R.color.white))
            .show()
    }

    private fun launchSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts(
            "package",
            BuildConfig.APPLICATION_ID, null
        )
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    companion object {
        private val NUKUS = LatLng(42.460168, 59.607280)
    }

    private fun isSearchingLocation(isSearching: Boolean) {
        binding.apply {
            progressBar.isVisible = isSearching
            btnMyLocation.isInvisible = isSearching
        }
    }

}
