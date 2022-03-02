package uz.texnopos.mytaxi.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import uz.texnopos.mytaxi.R
import uz.texnopos.mytaxi.databinding.FragmentTripDetailBinding


class TripDetailFragment : Fragment(R.layout.fragment_trip_detail) {
    private val binding by viewBinding(FragmentTripDetailBinding::bind)
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NestedScrollView>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.peekHeight = 1000

        }
    }
}