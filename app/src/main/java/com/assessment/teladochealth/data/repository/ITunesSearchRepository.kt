package com.assessment.teladochealth.data.repository

import com.assessment.teladochealth.data.ITunesSearchDataSource
import com.assessment.teladochealth.data.util.NoNetworkException
import com.assessment.teladochealth.domain.model.ITunesSearchResponse
import com.assessment.teladochealth.presentation.compose.states.RequestStates
import com.task.util.NetworkHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ITunesSearchRepository @Inject constructor(
    private val remoteDataSource: ITunesSearchDataSource.Remote,
    private val networkHelper: NetworkHelper
) {

    fun getITunesSearchResponse(
        term: String,
        media: String,
        entity: String,
        attribute: String
    ): Flow<RequestStates<List<ITunesSearchResponse.ITunesSearchItem>>> {
        return flow {
            emit(RequestStates.Loading)

            if (networkHelper.isNetworkConnected()) {
                try {
                    val remoteResponse = remoteDataSource.getITunesSearchApiResponse(
                        term = term,
                        media = media,
                        entity = entity,
                        attribute = attribute
                    )
                    emit(RequestStates.Success(remoteResponse.results))
                } catch (e: Exception) {
                    emit(RequestStates.Error(e))
                }
            } else {
                emit(RequestStates.Error(NoNetworkException()))
            }
        }
    }
}






