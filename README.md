# Interactive Adventure Game

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)


## Overview
Interactive Adventure Game is an object-riented game engine that lets players explore a dynamic world, interactic with items, solve puzzles, and battle monsters. Built using the Model-View-Controller (MVC) architecture, the projects focuses on a clean design, flexibility, and scalability - demonstrating core software engineering principles while being a fun and interactive game.

Welcome to the Adventure Game Engine, an object-oriented design project that lets players explore a virtual world, interact with items, solve puzzles, and battle monsters. This project demonstrates object-oriented principles and provides a platform to build a flexible and interactive adventure game using the MVC (Model-View-Controller) architecture.

## Features
- **Modula MVC architecture** for easy extension and future improvements.
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
   git clone https://github.com/tchiahsu/Interactive-Adventure-Game.git
   cd Interactive-Adventure-Game
   ```
   
2. Run the JAR file
   You should find `Adventure_Game_5004.jar` inside.
   ```
   out/artifacts/Adventure_Game_5004.jar/
   ```
   
3. In the terminal, choose your game mode:
   - Text-Based Mode:
     ```
     java -jar Adventure_Game_5004.jar <absolute-path-to-game-file.json> -text
     ```
   - Graphical Mode:
     ```
     java -jar Adventure_Game_5004.jar <absolute-path-to-game-file.json> -graphics
     ```
   - Batch Mode (Console Output):
     ```
     java -jar Adventure_Game_5004.jar <absolute-path-to-game-file.json> -batch <source-file.txt>
     ```
   - Batch Mode (File Output):
     ```
     java -jar Adventure_Game_5004.jar <absolute-path-to-game-file.json> -text <source-file.txt> <target-file.txt>
     ```
   > **Note:** JSON game files can be found under:
   > ```
   > src/data
   > ```

## Customization

### Using Your Own Images
To add custom images for graphical mode:
1. Place your images inside:
   ```
   src/data/Resources/
   ```
   
2. Update the corresponding JSON game file with your image file names.

3. **Rebuild the JAR**(see next section).

### Rebuilding the JAR
If you modify any resources or code, in IntelliJ, go to:
`Build > Build Artifacts > Adventure_Game_5004.jar > Rebuild`

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
