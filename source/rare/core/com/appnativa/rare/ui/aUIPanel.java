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

import com.appnativa.rare.platform.PlatformHelper;

public abstract class aUIPanel extends XPContainer {
  protected iPanelLayoutManager layoutManager;
  protected String              name;
  protected iPanelPainter       panelPainter;

  protected aUIPanel(Object view) {
    super(view);
  }

  /**
   * Creates a new composite object that can be used during paint
   * operations
   *
   * @param type the type can be a string representing the name of the composite
   *        or an instance of {@link iComposite.CompositeType};
   *        <p>The supported values are:</p>
   *        <ul>
   *          <li>source-atop</li>
   *          <li>source-in</li>
   *          <li>source-out</li>
   *          <li>source-over</li>
   *          <li>destination-atop</li>
   *          <li>destination-in</li>
   *          <li>destination-out</li>
   *          <li>destination-over</li>
   *          <li>lighter</li>
   *          <li>xor</li>
   *          <li>copy</li>
   *        </ul>
   * @param alpha the alpha value for the composite if the rage 0-1.0
   * @return the composite
   *
   * @throws and exception if the type is invalid
   */
  public iComposite createComposite(Object type, float alpha) {
    if (type instanceof iComposite.CompositeType) {
      return new GraphicsComposite((iComposite.CompositeType) type, alpha);
    }

    return PainterUtils.getComposite(type.toString(), alpha);
  }

  /**
   * Creates a new path object that can be used during paint
   * operations
   *
   * @return the path object
   */
  public iPlatformPath createPath() {
    return PlatformHelper.createPath();
  }

  /**
   * Creates a new transform object that can be used during paint
   * operations
   *
   * @return the transform object
   */
  public iTransform createTransform() {
    return new Transform();
  }

  @Override
  public String toString() {
    return "UIPanel." + ((name == null)
                         ? super.toString()
                         : name);
  }

  /**
   * Sets the layout manager to use when the panel needs to be laid out
   * @param lm the layout manager
   */
  public void setLayoutManager(iPanelLayoutManager lm) {
    layoutManager = lm;
  }

  /**
   * Set the name of the panel
   * @param name the name of the panel
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the painter to invoke when the panel needs to be painter
   * @param panelPainter the panel painter
   */
  public void setPanelPainter(iPanelPainter panelPainter) {
    this.panelPainter = panelPainter;
  }

  @Override
  public UIDimension getMinimumSize(UIDimension size) {
    if (size == null) {
      size = new UIDimension();
    }

    if (layoutManager != null) {
      layoutManager.getMinimumSize(size);
    } else {
      super.getMinimumSize(size);
    }

    return size;
  }

  public String getName() {
    return name;
  }

  public iPanelPainter getPanelPainter() {
    return panelPainter;
  }

  public abstract iPlatformComponent getPlatformComponent();

  @Override
  public UIDimension getPreferredSize(UIDimension size, float maxWidth) {
    if (size == null) {
      size = new UIDimension();
    }

    if (layoutManager != null) {
      layoutManager.getPreferredSize(size, maxWidth);
    } else {
      super.getPreferredSize(size, maxWidth);
    }

    return size;
  }

  /**
   * Gets one of the predefined strokes to use during paint
   * operations.
   *
   * @param style the stroke style. Can be "solid", "dotted", or 'dashed"
   * @return the stroke
   */
  public UIStroke getStroke(String style) {
    return UIStroke.getStroke(style);
  }

  public UIPoint getToolTipLocation(int x, int y) {
    return null;
  }

  public CharSequence getToolTipText(int x, int y) {
    return null;
  }

  protected void layout(float width, float height) {
    if (layoutManager != null) {
      layoutManager.layout(this, width, height);
    }
  }

  protected void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    if (panelPainter != null) {
      panelPainter.paint(g, x, y, width, height);
    }
  }

  protected boolean hasToolTips() {
    return false;
  }

  public static interface iPanelLayoutManager {
    void layout(aUIPanel panel, float width, float height);

    void getMinimumSize(UIDimension size);

    void getPreferredSize(UIDimension size, float maxWidth);
  }


  public static interface iPanelPainter {
    void paint(iPlatformGraphics g, float x, float y, float width, float height);
  }
}
