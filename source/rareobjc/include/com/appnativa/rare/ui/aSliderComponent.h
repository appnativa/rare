//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-slider/com/appnativa/rare/ui/aSliderComponent.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaSliderComponent_H_
#define _RAREaSliderComponent_H_

@class JavaUtilEventObject;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/XPComponent.h"
#include "com/appnativa/rare/ui/event/iChangeListener.h"
#include "com/appnativa/rare/ui/iChangeable.h"
#include "com/appnativa/rare/ui/iSlider.h"

@interface RAREaSliderComponent : RAREXPComponent < RAREiChangeable, RAREiSlider, RAREiChangeListener > {
 @public
  id<RAREiSlider> slider_;
}

- (id)initWithId:(id)view;
- (void)addChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (void)removeChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (void)stateChangedWithJavaUtilEventObject:(JavaUtilEventObject *)e;
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
- (void)copyAllFieldsTo:(RAREaSliderComponent *)other;
@end

J2OBJC_FIELD_SETTER(RAREaSliderComponent, slider_, id<RAREiSlider>)

typedef RAREaSliderComponent ComAppnativaRareUiASliderComponent;

#endif // _RAREaSliderComponent_H_
