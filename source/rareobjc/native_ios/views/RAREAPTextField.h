//
//  RAREAPTextField.h
//  RareIOS
//
//  Created by Don DeCoteau on 5/17/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "com/appnativa/rare/ui/RenderableDataItem.h"

@class RAREUIInsets;
@interface RAREAPTextField : UITextField{
  UIEdgeInsets insets_;
  BOOL autoShowKeybaord_;
  BOOL showKeyBoardCalled_;
  BOOL editable_;
  BOOL showMenu_;
}
@property (nonatomic) NSInteger maxCharacters;
@property (nonatomic,strong) UIFont* emptyFieldFont;
@property (nonatomic,strong) UIColor* emptyFieldColor;
-(void) setShowKeyBoard: (BOOL) show;
-(void) setAutoShowKeyboard: (BOOL) show;
-(void) setEditable: (BOOL) editable;
-(BOOL) isEditable;
-(void) setInsetsWithTop: (int) top right: (int)right bottom: (int) bottom left: (int) left;
-(void) getInsets:(RAREUIInsets*) insets;
-(void) setTextHorizontalAlignment: (RARERenderableDataItem_HorizontalAlign) alignment;
-(void) setShowMenu:(BOOL) show;

@end
