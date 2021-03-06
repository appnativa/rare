//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/WindowPane.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/jgoodies/forms/layout/CellConstraints.h"
#include "com/appnativa/jgoodies/forms/layout/FormLayout.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/ui/Container.h"
#include "com/appnativa/rare/ui/TitlePane.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIScreen.h"
#include "com/appnativa/rare/ui/WindowPane.h"
#include "com/appnativa/rare/ui/aFormsPanel.h"
#include "com/appnativa/rare/ui/event/EventListenerList.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/iPlatformMenuBar.h"
#include "com/appnativa/rare/ui/iStatusBar.h"
#include "com/appnativa/rare/ui/iToolBarHolder.h"
#include "com/appnativa/rare/viewer/WindowViewer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/lang/Throwable.h"

@implementation RAREWindowPane

- (id)initWithRAREiWidget:(id<RAREiWidget>)context {
  if (self = [super initWithRAREiWidget:context withRAREFormLayout:[RAREWindowPane createPaneFormLayout]]) {
    ccTitlebar_ = [[RARECellConstraints alloc] initWithInt:1 withInt:1 withInt:1 withInt:1 withRARECellConstraints_Alignment:[RARECellConstraints FILL] withRARECellConstraints_Alignment:[RARECellConstraints FILL]];
    ccMenubar_ = [[RARECellConstraints alloc] initWithInt:1 withInt:2 withInt:1 withInt:1 withRARECellConstraints_Alignment:[RARECellConstraints FILL] withRARECellConstraints_Alignment:[RARECellConstraints FILL]];
    ccToolbar_ = [[RARECellConstraints alloc] initWithInt:1 withInt:3 withInt:1 withInt:1 withRARECellConstraints_Alignment:[RARECellConstraints FILL] withRARECellConstraints_Alignment:[RARECellConstraints FILL]];
    ccStatusbar_ = [[RARECellConstraints alloc] initWithInt:1 withInt:5 withInt:1 withInt:1 withRARECellConstraints_Alignment:[RARECellConstraints FILL] withRARECellConstraints_Alignment:[RARECellConstraints FILL]];
    ccContent_ = [[RARECellConstraints alloc] initWithInt:1 withInt:4 withInt:1 withInt:1 withRARECellConstraints_Alignment:[RARECellConstraints FILL] withRARECellConstraints_Alignment:[RARECellConstraints FILL]];
  }
  return self;
}

- (id)initWithId:(id)view {
  if (self = [super initWithId:view]) {
    ccTitlebar_ = [[RARECellConstraints alloc] initWithInt:1 withInt:1 withInt:1 withInt:1 withRARECellConstraints_Alignment:[RARECellConstraints FILL] withRARECellConstraints_Alignment:[RARECellConstraints FILL]];
    ccMenubar_ = [[RARECellConstraints alloc] initWithInt:1 withInt:2 withInt:1 withInt:1 withRARECellConstraints_Alignment:[RARECellConstraints FILL] withRARECellConstraints_Alignment:[RARECellConstraints FILL]];
    ccToolbar_ = [[RARECellConstraints alloc] initWithInt:1 withInt:3 withInt:1 withInt:1 withRARECellConstraints_Alignment:[RARECellConstraints FILL] withRARECellConstraints_Alignment:[RARECellConstraints FILL]];
    ccStatusbar_ = [[RARECellConstraints alloc] initWithInt:1 withInt:5 withInt:1 withInt:1 withRARECellConstraints_Alignment:[RARECellConstraints FILL] withRARECellConstraints_Alignment:[RARECellConstraints FILL]];
    ccContent_ = [[RARECellConstraints alloc] initWithInt:1 withInt:4 withInt:1 withInt:1 withRARECellConstraints_Alignment:[RARECellConstraints FILL] withRARECellConstraints_Alignment:[RARECellConstraints FILL]];
  }
  return self;
}

- (void)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                               withId:(id)constraints
                              withInt:(int)position {
  if (constraints == nil) {
    [self setContentWithRAREiPlatformComponent:c];
  }
  else {
    [super addWithRAREiPlatformComponent:c withId:constraints withInt:position];
  }
}

