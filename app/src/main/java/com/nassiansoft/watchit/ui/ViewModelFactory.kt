package com.nassiansoft.watchit.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Lazy
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory<Vm:ViewModel> @Inject constructor(
    private val vm : Lazy<Vm>
) :ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return vm.get() as T
    }
}