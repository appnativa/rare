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

import com.appnativa.rare.ui.UIColor;
import com.appnativa.rare.ui.UIFont;
import com.appnativa.rare.ui.iPlatformComponent;

/**
 *
 * @author Don DeCoteau
 */
public interface iSpinnerEditor {
  public void setValue(Object value);

  public iPlatformComponent getComponent();

  public Object getValue();

  void requestFocus();

  void clearFocus();

  void commitEdit();

  void modelChanged();

  void setEditable(boolean editable);

  boolean isEditable();

  boolean isEditorDirty();

  void setForeground(UIColor color);

  void setFont(UIFont font);

  void dispose();

  boolean isTextField();

  Object removeSelectedData(boolean returnData);
}
