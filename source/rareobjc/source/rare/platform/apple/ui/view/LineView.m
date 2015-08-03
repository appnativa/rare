//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/LineView.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#include "com/appnativa/rare/platform/apple/ui/view/LineView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIScreen.h"
#include "com/appnativa/rare/ui/aLineHelper.h"
#import "RAREAPView.h"

@implementation RARELineView

- (id)init {
  if (self = [super initWithId:[RARELineView createProxy]]) {
    lineHelper_ = [[RARELineView_LineHelper alloc] initWithRARELineView:self withBoolean:YES];
    [super setPaintHandlerEnabledWithBoolean:YES];
  }
  return self;
}

- (void)paintBackgroundWithRAREAppleGraphics:(RAREAppleGraphics *)g
                                withRAREView:(RAREView *)v
                         withRAREUIRectangle:(RAREUIRectangle *)rect {
  [super paintBackgroundWithRAREAppleGraphics:g withRAREView:v withRAREUIRectangle:rect];
  [((RARELineView_LineHelper *) nil_chk(lineHelper_)) paintWithRAREiPlatformGraphics:g withFloat:((RAREUIRectangle *) nil_chk(rect))->x_ withFloat:rect->y_ withFloat:rect->width_ withFloat:rect->height_];
}

- (RAREaLineHelper *)getLineHelper {
  return lineHelper_;
}

- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size {
  ((RAREUIDimension *) nil_chk(size))->width_ = size->height_ = [RAREUIScreen snapToSizeWithFloat:[((RARELineView_LineHelper *) nil_chk(lineHelper_)) getThickness]];
}

- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  float th = [((RARELineView_LineHelper *) nil_chk(lineHelper_)) getThickness];
  if ([lineHelper_ isStandardLine]) {
    th *= 2;
  }
  ((RAREUIDimension *) nil_chk(size))->width_ = size->height_ = [RAREUIScreen snapToSizeWithFloat:th];
}

- (BOOL)isMouseTransparent {
  return (mouseListener_ == nil) || ((mouseMotionListener_ == nil) && !mouseGestureListenerAdded_);
}

- (void)disposeEx {
  [super disposeEx];
  lineHelper_ = nil;
}

- (void)setEnabledExWithBoolean:(BOOL)b {
}

+ (id)createProxy {
  return [[RAREAPView alloc]init];
}

- (void)copyAllFieldsTo:(RARELineView *)other {
  [super copyAllFieldsTo:other];
  other->lineHelper_ = lineHelper_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getLineHelper", NULL, "LRAREaLineHelper", 0x1, NULL },
    { "isMouseTransparent", NULL, "Z", 0x1, NULL },
    { "disposeEx", NULL, "V", 0x4, NULL },
    { "setEnabledExWithBoolean:", NULL, "V", 0x4, NULL },
    { "createProxy", NULL, "LNSObject", 0x10a, NULL },
  };
  static J2ObjcClassInfo _RARELineView = { "LineView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 5, methods, 0, NULL, 0, NULL};
  return &_RARELineView;
}

@end
@implementation RARELineView_LineHelper

- (id)initWithRARELineView:(RARELineView *)outer$
               withBoolean:(BOOL)horizontal {
  return [super initWithBoolean:horizontal];
}

- (void)thicknessRecalculated {
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "thicknessRecalculated", NULL, "V", 0x4, NULL },
  };
  static J2ObjcClassInfo _RARELineView_LineHelper = { "LineHelper", "com.appnativa.rare.platform.apple.ui.view", "LineView", 0x0, 1, methods, 0, NULL, 0, NULL};
  return &_RARELineView_LineHelper;
}

@end