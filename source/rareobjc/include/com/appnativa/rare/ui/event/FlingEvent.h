//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/FlingEvent.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREFlingEvent_H_
#define _RAREFlingEvent_H_

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/event/MouseEvent.h"

@interface RAREFlingEvent : RAREMouseEvent {
}

- (id)initWithId:(id)source
          withId:(id)me
         withInt:(int)modifiers
          withId:(id)me1
       withFloat:(float)x
       withFloat:(float)y;
- (float)getXVelocity;
- (float)getYVelocity;
@end

typedef RAREFlingEvent ComAppnativaRareUiEventFlingEvent;

#endif // _RAREFlingEvent_H_
