package com.example.coconote.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coconote.model.JournalEntry
import com.example.coconote.model.Mood
import com.example.coconote.ui.components.BentoCard
import com.example.coconote.ui.components.DesertTextField
import com.example.coconote.ui.components.MoodChip
import com.example.coconote.ui.components.PillButton
import com.example.coconote.ui.components.TagChip
import com.example.coconote.ui.theme.DesertOnPrimary
import com.example.coconote.ui.theme.DesertOnSurface
import com.example.coconote.ui.theme.DesertOnSurfaceVariant
import com.example.coconote.ui.theme.DesertOutlineVariant
import com.example.coconote.ui.theme.DesertPrimary
import com.example.coconote.ui.theme.DesertSecondaryContainer
import com.example.coconote.ui.theme.DesertSurface
import com.example.coconote.ui.theme.DesertSurfaceContainerLow
import com.example.coconote.ui.theme.DesertSurfaceContainerLowest

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddEditJournalScreen(
    entryToEdit: JournalEntry? = null,
    initialPrompt: String? = null,
    onBack: () -> Unit,
    onSave: (JournalEntry) -> Unit
) {
    var title by remember { mutableStateOf(entryToEdit?.title ?: "") }
    var content by remember { mutableStateOf(entryToEdit?.content ?: (initialPrompt?.let { "$it\n\n" } ?: "")) }
    var selectedMood by remember { mutableStateOf(entryToEdit?.mood ?: Mood.JOYFUL) }
    var selectedWeather by remember { mutableStateOf(entryToEdit?.weather ?: "☀️ Sunny") }
    var selectedTags by remember { mutableStateOf(entryToEdit?.tags ?: listOf("Personal")) }
    var customTagInput by remember { mutableStateOf("") }
    var showErrorMessage by remember { mutableStateOf(false) }

    val availableWeathers = remember {
        listOf("☀️ Sunny", "🌅 Sunrise Clear", "🌌 Starlit", "🌤️ Part Sun", "🌧️ Rain", "❄️ Calm Breeze")
    }

    val defaultTagOptions = remember {
        listOf("Personal", "Work", "Ideas", "Growth", "Gratitude", "Nature", "Reflection")
    }

    val wordCount = remember(content) {
        if (content.isBlank()) 0 else content.trim().split("\\s+".toRegex()).size
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
                .padding(bottom = 32.dp)
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

                Text(
                    text = if (entryToEdit == null) "New Journal Log" else "Edit Entry",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = DesertOnSurface
                )

                PillButton(
                    text = "Save",
                    icon = Icons.Default.Check,
                    onClick = {
                        if (title.isBlank() && content.isBlank()) {
                            showErrorMessage = true
                        } else {
                            val newOrUpdated = JournalEntry(
                                id = entryToEdit?.id ?: java.util.UUID.randomUUID().toString(),
                                title = if (title.isBlank()) "Untitled Thought" else title.trim(),
                                content = content.trim(),
                                timestamp = entryToEdit?.timestamp ?: System.currentTimeMillis(),
                                mood = selectedMood,
                                tags = selectedTags,
                                weather = selectedWeather,
                                location = entryToEdit?.location ?: "Desert Solace",
                                isFavorite = entryToEdit?.isFavorite ?: false,
                                prompt = initialPrompt ?: entryToEdit?.prompt
                            )
                            onSave(newOrUpdated)
                        }
                    }
                )
            }

            // Error validation alert
            AnimatedVisibility(
                visible = showErrorMessage,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 4.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(DesertSecondaryContainer)
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Please write a title or content for your entry before saving.",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                        color = DesertPrimary
                    )
                }
            }

            // Optional Prompt Card
            if (initialPrompt != null) {
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
                        Column {
                            Text(
                                text = "Prompt Inspiration",
                                style = MaterialTheme.typography.labelLarge,
                                color = DesertPrimary
                            )
                            Text(
                                text = initialPrompt,
                                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
                                color = DesertOnSurface
                            )
                        }
                    }
                }
            }

            // Section 1: Mood Selector
            Column(modifier = Modifier.padding(vertical = 12.dp)) {
                Text(
                    text = "HOW ARE YOU FEELING?",
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 11.sp, letterSpacing = 1.sp),
                    color = DesertOnSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(Mood.entries.toTypedArray()) { mood ->
                        MoodChip(
                            mood = mood,
                            isSelected = selectedMood == mood,
                            onClick = { selectedMood = mood }
                        )
                    }
                }
            }

            // Section 2: Entry Title Field
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                Text(
                    text = "TITLE",
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 11.sp, letterSpacing = 1.sp),
                    color = DesertOnSurfaceVariant,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                DesertTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        showErrorMessage = false
                    },
                    placeholder = "Name your entry...",
                    singleLine = true,
                    textStyle = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp)
                )
            }

            // Section 3: Entry Body Text Editor
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "YOUR THOUGHTS",
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 11.sp, letterSpacing = 1.sp),
                        color = DesertOnSurfaceVariant
                    )
                    Text(
                        text = "$wordCount words • ${content.length} chars",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                        color = DesertOnSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                DesertTextField(
                    value = content,
                    onValueChange = {
                        content = it
                        showErrorMessage = false
                    },
                    placeholder = "Pour your heart out here...",
                    minLines = 8,
                    maxLines = 20,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(lineHeight = 24.sp)
                )
            }

            // Section 4: Weather Selector
            Column(modifier = Modifier.padding(vertical = 10.dp)) {
                Text(
                    text = "ATMOSPHERE & WEATHER",
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 11.sp, letterSpacing = 1.sp),
                    color = DesertOnSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(availableWeathers) { weather ->
                        val isSelected = selectedWeather == weather
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(if (isSelected) DesertPrimary.copy(alpha = 0.15f) else DesertSurfaceContainerLowest)
                                .clickable { selectedWeather = weather }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = weather,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 13.sp
                                ),
                                color = if (isSelected) DesertPrimary else DesertOnSurface
                            )
                        }
                    }
                }
            }

            // Section 5: Category Tags
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
                Text(
                    text = "TAGS",
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 11.sp, letterSpacing = 1.sp),
                    color = DesertOnSurfaceVariant,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    defaultTagOptions.forEach { tag ->
                        val isSelected = selectedTags.contains(tag)
                        TagChip(
                            text = tag,
                            isSelected = isSelected,
                            onClick = {
                                selectedTags = if (isSelected) {
                                    selectedTags - tag
                                } else {
                                    selectedTags + tag
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Custom Tag Input
                Row(verticalAlignment = Alignment.CenterVertically) {
                    DesertTextField(
                        value = customTagInput,
                        onValueChange = { customTagInput = it },
                        placeholder = "Add custom tag...",
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    PillButton(
                        text = "+ Tag",
                        onClick = {
                            val trimmed = customTagInput.trim().removePrefix("#")
                            if (trimmed.isNotEmpty() && !selectedTags.contains(trimmed)) {
                                selectedTags = selectedTags + trimmed
                                customTagInput = ""
                            }
                        },
                        isPrimary = false
                    )
                }
            }
        }
    }
}
