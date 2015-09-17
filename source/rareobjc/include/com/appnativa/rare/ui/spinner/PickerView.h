//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios-spinner/com/appnativa/rare/ui/spinner/PickerView.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREPickerView_H_
#define _RAREPickerView_H_

@class RAREChangeEvent;
@class RAREUIDimension;
@protocol JavaLangCharSequence;
@protocol RAREPickerView_iPickerHelper;
@protocol RAREiChangeListener;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"

@interface RAREPickerView : RAREView {
 @public
  id<RAREiChangeListener> changeListener_;
  RAREChangeEvent *changeEvent_PickerView_;
  id<RAREPickerView_iPickerHelper> pickerHelper_;
}

- (id)init;
- (void)setChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)changeListener;
- (void)setIsCircularWithBoolean:(BOOL)circular;
- (void)setPickerHelperWithRAREPickerView_iPickerHelper:(id<RAREPickerView_iPickerHelper>)pickerHelper;
- (void)setSelectedIndexWithInt:(int)index;
- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (id<RAREPickerView_iPickerHelper>)getPickerHelper;
- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth;
- (int)getSelectedIndex;
+ (id)createProxy;
- (void)disposeEx;
- (void)rowSelectedWithInt:(int)index;
- (void)copyAllFieldsTo:(RAREPickerView *)other;
@end

J2OBJC_FIELD_SETTER(RAREPickerView, changeListener_, id<RAREiChangeListener>)
J2OBJC_FIELD_SETTER(RAREPickerView, changeEvent_PickerView_, RAREChangeEvent *)
J2OBJC_FIELD_SETTER(RAREPickerView, pickerHelper_, id<RAREPickerView_iPickerHelper>)

typedef RAREPickerView ComAppnativaRareUiSpinnerPickerView;

@protocol RAREPickerView_iPickerHelper < NSObject, JavaObject >
- (void)renderValueWithInt:(int)row
                    withId:(id)nativeView;
- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size;
- (int)getRowCount;
- (id<JavaLangCharSequence>)getValueWithInt:(int)row;
@end

#endif // _RAREPickerView_H_
