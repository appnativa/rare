/*
 * iTerminalCallback.java
 *
 * Created on February 10, 2007, 10:07 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.wimpi.telnetd;

import java.io.IOException;

/**
 *
 * @author Don DeCoteau
 */
public interface iTerminalCallback {
  /**
   * Mutator method to set the active terminal object
   * If the String does not name a terminal we support
   * then the vt100 is the terminal of selection automatically.
   *
   * @param terminalName String that represents common terminal name
   */
  public void setTerminal(String terminalName) throws IOException;

}
