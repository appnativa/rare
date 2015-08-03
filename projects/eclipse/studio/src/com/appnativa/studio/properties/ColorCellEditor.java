/**
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 */

package com.appnativa.studio.properties;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.wb.swt.SWTResourceManager;

import com.appnativa.rare.ui.ColorUtils;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIColorShade;
import com.appnativa.rare.ui.painter.iBackgroundPainter;
import com.appnativa.rare.ui.painter.iGradientPainter;
import com.appnativa.studio.Studio;
import com.appnativa.studio.dialogs.BackgroundColorDialog;
import com.appnativa.studio.dialogs.ColorChooserDialog;

/**
 * A cell editor that manages a color field. The cell editor's value is the
 * color (an SWT <code>RBG</code>).
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 *
 * @noextend This class is not intended to be subclassed by clients.
 */
public class ColorCellEditor extends DialogCellEditor {

  /**
   * The default extent in pixels.
   */
  private static final int DEFAULT_EXTENT = 16;

  /**
   * Gap between between image and text in pixels.
   */
  private static final int GAP      = 6;
  private int              alpha    = 255;
  private boolean          vertical = true;
  private boolean          background;
  private Color            color;

  /**
   * The label widget showing the current color.
   */
  private Label colorLabel;

  /**
   * The composite widget containing the color and RGB label widgets
   */
  private Composite composite;
  private Color     fgColor;

  /**
   * The image.
   */
  private Image image;

  /**
   * The label widget showing the RGB values.
   */
  private Text rgbLabel;

  /**
   * Creates a new color cell editor parented under the given control. The cell
   * editor value is black (<code>RGB(0,0,0)</code>) initially, and has no
   * validator.
   *
   * @param parent
   *          the parent control
   */
  public ColorCellEditor(Composite parent) {
    this(parent, false);
  }

  /**
   * Creates a new color cell editor parented under the given control. The cell
   * editor value is black (<code>RGB(0,0,0)</code>) initially, and has no
   * validator.
   *
   * @param parent
   *          the parent control
   * @since 2.1
   */
  public ColorCellEditor(Composite parent, boolean background) {
    super(parent);
    this.background = background;
  }

  public static Image createColorImage(Image img, Color color, int alpha) {
    return createColorImage(img, null, color, alpha, true);
  }

  public static Image createColorImage(Image img, Color fgColor, Color color, int alpha, boolean vertical) {
    if (img != null) {
      Image     checkered = Studio.getResourceAsIcon("blank_image100.png");
      GC        gc        = new GC(img);
      Rectangle r         = img.getBounds();

      if (checkered != null) {
        gc.drawImage(checkered, 0, 0);
      }

      if (color != null) {
        gc.setBackground(color);
        gc.setAlpha(alpha);

        if (fgColor == null) {
          gc.fillRectangle(r);
        } else {
          gc.setForeground(fgColor);
          gc.fillGradientRectangle(r.x, r.y, r.width, r.height, vertical);
        }
      }

      gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
      gc.drawRectangle(r.x, r.y, r.width - 1, r.height - 1);
      gc.dispose();
    }

    return img;
  }

  /*
   * (non-Javadoc) Method declared on CellEditor.
   */
  public void dispose() {
    if (image != null) {
      image.dispose();
      image = null;
    }

    if (color != null) {
      color.dispose();
      color = null;
    }

    super.dispose();
  }

  /*
   * (non-Javadoc) Method declared on DialogCellEditor.
   */
  protected Control createContents(Composite cell) {
    Color bg = cell.getBackground();

    composite = new Composite(cell, getStyle());
    composite.setBackground(bg);
    composite.setLayout(new ColorCellLayout());
    colorLabel = new Label(composite, SWT.LEFT);
    colorLabel.setBackground(bg);
    rgbLabel = (Text) super.createContents(composite);
    rgbLabel.setBackground(bg);
    rgbLabel.setFont(cell.getFont());

    return composite;
  }

  protected Object openDialogBox(Control cellEditorWindow) {
    if (background) {
      BackgroundColorDialog dialog = new BackgroundColorDialog(cellEditorWindow.getShell(), getColor(getValue()));
      int                   ret    = dialog.open();

      if ((ret == IDialogConstants.OK_ID) || (ret == IDialogConstants.CLIENT_ID)) {
        return dialog.getSelectedColor();
      }
    } else {
      ColorChooserDialog dialog = new ColorChooserDialog(cellEditorWindow.getShell());
      Object             value  = getColor(getValue());

      if (value != null) {
        dialog.setColor((UIColor) value);
      }

      int ret = dialog.open();

      if ((ret == IDialogConstants.OK_ID) || (ret == IDialogConstants.CLIENT_ID)) {
        return dialog.getSelectedColor();
      }
    }

    return getValue();
  }

