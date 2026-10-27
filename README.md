# Playlist Maker

Учебный проект по курсу «Android-разработчик» (Яндекс Практикум).

## Стек

- Kotlin
- Jetpack Compose (Material 3)
- Android SDK 36 (minSdk 24)
- Gradle 8.13, Android Gradle Plugin 8.13.0

## Сборка

```bash
./gradlew assembleDebug
```

APK появится в `app/build/outputs/apk/debug/app-debug.apk`.

## Структура

- `app/src/main/java/com/example/playlistmaker` — исходный код
- `app/src/main/java/com/example/playlistmaker/ui/theme` — тема приложения
- `app/src/main/res` — строковые и стилевые ресурсы

## Работа с Git

Разработка ведётся в ветках спринтов `feature/sprint-XX`, которые затем
сливаются в `main` через Pull Request.
