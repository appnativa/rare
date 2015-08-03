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

import java.util.ResourceBundle;

/**
 * Terminal constants
 *
 * @author Don DeCoteau
 */
public class iConstants {

  /** mask to determine if the code represents a character of a key code */
  public static int KEYCODE_MASK = 32768;

  /** control key code */
  public static final char KEY_CTRL_A = 1;

  /** control key code */
  public static final char KEY_CTRL_B = 2;

  /** control key code */
  public static final char KEY_CTRL_C = 3;

  /** control key code */
  public static final char KEY_CTRL_D = 4;

  /** control key code */
  public static final char KEY_CTRL_E = 5;

  /** control key code */
  public static final char KEY_CTRL_F = 6;

  /** control key code */
  public static final char KEY_CTRL_G = 7;

  /** control key code */
  public static final char KEY_CTRL_H = 8;

  /** control key code */
  public static final char KEY_CTRL_I = 9;

  /** control key code */
  public static final char KEY_CTRL_J = 10;

  /** control key code */
  public static final char KEY_CTRL_K = 11;

  /** control key code */
  public static final char KEY_CTRL_L = 12;

  /** control key code */
  public static final char KEY_CTRL_M = 13;

  /** control key code */
  public static final char KEY_CTRL_N = 14;

  /** control key code */
  public static final char KEY_CTRL_O = 15;

  /** control key code */
  public static final char KEY_CTRL_P = 16;

  /** control key code */
  public static final char KEY_CTRL_Q = 17;

  /** control key code */
  public static final char KEY_CTRL_R = 18;

  /** control key code */
  public static final char KEY_CTRL_S = 19;

  /** control key code */
  public static final char KEY_CTRL_T = 20;

  /** control key code */
  public static final char KEY_CTRL_U = 21;

  /** control key code */
  public static final char KEY_CTRL_V = 22;

  /** control key code */
  public static final char KEY_CTRL_W = 23;

  /** control key code */
  public static final char KEY_CTRL_X = 24;

  /** control key code */
  public static final char KEY_CTRL_Y = 25;

  /** control key code */
  public static final char KEY_CTRL_Z = 26;

  /** the read terminated because the end of the file was reached */
  public static final int STATUS_EOF = 8;

  /** the read was interrupted by an external signal */
  public static final int STATUS_INTERRUPTED = 2;

  /** the read terminated due to an I/O error */
  public static final int STATUS_IOERROR = 4;

  /**  */
  public static final int STATUS_OK = 0;

  /** the read terminated due to a time out */
  public static final int STATUS_TIMEDOUT = 1;
}
