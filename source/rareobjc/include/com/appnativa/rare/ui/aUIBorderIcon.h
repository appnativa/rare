//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aUIBorderIcon.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaUIBorderIcon_H_
#define _RAREaUIBorderIcon_H_

@class RAREUIInsets;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/aPlatformIcon.h"

@interface RAREaUIBorderIcon : RAREaPlatformIcon {
 @public
  id<RAREiPlatformBorder> border_;
  id<RAREiPlatformIcon> icon_;
  RAREUIInsets *insets_;
}

- (id)initWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border
            withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height;
- (id<RAREiPlatformIcon>)getDisabledVersion;
- (int)getIconHeight;
- (int)getIconWidth;
- (id<RAREiPlatformIcon>)getIcon;
- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (id<RAREiPlatformBorder>)getBorder;
- (void)setBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border;
- (void)copyAllFieldsTo:(RAREaUIBorderIcon *)other;
@end

J2OBJC_FIELD_SETTER(RAREaUIBorderIcon, border_, id<RAREiPlatformBorder>)
J2OBJC_FIELD_SETTER(RAREaUIBorderIcon, icon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RAREaUIBorderIcon, insets_, RAREUIInsets *)

typedef RAREaUIBorderIcon ComAppnativaRareUiAUIBorderIcon;

#endif // _RAREaUIBorderIcon_H_
