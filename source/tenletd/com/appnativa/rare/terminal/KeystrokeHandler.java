/*
 * Copyright SparseWare Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appnativa.rare.terminal;

import java.awt.event.KeyEvent;

import java.io.Reader;

/**
 * Telnet keyboard handler
 *
 * @author Don DeCoteau
 */
public class KeystrokeHandler implements Runnable {
  Reader   reader;
  Terminal terminal;

  /**
   * Creates a new instance of KeystrokeHandler
   *
   * @param terminal {@inheritDoc}
   * @param reader {@inheritDoc}
   */
  public KeystrokeHandler(Terminal terminal, Reader reader) {
    this.terminal = terminal;
    this.reader   = reader;
  }

  /**
   * {@inheritDoc}
   */
  public void run() {
    try {
      int  keyCode = 0;
      char keyChar;
      int  modifiers = iConstants.KEYCODE_MASK;

      while((keyCode = reader.read()) != -1) {
        if (keyCode > 0xffff) {
          keyCode -= 0xffff;
          keyChar = 0;
        } else {
          keyChar = (char) (keyCode & 0xffff);
          keyCode = 0;
        }

        if (keyCode == 0) {
          if (keyChar < 27) {
            if (keyChar != 0) {
              terminal.keyboardWrite(0, keyChar);
            }
          } else {
            terminal.keyboardWrite(0, keyChar);
          }
        } else {
          switch(keyCode) {
            case KeyEvent.VK_UNDEFINED :
              break;

            case KeyEvent.VK_PAUSE :
              terminal.togglePause();

              break;

            case KeyEvent.VK_F1 :
            case KeyEvent.VK_F2 :
            case KeyEvent.VK_F3 :
            case KeyEvent.VK_F4 :
            case KeyEvent.VK_F5 :
            case KeyEvent.VK_F6 :
            case KeyEvent.VK_F7 :
            case KeyEvent.VK_F8 :
            case KeyEvent.VK_F9 :
            case KeyEvent.VK_F10 :
            case KeyEvent.VK_F11 :
            case KeyEvent.VK_F12 :
              break;

            default :
              terminal.keyboardWrite(modifiers, (char) keyCode);

              break;
          }
        }
      }
    } catch(Exception ex) {}

    terminal.interruptReader();
  }
}
