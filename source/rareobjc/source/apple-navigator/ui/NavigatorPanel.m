//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-navigator/com/appnativa/rare/ui/NavigatorPanel.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#include "com/appnativa/rare/platform/apple/ui/view/CustomButtonView.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/platform/apple/ui/view/aView.h"
#include "com/appnativa/rare/ui/ActionComponent.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/NavigatorPanel.h"
#include "com/appnativa/rare/ui/UIAction.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/aNavigatorPanel.h"
#include "com/appnativa/rare/ui/event/MouseEvent.h"
#include "com/appnativa/rare/ui/iNavigatorPanel.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/widget/iWidget.h"

@implementation RARENavigatorPanel

- (id)initWithRAREiWidget:(id<RAREiWidget>)context
    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)backIcon {
  if (self = [super initWithId:[[RARENavigatorPanel_NavigatorView alloc] init]]) {
    RARENavigatorPanel_NavigatorView *p = (RARENavigatorPanel_NavigatorView *) check_class_cast(view_, [RARENavigatorPanel_NavigatorView class]);
    [((RARENavigatorPanel_NavigatorView *) nil_chk(p)) setLayoutManagerWithRAREiAppleLayoutManager:self];
    p->panel_ = self;
    [self initializePanelWithRAREiPlatformIcon:backIcon];
  }
  return self;
}

- (void)layoutWithRAREParentView:(RAREParentView *)view
                       withFloat:(float)width
                       withFloat:(float)height {
  [self layoutWithFloat:width withFloat:height];
}

- (id<RAREaNavigatorPanel_iButton>)createBackButtonWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  return [[RARENavigatorPanel_BackButton alloc] initWithRARENavigatorPanel:self withRAREiPlatformIcon:icon];
}

- (id<RAREaNavigatorPanel_iButton>)createButtonWithRAREUIAction:(RAREUIAction *)a {
  return [[RARENavigatorPanel_Button alloc] initWithRARENavigatorPanel:self withRAREUIAction:a];
}

- (id<RAREaNavigatorPanel_iButton>)createButtonWithNSString:(NSString *)text
                                      withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  return [[RARENavigatorPanel_Button alloc] initWithRARENavigatorPanel:self withNSString:text withRAREiPlatformIcon:icon];
}

- (void)repaintPanel {
  [self repaint];
}

- (void)invalidateLayout {
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createBackButtonWithRAREiPlatformIcon:", NULL, "LRAREaNavigatorPanel_iButton", 0x4, NULL },
    { "createButtonWithRAREUIAction:", NULL, "LRAREaNavigatorPanel_iButton", 0x4, NULL },
    { "createButtonWithNSString:withRAREiPlatformIcon:", NULL, "LRAREaNavigatorPanel_iButton", 0x4, NULL },
    { "repaintPanel", NULL, "V", 0x4, NULL },
  };
  static J2ObjcClassInfo _RARENavigatorPanel = { "NavigatorPanel", "com.appnativa.rare.ui", NULL, 0x1, 4, methods, 0, NULL, 0, NULL};
  return &_RARENavigatorPanel;
}

@end
@implementation RARENavigatorPanel_Button

- (id)initWithRARENavigatorPanel:(RARENavigatorPanel *)outer$
                    withNSString:(NSString *)text {
  return [self initRARENavigatorPanel_ButtonWithRARENavigatorPanel:outer$ withNSString:text withRAREiPlatformIcon:nil withRAREUIAction:nil];
}

- (id)initWithRARENavigatorPanel:(RARENavigatorPanel *)outer$
                withRAREUIAction:(RAREUIAction *)a {
  return [self initRARENavigatorPanel_ButtonWithRARENavigatorPanel:outer$ withNSString:nil withRAREiPlatformIcon:nil withRAREUIAction:a];
}

- (id)initWithRARENavigatorPanel:(RARENavigatorPanel *)outer$
                    withNSString:(NSString *)text
           withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  return [self initRARENavigatorPanel_ButtonWithRARENavigatorPanel:outer$ withNSString:text withRAREiPlatformIcon:icon withRAREUIAction:nil];
}

