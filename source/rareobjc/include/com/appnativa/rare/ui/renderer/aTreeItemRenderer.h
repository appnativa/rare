//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/ui/renderer/aTreeItemRenderer.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaTreeItemRenderer_H_
#define _RAREaTreeItemRenderer_H_

@class RAREUIInsets;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/renderer/ListItemRenderer.h"

@interface RAREaTreeItemRenderer : RAREListItemRenderer {
 @public
  BOOL showItemIcons_;
  BOOL empty_;
  BOOL expanded_;
  BOOL leaf_;
  int leftOffset_;
  int selectionLeftOffset_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)handleSelctionBackground;
- (void)prepareForEmptyItem;
- (void)setInsetsWithRAREUIInsets:(RAREUIInsets *)insets;
- (void)setItemStateWithBoolean:(BOOL)isLeaf
                    withBoolean:(BOOL)isExpanded
                        withInt:(int)indent;
- (int)getLeftOffset;
- (void)copyAllFieldsTo:(RAREaTreeItemRenderer *)other;
@end

typedef RAREaTreeItemRenderer ComAppnativaRareUiRendererATreeItemRenderer;

#endif // _RAREaTreeItemRenderer_H_