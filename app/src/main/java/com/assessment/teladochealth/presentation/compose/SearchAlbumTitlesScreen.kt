package com.assessment.teladochealth.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.assessment.teladochealth.domain.model.ITunesSearchResponse
import com.assessment.teladochealth.presentation.compose.states.RequestStates
import com.assessment.teladochealth.presentation.constants.PresentationConstants.Companion.DEFAULT_SEARCH_VALUE
import com.assessment.teladochealth.presentation.constants.PresentationConstants.Companion.LOADING
import com.assessment.teladochealth.presentation.viewmodel.SearchItunesViewModel

@Composable
fun SearchAlbumTitlesScreen(
    viewModel: SearchItunesViewModel
) {
    var query by remember { mutableStateOf(DEFAULT_SEARCH_VALUE) }

    LaunchedEffect(Unit) {
        viewModel.searchArtist(query)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        SearchableToolbar(onQueryUpdated = { updatedQuery ->
            query = updatedQuery
            viewModel.searchArtist(updatedQuery)
        }, query = query)

        val viewStates by viewModel.searchAlbumTitlesState.collectAsState()
        when (viewStates) {
            is RequestStates.Loading -> {
                Text(
                    LOADING, Modifier.padding(16.dp)
                )
            }

            is RequestStates.Success -> {
                val images =
                    (viewStates as RequestStates.Success<List<ITunesSearchResponse.ITunesSearchItem>>).data
                ItemsList(images = images)

            }

            is RequestStates.Error -> {
                val error = (viewStates as RequestStates.Error).exception
                Text(
                    "${error.message}", Modifier.padding(16.dp)
                )
            }

        }
    }
}

@Composable
fun ItemsList(
    images: List<ITunesSearchResponse.ITunesSearchItem>
) {
    LazyColumn(modifier = Modifier.padding(12.dp)) {
        items(images) { image ->
            ListItem(item = image)
        }
    }
}

@Composable
fun ListItem(
    item: ITunesSearchResponse.ITunesSearchItem
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), elevation = 4.dp

    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(item.artworkUrl100),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape)
            )

            Text(
                text = AnnotatedString(item.collectionName),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(CenterVertically)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    val image = ITunesSearchResponse.ITunesSearchItem(
        artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/f2/98/fb/f298fb48-1e0e-6ad4-4cff-fb824b77f02e/15UMGIM59587.rgb.jpg/100x100bb.jpg",
        collectionName = "The Beatles (The White Album)"
    )
    ListItem(image)
}


