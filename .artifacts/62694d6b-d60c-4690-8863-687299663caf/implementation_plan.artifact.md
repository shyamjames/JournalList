# Implementation Plan - Rename, Release v1, and Documentation

This plan addresses the incomplete project rename (fixing the "app not running" issue), creates a `README.md`, and builds the first "v1" APK for the project.

## User Review Required

> [!IMPORTANT]
> **Package Rename**: The package name will be changed from `com.example.journallist` to `com.example.coconote`. This is a breaking change for existing installations on devices.
> [!NOTE]
> **APK Release**: I will build a **Release APK**. Since I don't have a signing key, this will be an **unsigned** release APK by default unless a debug build is preferred for testing.

## Proposed Changes

### 1. Complete Project Rename
Fixing the inconsistencies from the recent name change to ensure the app runs correctly.

#### [MODIFY] [settings.gradle.kts](file:///home/shyam/AndroidStudioProjects/CocoNote/settings.gradle.kts)
- Update `rootProject.name` to `"CocoNote"`.

#### [MODIFY] [build.gradle.kts (app)](file:///home/shyam/AndroidStudioProjects/CocoNote/app/build.gradle.kts)
- Update `namespace` and `applicationId` to `"com.example.coconote"`.

#### [MODIFY] [strings.xml](file:///home/shyam/AndroidStudioProjects/CocoNote/app/src/main/res/values/strings.xml)
- Update `app_name` to `"CocoNote"`.

#### [MODIFY] [themes.xml](file:///home/shyam/AndroidStudioProjects/CocoNote/app/src/main/res/values/themes.xml) & [themes.xml (night)](file:///home/shyam/AndroidStudioProjects/CocoNote/app/src/main/res/values-night/themes.xml)
- Rename style `Theme.JournalList` to `Theme.CocoNote`.

#### [MODIFY] [AndroidManifest.xml](file:///home/shyam/AndroidStudioProjects/CocoNote/app/src/main/AndroidManifest.xml)
- Update `android:theme` to `@style/Theme.CocoNote`.

#### [MODIFY] Source Code Migration
- Update `package` declarations in all files from `com.example.journallist.*` to `com.example.coconote.*`.
- Update all `import` statements.
- Move files to the new directory structure: `app/src/main/java/com/example/coconote/`.
- Update hardcoded "Desert Solace" or "JournalList" UI strings to "CocoNote".

### 2. Documentation

#### [NEW] [README.md](file:///home/shyam/AndroidStudioProjects/CocoNote/README.md)
- Create a comprehensive README with:
    - Project Title (CocoNote)
    - Description (Personal journaling app with bento-style UI)
    - Features (Mood tracking, Calendar, Favorites)
    - Build/Run instructions.

### 3. Release v1 (APK Export)
- Execute `./gradlew assembleRelease` to generate the APK.
- Provide the user with the path to the generated APK file.

## Verification Plan

### Automated Tests
- Run `./gradlew clean :app:assembleDebug` to verify the build after renaming.
- Run `./gradlew :app:assembleRelease` for the v1 release.

### Manual Verification
- Deploy to the device using the new package name and verify the "CocoNote" branding.
- Verify that the app launches and functions as expected.
