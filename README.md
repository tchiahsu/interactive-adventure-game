# Adventure Game

## Object-Oriented Design Project
Welcome to the Adventure Game Engine, an object-oriented design project that lets players explore a virtual world, interact with items, solve puzzles, and battle monsters. This project demonstrates object-oriented principles and provides a platform to build a flexible and interactive adventure game using the MVC (Model-View-Controller) architecture.

## Team: TBH
- Chia-hsiang Hsu Tai (Tony)
- Bhoomika Gupta
- Harrison Pham

## High Level Evolution of Our Model from Homework 8 to Homework 9

The biggest change from Homework 8 to Homework 9 was the integration of a GUI. Thanks to our earlier focus on separating concerns in Homework 8, particularly aroound the model and the controller, we were able to implement the GUI with minimal changes to the model. Primarily, adding a few methods to support image handling between the Model, the Controller, and the View. This showed that our separation of responsibility between the Controller and the Model was efficient, as we continued to build on top of our prior work. Our design decisions and modularity considerations played an important role in making the addition of the view simpler.

For the GUI design, we used a layered approach inspired by the Decorator Pattern. We created a *JFrame*, and stacked panels on on top of each other to create the final view. Each panel that was added had its own purpose and displayed different types of information, which went hand in hand with our focus on keeping the game architecture modular.

As we were designing our view and its different panels, we realized the benefit of using a Utility Class to manage the methods that we kept on reusing in each panel over and over again. It was important for us to implement this utility class as it helped us avoid repeating code throughout the design of our GUI.

The controller saw the biggest architectural change. In Homework 8, we had one controller class, *GameController*, that implemented the *IController* class. For Homework 9, we initially attempted to continue using just a single controller with logic for both text-based and GUI modes, but it became difficult to manage as the controller ran a text-based game (string input/output) differently than a GUI game (click events). To resolve this difference, we decided to introduce a dedicated GUI controller, *ViewController*, that implements an *IViewController* interface. The different controllers allowed us to correctly manage command *execute* depending on the gamemode that was being played. This was particularly important for the GUI controller, since we had to determine if the output should be a popup or just updates to the existing GUI.

To handle input/output consistently across all the game modes, whether it was text, file, GUI or any combination of the three, we introduced the *IEventHandler* interface. All concrete classes that implement this interface specify how the event handler takes in information (typed, source file, etc) and where the information gets outputted (terminal, target file, etc). The issue with the GUI was that it required more awareness on the type of command being executed to render the output in the correct place. For example, if the command was utilizing an item, then the output had to be shown through a popup, whereas if the command was moving north, the output had to be shown on the description panel. Adding this functionality to the *IEventHandler* interface would have been a viable approach if it were not for violating the Integration Segregation Principle. Instead, we decided to introduce a separate *IGuiEventHandler* interface specifically for the GUI and the methods it needed to operate. This allowed us to preserve modularity and adhere to SOLID principles.

Our last addition was the use of Enums for the *GameEngineApp* class. The Enums represent the different types of game modes in the game. Each game mode gets initialized differently, so to account for the difference, we created multiple constructors in the *GameEngineApp* class to ensure that the right configuration was used based on the selected game mode. 

The *GameEngineApp* signature has also been updated. In Homework 8, we were given a specific constructor signature that the TAs could use to run smoke tests on our Adventure Game. In Homework 9, we have implemented the ability to run the game in multiple modes (console, file, GUI) through our new *IEventHandler* and *IGuiEventHandler* interfaces. Since we now use event handlers to manage input and output operations, we no longer need to pass input and output objects directly to the *GameEngineApp* as specified in Homework 8, changing the signature of the *GameEngineApp* constructor.


## The View and Its Components

As mentioned previously, we used a layered approach similar to the Decorator Pattern where we create *JPanels* and add them on top of the already existing ones. The main classes in our view are detailed below (not all are included):

