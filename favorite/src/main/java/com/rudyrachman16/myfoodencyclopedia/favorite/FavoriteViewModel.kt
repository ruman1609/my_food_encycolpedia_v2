package com.rudyrachman16.myfoodencyclopedia.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rudyrachman16.core.domain.usecase.IUseCase

class FavoriteViewModel(useCase: IUseCase) : ViewModel() {
    val getFavorite = useCase.getFavorite().asLiveData()
}