//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/StateListIcon.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREStateListIcon_H_
#define _RAREStateListIcon_H_

@class RAREPainterHolder;
@class RAREiPaintedButton_ButtonStateEnum;
@protocol RAREiPlatformGraphics;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"

@interface RAREStateListIcon : NSObject < RAREiPlatformIcon > {
 @public
  id<RAREiPlatformIcon> icon_;
  RAREPainterHolder *painterHolder_;
}

- (id)initWithRAREPainterHolder:(RAREPainterHolder *)ph;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height;
- (id<RAREiPlatformIcon>)getDisabledVersion;
- (id<RAREiPlatformIcon>)getIconWithRAREiPaintedButton_ButtonStateEnum:(RAREiPaintedButton_ButtonStateEnum *)state;
- (int)getIconHeight;
- (int)getIconWidth;
- (void)copyAllFieldsTo:(RAREStateListIcon *)other;
@end

J2OBJC_FIELD_SETTER(RAREStateListIcon, icon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RAREStateListIcon, painterHolder_, RAREPainterHolder *)

typedef RAREStateListIcon ComAppnativaRarePlatformAppleUiStateListIcon;

#endif // _RAREStateListIcon_H_
