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

import com.appnativa.rare.platform.apple.ui.view.View;
import com.appnativa.rare.ui.UIDimension;
import com.appnativa.rare.ui.event.ChangeEvent;
import com.appnativa.rare.ui.event.iChangeListener;
/*-[
#import "RAREUIPickerView.h"
 ]-*/

public class PickerView extends View {
  iChangeListener       changeListener;
  private ChangeEvent   changeEvent;
  private iPickerHelper pickerHelper;

  public PickerView() {
    super(createProxy());
  }

  @Override
  public void setChangeListener(iChangeListener changeListener) {
    this.changeListener = changeListener;
  }

  public native void setIsCircular(boolean circular)
  /*-[
    ((RAREUIPickerView*)proxy_).isCircular=circular;
  ]-*/
  ;

  public void setPickerHelper(iPickerHelper pickerHelper) {
    this.pickerHelper = pickerHelper;
  }

  public native void setSelectedIndex(int index)
  /*-[
    [((RAREUIPickerView*)proxy_) setSelectedIndexEx: index];
  ]-*/
  ;

  @Override
  public void getMinimumSize(UIDimension size,float maxWidth) {
    pickerHelper.getMinimumSize(size);
  }

  public iPickerHelper getPickerHelper() {
    return pickerHelper;
  }

  @Override
  public void getPreferredSize(UIDimension size, float maxWidth) {
    pickerHelper.getMinimumSize(size);

    float width = size.width;

    super.getPreferredSize(size, maxWidth);
    size.width = width;
  }

  public native int getSelectedIndex()
  /*-[
    return [((RAREUIPickerView*)proxy_) getSelectedIndexEx];
  ]-*/
  ;

  native static Object createProxy()
  /*-[
    return [RAREUIPickerView new];
  ]-*/
  ;

  @Override
  protected void disposeEx() {
    super.disposeEx();
    changeEvent    = null;
    pickerHelper   = null;
    changeListener = null;
  }

  protected void rowSelected(int index) {
    if (changeListener != null) {
      if (changeEvent == null) {
        changeEvent = new ChangeEvent(this);
      }

      changeListener.stateChanged(changeEvent);
    }
  }

  public interface iPickerHelper {
    void renderValue(int row, Object nativeView);

    void getMinimumSize(UIDimension size);

    int getRowCount();

    CharSequence getValue(int row);
  }
}
