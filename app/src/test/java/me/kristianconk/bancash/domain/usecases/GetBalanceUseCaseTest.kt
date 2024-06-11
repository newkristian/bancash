package me.kristianconk.bancash.domain.usecases

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import me.kristianconk.bancash.domain.model.BancashResult
import me.kristianconk.bancash.domain.model.DataError
import me.kristianconk.bancash.domain.model.UserBalance
import me.kristianconk.bancash.domain.repository.BancashRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetBalanceUseCaseTest {

    lateinit var systemUnderTest: GetBalanceUseCase

    @MockK(relaxed = true)
    lateinit var repository: BancashRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        systemUnderTest = GetBalanceUseCase(repository)
    }

    @Test
    fun testGetBalanceSuccess() = runTest {
        // given
        coEvery { repository.getBalance() } returns BancashResult.Success(UserBalance("12345", 500.0))
        // when
        val balance = systemUnderTest.execute()
        // then
        assert(balance != null)
        Assert.assertEquals(500.0, balance?.accountBalance)
    }

    @Test
    fun testGetBalanceError() = runTest {
        // given
        coEvery { repository.getBalance() } returns BancashResult.Error(DataError.NetworkError.SERVER_ERROR)
        // when
        val balance = systemUnderTest.execute()
        // then
        assert(balance == null)
    }
}