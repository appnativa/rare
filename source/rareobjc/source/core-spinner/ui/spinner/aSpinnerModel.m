//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-spinner/com/appnativa/rare/ui/spinner/aSpinnerModel.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/ui/event/ChangeEvent.h"
#include "com/appnativa/rare/ui/event/EventListenerList.h"
#include "com/appnativa/rare/ui/event/iChangeListener.h"
#include "com/appnativa/rare/ui/spinner/aSpinnerModel.h"
#include "java/lang/UnsupportedOperationException.h"

@implementation RAREaSpinnerModel

- (void)addChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l {
  [((RAREEventListenerList *) nil_chk(listenerList_)) addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiChangeListener)] withId:l];
}

- (void)removeChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l {
  [((RAREEventListenerList *) nil_chk(listenerList_)) removeWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiChangeListener)] withId:l];
}

- (BOOL)isEditable {
  return editable_;
}

- (void)dispose {
  if (listenerList_ != nil) {
    [listenerList_ clear];
    listenerList_ = nil;
  }
}

- (void)setEditableWithBoolean:(BOOL)editable {
  self->editable_ = editable;
}

- (NSString *)toStringWithId:(id)value {
  return (value == nil) ? nil : [value description];
}

- (id)fromStringWithNSString:(NSString *)value {
  @throw [[JavaLangUnsupportedOperationException alloc] init];
}

- (void)fireStateChanged {
  IOSObjectArray *listeners = [((RAREEventListenerList *) nil_chk(listenerList_)) getListenerList];
  for (int i = (int) [((IOSObjectArray *) nil_chk(listeners)) count] - 2; i >= 0; i -= 2) {
    if (IOSObjectArray_Get(listeners, i) == [IOSClass classWithProtocol:@protocol(RAREiChangeListener)]) {
      if (changeEvent_ == nil) {
        changeEvent_ = [[RAREChangeEvent alloc] initWithId:self];
      }
      [((id<RAREiChangeListener>) check_protocol_cast(IOSObjectArray_Get(listeners, i + 1), @protocol(RAREiChangeListener))) stateChangedWithJavaUtilEventObject:changeEvent_];
    }
  }
}

- (id)getNextValue {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id)getPreviousValue {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id)getValue {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (BOOL)isCircular {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)setValueWithId:(id)param0 {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
}

- (id)init {
  if (self = [super init]) {
    listenerList_ = [[RAREEventListenerList alloc] init];
  }
  return self;
}

- (void)copyAllFieldsTo:(RAREaSpinnerModel *)other {
  [super copyAllFieldsTo:other];
  other->changeEvent_ = changeEvent_;
  other->editable_ = editable_;
  other->listenerList_ = listenerList_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isEditable", NULL, "Z", 0x1, NULL },
    { "toStringWithId:", NULL, "LNSString", 0x1, NULL },
    { "fromStringWithNSString:", NULL, "LNSObject", 0x1, NULL },
    { "fireStateChanged", NULL, "V", 0x4, NULL },
    { "getNextValue", NULL, "LNSObject", 0x401, NULL },
    { "getPreviousValue", NULL, "LNSObject", 0x401, NULL },
    { "getValue", NULL, "LNSObject", 0x401, NULL },
    { "isCircular", NULL, "Z", 0x401, NULL },
    { "setValueWithId:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "listenerList_", NULL, 0x4, "LRAREEventListenerList" },
    { "editable_", NULL, 0x0, "Z" },
  };
  static J2ObjcClassInfo _RAREaSpinnerModel = { "aSpinnerModel", "com.appnativa.rare.ui.spinner", NULL, 0x401, 9, methods, 2, fields, 0, NULL};
  return &_RAREaSpinnerModel;
}

@end
