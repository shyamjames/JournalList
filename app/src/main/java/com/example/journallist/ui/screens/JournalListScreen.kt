package com.example.journallist.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.journallist.R
import com.example.journallist.model.JournalEntry
import com.example.journallist.model.Mood
import com.example.journallist.ui.components.BentoCard
import com.example.journallist.ui.components.DesertTextField
import com.example.journallist.ui.components.MoodChip
import com.example.journallist.ui.components.TagChip
import com.example.journallist.ui.theme.DesertOnPrimary
import com.example.journallist.ui.theme.DesertOnSurface
import com.example.journallist.ui.theme.DesertOnSurfaceVariant
import com.example.journallist.ui.theme.DesertOutlineVariant
import com.example.journallist.ui.theme.DesertPrimary
import com.example.journallist.ui.theme.DesertSecondaryContainer
import com.example.journallist.ui.theme.DesertSurface
import com.example.journallist.ui.theme.DesertSurfaceContainer
import com.example.journallist.ui.theme.DesertSurfaceContainerLow
import com.example.journallist.ui.theme.DesertSurfaceContainerLowest
import com.example.journallist.ui.theme.DesertTertiaryFixed
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun JournalListScreen(
    entries: List<JournalEntry>,
    onAddEntry: () -> Unit,
    onEntryClick: (JournalEntry) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onOpenCalendar: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedMoodFilter by remember { mutableStateOf<Mood?>(null) }

    val todayFormatted = remember {
        SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(Date())
    }

    val filteredEntries = remember(entries, searchQuery, selectedMoodFilter) {
        entries.filter { entry ->
            val matchesQuery = searchQuery.isBlank() ||
                    entry.title.contains(searchQuery, ignoreCase = true) ||
                    entry.content.contains(searchQuery, ignoreCase = true) ||
                    entry.tags.any { it.contains(searchQuery, ignoreCase = true) }

            val matchesMood = selectedMoodFilter == null || entry.mood == selectedMoodFilter
            matchesQuery && matchesMood
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DesertSurface)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // App Bar Header & Greeting
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Desert Solace Logo",
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(14.dp))
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = "Good Day,",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                                    color = DesertOnSurfaceVariant
                                )
                                Text(
                                    text = "My Sanctuary 🌿",
                                    style = MaterialTheme.typography.headlineLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp
                                    ),
                                    color = DesertOnSurface
                                )
                                Text(
                                    text = todayFormatted,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium
                                    ),
                                    color = DesertPrimary
                                )
                            }
                        }

                        // Calendar view button
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(DesertSurfaceContainer)
                                .clickable { onOpenCalendar() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = "Progress Calendar",
                                tint = DesertPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Search input field
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        DesertTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = "Search entries, tags, or thoughts...",
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )

                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear search",
                                    tint = DesertOnSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // Mood Filter Selector
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "FILTER BY MOOD",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 11.sp,
                            letterSpacing = 1.sp
                        ),
                        color = DesertOnSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                    )

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            // "All" Chip
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(if (selectedMoodFilter == null) DesertPrimary else DesertSurfaceContainerLowest)
                                    .clickable { selectedMoodFilter = null }
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = "All Moods",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = if (selectedMoodFilter == null) DesertOnPrimary else DesertOnSurface
                                )
                            }
                        }

                        items(Mood.entries, key = { it.id }) { mood ->
                            MoodChip(
                                mood = mood,
                                isSelected = selectedMoodFilter == mood,
                                onClick = {
                                    selectedMoodFilter = if (selectedMoodFilter == mood) null else mood
                                }
                            )
                        }
                    }
                }
            }

            // Entries Header & Count
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "JOURNAL LOGS (${filteredEntries.size})",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 12.sp,
                            letterSpacing = 1.sp
                        ),
                        color = DesertOnSurfaceVariant
                    )
                }
            }

            // Empty State
            if (filteredEntries.isEmpty()) {
                item {
                    BentoCard(
                        backgroundColor = DesertSurfaceContainerLow,
                        cornerRadius = 24.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "🌵", fontSize = 40.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "No entries found",
                                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 18.sp),
                                color = DesertOnSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Tap the + button to capture your thoughts in this sanctuary.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = DesertOnSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                // List of Bento Journal Entry Cards
                items(filteredEntries, key = { it.id }) { entry ->
                    JournalBentoCard(
                        entry = entry,
                        onClick = { onEntryClick(entry) },
                        onFavoriteClick = { onToggleFavorite(entry.id) },
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = { onAddEntry() },
            containerColor = DesertPrimary,
            contentColor = DesertOnPrimary,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .shadow(8.dp, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "New Journal Entry",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun JournalBentoCard(
    entry: JournalEntry,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormatted = remember(entry.timestamp) {
        SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault()).format(Date(entry.timestamp))
    }

    BentoCard(
        backgroundColor = DesertSurfaceContainerLowest,
        cornerRadius = 24.dp,
        elevation = 3.dp,
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Mood Badge
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(DesertTertiaryFixed)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${entry.mood.emoji} ${entry.mood.label}",
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp),
                            color = DesertOnSurface
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = entry.weather,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                        color = DesertOnSurfaceVariant
                    )
                }

                // Favorite Star Button
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (entry.isFavorite) Icons.Default.Star else Icons.Outlined.StarBorder,
                        contentDescription = "Favorite",
                        tint = if (entry.isFavorite) DesertPrimary else DesertOutlineVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Title
            Text(
                text = entry.title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                color = DesertOnSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Content Snippet
            Text(
                text = entry.content,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                ),
                color = DesertOnSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom metadata & tags
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .horizontalScroll(rememberScrollState())
                ) {
                    entry.tags.forEach { tag ->
                        TagChip(text = tag)
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = dateFormatted,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                    color = DesertOnSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}
