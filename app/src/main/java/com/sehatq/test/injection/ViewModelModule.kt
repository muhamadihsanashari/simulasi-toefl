package com.sehatq.test.injection

import com.sehatq.test.ui.home.HomeViewModel
import com.sehatq.test.ui.login.LoginViewModel
import com.sehatq.test.ui.mainPage.MainPageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        LoginViewModel()
    }

    viewModel {
        HomeViewModel(repository = get())
    }

    viewModel {
        MainPageViewModel()
    }
}