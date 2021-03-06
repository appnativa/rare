//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/scripting/Functions.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREFunctions_H_
#define _RAREFunctions_H_

@class IOSByteArray;
@class IOSCharArray;
@class IOSIntArray;
@class IOSObjectArray;
@class JavaIoFile;
@class JavaIoInputStream;
@class JavaNetURL;
@class JavaUtilArrayList;
@class JavaUtilCalendar;
@class JavaUtilDate;
@class JavaUtilHashMap;
@class JavaUtilRandom;
@class JavaUtilRegexPattern;
@class RAREFunctionCallbackChainer;
@class RAREFunctionCallbackWaiter;
@class RAREGrouper;
@class RARESPOTWidget;
@class RAREUIColor;
@class RAREUICompoundBorder;
@class RAREUICompoundIcon;
@class RAREUICompoundPainter;
@class RAREUIDimension;
@class RAREUIFont;
@class RAREUIImage;
@class RAREUIProperties;
@class RAREUITextIcon;
@class RAREUTCharArray;
@class RAREUTCharScanner;
@class RAREUTContainsFilter;
@class RAREUTEqualityFilter;
@class RAREUTIntList;
@class RAREUTJSONArray;
@class RAREUTJSONObject;
@class RAREUTObjectCache;
@class RAREUTObjectHolder;
@class RAREUTRegularExpressionFilter;
@protocol JavaUtilComparator;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREUTiCancelable;
@protocol RAREUTiFilter;
@protocol RAREUTiFilterableList;
@protocol RAREUTiPreferences;
@protocol RAREiAnimator;
@protocol RAREiAnimatorValueListener;
@protocol RAREiBackgroundPainter;
@protocol RAREiEventHandler;
@protocol RAREiFunctionCallback;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformIcon;
@protocol RAREiPlatformPainter;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/iFunctionHandler.h"
#include "java/lang/Runnable.h"
#include "java/lang/ThreadLocal.h"

#define RAREFunctions_FUNC_ADD 44
#define RAREFunctions_FUNC_APPURL 34
#define RAREFunctions_FUNC_BASE64 17
#define RAREFunctions_FUNC_BOLD 20
#define RAREFunctions_FUNC_CHOP 6
#define RAREFunctions_FUNC_CODEBASE 33
#define RAREFunctions_FUNC_COLOR 43
#define RAREFunctions_FUNC_CONCAT 22
#define RAREFunctions_FUNC_CURRENTDATE 38
#define RAREFunctions_FUNC_CURRENTTIME 28
#define RAREFunctions_FUNC_CURRENT_TIME 42
#define RAREFunctions_FUNC_DATE 25
#define RAREFunctions_FUNC_DATE_TIME 26
#define RAREFunctions_FUNC_DOCBASE 37
#define RAREFunctions_FUNC_ENCODE 27
#define RAREFunctions_FUNC_END_TAG 24
#define RAREFunctions_FUNC_HMAC_MD5 12
#define RAREFunctions_FUNC_HMAC_SHA 13
#define RAREFunctions_FUNC_HTML 40
#define RAREFunctions_FUNC_ITALIC 21
#define RAREFunctions_FUNC_LENGTH 4
#define RAREFunctions_FUNC_LOCATION 46
#define RAREFunctions_FUNC_LOWER_CASE 2
#define RAREFunctions_FUNC_MD5 15
#define RAREFunctions_FUNC_NANOTIME 29
#define RAREFunctions_FUNC_ORIENTATION 45
#define RAREFunctions_FUNC_PIECE 7
#define RAREFunctions_FUNC_PROPERTY 32
#define RAREFunctions_FUNC_RANDOM 11
#define RAREFunctions_FUNC_REPLACE_PIECE 18
#define RAREFunctions_FUNC_RESOLVE 35
#define RAREFunctions_FUNC_RFORMAT 41
#define RAREFunctions_FUNC_SERVERBASE 36
#define RAREFunctions_FUNC_SHA 14
#define RAREFunctions_FUNC_SOUND 19
#define RAREFunctions_FUNC_START_TAG 23
#define RAREFunctions_FUNC_SUBSTRING 3
#define RAREFunctions_FUNC_TRIM 5
#define RAREFunctions_FUNC_UNDERLINED 39
#define RAREFunctions_FUNC_UPPER_CASE 1

