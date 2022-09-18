package com.dicoding.courseschedule.ui.home

import android.app.Activity
import android.view.animation.Transformation
import androidx.lifecycle.*
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.util.QueryType
import java.lang.reflect.InvocationTargetException

class HomeViewModel(private val repository: DataRepository): ViewModel() {

    private val _queryType = MutableLiveData<QueryType>()

    init {
        _queryType.value = QueryType.CURRENT_DAY
    }

    fun setQueryType(queryType: QueryType) {
        _queryType.value = queryType
    }

    val schedule: LiveData<Course?> = Transformations.switchMap(_queryType) {
        repository.getNearestSchedule(it)
    }
}
