package com.example.challangeset_mar.presentation.ui.views

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challangeset_mar.R
import com.example.challangeset_mar.data.models.People
import com.example.challangeset_mar.ui.theme.BackArrowColor
import com.example.challangeset_mar.ui.theme.BackSideColor
import com.example.challangeset_mar.ui.theme.BackTextColor
import com.example.challangeset_mar.ui.theme.CardImageGradientEnd
import com.example.challangeset_mar.ui.theme.CardImageGradientStart
import com.example.challangeset_mar.ui.theme.FrontArrowColor
import com.example.challangeset_mar.ui.theme.FrontPrimaryText
import com.example.challangeset_mar.ui.theme.FrontSecondaryText
import com.example.challangeset_mar.ui.theme.FrontSideColor

enum class CardFace { Front, Back }

@Composable
fun AstroCard(
    craftName: String = "Unknown Spacecraft",
    crewCount: Int = 0,
    crewMembers: List<People> = emptyList(),
    modifier: Modifier = Modifier
) {
    SpacecraftFlipCard(
        teamList = crewMembers.map { it.name ?: "Unknown" },
        craftName = craftName,
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
    )
}

@Composable
private fun SpacecraftFlipCard(
    teamList: List<String>,
    craftName: String,
    modifier: Modifier = Modifier
) {
    var isFlipped by remember { mutableStateOf(false) }
    val rotationY by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 1000), label = ""
    )

    val showBackSide by remember {
        derivedStateOf { rotationY > 90f }
    }

    val density = LocalDensity.current.density

    Box(
        modifier = modifier
            .graphicsLayer {
                this.rotationY = rotationY
                cameraDistance = 12f * density
                scaleX = if (this.rotationY > 90f) -1f else 1f
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                isFlipped = !isFlipped
            }
    ) {
        if (showBackSide) {
            BackSideCard(teamList)
        } else {
            FrontSideCard(
                numberOfMembers = teamList.size,
                craftName = craftName
            )
        }
    }
}

@Composable
private fun FrontSideCard(
    numberOfMembers: Int,
    craftName: String
) {
    val density = LocalDensity.current
    val frontCutCornerShape = GenericShape { size, _ ->
        val cornerSize = with(density) { 30.dp.toPx() }
        moveTo(0f, 0f)
        lineTo(size.width - cornerSize, 0f)
        lineTo(size.width, cornerSize)
        lineTo(size.width, size.height)
        lineTo(cornerSize, size.height)
        lineTo(0f, size.height - cornerSize)
        lineTo(0f, 0f)
        close()
    }

    Card(
        modifier = Modifier
            .fillMaxSize(),
        shape = frontCutCornerShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FrontSideColor)
        ) {
            Image(
                painter = painterResource(id = R.drawable.vector),
                contentDescription = "Background vector",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.2f))
            )

            Image(
                painter = painterResource(id = R.drawable.union),
                contentDescription = "Union icon",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart),
                colorFilter = ColorFilter.tint(FrontArrowColor)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                CardImageGradientStart.copy(alpha = 0.55f),
                                CardImageGradientEnd.copy(alpha = 0.55f)
                            )
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = craftName,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = FrontPrimaryText,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "$numberOfMembers crew members",
                        fontSize = 18.sp,
                        color = FrontSecondaryText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun BackSideCard(
    teamList: List<String>
) {
    val density = LocalDensity.current
    val backCutCornerShape = GenericShape { size, _ ->
        val cornerSize = with(density) { 30.dp.toPx() }
        moveTo(0f, cornerSize)
        lineTo(0f, size.height)
        lineTo(size.width - cornerSize, size.height)
        lineTo(size.width, size.height - cornerSize)
        lineTo(size.width, 0f)
        lineTo(cornerSize, 0f)
        close()
    }

    Card(
        modifier = Modifier
            .fillMaxSize(),
        shape = backCutCornerShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = BackSideColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.vector),
                contentDescription = "Background vector",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .graphicsLayer {
                        rotationY = 180f
                    },
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.Gray.copy(alpha = 0.3f))
            )

            Image(
                painter = painterResource(id = R.drawable.union),
                contentDescription = "Union icon",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopEnd),
                colorFilter = ColorFilter.tint(BackArrowColor)
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                teamList.forEachIndexed { index, name ->
                    Text(
                        text = "${index + 1}. ${name}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = BackTextColor,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}