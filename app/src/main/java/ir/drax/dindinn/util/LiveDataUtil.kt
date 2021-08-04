package ir.drax.dindinn.util

import androidx.lifecycle.*


/**
 * Manual dispatch on LiveData objects
 */
public fun <X> MutableLiveData<X>.dispatch(){
    postValue(value)
}



/**
 * Map on MutableLiveData return value
 */
public inline fun <X, Y> LiveData<X>.map(crossinline transform: (X) -> Y): MutableLiveData<Y> {
    val result = MediatorLiveData<Y>()
    result.addSource(this){
        result.postValue(transform(it))
    }
    return result
}

fun <T> LiveData<T>.observeOnce(observer: Observer<T>) {
    observeForever(object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer:(T?)->Unit) {
    observe(lifecycleOwner,object : Observer<T> {
        override fun onChanged(t: T?) {
            removeObserver(this)
            observer(t)
        }
    })
}

fun <T> LiveData<T>.observeOnceNotNull(lifecycleOwner: LifecycleOwner, observer:(T)->Unit) {
    observe(lifecycleOwner,object : Observer<T> {
        override fun onChanged(t: T?) {
            t?.let {
                removeObserver(this)
                observer(t)
            }
        }
    })
}

fun <T> LiveData<T>.observeOnceNotNull(lifecycleOwner: LifecycleOwner, observer:Observer<T>) {
    observe(lifecycleOwner,object : Observer<T> {
        override fun onChanged(t: T?) {
            t?.let {
                removeObserver(this)
                observer.onChanged(t)
            }
        }
    })
}


/**
 * Combines multiple livedata objects and fires @param(block) each time a livedata is updated.
 * This is a brief version of MediatorLiveData.
 */
fun <T, R>mergeLiveData(
        viewLifecycleOwner: LifecycleOwner,
        vararg source: LiveData<T>,
        block: (List<T?>) -> R
): LiveData<R> {
    val result = MediatorLiveData<R>()
    source.forEach { it ->
        result.addSource(it) {
            result.value = block(source.map { it.value })
        }
    }
    result.observe(viewLifecycleOwner){}
    return result
}

fun <E, T,R> LiveData<T>.mergeLiveData(viewLifecycleOwner: LifecycleOwner, source: LiveData<E>, block: (T, E) -> R): MediatorLiveData<R> {
    val result = MediatorLiveData<R>()
    result.addSource(source) {
        it?.let { sourceValue->
            this.value?.let { originValue->
                result.postValue(block(originValue, sourceValue))
            }
        }
    }
    result.addSource(this) {
        it?.let { originValue->
            source.value?.let { sourceValue->
                result.postValue(block(originValue, sourceValue))
            }
        }
    }

    result.observe(viewLifecycleOwner){}

    return result
}

fun <E, T,R> LiveData<T>.mergeLiveData(source: LiveData<E>, block: (T, E) -> R): MediatorLiveData<R> {
    val result = MediatorLiveData<R>()
    result.addSource(source) {
        it?.let { sourceValue->
            this.value?.let { originValue->
                result.postValue(block(originValue, sourceValue))
            }
        }
    }
    result.addSource(this) {
        it?.let { originValue->
            source.value?.let { sourceValue->
                result.postValue(block(originValue, sourceValue))
            }
        }
    }

    result.observeForever{}
    return result
}
