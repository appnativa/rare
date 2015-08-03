//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/widget/LineWidget.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/apple/ui/view/LabelView.h"
#include "com/appnativa/rare/platform/apple/ui/view/LineView.h"
#include "com/appnativa/rare/spot/Line.h"
#include "com/appnativa/rare/ui/ActionComponent.h"
#include "com/appnativa/rare/ui/BorderPanel.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/aLineHelper.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponentFactory.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/LineWidget.h"
#include "com/appnativa/rare/widget/aLineWidget.h"
#include "com/appnativa/rare/widget/aWidget.h"

@implementation RARELineWidget

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [super initWithRAREiContainer:parent];
}

- (void)setHorizontalWithBoolean:(BOOL)horizontal {
  if ([(id) formComponent_ isKindOfClass:[RAREBorderPanel class]]) {
    [((RAREBorderPanel *) check_class_cast(formComponent_, [RAREBorderPanel class])) setHorizontalWithBoolean:horizontal];
  }
  [super setHorizontalWithBoolean:horizontal];
}

- (id<RAREiActionComponent>)createLabelWithNSString:(NSString *)text
                              withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                                        withBoolean:(BOOL)left {
  BOOL horiz = [self isHorizontal];
  RARELabelView *tv = [[RARELabelView alloc] init];
  [tv setTextWithJavaLangCharSequence:text];
  [tv setMarginWithFloat:2 withFloat:2 withFloat:2 withFloat:2];
  RAREActionComponent *l = [[RAREActionComponent alloc] initWithRAREView:tv];
  if (icon != nil) {
    [l setIconWithRAREiPlatformIcon:icon];
  }
  if (left) {
    [l setIconPositionWithRARERenderableDataItem_IconPositionEnum:horiz ? [RARERenderableDataItem_IconPositionEnum TRAILING] : [RARERenderableDataItem_IconPositionEnum BOTTOM_CENTER]];
  }
  else {
    [l setIconPositionWithRARERenderableDataItem_IconPositionEnum:horiz ? [RARERenderableDataItem_IconPositionEnum LEADING] : [RARERenderableDataItem_IconPositionEnum TOP_CENTER]];
  }
  return l;
}

- (RAREaLineHelper *)createLineHelperAndComponentsWithRARESPOTLine:(RARESPOTLine *)cfg {
  RARELineView *view = [((id<RAREiPlatformComponentFactory>) nil_chk([((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getComponentCreator])) getLineWithRAREiWidget:[self getViewer] withRARESPOTLine:cfg];
  dataComponent_ = formComponent_ = [[RAREComponent alloc] initWithRAREView:view];
  return [((RARELineView *) nil_chk(view)) getLineHelper];
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createLabelWithNSString:withRAREiPlatformIcon:withBoolean:", NULL, "LRAREiActionComponent", 0x4, NULL },
    { "createLineHelperAndComponentsWithRARESPOTLine:", NULL, "LRAREaLineHelper", 0x4, NULL },
  };
  static J2ObjcClassInfo _RARELineWidget = { "LineWidget", "com.appnativa.rare.widget", NULL, 0x1, 2, methods, 0, NULL, 0, NULL};
  return &_RARELineWidget;
}

@end