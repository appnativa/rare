//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-datechooser/com/appnativa/rare/widget/DateChooserWidget.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/spot/DateChooser.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/widget/DateChooserWidget.h"

@implementation RAREDateChooserWidget

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [super initWithRAREiContainer:parent];
}

+ (void)registerForUse {
  [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) registerWidgetClassWithNSString:[RAREPlatform getSPOTNameWithIOSClass:[IOSClass classWithClass:[RARESPOTDateChooser class]]] withIOSClass:[IOSClass classWithClass:[RAREDateChooserWidget class]]];
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "registerForUse", NULL, "V", 0xc, NULL },
  };
  static J2ObjcClassInfo _RAREDateChooserWidget = { "DateChooserWidget", "com.appnativa.rare.widget", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RAREDateChooserWidget;
}

@end
