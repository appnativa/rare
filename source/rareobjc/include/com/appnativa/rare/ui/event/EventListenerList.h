//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/event/EventListenerList.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREEventListenerList_H_
#define _RAREEventListenerList_H_

@class IOSClass;
@class IOSObjectArray;

#import "JreEmulation.h"
#include "java/io/Serializable.h"

@interface RAREEventListenerList : NSObject < JavaIoSerializable > {
 @public
  IOSObjectArray *listenerList_;
}

- (void)addWithIOSClass:(IOSClass *)listenerClass
                 withId:(id)listener;
- (void)clear;
- (void)removeWithIOSClass:(IOSClass *)listenerClass
                    withId:(id)listener;
- (NSString *)description;
- (int)getListenerCount;
- (int)getListenerCountWithIOSClass:(IOSClass *)listenerClass;
- (IOSObjectArray *)getListenerList;
- (IOSObjectArray *)getListenersWithIOSClass:(IOSClass *)listenerClass;
- (BOOL)hasListenerWithIOSClass:(IOSClass *)listenerClass
                         withId:(id)listener;
- (BOOL)hasListenersWithIOSClass:(IOSClass *)listenerClass;
- (id)init;
- (void)copyAllFieldsTo:(RAREEventListenerList *)other;
@end

J2OBJC_FIELD_SETTER(RAREEventListenerList, listenerList_, IOSObjectArray *)

typedef RAREEventListenerList ComAppnativaRareUiEventEventListenerList;

#endif // _RAREEventListenerList_H_
