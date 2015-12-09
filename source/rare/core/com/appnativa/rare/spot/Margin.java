/**************************************************************************
 * Margin.java - Wed Nov 18 17:50:58 PST 2015
 *
 * Copyright (c) appNativa
 *
 * All rights reserved.
 *
 * Generated by the Sparse Notation(tm) To Java Compiler v1.0
 * Note 1: Code entered after the "//USER_IMPORTS_AND_COMMENTS_MARK{}" comment and before the class declaration will be preserved.
 * Note 2: Code entered out side of the other   comment blocks will be preserved
 * Note 3: If you edit the automatically generated comments and want to preserve your edits remove the //GENERATED_COMMENT{} tags
 */

package com.appnativa.rare.spot;

import com.appnativa.spot.*;

//USER_IMPORTS_AND_COMMENTS_MARK{}
import com.appnativa.rare.ui.ScreenUtils;
import com.appnativa.rare.ui.UIInsets;
import com.appnativa.rare.ui.UIRectangle;
import com.appnativa.rare.ui.border.UIEmptyBorder;
import com.appnativa.spot.*;

//GENERATED_COMMENT{}
/**
 * This class represents the configuration information of a
 * margin around a widget or viewable area
 *
 * @author Don DeCoteau
 * @version 2.0
 */  
public class Margin extends SPOTSequence {
  //GENERATED_MEMBERS{

  /** Layout: the empty space to leave at the top */
  public SPOTPrintableString top = new SPOTPrintableString(null, 0, 32, "0", false);

  /** Layout: the empty space to leave on the right */
  public SPOTPrintableString right = new SPOTPrintableString(null, 0, 32, "0", false);

  /** Layout: the empty space to leave at the bottom */
  public SPOTPrintableString bottom = new SPOTPrintableString(null, 0, 32, "0", false);

  /** Layout: the empty space to leave on the left */
  public SPOTPrintableString left = new SPOTPrintableString(null, 0, 32, "0", false);

  //}GENERATED_MEMBERS

  //GENERATED_METHODS{

  /**
   * Creates a new optional <code>Margin</code> object.
   */
  public Margin()  {
    this(true);
  }

  /**
   * Creates a new <code>Margin</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   */
  public Margin( boolean optional ) {
    super( optional, false );
    spot_setElements();
  }

  /**
   * Creates a new <code>Margin</code> object.
   *
   * @param optional  <code>true</code> if the element is optional; <code>false</code> otherwise)
   * @param setElements  <code>true</code> if a call to setElements should be made; <code>false</code> otherwise)
   */
  protected Margin( boolean optional,boolean setElements ) {
    super( optional, setElements );
  }

  /**
   * Adds elements to the object elements map
   *
   */
  protected void spot_setElements()  {
    this.elementsSizeHint  +=4;
    this.attributeSizeHint +=4;
    super.spot_setElements();
    spot_defineAttribute("leftIcon",null);
    spot_defineAttribute("rightIcon",null);
    spot_defineAttribute("topIcon",null);
    spot_defineAttribute("bottomIcon",null);
    spot_addElement( "top", top );
    spot_addElement( "right", right );
    spot_addElement( "bottom", bottom );
    spot_addElement( "left", left );
  }

  //}GENERATED_METHODS
  public Margin(int top, int right, int bottom, int left) {
    this(false);
    this.top.setValue(top);
    this.left.setValue(left);
    this.bottom.setValue(bottom);
    this.right.setValue(right);
  }

  public void setValues(int top, int right, int bottom, int left) {
    this.top.setValue(top);
    this.left.setValue(left);
    this.bottom.setValue(bottom);
    this.right.setValue(right);
  }

  public UIInsets getInsets() {
    return new UIInsets(intValue(top), intValue(right), intValue(bottom), intValue(left));
  }

  public UIRectangle getRectangle() {
    return new UIRectangle(intValue(left), intValue(top), intValue(right), intValue(bottom));
  }

  public UIInsets setInsets(UIInsets in) {
    if (top.spot_valueWasSet()) {
      in.top = intValue(top);
    }

    if (left.spot_valueWasSet()) {
      in.left = intValue(left);
    }

    if (bottom.spot_valueWasSet()) {
      in.bottom = intValue(bottom);
    }

    if (right.spot_valueWasSet()) {
      in.right = intValue(right);
    }

    return in;
  }

  public UIEmptyBorder getBorder() {
    return new UIEmptyBorder(intValue(top), intValue(right), intValue(bottom), intValue(left));
  }

   private int intValue(SPOTPrintableString val) {
    return ScreenUtils.toPlatformPixelWidth(val.getValue(), null, 100);
  }
  //GENERATED_INNER_CLASSES{

  //}GENERATED_INNER_CLASSES
}
