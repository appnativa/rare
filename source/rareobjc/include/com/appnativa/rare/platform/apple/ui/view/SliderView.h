//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-slider/com/appnativa/rare/platform/apple/ui/view/SliderView.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARESliderView_H_
#define _RARESliderView_H_

@protocol RAREiChangeListener;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/ControlView.h"
#include "com/appnativa/rare/ui/iSlider.h"

@interface RARESliderView : RAREControlView < RAREiSlider > {
}

- (id)init;
- (void)disposeEx;
- (void)setMaximumWithInt:(int)maximum;
- (void)setMinimumWithInt:(int)minimum;
- (void)setMajorTickSpacingWithInt:(int)value;
- (void)setMinorTickSpacingWithInt:(int)value;
- (void)setValueWithInt:(int)value;
- (int)getValue;
- (void)setHorizontalWithBoolean:(BOOL)horizontal;
- (BOOL)isHorizontal;
- (int)getMaximum;
- (int)getMinimum;
- (void)setThumbOffsetWithInt:(int)off;
- (void)setChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (void)setShowTicksWithBoolean:(BOOL)show;
+ (id)createProxy;
@end

typedef RARESliderView ComAppnativaRarePlatformAppleUiViewSliderView;

#endif // _RARESliderView_H_
