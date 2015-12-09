//
//  RAREAPTextField.m
//  RareIOS
//
//  Created by Don DeCoteau on 5/17/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREAPTextField.h"
#import "RAREAPApplication.h"
#import "APView+Component.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "com/appnativa/rare/ui/UIInsets.h"
#import "com/appnativa/rare/ui/event/MouseEventEx.h"
#import "com/appnativa/rare/ui/listener/iMouseListener.h"
#import "com/appnativa/rare/ui/listener/iMouseMotionListener.h"
#import "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#import "com/appnativa/rare/platform/apple/ui/view/TextFieldView.h"
#import "com/appnativa/rare/ui/listener/iTextChangeListener.h"
#import "RAREUIViewController.h"
#import "AppleHelper.h"
@interface TextFieldDelegate : NSObject <UITextFieldDelegate>

@end
static TextFieldDelegate* textFieldDelegate;

@implementation RAREAPTextField {
  NSMutableDictionary* attributes;
  NSMutableParagraphStyle *paragraphStyle;
}

+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
- (id)initWithFrame:(CGRect)frame{
  if (self = [super initWithFrame:frame]) {
    [self setBorderStyle:UITextBorderStyleRoundedRect];
    self.contentVerticalAlignment = UIControlContentVerticalAlignmentCenter;
    insets_ = UIEdgeInsetsMake(2, 4, 2, 2);
    autoShowKeybaord_ = YES;
    editable_ = YES;
    if(!textFieldDelegate) {
      textFieldDelegate=[TextFieldDelegate new];
    }
    self.delegate=textFieldDelegate;
    self.userInteractionEnabled = YES;
    showMenu_=YES;
  }
  
  return self;
}
-(BOOL)canPerformAction:(SEL)action withSender:(id)sender {
  if(!showMenu_) {
    return NO;
  }
  return [super canPerformAction:action withSender:sender];
}

- (void)setEditable:(BOOL)editable {
  editable_ = editable;
#if !TARGET_IPHONE_SIMULATOR
  if (!editable) {
    [self resignFirstResponder];
  }
#endif
}
-(BOOL)canBecomeFirstResponder {
  if([RAREAPApplication isFirstTextFieldCheck]) {
    return NO;
  }
  return editable_ && self.enabled;
}
- (BOOL)isEditable {
  return editable_;
}
-(void) setShowMenu:(BOOL) show {
  showMenu_=show;
}
- (void)setTextHorizontalAlignment:(RARERenderableDataItem_HorizontalAlign)alignment {
  BOOL rtl = [AppleHelper isLTRText];
  RARERenderableDataItem_HorizontalAlign a = alignment;
  if (a == RARERenderableDataItem_HorizontalAlign_TRAILING) {
    a = rtl ? RARERenderableDataItem_HorizontalAlign_LEFT : RARERenderableDataItem_HorizontalAlign_RIGHT;
  }
  else if (a == RARERenderableDataItem_HorizontalAlign_LEADING) {
    a = rtl ? RARERenderableDataItem_HorizontalAlign_RIGHT : RARERenderableDataItem_HorizontalAlign_LEFT;
    
  }
  switch (a) {
    case RARERenderableDataItem_HorizontalAlign_AUTO:
      if (floor(NSFoundationVersionNumber) <= NSFoundationVersionNumber_iOS_6_1){
        self.textAlignment = NSTextAlignmentLeft;
      }
      else {
        self.textAlignment = NSTextAlignmentNatural;
      }
      break;
    case RARERenderableDataItem_HorizontalAlign_RIGHT:
      self.textAlignment = NSTextAlignmentRight;
      break;
    case RARERenderableDataItem_HorizontalAlign_CENTER:
      self.textAlignment = NSTextAlignmentCenter;
      break;
    default:
      self.textAlignment = NSTextAlignmentLeft;
      break;
  }
}

- (void)setInsetsWithTop:(int)top right:(int)right bottom:(int)bottom left:(int)left {
  insets_ = UIEdgeInsetsMake(top, left, bottom, right);
}

-(void) getInsets:(RAREUIInsets*) insets {
  insets->top_ =insets_.top;
  insets->right_=insets_.right;
  insets->bottom_=insets_.bottom;
  insets->left_=insets_.left;
}

