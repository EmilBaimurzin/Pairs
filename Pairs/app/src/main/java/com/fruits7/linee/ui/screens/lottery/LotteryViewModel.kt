package com.fruits7.linee.ui.screens.lottery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruits7.linee.domain.LotteryItem
import kotlinx.coroutines.launch

class LotteryViewModel: ViewModel() {
    private val repository = LotteryRepository()
    private val _items = MutableLiveData<MutableList<LotteryItem>>(mutableListOf())
    val items: LiveData<MutableList<LotteryItem>> = _items

    fun generateItems() {
        viewModelScope.launch {
            _items.postValue(repository.generate())
        }
    }

    fun openItem(index: Int) {
        val newList = _items.value
        newList!![index].isOpened = true
        _items.postValue(newList!!)
    }
}