- (void)centerContent {
  id<RAREiPlatformComponent> c = [self getContent];
  ccContent_ = [[RARECellConstraints alloc] initWithInt:1 withInt:4 withInt:1 withInt:1 withRARECellConstraints_Alignment:[RARECellConstraints CENTER] withRARECellConstraints_Alignment:[RARECellConstraints CENTER]];
  if (c != nil) {
    [self removeWithRAREiPlatformComponent:c];
  }
  [self setContentWithRAREiPlatformComponent:c];
}

- (RARETitlePane *)createDialogTitleBarWithRAREiWidget:(id<RAREiWidget>)context
                               withRAREiActionListener:(id<RAREiActionListener>)closeAction {
  RARETitlePane *tp = [RARETitlePane createDialogTitleWithRAREiWidget:context withRAREiActionListener:closeAction];
  [self setTitileBarWithRAREiPlatformComponent:tp];
  return tp;
}

- (id<RAREiPlatformComponent>)getContent {
  return [self getGridComponentAtWithInt:1 withInt:((RARECellConstraints *) nil_chk(ccContent_))->gridY_];
}

- (RAREEventListenerList *)getEventListenerList {
  return [super getEventListenerList];
}

- (RAREUIRectangle *)getInnerBoundsWithRAREUIRectangle:(RAREUIRectangle *)rect {
  rect = [super getInnerBoundsWithRAREUIRectangle:rect];
  id<RAREiPlatformComponent> c = [self getGridComponentAtWithInt:1 withInt:((RARECellConstraints *) nil_chk(ccStatusbar_))->gridY_];
  if ((c != nil) && [c isVisible]) {
    ((RAREUIRectangle *) nil_chk(rect))->height_ -= [c getHeight];
  }
  c = [self getGridComponentAtWithInt:1 withInt:((RARECellConstraints *) nil_chk(ccTitlebar_))->gridY_];
  if ((c != nil) && [c isVisible]) {
    ((RAREUIRectangle *) nil_chk(rect))->height_ -= [c getHeight];
  }
  c = [self getGridComponentAtWithInt:1 withInt:((RARECellConstraints *) nil_chk(ccMenubar_))->gridY_];
  if ((c != nil) && [c isVisible]) {
    ((RAREUIRectangle *) nil_chk(rect))->height_ -= [c getHeight];
  }
  c = [self getGridComponentAtWithInt:1 withInt:((RARECellConstraints *) nil_chk(ccToolbar_))->gridY_];
  if ((c != nil) && [c isVisible]) {
    ((RAREUIRectangle *) nil_chk(rect))->height_ -= [c getHeight];
  }
  ((RAREUIRectangle *) nil_chk(rect))->height_ -= [self getPlatformDecorationsHeight];
  return rect;
}

- (RAREUIDimension *)getInnerSizeWithRAREUIDimension:(RAREUIDimension *)size {
  RAREUIRectangle *rect = [self getInnerBoundsWithRAREUIRectangle:nil];
  if (size == nil) {
    return [[RAREUIDimension alloc] init];
  }
  ((RAREUIDimension *) nil_chk(size))->width_ = [RAREUIScreen snapToSizeWithFloat:((RAREUIRectangle *) nil_chk(rect))->width_];
  size->height_ = [RAREUIScreen snapToSizeWithFloat:rect->height_];
  return size;
}

- (id<RAREiPlatformMenuBar>)getMenuBar {
  return menuBar_;
}

- (int)getPlatformDecorationsHeight {
  return 0;
}

- (int)getPlatformWindowOffset {
  return 0;
}

- (id<RAREiStatusBar>)getStatusBar {
  return statusBar_;
}

- (NSString *)getTitle {
  return title_;
}

- (RARETitlePane *)getTitlePaneWithBoolean:(BOOL)create {
  id<RAREiPlatformComponent> c = [self getGridComponentAtWithInt:1 withInt:((RARECellConstraints *) nil_chk(ccTitlebar_))->gridY_];
  if ([(id) c isKindOfClass:[RARETitlePane class]]) {
    return (RARETitlePane *) check_class_cast(c, [RARETitlePane class]);
  }
  if ((c == nil) && create) {
    RARETitlePane *tp = [[RARETitlePane alloc] initWithRAREiWidget:[RAREPlatform getWindowViewer]];
    [self setTitileBarWithRAREiPlatformComponent:tp];
    return tp;
  }
  return nil;
}

- (id<RAREiToolBarHolder>)getToolBarHolder {
  return toolbarHolder_;
}

- (BOOL)isAutoCreateTitlePane {
  return autoCreateTitlePane_;
}

