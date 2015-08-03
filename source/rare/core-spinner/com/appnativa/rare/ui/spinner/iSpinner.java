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

import com.appnativa.rare.ui.event.iChangeListener;

public interface iSpinner {
  void addChangeListener(iChangeListener l);

  void commitEdit();

  void removeChangeListener(iChangeListener l);

  void setButtonsSideBySide(boolean sideBySide);

  void setButtonsVisible(boolean visible);

  void setEditable(boolean editable);

  void setEditor(iSpinnerEditor editor);

  void setEnabled(boolean enabled);

  void setModel(iSpinnerModel model);

  void setUseDesktopStyleEditor(boolean useDesktopStyleEditor);

  void setValue(Object value);

  void setVisibleCharacters(int count);

  iSpinnerEditor getEditor();

  void swapButtonIcons();

  /**
   * @return the spinnerModel
   */
  iSpinnerModel getModel();

  Object getNextValue();

  Object getPreviousValue();

  Object getValue();

  Object getView();

  boolean isButtonsSideBySide();

  boolean isButtonsVisible();

  boolean isEditable();

  boolean isUseDesktopStyleEditor();

  void setContinuousAction(boolean continuous);

  Object removeSelectedData(boolean returnData);
}
