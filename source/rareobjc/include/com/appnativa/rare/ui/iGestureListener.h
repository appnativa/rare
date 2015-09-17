//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iGestureListener.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _RAREiGestureListener_H_
#define _RAREiGestureListener_H_

@class RAREMouseEvent;

#import "JreEmulation.h"

@protocol RAREiGestureListener < NSObject, JavaObject >
- (void)onScaleEventWithId:(id)view
                   withInt:(int)type
                    withId:(id)sgd
                 withFloat:(float)factor;
- (void)onFlingWithId:(id)view
   withRAREMouseEvent:(RAREMouseEvent *)e1
   withRAREMouseEvent:(RAREMouseEvent *)e2
            withFloat:(float)velocityX
            withFloat:(float)velocityY;
- (void)onLongPressWithId:(id)view
       withRAREMouseEvent:(RAREMouseEvent *)e;
- (void)onRotateWithId:(id)view
               withInt:(int)type
             withFloat:(float)rotation
             withFloat:(float)velocity
             withFloat:(float)focusX
             withFloat:(float)focusY;
@end

#define ComAppnativaRareUiIGestureListener RAREiGestureListener

#endif // _RAREiGestureListener_H_
