package com.example.framework.features.characters.datasources

import com.example.domain.entities.Character
import com.example.framework.sources.db.daos.CharactersDao
import com.example.framework.sources.db.entities.DbCharactersPage
import com.example.framework.sources.db.entities.DbPage
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class InMemoryCharacterDataSourceImplTest {

    private val charactersDao: CharactersDao = mockk()

    private val inMemoryCharacterDataSourceImpl = InMemoryCharacterDataSourceImpl(charactersDao)

    @Test
    fun `getCharactersPage invokes getCharactersPage on dao with the same page`() {
        runBlockingTest {
            // Given
            val expectedPage = 1

            coEvery { charactersDao.getCharactersPage(expectedPage) } returns DbCharactersPage(
                DbPage(page = expectedPage + 1),
                emptyList()
            )

            // When
            inMemoryCharacterDataSourceImpl.getCharactersPage(expectedPage)

            // Then
            coVerify { charactersDao.getCharactersPage(expectedPage) }
        }
    }

    @Test
    fun `when storeCharactersPage invokes insertCharactersPage on dao with same page`() {
        runBlockingTest {
            // Given
            val expectedPage = 1

            val expectedCharacters = emptyList<Character>()

            coEvery { charactersDao.insertCharactersPage(any()) } just Runs

            // When
            inMemoryCharacterDataSourceImpl.storeCharactersPage(expectedPage, expectedCharacters)

            // Then
            coVerify { charactersDao.insertCharactersPage(match { it.page.page == expectedPage }) }
        }
    }

    @Test
    fun `when storeCharacters invokes insertCharacters on dao`() {
        runBlockingTest {
            // Given
            val expectedCharacters = emptyList<Character>()
            coEvery { charactersDao.insertCharacters(any()) } returns listOf(1)

            // When
            inMemoryCharacterDataSourceImpl.storeCharacters(expectedCharacters)

            // Then
            coVerify { charactersDao.insertCharacters(match { it.count() == expectedCharacters.count() }) }
        }
    }

    @Test
    fun `when isCharactersPageStored invokes getPage on dao`() {
        runBlockingTest {
            // Given
            val expectedPage = 1
            coEvery { charactersDao.getPage(expectedPage) } returns listOf(DbPage(page = expectedPage))

            // When
            inMemoryCharacterDataSourceImpl.isCharactersPageStored(expectedPage)

            // Then
            coVerify { charactersDao.getPage(expectedPage) }
        }
    }

    @Test
    fun `when isCharactersStored invokes getCharactersCountIn on dao`() {
        runBlockingTest {
            // Given
            val expectedCharacterIds = emptyList<Long>()

            coEvery { charactersDao.getCharactersCountIn(expectedCharacterIds) } returns 1

            // When
            inMemoryCharacterDataSourceImpl.isCharactersStored(expectedCharacterIds)

            // Then
            coVerify { charactersDao.getCharactersCountIn(expectedCharacterIds) }
        }
    }
}