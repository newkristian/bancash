package me.kristianconk.bancash.domain.usecases

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import me.kristianconk.bancash.domain.model.BancashResult
import me.kristianconk.bancash.domain.model.DataError
import me.kristianconk.bancash.domain.repository.BancashRepository
import me.kristianconk.bancash.domain.utils.UserDataValidator
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    lateinit var systemUnderTest: LoginUseCase

    @MockK(relaxed = true)
    lateinit var repository: BancashRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        systemUnderTest = LoginUseCase(UserDataValidator(), repository)
    }

    @Test
    fun testEmptyUser() = runTest {
        // given
        coEvery { repository.logIn("", "123") } returns BancashResult.Error(DataError.NetworkError.BAD_REQUEST)
        // when
        val result = systemUnderTest.execute("", "123")
        // then
        Assert.assertEquals(LoginUseCase.LoginResult.USERNAME_EMPTY, result)
    }

    @Test
    fun testEmptyPassword() = runTest {
        // given
        coEvery { repository.logIn("jorge", "") } returns BancashResult.Error(DataError.NetworkError.BAD_REQUEST)
        // when
        val result = systemUnderTest.execute("jorge", "")
        // then
        Assert.assertEquals(LoginUseCase.LoginResult.PASSWORD_EMPTY, result)
    }

    @Test
    fun testInvalidEmail() = runTest {
        // given
        coEvery { repository.logIn("jorge", "notAnEmail") } returns BancashResult.Error(DataError.NetworkError.BAD_REQUEST)
        // when
        val result = systemUnderTest.execute("jorge", "notAnEmail")
        // then
        Assert.assertEquals(LoginUseCase.LoginResult.INVALID_EMAIL, result)
    }

    @Test
    fun testInvalidInput() = runTest {
        // given
        coEvery { repository.logIn( "some@email.com", "pass") } returns BancashResult.Error(DataError.NetworkError.BAD_REQUEST)
        // when
        val result = systemUnderTest.execute( "some@email.com", "pass")
        // then
        Assert.assertEquals(LoginUseCase.LoginResult.INVALID_INPUT, result)
    }

    @Test
    fun testExternalError() = runTest {
        // given
        coEvery { repository.logIn( "some@email.com", "pass") } returns BancashResult.Error(DataError.NetworkError.SERVER_ERROR)
        // when
        val result = systemUnderTest.execute( "some@email.com", "pass")
        // then
        Assert.assertEquals(LoginUseCase.LoginResult.EXTERNAL_ERROR, result)
    }

    @Test
    fun testSuccess() = runTest { 
        // given
        coEvery { repository.logIn("some@email.com", "pass") } returns BancashResult.Success(mockk())
        // when
        val result = systemUnderTest.execute("some@email.com", "pass")
        // then
        Assert.assertEquals(LoginUseCase.LoginResult.SUCCESS, result)
    }
}