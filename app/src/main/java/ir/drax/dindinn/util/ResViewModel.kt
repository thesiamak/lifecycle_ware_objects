package ir.drax.dindinn.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class ResViewModel():ViewModel() {
    val isLoading = MutableLiveData(false)
    val isEmpty = MutableLiveData(true)
    fun signOut() = viewModelScope.launch(Dispatchers.IO) {
//        userRepository.logout()
    }
}