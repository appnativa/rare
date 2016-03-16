//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/border/aUIShapeBorder.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/ui/border/aUILineBorder.h"
#include "com/appnativa/rare/ui/border/aUIShapeBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformPath.h"
#include "com/appnativa/rare/ui/iPlatformShape.h"
#include "com/appnativa/rare/ui/iShapeCreator.h"

@implementation RAREaUIShapeBorder

- (id)initWithRAREiShapeCreator:(id<RAREiShapeCreator>)shapeCreator {
  return [self initRAREaUIShapeBorderWithRAREUIColor:[((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getColorWithNSString:@"controlShadow"] withRAREiShapeCreator:shapeCreator withRAREUIInsets:nil withFloat:1];
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
    withRAREiShapeCreator:(id<RAREiShapeCreator>)shapeCreator
                withFloat:(float)thickness {
  return [self initRAREaUIShapeBorderWithRAREUIColor:color withRAREiShapeCreator:shapeCreator withRAREUIInsets:[[RAREUIInsets alloc] initWithDouble:thickness withDouble:thickness withDouble:thickness withDouble:thickness] withFloat:thickness];
}

- (id)initRAREaUIShapeBorderWithRAREUIColor:(RAREUIColor *)color
                      withRAREiShapeCreator:(id<RAREiShapeCreator>)shapeCreator
                           withRAREUIInsets:(RAREUIInsets *)insets
                                  withFloat:(float)thickness {
  if (self = [super initWithRAREUIColor:color withFloat:thickness withBoolean:NO]) {
    self->insets_ = insets;
    self->shapeCreator_ = shapeCreator;
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)color
    withRAREiShapeCreator:(id<RAREiShapeCreator>)shapeCreator
         withRAREUIInsets:(RAREUIInsets *)insets
                withFloat:(float)thickness {
  return [self initRAREaUIShapeBorderWithRAREUIColor:color withRAREiShapeCreator:shapeCreator withRAREUIInsets:insets withFloat:thickness];
}

- (void)clipWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                            withFloat:(float)x
                            withFloat:(float)y
                            withFloat:(float)width
                            withFloat:(float)height {
  id<RAREiPlatformPath> p = [((id<RAREiShapeCreator>) nil_chk(shapeCreator_)) createClipShapeWithRAREiPlatformPath:nil withFloat:width withFloat:height withBoolean:NO];
  if (p != nil) {
    [((id<RAREiPlatformGraphics>) nil_chk(g)) translateWithFloat:x withFloat:y];
    [g clipShapeWithRAREiPlatformShape:p];
    [g translateWithFloat:-x withFloat:-y];
  }
  else {
    if (clipInsets_ == nil) {
      clipInsets_ = [[RAREUIInsets alloc] init];
    }
    RAREUIInsets *in = [self getBorderInsetsWithRAREUIInsets:clipInsets_];
    [((id<RAREiPlatformGraphics>) nil_chk(g)) clipRectWithFloat:x + ((RAREUIInsets *) nil_chk(in))->left_ withFloat:y + in->top_ withFloat:width - in->right_ - in->left_ withFloat:height - in->bottom_ - in->top_];
  }
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height
                           withBoolean:(BOOL)end {
  if (end != [self isPaintLast]) {
    return;
  }
  RAREUIColor *color = [self getPaintColorWithRAREiPlatformComponent:[((id<RAREiPlatformGraphics>) nil_chk(g)) getComponent]];
  if ([((RAREUIColor *) nil_chk(color)) getAlpha] == 0) {
    return;
  }
  id<RAREiPlatformShape> shape = [((id<RAREiShapeCreator>) nil_chk(shapeCreator_)) createShapeWithFloat:width withFloat:height];
  if (shape == nil) {
    return;
  }
  RAREUIColor *oldColor = [g getColor];
  [g setColorWithRAREUIColor:color];
  [g translateWithFloat:x withFloat:y];
  float sw = [g getStrokeWidth];
  [g setStrokeWidthWithFloat:thickness_];
  [g drawShapeWithRAREiPlatformShape:shape withFloat:x withFloat:y];
  [g setStrokeWidthWithFloat:sw];
  [g setColorWithRAREUIColor:oldColor];
}

- (void)setPadForArcWithBoolean:(BOOL)padForArc {
}

- (void)setShapeCreatorWithRAREiShapeCreator:(id<RAREiShapeCreator>)shapeCreator {
  self->shapeCreator_ = shapeCreator;
}

- (id<RAREiPlatformShape>)getShapeWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                                                  withFloat:(float)x
                                                  withFloat:(float)y
                                                  withFloat:(float)width
                                                  withFloat:(float)height {
  id<RAREiPlatformPath> p = [((id<RAREiShapeCreator>) nil_chk(shapeCreator_)) createClipShapeWithRAREiPlatformPath:nil withFloat:width withFloat:height withBoolean:NO];
  if (p != nil) {
    [p translateWithFloat:x withFloat:y];
  }
  return p;
}

- (id<RAREiShapeCreator>)getShapeCreator {
  return shapeCreator_;
}

- (BOOL)isPadForArc {
  return NO;
}

- (BOOL)isRectangular {
  return NO;
}

- (void)copyAllFieldsTo:(RAREaUIShapeBorder *)other {
  [super copyAllFieldsTo:other];
  other->shapeCreator_ = shapeCreator_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getShapeWithRAREiPlatformGraphics:withFloat:withFloat:withFloat:withFloat:", NULL, "LRAREiPlatformShape", 0x1, NULL },
    { "getShapeCreator", NULL, "LRAREiShapeCreator", 0x1, NULL },
    { "isPadForArc", NULL, "Z", 0x1, NULL },
    { "isRectangular", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREaUIShapeBorder = { "aUIShapeBorder", "com.appnativa.rare.ui.border", NULL, 0x401, 4, methods, 0, NULL, 0, NULL};
  return &_RAREaUIShapeBorder;
}

@end
