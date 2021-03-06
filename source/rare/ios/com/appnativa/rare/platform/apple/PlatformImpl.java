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

package com.appnativa.rare.platform.apple;

import com.appnativa.rare.platform.aPlatformImpl;
import com.appnativa.rare.ui.dnd.DnDHelper;
import com.appnativa.rare.ui.dnd.iFlavorCreator;

/*-[
#import "RAREAPApplication.h"
#import "RAREUIViewController.h"
]-*/
public class PlatformImpl extends aPlatformImpl {
  protected PlatformImpl(aAppContextImpl context) {
    super(context);
  }

  @Override
  public iFlavorCreator getTransferFlavorCreator() {
    return DnDHelper.getInstance();
  }

  @Override
  public String getUserAgent() {
    return "Rare/iOS";
  }

  @Override
  public native boolean isUseFullScreen()
  /*-[
    RAREAPApplication* app=(RAREAPApplication*) [UIApplication sharedApplication];
    return [[app getMainWindow].rootViewController prefersStatusBarHidden];
  ]-*/
  ;
  
  @Override
  public native void setUseFullScreen(boolean use)
  /*-[
    RAREAPApplication* app=(RAREAPApplication*) [UIApplication sharedApplication];
    [(RAREUIViewController*)[app getMainWindow].rootViewController setStatusBarVisibleEx: !use];
  ]-*/
  ;
}
