//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/PopupList.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/platform/apple/ui/PopupListBoxHandler.h"
#include "com/appnativa/rare/scripting/Functions.h"
#include "com/appnativa/rare/ui/BorderUtils.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/PopupList.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/TitlePane.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIMenuItem.h"
#include "com/appnativa/rare/ui/UIPopupMenu.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIScreen.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/WindowPane.h"
#include "com/appnativa/rare/ui/border/UIDropShadowBorder.h"
#include "com/appnativa/rare/ui/border/UIEmptyBorder.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/event/EventListenerList.h"
#include "com/appnativa/rare/ui/event/ExpansionEvent.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/event/iPopupMenuListener.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/iPlatformItemRenderer.h"
#include "com/appnativa/rare/ui/iPlatformListDataModel.h"
#include "com/appnativa/rare/ui/iPlatformListHandler.h"
#include "com/appnativa/rare/ui/iPlatformWindowManager.h"
#include "com/appnativa/rare/ui/iPopup.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/painter/UIComponentPainter.h"
#include "com/appnativa/rare/ui/painter/iBackgroundPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/ui/renderer/aListItemRenderer.h"
#include "com/appnativa/rare/util/ListHelper.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/util/iFilterableList.h"
#include "java/lang/Math.h"
#include "java/util/List.h"

@implementation RAREPopupList

static RAREUIEmptyBorder * RAREPopupList_listBorder_;

+ (RAREUIEmptyBorder *)listBorder {
  return RAREPopupList_listBorder_;
}

+ (void)setListBorder:(RAREUIEmptyBorder *)listBorder {
  RAREPopupList_listBorder_ = listBorder;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)context {
  if (self = [super init]) {
    contextWidget_ = context;
  }
  return self;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e {
  int row = [((id<RAREiPlatformListHandler>) nil_chk(listHandler_)) getSelectedIndex];
  id o = [listHandler_ getSelection];
  [listHandler_ clearSelection];
  [RAREListHelper flashHilightWithRAREiListHandler:listHandler_ withInt:row withBoolean:YES withInt:3 withJavaLangRunnable:[[RAREPopupList_$1 alloc] initWithRAREPopupList:self withId:o]];
}

- (void)addPopupMenuListenerWithRAREiPopupMenuListener:(id<RAREiPopupMenuListener>)l {
  if (l != nil) {
    [((RAREEventListenerList *) nil_chk([self getEventListenerList])) addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiPopupMenuListener)] withId:l];
  }
}

- (void)removePopupMenuListenerWithRAREiPopupMenuListener:(id<RAREiPopupMenuListener>)l {
  if ((l != nil) && (listenerList_ != nil)) {
    [listenerList_ removeWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiPopupMenuListener)] withId:l];
  }
}

- (void)dispose {
  if ((currentPopup_ != nil) && [currentPopup_ isShowing]) {
    [currentPopup_ hidePopup];
    return;
  }
  if (currentPopup_ != nil) {
    [currentPopup_ dispose];
    currentPopup_ = nil;
  }
  if (listHandler_ != nil) {
    [listHandler_ dispose];
  }
  if (listenerList_ != nil) {
    [listenerList_ clear];
  }
  border_ = nil;
  popupPainter_ = nil;
  currentPopup_ = nil;
  listHandler_ = nil;
  listenerList_ = nil;
}

- (void)popupMenuCanceledWithRAREExpansionEvent:(RAREExpansionEvent *)e {
  if (listenerList_ != nil) {
    [RAREUtils firePopupCanceledEventWithRAREEventListenerList:listenerList_ withRAREExpansionEvent:e];
  }
}

- (void)popupMenuWillBecomeInvisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e {
  if (listenerList_ != nil) {
    [RAREUtils firePopupEventWithRAREEventListenerList:listenerList_ withRAREExpansionEvent:e withBoolean:NO];
  }
  [RAREPlatform invokeLaterWithJavaLangRunnable:[[RAREPopupList_$2 alloc] initWithRAREPopupList:self]];
}

- (void)popupMenuWillBecomeVisibleWithRAREExpansionEvent:(RAREExpansionEvent *)e {
  if (listenerList_ != nil) {
    [RAREUtils firePopupEventWithRAREEventListenerList:listenerList_ withRAREExpansionEvent:e withBoolean:YES];
  }
}

