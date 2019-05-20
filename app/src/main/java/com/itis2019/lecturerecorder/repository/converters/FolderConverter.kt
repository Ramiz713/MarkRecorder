package com.itis2019.lecturerecorder.repository.converters

import com.itis2019.lecturerecorder.entities.Folder
import com.itis2019.lecturerecorder.repository.dbEntities.DbFolder

fun DbFolder.convertToFolder(): Folder =
    Folder(
        id = this.id,
        name = this.name,
        creationDate = this.creationDate,
        background = this.background
    )

fun Folder.convertToDbFolder(): DbFolder =
    DbFolder(
        id = this.id,
        name = this.name,
        creationDate = this.creationDate,
        background = this.background
    )
