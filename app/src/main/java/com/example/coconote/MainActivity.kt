package com.example.coconote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.coconote.data.JournalRepository
import com.example.coconote.model.JournalEntry
import com.example.coconote.ui.screens.AddEditJournalScreen
import com.example.coconote.ui.screens.CalendarProgressScreen
import com.example.coconote.ui.screens.JournalDetailScreen
import com.example.coconote.ui.screens.JournalListScreen
import com.example.coconote.ui.screens.WelcomeScreen
import com.example.coconote.ui.theme.DesertSolaceTheme
import com.example.coconote.ui.theme.DesertSurface

sealed interface Screen {
    data object Welcome : Screen
    data object JournalList : Screen
    data class AddEdit(val entryToEdit: JournalEntry? = null, val initialPrompt: String? = null) : Screen
    data class Detail(val entry: JournalEntry) : Screen
    data object CalendarProgress : Screen
}

class MainActivity : ComponentActivity() {

    private lateinit var repository: JournalRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        repository = JournalRepository(applicationContext)

        setContent {
            DesertSolaceTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    color = DesertSurface
                ) {
                    DesertSolaceApp(repository = repository)
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DesertSolaceApp(repository: JournalRepository) {
    val initialScreen = remember {
        if (repository.isFirstLaunch()) Screen.Welcome else Screen.JournalList
    }
    var currentScreen by remember { mutableStateOf<Screen>(initialScreen) }
    val entries by repository.entries.collectAsState()

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = {
            fadeIn(animationSpec = tween(350)) togetherWith fadeOut(animationSpec = tween(250))
        },
        label = "screenTransition"
    ) { targetScreen ->
        when (targetScreen) {
            is Screen.Welcome -> {
                WelcomeScreen(
                    onStartJournaling = {
                        repository.setWelcomeCompleted()
                        currentScreen = Screen.JournalList
                    }
                )
            }

            is Screen.JournalList -> {
                JournalListScreen(
                    entries = entries,
                    onAddEntry = { prompt ->
                        currentScreen = Screen.AddEdit(entryToEdit = null, initialPrompt = prompt)
                    },
                    onEntryClick = { entry ->
                        currentScreen = Screen.Detail(entry = entry)
                    },
                    onToggleFavorite = { id ->
                        repository.toggleFavorite(id)
                    },
                    onOpenCalendar = {
                        currentScreen = Screen.CalendarProgress
                    }
                )
            }

            is Screen.AddEdit -> {
                AddEditJournalScreen(
                    entryToEdit = targetScreen.entryToEdit,
                    initialPrompt = targetScreen.initialPrompt,
                    onBack = {
                        currentScreen = Screen.JournalList
                    },
                    onSave = { savedEntry ->
                        repository.saveEntry(savedEntry)
                        currentScreen = Screen.JournalList
                    }
                )
            }

            is Screen.Detail -> {
                // Ensure detail reflects updated repository entry state
                val activeEntry = entries.find { it.id == targetScreen.entry.id } ?: targetScreen.entry

                JournalDetailScreen(
                    entry = activeEntry,
                    onBack = {
                        currentScreen = Screen.JournalList
                    },
                    onEdit = {
                        currentScreen = Screen.AddEdit(entryToEdit = activeEntry)
                    },
                    onDelete = {
                        repository.deleteEntry(activeEntry.id)
                        currentScreen = Screen.JournalList
                    },
                    onToggleFavorite = {
                        repository.toggleFavorite(activeEntry.id)
                    }
                )
            }

            is Screen.CalendarProgress -> {
                CalendarProgressScreen(
                    entries = entries,
                    onBack = {
                        currentScreen = Screen.JournalList
                    },
                    onEntryClick = { entry ->
                        currentScreen = Screen.Detail(entry = entry)
                    },
                    onToggleFavorite = { id ->
                        repository.toggleFavorite(id)
                    }
                )
            }
        }
    }
}
