//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/DatePickerView.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/platform/apple/ui/view/DatePickerView.h"
#include "com/appnativa/rare/platform/apple/ui/view/aView.h"
#include "com/appnativa/rare/ui/ActionComponent.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "java/util/Calendar.h"
#import "RAREAPDatePicker.h"
 #import "APView+Component.h"

@implementation RAREDatePickerView

- (id)initWithRAREDatePickerView_iUpdateListener:(id<RAREDatePickerView_iUpdateListener>)updateListener
                                     withBoolean:(BOOL)showTime
                                     withBoolean:(BOOL)showTimeOnly {
  if (self = [super initWithId:[RAREDatePickerView createProxyWithBoolean:showTime withBoolean:showTimeOnly]]) {
    self->updateListener_ = updateListener;
    self->showTime_ = showTime;
    self->showTimeOnly_ = showTimeOnly;
    RAREUIColor *c = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getColorWithNSString:@"Rare.DateChooser.background"];
    if (c == nil) {
      c = [RAREColorUtils getBackground];
    }
    [self setBackgroundColorWithRAREUIColor:c];
  }
  return self;
}

- (BOOL)setBackgroundColorExWithRAREUIColor:(RAREUIColor *)bg {
  ((RAREAPDatePicker*)proxy_).backgroundColor=(UIColor*)[bg getAPColor];
  return YES;
}

- (void)setDateWithJavaUtilCalendar:(JavaUtilCalendar *)date {
  [((RAREAPDatePicker*)proxy_) setDateValue:[NSDate fromJavaCalendar: date ]];
}

- (void)setDateInMillisWithLong:(long long int)date {
  [((RAREAPDatePicker*)proxy_) setDateInMillis: date];
}

- (void)setEndDateWithLong:(long long int)date {
  if(date) {
    [((RAREAPDatePicker*)proxy_) setEndDateInMillis: date];
  }
}

- (void)setMaxDateWithJavaUtilCalendar:(JavaUtilCalendar *)date {
  if(date) {
    [((RAREAPDatePicker*)proxy_) setMaxDate:[NSDate fromJavaCalendar: date ]];
  }
  else {
    [((RAREAPDatePicker*)proxy_) setMinDate: nil];
  }
}

- (void)setMinDateWithJavaUtilCalendar:(JavaUtilCalendar *)date {
  if(date) {
    [((RAREAPDatePicker*)proxy_) setMinDate:[NSDate fromJavaCalendar: date ]];
  }
  else {
    [((RAREAPDatePicker*)proxy_) setMinDate: nil];
  }
}

- (void)setShowTimeWithBoolean:(BOOL)show {
  [((RAREAPDatePicker*)proxy_) setShowTime: show];
}

- (void)setShowTimeOnlyWithBoolean:(BOOL)show {
  [((RAREAPDatePicker*)proxy_) setShowTimeOnly: show];
}

- (void)setWeekdaysOnlyWithBoolean:(BOOL)weekdaysOnly {
  ((RAREAPDatePicker*)proxy_).weekdaysOnly=weekdaysOnly;
}

- (void)setWeekendsOnlyWithBoolean:(BOOL)weekendsOnly {
  ((RAREAPDatePicker*)proxy_).weekendsOnly=weekendsOnly;
}

+ (id)createProxyWithBoolean:(BOOL)showTime
                 withBoolean:(BOOL)showTimeOnly {
  return [[RAREAPDatePicker alloc] initWithShowTime: showTime andShowTimeOnly: showTimeOnly];
}

- (void)disposeEx {
  [super disposeEx];
  updateListener_ = nil;
}

- (void)rangeChangedWithLong:(long long int)startTime
                    withLong:(long long int)endTime {
  if (updateListener_ != nil) {
    [updateListener_ updateRangeWithLong:startTime withLong:endTime];
  }
}

- (void)timeChangedWithLong:(long long int)time {
  if (updateListener_ != nil) {
    [updateListener_ updateTimeWithLong:time];
  }
}

- (void)copyAllFieldsTo:(RAREDatePickerView *)other {
  [super copyAllFieldsTo:other];
  other->noneButton_ = noneButton_;
  other->okButton_ = okButton_;
  other->showTime_ = showTime_;
  other->showTimeOnly_ = showTimeOnly_;
  other->todayButton_ = todayButton_;
  other->updateListener_ = updateListener_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setBackgroundColorExWithRAREUIColor:", NULL, "Z", 0x101, NULL },
    { "setDateWithJavaUtilCalendar:", NULL, "V", 0x101, NULL },
    { "setDateInMillisWithLong:", NULL, "V", 0x101, NULL },
    { "setEndDateWithLong:", NULL, "V", 0x101, NULL },
    { "setMaxDateWithJavaUtilCalendar:", NULL, "V", 0x101, NULL },
    { "setMinDateWithJavaUtilCalendar:", NULL, "V", 0x101, NULL },
    { "setShowTimeWithBoolean:", NULL, "V", 0x101, NULL },
    { "setShowTimeOnlyWithBoolean:", NULL, "V", 0x101, NULL },
    { "setWeekdaysOnlyWithBoolean:", NULL, "V", 0x101, NULL },
    { "setWeekendsOnlyWithBoolean:", NULL, "V", 0x101, NULL },
    { "createProxyWithBoolean:withBoolean:", NULL, "LNSObject", 0x108, NULL },
    { "disposeEx", NULL, "V", 0x4, NULL },
    { "rangeChangedWithLong:withLong:", NULL, "V", 0x4, NULL },
    { "timeChangedWithLong:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "showTime_", NULL, 0x0, "Z" },
    { "showTimeOnly_", NULL, 0x0, "Z" },
    { "noneButton_", NULL, 0x4, "LRAREActionComponent" },
    { "okButton_", NULL, 0x4, "LRAREActionComponent" },
    { "todayButton_", NULL, 0x4, "LRAREActionComponent" },
    { "updateListener_", NULL, 0x4, "LRAREDatePickerView_iUpdateListener" },
  };
  static J2ObjcClassInfo _RAREDatePickerView = { "DatePickerView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 14, methods, 6, fields, 0, NULL};
  return &_RAREDatePickerView;
}

@end

@interface RAREDatePickerView_iUpdateListener : NSObject
@end

@implementation RAREDatePickerView_iUpdateListener

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "updateRangeWithLong:withLong:", NULL, "V", 0x401, NULL },
    { "updateTimeWithLong:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREDatePickerView_iUpdateListener = { "iUpdateListener", "com.appnativa.rare.platform.apple.ui.view", "DatePickerView", 0x201, 2, methods, 0, NULL, 0, NULL};
  return &_RAREDatePickerView_iUpdateListener;
}

@end
