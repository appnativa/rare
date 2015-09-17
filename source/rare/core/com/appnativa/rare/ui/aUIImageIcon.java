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
import com.appnativa.rare.ui.painter.RenderSpace;
import com.appnativa.rare.ui.painter.aUIPainter;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iImagePainter.ScalingType;
import com.appnativa.rare.ui.painter.iPlatformPainter;
import com.appnativa.util.iCancelable;

import java.net.URL;

public abstract class aUIImageIcon extends aPlatformIcon
        implements iBackgroundPainter, Runnable, iImageObserver, iCancelable, Cloneable {
  private static UIImage brokenImage;
  protected String       description = null;

  /** the icon's height */
  protected int iconHeight = -1;

  /** the icon's width */
  protected int         iconWidth   = -1;
  protected ScalingType scalingType = ScalingType.BILINEAR;
  protected RenderType  renderType  = RenderType.UPPER_LEFT;
  protected RenderSpace renderSpace = RenderSpace.WITHIN_BORDER;
  protected boolean     enabled     = true;
  protected Displayed   displayed   = Displayed.ALWAYS;

  /** the background color */
  protected UIColor               backgroundColor;
  protected volatile boolean      canceled;
  protected UIColor               constrainedBackground;
  protected volatile aUIImageIcon delayedIcon;
  protected int                   delayedIconConstraints;

  /** a disabled version of this icon */
  protected aUIImageIcon disabledIcon;

  /**
   * use for non garbage collected environs were a self references causes a
   * retain cycle
   */
  protected boolean           disabledIconIsSelf;
  protected transient UIImage image;
  protected iImageObserver    imageObserver;
  protected Object            linkedData;
  protected boolean           loaded;
  protected volatile URL      location;
  protected Runnable          notifieRunner;
  protected UIRectangle       renderLoc;
  protected String            resourceName;
  protected boolean           runNotifierOnEventThread;
  protected iPlatformBorder   imageBorder;
  private DelayedInfo         delayedInfo;

  /** Creates a new instance of UIImageIcon */
  public aUIImageIcon() {}

  /**
   * Creates an ImageIcon from an image object. If the image has a "comment"
   * property that is a string, then the string is used as the description of
   * this icon.
   *
   * @param image
   *          the image
   */
  public aUIImageIcon(UIImage image) {
    setImage(image);
  }

  /**
   * Creates an ImageIcon from the image.
   *
   * @param image
   *          the image
   * @param description
   *          a brief textual description of the image
   */
  public aUIImageIcon(UIImage image, String description) {
    this(image, description, null);
  }

  @Override
  public String toString() {
    if (resourceName != null) {
      return resourceName;
    }

    if (description != null) {
      return description;
    }

    if (image != null) {
      return image.toString();
    }

    return super.toString();
  }

  /**
   * Creates an ImageIcon from the image.
   *
   * @param image
   *          the image
   * @param description
   *          a brief textual description of the image
   * @param resource
   *          the name of the resource that identifies this icon
   */
  public aUIImageIcon(UIImage image, String description, String resource) {
    setImage(image);
    this.description = description;
    resourceName     = resource;
  }

  /**
   * Creates an ImageIcon from the specified URL. of the image. The deleyedImage
   * will be used until the run() method is invoked to load the icon
   *
   * @param location
   *          the URL for the image
   * @param description
   *          a brief textual description of the image
   * @param delayedIcon
   *          icon to display while waiting for the real image to load
   * @param constraints
   *          delayed icon constraints 0=do nothing; 1=constrain width to
   *          delayed icon width; 2=constrain height to delayed icon height;
   *          3=constrain width and height to fit within delayed icon size (will
   *          maintain proportions) 4=force size to delayed icon size
   */
  public aUIImageIcon(URL location, String description, aUIImageIcon delayedIcon, int constraints) {
    this(null, description);
    this.location               = location;
    this.delayedIcon            = delayedIcon;
    this.delayedIconConstraints = constraints;
  }

  /**
   * Creates an ImageIcon from the specified URL. of the image. The deleyedImage
   * will be used until the run() method is invoked to load the icon. The image
   * will be constrained to fit within delayed icon size with its maintain
   * proportions maintained. Any empty space will be filled with the specified
   * color
   *
   * @param location
   *          the URL for the image
   * @param description
   *          a brief textual description of the image
   * @param delayedIcon
   *          icon to display while waiting for the real image to load
   * @param bg
   *          the background color to use to fill empty space
   */
  public aUIImageIcon(URL location, String description, aUIImageIcon delayedIcon, UIColor bg) {
    this(location, description, delayedIcon, 5);
    this.constrainedBackground = bg;
  }

  @Override
  public iBackgroundPainter alpha(int alpha) {
    return this;
  }

  @Override
  public Object clone() {
    return getCopy(null);
  }

  @Override
  public void dispose() {
    scalingType           = null;
    renderType            = null;
    renderSpace           = null;
    displayed             = null;
    backgroundColor       = null;
    constrainedBackground = null;
    delayedIcon           = null;
    disabledIcon          = null;
    image                 = null;
    imageObserver         = null;
    linkedData            = null;
    location              = null;
    notifieRunner         = null;
  }

  @Override
  public boolean isDisposed() {
    return (image == null) && (location == null);
  }

  @Override
  public void imageLoaded(UIImage image) {
    setImage(image);
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    if ((image != null) && loaded) {
      float              iw = image.getWidth();
      float              ih = image.getHeight();
      float              f  = 0;
      iPlatformComponent c  = g.getComponent();

      if ((c != null) && c.isScaleIcon()) {
        f = c.getIconScaleFactor();
        if(iw>60) {
          f=1;
        }
        float size = Math.min(width, height) * f;

        f  = size / Math.max(iw, ih);
        iw *= f;
        ih *= f;
      }

      if (renderType != RenderType.UPPER_LEFT) {
        renderLoc = aUIPainter.getRenderLocation(g.getComponent(), getRenderSpace(), renderType, x, y, width, height,
                iw, ih, renderLoc);
        x = renderLoc.x;
        y = renderLoc.y;
      }

      boolean cliped = false;

      if (imageBorder != null) {
        if (imageBorder.clipsContents()) {
          g.saveState();
          imageBorder.clip(g, x, y, iw, ih);
          cliped = true;
        }
      }

      if (backgroundColor != null) {
        g.setColor(backgroundColor);
        g.fillRect(x, y, iw, ih);
      }

      if (f == 0) {
        g.drawImage(image, x, y);
      } else {
        g.drawImage(image, x, y, iw, ih);
      }

      if (cliped) {
        g.restoreState();
      }

      if (imageBorder != null) {
        imageBorder.paint(g, x, y, iw, ih, imageBorder.isPaintLast());
      }
    }
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height, int orientation) {
    paint(g, x, y, width, height);
  }

  @Override
  public iPlatformPainter reference() {
    return this;
  }

  /**
   * Scales the icon to the specified size.
   *
   * @param width
   *          the width of the image
   * @param height
   *          the height of the image
   */
  public void scale(int width, int height) {
    UIImage img = getImage();
    if(img.getHeight()!=height && img.getWidth()!=width) {
      this.image = UIImageHelper.scaleImage(img, width, height);
    }
  }

  /**
   * Sets the background color for the painter
   *
   * @param bg
   *          the background color for the painter
   */
  public void setBackgroundColor(UIColor bg) {
    backgroundColor = bg;
  }

  /**
   * The image object that represents a broken image
   *
   * @param image
   *          the broken image
   */
  public static void setBrokenimage(UIImage image) {
    brokenImage = image;
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public void setDisplayed(Displayed displayed) {
    this.displayed = (displayed == null)
                     ? Displayed.ALWAYS
                     : displayed;
  }

  @Override
  public void setDisposable(boolean disposable) {}

  /**
   * @param enabled
   *          the enabled to set
   */
  @Override
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Sets the image displayed by this icon.
   *
   * @param image
   *          the image
   */
  public void setImage(UIImage image) {
    this.image = image;

    if (image != null) {
      iconHeight = image.getHeight();
      iconWidth  = image.getWidth();
      loaded     = image.isLoaded(this);
    }
  }

  public void setImageObserver(iImageObserver io) {
    imageObserver = io;
  }

  /**
   * @param linkedData
   *          the linkedData to set
   */
  public void setLinkedData(Object linkedData) {
    this.linkedData = linkedData;
  }

  /**
   * Sets the a runner that will be called when a delayed icon is loaded
   *
   * @param r
   *          the runnable
   * @param runOnEventThread
   *          true to run the notifier on the event thread; false to run inline
   */
  public void setNotifierRunner(Runnable r, boolean runOnEventThread) {
    this.notifieRunner            = r;
    this.runNotifierOnEventThread = runOnEventThread;
  }

  @Override
  public void setRenderSpace(RenderSpace space) {
    renderSpace = (space == null)
                  ? RenderSpace.COMPONENT
                  : space;
  }

  @Override
  public void setRenderType(RenderType type) {
    this.renderType = (type == null)
                      ? RenderType.TILED
                      : type;
  }

  /**
   * Sets the resource name of this icon
   *
   * @param name
   *          the resource name of this icon
   */
  public void setResourceName(String name) {
    resourceName = name;
  }

  /**
   * Sets the scaling type for the image
   *
   * @param st
   *          the scaling type for the image
   */
  public void setScalingType(ScalingType st) {
    scalingType = st;
  }

  /**
   * Gets the background color for the painter
   *
   * @return the background color for the painter or null
   */
  @Override
  public UIColor getBackgroundColor() {
    return backgroundColor;
  }

  /**
   * The image object that represents a broken image
   *
   * @return the broken image
   */
  public static UIImage getBrokenImage() {
    if (brokenImage == null) {
      try {
        brokenImage = Platform.getAppContext().getResourceAsImage("Rare.icon.broken");
      } catch(Throwable e) {}
    }

    return brokenImage;
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
  public abstract aUIImageIcon getCopy(String description);

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns an icon that is a disabled version of this icon
   *
   * @return an icon that is a disabled version of this icon
   */
  @Override
  public iPlatformIcon getDisabledVersion() {
    if (disabledIconIsSelf) {
      return this;
    }

    if (disabledIcon == null) {
      if (getIconHeight() != -1) {
        aUIImageIcon ic = (aUIImageIcon) PlatformHelper.createDisabledIconIfNeeded(this);

        if (ic == this) {
          disabledIconIsSelf = true;

          return this;
        } else if (ic != null) {
          ic.scalingType        = this.scalingType;
          ic.disabledIconIsSelf = true;

          String s = this.getDescription();

          if (s != null) {
            ic.setDescription(s + " (disabled)");
          }

          disabledIcon = ic;
        }
      }
    }

    return disabledIcon;
  }

  @Override
  public Displayed getDisplayed() {
    return displayed;
  }

  /**
   * Gets the height of the icon
   *
   * @return the height of the icon
   */
  @Override
  public int getIconHeight() {
    iPlatformIcon dl = delayedIcon;

    if (dl != null) {
      return dl.getIconHeight();
    }

    return iconHeight;
  }

  /**
   * Gets the width of the icon
   *
   * @return the width of the icon
   */
  @Override
  public int getIconWidth() {
    iPlatformIcon dl = delayedIcon;

    if (dl != null) {
      return dl.getIconWidth();
    }

    return iconWidth;
  }

  /**
   * Returns this icon's <code>UIImage</code>.
   *
   * @return the image
   */
  public UIImage getImage() {
    return image;
  }

  public iImageObserver getImageObserver() {
    return imageObserver;
  }

  /**
   * @return the linkedData
   */
  public Object getLinkedData() {
    return linkedData;
  }

  /**
   * @return the notifieRunner
   */
  public Runnable getNotifieRunner() {
    return notifieRunner;
  }

  /**
   * Gets a copy of this icon that is scaled proportionally to the specified
   * size
   *
   * @param description
   *          the description of the copy; if null the current description is
   *          used
   *
   * @param width
   *          the new width of the image
   * @param height
   *          the new height of the image
   * @return a scaled copy of the icon
   */
  public aUIImageIcon getProportionallyScaledCopy(int width, int height, String description) {
    aUIImageIcon icon = getCopy(description);
    int          iw   = getIconWidth();
    int          ih   = getIconHeight();

    if ((iw > width) || (ih > height)) {
      if (iw == ih) {
        ih = (int) (((float) width / (float) iw) * ih);
        iw = ih;
      } else if (iw > ih) {
        ih = (int) (((float) width / (float) iw) * ih);
        iw = width;
      } else {
        iw = (int) (((float) height / (float) ih) * iw);
        ih = height;
      }
    }

    icon.scale(iw, ih);

    return icon;
  }

  @Override
  public RenderSpace getRenderSpace() {
    return renderSpace;
  }

  @Override
  public RenderType getRenderType() {
    return renderType;
  }

  /**
   * Gets the resource name for this icon
   *
   * @return the resource name for this icon
   */
  public String getResourceName() {
    return resourceName;
  }

  /**
   * Gets a copy of this icon that is scaled to the specified size
   *
   * @param description
   *          the description of the copy; if null the current description is
   *          used
   *
   * @param width
   *          the new width of the image
   * @param height
   *          the new height of the image
   * @return a scaled copy of the icon
   */
  public aUIImageIcon getScaledCopy(int width, int height, String description) {
    aUIImageIcon icon = getCopy(description);

    icon.scale(width, height);

    return icon;
  }

  /**
   * Gets the scaling type for the image
   *
   * @return the scaling type for the image
   */
  public ScalingType getScalingType() {
    return scalingType;
  }

  /**
   * Returns an icon that is a slice of this icon
   *
   * @param description
   *          the description for the slice
   * @param rect
   *          the rectangular slice
   *
   * @return an icon that is a slice of this icon
   */
  public aUIImageIcon getSlice(String description, UIRectangle rect) {
    if ((rect.width == 0) && (rect.height == 0)) {
      return getSlice(description, (int) rect.x, (int) rect.y);
    } else {
      return getSlice(description, (int) rect.x, (int) rect.y, (int) rect.width, (int) rect.height);
    }
  }

  /**
   * Returns an icon that is a slice of this icon. The icon's image is assumed
   * to be made up of uniform slices
   *
   * @param description
   *          the description for the slice
   * @param pos
   *          the position of the slice
   * @param size
   *          the uniform size of slices
   *
   * @return the slice
   */
  public aUIImageIcon getSlice(String description, int pos, int size) {
    aUIImageIcon ic = getCopy(description);

    ic.setSlice(pos, size);

    return ic;
  }

  /**
   * Returns an icon that is a slice of this icon
   *
   * @param description
   *          the description for the slice
   * @param x
   *          the x-position
   * @param y
   *          the y-position
   * @param width
   *          the width
   * @param height
   *          the height
   *
   * @return an icon that is a slice of this icon
   */
  public aUIImageIcon getSlice(String description, int x, int y, int width, int height) {
    aUIImageIcon ic = getCopy(description);

    ic.setSlice(x, y, width, height);

    return ic;
  }

  /**
   * Returns this icon's <code>UIImage</code>.
   *
   * @return the image
   */
  public UIImage getUIImage() {
    return getImage();
  }

  /**
   * Returns whether the icon represents a broken image
   *
   * @return true if its a broken image; false if it does not or the image has
   *         not yet been loaded
   */
  public boolean isBrokenImage() {
    return loaded && (getImage() == getBrokenImage());
  }

  @Override
  public boolean isCanceled() {
    return canceled;
  }

  @Override
  public boolean isDisposable() {
    return false;
  }

  @Override
  public boolean isDone() {
    return (image == null) ||!isDelayedImage();
  }

  /**
   * @return the enabled
   */
  @Override
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * Checks if the icon image has been loaded
   *
   * @param is
   *          if and image observer has been specified, the observer will be
   *          notified when the image is loaded
   * @return true is the images has been loaded; false otherwise
   */
  public abstract boolean isImageLoaded(iImageObserver is);

  @Override
  public boolean isSingleColorPainter() {
    return false;
  }

  protected void imageWasLoaded() {
    loaded     = true;
    iconHeight = image.getHeight();
    iconWidth  = image.getWidth();

    if (delayedInfo != null) {
      UIRectangle r = delayedInfo.slice;

      if (r != null) {
        setImage(getImage().getSlice((int) r.x, (int) r.y, (int) r.width, (int) r.height));
      } else {
        setImage(Utils.getSlice(getImage(), delayedInfo.slicePosition, delayedInfo.sliceSize));
      }

      delayedInfo = null;
    }
  }

  protected abstract boolean isDelayedImage();

  private void setSlice(int pos, int size) {
    if (!loaded) {
      delayedInfo = new DelayedInfo(pos, size);
    } else {
      setImage(Utils.getSlice(getImage(), pos, size));
    }
  }

  private void setSlice(int x, int y, int width, int height) {
    if (!loaded) {
      delayedInfo = new DelayedInfo(new UIRectangle(x, y, width, height));
    } else {
      setImage(getImage().getSlice(x, y, width, height));
    }
  }

  public iPlatformBorder getImageBorder() {
    return imageBorder;
  }

  public void setImageBorder(iPlatformBorder imageBorder) {
    this.imageBorder = imageBorder;
  }

  protected static class DelayedInfo {
    UIRectangle slice;
    int         slicePosition;
    int         sliceSize;

    public DelayedInfo(UIRectangle slice) {
      super();
      this.slice = slice;
    }

    public DelayedInfo(int slicePosition, int sliceSize) {
      super();
      this.slicePosition = slicePosition;
      this.sliceSize     = sliceSize;
    }
  }
}
