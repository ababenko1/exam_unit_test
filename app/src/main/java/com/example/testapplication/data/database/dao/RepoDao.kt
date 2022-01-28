package com.example.testapplication.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.testapplication.domain.entity.Repo

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Repo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Repo)

    @Query("SELECT * FROM repo")
    fun getAll(): PagingSource<Int, Repo>

    @Query("SELECT * FROM repo WHERE id=:id")
    suspend fun getRepoById(id: Int): Repo

    @Update
    suspend fun update(repo: Repo)

    @Query("DELETE FROM repo")
    suspend fun deleteAll()
}