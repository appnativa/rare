/*
 * @(#)HostingWindow.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JLayeredPane;
import javax.swing.JRootPane;
import javax.swing.RootPaneContainer;

import com.appnativa.rare.iPlatformAppContext;
import com.appnativa.rare.platform.swing.ui.view.JRootPaneEx;
import com.appnativa.rare.ui.Frame;
import com.appnativa.rare.ui.iPlatformMenuBar;
import com.appnativa.rare.ui.iStatusBar;
import com.appnativa.rare.ui.iToolBarHolder;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.appnativa.studio.composite.DesignComposite;

public class HostingFrame extends Frame {
  public HostingFrame(iPlatformAppContext app, DesignComposite dc) {
    super(app, dc.getFrame(), new RootPaneHolder());//.getRootPaneContainer());
    dc.getRootPaneContainer().getContentPane().removeAll();
    disposeOfNativeWindow = false;
    dc.getRootPaneContainer().getContentPane().add(getRootPaneContainer().getRootPane());
  }
  private boolean forPreview;
  @Override
  public void disposeEx() {
    if (target != null) {
      target.dispose(true);
      JRootPaneEx rp=(JRootPaneEx) getRootPaneContainer().getRootPane();
      rp.disposeOfPane();
    }

    super.disposeEx();
  }

  @Override
  public void pack() {}

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    if(forPreview) {
      super.setComponentPainter(cp);
    }
  }

  @Override
  public iPlatformMenuBar setMenuBar(iPlatformMenuBar mb) {
    if(forPreview) {
      return super.setMenuBar(mb);
    }
    return null;
  }

  @Override
  public iStatusBar setStatusBar(iStatusBar sb) {
    if(forPreview) {
      return super.setStatusBar(sb);
    }
    return null;
  }

  @Override
  public iToolBarHolder setToolBarHolder(iToolBarHolder tbh) {
    if(forPreview) {
      return super.setToolBarHolder(tbh);
    }
    return null;
  }
  
  public boolean isForPreview() {
    return forPreview;
  }

  public void setForPreview(boolean forPreview) {
    this.forPreview = forPreview;
  }
  static class RootPaneHolder implements RootPaneContainer {
    JRootPaneEx rootpane=new JRootPaneEx();

    public Container getContentPane() {
      return rootpane.getContentPane();
    }

    public void setContentPane(Container content) {
      rootpane.setContentPane(content);
    }

    public void setLayeredPane(JLayeredPane layered) {
      rootpane.setLayeredPane(layered);
    }

    public JLayeredPane getLayeredPane() {
      return rootpane.getLayeredPane();
    }

    public void setGlassPane(Component glass) {
      rootpane.setGlassPane(glass);
    }

    public Component getGlassPane() {
      return rootpane.getGlassPane();
    }

    @Override
    public JRootPane getRootPane() {
      return rootpane;
    }
    
  }
}
