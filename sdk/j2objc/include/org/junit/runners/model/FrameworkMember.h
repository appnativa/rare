//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/junit/build_result/java/org/junit/runners/model/FrameworkMember.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgJunitRunnersModelFrameworkMember_H_
#define _OrgJunitRunnersModelFrameworkMember_H_

@class IOSObjectArray;
@protocol JavaUtilList;

#import "JreEmulation.h"

@interface OrgJunitRunnersModelFrameworkMember : NSObject {
}

- (IOSObjectArray *)getAnnotations;
- (BOOL)isShadowedByWithId:(OrgJunitRunnersModelFrameworkMember *)otherMember;
- (BOOL)isShadowedByWithJavaUtilList:(id<JavaUtilList>)members;
- (id)init;
@end

#endif // _OrgJunitRunnersModelFrameworkMember_H_
