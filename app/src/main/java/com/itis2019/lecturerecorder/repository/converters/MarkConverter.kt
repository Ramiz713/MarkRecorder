package com.itis2019.lecturerecorder.repository.converters

import com.itis2019.lecturerecorder.entities.Mark
import com.itis2019.lecturerecorder.repository.dbEntities.DbMark

fun DbMark.convertToMark(): Mark =
    Mark(
        id = this.id,
        name = this.name,
        time = this.time,
        lectureId = this.lectureId
    )

fun Mark.convertToDbMark(): DbMark =
    DbMark(
        id = this.id,
        name = this.name,
        time = this.time,
        lectureId = this.lectureId
    )
