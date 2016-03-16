//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-datechooser/com/appnativa/rare/ui/calendar/DateSelectionModel.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/ui/calendar/DateSelectionModel.h"
#include "com/appnativa/rare/ui/event/ChangeEvent.h"
#include "com/appnativa/rare/ui/event/EventListenerList.h"
#include "com/appnativa/rare/ui/event/iChangeListener.h"
#include "com/appnativa/util/Helper.h"
#include "java/lang/Math.h"
#include "java/util/ArrayList.h"
#include "java/util/Calendar.h"
#include "java/util/Date.h"

@implementation RAREDateSelectionModel

+ (int)MULTIPLE_INTERVAL_SELECTION {
  return RAREDateSelectionModel_MULTIPLE_INTERVAL_SELECTION;
}

+ (int)SINGLE_INTERVAL_SELECTION {
  return RAREDateSelectionModel_SINGLE_INTERVAL_SELECTION;
}

+ (int)SINGLE_SELECTION {
  return RAREDateSelectionModel_SINGLE_SELECTION;
}

- (void)addChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l {
  if (listenerList_ == nil) {
    listenerList_ = [[RAREEventListenerList alloc] init];
    [listenerList_ addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiChangeListener)] withId:l];
  }
  else {
    [listenerList_ removeWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiChangeListener)] withId:l];
    [listenerList_ addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiChangeListener)] withId:l];
  }
}

- (void)addSelectedDateWithJavaUtilDate:(JavaUtilDate *)date {
  if ((selectionMode_ == RAREDateSelectionModel_SINGLE_SELECTION) || ((selectedDate_ == nil) && ((selectedDates_ == nil) || [selectedDates_ isEmpty]))) {
    [self setSelectedDateWithJavaUtilDate:date];
  }
  else if ([self addSelectedDateExWithJavaUtilDate:date]) {
    [self fireChangeEvent];
  }
}

- (void)clearSelection {
  if (![self isSelectionEmpty]) {
    [self clearSelectionEx];
    [self fireChangeEvent];
  }
}

- (void)removeChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l {
  if (listenerList_ != nil) {
    [listenerList_ removeWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiChangeListener)] withId:l];
  }
}

- (void)setLeadSelectionDateWithJavaUtilDate:(JavaUtilDate *)date {
  if (anchorDate_ == nil) {
    [self setSelectedDateWithJavaUtilDate:date];
  }
  else {
    JavaUtilDate *a = anchorDate_;
    [self clearSelectionEx];
    anchorDate_ = a;
    selectedDate_ = date;
    leadDate_ = date;
    [self fireChangeEvent];
  }
}

- (void)setSelectedDateWithJavaUtilDate:(JavaUtilDate *)date {
  if (selectedDates_ != nil) {
    [selectedDates_ clear];
  }
  if ((selectedDate_ == nil) || ![selectedDate_ isEqual:date]) {
    [self clearSelectionEx];
    selectedDate_ = date;
    anchorDate_ = date;
    leadDate_ = date;
  }
  [self fireChangeEvent];
}

- (void)setSelectedDatesWithJavaUtilDateArray:(IOSObjectArray *)date {
  int len = (date == nil) ? 0 : (int) [date count];
  if (len > 0) {
    if ((selectionMode_ == RAREDateSelectionModel_SINGLE_SELECTION) || ((len == 1) && (selectedDates_ == nil))) {
      [self setSelectedDateWithJavaUtilDate:IOSObjectArray_Get(nil_chk(date), 0)];
      return;
    }
    [self clearSelectionEx];
    if (selectedDates_ == nil) {
      selectedDates_ = [[JavaUtilArrayList alloc] initWithInt:(len > 5) ? len : 5];
    }
    for (int i = 0; i < len; i++) {
      [self addSelectedDateExWithJavaUtilDate:IOSObjectArray_Get(nil_chk(date), i)];
    }
    if (![((JavaUtilArrayList *) nil_chk(selectedDates_)) isEmpty]) {
      selectedDate_ = [selectedDates_ getWithInt:[selectedDates_ size] - 1];
      anchorDate_ = [selectedDates_ getWithInt:0];
      leadDate_ = selectedDate_;
      [self fireChangeEvent];
    }
  }
  else {
    [self clearSelection];
  }
}

- (void)setSelectionIntervalWithJavaUtilDate:(JavaUtilDate *)date0
                            withJavaUtilDate:(JavaUtilDate *)date1 {
  anchorDate_ = date0;
  [self setLeadSelectionDateWithJavaUtilDate:date1];
}

- (void)setSelectionModeWithInt:(int)selectionMode {
  if (self->selectionMode_ != selectionMode) {
    if ((selectedDates_ != nil) && ![selectedDates_ isEmpty]) {
      if (selectedDate_ == nil) {
        [self setSelectedDateWithJavaUtilDate:[selectedDates_ getWithInt:0]];
      }
    }
    self->selectionMode_ = selectionMode;
  }
}

- (JavaUtilDate *)getAnchorDate {
  return anchorDate_;
}

- (JavaUtilDate *)getLeadSelectionDate {
  return leadDate_;
}