@interface RAREFunctions : NSObject < RAREiFunctionHandler > {
 @public
  IOSObjectArray *zero_string_;
}

+ (int)FUNC_ADD;
+ (int)FUNC_APPURL;
+ (int)FUNC_BASE64;
+ (int)FUNC_BOLD;
+ (int)FUNC_CHOP;
+ (int)FUNC_CODEBASE;
+ (int)FUNC_COLOR;
+ (int)FUNC_CONCAT;
+ (int)FUNC_CURRENTDATE;
+ (int)FUNC_CURRENTTIME;
+ (int)FUNC_CURRENT_TIME;
+ (int)FUNC_DATE;
+ (int)FUNC_DATE_TIME;
+ (int)FUNC_DOCBASE;
+ (int)FUNC_END_TAG;
+ (int)FUNC_ENCODE;
+ (int)FUNC_HMAC_MD5;
+ (int)FUNC_HMAC_SHA;
+ (int)FUNC_HTML;
+ (int)FUNC_ITALIC;
+ (int)FUNC_LENGTH;
+ (int)FUNC_LOCATION;
+ (int)FUNC_LOWER_CASE;
+ (int)FUNC_MD5;
+ (int)FUNC_NANOTIME;
+ (int)FUNC_ORIENTATION;
+ (int)FUNC_PIECE;
+ (int)FUNC_PROPERTY;
+ (int)FUNC_RANDOM;
+ (int)FUNC_REPLACE_PIECE;
+ (int)FUNC_RESOLVE;
+ (int)FUNC_RFORMAT;
+ (int)FUNC_SERVERBASE;
+ (int)FUNC_SHA;
+ (int)FUNC_SOUND;
+ (int)FUNC_START_TAG;
+ (int)FUNC_SUBSTRING;
+ (int)FUNC_TRIM;
+ (int)FUNC_UNDERLINED;
+ (int)FUNC_UPPER_CASE;
+ (IOSCharArray *)BR;
+ (JavaLangThreadLocal *)perThreadCharArray;
+ (void)setPerThreadCharArray:(JavaLangThreadLocal *)perThreadCharArray;
+ (JavaLangThreadLocal *)perThreadScanner;
+ (void)setPerThreadScanner:(JavaLangThreadLocal *)perThreadScanner;
+ (JavaLangThreadLocal *)perThreadStringList;
+ (void)setPerThreadStringList:(JavaLangThreadLocal *)perThreadStringList;
+ (JavaUtilHashMap *)functionMap;
+ (JavaUtilRegexPattern *)urlPattern;
+ (IOSCharArray *)wwwPrefixChars;
+ (void)setWwwPrefixChars:(IOSCharArray *)wwwPrefixChars;
+ (IOSCharArray *)urlParamsTokens;
+ (void)setUrlParamsTokens:(IOSCharArray *)urlParamsTokens;
+ (IOSCharArray *)httpsPrefixChars;
+ (void)setHttpsPrefixChars:(IOSCharArray *)httpsPrefixChars;
+ (IOSCharArray *)httpPrefixChars;
+ (void)setHttpPrefixChars:(IOSCharArray *)httpPrefixChars;
+ (BOOL)_initialized;
+ (BOOL *)_initializedRef;
+ (RAREFunctions *)_instance;
+ (void)set_instance:(RAREFunctions *)_instance;
+ (NSString *)osVersion;
+ (void)setOsVersion:(NSString *)osVersion;
+ (JavaUtilRandom *)randomGenerator;
+ (void)setRandomGenerator:(JavaUtilRandom *)randomGenerator;
- (id)init;
+ (NSNumber *)addWithId:(id)a
                 withId:(id)b;
