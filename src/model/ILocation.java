package model;

public interface ILocation extends IGameObject {
  String getRoomNumber();
  String getNorth();
  String getSouth();
  String getEast();
  String getWest();
  String getPuzzleName();
  String getMonsterName();
  String getItems();
  String getFixtures();
  String getPictureName();
}
