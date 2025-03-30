# Adventure Game

## Object-Oriented Design Project
Welcome to the Adventure Game Engine, an object-oriented design project that lets players explore a virtual world, interact with items, solve puzzles, and battle monsters. This project demonstrates object-oriented principles and provides a platform to build a flexible and interactive adventure game using the MVC (Model-View-Controller) architecture.

## Team: TBH
- Chia-hsiang Hsu Tai
- Bhoomika Gupta
- Harrison Pham

## Project Overview
The Adventure Game Engine follows an object-oriented design using the MVC architecture, ensuring modularity and separation of concerns. The game mechanics include player movement, health tracking, item collection, monster battles, and puzzle-solving. The game world consists of interconnected rooms that players can navigate, with various interactions available based on the environment, items, and challenges.

## MVC Design
- Model: Represents the core game logic, including the player, rooms, items, monsters, puzzles, and health.
- Controller: Handles user input (e.g., player movement, item interaction) and updates the Model accordingly.
- View: (To be added) Displays the game world, player status, and room descriptions to the user.

## Key Features

- **MVC Architecture**: The project follows a Model-View-Controller architecture, providing a modular and organized structure to separate game logic, user interface, and input handling.
- **Object-Oriented Design**: The project utilizes an object-oriented approach to ensure code modularity, encapsulation, and reusability.
- **Player Movement**: Players can move in four cardinal directions (North, South, East, West).
- **Room**: Interconnected locations with path for player movement.
- **Inventory**: Players can collect items, which are limited by weight.
- **Monsters**: Monsters affect the Room's environment, player health and block path sometimes but they can be defeated with items.
- **Puzzles**: Room-altering puzzles block movement until solved.

## How to Use

1. Clone the repository and open it in IntelliJ.
2. Load your JSON file in the data package or use the current data files.
3. Compile and run the GameEngineApp class to start the game.
4. The game starts by displaying the player's initial room and allows movement between rooms.
5. The controller takes user input to control the avatar's actions and update the game state.
