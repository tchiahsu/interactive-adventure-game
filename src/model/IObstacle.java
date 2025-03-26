package engineDriver;

public interface IObstacle extends IGameObject {

  boolean isActive();
  boolean affectsTarget();
  boolean affectsPlayer();
  String getSolution();
  int getValue();
  String getEffects();
  String getTarget();
  String getPicture();
}
