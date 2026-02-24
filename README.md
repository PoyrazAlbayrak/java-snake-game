# Classic Snake Game (Java) 🐍

A classic, fully functional Snake Game built entirely from scratch using Java Swing and AWT. This project demonstrates foundational Computer Science concepts including Object-Oriented Programming (OOP), File I/O operations, and Multithreading.

### 🌟 Features
* **Classic Gameplay & UI:** Grid-based movement with dynamic snake growth and real-time score tracking.
* **Persistent High Score:** Utilizes Java File I/O (`FileWriter` and `Scanner`) to automatically create a `highscore.txt` file on the user's local machine, ensuring the best score is saved across sessions.
* **Optimized Audio Engine:** Sound effects (eating and game-over events) are executed on a separate background `Thread`. This prevents the main game loop from stuttering, ensuring a smooth rendering experience.
* **Custom Graphics:** Styled UI elements and image rendering for the food (`apple.png`).

### 🛠️ Technologies & Concepts Used
* **Language:** Java (Built and tested with **JDK 25**)
* **UI Framework:** `javax.swing` (JFrame, JPanel, Timer) and `java.awt` (Graphics, Color, Font)
* **Audio:** `javax.sound.sampled` for `.wav` playback
* **Data Structures:** `ArrayList<Point>` for dynamic snake body management

### 🚀 How to Run
1. Clone this repository to your local machine.
2. Ensure you have **JDK 25** (or a compatible modern Java version) installed.
3. Compile and run the `App.java` file from the `src` directory.
4. Press `ENTER` to start the game and use the `W, A, S, D` or `Arrow Keys` to navigate!

---
*Developed as a foundational project to practice Java programming and algorithm design.*
