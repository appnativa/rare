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

package com.appnativa.rare.ui.tabpane;

import com.appnativa.rare.ui.Location;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.iActionComponent;
import com.appnativa.rare.ui.iPlatformGraphics;
import com.appnativa.rare.ui.iPlatformPaint;
import com.appnativa.rare.ui.iPlatformPath;
import com.appnativa.rare.ui.painter.PaintBucket;
import com.appnativa.rare.ui.painter.iPainter;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;

import java.util.ArrayList;

public abstract class aBasicTabPainter extends aPlatformTabPainter {
  protected UIRectangle         selectedRect = new UIRectangle();
  protected ArrayList<TabShape> paths;
  protected iTabLabel           selectedLabel;

  public aBasicTabPainter(int cornerSize) {
    super();

    float dp2 = ScreenUtils.platformPixels(2);

    this.cornerSize    = cornerSize;
    this.overlapOffset = -cornerSize;
    textInsets.left    = cornerSize + dp2;
    textInsets.top     = dp2;
    textInsets.bottom  = dp2;
    textInsets.right   = dp2 * 2;
    padding            = (int) Math.ceil(dp2 * 4);
  }

  @Override
  public void dispose() {
    super.dispose();

    if (paths != null) {
      paths.clear();
      paths = null;
    }

    selectedLabel = null;
  }

  @Override
  public void paint(iPlatformGraphics g, float x, float y, float width, float height) {
    selectedLabel = null;
    super.paint(g, x, y + padding, width, height - padding - ScreenUtils.PLATFORM_PIXELS_2);
    paintLine(g, x, y, width, height);

    if (selectedLabel != null) {
      paintSelectedTab(g, selectedLabel, selectedRect.x, selectedRect.y, selectedRect.width, selectedRect.height);
    }
  }

  @Override
  public void setNormalPaint(PaintBucket pb) {
    super.setNormalPaint(pb);

    if (normalComponentPainter != null) {
      normalComponentPainter.setBorder(null);
    }
  }

  @Override
  public void setSelectedPaint(PaintBucket pb) {
    super.setSelectedPaint(pb);
    selectedComponentPainter.setBorder(null);
  }

  protected abstract TabShape createTabShape();

  @Override
  protected void layoutMoreButton(iActionComponent button, float x, float y, float width, float height,
                                  boolean vertical) {
    UIDimension size = new UIDimension();

    button.setIconPosition(IconPosition.LEADING);
    button.getPreferredSize(size);

    if (size.width > width) {
      size.width = width;
    }

    if (size.height > height) {
      size.height = height;
    }

    x = Math.max(0, x + width - size.width - ScreenUtils.platformPixels(8));
    y += Math.max(0, (height - size.height) / 2);
    button.setBounds(x, y, size.width, size.height);
  }

  @Override
  protected void layoutTab(iTabLabel label, float x, float y, float width, float height, int index) {
    if (index == -1) {
      return;
    }

    x += overlapOffset + getStartOffset();

    float pad = getTabsPadding() + ScreenUtils.PLATFORM_PIXELS_1;

    if (getPosition() == Location.BOTTOM) {
      label.setBounds(x, y, width, height - pad);
    } else {
      label.setBounds(x, y + pad, width, height - pad);
    }

    x -= overlapOffset;

    if (paths == null) {
      paths = new ArrayList<TabShape>(tabs.size());
    }

    int plen = paths.size();
    int len  = tabs.size();

    if (len != plen) {
      if (len > plen) {
        while(plen < len) {
          paths.add(createTabShape());
          plen++;
        }
      } else {
        while(plen > len) {
          paths.remove(--plen);
        }
      }

      updatePathRenderer();
    }

    TabShape shape = paths.get(index);

    width += overlapOffset;

    if (index == selectedTab) {
      updateSelectedShape(shape.pathShape, width, height - pad, cornerSize);
      updateSelectedClip(shape.clipShape, width, height - pad, cornerSize);
    } else {
      updateShape(shape.pathShape, width, height - pad, cornerSize);
      updateClip(shape.clipShape, width, height - pad, cornerSize);
    }

    updateShapeAfterLayout(label, shape, x, y, index == selectedTab);
  }

  protected void paintLine(iPlatformGraphics g, float x, float y, float width, float height) {
    g.setColor((selectedTabBorderColor == null)
               ? tabBorderColor
               : selectedTabBorderColor);

    float dp1 = ScreenUtils.PLATFORM_PIXELS_1;

    if (isHorizontal()) {
      g.drawLine(x, y + height - dp1, x + width, y + height - dp1);
    } else {
      g.drawLine(x, y + width - dp1, x + height, y + width - dp1);
    }
  }

