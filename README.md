# Adventure Game

## Object-Oriented Design Project
Welcome to the Adventure Game Engine, an object-oriented design project that lets players explore a virtual world, interact with items, solve puzzles, and battle monsters. This project demonstrates object-oriented principles and provides a platform to build a flexible and interactive adventure game using the MVC (Model-View-Controller) architecture.

## Team: TBH
- Chia-hsiang Hsu Tai (Tony)
- Bhoomika Gupta
- Harrison Pham

## Project Overview
The Adventure Game Engine follows an object-oriented design using the MVC architecture, ensuring modularity and separation of concerns. The game mechanics includes player movement, health tracking, item collection, monster battles, and puzzle-solving. The game world consists of interconnected rooms that players can navigate, with various interactions available based on the environment, items, and obstacles.

## MVC Design
- Model (GameModel): Represents the core game logic, including the player, rooms, items, monsters, and puzzles.
- Controller (GameController): Handles user input (e.g., player movement, item interaction) and updates the Model accordingly.
- View (Future Implementation): Displays the game world, player status, and room descriptions to the user.

## Key Design Decisions

- **Command Pattern**: Each valid user action is encapsulated in their own stand-along command class (*ICommand*). This promotes modularity and flexibility making it easier to modify or remap an action to a command wihtout having to deal with other commands. A tradeoff with this pattern is scalability, as the game increases in complexity, the number of commands will increase which will lead to high maintenance overhead.
- **Composition over Inheritance**: Our game implementation relies heavily on composition as opposed to inheritance. There is inheritance, particularly through abstract classes to avoid code duplication in our main game elements (*Monster and Puzzle, Item and Fixture*). Furthermore, since we are reading the data from the game file, composition allows us to build the classes based on the data proivded rather than having to follow a rigid class structure. Furthermore, expanding the game is easier when the design is predominantly built with composition over inheritance, since it makes it easier to implement new features without significantly modifying existing code. 
- **Reliance on Interfaces**: Following our design decision in Homework 7, we decided to continue to use interfaces to guarantee loose coupling. As mentioned before, we do rely on composition, but interfaces remained an important abstraction in our design. We are able to reduce interdependnecies between classes and have a real separation of concern. Each class needs to abide by a contract, and if we wanted to add new parts/features to the game, as long as they follow the contract, the functionality of the game can be kept mostly intact. This is one of the advantages that interfaces provide, and it makes our game more flexible and modular.
- **String-Based Object Reference**: In the brainstorming part of Homework 7, we had intially chosen to pass in objects throughout the game to determine if certain commands could be performed particularly if an item could deactives a Monster/Puzzle. We shifted our idea towards working with their String name rather than the object itself. Homework 8 follows the same idea, where we work and pass around the entity's name as a string, which offer a simpler way of handling and moving data throughout the game. Particularly, it helps avoid type mismatches and since we are working with String over class types, it makes it easier to serialize and deserialize data. This approach meant that we had to create a GameData class to map String names with the actual object in case we needed access to the actual object to check its fields (i.e. getting a Puzzle's solution).
- **Monster/Puzzle Interactions**: Our initial design of the game required Players to deactive a Monster or a Puzzle before being able to run any movement command. The game file and game specification seem to incline that the Player is able to move to different locations, just not in the direction of the obstacle. This meant that Player could choose to interact with the obstacle, but the obstacle will always interact with the player (i.e.  monster deals damage to player).
- **Future View Implementation**: As we examiend the game files, we noticed that there is a lot of information about pictures or images for the adventure game. This is something that is out of scope for our current assignment, but will be useful in a future assignment. We chose to implement these features in the current assignment even though their functionality and behavior isn't tested, making them redundant to our game (for now). They are currently present in our code since we are working towards implementing the View for Homework 9.

## Key Classes and their Importance

These are the main classes that allow our adventure game to function as expected. We don't specify what each game element (Room, Monster, Puzzle, Fixture, etc.) does exaclty. We focus more on the classes the manage functonality in our game.

- **GameController**: Coordinates the input by the user and the game logic. It processes a command, updates the *GameModel*, and ensures that the state of the game changes apporpiately. It works with the *GameInput* and *GameCommandFinder* to transform user input into an executable method.
- **GameInputReader**: Takes input from the user, validates that the input is a valid command and passes the string as a list composed of the action verb and the argument (if there is any).
- **GameCommandFinder**: Uses the action verb to identify the method in the model that should be executed to change the game state accordingly.
- **GameModel**: Manages the state of the game. This includes all the elements present in the game. It responds to the information given by the Controller. 
- **GameInput**: Parses the Game File and intializes all the game entities (in this case: rooms, items, monsters, puzzles, fixtures, etc.).
- **GameData**: Stores all the data related to game objects. It acts as a directory where we use the objects String name to retrieve the actual object.

The first three classes work alongside to perfrom the responsabilities of coordinating the game and the bottom three classes work together to manage the game state. Each class has their own focus but they work together to manage the game flow, which highlights our focus on separating responsabilities and lower coupling concerns.

## High Level Evolution of Our Model

From Homework 7 to Homework 8, the biggest change in our implementation was the introduction of a controller and its associated components (the input reader, the command finder, and the command classes). Our initial UML diagrams focused heavily on composition through interfaces, but while implementing the controller, we introduced helper functions to help parse data, validate input, and other operations. This forced us to shift away from an aggregation relationship and moved towards composition with concerete classes, particularly surrounding *GameInfo*. We also introduced a dependency between the command classes and the game model, given that commands reference methods inside the *GameModel* class.

For the *GameData* class, we initially envisioned storing similar objects in shared lists by using their respective Interfaces. For example, Items and Fixtures would be stored in an *IItem* list whereas Monsters and Puzzles would be stored in an *IObstacle* list. However, during the JSON deserialization, we noticed that due to the structure of the data, we needed separate lists for each individual class, and so the relationship between the GameData class and the game entities is no longer through their Interface but through the concrete class.

Another important change was the implementation of Enums. As we began our implementation, we noticed areas where there was significant hardcoded text, specially when we were dealing with Player health, the game commands and the Player rank. To keep the code more modular, we used Enums which allowed us to store those string in one location and reference them as needed throughout the code. This made our code easier to modify if any of the String messages or Player ranks needed to be changed.

In regards to the model and the game entities, most of our initial design decisions remained the same (outside the design decisions mentioned previously). Our model was based on a lot of assumptions that were mostly correct, which allowed us to keep a lot of our implementation ideas. However, there were a few assumptions that required us to change how we want to approach our implementations. We had assumed that if a Player entered a room with an Obstacle, they could only interact with that Obstacle. Instead, they are allowed to move in any direciton as long as its not in the direction of the Obstacle. We had also assumed that fixtures were purely decorative, but we learned that they can be examined and can have puzzles associated to them. These two were the biggest changes surrounding our game entity interactions.


## Note on Testing

We have an extensive test suite that checks the model works as expected. We want to note that we don't have tests for our command classes. This was done purposely since each command class only has one method, which is the method in the GameModel class that needs to be triggered to change the state of the game. For example, the *LookCommand* will execute the *look()* in the *GameModel* class, but since we are already testing it on the *GameModel* class, we believe that testing the *LookCommand* execute method again would result in repetitive and redundant testing.

## How to Use

1. Clone the repository and open it in IntelliJ.
2. Load your JSON file in the data package or use the current data files available.
3. Inovke entry point to initialize and run the game - We have provided a main function with a pre-defined JSON file.
4. The game starts by displaying the player's initial room and prompts the user for their first command.
