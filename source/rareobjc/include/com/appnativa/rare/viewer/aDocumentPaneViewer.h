//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/aDocumentPaneViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaDocumentPaneViewer_H_
#define _RAREaDocumentPaneViewer_H_

@class IOSCharArray;
@class JavaIoReader;
@class RAREActionLink;
@class RARESPOTDocumentPane;
@class RARESPOTViewer;
@class RAREUIColor;
@protocol RAREiContainer;
@protocol RAREiPlatformTextEditor;
@protocol RAREiTextAttributes;

#import "JreEmulation.h"
#include "com/appnativa/rare/viewer/aPlatformViewer.h"
#include "java/io/Writer.h"
#include "java/lang/Runnable.h"

@interface RAREaDocumentPaneViewer : RAREaPlatformViewer {
 @public
  BOOL htmlDocument_;
  BOOL modified_;
  id<RAREiPlatformTextEditor> textEditor_;
}

- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)appendTextWithNSString:(NSString *)text;
- (void)boldText;
- (BOOL)canCut;
- (BOOL)canDelete;
- (BOOL)canPaste;
- (void)clearContents;
- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (void)decreaseIndent;
- (void)dispose;
- (BOOL)editorHasEditingToolbar;
- (void)enlargeFont;
- (void)handleActionLinkWithRAREActionLink:(RAREActionLink *)link
                               withBoolean:(BOOL)deferred;
- (void)handleReaderWithNSString:(NSString *)mimeType
                withJavaIoReader:(JavaIoReader *)reader;
- (void)increaseIndent;
- (void)insertHTMLWithInt:(int)pos
             withNSString:(NSString *)html;
- (void)insertTextWithInt:(int)pos
             withNSString:(NSString *)text;
- (void)italicText;
- (void)newHTMLDocument OBJC_METHOD_FAMILY_NONE;
- (void)newPlainTextDocument OBJC_METHOD_FAMILY_NONE;
- (void)pasteTextWithNSString:(NSString *)text;
- (void)selectWithInt:(int)beginIndex;
- (void)selectWithInt:(int)beginIndex
              withInt:(int)endIndex;
- (void)selectAll;
- (void)shrinkFont;
- (void)strikeThroughText;
- (void)subscriptText;
- (void)superscriptText;
- (void)underlineText;
- (void)writeHTTPContentWithBoolean:(BOOL)first
                   withJavaIoWriter:(JavaIoWriter *)writer
                       withNSString:(NSString *)boundary;
- (BOOL)writeJSONValueWithBoolean:(BOOL)first
                 withJavaIoWriter:(JavaIoWriter *)writer;
- (void)setCaretPositionWithInt:(int)position;
- (void)setEditableWithBoolean:(BOOL)editable;
- (void)setFollowHyperlinksWithBoolean:(BOOL)follow;
- (void)setHTMLWithNSString:(NSString *)text;
- (void)setPlainTextWithNSString:(NSString *)text;
- (void)setTextWithNSString:(NSString *)text;
- (void)setTextWithNSString:(NSString *)text
                withBoolean:(BOOL)html;
- (void)setTextColorWithRAREUIColor:(RAREUIColor *)c;
- (void)setTextFontFamilyWithNSString:(NSString *)family;
- (void)setTextFontSizeWithInt:(int)size;
- (void)setUnModified;
- (void)setValueWithId:(id)value;
- (void)setWordWrapWithBoolean:(BOOL)wrap;
- (id<RAREiTextAttributes>)getAttributeSetWithInt:(int)pos
                                      withBoolean:(BOOL)paragraph;
- (id<RAREiTextAttributes>)getAttributeSetAtCursorWithBoolean:(BOOL)paragraph;
- (id<RAREiTextAttributes>)getAttributeSetForSelectionWithBoolean:(BOOL)paragraph;
- (int)getCaretPosition;
- (NSString *)getHTMLText;
- (NSString *)getPlainText;
- (id)getSelection;
- (NSString *)getText;
- (int)getTextLength;
- (id)getValue;
- (NSString *)getValueAsString;
- (JavaIoWriter *)getWriter;
- (BOOL)isFollowHyperlinks;
- (BOOL)isMultipartContent;
- (BOOL)isOverwriteMode;
- (BOOL)isPlainTextDocument;
- (BOOL)isWordWrap;
- (void)configureExWithRARESPOTDocumentPane:(RARESPOTDocumentPane *)cfg;
- (id<RAREiPlatformTextEditor>)createEditorWithRARESPOTDocumentPane:(RARESPOTDocumentPane *)cfg;
- (void)copyAllFieldsTo:(RAREaDocumentPaneViewer *)other;
@end

J2OBJC_FIELD_SETTER(RAREaDocumentPaneViewer, textEditor_, id<RAREiPlatformTextEditor>)

typedef RAREaDocumentPaneViewer ComAppnativaRareViewerADocumentPaneViewer;

@interface RAREaDocumentPaneViewer_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREaDocumentPaneViewer *this$0_;
  NSString *val$data_;
}

- (void)run;
- (id)initWithRAREaDocumentPaneViewer:(RAREaDocumentPaneViewer *)outer$
                         withNSString:(NSString *)capture$0;
@end

J2OBJC_FIELD_SETTER(RAREaDocumentPaneViewer_$1, this$0_, RAREaDocumentPaneViewer *)
J2OBJC_FIELD_SETTER(RAREaDocumentPaneViewer_$1, val$data_, NSString *)

@interface RAREaDocumentPaneViewer_$2 : JavaIoWriter {
 @public
  RAREaDocumentPaneViewer *this$0_;
}

- (void)writeWithCharArray:(IOSCharArray *)cbuf
                   withInt:(int)off
                   withInt:(int)len;
- (void)flush;
- (void)close;
- (id)initWithRAREaDocumentPaneViewer:(RAREaDocumentPaneViewer *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREaDocumentPaneViewer_$2, this$0_, RAREaDocumentPaneViewer *)

#endif // _RAREaDocumentPaneViewer_H_
