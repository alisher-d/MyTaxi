package uz.texnopos.mytaxi.data

import android.content.Context
import com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL
import uz.texnopos.mytaxi.utils.IntPreference
import javax.inject.Inject

class SharedPref @Inject constructor(context: Context) {

    private val pref = context.getSharedPreferences("MyTaxi", Context.MODE_PRIVATE)

    var mapType by IntPreference(pref, MAP_TYPE_NORMAL)
}