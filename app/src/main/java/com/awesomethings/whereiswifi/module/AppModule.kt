package com.awesomethings.whereiswifi.module

import android.content.Context
import android.location.LocationManager
import com.activeandroid.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Created by Jemo on 8/8/16.
 */
@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    @ForApplication
    fun provideApplicationContext() : Context {
        return application
    }

    @Provides
    @Singleton
    fun provideLocationManager() : LocationManager {
        return application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ForApplication
