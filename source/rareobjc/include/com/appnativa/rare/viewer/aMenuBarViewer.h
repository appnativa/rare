//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/aMenuBarViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREaMenuBarViewer_H_
#define _RAREaMenuBarViewer_H_

@class IOSObjectArray;
@class RARERenderableDataItem;
@class RARESPOTMenuBar;
@class RARESPOTMenuItem;
@class RARESPOTViewer;
@class RARESPOTWidget;
@class RAREUIAction;
@class RAREUIMenu;
@class RAREUIMenuItem;
@class SPOTSet;
@protocol JavaUtilList;
@protocol RAREiContainer;
@protocol RAREiMenuBarComponent;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformIcon;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/iPlatformMenuBar.h"
#include "com/appnativa/rare/viewer/aContainer.h"

@interface RAREaMenuBarViewer : RAREaContainer < RAREiPlatformMenuBar > {
 @public
  RAREUIMenu *debugMenu_;
  id<RAREiMenuBarComponent> menuBar_;
  IOSObjectArray *menuItems_;
  RAREUIMenuItem *selectedItem_;
}

+ (NSString *)MENUBAR_NAME;
- (id)init;
- (id)initWithRAREiContainer:(id<RAREiContainer>)parent;
- (void)clearContents;
- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg;
- (void)configureMenusWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                              withRARESPOTWidget:(RARESPOTWidget *)cfg
                                     withBoolean:(BOOL)textMenus;
- (id<RAREiMenuBarComponent>)createWithRARESPOTMenuBar:(RARESPOTMenuBar *)cfg;
- (RAREUIMenuItem *)createCheckBoxMenuItemWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (RAREUIMenuItem *)createCheckboxMenuItemWithNSString:(NSString *)text
                                 withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                                withId:(id)data;
- (RAREUIMenu *)createMenuWithNSString:(NSString *)text
                 withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                withId:(id)data;
- (RAREUIMenuItem *)createMenuItemWithRARESPOTMenuItem:(RARESPOTMenuItem *)item;
- (RAREUIMenuItem *)createMenuItemWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (RAREUIMenuItem *)createMenuItemWithRAREUIAction:(RAREUIAction *)a
                                       withBoolean:(BOOL)checkbox;
- (RAREUIMenuItem *)createMenuItemWithNSString:(NSString *)text
                         withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                        withId:(id)data;
- (RAREUIMenuItem *)createMenuItemWithNSString:(NSString *)text
                         withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                        withId:(id)data
                                  withNSString:(NSString *)code;
- (id<JavaUtilList>)createMenuItemsWithSPOTSet:(SPOTSet *)set;
- (void)hidePopupContainer;
- (void)removeAll;
- (void)toggleVisibility;
- (void)setItemEnabledWithNSString:(NSString *)name
                       withBoolean:(BOOL)enabled;
- (void)setItemSelectedWithNSString:(NSString *)name
                        withBoolean:(BOOL)selected;
- (void)setItemVisibleWithNSString:(NSString *)name
                       withBoolean:(BOOL)visible;
- (void)setSelectedItemWithRAREUIMenuItem:(RAREUIMenuItem *)mi;
- (void)setVisibleWithBoolean:(BOOL)visible;
- (RAREUIMenu *)getMenuWithNSString:(NSString *)name;
- (id<RAREiMenuBarComponent>)getMenuBarComponent;
- (id)getSelection;
- (RAREUIMenuItem *)getSeparatorItem;
- (BOOL)hasSelection;
- (BOOL)isItemEnabledWithNSString:(NSString *)name;
- (BOOL)isItemSelectedWithNSString:(NSString *)name;
- (BOOL)isItemVisibleWithNSString:(NSString *)name;
- (void)clearConfigurationWithBoolean:(BOOL)dispose;
- (void)configureExWithRARESPOTMenuBar:(RARESPOTMenuBar *)cfg;
- (id<RAREiMenuBarComponent>)createMenuBarAndComponentsWithRARESPOTMenuBar:(RARESPOTMenuBar *)cfg;
- (void)addDebugOptions;
- (void)copyAllFieldsTo:(RAREaMenuBarViewer *)other;
@end

J2OBJC_FIELD_SETTER(RAREaMenuBarViewer, debugMenu_, RAREUIMenu *)
J2OBJC_FIELD_SETTER(RAREaMenuBarViewer, menuBar_, id<RAREiMenuBarComponent>)
J2OBJC_FIELD_SETTER(RAREaMenuBarViewer, menuItems_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(RAREaMenuBarViewer, selectedItem_, RAREUIMenuItem *)

typedef RAREaMenuBarViewer ComAppnativaRareViewerAMenuBarViewer;

#endif // _RAREaMenuBarViewer_H_
