package com.itis2019.lecturerecorder.repository.converters

import com.itis2019.lecturerecorder.entities.Lecture
import com.itis2019.lecturerecorder.repository.dbEntities.DbLecture

fun DbLecture.convertToLecture(): Lecture =
    Lecture(
        id = this.id,
        name = this.name,
        folderBackground = this.folderBackground,
        folderId = this.folderId,
        folderName = this.folderName,
        creationDate = this.creationDate,
        duration = this.duration,
        filePath = this.filePath
    )

fun Lecture.convertToDbLecture(): DbLecture =
    DbLecture(
        id = this.id,
        name = this.name,
        folderBackground = this.folderBackground,
        folderId = this.folderId,
        folderName = this.folderName,
        creationDate = this.creationDate,
        duration = this.duration,
        filePath = this.filePath
    )
