package com.dicoding.courseschedule.paging

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.util.DayName.Companion.getByNumber

class CourseViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private lateinit var course: Course
    private val timeString = itemView.context.resources.getString(R.string.time_format)

    private lateinit var tvCourse: TextView
    private lateinit var tvLecturer: TextView
    private lateinit var tvTime: TextView

    //TODO 7 : Complete ViewHolder to show item
    fun bind(course: Course, clickListener: (Course) -> Unit) {
        this.course = course

        with(itemView) {
            tvCourse = findViewById(R.id.tv_course)
            tvLecturer = findViewById(R.id.tv_lecturer)
            tvTime = findViewById(R.id.tv_time)
        }

        course.apply {
            val dayName = getByNumber(day)
            val timeFormat = String.format(timeString, dayName, startTime, endTime)

            tvCourse.text = courseName
            tvLecturer.text = lecturer
            tvTime.text = timeFormat
        }

        itemView.setOnClickListener {
            clickListener(course)
        }
    }

    fun getCourse(): Course = course
}