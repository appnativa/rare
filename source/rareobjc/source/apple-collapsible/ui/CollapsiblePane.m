//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-collapsible/com/appnativa/rare/ui/CollapsiblePane.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/platform/apple/ui/view/BorderLayoutView.h"
#include "com/appnativa/rare/platform/apple/ui/view/LabelView.h"
#include "com/appnativa/rare/ui/ActionComponent.h"
#include "com/appnativa/rare/ui/CollapsiblePane.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/Container.h"
#include "com/appnativa/rare/ui/PainterUtils.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/aBorderPanel.h"
#include "com/appnativa/rare/ui/aCollapsiblePane.h"
#include "com/appnativa/rare/ui/event/ExpansionEvent.h"
#include "com/appnativa/rare/ui/event/MouseEvent.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"

@implementation RARECollapsiblePane

- (id)init {
  return [self initRARECollapsiblePaneWithNSString:nil withRAREiPlatformComponent:nil];
}

- (id)initRARECollapsiblePaneWithNSString:(NSString *)title
               withRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  if (self = [super init]) {
    eventObject_ = [[RAREExpansionEvent alloc] initWithId:self];
    toggleOnTitleSingleClick_ = [RAREPlatform isTouchDevice];
    [self initComponents];
    if (title != nil) {
      [self setTitleTextWithJavaLangCharSequence:title];
    }
    if (c != nil) {
      [self addWithRAREiPlatformComponent:c];
    }
  }
  return self;
}

- (id)initWithNSString:(NSString *)title
withRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  return [self initRARECollapsiblePaneWithNSString:title withRAREiPlatformComponent:c];
}

- (void)createAndAddTitleLabel {
  titleComponent_ = [[RARECollapsiblePane_TitleComponent alloc] initWithRARECollapsiblePane:self];
  [self setTopViewWithRAREiPlatformComponent:titleComponent_];
}

- (id<RAREiPlatformIcon>)createChevronIconWithBoolean:(BOOL)up {
  return [[RAREPainterUtils_ChevronIcon alloc] initWithRAREiPlatformComponent:self withBoolean:up];
}

- (id<RAREiPlatformIcon>)createTwistyIconWithBoolean:(BOOL)up {
  return [[RAREPainterUtils_TwistyIcon alloc] initWithRAREiPlatformComponent:self withBoolean:up];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createAndAddTitleLabel", NULL, "V", 0x4, NULL },
    { "createChevronIconWithBoolean:", NULL, "LRAREiPlatformIcon", 0x4, NULL },
    { "createTwistyIconWithBoolean:", NULL, "LRAREiPlatformIcon", 0x4, NULL },
  };
  static J2ObjcClassInfo _RARECollapsiblePane = { "CollapsiblePane", "com.appnativa.rare.ui", NULL, 0x1, 3, methods, 0, NULL, 0, NULL};
  return &_RARECollapsiblePane;
}

@end
@implementation RARECollapsiblePane_MouseListener

- (id)initWithRARECollapsiblePane_TitleComponent:(RARECollapsiblePane_TitleComponent *)title {
  if (self = [super init]) {
    self->title_ = title;
  }
  return self;
}

- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)e {
  if (!((RARECollapsiblePane *) nil_chk(((RARECollapsiblePane_TitleComponent *) nil_chk(title_))->pane_))->userControllable_) {
    return;
  }
  id<RAREiPlatformComponent> n = title_->iconOnLeft_ ? [title_ getRightView] : [title_ getLeftView];
  if (n != nil) {
    RAREUIRectangle *r = [[RAREUIRectangle alloc] initWithRAREUIPoint:[n getLocationOnScreen] withRAREUIDimension:[n getSize]];
    if ([r containsWithFloat:(int) [((RAREMouseEvent *) nil_chk(e)) getScreenX] withFloat:(int) [e getScreenY]]) {
      [title_->pane_ togglePane];
      return;
    }
  }
  if ((title_->pane_->toggleOnTitleSingleClick_ || ([((RAREMouseEvent *) nil_chk(e)) getClickCount] > 1))) {
    [title_->pane_ togglePane];
  }
  [super mousePressedWithRAREMouseEvent:e];
}

- (void)copyAllFieldsTo:(RARECollapsiblePane_MouseListener *)other {
  [super copyAllFieldsTo:other];
  other->title_ = title_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "title_", NULL, 0x0, "LRARECollapsiblePane_TitleComponent" },
  };
  static J2ObjcClassInfo _RARECollapsiblePane_MouseListener = { "MouseListener", "com.appnativa.rare.ui", "CollapsiblePane", 0x8, 0, NULL, 1, fields, 0, NULL};
  return &_RARECollapsiblePane_MouseListener;
}

@end
@implementation RARECollapsiblePane_TitleComponent

- (id)initWithRARECollapsiblePane:(RARECollapsiblePane *)pane {
  if (self = [super initWithId:[[RAREBorderLayoutView alloc] init]]) {
    RARELabelView *title = [[RARELabelView alloc] init];
    self->pane_ = pane;
    titleLabel_ = [[RAREActionComponent alloc] initWithRAREView:title];
    [self setCenterViewWithRAREiPlatformComponent:titleLabel_];
    [self addMouseListenerWithRAREiMouseListener:[[RARECollapsiblePane_MouseListener alloc] initWithRARECollapsiblePane_TitleComponent:self]];
  }
  return self;
}

- (id<RAREiActionComponent>)createIconComponent {
  return [[RAREActionComponent alloc] initWithRAREView:[[RARELabelView alloc] init]];
}

- (void)copyAllFieldsTo:(RARECollapsiblePane_TitleComponent *)other {
  [super copyAllFieldsTo:other];
  other->pane_ = pane_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithRARECollapsiblePane:", NULL, NULL, 0x0, NULL },
    { "createIconComponent", NULL, "LRAREiActionComponent", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "pane_", NULL, 0x0, "LRARECollapsiblePane" },
  };
  static J2ObjcClassInfo _RARECollapsiblePane_TitleComponent = { "TitleComponent", "com.appnativa.rare.ui", "CollapsiblePane", 0x8, 2, methods, 1, fields, 0, NULL};
  return &_RARECollapsiblePane_TitleComponent;
}

@end
