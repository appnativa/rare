//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/junit/build_result/java/org/junit/runner/manipulation/Sorter.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgJunitRunnerManipulationSorter_H_
#define _OrgJunitRunnerManipulationSorter_H_

@class OrgJunitRunnerDescription;

#import "JreEmulation.h"
#include "java/util/Comparator.h"

@interface OrgJunitRunnerManipulationSorter : NSObject < JavaUtilComparator > {
 @public
  id<JavaUtilComparator> fComparator_;
}

+ (OrgJunitRunnerManipulationSorter *)getNULL;
+ (void)setNULL:(OrgJunitRunnerManipulationSorter *)NULL_;
- (id)initWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (void)applyWithId:(id)object;
- (int)compareWithId:(OrgJunitRunnerDescription *)o1
              withId:(OrgJunitRunnerDescription *)o2;
- (void)copyAllFieldsTo:(OrgJunitRunnerManipulationSorter *)other;
@end

J2OBJC_FIELD_SETTER(OrgJunitRunnerManipulationSorter, fComparator_, id<JavaUtilComparator>)

@interface OrgJunitRunnerManipulationSorter_$1 : NSObject < JavaUtilComparator > {
}

- (int)compareWithId:(OrgJunitRunnerDescription *)o1
              withId:(OrgJunitRunnerDescription *)o2;
- (id)init;
@end

#endif // _OrgJunitRunnerManipulationSorter_H_