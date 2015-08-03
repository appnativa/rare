/*
 * @(#)CanvasComponent.java
 * 
 * Copyright (c) SparseWare. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui.canvas;

import java.awt.Dimension;
import java.awt.Graphics;

import com.appnativa.rare.platform.swing.ui.view.JPanelEx;
import com.appnativa.rare.ui.Component;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.widget.iWidget;

public class CanvasComponent extends Component implements iCanvasComponent {
  iContext context;

  public CanvasComponent(iWidget w) {
    super(new CanvasView());
  }

  @Override
  public void setContext(iContext context) {
    this.context = context;
    ((CanvasView) view).setContext(context);
  }

  @Override
  public iContext getContext() {
    return context;
  }

  @Override
  public iPlatformComponent getPlatformComponent() {
    return this;
  }
  
  protected void paintComponent(Graphics g) {}

  static class CanvasView extends JPanelEx {
    private iContext context;

    public CanvasView() {
      super();
    }
    @Override
    public void getPreferredSize(UIDimension size, int maxWidth) {
      if(!isPreferredSizeSet()) {
        size.width=50;
        size.height=50;
      }
      else {
        Dimension d=super.getPreferredSize();
        size.width=d.width;
        size.height=d.height;
      }
      
    }
    
    public void setContext(iContext context) {
      this.context = context;
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      if (context instanceof CanvasRenderingContext2D) {
        ((CanvasRenderingContext2D) context).paint(this, g, true);
      }
    }
  }
}
