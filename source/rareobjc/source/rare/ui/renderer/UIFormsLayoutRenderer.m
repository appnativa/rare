//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/renderer/UIFormsLayoutRenderer.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/platform/apple/ui/view/FormsView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/aFormsPanel.h"
#include "com/appnativa/rare/ui/border/UICompoundBorder.h"
#include "com/appnativa/rare/ui/border/UIEmptyBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformRenderingComponent.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/ui/renderer/ListItemRenderer.h"
#include "com/appnativa/rare/ui/renderer/Renderers.h"
#include "com/appnativa/rare/ui/renderer/UIComponentRenderer.h"
#include "com/appnativa/rare/ui/renderer/UIFormsLayoutRenderer.h"
#include "com/appnativa/rare/ui/renderer/aFormsLayoutRenderer.h"
#include "com/appnativa/rare/ui/renderer/aListItemRenderer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/jgoodies/forms/layout/FormLayout.h"
#include "java/lang/Exception.h"
#include "java/util/Collection.h"
#include "java/util/Iterator.h"
#include "java/util/Map.h"

@implementation RAREUIFormsLayoutRenderer

- (id)init {
  return [super initWithRAREaFormsPanel:[[RAREUIFormsLayoutRenderer_$1 alloc] initWithRAREFormsView:[[RAREFormsView alloc] initWithRAREJGFormLayout:[[RAREJGFormLayout alloc] init]]]];
}

- (id)initWithNSString:(NSString *)columns
          withNSString:(NSString *)rows {
  if (self = [super initWithRAREaFormsPanel:[[RAREUIFormsLayoutRenderer_FormsPanelEx alloc] initWithRAREFormsView:([[RAREFormsView alloc] initWithRAREJGFormLayout:[[RAREJGFormLayout alloc] initWithNSString:columns withNSString:rows]])]]) {
    self->columns_ = columns;
    self->rows_ = rows;
  }
  return self;
}

- (id)createNewNativeView {
  return [RAREFormsView createProxy];
}

- (void)dispose {
  if (renderingComponents_ != nil) {
    for (id<RAREiPlatformRenderingComponent> __strong rc in nil_chk([renderingComponents_ values])) {
      [((id<RAREiPlatformRenderingComponent>) nil_chk(rc)) dispose];
    }
  }
  [super dispose];
}

- (void)clearRenderer {
  [super clearRenderer];
  [((RAREView *) nil_chk([((RAREaFormsPanel *) nil_chk(formsPanel_)) getView])) clearVisualState];
}

- (id<RAREiPlatformRenderingComponent>)newCopy {
  if ((columns_ == nil) && (rows_ == nil)) {
    return [[RAREUIFormsLayoutRenderer alloc] init];
  }
  RAREUIFormsLayoutRenderer *flr = [[RAREUIFormsLayoutRenderer alloc] initWithNSString:columns_ withNSString:rows_];
  (void) [self setupNewCopyWithRAREaFormsLayoutRenderer:flr];
  return flr;
}

- (void)prepareForReuseWithInt:(int)row
                       withInt:(int)column {
  [((RAREView *) nil_chk([((RAREaFormsPanel *) nil_chk(formsPanel_)) getView])) clearVisualState];
  id<JavaUtilIterator> it = [((id<JavaUtilCollection>) nil_chk([((id<JavaUtilMap>) nil_chk(renderingComponents_)) values])) iterator];
  while ([((id<JavaUtilIterator>) nil_chk(it)) hasNext]) {
    id<RAREiPlatformRenderingComponent> rc = ((id<RAREiPlatformRenderingComponent>) check_protocol_cast([it next], @protocol(RAREiPlatformRenderingComponent)));
    [((id<RAREiPlatformRenderingComponent>) nil_chk(rc)) prepareForReuseWithInt:row withInt:column];
  }
}

- (void)setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)ha
                      withRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)va {
}

- (void)setColumnWidthWithInt:(int)width {
}

- (void)setNativeViewWithId:(id)proxy {
  [((RAREView *) nil_chk([((RAREaFormsPanel *) nil_chk(formsPanel_)) getView])) setProxyWithId:proxy];
}

- (void)setRenderingViewWithRAREView:(RAREView *)view {
}

- (id<RAREiPlatformRenderingComponent>)createComponentRendererWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  return [[RAREUIComponentRenderer alloc] initWithRAREiPlatformComponent:c];
}

