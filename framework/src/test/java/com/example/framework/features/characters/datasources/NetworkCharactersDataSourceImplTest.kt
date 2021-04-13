package com.example.framework.features.characters.datasources

import com.example.domain.entities.CharactersPage
import com.example.framework.sources.network.api.CharactersApi
import com.example.framework.sources.network.models.CharactersResponse
import com.example.framework.sources.network.models.Info
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import retrofit2.awaitResponse
import java.lang.Exception
import java.lang.IllegalArgumentException

@ExperimentalCoroutinesApi
class NetworkCharactersDataSourceImplTest {

    private val charactersApi = mockk<CharactersApi>(relaxed = true)

    private val networkCharactersDataSourceImpl = NetworkCharactersDataSourceImpl(charactersApi)

    @Test
    fun `when getCharactersPage with successful response with data returns CharactersPage`() {
        runBlockingTest {
            // Given
            mockkStatic("retrofit2.KotlinExtensions")
            val expectedPage = 0
            val expectedCharactersResponse = CharactersResponse(
                info = Info(1, 1, "1", 0),
                results = listOf(mockk(relaxed = true))
            )

            val charactersPageCall = mockk<Call<CharactersResponse>>()

            every { charactersApi.getCharactersPage(any()) } returns charactersPageCall
            coEvery { charactersPageCall.awaitResponse() } returns Response.success(
                expectedCharactersResponse
            )

            // When
            val response = networkCharactersDataSourceImpl.getCharactersPage(expectedPage)

            // Then
            assertEquals(expectedCharactersResponse.results.count(), response.characters.count())
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `when getCharactersPage with successful response without data throws exception`() {
        runBlockingTest {
            // Given
            mockkStatic("retrofit2.KotlinExtensions")
            val expectedPage = 0
            val expectedCharactersResponse = CharactersResponse(
                info = Info(1, 1, "1", 0),
                results = emptyList()
            )

            val charactersPageCall = mockk<Call<CharactersResponse>>()

            every { charactersApi.getCharactersPage(any()) } returns charactersPageCall
            coEvery { charactersPageCall.awaitResponse() } returns Response.success(
                expectedCharactersResponse
            )

            // When
            networkCharactersDataSourceImpl.getCharactersPage(expectedPage)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `when getCharactersPage with unsuccessful response throws exception`() {
        runBlockingTest {
            // Given
            mockkStatic("retrofit2.KotlinExtensions")
            val expectedPage = 0
            val expectedCharactersResponse = CharactersResponse(
                info = Info(1, 1, "1", 0),
                results = emptyList()
            )

            val charactersPageCall = mockk<Call<CharactersResponse>>()

            every { charactersApi.getCharactersPage(any()) } returns charactersPageCall
            coEvery { charactersPageCall.awaitResponse() } returns Response.error(500, ResponseBody.create(null, ""))

            // When
            networkCharactersDataSourceImpl.getCharactersPage(expectedPage)
        }
    }
}