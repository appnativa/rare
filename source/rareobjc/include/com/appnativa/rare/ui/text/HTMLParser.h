//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-android-htmllabel/com/appnativa/rare/ui/text/HTMLParser.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREHTMLParser_H_
#define _RAREHTMLParser_H_

@class JavaLangStringBuilder;
@class RAREHTMLCharSequence;
@class RAREHTMLParser_ANode;
@class RAREUIColor;
@class RAREUIFont;
@protocol AndroidTextHtml_ImageGetter;
@protocol AndroidTextHtml_TagHandler;
@protocol AndroidTextSpanned;

#import "JreEmulation.h"
#include "android/text/Html.h"
#include "android/text/iNodeBuilder.h"

@interface RAREHTMLParser : NSObject < AndroidTextiNodeBuilder > {
}

+ (RAREHTMLParser *)_instance;
+ (void)set_instance:(RAREHTMLParser *)_instance;
- (id)init;
+ (RAREHTMLCharSequence *)fromFontWithNSString:(NSString *)source
                                withRAREUIFont:(RAREUIFont *)font;
+ (RAREHTMLCharSequence *)fromHtmlWithNSString:(NSString *)source
                     withJavaLangStringBuilder:(JavaLangStringBuilder *)plaintTextOutput
                                withRAREUIFont:(RAREUIFont *)base;
+ (RAREHTMLCharSequence *)fromHtmlWithNSString:(NSString *)source
                withAndroidTextHtml_TagHandler:(id<AndroidTextHtml_TagHandler>)tagHandler
                     withJavaLangStringBuilder:(JavaLangStringBuilder *)plaintTextOutput
                                withRAREUIFont:(RAREUIFont *)base;
- (void)withinHtmlWithAndroidTextHtml_Node:(AndroidTextHtml_Node *)outArg
                    withAndroidTextSpanned:(id<AndroidTextSpanned>)text;
- (NSString *)getPlainTextWithNSString:(NSString *)source;
+ (JavaLangStringBuilder *)getPlainTextWithNSString:(NSString *)source
                    withAndroidTextHtml_ImageGetter:(id<AndroidTextHtml_ImageGetter>)imageGetter
                     withAndroidTextHtml_TagHandler:(id<AndroidTextHtml_TagHandler>)tagHandler
                          withJavaLangStringBuilder:(JavaLangStringBuilder *)plaintTextOutput;
+ (JavaLangStringBuilder *)nodeToStringWithRAREHTMLParser_ANode:(RAREHTMLParser_ANode *)node
                                      withJavaLangStringBuilder:(JavaLangStringBuilder *)sb;
+ (void)updateAttributesWithRAREHTMLParser_ANode:(RAREHTMLParser_ANode *)node
                                          withId:(id)astring;
- (void)withinBlockquoteWithAndroidTextHtml_Node:(AndroidTextHtml_Node *)outArg
                          withAndroidTextSpanned:(id<AndroidTextSpanned>)text
                                         withInt:(int)start
                                         withInt:(int)end;
- (void)withinDivWithAndroidTextHtml_Node:(AndroidTextHtml_Node *)outArg
                   withAndroidTextSpanned:(id<AndroidTextSpanned>)text
                                  withInt:(int)start
                                  withInt:(int)end;
- (void)withinParagraphWithAndroidTextHtml_Node:(AndroidTextHtml_Node *)parent
                         withAndroidTextSpanned:(id<AndroidTextSpanned>)text
                                        withInt:(int)start
                                        withInt:(int)end
                                        withInt:(int)nl
                                    withBoolean:(BOOL)last;
- (void)withinStyleWithAndroidTextHtml_Node:(AndroidTextHtml_Node *)outArg
                     withAndroidTextSpanned:(id<AndroidTextSpanned>)text
                                    withInt:(int)start
                                    withInt:(int)end;
+ (void)addAlignmentWithId:(id)astring
                   withInt:(int)val
                   withInt:(int)offset
                   withInt:(int)length;
+ (void)addSubSuperScriptWithId:(id)astring
                        withInt:(int)val
                        withInt:(int)offset
                        withInt:(int)length;
+ (id)createAttributedStringWithNSString:(NSString *)text;
@end

typedef RAREHTMLParser ComAppnativaRareUiTextHTMLParser;

@interface RAREHTMLParser_ANode : AndroidTextHtml_Node {
 @public
  int subSuperScript_;
  int alignment_;
  RAREUIColor *color_;
  RAREUIFont *font_;
  int length_;
  int offset_;
  __weak RAREHTMLParser_ANode *parent_;
}

- (RAREHTMLParser_ANode *)newChild OBJC_METHOD_FAMILY_NONE;
- (void)updateAttributedStringWithId:(id)astring;
- (int)textLength;
- (RAREUIFont *)getFont;
- (id)init;
- (void)copyAllFieldsTo:(RAREHTMLParser_ANode *)other;
@end

J2OBJC_FIELD_SETTER(RAREHTMLParser_ANode, color_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREHTMLParser_ANode, font_, RAREUIFont *)

#endif // _RAREHTMLParser_H_