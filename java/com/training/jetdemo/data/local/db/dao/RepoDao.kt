package com.training.jetdemo.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.training.jetdemo.data.local.db.entity.RepoEntity
import io.reactivex.Single

@Dao
interface RepoDao {
    @Query("SELECT * FROM repo_entity")
    fun getAll(): Single<List<RepoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(item: List<RepoEntity>)
}