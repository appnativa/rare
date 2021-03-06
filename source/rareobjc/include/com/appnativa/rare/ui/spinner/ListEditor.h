//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-spinner/com/appnativa/rare/ui/spinner/ListEditor.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREListEditor_H_
#define _RAREListEditor_H_

@class RARESpinnerListModel;
@protocol JavaLangCharSequence;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/listener/iTextChangeListener.h"
#include "com/appnativa/rare/ui/spinner/DefaultEditor.h"

@interface RAREListEditor : RAREDefaultEditor < RAREiTextChangeListener > {
 @public
  BOOL dirty_;
  int dirtyIndex_;
}

- (id)initWithRARESpinnerListModel:(RARESpinnerListModel *)model;
- (void)beforeTextChangedWithJavaLangCharSequence:(id<JavaLangCharSequence>)cs
                                          withInt:(int)start
                                          withInt:(int)count
                                          withInt:(int)after;
- (void)onTextChangedWithJavaLangCharSequence:(id<JavaLangCharSequence>)cs
                                      withInt:(int)start
                                      withInt:(int)before
                                      withInt:(int)count;
- (BOOL)shouldStopEditingWithId:(id)source;
- (void)textChangedWithId:(id)source;
- (BOOL)textChangingWithId:(id)view
                   withInt:(int)start
                   withInt:(int)end
  withJavaLangCharSequence:(id<JavaLangCharSequence>)replacementString;
- (void)customizeEditor;
- (void)copyAllFieldsTo:(RAREListEditor *)other;
@end

typedef RAREListEditor ComAppnativaRareUiSpinnerListEditor;

#endif // _RAREListEditor_H_
