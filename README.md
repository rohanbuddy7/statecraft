# StateCraft

Reusable **Jetpack Compose UI components** for Android applications.


Screenshots :
[Statecraft Screenshots](https://github.com/rohanbuddy7/docs/tree/main/statecraft)


## InputField Component

A customizable text input field with error handling, focus color, and more.

### Usage:
```kotlin
InputField(
    value = "Hello",
    onValueChange = { /* Handle value change */ },
    label = "Username",
    isError = false,
    errorMessage = "Please enter a valid username"
)
```

---

## EmptyScreen Component

A screen layout with an optional icon, title, message, and button for empty or error states.

### Usage:
```kotlin
EmptyScreen(
    icon = Icons.Default.Warning,
    title = "Warning!",
    message = "Something went wrong.",
    buttonText = "Retry",
    onButtonClick = { /* Retry action */ }
)
```

---

## Loader Component

A customizable loader with shimmer effect or other types for loading states.

### Usage:
```kotlin
Loader(type = LoaderType.SHIMMER, size = 80.dp)
```

---

## Walkthrough Component

A walkthrough component for user onboarding or tutorials.

### Usage:
```kotlin
Walkthrough(
    steps = listOf("Step 1", "Step 2"),
    onNext = { /* Next step */ },
    onSkip = { /* Skip */ }
)
```

---

## CoachMark Component

A coach mark to highlight UI elements and provide tips.

### Usage:
```kotlin
CoachMark(
    target = Modifier.padding(16.dp),
    message = "This is a special feature!",
    onDismiss = { /* Dismiss */ }
)
```

---

## Result Sealed Class

A sealed class to handle different result states such as success, error, progress, and more.

### Definition:
```kotlin
sealed class Result<T> {
    data class Success<T>(var data: T) : Result<T>()
    data class Error<T>(var throwable: Throwable) : Result<T>()
    data class Progress<T>(var percentage: Int) : Result<T>()
    class Unauthorized<T>(val message: String? = "Unauthorized Access") : Result<T>()
    class Empty<T>(val message: String? = null): Result<T>()
    class Initial<T>(): Result<T>()
}
```

### Usage:
```kotlin
fun handleResult(result: Result<String>) {
    when (result) {
        is Result.Success -> { /* Handle success */ }
        is Result.Error -> { /* Handle error */ }
        is Result.Progress -> { /* Show progress */ }
        is Result.Unauthorized -> { /* Handle unauthorized */ }
        is Result.Empty -> { /* Handle empty state */ }
        is Result.Initial -> { /* Initial state */ }
    }
}
```

---

## Installation

Add the following dependency in `build.gradle`:

```gradle
dependencies {
    implementation 'com.github.rohanbuddy7:statecraft:1.0.2'
}
```

---

## License

MIT License. See [LICENSE](LICENSE) for details.




