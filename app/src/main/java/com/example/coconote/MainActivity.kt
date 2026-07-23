package com.example.coconote

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    val initialScreen = remember {
        if (repository.isFirstLaunch()) Screen.Welcome else Screen.JournalList
    }
    var backStack by remember { mutableStateOf<List<Screen>>(listOf(initialScreen)) }
    val currentScreen = backStack.lastOrNull() ?: initialScreen
    val entries by repository.entries.collectAsState()

    var lastBackPressedTime by remember { mutableLongStateOf(0L) }

    fun navigateTo(screen: Screen) {
        backStack = backStack + screen
    }

    fun navigateBack() {
        if (backStack.size > 1) {
            backStack = backStack.dropLast(1)
        }
    }

    BackHandler(enabled = true) {
        if (backStack.size > 1) {
            navigateBack()
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastBackPressedTime < 2000) {
                (context as? ComponentActivity)?.finish()
            } else {
                lastBackPressedTime = currentTime
                Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
            }
        }
    }

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
                        backStack = listOf(Screen.JournalList)
                    }
                )
            }

            is Screen.JournalList -> {
                JournalListScreen(
                    entries = entries,
                    onAddEntry = {
                        navigateTo(Screen.AddEdit(entryToEdit = null, initialPrompt = null))
                    },
                    onEntryClick = { entry ->
                        navigateTo(Screen.Detail(entry = entry))
                    },
                    onToggleFavorite = { id ->
                        repository.toggleFavorite(id)
                    },
                    onOpenCalendar = {
                        navigateTo(Screen.CalendarProgress)
                    }
                )
            }

            is Screen.AddEdit -> {
                AddEditJournalScreen(
                    entryToEdit = targetScreen.entryToEdit,
                    initialPrompt = targetScreen.initialPrompt,
                    onBack = {
                        navigateBack()
                    },
                    onSave = { savedEntry ->
                        repository.saveEntry(savedEntry)
                        navigateBack()
                    }
                )
            }

            is Screen.Detail -> {
                // Ensure detail reflects updated repository entry state
                val activeEntry = entries.find { it.id == targetScreen.entry.id } ?: targetScreen.entry

                JournalDetailScreen(
                    entry = activeEntry,
                    onBack = {
                        navigateBack()
                    },
                    onEdit = {
                        navigateTo(Screen.AddEdit(entryToEdit = activeEntry))
                    },
                    onDelete = {
                        repository.deleteEntry(activeEntry.id)
                        navigateBack()
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
                        navigateBack()
                    },
                    onEntryClick = { entry ->
                        navigateTo(Screen.Detail(entry = entry))
                    },
                    onToggleFavorite = { id ->
                        repository.toggleFavorite(id)
                    }
                )
            }
        }
    }
}
