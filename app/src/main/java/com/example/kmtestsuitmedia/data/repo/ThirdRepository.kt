package com.example.kmtestsuitmedia.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kmtestsuitmedia.data.remote.ApiConfig
import com.example.kmtestsuitmedia.data.remote.DataItem
import com.example.kmtestsuitmedia.data.remote.ThirdResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdRepository {
    private val _userData = MutableLiveData<List<DataItem>>()
    val userData: LiveData<List<DataItem>> = _userData

    private var currentPage = 1

    fun getData(page: Int, per: Int) {
        ApiConfig.getApiService().getUsers(page, per)
            .enqueue(object : Callback<ThirdResponse> {
                override fun onResponse(
                    call: Call<ThirdResponse>,
                    response: Response<ThirdResponse>,
                ) {
                    if (response.isSuccessful) {
                        val newData = response.body()?.data ?: emptyList()

                        if (page == 1) {
                            clearData()
                            currentPage = 1
                            _userData.postValue(newData)
                        } else {
                            val currentData = _userData.value?.toMutableList() ?: mutableListOf()
                            currentData.addAll(newData)
                            _userData.postValue(currentData)
                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ThirdResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }

    fun clearData() {
        _userData.postValue(emptyList())
    }

    companion object {
        private const val TAG = "Third Repository"
    }

}
