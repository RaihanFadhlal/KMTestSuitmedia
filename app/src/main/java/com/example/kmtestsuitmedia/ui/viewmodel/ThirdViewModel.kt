package com.example.kmtestsuitmedia.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kmtestsuitmedia.data.remote.DataItem
import com.example.kmtestsuitmedia.data.repo.ThirdRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThirdViewModel @Inject constructor(
    private val thirdRepo: ThirdRepository
) : ViewModel() {

    val userData: LiveData<List<DataItem>> = thirdRepo.userData

    fun getUsers(page : Int, per : Int) = thirdRepo.getData(page, per)

    fun refreshData(per: Int) {
        thirdRepo.clearData()
        thirdRepo.getData(1, per)
    }

}