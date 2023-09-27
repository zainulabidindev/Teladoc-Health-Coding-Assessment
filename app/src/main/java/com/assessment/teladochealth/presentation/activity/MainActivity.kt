package com.assessment.teladochealth.presentation.activity


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.assessment.teladochealth.presentation.compose.SearchAlbumTitlesScreen
import com.assessment.teladochealth.presentation.compose.theme.TeladocHealthAssessmentTheme
import com.assessment.teladochealth.presentation.viewmodel.SearchItunesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeladocHealthAssessmentTheme {
                val viewModel = hiltViewModel<SearchItunesViewModel>()
                SearchAlbumTitlesScreen( viewModel =  viewModel)
            }
        }
    }
}