- (id)initRARENavigatorPanel_ButtonWithRARENavigatorPanel:(RARENavigatorPanel *)outer$
                                             withNSString:(NSString *)text
                                    withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                         withRAREUIAction:(RAREUIAction *)a {
  this$1_ = outer$;
  if (self = [super initWithRAREaNavigatorPanel:outer$]) {
    [self setViewWithRAREView:[[RARENavigatorPanel_ButtonViewEx alloc] initWithRARENavigatorPanel:outer$]];
    [self initialize__WithNSString:text withRAREiPlatformIcon:icon withRAREUIAction:a];
  }
  return self;
}

- (id)initWithRARENavigatorPanel:(RARENavigatorPanel *)outer$
                    withNSString:(NSString *)text
           withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                withRAREUIAction:(RAREUIAction *)a {
  return [self initRARENavigatorPanel_ButtonWithRARENavigatorPanel:outer$ withNSString:text withRAREiPlatformIcon:icon withRAREUIAction:a];
}

- (void)doClick {
  BOOL b = this$1_->alwaysFireAction_;
  @try {
    this$1_->alwaysFireAction_ = YES;
    [super doClick];
  }
  @finally {
    this$1_->alwaysFireAction_ = b;
  }
}

- (RAREUIRectangle *)getBoundsWithRAREUIRectangle:(RAREUIRectangle *)rect {
  return [((RAREView *) nil_chk(view_)) getBoundsWithRAREUIRectangle:rect];
}

- (BOOL)isPressed {
  return ((RARENavigatorPanel_ButtonViewEx *) nil_chk([self getView]))->isPressed_;
}

- (void)perfromClick {
  if (this$1_->panelType_ == [RAREiNavigatorPanel_PanelTypeEnum HIERARCHICAL]) {
    [this$1_ removeChildrenWithRAREaNavigatorPanel_iButton:self];
  }
  if ([self isSelected] && [this$1_ isToggle] && !this$1_->alwaysFireAction_) {
    return;
  }
  [self setSelectedWithBoolean:!selected_];
  [this$1_ repaintPanel];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithRARENavigatorPanel:withNSString:withRAREiPlatformIcon:withRAREUIAction:", NULL, NULL, 0x4, NULL },
    { "getBoundsWithRAREUIRectangle:", NULL, "LRAREUIRectangle", 0x1, NULL },
    { "isPressed", NULL, "Z", 0x1, NULL },
    { "perfromClick", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "this$1_", NULL, 0x1012, "LRARENavigatorPanel" },
  };
  static J2ObjcClassInfo _RARENavigatorPanel_Button = { "Button", "com.appnativa.rare.ui", "NavigatorPanel", 0x4, 4, methods, 1, fields, 0, NULL};
  return &_RARENavigatorPanel_Button;
}

@end
@implementation RARENavigatorPanel_BackButton

- (id)initWithRARENavigatorPanel:(RARENavigatorPanel *)outer$
           withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  this$2_ = outer$;
  if (self = [super initWithRARENavigatorPanel:outer$ withNSString:nil withRAREiPlatformIcon:icon]) {
    [((RARENavigatorPanel_ButtonViewEx *) check_class_cast([self getView], [RARENavigatorPanel_ButtonViewEx class])) setAsBackButton];
  }
  return self;
}

- (RAREUIDimension *)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size {
  id<RAREiPlatformIcon> icon = [self getIcon];
  if (size == nil) {
    size = [[RAREUIDimension alloc] init];
  }
  [((RAREUIDimension *) nil_chk(size)) setSizeWithInt:[((id<RAREiPlatformIcon>) nil_chk(icon)) getIconWidth] + 4 withInt:[icon getIconHeight] + 4];
  return size;
}

- (void)perfromClick {
  [this$2_ backup];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getPreferredSizeWithRAREUIDimension:", NULL, "LRAREUIDimension", 0x1, NULL },
    { "perfromClick", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "this$2_", NULL, 0x1012, "LRARENavigatorPanel" },
  };
  static J2ObjcClassInfo _RARENavigatorPanel_BackButton = { "BackButton", "com.appnativa.rare.ui", "NavigatorPanel", 0x0, 2, methods, 1, fields, 0, NULL};
  return &_RARENavigatorPanel_BackButton;
}

@end
@implementation RARENavigatorPanel_ButtonViewEx

- (id)initWithRARENavigatorPanel:(RARENavigatorPanel *)outer$ {
  this$0_ = outer$;
  if (self = [super init]) {
    [self setMouseListenerWithRAREiMouseListener:self];
    [self setMarginWithFloat:0 withFloat:0 withFloat:0 withFloat:0];
  }
  return self;
}

