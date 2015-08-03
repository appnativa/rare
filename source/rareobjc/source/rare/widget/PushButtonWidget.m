//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/widget/PushButtonWidget.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/spot/Button.h"
#include "com/appnativa/rare/spot/PushButton.h"
#include "com/appnativa/rare/ui/ActionComponent.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponentFactory.h"
#include "com/appnativa/rare/ui/iPopup.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/viewer/ToolBarViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/PushButtonWidget.h"
#include "com/appnativa/rare/widget/aPlatformWidget.h"
#include "com/appnativa/rare/widget/aWidget.h"

@implementation RAREPushButtonWidget

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [super initWithRAREiContainer:parent];
}

- (void)configurePaintersWithRARESPOTPushButton:(RARESPOTPushButton *)cfg
                                        withInt:(int)buttonStyle {
  [super configurePaintersWithRARESPOTPushButton:cfg withInt:buttonStyle];
  id<RAREiPlatformComponentPainter> cp = [self getComponentPainter];
  if (cp != nil) {
    [cp updateForStateWithRAREaView:[self getDataView]];
  }
}

- (id<RAREiActionComponent>)createButtonWithRARESPOTButton:(RARESPOTButton *)cfg {
  RAREView *v;
  id<RAREiViewer> viewer = [self getViewer];
  RARESPOTPushButton *pb = (RARESPOTPushButton *) check_class_cast(cfg, [RARESPOTPushButton class]);
  if (([(id) viewer isKindOfClass:[RAREToolBarViewer class]]) && ![((RARESPOTPushButton_CButtonStyle *) nil_chk(((RARESPOTPushButton *) nil_chk(pb))->buttonStyle_)) spot_valueWasSet]) {
    v = [((id<RAREiPlatformComponentFactory>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getComponentCreator])) getToolbarButtonWithRAREiWidget:viewer withRARESPOTPushButton:pb];
  }
  else {
    v = [((id<RAREiPlatformComponentFactory>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getComponentCreator])) getButtonWithRAREiWidget:viewer withRARESPOTPushButton:pb];
  }
  return [[RAREActionComponent alloc] initWithRAREView:v];
}

- (void)setupSharedBorder {
  [super setupSharedBorder];
  [((RAREView *) nil_chk([self getContainerView])) setPaintHandlerEnabledWithBoolean:YES];
}

- (void)updatePopupComponentWithRAREiPopup:(id<RAREiPopup>)popup {
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "configurePaintersWithRARESPOTPushButton:withInt:", NULL, "V", 0x4, NULL },
    { "createButtonWithRARESPOTButton:", NULL, "LRAREiActionComponent", 0x4, NULL },
    { "setupSharedBorder", NULL, "V", 0x4, NULL },
    { "updatePopupComponentWithRAREiPopup:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREPushButtonWidget = { "PushButtonWidget", "com.appnativa.rare.widget", NULL, 0x1, 4, methods, 0, NULL, 0, NULL};
  return &_RAREPushButtonWidget;
}

@end