- **GameView**: This is the main entry point for initializing the GUI. As mentioned, the view is made up of several panels layered on top of each other. This class initalizes the panels and places them on top of one another creating the GUI. Since this class' purpose is to initialize the GUI, it also sets the click events for all the buttons/elements in the game.
- **GameBoard**: Creates the canvas where the game will be located in. It specifies the size, background, title and general properties of the game board.
- **ViewManager**: This class creates the first layer of the GUI. It utilizes the GridBagLayout to create size separate panels where information will be displayed. We utilized this layout so we could make the size of the panels fixed.

There are three panels that display information and two panels that allow the user to execute commands. Below you will find a short description of their content and purpose.

- **DescriptionPanel**: Displays the current game state's textual description.
- **StatusPanel**: Shows the player's health status, actual health, and score.
- **PicturePanel**: Displays the current room's name and an image representing it.
- **InventoryPanel**: Lists items in player inventory and provide commands to interact with those items.
- **NavigationPanel**: Shows the movement keys and some useful commands to interact with the elements in a room.

## Notes on Testing

We have added additional tests to cover the new classes in our game implementation. Some classes are not tested directly because their behaviour is already checked through other components. We believe that testing some of those classes would result in duplicated and redundant tests that do not add any additonal value.

For a lot of the tests involving the Controller and EventHandlers, we relied heavily on dummy classes. The reason behind using dummy classes rather than the actual class was to verify the correct functionality, making sure that the right methods are getting triggered after an event, rather than focusing on the internal behavior of all classes. This allows us to check that the right interactions happen and the flow of the game remains consistent.

We opted out of testing the GUI components. Instead, we have perfomed extensive visual inspection to make sure that the GUI works as expected and runs smoothly. Testing the GUI is quite complex, since it requires simulating click events and checking for the correct textual and graphical updates on the visual side. Given the scope of the project, we believe that a visual inspection is sufficient to conclude that the GUI is working correctly.

We would like to note that the look and feel of the GUI is slightly different depending on the opearting system used. We explored standardizing the appearance regardless of operating system by using the UIManager build-in class, however, we ran into some implementation issues and exceptions. As a result, we decided to design a GUI that stylistically would suit both the Mac and Windows operating system despite them looking a little different.

## Important Notes for Batch Game Mode

For all game modes where the input comes from a source file, we assume that the file will quit the game at some point. That means that the source file must have a line that quits the game.

## Running the Adventure Game

1. Clone the repository to your computer.
2. From the command line, navigate to the project.
3. From the project root (`Adventure_Game`), change the directory to the following path:
    *out/artifacts/Adventure_Game_5004_jar/*<br />
    There should only be one file inside this directory (`Adventure_Game_5004.jar`).
4. Depending on the game mode you want to play with, use one of the following commands to run the game. Make sure to provide the necessary input/output files if the game mode requires them. JSON game files can be found within the data directory located inside the src directory from the project root.
    - **Text-Based:** java -jar Adventure_Game_5004.jar \<insert gamefile path\> -text
    - **Graphics:** java -jar Adventure_Game_5004.jar \<insert gamefile path\> -graphics
    - **Batch (Console Output):** java -jar Adventure_Game_5004.jar \<insert gamefile path\> -batch \<source file\>
    - **Batch (File Output):** java -jar Adventure_Game_5004.jar \<insert gamefile path\> -batch \<source file\> \<target file\>

**Using your own images**<br />
If you would like to use your own images, place your images inside the Resources directory, which is located inside the data directory. The data directory can be found within the src directory from the project root. Please note, your image file names must be included in the JSON game file you wish to play with. Also note, if you include your own images, the JAR file will need to be rebuilt (see the next section for more info)

**Rebuilding the JAR file**<br />
If you changed anything about the program, the JAR file will need to be rebuilt before you run the game. To rebuild the JAR file, select *Build* from the navigation bar in IntelliJ. Scroll down to *Build Artifacts* and select the `Adventure_Game_5004.jar` file. From the menu that opens, select *Rebuild*.

## Shout Out

Special thanks to Soni for her help clarifying the documentation requirements for Homework 9. We have always been aware that our UML is quite large and extensive and especially hard to include in our written documents. We want to thank her for giving us some ideas on how to make it work.

We are also very appreciative of her feedback on all our assignments so far, particularly her comments on how to improve our Sequence Diagrams and the importance of keeping our model flexible and maintainable, especially when working with large projects like this one.
