package com.example.framework.features.characters.datasources

import com.example.domain.entities.Character
import com.example.framework.sources.db.daos.CharactersDao
import com.example.framework.sources.db.entities.DbCharactersPage
import com.example.framework.sources.db.entities.DbPage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.*
import kotlin.math.exp

@ExperimentalCoroutinesApi
class InMemoryCharacterDataSourceImplTest {

    private val charactersDao: CharactersDao = mock()

    private val inMemoryCharacterDataSourceImpl = InMemoryCharacterDataSourceImpl(charactersDao)

    @Test
    fun `getCharactersPage invokes getCharactersPage on dao with the same page`() {
        runBlockingTest {
            // Given
            val expectedPage = 1

            whenever(charactersDao.getCharactersPage(expectedPage)).thenReturn(DbCharactersPage(
                DbPage(page = expectedPage + 1),
                emptyList()
            ))

            // When
            inMemoryCharacterDataSourceImpl.getCharactersPage(expectedPage)

            // Then
            verify(charactersDao).getCharactersPage(expectedPage)
        }
    }

    @Test
    fun `when storeCharactersPage invokes insertPage and insertCharacters on dao`() {
        runBlockingTest {
            // Given
            val expectedPage = 1

            val expectedCharacters = emptyList<Character>()

            whenever(charactersDao.insertPage(any())).thenReturn(1)
            whenever(charactersDao.insertCharacters(any())).thenReturn(listOf(1))

            // When
            inMemoryCharacterDataSourceImpl.storeCharactersPage(expectedPage, expectedCharacters)

            // Then
            verify(charactersDao).insertPage(argThat { page == expectedPage })
            verify(charactersDao).insertCharacters(argThat { count() == expectedCharacters.count() })
        }
    }

    @Test
    fun `when storeCharacters invokes insertCharacters on dao`() {
        runBlockingTest {
            // Given
            val expectedCharacters = emptyList<Character>()

            // When
            inMemoryCharacterDataSourceImpl.storeCharacters(expectedCharacters)

            // Then
            verify(charactersDao).insertCharacters(argThat { count() == expectedCharacters.count() })
        }
    }

    @Test
    fun `when isCharactersPageStored invokes getPage on dao`() {
        runBlockingTest {
            // Given
            val expectedPage = 1
            whenever(charactersDao.getPage(expectedPage)).thenReturn(listOf(DbPage(page = expectedPage)))

            // When
            inMemoryCharacterDataSourceImpl.isCharactersPageStored(expectedPage)

            // Then
            verify(charactersDao).getPage(expectedPage)
        }
    }

    @Test
    fun `when isCharactersStored invokes getCharactersCountIn on dao`() {
        runBlockingTest {
            // Given
            val expectedCharacterIds = emptyList<Long>()

            whenever(charactersDao.getCharactersCountIn(expectedCharacterIds)).thenReturn(1)

            // When
            inMemoryCharacterDataSourceImpl.isCharactersStored(expectedCharacterIds)

            // Then
            verify(charactersDao).getCharactersCountIn(expectedCharacterIds)
        }
    }
}