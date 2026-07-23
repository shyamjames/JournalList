package com.example.coconote.model

import java.util.UUID

enum class Mood(
    val id: String,
    val label: String,
    val emoji: String,
    val colorHex: String,
    val containerColorHex: String
) {
    JOYFUL("joyful", "Joyful", "☀️", "#8D4B00", "#FFE9E1"),
    CALM("calm", "Calm", "🧘", "#3F6355", "#C4EBD9"),
    ENERGETIC("energetic", "Energetic", "⚡", "#6E5E0D", "#F6DF84"),
    REFLECTIVE("reflective", "Reflective", "💭", "#554336", "#FFF1EC"),
    GRATEFUL("grateful", "Grateful", "🌿", "#587C6D", "#F5FFF8"),
    MELANCHOLY("melancholy", "Melancholy", "🌧️", "#887364", "#FDDBCF");

    companion object {
        fun fromId(id: String): Mood {
            return entries.firstOrNull { it.id.equals(id, ignoreCase = true) } ?: JOYFUL
        }
    }
}

data class JournalEntry(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val mood: Mood = Mood.JOYFUL,
    val tags: List<String> = emptyList(),
    val weather: String = "☀️ Sunny",
    val location: String = "Desert Solace",
    val isFavorite: Boolean = false,
    val prompt: String? = null
)
