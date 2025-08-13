package com.example.challangeset_mar.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.challangeset_mar.presentation.AstrosUiState
import com.example.challangeset_mar.presentation.ui.views.AstroCard
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import com.example.challangeset_mar.ui.theme.BackgroundGradientEnd
import com.example.challangeset_mar.ui.theme.BackgroundGradientStart

@Composable
fun AstrosScreen(
    modifier: Modifier,
    uiState : AstrosUiState
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(BackgroundGradientStart, BackgroundGradientEnd)
                )
            )
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            uiState.error != null -> {
                Text(
                    text = "Error: ${uiState.error}",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }

            uiState.astrosData != null -> {
                val astrosData = uiState.astrosData!!
                val crewMembers = astrosData.people?.filterNotNull() ?: emptyList()

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = crewMembers.groupBy { it.craft }.toList(),
                        key = { (craft, _) -> craft ?: "unknown" }
                    ) { (craft, members) ->
                        AstroCard(
                            craftName = craft ?: "Unknown Spacecraft",
                            crewCount = members.size,
                            crewMembers = members,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}