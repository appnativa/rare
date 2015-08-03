//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/apple/ui/view/CheckBoxView.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARECheckBoxView_H_
#define _RARECheckBoxView_H_

@class RAREaCheckBoxWidget_StateEnum;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/CustomButtonView.h"

@interface RARECheckBoxView : RARECustomButtonView {
 @public
  BOOL isSwitch_;
  BOOL tristate_;
}

- (id)initWithBoolean:(BOOL)onOffSwitch;
- (void)makeTriState;
- (void)setCheckedWithBoolean:(BOOL)checked;
- (void)setIndeterminate;
- (void)setStateWithRAREaCheckBoxWidget_StateEnum:(RAREaCheckBoxWidget_StateEnum *)state;
- (RAREaCheckBoxWidget_StateEnum *)getState;
- (BOOL)isChecked;
- (BOOL)isIndeterminate;
- (BOOL)isTriState;
- (void)makeSwitch;
- (void)makeTriStateCheckboxWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                            withRAREiPlatformIcon:(id<RAREiPlatformIcon>)pressedIcon;
- (void)setDefaultIcons;
- (void)setButtonStyle;
- (void)copyAllFieldsTo:(RARECheckBoxView *)other;
@end

typedef RARECheckBoxView ComAppnativaRarePlatformAppleUiViewCheckBoxView;

#endif // _RARECheckBoxView_H_
