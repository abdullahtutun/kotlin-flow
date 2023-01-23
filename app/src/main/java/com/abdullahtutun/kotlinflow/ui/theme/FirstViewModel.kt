package com.abdullahtutun.kotlinflow.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FirstViewModel : ViewModel() {

    init {
        //collectInViewModel()
    }

    val countDownTimerFlow = flow<Int> {
        val countDownFrom = 10
        var counter = countDownFrom
        emit(countDownFrom)
        while (counter > 0) {
            delay(1000)
            counter--
            emit(counter)
        }
    }

    private fun collectInViewModel(){
        viewModelScope.launch {
            countDownTimerFlow
                .filter {
                    it %3 == 0
                }
                .map {
                    it + it
                }
                .collect {
                    println(it)
                }
        }

        /*countDownTimerFlow.onEach {
            println(it)
        }.launchIn(viewModelScope)*/
    }

    private val _liveData = MutableLiveData<String>("KotlinLiveData")
    val liveData : LiveData<String> = _liveData

    fun changeLiveDataValue(){
        _liveData.value = "Live data"
    }

    private val _stateFlow = MutableStateFlow("KotlinStateFlow")
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun changeStateFromValue() {
        _stateFlow.value = "State Flow"
    }

    fun changeSharedFlowValue(){
        viewModelScope.launch {
            _sharedFlow.emit("Shared Flow")
        }
    }
}