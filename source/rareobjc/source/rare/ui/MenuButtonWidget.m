//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/MenuButtonWidget.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/spot/ListBox.h"
#include "com/appnativa/rare/spot/PushButton.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/MenuButtonWidget.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIProperties.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/border/UIBalloonBorder.h"
#include "com/appnativa/rare/ui/border/UIEmptyBorder.h"
#include "com/appnativa/rare/ui/border/aUIBalloonBorder.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPopup.h"
#include "com/appnativa/rare/ui/painter/UIComponentPainter.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/viewer/aListViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/widget/aPushButtonWidget.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/spot/SPOTAny.h"
#include "java/util/List.h"

@implementation RAREMenuButtonWidget

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent
withRAREMenuButtonWidget_iPopulateCallback:(id<RAREMenuButtonWidget_iPopulateCallback>)cb {
  if (self = [super initWithRAREiContainer:parent]) {
    callback_ = cb;
    RARESPOTPushButton *cfg = [[RARESPOTPushButton alloc] init];
    [((RARESPOTPushButton_CButtonStyle *) nil_chk(cfg->buttonStyle_)) setValueWithInt:RARESPOTPushButton_CButtonStyle_toolbar];
    [((RARESPOTPushButton_CActionType *) nil_chk(cfg->actionType_)) setValueWithInt:RARESPOTPushButton_CActionType_popup_widget];
    [((SPOTAny *) nil_chk(cfg->popupWidget_)) setValueWithISPOTElement:[self createPopupMenuConfigurationWithInt:0 withNSString:@"Rare.MenuBar.actionBarMenu"]];
    (void) [cfg addBorderWithNSString:@"none"];
    [self configureWithRARESPOTWidget:cfg];
    RAREUIColor *c = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getColorWithNSString:@"Rare.MenuBar.actionBarMenu.Button.sharedBorderColor"];
    if (c != nil) {
      sharedBorderColor_ = c;
    }
    sharedBorderArc_ = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getIntWithNSString:@"Rare.MenuBar.actionBarMenu.Button.sharedBorderCornerArc" withInt:[RAREScreenUtils PLATFORM_PIXELS_6]];
    sharedBorderThickness_ = [((RAREUIProperties *) nil_chk([RAREPlatform getUIDefaults])) getFloatWithNSString:@"Rare.MenuBar.actionBarMenu.Button.sharedBorderThickness" withFloat:sharedBorderThickness_];
    [self setBorderWithRAREiPlatformBorder:[[RAREUIEmptyBorder alloc] initWithFloat:[RAREPlatform isTouchDevice] ? [RAREScreenUtils PLATFORM_PIXELS_6] : [RAREScreenUtils PLATFORM_PIXELS_2]]];
  }
  return self;
}

- (void)createPopupWidget {
  [super createPopupWidget];
  [self configureListWithBoolean:YES];
}

- (void)showPopupWidget {
  if (popupWidget_ != nil) {
    [self configureListWithBoolean:NO];
  }
  [super showPopupWidget];
}

- (void)setPopupBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)border {
  shareBorder_ = NO;
  sharedLineBorder_ = nil;
  if (popupPainter_ == nil) {
    popupPainter_ = [[RAREUIComponentPainter alloc] init];
  }
  [((id<RAREiPlatformComponentPainter>) nil_chk(popupPainter_)) setBorderWithRAREiPlatformBorder:border];
}

- (void)setUseActionListenerAsEventSourceWithBoolean:(BOOL)useActionListenerAsSource {
  self->useActionListenerAsSource_ = useActionListenerAsSource;
}

- (BOOL)isUseActionListenerAsEventSource {
  return useActionListenerAsSource_;
}

