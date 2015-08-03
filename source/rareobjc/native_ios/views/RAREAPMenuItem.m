//
//  RAREAPMenuItem.m
//  RareIOS
//
//  Created by Don DeCoteau on 5/18/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREAPMenuItem.h"
#import "com/appnativa/rare/ui/event/iActionListener.h"
@implementation RAREAPMenuItem
-(id) initWithTitle: (NSString*) title keyEquivalent: (NSString*) key {
  return [super initWithTitle:title action:@selector(actionPerformed)];
}
-(void) setAPView: (NSObject*) view {
}
-(void) setFont: (NSObject*) font{
}
-(void) setAPFont: (NSObject*) font {
  
}
-(NSObject*) representedObject {
  return userData;
}
-(void) setRepresentedObject: (NSObject*) object {
  userData=object;
}
-(void) setEnabled: (BOOL) enabled {
  
}

-(NSObject*) getParentRepresentedObject {
  return nil;
}

-(void) setAttributedTitle:(NSAttributedString *) title {
  
}
-(void) setTitle:(NSString *) title {
  
}
-(void) setVisible: (BOOL) visible {
  
}

-(void) actionPerformed {
  if(actionListener) {
    [actionListener actionPerformedWithRAREActionEvent:nil];
  }
}
-(void) setAPImage: (NSObject*) image {
  
}

-(void) setActionListener: (id<RAREiActionListener>) listener {
  actionListener=listener;
}

+(RAREAPMenuItem*) separatorItem {
  return [[RAREAPMenuItem alloc]initWithTitle:@"" keyEquivalent:@""];
}
- (void)setSelected:(BOOL)selected   {
}

@end
