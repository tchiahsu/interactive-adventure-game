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
- Model (GameModel): Represents the core game logic, including the player, rooms, items, monsters, puzzles, and health.
- Controller (GameController): Handles user input (e.g., player movement, item interaction) and updates the Model accordingly.
- View (Future Implementation): Displays the game world, player status, and room descriptions to the user.

## Key Design Decisions

- **Command Pattern**: Each valid user action is encapsulated in their own stand-along command class (ICommand). This promotes modularity and flexibility making it easier to modify or remap an action to a command wihtout having to deal with other commands. A tradeoff with this pattern is scalability, as the game increases in complexity, the number of commands will increase which will lead to high maintenance overhead.
- **Composition over Inheritance**: Our game implementation relies heavily in composition as opposed to inheritance. There is inheritance, particularly through abstract classes to avoid code duplication in our main game elements (Monster and Puzzle, Item and Fixture). Furthermore, since we are reading the data from a JSON file, composition allows us to build the classes based on the data proivded rather than having to follow a rigid class structure. Furthermore, expanding the game is easier when the design is predominantly with composition over inheritance, since it makes it easier to implement new features without significantly modifying existing code.
- **Reliance on Interfaces**: Following our design decision in homework 7, we decided to continue to use interfaces to guarantee loose coupling. As mentioned before, we do rely on composition, but interface remained an important abstraction in our design. We are able to reduce interdependnecies between classes and have a real separation of concern. Each class needs to abide by a contract, and if we wanted to add new parts/features to the game, as long as they abide the contract, the functionality of the game can be kept mostly intact. This is one of the advantages that interfaces provide, and it makes our game more flexible and modular.
- **String-Based Object Reference**: In the brainstorming part of homework 7, we had intially decided to pass in objects throughout the game to determine if certain commands could be performed. We shifted our ideas to working with their String name rather than the object itself. Homework 8 follows the same idea, where we compare each entities name as a string, which offer a simpler way of handling and moving data throughout the game. Particularly, it helps avoid type mismatches. Since we don't different class types being moved around the model, but rather just String, it makes it easier to serialize and deserialize data. This approach meant that we had to create a GameData class to map String names with the actual object in case we needed access to the actual object.
- **Monster/Puzzle Interactions**: Our initial design of the game required Players to deactive a Monster or a Puzzle before being able to run any movement command. The JSON data and game specification seem to incline that the Player is able to move to different locations, just not in the direction of the obstacle. This meant that Player could choose to interact with the obstacle, but the obstacle will always interact with the player (i.e.  monster deals damage to player).
- **Future View Implementation**: As we examiend the JSON files, we noticed that there is a lot of information about pictures or images for the adventure game. This is something that is out of scope for our assignment, but will be useful in a future assignment. We chose to implement these features in the current assignment even though their functionality and behavior isn't tested, making them redundant to our game (for now). They are currently present in our code since we are working towards implementing the View for homework 9.


## How has our design changed from Homework 7

The Class diagram from homework 7 has stayed mostly the same. The biggest changes were the addition of the controller class and all the additional classes that go along with it. The GameModel is the class that manages the state of the game, this was the same for our class diagram in homework 7, the controller is the class that tells the model what do based on the users input. To manage the user input we have creates an GameInputReader class and to find the method that would trigger the change of state in the model, we created a GameCommandFinder. These two classes work together to tell the GameController what to execute so that the GameModel changes its state. One design decision that we chose to make was to create a separate class for each game command. This was done with the idea of separation of concern in mind as we didn't want to have one class that trigger every method. We believe that by splitting each command into its own class, it easier to modify behaviour in future implementations.

One big difference in our class diagram is the use of composition. Due to the way we parse the JSON file to extract the class data, we have a lot more composition specially inside our GameModel. This is particularly clear with our GameData and GameInput classes, where GameInput creates the classes based on the JSON data and GameData stores the information so that the model can retried information throughout the game.

One of the biggest design decision that we decided to stick with was treating every object as a string. When we are string to determine whether an item can be used or a monster can be defeated, we use their name (a string) to determine if commands are valid or certain actions can be performed, rather than comparing the actual object. This allowed use to be able to pass strings throughout the game without having to worry about comparing different data types (for example puzzles that have items or string answer). This design decision meant that most of the data representation was done through Strings, List of Strings or Arrays. We believe that working with string was a better decision than working with the classes themselves since it can lead to unexpected behaviour if you aren't keeping good track of the class types. This decision also heightened the important of the GameData class, that mapped the name of the items/monster/puzzle/fixture/etc. to the actual class whenever we needed access to the class. It was a tradeoff that we believe was worth making in order to keep our game more flexible.

Somehting that was new to use was how the interaction with the monster occurs. In our initial implemntation, we assumed that monsters (and puzzles) were unavoidable, meaning that if the player was to find one, they would be force to deactivate them before being able to do anything else in the game. However, through the information provided, we learn that the player has a choice in if the want to continue interacting with the obstacle or move to another room. This was an new implementation idea that was different from our initial thoughts. Also, we have assumed that fixtures were items that were only decorative, but they are instead interactable objects that the player can examine.

For our tests, we don't test the command classes individually since they trigger the exact method in the GameModel. Since we tested the GameModel extensively, we believe that it was not necessary to test the command classes individually since it would result in double testing the same methods.

We believe that our initial class diagram was fairly accurate in trying to capture the general idea of the game. We had to make slight changed to our classes to fit the specifications in homework 8, but where able to keep the general game flow the same. The additon of the controller has added a level of complexity that has made the flow more interesting and forced us to think of how to add these new features while trying to maintain the modularity and flexibility that we mentioned in homework 7. 

## How to Use

1. Clone the repository and open it in IntelliJ.
2. Load your JSON file in the data package or use the current data files available.
3. Inovke entry point to initialize and run the game.
4. The game starts by displaying the player's initial room and prompts the user for their first command.
