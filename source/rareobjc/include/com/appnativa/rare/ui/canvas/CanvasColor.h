//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-canvas/com/appnativa/rare/ui/canvas/CanvasColor.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARECanvasColor_H_
#define _RARECanvasColor_H_

@class RAREUIColor;
@protocol RAREiPlatformPaint;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/canvas/iContext.h"

@interface RARECanvasColor : NSObject < RAREiContext_iCanvasPaint, NSCopying > {
 @public
  RAREUIColor *color_;
}

- (id)initWithRAREUIColor:(RAREUIColor *)color;
- (id)clone;
- (id<RAREiContext_iCanvasPaint>)cloneCopy;
- (id<RAREiPlatformPaint>)getPaint;
- (RAREUIColor *)getUIColor;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(RARECanvasColor *)other;
@end

J2OBJC_FIELD_SETTER(RARECanvasColor, color_, RAREUIColor *)

typedef RARECanvasColor ComAppnativaRareUiCanvasCanvasColor;

#endif // _RARECanvasColor_H_
