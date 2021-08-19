package ir.drax.samples.lifecycle.model

import org.junit.Test

class ResponseViewStateTest {

    @Test
    fun `should return loading true when status is loading`() {

        // Given
        val givenViewState = ResponseViewStateTest( Status.LOADING)

        // When
        val actualResult = givenViewState.isLoading()

        // Then
        assert(actualResult)
    }

    @Test
    fun `should not return loading false when status is error`() {

        // Given
        val givenViewState = ResponseViewState(status = Status.Error(Exception()))

        // When
        val actualResult = givenViewState.isLoading()

        // Then
        assert(actualResult.not())
    }

    @Test
    fun `should not return loading false when status is success`() {

        // Given
        val givenViewState = ResponseViewState(status = Status.Content)

        // When
        val actualResult = givenViewState.isLoading()

        // Then
        assert(actualResult.not())
    }

    @Test
    fun `should return correct error message when status is error`() {

        // Given
        val givenViewState = ResponseViewState(status = Status.Error(Exception("500 Internal Server Error")))

        // When
        val actualResult = givenViewState.getErrorMessage()

        // Then
        assert(actualResult == "500 Internal Server Error")
    }

    @Test
    fun `should return true for error placeholder visibility  when status is error`() {

        // Given
        val givenViewState = ResponseViewState(status = Status.Error(Exception("500 Internal Server Error")))

        // When
        val actualResult = givenViewState.shouldShowErrorMessage()

        // Then
        assert(actualResult)
    }
}