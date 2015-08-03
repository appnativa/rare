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

import com.appnativa.rare.iConstants;
import com.appnativa.rare.platform.PlatformHelper;

import com.jgoodies.forms.layout.CellConstraints;

import java.util.ArrayList;
import java.util.Collections;

public abstract class aLinearPanel extends aFormsPanel {
  boolean           firstComponentAdded;
  protected String  colSpec;
  protected boolean horizontal;
  protected String  rowSpec;
  protected int     spacing;
  private String    originalRowSpec;
  private String    originalColSpec;

  public aLinearPanel(boolean horizontal) {
    this.horizontal = horizontal;
  }

  public void addComponent(iPlatformComponent comp) {
    addComponent(comp, null);
  }

  public void addComponent(iPlatformComponent comp, String spec) {
    if (!firstComponentAdded) {
      firstComponentAdded = true;

      if (getComponentCount() == 0) {
        addFirstComponent(comp, spec);

        return;
      }
    }

    if ((spec == null) && Boolean.TRUE.equals(comp.getClientProperty(iConstants.MENU_EXPANSION_NAME))) {
      spec = "FILL:DEFAULT:GROW";
    }

    if (horizontal) {
      if ((spacing > 0) && (getColumnCount() == 0)) {
        addSpacerColumn(spacing);
      }

      addComponent(comp, addColumn(-1, (spec == null)
                                       ? colSpec
                                       : spec), 0);

      if (spacing > 0) {
        addSpacerColumn(spacing);
      }
    } else {
      if ((spacing > 0) && (getColumnCount() == 0)) {
        addSpacerRow(spacing);
      }

      addComponent(comp, 0, addRow(-1, (spec == null)
                                       ? rowSpec
                                       : spec));

      if (spacing > 0) {
        addSpacerRow(spacing);
      }
    }
  }

  protected void setSpecs(boolean horizontal, String rspec, String cspec) {
    originalRowSpec = rspec;
    originalColSpec = cspec;

    if (rspec == null) {
      if (horizontal) {
        rspec = "FILL:DEFAULT:GROW";
      } else {
        rspec = "FILL:DEFAULT:NONE";
      }
    }

    if (cspec == null) {
      if (horizontal) {
        cspec = "FILL:DEFAULT:NONE";
      } else {
        cspec = "FILL:DEFAULT:GROW";
      }
    }

    rowSpec = rspec;
    colSpec = cspec;
  }

  public void addExpansionComponent() {
    addComponent(PlatformHelper.createSpacerComponent(this), "FILL:DEFAULT:GROW");
  }

  public void addSeparatorComponent() {
    addComponent(PlatformHelper.createSeparatorComponent(this));
  }

  @Override
  public void remove(iPlatformComponent c) {
    if (c != null) {
      CellConstraints cc = getCellConstraints(c);

      super.remove(c);

      if (cc != null) {
        int row = cc.gridY - 1;
        int col = cc.gridX - 1;

        if (horizontal) {
          if (spacing > 0) {
            removeColumn(col);
          }

          removeColumn(col);
        } else {
          if (spacing > 0) {
            removeRow(row);
          }

          removeRow(row);
        }
      }
    }
  }

  public void setColumnSpec(String spec) {
    colSpec = spec;
  }

  public void setHorizontal(boolean horizontal) {
    if (this.horizontal != horizontal) {
      this.horizontal = horizontal;

      int len = getComponentCount();

      if (len != 0) {
        ArrayList<iPlatformComponent> list = new ArrayList<iPlatformComponent>(len);

        getComponents(list);
        removeAll();
        firstComponentAdded = false;

        String c = (originalRowSpec == null)
                   ? rowSpec
                   : originalRowSpec;
        String r = (originalColSpec == null)
                   ? colSpec
                   : originalColSpec;

        setSpecs(horizontal, r, c);
        updateFormLayout();

        for (int i = 0; i < len; i++) {
          addComponent(list.get(i));
        }
      }
    }
  }

  public void setRowSpec(String spec) {
    rowSpec = spec;
  }

  public void setSpacing(int spacing) {
    if (this.spacing != spacing) {
      this.spacing = spacing;

      int len = getComponentCount();

      if (len != 0) {
        ArrayList<iPlatformComponent> list = new ArrayList<iPlatformComponent>(len);

        getComponents(list);
        removeAll();

        for (int i = 0; i < len; i++) {
          addComponent(list.get(i));
        }
      }
    }
  }

  public int getSpacing() {
    return spacing;
  }

  public boolean isHorizontal() {
    return horizontal;
  }

  protected abstract void updateFormLayout();

  protected void addFirstComponent(iPlatformComponent comp, String spec) {
    firstComponentAdded = true;

    if ((spec == null) && Boolean.TRUE.equals(comp.getClientProperty(iConstants.MENU_EXPANSION_NAME))) {
      spec = "FILL:DEFAULT:GROW";
    }

    if (spec != null) {
      if (horizontal) {
        setColumnSpec(0, spec);
      } else {
        setRowSpec(0, spec);
      }
    }

    addComponent(comp, 0, 0);

    if (this.spacing > 0) {
      int sp = this.spacing;

      this.spacing = 0;
      setSpacing(sp);
    }
  }

  public void reverseComponentOrder() {
    int len = getComponentCount();

    if (len != 0) {
      ArrayList<iPlatformComponent> list = new ArrayList<iPlatformComponent>(len);

      getComponents(list);
      removeAll();
      Collections.reverse(list);
      firstComponentAdded = false;

      String r = (originalRowSpec == null)
                 ? rowSpec
                 : originalRowSpec;
      String c = (originalColSpec == null)
                 ? colSpec
                 : originalColSpec;

      setSpecs(horizontal, r, c);
      updateFormLayout();

      for (int i = 0; i < len; i++) {
        addComponent(list.get(i));
      }
    }
  }
}
