//
//  RAREUITableViewCell.h
//  RareIOS
//
//  Created by Don DeCoteau on 5/15/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "com/appnativa/rare/ui/RenderableDataItem.h"
@class RAREUIRectangle;
@class RAREAppleGraphics;
@class RAREListView;
@class RAREUITableContentView;
@protocol RAREiTreeItem;
@interface RAREUITableViewCell : UITableViewCell {
@package
  UIEdgeInsets insets_;
  id<RAREiPlatformIcon> icon_;
  RARERenderableDataItem_IconPosition iconPosition;
  RARERenderableDataItem_VerticalAlign verticalAlighment;
  float rotation_;
  int iconGap_;
@public
  int rowIndex;
  id rowItem;
  RAREUITableContentView* tableContent;
  UIView* listContent;
  
  __weak RAREListView* listView;
  __weak id<RAREiTreeItem> treeItem;
}
-(void) setEditingImage:(UIImage*) img;
-(CGSize) preferredSize;
-(void) setInsetsWithTop: (int) top right: (int)right bottom: (int) bottom left: (int) left;
-(void) setRotation:(int) rotation;
-(void) setIconGap: (int) gap;
-(void) setIconPosition: (RARERenderableDataItem_IconPosition) position ;
-(void) setTextVerticalAlignment: (RARERenderableDataItem_VerticalAlign) alignment;
-(void) setTextHorizontalAlignment: (RARERenderableDataItem_HorizontalAlign) alignment;
-(void) setIcon: (id<RAREiPlatformIcon>) icon;
-(BOOL) isPressed;
-(void) setListContent: (UIView*) view;
-(RAREUITableContentView*) getTableContentHolder;
-(void) sparPrepareForReuse;
-(void) showRowEditingView: (UIView*) view animate: (BOOL) animate centerVertically: (BOOL) center;
-(void) hideRowEditingView: (BOOL) animate;
-(BOOL) isRowEditorOrChild:(UIView*) view;
-(int) getPressedContentViewIndex;
-(UIView*) getPressedContentViewAtIndex: (int) index;

@end

