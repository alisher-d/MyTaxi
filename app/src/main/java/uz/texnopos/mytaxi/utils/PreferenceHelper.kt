package uz.texnopos.mytaxi.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class IntPreference(
    private val pref: SharedPreferences,
    private val defValue: Int,
) : ReadWriteProperty<Any, Int> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Int =
        pref.getInt(property.name, defValue)


    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        pref.edit { putInt(property.name, value) }
    }

}
