//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/iEventHandler.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREiEventHandler_H_
#define _RAREiEventHandler_H_

@class JavaUtilEventObject;
@protocol RAREiWidget;

#import "JreEmulation.h"

@protocol RAREiEventHandler < NSObject, JavaObject >
- (void)onEventWithNSString:(NSString *)eventName
            withRAREiWidget:(id<RAREiWidget>)widget
    withJavaUtilEventObject:(JavaUtilEventObject *)event;
@end

#define ComAppnativaRareUiIEventHandler RAREiEventHandler

#endif // _RAREiEventHandler_H_
