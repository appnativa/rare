//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/invocation/ArgumentsProcessor.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalInvocationArgumentsProcessor_H_
#define _OrgMockitoInternalInvocationArgumentsProcessor_H_

@class IOSObjectArray;
@protocol JavaUtilList;

#import "JreEmulation.h"

@interface OrgMockitoInternalInvocationArgumentsProcessor : NSObject {
}

+ (IOSObjectArray *)expandVarArgsWithBoolean:(BOOL)isVarArgs
                           withNSObjectArray:(IOSObjectArray *)args;
+ (id<JavaUtilList>)argumentsToMatchersWithNSObjectArray:(IOSObjectArray *)arguments;
- (id)init;
@end

#endif // _OrgMockitoInternalInvocationArgumentsProcessor_H_
