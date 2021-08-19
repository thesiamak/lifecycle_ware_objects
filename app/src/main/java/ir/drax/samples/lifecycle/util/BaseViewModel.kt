package ir.drax.samples.lifecycle.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel():ViewModel() {
    val isLoading = MutableLiveData(false)
    val isEmpty = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Boolean>()
    val disposable = CompositeDisposable()

    override fun onCleared() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        super.onCleared()
    }

    fun signOut() = viewModelScope.launch(Dispatchers.IO) {
//        userRepository.logout()
    }
}