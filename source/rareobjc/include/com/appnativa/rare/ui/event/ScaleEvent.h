//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/ScaleEvent.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREScaleEvent_H_
#define _RAREScaleEvent_H_

@class RAREScaleEvent_TypeEnum;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/event/EventBase.h"
#include "java/lang/Enum.h"

@interface RAREScaleEvent : RAREEventBase {
 @public
  RAREScaleEvent_TypeEnum *type_;
  float scaleFactor_;
  id scaleGestureDetector_;
}

- (id)initWithId:(id)source
          withId:(id)sgd
withRAREScaleEvent_TypeEnum:(RAREScaleEvent_TypeEnum *)type
       withFloat:(float)scaleFactor;
- (float)getCurrentSpan;
- (RAREScaleEvent_TypeEnum *)getEventType;
- (float)getFocusX;
- (float)getFocusY;
- (float)getPreviousSpan;
- (float)getScaleFactor;
- (id)getScaleGestureDetector;
- (BOOL)isTypeWithRAREScaleEvent_TypeEnum:(RAREScaleEvent_TypeEnum *)type;
- (void)copyAllFieldsTo:(RAREScaleEvent *)other;
@end

J2OBJC_FIELD_SETTER(RAREScaleEvent, type_, RAREScaleEvent_TypeEnum *)
J2OBJC_FIELD_SETTER(RAREScaleEvent, scaleGestureDetector_, id)

typedef RAREScaleEvent ComAppnativaRareUiEventScaleEvent;

typedef enum {
  RAREScaleEvent_Type_SCALE = 0,
  RAREScaleEvent_Type_SCALE_BEGIN = 1,
  RAREScaleEvent_Type_SCALE_END = 2,
} RAREScaleEvent_Type;

@interface RAREScaleEvent_TypeEnum : JavaLangEnum < NSCopying > {
}
+ (RAREScaleEvent_TypeEnum *)SCALE;
+ (RAREScaleEvent_TypeEnum *)SCALE_BEGIN;
+ (RAREScaleEvent_TypeEnum *)SCALE_END;
+ (IOSObjectArray *)values;
+ (RAREScaleEvent_TypeEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

#endif // _RAREScaleEvent_H_
