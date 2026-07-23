package com.example.journallist.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.journallist.model.JournalEntry
import com.example.journallist.model.Mood
import com.example.journallist.ui.components.BentoCard
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
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun CalendarProgressScreen(
    entries: List<JournalEntry>,
    onBack: () -> Unit,
    onEntryClick: (JournalEntry) -> Unit,
    onToggleFavorite: (String) -> Unit
) {
    val calendarState = remember { Calendar.getInstance() }
    var currentMonthOffset by remember { mutableIntStateOf(0) }
    var selectedDayOfMonth by remember { mutableStateOf<Int?>(null) }

    // Compute calendar grid for current month offset
    val displayCalendar = remember(currentMonthOffset) {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, currentMonthOffset)
        cal.set(Calendar.DAY_OF_MONTH, 1)
        cal
    }

    val monthYearTitle = remember(displayCalendar) {
        SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(displayCalendar.time)
    }

    val daysInMonth = displayCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfWeek = displayCalendar.get(Calendar.DAY_OF_WEEK) - 1 // 0-indexed (Sun=0)

    // Map entries to days of the month
    val entriesByDay = remember(entries, displayCalendar) {
        val targetYear = displayCalendar.get(Calendar.YEAR)
        val targetMonth = displayCalendar.get(Calendar.MONTH)

        val map = mutableMapOf<Int, MutableList<JournalEntry>>()
        entries.forEach { entry ->
            val entryCal = Calendar.getInstance().apply { timeInMillis = entry.timestamp }
            if (entryCal.get(Calendar.YEAR) == targetYear && entryCal.get(Calendar.MONTH) == targetMonth) {
                val day = entryCal.get(Calendar.DAY_OF_MONTH)
                map.getOrPut(day) { mutableListOf() }.add(entry)
            }
        }
        map
    }

    // Compute streak
    val streakDays = remember(entries) {
        if (entries.isEmpty()) 0
        else {
            val uniqueDays = entries.map { entry ->
                val cal = Calendar.getInstance().apply { timeInMillis = entry.timestamp }
                "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.DAY_OF_YEAR)}"
            }.distinct().size
            uniqueDays
        }
    }

    // Filter entries for selected day or full month
    val displayedEntries = remember(selectedDayOfMonth, entriesByDay, entries) {
        if (selectedDayOfMonth != null) {
            entriesByDay[selectedDayOfMonth] ?: emptyList()
        } else {
            entriesByDay.values.flatten().sortedByDescending { it.timestamp }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DesertSurface)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 40.dp)
        ) {
            // Top Navigation Bar
            item {
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
                        text = "Progress & Calendar",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = DesertOnSurface
                    )

                    Spacer(modifier = Modifier.width(48.dp))
                }
            }

            // Bento Overview Cards (Streak & Totals)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Streak Card
                    BentoCard(
                        backgroundColor = DesertSecondaryContainer,
                        cornerRadius = 24.dp,
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(DesertPrimary)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalFireDepartment,
                                    contentDescription = null,
                                    tint = DesertOnPrimary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "$streakDays Days",
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    ),
                                    color = DesertOnSurface
                                )
                                Text(
                                    text = "Journal Streak",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                    color = DesertPrimary
                                )
                            }
                        }
                    }

                    // Total Entries Card
                    BentoCard(
                        backgroundColor = DesertTertiaryFixed,
                        cornerRadius = 24.dp,
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(DesertSurfaceContainerLowest)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Book,
                                    contentDescription = null,
                                    tint = DesertOnSurface,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "${entries.size}",
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    ),
                                    color = DesertOnSurface
                                )
                                Text(
                                    text = "Total Entries",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                    color = DesertOnSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // Monthly Calendar Card
            item {
                BentoCard(
                    backgroundColor = DesertSurfaceContainerLowest,
                    cornerRadius = 28.dp,
                    elevation = 3.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        // Month Selector Controls
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {
                                currentMonthOffset--
                                selectedDayOfMonth = null
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = "Previous Month",
                                    tint = DesertOnSurface
                                )
                            }

                            Text(
                                text = monthYearTitle,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                ),
                                color = DesertOnSurface
                            )

                            IconButton(onClick = {
                                currentMonthOffset++
                                selectedDayOfMonth = null
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = "Next Month",
                                    tint = DesertOnSurface
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Days of week header
                        val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
                        Row(modifier = Modifier.fillMaxWidth()) {
                            daysOfWeek.forEach { dayName ->
                                Text(
                                    text = dayName,
                                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp),
                                    color = DesertOnSurfaceVariant,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Calendar Grid (6 rows max x 7 cols)
                        val totalSlots = firstDayOfWeek + daysInMonth
                        val numRows = (totalSlots + 6) / 7

                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            for (row in 0 until numRows) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    for (col in 0 until 7) {
                                        val slotIndex = row * 7 + col
                                        val dayNum = slotIndex - firstDayOfWeek + 1

                                        if (dayNum in 1..daysInMonth) {
                                            val dayEntries = entriesByDay[dayNum] ?: emptyList()
                                            val isSelected = selectedDayOfMonth == dayNum

                                            CalendarDayCell(
                                                dayNum = dayNum,
                                                entries = dayEntries,
                                                isSelected = isSelected,
                                                onClick = {
                                                    selectedDayOfMonth = if (isSelected) null else dayNum
                                                },
                                                modifier = Modifier.weight(1f)
                                            )
                                        } else {
                                            Spacer(modifier = Modifier.weight(1f))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Filter status banner
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (selectedDayOfMonth != null)
                            "ENTRIES FOR DAY $selectedDayOfMonth (${displayedEntries.size})"
                        else
                            "ENTRIES IN $monthYearTitle (${displayedEntries.size})",
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp, letterSpacing = 1.sp),
                        color = DesertOnSurfaceVariant
                    )

                    if (selectedDayOfMonth != null) {
                        Text(
                            text = "Show All",
                            style = MaterialTheme.typography.labelLarge.copy(color = DesertPrimary, fontSize = 12.sp),
                            modifier = Modifier.clickable { selectedDayOfMonth = null }
                        )
                    }
                }
            }

            // Displayed Entries List
            if (displayedEntries.isEmpty()) {
                item {
                    BentoCard(
                        backgroundColor = DesertSurfaceContainerLow,
                        cornerRadius = 20.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "No journal logs recorded for this day.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = DesertOnSurfaceVariant,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }
            } else {
                items(displayedEntries, key = { it.id }) { entry ->
                    JournalBentoCard(
                        entry = entry,
                        onClick = { onEntryClick(entry) },
                        onFavoriteClick = { onToggleFavorite(entry.id) },
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CalendarDayCell(
    dayNum: Int,
    entries: List<JournalEntry>,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hasEntries = entries.isNotEmpty()
    val topMood = entries.firstOrNull()?.mood

    val bgColor = when {
        isSelected -> DesertPrimary
        hasEntries -> DesertSecondaryContainer
        else -> DesertSurfaceContainerLow.copy(alpha = 0.5f)
    }

    val textColor = when {
        isSelected -> DesertOnPrimary
        hasEntries -> DesertOnSurface
        else -> DesertOnSurfaceVariant
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .border(
                width = if (isSelected) 2.dp else if (hasEntries) 1.dp else 0.dp,
                color = if (isSelected) DesertPrimary else DesertOutlineVariant,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$dayNum",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (hasEntries || isSelected) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 13.sp
                ),
                color = textColor
            )

            if (hasEntries) {
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    entries.take(3).forEach { entry ->
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) DesertOnPrimary else DesertPrimary)
                        )
                    }
                }
            }
        }
    }
}
