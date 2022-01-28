package com.example.testapplication.presentation.core.di.module

import com.example.testapplication.presentation.core.di.module.viewModel.ViewModelModule
import dagger.Module

@Module(
    includes = [
        (ApplicationModule::class),
        (ViewModelModule::class),
        (ActivityModule::class),
        (FragmentModule::class)
    ],
)
interface CoreModule