- (void)setShowKeyBoard:(BOOL)show {
  if (show) {
    showKeyBoardCalled_ = YES;
    [self becomeFirstResponder];
  }
  else {
    [self resignFirstResponder];
  }
}

- (void)setAutoShowKeyboard:(BOOL)show {
  autoShowKeybaord_ = show;
}

- (BOOL)textFieldShouldBeginEditingEx:(UITextField *)textField {
  if ((autoShowKeybaord_ || showKeyBoardCalled_) && editable_ && textField.enabled) {
    return YES;
  }
  return NO;
  
}

- (BOOL)textFieldShouldEndEditingEx:(UITextField *)textField {
  RARETextFieldView *tv = (RARETextFieldView *) self.sparView;
  if (tv->changeListener_) {
    if (![tv->changeListener_ shouldStopEditingWithId:tv]) {
      return NO;
    }
  }
  if (autoShowKeybaord_ || showKeyBoardCalled_) {
    return YES;
  }
  return NO;
  
}

- (void)textFieldDidEndEditingEx:(UITextField *)textField {
  showKeyBoardCalled_ = FALSE;
}

- (BOOL)textFieldShouldReturnEx:(UITextField *)textField {
  RARETextFieldView *tv = (RARETextFieldView *) self.sparView;
  if(tv->actionListener_) {
    [self resignFirstResponder];
    [tv actionPerformed];
  }
  else if(self.returnKeyType==UIReturnKeyNext) {
    if(![tv handleFocusWithBoolean:YES]) {
      [self resignFirstResponder];
    }
  }
  else {
    [self resignFirstResponder];
  }
  
  return YES;
}

- (void)textFieldDidChange:(id) notification{
  RARETextFieldView *tv = (RARETextFieldView *) self.sparView;
  if (tv->changeListener_ ) {
    [tv->changeListener_  textChangedWithId:tv];
  }
}

- (BOOL)textFieldEx:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)text {
  RARETextFieldView *tv = (RARETextFieldView *) textField.sparView;
  if (tv->changeListener_ ) {
    if (![tv->changeListener_  textChangingWithId:tv withInt:(int)range.location withInt:(int)range.location + (int)range.location withJavaLangCharSequence:text]) {
      return NO;
    }
  }
  NSInteger max = self.maxCharacters;
  if (max < 1) {
    return YES;
  }
  NSUInteger newLength = [textField.text length] + [text length] - range.length;
  if (newLength <= max) {
    return YES;
  } else {
    NSUInteger emptySpace = max - (textField.text.length - range.length);
    if (emptySpace > 0) {
      textField.text = [[[textField.text substringToIndex:range.location]
                         stringByAppendingString:[text substringToIndex:emptySpace]]
                        stringByAppendingString:[textField.text substringFromIndex:(range.location + range.length)]];
    }
    return NO;
  }
  
}

- (CGRect)editingRectForBounds:(CGRect)bounds {
  return UIEdgeInsetsInsetRect(bounds, insets_);
}

- (CGRect)placeholderRectForBounds:(CGRect)bounds {
  return UIEdgeInsetsInsetRect(bounds, insets_);
}

- (CGRect)textRectForBounds:(CGRect)bounds {
  return UIEdgeInsetsInsetRect(bounds, insets_);
}

