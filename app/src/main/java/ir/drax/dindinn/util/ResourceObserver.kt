package ir.drax.dindinn.util

import androidx.lifecycle.Observer
import ir.drax.dindinn.R
import ir.drax.dindinn.model.Resource
import ir.drax.dindinn.model.Status
import ir.drax.dindinn.network.NetworkCall
import java.net.HttpURLConnection

/**
 * ResourceObserver : This is a medium to parse and handle network requests using Resource pattern.
 * @property ResViewModel -> Calling viewmodel has to extend this viewmodel in order to inherit required methods.
 *
 * @param viewModel: ResViewModel -> A parent viewmodel to implement Loading and Signout.
 * @param message: ((Int, String?)-> Unit) -> Method passing transaction message to a higher level.
 * @param onChange :Observer.OnChange -> The callback function to .
 *
 */
class ResourceObserver<E>(private val viewModel: ResViewModel, private val message: ((Int, String?)-> Unit)={ _, _->}
                          , val onChange:(Resource<E>?)->Unit = {})
    : Observer<Resource<E>> {

    override fun onChanged(it: Resource<E>?) {
        it?.let {
            it.message.takeIf { message -> !message.isNullOrEmpty() }?.let { msg ->
                message(0,msg)
            }

            when(it.status) {
                Status.LOADING -> {
                    viewModel.isLoading.postValue(true)
                }

                Status.ERROR -> {
                    viewModel.isLoading.postValue(false)

                    //todo : Zeynab & Siamak-> Handle more error cases.
                    val message = when(it.errorCode){
                        HttpURLConnection.HTTP_NOT_FOUND -> R.string.error_not_found
                        NetworkCall.ConnectException            -> R.string.network_unavailable
                        NetworkCall.SocketTimeoutException      -> R.string.network_unavailable
                        NetworkCall.UnknownHostException        -> R.string.network_unavailable
                        in HttpURLConnection.HTTP_INTERNAL_ERROR..HttpURLConnection.HTTP_VERSION -> R.string.error_internal

                        in HttpURLConnection.HTTP_UNAUTHORIZED..HttpURLConnection.HTTP_FORBIDDEN ->{
                            viewModel.signOut()
                            R.string.error_unauthorized
                        }
                        else -> R.string.unknown_error
                    }

                    // Display local message text if api response has not offered one.
                    if (it.message.isNullOrEmpty())
                        message(message,null)
                }

                Status.EMPTY_RESULT -> {
                    viewModel.isEmpty.postValue(true)
                    viewModel.isLoading.postValue(false)
                }

                else -> {
                    viewModel.isLoading.postValue(false)
                    when(it.data){
                        is Collection<*> ->{
                            viewModel.isEmpty.postValue(it.data.isEmpty())
                        }

                        else -> {}
                    }
                }
            }
        }
        onChange(it)
    }
}