//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/text/HTMLCharSequence.java
//
//  Created by decoteaud on 12/8/15.
//

#ifndef _RAREHTMLCharSequence_H_
#define _RAREHTMLCharSequence_H_

@class RAREUIFont;
@protocol RAREiHTMLEncoder;

#import "JreEmulation.h"
#include "java/lang/CharSequence.h"

@interface RAREHTMLCharSequence : NSObject < JavaLangCharSequence > {
 @public
  id attributedText_;
  NSString *text_;
}

+ (id<RAREiHTMLEncoder>)htmlEncoder;
+ (void)setHtmlEncoder:(id<RAREiHTMLEncoder>)htmlEncoder;
- (id)initWithNSString:(NSString *)text
                withId:(id)attributedText;
- (unichar)charAtWithInt:(int)index;
+ (id<JavaLangCharSequence>)checkSequenceWithJavaLangCharSequence:(id<JavaLangCharSequence>)s
                                                   withRAREUIFont:(RAREUIFont *)font;
- (int)sequenceLength;
+ (id<JavaLangCharSequence>)parseHTMLSequenceWithJavaLangCharSequence:(id<JavaLangCharSequence>)s
                                                       withRAREUIFont:(RAREUIFont *)font;
- (id<JavaLangCharSequence>)subSequenceFrom:(int)beginIndex to:(int)endIndex;
- (NSString *)sequenceDescription;
- (id)getAttributedText;
- (NSString *)getPlainText;
+ (NSString *)getPlainTextWithNSString:(NSString *)html;
+ (id<RAREiHTMLEncoder>)getEncoder;
- (void)copyAllFieldsTo:(RAREHTMLCharSequence *)other;
@end

J2OBJC_FIELD_SETTER(RAREHTMLCharSequence, attributedText_, id)
J2OBJC_FIELD_SETTER(RAREHTMLCharSequence, text_, NSString *)

typedef RAREHTMLCharSequence ComAppnativaRareUiTextHTMLCharSequence;

#endif // _RAREHTMLCharSequence_H_
