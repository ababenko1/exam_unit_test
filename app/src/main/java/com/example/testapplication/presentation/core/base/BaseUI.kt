package com.example.testapplication.presentation.core.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.*

inline fun <reified T : ViewModel> Fragment.createViewModel(
    factory: ViewModelProvider.Factory,
    body: T.() -> Unit,
): T {
    val vm = ViewModelProvider(this, factory)[T::class.java]
    vm.body()
    return vm
}

fun <T : Any?, L : LiveData<T>> LifecycleOwner.observeData(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <T : Any?, L : LiveData<Event<T>>> LifecycleOwner.observeDataEvent(
    liveData: L,
    body: (T?) -> Unit,
) = liveData.observe(this, EventObserver(body))