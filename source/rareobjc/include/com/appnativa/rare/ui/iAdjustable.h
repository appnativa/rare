//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iAdjustable.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREiAdjustable_H_
#define _RAREiAdjustable_H_

@protocol RAREiChangeListener;

#import "JreEmulation.h"

@protocol RAREiAdjustable < NSObject, JavaObject >
- (void)setMinimumWithDouble:(double)min;
- (double)getMinimum;
- (void)setMaximumWithDouble:(double)max;
- (double)getMaximum;
- (void)setUnitIncrementWithDouble:(double)inc;
- (double)getUnitIncrement;
- (void)setBlockIncrementWithDouble:(double)inc;
- (double)getBlockIncrement;
- (void)setVisibleAmountWithDouble:(double)v;
- (double)getVisibleAmount;
- (void)setValueWithDouble:(double)v;
- (double)getValue;
- (BOOL)isAdjusting;
- (void)addChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (void)removeChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
@end

#define ComAppnativaRareUiIAdjustable RAREiAdjustable

#endif // _RAREiAdjustable_H_
