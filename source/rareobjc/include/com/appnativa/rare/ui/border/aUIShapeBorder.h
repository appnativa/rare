//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/border/aUIShapeBorder.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaUIShapeBorder_H_
#define _RAREaUIShapeBorder_H_

@class RAREUIColor;
@class RAREUIInsets;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformShape;
@protocol RAREiShapeCreator;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/border/UILineBorder.h"

@interface RAREaUIShapeBorder : RAREUILineBorder {
 @public
  id<RAREiShapeCreator> shapeCreator_;
}

- (id)initWithRAREiShapeCreator:(id<RAREiShapeCreator>)shapeCreator;
- (id)initWithRAREUIColor:(RAREUIColor *)color
    withRAREiShapeCreator:(id<RAREiShapeCreator>)shapeCreator
                withFloat:(float)thickness;
- (id)initWithRAREUIColor:(RAREUIColor *)color
    withRAREiShapeCreator:(id<RAREiShapeCreator>)shapeCreator
         withRAREUIInsets:(RAREUIInsets *)insets
                withFloat:(float)thickness;
- (void)clipWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                            withFloat:(float)x
                            withFloat:(float)y
                            withFloat:(float)width
                            withFloat:(float)height;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)end;
- (void)setPadForArcWithBoolean:(BOOL)padForArc;
- (void)setShapeCreatorWithRAREiShapeCreator:(id<RAREiShapeCreator>)shapeCreator;
- (id<RAREiPlatformShape>)getShapeWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                                  withFloat:(float)x
                                                  withFloat:(float)y
                                                  withFloat:(float)width
                                                  withFloat:(float)height;
- (id<RAREiShapeCreator>)getShapeCreator;
- (BOOL)isPadForArc;
- (BOOL)isRectangular;
- (void)copyAllFieldsTo:(RAREaUIShapeBorder *)other;
@end

J2OBJC_FIELD_SETTER(RAREaUIShapeBorder, shapeCreator_, id<RAREiShapeCreator>)

typedef RAREaUIShapeBorder ComAppnativaRareUiBorderAUIShapeBorder;

#endif // _RAREaUIShapeBorder_H_
