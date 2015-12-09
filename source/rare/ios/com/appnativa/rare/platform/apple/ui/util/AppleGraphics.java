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

package com.appnativa.rare.platform.apple.ui.util;

import com.appnativa.rare.platform.apple.ui.view.View;
/*-[
 #import <com/appnativa/rare/ui/UIFont.h>
 #import <com/appnativa/rare/ui/UIColor.h>
]-*/

public class AppleGraphics extends aAppleGraphics {
  public AppleGraphics(Object g, View view) {
    super(g, view);
  }

  @Override
  public native void drawString(String str, float x, float y, float height)
  /*-[
    RAREUIFont* f=[self getFont];
    BOOL pushed=[self setContextAsCurrent];
    NSMutableDictionary* attributes=[RAREAppleGraphics addDictionaryAttributeWithNSString:NSFontAttributeName withId:[f getIOSProxy] withBoolean:YES];
    RAREUIColor* fg=[self getColor];
    [attributes setObject:[fg getAPColor] forKey:NSForegroundColorAttributeName];
    [str drawAtPoint:CGPointMake(x, y) withAttributes:attributes];
    if(pushed) {
      [self restoreOldContextAsCurrent];
    }
  ]-*/
  ;

  @Override
  protected native Object getRoundedRectPath(float x, float y, float width, float height, float arcWidth,
          float arcHeight)    
  /*-[
    CGFloat radius=(arcHeight/2)+((arcWidth*arcWidth)/(8*arcHeight));
    return [UIBezierPath bezierPathWithRoundedRect:CGRectMake(x,y,width,height) cornerRadius:radius];
  ]-*/
  ;

  @Override
  protected native boolean setContextAsCurrent()
  /*-[
    CGContextRef ctx=(__bridge CGContextRef)[self getContextRef];
    if(UIGraphicsGetCurrentContext()==ctx) {
      return false;
    }
    UIGraphicsPushContext(ctx);
    return true;
  ]-*/
  ;

  @Override
  protected native void restoreOldContextAsCurrent()
  /*-[
    UIGraphicsPopContext();
  ]-*/
  ;
}
