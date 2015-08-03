/*
 * @(#)UIAction.java   2011-03-06
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import javax.swing.Action;

import com.appnativa.rare.platform.PlatformHelper;
import com.appnativa.rare.spot.ActionItem;
import com.appnativa.rare.widget.iWidget;

/**
 * A class representing an executable action
 *
 * @author Don DeCoteau
 */
public class UIAction extends aUIAction implements Action{
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
		if(key==Action.NAME) {
      return getActionName();
		}
		if(key==Action.ACTION_COMMAND_KEY) {
      return getActionText();
		}
		if(key==Action.SMALL_ICON) {
			return getSmallIcon();
		}
		if(key==Action.LARGE_ICON_KEY) {
			return getSmallIcon();
		}
		if(key==Action.SHORT_DESCRIPTION) {
			return getShortDescription();
		}
		if(key==Action.LONG_DESCRIPTION) {
			return getLongDescription();
		}
		if(key==Action.MNEMONIC_KEY) {
			return mnemonicKey;
		}
		if(key==Action.ACCELERATOR_KEY) {
			return acceleratorKey;
		}
		return null;
	}

	@Override
  public void putValue(String key, Object value) {
		if(key==Action.NAME) {
      setActionName(value==null ? "" :value.toString());
		}
		else if(key==Action.ACTION_COMMAND_KEY) {
      setActionText(value==null ? "" :value.toString());
		}
		else if(key==Action.SMALL_ICON) {
			setSmallIcon((iPlatformIcon)value);
		}
		else if(key==Action.LARGE_ICON_KEY) {
			setLargeIcon((iPlatformIcon)value);
		}
		else if(key==Action.SHORT_DESCRIPTION) {
			setShortDescription(value==null ? "" :value.toString());
		}
		else if(key==Action.LONG_DESCRIPTION) {
			setLongDescription(value==null ? "" :value.toString());
		}
		else if(key==Action.MNEMONIC_KEY) {
			mnemonicKey=value;
		}
		else if(key==Action.ACCELERATOR_KEY) {
			acceleratorKey=value;
		}
		
	}


}
