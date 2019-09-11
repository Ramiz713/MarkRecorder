package com.itis2019.lecturerecorder.repository.converters

import com.itis2019.lecturerecorder.entities.Record
import com.itis2019.lecturerecorder.repository.dbEntities.DbRecord

fun DbRecord.convertToRecord(): Record =
    Record(
        id = this.id,
        name = this.name,
        marks = this.marks.map { it.convertToMark() },
        folderBackground = this.folderBackground,
        folderId = this.folderId,
        folderName = this.folderName,
        creationDate = this.creationDate,
        duration = this.duration,
        filePath = this.filePath
    )

fun Record.convertToDbRecord(): DbRecord =
    DbRecord(
        id = this.id,
        name = this.name,
        marks = this.marks.map { it.convertToDbMark() },
        folderBackground = this.folderBackground,
        folderId = this.folderId,
        folderName = this.folderName,
        creationDate = this.creationDate,
        duration = this.duration,
        filePath = this.filePath
    )
