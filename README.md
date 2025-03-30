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
- **Commands**: Commands follow the Command pattern where each user request is its own stand-alone object.
- **Player Movement**: Players can move in four cardinal directions (North, South, East, West).
- **Room**: Interconnected locations with path for player movement.
- **Inventory**: Players can collect items, which are limited by weight.
- **Monsters**: Monsters affect the Room's environment, player health and block path sometimes but they can be defeated with items.
- **Puzzles**: Room-altering puzzles block movement until solved.

## How has our design changed from Homework 7

The Class diagram from homework 7 has stayed mostly the same. The biggest changes were the addition of the controller class and all the additional classes that go along with it. The GameModel is the class that manages the state of the game, this was the same for our class diagram in homework 7, the controller is the class that tells the model what do based on the users input. To manage the user input we have creates an GameInputReader class and to find the method that would trigger the change of state in the model, we created a GameCommandFinder. These two classes work together to tell the GameController what to execute so that the GameModel changes its state. One design decision that we chose to make was to create a separate class for each game command. This was done with the idea of separation of concern in mind as we didn't want to have one class that trigger every method. We believe that by splitting each command into its own class, it easier to modify behaviour in future implementations.

One big difference in our class diagram is the use of composition. Due to the way we parse the JSON file to extract the class data, we have a lot more composition specially inside our GameModel. This is particularly clear with our GameData and GameInput classes, where GameInput creates the classes based on the JSON data and GameData stores the information so that the model can retried information throughout the game.

One of the biggest design decision that we decided to stick with was treating every object as a string. When we are string to determine whether an item can be used or a monster can be defeated, we use their name (a string) to determine if commands are valid or certain actions can be performed, rather than comparing the actual object. This allowed use to be able to pass strings throughout the game without having to worry about comparing different data types (for example puzzles that have items or string answer). This design decision meant that most of the data representation was done through Strings, List of Strings or Arrays. We believe that working with string was a better decision than working with the classes themselves since it can lead to unexpected behaviour if you aren't keeping good track of the class types. This decision also heightened the important of the GameData class, that mapped the name of the items/monster/puzzle/fixture/etc. to the actual class whenever we needed access to the class. It was a tradeoff that we believe was worth making in order to keep our game more flexible.

Somehting that was new to use was how the interaction with the monster occurs. In our initial implemntation, we assumed that monsters (and puzzles) were unavoidable, meaning that if the player was to find one, they would be force to deactivate them before being able to do anything else in the game. However, through the information provided, we learn that the player has a choice in if the want to continue interacting with the obstacle or move to another room. This was an new implementation idea that was different from our initial thoughts. Also, we have assumed that fixtures were items that were only decorative, but they are instead interactable objects that the player can examine.

For our tests, we don't test the command classes individually since they trigger the exact method in the GameModel. Since we tested the GameModel extensively, we believe that it was not necessary to test the command classes individually since it would result in double testing the same methods.

We believe that our initial class diagram was fairly accurate in trying to capture the general idea of the game. We had to make slight changed to our classes to fit the specifications in homework 8, but where able to keep the general game flow the same. The additon of the controller has added a level of complexity that has made the flow more interesting and forced us to think of how to add these new features while trying to maintain the modularity and flexibility that we mentioned in homework 7. 

## How to Use

1. Clone the repository and open it in IntelliJ.
2. Load your JSON file in the data package or use the current data files.
3. Inovke entry point to initialize and run the game.
4. The game starts by displaying the player's initial room and prompts the user for their first command.
5. The controller reads the user command and tells the model to update the game state accordingly.
