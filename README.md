# Battleship Game - Java Console Implementation

## 📋 Description
A complete console-based Battleship game implemented in Java. This classic naval combat game allows a player to compete against the computer in a strategic battle to sink all opponent's ships.

## 🎮 Features
- **Player vs Computer Gameplay**: Battle against an AI opponent
- **Interactive Console Interface**: Visual boards with coordinate system
- **Manual Ship Placement**: Choose where to position your fleet
- **Random AI Ship Placement**: Computer places ships automatically
- **Real-time Game Status**: Track hits, misses, and ship locations
- **Cross-platform Compatibility**: Works on Windows, macOS, and Linux

## 🚢 Ship Configuration
The game includes the following fleet:
- 1 ship of size 5
- 1 ship of size 4
- 2 ships of size 3
- 1 ship of size 2

## 🎯 How to Play

### Setup Phase
1. The player places ships on their 10x10 grid
2. Ships can be placed horizontally or vertically
3. Ships cannot overlap or go out of bounds
4. The computer randomly places its fleet

### Game Phase
1. Players take turns shooting at coordinates
2. Enter coordinates in format: `LetterNumber` (e.g., `B6`)
   - Letters: A-J (rows)
   - Numbers: 0-9 (columns)
3. Game continues until all ships of one player are sunk

### Board Symbols
- `~` : Water (untouched)
- `B` : Ship (only visible on your board)
- `X` : Hit (successful shot)
- `-` : Miss (shot landed in water)

## 📁 Project Structure

```
Main.java                    # Main game class with all game logic
├── initializeBoard()        # Initialize game boards with water
├── displayBoard()           # Display player and computer boards
├── placePlayerShips()       # Player ship placement
├── placeComputerShips()     # Computer ship placement
├── playerShot()             # Player shooting logic
├── computerShot()           # Computer shooting logic
├── clearScreen()            # Clear console for better UI
├── hasCollision()           # Ship placement validation
└── placeShip()              # Ship placement on board
```

## 🛠️ Requirements
- Java Development Kit (JDK) 8 or higher
- Command line terminal or IDE

## ▶️ How to Run

### Compilation
```bash
javac Main.java
```

### Execution
```bash
java Main
```

## 🎲 Game Rules
1. Each player has a 10x10 grid
2. Ships are placed at the beginning and cannot be moved
3. Players alternate turns
4. A "hit" occurs when a shot lands on an opponent's ship
5. A "miss" occurs when a shot lands in water
6. A ship is sunk when all its cells are hit
7. The first player to sink all opponent's ships wins

## 💡 Tips
- Try to create patterns when guessing ship locations
- Remember your previous shots to avoid wasting turns
- Ships are placed contiguously (no gaps between ship cells)
- The computer uses random shooting strategy

## 🔧 Technical Details
- **Language**: Java
- **Input**: Console-based text input
- **Output**: Formatted ASCII board display
- **Randomization**: Math.random() for computer moves
- **Validation**: Regex patterns for coordinate validation
