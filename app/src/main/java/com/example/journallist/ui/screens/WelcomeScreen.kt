package com.example.journallist.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.journallist.R
import com.example.journallist.ui.components.BentoCard
import com.example.journallist.ui.components.PillButton
import com.example.journallist.ui.theme.DesertOnPrimary
import com.example.journallist.ui.theme.DesertOnSurface
import com.example.journallist.ui.theme.DesertOnSurfaceVariant
import com.example.journallist.ui.theme.DesertPrimary
import com.example.journallist.ui.theme.DesertSecondaryContainer
import com.example.journallist.ui.theme.DesertSurface
import com.example.journallist.ui.theme.DesertSurfaceContainer
import com.example.journallist.ui.theme.DesertSurfaceContainerLow
import com.example.journallist.ui.theme.DesertTertiaryFixed

@Composable
fun WelcomeScreen(
    onStartJournaling: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DesertSurface)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Header Section
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = tween(600)) + slideInVertically(initialOffsetY = { -40 }, animationSpec = tween(600))
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(96.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(DesertSurfaceContainer)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Desert Solace Logo",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Desert Solace",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 34.sp
                        ),
                        color = DesertOnSurface
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Your Cozy Sanctuaries of Daily Reflections",
                        style = MaterialTheme.typography.bodyLarge,
                        color = DesertOnSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Bento Feature Grid Showcase
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = tween(800, delayMillis = 200)) + slideInVertically(initialOffsetY = { 60 }, animationSpec = tween(800, delayMillis = 200))
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Bento Row: 2 Mini Cards
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        BentoCard(
                            backgroundColor = DesertTertiaryFixed,
                            cornerRadius = 24.dp,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Icon(
                                    imageVector = Icons.Default.Book,
                                    contentDescription = null,
                                    tint = DesertOnSurface,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Bento Style",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = DesertOnSurface
                                )
                                Text(
                                    text = "Soft cards & moods",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        BentoCard(
                            backgroundColor = DesertSecondaryContainer,
                            cornerRadius = 24.dp,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = DesertOnSurface,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Track Progress",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = DesertOnSurface
                                )
                                Text(
                                    text = "Streaks & calendar",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Action Button
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = tween(1000, delayMillis = 400)) + slideInVertically(initialOffsetY = { 80 }, animationSpec = tween(1000, delayMillis = 400))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PillButton(
                        text = "Begin Journaling",
                        icon = Icons.AutoMirrored.Filled.ArrowForward,
                        onClick = onStartJournaling,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Everything stays private on your device",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                        color = DesertOnSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
