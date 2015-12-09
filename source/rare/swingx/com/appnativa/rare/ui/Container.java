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

import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.iConstants;
import com.appnativa.rare.ui.painter.RenderSpace;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 *
 * @author Don DeCoteau
 */
public class Container extends Component implements iParentComponent {
  protected RenderSpace renderSpace = RenderSpace.WITHIN_MARGIN;
  private int           minimumHeight;
  private int           minimumWidth;
  private boolean       useMinimumVarsOnlyWhenEmpty;
  private float         enabledAlpha = 1;
  private float         viewAlpha    = 1;

  public Container(JComponent c) {
    super(c);
  }

  protected Container() {}

  @Override
  public void add(iPlatformComponent c) {
    this.add(c, null, -1);
  }

  @Override
  public void add(iPlatformComponent c, int position) {
    this.add(c, null, position);
  }

  @Override
  public void add(iPlatformComponent c, Object constraints) {
    this.add(c, constraints, -1);
  }

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    if (c != null) {
      ((Component) c).parent = this;
    }

    if (view instanceof iLayoutManager) {
      ((iLayoutManager) view).add(c, constraints, position);
    } else {
      ((java.awt.Container) view).add(c.getView());
    }
  }

  @Override
  public iPlatformComponent copy() {
    try {
      return new Container(view.getClass().newInstance());
    } catch(Exception e) {
      throw new ApplicationException(e);
    }
  }

  public boolean doesHeightChangeBasedOnWidth() {
    return true;
  }

  @Override
  public iPlatformComponent getComponentAt(float x, float y, boolean deepest) {
    java.awt.Component c = null;

    if (deepest) {
      c = SwingUtilities.getDeepestComponentAt(getView(), (int) x, (int) y);
    } else {
      c = ((java.awt.Container) view).getComponentAt((int) x, (int) y);
    }

    if (c != null) {
      while(!(c instanceof JComponent)) {
        if (c != null) {
          c = c.getParent();
        }
      }
    }

    if (c instanceof JComponent) {
      return Component.fromView((JComponent) c);
    }

    return null;
  }

  @Override
  public iPlatformComponent getComponentAt(int index) {
    JComponent c = (JComponent) view.getComponent(index);

    return Component.fromView(c);
  }

  @Override
  public Object getComponentConstraints(iPlatformComponent component) {
    if (view instanceof iLayoutManager) {
      return ((iLayoutManager) view).getConstraints(component);
    }

    return component.getClientProperty(iConstants.RARE_CONSTRAINTS_PROPERTY);
  }

  @Override
  public int getComponentCount() {
    return (view == null)
           ? 0
           : ((java.awt.Container) view).getComponentCount();
  }

  @Override
  public void getComponents(List<iPlatformComponent> components) {
    int len = view.getComponentCount();

    for (int i = 0; i < len; i++) {
      components.add(Component.fromView((JComponent) view.getComponent(i)));
    }
  }

  @Override
  public UIInsets getInsets(UIInsets in) {
    iPlatformBorder border = getBorder();

    if (border != null) {
      if (renderSpace == RenderSpace.WITHIN_MARGIN) {
        return border.getBorderInsets(in);
      }

      if (renderSpace == RenderSpace.WITHIN_BORDER) {
        return border.getBorderInsetsEx(in);
      }
    }

    if (in != null) {
      in.set(0, 0, 0, 0);
    } else {
      in = new UIInsets();
    }

    return in;
  }

  @Override
  public UIDimension getMinimumSize(UIDimension size) {
    size = super.getMinimumSize(size);

    if (!useMinimumVarsOnlyWhenEmpty || (isEmpty())) {
      size.width  = Math.max(minimumWidth, size.width);
      size.height = Math.max(minimumHeight, size.height);
    }

    return size;
  }

  @Override
  public UIDimension getPreferredSize(UIDimension size, float maxWidth) {
    size = super.getPreferredSize(size, maxWidth);

    if (!useMinimumVarsOnlyWhenEmpty || (isEmpty())) {
      size.width  = Math.max(minimumWidth, size.width);
      size.height = Math.max(minimumHeight, size.height);
    }

    return size;
  }

  public int indexOf(iPlatformComponent c) {
    if (c != null) {
      int        len = view.getComponentCount();
      JComponent jc  = c.getView();

      for (int i = 0; i < len; i++) {
        if (view.getComponent(i) == jc) {
          return i;
        }
      }
    }

    return -1;
  }

  public void layoutContainer() {
    view.doLayout();
  }

  @Override
  public void remove(iPlatformComponent c) {
    if (view != null) {
      if (c != null) {
        ((Component) c).parent = null;
      }

      if (view instanceof iLayoutManager) {
        ((iLayoutManager) view).remove(c);
      } else if (c != null) {
        ((java.awt.Container) view).remove(c.getView());
      }
    }
  }

  @Override
  public void removeAll() {
    if (view != null) {
      int len = view.getComponentCount();

      for (int i = 0; i < len; i++) {
        java.awt.Component c  = view.getComponent(i);
        Component          pc = (c instanceof JComponent)
                                ? (Component) ((JComponent) c).getClientProperty(RARE_COMPONENT_PROXY_PROPERTY)
                                : null;

        if (pc != null) {
          pc.parent = null;
        }
      }

      if (view instanceof iLayoutManager) {
        ((iLayoutManager) view).removeAll();
      } else {
        ((java.awt.Container) view).removeAll();
      }
    }
  }

  /**
   * Sets the default minimum size for the panel
   *
   * @param width the width
   * @param height the height
   *
   * @param onlyWhenEmpty  true to use these values only when the panel is empty; false otherwise
   */
  public void setDefaultMinimumSize(int width, int height, boolean onlyWhenEmpty) {
    this.minimumWidth                = width;
    this.minimumHeight               = height;
    this.useMinimumVarsOnlyWhenEmpty = onlyWhenEmpty;
  }

  @Override
  public boolean setAlpha(float alpha) {
    viewAlpha = alpha;

    return super.setAlpha(alpha);
  }

  @Override
  public void setEnabled(boolean enabled) {
    if (isEnabled() != enabled) {
      if (enabled) {
        if (enabledAlpha <= 0) {
          enabledAlpha = 1;
        }

        setAlpha(enabledAlpha);
      } else {
        enabledAlpha = viewAlpha;
        super.setAlpha(0.5f * viewAlpha);
      }

      super.setEnabled(enabled);
    }
  }

  protected void addEx(iPlatformComponent c) {
    view.add(c.getView());
  }

  @Override
  protected void disposeEx() {
    removeAll();
  }

  protected boolean isEmpty() {
    return getComponentCount() == 0;
  }
}
