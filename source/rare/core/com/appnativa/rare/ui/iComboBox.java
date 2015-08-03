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

import com.appnativa.rare.ui.event.iPopupMenuListener;
import com.appnativa.rare.ui.text.iPlatformTextEditor;

public interface iComboBox {
  public void addPopupMenuListener(iPopupMenuListener l);

  public void cancelPopup();

  public void removePopupMenuListener(iPopupMenuListener l);

  public void setButtonVisible(boolean visible);

  public void setEditable(boolean editable);

  public void setEditorValue(CharSequence value);

  public void setEditorIcon(iPlatformIcon icon);

  public void setPopupBorder(iPlatformBorder b);

  public void setPopupVisible(boolean visible);

  public void setUseDialogButton(boolean dialog);

  public String getEditorValue();

  public iPlatformTextEditor getTextEditor();

  public iPlatformBorder getPopupBorder();

  public iActionComponent getPopupButton();

  public boolean isButtonVisible();

  public boolean isEditable();

  public boolean isPopupVisible();

  public void getWillBecomeVisibleBounds(UIRectangle rect);
}
