package uz.texnopos.mytaxi.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import uz.texnopos.mytaxi.BuildConfig
import uz.texnopos.mytaxi.R
import uz.texnopos.mytaxi.databinding.FragmentMapBinding
import uz.texnopos.mytaxi.utils.TAG
import uz.texnopos.mytaxi.utils.hasPermission
import uz.texnopos.mytaxi.utils.isLocationEnabled
import uz.texnopos.mytaxi.utils.onClick


class MapFragment : Fragment(R.layout.fragment_map) {
    private val binding by viewBinding(FragmentMapBinding::bind)
    private var timer: CountDownTimer? = null
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity().applicationContext)
    }
    private lateinit var googleMap: GoogleMap
    private var cancellationTokenSource = CancellationTokenSource()

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Snackbar.make(
                    binding.container,
                    R.string.permission_approved_explanation,
                    LENGTH_LONG
                )
                    .show()
            } else {
                Snackbar.make(
                    binding.container,
                    R.string.fine_permission_denied_explanation,
                    LENGTH_LONG
                )
                    .setAction(R.string.settings) {
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
                    .show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(mapFragment)
        val viewModel = MapViewModel(Geocoder(requireContext()))
        mapFragment.onMapReady { googleMap ->
            var center = googleMap.cameraPosition.target
            viewModel.getAddressByCoordinate(center)
            this.googleMap = googleMap
            googleMap.uiSettings.apply {
                isCompassEnabled = false
                isRotateGesturesEnabled = false
                isMyLocationButtonEnabled = false
            }
            googleMap.setOnCameraMoveListener {
                center = googleMap.cameraPosition.target
                timer?.cancel()
                timer = object : CountDownTimer(500, 500) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        viewModel.getAddressByCoordinate(center)
                    }
                }.start()
            }
        }
        binding.apply {
            viewModel.address.observe(viewLifecycleOwner) {
                tvAddressName.text = it
            }
            btnMyLocation.onClick {
                if (isLocationEnabled()) locationRequest()
                else showAlert()
            }
        }
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Enable Location")
            .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to use this app")
            .setPositiveButton("Location Settings") { _, _ ->
                val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(myIntent)
            }
            .setNegativeButton("Cancel") { _, _ -> }
        dialog.show()
    }


    private fun locationRequest() {
        Log.d(TAG, "locationRequestOnClick()")
        val permissionApproved =
            requireActivity().applicationContext.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)

        if (permissionApproved) requestCurrentLocation()
        else requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @SuppressLint("MissingPermission")
    private fun requestCurrentLocation() {
        Log.d(TAG, "requestCurrentLocation()")

        fusedLocationClient.getCurrentLocation(
            PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        )
            .addOnSuccessListener { location ->
                val coordinate = LatLng(location.latitude, location.longitude)
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordinate, 18f)
                googleMap.animateCamera(cameraUpdate)
                Log.d(TAG, "Location (success): ${location.latitude}, ${location.longitude}")
            }
            .addOnFailureListener {
                Log.d(TAG, "Location (failure): $it")
            }
    }

    override fun onStop() {
        super.onStop()
        cancellationTokenSource.cancel()
    }
}