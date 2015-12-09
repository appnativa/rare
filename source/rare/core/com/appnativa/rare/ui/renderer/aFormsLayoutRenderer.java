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

package com.appnativa.rare.ui.renderer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.appnativa.rare.exception.ApplicationException;
import com.appnativa.rare.ui.Column;
import com.appnativa.rare.ui.FormsPanel;
import com.appnativa.rare.ui.ItemModelMap;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.RenderableDataItem.IconPosition;
import com.appnativa.rare.ui.RenderableDataItem.Orientation;
import com.appnativa.rare.ui.RenderableDataItem.VerticalAlign;
import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.iPlatformBorder;
import com.appnativa.rare.ui.iPlatformComponent;
import com.appnativa.rare.ui.iPlatformIcon;
import com.appnativa.rare.ui.iPlatformListDataModel;
import com.appnativa.rare.ui.iPlatformRenderingComponent;
import com.appnativa.rare.ui.border.UICompoundBorder;
import com.appnativa.rare.ui.painter.iPlatformComponentPainter;
import com.google.j2objc.annotations.Weak;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormLayout.LayoutInfo;

public abstract class aFormsLayoutRenderer implements iPlatformRenderingComponent {
  protected final String                             LAYOUT_MAP_KEY      = "aFormsLayoutRenderer.layoutMap";
  protected Map<String, iPlatformRenderingComponent> renderingComponents = new HashMap<String,
                                                                             iPlatformRenderingComponent>();
  protected StringBuilder     stringBuilder = new StringBuilder(10);
  UIDimension                 size          = new UIDimension();
  protected boolean           useLayoutMap  = true;
  protected String            columns;
  protected UICompoundBorder  compoundBorder;
  protected FormsPanelEx       formsPanel;
  protected UIInsets          insets;
  protected aListItemRenderer liRenderer;
  protected ItemModelMap      modelMap;
  protected String            rows;
  protected int columnWidth;
  public aFormsLayoutRenderer(FormsPanelEx panel) {
    formsPanel = panel;
    panel.renderer=this;
    liRenderer = createListItemRenderer();
  }

  /**
   * Adds a new component that will be used for rendering
   *
   * @param c the component that will be used for rendering
   * @param cellConstraints the cell constraints for the renderer
   *
   */
  public void addComponent(iPlatformComponent c, String cellConstraints) {
    CellConstraints cc = new CellConstraints(cellConstraints);

    formsPanel.add(c, cc);
    renderingComponents.put(cellConstraints, createComponentRenderer(c));
  }

  /**
   * Adds a new label renderer
   *
   * @param cellConstraints the cell constraints for the renderer
   *
   * @return the rendering component
   */
  public iPlatformRenderingComponent addLabelRenderer(String cellConstraints) {
    iPlatformRenderingComponent rc;

    addRenderingComponent(rc = createLabelRenderer(), cellConstraints);
    rc.setWordWrap(true);

    return rc;
  }

  /**
   * Adds a new renderer
   *
   * @param className the class name for the renderer. The name is the same as would be used
   *                   when specifying the name of a renderer in a markup.
   * @param cellConstraints the cell constraints for the renderer
   * @return the new rendering component
   */
  public iPlatformRenderingComponent addRendererComponent(String className, String cellConstraints) {
    try {
      iPlatformRenderingComponent rc = createRenderer(className);

      addRenderingComponent(rc, cellConstraints);

      return rc;
    } catch(Exception ex) {
      throw ApplicationException.runtimeException(ex);
    }
  }

  public void addRenderingComponent(iPlatformRenderingComponent rc, String cellConstraints) {
    CellConstraints cc = new CellConstraints(cellConstraints);

    formsPanel.add(rc.getComponent(), cc);
    renderingComponents.put(cellConstraints, rc);
  }

  @Override
  public void clearRenderer() {
    setComponentPainter(null);
    setBackground(null);
    setBorder(null);
    setIcon(null);
  }