- (void)showModalPopupWithBoolean:(BOOL)showCloseButton {
  currentPopup_ = [self createPopupWithBoolean:YES withBoolean:showCloseButton];
  [((id<RAREiPopup>) nil_chk(currentPopup_)) showModalPopup];
}

- (void)showPopupWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)owner
                                    withInt:(int)x
                                    withInt:(int)y {
  if (currentPopup_ != nil) {
    [currentPopup_ dispose];
  }
  currentPopup_ = [self createPopupWithBoolean:NO withBoolean:NO];
  [((id<RAREiPopup>) nil_chk(currentPopup_)) setTransientWithBoolean:YES];
  RAREUIRectangle *r = [[RAREUIRectangle alloc] init];
  [RAREUtils getProposedBoundsForLocationWithRAREUIRectangle:r withInt:x withInt:y withRAREUIDimension:[self getPreferredSize]];
  [currentPopup_ showPopupWithRAREiPlatformComponent:owner withFloat:(int) r->x_ withFloat:(int) r->y_];
}

- (void)setItemsWithJavaUtilList:(id<JavaUtilList>)items
         withRAREiActionListener:(id<RAREiActionListener>)l
                     withBoolean:(BOOL)menuStyle
                         withInt:(int)visibleRowCount {
  id<RAREiWidget> ctx = (contextWidget_ == nil) ? [RAREPlatform getWindowViewer] : ((id) contextWidget_);
  id<RAREiPlatformListDataModel> model = [RAREaPlatformHelper createListDataModelWithRAREiWidget:ctx withJavaUtilList:items];
  listHandler_ = [RAREaPlatformHelper createPopupListBoxHandlerWithRAREiWidget:ctx withRAREiPlatformListDataModel:model withBoolean:menuStyle];
  [((id<RAREiPlatformComponent>) nil_chk([((id<RAREiPlatformListHandler>) nil_chk(listHandler_)) getListComponent])) setFocusPaintedWithBoolean:NO];
  [listHandler_ addActionListenerWithRAREiActionListener:self];
  actionListener_ = l;
  [listHandler_ setVisibleRowCountWithInt:visibleRowCount];
  int rh = [self getDefaultRowHeight];
  int len = [((id<JavaUtilList>) nil_chk(items)) size];
  for (int i = 0; i < len; i++) {
    rh = [JavaLangMath maxWithInt:rh withInt:[listHandler_ getPreferredHeightWithInt:i]];
  }
  [listHandler_ setRowHeightWithInt:rh + [RAREUIScreen PLATFORM_PIXELS_2]];
  self->menuStyle_ = menuStyle;
}

- (void)setMenuItemsWithRAREUIPopupMenu:(RAREUIPopupMenu *)menu {
  [self setItemsWithJavaUtilList:[((RAREUIPopupMenu *) nil_chk(menu)) getItems] withRAREiActionListener:nil withBoolean:YES withInt:[menu size]];
}

- (int)getDefaultRowHeight {
  if (defaultRowHeight_ == 0) {
    defaultRowHeight_ = [RAREUIScreen toPlatformPixelHeightWithNSString:[RAREaPlatformHelper getDefaultRowHeight] withRAREiPlatformComponent:[((RAREWindowViewer *) nil_chk([RAREPlatform getWindowViewer])) getComponent] withFloat:100 withBoolean:NO];
  }
  return defaultRowHeight_;
}

- (void)setPopupPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)popupPainter {
  self->popupPainter_ = popupPainter;
}

- (void)setPoupuBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b {
  border_ = b;
}

- (id<RAREiPlatformComponent>)getContent {
  return [((id<RAREiPlatformListHandler>) nil_chk(listHandler_)) getContainerComponent];
}

- (id<RAREiPlatformBorder>)getPopupBorder {
  return (popupPainter_ == nil) ? border_ : [popupPainter_ getBorder];
}

- (id<RAREiPlatformComponentPainter>)getPopupPainter {
  return popupPainter_;
}

- (RAREUIDimension *)getPreferredSize {
  return [((id<RAREiPlatformListHandler>) nil_chk(listHandler_)) getPreferredSize];
}

