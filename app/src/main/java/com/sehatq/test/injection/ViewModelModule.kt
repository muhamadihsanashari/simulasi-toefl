package com.sehatq.test.injection

import com.sehatq.test.ui.cart.CartViewModel
import com.sehatq.test.ui.detailProduct.DetailProductViewModel
import com.sehatq.test.ui.feed.FeedViewModel
import com.sehatq.test.ui.home.HomeViewModel
import com.sehatq.test.ui.login.LoginViewModel
import com.sehatq.test.ui.mainPage.MainPageViewModel
import com.sehatq.test.ui.profile.ProfileViewModel
import com.sehatq.test.ui.search.SearchViewModel
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

    viewModel {
        CartViewModel()
    }

    viewModel {
        FeedViewModel()
    }

    viewModel {
        ProfileViewModel(repository = get())
    }

    viewModel {
        SearchViewModel()
    }

    viewModel {
        DetailProductViewModel(repository = get())
    }

}