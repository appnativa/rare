//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/ScaleEvent.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREScaleEvent_H_
#define _RAREScaleEvent_H_

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/event/EventBase.h"

#define RAREScaleEvent_SCALE 2
#define RAREScaleEvent_SCALE_BEGIN 1
#define RAREScaleEvent_SCALE_END 3

@interface RAREScaleEvent : RAREEventBase {
 @public
  int eventType_;
  float scaleFactor_;
  id scaleGestureDetector_;
}

+ (int)SCALE;
+ (int)SCALE_BEGIN;
+ (int)SCALE_END;
- (id)initWithId:(id)source
          withId:(id)sgd
         withInt:(int)type
       withFloat:(float)scaleFactor;
- (float)getCurrentSpan;
- (int)getEventType;
- (float)getFocusX;
- (float)getFocusY;
- (float)getPreviousSpan;
- (float)getScaleFactor;
- (id)getScaleGestureDetector;
- (void)copyAllFieldsTo:(RAREScaleEvent *)other;
@end

J2OBJC_FIELD_SETTER(RAREScaleEvent, scaleGestureDetector_, id)

typedef RAREScaleEvent ComAppnativaRareUiEventScaleEvent;

#endif // _RAREScaleEvent_H_