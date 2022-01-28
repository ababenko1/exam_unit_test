package com.example.testapplication.presentation.core.di

import com.example.testapplication.presentation.TestApplication
import com.example.testapplication.presentation.core.di.annotation.PerApplication
import com.example.testapplication.presentation.core.di.module.CoreModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule


@PerApplication
@Component(modules = [(CoreModule::class), (AndroidSupportInjectionModule::class)])
interface ApplicationComponent : AndroidInjector<TestApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TestApplication>()
}















