//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-datechooser/com/appnativa/rare/ui/calendar/DateViewManager.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/platform/apple/ui/view/DatePickerView.h"
#include "com/appnativa/rare/ui/Container.h"
#include "com/appnativa/rare/ui/calendar/DateViewManager.h"
#include "com/appnativa/rare/ui/calendar/aDateViewManager.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "java/util/Calendar.h"

@implementation RAREDateViewManager

- (void)dispose {
  [super dispose];
  updateListener_ = nil;
  picker_ = nil;
}

- (void)updateRangeWithLong:(long long int)startTime
                   withLong:(long long int)endTime {
  [((JavaUtilCalendar *) nil_chk(date_)) setTimeInMillisWithLong:startTime];
  if (endDate_ == nil) {
    endDate_ = [JavaUtilCalendar getInstance];
  }
  [((JavaUtilCalendar *) nil_chk(endDate_)) setTimeInMillisWithLong:startTime];
  if (okButton_ == nil && ![self isShowingDialog]) {
    [self fireEvent];
  }
}

- (void)updateTimeWithLong:(long long int)time {
  [((JavaUtilCalendar *) nil_chk(date_)) setTimeInMillisWithLong:time];
  stringValue_ = nil;
  if (okButton_ == nil && ![self isShowingDialog]) {
    [self fireEvent];
  }
}

- (void)setDateWithJavaUtilCalendar:(JavaUtilCalendar *)cal {
  [super setMinDateWithJavaUtilCalendar:cal];
  if (picker_ != nil) {
    [picker_ setMinDateWithJavaUtilCalendar:cal];
  }
}

- (void)setEndDateWithJavaUtilCalendar:(JavaUtilCalendar *)cal {
  [super setEndDateWithJavaUtilCalendar:cal];
  if (picker_ != nil) {
    [picker_ setEndDateWithLong:(cal == nil) ? [((JavaUtilCalendar *) nil_chk(date_)) getTimeInMillis] : [cal getTimeInMillis]];
  }
}

- (void)setMaxDateWithJavaUtilCalendar:(JavaUtilCalendar *)cal {
  [super setMaxDateWithJavaUtilCalendar:cal];
  if (picker_ != nil) {
    [picker_ setMaxDateWithJavaUtilCalendar:cal];
  }
}

- (void)setMinDateWithJavaUtilCalendar:(JavaUtilCalendar *)cal {
  [super setMinDateWithJavaUtilCalendar:cal];
  if (picker_ != nil) {
    [picker_ setMinDateWithJavaUtilCalendar:cal];
  }
}

- (void)setShowTimeWithBoolean:(BOOL)show {
  if (show != showTime_) {
    [super setShowTimeWithBoolean:show];
    if (picker_ != nil) {
      [picker_ setShowTimeWithBoolean:show];
    }
  }
}

- (void)setShowTimeOnlyWithBoolean:(BOOL)show {
  if (show != showTimeOnly_) {
    [super setShowTimeOnlyWithBoolean:show];
    if (picker_ != nil) {
      [picker_ setShowTimeOnlyWithBoolean:show];
    }
  }
}

- (id<RAREiPlatformComponent>)getDatePickerComponent {
  id<RAREiPlatformComponent> c;
  if (picker_ == nil) {
    picker_ = [[RAREDatePickerView alloc] initWithRAREDatePickerView_iUpdateListener:self withBoolean:showTime_ withBoolean:showTimeOnly_];
    c = [[RAREContainer alloc] initWithRAREParentView:picker_];
  }
  else {
    c = [picker_ getComponent];
  }
  ignoreChangeEvent_ = YES;
  [((RAREDatePickerView *) nil_chk(picker_)) setDateInMillisWithLong:[((JavaUtilCalendar *) nil_chk(date_)) getTimeInMillis]];
  ignoreChangeEvent_ = NO;
  return c;
}

- (void)setValueExWithJavaUtilCalendar:(JavaUtilCalendar *)cal {
  [super setValueExWithJavaUtilCalendar:cal];
  if (picker_ != nil) {
    [picker_ setDateInMillisWithLong:[((JavaUtilCalendar *) nil_chk(date_)) getTimeInMillis]];
  }
}

- (id)init {
  return [super init];
}

- (void)copyAllFieldsTo:(RAREDateViewManager *)other {
  [super copyAllFieldsTo:other];
  other->picker_ = picker_;
  other->updateListener_ = updateListener_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getDatePickerComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "setValueExWithJavaUtilCalendar:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "picker_", NULL, 0x4, "LRAREDatePickerView" },
    { "updateListener_", NULL, 0x4, "LRAREDatePickerView_iUpdateListener" },
  };
  static J2ObjcClassInfo _RAREDateViewManager = { "DateViewManager", "com.appnativa.rare.ui.calendar", NULL, 0x1, 2, methods, 2, fields, 0, NULL};
  return &_RAREDateViewManager;
}

@end
