package com.assessment.teladochealth.domain.model

data class ITunesSearchResponse(
    val resultCount: Int,
    val results: List<ITunesSearchItem>
){
    data class ITunesSearchItem(
        val collectionName: String,
        val artworkUrl100: String,
    )
}


