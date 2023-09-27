package com.assessment.teladochealth.domain.usecase

import com.assessment.teladochealth.data.repository.ITunesSearchRepository
import com.assessment.teladochealth.domain.constants.DomainConstants.Companion.ATTRIBUTE_ARTIST_TERM
import com.assessment.teladochealth.domain.constants.DomainConstants.Companion.ENTITY_ALBUM
import com.assessment.teladochealth.domain.constants.DomainConstants.Companion.MEDIA_TYPE_MUSIC
import com.assessment.teladochealth.domain.constants.DomainConstants.Companion.NO_ALBUMS_FOUND_ERROR_MESSAGE
import com.assessment.teladochealth.domain.model.ITunesSearchResponse
import com.assessment.teladochealth.presentation.compose.states.RequestStates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList

/**
 * Use case for searching album titles on iTunes.
 * @param repository The repository responsible for fetching data from iTunes.
 */
class SearchAlbumTitlesUseCase(private val repository: ITunesSearchRepository) {

    operator fun invoke(term: String): Flow<RequestStates<List<ITunesSearchResponse.ITunesSearchItem>>> {
        return flow {
            try {
                val requestStates = repository.getITunesSearchResponse(
                    term = term,
                    media = MEDIA_TYPE_MUSIC,
                    entity = ENTITY_ALBUM,
                    attribute = ATTRIBUTE_ARTIST_TERM
                )
                val imagesState = requestStates.toList().lastOrNull()

                if (imagesState is RequestStates.Success && imagesState.data.isEmpty()) {
                    emit(RequestStates.Error(Exception(String.format(NO_ALBUMS_FOUND_ERROR_MESSAGE, term))))
                } else {
                    emitAll(requestStates)
                }
            } catch (e: Exception) {
                emit(RequestStates.Error(e))
            }
        }
    }

}