- (JavaUtilDate *)getSelectedDate {
  return selectedDate_;
}

- (IOSObjectArray *)getSelectedDates {
  int count = [self getSelectionCount];
  if (count == 0) {
    return nil;
  }
  IOSObjectArray *dates = [IOSObjectArray arrayWithLength:count type:[IOSClass classWithClass:[JavaUtilDate class]]];
  if ((selectedDates_ != nil) && ![selectedDates_ isEmpty]) {
    dates = [selectedDates_ toArrayWithNSObjectArray:dates];
  }
  else if (count == 1) {
    (void) IOSObjectArray_Set(dates, 0, selectedDate_);
  }
  else {
    JavaUtilDate *d0 = anchorDate_;
    JavaUtilDate *d1 = leadDate_;
    if ([((JavaUtilDate *) nil_chk(d1)) beforeWithJavaUtilDate:d0]) {
      d1 = anchorDate_;
      d0 = leadDate_;
    }
    JavaUtilCalendar *cal = [self getCalendar];
    count--;
    (void) IOSObjectArray_Set(dates, 0, d0);
    (void) IOSObjectArray_Set(dates, count, d1);
    [((JavaUtilCalendar *) nil_chk(cal)) setTimeWithJavaUtilDate:d0];
    for (int i = 1; i < count; i++) {
      [cal addWithInt:JavaUtilCalendar_DAY_OF_YEAR withInt:1];
      (void) IOSObjectArray_Set(dates, i, [cal getTime]);
    }
  }
  return dates;
}

- (int)getSelectionCount {
  int size = (selectedDates_ == nil) ? 0 : [selectedDates_ size];
  if (size == 0) {
    if ((leadDate_ == nil) || (anchorDate_ == nil)) {
      return 0;
    }
    if ([((JavaUtilDate *) nil_chk(leadDate_)) isEqual:anchorDate_]) {
      return 1;
    }
    return [JavaLangMath absWithInt:[RAREUTHelper daysBetweenWithJavaUtilDate:anchorDate_ withJavaUtilDate:leadDate_]];
  }
  return size;
}

- (int)getSelectionMode {
  return selectionMode_;
}

- (BOOL)isSameDayWithJavaUtilDate:(JavaUtilDate *)date0
                 withJavaUtilDate:(JavaUtilDate *)date1 {
  if ((date0 == nil) || (date1 == nil)) {
    return date0 == date1;
  }
  JavaUtilCalendar *cal = [self getCalendar];
  [((JavaUtilCalendar *) nil_chk(cal)) setTimeWithJavaUtilDate:date0];
  int y = [cal getWithInt:JavaUtilCalendar_YEAR];
  int dd = [cal getWithInt:JavaUtilCalendar_DAY_OF_YEAR];
  [cal setTimeWithJavaUtilDate:date1];
  return (([cal getWithInt:JavaUtilCalendar_YEAR] == y) && ([cal getWithInt:JavaUtilCalendar_DAY_OF_YEAR] == dd));
}

- (BOOL)isSelectedDateWithJavaUtilDate:(JavaUtilDate *)date {
  if ((selectedDate_ == nil) && ((selectedDates_ == nil) || [selectedDates_ isEmpty])) {
    return NO;
  }
  JavaUtilCalendar *cal = [self getCalendar];
  [((JavaUtilCalendar *) nil_chk(cal)) setTimeWithJavaUtilDate:date];
  int y = [cal getWithInt:JavaUtilCalendar_YEAR];
  int dd = [cal getWithInt:JavaUtilCalendar_DAY_OF_YEAR];
  if ((selectedDate_ != nil)) {
    [cal setTimeWithJavaUtilDate:selectedDate_];
    if ((([cal getWithInt:JavaUtilCalendar_YEAR] == y) && ([cal getWithInt:JavaUtilCalendar_DAY_OF_YEAR] == dd))) {
      return YES;
    }
  }
  JavaUtilArrayList *dates = selectedDates_;
  int len = (selectedDates_ == nil) ? 0 : [selectedDates_ size];
  for (int i = 0; i < len; i++) {
    [cal setTimeWithJavaUtilDate:[((JavaUtilArrayList *) nil_chk(dates)) getWithInt:i]];
    if ((([cal getWithInt:JavaUtilCalendar_YEAR] == y) && ([cal getWithInt:JavaUtilCalendar_DAY_OF_YEAR] == dd))) {
      return YES;
    }
  }
  return NO;
}

- (BOOL)isSelectionEmpty {
  return (selectedDate_ == nil) && ((selectedDates_ == nil) || [selectedDates_ isEmpty]);
}

- (void)clearSelectionEx {
  if (selectedDates_ != nil) {
    [selectedDates_ clear];
  }
  anchorDate_ = nil;
  leadDate_ = nil;
  selectedDate_ = nil;
}

