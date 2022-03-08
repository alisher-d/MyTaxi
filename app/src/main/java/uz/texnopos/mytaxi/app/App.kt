package uz.texnopos.mytaxi.app

import android.app.Application
import android.content.res.Configuration
import android.util.DisplayMetrics
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class App : Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        val res = this.resources
        val dm: DisplayMetrics = res.displayMetrics
        val conf: Configuration = res.configuration
        conf.setLocale(Locale("ru"))
        res.updateConfiguration(conf, dm)
        instance = this
    }
}
