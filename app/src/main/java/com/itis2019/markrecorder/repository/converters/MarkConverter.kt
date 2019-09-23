package com.itis2019.markrecorder.repository.converters

import com.itis2019.markrecorder.entities.Mark
import com.itis2019.markrecorder.repository.dbEntities.DbMark

fun DbMark.convertToMark(): Mark =
    Mark(
        id = this.id,
        name = this.name,
        time = this.time
    )

fun Mark.convertToDbMark(): DbMark =
    DbMark(
        id = this.id,
        name = this.name,
        time = this.time
    )
