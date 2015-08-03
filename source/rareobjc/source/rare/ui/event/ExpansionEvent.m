//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/ExpansionEvent.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/event/EventBase.h"
#include "com/appnativa/rare/ui/event/ExpansionEvent.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/lang/StringBuilder.h"

@implementation RAREExpansionEvent

- (id)initWithId:(id)source {
  return [super initWithId:source];
}

- (id)initWithId:(id)source
          withId:(id)item {
  if (self = [super initWithId:source]) {
    eventItem_ = item;
  }
  return self;
}

- (void)reject {
  consumed_ = YES;
}

- (NSString *)description {
  JavaLangStringBuilder *sb = [[JavaLangStringBuilder alloc] initWithNSString:@"ExpansionEvent"];
  if ([[self getSource] conformsToProtocol: @protocol(RAREiWidget)]) {
    (void) [((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:@"\n  source="])) appendWithNSString:[((id<RAREiWidget>) check_protocol_cast([self getSource], @protocol(RAREiWidget))) getName]];
  }
  else {
    (void) [((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:@"\n  source="])) appendWithNSString:[nil_chk([self getSource]) description]];
  }
  if (eventItem_ != nil) {
    (void) [((JavaLangStringBuilder *) nil_chk([sb appendWithNSString:@"eventItem="])) appendWithNSString:[eventItem_ description]];
  }
  return [sb description];
}

- (void)setItemWithId:(id)item {
  eventItem_ = item;
}

- (id)getItem {
  return eventItem_;
}

- (void)copyAllFieldsTo:(RAREExpansionEvent *)other {
  [super copyAllFieldsTo:other];
  other->eventItem_ = eventItem_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getItem", NULL, "LNSObject", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREExpansionEvent = { "ExpansionEvent", "com.appnativa.rare.ui.event", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RAREExpansionEvent;
}

@end
