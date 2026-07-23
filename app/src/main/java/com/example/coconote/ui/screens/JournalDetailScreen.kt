package com.example.journallist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.journallist.model.JournalEntry
import com.example.journallist.ui.components.BentoCard
import com.example.journallist.ui.components.TagChip
import com.example.journallist.ui.theme.DesertOnSurface
import com.example.journallist.ui.theme.DesertOnSurfaceVariant
import com.example.journallist.ui.theme.DesertOutlineVariant
import com.example.journallist.ui.theme.DesertPrimary
import com.example.journallist.ui.theme.DesertSecondaryContainer
import com.example.journallist.ui.theme.DesertSurface
import com.example.journallist.ui.theme.DesertSurfaceContainerLow
import com.example.journallist.ui.theme.DesertSurfaceContainerLowest
import com.example.journallist.ui.theme.DesertTertiaryFixed
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JournalDetailScreen(
    entry: JournalEntry,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    val formattedDate = remember(entry.timestamp) {
        SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault()).format(Date(entry.timestamp))
    }
    val formattedTime = remember(entry.timestamp) {
        SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date(entry.timestamp))
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = "Delete Entry?",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                    color = DesertOnSurface
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete this journal memory? This action cannot be undone.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = DesertOnSurfaceVariant
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDelete()
                    }
                ) {
                    Text(
                        text = "Delete",
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(text = "Cancel", color = DesertOnSurface)
                }
            },
            containerColor = DesertSurfaceContainerLowest,
            shape = RoundedCornerShape(24.dp)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DesertSurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 40.dp)
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = DesertOnSurface
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Favorite Toggle Button
                    IconButton(onClick = onToggleFavorite) {
                        Icon(
                            imageVector = if (entry.isFavorite) Icons.Default.Star else Icons.Outlined.StarBorder,
                            contentDescription = "Favorite",
                            tint = if (entry.isFavorite) DesertPrimary else DesertOutlineVariant
                        )
                    }

                    // Edit Button
                    IconButton(onClick = onEdit) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Entry",
                            tint = DesertPrimary
                        )
                    }

                    // Delete Button
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Entry",
                            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            // Entry Header Banner Card
            BentoCard(
                backgroundColor = DesertSurfaceContainerLow,
                cornerRadius = 28.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(DesertTertiaryFixed)
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "${entry.mood.emoji} ${entry.mood.label}",
                                style = MaterialTheme.typography.labelLarge,
                                color = DesertOnSurface
                            )
                        }

                        Text(
                            text = entry.weather,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                            color = DesertOnSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = entry.title,
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            lineHeight = 32.sp
                        ),
                        color = DesertOnSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "$formattedDate • $formattedTime",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                            color = DesertPrimary
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = null,
                                tint = DesertOnSurfaceVariant,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = entry.location,
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                color = DesertOnSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Prompt Card if present
            if (entry.prompt != null) {
                BentoCard(
                    backgroundColor = DesertSecondaryContainer,
                    cornerRadius = 20.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = DesertPrimary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Prompt: ${entry.prompt}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                fontWeight = FontWeight.Medium
                            ),
                            color = DesertOnSurface
                        )
                    }
                }
            }

            // Main Content Body Card (Paper Texture Feel)
            BentoCard(
                backgroundColor = DesertSurfaceContainerLowest,
                cornerRadius = 28.dp,
                elevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = entry.content,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            lineHeight = 28.sp,
                            fontSize = 17.sp
                        ),
                        color = DesertOnSurface
                    )

                    if (entry.tags.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(24.dp))

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            entry.tags.forEach { tag ->
                                TagChip(text = tag)
                            }
                        }
                    }
                }
            }
        }
    }
}
