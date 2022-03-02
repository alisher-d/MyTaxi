package uz.texnopos.mytaxi.ui.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.texnopos.mytaxi.R
import uz.texnopos.mytaxi.databinding.FragmentMapBinding


class MapFragment : Fragment(R.layout.fragment_map) {
    private val binding by viewBinding(FragmentMapBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(mapFragment)

        binding.apply {
            btnMyLocation.setOnClickListener {
                mapFragment
            }
        }
    }
}