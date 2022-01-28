package com.example.testapplication.presentation.core.di.module

import android.content.Context
import androidx.room.Room
import com.example.testapplication.BuildConfig
import com.example.testapplication.data.api.GithubService
import com.example.testapplication.data.database.AppDatabase
import com.example.testapplication.data.repository.RepoRepositoryImpl
import com.example.testapplication.domain.repository.RepoRepository
import com.example.testapplication.presentation.TestApplication
import com.example.testapplication.presentation.core.base.BaseViewModel
import com.example.testapplication.presentation.core.base.IBaseViewModel
import com.example.testapplication.presentation.core.di.annotation.PerApplication
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApplicationModule {

    @Provides
    @PerApplication
    fun provideApplicationContext(application: TestApplication): Context = application

    @Provides
    @PerApplication
    fun provideGSon(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @PerApplication
    fun provideHttpClient(): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @PerApplication
    fun provideRetrofit(gSon: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gSon))
            .build()
    }

    @Provides
    @PerApplication
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @PerApplication
    fun provideRestApi(retrofit: Retrofit): GithubService =
        retrofit.create(GithubService::class.java)

    @Provides
    @PerApplication
    fun provideDatabase(appContext: TestApplication): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "TestDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @PerApplication
    fun provideRepository(repository: RepoRepositoryImpl): RepoRepository = repository

    @Provides
    @PerApplication
    fun provideBaseViewModel(baseModel: BaseViewModel): IBaseViewModel = baseModel
}