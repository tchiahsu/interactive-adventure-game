package controller;

import java.io.IOException;

import model.GameModel;
import model.IGameModel;

public class GameController implements IController{
  //private IGameModel model;
  private GameCommandReader dataReader;

  public GameController(/**IGameModel model*/) {
    //this.model = model;
  }

  @Override
  public void go() throws IOException {
    this.dataReader = new GameCommandReader();
    while(this.dataReader.getUserInput()) {
      if (this.dataReader.getVerb().equalsIgnoreCase("N")) {
        System.out.println("You are going North!\n");
      } else if (this.dataReader.getVerb().equalsIgnoreCase("S")) {
        System.out.println("You are going South!\n");
      } else if (this.dataReader.getVerb().equalsIgnoreCase("E")) {
        System.out.println("You are going East!\n");
      } else if (this.dataReader.getVerb().equalsIgnoreCase("W")) {
        System.out.println("You are going West!\n");
      } else if (this.dataReader.getVerb().equalsIgnoreCase("I")) {
        System.out.println("You are checking Inventory!\n");
      } else if (this.dataReader.getVerb().equalsIgnoreCase("L")) {
        System.out.println("You are looking around!\n");
      } else if (this.dataReader.getVerb().equalsIgnoreCase("U")) {
        System.out.println("You are using an item!\n");
      } else if (this.dataReader.getVerb().equalsIgnoreCase("T")) {
        System.out.println("You are taking an item!\n");
      } else if (this.dataReader.getVerb().equalsIgnoreCase("D")) {
        System.out.println("You are dropping an item!\n");
      } else if (this.dataReader.getVerb().equalsIgnoreCase("X")) {
        System.out.println("You are examining an item!\n");
      } else if (this.dataReader.getVerb().equalsIgnoreCase("A")) {
        System.out.println("You are answering a puzzle!\n");
      }
    }
  }

  public static void main(String [] args) throws IOException {
    IController controller = new GameController();
    controller.go();
  }
}
