//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/platform/apple/ui/view/TextAreaView.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARETextAreaView_H_
#define _RARETextAreaView_H_

@class RAREContainerPanel;
@class RAREUIColor;
@class RAREUIFont;
@protocol JavaLangCharSequence;
@protocol RAREiPlatformComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/platform/apple/ui/view/aTextEditorView.h"

@interface RARETextAreaView : RAREaTextEditorView {
 @public
  RAREContainerPanel *container_;
}

- (id)init;
- (id)initWithNSString:(NSString *)text;
- (void)removeNativeBorder;
- (void)setAllowTextAttributesWithBoolean:(BOOL)allow;
- (void)setAutoShowKeyboardWithBoolean:(BOOL)show;
- (void)setSuggestionsEnabledWithBoolean:(BOOL)enabled;
- (void)setEditableWithBoolean:(BOOL)editable;
- (void)setFontWithRAREUIFont:(RAREUIFont *)font;
- (void)setMarginWithInt:(int)top
                 withInt:(int)right
                 withInt:(int)bottom
                 withInt:(int)left;
- (void)setShowKeyBoardWithBoolean:(BOOL)show;
- (void)setTextWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (void)setTextExWithJavaLangCharSequence:(id<JavaLangCharSequence>)text;
- (void)setVisibleLinesWithInt:(int)lines;
- (id<RAREiPlatformComponent>)getComponent;
- (NSString *)getHtmlText;
- (NSString *)getPlainText;
- (int)getSelectionEnd;
- (int)getSelectionStart;
- (NSString *)getText;
- (int)getTextLength;
- (BOOL)isEditable;
- (BOOL)isScrollView;
- (void)disposeEx;
- (void)setForegroundColorExWithRAREUIColor:(RAREUIColor *)fg;
+ (id)createProxy;
- (void)copyAllFieldsTo:(RARETextAreaView *)other;
@end

J2OBJC_FIELD_SETTER(RARETextAreaView, container_, RAREContainerPanel *)

typedef RARETextAreaView ComAppnativaRarePlatformAppleUiViewTextAreaView;

#endif // _RARETextAreaView_H_
