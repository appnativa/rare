package com.appnativa.rare.ui;

import com.appnativa.rare.ui.event.iDataModelListener;


public interface iPlatformListHandler extends iListHandler,iScrollerSupport{
float getPreferredWidth();

UIDimension getPreferredSize();

void removeDataModelListener(iDataModelListener l);

void addDataModelListener(iDataModelListener l);

}
