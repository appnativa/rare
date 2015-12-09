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

package com.appnativa.rare.ui.spinner;

import com.appnativa.rare.platform.apple.ui.view.LabelView;
import com.appnativa.rare.ui.FontUtils;
import com.appnativa.rare.ui.RenderableDataItem;
import com.appnativa.rare.ui.RenderableDataItem.HorizontalAlign;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.aSpinnerComponent;
import com.appnativa.rare.ui.event.iChangeListener;
import com.appnativa.rare.ui.renderer.UILabelRenderer;
import com.appnativa.rare.ui.spinner.PickerView.iPickerHelper;
import com.appnativa.rare.ui.spinner.SpinnerDateModel;
import com.appnativa.rare.ui.spinner.SpinnerListModel;
import com.appnativa.rare.ui.spinner.SpinnerNumberModel;
import com.appnativa.rare.ui.spinner.aSpinnerEditor;
import com.appnativa.rare.ui.spinner.iSpinnerModel;
import com.appnativa.util.SNumber;

import java.util.Date;
import java.util.EventObject;
import java.util.List;

public class PickerEditor extends aSpinnerEditor implements iChangeListener, iPickerHelper {
  UIDimension        minSize;
  int                numberRowCount;
  UILabelRenderer    renderingComponent;
  SNumber            returnValue;
  SNumber            workValue;
  RenderableDataItem defaultItem;

  public PickerEditor(iSpinnerModel model) {
    super(model);

    PickerView pv = new PickerView();

    pv.setChangeListener(this);
    pv.setPickerHelper(this);
    pv.setIsCircular(model.isCircular());
    editorView = pv;
  }

  @Override
  public void modelChanged() {
    super.modelChanged();
    minSize = null;
  }

  @Override
  public void renderValue(int row, Object nativeView) {
    aSpinnerComponent spinner = getSpinner();

    if (renderingComponent == null) {
      renderingComponent = new UILabelRenderer(new LabelView(nativeView));
    } else {
      renderingComponent.setNativeView(nativeView);
    }

    renderingComponent.setFont(editorView.getFontAlways());

    Object o = "";

    do {
      if (spinnerModel instanceof SpinnerListModel) {
        SpinnerListModel lm   = (SpinnerListModel) spinnerModel;
        List             list = lm.getList();
        int              len  = list.size();

        if (len == 0) {
          break;
        }

        row = row % len;
        o   = list.get(row);
      } else if (spinnerModel instanceof SpinnerNumberModel) {
        if (defaultItem == null) {
          defaultItem = new RenderableDataItem("");
          defaultItem.setHorizontalAlignment(HorizontalAlign.CENTER);
        }

        o = defaultItem;

        SpinnerNumberModel nm = (SpinnerNumberModel) spinnerModel;

        if (workValue == null) {
          workValue = new SNumber();
        }

        workValue.setValue(nm.getStepSize());
        workValue.multiply(row % numberRowCount);
        workValue.add(nm.getMinimum());
        defaultItem.setValue(nm.toString(workValue));
      }
    } while(false);

    spinner.renderItemAt(o, renderingComponent);
  }

  @Override
  public void stateChanged(EventObject e) {
    int index = ((PickerView) editorView).getSelectedIndex();

    if (index > -1) {
      Object o = getObject(index);

      if (o != null) {
        spinnerModel.setValue(o);
      }
    }
  }

  @Override
  public void setValue(Object value) {
    if (spinnerModel instanceof SpinnerListModel) {
      SpinnerListModel         lm   = (SpinnerListModel) spinnerModel;
      List<RenderableDataItem> list = lm.getList();
      int                      n    = RenderableDataItem.findValueEx(list, value);

      if (n != -1) {
        ((PickerView) editorView).setSelectedIndex(n);
      }
    } else if (spinnerModel instanceof SpinnerNumberModel) {
      SpinnerNumberModel nm = (SpinnerNumberModel) spinnerModel;

      nm.setValue(value);
    }
  }

