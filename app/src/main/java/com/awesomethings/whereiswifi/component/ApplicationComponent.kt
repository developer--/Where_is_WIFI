package com.awesomethings.whereiswifi.component

import com.awesomethings.whereiswifi.app.MyApplication
import com.awesomethings.whereiswifi.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Jemo on 8/8/16.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface ApplicationComponent {
    fun inject(application: MyApplication)
}