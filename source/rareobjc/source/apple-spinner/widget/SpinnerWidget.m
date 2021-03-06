//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-spinner/com/appnativa/rare/widget/SpinnerWidget.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/spot/NumberSpinner.h"
#include "com/appnativa/rare/spot/Spinner.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/SpinnerComponent.h"
#include "com/appnativa/rare/ui/WidgetListener.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/spinner/iSpinner.h"
#include "com/appnativa/rare/ui/spinner/iSpinnerEditor.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/widget/SpinnerWidget.h"
#include "com/appnativa/rare/widget/aPlatformWidget.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSet.h"

@implementation RARESpinnerWidget

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [super initWithRAREiContainer:parent];
}

- (id<RAREiSpinner>)createSpinnerAndComponentsWithRARESPOTSpinner:(RARESPOTSpinner *)cfg {
  RARESpinnerComponent *spinner = [[RARESpinnerComponent alloc] init];
  dataComponent_ = formComponent_ = spinner;
  return spinner;
}

- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)listener {
  RAREWidgetListener *l = (RAREWidgetListener *) check_class_cast(listener, [RAREWidgetListener class]);
  [super initializeListenersWithRAREaWidgetListener:l];
  if ((l != nil) && [l isKeyEventsEnabled]) {
    [((RAREComponent *) nil_chk([self getDataComponentEx])) addKeyListenerWithRAREiKeyListener:l];
  }
}

- (void)registerEditorWithWidgetWithRAREiSpinnerEditor:(id<RAREiSpinnerEditor>)editor {
  if (editor != nil) {
    RAREComponent *c = (RAREComponent *) check_class_cast([editor getComponent], [RAREComponent class]);
    [self registerWithWidgetWithRAREiPlatformComponent:c];
    RAREWidgetListener *l = (RAREWidgetListener *) check_class_cast([self getWidgetListener], [RAREWidgetListener class]);
    if (l != nil) {
      if ([l isKeyEventsEnabled]) {
        [((RAREComponent *) nil_chk(c)) addKeyListenerWithRAREiKeyListener:l];
      }
      if ([l isEnabledWithNSString:[RAREiConstants EVENT_BLUR]] || [l isEnabledWithNSString:[RAREiConstants EVENT_BLUR]]) {
        [((RAREComponent *) nil_chk(c)) addFocusListenerWithRAREiFocusListener:l];
      }
    }
  }
}

+ (void)registerForUse {
  [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) registerWidgetClassWithNSString:[RAREPlatform getSPOTNameWithIOSClass:[IOSClass classWithClass:[RARESPOTSpinner class]]] withIOSClass:[IOSClass classWithClass:[RARESpinnerWidget class]]];
}

- (void)setupNumericSpinnerWithRAREiSpinner:(id<RAREiSpinner>)spinner
                  withRARESPOTNumberSpinner:(RARESPOTNumberSpinner *)cfg {
  if ([RAREPlatform isIOS] && [((SPOTBoolean *) nil_chk(((RARESPOTNumberSpinner *) nil_chk(cfg))->supportDecimalValues_)) booleanValue]) {
    [((id<RAREiSpinner>) nil_chk(spinner)) setUseDesktopStyleEditorWithBoolean:YES];
  }
  [super setupNumericSpinnerWithRAREiSpinner:spinner withRARESPOTNumberSpinner:cfg];
  if (![((id<RAREiSpinner>) nil_chk(spinner)) isUseDesktopStyleEditor]) {
    if (([((RARESPOTNumberSpinner *) nil_chk(cfg)) getBorders] != nil) || (([((SPOTPrintableString *) nil_chk(cfg->bgColor_)) getValue] != nil) && ![((NSString *) nil_chk([cfg->bgColor_ getValue])) isEqual:@"transparent"])) {
      [((RAREView *) nil_chk([((id<RAREiPlatformComponent>) nil_chk(dataComponent_)) getView])) makeTransparent];
      [((RAREView *) nil_chk([((id<RAREiPlatformComponent>) nil_chk([((id<RAREiSpinnerEditor>) nil_chk([spinner getEditor])) getComponent])) getView])) makeTransparent];
    }
  }
}

- (void)unregisterEditorWithWidgetWithRAREiSpinnerEditor:(id<RAREiSpinnerEditor>)editor {
  if (editor != nil) {
    RAREWidgetListener *l = (RAREWidgetListener *) check_class_cast([self getWidgetListener], [RAREWidgetListener class]);
    if (l != nil) {
      RAREComponent *c = (RAREComponent *) check_class_cast([editor getComponent], [RAREComponent class]);
      if (c != nil) {
        [c removeKeyListenerWithRAREiKeyListener:l];
        [c addFocusListenerWithRAREiFocusListener:l];
      }
    }
  }
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createSpinnerAndComponentsWithRARESPOTSpinner:", NULL, "LRAREiSpinner", 0x4, NULL },
    { "initializeListenersWithRAREaWidgetListener:", NULL, "V", 0x4, NULL },
    { "registerEditorWithWidgetWithRAREiSpinnerEditor:", NULL, "V", 0x4, NULL },
    { "registerForUse", NULL, "V", 0xc, NULL },
    { "setupNumericSpinnerWithRAREiSpinner:withRARESPOTNumberSpinner:", NULL, "V", 0x4, NULL },
    { "unregisterEditorWithWidgetWithRAREiSpinnerEditor:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcClassInfo _RARESpinnerWidget = { "SpinnerWidget", "com.appnativa.rare.widget", NULL, 0x1, 6, methods, 0, NULL, 0, NULL};
  return &_RARESpinnerWidget;
}

@end
