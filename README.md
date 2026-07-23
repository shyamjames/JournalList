# JournalList 🌿

JournalList (formerly known as CocoNote) is a beautifully designed, modern Android journaling application built with Kotlin and Jetpack Compose. It provides a peaceful sanctuary for users to log their thoughts, track their moods, and reflect on their personal growth over time.

## 🌟 Features

* **Rich Journaling Experience:** Create, edit, and delete detailed journal entries seamlessly.
* **Mood Tracking:** Assign moods (Joyful ☀️, Calm 🧘🏽, Energetic ⚡, Sad 🌧️, Stressed 🌪️) to entries to track emotional well-being.
* **Tagging System:** Organize entries with custom hashtags for easy categorization (e.g., `#Personal`, `#Work`, `#Ideas`).
* **Favorites:** Star specific entries as favorites for quick access later.
* **Advanced Filtering & Search:**
  * Search instantly across entry titles, content, and tags.
  * Filter the feed by specific moods or by starred (⭐) entries.
* **Calendar Progress View:** A dedicated screen to visualize journaling streaks and progress over the month.
* **Modern UI/UX:** Built with a cohesive "Desert Solace" design system using Material 3, featuring glassmorphism, soft shadows, and smooth navigation transitions.

## 🛠 Tech Stack

* **Language:** Kotlin
* **UI Toolkit:** Jetpack Compose & Material 3
* **Architecture:** Single Activity, custom state-based navigation with backstack management.
* **Storage:** Local persistence (JSON-backed custom repository).

## 🚀 Getting Started

### Prerequisites
* Android Studio (latest stable version recommended)
* JDK 17+

### Building the Project
1. Clone the repository and open the project in Android Studio.
2. Allow Gradle to sync and download dependencies.
3. Build the debug APK using Gradle:
   ```bash
   ./gradlew assembleDebug
   ```
4. Run the app on an emulator or physical device directly from Android Studio.

## 🎨 Design System (Desert Solace)

The app leverages a custom color palette inspired by warm, desert tones to invoke a sense of calm and reflection:
* **Primary:** Rich earthy tones for main actions.
* **Surfaces:** Muted sand and soft cream backgrounds for reduced eye strain.
* **Typography:** Clean, modern, and highly legible typography prioritizing user content.

## 📝 License

This project is licensed under the MIT License.