- (BOOL)isPopupVisible {
  return (currentPopup_ != nil) && [currentPopup_ isShowing];
}

- (id<RAREiPopup>)createPopupWithBoolean:(BOOL)modal
                             withBoolean:(BOOL)showCloseButton {
  if (!initialized_) {
    initialized_ = YES;
    [self setRenderingDefaults];
  }
  id<RAREiPopup> p = [((id<RAREiPlatformWindowManager>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getWindowManager])) createPopupWithRAREiWidget:contextWidget_];
  [((id<RAREiPopup>) nil_chk(p)) setPopupOwnerWithRAREiPlatformComponent:[((id<RAREiWidget>) nil_chk(contextWidget_)) getContainerComponent]];
  id<RAREiActionComponent> a = nil;
  NSString *s = nil;
  if (showCloseButton) {
    a = [RAREPlatformHelper createNakedButtonWithRAREiPlatformComponent:[p getWindowPane] withBoolean:NO withInt:0];
    s = cancelButtonText_;
    if ((s == nil) || ([s sequenceLength] == 0)) {
      s = [RAREPlatform getResourceAsStringWithNSString:@"Rare.runtime.text.popupMenu.cancel"];
      RAREUIFont *f = [((id<RAREiActionComponent>) nil_chk(a)) getFont];
      [a setFontWithRAREUIFont:[f deriveFontWithInt:[RAREUIFont BOLD] withFloat:[((RAREUIFont *) nil_chk(f)) getSize] + 2]];
    }
    else {
      if (![s hasPrefix:@"<html>"]) {
        int wrap = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getIntWithNSString:@"Rare.PopupMenu.titleCharacterWrapCount" withInt:40];
        s = [RAREFunctions htmlWordWrapWithNSString:s withInt:wrap withBoolean:YES];
      }
    }
    [((id<RAREiActionComponent>) nil_chk(a)) setTextWithJavaLangCharSequence:s];
    [a setIconGapWithInt:[RAREUIScreen platformPixelsWithInt:4]];
    [a setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:[RARERenderableDataItem_HorizontalAlignEnum LEFT] withRARERenderableDataItem_VerticalAlignEnum:[RARERenderableDataItem_VerticalAlignEnum CENTER]];
    [a setIconPositionWithRARERenderableDataItem_IconPositionEnum:[RARERenderableDataItem_IconPositionEnum RIGHT_JUSTIFIED]];
    [a setIconWithRAREiPlatformIcon:[RAREPlatform getResourceAsIconWithNSString:@"Rare.icon.close"]];
    [a setBackgroundWithRAREUIColor:[RAREColorUtils getBackground]];
    [a addActionListenerWithRAREiActionListener:[[RAREPopupList_$3 alloc] initWithRAREPopupList:self]];
    [a setForegroundWithRAREUIColor:[((id<RAREiPlatformComponent>) nil_chk([((id<RAREiPlatformListHandler>) nil_chk(listHandler_)) getListComponent])) getForeground]];
    [a setBorderWithRAREiPlatformBorder:[RARETitlePane getTitlePaneBorder]];
    [a putClientPropertyWithNSString:[RAREiConstants RARE_MIN_HEIGHT_PROPERTY] withId:@"1.5ln"];
    [((RAREWindowPane *) nil_chk([p getWindowPane])) setTitileBarWithRAREiPlatformComponent:a];
  }
  if (modal) {
    [((id<RAREiPlatformComponent>) nil_chk([((id<RAREiPlatformListHandler>) nil_chk(listHandler_)) getContainerComponent])) putClientPropertyWithNSString:[RAREiConstants RARE_MIN_WIDTH_PROPERTY] withId:@"20ch"];
  }
  [p setContentWithRAREiPlatformComponent:[((id<RAREiPlatformListHandler>) nil_chk(listHandler_)) getContainerComponent]];
  if (popupPainter_ != nil) {
    [p setComponentPainterWithRAREiPlatformComponentPainter:popupPainter_];
  }
  [p addPopupMenuListenerWithRAREiPopupMenuListener:self];
  return p;
}

