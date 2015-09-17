//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-tabpane/com/appnativa/rare/ui/tabpane/aBoxTabPainter.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREaBoxTabPainter_H_
#define _RAREaBoxTabPainter_H_

@class JavaLangStringBuilder;
@class RARELocationEnum;
@class RAREPaintBucket;
@class RAREUIAction;
@class RAREUIColor;
@class RAREiGradientPainter_DirectionEnum;
@protocol RAREiActionComponent;
@protocol RAREiPlatformGraphics;
@protocol RAREiTabLabel;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/tabpane/aPlatformTabPainter.h"

@interface RAREaBoxTabPainter : RAREaPlatformTabPainter {
 @public
  RAREiGradientPainter_DirectionEnum *horizontalGradientDirection_;
  RAREiGradientPainter_DirectionEnum *horizontalSelectedGradientDirection_;
  BOOL borderPainted_;
}

- (id)init;
- (void)dispose;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height;
- (void)setMoreButtonWithRAREiActionComponent:(id<RAREiActionComponent>)more;
- (void)setNormalPaintWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setSelectedPaintWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setSelectedTabBorderColorWithRAREUIColor:(RAREUIColor *)fg;
- (BOOL)isHandlesBottomRotation;
- (BOOL)isHandlesRightLeftRotation;
- (RAREPaintBucket *)createDefaultPainter;
- (RAREPaintBucket *)createDefaultSelectedPainter;
- (id<RAREiTabLabel>)createNewRendererWithRAREUIAction:(RAREUIAction *)a;
- (void)locationChanged;
- (void)paintBackgroundWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                       withFloat:(float)x
                                       withFloat:(float)y
                                       withFloat:(float)width
                                       withFloat:(float)height;
- (void)paintBorderWithRARELocationEnum:(RARELocationEnum *)loc
              withRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                              withFloat:(float)x
                              withFloat:(float)y
                              withFloat:(float)width
                              withFloat:(float)height;
- (void)paintTabWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                        withRAREiTabLabel:(id<RAREiTabLabel>)tab
                                withFloat:(float)x
                                withFloat:(float)y
                                withFloat:(float)width
                                withFloat:(float)height
                                  withInt:(int)index;
- (void)setInsets;
- (id)getUIDefaultsWithJavaLangStringBuilder:(JavaLangStringBuilder *)sb
                                     withInt:(int)sblen
                                 withBoolean:(BOOL)painter
                                withNSString:(NSString *)key;
- (BOOL)isBorderPainted;
- (void)setBorderPaintedWithBoolean:(BOOL)borderPainted;
- (void)copyAllFieldsTo:(RAREaBoxTabPainter *)other;
@end

J2OBJC_FIELD_SETTER(RAREaBoxTabPainter, horizontalGradientDirection_, RAREiGradientPainter_DirectionEnum *)
J2OBJC_FIELD_SETTER(RAREaBoxTabPainter, horizontalSelectedGradientDirection_, RAREiGradientPainter_DirectionEnum *)

typedef RAREaBoxTabPainter ComAppnativaRareUiTabpaneABoxTabPainter;

#endif // _RAREaBoxTabPainter_H_