+ (NSString *)aesDecryptWithNSString:(NSString *)val
                        withNSString:(NSString *)password
                        withNSString:(NSString *)salt
                             withInt:(int)iteration
                         withBoolean:(BOOL)base64;
+ (NSString *)aesEncryptWithNSString:(NSString *)val
                        withNSString:(NSString *)password
                        withNSString:(NSString *)salt
                             withInt:(int)iteration
                         withBoolean:(BOOL)base64;
+ (NSString *)applicationURL;
+ (id<JavaUtilList>)asListWithId:(id)o;
+ (NSString *)base64WithNSString:(NSString *)val;
+ (NSString *)base64NOLFWithNSString:(NSString *)val;
+ (BOOL)booleanValueWithId:(id)o;
+ (BOOL)cacheDataWithNSString:(NSString *)name
        withJavaIoInputStream:(JavaIoInputStream *)inArg;
+ (BOOL)cacheDataWithNSString:(NSString *)name
                 withNSString:(NSString *)data;
+ (NSString *)charToStringWithChar:(unichar)c;
+ (NSString *)chopWithNSString:(NSString *)val
                       withInt:(int)size;
+ (NSString *)codeBase;
+ (NSString *)concatWithId:(id)a
                    withId:(id)b;
+ (NSString *)convertDateWithId:(id)date;
+ (NSString *)convertDateWithId:(id)date
                    withBoolean:(BOOL)display;
+ (NSString *)convertDateWithId:(id)date
                   withNSString:(NSString *)outputFormat;
+ (NSString *)convertDateWithId:(id)date
                   withNSString:(NSString *)inputFormat
                   withNSString:(NSString *)outputFormat;
+ (NSString *)convertDateTimeWithId:(id)date;
+ (NSString *)convertDateTimeWithId:(id)date
                        withBoolean:(BOOL)display;
+ (id<RAREiBackgroundPainter>)createBackgroundPainterWithRAREUIColor:(RAREUIColor *)bg;
+ (id<RAREiPlatformBorder>)createBorderWithNSString:(NSString *)borderString;
+ (id<RAREiPlatformIcon>)createBorderIconWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border
                                           withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
+ (JavaIoFile *)createCacheFileWithNSString:(NSString *)name;
+ (RAREUTObjectCache *)createCacheMapWithInt:(int)len
                                     withInt:(int)maxSize;
+ (JavaUtilCalendar *)createCalendarWithNSString:(NSString *)spec;
+ (id<RAREiPlatformIcon>)createColorIconWithRAREUIColor:(RAREUIColor *)color
                                                withInt:(int)width
                                                withInt:(int)height
                                withRAREiPlatformBorder:(id<RAREiPlatformBorder>)border;
+ (RAREUICompoundBorder *)createCompoundBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)firstBorder
                                              withRAREiPlatformBorder:(id<RAREiPlatformBorder>)secondBorder;
+ (RAREUICompoundIcon *)createCompoundIconWithRAREiPlatformIconArray:(IOSObjectArray *)icons;
+ (RAREUICompoundPainter *)createCompoundPainterWithRAREiPlatformPainter:(id<RAREiPlatformPainter>)firstPainter
                                                withRAREiPlatformPainter:(id<RAREiPlatformPainter>)secondPainter;
+ (id<JavaUtilMap>)createConcurrentHashMapWithInt:(int)len;
+ (RAREUTContainsFilter *)createContainsFilterWithNSString:(NSString *)value
                                               withBoolean:(BOOL)startsWith;
+ (JavaUtilDate *)createDateWithNSString:(NSString *)spec;
+ (id<RAREiPlatformIcon>)createEmptyIconWithInt:(int)width
                                        withInt:(int)height
                                withRAREUIColor:(RAREUIColor *)borderColor;
+ (RAREUTEqualityFilter *)createEqualityFilterWithNSString:(NSString *)value
                                               withBoolean:(BOOL)ignorecase;
