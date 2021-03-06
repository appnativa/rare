//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../util/src-json/com/appnativa/util/json/JSONTokener.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUTJSONTokener_H_
#define _RAREUTJSONTokener_H_

@class JavaIoReader;
@class RAREUTCharArray;
@class RAREUTJSONArray;
@class RAREUTJSONException;
@class RAREUTJSONObject;
@protocol RAREUTJSONTokener_iWatcher;

#import "JreEmulation.h"

@interface RAREUTJSONTokener : NSObject {
 @public
  int index_;
  JavaIoReader *reader_;
  unichar lastChar_;
  BOOL useLastChar_;
  BOOL terminateParsing_;
  BOOL usePathAsName_;
  id<RAREUTJSONTokener_iWatcher> watcher_;
  RAREUTCharArray *pathBuffer_;
}

- (id)initWithJavaIoReader:(JavaIoReader *)reader;
- (id)initWithNSString:(NSString *)s;
- (void)back;
+ (int)dehexcharWithChar:(unichar)c;
- (BOOL)more;
- (unichar)next;
- (unichar)nextWithChar:(unichar)c;
- (NSString *)nextWithInt:(int)n;
- (unichar)nextClean;
- (NSString *)nextStringWithChar:(unichar)quote;
- (NSString *)nextToWithChar:(unichar)d;
- (NSString *)nextToWithNSString:(NSString *)delimiters;
- (id)nextValueWithNSString:(NSString *)key;
- (unichar)skipToWithChar:(unichar)to;
- (RAREUTJSONException *)syntaxErrorWithNSString:(NSString *)message;
- (NSString *)description;
- (void)setTerminateParsingWithBoolean:(BOOL)terminateParsing;
- (id<RAREUTJSONTokener_iWatcher>)getWatcher;
- (void)setWatcherWithRAREUTJSONTokener_iWatcher:(id<RAREUTJSONTokener_iWatcher>)watcher;
- (BOOL)isTerminateParsing;
- (void)dispose;
- (BOOL)isUsePathAsName;
- (NSString *)makePathNameWithNSString:(NSString *)parentName
                          withNSString:(NSString *)key;
- (void)setUsePathAsNameWithBoolean:(BOOL)usePathAsName;
- (void)copyAllFieldsTo:(RAREUTJSONTokener *)other;
@end

J2OBJC_FIELD_SETTER(RAREUTJSONTokener, reader_, JavaIoReader *)
J2OBJC_FIELD_SETTER(RAREUTJSONTokener, watcher_, id<RAREUTJSONTokener_iWatcher>)
J2OBJC_FIELD_SETTER(RAREUTJSONTokener, pathBuffer_, RAREUTCharArray *)

typedef RAREUTJSONTokener ComAppnativaUtilJsonJSONTokener;

@protocol RAREUTJSONTokener_iWatcher < NSObject, JavaObject >
- (BOOL)keyEncounteredWithRAREUTJSONObject:(RAREUTJSONObject *)parent
                              withNSString:(NSString *)key;
- (id)valueEncounteredWithRAREUTJSONObject:(RAREUTJSONObject *)parent
                              withNSString:(NSString *)valueName
                                    withId:(id)value;
- (id)valueEncounteredWithRAREUTJSONArray:(RAREUTJSONArray *)parent
                             withNSString:(NSString *)arrayName
                                   withId:(id)value;
- (void)willParseObjectWithRAREUTJSONObject:(RAREUTJSONObject *)object;
- (void)willParseArrayWithRAREUTJSONArray:(RAREUTJSONArray *)array;
- (void)didParseObjectWithRAREUTJSONObject:(RAREUTJSONObject *)object;
- (void)didParseArrayWithRAREUTJSONArray:(RAREUTJSONArray *)array;
@end

@interface RAREUTJSONTokener_aWatcher : NSObject < RAREUTJSONTokener_iWatcher > {
}

- (BOOL)keyEncounteredWithRAREUTJSONObject:(RAREUTJSONObject *)parent
                              withNSString:(NSString *)key;
- (id)valueEncounteredWithRAREUTJSONObject:(RAREUTJSONObject *)parent
                              withNSString:(NSString *)valueName
                                    withId:(id)value;
- (id)valueEncounteredWithRAREUTJSONArray:(RAREUTJSONArray *)parent
                             withNSString:(NSString *)arrayName
                                   withId:(id)value;
- (void)willParseObjectWithRAREUTJSONObject:(RAREUTJSONObject *)object;
- (void)willParseArrayWithRAREUTJSONArray:(RAREUTJSONArray *)array;
- (void)didParseObjectWithRAREUTJSONObject:(RAREUTJSONObject *)object;
- (void)didParseArrayWithRAREUTJSONArray:(RAREUTJSONArray *)array;
- (id)init;
@end

#endif // _RAREUTJSONTokener_H_
