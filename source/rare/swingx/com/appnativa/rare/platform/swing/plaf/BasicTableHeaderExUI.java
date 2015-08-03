/*
 * @(#)BasicTableHeaderExUI.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.plaf;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableHeaderUI;

public class BasicTableHeaderExUI extends BasicTableHeaderUI {
  public BasicTableHeaderExUI() {
  }

  public static ComponentUI createUI(JComponent h) {
    return new BasicTableHeaderExUI();
  }
}
