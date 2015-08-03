//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/NSUserDefaultsPreferences.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARENSUserDefaultsPreferences_H_
#define _RARENSUserDefaultsPreferences_H_

@class IOSByteArray;
@class IOSObjectArray;
@class RAREEventListenerList;

#import "JreEmulation.h"
#include "com/appnativa/util/iPreferences.h"

@interface RARENSUserDefaultsPreferences : NSObject < RAREUTiPreferences > {
 @public
  id proxy_;
  NSString *prefix_;
  RAREEventListenerList *listenerList_;
  BOOL isUser_;
}

- (id)initWithNSString:(NSString *)prefix;
- (id)initWithId:(id)proxy
    withNSString:(NSString *)prefix
     withBoolean:(BOOL)isUser;
- (void)flush;
- (void)putWithNSString:(NSString *)key
           withNSString:(NSString *)value;
- (NSString *)getWithNSString:(NSString *)key
                 withNSString:(NSString *)def;
- (void)removeWithNSString:(NSString *)key;
- (void)clear;
- (void)putIntWithNSString:(NSString *)key
                   withInt:(int)value;
- (int)getIntWithNSString:(NSString *)key
                  withInt:(int)def;
- (void)putLongWithNSString:(NSString *)key
                   withLong:(long long int)value;
- (long long int)getLongWithNSString:(NSString *)key
                            withLong:(long long int)def;
- (void)putBooleanWithNSString:(NSString *)key
                   withBoolean:(BOOL)value;
- (BOOL)getBooleanWithNSString:(NSString *)key
                   withBoolean:(BOOL)def;
- (void)putFloatWithNSString:(NSString *)key
                   withFloat:(float)value;
- (float)getFloatWithNSString:(NSString *)key
                    withFloat:(float)def;
- (void)putDoubleWithNSString:(NSString *)key
                   withDouble:(double)value;
- (double)getDoubleWithNSString:(NSString *)key
                     withDouble:(double)def;
- (void)putByteArrayWithNSString:(NSString *)key
                   withByteArray:(IOSByteArray *)value;
- (IOSByteArray *)getByteArrayWithNSString:(NSString *)key
                             withByteArray:(IOSByteArray *)def;
- (IOSObjectArray *)keys;
- (IOSObjectArray *)childrenNames;
- (id<RAREUTiPreferences>)getParent;
- (id<RAREUTiPreferences>)getNodeWithNSString:(NSString *)pathName;
- (BOOL)nodeExistsWithNSString:(NSString *)pathName;
- (void)removeNode;
- (NSString *)name;
- (NSString *)absolutePath;
- (BOOL)isUserNode;
- (void)sync;
- (NSString *)makeKeyWithNSString:(NSString *)key;
+ (id)createProxy;
- (void)copyAllFieldsTo:(RARENSUserDefaultsPreferences *)other;
@end

J2OBJC_FIELD_SETTER(RARENSUserDefaultsPreferences, proxy_, id)
J2OBJC_FIELD_SETTER(RARENSUserDefaultsPreferences, prefix_, NSString *)
J2OBJC_FIELD_SETTER(RARENSUserDefaultsPreferences, listenerList_, RAREEventListenerList *)

typedef RARENSUserDefaultsPreferences ComAppnativaRarePlatformNSUserDefaultsPreferences;

#endif // _RARENSUserDefaultsPreferences_H_