+ (RAREFunctionCallbackChainer *)createFunctionCallbackChainner;
+ (RAREFunctionCallbackWaiter *)createFunctionCallbackWaiter;
+ (RAREGrouper *)createGrouper;
+ (id<JavaUtilMap>)createHashMapWithInt:(int)len;
+ (id<JavaUtilMap>)createIdentityHashMapWithInt:(int)len;
+ (id<JavaUtilList>)createIdentityListWithInt:(int)len;
+ (IOSIntArray *)createIntArrayWithInt:(int)size;
+ (RAREUTIntList *)createIntArrayListWithInt:(int)size;
+ (RAREUTJSONArray *)createJSONArrayWithId:(id)o;
+ (RAREUTJSONObject *)createJSONObjectWithId:(id)o;
+ (id<JavaUtilList>)createListWithId:(id)obj;
+ (IOSObjectArray *)createObjectArrayWithInt:(int)size;
+ (RAREUTObjectHolder *)createObjectHolderWithId:(id)key
                                          withId:(id)value;
+ (RAREUTRegularExpressionFilter *)createRegExFilterWithNSString:(NSString *)value
                                                     withBoolean:(BOOL)parse;
+ (id)createScriptableVarWithRAREiWidget:(id<RAREiWidget>)context
                                  withId:(id)javaobj;
+ (id<JavaUtilMap>)createSortedMap;
+ (IOSObjectArray *)createStringArrayWithInt:(int)size;
+ (RAREUITextIcon *)createTextIconWithNSString:(NSString *)text
                               withRAREUIColor:(RAREUIColor *)fg
                                withRAREUIFont:(RAREUIFont *)font
                       withRAREiPlatformBorder:(id<RAREiPlatformBorder>)border;
+ (RAREUIImage *)createTextImageWithNSString:(NSString *)text
                              withRAREUIFont:(RAREUIFont *)font;
+ (RAREUIImage *)createTextImageWithNSString:(NSString *)text
                              withRAREUIFont:(RAREUIFont *)font
                             withRAREUIColor:(RAREUIColor *)fg
                             withRAREUIColor:(RAREUIColor *)bg
                     withRAREiPlatformBorder:(id<RAREiPlatformBorder>)b
                                 withBoolean:(BOOL)square;
+ (RAREUITextIcon *)createTextPainterWithNSString:(NSString *)text;
+ (id<RAREiAnimator>)createValueAnimatorWithDouble:(double)start
                                        withDouble:(double)end
                                        withDouble:(double)inc
                                       withBoolean:(BOOL)accelerate
                                       withBoolean:(BOOL)decelerate
                    withRAREiAnimatorValueListener:(id<RAREiAnimatorValueListener>)l;
+ (NSString *)currentDateWithNSString:(NSString *)format;
+ (NSString *)currentDateWithRAREiWidget:(id<RAREiWidget>)context
                            withNSString:(NSString *)format;
+ (long long int)currentTime;
+ (NSString *)currentTimeWithNSString:(NSString *)format;
+ (NSString *)dateWithNSString:(NSString *)spec;
+ (NSString *)dateWithNSString:(NSString *)spec
                  withNSString:(NSString *)format;
+ (NSString *)dateTimeWithNSString:(NSString *)spec;
+ (NSString *)dateTimeWithNSString:(NSString *)spec
                      withNSString:(NSString *)format;
+ (NSString *)decodeWithNSString:(NSString *)str;
+ (NSString *)decodeBase64WithNSString:(NSString *)val;
+ (void)deleteCachedDataWithNSString:(NSString *)name;
+ (void)deleteFileWithJavaIoFile:(JavaIoFile *)file;
+ (void)disableDebugLogging;
+ (NSString *)documentBase;
+ (NSString *)documentServerBase;
+ (NSString *)encodeWithNSString:(NSString *)str;
+ (NSString *)encodeUrlWithNSString:(NSString *)str;
+ (void)eprintlnWithId:(id)o;
+ (NSString *)escapeWithNSString:(NSString *)val;
+ (NSString *)escapeHTMLWithNSString:(NSString *)str
                         withBoolean:(BOOL)whitespace
                         withBoolean:(BOOL)addHTMLTag;
