package com.example.beadando.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.beadando.data.Star
import com.example.beadando.data.StarDao
import kotlinx.coroutines.launch

class StarListViewModel(private val starDao: StarDao) : ViewModel() {

    val fullList: LiveData<List<Star>> = starDao.getAll().asLiveData()

    fun deleteStar(star: Star) {
        viewModelScope.launch {
            starDao.delete(star)
        }
    }

    fun retrieveStar(id: Int): LiveData<Star> {
        return starDao.getStar(id).asLiveData()
    }

    fun addStar(starName: String, towerNumber: String, starComment: String) {
        val newStar = getNewStar(starName, towerNumber, starComment)
        insertStar(newStar)
    }

    private fun insertStar(star: Star) {
        viewModelScope.launch {
            starDao.insert(star)
        }
    }

    private fun getNewStar(starName: String, towerNumber: String, starComment: String): Star {
        return Star(
            starName = starName,
            watchTower = towerNumber.toInt(),
            starComment = starComment
        )
    }

    fun isEntryValid(starName: String, towerNumber: String): Boolean {
        if (starName.isBlank() || towerNumber.isBlank()) {
            return false
        }
        return true
    }

    fun updateStar(
        starId: Int,
        starName: String,
        towerNumber: String,
        starComment: String
    ) {
        val updateStar = getUpdatedStar(starId, starName, towerNumber, starComment)
        updateStar(updateStar)
    }

    private fun updateStar(star: Star) {
        viewModelScope.launch {
            starDao.update(star)
        }
    }

    private fun getUpdatedStar(
        starId: Int,
        starName: String,
        towerNumber: String,
        starComment: String
    ): Star {
        return Star(
            id = starId,
            starName = starName,
            watchTower = towerNumber.toInt(),
            starComment = starComment
        )
    }

}

class StarViewModelFactory(private val starDao: StarDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StarListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StarListViewModel(starDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}