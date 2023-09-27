package com.assessment.teladochealth.data.util

import com.assessment.teladochealth.data.constants.DataConstants.Companion.NO_NETWORK_CONNECTION

class NoNetworkException(message: String = NO_NETWORK_CONNECTION) : Exception(message)
