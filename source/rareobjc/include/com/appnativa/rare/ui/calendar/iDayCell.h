//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-datechooser/com/appnativa/rare/ui/calendar/iDayCell.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiDayCell_H_
#define _RAREiDayCell_H_

#import "JreEmulation.h"

@protocol RAREiDayCell < NSObject, JavaObject >
- (int)getDay;
- (BOOL)isToday;
- (BOOL)isSelected;
- (void)setDayWithInt:(int)day;
- (void)setTodayWithBoolean:(BOOL)today;
- (void)setSelectedWithBoolean:(BOOL)selected;
- (void)setCurrentMonthWithBoolean:(BOOL)currentMonth;
- (BOOL)isCurrentMonth;
@end

#define ComAppnativaRareUiCalendarIDayCell RAREiDayCell

#endif // _RAREiDayCell_H_
