//
// Created by Don DeCoteau on 9/29/13.
// Copyright (c) 2013 SparseWare. All rights reserved.
//
// To change the template use AppCode | Preferences | File Templates.
//


#import <Foundation/Foundation.h>


@interface RAREDisplayLinkManager : NSObject {
  NSMutableArray* views;
  CADisplayLink* link;
}
-(void) addView:(id) view;
-(void) removeView:(id) view;
@end