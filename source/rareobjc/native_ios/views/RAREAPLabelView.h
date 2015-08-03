//
//  RAREAPLabelView.h
//  RareOSX
//
//  Created by Don DeCoteau on 3/28/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "com/appnativa/rare/ui/RenderableDataItem.h"
@class RAREUIInsets;

@interface RAREAPLabelView : UILabel {
  BOOL pressed_;
  id<RAREiPlatformIcon> icon_;
  RARERenderableDataItem_IconPosition iconPosition;
  RARERenderableDataItem_VerticalAlign verticalAlighment;
  UIEdgeInsets insets_;
  int iconGap;
  CGRect titleRect_;
  BOOL dirty_;
}
-(RARERenderableDataItem_IconPosition) getIconPosition;
-(void) setInsetsWithTop: (int) top right: (int)right bottom: (int) bottom left: (int) left;
-(void) getInsets:(RAREUIInsets*) insets;
-(void) setIconGap: (int) gap;
-(void) setIconPosition: (RARERenderableDataItem_IconPosition) position ;
-(void) setTextVerticalAlignment: (RARERenderableDataItem_VerticalAlign) alignment;
-(void) setTextHorizontalAlignment: (RARERenderableDataItem_HorizontalAlign) alignment;
-(void) setIcon: (id<RAREiPlatformIcon>) icon;
-(BOOL) isPressed;
-(void) setPressed: (BOOL) pressed;
-(CGFloat) getPreferredHeight: (CGFloat) width;
+(CGRect)imageRectForContentRect:(CGRect)contentRect titleRect: (CGRect) titleRect titleSize: (CGSize) titleSize imageSize: (CGSize) imageSize iconPosition: (RARERenderableDataItem_IconPosition ) ip iconGap : (int) iconGap textAlignment:(NSTextAlignment) alignment;
+ (CGRect)titleRectForContentRect:(CGRect)contentRect titleSize: (CGSize) tsize imageSize: (CGSize) imageSize iconPosition: (RARERenderableDataItem_IconPosition ) ip iconGap : (int) iconGap valign: (RARERenderableDataItem_VerticalAlign) verticalAlighment;
@end
