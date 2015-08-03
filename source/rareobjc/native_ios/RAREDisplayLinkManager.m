//
// Created by Don DeCoteau on 9/29/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import "RAREDisplayLinkManager.h"


@implementation RAREDisplayLinkManager {

}
- (void)addView:(id)view {
  if(!views) {
    views=[NSMutableArray arrayWithCapacity:2];
  }
  if([views indexOfObject:view]==NSNotFound) {
    [views addObject:view];
    if(!link) {
      link = [CADisplayLink displayLinkWithTarget:self selector:@selector(onDisplayLink:)];
      [link setFrameInterval:1];
      if([NSThread isMainThread]) {
        [link addToRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
      }
      else {
        dispatch_async(dispatch_get_main_queue(), ^{
          [link addToRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
        });//end block
      }
    }
  }
}
- (void)removeView:(id)view {
  if(!views) return;
  [views removeObject:view];
  if(views.count==0 && link) {
    [link invalidate];
    link=nil;
  }
}
-(void) onDisplayLink: (id) sender {
  if(!views) return;
  [views enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
#ifdef TARGET_OS_IPHONE
    [((UIView *) obj) setNeedsDisplay];
#else
    [((NSView *) obj) setNeedsDisplay: YES];
#endif

  }];
}
@end