- (NSString *)executeWithRAREiWidget:(id<RAREiWidget>)context
                        withNSString:(NSString *)function;
- (NSString *)resolveParameterWithRAREiWidget:(id<RAREiWidget>)context
                                 withNSString:(NSString *)s
                        withRAREUTCharScanner:(RAREUTCharScanner *)sc;
+ (id<RAREiEventHandler>)getEventHandlerWithNSString:(NSString *)className_;
- (NSString *)executeFunctionWithRAREiWidget:(id<RAREiWidget>)context
                                withNSString:(NSString *)name
                           withNSStringArray:(IOSObjectArray *)parameters;
+ (NSString *)expandWithNSString:(NSString *)pattern
               withNSStringArray:(IOSObjectArray *)args;
+ (id<RAREUTiCancelable>)filterInBackgroundWithJavaUtilList:(id<JavaUtilList>)list
                                          withRAREUTiFilter:(id<RAREUTiFilter>)filter
                                  withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
+ (RARESPOTWidget *)findWidgetWithRARESPOTWidget:(RARESPOTWidget *)w
                                    withNSString:(NSString *)name
                                     withBoolean:(BOOL)useNameMap;
+ (float)floatValueWithNSString:(NSString *)s;
+ (void)focusLaterWithRAREiWidget:(id<RAREiWidget>)widget;
+ (NSString *)formatWithNSString:(NSString *)pattern
               withNSObjectArray:(IOSObjectArray *)args;
+ (NSString *)generateKeyWithNSString:(NSString *)password
                         withNSString:(NSString *)salt
                              withInt:(int)iteration;
+ (NSString *)generateSaltWithInt:(int)bytes;
+ (NSString *)hmacMD5WithNSString:(NSString *)val
                     withNSString:(NSString *)key
                      withBoolean:(BOOL)base64;
+ (NSString *)hmacSHAWithNSString:(NSString *)val
                     withNSString:(NSString *)key
                      withBoolean:(BOOL)base64;
+ (NSString *)htmlReplaceWithNSString:(NSString *)s
             withJavaUtilRegexPattern:(JavaUtilRegexPattern *)urlPattern
                         withNSString:(NSString *)replacement
                          withBoolean:(BOOL)escape
                         withNSString:(NSString *)tag;
+ (NSString *)htmlWordWrapWithNSString:(NSString *)s
                               withInt:(int)width
                           withBoolean:(BOOL)html_tag;
+ (int)intValueWithId:(id)o;
+ (NSString *)joinWithJavaUtilList:(id<JavaUtilList>)list
                      withNSString:(NSString *)sep;
+ (NSString *)joinWithNSObjectArray:(IOSObjectArray *)array
                       withNSString:(NSString *)sep;
+ (int)lengthWithNSString:(NSString *)val
             withNSString:(NSString *)tok;
+ (NSString *)linefeedToHTMLBreakWithNSString:(NSString *)s;
+ (NSString *)linefeedToHTMLBreakWithNSString:(NSString *)s
                                  withBoolean:(BOOL)html;
+ (NSString *)lowerCaseWithNSString:(NSString *)val;
+ (NSString *)makeHyperlinksWithNSString:(NSString *)s
                             withBoolean:(BOOL)escape
                            withNSString:(NSString *)tag;
+ (NSString *)md5WithNSString:(NSString *)val;
+ (NSString *)md5WithNSString:(NSString *)val
                  withBoolean:(BOOL)base64;
+ (long long int)nanoTime;
+ (JavaUtilDate *)parseDateStringWithRAREiWidget:(id<RAREiWidget>)context
                                    withNSString:(NSString *)date;
