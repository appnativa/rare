/*
 * @(#)TabPaneComponent.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.tabpane;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.swing.ui.view.BorderLayoutView;
import com.appnativa.rare.platform.swing.ui.view.FrameView;
import com.appnativa.rare.ui.BorderPanel;
import com.appnativa.rare.ui.Container;
import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.layout.BorderLayout;
import com.appnativa.rare.viewer.WindowViewer;
import com.appnativa.rare.widget.iWidget;

public class TabPaneComponent extends aTabPaneComponent {
  Container contentView;
  Container headerView;

  public TabPaneComponent(iWidget context) {
    super(new BorderLayoutView());

    TabStripComponent strip = new TabStripComponent();
    BorderPanel th = new BorderPanel();

    th.add(strip);

    Container content = new Container(new FrameView());

    add(th, Location.TOP);
    add(content, Location.CENTER);

    TabPaneLayout l = new TabPaneLayout(this);

    l.setComponents(this, th, strip, content);
    setLayout(l);
    headerView = th;
    contentView = content;
    view.setFocusTraversalKeysEnabled(false);
    AbstractAction nextTabAction = new AbstractAction("Rare.nextTabAction") {
      @Override
      public void actionPerformed(ActionEvent e) {
        Object o = (e == null) ? null : e.getSource();
        navigateToTab(o, true);
      }
    };

    AbstractAction previousTabAction = new AbstractAction("Rare.previousTabAction") {
      @Override
      public void actionPerformed(ActionEvent e) {
        Object o = (e == null) ? null : e.getSource();
        navigateToTab(o, false);
      }
    };
    KeyStroke ctrlTab = KeyStroke.getKeyStroke("ctrl TAB");
    KeyStroke ctrlShiftTab = KeyStroke.getKeyStroke("ctrl shift TAB");
    InputMap inputMap = view.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    inputMap.put(ctrlTab , nextTabAction.getValue(Action.NAME));
    inputMap.put(ctrlShiftTab, previousTabAction.getValue(Action.NAME));
    ActionMap am = view.getActionMap();
    am.put(nextTabAction.getValue(Action.NAME), nextTabAction);
    am.put(previousTabAction.getValue(Action.NAME), previousTabAction);
  }
  
  @Override
  public void checkOrientation(Object newConfig) {
    if (newConfig == null) {
      WindowViewer w = Platform.getWindowViewer(this);

      if (w != null) {
        newConfig = w.getWidth() > w.getHeight();
      }
    }

    super.checkOrientation(newConfig);
  }

  @Override
  public void dispose() {
    super.dispose();

    if (tabsLayout != null) {
      tabsLayout.dispose();
    }

    headerView = null;
    contentView = null;
  }
 
  protected BorderLayout getLayout() {
    return (BorderLayout) ((BorderLayoutView) view).getLayout();
  }

  @Override
  protected boolean isEmpty() {
    return contentView.getComponentCount() == 0;
  }
}
