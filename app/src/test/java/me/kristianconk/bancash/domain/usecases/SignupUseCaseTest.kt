package me.kristianconk.bancash.domain.usecases

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import me.kristianconk.bancash.domain.model.BancashResult
import me.kristianconk.bancash.domain.model.DataError
import me.kristianconk.bancash.domain.repository.BancashRepository
import me.kristianconk.bancash.domain.utils.UserDataValidator
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SignupUseCaseTest {

    val EMPTY = ""
    val NON_EMPTY = "estoNoEsVacio"


    lateinit var systemUnderTest: SignUpUseCase

    @MockK(relaxed = true)
    lateinit var repository: BancashRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        systemUnderTest = SignUpUseCase(repository, UserDataValidator())
    }

    @Test
    fun testEmptyEmail() = runTest {
        // given
        coEvery { repository.signUp(EMPTY, NON_EMPTY, NON_EMPTY, NON_EMPTY, any()) } returns BancashResult.Error(DataError.NetworkError.BAD_REQUEST)
        // when
        val result = systemUnderTest.execute(EMPTY, NON_EMPTY, NON_EMPTY, NON_EMPTY, null)
        // then
        Assert.assertEquals(SignUpUseCase.SignupResult.EMAIL_EMPTY, result)
    }

    @Test
    fun testEmptyPassword() = runTest {
        // given
        coEvery { repository.signUp(NON_EMPTY, EMPTY, NON_EMPTY, NON_EMPTY, any()) } returns BancashResult.Error(DataError.NetworkError.BAD_REQUEST)
        // when
        val result = systemUnderTest.execute(NON_EMPTY, EMPTY,  NON_EMPTY, NON_EMPTY, null)
        // then
        Assert.assertEquals(SignUpUseCase.SignupResult.PASSWORD_EMPTY, result)
    }

    @Test
    fun testEmptyName() = runTest {
        // given
        coEvery { repository.signUp(NON_EMPTY, NON_EMPTY, EMPTY, NON_EMPTY, any()) } returns BancashResult.Error(DataError.NetworkError.BAD_REQUEST)
        // when
        val result = systemUnderTest.execute(NON_EMPTY, NON_EMPTY, EMPTY, NON_EMPTY, null)
        // then
        Assert.assertEquals(SignUpUseCase.SignupResult.NAME_EMPTY, result)
    }

    @Test
    fun testEmptyLastName() = runTest {
        // given
        coEvery { repository.signUp(NON_EMPTY, NON_EMPTY, NON_EMPTY, EMPTY, any()) } returns BancashResult.Error(DataError.NetworkError.BAD_REQUEST)
        // when
        val result = systemUnderTest.execute(NON_EMPTY, NON_EMPTY, NON_EMPTY, EMPTY, null)
        // then
        Assert.assertEquals(SignUpUseCase.SignupResult.LASTNAME_EMPTY, result)
    }


}