+ (id<JavaUtilList>)parseJSONObjectWithRAREiWidget:(id<RAREiWidget>)context
                              withRAREUTJSONObject:(RAREUTJSONObject *)json
                                       withBoolean:(BOOL)tabular;
+ (id<JavaUtilMap>)parseOptionsStringWithNSString:(NSString *)options;
+ (id<JavaUtilMap>)parseOptionsStringWithNSString:(NSString *)options
                                         withChar:(unichar)delimiter
                                      withBoolean:(BOOL)unquote;
+ (NSString *)pieceWithNSString:(NSString *)val
                   withNSString:(NSString *)tok;
+ (NSString *)pieceWithNSString:(NSString *)val
                   withNSString:(NSString *)tok
                        withInt:(int)start;
+ (NSString *)pieceWithNSString:(NSString *)val
                   withNSString:(NSString *)tok
                        withInt:(int)start
                        withInt:(int)end;
+ (void)printWithNSObjectArray:(IOSObjectArray *)o;
+ (void)printlnWithNSObjectArray:(IOSObjectArray *)o;
+ (NSString *)propertyWithNSString:(NSString *)name
                      withNSString:(NSString *)def;
+ (NSString *)quoteWithNSString:(NSString *)str;
+ (long long int)randomLong;
+ (long long int)randomLongWithLong:(long long int)max;
+ (id<JavaUtilMap>)removeAllWithJavaUtilMap:(id<JavaUtilMap>)main
                            withJavaUtilMap:(id<JavaUtilMap>)remove;
+ (NSString *)replacePieceWithNSString:(NSString *)val
                          withNSString:(NSString *)tok
                               withInt:(int)tlen
                               withInt:(int)pos
                               withInt:(int)pos2
                          withNSString:(NSString *)rval;
+ (NSString *)resolveWithNSString:(NSString *)str;
+ (NSString *)resolveWithRAREiWidget:(id<RAREiWidget>)context
                        withNSString:(NSString *)str;
+ (NSString *)rformatWithNSString:(NSString *)resource_string
                withNSObjectArray:(IOSObjectArray *)args;
+ (id<RAREUTiCancelable>)searchInBackgroundWithJavaUtilList:(id<JavaUtilList>)list
                                          withRAREUTiFilter:(id<RAREUTiFilter>)filter
                                                withBoolean:(BOOL)returnIndexes
                                  withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
+ (NSString *)serverBase;
+ (NSString *)sha1WithNSString:(NSString *)val;
+ (NSString *)sha1WithByteArray:(IOSByteArray *)val
                    withBoolean:(BOOL)base64;
+ (NSString *)sha1WithNSString:(NSString *)val
                   withBoolean:(BOOL)base64;
+ (id<RAREUTiCancelable>)sortInBackgroundWithJavaUtilList:(id<JavaUtilList>)list
                                   withJavaUtilComparator:(id<JavaUtilComparator>)comparator
                                withRAREiFunctionCallback:(id<RAREiFunctionCallback>)cb;
+ (NSString *)stringValueWithInt:(int)number;
+ (NSString *)stringValueWithLong:(long long int)number;
+ (NSString *)stringValueWithDouble:(double)number;
+ (NSString *)stringValueWithId:(id)obj;
+ (NSString *)stripMnemonicWithNSString:(NSString *)text;
+ (NSString *)substringWithNSString:(NSString *)val
                            withInt:(int)start
                            withInt:(int)end;
+ (NSString *)titleCaseWithNSString:(NSString *)val;
+ (NSString *)titleCaseWithNSString:(NSString *)val
                       withNSString:(NSString *)wordSeparators;
+ (NSString *)toExternalFormWithJavaNetURL:(JavaNetURL *)url;
+ (NSString *)tokenReplacementWithNSString:(NSString *)s
                              withNSString:(NSString *)what
                              withNSString:(NSString *)with
                               withBoolean:(BOOL)html
                              withNSString:(NSString *)prefix
                              withNSString:(NSString *)suffix
                                   withInt:(int)maxLineCount;
