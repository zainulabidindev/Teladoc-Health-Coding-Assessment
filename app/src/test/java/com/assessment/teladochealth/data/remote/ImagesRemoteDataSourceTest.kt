package com.assessment.teladochealth.data.remote

import com.assessment.teladochealth.data.ITunesSearchDataSource
import com.assessment.teladochealth.data.remote.retrofit.Endpoints
import com.assessment.teladochealth.domain.model.ITunesSearchResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class ImagesRemoteDataSourceTest {

    @Mock
    private lateinit var mockEndpoints: Endpoints

    private lateinit var remoteDataSource: ITunesSearchDataSource.Remote

    @Before
    fun setUp() {
        remoteDataSource = ImagesRemoteDataSource(mockEndpoints)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetImagesApiResponseSuccess() = runTest {
        val mockResponse = ITunesSearchResponse(
            resultCount = 100,
            results = mockList
        )


        `when`(
            mockEndpoints.getITunesSearchResponse(
                term = "The Beatles",
                media = DomainConstants.MEDIA_TYPE_MUSIC,
                entity = DomainConstants.ENTITY_ALBUM,
                attribute = DomainConstants.ATTRIBUTE_ARTIST_TERM
            )
        ).thenReturn(mockResponse)

        val result = remoteDataSource.getITunesSearchApiResponse(
            term = "The Beatles",
            media = DomainConstants.MEDIA_TYPE_MUSIC,
            entity = DomainConstants.ENTITY_ALBUM,
            attribute = DomainConstants.ATTRIBUTE_ARTIST_TERM
        )

        assertThat(result).isEqualTo(mockResponse)
    }

    val mockList = listOf(
        ITunesSearchResponse.ITunesSearchItem(
            artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/f2/98/fb/f298fb48-1e0e-6ad4-4cff-fb824b77f02e/15UMGIM59587.rgb.jpg/100x100bb.jpg",
            collectionName = "The Beatles (The White Album)"
        )
    )
}
