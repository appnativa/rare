//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/ScaleEvent.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/platform/EventHelper.h"
#include "com/appnativa/rare/ui/event/ScaleEvent.h"

@implementation RAREScaleEvent

+ (int)SCALE {
  return RAREScaleEvent_SCALE;
}

+ (int)SCALE_BEGIN {
  return RAREScaleEvent_SCALE_BEGIN;
}

+ (int)SCALE_END {
  return RAREScaleEvent_SCALE_END;
}

- (id)initWithId:(id)source
          withId:(id)sgd
         withInt:(int)type
       withFloat:(float)scaleFactor {
  if (self = [super initWithId:source]) {
    eventType_ = type;
    scaleGestureDetector_ = sgd;
    self->scaleFactor_ = scaleFactor;
  }
  return self;
}

- (float)getCurrentSpan {
  return [RAREEventHelper getScaleCurrentSpanWithId:scaleGestureDetector_];
}

- (int)getEventType {
  return eventType_;
}

- (float)getFocusX {
  return [RAREEventHelper getScaleFocusXWithId:scaleGestureDetector_];
}

- (float)getFocusY {
  return [RAREEventHelper getScaleFocusYWithId:scaleGestureDetector_];
}

- (float)getPreviousSpan {
  return [RAREEventHelper getScalePreviousSpanWithId:scaleGestureDetector_];
}

- (float)getScaleFactor {
  return scaleFactor_;
}

- (id)getScaleGestureDetector {
  return scaleGestureDetector_;
}

- (void)copyAllFieldsTo:(RAREScaleEvent *)other {
  [super copyAllFieldsTo:other];
  other->eventType_ = eventType_;
  other->scaleFactor_ = scaleFactor_;
  other->scaleGestureDetector_ = scaleGestureDetector_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getScaleGestureDetector", NULL, "LNSObject", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "SCALE_", NULL, 0x19, "I" },
    { "SCALE_BEGIN_", NULL, 0x19, "I" },
    { "SCALE_END_", NULL, 0x19, "I" },
    { "eventType_", NULL, 0x12, "I" },
    { "scaleFactor_", NULL, 0x12, "F" },
    { "scaleGestureDetector_", NULL, 0x12, "LNSObject" },
  };
  static J2ObjcClassInfo _RAREScaleEvent = { "ScaleEvent", "com.appnativa.rare.ui.event", NULL, 0x1, 1, methods, 6, fields, 0, NULL};
  return &_RAREScaleEvent;
}

@end
