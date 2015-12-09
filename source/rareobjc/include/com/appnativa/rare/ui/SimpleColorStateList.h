//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/SimpleColorStateList.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RARESimpleColorStateList_H_
#define _RARESimpleColorStateList_H_

@class RAREUIColor;
@class RAREUTCharScanner;
@class RAREiPaintedButton_ButtonStateEnum;
@protocol JavaUtilMap;

#import "JreEmulation.h"

@interface RARESimpleColorStateList : NSObject {
 @public
  RAREUIColor *defaultColor_;
  RAREUIColor *disabledColor_;
  RAREUIColor *focusedColor_;
  RAREUIColor *pressedColor_;
  RAREUIColor *rolloverColor_;
  RAREUIColor *selectedColor_;
  RAREUIColor *selectedDisabledColor_;
  RAREUIColor *selectedPressedColor_;
}

- (id)init;
- (id)initWithRAREUIColor:(RAREUIColor *)defaultColor
          withRAREUIColor:(RAREUIColor *)disabledColor;
- (id)initWithRAREUIColor:(RAREUIColor *)defaultColor
          withRAREUIColor:(RAREUIColor *)disabledColor
          withRAREUIColor:(RAREUIColor *)selectedColor
          withRAREUIColor:(RAREUIColor *)selectedDisabledColor;
+ (RARESimpleColorStateList *)createWithRAREUTCharScanner:(RAREUTCharScanner *)sc
                                          withJavaUtilMap:(id<JavaUtilMap>)map;
- (void)setDefaultColorWithRAREUIColor:(RAREUIColor *)defaultColor;
- (void)setDisabledColorWithRAREUIColor:(RAREUIColor *)disabledColor;
- (void)setFocusedColorWithRAREUIColor:(RAREUIColor *)focusedColor;
- (void)setRolloverColorWithRAREUIColor:(RAREUIColor *)rolloverColor;
- (void)setSelectedColorWithRAREUIColor:(RAREUIColor *)selectedColor;
- (void)setSelectedDisabledColorWithRAREUIColor:(RAREUIColor *)selectedDisabledColor;
- (RAREUIColor *)getColorWithRAREiPaintedButton_ButtonStateEnum:(RAREiPaintedButton_ButtonStateEnum *)state;
- (RAREUIColor *)getDefaultColor;
- (RAREUIColor *)getDisabledColor;
- (RAREUIColor *)getFocusedColor;
- (RAREUIColor *)getPressedColor;
- (RAREUIColor *)getRolloverColor;
- (RAREUIColor *)getSelectedColor;
- (RAREUIColor *)getSelectedDisabledColor;
- (RAREUIColor *)getSelectedPressedColor;
- (void)setSelectedPressedColorWithRAREUIColor:(RAREUIColor *)selectedPressedColor;
- (void)setPressedColorWithRAREUIColor:(RAREUIColor *)pressedColor;
- (void)copyAllFieldsTo:(RARESimpleColorStateList *)other;
@end

J2OBJC_FIELD_SETTER(RARESimpleColorStateList, defaultColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RARESimpleColorStateList, disabledColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RARESimpleColorStateList, focusedColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RARESimpleColorStateList, pressedColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RARESimpleColorStateList, rolloverColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RARESimpleColorStateList, selectedColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RARESimpleColorStateList, selectedDisabledColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RARESimpleColorStateList, selectedPressedColor_, RAREUIColor *)

typedef RARESimpleColorStateList ComAppnativaRareUiSimpleColorStateList;

#endif // _RARESimpleColorStateList_H_
