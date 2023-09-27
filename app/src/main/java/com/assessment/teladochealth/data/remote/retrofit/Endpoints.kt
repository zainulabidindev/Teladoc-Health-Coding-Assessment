package com.assessment.teladochealth.data.remote.retrofit

import com.assessment.teladochealth.domain.model.ITunesSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface Endpoints {

    @GET("?")
    suspend fun getITunesSearchResponse(
        @Query("term") term:String,
        @Query("media") media: String,
        @Query("entity") entity: String,
        @Query("attribute") attribute: String,
    ): ITunesSearchResponse

}