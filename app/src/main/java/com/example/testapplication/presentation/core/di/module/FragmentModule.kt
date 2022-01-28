package com.example.testapplication.presentation.core.di.module

import com.example.testapplication.presentation.core.di.annotation.PerFragment
import com.example.testapplication.presentation.ui.detail.RepoDetailsFragment
import com.example.testapplication.presentation.ui.list.RepoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface FragmentModule {

    @ContributesAndroidInjector
    @PerFragment
    fun bindRepoListFragment(): RepoListFragment

    @ContributesAndroidInjector
    @PerFragment
    fun bindRepoDetailsFragment(): RepoDetailsFragment
}