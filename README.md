# MAD-Java App — Study Notes (Assignment-1)

Date: 2025-11-06
Branch: `Assignment-1`

These notes document everything performed in the app: features, screens, flow, key Android concepts used, and how to reproduce each behavior.

## Overview

This Android app demonstrates core Mobile App Development concepts in Java:

- Login and Signup using SharedPreferences
- Theme toggling (Light/Dark) persisted across launches
- Activity lifecycle callbacks with visual feedback (Toasts)
- Explicit and implicit Intents
- System Notifications with channels
- Basic UI widgets (ConstraintLayout, Buttons, Switch, ListView, Spinner)

Minimum Android concepts touched: Manifest, runtime permissions (notifications), resources (layouts, colors, themes), and navigation between Activities.

## App structure (source)

- Activities (package `com.example.madjava`):
  - `LoginActivity.java` — Entry/launcher activity handling login and auto-login
  - `SignupActivity.java` — Registration screen storing credentials
  - `MainActivity.java` — Home screen with theme toggle, actions, and lifecycle toasts
  - `MainActivity2.java` — Topics list that navigates to demos or triggers a notification
  - `ActivityLifecycle.java` — Spinner-based screen for lifecycle topic list
  - `IntentLifecycle.java` — Implicit intents demo (Dial, Geo, SMS, Web search)
- Layouts (`app/src/main/res/layout`):
  - `activity_login.xml`, `activity_signup.xml`, `activity_main.xml`, `activity_main2.xml`, `activity_lifecycle.xml`, `activity_intent_lifecycle.xml`
- Themes/values (`app/src/main/res/values`):
  - `themes.xml` with `Theme.MADJava` (Material 3, DayNight)
  - `modes.xml` defining custom `LightTheme`/`DarkTheme` used by `MainActivity` toggle
  - `colors.xml`, `strings.xml`
- Drawables: `drawable/tree.png` (used as notification small icon)
- Manifest: `app/src/main/AndroidManifest.xml` (declares activities and notifications permission)

## Launch and navigation flow

1. App launches into `LoginActivity` (declared as MAIN/LAUNCHER in the manifest).
2. If SharedPreferences `IsLoggedIn` is true, the user is forwarded to `MainActivity` automatically.
3. From `LoginActivity`:
   - “Login” validates credentials saved by Signup and on success navigates to `MainActivity`.
   - “Register” opens `SignupActivity`.
4. From `SignupActivity`:
   - “Register” saves credentials into SharedPreferences and navigates to `LoginActivity`.
   - “Login” goes back to `LoginActivity`.
5. `MainActivity` actions:
   - Switch toggles Light/Dark theme and persists the choice in SharedPreferences (`isDarkMode`).
   - “Change color” sets background to green for the current session.
   - “Topics” opens `MainActivity2` (topic list/deep-dive demos).
   - “Logout” clears `IsLoggedIn` and navigates to `LoginActivity`.
6. `MainActivity2` topic list:
   - “Activity life cycle” → opens `ActivityLifecycle` screen.
   - “Intents” → opens `IntentLifecycle` screen.
   - “Notification” → sends a system notification with a tap action back to `MainActivity`.

## Topic-by-topic code walkthrough

### 1) Authentication (Login & Signup with SharedPreferences)

- Purpose: Demonstrate simple credential storage and session flag using `SharedPreferences`.
- Flow:
  1. User registers in `SignupActivity` → saves `username:` and `password:` in `SharedPreferences("login")`.
  2. On app launch, `LoginActivity` checks `IsLoggedIn`; if true, navigate to `MainActivity`.
  3. On Login button, entered values are compared with saved ones; on match, set `IsLoggedIn=true` → go to `MainActivity`.
- Key code:
  - `SignupActivity.onCreate` → click listener for `registerBtn` writes `username:`/`password:`.
  - `LoginActivity.onCreate` → auto-forward if `IsLoggedIn` is true.
  - `LoginActivity` Login button → compare inputs and update `IsLoggedIn`.
- Files: `app/src/main/java/com/example/madjava/SignupActivity.java`, `LoginActivity.java`
- Notes: For learning only; storing plain-text credentials is not secure.

### 2) Theme toggle (Light/Dark) with persistence

- Purpose: Let user switch themes and keep the choice across app restarts.
- Flow:
  1. `MainActivity.onCreate` reads `isDarkMode` from `SharedPreferences("login")` before `setContentView`.
  2. Applies `R.style.DarkTheme` or `R.style.LightTheme` via `setTheme(...)`.
  3. Switch (`switch1`) `onCheckedChanged` updates `isDarkMode` and calls `recreate()` to re-apply theme.
- Key code: `MainActivity.onCreate`, `CompoundButton.OnCheckedChangeListener` for `switch1`.
- Files: `MainActivity.java`, `res/values/modes.xml` (defines `LightTheme`/`DarkTheme`).
- Notes: Base app theme is Material3; runtime themes here are AppCompat—mixed on purpose for demo.

