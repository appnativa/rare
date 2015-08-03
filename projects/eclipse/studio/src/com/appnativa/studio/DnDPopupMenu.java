/*
 * @(#)DnDPopupMenu.java   2008-05-13
 *
 * Copyright (c) appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.appnativa.rare.spot.Widget;
import com.appnativa.rare.viewer.GroupBoxViewer;
import com.appnativa.rare.viewer.iContainer;
import com.appnativa.rare.viewer.iContainer.Layout;

/**
 *
 * @author decoteaud
 */
public class DnDPopupMenu extends JPopupMenu implements ActionListener {
  private Widget     cfg;
  private JMenuItem  insert;
  private JMenuItem  append;
  private DesignPane pane;
  private JMenuItem  replace;
  private Object     selectedItem;
  private JMenuItem  swap;
  private boolean    tracking;

  public DnDPopupMenu() {}

  public DnDPopupMenu(final DesignPane pane, iContainer parent, Widget cfg, boolean tracking) {
    this.pane     = pane;
    this.cfg      = cfg;
    this.tracking = tracking;
    setBackground(Color.white);

    switch(parent.getWidgetType()) {
      case MenuBar :
      case ToolBar :
        insert = add(Studio.getResourceAsString("Studio.text.insertWidget"));
        insert.addActionListener(this);

        break;
      case GroupBox:
        if(((GroupBoxViewer)parent).getFormLayout()==Layout.FLOW) {
          insert = add(Studio.getResourceAsString("Studio.text.insertWidget"));
          insert.addActionListener(this);
        }
        break;
      case StackPane :
        insert = add(Studio.getResourceAsString("Studio.text.stackPaneInsertWidget"));
        insert.addActionListener(this);
        append = add(Studio.getResourceAsString("Studio.text.stackPaneAppendWidget"));
        append.addActionListener(this);
        break;
        default:
          break;
    }

    swap = add(Studio.getResourceAsString("Studio.text.swapWidgets"));

    if ((cfg == null) && (pane.getTrackingArea().selectionWidget != null) && tracking) {
      swap.addActionListener(this);
    } else {
      swap.setEnabled(false);
    }

    replace = add(Studio.getResourceAsString("Studio.text.replaceWidget"));
    replace.addActionListener(this);
    replace.setForeground(Color.black);
    addSeparator();

    JMenuItem m = add(Studio.getResourceAsString("Rare.text.cancel"));
    m.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        if(pane!=null) {
          pane.repaint();
        }
      }
    });
    m.setForeground(Color.black);
  }

 
  public void actionPerformed(ActionEvent e) {
    selectedItem = e.getSource();
    FormsUtils.handleDropEx(pane, cfg, this, tracking);
  }

  public boolean isInsert() {
    return selectedItem == insert;
  }

  public boolean isReplace() {
    return selectedItem == replace;
  }

  public boolean isAppend() {
    return selectedItem == append;
  }

  public boolean isSwap() {
    return selectedItem == swap;
  }
}
