//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-datechooser/com/appnativa/rare/ui/calendar/iDateFilter.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREiDateFilter_H_
#define _RAREiDateFilter_H_

@class JavaUtilCalendar;

#import "JreEmulation.h"

@protocol RAREiDateFilter < NSObject, JavaObject >
- (JavaUtilCalendar *)getMaxDate;
- (JavaUtilCalendar *)getMinDate;
- (BOOL)isDateValidWithJavaUtilCalendar:(JavaUtilCalendar *)date;
@end

#define ComAppnativaRareUiCalendarIDateFilter RAREiDateFilter

#endif // _RAREiDateFilter_H_
