Battery Notifier

A simple Android application that notifies users about their device's battery status and charging state. The app provides a visual representation of the battery level and updates in real-time when the device is charging or discharging.

Features
1. Monitors battery level and charging status.
2. Displays a notification with battery status and progress bars.
3. Changes notification appearance based on charging state.
4. Uses foreground services for continuous monitoring.

Requirements
1. Android API level 26 (Oreo) or higher.
2. Android Studio or any compatible IDE.

Installation
1. Clone the repository:
git clone https://github.com/ahsan-0707/batterynotifier.git
2. Open the project in Android Studio.
3. Build and run the application on your Android device or emulator.

Usage
1. The app runs in the background and listens for battery status changes.
2. A notification will appear showing the current battery level and whether the device is charging.
3. The notification updates dynamically as the battery level changes.

Architecture
The app consists of the following components:
1. MainActivity.kt: The main UI and battery status handling.
2. BatteryReceiver.kt: Receives battery and power connection events.
3. PowerConnectionReceiver.kt: Handles power connection broadcasts.
4. BatteryService.kt: Manages foreground service for battery monitoring.
5. notification_layout.xml: Custom layout for the notification.

Contributing
Contributions are welcome! Please open an issue or submit a pull request if you have suggestions or improvements.

Acknowledgments
Thanks to Android Developers for their extensive documentation and resources.
