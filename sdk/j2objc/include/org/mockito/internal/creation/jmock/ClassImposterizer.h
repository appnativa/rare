//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: src/main/java/org/mockito/internal/creation/jmock/ClassImposterizer.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalCreationJmockClassImposterizer_H_
#define _OrgMockitoInternalCreationJmockClassImposterizer_H_

@class IOSClass;

#import "JreEmulation.h"

@interface OrgMockitoInternalCreationJmockClassImposterizer : NSObject {
}

+ (OrgMockitoInternalCreationJmockClassImposterizer *)INSTANCE;
- (id)init;
- (BOOL)canImposteriseWithIOSClass:(IOSClass *)type;
@end

#endif // _OrgMockitoInternalCreationJmockClassImposterizer_H_