- (void)drawRect:(CGRect)dirtyRect {
  CGContextRef ctx = UIGraphicsGetCurrentContext();
  if (self.tag & MASK_PAINT_HANDLER) {
    RAREView *view = self.sparView;
    RAREUIRectangle *rect = [self sparBounds];
    RAREAppleGraphics *g = [[RAREAppleGraphics alloc] initWithId:(__bridge id) ctx withRAREView:view];
    [view paintBackgroundWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
    
    [super drawRect:dirtyRect];
    
    [view paintOverlayWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
    [g dispose];
  }
  else {
    [super drawRect:dirtyRect];
  }
}

- (void)drawPlaceholderInRect:(CGRect)rect {
  if (!self.emptyFieldColor) {
    self.emptyFieldColor=[self.textColor colorWithAlphaComponent:.5];
  }
  UIFont *f = self.font;
  if (self.emptyFieldFont) {
    f = self.emptyFieldFont;
  }
  if(!attributes) {
    attributes=[NSMutableDictionary dictionaryWithCapacity:2];
    paragraphStyle = [[NSMutableParagraphStyle alloc] init];
  }
  else {
    [attributes removeAllObjects];
  }
  paragraphStyle.lineBreakMode = NSLineBreakByTruncatingTail;
  [attributes setObject:paragraphStyle forKey:NSParagraphStyleAttributeName];
  [attributes setObject:f forKey:NSFontAttributeName];
  [attributes setObject:self.emptyFieldColor forKey:NSForegroundColorAttributeName];
  CGSize size = [self.placeholder sizeWithAttributes:attributes];
  rect.origin.y += (rect.size.height - size.height) / 2;
  rect.size.height = size.height;
  [self.placeholder drawInRect:rect withAttributes:attributes];
}

- (BOOL)becomeFirstResponder {
  BOOL ok = [super becomeFirstResponder];
  if (ok) {
    RARETextFieldView *tv = (RARETextFieldView *) self.sparView;
    if(tv->cursorPosition_>0 && tv->cursorPosition_<=self.text.length) {
      [tv setSelectionWithInt:tv->cursorPosition_ withInt:tv->cursorPosition_];
    }
    [self gainedFocusEx];
  }
  return ok;
}

- (BOOL)resignFirstResponder {
  RARETextFieldView *tv = (RARETextFieldView *) self.sparView;
  [tv saveCursorPosition];
  BOOL ok = [super resignFirstResponder];
  if (ok) {
    [self lostFocusEx];
  }
  return ok;
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
  if (self.tag & MASK_MOUSE_HANDLER) {
    RAREView *view = self.sparView;
    RAREMouseEvent *me = [[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseListener_ mousePressedWithRAREMouseEvent:me];
    if (me.isConsumed) {
      return;
    }
  }
  
  [super touchesBegan:touches withEvent:event];
  if(![self isFirstResponder] && [self canBecomeFirstResponder]) {
    [self becomeFirstResponder];
  }
}

- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
  if (self.tag & MASK_MOUSE_HANDLER) {
    RAREView *view = self.sparView;
    RAREMouseEvent *me = [[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseListener_ mouseReleasedWithRAREMouseEvent:me];
    if (me.isConsumed) {
      return;
    }
  }
  [super touchesEnded:touches withEvent:event];
  
  
}

- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event {
  if (self.tag & MASK_MOUSE_MOTION_HANDLER) {
    RAREView *view = self.sparView;
    RAREMouseEvent *me = [[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseMotionListener_ mouseDraggedWithRAREMouseEvent:me];
    if (me.isConsumed) {
      return;
    }
  }
  [super touchesMoved:touches withEvent:event];
}

- (void)willMoveToWindow:(UIWindow *)newWindow {
  [super willMoveToWindow:newWindow];
  RAREView* view=self.sparView;
  if(view && view->viewListener_ && self.window!=newWindow) {
    [view visibilityChangedWithBoolean:newWindow!=nil];
  }
}
@end
@implementation TextFieldDelegate

- (BOOL)textFieldShouldBeginEditing:(UITextField *)textField {
  return [(RAREAPTextField*) textField textFieldShouldEndEditingEx:textField];
}

- (BOOL)textFieldShouldEndEditing:(UITextField *)textField {
  return [(RAREAPTextField*) textField textFieldShouldBeginEditingEx:textField];
}

- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string {
  BOOL should=[(RAREAPTextField*) textField textFieldEx:textField shouldChangeCharactersInRange:range replacementString:string];
  if(should) {
    RARETextFieldView *tv = (RARETextFieldView *) textField.sparView;
    if(tv->changeListener_) {
      [tv notifyTextChanged];
    }
  }
  return should;
}

-(void)textFieldDidBeginEditing:(UITextField *)textField {
}
- (void)textFieldDidEndEditing:(UITextField *)textField {
  [(RAREAPTextField*) textField textFieldDidEndEditingEx:textField];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
  return [(RAREAPTextField*) textField textFieldShouldReturnEx:textField];
}


@end