- (void)setRenderingDefaults {
  id<RAREiPlatformComponent> list = [((id<RAREiPlatformListHandler>) nil_chk(listHandler_)) getListComponent];
  id<RAREiPlatformItemRenderer> renderer = (RAREaListItemRenderer *) check_class_cast([listHandler_ getItemRenderer], [RAREaListItemRenderer class]);
  RAREUIProperties *p = [RAREPlatform getUIDefaults];
  RAREUIColor *c = menuStyle_ ? [((RAREUIProperties *) nil_chk(p)) getColorWithNSString:@"Rare.PopupMenu.list.selectedForeground"] : nil;
  ;
  if (c == nil) {
    c = [((RAREUIProperties *) nil_chk(p)) getColorWithNSString:@"Rare.ComboBox.list.selectedForeground"];
  }
  RAREPaintBucket *pb = menuStyle_ ? [((RAREUIProperties *) nil_chk(p)) getPaintBucketWithNSString:@"Rare.PopupMenu.list.selectedBackground"] : nil;
  if (pb == nil) {
    pb = [((id<RAREiPlatformItemRenderer>) nil_chk(renderer)) getAutoHilightPaint];
  }
  if ((c != nil) || (pb != nil)) {
    if (pb == nil) {
      pb = [[RAREPaintBucket alloc] init];
      [pb setForegroundColorWithRAREUIColor:c];
    }
    [((id<RAREiPlatformItemRenderer>) nil_chk(renderer)) setSelectionPaintWithRAREPaintBucket:pb];
  }
  c = menuStyle_ ? [((RAREUIProperties *) nil_chk(p)) getColorWithNSString:@"Rare.PopupMenu.list.foreground"] : nil;
  if (c == nil) {
    c = [((RAREUIProperties *) nil_chk(p)) getColorWithNSString:@"Rare.ComboBox.list.foreground"];
  }
  if (c != nil) {
    [((id<RAREiPlatformComponent>) nil_chk(list)) setForegroundWithRAREUIColor:c];
  }
  c = menuStyle_ ? [((RAREUIProperties *) nil_chk(p)) getColorWithNSString:@"Rare.PopupMenu.list.background"] : nil;
  if (c == nil) {
    c = [((RAREUIProperties *) nil_chk(p)) getColorWithNSString:@"Rare.ComboBox.list.background"];
  }
  if (c != nil) {
    [((id<RAREiPlatformComponent>) nil_chk(list)) setBackgroundWithRAREUIColor:c];
  }
  [((id<RAREiPlatformComponent>) nil_chk(list)) setBorderWithRAREiPlatformBorder:RAREPopupList_listBorder_];
  [list putClientPropertyWithNSString:[RAREiConstants RARE_MIN_WIDTH_PROPERTY] withId:@"12ch"];
  id<RAREiBackgroundPainter> bp = [((RAREUIProperties *) nil_chk(p)) getBackgroundPainterWithNSString:@"Rare.PopupMenu.background"];
  if (popupPainter_ == nil) {
    if (border_ == nil) {
      border_ = [p getBorderWithNSString:@"Rare.PopupMenu.border"];
    }
    if (border_ == nil) {
      border_ = [RAREBorderUtils createShadowBorderWithFloat:[RAREScreenUtils PLATFORM_PIXELS_7]];
    }
    popupPainter_ = [[RAREUIComponentPainter alloc] init];
    [popupPainter_ setBorderWithRAREiPlatformBorder:border_];
    if (bp == nil) {
      [popupPainter_ setBackgroundColorWithRAREUIColor:c];
    }
    else if ([bp isSingleColorPainter]) {
      [popupPainter_ setBackgroundColorWithRAREUIColor:[bp getBackgroundColor]];
    }
    else {
      [popupPainter_ setBackgroundPainterWithRAREiBackgroundPainter:bp withBoolean:NO];
    }
  }
}

- (NSString *)getCancelButtonText {
  return cancelButtonText_;
}

- (void)setCancelButtonTextWithNSString:(NSString *)cancelButtonText {
  self->cancelButtonText_ = cancelButtonText;
}

- (RAREEventListenerList *)getEventListenerList {
  if (listenerList_ == nil) {
    listenerList_ = [[RAREEventListenerList alloc] init];
  }
  return listenerList_;
}

+ (void)initialize {
  if (self == [RAREPopupList class]) {
    RAREPopupList_listBorder_ = [[RAREUIEmptyBorder alloc] initWithFloat:[RAREScreenUtils PLATFORM_PIXELS_8]];
  }
}

