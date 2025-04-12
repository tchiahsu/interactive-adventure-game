package eventhandler;

/**
 * This interface extends the IEventHandler to provide additional behavior that is
 * specific to the GUI. This includes tracking the action that is triggering the event.
 */
public interface IGuiEventHandler extends IEventHandler {

  /**
   * Sets the current command to be used by the GUI to determine where the output
   * should be displayed.
   * @param action : command that was executed.
   */
  void setCommandAction(String action);
}
