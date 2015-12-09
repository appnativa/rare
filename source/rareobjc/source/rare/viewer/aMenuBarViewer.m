//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/aMenuBarViewer.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/net/ActionLink.h"
#include "com/appnativa/rare/net/iURLConnection.h"
#include "com/appnativa/rare/spot/MenuBar.h"
#include "com/appnativa/rare/spot/MenuItem.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/MenuUtils.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIAction.h"
#include "com/appnativa/rare/ui/UIMenu.h"
#include "com/appnativa/rare/ui/UIMenuItem.h"
#include "com/appnativa/rare/ui/event/ScriptActionListener.h"
#include "com/appnativa/rare/ui/iMenuBarComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/util/DataParser.h"
#include "com/appnativa/rare/viewer/aMenuBarViewer.h"
#include "com/appnativa/rare/viewer/aViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSet.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "com/appnativa/util/FilterableList.h"
#include "java/lang/Exception.h"
#include "java/lang/RuntimeException.h"
#include "java/util/Collections.h"
#include "java/util/List.h"

@implementation RAREaMenuBarViewer

static NSString * RAREaMenuBarViewer_MENUBAR_NAME_ = @"MenuBar";

+ (NSString *)MENUBAR_NAME {
  return RAREaMenuBarViewer_MENUBAR_NAME_;
}

- (id)init {
  return [self initRAREaMenuBarViewerWithRAREiContainer:nil];
}

- (id)initRAREaMenuBarViewerWithRAREiContainer:(id<RAREiContainer>)parent {
  if (self = [super initWithRAREiContainer:parent]) {
    widgetType_ = [RAREiWidget_WidgetTypeEnum MenuBar];
  }
  return self;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [self initRAREaMenuBarViewerWithRAREiContainer:parent];
}

- (void)clearContents {
  [super clearContents];
  if (menuBar_ != nil) {
    [menuBar_ removeAll];
  }
}

- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg {
  [self configureExWithRARESPOTMenuBar:(RARESPOTMenuBar *) check_class_cast(vcfg, [RARESPOTMenuBar class])];
  [self fireConfigureEventWithRARESPOTWidget:vcfg withNSString:[RAREiConstants EVENT_CONFIGURE]];
}

- (void)configureMenusWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp
                              withRARESPOTWidget:(RARESPOTWidget *)cfg
                                     withBoolean:(BOOL)textMenus {
}

- (id<RAREiMenuBarComponent>)createWithRARESPOTMenuBar:(RARESPOTMenuBar *)cfg {
  SPOTSet *menus = [((RARESPOTMenuBar *) nil_chk(cfg)) getPopupMenu];
  NSString *s;
  if (menus == nil) {
    s = [((SPOTPrintableString *) nil_chk(cfg->dataURL_)) getValue];
    if ((s != nil) && ([s sequenceLength] > 0)) {
      id<iSPOTElement> item;
      RAREActionLink *link = [RAREActionLink getActionLinkWithRAREiWidget:self withSPOTPrintableString:cfg->dataURL_ withInt:0];
      @try {
        self->viewerActionLink_ = link;
        item = [RAREDataParser loadSPOTObjectWithRAREiWidget:self withRAREiURLConnection:[((RAREActionLink *) nil_chk(link)) getConnection] withISPOTElement:nil];
        if ([(id) item isKindOfClass:[RARESPOTMenuItem class]]) {
          menus = [((RARESPOTMenuItem *) check_class_cast(item, [RARESPOTMenuItem class])) getSubMenu];
        }
        else if ([(id) item isKindOfClass:[RARESPOTMenuBar class]]) {
          cfg = (RARESPOTMenuBar *) check_class_cast(item, [RARESPOTMenuBar class]);
          menus = [((RARESPOTMenuBar *) nil_chk(cfg)) getPopupMenu];
        }
      }
      @catch (JavaLangException *ex) {
        @throw [RAREDataParser invalidConfigurationExceptionWithRAREiPlatformAppContext:[self getAppContext] withJavaLangThrowable:ex withNSString:s];
      }
    }
  }
  menuBar_ = [self createMenuBarAndComponentsWithRARESPOTMenuBar:cfg];
  [super configureExWithRARESPOTViewer:cfg withBoolean:YES withBoolean:NO withBoolean:YES];
  s = [((RARESPOTMenuBar *) nil_chk(cfg)) spot_getAttributeWithNSString:[RAREiConstants ATTRIBUTE_ON_ACTION]];
  if ((s != nil) && ([s sequenceLength] > 0)) {
    [((id<RAREiMenuBarComponent>) nil_chk(menuBar_)) addActionListenerWithRAREiActionListener:[[RAREScriptActionListener alloc] initWithRAREiWidget:self withNSString:s]];
  }
  int len = (menus == nil) ? 0 : [menus getCount];
  if (len > 0) {
    menuItems_ = [RAREMenuUtils createMenusWithRAREiWidget:self withSPOTSet:menus];
    RAREUIMenuItem *mi;
    NSString *name;
    for (int i = 0; i < (int) [((IOSObjectArray *) nil_chk(menuItems_)) count]; i++) {
      mi = IOSObjectArray_Get(menuItems_, i);
      name = [((RAREUIMenuItem *) nil_chk(mi)) getName];
      [((id<RAREiMenuBarComponent>) nil_chk(menuBar_)) addWithRAREUIMenuItem:mi];
      if (name != nil) {
        (void) [self registerNamedItemWithNSString:name withId:mi];
      }
    }
  }
  if (![self isDesignMode]) {
    [self addDebugOptions];
  }
  return menuBar_;
}

