//
//  RAREOverlayView.m
//  RareIOS
//
//  Created by Don DeCoteau on 6/17/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREOverlayView.h"
#import "com/appnativa/rare/ui/UIRectangle.h"
#import "com/appnativa/rare/ui/Component.h"
#import "com/appnativa/rare/ui/OverlayContainer.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "APView+Component.h"
#import "com/appnativa/rare/platform/apple/ui/view/ParentView.h"

@implementation RAREOverlayView
+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
-(id)initWithFrame:(CGRect)frame {
  self=[super initWithFrame:frame];
  if(self) {
    self.backgroundColor=[UIColor clearColor];
  }
  return self;
}

-(BOOL)isOpaque {
  return NO;
}
//
//-(UIView *)hitTest:(CGPoint)point withEvent:(UIEvent *)event {
//  if(!actAsGlass_) {
//    UIView* v=[super hitTest:point withEvent:event];
//    return v==self ? nil : v;
//  }
//  if(!wantsFirstInteraction_) {
//    return nil;
//  }
//  return overlayHadInteraction_ ? nil : self;
//}
//
-(void) setWantsFirstInteraction: (BOOL) wants {
  wantsFirstInteraction_=wants;
}
-(void) setActAsGlass: (BOOL) glass {
  actAsGlass_=glass;
}
-(void)layoutSubviews {
  if(!actAsGlass_) {
    RAREComponent* c=(RAREComponent*)[self.sparView getComponent];
    if([c isKindOfClass:[RAREOverlayContainer class]]) {
      [(RAREOverlayContainer*)c updateManagedOverlays];
    }
  }
  
}
-(void)drawRect:(CGRect)rect {
  if(actAsGlass_ || !(self.tag & MASK_PAINT_HANDLER)) {
    [super drawRect:rect];
  }
  else {
    CGContextRef ctx = UIGraphicsGetCurrentContext();
    
    RAREView* view=self.superview.sparView;
    RAREUIRectangle* r=[self sparBounds];
    RAREAppleGraphics* g=[[RAREAppleGraphics alloc]initWithId:(__bridge id)(ctx) withRAREView:view];
    [view paintBackgroundWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:r];
    [g dispose];
  }
  
}
-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
  if(!overlayHadInteraction_) {
    RAREParentView* view=(RAREParentView*)self.superview.sparView;
    [view hadInteraction];
    overlayHadInteraction_=YES;
  }
  
}

@end
