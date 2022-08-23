package com.example.android.fetchtakehomeapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.fetchtakehomeapp.domain.JsonResponseModel
import com.example.android.fetchtakehomeapp.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeListViewModel
@Inject constructor(
    val repository: ItemRepository,
): ViewModel() {

    private val _informationList: MutableLiveData<List<JsonResponseModel>> = MutableLiveData()
    val informationList: LiveData<List<JsonResponseModel>> get() = _informationList

    init {
        getInfo()
        getInforForDb()
    }

    fun getInfo() = viewModelScope.launch {
//         val result = repository.getListOfItems()
//        val result = repository.getSortedList()
//        val result = repository.getSortedListExNulls()
        val result = repository.getSortedListExNullsExBlanks()
        result.sortedByDescending { mod->
            mod.id
        }


        _informationList.postValue(result)
    }

    fun getInforForDb() = viewModelScope.launch {
        repository.getInfoForDatabase()
    }

}