  protected void paintSelectedTab(iPlatformGraphics g, iTabLabel tab, float x, float y, float width, float height) {
    TabShape shape = getTabShape(selectedTab);

    if (shape != null) {
      width += overlapOffset;

      iPlatformPaint p = selectedComponentPainter.getPaint(width, height);

      if (p == null) {
        g.saveState();
        g.translate(x, y);
        g.clipShape(shape.clipShape);
        selectedComponentPainter.paint(g, 0, 0, width, height, iPainter.UNKNOWN);
        g.restoreState();
      } else {
        g.setPaint(p);
        g.fillShape(shape.clipShape, x, y);
      }

      g.setColor(selectedTabBorderColor);
      g.drawShape(shape.pathShape, x, y);
    }
  }

  @Override
  protected void paintTab(iPlatformGraphics g, iTabLabel tab, float x, float y, float width, float height, int index) {
    if (index == selectedTab) {
      selectedLabel = tab;
      selectedRect.set(x, y, width, height);

      return;
    }

    TabShape shape = getTabShape(index);

    if (shape != null) {
      width += overlapOffset;

      iPlatformComponentPainter cp = getUnselectedPainter(index);
      iPlatformPaint            p  = null;

      if (cp.getBackgroundPainter() != null) {
        UIRectangle r = shape.pathShape.getBounds();

        p = cp.getPaint(r.width, r.height);
      }

      if (p == null) {
        if (cp.isBackgroundPaintEnabled()) {
          UIRectangle r = shape.pathShape.getBounds();

          g.saveState();
          g.translate(x, y);
          g.clipShape(shape.clipShape);
          cp.paint(g, 0, 0, r.width, r.height, iPainter.UNKNOWN);
          g.restoreState();
        }
      } else {
        g.setPaint(p);
        g.fillShape(shape.pathShape, x, y);
      }

      g.setColor(tabBorderColor);
      g.drawShape(shape.pathShape, x, y);
    }
  }

  @Override
  protected void paintTabs(iPlatformGraphics g, float x, float y, float width, float height, int start, int end,
                           boolean vertical) {
    g.setStrokeWidth(ScreenUtils.platformPixelsf(1.5f));
    super.paintTabs(g, x, y, width, height, start, end, vertical);
  }

  protected void updateClip(iPlatformPath path, float width, float height, float size) {
    updateShape(path, width, height, size);
  }

  protected void updatePathRenderer() {}

  protected void updateSelectedClip(iPlatformPath path, float width, float height, float size) {
    updateSelectedShape(path, width, height + ScreenUtils.PLATFORM_PIXELS_1, size);
  }

  protected void updateSelectedShape(iPlatformPath path, float width, float height, float size) {
    updateShape(path, width, height, size);
  }

  protected void updateShape(iPlatformPath path, float width, float height, float size) {
    path.reset();

    if (size == 0) {
      path.moveTo(0, height);
      path.lineTo(0, 0);
      path.lineTo(width, 0);
      path.lineTo(width, height);
      path.moveTo(width, height);
      path.moveTo(width, height);
    } else {
      path.moveTo(0, height);
      path.lineTo(0, size);
      path.quadTo(0, 0, size, 0);
      path.lineTo(width - size, 0);
      path.quadTo(width, 0, width, size);
      path.lineTo(width, height);
      path.moveTo(width, height);
    }

    path.close();
  }

  protected void updateShapeAfterLayout(iTabLabel label, TabShape shape, float x, float y, boolean selected) {}

  protected TabShape getTabShape(int index) {
    if ((index < 0) || (index >= paths.size())) {
      return null;
    }

    return paths.get(index);
  }

  @Override
  protected boolean isInsideShape(float relX, float relY, float x, float y, float width, float height, int index) {
    TabShape shape = paths.get(index);

    if (location == Location.BOTTOM) {
      relY = Math.max(relY - padding, 0);
    }

    relX -= x;

    return shape.pathShape.isPointInPath(relX, relY);
  }

  protected static class TabShape {
    public iPlatformPath clipShape;
    public iPlatformPath pathShape;

    public TabShape(iPlatformPath pathShape, iPlatformPath clipShape) {
      super();
      this.pathShape = pathShape;
      this.clipShape = clipShape;
    }
  }
}
