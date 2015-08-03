/*
 * @(#)ListEditor.java   2011-03-15
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.platform.swing.ui.view.spinner;

import java.util.List;
import java.util.Locale;

import com.appnativa.rare.ui.listener.iTextChangeListener;
import com.appnativa.rare.ui.spinner.SpinnerListModel;

/**
 *
 * @author Don DeCoteau
 */
public class ListEditor extends DefaultEditor implements iTextChangeListener{
  private volatile boolean dirty;
  private volatile int dirtyIndex;
  public ListEditor(SpinnerListModel model) {
    super(model);
  }

  @Override
  protected void customizeEditor() {
    super.customizeEditor();
    getTextField().removeTextChangeListener(this);
    getTextField().addTextChangeListener(this);
  }

	@Override
  public boolean textChanging(Object source, int start, int end, CharSequence replacementString) {
		String text=getTextField().getText();
		int slen=text.length();
		StringBuilder sb=new StringBuilder(slen-(end-start)+replacementString.length());
		if(start>0) {
			sb.append(text.substring(0,start));
		}
		sb.append(replacementString);
		if(end<slen) {
			sb.append(text.substring(end));
		}
    String       result   = sb.toString();
    String       str      = result.toLowerCase(Locale.getDefault());
    List         list     = ((SpinnerListModel) spinnerModel).getList();
    final int len=list.size();
    for (int i=0;i<len;i++) {
      Object o=list.get(i);
      String val = spinnerModel.toString(o);

      if (val.toLowerCase().startsWith(str)) {
        dirty=!val.equals(result);
        dirtyIndex=i;
        return true;
      }
    }

    return false;
		
	}
	@Override
  public boolean shouldStopEditing(Object source) {
		return true;
	}

  @Override
  public void textChanged(Object source) {
    if(dirty) {
      dirty=false;
      Object o=((SpinnerListModel) spinnerModel).getList().get(dirtyIndex);
      String val=o.toString();
      String s=getTextField().getText();
      int slen=s.length();
      int vlen=val.length();
      getTextField().replaceText(0,slen,val);
      if(slen<vlen) {
        getTextField().select(slen, vlen);
      }
    }
  }
}
