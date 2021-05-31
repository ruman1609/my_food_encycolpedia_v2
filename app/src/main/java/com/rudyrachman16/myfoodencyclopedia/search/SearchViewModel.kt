package com.rudyrachman16.myfoodencyclopedia.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rudyrachman16.core.domain.usecase.IUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(useCase: IUseCase) : ViewModel() {
    private val state = MutableStateFlow("")
    private val queryResult: StateFlow<String> = state

    fun setQuery(query: String) {
        viewModelScope.launch {
            state.value = query
        }
    }

    val searchResult = useCase.getSearch(queryResult).asLiveData()
}