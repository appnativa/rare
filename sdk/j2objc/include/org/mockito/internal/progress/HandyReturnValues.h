//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/tball/tmp/j2objc/testing/mockito/build_result/java/org/mockito/internal/progress/HandyReturnValues.java
//
//  Created by tball on 11/23/13.
//

#ifndef _OrgMockitoInternalProgressHandyReturnValues_H_
#define _OrgMockitoInternalProgressHandyReturnValues_H_

@class IOSClass;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol JavaUtilSet;

#import "JreEmulation.h"

@interface OrgMockitoInternalProgressHandyReturnValues : NSObject {
}

- (char)returnZero;
- (unichar)returnChar;
- (id)returnNull;
- (BOOL)returnFalse;
- (NSString *)returnString;
- (id)returnForWithIOSClass:(IOSClass *)clazz;
- (id<JavaUtilMap>)returnMap;
- (id<JavaUtilList>)returnList;
- (id<JavaUtilSet>)returnSet;
- (id)returnForWithId:(id)instance;
- (id)init;
@end

#endif // _OrgMockitoInternalProgressHandyReturnValues_H_