- (void)mouseEnteredWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)mouseExitedWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)e {
  if ([this$0_ isToggle] && (this$0_->selectedButton_ != nil) && ([this$0_->selectedButton_ getView] == self) && !this$0_->alwaysFireAction_) {
    return;
  }
  isPressed_ = YES;
  wasPressed_ = YES;
  if (isBack_) {
    [self revalidate];
  }
  else {
    [this$0_ repaintPanel];
  }
}

- (void)borderChangedWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)newBorder {
}

- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e {
  isPressed_ = NO;
  wasPressed_ = NO;
  if (isBack_) {
    [self revalidate];
  }
  else {
    [this$0_ repaintPanel];
  }
}

- (void)paintBackgroundWithRAREAppleGraphics:(RAREAppleGraphics *)g
                                withRAREView:(RAREView *)v
                         withRAREUIRectangle:(RAREUIRectangle *)rect {
  if (!isBack_) {
    return;
  }
  [this$0_ paintButtonWithRAREiPlatformGraphics:g withRAREUIRectangle:rect withBoolean:isPressed_];
  return;
}

- (void)pressCanceledWithRAREMouseEvent:(RAREMouseEvent *)e {
}

- (BOOL)wantsLongPress {
  return NO;
}

- (BOOL)isEnabled {
  if (![this$0_ isEnabled]) {
    return NO;
  }
  return [super isEnabled];
}

- (void)setAsBackButton {
  isBack_ = YES;
  [self setPaintHandlerEnabledWithBoolean:YES];
}

- (void)actionPerformed {
  [((RARENavigatorPanel_Button *) check_class_cast(component_, [RARENavigatorPanel_Button class])) perfromClick];
  [super actionPerformed];
}

- (void)copyAllFieldsTo:(RARENavigatorPanel_ButtonViewEx *)other {
  [super copyAllFieldsTo:other];
  other->isBack_ = isBack_;
  other->isPressed_ = isPressed_;
  other->wasPressed_ = wasPressed_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "wantsLongPress", NULL, "Z", 0x1, NULL },
    { "isEnabled", NULL, "Z", 0x1, NULL },
    { "setAsBackButton", NULL, "V", 0x0, NULL },
    { "actionPerformed", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRARENavigatorPanel" },
    { "isBack_", NULL, 0x0, "Z" },
    { "isPressed_", NULL, 0x0, "Z" },
    { "wasPressed_", NULL, 0x0, "Z" },
  };
  static J2ObjcClassInfo _RARENavigatorPanel_ButtonViewEx = { "ButtonViewEx", "com.appnativa.rare.ui", "NavigatorPanel", 0x0, 4, methods, 4, fields, 0, NULL};
  return &_RARENavigatorPanel_ButtonViewEx;
}

@end
@implementation RARENavigatorPanel_NavigatorView

- (id)init {
  if (self = [super initWithId:[RAREaView createAPView]]) {
    [self setPaintHandlerEnabledWithBoolean:YES];
    [self setUseMainLayerForPainterWithBoolean:NO];
  }
  return self;
}

- (void)paintBackgroundWithRAREAppleGraphics:(RAREAppleGraphics *)g
                                withRAREView:(RAREView *)v
                         withRAREUIRectangle:(RAREUIRectangle *)rect {
  [super paintBackgroundWithRAREAppleGraphics:g withRAREView:v withRAREUIRectangle:rect];
  [((RARENavigatorPanel *) nil_chk(panel_)) paintPanelWithRAREiPlatformGraphics:g withRAREUIRectangle:rect];
}

- (void)disposeEx {
  [super disposeEx];
  panel_ = nil;
}

- (void)copyAllFieldsTo:(RARENavigatorPanel_NavigatorView *)other {
  [super copyAllFieldsTo:other];
  other->panel_ = panel_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "disposeEx", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "panel_", NULL, 0x0, "LRARENavigatorPanel" },
  };
  static J2ObjcClassInfo _RARENavigatorPanel_NavigatorView = { "NavigatorView", "com.appnativa.rare.ui", "NavigatorPanel", 0x8, 1, methods, 1, fields, 0, NULL};
  return &_RARENavigatorPanel_NavigatorView;
}

@end