  @Override
  public void getMinimumSize(UIDimension size) {
    if (minSize == null) {
      UIFont f     = ((PickerView) editorView).getFontAlways();
      int    chars = 5;

      if (spinnerModel instanceof SpinnerNumberModel) {
        SpinnerNumberModel m = (SpinnerNumberModel) spinnerModel;

        chars = Math.max(chars, m.getMaximum().toString().length());
        chars = Math.max(chars, m.getMinimum().toString().length());
      } else if (spinnerModel instanceof SpinnerListModel) {
        SpinnerListModel m    = (SpinnerListModel) spinnerModel;
        List             list = m.getList();
        int              len  = list.size();

        for (int i = 0; i < len; i++) {
          chars = Math.max(chars, list.get(i).toString().length());
        }
      } else if (spinnerModel instanceof SpinnerDateModel) {
        SpinnerDateModel m    = (SpinnerDateModel) spinnerModel;
        Date             date = new Date();

        chars = Math.max(chars, m.toString(date).length());
        chars = Math.max(chars, m.getMinimum().toString().length());
      }

      minSize = new UIDimension(FontUtils.getCharacterWidth(f) * chars, FontUtils.getFontHeight(f, true) * 4);
    }

    size.setSize(minSize);
  }

  @Override
  public int getRowCount() {
    if (spinnerModel instanceof SpinnerNumberModel) {
      SpinnerNumberModel nm  = (SpinnerNumberModel) spinnerModel;
      SNumber            min = nm.getMinimum();
      SNumber            max = nm.getMaximum();

      if (max == null) {
        max = new SNumber(Short.MAX_VALUE - 2);
      }

      if (min == null) {
        min = SNumber.ZERO;
      }

      int count = max.subtract(min).divide(nm.getStepSize()).intValue();

      if (count < 0) {
        count *= -1;
      }

      numberRowCount = count + 1;

      return numberRowCount;
    }

    if (spinnerModel instanceof SpinnerListModel) {
      SpinnerListModel lm = (SpinnerListModel) spinnerModel;

      return lm.getList().size();
    }

    return 0;
  }

  @Override
  public Object getValue() {
    int index = ((PickerView) editorView).getSelectedIndex();

    return (index == -1)
           ? null
           : getObject(index);
  }

  @Override
  public CharSequence getValue(int row) {
    if (spinnerModel instanceof SpinnerListModel) {
      SpinnerListModel lm   = (SpinnerListModel) spinnerModel;
      List             list = lm.getList();
      int              len  = list.size();

      if (len == 0) {
        return null;
      }

      row = row % len;

      Object o = list.get(row);

      if (o instanceof RenderableDataItem) {
        return ((RenderableDataItem) o).toCharSequence();
      }

      return o.toString();
    }

    if (spinnerModel instanceof SpinnerNumberModel) {
      SpinnerNumberModel nm = (SpinnerNumberModel) spinnerModel;

      if (workValue == null) {
        workValue = new SNumber();
      }

      workValue.setValue(nm.getStepSize());
      workValue.multiply(row % numberRowCount);
      workValue.add(nm.getMinimum());

      return nm.toString(workValue);
    }

    return null;
  }

  protected Object getObject(int row) {
    if (spinnerModel instanceof SpinnerListModel) {
      SpinnerListModel lm   = (SpinnerListModel) spinnerModel;
      List             list = lm.getList();

      row = row % list.size();

      return list.get(row);
    }

    if (spinnerModel instanceof SpinnerNumberModel) {
      SpinnerNumberModel nm = (SpinnerNumberModel) spinnerModel;

      if (returnValue == null) {
        returnValue = new SNumber();
      }

      returnValue.setValue(nm.getStepSize());
      returnValue.multiply(row % numberRowCount);
      returnValue.add(nm.getMinimum());

      return returnValue;
    }

    return null;
  }

  @Override
  public boolean isTextField() {
    return false;
  }

  @Override
  public Object removeSelectedData(boolean returnData) {
    return null;
  }

  @Override
  public void selectField() {
  }
}
