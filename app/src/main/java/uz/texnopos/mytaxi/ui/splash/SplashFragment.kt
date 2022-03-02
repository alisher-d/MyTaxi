package uz.texnopos.mytaxi.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import uz.texnopos.mytaxi.R


class SplashFragment : Fragment(R.layout.fragment_splash) {
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        Handler(Looper.getMainLooper()).postDelayed(navigateUI(), 2000)

    }

    private fun navigateUI(): () -> Unit {
        return { navController.navigate(R.id.action_splashFragment_to_mainFragment) }
    }
}