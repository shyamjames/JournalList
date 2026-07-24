# JournalList 🌿

JournalList is a beautifully designed, modern Android journaling application built with Kotlin and Jetpack Compose. It provides a peaceful sanctuary for users to log their thoughts, track their moods, and reflect on their personal growth over time.

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
* **Storage:** Local persistence with on-device SQLite.

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

### Privacy Defaults
* JournalList stores entries locally on-device.
* Android backup is explicitly disabled for app data to keep journal entries private to the device.

## 🎨 Design System (Desert Solace)

The app leverages a custom color palette inspired by warm, desert tones to invoke a sense of calm and reflection:
* **Primary:** Rich earthy tones for main actions.
* **Surfaces:** Muted sand and soft cream backgrounds for reduced eye strain.
* **Typography:** Clean, modern, and highly legible typography prioritizing user content.

## 📝 License

This project is licensed under the MIT License.

See:
* `/home/runner/work/JournalList/JournalList/LICENSE`
* `/home/runner/work/JournalList/JournalList/ASSET_LICENSES.md`

## 📦 F-Droid Metadata

F-Droid/fastlane metadata is provided under:
* `/home/runner/work/JournalList/JournalList/fastlane/metadata/android/en-US/title.txt`
* `/home/runner/work/JournalList/JournalList/fastlane/metadata/android/en-US/short_description.txt`
* `/home/runner/work/JournalList/JournalList/fastlane/metadata/android/en-US/full_description.txt`
* `/home/runner/work/JournalList/JournalList/fastlane/metadata/android/en-US/changelogs/default.txt`
* `/home/runner/work/JournalList/JournalList/fastlane/metadata/android/en-US/images/phoneScreenshots/`
* Screenshot placeholders are currently included and should be replaced with real device screenshots before F-Droid submission.

## 🧪 Reproducible Build Verification

Run from repository root:
```bash
./gradlew --no-daemon clean testDebugUnitTest lintDebug assembleDebug
```

Last verification run (2026-07-24) result:
* ❌ Failed at Gradle plugin resolution
* Error: `Plugin [id: 'com.android.application', version: '9.3.0', apply: false] was not found`
* Impact: reproducible builds are currently blocked until `com.android.application` is pinned to a resolvable released version in `/home/runner/work/JournalList/JournalList/gradle/libs.versions.toml`.
