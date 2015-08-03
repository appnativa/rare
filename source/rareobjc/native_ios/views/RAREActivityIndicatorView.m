//
//  RAREActivityIndicatorView.m
//  RareTOUCH
//
//  Created by Don DeCoteau on 5/30/14.
//  Copyright (c) 2014 SparseWare. All rights reserved.
//

#import "RAREActivityIndicatorView.h"
#import "APView+Component.h"

@implementation RAREActivityIndicatorView {
  int graphicSize_;
}

+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
      self.autoAnimate=YES;
      self.activityIndicatorViewStyle=UIActivityIndicatorViewStyleGray;
      self.backgroundColor=[UIColor clearColor];
      self.hidesWhenStopped=NO;
    }
    return self;
}

-(CGSize)sizeThatFits:(CGSize)size {
  if(graphicSize_>0) {
    size=CGSizeMake(graphicSize_, graphicSize_);
  }
  else {
    size=[super sizeThatFits:size];
  }
  return size;
}

-(void) setGraphicSize: (int) size {
  graphicSize_=size;
  if(size>32) {
    self.activityIndicatorViewStyle=UIActivityIndicatorViewStyleWhiteLarge;
  }
  else {
    self.activityIndicatorViewStyle=UIActivityIndicatorViewStyleGray;
  }
}

-(void)didMoveToWindow {
  if(self.autoAnimate) {
    if(self.window==nil) {
      if(self.isAnimating) {
        [self stopAnimating];
      }
    }
    else {
      if(!self.isAnimating) {
        [self startAnimating];
      }
    }
  }
  
}
@end
