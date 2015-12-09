//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios-spinner/com/appnativa/rare/ui/spinner/DatePickerEditor.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/platform/apple/ui/view/DatePickerView.h"
#include "com/appnativa/rare/ui/spinner/DatePickerEditor.h"
#include "com/appnativa/rare/ui/spinner/SpinnerDateModel.h"
#include "com/appnativa/rare/ui/spinner/iSpinnerModel.h"
#include "java/util/Calendar.h"
#include "java/util/Date.h"
#include "java/util/EventObject.h"

@implementation RAREDatePickerEditor

- (id)initWithRAREiSpinnerModel:(id<RAREiSpinnerModel>)model {
  if (self = [super initWithRAREiSpinnerModel:model]) {
    date_ = [JavaUtilCalendar getInstance];
    RARESpinnerDateModel *dm = (RARESpinnerDateModel *) check_class_cast(model, [RARESpinnerDateModel class]);
    max_ = [((RARESpinnerDateModel *) nil_chk(dm)) getMaximum];
    min_ = [dm getMinimum];
    RAREDatePickerView *pv = [[RAREDatePickerView alloc] initWithRAREDatePickerView_iUpdateListener:self withBoolean:[dm isShowTime] withBoolean:[dm isShowTimeOnly]];
    if (max_ != nil) {
      [((JavaUtilCalendar *) nil_chk(date_)) setTimeWithJavaUtilDate:max_];
      [pv setMaxDateWithJavaUtilCalendar:date_];
    }
    if (min_ != nil) {
      [((JavaUtilCalendar *) nil_chk(date_)) setTimeWithJavaUtilDate:min_];
      [pv setMinDateWithJavaUtilCalendar:date_];
    }
    editorView_ = pv;
    [((id<RAREiSpinnerModel>) nil_chk(model)) addChangeListenerWithRAREiChangeListener:self];
  }
  return self;
}

- (void)setValueWithId:(id)value {
}

- (id)getValue {
  return nil;
}

- (void)updateTimeWithLong:(long long int)time {
  [((JavaUtilCalendar *) nil_chk(date_)) setTimeInMillisWithLong:time];
  [((id<RAREiSpinnerModel>) nil_chk(spinnerModel_)) setValueWithId:date_];
}

- (void)updateRangeWithLong:(long long int)startTime
                   withLong:(long long int)endTime {
}

- (void)stateChangedWithJavaUtilEventObject:(JavaUtilEventObject *)e {
  RARESpinnerDateModel *dm = (RARESpinnerDateModel *) check_class_cast(spinnerModel_, [RARESpinnerDateModel class]);
  max_ = [((RARESpinnerDateModel *) nil_chk(dm)) getMaximum];
  min_ = [dm getMinimum];
  RAREDatePickerView *pv = (RAREDatePickerView *) check_class_cast(editorView_, [RAREDatePickerView class]);
  if (max_ == nil) {
    [((RAREDatePickerView *) nil_chk(pv)) setMaxDateWithJavaUtilCalendar:nil];
  }
  else {
    [((JavaUtilCalendar *) nil_chk(date_)) setTimeWithJavaUtilDate:max_];
    [((RAREDatePickerView *) nil_chk(pv)) setMaxDateWithJavaUtilCalendar:date_];
  }
  if (min_ == nil) {
    [((RAREDatePickerView *) nil_chk(pv)) setMinDateWithJavaUtilCalendar:nil];
  }
  else {
    [((JavaUtilCalendar *) nil_chk(date_)) setTimeWithJavaUtilDate:min_];
    [((RAREDatePickerView *) nil_chk(pv)) setMinDateWithJavaUtilCalendar:date_];
  }
}

- (BOOL)isTextField {
  return NO;
}

- (id)removeSelectedDataWithBoolean:(BOOL)returnData {
  return nil;
}

- (void)selectField {
}

- (void)copyAllFieldsTo:(RAREDatePickerEditor *)other {
  [super copyAllFieldsTo:other];
  other->date_ = date_;
  other->max_ = max_;
  other->min_ = min_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getValue", NULL, "LNSObject", 0x1, NULL },
    { "isTextField", NULL, "Z", 0x1, NULL },
    { "removeSelectedDataWithBoolean:", NULL, "LNSObject", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "date_", NULL, 0x0, "LJavaUtilCalendar" },
    { "max_", NULL, 0x0, "LJavaUtilDate" },
    { "min_", NULL, 0x0, "LJavaUtilDate" },
  };
  static J2ObjcClassInfo _RAREDatePickerEditor = { "DatePickerEditor", "com.appnativa.rare.ui.spinner", NULL, 0x1, 3, methods, 3, fields, 0, NULL};
  return &_RAREDatePickerEditor;
}

@end
