//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/android/text/style/LeadingMarginSpan.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARELeadingMarginSpan_H_
#define _RARELeadingMarginSpan_H_

#import "JreEmulation.h"
#include "android/text/style/ParagraphStyle.h"
#include "android/text/style/WrapTogetherSpan.h"

@protocol RARELeadingMarginSpan < RAREParagraphStyle, NSObject, JavaObject >
- (int)getLeadingMarginWithBoolean:(BOOL)first;
@end

#define AndroidTextStyleLeadingMarginSpan RARELeadingMarginSpan

@protocol RARELeadingMarginSpan_LeadingMarginSpan2 < RARELeadingMarginSpan, RAREWrapTogetherSpan, NSObject, JavaObject >
- (int)getLeadingMarginLineCount;
@end

@interface RARELeadingMarginSpan_Standard : NSObject < RARELeadingMarginSpan > {
 @public
  int mFirst_, mRest_;
}

- (id)initWithInt:(int)first
          withInt:(int)rest;
- (id)initWithInt:(int)every;
- (int)getSpanTypeId;
- (int)getLeadingMarginWithBoolean:(BOOL)first;
- (void)copyAllFieldsTo:(RARELeadingMarginSpan_Standard *)other;
@end

#endif // _RARELeadingMarginSpan_H_