- (BOOL)isCombineMenuBarAndTitle {
  return combineMenuBarAndTitle_;
}

- (void)setAutoCreateTitlePaneWithBoolean:(BOOL)autoCreateTitlePane {
  self->autoCreateTitlePane_ = autoCreateTitlePane;
}

- (void)setCombineMenuBarAndTitleWithBoolean:(BOOL)combineMenuBarAndTitle {
  self->combineMenuBarAndTitle_ = combineMenuBarAndTitle;
}

- (void)setContentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)content {
  id<RAREiPlatformComponent> c = [self getGridComponentAtWithInt:1 withInt:((RARECellConstraints *) nil_chk(ccContent_))->gridY_];
  if (c != nil) {
    [self removeWithRAREiPlatformComponent:c];
  }
  if (content != nil) {
    [self addWithRAREiPlatformComponent:content withId:ccContent_];
  }
}

- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  self->icon_ = icon;
  RARETitlePane *tp = [self getTitlePaneWithBoolean:[self isAutoCreateTitlePane]];
  if (tp != nil) {
    [tp setIconWithRAREiPlatformIcon:icon];
  }
}

- (void)setMenuBarWithRAREiPlatformMenuBar:(id<RAREiPlatformMenuBar>)mb {
  menuBar_ = mb;
  if (combineMenuBarAndTitle_) {
    RARETitlePane *tp = [self getTitlePaneWithBoolean:YES];
    [((RARETitlePane *) nil_chk(tp)) setOtherComponentWithRAREiPlatformComponent:(mb == nil) ? nil : [mb getContainerComponent]];
  }
  else {
    id<RAREiPlatformComponent> c = [self getGridComponentAtWithInt:1 withInt:((RARECellConstraints *) nil_chk(ccMenubar_))->gridY_];
    if (c != nil) {
      [self removeWithRAREiPlatformComponent:c];
    }
    if (mb != nil) {
      [self addWithRAREiPlatformComponent:[mb getContainerComponent] withId:ccMenubar_];
    }
  }
}

- (void)setStatusBarWithRAREiStatusBar:(id<RAREiStatusBar>)sb {
  statusBar_ = sb;
  id<RAREiPlatformComponent> c = [self getGridComponentAtWithInt:1 withInt:((RARECellConstraints *) nil_chk(ccStatusbar_))->gridY_];
  if (c != nil) {
    [self removeWithRAREiPlatformComponent:c];
  }
  if (sb != nil) {
    [self addWithRAREiPlatformComponent:[sb getComponent] withId:ccStatusbar_];
  }
}

- (void)setTitileBarWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)tb {
  id<RAREiPlatformComponent> c = [self getGridComponentAtWithInt:1 withInt:((RARECellConstraints *) nil_chk(ccTitlebar_))->gridY_];
  if (c != nil) {
    [self removeWithRAREiPlatformComponent:c];
    if ([(id) c isKindOfClass:[RARETitlePane class]]) {
      [c dispose];
    }
  }
  if (tb != nil) {
    [self addWithRAREiPlatformComponent:tb withId:ccTitlebar_];
  }
}

- (void)setTitleWithNSString:(NSString *)title {
  self->title_ = title;
  RARETitlePane *tp = [self getTitlePaneWithBoolean:[self isAutoCreateTitlePane]];
  if (tp != nil) {
    [tp setTitleWithNSString:title];
  }
}

- (void)setTitlePaneAsWindowDraggerWithRAREWindowViewer:(RAREWindowViewer *)w {
  RARETitlePane *tp = [self getTitlePaneWithBoolean:YES];
  [((RAREWindowViewer *) nil_chk(w)) addWindowDraggerWithRAREiPlatformComponent:tp];
}

- (void)setToolBarHolderWithRAREiToolBarHolder:(id<RAREiToolBarHolder>)tbh {
  toolbarHolder_ = tbh;
  id<RAREiPlatformComponent> c = [self getGridComponentAtWithInt:1 withInt:((RARECellConstraints *) nil_chk(ccToolbar_))->gridY_];
  if (c != nil) {
    [self removeWithRAREiPlatformComponent:c];
  }
  if (tbh != nil) {
    [self addWithRAREiPlatformComponent:[tbh getComponent] withId:ccToolbar_];
  }
}

