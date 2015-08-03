//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/UITextIcon.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/platform/apple/ui/view/LabelView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/ActionComponent.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UITextIcon.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/painter/aUIPainter.h"
#include "java/lang/CharSequence.h"

@implementation RAREUITextIcon

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text {
  return [super initWithJavaLangCharSequence:text];
}

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
                   withRAREUIColor:(RAREUIColor *)fg {
  return [super initWithJavaLangCharSequence:text withRAREUIColor:fg];
}

- (id)initWithJavaLangCharSequence:(id<JavaLangCharSequence>)text
                   withRAREUIColor:(RAREUIColor *)fg
                    withRAREUIFont:(RAREUIFont *)font
           withRAREiPlatformBorder:(id<RAREiPlatformBorder>)border {
  return [super initWithJavaLangCharSequence:text withRAREUIColor:fg withRAREUIFont:font withRAREiPlatformBorder:border];
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height {
  RAREUIRectangle *rect = [RAREaUIPainter getRenderLocationWithId:[((id<RAREiPlatformGraphics>) nil_chk(g)) getComponent] withRARERenderSpaceEnum:renderSpace_ withRARERenderTypeEnum:renderType_ withFloat:x withFloat:y withFloat:width withFloat:height withFloat:((RAREUIDimension *) nil_chk(size_))->width_ withFloat:size_->height_ withRAREUIRectangle:nil];
  [g translateWithFloat:((RAREUIRectangle *) nil_chk(rect))->x_ withFloat:rect->y_];
  [((RAREView *) nil_chk([((id<RAREiActionComponent>) nil_chk(label_)) getView])) paintWithRAREiPlatformGraphics:g];
  [g translateWithFloat:-rect->x_ withFloat:-rect->y_];
}

- (id<RAREiActionComponent>)createComponent {
  return [[RAREActionComponent alloc] initWithRAREView:[[RARELabelView alloc] init]];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createComponent", NULL, "LRAREiActionComponent", 0x4, NULL },
  };
  static J2ObjcClassInfo _RAREUITextIcon = { "UITextIcon", "com.appnativa.rare.ui", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_RAREUITextIcon;
}

@end
