//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/ui/renderer/TreeItemRenderer.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARETreeItemRenderer_H_
#define _RARETreeItemRenderer_H_

@class RAREColumn;
@class RARERenderableDataItem;
@class RARETreeViewer;
@protocol JavaLangCharSequence;
@protocol RAREiPlatformRenderingComponent;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/renderer/aTreeItemRenderer.h"

@interface RARETreeItemRenderer : RAREaTreeItemRenderer {
 @public
  RARETreeViewer *treeViewer_;
}

- (id)initWithRARETreeViewer:(RARETreeViewer *)treeViewer;
- (void)setIconAndAlignmentWithRAREiPlatformRenderingComponent:(id<RAREiPlatformRenderingComponent>)rc
                                      withJavaLangCharSequence:(id<JavaLangCharSequence>)text
                                    withRARERenderableDataItem:(RARERenderableDataItem *)item
                                    withRARERenderableDataItem:(RARERenderableDataItem *)row
                                                withRAREColumn:(RAREColumn *)col
                                                   withBoolean:(BOOL)enabled
                                                   withBoolean:(BOOL)center
                                                   withBoolean:(BOOL)top
                                                   withBoolean:(BOOL)seticon
                                                   withBoolean:(BOOL)oexpanded;
- (void)copyAllFieldsTo:(RARETreeItemRenderer *)other;
@end

J2OBJC_FIELD_SETTER(RARETreeItemRenderer, treeViewer_, RARETreeViewer *)

typedef RARETreeItemRenderer ComAppnativaRareUiRendererTreeItemRenderer;

#endif // _RARETreeItemRenderer_H_
