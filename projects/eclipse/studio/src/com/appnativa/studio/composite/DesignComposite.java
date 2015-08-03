/*
 * @(#)DesignComposite.java
 *
 * Copyright (c) appNativa. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.studio.composite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.wb.swt.swing.EmbeddedSwingComposite;

import com.appnativa.rare.platform.swing.ui.util.Java2DUtils;
import com.appnativa.rare.platform.swing.ui.util.SwingGraphics;
import com.appnativa.studio.DesignPane;

public class DesignComposite extends EmbeddedSwingComposite implements PaintListener {
  boolean                   paint = true;
  Font                      font;
  boolean                   populated;
  private RootPaneContainer rpc;
  private BufferedImage previewBufferedImage;

  public DesignComposite(Composite parent, int style) {
    super(parent, style);

    FontData[] fontData = getFont().getFontData();

    for (int i = 0; i < fontData.length; ++i) {
      fontData[i].setHeight(24);
    }

    font = new Font(Display.getCurrent(), fontData);
    setFont(font);
    addPaintListener(this);
    addListener(SWT.MouseMove|SWT.MouseUp, new Listener() {
      public void handleEvent(Event event) {
        if (rpc != null) {
          DesignPane p = (DesignPane) DesignPane.findPane(rpc.getContentPane());

          if ((p != null) && p.isMotionTracking()) {
            if(event.type==SWT.MouseMove) {
              handleTracking(p, event.x, event.y);
            }
            else {
              handleClick(p, event.x, event.y);
            }
          }
        }
      }
    });
  }

  @Override
  public void dispose() {
    try {
      if (font != null) {
        font.dispose();
        font = null;
      }

      super.dispose();
    } catch(Exception e) {
      e.printStackTrace();
    }
    previewBufferedImage=null;
    rpc = null;
  }

  public void handleTracking(final DesignPane p, final int x, final int y) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        if(rpc==null || rpc.getRootPane()==null) {
          return;
        }
        java.awt.Point pt = SwingUtilities.convertPoint(rpc.getRootPane(), x, y, p);

        p.updateDropTracking(pt, false);
      }
    });
  }

  public void handleClick(final DesignPane p, final int x, final int y) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        if(rpc==null || rpc.getRootPane()==null) {
          return;
        }
        java.awt.Point pt = SwingUtilities.convertPoint(rpc.getRootPane(), x, y, p);
        p.handleSingleMouseClick(pt);
      }
    });
  }

  public void paintControl(PaintEvent e) {
    if (paint) {
      Rectangle r = getBounds();

      e.gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
      e.gc.fillRectangle(r);
      e.gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));

      Point size = e.gc.textExtent("Loading...");

      e.gc.drawString("Loading...", (r.width - size.x) / 2 + r.x, (r.height - size.y) / 2 + r.y, true);
    }
  }
  protected SwingGraphics createPreviewGraphics() {
    Point         p      = getSize();
    BufferedImage img    = previewBufferedImage;
    int           width  =  p.x;
    int           height =  p.y;

    if ((img == null) || (img.getWidth() != width) || (img.getHeight() != height)) {
      img                  = Java2DUtils.createCompatibleImage(width, height);
      previewBufferedImage = img;
    }

    Graphics2D g2 = img.createGraphics();

    g2.clipRect(0, 0, width, height);

    SwingGraphics g = new SwingGraphics(g2);

    g.clearRect(0, 0, width, height);

    return g;
  }
  @Override
  public void populate() {
    if (!populated) {
      populated = true;
      super.populate();
    }
  }
 
  public RootPaneContainer getRootPaneContainer() {
    return rpc;
  }

  @Override
  protected RootPaneContainer addRootPaneContainer(java.awt.Frame frame) {
    rpc = super.addRootPaneContainer(frame);
    return rpc;
  }

  @Override
  protected JComponent createSwingComponent() {
    JPanel p = new JPanel();

    p.setBackground(Color.WHITE);
    paint = false;

    return p;
  }

  protected void handleKeyDown(Event e) {
  }
}
