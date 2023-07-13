package com.example.kmtestsuitmedia.di

import com.example.kmtestsuitmedia.data.repo.ThirdRepository

object Injection {
    fun thirdRepo() : ThirdRepository {
        return ThirdRepository()
    }
}