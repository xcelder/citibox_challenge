package com.example.framework.features.characters.repository

import com.example.data.characters.datasources.InMemoryCharactersDataSource
import com.example.data.characters.datasources.NetworkCharactersDataSource
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage
import com.example.domain.entities.Location
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersRepositoryImplTest {

    private val inMemoryCharactersDataSource: InMemoryCharactersDataSource = mockk(relaxed = true)
    private val networkCharactersDataSource: NetworkCharactersDataSource = mockk(relaxed = true)

    private val charactersRepositoryImpl =
        CharactersRepositoryImpl(inMemoryCharactersDataSource, networkCharactersDataSource)

    @Test
    fun `when getCharactersPage and characters page is stored then gets data from dao`() {
        runBlockingTest {
            // given
            val expectedPage = 1

            coEvery { inMemoryCharactersDataSource.isCharactersPageStored(expectedPage) } returns true

            // When
            charactersRepositoryImpl.getCharactersPage(expectedPage)

            // Then
            coVerify { inMemoryCharactersDataSource.isCharactersPageStored(expectedPage) }
            coVerify { inMemoryCharactersDataSource.getCharactersPage(expectedPage) }
        }
    }

    @Test
    fun `when getCharactersPage and characters page is not stored then gets data from network and stores in db`() {
        runBlockingTest {
            // given
            val expectedPage = 1

            val expectedCharactersPage = CharactersPage(
                nextPage = expectedPage + 1,
                characters = emptyList()
            )

            coEvery { inMemoryCharactersDataSource.isCharactersPageStored(expectedPage) } returns false
            coEvery { networkCharactersDataSource.getCharactersPage(expectedPage) } returns expectedCharactersPage

            // When
            charactersRepositoryImpl.getCharactersPage(expectedPage)

            // Then
            coVerify { inMemoryCharactersDataSource.isCharactersPageStored(expectedPage) }
            coVerify { networkCharactersDataSource.getCharactersPage(expectedPage) }
            coVerify { inMemoryCharactersDataSource.storeCharactersPage(expectedPage, expectedCharactersPage.characters) }
        }
    }

    @Test
    fun `when getCharacters and characters are stored then gets data from dao`() {
        runBlockingTest {
            // given
            val expectedCharacterIds = listOf("1")

            coEvery { inMemoryCharactersDataSource.isCharactersStored(any()) } returns true

            // When
            charactersRepositoryImpl.getCharacters(expectedCharacterIds)

            // Then
            coVerify { inMemoryCharactersDataSource.isCharactersStored(any()) }
            coVerify { inMemoryCharactersDataSource.getCharacters(any()) }
        }
    }

    @Test
    fun `when getCharacters and characters are not stored then gets data from network and stores in db`() {
        runBlockingTest {
            // given
            val expectedCharacterIds = listOf("1")
            val expectedCharacters = listOf(Character(
                id = 1,
                name = "name",
                location = Location("location", "url"),
                episode = listOf(1),
                species = "species",
                type = "type",
                imageUrl = "image url"
            ))

            coEvery { inMemoryCharactersDataSource.isCharactersStored(any()) } returns false
            coEvery { networkCharactersDataSource.getCharacters(expectedCharacterIds) } returns expectedCharacters

            // When
            charactersRepositoryImpl.getCharacters(expectedCharacterIds)

            // Then
            coVerify { inMemoryCharactersDataSource.isCharactersStored(any()) }
            coVerify { networkCharactersDataSource.getCharacters(expectedCharacterIds) }
            coVerify { inMemoryCharactersDataSource.storeCharacters(expectedCharacters) }
        }
    }
}