//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/border/UIDropShadowBorder.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUIDropShadowBorder_H_
#define _RAREUIDropShadowBorder_H_

@class RAREUIColor;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/border/aUIDropShadowBorder.h"

@interface RAREUIDropShadowBorder : RAREaUIDropShadowBorder {
}

- (id)init;
- (id)initWithBoolean:(BOOL)showLeftShadow;
- (id)initWithRAREUIColor:(RAREUIColor *)shadowColor
                withFloat:(float)shadowSize;
- (id)initWithRAREUIColor:(RAREUIColor *)shadowColor
                withFloat:(float)shadowSize
                withFloat:(float)shadowOpacity
                withFloat:(float)cornerSize
              withBoolean:(BOOL)showTopShadow
              withBoolean:(BOOL)showLeftShadow
              withBoolean:(BOOL)showBottomShadow
              withBoolean:(BOOL)showRightShadow;
@end

typedef RAREUIDropShadowBorder ComAppnativaRareUiBorderUIDropShadowBorder;

#endif // _RAREUIDropShadowBorder_H_
