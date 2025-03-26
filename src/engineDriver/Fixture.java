package engineDriver;

public class Fixture {
  private String name;
  private String description;
  private int weight;
  private Puzzle puzzle;
  private boolean state;

  public Fixture(){}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public boolean isState() {
    return state;
  }

  public void setState(boolean state) {
    this.state = state;
  }

  public Puzzle getPuzzle() {
    return puzzle;
  }

  public void setPuzzle(Puzzle puzzle) {
    this.puzzle = puzzle;
  }
}
