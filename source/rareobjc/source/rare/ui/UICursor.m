//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/UICursor.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/UICursor.h"
#import "java/util/Locale.h"

@implementation RAREUICursor

- (id)initWithNSString:(NSString *)name
                withId:(id)cursor {
  if (self = [super init]) {
    self->name_ = name;
    self->cursor_ = cursor;
  }
  return self;
}

- (id)getNSCursor {
  return cursor_;
}

+ (RAREUICursor *)getCursorWithNSString:(NSString *)name {
  #if TARGET_OS_IPHONE
  #else
  name = [name lowercaseStringWithJRELocale:[JavaUtilLocale US]];
  if ([name isEqual:@"CROSSHAIR"]) {
    return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor crosshairCursor]];
  }
  else if ([name isEqual:@"DEFAULT"]) {
    return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor arrowCursor]];
  }
  else if ([name isEqual:@"HELP"]) {
    return nil;
  }
  else if ([name isEqual:@"MOVE"]) {
    return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor closedHandCursor]];
  }
  else if ([name isEqual:@"POINTER "]) {
    return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor arrowCursor]];
  }
  else if ([name isEqual:@"PROGRESS"]) {
    return nil;
  }
  else if ([name isEqual:@"TEXT"]) {
    return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor IBeamCursor]];
  }
  else if ([name isEqual:@"WAIT"]) {
    return nil;
  }
  else if ([name isEqual:@"E-RESIZE"]) {
    return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor resizeRightCursor]];
  }
  else if ([name isEqual:@"NE-RESIZE"]) {
    return nil;
  }
  else if ([name isEqual:@"NW-RESIZE"]) {
    return nil;
  }
  else if ([name isEqual:@"N-RESIZE"]) {
    return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor resizeUpCursor]];
  }
  else if ([name isEqual:@"SE-RESIZE"]) {
    return nil;
  }
  else if ([name isEqual:@"SW-RESIZE"]) {
    return nil;
  }
  else if ([name isEqual:@"S-RESIZE"]) {
    return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor resizeDownCursor]];
  }
  else if ([name isEqual:@"W-RESIZE"]) {
    return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor resizeLeftCursor]];
  }
  else if ([name isEqual:@"EW-RESIZE"]) {
    return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor resizeLeftRightCursor]];
  }
  else if ([name isEqual:@"NS-RESIZE"]) {
    return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor resizeUpDownCursor]];
  }
  else if ([name isEqual:@"NESW-RESIZE"]) {
    return nil;
  }
  else if ([name isEqual:@"NWSE-RESIZE"]) {
    return nil;
  }
  else if ([name isEqual:@"COL-RESIZE"]) {
    return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor resizeLeftRightCursor]];
  }
  else if ([name isEqual:@"ROW-RESIZE"]) {
    return [[RAREUICursor alloc] initWithNSString:name withId:[NSCursor resizeUpDownCursor]];
  }
  else if ([name isEqual:@"ALL-SCROLL"]) {
  }
  #endif
  return nil;
}

- (NSString *)getName {
  return name_;
}

- (void)copyAllFieldsTo:(RAREUICursor *)other {
  [super copyAllFieldsTo:other];
  other->cursor_ = cursor_;
  other->name_ = name_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getNSCursor", NULL, "LNSObject", 0x1, NULL },
    { "getCursorWithNSString:", NULL, "LRAREUICursor", 0x109, NULL },
    { "getName", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "cursor_", NULL, 0x10, "LNSObject" },
    { "name_", NULL, 0x10, "LNSString" },
  };
  static J2ObjcClassInfo _RAREUICursor = { "UICursor", "com.appnativa.rare.ui", NULL, 0x1, 3, methods, 2, fields, 0, NULL};
  return &_RAREUICursor;
}

@end
