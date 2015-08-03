//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/junit/build_result/java/org/hamcrest/BaseDescription.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgHamcrestBaseDescription_H_
#define _OrgHamcrestBaseDescription_H_

@class IOSObjectArray;
@protocol JavaLangIterable;
@protocol JavaUtilIterator;
@protocol OrgHamcrestSelfDescribing;

#import "JreEmulation.h"
#include "org/hamcrest/Description.h"

@interface OrgHamcrestBaseDescription : NSObject < OrgHamcrestDescription > {
}

- (id<OrgHamcrestDescription>)appendTextWithNSString:(NSString *)text;
- (id<OrgHamcrestDescription>)appendDescriptionOfWithOrgHamcrestSelfDescribing:(id<OrgHamcrestSelfDescribing>)value;
- (id<OrgHamcrestDescription>)appendValueWithId:(id)value;
- (id<OrgHamcrestDescription>)appendValueListWithNSString:(NSString *)start
                                             withNSString:(NSString *)separator
                                             withNSString:(NSString *)end
                                        withNSObjectArray:(IOSObjectArray *)values;
- (id<OrgHamcrestDescription>)appendValueListWithNSString:(NSString *)start
                                             withNSString:(NSString *)separator
                                             withNSString:(NSString *)end
                                     withJavaLangIterable:(id<JavaLangIterable>)values;
- (id<OrgHamcrestDescription>)appendValueListWithNSString:(NSString *)start
                                             withNSString:(NSString *)separator
                                             withNSString:(NSString *)end
                                     withJavaUtilIterator:(id<JavaUtilIterator>)values;
- (id<OrgHamcrestDescription>)appendListWithNSString:(NSString *)start
                                        withNSString:(NSString *)separator
                                        withNSString:(NSString *)end
                                withJavaLangIterable:(id<JavaLangIterable>)values;
- (id<OrgHamcrestDescription>)appendListWithNSString:(NSString *)start
                                        withNSString:(NSString *)separator
                                        withNSString:(NSString *)end
                                withJavaUtilIterator:(id<JavaUtilIterator>)i;
- (void)appendWithNSString:(NSString *)str;
- (void)appendWithChar:(unichar)c;
- (void)toJavaSyntaxWithNSString:(NSString *)unformatted;
- (void)toJavaSyntaxWithChar:(unichar)ch;
- (id)init;
@end

#endif // _OrgHamcrestBaseDescription_H_