+ (NSString *)tokenToHTMLBreakWithNSString:(NSString *)s
                              withNSString:(NSString *)tok
                               withBoolean:(BOOL)html;
+ (NSString *)tokenToHTMLBreakWithNSString:(NSString *)s
                              withNSString:(NSString *)tok
                               withBoolean:(BOOL)html
                              withNSString:(NSString *)prefix
                              withNSString:(NSString *)suffix
                                   withInt:(int)maxLineCount;
+ (NSString *)trimWithNSString:(NSString *)val;
+ (NSString *)unescapeWithNSString:(NSString *)val;
+ (NSString *)unescapeQuotedStringWithNSString:(NSString *)val;
+ (id<JavaUtilList>)ungroupWithJavaUtilList:(id<JavaUtilList>)list
                                    withInt:(int)groupings
                           withJavaUtilList:(id<JavaUtilList>)outArg;
+ (void)updateUIColorWithNSString:(NSString *)name
                           withId:(id)value;
+ (NSString *)upperCaseWithNSString:(NSString *)val;
+ (NSString *)utf8StringWithNSString:(NSString *)value;
+ (void)setOptimizationEnabledWithBoolean:(BOOL)enabled;
+ (void)setRelativeFontSizeWithFloat:(float)size;
+ (void)setStrictScriptingModeWithBoolean:(BOOL)strict;
+ (id)getCachedDataWithNSString:(NSString *)name
                    withBoolean:(BOOL)asString;
- (RAREFunctions *)getFunctions;
+ (id<RAREiFunctionHandler>)getInstance;
+ (long long int)getLastConnectionSuccessTime;
+ (NSString *)getLinesWithNSString:(NSString *)s
                           withInt:(int)lines;
+ (NSString *)getLocation;
+ (id<JavaUtilList>)getMonths;
+ (id<JavaUtilList>)getMonthsShortNames;
+ (NSString *)getOs;
+ (NSString *)getOsVersion;
+ (float)getPixelMultipler;
+ (id<RAREUTiPreferences>)getPreferencesWithNSString:(NSString *)appKey;
+ (float)getRelativeFontSize;
+ (NSString *)getScreenOrientation;
+ (RAREUIDimension *)getScreenSize;
+ (RAREUIProperties *)getUIDefaults;
+ (NSString *)getURLQueryParameterWithNSString:(NSString *)url
                                  withNSString:(NSString *)param;
+ (BOOL)isAndroid;
+ (BOOL)isExceptionWithId:(id)o;
+ (BOOL)isJava6OrAbove;
+ (BOOL)isJava7OrAbove;
+ (BOOL)isLinux;
+ (BOOL)isMac;
+ (BOOL)isOptimizationEnabled;
+ (BOOL)isEqualWithId:(id)o1
               withId:(id)o2;
+ (BOOL)isPrintableCharWithChar:(unichar)c;
+ (BOOL)isRunningInBackground;
+ (BOOL)isStrictScriptingMode;
+ (BOOL)isTouchDevice;
+ (BOOL)isTouchableDevice;
+ (BOOL)isUnix;
+ (BOOL)isWindows;
+ (void)checkParmLengthWithRAREiWidget:(id<RAREiWidget>)w
                               withInt:(int)actual
                               withInt:(int)required;
+ (void)initializeFunctionMap OBJC_METHOD_FAMILY_NONE;
- (void)copyAllFieldsTo:(RAREFunctions *)other;
@end

J2OBJC_FIELD_SETTER(RAREFunctions, zero_string_, IOSObjectArray *)

typedef RAREFunctions ComAppnativaRareScriptingFunctions;

@interface RAREFunctions_$1 : JavaLangThreadLocal {
}

- (RAREUTCharArray *)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

@interface RAREFunctions_$2 : JavaLangThreadLocal {
}

- (RAREUTCharScanner *)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

@interface RAREFunctions_$3 : JavaLangThreadLocal {
}

- (JavaUtilArrayList *)initialValue OBJC_METHOD_FAMILY_NONE;
- (id)init;
@end

