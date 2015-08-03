//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/guava/sources/com/google/common/base/Preconditions.java
//
//  Created by tball on 11/23/13.
//

#import "JreEmulation.h"

#if !ComGoogleCommonBasePreconditions_RESTRICT
#define ComGoogleCommonBasePreconditions_INCLUDE_ALL 1
#endif
#undef ComGoogleCommonBasePreconditions_RESTRICT

#if !defined (_ComGoogleCommonBasePreconditions_) && (ComGoogleCommonBasePreconditions_INCLUDE_ALL || ComGoogleCommonBasePreconditions_INCLUDE)
#define _ComGoogleCommonBasePreconditions_

@class IOSObjectArray;

@interface ComGoogleCommonBasePreconditions : NSObject {
}

- (id)init;
+ (void)checkArgumentWithBoolean:(BOOL)expression;
+ (void)checkArgumentWithBoolean:(BOOL)expression
                          withId:(id)errorMessage;
+ (void)checkArgumentWithBoolean:(BOOL)expression
                    withNSString:(NSString *)errorMessageTemplate
               withNSObjectArray:(IOSObjectArray *)errorMessageArgs;
+ (void)checkStateWithBoolean:(BOOL)expression;
+ (void)checkStateWithBoolean:(BOOL)expression
                       withId:(id)errorMessage;
+ (void)checkStateWithBoolean:(BOOL)expression
                 withNSString:(NSString *)errorMessageTemplate
            withNSObjectArray:(IOSObjectArray *)errorMessageArgs;
+ (id)checkNotNullWithId:(id)reference;
+ (id)checkNotNullWithId:(id)reference
                  withId:(id)errorMessage;
+ (id)checkNotNullWithId:(id)reference
            withNSString:(NSString *)errorMessageTemplate
       withNSObjectArray:(IOSObjectArray *)errorMessageArgs;
+ (int)checkElementIndexWithInt:(int)index
                        withInt:(int)size;
+ (int)checkElementIndexWithInt:(int)index
                        withInt:(int)size
                   withNSString:(NSString *)desc;
+ (NSString *)badElementIndexWithInt:(int)index
                             withInt:(int)size
                        withNSString:(NSString *)desc;
+ (int)checkPositionIndexWithInt:(int)index
                         withInt:(int)size;
+ (int)checkPositionIndexWithInt:(int)index
                         withInt:(int)size
                    withNSString:(NSString *)desc;
+ (NSString *)badPositionIndexWithInt:(int)index
                              withInt:(int)size
                         withNSString:(NSString *)desc;
+ (void)checkPositionIndexesWithInt:(int)start
                            withInt:(int)end
                            withInt:(int)size;
+ (NSString *)badPositionIndexesWithInt:(int)start
                                withInt:(int)end
                                withInt:(int)size;
+ (NSString *)formatWithNSString:(NSString *)template_
               withNSObjectArray:(IOSObjectArray *)args;
@end
#endif
