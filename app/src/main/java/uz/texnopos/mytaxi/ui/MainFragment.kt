package uz.texnopos.mytaxi.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.navigation.NavigationView
import uz.texnopos.mytaxi.R
import uz.texnopos.mytaxi.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main),
    NavigationView.OnNavigationItemSelectedListener {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController =
            Navigation.findNavController(requireActivity(), R.id.mainContainer)
        binding.apply {
            val activity = (requireActivity() as AppCompatActivity)
            activity.setSupportActionBar(appBarMain.toolbar)
            val toggle = ActionBarDrawerToggle(
                requireActivity(), drawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
            )
            appBarMain.appBarMain.setBackgroundColor(0)

            toggle.isDrawerIndicatorEnabled = false
            toggle.setHomeAsUpIndicator(R.drawable.ic_nav_drawer)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            navView.setNavigationItemSelectedListener(this@MainFragment)
            drawerLayout.setViewScale(Gravity.START, 0.90f)
            drawerLayout.setViewElevation(Gravity.START, 18f)
            drawerLayout.setRadius(Gravity.START, 40f)
            drawerLayout.setViewRotation(Gravity.START,20f)

            appBarMain.toolbar.setNavigationOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)

            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.drawer_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_trips -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                navController.navigate(R.id.action_mainFragment_to_tripHistoryFragment)
                true
            }
            else -> false
        }
    }
}