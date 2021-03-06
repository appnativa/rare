//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-spinner/com/appnativa/rare/ui/spinner/NumberEditor.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARENumberEditor_H_
#define _RARENumberEditor_H_

@class JavaTextDecimalFormat;
@class RARESpinnerNumberModel;
@protocol JavaLangCharSequence;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/listener/iTextChangeListener.h"
#include "com/appnativa/rare/ui/spinner/DefaultEditor.h"

@interface RARENumberEditor : RAREDefaultEditor < RAREiTextChangeListener > {
 @public
  JavaTextDecimalFormat *numberFormat_;
  BOOL supportDecimalValues_;
}

- (id)initWithRARESpinnerNumberModel:(RARESpinnerNumberModel *)model;
- (void)modelChanged;
- (BOOL)shouldStopEditingWithId:(id)source;
- (void)textChangedWithId:(id)source;
- (BOOL)textChangingWithId:(id)view
                   withInt:(int)start
                   withInt:(int)end
  withJavaLangCharSequence:(id<JavaLangCharSequence>)replacementString;
- (void)setEditableWithBoolean:(BOOL)editable;
- (void)customizeEditor;
- (void)copyAllFieldsTo:(RARENumberEditor *)other;
@end

J2OBJC_FIELD_SETTER(RARENumberEditor, numberFormat_, JavaTextDecimalFormat *)

typedef RARENumberEditor ComAppnativaRareUiSpinnerNumberEditor;

#endif // _RARENumberEditor_H_
