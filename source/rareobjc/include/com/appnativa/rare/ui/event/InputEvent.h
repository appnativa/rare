//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/InputEvent.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREInputEvent_H_
#define _RAREInputEvent_H_

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/event/EventBase.h"

@interface RAREInputEvent : RAREEventBase {
}

- (id)initWithId:(id)source;
- (id)initWithId:(id)source
          withId:(id)nativeEvent;
- (long long int)getEventTime;
@end

typedef RAREInputEvent ComAppnativaRareUiEventInputEvent;

#endif // _RAREInputEvent_H_
