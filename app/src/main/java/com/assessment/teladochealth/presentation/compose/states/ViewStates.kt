package com.assessment.teladochealth.presentation.compose.states

sealed class RequestStates<out R> {
    data class Success<out T>(val data:T): RequestStates<T>()
    data class Error(val exception: Exception): RequestStates<Nothing>()
    object Loading: RequestStates<Nothing>()
}
