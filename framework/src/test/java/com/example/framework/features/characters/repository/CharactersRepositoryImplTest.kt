package com.example.framework.features.characters.repository

import com.example.data.characters.datasources.InMemoryCharactersDataSource
import com.example.data.characters.datasources.NetworkCharactersDataSource
import com.example.domain.entities.Character
import com.example.domain.entities.CharactersPage
import com.example.domain.entities.Location
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.*
import kotlin.math.exp

@ExperimentalCoroutinesApi
class CharactersRepositoryImplTest {

    private val inMemoryCharactersDataSource: InMemoryCharactersDataSource = mock()
    private val networkCharactersDataSource: NetworkCharactersDataSource = mock()

    private val charactersRepositoryImpl =
        CharactersRepositoryImpl(inMemoryCharactersDataSource, networkCharactersDataSource)

    @Test
    fun `when getCharactersPage and characters page is stored then gets data from dao`() {
        runBlockingTest {
            // given
            val expectedPage = 1

            whenever(inMemoryCharactersDataSource.isCharactersPageStored(expectedPage)).thenReturn(
                true
            )

            // When
            charactersRepositoryImpl.getCharactersPage(expectedPage)

            // Then
            verify(inMemoryCharactersDataSource).isCharactersPageStored(expectedPage)
            verify(inMemoryCharactersDataSource).getCharactersPage(expectedPage)
            verifyNoMoreInteractions(inMemoryCharactersDataSource)
            verifyNoMoreInteractions(networkCharactersDataSource)
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

            whenever(inMemoryCharactersDataSource.isCharactersPageStored(expectedPage)).thenReturn(false)
            whenever(networkCharactersDataSource.getCharactersPage(expectedPage)).thenReturn(expectedCharactersPage)

            // When
            charactersRepositoryImpl.getCharactersPage(expectedPage)

            // Then
            verify(inMemoryCharactersDataSource).isCharactersPageStored(expectedPage)
            verify(networkCharactersDataSource).getCharactersPage(expectedPage)
            verify(inMemoryCharactersDataSource).storeCharactersPage(expectedPage, expectedCharactersPage.characters)
            verifyNoMoreInteractions(inMemoryCharactersDataSource)
            verifyNoMoreInteractions(networkCharactersDataSource)
        }
    }

    @Test
    fun `when getCharacters and characters are stored then gets data from dao`() {
        runBlockingTest {
            // given
            val expectedCharacterIds = listOf("1")

            whenever(inMemoryCharactersDataSource.isCharactersStored(any())).thenReturn(true)

            // When
            charactersRepositoryImpl.getCharacters(expectedCharacterIds)

            // Then
            verify(inMemoryCharactersDataSource).isCharactersStored(any())
            verify(inMemoryCharactersDataSource).getCharacters(any())
            verifyNoMoreInteractions(inMemoryCharactersDataSource)
            verifyNoMoreInteractions(networkCharactersDataSource)
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

            whenever(inMemoryCharactersDataSource.isCharactersStored(any())).thenReturn(false)
            whenever(networkCharactersDataSource.getCharacters(expectedCharacterIds)).thenReturn(expectedCharacters)

            // When
            charactersRepositoryImpl.getCharacters(expectedCharacterIds)

            // Then
            verify(inMemoryCharactersDataSource).isCharactersStored(any())
            verify(networkCharactersDataSource).getCharacters(expectedCharacterIds)
            verify(inMemoryCharactersDataSource).storeCharacters(expectedCharacters)
            verifyNoMoreInteractions(inMemoryCharactersDataSource)
            verifyNoMoreInteractions(networkCharactersDataSource)
        }
    }
}