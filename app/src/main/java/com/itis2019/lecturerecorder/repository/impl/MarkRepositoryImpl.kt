package com.itis2019.lecturerecorder.repository.impl

import com.itis2019.lecturerecorder.model.Mark
import com.itis2019.lecturerecorder.repository.database.MarkDao
import com.itis2019.lecturerecorder.repository.MarkRepository
import io.reactivex.Single

class MarkRepositoryImpl(markDao: MarkDao): MarkRepository {
    override fun insertMark(mark: Mark) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteMark(mark: Mark) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllMark(): Single<List<Mark>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
