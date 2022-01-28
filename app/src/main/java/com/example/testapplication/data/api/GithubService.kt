package com.example.testapplication.data.api

import com.example.testapplication.domain.entity.Repo
import retrofit2.HttpException
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException

interface GithubService {

    @Throws(IOException::class, HttpException::class)
    @GET("repositories")
    suspend fun getRepoList(@Query("since") sinceRepoId: Int): List<Repo>

    @GET("repositories/{id}")
    suspend fun getRepoById(@Path("id") id: Int): Repo
}