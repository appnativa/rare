//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/util/collections/HashCodeAndEqualsMockWrapper.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalUtilCollectionsHashCodeAndEqualsMockWrapper_H_
#define _OrgMockitoInternalUtilCollectionsHashCodeAndEqualsMockWrapper_H_

#import "JreEmulation.h"

@interface OrgMockitoInternalUtilCollectionsHashCodeAndEqualsMockWrapper : NSObject {
 @public
  id mockInstance_;
}

- (id)initWithId:(id)mockInstance;
- (id)get;
- (BOOL)isEqual:(id)o;
- (NSUInteger)hash;
+ (OrgMockitoInternalUtilCollectionsHashCodeAndEqualsMockWrapper *)ofWithId:(id)mock;
- (NSString *)description;
- (NSString *)typeInstanceString;
- (void)copyAllFieldsTo:(OrgMockitoInternalUtilCollectionsHashCodeAndEqualsMockWrapper *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalUtilCollectionsHashCodeAndEqualsMockWrapper, mockInstance_, id)

#endif // _OrgMockitoInternalUtilCollectionsHashCodeAndEqualsMockWrapper_H_