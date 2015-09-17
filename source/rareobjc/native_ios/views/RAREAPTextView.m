//
//  RAREAPTextView.m
//  RareOSX
//
//  Created by Don DeCoteau on 3/25/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREAPTextView.h"
#import "APView+Component.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "com/appnativa/rare/ui/event/MouseEventEx.h"
#import "com/appnativa/rare/ui/listener/iMouseListener.h"
#import "com/appnativa/rare/ui/listener/iMouseMotionListener.h"
#import "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#import "com/appnativa/rare/platform/apple/ui/view/TextAreaView.h"
#import "com/appnativa/rare/ui/listener/iTextChangeListener.h"

@implementation RAREAPTextView

+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
- (id)initWithFrame:(CGRect)frame
{
  if(self=[super initWithFrame:frame]) {
    autoShowKeybaord=YES;
    self.delegate=self;
  }
  self.userInteractionEnabled=YES;
  
  return self;
}
-(void)sparDispose {
  self.delegate=nil;
  [super sparDispose];
}

-(void) setShowKeyBoard: (BOOL) show {
  if(show) {
    showKeyBoardCalled= YES;
    [self becomeFirstResponder];
  }
  else {
    [self resignFirstResponder];
  }
}
  
-(void) setAutoShowKeyboard: (BOOL) show {
  autoShowKeybaord=show;
}
-(BOOL)textViewShouldBeginEditing:(UITextView *)textView {
  if(autoShowKeybaord || showKeyBoardCalled) {
    return YES;
  }
  return NO;
}

-(void)textViewDidEndEditing:(UITextView *)textView {
  showKeyBoardCalled= FALSE;
}

-(BOOL)textViewShouldEndEditing:(UITextView *)textView {
  RARETextAreaView* tv=(RARETextAreaView*)self.sparView;
  if(tv->changeListener_) {
    if(![tv->changeListener_ shouldStopEditingWithId:tv]) {
      return NO;
    }
  }
  if(autoShowKeybaord || showKeyBoardCalled) {
    return YES;
  }
  return NO;
}

-(BOOL)becomeFirstResponder {
  BOOL ok=[super becomeFirstResponder];
  if(ok) {
    [self gainedFocusEx];
  }
  return ok;
}
-(BOOL)resignFirstResponder {
  BOOL ok=[super resignFirstResponder];
  if(ok) {
    [self lostFocusEx];
  }
  return ok;
}

- (void)drawRect:(CGRect)dirtyRect
{
  if(self.tag & MASK_PAINT_HANDLER) {
    
    RAREView* view=self.sparView;
    RAREUIRectangle* rect=[self sparBounds];
    
    RAREAppleGraphics* g=[[RAREAppleGraphics alloc]initWithId:(__bridge id)(UIGraphicsGetCurrentContext()) withRAREView:view];
    [view paintBackgroundWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
//    [view paintOverlayWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
    [g dispose];
  }
  else {
    [super drawRect:dirtyRect];
  }
}
-(void)textViewDidChange:(UITextView *)textView {
  RARETextAreaView* tv=(RARETextAreaView*)self.sparView;
  if(tv->changeListener_) {
    [tv->changeListener_ textChangedWithId:tv];
  }
}
-(BOOL)textView:(UITextView *)textView shouldChangeTextInRange:(NSRange)range replacementText:(NSString *)text {
  RARETextAreaView* tv=(RARETextAreaView*)self.sparView;
  if(tv->changeListener_) {
    if(![tv->changeListener_ textChangingWithId:tv withInt:(int)range.location withInt:(int)(range.location+range.location) withJavaLangCharSequence: text]) {
      return NO;
    }
  }
  NSInteger max=self.maxCharacters;
  if(max<1) {
    return YES;
  }
  NSUInteger newLength = [textView.text length] + [text length] - range.length;
  if(newLength <= max)
  {
    return YES;
  } else {
    NSUInteger emptySpace = max - (textView.text.length - range.length);
    if(emptySpace>0) {
      textView.text = [[[textView.text substringToIndex:range.location]
                         stringByAppendingString:[text substringToIndex:emptySpace]]
                        stringByAppendingString:[textView.text substringFromIndex:(range.location + range.length)]];
    }
    return NO;
  }
}
-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
  if(self.tag & MASK_MOUSE_HANDLER) {
    RAREView* view=self.sparView;
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseListener_ mousePressedWithRAREMouseEvent:me];
    if(me.isConsumed) {
      return;
    }
  }
  
  [super touchesBegan:touches withEvent:event];
  
}

-(void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
  if(self.tag & MASK_MOUSE_HANDLER) {
    RAREView* view=self.sparView;
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseListener_ mouseReleasedWithRAREMouseEvent:me];
    if(me.isConsumed) {
      return;
    }
  }
  [super touchesEnded:touches withEvent:event];
  
  
}
-(void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event {
  if(self.tag & MASK_MOUSE_MOTION_HANDLER) {
    RAREView* view=self.sparView;
    RAREMouseEvent* me=[[RAREMouseEventEx alloc] initWithId:view withId:event];
    [view->mouseMotionListener_ mouseDraggedWithRAREMouseEvent:me];
    if(me.isConsumed) {
      return;
    }
  }
  [super touchesMoved:touches withEvent:event];
}

-(void)setHidden:(BOOL)hidden {
  BOOL changed=self.hidden!=hidden;
  [super setHidden:hidden];
  if(changed && self.window) {
    RAREView *view = self.sparView;
    if (view && view->viewListener_ ) {
      [view visibilityChangedWithBoolean:!hidden];
    }
  }
}
- (void)willMoveToWindow:(UIWindow *)newWindow {
  [super willMoveToWindow:newWindow];
  RAREView* view=self.sparView;
  if(view && view->viewListener_ && self.window!=newWindow) {
    [view visibilityChangedWithBoolean:newWindow!=nil];
  }
}
@end
