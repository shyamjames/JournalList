package com.example.coconote.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.coconote.model.JournalEntry
import com.example.coconote.model.Mood
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

class JournalDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "desert_solace_journal.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_ENTRIES = "entries"

        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_TIMESTAMP = "timestamp"
        private const val COLUMN_MOOD = "mood"
        private const val COLUMN_TAGS = "tags"
        private const val COLUMN_WEATHER = "weather"
        private const val COLUMN_LOCATION = "location"
        private const val COLUMN_FAVORITE = "is_favorite"
        private const val COLUMN_PROMPT = "prompt"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_ENTRIES (
                $COLUMN_ID TEXT PRIMARY KEY,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_CONTENT TEXT NOT NULL,
                $COLUMN_TIMESTAMP INTEGER NOT NULL,
                $COLUMN_MOOD TEXT NOT NULL,
                $COLUMN_TAGS TEXT NOT NULL,
                $COLUMN_WEATHER TEXT NOT NULL,
                $COLUMN_LOCATION TEXT NOT NULL,
                $COLUMN_FAVORITE INTEGER NOT NULL,
                $COLUMN_PROMPT TEXT
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ENTRIES")
        onCreate(db)
    }

    fun getAllEntries(): List<JournalEntry> {
        val list = mutableListOf<JournalEntry>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_ENTRIES,
            null, null, null, null, null,
            "$COLUMN_TIMESTAMP DESC"
        )

        cursor.use { c ->
            val idIndex = c.getColumnIndexOrThrow(COLUMN_ID)
            val titleIndex = c.getColumnIndexOrThrow(COLUMN_TITLE)
            val contentIndex = c.getColumnIndexOrThrow(COLUMN_CONTENT)
            val timeIndex = c.getColumnIndexOrThrow(COLUMN_TIMESTAMP)
            val moodIndex = c.getColumnIndexOrThrow(COLUMN_MOOD)
            val tagsIndex = c.getColumnIndexOrThrow(COLUMN_TAGS)
            val weatherIndex = c.getColumnIndexOrThrow(COLUMN_WEATHER)
            val locationIndex = c.getColumnIndexOrThrow(COLUMN_LOCATION)
            val favoriteIndex = c.getColumnIndexOrThrow(COLUMN_FAVORITE)
            val promptIndex = c.getColumnIndexOrThrow(COLUMN_PROMPT)

            while (c.moveToNext()) {
                val tagsStr = c.getString(tagsIndex)
                val tagsList = if (tagsStr.isBlank()) emptyList() else tagsStr.split(",")

                list.add(
                    JournalEntry(
                        id = c.getString(idIndex),
                        title = c.getString(titleIndex),
                        content = c.getString(contentIndex),
                        timestamp = c.getLong(timeIndex),
                        mood = Mood.fromId(c.getString(moodIndex)),
                        tags = tagsList,
                        weather = c.getString(weatherIndex),
                        location = c.getString(locationIndex),
                        isFavorite = c.getInt(favoriteIndex) == 1,
                        prompt = c.getString(promptIndex)
                    )
                )
            }
        }
        return list
    }

    fun insertEntry(entry: JournalEntry) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID, entry.id)
            put(COLUMN_TITLE, entry.title)
            put(COLUMN_CONTENT, entry.content)
            put(COLUMN_TIMESTAMP, entry.timestamp)
            put(COLUMN_MOOD, entry.mood.id)
            put(COLUMN_TAGS, entry.tags.joinToString(","))
            put(COLUMN_WEATHER, entry.weather)
            put(COLUMN_LOCATION, entry.location)
            put(COLUMN_FAVORITE, if (entry.isFavorite) 1 else 0)
            put(COLUMN_PROMPT, entry.prompt)
        }
        db.insertWithOnConflict(TABLE_ENTRIES, null, values, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun updateEntry(entry: JournalEntry) {
        insertEntry(entry)
    }

    fun deleteEntry(id: String) {
        val db = writableDatabase
        db.delete(TABLE_ENTRIES, "$COLUMN_ID = ?", arrayOf(id))
    }
}

class JournalRepository(context: Context) {
    private val dbHelper = JournalDatabaseHelper(context)
    private val _entries = MutableStateFlow<List<JournalEntry>>(emptyList())
    val entries: StateFlow<List<JournalEntry>> = _entries.asStateFlow()

    init {
        loadEntries()
        if (_entries.value.isEmpty()) {
            seedSampleEntries()
        }
    }

    fun refresh() {
        loadEntries()
    }

    private fun loadEntries() {
        _entries.value = dbHelper.getAllEntries()
    }

    fun saveEntry(entry: JournalEntry) {
        dbHelper.insertEntry(entry)
        loadEntries()
    }

    fun deleteEntry(id: String) {
        dbHelper.deleteEntry(id)
        loadEntries()
    }

    fun toggleFavorite(id: String) {
        val current = _entries.value.find { it.id == id } ?: return
        val updated = current.copy(isFavorite = !current.isFavorite)
        dbHelper.updateEntry(updated)
        loadEntries()
    }

    private fun seedSampleEntries() {
        val now = Calendar.getInstance()
        
        val entry1 = JournalEntry(
            title = "Morning Coffee & Quiet Sanctuary",
            content = "Spent the early morning sitting on the warm patio as the sun broke through the horizon. The air was crisp, carrying a faint scent of pine and terracotta. Recorded three things I felt deeply grateful for today: peace of mind, warm coffee, and slow progress.",
            timestamp = now.timeInMillis - (2 * 3600 * 1000), // 2 hours ago
            mood = Mood.CALM,
            tags = listOf("Personal", "Morning", "Gratitude"),
            weather = "🌅 Sunrise Clear",
            location = "Home Patio",
            isFavorite = true,
            prompt = "What made you feel grounded this morning?"
        )

        now.add(Calendar.DAY_OF_YEAR, -1)
        val entry2 = JournalEntry(
            title = "Breakthrough on UI Design System",
            content = "Finally landed on the Bento Box structural logic for the application! Settled on generous 24dp rounded corners, muted sand backgrounds, and warm Ochre highlights. Everything feels calm, tactile, and delightfully cozy.",
            timestamp = now.timeInMillis,
            mood = Mood.JOYFUL,
            tags = listOf("Creative", "Design", "Work"),
            weather = "☀️ Warm & Clear",
            location = "Desert Studio",
            isFavorite = true
        )

        now.add(Calendar.DAY_OF_YEAR, -1)
        val entry3 = JournalEntry(
            title = "Evening Hike under Starlit Skies",
            content = "Walked along the foothills as twilight settled into night. The temperature dropped gently into a cool breeze. Watching the stars bloom one by one felt like a quiet reminder of scale and patience.",
            timestamp = now.timeInMillis,
            mood = Mood.REFLECTIVE,
            tags = listOf("Nature", "Walk", "Thoughts"),
            weather = "🌌 Clear & Starlit",
            location = "Foothill Trail"
        )

        now.add(Calendar.DAY_OF_YEAR, -2)
        val entry4 = JournalEntry(
            title = "Energized Sprint & New Ideas",
            content = "Sketched five new concepts for feature layouts today. Felt an incredible surge of creative flow. Celebrated with matcha tea and a short piano break.",
            timestamp = now.timeInMillis,
            mood = Mood.ENERGETIC,
            tags = listOf("Ideas", "Focus"),
            weather = "🌤️ Part Sun",
            location = "Desert Studio"
        )

        saveEntry(entry1)
        saveEntry(entry2)
        saveEntry(entry3)
        saveEntry(entry4)
    }
}
