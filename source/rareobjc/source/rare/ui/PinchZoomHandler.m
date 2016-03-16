//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/PinchZoomHandler.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/PinchZoomHandler.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/util/SNumber.h"

@implementation RAREPinchZoomHandler

- (id)initWithFloat:(float)min
          withFloat:(float)max {
  if (self = [super init]) {
    minScale_ = min;
    maxScale_ = max;
  }
  return self;
}

- (void)resetRangeWithFloat:(float)min
                  withFloat:(float)max {
  minScale_ = min;
  maxScale_ = max;
}

- (void)resetBoundsWithRAREUIRectangle:(RAREUIRectangle *)currentBounds
                             withFloat:(float)baseWidth
                             withFloat:(float)baseHeight
                             withFloat:(float)baseScale {
  newOffsetX_ = currentOffsetX_ = ((RAREUIRectangle *) nil_chk(currentBounds))->x_;
  newOffsetY_ = currentOffsetY_ = currentBounds->y_;
  newWidth_ = currentWidth_ = currentBounds->width_;
  newHeight_ = currentHeight_ = currentBounds->height_;
  self->baseWidth_ = baseWidth;
  self->baseHeight_ = baseHeight;
  self->baseScale_ = baseScale;
  self->scale__ = baseScale;
}

- (float)getScaledX {
  return newOffsetX_;
}

- (float)getScaledY {
  return newOffsetX_;
}

- (float)getScaledWidth {
  return newWidth_;
}

- (float)getScaledHeight {
  return newHeight_;
}

- (float)getScale {
  return scale__;
}

- (void)doubleTabScaleWithFloat:(float)x
                      withFloat:(float)y
                      withFloat:(float)scale_ {
  [self scaleStartWithFloat:x withFloat:y];
  [self scale__WithFloat:x withFloat:y withFloat:scale_];
  [self scaleEndWithFloat:x withFloat:y];
}

- (void)scaleStartWithFloat:(float)focusX
                  withFloat:(float)focusY {
  inProgress_ = YES;
  centerPointStartX_ = focusX;
  centerPointStartY_ = focusY;
  percentageOfItemAtPinchPointX_ = (centerPointStartX_ - currentOffsetX_) / currentWidth_;
  percentageOfItemAtPinchPointY_ = (centerPointStartY_ - currentOffsetY_) / currentHeight_;
}

- (BOOL)scale__WithFloat:(float)focusX
               withFloat:(float)focusY
               withFloat:(float)scale_ {
  scale_ = baseScale_ * scale_;
  if (scale_ < minScale_) {
    scale_ = minScale_;
  }
  if (scale_ > maxScale_) {
    scale_ = maxScale_;
  }
  if ([RAREUTSNumber isEqualWithFloat:scale_ withFloat:self->scale__]) {
    return NO;
  }
  self->scale__ = scale_;
  float centerPointEndX = focusX;
  float centerPointEndY = focusY;
  newWidth_ = baseWidth_ * scale_;
  newHeight_ = baseHeight_ * scale_;
  float translateFromZoomingX = (currentWidth_ - newWidth_) * percentageOfItemAtPinchPointX_;
  float translateFromZoomingY = (currentHeight_ - newHeight_) * percentageOfItemAtPinchPointY_;
  float translateFromTranslatingX = centerPointEndX - centerPointStartX_;
  float translateFromTranslatingY = centerPointEndY - centerPointStartY_;
  float translateTotalX = translateFromZoomingX + translateFromTranslatingX;
  float translateTotalY = translateFromZoomingY + translateFromTranslatingY;
  newOffsetX_ = currentOffsetX_ + translateTotalX;
  newOffsetY_ = currentOffsetY_ + translateTotalY;
  return YES;
}

- (void)getBoundsWithRAREUIRectangle:(RAREUIRectangle *)rect {
  [((RAREUIRectangle *) nil_chk(rect)) setWithFloat:newOffsetX_ withFloat:newOffsetY_ withFloat:newWidth_ withFloat:newHeight_];
}

- (void)scaleEndWithFloat:(float)focusX
                withFloat:(float)focusY {
  inProgress_ = NO;
}

- (BOOL)isInProgress {
  return inProgress_;
}

- (void)copyAllFieldsTo:(RAREPinchZoomHandler *)other {
  [super copyAllFieldsTo:other];
  other->baseHeight_ = baseHeight_;
  other->baseScale_ = baseScale_;
  other->baseWidth_ = baseWidth_;
  other->centerPointStartX_ = centerPointStartX_;
  other->centerPointStartY_ = centerPointStartY_;
  other->currentHeight_ = currentHeight_;
  other->currentOffsetX_ = currentOffsetX_;
  other->currentOffsetY_ = currentOffsetY_;
  other->currentSpan_ = currentSpan_;
  other->currentWidth_ = currentWidth_;
  other->inProgress_ = inProgress_;
  other->maxScale_ = maxScale_;
  other->minScale_ = minScale_;
  other->newHeight_ = newHeight_;
  other->newOffsetX_ = newOffsetX_;
  other->newOffsetY_ = newOffsetY_;
  other->newWidth_ = newWidth_;
  other->percentageOfItemAtPinchPointX_ = percentageOfItemAtPinchPointX_;
  other->percentageOfItemAtPinchPointY_ = percentageOfItemAtPinchPointY_;
  other->previousSpan_ = previousSpan_;
  other->scale__ = scale__;
  other->startSpan_ = startSpan_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "scale__WithFloat:withFloat:withFloat:", NULL, "Z", 0x1, NULL },
    { "isInProgress", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "baseScale_", NULL, 0x0, "F" },
    { "scale__", "scale", 0x0, "F" },
    { "centerPointStartX_", NULL, 0x0, "F" },
    { "centerPointStartY_", NULL, 0x0, "F" },
    { "percentageOfItemAtPinchPointX_", NULL, 0x0, "F" },
    { "percentageOfItemAtPinchPointY_", NULL, 0x0, "F" },
    { "currentOffsetX_", NULL, 0x0, "F" },
    { "currentOffsetY_", NULL, 0x0, "F" },
    { "newOffsetX_", NULL, 0x0, "F" },
    { "newOffsetY_", NULL, 0x0, "F" },
    { "currentWidth_", NULL, 0x0, "F" },
    { "currentHeight_", NULL, 0x0, "F" },
    { "baseWidth_", NULL, 0x0, "F" },
    { "baseHeight_", NULL, 0x0, "F" },
    { "newWidth_", NULL, 0x0, "F" },
    { "newHeight_", NULL, 0x0, "F" },
    { "startSpan_", NULL, 0x0, "F" },
    { "currentSpan_", NULL, 0x0, "F" },
    { "previousSpan_", NULL, 0x0, "F" },
    { "minScale_", NULL, 0x0, "F" },
    { "maxScale_", NULL, 0x0, "F" },
    { "inProgress_", NULL, 0x0, "Z" },
  };
  static J2ObjcClassInfo _RAREPinchZoomHandler = { "PinchZoomHandler", "com.appnativa.rare.ui", NULL, 0x1, 2, methods, 22, fields, 0, NULL};
  return &_RAREPinchZoomHandler;
}

@end
