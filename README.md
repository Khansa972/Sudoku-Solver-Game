# 🧩 Sudoku Solver Game

> A fully-featured Java Sudoku game with intelligent puzzle generation, backtracking solver, hint system, and a rich performance analytics dashboard.

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/Java%20Swing-GUI-007396?style=for-the-badge&logo=java&logoColor=white)
![AWT](https://img.shields.io/badge/Java%20AWT-Graphics-5382A1?style=for-the-badge&logo=java&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-22c55e?style=for-the-badge&logo=opensourceinitiative&logoColor=white)
![Course](https://img.shields.io/badge/Software%20Construction%20%26%20Development-Course%20Project-8b5cf6?style=for-the-badge&logo=bookstack&logoColor=white)
![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20macOS%20%7C%20Linux-0ea5e9?style=for-the-badge&logo=dell&logoColor=white)

</div>

---

## 🔍 Overview

This project is a complete **Sudoku Solver Game** built in Java with a Swing-based GUI. It was developed as a course project for **Software Construction & Development**, demonstrating key software engineering principles including:

- **Separation of Concerns** — Board logic, solver, generator, and GUI are each in their own class
- **Object-Oriented Design** — Clean encapsulation and responsibility delegation
- **Algorithm Design** — Backtracking-based puzzle solving and generation
- **User Experience** — Real-time feedback, error highlighting, hints, and a post-game analytics dashboard

---

## ✨ Features

### 🎮 Gameplay
- Generate puzzles at **three difficulty levels**: Easy, Medium, and Hard
- Manual puzzle entry support
- Real-time **error highlighting** for invalid number placements
- Row, column, and 3×3 box **cell highlighting** on focus
- **Hint system** that reveals the correct number for any empty cell
- **Auto-solve** button that completes the puzzle instantly
- **Board validation** to check if a completed puzzle is correct

### ⏱️ Statistics Tracking
- Live **game timer** tracking time elapsed
- **Hints used** counter
- **Mistakes made** counter

### 🏆 Performance Dashboard (Post-Game)
- Key Performance Indicators: accuracy, efficiency, correct moves
- **Donut chart** visualization of performance breakdown
- **Achievements system** with unlockable badges
- **Game history comparison** across multiple sessions (Overview, Detailed, Charts, Statistics tabs)
- **Report generation** — export a `.txt` performance report
- **CSV data export** for game history
- **Practice mode** with targeted improvement tips
- **Share results** dialog

---

## 📁 Project Structure

```
sudoku-solver-game/
│
├── SudokuGUI.java              # Main application window and game controller
├── SudokuBoard.java            # Board state management and game logic
├── SudokuSolver.java           # Backtracking solver and solution counter
├── PuzzleGenerator.java        # Random puzzle generation by difficulty
├── finalWindow.java            # Post-game performance analytics dashboard
│
├── README.md                   # Project documentation
├── .gitignore                  # Ignore compiled class files
├── LICENSE                     # MIT License
│
└── screenshots/
    ├── main-board.png          # Main game board
    ├── easy-puzzle.png         # Easy difficulty puzzle
    ├── hard-puzzle.png         # Hard difficulty puzzle
    ├── hint-system.png         # Hint cells highlighted
    └── dashboard.png           # Post-game analytics dashboard
```

---

## 🏗️ Architecture

The project follows a clean **layered architecture**:

```
┌──────────────────────────────────────┐
│            SudokuGUI.java            │  ← Presentation Layer (Swing UI)
│    (Game window, controls, events)   │
└──────────────────┬───────────────────┘
                   │
┌──────────────────▼───────────────────┐
│          SudokuBoard.java            │  ← Domain Layer (State & Rules)
│  (Board state, user moves, hints)    │
└────────┬─────────────────┬───────────┘
         │                 │
┌────────▼────────┐  ┌─────▼──────────┐
│ SudokuSolver    │  │ PuzzleGenerator │  ← Logic Layer
│ (Backtracking)  │  │ (Generation)    │
└─────────────────┘  └────────────────┘
                   │
┌──────────────────▼───────────────────┐
│          finalWindow.java            │  ← Analytics Layer
│   (Dashboard, charts, history)       │
└──────────────────────────────────────┘
```

---

## 🚀 Getting Started

### Prerequisites

- **Java JDK 8 or higher**
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code with Java extension) or command line

### Running from Command Line

```bash
# Clone the repository
git clone https://github.com/YOUR_USERNAME/Sudoku-Solver-Game.git
cd Sudoku-Solver-Game

# Compile all Java files
javac *.java

# Run the application
java SudokuGUI
```

### Running from an IDE

1. Open your IDE and import the project folder
2. Make sure all 5 `.java` files are in the same package/directory
3. Run `SudokuGUI.java` as the main class

---

## 🎯 How to Play

1. **Generate a Puzzle** — Click Easy, Medium, or Hard to generate a new puzzle
2. **Fill Cells** — Click any empty cell and type a number (1–9)
3. **Use Hints** — Click "Get Hint" to auto-fill the next empty cell with the correct number
4. **Check Your Work** — Click "Check Board" when all cells are filled
5. **Win!** — If correct, the performance dashboard opens with your stats

### Keyboard & Mouse
- Click a cell to focus and highlight its row, column, and box
- Type `1–9` to enter a number; `Backspace` or `Delete` to clear
- Puzzle clue cells (pre-filled, shaded blue) cannot be edited

### Color Guide

| Color | Meaning |
|-------|---------|
| 🔵 Light Blue | Original puzzle clue (read-only) |
| ⬜ White | Empty or valid user-entered cell |
| 🟢 Light Green | Hint-filled cell |
| 🔴 Light Red | Invalid/conflicting number |
| 🟡 Yellow | Highlighted row/column/box |

---

## 📖 Class Descriptions

### `SudokuBoard.java`
Manages the complete board state. Maintains three parallel grids:
- `board[][]` — current numbers (clues + user entries + hints)
- `originalPuzzle[][]` — immutable clue values
- `userFilled[][]` / `hintCells[][]` — tracks cell origin

Key methods: `putNumber()`, `setNumber()`, `putHint()`, `canPlace()`, `checkBoard()`, `isValidBoard()`, `clearUserCell()`, `clearAllUserCells()`

---

### `SudokuSolver.java`
Implements a **recursive backtracking algorithm** to solve any valid Sudoku puzzle.

Key methods:
- `solve(board)` — fills in a solution in-place; returns `true` if solvable
- `canPlace(board, row, col, num)` — validates Sudoku row/column/box constraints
- `getHint(board, row, col)` — returns the correct value for a specific cell
- `countSolutionsLimited(board, limit)` — checks if puzzle has a unique solution (used during generation)

---

### `PuzzleGenerator.java`
Creates random, valid Sudoku puzzles with guaranteed unique solutions.

**Algorithm:**
1. Fill the three diagonal 3×3 boxes with random shuffled numbers (they are independent)
2. Use backtracking to fill remaining cells
3. Remove cells one by one (random order), verifying uniqueness after each removal
4. Stop when the target cell count for the chosen difficulty is reached

| Difficulty | Cells Removed | Empty Cells |
|------------|:------------:|:-----------:|
| 🟢 Easy    | 25           | 25          |
| 🟡 Medium  | 45           | 45          |
| 🔴 Hard    | 57           | 57          |

---

### `SudokuGUI.java`
The main application window built with **Java Swing**. Responsibilities:
- Renders the 9×9 grid as `JTextField` cells
- Handles all user input events (key presses, mouse clicks, focus changes)
- Manages the game timer, hint counter, and mistake counter
- Coordinates between `SudokuBoard`, `SudokuSolver`, and `PuzzleGenerator`
- Launches `finalWindow` upon successful puzzle completion

---

### `finalWindow.java`
A rich post-game **Performance Analytics Dashboard** displayed after solving a puzzle.

Panels & Features:
- **KPI Cards** — time, accuracy, efficiency, hints, mistakes, correct moves
- **Donut Chart** — multi-ring visualization of performance metrics
- **Game History Comparison** — tabbed view with Overview, Detailed Table, Progress Charts, and Advanced Statistics
- **Achievements** — badge system (Perfect Game, Hintless Hero, Accuracy Master, etc.)
- **Generate Report** — saves a formatted `.txt` performance report
- **Export CSV** — exports all game history data
- **Practice Mode** — gives targeted tips based on your weak areas
- **Share Results** — generates a shareable summary text

---

## 🛠️ Technologies Used

<div align="center">

| Technology | Purpose |
|------------ |---------|
| **Java SE 8+** | Core programming language |
| **Java Swing** |  GUI components — JFrame, JPanel, JTextField, JButton |
| **Java AWT** | Custom charts — Graphics2D, Arc2D, BasicStroke |
| **Java Timer** | Live game timer implementation |
| **Java I/O** |  Report generation and CSV export |
| **Java Collections** | Game history and list management |
| **Git** |  Source control |
| **GitHub** | Code hosting |

</div>

---

## 🔧 Key Algorithms

### Backtracking Solver
```
solve(board):
  find first empty cell
  for num in 1..9:
    if canPlace(board, row, col, num):
      place num
      if solve(board): return true
      undo placement (backtrack)
  return false  ← trigger backtrack in caller
```

### Unique Solution Verification
During puzzle generation, `countSolutionsLimited(puzzle, 1)` is called after each cell removal. If more than 1 solution exists, the removal is undone — guaranteeing every generated puzzle has exactly one solution.

---

## 👨‍💻 Authors

Developed as a **Software Construction & Development** course project.

---

## 📄 License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

---

<div align="center">

*Built with ☕ Java and a love for puzzles.*

⭐ If you found this project helpful, please consider giving it a star!

</div>
