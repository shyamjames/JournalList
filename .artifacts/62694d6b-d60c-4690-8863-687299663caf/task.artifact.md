# Task: Rename Project, Release v1, and Documentation

- [ ] **Phase 1: Project Rename & Fix Launch Issue**
    - [ ] Update `settings.gradle.kts` (rootProject.name)
    - [ ] Update `app/build.gradle.kts` (namespace, applicationId)
    - [ ] Update `strings.xml` (app_name)
    - [ ] Update `themes.xml` (Style names)
    - [ ] Update `AndroidManifest.xml` (Theme reference)
    - [ ] Move source files to `com.example.coconote` and update package declarations
    - [ ] Update all internal references to `journallist`
    - [ ] Gradle Sync and Build Verify
- [ ] **Phase 2: Documentation**
    - [ ] Create `README.md`
- [ ] **Phase 3: Release v1**
    - [ ] Build Release APK (`./gradlew assembleRelease`)
    - [ ] Provide APK path to user
