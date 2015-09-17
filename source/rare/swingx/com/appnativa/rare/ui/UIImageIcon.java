/*
 * Copyright appNativa Inc. All Rights Reserved.
 *
 * This file is part of the Real-time Application Rendering Engine (RARE).
 *
 * RARE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.appnativa.rare.ui;

import com.appnativa.rare.Platform;
import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.platform.swing.ui.util.ImageHelper.DelayedImage;
import com.appnativa.rare.platform.swing.ui.util.SwingPaint;
import com.appnativa.rare.ui.painter.iPainter;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;

import java.net.URL;

import javax.swing.JComponent;

/**
 * A class representing and image icon
 *
 * @author Don DeCoteau
 */
public class UIImageIcon extends aUIImageIcon {
  SwingPaint               paint;
  private volatile boolean spawned = false;
  private iImageObserver   transientImageObserver;

  public UIImageIcon() {
    super();
  }

  public UIImageIcon(UIImage image) {
    super(image);
  }

  public UIImageIcon(UIImage image, String description) {
    super(image, description);
  }

  public UIImageIcon(UIImage image, String description, String resource) {
    super(image, description, resource);
  }

  public UIImageIcon(URL location, String description, aUIImageIcon delayedIcon, int constraints) {
    super(location, description, delayedIcon, constraints);
  }

  public UIImageIcon(URL location, String description, aUIImageIcon delayedIcon, UIColor bg) {
    super(location, description, delayedIcon, bg);
  }

  @Override
  public void cancel(boolean canInterrupt) {
    if (this.location != null) {
      canceled      = true;
      this.location = null;

      if (canInterrupt) {
        // TODO
      }
    } else if (!loaded) {
      if (this.image instanceof DelayedImage) {
        ((DelayedImage) this.image).cancel(true);
      }
    }
  }

  @Override
  public void dispose() {
    super.dispose();
    transientImageObserver = null;
  }

  /**
   * Checks if the icon loading was initiated
   *
   * @return true is the loading was initiated; false otherwise
   */
  public boolean loadingInitiated() {
    if (spawned) {
      return true;
    }

    if (this.image instanceof DelayedImage) {
      return true;
    }

    return location == null;
  }

  @Override
  public void paint(Component c, Graphics g, boolean hasValue) {
    paint(c, (Graphics2D) g, 0, 0, c.getWidth(), c.getHeight(), hasValue, iPainter.UNKNOWN);
  }

  @Override
  public void paint(Component c, Graphics2D g, int x, int y, int width, int height, boolean hasValue, int orientation) {
    iPlatformComponent pc = com.appnativa.rare.ui.Component.fromView((JComponent) c);
    iPlatformGraphics  pg = pc.graphicsWrap(g);

    paint(pg, x, y, width, height);
    pc.graphicsUnwrap(pg);
  }

  @Override
  public void paintIcon(Component c, Graphics g, int x, int y) {
    iPlatformComponent pc = com.appnativa.rare.ui.Component.fromView((JComponent) c);
    iPlatformGraphics  pg = pc.graphicsWrap(g);

    paint(pg, x, y, c.getWidth(), c.getHeight());
    pc.graphicsUnwrap(pg);
  }

  @Override
  public void run() {
    spawned = true;

    final URL url = location;

    try {
      if (url != null) {
        synchronized(url) {
          canceled = false;

          if (this.location == null) {
            return;    // already loaded
          }

          int width  = (delayedIcon == null)
                       ? 0
                       : delayedIcon.getIconWidth();
          int height = (delayedIcon == null)
                       ? 0
                       : delayedIcon.getIconHeight();

          try {
            UIImage img = PlatformHelper.createImage(url, false, 0);

            if (img != null) {
              if ((width > 0) && (height > 0) && (delayedIconConstraints > 0)) {
                image.contstrain(width, height, delayedIconConstraints, constrainedBackground, scalingType);
              }

              setImage(img);
            }

            delayedIcon = null;
          } catch(Exception e) {
            Platform.ignoreException("failed to load image", e);
          } finally {
            location = null;
            loaded   = true;

            if (getImageObserver() != null) {
              getImageObserver().imageLoaded(getImage());
            }
          }
        }
      }
    } finally {
      Runnable r = notifieRunner;

      if (transientImageObserver != null) {
        try {
          transientImageObserver.imageLoaded(getImage());
        } catch(Exception e) {}
      }

      notifieRunner          = null;
      transientImageObserver = null;

      if (r != null) {
        if (runNotifierOnEventThread) {
          if (Platform.isUIThread()) {
            r.run();
          } else {
            Platform.invokeLater(r);
          }
        } else {
          r.run();
        }
      }
    }
  }

  /**
   * Gets a copy of this icon
   *
   * @param description
   *          the description of the copy; if null the current description is
   *          used
   *
   * @return a copy of the icon
   */
  @Override
  public aUIImageIcon getCopy(String description) {
    if (description == null) {
      description = getDescription();
    }

    UIImageIcon ic = new UIImageIcon(image, description, resourceName);

    ic.location   = this.location;
    ic.linkedData = this.linkedData;

    return ic;
  }

  @Override
  public iPlatformPaint getPaint(float width, float height) {
    if (loaded && (paint == null)) {
      Rectangle2D anchor = new Rectangle2D.Double(0, 0, getIconWidth(), getIconHeight());

      paint = new SwingPaint(new TexturePaint(getImage().getBufferedImage(), anchor));
    }

    return paint;
  }

  /**
   * Checks if the icon image has been loaded
   *
   * @param is
   *          if and image observer has been specified, the observer will be
   *          notified when the image is loaded
   * @return true is the images has been loaded; false otherwise
   */
  @Override
  public boolean isImageLoaded(iImageObserver is) {
    if (location != null) {
      if (is != null) {
        transientImageObserver = is;
      }

      return false;
    }

    if (image instanceof DelayedImage) {
      boolean loaded = ((DelayedImage) image).isLoaded(is);

      if (loaded) {
        imageWasLoaded();
      }

      return loaded;
    } else if (location != null) {
      if (is != null) {
        transientImageObserver = is;
      }

      return false;
    }

    return true;
  }

  @Override
  protected void imageWasLoaded() {
    if (!loaded) {
      if (image instanceof DelayedImage) {
        image = ((DelayedImage) image).getRealImage();

        if (image == null) {
          image = getBrokenImage();
        }
      }

      super.imageWasLoaded();
    }
  }

  @Override
  protected boolean isDelayedImage() {
    return image instanceof DelayedImage;
  }
}
