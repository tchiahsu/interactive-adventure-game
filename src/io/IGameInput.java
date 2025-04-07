package io;

import java.io.IOException;

public interface IGameInput {
  String readInput() throws IOException;
  String getAvatarName() throws IOException;
}
