//
//  RAREAPMenuItem.h
//  RareIOS
//
//  Created by Don DeCoteau on 5/18/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <UIKit/UIKit.h>
@class  RAREAPMenu;
@protocol RAREiActionListener;
@interface RAREAPMenuItem : UIMenuItem {
  NSObject* userData;
  RAREAPMenu* parent;
  id<RAREiActionListener> actionListener;
}
-(id) initWithTitle: (NSString*) title keyEquivalent: (NSString*) key;
-(void) setAPView: (NSObject*) view;
-(void) setAPFont: (NSObject*) font;
-(NSObject*) representedObject;
-(void) setRepresentedObject: (NSObject*) object;
-(void) setEnabled: (BOOL) enabled;
-(NSObject*) getParentRepresentedObject;
-(void) setAttributedTitle:(NSAttributedString *) title;
-(void) setVisible: (BOOL) visible;
-(void) setAPImage: (NSObject*) image;
-(void) actionPerformed;
-(void) setActionListener: (id<RAREiActionListener>) listener;
- (void)setSelected:(BOOL)selected;
+(RAREAPMenuItem*) separatorItem;
@end
