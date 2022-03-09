package uz.texnopos.mytaxi.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.LocationManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import uz.texnopos.mytaxi.helper.Event

const val TAG = "tekseriw"
fun Fragment.bitmapFromVector(vectorResId: Int): BitmapDescriptor {
    val vectorDrawable = ContextCompat.getDrawable(this.requireContext(), vectorResId)

    vectorDrawable!!.setBounds(
        0,
        0,
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight
    )

    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

inline fun <T : View> T.onClick(crossinline func: T.() -> Unit) = setOnClickListener { func() }
inline fun <T : Toolbar> T.navOnClick(crossinline func: T.() -> Unit) =
    setNavigationOnClickListener { func() }

fun Fragment.isLocationEnabled(): Boolean {
    val locationManager =
        requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER
    )
}

fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, text, duration).show()

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    if (context != null) {
        context!!.toast(text, duration)
    }
}



fun Context.hasPermission(permission: String): Boolean {

    if (permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
        android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q
    ) return true

    return ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}

fun Fragment.hasPermission(permission: String): Boolean {
    return requireActivity().applicationContext.hasPermission(permission)
}

fun Long.changeFormat(): String {
    val num = this.toString()
    var s = ""
    val sz = num.length
    for (i in 0 until sz) {
        if (i != 0 && (i - sz % 3) % 3 == 0) s += ' '
        s += num[i]
    }
    return "$s сум"
}


fun Fragment.getColorRes(colorRes: Int): Int = ContextCompat.getColor(requireContext(), colorRes)


