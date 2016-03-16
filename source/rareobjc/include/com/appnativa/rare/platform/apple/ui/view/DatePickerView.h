//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/DatePickerView.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREDatePickerView_H_
#define _RAREDatePickerView_H_

@class JavaUtilCalendar;
@class RAREActionComponent;
@class RAREUIColor;
@protocol RAREDatePickerView_iUpdateListener;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"

@interface RAREDatePickerView : RAREParentView {
 @public
  BOOL showTime_;
  BOOL showTimeOnly_;
  RAREActionComponent *noneButton_;
  RAREActionComponent *okButton_;
  RAREActionComponent *todayButton_;
  id<RAREDatePickerView_iUpdateListener> updateListener_;
}

- (id)initWithRAREDatePickerView_iUpdateListener:(id<RAREDatePickerView_iUpdateListener>)updateListener
                                     withBoolean:(BOOL)showTime
                                     withBoolean:(BOOL)showTimeOnly;
- (BOOL)setBackgroundColorExWithRAREUIColor:(RAREUIColor *)bg;
- (void)setDateWithJavaUtilCalendar:(JavaUtilCalendar *)date;
- (void)setDateInMillisWithLong:(long long int)date;
- (void)setEndDateWithLong:(long long int)date;
- (void)setMaxDateWithJavaUtilCalendar:(JavaUtilCalendar *)date;
- (void)setMinDateWithJavaUtilCalendar:(JavaUtilCalendar *)date;
- (void)setShowTimeWithBoolean:(BOOL)show;
- (void)setShowTimeOnlyWithBoolean:(BOOL)show;
- (void)setWeekdaysOnlyWithBoolean:(BOOL)weekdaysOnly;
- (void)setWeekendsOnlyWithBoolean:(BOOL)weekendsOnly;
+ (id)createProxyWithBoolean:(BOOL)showTime
                 withBoolean:(BOOL)showTimeOnly;
- (void)disposeEx;
- (void)rangeChangedWithLong:(long long int)startTime
                    withLong:(long long int)endTime;
- (void)timeChangedWithLong:(long long int)time;
- (void)copyAllFieldsTo:(RAREDatePickerView *)other;
@end

J2OBJC_FIELD_SETTER(RAREDatePickerView, noneButton_, RAREActionComponent *)
J2OBJC_FIELD_SETTER(RAREDatePickerView, okButton_, RAREActionComponent *)
J2OBJC_FIELD_SETTER(RAREDatePickerView, todayButton_, RAREActionComponent *)
J2OBJC_FIELD_SETTER(RAREDatePickerView, updateListener_, id<RAREDatePickerView_iUpdateListener>)

typedef RAREDatePickerView ComAppnativaRarePlatformAppleUiViewDatePickerView;

@protocol RAREDatePickerView_iUpdateListener < NSObject, JavaObject >
- (void)updateRangeWithLong:(long long int)startTime
                   withLong:(long long int)endTime;
- (void)updateTimeWithLong:(long long int)time;
@end

#endif // _RAREDatePickerView_H_
