package com.fastwork.toefl.di

import com.fastwork.toefl.ui.login.LoginViewModel
import com.fastwork.toefl.ui.main.MainViewModel
import com.fastwork.toefl.ui.postAndPreTest.PreAndPostTestViewModel
import com.fastwork.toefl.ui.practice.PracticeViewModel
import com.fastwork.toefl.ui.practice.test.PracticeTestViewModel
import com.fastwork.toefl.ui.score.ScoreViewModel
import com.fastwork.toefl.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        LoginViewModel(get(), get())
    }
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        PracticeViewModel()
    }
    viewModel {
        PracticeTestViewModel(get())
    }
    viewModel {
        ScoreViewModel(get())
    }
    viewModel {
        SplashViewModel(get(), get())
    }
    viewModel {
        PreAndPostTestViewModel(get(), get())
    }
}