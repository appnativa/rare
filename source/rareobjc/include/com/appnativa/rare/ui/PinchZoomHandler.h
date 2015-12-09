//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/PinchZoomHandler.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREPinchZoomHandler_H_
#define _RAREPinchZoomHandler_H_

@class RAREUIRectangle;

#import "JreEmulation.h"

@interface RAREPinchZoomHandler : NSObject {
 @public
  float baseScale_;
  float scale__;
  float centerPointStartX_;
  float centerPointStartY_;
  float percentageOfItemAtPinchPointX_;
  float percentageOfItemAtPinchPointY_;
  float currentOffsetX_;
  float currentOffsetY_;
  float newOffsetX_;
  float newOffsetY_;
  float currentWidth_;
  float currentHeight_;
  float baseWidth_;
  float baseHeight_;
  float newWidth_;
  float newHeight_;
  float startSpan_;
  float currentSpan_;
  float previousSpan_;
  float minScale_;
  float maxScale_;
  BOOL inProgress_;
}

- (id)initWithFloat:(float)min
          withFloat:(float)max;
- (void)resetRangeWithFloat:(float)min
                  withFloat:(float)max;
- (void)resetBoundsWithRAREUIRectangle:(RAREUIRectangle *)currentBounds
                             withFloat:(float)baseWidth
                             withFloat:(float)baseHeight
                             withFloat:(float)baseScale;
- (float)getScaledX;
- (float)getScaledY;
- (float)getScaledWidth;
- (float)getScaledHeight;
- (float)getScale;
- (void)doubleTabScaleWithFloat:(float)x
                      withFloat:(float)y
                      withFloat:(float)scale_;
- (void)scaleStartWithFloat:(float)focusX
                  withFloat:(float)focusY;
- (BOOL)scale__WithFloat:(float)focusX
               withFloat:(float)focusY
               withFloat:(float)scale_;
- (void)getBoundsWithRAREUIRectangle:(RAREUIRectangle *)rect;
- (void)scaleEndWithFloat:(float)focusX
                withFloat:(float)focusY;
- (BOOL)isInProgress;
- (void)copyAllFieldsTo:(RAREPinchZoomHandler *)other;
@end

typedef RAREPinchZoomHandler ComAppnativaRareUiPinchZoomHandler;

#endif // _RAREPinchZoomHandler_H_
