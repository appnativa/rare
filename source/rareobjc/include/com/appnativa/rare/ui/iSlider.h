//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iSlider.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREiSlider_H_
#define _RAREiSlider_H_

#import "JreEmulation.h"

@protocol RAREiSlider < NSObject, JavaObject >
- (void)setHorizontalWithBoolean:(BOOL)horizontal;
- (void)setMajorTickSpacingWithInt:(int)value;
- (void)setMaximumWithInt:(int)maximum;
- (void)setMinimumWithInt:(int)minimum;
- (void)setMinorTickSpacingWithInt:(int)value;
- (void)setShowTicksWithBoolean:(BOOL)show;
- (void)setThumbOffsetWithInt:(int)off;
- (void)setValueWithInt:(int)value;
- (int)getMaximum;
- (int)getMinimum;
- (int)getValue;
- (BOOL)isHorizontal;
@end

#define ComAppnativaRareUiISlider RAREiSlider

#endif // _RAREiSlider_H_
