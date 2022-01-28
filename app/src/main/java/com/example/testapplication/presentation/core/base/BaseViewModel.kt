package com.example.testapplication.presentation.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.presentation.util.ErrorMessageParser
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

open class BaseViewModel : ViewModel(), IBaseViewModel {

    private val _progressBarShown = MutableLiveData<Boolean>()
    val progressBarShown: LiveData<Boolean> = _progressBarShown

    private val _snackBar = MutableLiveData<Event<String>>()
    val snackBar: LiveData<Event<String>> = _snackBar

    override fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _progressBarShown.value = true
                block()
            } catch (error: Throwable) {
                _snackBar.value = Event(ErrorMessageParser.parse(error))
                Timber.d(error)
            } finally {
                _progressBarShown.value = false
            }
        }
    }
}