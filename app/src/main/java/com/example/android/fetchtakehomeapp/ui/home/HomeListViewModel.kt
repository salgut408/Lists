package com.example.android.fetchtakehomeapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.fetchtakehomeapp.domain.JsonResponseModel
import com.example.android.fetchtakehomeapp.repository.ItemRepository
import com.example.android.fetchtakehomeapp.util.ItemComparator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeListViewModel
@Inject constructor(
    val repository: ItemRepository,
) : ViewModel() {

    private val _informationList: MutableLiveData<List<JsonResponseModel>> = MutableLiveData()
    val informationList: LiveData<List<JsonResponseModel>> get() = _informationList

    init {
        getInforForDb()
    }

    fun getInfo() = viewModelScope.launch {
        val result = repository.getSortedListExNullsExBlanks()
         result.sortedWith(ItemComparator)
         _informationList.postValue(result)
    }

    fun getFlowItem() = viewModelScope.launch {
        repository.items.collect { items ->
            _informationList.postValue(items)
        }
    }

    fun getInforForDb() = viewModelScope.launch {
        repository.getInfoForDatabase()
    }
}