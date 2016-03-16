//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/widget/TextAreaWidget.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARETextAreaWidget_H_
#define _RARETextAreaWidget_H_

@class IOSCharArray;
@class RARESPOTTextField;
@protocol RAREiContainer;
@protocol RAREiPlatformTextEditor;
@protocol RAREiViewer;

#import "JreEmulation.h"
#include "com/appnativa/rare/widget/TextFieldWidget.h"
#include "java/io/Writer.h"

@interface RARETextAreaWidget : RARETextFieldWidget {
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)setVisibleLinesWithInt:(int)lines;
- (id<RAREiPlatformTextEditor>)createEditorAndComponentsWithRAREiViewer:(id<RAREiViewer>)viewer
                                                  withRARESPOTTextField:(RARESPOTTextField *)cfg;
- (JavaIoWriter *)getWriter;
@end

typedef RARETextAreaWidget ComAppnativaRareWidgetTextAreaWidget;

@interface RARETextAreaWidget_$1 : JavaIoWriter {
 @public
  RARETextAreaWidget *this$0_;
}

- (void)writeWithCharArray:(IOSCharArray *)cbuf
                   withInt:(int)off
                   withInt:(int)len;
- (void)flush;
- (void)close;
- (id)initWithRARETextAreaWidget:(RARETextAreaWidget *)outer$;
@end

J2OBJC_FIELD_SETTER(RARETextAreaWidget_$1, this$0_, RARETextAreaWidget *)

#endif // _RARETextAreaWidget_H_
