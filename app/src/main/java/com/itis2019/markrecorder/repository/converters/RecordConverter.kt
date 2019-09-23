package com.itis2019.markrecorder.repository.converters

import com.itis2019.markrecorder.entities.Record
import com.itis2019.markrecorder.repository.dbEntities.DbRecord
import com.itis2019.markrecorder.repository.dbEntities.DbRecordWithFolder

fun DbRecord.convertToRecord(): Record =
    Record(
        id = this.id,
        name = this.name,
        marks = this.marks.map { it.convertToMark() },
        folderId = this.folderId,
        creationDate = this.creationDate,
        duration = this.duration,
        filePath = this.filePath
    )

fun DbRecordWithFolder.convertToRecord(): Record =
    Record(
        id = this.record.id,
        name = this.record.name,
        marks = this.record.marks.map { it.convertToMark() },
        folderBackground = this.folder.background,
        folderId = this.record.folderId,
        folderName = this.folder.name,
        creationDate = this.record.creationDate,
        duration = this.record.duration,
        filePath = this.record.filePath
    )

fun Record.convertToDbRecord(): DbRecord =
    DbRecord(
        id = this.id,
        name = this.name,
        marks = this.marks.map { it.convertToDbMark() },
        folderId = this.folderId,
        creationDate = this.creationDate,
        duration = this.duration,
        filePath = this.filePath
    )
