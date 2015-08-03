///*
// * @(#)ComboBoxListHandler.java   2010-11-28
// *
// * Copyright (c) 2007-2009 appNativa Inc. All rights reserved.
// *
// * Use is subject to license terms.
// */
//
//package com.appnativa.rare.platform.swing.ui;
//
//import com.appnativa.rare.platform.swing.ui.view.ComboBoxView;
//import com.appnativa.rare.ui.UIColor;
//import com.appnativa.rare.ui.iPlatformListDataModel;
//
///**
// * A base class for list style components
// *
// * @author Don DeCoteau
// */
//public class ComboBoxListHandler extends aListHandler {
//  public ComboBoxListHandler(ComboBoxView view, iPlatformListDataModel model) {
//    super(view, model);
//    singleClickAction = true;
//  }
//
//  
//  @Override
//  public void clear() {
//    super.clear();
//    getListView().setText("");
//  }
//
//  @Override
//  public void clearPopupMenuIndex() {}
//
//  public void setSelectedIndex(int index) {
//    if ((index < 0) || (index >= listModel.size())) {
//      index = -1;
//    }
//
//    getListView().setSelectedIndex(index);
//  }
//
//
//  @Override
//  public int getPopupMenuIndex() {
//    return -1;
//  }
//
//  public int getSelectedIndex() {
//    return ((ComboBoxView) getListView()).getSelectedIndex();
//  }
//
//   protected ComboBoxView getListView() {
//    return (ComboBoxView) getView();
//  }
//
//
//	@Override
//	public void setShowDivider(boolean show) {
//		((ComboBoxView) getView()).setShowDivider(show);
//		
//	}
//
//
//	@Override
//	public UIColor getAlternatingRowColor() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//	@Override
//	public void setAlternatingRowColor(UIColor color) {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
