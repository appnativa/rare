//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/UIPopupMenu.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREUIPopupMenu_H_
#define _RAREUIPopupMenu_H_

@class RARESPOTMenuBar;
@class SPOTSet;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/UIMenu.h"

@interface RAREUIPopupMenu : RAREUIMenu {
}

- (id)init;
- (id)initWithRAREiWidget:(id<RAREiWidget>)context
      withRARESPOTMenuBar:(RARESPOTMenuBar *)cfg;
- (id)initWithRAREiWidget:(id<RAREiWidget>)context
              withSPOTSet:(SPOTSet *)menus;
- (id)initWithRAREiWidget:(id<RAREiWidget>)context
              withSPOTSet:(SPOTSet *)menus
              withBoolean:(BOOL)addTextItems;
@end

typedef RAREUIPopupMenu ComAppnativaRareUiUIPopupMenu;

#endif // _RAREUIPopupMenu_H_
