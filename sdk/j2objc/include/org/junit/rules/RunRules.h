//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/junit/build_result/java/org/junit/rules/RunRules.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgJunitRulesRunRules_H_
#define _OrgJunitRulesRunRules_H_

@class OrgJunitRunnerDescription;
@protocol JavaLangIterable;

#import "JreEmulation.h"
#include "org/junit/runners/model/Statement.h"

@interface OrgJunitRulesRunRules : OrgJunitRunnersModelStatement {
 @public
  OrgJunitRunnersModelStatement *statement_;
}

- (id)initWithOrgJunitRunnersModelStatement:(OrgJunitRunnersModelStatement *)base
                       withJavaLangIterable:(id<JavaLangIterable>)rules
              withOrgJunitRunnerDescription:(OrgJunitRunnerDescription *)description_;
- (void)evaluate;
+ (OrgJunitRunnersModelStatement *)applyAllWithOrgJunitRunnersModelStatement:(OrgJunitRunnersModelStatement *)result
                                                        withJavaLangIterable:(id<JavaLangIterable>)rules
                                               withOrgJunitRunnerDescription:(OrgJunitRunnerDescription *)description_;
- (void)copyAllFieldsTo:(OrgJunitRulesRunRules *)other;
@end

J2OBJC_FIELD_SETTER(OrgJunitRulesRunRules, statement_, OrgJunitRunnersModelStatement *)

#endif // _OrgJunitRulesRunRules_H_