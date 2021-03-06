//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/apple/ui/view/ControlView.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREControlView_H_
#define _RAREControlView_H_

@protocol RAREiChangeListener;
@protocol RAREiPlatformBorder;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"

@interface RAREControlView : RAREView {
 @public
  id<RAREiChangeListener> changeListener_;
}

- (id)initWithId:(id)uicontrol;
- (void)setChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (id)init;
- (void)borderChangedWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)newBorder;
- (void)handleChangeEvent;
- (void)setSelectedWithBoolean:(BOOL)selected;
- (BOOL)isPressed;
- (BOOL)isSelected;
- (void)setEnabledExWithBoolean:(BOOL)b;
- (void)copyAllFieldsTo:(RAREControlView *)other;
@end

J2OBJC_FIELD_SETTER(RAREControlView, changeListener_, id<RAREiChangeListener>)

typedef RAREControlView ComAppnativaRarePlatformAppleUiViewControlView;

#endif // _RAREControlView_H_
