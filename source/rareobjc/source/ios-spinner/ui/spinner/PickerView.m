//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios-spinner/com/appnativa/rare/ui/spinner/PickerView.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/event/ChangeEvent.h"
#include "com/appnativa/rare/ui/event/iChangeListener.h"
#include "com/appnativa/rare/ui/spinner/PickerView.h"
#include "java/lang/CharSequence.h"
#import "RAREUIPickerView.h"

@implementation RAREPickerView

- (id)init {
  return [super initWithId:[RAREPickerView createProxy]];
}

- (void)setChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)changeListener {
  self->changeListener_ = changeListener;
}

- (void)setIsCircularWithBoolean:(BOOL)circular {
  ((RAREUIPickerView*)proxy_).isCircular=circular;
}

- (void)setPickerHelperWithRAREPickerView_iPickerHelper:(id<RAREPickerView_iPickerHelper>)pickerHelper {
  self->pickerHelper_ = pickerHelper;
}

- (void)setSelectedIndexWithInt:(int)index {
  [((RAREUIPickerView*)proxy_) setSelectedIndexEx: index];
}

- (void)getMinimumSizeWithRAREUIDimension:(RAREUIDimension *)size {
  [((id<RAREPickerView_iPickerHelper>) nil_chk(pickerHelper_)) getMinimumSizeWithRAREUIDimension:size];
}

- (id<RAREPickerView_iPickerHelper>)getPickerHelper {
  return pickerHelper_;
}

- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  [((id<RAREPickerView_iPickerHelper>) nil_chk(pickerHelper_)) getMinimumSizeWithRAREUIDimension:size];
  float width = ((RAREUIDimension *) nil_chk(size))->width_;
  [super getPreferredSizeWithRAREUIDimension:size withFloat:maxWidth];
  size->width_ = width;
}

- (int)getSelectedIndex {
  return [((RAREUIPickerView*)proxy_) getSelectedIndexEx];
}

+ (id)createProxy {
  return [RAREUIPickerView new];
}

- (void)disposeEx {
  [super disposeEx];
  changeEvent_PickerView_ = nil;
  pickerHelper_ = nil;
  changeListener_ = nil;
}

- (void)rowSelectedWithInt:(int)index {
  if (changeListener_ != nil) {
    if (changeEvent_PickerView_ == nil) {
      changeEvent_PickerView_ = [[RAREChangeEvent alloc] initWithId:self];
    }
    [changeListener_ stateChangedWithJavaUtilEventObject:changeEvent_PickerView_];
  }
}

- (void)copyAllFieldsTo:(RAREPickerView *)other {
  [super copyAllFieldsTo:other];
  other->changeEvent_PickerView_ = changeEvent_PickerView_;
  other->changeListener_ = changeListener_;
  other->pickerHelper_ = pickerHelper_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setIsCircularWithBoolean:", NULL, "V", 0x101, NULL },
    { "setSelectedIndexWithInt:", NULL, "V", 0x101, NULL },
    { "getPickerHelper", NULL, "LRAREPickerView_iPickerHelper", 0x1, NULL },
    { "getSelectedIndex", NULL, "I", 0x101, NULL },
    { "createProxy", NULL, "LNSObject", 0x108, NULL },
    { "disposeEx", NULL, "V", 0x4, NULL },
    { "rowSelectedWithInt:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "changeListener_", NULL, 0x0, "LRAREiChangeListener" },
  };
  static J2ObjcClassInfo _RAREPickerView = { "PickerView", "com.appnativa.rare.ui.spinner", NULL, 0x1, 7, methods, 1, fields, 0, NULL};
  return &_RAREPickerView;
}

@end

@interface RAREPickerView_iPickerHelper : NSObject
@end

@implementation RAREPickerView_iPickerHelper

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "renderValueWithInt:withId:", NULL, "V", 0x401, NULL },
    { "getMinimumSizeWithRAREUIDimension:", NULL, "V", 0x401, NULL },
    { "getRowCount", NULL, "I", 0x401, NULL },
    { "getValueWithInt:", NULL, "LJavaLangCharSequence", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREPickerView_iPickerHelper = { "iPickerHelper", "com.appnativa.rare.ui.spinner", "PickerView", 0x201, 4, methods, 0, NULL, 0, NULL};
  return &_RAREPickerView_iPickerHelper;
}

@end