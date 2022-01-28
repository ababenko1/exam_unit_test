package com.example.testapplication.presentation.core.di


import com.example.testapplication.presentation.TestApplication
import dagger.android.AndroidInjector


object AppInjector {

    fun init(app: TestApplication): AndroidInjector<TestApplication> =
        DaggerApplicationComponent.builder().create(app)
}