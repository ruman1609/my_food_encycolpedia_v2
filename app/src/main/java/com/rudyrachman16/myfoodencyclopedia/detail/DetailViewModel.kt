package com.rudyrachman16.myfoodencyclopedia.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rudyrachman16.core.domain.model.Meal
import com.rudyrachman16.core.domain.usecase.IUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val useCase: IUseCase) : ViewModel() {
    fun getDetail(id: String) = useCase.getDetail(id).asLiveData()

    fun insertMeal(meal: Meal) = viewModelScope.launch(Dispatchers.IO) { useCase.insertMeal(meal) }

    fun setFavorite(meal: Meal) = viewModelScope.launch(Dispatchers.IO) { useCase.updateMeal(meal) }
}