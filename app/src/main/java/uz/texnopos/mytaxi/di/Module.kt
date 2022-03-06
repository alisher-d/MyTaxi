package uz.texnopos.mytaxi.di

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.texnopos.mytaxi.data.SharedPref
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @[Provides Singleton]
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPref =
        SharedPref(context)

    @[Provides Singleton]
    fun provideGeocoder(@ApplicationContext context: Context): Geocoder = Geocoder(context)

    @[Provides Singleton]
    fun provideFusedLocationClient(@ApplicationContext context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
}