@interface RAREFunctions_$4 : NSObject < JavaLangRunnable > {
 @public
  id<JavaUtilList> val$list_;
  id<RAREUTiFilter> val$filter_;
  id<RAREiFunctionCallback> val$cb_;
}

- (void)run;
- (id)initWithJavaUtilList:(id<JavaUtilList>)capture$0
         withRAREUTiFilter:(id<RAREUTiFilter>)capture$1
 withRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$2;
@end

J2OBJC_FIELD_SETTER(RAREFunctions_$4, val$list_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RAREFunctions_$4, val$filter_, id<RAREUTiFilter>)
J2OBJC_FIELD_SETTER(RAREFunctions_$4, val$cb_, id<RAREiFunctionCallback>)

@interface RAREFunctions_$4_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREFunctions_$4 *this$0_;
  id<RAREUTiFilterableList> val$fl_;
}

- (void)run;
- (id)initWithRAREFunctions_$4:(RAREFunctions_$4 *)outer$
     withRAREUTiFilterableList:(id<RAREUTiFilterableList>)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREFunctions_$4_$1, this$0_, RAREFunctions_$4 *)
J2OBJC_FIELD_SETTER(RAREFunctions_$4_$1, val$fl_, id<RAREUTiFilterableList>)

@interface RAREFunctions_$5 : NSObject < JavaLangRunnable > {
 @public
  id<RAREiWidget> val$widget_;
}

- (void)run;
- (id)initWithRAREiWidget:(id<RAREiWidget>)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREFunctions_$5, val$widget_, id<RAREiWidget>)

@interface RAREFunctions_$6 : NSObject < JavaLangRunnable > {
 @public
  id<JavaUtilList> val$list_;
  id<RAREUTiFilter> val$filter_;
  BOOL val$returnIndexes_;
  id<RAREiFunctionCallback> val$cb_;
}

- (void)run;
- (id)initWithJavaUtilList:(id<JavaUtilList>)capture$0
         withRAREUTiFilter:(id<RAREUTiFilter>)capture$1
               withBoolean:(BOOL)capture$2
 withRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$3;
@end

J2OBJC_FIELD_SETTER(RAREFunctions_$6, val$list_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RAREFunctions_$6, val$filter_, id<RAREUTiFilter>)
J2OBJC_FIELD_SETTER(RAREFunctions_$6, val$cb_, id<RAREiFunctionCallback>)

@interface RAREFunctions_$6_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREFunctions_$6 *this$0_;
  id val$results_;
}

- (void)run;
- (id)initWithRAREFunctions_$6:(RAREFunctions_$6 *)outer$
                        withId:(id)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREFunctions_$6_$1, this$0_, RAREFunctions_$6 *)
J2OBJC_FIELD_SETTER(RAREFunctions_$6_$1, val$results_, id)

@interface RAREFunctions_$7 : NSObject < JavaLangRunnable > {
 @public
  id<JavaUtilComparator> val$comparator_;
  id<JavaUtilList> val$list_;
  id<RAREiFunctionCallback> val$cb_;
}

- (void)run;
- (id)initWithJavaUtilComparator:(id<JavaUtilComparator>)capture$0
                withJavaUtilList:(id<JavaUtilList>)capture$1
       withRAREiFunctionCallback:(id<RAREiFunctionCallback>)capture$2;
@end

J2OBJC_FIELD_SETTER(RAREFunctions_$7, val$comparator_, id<JavaUtilComparator>)
J2OBJC_FIELD_SETTER(RAREFunctions_$7, val$list_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RAREFunctions_$7, val$cb_, id<RAREiFunctionCallback>)

@interface RAREFunctions_$7_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREFunctions_$7 *this$0_;
}

- (void)run;
- (id)initWithRAREFunctions_$7:(RAREFunctions_$7 *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREFunctions_$7_$1, this$0_, RAREFunctions_$7 *)

#endif // _RAREFunctions_H_
