# Adventure Game

## Object-Oriented Design Project
Welcome to the Adventure Game Engine, an object-oriented design project that lets players explore a virtual world, interact with items, solve puzzles, and battle monsters. This project demonstrates object-oriented principles and provides a platform to build a flexible and interactive adventure game using the MVC (Model-View-Controller) architecture.

## Team: TBH
- Chia-hsiang Hsu Tai (Tony)
- Bhoomika Gupta
- Harrison Pham

## Running the Adventure Game

1. Clone the repository to your computer.
2. From the command line, navigate to the project.
3. From the project root (`Adventure_Game`), change the directory to the following path:
    *out/artifacts/Adventure_Game_5004_jar/*<br />
    There should only be one file inside this directory (`Adventure_Game_5004.jar`).
4. Depending on the game mode you want to play with, use one of the following commands to run the game. Make sure to provide the necessary input/output files if the game mode requires them. JSON game files can be found within the *data* directory located inside the *src* directory from the project root.
    - **Text-Based:** `java -jar Adventure_Game_5004.jar <insert gamefile path> -text`
    - **Graphics:** `java -jar Adventure_Game_5004.jar <insert gamefile path> -graphics`
    - **Batch (Console Output):** `java -jar Adventure_Game_5004.jar <insert gamefile path> -batch <source file>`
    - **Batch (File Output):** `java -jar Adventure_Game_5004.jar <insert gamefile path> -batch <source file> <target file>`

**Using your own images**<br />
If you would like to use your own images, place your images inside the *Resources* directory, which is located inside the *data* directory. The *data* directory can be found within the *src* directory from the project root. Please note, your image file names must be included in the JSON game file you wish to play with. Also note, if you include your own images, the JAR file will need to be rebuilt (see the next section for more info).

**Rebuilding the JAR file**<br />
If you changed anything about the program, the JAR file will need to be rebuilt before you run the game. To rebuild the JAR file, select *Build* from the navigation bar in IntelliJ. Scroll down to *Build Artifacts* and select the `Adventure_Game_5004.jar` file. From the menu that opens, select *Rebuild*.
