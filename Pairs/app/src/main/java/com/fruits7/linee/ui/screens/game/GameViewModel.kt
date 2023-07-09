package com.fruits7.linee.ui.screens.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruits7.linee.domain.GameItem
import com.fruits7.linee.domain.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val repository = GameRepository()
    private val _items = MutableLiveData<List<GameItem>>(emptyList())
    val items: LiveData<List<GameItem>> = _items

    private val _pairs = MutableLiveData<Int>(0)
    val pairs: LiveData<Int> = _pairs

    private val _timer = MutableLiveData<Int>(-1)
    val timer: LiveData<Int> = _timer

    private val _stack = MutableLiveData<MutableList<GameItem>>(mutableListOf())
    val stack: LiveData<MutableList<GameItem>> = _stack
    var switch = true

    fun generateItems() {
        viewModelScope.launch {
            _items.postValue(repository.generateItems())
        }
    }

    fun startTimer(time: Int) {
        _timer.postValue(time)
        val flow = flow {
            repeat(time) {
                delay(1000)
                emit(_timer.value!! - 1)
            }
        }.flowOn(Dispatchers.Default)

        viewModelScope.launch {
            flow.collect {
                _timer.postValue(it)
            }
        }
    }

    fun openCard(index: Int) {
        _items.value!![index].isOpened = true
        val newList = _stack.value
        newList?.add(_items.value!![index])
        _stack.postValue(newList ?: mutableListOf())
        switch = !switch
    }

    fun addPair() {
        _pairs.value = _pairs.value?.plus(1)
    }

    fun clearStack() {
        _stack.postValue(mutableListOf())
        switch = !switch
    }

    fun closeItem(index: Int) {
        val newList = _items.value?.toMutableList() ?: return
        newList[index].isOpened = false
        _items.postValue(newList)
    }
}