  /*
   * (non-Javadoc) Method declared on DialogCellEditor.
   */
  protected void updateContents(Object value) {
    UIColor c = getColor(value);

    if (image != null) {
      image.dispose();
    }

    iGradientPainter gp = null;

    if (c instanceof UIColorShade) {
      iBackgroundPainter bp = ((UIColorShade) c).getBackgroundPainter();

      if (bp instanceof iGradientPainter) {
        gp = (iGradientPainter) bp;
      }
    }

    if (c == null) {
      color = null;
    } else if (!equalsColor(c)) {
      if (gp != null) {
        switch(gp.getGradientDirection()) {
          case HORIZONTAL_LEFT :
            vertical = false;
            setGradientColors(gp.getGradientColors(), false);

            break;

          case HORIZONTAL_RIGHT :
            vertical = false;
            setGradientColors(gp.getGradientColors(), true);

            break;

          case VERTICAL_BOTTOM :
            vertical = true;
            setGradientColors(gp.getGradientColors(), true);

            break;

          default :
            vertical = true;
            setGradientColors(gp.getGradientColors(), false);

            break;
        }
      } else {
        color = new Color(Display.getDefault(), c.getRed(), c.getGreen(), c.getBlue());
        alpha = c.getAlpha();
      }
    }

    image = createColorImage(colorLabel.getParent().getParent().getParent());
    colorLabel.setImage(image);
    rgbLabel.setText((c == null)
                     ? ""
                     : c.toString());
  }

  private Image createColorImage(Control w) {
    GC          gc   = new GC(w);
    FontMetrics fm   = gc.getFontMetrics();
    int         size = fm.getAscent();

    gc.dispose();

    int extent = DEFAULT_EXTENT;

    if (w instanceof Table) {
      extent = ((Table) w).getItemHeight() - 3;
    } else if (w instanceof Tree) {
      extent = ((Tree) w).getItemHeight() - 3;
    }

    if (size < extent) {
      size = extent;
    }

    int   width  = size;
    int   height = extent;
    Image image  = new Image(w.getDisplay(), width, height);

    createColorImage(image, fgColor, color, alpha, vertical);

    return image;
  }

  private boolean equalsColor(java.awt.Color c) {
    if ((c == null) || (color == null)) {
      return (c == null) && (color == null);
    }

    if (c.getRed() != color.getRed()) {
      return false;
    }

    if (c.getGreen() != color.getGreen()) {
      return false;
    }

    if (c.getBlue() != color.getBlue()) {
      return false;
    }

    if (c.getAlpha() != alpha) {
      return false;
    }

    return true;
  }

  private void setGradientColors(java.awt.Color[] a, boolean swap) {
    java.awt.Color c1 = null;
    java.awt.Color c2 = null;

    for (java.awt.Color c : a) {
      if (c != null) {
        if (c1 == null) {
          c1 = c;
        } else {
          c2 = c;
        }
      }
    }

    if (swap && (c2 != null)) {
      java.awt.Color c = c1;

      c1 = c2;
      c2 = c;
    }

    if (c2 == null) {
      c2 = c1;
      c1 = null;
    }

    if (c2 != null) {
      color = new Color(Display.getDefault(), c2.getRed(), c2.getGreen(), c2.getBlue());
      alpha = c2.getAlpha();
    } else {
      color = null;
    }

    if (c1 != null) {
      fgColor = new Color(Display.getDefault(), c1.getRed(), c1.getGreen(), c1.getBlue());
      alpha   = Math.max(alpha, c1.getAlpha());
    } else {
      fgColor = null;
    }
  }

  private UIColor getColor(Object value) {
    if (value instanceof String) {
      String s = (String) value;

      s = s.trim();

      if (s.length() == 0) {
        return null;
      }

      if (background) {
        return ColorUtils.getBackgroundColor((String) value, false);
      }

      return ColorUtils.getColor((String) value);
    } else if (value instanceof java.awt.Color) {
      return UIColor.fromColor((java.awt.Color) value);
    }

    return null;
  }

  /**
   * Internal class for laying out this cell editor.
   */
  private class ColorCellLayout extends Layout {
    public Point computeSize(Composite editor, int wHint, int hHint, boolean force) {
      if ((wHint != SWT.DEFAULT) && (hHint != SWT.DEFAULT)) {
        return new Point(wHint, hHint);
      }

      Point colorSize = colorLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);
      Point rgbSize   = rgbLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);

      return new Point(colorSize.x + GAP + rgbSize.x + 6, Math.max(colorSize.y, rgbSize.y));
    }

    public void layout(Composite editor, boolean force) {
      Rectangle bounds    = editor.getClientArea();
      Point     colorSize = colorLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);
      Point     rgbSize   = rgbLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);
      int       ty        = (bounds.height - rgbSize.y) / 2;

      if (ty < 0) {
        ty = 0;
      }

      colorLabel.setBounds(6, (bounds.height - colorSize.y) / 2, colorSize.x, colorSize.y);
      rgbLabel.setBounds(colorSize.x + GAP + 6, ty, bounds.width - colorSize.x - GAP, bounds.height);
    }
  }
}
