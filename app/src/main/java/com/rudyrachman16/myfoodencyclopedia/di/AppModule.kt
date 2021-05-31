package com.rudyrachman16.myfoodencyclopedia.di

import com.rudyrachman16.core.domain.usecase.IUseCase
import com.rudyrachman16.core.domain.usecase.UseCase
import com.rudyrachman16.myfoodencyclopedia.detail.DetailViewModel
import com.rudyrachman16.myfoodencyclopedia.recommended.RecommendedViewModel
import com.rudyrachman16.myfoodencyclopedia.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    single<IUseCase> { UseCase(get()) }
}

val viewModelModule = module {
    viewModel { RecommendedViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}