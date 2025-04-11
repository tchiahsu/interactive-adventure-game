package controller;

import java.io.IOException;
import java.util.List;

public interface IViewController {
  void executeCommand(String command) throws IOException;
  List<String> getCurrentState();
  void setPlayerName(String name);
  String[] getCurrentRoomItems();
  String[] getInventoryItems();
  String[] getExaminableObjects();
  String getImagePath(String object);
  String getGameName();
  String getGameSummary();
  ;
}
