//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/Projects/rare/core-apple/javax/script/Invocable.java
//
//  Created by decoteaud on 6/13/14.
//

#ifndef _JavaxScriptInvocable_H_
#define _JavaxScriptInvocable_H_

@class IOSClass;
@class IOSObjectArray;

#import "JreEmulation.h"

@protocol JavaxScriptInvocable < NSObject, JavaObject >
- (id)invokeFunctionWithNSString:(NSString *)name
               withNSObjectArray:(IOSObjectArray *)args;
- (id)invokeMethodWithId:(id)thiz
            withNSString:(NSString *)name
       withNSObjectArray:(IOSObjectArray *)args;
- (id)getInterfaceWithIOSClass:(IOSClass *)clasz;
- (id)getInterfaceWithId:(id)thiz
            withIOSClass:(IOSClass *)clasz;
@end

#endif // _JavaxScriptInvocable_H_
