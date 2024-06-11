package me.kristianconk.bancash.domain.usecases

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import me.kristianconk.bancash.domain.model.BancashResult
import me.kristianconk.bancash.domain.model.DataError
import me.kristianconk.bancash.domain.model.Movement
import me.kristianconk.bancash.domain.model.MovementType
import me.kristianconk.bancash.domain.repository.BancashRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class GetMovementsUseCaseTest {

    lateinit var systemUnderTest: GetMovementsUseCase

    @MockK(relaxed = true)
    lateinit var repository: BancashRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        systemUnderTest = GetMovementsUseCase(repository)
    }

    @Test
    fun testGetMovementsSuccessAndData() = runTest {
        // given
        val mov = Movement("id", LocalDateTime.now(), 100.0, MovementType.PURCHASE, "compra")
        coEvery { repository.getMovements() } returns BancashResult.Success(listOf(mov))
        // when
        val movements = systemUnderTest.execute()
        // then
        assert(movements.isNotEmpty())
        Assert.assertEquals(MovementType.PURCHASE, movements[0].type)
    }

    @Test
    fun testGetMovementsFailToEmpty() = runTest {
        // given
        coEvery { repository.getMovements() } returns BancashResult.Error(DataError.NetworkError.SERVER_ERROR)
        // when
        val movements = systemUnderTest.execute()
        // then
        assert(movements.isEmpty())
    }
}