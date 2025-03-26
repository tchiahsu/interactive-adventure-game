package engineDriver;

public class Item {
  private String name;
  private int weight;
  private int maxUses;
  private int usesRemaining;
  private int value;
  private String whenUsed;
  private String description;

  public Item(String name, String description, String whenUsed, int value,
              int usesRemaining, int maxUses, int weight) {
    this.name = name;
    this.description = description;
    this.whenUsed = whenUsed;
    this.value = value;
    this.usesRemaining = usesRemaining;
    this.maxUses = maxUses;
    this.weight = weight;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public int getMaxUses() {
    return maxUses;
  }

  public void setMaxUses(int maxUses) {
    this.maxUses = maxUses;
  }

  public int getUsesRemaining() {
    return usesRemaining;
  }

  public void setUsesRemaining(int usesRemaining) {
    this.usesRemaining = usesRemaining;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public String getWhenUsed() {
    return whenUsed;
  }

  public void setWhenUsed(String whenUsed) {
    this.whenUsed = whenUsed;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
