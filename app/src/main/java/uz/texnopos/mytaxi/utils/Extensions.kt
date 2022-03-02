package uz.texnopos.mytaxi.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

fun Double.format(digits: Int) = "%.${digits}f".format(this)
fun Fragment.bitmapFromVector(vectorResId: Int): BitmapDescriptor? {
    // below line is use to generate a drawable.
    val vectorDrawable = ContextCompat.getDrawable(this.requireContext(), vectorResId)

    // below line is use to set bounds to our vector drawable.
    vectorDrawable!!.setBounds(
        0,
        0,
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight
    )

    // below line is use to create a bitmap for our
    // drawable which we have added.
    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // below line is use to add bitmap in our canvas.
    val canvas = Canvas(bitmap)

    // below line is use to draw our
    // vector drawable in canvas.
    vectorDrawable.draw(canvas)

    // after generating our bitmap we are returning our bitmap.
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
inline fun <T : View> T.onClick(crossinline func: T.() -> Unit) = setOnClickListener { func() }
inline fun <T : Toolbar> T.navOnClick(crossinline func: T.() -> Unit) =
    setNavigationOnClickListener { func() }

