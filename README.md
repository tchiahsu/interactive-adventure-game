# Interactive Adventure Game

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)


## Overview
Interactive Adventure Game is an object-oriented game engine that lets players explore a dynamic world, interact with items, solve puzzles, and battle monsters. Built using the Model-View-Controller (MVC) architecture, the project focuses on a clean design, flexibility, and scalability - demonstrating core software engineering principles while being a fun and interactive game.

Welcome to the Adventure Game Engine, an object-oriented design project that lets players explore a virtual world, interact with items, solve puzzles, and battle monsters. This project demonstrates object-oriented principles and provides a platform to build a flexible and interactive adventure game using the MVC (Model-View-Controller) architecture.

## Features
- **Modular MVC architecture** for easy extension and future improvements.
- **Graphical and Text-Based Modes** available to play.
- **Customizable Worlds** via JSON configuration files.
- **Support Custom Images** in graphical game mode.

## Running the Adventure Game

### Prerequisites
- Java 17+
- Maven (for building from source)
- IntelliJ IDEA (recommended, but not required)

### Installation
1. Clone the repository
   ```
   git clone https://github.com/tchiahsu/interactive_adventure_game.git
   cd interactive-adventure-game
   ```
   
2. Run the JAR file
   Go to the directory with the `adventure_game.jar` file and run the command:.
   ```
   cd out/artifacts/adventure_game.jar/
   java -jar adventure_game.jar
   ```
   
3. Pick the Map you will Play
   Map game file is stored as a JSON. To view the maps available, starting at the root:
   ```
   cd src/data/
   ```
   
3. Run the Game:
   Make sure to have the absolute path to the gamefile you want to play. In terminal, select run the following command based on the game mode you want to play:
   - Text-Based Mode:
     ```
     java -jar adventure_game.jar <absolute-path-to-game-file.json> -text
     ```
   - Graphical Mode:
     ```
     java -jar adventure_game.jar <absolute-path-to-game-file.json> -graphics
     ```
   - Batch Mode (Console Output):
     ```
     java -jar adventure_game.jar <absolute-path-to-game-file.json> -batch <source-file.txt>
     ```
   - Batch Mode (File Output):
     ```
     java -jar adventure_game.jar <absolute-path-to-game-file.json> -text <source-file.txt> <target-file.txt>
     ```
   > **Note:** JSON game files can be found under:
   > ```
   > src/data
   > ```

## Project Structure
```
Adventure_Game/
├── src/
|   ├── controller/  # Controllers (input handling / batch processing)
|   ├── model/       # Game Model (game logic)
|   ├── view/        # Game View (text and graphical components)
|   ├── data/        # JSON game files and images
├── out/             # Compiled artifacts and JAR files
├── pom.xml          # Maven configuration
└── README.md        # Project documentation
```

## Customization

### Using Your Own Images
To add custom images for graphical mode:
1. Place your images inside:
   ```
   src/data/Resources/
   ```
   
2. Update the corresponding JSON game file with your image file names.

3. **Rebuild the JAR** (see next section).

### Rebuilding the JAR
If you modify any resources or code, in IntelliJ, go to:
`Build > Build Artifacts > Adventure_Game_5004.jar > Rebuild`
