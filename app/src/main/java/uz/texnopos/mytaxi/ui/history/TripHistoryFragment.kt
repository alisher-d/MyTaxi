package uz.texnopos.mytaxi.ui.history

import android.os.Bundle
import android.os.Handler
import android.os.Looper.getMainLooper
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.texnopos.mytaxi.R
import uz.texnopos.mytaxi.data.static.trips
import uz.texnopos.mytaxi.databinding.FragmentTripHistoryBinding
import uz.texnopos.mytaxi.utils.navOnClick


class TripHistoryFragment : Fragment(R.layout.fragment_trip_history) {
    private val binding by viewBinding(FragmentTripHistoryBinding::bind)
    private val tripAdapter = TripAdapter()
    private lateinit var navController: NavController
    private var isCreated = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController(requireActivity(), R.id.mainContainer)
        binding.apply {
            rvTrips.adapter = tripAdapter
            if (isCreated) {
                shimmerLayout.isInvisible = true
                rvTrips.isInvisible = false
            } else {
                Handler(getMainLooper()).postDelayed({
                    shimmerLayout.isInvisible = true
                    rvTrips.isInvisible = false
                }, 3000)
                isCreated = true
            }

            tripAdapter.setData(trips)
            toolbar.navOnClick {
                requireActivity().onBackPressed()
            }
            tripAdapter.onItemClickListener {
                val action =
                    TripHistoryFragmentDirections.actionTripHistoryFragmentToTripDetailFragment(it)
                navController.navigate(action)
            }
        }
    }
}
