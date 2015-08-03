/*
 * @(#)iPlatformListDataModel.java   2012-01-04
 *
 * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
 *
 * Use is subject to license terms.
 */

package com.appnativa.rare.ui;

import javax.swing.ListModel;


public interface iPlatformListDataModel extends iListDataModel,ListModel {

  void setEditing(boolean editing);

}
