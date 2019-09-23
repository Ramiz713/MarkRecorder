package com.itis2019.markrecorder.repository.dbEntities

import androidx.room.Embedded
import androidx.room.Relation

data class DbRecordWithFolder(
    @Embedded
    val record: DbRecord,
    @Relation(parentColumn = "folderId", entityColumn = "id")
    val folder: DbFolder
)