### 3) Activity lifecycle visualization

- Purpose: Show lifecycle callbacks order using Toasts.
- Flow: When navigating between activities or backgrounding the app, Toasts appear in `onCreate`, `onStart`, `onResume`, `onPause`, `onStop`, `onDestroy` of `MainActivity`.
- Key code: `MainActivity` lifecycle overrides.
- Files: `MainActivity.java`

### 4) Navigation with explicit intents

- Purpose: Move between app screens explicitly.
- Flow:
  - `MainActivity` → `MainActivity2` via button `btn2`.
  - `MainActivity2` list items:
    - index 0 → `ActivityLifecycle`
    - index 1 → `IntentLifecycle`
  - `MainActivity` → `LoginActivity` on Logout (`btn3`).
- Key code:
  - `MainActivity.startActivity()` private method opens `MainActivity2`.
  - `MainActivity2.onItemClick` switch-case opens target Activities.
  - Logout: clears `IsLoggedIn` then starts `LoginActivity`.
- Files: `MainActivity.java`, `MainActivity2.java`

### 5) Implicit intents (Dial, Geo, SMS, Web search)

- Purpose: Use system apps to handle actions.
- Flow (buttons in `IntentLifecycle`):
  - Call: `Intent.ACTION_DIAL` with `tel:` URI.
  - Geo: `Intent.ACTION_VIEW` with `geo:0,0?q=University of Gujrat, Gujrat`.
  - SMS: `Intent.ACTION_SENDTO` with `smsto:` and `sms_body` extra.
  - Web search: `Intent.ACTION_WEB_SEARCH` with `SearchManager.QUERY`.
- Files: `IntentLifecycle.java`
- Notes: Some actions may require user consent or a compatible app on the device.

### 6) Notifications with channel and PendingIntent

- Purpose: Post a system notification and handle tap action.
- Flow:
  1. Create `NotificationChannel` (Android O+) with id `ch_1000`.
  2. Build `NotificationCompat.Builder` with title, text, and small icon `R.drawable.tree`.
  3. Create `PendingIntent` to open `MainActivity`.
  4. Call `NotificationManager.notify(1000, ...)`.
- Files: `MainActivity2.java`
- Notes: `POST_NOTIFICATIONS` permission is declared. On Android 13+, prompt may appear.

### 7) Layouts and UI widgets

- Purpose: Show common widgets and ConstraintLayout usage.
- Key layouts:
  - `activity_login.xml` / `activity_signup.xml` — form fields and buttons.
  - `activity_main.xml` — Switch (`switch1`) and action buttons (`btn1`, `btn2`, `btn3`).
  - `activity_main2.xml` — `ListView` (`list1`).
  - `activity_lifecycle.xml` — `Spinner` (`sp1`).
  - `activity_intent_lifecycle.xml` — four demo buttons.

### 8) Manifest and permissions

- Purpose: Declare components and app-level config.
- Highlights:
  - Launcher activity: `LoginActivity` with `MAIN` + `LAUNCHER` filter.
  - Exported flags: external entry only for `LoginActivity` and `MainActivity2`.
  - Permission: `android.permission.POST_NOTIFICATIONS`.
  - App theme: `@style/Theme.MADJava` on `<application>`.
- File: `app/src/main/AndroidManifest.xml`

### 9) Resources and themes

- Base theme: `Theme.MADJava` from `values/themes.xml` (Material3 DayNight, no action bar).
- Runtime Light/Dark: defined in `values/modes.xml` and chosen at runtime in `MainActivity`.
- Colors/strings: `values/colors.xml`, `values/strings.xml`.

### 10) SharedPreferences contract (keys and meanings)

- File name: `login`
- Keys:
  - `username:` → String
  - `password:` → String
  - `IsLoggedIn` → boolean
  - `isDarkMode` → boolean

## Screens and behaviors

### LoginActivity (`activity_login.xml`)

- Widgets: Two `EditText` fields (`userInput`, `passInput`) and buttons `loginbtn`, `registerbtn`.
- Auto-login: On create, checks `IsLoggedIn` from `SharedPreferences("login")`.
- Login process:
  - Reads `username:` and `password:` from SharedPreferences (note the trailing colon in keys).
  - Compares to entered values; on match, sets `IsLoggedIn=true` and opens `MainActivity`.
  - On mismatch, shows a Toast: “Invalid User name or password”.
- Register button: Opens `SignupActivity`.

### SignupActivity (`activity_signup.xml`)

- Widgets mirror Login.
- Register process:
  - Validates non-empty username/password.
  - Saves to SharedPreferences (file `login`) with keys `username:` and `password:`.
  - Shows Toast and returns to `LoginActivity`.
- Login button: Navigates directly to `LoginActivity`.

Notes on credentials:

- Storage uses plain-text SharedPreferences for demo purposes only (not secure for production).
- Keys contain a colon (`username:`, `password:`); Login reads with the same keys.

