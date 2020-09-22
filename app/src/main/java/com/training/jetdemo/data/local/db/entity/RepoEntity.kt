package com.training.jetdemo.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.training.jetdemo.data.model.License
import com.training.jetdemo.data.model.Permissions
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "repo_entity")
data class RepoEntity(

    @PrimaryKey(autoGenerate = true)
    @NotNull
    val id: Int,

    @ColumnInfo(name = "user_name")
    val name: String?=null,

    @ColumnInfo(name = "url")
    val url: String?=null,

    @ColumnInfo(name = "description")

    val description: String?=null,

    @ColumnInfo(name = "open_issues_count")
    val open_issues_count: Int?=null,

    @Embedded
    val license: License?=null,

    @Embedded
    val permissions: Permissions?=null

):Serializable
