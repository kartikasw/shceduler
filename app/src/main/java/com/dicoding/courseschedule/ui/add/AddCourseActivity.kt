package com.dicoding.courseschedule.ui.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.home.HomeActivity
import com.dicoding.courseschedule.util.Event
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var edCourse: TextInputEditText
    private lateinit var edLecturer: TextInputEditText
    private lateinit var edNote: TextInputEditText
    private lateinit var spinner: Spinner
    private lateinit var btnStartTime: ImageButton
    private lateinit var btnEndTime: ImageButton
    private lateinit var tvStartTime: TextView
    private lateinit var tvEndTime: TextView

    private lateinit var viewModel: AddCourseViewModel

    companion object {
        const val START_TIME = "start_time"
        const val END_TIME = "end_time"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        edCourse = findViewById(R.id.add_ed_course)
        edLecturer = findViewById(R.id.add_ed_lecturer)
        edNote = findViewById(R.id.add_ed_note)
        spinner = findViewById(R.id.add_spinner)
        btnStartTime = findViewById(R.id.btn_start_time)
        btnEndTime = findViewById(R.id.btn_end_time)
        tvStartTime = findViewById(R.id.tv_start_time)
        tvEndTime = findViewById(R.id.tv_end_time)

        val factory = AddViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_insert -> {
                addCourse()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun addCourse() {
        val courseData = edCourse.text.toString()
        val lecturerData = edLecturer.text.toString()
        val noteData = edNote.text.toString()
        val day = spinner.selectedItemPosition
        val startTime = tvStartTime.text.toString()
        val endTime = tvEndTime.text.toString()

        try {
            if(courseData.isNotEmpty() && lecturerData.isNotEmpty() && noteData.isNotEmpty()) {
                viewModel.apply {
                    insertCourse(courseData, day, startTime, endTime, lecturerData, noteData)
                    saved.observe(this@AddCourseActivity) {
                        addState(it)
                    }
                }

            }
        } catch (error: Throwable) {
            Toast.makeText(this, "Failed to add", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addState(event: Event<Boolean>) {
        val state = event.getContentIfNotHandled()
        if (state == true) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            START_TIME -> tvStartTime.text = dateFormat.format(calendar.time)
            END_TIME -> tvEndTime.text = dateFormat.format(calendar.time)
        }
    }

    fun setStartTime(view: View) {
        TimePickerFragment().show(supportFragmentManager, START_TIME)
    }

    fun setEndTime(view : View){
        TimePickerFragment().show(supportFragmentManager, END_TIME)
    }
}