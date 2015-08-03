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

/*-[
 #import "java/util/Locale.h"
]-*/
public class UICursor {
  final Object cursor;
  final String name;

  public UICursor(String name, Object cursor) {
    this.name   = name;
    this.cursor = cursor;
  }

  public Object getNSCursor() {
    return cursor;
  }

  public static native UICursor getCursor(String name)
  /*-[
#if TARGET_OS_IPHONE
#else
    name = [name lowercaseStringWithJRELocale:[JavaUtilLocale US]];
    if ([name isEqual:@"CROSSHAIR"]) {
      return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor crosshairCursor]];
    }
    else if ([name isEqual:@"DEFAULT"]) {
      return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor arrowCursor]];
    }
    else if ([name isEqual:@"HELP"]) {
      return nil;
    }
    else if ([name isEqual:@"MOVE"]) {
      return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor closedHandCursor]];
    }
    else if ([name isEqual:@"POINTER "]) {
      return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor arrowCursor]];
    }
    else if ([name isEqual:@"PROGRESS"]) {
      return nil;
    }
    else if ([name isEqual:@"TEXT"]) {
      return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor IBeamCursor]];
    }
    else if ([name isEqual:@"WAIT"]) {
      return nil;
    }
    else if ([name isEqual:@"E-RESIZE"]) {
      return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor resizeRightCursor]];
    }
    else if ([name isEqual:@"NE-RESIZE"]) {
      return nil;
    }
    else if ([name isEqual:@"NW-RESIZE"]) {
      return nil;
    }
    else if ([name isEqual:@"N-RESIZE"]) {
      return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor resizeUpCursor]];
    }
    else if ([name isEqual:@"SE-RESIZE"]) {
      return nil;
    }
    else if ([name isEqual:@"SW-RESIZE"]) {
      return nil;
    }
    else if ([name isEqual:@"S-RESIZE"]) {
      return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor resizeDownCursor]];
    }
    else if ([name isEqual:@"W-RESIZE"]) {
      return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor resizeLeftCursor]];
    }
    else if ([name isEqual:@"EW-RESIZE"]) {
      return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor resizeLeftRightCursor]];
    }
    else if ([name isEqual:@"NS-RESIZE"]) {
      return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor resizeUpDownCursor]];
    }
    else if ([name isEqual:@"NESW-RESIZE"]) {
      return nil;
    }
    else if ([name isEqual:@"NWSE-RESIZE"]) {
      return nil;
    }
    else if ([name isEqual:@"COL-RESIZE"]) {
      return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor resizeLeftRightCursor]];
    }
    else if ([name isEqual:@"ROW-RESIZE"]) {
      return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor resizeUpDownCursor]];
    }
    else if ([name isEqual:@"ALL-SCROLL"]) {
    }
#endif
    return nil;
  ]-*/
  ;

  public String getName() {
    return name;
  }
}
