//
//  RAREAPImageView.m
//  RareOSX
//
//  Created by Don DeCoteau on 5/1/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <com/appnativa/rare/ui/UIStroke.h>
#import <com/appnativa/rare/ui/iPlatformShape.h>
#import <com/appnativa/rare/ui/iPlatformPath.h>
#import <com/appnativa/rare/ui/UIImage.h>
#import <com/appnativa/rare/ui/UIColor.h>
#import <com/appnativa/rare/platform/apple/ui/view/View.h>
#import "RAREAPImageView.h"
#import "RARECALayer.h"
#import "AppleHelper.h"
#import "RAREImageWrapper.h"
#import "APView+Component.h"

@implementation RAREAPImageView {
  UIColor* selectionColor_;
  CAShapeLayer* selection_;
  RAREUIStroke * selectionStroke_;
  int rotation_;
}

+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
- (id)init {
  self=[super init];
  return self;
}
- (void)setSelectionShape:(id <RAREiPlatformShape>)shape {
  if(shape && !selection_) {
    selection_=[CAShapeLayer layer];
    selection_.bounds=self.bounds;
    selection_.fillColor=[UIColor clearColor].CGColor;
    if(selectionColor_) {
      selection_.strokeColor=selectionColor_.CGColor;
    }
    if(selectionStroke_) {
      [RARECALayer setStroke:selectionStroke_ onLayer:selection_];
    }
    [self.layer addSublayer:selection_];
  }
  if(selection_) {
    if(shape) {
      UIBezierPath* bp;
      id <RAREiPlatformPath> path = [shape getBezierPath];
      if(path) {
        bp=((UIBezierPath *)[path getPath]);
      }
      else {
        CGRect rect= [[shape getRectangle] toRect];
        bp=[UIBezierPath bezierPathWithRect:rect];
      }
      selection_.path=bp.CGPath;
    }
    selection_.hidden=shape==nil;
  }
}

- (void)setSelectionStroke:(RAREUIStroke *)stroke {
  selectionStroke_=stroke;
  if(stroke && selection_) {
    [RARECALayer setStroke:stroke onLayer:selection_];
  }
}

- (void)setRAREUIImage:(RAREUIImage *)image {
  UIImage* img=image ? [((RAREImageWrapper *) image.getProxy) getImage] : nil;
  if(img!=self.image) {
    self.image=img;
    [self setNeedsDisplay];
  }
}

- (void) refreshView {
  [self.layer setNeedsDisplay];
  [self setNeedsDisplay];
}
- (void)setSelectionColor:(RAREUIColor *)color {
  if(selection_ && color) {
    selection_.strokeColor=((UIColor*)[color getAPColor]).CGColor;
  }
  if(color) {
    selectionColor_=(UIColor*)[color getAPColor];
    if(selection_) {
      selection_.strokeColor=selectionColor_.CGColor;
    }
  }
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
