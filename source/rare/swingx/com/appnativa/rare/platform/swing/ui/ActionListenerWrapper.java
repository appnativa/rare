/*
 * @(#)ActionListenerWrapper.java
 * 
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.ui.event.iActionListener;

public class ActionListenerWrapper implements ActionListener {
  iActionListener actionListener;

  public ActionListenerWrapper(iActionListener actionListener) {
    this.actionListener = actionListener;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    actionListener.actionPerformed(PlatformHelper.createActionEvent(e));
  }
}
