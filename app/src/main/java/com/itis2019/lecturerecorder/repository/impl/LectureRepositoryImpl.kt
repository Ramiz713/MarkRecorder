package com.itis2019.lecturerecorder.repository.impl

import com.itis2019.lecturerecorder.model.Lecture
import com.itis2019.lecturerecorder.repository.database.LectureDao
import com.itis2019.lecturerecorder.repository.LectureRepository
import io.reactivex.Single
import java.util.*
import kotlin.collections.ArrayList

class LectureRepositoryImpl(lectureDao: LectureDao) :
    LectureRepository {

    override fun getLecture(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertLecture(lecture: Lecture) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteLecture(lecture: Lecture) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllLectures(): Single<List<Lecture>> {
        val list = ArrayList<Lecture>()
        list.add(Lecture(1, "IPV6 and Subnetting", 4500000, Calendar.getInstance().time, "Networks", 1, 1))
        list.add(Lecture(2, "IPV6 and Subnetting", 4500000, Calendar.getInstance().time, "Networks", 1, 1))
        list.add(Lecture(3, "IPV6 and Subnetting", 4500000, Calendar.getInstance().time, "Networks", 1, 1))
        list.add(Lecture(4, "IPV6 and Subnetting", 4500000, Calendar.getInstance().time, "Networks", 1, 1))
        list.add(Lecture(5, "IPV6 and Subnetting", 4500000, Calendar.getInstance().time, "Networks", 1, 1))
        list.add(Lecture(6, "IPV6 and Subnetting", 4500000, Calendar.getInstance().time, "Networks", 1, 1))
        return Single.just(list)
    }
}
