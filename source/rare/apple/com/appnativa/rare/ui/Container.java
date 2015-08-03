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

import com.appnativa.rare.platform.apple.ui.view.ParentView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.painter.RenderSpace;
import com.appnativa.util.IdentityArrayList;

import java.util.List;

/**
 *
 * @author Don DeCoteau
 */
public class Container extends Component implements iParentComponent {
  protected IdentityArrayList<iPlatformComponent> components        = new IdentityArrayList<iPlatformComponent>(4);
  private boolean                                 needsInvalidation = true;
  protected RenderSpace                           renderSpace       = RenderSpace.WITHIN_MARGIN;

  public Container(ParentView view) {
    super(view);
  }

  protected Container() {}

  public boolean doesHeightChangeBasedOnWidth() {
    return true;
  }

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
    if (c.getView().getParent() == view) {
      return;
    }

    if (view instanceof iLayoutManager) {
      ((iLayoutManager) view).add(c, constraints, position);
    } else {
      ((ParentView) view).add(position, c.getView());
    }

    ((Component) c).setParent(this);

    if ((position < 0) || (position >= components.size())) {
      components.add(c);
    } else {
      components.add(position, c);
    }

    if (isShowing()) {
      revalidate();
    }
  }

  public int indexOf(iPlatformComponent c) {
    if (c != null) {
      return components.indexOf(c);
    }

    return -1;
  }

  @Override
  public void dispose() {
    int len = components.size();

    if (len > 0) {
      iPlatformComponent[] a = new iPlatformComponent[len];

      a = components.toArray(a);

      for (iPlatformComponent c : a) {
        c.dispose();
      }

      components.clear();
    }

    super.dispose();
  }

  @Override
  public void remove(iPlatformComponent c) {
    if (c instanceof Component) {
      if (view instanceof iLayoutManager) {
        ((iLayoutManager) view).remove(c);
      } else if (c != null) {
        ((ParentView) view).removeChild(c.getView());
      }

      components.remove(c);
      ((Component) c).makeOrphan();

      if (isShowing()) {
        revalidate();
      }
    }
  }

  @Override
  public void removeAll() {
    removeAllEx();

    if (isShowing()) {
      revalidate();
    }
  }

  public void setNeedsHiearachyInvalidated(boolean needs) {
    needsInvalidation = needs;
  }

  @Override
  public iPlatformComponent getComponentAt(int index) {
    return components.get(index);
  }

  @Override
  public iPlatformComponent getComponentAt(float x, float y, boolean deepest) {
    if (view instanceof ParentView) {
      View v = ((ParentView) view).getViewAt(x, y, deepest);

      if (v != null) {
        iPlatformComponent c = Component.findFromView(v);

        return (c == this)
               ? null
               : c;
      }
    }

    return null;
  }

  @Override
  public Object getComponentConstraints(iPlatformComponent component) {
    if (view instanceof iLayoutManager) {
      return ((iLayoutManager) view).getConstraints(component);
    }

    return null;
  }

  @Override
  public int getComponentCount() {
    return components.size();
  }

  @Override
  public void getComponents(List<iPlatformComponent> components) {
    components.addAll(this.components);
  }

  @Override
  public UIInsets getInsets(UIInsets in) {
    iPlatformBorder b = getBorder();

    if (b != null) {
      if (renderSpace == RenderSpace.WITHIN_MARGIN) {
        return b.getBorderInsets(in);
      }

      if (renderSpace == RenderSpace.WITHIN_BORDER) {
        return b.getBorderInsetsEx(in);
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
  public boolean hasChildren() {
    return components.size() > 0;
  }

  protected void addEx(iPlatformComponent c) {
    ((Component) c).setParent(this);
    components.add(c);
  }

  @Override
  protected boolean needsHiearachyInvalidated() {
    return needsInvalidation;
  }

  protected void removeAllEx() {
    if (view instanceof iLayoutManager) {
      ((iLayoutManager) view).removeAll();
    } else {
      ((ParentView) view).removeChildren();
    }

    for (iPlatformComponent c : components) {
      ((Component) c).makeOrphan();
    }

    components.clear();
  }
}
