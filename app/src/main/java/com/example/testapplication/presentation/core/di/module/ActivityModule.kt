package com.example.testapplication.presentation.core.di.module

import com.example.testapplication.presentation.core.di.annotation.PerActivity
import com.example.testapplication.presentation.ui.MainActivity
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

@Module(includes = [(AndroidInjectionModule::class), (FragmentModule::class)])
interface ActivityModule {

    @ContributesAndroidInjector
    @PerActivity
    fun mainActivity(): MainActivity
}