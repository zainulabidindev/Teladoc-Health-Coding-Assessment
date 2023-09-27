package com.assessment.teladochealth.data.repository

import com.assessment.teladochealth.data.ITunesSearchDataSource
import com.assessment.teladochealth.data.util.NoNetworkException
import com.assessment.teladochealth.data.constants.DataConstants.Companion.ATTRIBUTE_ARTIST_TERM
import com.assessment.teladochealth.data.constants.DataConstants.Companion.ENTITY_ALBUM
import com.assessment.teladochealth.data.constants.DataConstants.Companion.MEDIA_TYPE_MUSIC
import com.assessment.teladochealth.domain.model.ITunesSearchResponse
import com.assessment.teladochealth.presentation.compose.states.RequestStates
import com.task.util.NetworkHelper
import junit.framework.TestCase.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify


@ExperimentalCoroutinesApi
class ITunesSearchRepositoryTest {

    private lateinit var repository: ITunesSearchRepository
    private val remoteDataSource: ITunesSearchDataSource.Remote =
        mock(ITunesSearchDataSource.Remote::class.java)
    private val networkHelper: NetworkHelper = mock(NetworkHelper::class.java)


    val term = "The Beatles"

    @Before
    fun setUp() {
        repository = ITunesSearchRepository(remoteDataSource, networkHelper)
    }

    @Test
    fun `getITunesSearchResponse success`() = runTest {
        val response = ITunesSearchResponse(
            resultCount = 1,
            results = mockList
        )

        Mockito.`when`(networkHelper.isNetworkConnected()).thenReturn(true)

        Mockito.`when`(
            remoteDataSource.getITunesSearchApiResponse(
                term,
                MEDIA_TYPE_MUSIC,
                ENTITY_ALBUM,
                ATTRIBUTE_ARTIST_TERM
            )
        )
            .thenReturn(response)

        val flow = repository.getITunesSearchResponse(
            term,
            MEDIA_TYPE_MUSIC,
            ENTITY_ALBUM,
            ATTRIBUTE_ARTIST_TERM
        )

        flow.collect { result ->
            when (result) {
                is RequestStates.Loading -> {
                }

                is RequestStates.Success -> {
                    assert(result.data == response.results)
                }

                else -> {
                    fail("Expected RequestStates.Success, but got $result")
                }
            }
        }

        verify(networkHelper).isNetworkConnected()
        verify(remoteDataSource).getITunesSearchApiResponse(
            term,
            MEDIA_TYPE_MUSIC,
            ENTITY_ALBUM,
            ATTRIBUTE_ARTIST_TERM
        )
    }

    @Test
    fun `getITunesSearchResponse no network connection`() = runTest {
        Mockito.`when`(networkHelper.isNetworkConnected()).thenReturn(false)

        val flow = repository.getITunesSearchResponse(
            term,
            MEDIA_TYPE_MUSIC,
            ENTITY_ALBUM,
            ATTRIBUTE_ARTIST_TERM
        )


        flow.collect { result ->
            when (result) {
                is RequestStates.Loading -> {
                }

                is RequestStates.Error -> {
                    assert(result.exception is NoNetworkException)
                }

                else -> {
                    fail("Expected RequestStates.Error, but got $result")
                }
            }
        }


        verify(networkHelper).isNetworkConnected()
        verify(remoteDataSource, times(0)).getITunesSearchApiResponse(
            term,
            MEDIA_TYPE_MUSIC,
            ENTITY_ALBUM,
            ATTRIBUTE_ARTIST_TERM
        )
    }

    val mockList = listOf(
        ITunesSearchResponse.ITunesSearchItem(
            artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/f2/98/fb/f298fb48-1e0e-6ad4-4cff-fb824b77f02e/15UMGIM59587.rgb.jpg/100x100bb.jpg",
            collectionName = "The Beatles (The White Album)"
        )
    )
}







