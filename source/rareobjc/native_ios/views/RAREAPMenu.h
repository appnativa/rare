//
//  RAREAPMenu.h
//  RareIOS
//
//  Created by Don DeCoteau on 5/18/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREAPMenuItem.h"

@interface RAREAPMenu : RAREAPMenuItem

-(void)removeAllItems;
-(void) removeItem: (RAREAPMenuItem*)item;
-(void) update;
-(BOOL) hasParentMenu;
-(void) addItem: (RAREAPMenuItem*) subItem;
-(void) insertItem: (RAREAPMenuItem*) subItem atIndex: (NSInteger) index;
-(void) setSubmenu: (RAREAPMenu*) subMenu forItem: (RAREAPMenuItem*) item;
+(void) setMenuBarVisible: (BOOL) visible;
+(BOOL) menuBarVisible;

@end
