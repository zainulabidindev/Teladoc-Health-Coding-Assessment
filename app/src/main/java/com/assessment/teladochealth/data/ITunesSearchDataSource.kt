package com.assessment.teladochealth.data

import com.assessment.teladochealth.domain.model.ITunesSearchResponse

interface ITunesSearchDataSource {

    interface Remote {
        suspend fun getITunesSearchApiResponse(
            term: String,
            media: String,
            entity: String,
            attribute: String
        ): ITunesSearchResponse
    }
}
