package com.example.testapplication.presentation.core.base

import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment(), IBaseFragment {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected fun showSnackbarEvent(message: String?) {
        message?.let { it ->
            Snackbar.make(requireView(), it, 5000).show()
        }
    }
}