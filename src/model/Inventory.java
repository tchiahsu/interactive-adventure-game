package model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
  private static final int MAX_CAPACITY = 13;
  private int maxCapacity;
  private int currentCapacity;
  private List<Item> items;

  public Inventory() {
    maxCapacity = MAX_CAPACITY;
    currentCapacity = 0;
    items = new ArrayList<>();
  }

  public void addItem(Item item) {
    if (currentCapacity + item.getWeight() > maxCapacity) {
      throw new IllegalArgumentException("Bag is too full to carry this item.");
    }
    items.add(item);
    currentCapacity += item.getWeight();
  }

  public void removeItem(Item item) {
    items.remove(item);
    currentCapacity -= item.getWeight();
  }

  public Item getItem(Item item) {
    return items.get(items.indexOf(item));
  }

  public int getCurrentCapacity() {
    return currentCapacity;
  }

  public void setCurrentCapacity(int currentCapacity) {
    this.currentCapacity = currentCapacity;
  }

  public int getMaxCapacity() {
    return maxCapacity;
  }

  public boolean hasItem(Item item) {
    return items.contains(item);
  }

  @Override
  public String toString() {
    String itemsInInventory = "";

    for (Item item : items) {
      itemsInInventory.concat(item.getName() + " ");
    }

    return itemsInInventory;
  }
}