- (void)fireChangeEvent {
  if (listenerList_ != nil) {
    if (changeEvent_ == nil) {
      changeEvent_ = [[RAREChangeEvent alloc] initWithId:self];
    }
    IOSObjectArray *listeners = [listenerList_ getListenerList];
    for (int i = (int) [((IOSObjectArray *) nil_chk(listeners)) count] - 2; i >= 0; i -= 2) {
      if (IOSObjectArray_Get(listeners, i) == [IOSClass classWithProtocol:@protocol(RAREiChangeListener)]) {
        [((id<RAREiChangeListener>) check_protocol_cast(IOSObjectArray_Get(listeners, i + 1), @protocol(RAREiChangeListener))) stateChangedWithJavaUtilEventObject:changeEvent_];
      }
    }
  }
}

- (JavaUtilCalendar *)getCalendar {
  if (calendar_ == nil) {
    calendar_ = [JavaUtilCalendar getInstance];
  }
  return calendar_;
}

- (IOSObjectArray *)getSelectedDatesEx {
  if (selectedDates_ != nil) {
    return [selectedDates_ toArrayWithNSObjectArray:[IOSObjectArray arrayWithLength:[selectedDates_ size] type:[IOSClass classWithClass:[JavaUtilDate class]]]];
  }
  return (selectedDate_ == nil) ? nil : [IOSObjectArray arrayWithObjects:(id[]){ selectedDate_ } count:1 type:[IOSClass classWithClass:[JavaUtilDate class]]];
}

- (BOOL)addSelectedDateExWithJavaUtilDate:(JavaUtilDate *)date {
  if ([self isSameDayWithJavaUtilDate:date withJavaUtilDate:selectedDate_]) {
    return NO;
  }
  if (selectedDates_ == nil) {
    selectedDates_ = [[JavaUtilArrayList alloc] initWithInt:5];
    [selectedDates_ addWithId:selectedDate_];
    [selectedDates_ addWithId:date];
    return YES;
  }
  int len = [((JavaUtilArrayList *) nil_chk(selectedDates_)) size];
  for (int i = 0; i < len; i++) {
    JavaUtilDate *d = [selectedDates_ getWithInt:i];
    if ([((JavaUtilDate *) nil_chk(date)) beforeWithJavaUtilDate:d]) {
      if ([self isSameDayWithJavaUtilDate:d withJavaUtilDate:date]) {
        return NO;
      }
      [selectedDates_ addWithInt:i withId:date];
      return YES;
    }
    if ([self isSameDayWithJavaUtilDate:d withJavaUtilDate:date]) {
      return NO;
    }
  }
  [selectedDates_ addWithId:date];
  return YES;
}

- (id)init {
  if (self = [super init]) {
    listenerList_ = [[RAREEventListenerList alloc] init];
    selectionMode_ = RAREDateSelectionModel_SINGLE_SELECTION;
  }
  return self;
}

- (void)copyAllFieldsTo:(RAREDateSelectionModel *)other {
  [super copyAllFieldsTo:other];
  other->anchorDate_ = anchorDate_;
  other->calendar_ = calendar_;
  other->changeEvent_ = changeEvent_;
  other->leadDate_ = leadDate_;
  other->listenerList_ = listenerList_;
  other->selectedDate_ = selectedDate_;
  other->selectedDates_ = selectedDates_;
  other->selectionMode_ = selectionMode_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setSelectedDatesWithJavaUtilDateArray:", NULL, "V", 0x81, NULL },
    { "getAnchorDate", NULL, "LJavaUtilDate", 0x1, NULL },
    { "getLeadSelectionDate", NULL, "LJavaUtilDate", 0x1, NULL },
    { "getSelectedDate", NULL, "LJavaUtilDate", 0x1, NULL },
    { "getSelectedDates", NULL, "LIOSObjectArray", 0x1, NULL },
    { "isSameDayWithJavaUtilDate:withJavaUtilDate:", NULL, "Z", 0x1, NULL },
    { "isSelectedDateWithJavaUtilDate:", NULL, "Z", 0x1, NULL },
    { "isSelectionEmpty", NULL, "Z", 0x1, NULL },
    { "clearSelectionEx", NULL, "V", 0x4, NULL },
    { "fireChangeEvent", NULL, "V", 0x4, NULL },
    { "getCalendar", NULL, "LJavaUtilCalendar", 0x14, NULL },
    { "getSelectedDatesEx", NULL, "LIOSObjectArray", 0x4, NULL },
    { "addSelectedDateExWithJavaUtilDate:", NULL, "Z", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "MULTIPLE_INTERVAL_SELECTION_", NULL, 0x19, "I" },
    { "SINGLE_INTERVAL_SELECTION_", NULL, 0x19, "I" },
    { "SINGLE_SELECTION_", NULL, 0x19, "I" },
    { "listenerList_", NULL, 0x4, "LRAREEventListenerList" },
    { "anchorDate_", NULL, 0x4, "LJavaUtilDate" },
    { "leadDate_", NULL, 0x4, "LJavaUtilDate" },
    { "selectedDate_", NULL, 0x4, "LJavaUtilDate" },
    { "selectedDates_", NULL, 0x4, "LJavaUtilArrayList" },
  };
  static J2ObjcClassInfo _RAREDateSelectionModel = { "DateSelectionModel", "com.appnativa.rare.ui.calendar", NULL, 0x1, 13, methods, 8, fields, 0, NULL};
  return &_RAREDateSelectionModel;
}

@end
