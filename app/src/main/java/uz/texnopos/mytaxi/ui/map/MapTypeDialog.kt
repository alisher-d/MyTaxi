package uz.texnopos.mytaxi.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.maps.GoogleMap.*
import dagger.hilt.android.AndroidEntryPoint
import uz.texnopos.mytaxi.R
import uz.texnopos.mytaxi.data.SharedPref
import uz.texnopos.mytaxi.databinding.MapTypeDialogBinding
import uz.texnopos.mytaxi.utils.getColorRes
import uz.texnopos.mytaxi.utils.onClick
import javax.inject.Inject

@AndroidEntryPoint
class MapTypeDialog : DialogFragment() {
    private val binding by viewBinding(MapTypeDialogBinding::bind)
    @Inject
    lateinit var pref: SharedPref
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        return inflater.inflate(R.layout.map_type_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private var typeOnClick: (mapType: Int) -> Unit = {}

    fun mapTypeOnClick(typeOnClick: (mapType: Int) -> Unit) {
        this.typeOnClick = typeOnClick
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            when (pref.mapType) {
                MAP_TYPE_NORMAL -> lnDefault.setCardBackgroundColor(getColorRes(R.color.black_alpha))
                MAP_TYPE_SATELLITE -> lnSatellite.setCardBackgroundColor(getColorRes(R.color.black_alpha))
                MAP_TYPE_TERRAIN -> lnTerrain.setCardBackgroundColor(getColorRes(R.color.black_alpha))
            }
            lnDefault.onClick {
                typeOnClick.invoke(MAP_TYPE_NORMAL)
            }
            lnSatellite.onClick {
                typeOnClick.invoke(MAP_TYPE_SATELLITE)
            }
            lnTerrain.onClick {
                typeOnClick.invoke(MAP_TYPE_TERRAIN)
            }
        }
    }

}