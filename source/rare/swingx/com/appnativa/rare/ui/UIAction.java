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

import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.ActionItem;
import com.appnativa.rare.widget.iWidget;

import javax.swing.Action;

/**
 * A class representing an executable action
 *
 * @author Don DeCoteau
 */
public class UIAction extends aUIAction implements Action {
  protected Object mnemonicKey;
  protected Object acceleratorKey;

  public UIAction(aUIAction a) {
    super(a);
  }

  public UIAction(iWidget context, ActionItem item) {
    super(context, item);
  }

  public UIAction(String name, CharSequence text, iPlatformIcon icon, String desc) {
    super(name, text, icon, desc);
  }

  public UIAction(String name, CharSequence text, iPlatformIcon icon) {
    super(name, text, icon);
  }

  public UIAction(String name) {
    super(name);
  }

  @Override
  public void actionPerformed(java.awt.event.ActionEvent e) {
    actionPerformed(PlatformHelper.createActionEvent(e));
  }

  @Override
  public Object getValue(String key) {
    if (key == Action.NAME) {
      return getActionName();
    }

    if (key == Action.ACTION_COMMAND_KEY) {
      return getActionText();
    }

    if (key == Action.SMALL_ICON) {
      return getSmallIcon();
    }

    if (key == Action.LARGE_ICON_KEY) {
      return getSmallIcon();
    }

    if (key == Action.SHORT_DESCRIPTION) {
      return getShortDescription();
    }

    if (key == Action.LONG_DESCRIPTION) {
      return getLongDescription();
    }

    if (key == Action.MNEMONIC_KEY) {
      return mnemonicKey;
    }

    if (key == Action.ACCELERATOR_KEY) {
      return acceleratorKey;
    }

    return null;
  }

  @Override
  public void putValue(String key, Object value) {
    if (key == Action.NAME) {
      setActionName((value == null)
                    ? ""
                    : value.toString());
    } else if (key == Action.ACTION_COMMAND_KEY) {
      setActionText((value == null)
                    ? ""
                    : value.toString());
    } else if (key == Action.SMALL_ICON) {
      setSmallIcon((iPlatformIcon) value);
    } else if (key == Action.LARGE_ICON_KEY) {
      setLargeIcon((iPlatformIcon) value);
    } else if (key == Action.SHORT_DESCRIPTION) {
      setShortDescription((value == null)
                          ? ""
                          : value.toString());
    } else if (key == Action.LONG_DESCRIPTION) {
      setLongDescription((value == null)
                         ? ""
                         : value.toString());
    } else if (key == Action.MNEMONIC_KEY) {
      mnemonicKey = value;
    } else if (key == Action.ACCELERATOR_KEY) {
      acceleratorKey = value;
    }
  }
}
