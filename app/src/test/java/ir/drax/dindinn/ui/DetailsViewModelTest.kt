package ir.drax.dindinn.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import ir.drax.dindinn.RxImmediateSchedulerRule
import com.interview.blocket.data.model.User
import com.interview.blocket.data.network.Resource
import com.interview.blocket.data.rest.GithubUseCase
import com.interview.blocket.ui.details.DetailsViewModel
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailsViewModelTest {

    private val userId = MockData.mockUser.id!!
    private val url = MockData.mockRepository.contributorsUrl!!

    private lateinit var detailsViewModel: DetailsViewModel

    @Mock
    lateinit var githubUseCase: GithubUseCase

    @Mock
    lateinit var userLiveData: Observer<User>

    @Mock
    lateinit var contributorsLiveData: Observer<List<User>>

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val ruleForLiveData = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        detailsViewModel = DetailsViewModel(githubUseCase)
    }

    @Test
    fun `Given repository returns data, when fetchData() called, then live data is updated for merged observables`() {
        val expectedUser = Resource.Success(MockData.mockUser)
        val expectedContributors = Resource.Success(listOf(MockData.mockUser))
        whenever(githubUseCase.getUser(userId)).thenReturn(Observable.just(expectedUser))
        whenever(githubUseCase.getContributors(url)).thenReturn(Observable.just(expectedContributors))

        detailsViewModel.fetchData(userId, url)

        assertEquals(expectedUser.data, detailsViewModel.userLiveData.value)
        assertEquals(expectedContributors.data, detailsViewModel.contributorsLiveData.value)
    }

    @Test
    fun `Given repository returns error for one request, when fetchData() called, then live data is not updated for merged observables`() {
        val expectedContributors = Resource.Success(listOf(MockData.mockUser))

        //Setting how up the mock behaves
        whenever(githubUseCase.getUser(userId)).thenReturn(Observable.error(Throwable()))
        whenever(githubUseCase.getContributors(url)).thenReturn(Observable.just(expectedContributors))

        //Fire the test method
        detailsViewModel.fetchData(userId, url)

        //Check that our live data stayed un-touched
        assertEquals(null, detailsViewModel.userLiveData.value)
        assertEquals(null, detailsViewModel.contributorsLiveData.value)
    }

    @Test
    fun `Given repository returns error, when fetchData() called, then live data is not updated via observer for merged observables`() {
        val expectedContributors = Resource.Success(listOf(MockData.mockUser))

        //Setting how up the mock behaves
        whenever(githubUseCase.getUser(userId)).thenReturn(Observable.error(Throwable()))
        whenever(githubUseCase.getContributors(url)).thenReturn(Observable.just(expectedContributors))

        //Observe live data and fire the test method
        detailsViewModel.userLiveData.observeForever(userLiveData)
        detailsViewModel.contributorsLiveData.observeForever(contributorsLiveData)
        detailsViewModel.fetchData(userId, url)

        //Check that observer has not received any changes
        verify(userLiveData, times(0)).onChanged(any())
        verify(contributorsLiveData, times(0)).onChanged(any())
    }
}