  public void dataModelChanged(iPlatformListDataModel model) {
    if (useLayoutMap) {
      if (modelMap != null) {
        modelMap.remodel(model);
      } else {
        modelMap = new ItemModelMap(model);
      }
    }
  }

  @Override
  public void dispose() {
    if (liRenderer != null) {
      liRenderer.dispose();
    }

    if (formsPanel != null) {
      formsPanel.dispose();
    }

    if (renderingComponents != null) {
      renderingComponents.clear();
    }

    if (modelMap != null) {
      modelMap.dispose();
    }

    formsPanel     = null;
    liRenderer     = null;
    stringBuilder  = null;
    compoundBorder = null;
  }

  public iPlatformRenderingComponent setupNewCopy(aFormsLayoutRenderer flr) {
    flr.useLayoutMap = useLayoutMap;
    flr.modelMap     = modelMap;

    Iterator  it = renderingComponents.entrySet().iterator();
    Map.Entry e;

    while(it.hasNext()) {
      e = (Entry) it.next();

      String                      key = (String) e.getKey();
      iPlatformRenderingComponent rc  = ((iPlatformRenderingComponent) e.getValue());
      CellConstraints             cc  = (CellConstraints) formsPanel.getCellConstraints(rc.getComponent()).clone();

      rc = rc.newCopy();
      flr.renderingComponents.put(key, rc);
      flr.formsPanel.add(rc.getComponent(), cc);
    }
    flr.columnWidth=columnWidth;
    return flr;
  }

  @Override
  public void setAlignment(HorizontalAlign ha, VerticalAlign va) {}

  @Override
  public void setBackground(UIColor bg) {
    formsPanel.setBackground(bg);
  }

  @Override
  public void setBorder(iPlatformBorder b) {
    formsPanel.setBorder(b);
  }

  @Override
  public void setComponentPainter(iPlatformComponentPainter cp) {
    formsPanel.setComponentPainter(cp);
  }

  @Override
  public void setEnabled(boolean enabled) {
    formsPanel.setEnabled(enabled);
  }

  @Override
  public void setFont(UIFont font) {
    formsPanel.setFont(font);
  }

  @Override
  public void setForeground(UIColor fg) {
    formsPanel.setForeground(fg);
  }

  @Override
  public void setIcon(iPlatformIcon icon) {}

  @Override
  public void setIconPosition(IconPosition position) {}

  public void setMargin(int top, int right, int bottom, int left) {
    setMargin(new UIInsets(top, right, bottom, left));
  }

  @Override
  public void setMargin(UIInsets insets) {
    formsPanel.setMargin(insets);
  }
  
  /**
   * Sets rendering options.
   * Creates the layout from the rows and columns values in the options map
   *
   * @param options the rendering option
   */
  @Override
  public void setOptions(Map<String, Object> options) {
    if (options == null) {
      return;
    }

    rows    = (String) options.get("rows");
    columns = (String) options.get("columns");
    formsPanel.setLayout(columns, rows);
  }

  @Override
  public void setOrientation(Orientation o) {}

  @Override
  public void setScaleIcon(boolean scale, float scaleFactor) {}

  @Override
  public void setWordWrap(boolean wrap) {}

   @Override
  public void setColumnWidth(int width) {
     columnWidth=width;
  }
   
  /**
   * Gets the Cell constraints for a rendering component
   * @param rc the rendering component
   * @return the Cell constraints for a rendering component
   */
  public CellConstraints getCellConstraints(iPlatformRenderingComponent rc) {
    return formsPanel.getCellConstraints(rc.getComponent());
  }

  @Override
  public iPlatformComponent getComponent() {
    return formsPanel;
  }

  @Override
  public iPlatformComponent getComponent(CharSequence value, RenderableDataItem item) {
    return getComponent(formsPanel, value, item, 0, false, false, null, null, false);
  }

