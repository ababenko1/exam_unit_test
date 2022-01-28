package com.example.testapplication.domain.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "repo")
data class Repo(
    @PrimaryKey val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("full_name")
    val fullName: String,
    @field:SerializedName("description")
    val description: String?,
    @field:SerializedName("owner")
    @field:Embedded(prefix = "owner_")
    val owner: Owner,
    @field:SerializedName("stargazers_count")
    val stars: Int,
    @field:SerializedName("created_at")
    val createdAt: String? = null,
) {

    data class Owner(
        @field:SerializedName("login")
        val login: String,
        @field:SerializedName("avatar_url")
        val avatarUrl: String?,
    )
}