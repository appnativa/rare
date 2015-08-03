//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/widget/LabelWidget.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/apple/ui/view/LabelView.h"
#include "com/appnativa/rare/spot/Label.h"
#include "com/appnativa/rare/ui/ActionComponent.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponentFactory.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/LabelWidget.h"
#include "com/appnativa/rare/widget/aWidget.h"

@implementation RARELabelWidget

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [super initWithRAREiContainer:parent];
}

- (id<RAREiActionComponent>)createActionComponentWithRARESPOTLabel:(RARESPOTLabel *)cfg {
  return [[RAREActionComponent alloc] initWithRAREView:[((id<RAREiPlatformComponentFactory>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getComponentCreator])) getLabelWithRAREiWidget:[self getViewer] withRARESPOTLabel:cfg]];
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createActionComponentWithRARESPOTLabel:", NULL, "LRAREiActionComponent", 0x4, NULL },
  };
  static J2ObjcClassInfo _RARELabelWidget = { "LabelWidget", "com.appnativa.rare.widget", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RARELabelWidget;
}

@end