- (id<RAREiPlatformRenderingComponent>)createLabelRenderer {
  return [RARERenderers createLabelRenderer];
}

- (RAREaListItemRenderer *)createListItemRenderer {
  return [[RAREListItemRenderer alloc] init];
}

- (id<RAREiPlatformRenderingComponent>)createRendererWithNSString:(NSString *)className_ {
  return [RARERenderers createRendererWithNSString:className_];
}

- (void)copyAllFieldsTo:(RAREUIFormsLayoutRenderer *)other {
  [super copyAllFieldsTo:other];
  other->cborder_ = cborder_;
  other->paddingBorder_ = paddingBorder_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createNewNativeView", NULL, "LNSObject", 0x1, NULL },
    { "newCopy", NULL, "LRAREiPlatformRenderingComponent", 0x1, NULL },
    { "createComponentRendererWithRAREiPlatformComponent:", NULL, "LRAREiPlatformRenderingComponent", 0x4, NULL },
    { "createLabelRenderer", NULL, "LRAREiPlatformRenderingComponent", 0x4, NULL },
    { "createListItemRenderer", NULL, "LRAREaListItemRenderer", 0x4, NULL },
    { "createRendererWithNSString:", NULL, "LRAREiPlatformRenderingComponent", 0x4, "JavaLangException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "cborder_", NULL, 0x0, "LRAREUICompoundBorder" },
    { "paddingBorder_", NULL, 0x4, "LRAREUIEmptyBorder" },
  };
  static J2ObjcClassInfo _RAREUIFormsLayoutRenderer = { "UIFormsLayoutRenderer", "com.appnativa.rare.ui.renderer", NULL, 0x1, 6, methods, 2, fields, 0, NULL};
  return &_RAREUIFormsLayoutRenderer;
}

@end
@implementation RAREUIFormsLayoutRenderer_FormsPanelEx

- (id)init {
  return [super init];
}

- (id)initWithRAREFormsView:(RAREFormsView *)view {
  return [super initWithRAREFormsView:view];
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)context {
  return [super initWithRAREiWidget:context];
}

- (id)initWithId:(id)view {
  return [super initWithId:view];
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)context
     withRAREJGFormLayout:(RAREJGFormLayout *)layout {
  return [super initWithRAREiWidget:context withRAREJGFormLayout:layout];
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)context
                  withInt:(int)rows
                  withInt:(int)cols {
  return [super initWithRAREiWidget:context withInt:rows withInt:cols];
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)context
                  withInt:(int)rows
                  withInt:(int)cols
             withNSString:(NSString *)rspec
             withNSString:(NSString *)cspec {
  return [super initWithRAREiWidget:context withInt:rows withInt:cols withNSString:rspec withNSString:cspec];
}

- (void)setBoundsWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)w
                 withFloat:(float)h {
  [super setBoundsWithFloat:x withFloat:y withFloat:w withFloat:h];
}

- (RAREUIDimension *)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                               withFloat:(float)maxWidth {
  return [super getPreferredSizeWithRAREUIDimension:size withFloat:maxWidth];
}

- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth {
  [super getPreferredSizeExWithRAREUIDimension:size withFloat:maxWidth];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getPreferredSizeWithRAREUIDimension:withFloat:", NULL, "LRAREUIDimension", 0x1, NULL },
    { "getPreferredSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREUIFormsLayoutRenderer_FormsPanelEx = { "FormsPanelEx", "com.appnativa.rare.ui.renderer", "UIFormsLayoutRenderer", 0x8, 2, methods, 0, NULL, 0, NULL};
  return &_RAREUIFormsLayoutRenderer_FormsPanelEx;
}

@end
@implementation RAREUIFormsLayoutRenderer_$1

- (void)setBackgroundWithRAREUIColor:(RAREUIColor *)bg {
  if ([((RAREView *) nil_chk(view_)) getComponentPainter] != nil) {
    [super setComponentPainterWithRAREiPlatformComponentPainter:nil];
  }
  [view_ setBackgroundColorExWithRAREUIColor:bg];
}

- (id)initWithRAREFormsView:(RAREFormsView *)arg$0 {
  return [super initWithRAREFormsView:arg$0];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREUIFormsLayoutRenderer_$1 = { "$1", "com.appnativa.rare.ui.renderer", "UIFormsLayoutRenderer", 0x8000, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREUIFormsLayoutRenderer_$1;
}

@end