- (void)copyAllFieldsTo:(RAREPopupList *)other {
  [super copyAllFieldsTo:other];
  other->actionListener_ = actionListener_;
  other->border_ = border_;
  other->cancelButtonText_ = cancelButtonText_;
  other->contextWidget_ = contextWidget_;
  other->currentPopup_ = currentPopup_;
  other->defaultRowHeight_ = defaultRowHeight_;
  other->initialized_ = initialized_;
  other->listHandler_ = listHandler_;
  other->listenerList_ = listenerList_;
  other->menuStyle_ = menuStyle_;
  other->popupPainter_ = popupPainter_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getDefaultRowHeight", NULL, "I", 0x4, NULL },
    { "getContent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getPopupBorder", NULL, "LRAREiPlatformBorder", 0x1, NULL },
    { "getPopupPainter", NULL, "LRAREiPlatformComponentPainter", 0x1, NULL },
    { "getPreferredSize", NULL, "LRAREUIDimension", 0x1, NULL },
    { "isPopupVisible", NULL, "Z", 0x1, NULL },
    { "createPopupWithBoolean:withBoolean:", NULL, "LRAREiPopup", 0x4, NULL },
    { "setRenderingDefaults", NULL, "V", 0x4, NULL },
    { "getCancelButtonText", NULL, "LNSString", 0x1, NULL },
    { "getEventListenerList", NULL, "LRAREEventListenerList", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "listBorder_", NULL, 0xa, "LRAREUIEmptyBorder" },
    { "actionListener_", NULL, 0x4, "LRAREiActionListener" },
    { "contextWidget_", NULL, 0x4, "LRAREiWidget" },
    { "currentPopup_", NULL, 0x4, "LRAREiPopup" },
    { "initialized_", NULL, 0x4, "Z" },
    { "listHandler_", NULL, 0x4, "LRAREiPlatformListHandler" },
    { "popupPainter_", NULL, 0x4, "LRAREiPlatformComponentPainter" },
    { "cancelButtonText_", NULL, 0x4, "LNSString" },
  };
  static J2ObjcClassInfo _RAREPopupList = { "PopupList", "com.appnativa.rare.ui", NULL, 0x1, 10, methods, 8, fields, 0, NULL};
  return &_RAREPopupList;
}

@end
@implementation RAREPopupList_$1

- (void)run {
  if (this$0_->actionListener_ != nil) {
    [this$0_->actionListener_ actionPerformedWithRAREActionEvent:[[RAREActionEvent alloc] initWithId:val$o_]];
  }
  else if ([val$o_ isKindOfClass:[RAREUIMenuItem class]]) {
    [((RAREUIMenuItem *) check_class_cast(val$o_, [RAREUIMenuItem class])) fireWithRAREiWidget:this$0_->contextWidget_];
  }
  [((id<RAREiPopup>) nil_chk(this$0_->currentPopup_)) hidePopup];
}

- (id)initWithRAREPopupList:(RAREPopupList *)outer$
                     withId:(id)capture$0 {
  this$0_ = outer$;
  val$o_ = capture$0;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREPopupList" },
    { "val$o_", NULL, 0x1012, "LNSObject" },
  };
  static J2ObjcClassInfo _RAREPopupList_$1 = { "$1", "com.appnativa.rare.ui", "PopupList", 0x8000, 0, NULL, 2, fields, 0, NULL};
  return &_RAREPopupList_$1;
}

@end
@implementation RAREPopupList_$2

- (void)run {
  [this$0_ dispose];
}

- (id)initWithRAREPopupList:(RAREPopupList *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREPopupList" },
  };
  static J2ObjcClassInfo _RAREPopupList_$2 = { "$2", "com.appnativa.rare.ui", "PopupList", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREPopupList_$2;
}

@end
@implementation RAREPopupList_$3

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e {
  [this$0_ dispose];
}

- (id)initWithRAREPopupList:(RAREPopupList *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREPopupList" },
  };
  static J2ObjcClassInfo _RAREPopupList_$3 = { "$3", "com.appnativa.rare.ui", "PopupList", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREPopupList_$3;
}

@end
