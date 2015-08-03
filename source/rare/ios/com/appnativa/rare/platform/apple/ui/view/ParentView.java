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

package com.appnativa.rare.platform.apple.ui.view;

import com.appnativa.rare.platform.apple.ui.iAppleLayoutManager;

import java.beans.PropertyChangeListener;

/*-[
 #import <UIKit/UIKit.h>
 #import "AppleHelper.h"
 #import "RAREAPView.h"
 #import "APView+Component.h"
 ]-*/
public class ParentView extends View implements PropertyChangeListener {
  protected ParentView() {}

  public ParentView(Object nsview) {
    super(nsview);
  }

  public native void add(int position, View view)
  /*-[
    [view setParentViewWithRAREView:self];
    [((UIView*)proxy_) addSubview:((UIView*)view->proxy_)];
   ]-*/
  ;

  public native void removeChild(View view)
  /*-[
    [((UIView*)view->proxy_) removeFromSuperview];
  ]-*/
  ;

  public native void removeChildren()
  /*-[
    [[((UIView*)proxy_) subviews] makeObjectsPerformSelector:@selector(removeFromSuperview)];
  ]-*/
  ;

  public native void setLayoutManager(iAppleLayoutManager lm)
  /*-[
    [((RAREAPView*)proxy_) setLayoutManager: lm];
  ]-*/
  ;

  public native View getViewAt(float x, float y, boolean deepest)
  /*-[
    UIView* v=(UIView*)proxy_;
    return [v getViewAtX:x andY:y deepest: deepest];
  ]-*/
  ;

  protected native void setUseFlipTransform(boolean use)
  /*-[
    [((RAREAPView*)proxy_) setUseFlipTransform: use];
  ]-*/
  ;

  public void invalidateLayout() {}
}
