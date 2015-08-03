//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/junit/build_result/java/org/junit/runners/model/RunnerBuilder.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgJunitRunnersModelRunnerBuilder_H_
#define _OrgJunitRunnersModelRunnerBuilder_H_

@class IOSClass;
@class IOSObjectArray;
@class OrgJunitRunnerRunner;
@protocol JavaUtilList;
@protocol JavaUtilSet;

#import "JreEmulation.h"

@interface OrgJunitRunnersModelRunnerBuilder : NSObject {
 @public
  id<JavaUtilSet> parents_;
}

- (OrgJunitRunnerRunner *)runnerForClassWithIOSClass:(IOSClass *)testClass;
- (OrgJunitRunnerRunner *)safeRunnerForClassWithIOSClass:(IOSClass *)testClass;
- (IOSClass *)addParentWithIOSClass:(IOSClass *)parent;
- (void)removeParentWithIOSClass:(IOSClass *)klass;
- (id<JavaUtilList>)runnersWithIOSClass:(IOSClass *)parent
                      withIOSClassArray:(IOSObjectArray *)children;
- (id<JavaUtilList>)runnersWithIOSClass:(IOSClass *)parent
                       withJavaUtilList:(id<JavaUtilList>)children;
- (id<JavaUtilList>)runnersWithIOSClassArray:(IOSObjectArray *)children;
- (id)init;
- (void)copyAllFieldsTo:(OrgJunitRunnersModelRunnerBuilder *)other;
@end

J2OBJC_FIELD_SETTER(OrgJunitRunnersModelRunnerBuilder, parents_, id<JavaUtilSet>)

#endif // _OrgJunitRunnersModelRunnerBuilder_H_
