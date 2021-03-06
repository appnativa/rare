//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/renderer/UILabelRenderer.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/platform/apple/ui/view/LabelView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/ActionComponent.h"
#include "com/appnativa/rare/ui/Column.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformRenderingComponent.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/ui/renderer/UILabelRenderer.h"
#include "java/lang/CharSequence.h"
#include "java/util/Map.h"

@implementation RAREUILabelRenderer

- (id)init {
  return [self initRAREUILabelRendererWithRARELabelView:[[RARELabelView alloc] init]];
}

- (id)initRAREUILabelRendererWithRARELabelView:(RARELabelView *)tv {
  return [super initWithRAREView:tv];
}

- (id)initWithRARELabelView:(RARELabelView *)tv {
  return [self initRAREUILabelRendererWithRARELabelView:tv];
}

- (id)createNewNativeView {
  return [RARELabelView createProxy];
}

- (void)dispose {
  [super dispose];
}

- (void)clearRenderer {
  [self setIconWithRAREiPlatformIcon:nil];
  [self setTextWithJavaLangCharSequence:@""];
  [((RAREView *) nil_chk(view_)) clearVisualState];
}

- (id<RAREiPlatformRenderingComponent>)newCopy {
  return [self setupNewCopyWithRAREUILabelRenderer:[[RAREUILabelRenderer alloc] init]];
}

- (id<RAREiPlatformRenderingComponent>)setupNewCopyWithRAREUILabelRenderer:(RAREUILabelRenderer *)r {
  ((RAREUILabelRenderer *) nil_chk(r))->columnWidth_ = columnWidth_;
  [r setWordWrapWithBoolean:[self isWordWrap]];
  return r;
}

- (void)prepareForReuseWithInt:(int)row
                       withInt:(int)column {
  [((RAREView *) nil_chk(view_)) setBackgroundColorExWithRAREUIColor:nil];
  [view_ clearVisualState];
}

- (void)setBackgroundWithRAREUIColor:(RAREUIColor *)bg {
  if ([((RAREView *) nil_chk(view_)) getComponentPainter] != nil) {
    [super setComponentPainterWithRAREiPlatformComponentPainter:nil];
  }
  [view_ setBackgroundColorExWithRAREUIColor:bg];
}

- (void)setColumnWidthWithInt:(int)width {
  if (width <= 0) {
    columnWidth_ = width;
  }
  else {
    columnWidth_ = 0;
    [((RARELabelView *) check_class_cast(view_, [RARELabelView class])) setPrefColumnWidthWithInt:width];
  }
}

- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth {
  maxWidth += columnWidth_;
  if (maxWidth < 0) {
    maxWidth = 0;
  }
  [super getPreferredSizeExWithRAREUIDimension:size withFloat:maxWidth];
}

- (void)setEnabledWithBoolean:(BOOL)enabled {
  [((RAREView *) nil_chk(view_)) setEnabledWithBoolean:enabled];
}

- (void)setNativeViewWithId:(id)proxy {
  [((RAREView *) nil_chk(view_)) setProxyWithId:proxy];
  [view_ clearVisualState];
}

- (void)setOptionsWithJavaUtilMap:(id<JavaUtilMap>)options {
}

- (void)setOrientationWithRARERenderableDataItem_OrientationEnum:(RARERenderableDataItem_OrientationEnum *)o {
}

- (void)setRenderingViewWithRAREView:(RAREView *)view {
  [super setViewWithRAREView:view];
}

- (id<RAREiPlatformComponent>)getComponent {
  return self;
}

- (id<RAREiPlatformComponent>)getComponentWithJavaLangCharSequence:(id<JavaLangCharSequence>)value
                                        withRARERenderableDataItem:(RARERenderableDataItem *)item {
  RARELabelView *v = (RARELabelView *) check_class_cast(view_, [RARELabelView class]);
  [((RARELabelView *) nil_chk(v)) setTextWithJavaLangCharSequence:(value == nil) ? @"" : ((id) value)];
  return self;
}

- (id<RAREiPlatformComponent>)getComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)list
                                                              withId:(id)value
                                          withRARERenderableDataItem:(RARERenderableDataItem *)item
                                                             withInt:(int)row
                                                         withBoolean:(BOOL)isSelected
                                                         withBoolean:(BOOL)hasFocus
                                                      withRAREColumn:(RAREColumn *)col
                                          withRARERenderableDataItem:(RARERenderableDataItem *)rowItem
                                                         withBoolean:(BOOL)handleAll {
  if (handleAll) {
    [RAREUtils setIconAndAlignmentWithRAREiPlatformRenderingComponent:self withRARERenderableDataItem:item withRARERenderableDataItem:nil withRAREColumn:nil withBoolean:isSelected withBoolean:NO withBoolean:NO withBoolean:YES withRAREiPlatformIcon:nil];
    [self setBorderWithRAREiPlatformBorder:[((RARERenderableDataItem *) nil_chk(item)) getBorder]];
    RAREUIFont *f = [item getFont];
    if (f == nil) {
      f = [((id<RAREiPlatformComponent>) nil_chk(list)) getFont];
    }
    [self setFontWithRAREUIFont:f];
    RAREUIColor *fg = [item getForeground];
    if (fg == nil) {
      fg = [((id<RAREiPlatformComponent>) nil_chk(list)) getForeground];
    }
    [self setForegroundWithRAREUIColor:fg];
  }
  id<JavaLangCharSequence> s;
  if ([value conformsToProtocol: @protocol(JavaLangCharSequence)]) {
    s = (id<JavaLangCharSequence>) check_protocol_cast(value, @protocol(JavaLangCharSequence));
  }
  else {
    s = (value == nil) ? @"" : [value description];
  }
  return [self getComponentWithJavaLangCharSequence:s withRARERenderableDataItem:item];
}

- (void)copyAllFieldsTo:(RAREUILabelRenderer *)other {
  [super copyAllFieldsTo:other];
  other->columnWidth_ = columnWidth_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createNewNativeView", NULL, "LNSObject", 0x1, NULL },
    { "newCopy", NULL, "LRAREiPlatformRenderingComponent", 0x1, NULL },
    { "setupNewCopyWithRAREUILabelRenderer:", NULL, "LRAREiPlatformRenderingComponent", 0x4, NULL },
    { "getPreferredSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
    { "getComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getComponentWithJavaLangCharSequence:withRARERenderableDataItem:", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getComponentWithRAREiPlatformComponent:withId:withRARERenderableDataItem:withInt:withBoolean:withBoolean:withRAREColumn:withRARERenderableDataItem:withBoolean:", NULL, "LRAREiPlatformComponent", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "columnWidth_", NULL, 0x0, "I" },
  };
  static J2ObjcClassInfo _RAREUILabelRenderer = { "UILabelRenderer", "com.appnativa.rare.ui.renderer", NULL, 0x1, 7, methods, 1, fields, 0, NULL};
  return &_RAREUILabelRenderer;
}

@end
