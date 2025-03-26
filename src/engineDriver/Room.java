package engineDriver;

public class Room {
  private String roomName;
  private String description;
  private int roomNumber;
  private int N;
  private int S;
  private int E;
  private int W;
  private Puzzle puzzle;
  private Monster monster;
  private Item item;
  private Fixture fixture;
  private Picture picture;

  public Room(String roomName, String roomNumber) {}

  public String getRoomName() {
    return roomName;
  }

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(int roomNumber) {
    this.roomNumber = roomNumber;
  }

  public int getN() {
    return N;
  }

  public void setN(int n) {
    N = n;
  }

  public int getS() {
    return S;
  }

  public void setS(int s) {
    S = s;
  }

  public int getE() {
    return E;
  }

  public void setE(int e) {
    E = e;
  }

  public int getW() {
    return W;
  }

  public void setW(int w) {
    W = w;
  }

  public Puzzle getPuzzle() {
    return puzzle;
  }

  public void setPuzzle(Puzzle puzzle) {
    this.puzzle = puzzle;
  }

  public Monster getMonster() {
    return monster;
  }

  public void setMonster(Monster monster) {
    this.monster = monster;
  }

  public Picture getPicture() {
    return picture;
  }

  public void setPicture(Picture picture) {
    this.picture = picture;
  }

  public Fixture getFixture() {
    return fixture;
  }

  public void setFixture(Fixture fixture) {
    this.fixture = fixture;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }
}
