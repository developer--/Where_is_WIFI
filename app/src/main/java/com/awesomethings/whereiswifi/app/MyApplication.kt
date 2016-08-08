package com.awesomethings.whereiswifi.app

import android.location.LocationManager
import com.activeandroid.ActiveAndroid
import com.activeandroid.Configuration
import com.awesomethings.whereiswifi.component.ApplicationComponent
import com.awesomethings.whereiswifi.module.AppModule
import javax.inject.Inject


/**
 * Created by Jemo on 8/8/16.
 */
class MyApplication : com.activeandroid.app.Application() {

    companion object {
        //platformStatic allow access it from java code
        @JvmStatic lateinit var graph: ApplicationComponent
    }
    @Inject
    lateinit var locationManager : LocationManager

    override fun onCreate() {
        super.onCreate()
        val dbConfiguration = Configuration.Builder(this).setDatabaseName("wifi.db").create()
        ActiveAndroid.initialize(dbConfiguration)
//        graph = DaggerApplicationComponent.builder().appModule(AppModule(this)).build()
//        graph.inject(this)

    }

}