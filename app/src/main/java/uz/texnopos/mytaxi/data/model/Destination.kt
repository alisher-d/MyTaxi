package uz.texnopos.mytaxi.data.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class Destination(
    val start: LatLng,
    val end: LatLng,
    val whereFrom: String,
    val whereTo: String,
):Parcelable