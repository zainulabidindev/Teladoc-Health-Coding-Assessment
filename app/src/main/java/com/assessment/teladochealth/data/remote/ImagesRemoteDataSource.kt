package com.assessment.teladochealth.data.remote

import com.assessment.teladochealth.data.ITunesSearchDataSource
import com.assessment.teladochealth.data.remote.retrofit.Endpoints
import com.assessment.teladochealth.domain.model.ITunesSearchResponse
import javax.inject.Inject

class ImagesRemoteDataSource @Inject constructor(
    private val endpoints: Endpoints,
) : ITunesSearchDataSource.Remote {

    override suspend fun getITunesSearchApiResponse(
        term: String,
        media: String,
        entity: String,
        attribute: String
    ): ITunesSearchResponse {
        return endpoints.getITunesSearchResponse(
            term = term,
            media = media,
            entity = entity,
            attribute = attribute
        )
    }

}