- (RAREUIMenuItem *)createCheckBoxMenuItemWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  RAREUIMenuItem *m = [RAREaUIMenuItem createCheckboxMenuItemWithRARERenderableDataItem:item];
  [((RAREUIMenuItem *) nil_chk(m)) setContextWidgetWithRAREiWidget:self];
  return m;
}

- (RAREUIMenuItem *)createCheckboxMenuItemWithNSString:(NSString *)text
                                 withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                                withId:(id)data {
  RAREUIMenuItem *m = [RAREaUIMenuItem createCheckboxMenuItemWithJavaLangCharSequence:text withRAREiPlatformIcon:icon withId:data];
  [((RAREUIMenuItem *) nil_chk(m)) setContextWidgetWithRAREiWidget:self];
  return m;
}

- (RAREUIMenu *)createMenuWithNSString:(NSString *)text
                 withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                withId:(id)data {
  RAREUIMenu *m = [RAREMenuUtils createMenuWithNSString:text withRAREiPlatformIcon:icon withId:data];
  [((RAREUIMenu *) nil_chk(m)) setContextWidgetWithRAREiWidget:self];
  return m;
}

- (RAREUIMenuItem *)createMenuItemWithRARESPOTMenuItem:(RARESPOTMenuItem *)item {
  return [RAREMenuUtils createMenuItemWithRAREiWidget:self withRARESPOTMenuItem:item];
}

- (RAREUIMenuItem *)createMenuItemWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  RAREUIMenuItem *m = [[RAREUIMenuItem alloc] initWithRARERenderableDataItem:item];
  [m setContextWidgetWithRAREiWidget:self];
  return m;
}

- (RAREUIMenuItem *)createMenuItemWithRAREUIAction:(RAREUIAction *)a
                                       withBoolean:(BOOL)checkbox {
  return [[RAREUIMenuItem alloc] initWithRAREUIAction:a withBoolean:checkbox];
}

- (RAREUIMenuItem *)createMenuItemWithNSString:(NSString *)text
                         withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                        withId:(id)data {
  RAREUIMenuItem *m = [RAREaUIMenuItem createMenuItemWithJavaLangCharSequence:text withRAREiPlatformIcon:icon withId:data];
  [((RAREUIMenuItem *) nil_chk(m)) setContextWidgetWithRAREiWidget:self];
  return m;
}

- (RAREUIMenuItem *)createMenuItemWithNSString:(NSString *)text
                         withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                        withId:(id)data
                                  withNSString:(NSString *)code {
  RAREUIMenuItem *m = [RAREaUIMenuItem createMenuItemWithJavaLangCharSequence:text withRAREiPlatformIcon:icon withId:data];
  [((RAREUIMenuItem *) nil_chk(m)) setContextWidgetWithRAREiWidget:self];
  [m setActionScriptWithId:code];
  return m;
}

- (id<JavaUtilList>)createMenuItemsWithSPOTSet:(SPOTSet *)set {
  if (set == nil) {
    return [JavaUtilCollections emptyList];
  }
  IOSObjectArray *a = [RAREMenuUtils createMenuItemsWithRAREiWidget:self withSPOTSet:set];
  RAREUTFilterableList *l = [[RAREUTFilterableList alloc] initWithInt:(int) [((IOSObjectArray *) nil_chk(a)) count]];
  [l addAllWithNSObjectArray:a withInt:(int) [a count]];
  return l;
}

