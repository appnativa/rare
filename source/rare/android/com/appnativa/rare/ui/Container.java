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

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.appnativa.rare.platform.android.ui.view.ViewGroupEx;
import com.appnativa.rare.ui.painter.RenderSpace;

import java.util.List;

/**
 *
 * @author Don DeCoteau
 */
public class Container extends Component implements iParentComponent {
  protected RenderSpace renderSpace = RenderSpace.WITHIN_MARGIN;

  public Container(ViewGroup view) {
    super(view);
  }

  protected Container(Object view) {
    super((View) view);
  }

  protected Container() {}

  public boolean heightChangesBasedOnWidth() {
    return true;
  }

  public void add(iPlatformComponent c) {
    this.add(c, null, -1);
  }

  public void add(iPlatformComponent c, int position) {
    this.add(c, null, position);
  }

  public void add(iPlatformComponent c, Object constraints) {
    this.add(c, constraints, -1);
  }

  public void add(iPlatformComponent c, Object constraints, int position) {
    final View v  = (View) c.getView();
    ViewParent vp = v.getParent();

    if (vp == view) {
      return;
    }

    if (vp instanceof ViewGroup) {
      ((ViewGroup) vp).removeView(v);
    }

    ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) constraints;

    if (params != null) {
      v.setLayoutParams(params);
    }

    if (position != -1) {
      ((ViewGroup) view).addView(v, position);
    } else {
      ((ViewGroup) view).addView(v);
    }
  }

  public void remove(iPlatformComponent c) {
    if (c != null) {
      ((ViewGroup) view).removeView((View) c.getView());
    }
  }

  public int indexOf(iPlatformComponent c) {
    if (c != null) {
      return ((ViewGroup) view).indexOfChild((View) c.getView());
    }

    return -1;
  }

  public void removeAll() {
    if (view != null) {
      ((ViewGroup) view).removeAllViews();
    }
  }

  public iPlatformComponent getComponentAt(int index) {
    View v = (view == null)
             ? null
             : ((ViewGroup) view).getChildAt(index);

    return (v == null)
           ? null
           : Component.fromView(v);
  }

  public UIInsets getInsets(UIInsets in) {
    if (in == null) {
      in = new UIInsets();
    }

    iPlatformBorder b = getBorder();

    if (b != null) {
      if (renderSpace == RenderSpace.WITHIN_MARGIN) {
        return b.getBorderInsets(in);
      }

      if (renderSpace == RenderSpace.WITHIN_BORDER) {
        return b.getBorderInsetsEx(in);
      }
    } else {
      in.set(0, 0, 0, 0);
    }

    return in;
  }

  public iPlatformComponent getComponentAt(float x, float y, boolean deepest) {
    if (!(view instanceof ViewGroup)) {
      return null;
    }

    View               v = ViewGroupEx.getViewtAt((ViewGroup) view, x, y, deepest);
    iPlatformComponent c = (v == null)
                           ? null
                           : Component.findFromView(v);

    return (c == this)
           ? null
           : c;
  }

  public int getComponentCount() {
    return (view == null)
           ? 0
           : ((ViewGroup) view).getChildCount();
  }

  public void getComponents(List<iPlatformComponent> components) {
    int len = getComponentCount();

    if (len == 0) {
      return;
    }

    ViewGroup parent = (ViewGroup) view;

    for (int i = 0; i < len; i++) {
      components.add(Component.fromView(parent.getChildAt(i)));
    }
  }

  public Object getComponentConstraints(iPlatformComponent component) {
    return ((iPlatformComponent) component).getView().getLayoutParams();
  }
}
