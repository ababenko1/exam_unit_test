package com.example.testapplication.presentation.core.base

import kotlinx.coroutines.Job

interface IBaseViewModel {

    fun launchDataLoad(block: suspend () -> Unit): Job
}