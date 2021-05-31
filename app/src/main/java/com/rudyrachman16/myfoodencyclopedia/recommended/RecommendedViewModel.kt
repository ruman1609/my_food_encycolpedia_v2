package com.rudyrachman16.myfoodencyclopedia.recommended

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rudyrachman16.core.domain.usecase.IUseCase

class RecommendedViewModel(useCase: IUseCase) : ViewModel() {
    val recommended = useCase.getRecommended().asLiveData()
}