package com.assessment.teladochealth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.teladochealth.domain.model.ITunesSearchResponse
import com.assessment.teladochealth.domain.usecase.SearchAlbumTitlesUseCase
import com.assessment.teladochealth.presentation.compose.states.RequestStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchItunesViewModel @Inject constructor(
    private val searchTitlesUseCase: SearchAlbumTitlesUseCase
) : ViewModel() {

    private var searchJob: Job? = null

    private val _searchAlbumTitlesState =
        MutableStateFlow<RequestStates<List<ITunesSearchResponse.ITunesSearchItem>>>(
            RequestStates.Loading
        )
    val searchAlbumTitlesState: StateFlow<RequestStates<List<ITunesSearchResponse.ITunesSearchItem>>> =
        _searchAlbumTitlesState

    fun searchArtist(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(1000)
            searchTitlesUseCase.invoke(query).collect { state ->
                _searchAlbumTitlesState.value = state
            }
        }
    }

}