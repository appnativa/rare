//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-datechooser/com/appnativa/rare/ui/calendar/DateViewManager.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREDateViewManager_H_
#define _RAREDateViewManager_H_

@class JavaUtilCalendar;
@class RAREDatePickerView;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/DatePickerView.h"
#include "com/appnativa/rare/ui/calendar/aDateViewManager.h"

@interface RAREDateViewManager : RAREaDateViewManager < RAREDatePickerView_iUpdateListener > {
 @public
  RAREDatePickerView *picker_;
  id<RAREDatePickerView_iUpdateListener> updateListener_;
}

- (void)dispose;
- (void)updateRangeWithLong:(long long int)startTime
                   withLong:(long long int)endTime;
- (void)updateTimeWithLong:(long long int)time;
- (void)setDateWithJavaUtilCalendar:(JavaUtilCalendar *)cal;
- (void)setEndDateWithJavaUtilCalendar:(JavaUtilCalendar *)cal;
- (void)setMaxDateWithJavaUtilCalendar:(JavaUtilCalendar *)cal;
- (void)setMinDateWithJavaUtilCalendar:(JavaUtilCalendar *)cal;
- (void)setShowTimeWithBoolean:(BOOL)show;
- (void)setShowTimeOnlyWithBoolean:(BOOL)show;
- (id<RAREiPlatformComponent>)getDatePickerComponent;
- (void)setValueExWithJavaUtilCalendar:(JavaUtilCalendar *)cal;
- (id)init;
- (void)copyAllFieldsTo:(RAREDateViewManager *)other;
@end

J2OBJC_FIELD_SETTER(RAREDateViewManager, picker_, RAREDatePickerView *)
J2OBJC_FIELD_SETTER(RAREDateViewManager, updateListener_, id<RAREDatePickerView_iUpdateListener>)

typedef RAREDateViewManager ComAppnativaRareUiCalendarDateViewManager;

#endif // _RAREDateViewManager_H_