### MainActivity (`activity_main.xml`)

- Purpose: Home screen after authentication; demonstrates theme toggling and lifecycle.
- Widgets:
  - `Switch` (`switch1`) labeled “Switch mode” to toggle dark/light theme.
  - Button `btn1` “Change color” sets background to green in-session.
  - Button `btn2` “Topics” opens `MainActivity2`.
  - Button `btn3` “Logout” clears login state and returns to `LoginActivity`.
- Theme persistence:
  - Reads `isDarkMode` from `SharedPreferences("login")` before `setContentView` and calls `setTheme(R.style.DarkTheme)` or `R.style.LightTheme` accordingly.
  - Applies a black background when dark mode is active.
  - Updates preference and calls `recreate()` when switch changes to apply the theme.
- Lifecycle toasts: Shows Toasts in `onCreate`, `onStart`, `onResume`, `onPause`, `onStop`, `onDestroy` to visualize the Activity lifecycle.

### MainActivity2 (`activity_main2.xml`)

- Purpose: Topic selector.
- Widgets: `ListView` (`list1`) bound with an `ArrayAdapter` of three items: Activity life cycle, Intents, Notification.
- Item actions:
  - 0 → `ActivityLifecycle` activity (explicit Intent).
  - 1 → `IntentLifecycle` activity (explicit Intent).
  - 2 → Triggers a Notification (see below).

### ActivityLifecycle (`activity_lifecycle.xml`)

- Purpose: Simple UI with a `Spinner` containing topics: Activity LifeCycle, Layouts, Intent, Notification (educational list).
- Demonstrates view binding and adapter usage (`ArrayAdapter`).

### IntentLifecycle (`activity_intent_lifecycle.xml`)

- Purpose: Demonstrates implicit intents.
- Buttons and actions:
  - “Call Button” (`button`) → `Intent.ACTION_DIAL` to dial `0331 1234567`.
  - “Geo Button” (`button2`) → `ACTION_VIEW` with a geo URI to show “University of Gujrat, Gujrat” on Maps.
  - “Sms Button” (`button3`) → `ACTION_SENDTO` with `smsto:` to open SMS composer pre-filled with “Hello how are you?”.
  - “Web SearchButton” (`button4`) → `ACTION_WEB_SEARCH` for query “Assasin's Creed”.

## Themes and styles

- Base theme: `Theme.MADJava` (Material3 DayNight, no action bar) from `values/themes.xml`.
- Custom runtime themes in `values/modes.xml`:
  - `LightTheme` (AppCompat Light) — white background, black text, `colorPrimary` red.
  - `DarkTheme` (AppCompat DayNight) — black background, white text, `colorPrimary` dark blue.
- `MainActivity` selects `LightTheme`/`DarkTheme` based on persisted `isDarkMode` and recreates the Activity when toggled.

## Notifications

- Permission: `POST_NOTIFICATIONS` declared in `AndroidManifest.xml` (required for Android 13+).
- Channel: Created for Android O+ with id `ch_1000`, name `chat_channel`, importance `DEFAULT`.
- Content: Title “BS_7B”, text “this is your new message”, small icon `R.drawable.tree`.
- Tap action: PendingIntent opens `MainActivity` with flags `NEW_TASK | CLEAR_TASK`.
- ID: Notification posted with id `1000`.

Notes:

- On Android 13+, the system prompts for notification permission on first post. Ensure the app requests/handles it as needed.
- The small icon should be a simple, solid icon; colorful PNGs may render poorly on some devices.

## SharedPreferences contract

- File name: `login`
- Keys:
  - `username:` → String (saved by SignupActivity)
  - `password:` → String (saved by SignupActivity)
  - `IsLoggedIn` → boolean (set true after successful login; cleared on logout)
  - `isDarkMode` → boolean (theme preference for MainActivity)

## AndroidManifest highlights

- Launcher: `LoginActivity` has `MAIN` and `LAUNCHER` intent filter.
- Exported flags set appropriately (LoginActivity and MainActivity2 are exported; others are internal only).
- App theme: `@style/Theme.MADJava` on the application.
- Icon: `@mipmap/s_app_icon` (set in both `icon` and `roundIcon`).
  - In `MainActivity`, tap “Logout” → sets `IsLoggedIn=false` → navigates back to `LoginActivity`.

## How to reproduce features

1. First-time setup: open app → `LoginActivity` → tap “Register”, enter credentials, tap “Register” → back to Login.
2. Login: enter the same credentials → tap “Login” → lands on `MainActivity`.
3. Theme toggle: flip the switch → app recreates with Light/Dark; restart app to see persistence.
4. Change color: tap “Change color” → background turns green.
5. Topics: tap “Topics” → select “Activity life cycle”, “Intents”, or “Notification” as desired.
6. Logout: from `MainActivity`, tap “Logout” → returns to `LoginActivity` and disables auto-login.

End of notes.