- (void)hidePopupContainer {
}

- (void)removeAll {
  [((id<RAREiMenuBarComponent>) nil_chk(menuBar_)) removeAll];
}

- (void)toggleVisibility {
  [self setVisibleWithBoolean:![self isVisible]];
}

- (void)setItemEnabledWithNSString:(NSString *)name
                       withBoolean:(BOOL)enabled {
  if (name == nil) {
    return;
  }
  RAREUIMenuItem *item = (RAREUIMenuItem *) check_class_cast([self getNamedItemWithNSString:name], [RAREUIMenuItem class]);
  if (item == nil) {
    return;
  }
  [((RAREUIMenuItem *) nil_chk(item)) setEnabledWithBoolean:enabled];
}

- (void)setItemSelectedWithNSString:(NSString *)name
                        withBoolean:(BOOL)selected {
  if (name == nil) {
    return;
  }
  RAREUIMenuItem *item = (RAREUIMenuItem *) check_class_cast([self getNamedItemWithNSString:name], [RAREUIMenuItem class]);
  if (item == nil) {
    return;
  }
  [((RAREUIMenuItem *) nil_chk(item)) setSelectedWithBoolean:selected];
}

- (void)setItemVisibleWithNSString:(NSString *)name
                       withBoolean:(BOOL)visible {
  if (name == nil) {
    return;
  }
  RAREUIMenuItem *item = (RAREUIMenuItem *) check_class_cast([self getNamedItemWithNSString:name], [RAREUIMenuItem class]);
  if (item == nil) {
    return;
  }
  [((RAREUIMenuItem *) nil_chk(item)) setVisibleWithBoolean:visible];
}

- (void)setSelectedItemWithRAREUIMenuItem:(RAREUIMenuItem *)mi {
  selectedItem_ = mi;
}

- (void)setVisibleWithBoolean:(BOOL)visible {
  if (menuBar_ != nil) {
    [menuBar_ setVisibleWithBoolean:visible];
  }
}

- (RAREUIMenu *)getMenuWithNSString:(NSString *)name {
  id o = [self getNamedItemWithNSString:name];
  if ([o isKindOfClass:[RAREUIMenu class]]) {
    return (RAREUIMenu *) check_class_cast(o, [RAREUIMenu class]);
  }
  return nil;
}

- (id<RAREiMenuBarComponent>)getMenuBarComponent {
  return menuBar_;
}

- (id)getSelection {
  return selectedItem_;
}

- (RAREUIMenuItem *)getSeparatorItem {
  return [RAREMenuUtils getSeparatorItem];
}

- (BOOL)hasSelection {
  return NO;
}

- (BOOL)isItemEnabledWithNSString:(NSString *)name {
  if (name == nil) {
    return NO;
  }
  RAREUIMenuItem *item = (RAREUIMenuItem *) check_class_cast([self getNamedItemWithNSString:name], [RAREUIMenuItem class]);
  if (item == nil) {
    return NO;
  }
  return [((RAREUIMenuItem *) nil_chk(item)) isEnabled];
}

- (BOOL)isItemSelectedWithNSString:(NSString *)name {
  if (name == nil) {
    return NO;
  }
  RAREUIMenuItem *item = (RAREUIMenuItem *) check_class_cast([self getNamedItemWithNSString:name], [RAREUIMenuItem class]);
  if (item == nil) {
    return NO;
  }
  return [((RAREUIMenuItem *) nil_chk(item)) isSelected];
}

- (BOOL)isItemVisibleWithNSString:(NSString *)name {
  if (name == nil) {
    return NO;
  }
  RAREUIMenuItem *item = (RAREUIMenuItem *) check_class_cast([self getNamedItemWithNSString:name], [RAREUIMenuItem class]);
  if (item == nil) {
    return NO;
  }
  return [((RAREUIMenuItem *) nil_chk(item)) isVisible];
}

