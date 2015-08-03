//
//  RAREAPTextView.h
//  RareOSX
//
//  Created by Don DeCoteau on 3/25/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RAREAPTextView : UITextView <UITextViewDelegate> {
  BOOL autoShowKeybaord;
  BOOL showKeyBoardCalled;
}
@property (nonatomic) NSInteger maxCharacters;
-(void) setShowKeyBoard: (BOOL) show;
-(void) setAutoShowKeyboard: (BOOL) show;

@end