  @Override
  public iPlatformComponent getComponent(iPlatformComponent list, Object value, RenderableDataItem item, int row,
          boolean isSelected, boolean hasFocus, Column col, RenderableDataItem rowItem, boolean handleAll) {
    if (item != null) {
      iPlatformComponent pc = item.getRenderingComponent();

      if (pc != null) {
        return pc;
      }

      LayoutMap layoutMap = null;

      if (modelMap != null) {
        layoutMap = (LayoutMap) modelMap.get(item);

        if (layoutMap == null) {
          modelMap.put(item, new LayoutMap());
        }

        formsPanel.putClientProperty(LAYOUT_MAP_KEY, layoutMap);
      }

      Iterator  it = renderingComponents.entrySet().iterator();
      Map.Entry e;

      while(it.hasNext()) {
        e = (Entry) it.next();

        String                      key = (String) e.getKey();
        iPlatformRenderingComponent rc  = ((iPlatformRenderingComponent) e.getValue());
        RenderableDataItem          di  = (RenderableDataItem) item.getCustomProperty(key);
        CharSequence                cs  = liRenderer.configureRenderingComponent(formsPanel, rc, di, row, false, false,
                                            col, item);

        rc.getComponent(cs, di);
      }
    }

    formsPanel.revalidate();

    return formsPanel;
  }

  CellConstraints.Alignment getAlignment(String value) {
    if (value == null) {
      return CellConstraints.DEFAULT;
    }

    try {
      return CellConstraints.Alignment.valueOf(value);
    } catch(Exception e) {
      throw ApplicationException.runtimeException(e);
    }
  }

  protected abstract iPlatformRenderingComponent createComponentRenderer(iPlatformComponent c);

  protected abstract iPlatformRenderingComponent createLabelRenderer();

  protected abstract aListItemRenderer createListItemRenderer();

  protected abstract iPlatformRenderingComponent createRenderer(String className) throws Exception;

  protected void layout(float width, float height) {
    if ((width == 0) || (height == 0)) {
      return;
    }

    LayoutInfo layoutInfo = null;
    LayoutMap  map        = null;
    FormLayout layout     = formsPanel.getFormLayout();

    if (useLayoutMap) {
      map = (LayoutMap) formsPanel.getClientProperty(LAYOUT_MAP_KEY);

      if (map != null) {
        if ((map.width == width) && (map.height == height)) {
          layoutInfo = map.layoutInfo;
        }
      } else {
        map = new LayoutMap();
        formsPanel.putClientProperty(LAYOUT_MAP_KEY, map);
      }
    }

    int x[], y[];

    if (layoutInfo != null) {
      x = layoutInfo.columnOrigins;
      y = layoutInfo.rowOrigins;
    } else {
      size.setSize(width, height);
      insets = formsPanel.getInsets(insets);
      layout.initializeColAndRowComponentLists(formsPanel);
      layoutInfo = layout.computeLayout(formsPanel, size, insets);
      x          = layoutInfo.columnOrigins;
      y          = layoutInfo.rowOrigins;
    }

    layout.layoutComponents(formsPanel, x, y);

    if (map != null) {
      map.layoutInfo = layoutInfo;
      map.width      = width;
      map.height     = height;
    }
  }
 
  protected static class LayoutMap {
    float      width  = Short.MIN_VALUE;
    float      height = Short.MIN_VALUE;
    LayoutInfo layoutInfo;

    LayoutMap() {}
  }

  static class FormsPanelEx extends FormsPanel {
    @Weak
    aFormsLayoutRenderer renderer;
   
    public FormsPanelEx(Object view) {
      super(view);
    }
    
    @Override
    protected void getPreferredSizeEx(UIDimension size, float maxWidth) {
      int n=renderer.columnWidth;
      if(n<0) {
        maxWidth+=n;
        if(maxWidth<0) {
          maxWidth=0;
        }
      }
      else if(n>0 && maxWidth>n){
        maxWidth=n;
      }
      super.getPreferredSizeEx(size, maxWidth);
    }
  }
 }
