package com.example.kmtestsuitmedia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kmtestsuitmedia.di.Injection

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThirdViewModel::class.java)) {
            return ThirdViewModel(Injection.thirdRepo()) as T
        }
        throw IllegalArgumentException("error ${modelClass.name}")
    }
}