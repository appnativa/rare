//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-datechooser/com/appnativa/rare/ui/calendar/iDateFilter.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/ui/calendar/iDateFilter.h"
#include "java/util/Calendar.h"


@interface RAREiDateFilter : NSObject
@end

@implementation RAREiDateFilter

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getMaxDate", NULL, "LJavaUtilCalendar", 0x401, NULL },
    { "getMinDate", NULL, "LJavaUtilCalendar", 0x401, NULL },
    { "isDateValidWithJavaUtilCalendar:", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiDateFilter = { "iDateFilter", "com.appnativa.rare.ui.calendar", NULL, 0x201, 3, methods, 0, NULL, 0, NULL};
  return &_RAREiDateFilter;
}

@end