- (void)clearConfigurationWithBoolean:(BOOL)dispose {
  [super clearConfigurationWithBoolean:dispose];
  if (menuBar_ != nil) {
    [menuBar_ removeAll];
  }
  IOSObjectArray *a = menuItems_;
  int len = (a == nil) ? 0 : (int) [a count];
  for (int i = 0; i < len; i++) {
    [((RAREUIMenuItem *) IOSObjectArray_Get(nil_chk(a), i)) dispose];
    (void) IOSObjectArray_Set(a, i, nil);
  }
  if ((debugMenu_ != nil) && dispose) {
    [debugMenu_ dispose];
    debugMenu_ = nil;
  }
  menuItems_ = nil;
  selectedItem_ = nil;
}

- (void)configureExWithRARESPOTMenuBar:(RARESPOTMenuBar *)cfg {
  (void) [self createWithRARESPOTMenuBar:cfg];
}

- (id<RAREiMenuBarComponent>)createMenuBarAndComponentsWithRARESPOTMenuBar:(RARESPOTMenuBar *)cfg {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)addDebugOptions {
}

- (void)copyAllFieldsTo:(RAREaMenuBarViewer *)other {
  [super copyAllFieldsTo:other];
  other->debugMenu_ = debugMenu_;
  other->menuBar_ = menuBar_;
  other->menuItems_ = menuItems_;
  other->selectedItem_ = selectedItem_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "configureMenusWithRAREiPlatformComponent:withRARESPOTWidget:withBoolean:", NULL, "V", 0x4, NULL },
    { "createWithRARESPOTMenuBar:", NULL, "LRAREiMenuBarComponent", 0x1, NULL },
    { "createCheckBoxMenuItemWithRARERenderableDataItem:", NULL, "LRAREUIMenuItem", 0x1, NULL },
    { "createCheckboxMenuItemWithNSString:withRAREiPlatformIcon:withId:", NULL, "LRAREUIMenuItem", 0x1, NULL },
    { "createMenuWithNSString:withRAREiPlatformIcon:withId:", NULL, "LRAREUIMenu", 0x1, NULL },
    { "createMenuItemWithRARESPOTMenuItem:", NULL, "LRAREUIMenuItem", 0x1, NULL },
    { "createMenuItemWithRARERenderableDataItem:", NULL, "LRAREUIMenuItem", 0x1, NULL },
    { "createMenuItemWithRAREUIAction:withBoolean:", NULL, "LRAREUIMenuItem", 0x1, NULL },
    { "createMenuItemWithNSString:withRAREiPlatformIcon:withId:", NULL, "LRAREUIMenuItem", 0x1, NULL },
    { "createMenuItemWithNSString:withRAREiPlatformIcon:withId:withNSString:", NULL, "LRAREUIMenuItem", 0x1, NULL },
    { "createMenuItemsWithSPOTSet:", NULL, "LJavaUtilList", 0x1, NULL },
    { "getMenuWithNSString:", NULL, "LRAREUIMenu", 0x1, NULL },
    { "getMenuBarComponent", NULL, "LRAREiMenuBarComponent", 0x1, NULL },
    { "getSelection", NULL, "LNSObject", 0x1, NULL },
    { "getSeparatorItem", NULL, "LRAREUIMenuItem", 0x1, NULL },
    { "hasSelection", NULL, "Z", 0x1, NULL },
    { "isItemEnabledWithNSString:", NULL, "Z", 0x1, NULL },
    { "isItemSelectedWithNSString:", NULL, "Z", 0x1, NULL },
    { "isItemVisibleWithNSString:", NULL, "Z", 0x1, NULL },
    { "clearConfigurationWithBoolean:", NULL, "V", 0x4, NULL },
    { "configureExWithRARESPOTMenuBar:", NULL, "V", 0x4, NULL },
    { "createMenuBarAndComponentsWithRARESPOTMenuBar:", NULL, "LRAREiMenuBarComponent", 0x404, NULL },
    { "addDebugOptions", NULL, "V", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "MENUBAR_NAME_", NULL, 0x19, "LNSString" },
    { "debugMenu_", NULL, 0x4, "LRAREUIMenu" },
    { "menuBar_", NULL, 0x4, "LRAREiMenuBarComponent" },
    { "menuItems_", NULL, 0x4, "LIOSObjectArray" },
    { "selectedItem_", NULL, 0x4, "LRAREUIMenuItem" },
  };
  static J2ObjcClassInfo _RAREaMenuBarViewer = { "aMenuBarViewer", "com.appnativa.rare.viewer", NULL, 0x401, 23, methods, 5, fields, 0, NULL};
  return &_RAREaMenuBarViewer;
}

@end
