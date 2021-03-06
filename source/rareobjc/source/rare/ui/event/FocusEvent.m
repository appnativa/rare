//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/FocusEvent.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/event/FocusEvent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"

@implementation RAREFocusEvent

- (id)initWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)source
                         withBoolean:(BOOL)gained {
  return [self initRAREFocusEventWithRAREiPlatformComponent:source withBoolean:gained withBoolean:NO];
}

- (id)initRAREFocusEventWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)source
                                       withBoolean:(BOOL)gained
                                       withBoolean:(BOOL)temporary {
  return [self initRAREFocusEventWithRAREiPlatformComponent:source withBoolean:gained withBoolean:temporary withRAREiPlatformComponent:nil];
}

- (id)initWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)source
                         withBoolean:(BOOL)gained
                         withBoolean:(BOOL)temporary {
  return [self initRAREFocusEventWithRAREiPlatformComponent:source withBoolean:gained withBoolean:temporary];
}

- (id)initRAREFocusEventWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)source
                                       withBoolean:(BOOL)gained
                                       withBoolean:(BOOL)temporary
                        withRAREiPlatformComponent:(id<RAREiPlatformComponent>)opposite {
  if (self = [super initWithId:source]) {
    self->temporary_ = temporary;
    self->opposite_ = opposite;
  }
  return self;
}

- (id)initWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)source
                         withBoolean:(BOOL)gained
                         withBoolean:(BOOL)temporary
          withRAREiPlatformComponent:(id<RAREiPlatformComponent>)opposite {
  return [self initRAREFocusEventWithRAREiPlatformComponent:source withBoolean:gained withBoolean:temporary withRAREiPlatformComponent:opposite];
}

- (id<RAREiPlatformComponent>)getOppositeComponent {
  return opposite_;
}

- (BOOL)isTemporary {
  return temporary_;
}

- (BOOL)wasFocusLost {
  return !gained_;
}

- (BOOL)wasFocusGained {
  return gained_;
}

- (void)copyAllFieldsTo:(RAREFocusEvent *)other {
  [super copyAllFieldsTo:other];
  other->gained_ = gained_;
  other->opposite_ = opposite_;
  other->temporary_ = temporary_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getOppositeComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "isTemporary", NULL, "Z", 0x1, NULL },
    { "wasFocusLost", NULL, "Z", 0x1, NULL },
    { "wasFocusGained", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREFocusEvent = { "FocusEvent", "com.appnativa.rare.ui.event", NULL, 0x1, 4, methods, 0, NULL, 0, NULL};
  return &_RAREFocusEvent;
}

@end
