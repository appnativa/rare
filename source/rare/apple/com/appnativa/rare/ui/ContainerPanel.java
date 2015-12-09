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

import com.appnativa.rare.platform.apple.ui.view.FrameView;
import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.painter.RenderSpace;
import com.appnativa.rare.ui.painter.UIComponentPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

import com.google.j2objc.annotations.Weak;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ContainerPanel extends Container {
  private boolean        borderPanel;
  BorderComponentPainter borderComponentPainter;

  public ContainerPanel() {
    super(new FrameView());
  }

  public ContainerPanel(FrameView fv) {
    super(fv);
  }

  public ContainerPanel(iPlatformComponent c) {
    super(new FrameView());
    add(c);
  }

  @Override
  public void add(iPlatformComponent c, Object constraints, int position) {
    super.add(c, constraints, position);

    if ((borderComponentPainter != null) && (c != null)) {
      borderComponentPainter.setPartner(c.getComponentPainter());
    }
  }

  @Override
  public void remove(iPlatformComponent c) {
    if ((borderComponentPainter != null) && (c != null) && (c.getParent() == this)) {
      borderComponentPainter.setPartner(null);
    }

    super.remove(c);
  }

  @Override
  public void setFocusPainted(boolean focusPainted) {
    super.setFocusPainted(focusPainted);

    if (getComponentCount() > 0) {
      iPlatformComponent c = getComponentAt(0);

      c.setFocusPainted(focusPainted);
    }
  }

  @Override
  public iPlatformComponentPainter getComponentPainter() {
    if (borderPanel && (borderComponentPainter != null)) {
      return borderComponentPainter.getPartner();
    } else {
      return super.getComponentPainter();
    }
  }

  @Override
  public void dispose() {
    if (borderComponentPainter != null) {
      borderComponentPainter.dispose();
      borderComponentPainter = null;
    }
    super.dispose();

    borderPanel = false;
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    if (borderPanel) {
      borderComponentPainter.setPartner(cp);

      if (getComponentCount() > 0) {
        getComponentAt(0).setComponentPainter(cp);
      }
    } else {
      super.setComponentPainter(cp);
    }
  }

  public void setBorderPanel(boolean borderPanel) {
    this.borderPanel = borderPanel;

    if (borderComponentPainter == null) {
      iPlatformComponentPainter cp = null;

      if (getComponentCount() > 0) {
        cp = getComponentAt(0).getComponentPainter();
      }

      borderComponentPainter = new BorderComponentPainter(view, cp);
    }

    ((FrameView) view).setRenderSpace(borderPanel
                                      ? RenderSpace.WITHIN_BORDER
                                      : RenderSpace.WITHIN_MARGIN);
  }

  @Override
  public void setFont(UIFont f) {
    super.setFont(f);

    if (getComponentCount() > 0) {
      getComponentAt(0).setFont(f);
    }
  }

  @Override
  public void setForeground(UIColor fg) {
    super.setForeground(fg);

    if (getComponentCount() > 0) {
      getComponentAt(0).setForeground(fg);
    }
  }

  public void setPadding(UIInsets insets) {
    ((FrameView) view).setPadding(insets);
  }

  public void setRenderType(RenderType renderType) {
    ((FrameView) view).setViewRenderType(renderType);
  }

  public RenderType getRenderType() {
    return ((FrameView) view).getViewRenderType();
  }

  public boolean isBorderPanel() {
    return borderPanel;
  }

  @Override
  protected void getMinimumSizeEx(UIDimension size, float maxWidth) {
    size.setSize(0, 0);

    if (getComponentCount() > 0) {
      iPlatformComponent c = getComponentAt(0);

      if (c.isVisible()) {
        c.getMinimumSize(size,maxWidth);
      }
    }

    UIInsets in = getInsets(null);

    size.width  += in.left + in.right;
    size.height += in.top + in.bottom;
  }

  protected void getPreferredSizeEx(UIDimension size) {
    size.setSize(0, 0);

    if (getComponentCount() > 0) {
      iPlatformComponent c = getComponentAt(0);

      if (c.isVisible()) {
        c.getPreferredSize(size);
      }
    }

    UIInsets in = getInsets(null);

    size.width  += in.left + in.right;
    size.height += in.top + in.bottom;
  }

  public static class BorderComponentPainter extends UIComponentPainter implements PropertyChangeListener {
    iPlatformComponentPainter partner;
    @Weak
    View                      view;

    public BorderComponentPainter(View view, iPlatformComponentPainter cp) {
      this.view = view;
      setPartner(cp);
    }

    public iPlatformComponentPainter getPartner() {
      return partner;
    }

    @Override
    public synchronized void dispose() {
      if (partner != null) {
        partner.removePropertyChangeListener(this);
      }
      partner=null;
      view = null;
      super.dispose();
    }

    public void setPartner(iPlatformComponentPainter cp) {
      if (partner != cp) {
        if (partner != null) {
          partner.removePropertyChangeListener(this);
        }

        partner = cp;
        setBorder((cp == null)
                  ? null
                  : cp.getBorder());

        if (cp != null) {
          cp.addPropertyChangeListener(this);
        }

        modCount++;

        if (view != null) {
          view.setComponentPainter(this);
        }
      }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      if (evt.getPropertyName() == PROPERTY_BORDER) {
        setBorder((iPlatformBorder) evt.getNewValue());
      }
    }
  }
}