- (void)willShowPopupWithRAREiPopup:(id<RAREiPopup>)p
                withRAREUIRectangle:(RAREUIRectangle *)bounds {
  [super willShowPopupWithRAREiPopup:p withRAREUIRectangle:bounds];
  if ((popupPainter_ != nil) && ([(id) [popupPainter_ getBorder] isKindOfClass:[RAREUIBalloonBorder class]])) {
    RAREUIBalloonBorder *b = (RAREUIBalloonBorder *) check_class_cast([popupPainter_ getBorder], [RAREUIBalloonBorder class]);
    [((RAREUIBalloonBorder *) nil_chk(b)) setPeakOffsetWithFloat:[RAREScreenUtils PLATFORM_PIXELS_4]];
    float ps = [b getPeakSize];
    if (((RAREUIRectangle *) nil_chk(bounds))->x_ == 0) {
      if (bounds->y_ >= -ps) {
        [b setPeakLocationWithRAREaUIBalloonBorder_PeakLocationEnum:[RAREaUIBalloonBorder_PeakLocationEnum UL_TOP]];
      }
      else {
        [b setPeakLocationWithRAREaUIBalloonBorder_PeakLocationEnum:[RAREaUIBalloonBorder_PeakLocationEnum LL_BOTTOM]];
      }
    }
    else if (bounds->x_ > 0) {
      if (bounds->y_ > -(bounds->height_ / 2)) {
        [b setPeakLocationWithRAREaUIBalloonBorder_PeakLocationEnum:[RAREaUIBalloonBorder_PeakLocationEnum UL_LEFT]];
      }
      else {
        [b setPeakLocationWithRAREaUIBalloonBorder_PeakLocationEnum:[RAREaUIBalloonBorder_PeakLocationEnum LL_LEFT]];
      }
    }
    else {
      if (bounds->y_ < -ps) {
        [b setPeakLocationWithRAREaUIBalloonBorder_PeakLocationEnum:[RAREaUIBalloonBorder_PeakLocationEnum LR_BOTTOM]];
      }
      else {
        [b setPeakLocationWithRAREaUIBalloonBorder_PeakLocationEnum:[RAREaUIBalloonBorder_PeakLocationEnum UR_TOP]];
      }
    }
  }
}

- (void)configureListWithBoolean:(BOOL)first {
  RAREaListViewer *lv = (RAREaListViewer *) check_class_cast(popupWidget_, [RAREaListViewer class]);
  if (first) {
    [((RAREaListViewer *) nil_chk(lv)) addActionListenerWithRAREiActionListener:[[RAREMenuButtonWidget_$1 alloc] initWithRAREMenuButtonWidget:self]];
  }
  [((RAREaListViewer *) nil_chk(lv)) clear];
  [((id<RAREMenuButtonWidget_iPopulateCallback>) nil_chk(callback_)) addMenuItemsWithJavaUtilList:lv];
  [lv setVisibleRowCountWithInt:[lv size]];
}

- (void)copyAllFieldsTo:(RAREMenuButtonWidget *)other {
  [super copyAllFieldsTo:other];
  other->callback_ = callback_;
  other->useActionListenerAsSource_ = useActionListenerAsSource_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isUseActionListenerAsEventSource", NULL, "Z", 0x1, NULL },
    { "willShowPopupWithRAREiPopup:withRAREUIRectangle:", NULL, "V", 0x4, NULL },
    { "configureListWithBoolean:", NULL, "V", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "callback_", NULL, 0x0, "LRAREMenuButtonWidget_iPopulateCallback" },
  };
  static J2ObjcClassInfo _RAREMenuButtonWidget = { "MenuButtonWidget", "com.appnativa.rare.ui", NULL, 0x1, 3, methods, 1, fields, 0, NULL};
  return &_RAREMenuButtonWidget;
}

@end

@interface RAREMenuButtonWidget_iPopulateCallback : NSObject
@end

@implementation RAREMenuButtonWidget_iPopulateCallback

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addMenuItemsWithJavaUtilList:", NULL, "V", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREMenuButtonWidget_iPopulateCallback = { "iPopulateCallback", "com.appnativa.rare.ui", "MenuButtonWidget", 0x209, 1, methods, 0, NULL, 0, NULL};
  return &_RAREMenuButtonWidget_iPopulateCallback;
}

@end
@implementation RAREMenuButtonWidget_$1

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e {
  [this$0_ disposeOfPopup];
  [this$0_ resetButtonBorderAndBackground];
}

- (id)initWithRAREMenuButtonWidget:(RAREMenuButtonWidget *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREMenuButtonWidget" },
  };
  static J2ObjcClassInfo _RAREMenuButtonWidget_$1 = { "$1", "com.appnativa.rare.ui", "MenuButtonWidget", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREMenuButtonWidget_$1;
}

@end
