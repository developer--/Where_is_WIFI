package com.awesomethings.whereiswifi.component

import com.awesomethings.whereiswifi.app.MyApplication
import com.awesomethings.whereiswifi.module.AppModule
import com.awesomethings.whereiswifi.view.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Jemo on 8/8/16.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface ApplicationComponent {
    fun inject(application: MyApplication)
    fun inject(application: MainActivity)
}