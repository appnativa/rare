//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/util/collections/IdentitySet.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalUtilCollectionsIdentitySet_H_
#define _OrgMockitoInternalUtilCollectionsIdentitySet_H_

@class JavaUtilLinkedList;

#import "JreEmulation.h"

@interface OrgMockitoInternalUtilCollectionsIdentitySet : NSObject {
 @public
  JavaUtilLinkedList *list_;
}

- (BOOL)containsWithId:(id)o;
- (void)addWithId:(id)o;
- (id)init;
- (void)copyAllFieldsTo:(OrgMockitoInternalUtilCollectionsIdentitySet *)other;
@end

J2OBJC_FIELD_SETTER(OrgMockitoInternalUtilCollectionsIdentitySet, list_, JavaUtilLinkedList *)

#endif // _OrgMockitoInternalUtilCollectionsIdentitySet_H_
