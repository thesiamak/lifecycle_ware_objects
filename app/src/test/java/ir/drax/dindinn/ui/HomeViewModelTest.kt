package ir.drax.dindinn.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import ir.drax.dindinn.RxImmediateSchedulerRule
import com.interview.blocket.data.model.Repository
import com.interview.blocket.data.network.Resource
import com.interview.blocket.data.rest.GithubUseCase
import com.interview.blocket.ui.home.HomeViewModel
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class HomeViewModelTest {

    lateinit var homeViewModel: HomeViewModel

    @Mock
    lateinit var githubUseCase: GithubUseCase

    @Mock
    lateinit var repositoriesLiveData: Observer<List<Repository>>

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        homeViewModel = HomeViewModel(githubUseCase)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Given repository returns data, when getRepositories() called, then live data is updated`() {
        val expected = Resource.Success(listOf(Repository(name = "mock/repository")))

        //Setting how up the mock behaves
        whenever(githubUseCase.getRepositories()).thenReturn(Observable.just(expected))

        //Fire the test method
        homeViewModel.getRepositories()

        //Check that our live data is updated
        assertEquals(expected.data, homeViewModel.repositoriesLiveData.value)
    }

    @Test
    fun `Given repository returns error, when getRepositories() called, then live data is not updated`() {
        //Setting how up the mock behaves
        whenever(githubUseCase.getRepositories()).thenReturn(Observable.error(Throwable()))

        //Fire the test method
        homeViewModel.getRepositories()

        //Check that our live data stayed un-touched
        assertEquals(null, homeViewModel.repositoriesLiveData.value)
    }

    @Test
    fun `Given repository returns error, when fetchPuppies() called, then live data is not updated via observer`() {
        //Setting how up the mock behaves
        whenever(githubUseCase.getRepositories()).thenReturn(Observable.error(Throwable()))

        //Observe live data and fire the test method
        homeViewModel.repositoriesLiveData.observeForever(repositoriesLiveData)
        homeViewModel.getRepositories()

        //Check that observer has not received any changes
        verify(repositoriesLiveData, times(0)).onChanged(any())
    }
}