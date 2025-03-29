package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an inventory of items that a player can carry in the game.
 * The inventory has a maximum capacity and supports adding, removing, and retrieving items.
 */
public class Inventory {
  private static final int MAX_CAPACITY = 13;
  private int maxCapacity;
  private int currentCapacity;
  private List<Item> items;

  /**
   * Constructs a new Inventory instance with the default maximum capacity
   * and an empty list of items.
   * The default maximum capacity is set to 13.
   */
  public Inventory() {
    maxCapacity = MAX_CAPACITY;
    currentCapacity = 0;
    items = new ArrayList<>();
  }

  /**
   * Adds an item to the inventory if the total weight does not exceed the inventory's capacity.
   * @param item the item to be added to the inventory.
   * @throws IllegalArgumentException if the total weight of items exceeds the inventory's capacity.
   */
  public void addItem(Item item) {
    if (currentCapacity + item.getWeight() > maxCapacity) {
      throw new IllegalArgumentException("Bag is too full to carry this item.");
    }
    items.add(item);
    currentCapacity += item.getWeight();
  }

  /**
   * Removes an item from the inventory and adjusts the current capacity.
   * @param item the item to be removed from the inventory.
   */
  public void removeItem(Item item) {
    items.remove(item);
    currentCapacity -= item.getWeight();
  }

  /**
   * Gets a list of all the items currently in the inventory.
   * @return a list containing all items in the inventory.
   */
  public List<Item> getItems() {
    return items;
  }

  /**
   * Retrieves the specified item from the inventory.
   * @param item the item to retrieve.
   */
  public Item getItem(Item item) {
    return items.get(items.indexOf(item));
  }

  /**
   * Gets the current total weight of the items in the inventory.
   * @return the total weight of items currently in the inventory.
   */
  public int getCurrentCapacity() {
    return currentCapacity;
  }

  /**
   * Sets the current capacity of the inventory.
   * @param currentCapacity the current capacity to set.
   */
  public void setCurrentCapacity(int currentCapacity) {
    this.currentCapacity = currentCapacity;
  }

  /**
   * Gets the maximum capacity of the inventory.
   * @return the maximum capacity of the inventory.
   */
  public int getMaxCapacity() {
    return maxCapacity;
  }

  /**
   * Checks if the inventory contains a specified item.
   * @param item the item to check for in the inventory.
   * @return true if the inventory contains the specified item, false otherwise.
   */
  public boolean hasItem(Item item) {
    return items.contains(item);
  }

  /**
   * Provides a string representation of the inventory.
   * If the inventory is empty, it indicates that no items are found.
   * @return a string representing the items in the inventory.
   */
  @Override
  public String toString() {
    String itemsInInventory = "Items in inventory: ";

    if (items.isEmpty()) {
      itemsInInventory = itemsInInventory.concat("No items found.");
      return itemsInInventory;
    }

    for (int i = 0; i < items.size(); i++) {
      itemsInInventory = itemsInInventory.concat(items.get(i).getName());

      if (i != items.size() - 1) {
        itemsInInventory = itemsInInventory.concat(", ");
      }
    }
    return itemsInInventory;
  }
}
