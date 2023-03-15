package com.mahnoosh.core.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TestEntity(
    @PrimaryKey(autoGenerate = true) val idTable: Int = 0,
    @ColumnInfo(name = "x") val x: String
)