- (void)windowGainedFocus {
  if (firstTime_) {
    firstTime_ = NO;
    id<RAREiPlatformComponent> c = [self getGridComponentAtWithInt:1 withInt:((RARECellConstraints *) nil_chk(ccContent_))->gridY_];
    if (c != nil) {
      [c requestFocus];
    }
  }
  RARETitlePane *tp = [self getTitlePaneWithBoolean:NO];
  if (tp != nil) {
    [tp repaint];
  }
}

- (void)windowLostFocus {
  RARETitlePane *tp = [self getTitlePaneWithBoolean:NO];
  if (tp != nil) {
    [tp repaint];
  }
}

- (void)dispose {
  [super dispose];
  @try {
    if (menuBar_ != nil) {
      [menuBar_ dispose];
      menuBar_ = nil;
    }
    if (statusBar_ != nil) {
      [statusBar_ dispose];
      statusBar_ = nil;
    }
    if (toolbarHolder_ != nil) {
      [toolbarHolder_ dispose];
      toolbarHolder_ = nil;
    }
  }
  @catch (JavaLangThrowable *e) {
    [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:e];
  }
}

+ (RAREFormLayout *)createPaneFormLayout {
  return [[RAREFormLayout alloc] initWithNSString:@"f:d:g" withNSString:@"f:p,f:p,f:p,f:d:g,f:p"];
}

- (void)copyAllFieldsTo:(RAREWindowPane *)other {
  [super copyAllFieldsTo:other];
  other->autoCreateTitlePane_ = autoCreateTitlePane_;
  other->ccContent_ = ccContent_;
  other->ccMenubar_ = ccMenubar_;
  other->ccStatusbar_ = ccStatusbar_;
  other->ccTitlebar_ = ccTitlebar_;
  other->ccToolbar_ = ccToolbar_;
  other->combineMenuBarAndTitle_ = combineMenuBarAndTitle_;
  other->firstTime_ = firstTime_;
  other->icon_ = icon_;
  other->menuBar_ = menuBar_;
  other->statusBar_ = statusBar_;
  other->title_ = title_;
  other->toolbarHolder_ = toolbarHolder_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createDialogTitleBarWithRAREiWidget:withRAREiActionListener:", NULL, "LRARETitlePane", 0x1, NULL },
    { "getContent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getEventListenerList", NULL, "LRAREEventListenerList", 0x1, NULL },
    { "getInnerBoundsWithRAREUIRectangle:", NULL, "LRAREUIRectangle", 0x1, NULL },
    { "getInnerSizeWithRAREUIDimension:", NULL, "LRAREUIDimension", 0x1, NULL },
    { "getMenuBar", NULL, "LRAREiPlatformMenuBar", 0x1, NULL },
    { "getStatusBar", NULL, "LRAREiStatusBar", 0x1, NULL },
    { "getTitle", NULL, "LNSString", 0x1, NULL },
    { "getTitlePaneWithBoolean:", NULL, "LRARETitlePane", 0x1, NULL },
    { "getToolBarHolder", NULL, "LRAREiToolBarHolder", 0x1, NULL },
    { "isAutoCreateTitlePane", NULL, "Z", 0x1, NULL },
    { "isCombineMenuBarAndTitle", NULL, "Z", 0x1, NULL },
    { "createPaneFormLayout", NULL, "LRAREFormLayout", 0x9, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "ccTitlebar_", NULL, 0x0, "LRARECellConstraints" },
    { "ccMenubar_", NULL, 0x0, "LRARECellConstraints" },
    { "ccToolbar_", NULL, 0x0, "LRARECellConstraints" },
    { "ccStatusbar_", NULL, 0x0, "LRARECellConstraints" },
    { "ccContent_", NULL, 0x0, "LRARECellConstraints" },
    { "icon_", NULL, 0x0, "LRAREiPlatformIcon" },
    { "title_", NULL, 0x0, "LNSString" },
    { "statusBar_", NULL, 0x0, "LRAREiStatusBar" },
    { "menuBar_", NULL, 0x0, "LRAREiPlatformMenuBar" },
    { "toolbarHolder_", NULL, 0x0, "LRAREiToolBarHolder" },
  };
  static J2ObjcClassInfo _RAREWindowPane = { "WindowPane", "com.appnativa.rare.ui", NULL, 0x1, 13, methods, 10, fields, 0, NULL};
  return &_RAREWindowPane;
}

@end
