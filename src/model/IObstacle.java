package model;

public interface IObstacle extends IGameObject {

  boolean isActive();
  boolean affectsTarget();
  boolean affectsPlayer();
  String getSolution();
  int getValue();
  String getActiveDescription();
  String getTarget();
  String getPicture();
}
