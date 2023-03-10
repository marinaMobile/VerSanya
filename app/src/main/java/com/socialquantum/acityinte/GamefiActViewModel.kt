package com.socialquantum.acityinte

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GamefiActViewModel : ViewModel() {

    private val _timeSubject = MutableLiveData<Int>()
    val timeSubject: LiveData<Int> = _timeSubject

    private val _scoreSubject = MutableLiveData<Int>()
    val scoreSubject: LiveData<Int> = _scoreSubject

    private val _gameStarted = MutableLiveData<Boolean>()
    val gameStarted: LiveData<Boolean> = _gameStarted

    private val _bitmapTask = MutableLiveData<Bitmap>()
    val bitmapTask: LiveData<Bitmap> = _bitmapTask

    init {
        _timeSubject.postValue(STARTING_TIME)
        _scoreSubject.postValue(0)
    }

    private fun setTime(time: Int) = _timeSubject.postValue(time)
    private fun setScore(score: Int) = _scoreSubject.postValue(score)

    fun setGameStatus() {
        if (_gameStarted.value == null) {
            _gameStarted.postValue(true)
        } else _gameStarted.value = !_gameStarted.value!!
    }

    fun setTaskBitmap(bitmap: Bitmap) = _bitmapTask.postValue(bitmap)


    fun refreshField() {
        setScore(_scoreSubject.value!!.minus(LOSE_SCORE_AFTER_REFRESH))
    }

    fun startTimer() {
        if (_gameStarted.value == true)
            viewModelScope.launch {
                while (_timeSubject.value!! > 0) {
                    setTime(_timeSubject.value!! - 1)
                    delay(ONE_SECOND_DELAY)
                }
                setGameStatus()
            }
    }

    fun getCorrectVariant() {
        setScore(_scoreSubject.value!!.plus(SCORE_FOR_CORRECT_ANSWER))
        setTime(_timeSubject.value!!.plus(TIME_FOR_CORRECT_ANSWER))
    }

    fun getIncorrectVariant() {
        setScore(_scoreSubject.value!!.minus(LOSE_SCORE_AFTER_MISTAKE))
    }

    companion object {
        const val STARTING_TIME = 15
        const val LOSE_SCORE_AFTER_REFRESH = 2
        const val LOSE_SCORE_AFTER_MISTAKE = 1
        const val ONE_SECOND_DELAY = 1000L
        const val SCORE_FOR_CORRECT_ANSWER = 5
        const val TIME_FOR_CORRECT_ANSWER = 2
    }
}