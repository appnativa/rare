//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/Spanned.java
//
//  Created by decoteaud on 9/15/15.
//

#ifndef _AndroidTextSpanned_H_
#define _AndroidTextSpanned_H_

@class IOSClass;
@class IOSObjectArray;

#import "JreEmulation.h"
#include "java/lang/CharSequence.h"

#define AndroidTextSpanned_SPAN_COMPOSING 256
#define AndroidTextSpanned_SPAN_EXCLUSIVE_EXCLUSIVE 33
#define AndroidTextSpanned_SPAN_EXCLUSIVE_INCLUSIVE 34
#define AndroidTextSpanned_SPAN_INCLUSIVE_EXCLUSIVE 17
#define AndroidTextSpanned_SPAN_INCLUSIVE_INCLUSIVE 18
#define AndroidTextSpanned_SPAN_INTERMEDIATE 512
#define AndroidTextSpanned_SPAN_MARK_MARK 17
#define AndroidTextSpanned_SPAN_MARK_POINT 18
#define AndroidTextSpanned_SPAN_PARAGRAPH 51
#define AndroidTextSpanned_SPAN_POINT_MARK 33
#define AndroidTextSpanned_SPAN_POINT_MARK_MASK 51
#define AndroidTextSpanned_SPAN_POINT_POINT 34
#define AndroidTextSpanned_SPAN_PRIORITY 16711680
#define AndroidTextSpanned_SPAN_PRIORITY_SHIFT 16
#define AndroidTextSpanned_SPAN_USER -16777216
#define AndroidTextSpanned_SPAN_USER_SHIFT 24

@protocol AndroidTextSpanned < JavaLangCharSequence, NSObject, JavaObject >
- (IOSObjectArray *)getSpansWithInt:(int)start
                            withInt:(int)end
                       withIOSClass:(IOSClass *)type;
- (int)getSpanStartWithId:(id)tag;
- (int)getSpanEndWithId:(id)tag;
- (int)getSpanFlagsWithId:(id)tag;
- (int)nextSpanTransitionWithInt:(int)start
                         withInt:(int)limit
                    withIOSClass:(IOSClass *)type;
- (void)dispose;
@end

@interface AndroidTextSpanned : NSObject {
}
+ (int)SPAN_POINT_MARK_MASK;
+ (int)SPAN_MARK_MARK;
+ (int)SPAN_MARK_POINT;
+ (int)SPAN_POINT_MARK;
+ (int)SPAN_POINT_POINT;
+ (int)SPAN_PARAGRAPH;
+ (int)SPAN_INCLUSIVE_EXCLUSIVE;
+ (int)SPAN_INCLUSIVE_INCLUSIVE;
+ (int)SPAN_EXCLUSIVE_EXCLUSIVE;
+ (int)SPAN_EXCLUSIVE_INCLUSIVE;
+ (int)SPAN_COMPOSING;
+ (int)SPAN_INTERMEDIATE;
+ (int)SPAN_USER_SHIFT;
+ (int)SPAN_USER;
+ (int)SPAN_PRIORITY_SHIFT;
+ (int)SPAN_PRIORITY;
@end

#endif // _AndroidTextSpanned_H_
