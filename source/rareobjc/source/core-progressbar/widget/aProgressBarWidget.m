//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-progressbar/com/appnativa/rare/widget/aProgressBarWidget.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/spot/ProgressBar.h"
#include "com/appnativa/rare/spot/Slider.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/BorderPanel.h"
#include "com/appnativa/rare/ui/Location.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iProgressBar.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/widget/ProgressBarWidget.h"
#include "com/appnativa/rare/widget/aProgressBarWidget.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/util/SNumber.h"
#include "java/lang/Integer.h"

@implementation RAREaProgressBarWidget

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  if (self = [super initWithRAREiContainer:parent]) {
    labelSide_ = [RARELocationEnum AUTO];
    widgetType_ = [RAREiWidget_WidgetTypeEnum ProgressBar];
    isSubmittable__ = NO;
  }
  return self;
}

- (void)clearContents {
  [super clearContents];
  if (progressLabel_ != nil) {
    [progressLabel_ setVisibleWithBoolean:NO];
  }
}

- (void)configureWithRARESPOTWidget:(RARESPOTWidget *)cfg {
  [self configureExWithRARESPOTProgressBar:(RARESPOTProgressBar *) check_class_cast(cfg, [RARESPOTProgressBar class])];
  [self fireConfigureEventWithRARESPOTWidget:cfg withNSString:[RAREiConstants EVENT_CONFIGURE]];
}

+ (RAREProgressBarWidget *)createWithRAREiContainer:(id<RAREiContainer>)parent
                                 withRARESPOTSlider:(RARESPOTSlider *)cfg {
  RAREProgressBarWidget *widget = [[RAREProgressBarWidget alloc] initWithRAREiContainer:parent];
  [widget configureWithRARESPOTWidget:cfg];
  return widget;
}

- (void)dispose {
  [super dispose];
  if (progressLabel_ != nil) {
    [progressLabel_ dispose];
  }
  progressLabel_ = nil;
  progressBar_ = nil;
}

- (void)reset {
  if (progressLabel_ != nil) {
    [progressLabel_ setVisibleWithBoolean:NO];
  }
}

- (void)setProgressTextWithNSString:(NSString *)value {
  if (progressLabel_ != nil) {
    if ((value == nil) || ([value sequenceLength] == 0)) {
      [progressLabel_ setTextWithJavaLangCharSequence:@""];
      [progressLabel_ setVisibleWithBoolean:NO];
    }
    else {
      [progressLabel_ setTextWithJavaLangCharSequence:value];
      [progressLabel_ setVisibleWithBoolean:showText_];
    }
  }
}

- (void)setValueWithInt:(int)value {
  [((id<RAREiProgressBar>) nil_chk(progressBar_)) setValueWithInt:value];
}

- (void)setValueWithId:(id)value {
  if ([value isKindOfClass:[NSNumber class]]) {
    [((id<RAREiProgressBar>) nil_chk(progressBar_)) setValueWithInt:[((NSNumber *) check_class_cast(value, [NSNumber class])) intValue]];
  }
  else {
    NSString *s = nil;
    if ([value isKindOfClass:[NSString class]]) {
      s = (NSString *) check_class_cast(value, [NSString class]);
    }
    else if (value != nil) {
      s = [value description];
    }
    [((id<RAREiProgressBar>) nil_chk(progressBar_)) setValueWithInt:[RAREUTSNumber intValueWithNSString:s]];
  }
}

- (id)getSelection {
  return [JavaLangInteger valueOfWithInt:[((id<RAREiProgressBar>) nil_chk(progressBar_)) getValue]];
}

- (id)getValue {
  return [JavaLangInteger valueOfWithInt:[((id<RAREiProgressBar>) nil_chk(progressBar_)) getValue]];
}

- (double)getValueAsDouble {
  return [((id<RAREiProgressBar>) nil_chk(progressBar_)) getValue];
}

- (BOOL)isHorizontal {
  return YES;
}

- (void)setIndeterminateWithBoolean:(BOOL)indeterminate {
  [((id<RAREiProgressBar>) nil_chk(progressBar_)) setIndeterminateWithBoolean:indeterminate];
}

- (void)setOrientationWithBoolean:(BOOL)horizontal {
}

- (int)getValueAsInt {
  return [((id<RAREiProgressBar>) nil_chk(progressBar_)) getValue] * 100;
}

- (id<RAREiProgressBar>)createProgressBarWithRARESPOTProgressBar:(RARESPOTProgressBar *)cfg {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)configureExWithRARESPOTProgressBar:(RARESPOTProgressBar *)cfg {
  showText_ = [((SPOTBoolean *) nil_chk(((RARESPOTProgressBar *) nil_chk(cfg))->showText_)) booleanValue];
  progressBar_ = [self createProgressBarWithRARESPOTProgressBar:cfg];
  id<RAREiPlatformComponent> c = [((id<RAREiProgressBar>) nil_chk(progressBar_)) getComponent];
  dataComponent_ = formComponent_ = c;
  RAREBorderPanel *panel = nil;
  if (showText_) {
    panel = [[RAREBorderPanel alloc] initWithRAREiWidget:self];
    [panel setCenterViewWithRAREiPlatformComponent:c];
    formComponent_ = panel;
  }
  [self configureWithRARESPOTWidget:cfg withBoolean:YES withBoolean:NO withBoolean:YES withBoolean:YES];
  if (panel != nil) {
    progressLabel_ = [RAREaPlatformHelper createLabelWithRAREiPlatformComponent:panel];
    [((id<RAREiActionComponent>) nil_chk(progressLabel_)) setAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:[RARERenderableDataItem_HorizontalAlignEnum CENTER] withRARERenderableDataItem_VerticalAlignEnum:[RARERenderableDataItem_VerticalAlignEnum CENTER]];
    [progressLabel_ setMarginWithRAREUIInsets:[[RAREUIInsets alloc] initWithFloat:2]];
    if ([((SPOTPrintableString *) nil_chk(cfg->progressText_)) getValue] != nil) {
      [self setProgressTextWithNSString:[cfg->progressText_ getValue]];
    }
    if ([((id<RAREiPlatformComponent>) nil_chk(c)) getFontEx] != nil) {
      [progressLabel_ setFontWithRAREUIFont:[c getFontEx]];
    }
    if ([c getForegroundEx] != nil) {
      [progressLabel_ setForegroundWithRAREUIColor:[c getForegroundEx]];
    }
  }
  if ([((SPOTBoolean *) nil_chk(cfg->indeterminate_)) booleanValue]) {
    [progressBar_ setIndeterminateWithBoolean:YES];
  }
  NSString *s = [((SPOTPrintableString *) nil_chk(cfg->graphicSize_)) getValue];
  if (s != nil) {
    int n = [RAREScreenUtils toPlatformPixelWidthWithNSString:s withRAREiPlatformComponent:panel withFloat:100];
    if (n > 0) {
      [progressBar_ setGraphicSizeWithInt:n];
    }
  }
  [self setLabelSideWithRARELocationEnum:[RARELocationEnum AUTO]];
}

- (RARELocationEnum *)getLabelSide {
  return labelSide_;
}

- (void)setLabelSideWithRARELocationEnum:(RARELocationEnum *)labelSide {
  if (labelSide == nil) {
    labelSide = [RARELocationEnum AUTO];
  }
  self->labelSide_ = labelSide;
  if (showText_) {
    RAREBorderPanel *panel = (RAREBorderPanel *) check_class_cast(formComponent_, [RAREBorderPanel class]);
    ;
    [((RAREBorderPanel *) nil_chk(panel)) removeWithRAREiPlatformComponent:progressLabel_];
    switch ([labelSide ordinal]) {
      case RARELocation_BOTTOM:
      [panel setBottomViewWithRAREiPlatformComponent:progressLabel_];
      break;
      case RARELocation_TOP:
      [panel setTopViewWithRAREiPlatformComponent:progressLabel_];
      break;
      case RARELocation_LEFT:
      [panel setRightViewWithRAREiPlatformComponent:progressLabel_];
      break;
      default:
      [panel setRightViewWithRAREiPlatformComponent:progressLabel_];
      break;
    }
  }
}

- (void)copyAllFieldsTo:(RAREaProgressBarWidget *)other {
  [super copyAllFieldsTo:other];
  other->labelSide_ = labelSide_;
  other->progressBar_ = progressBar_;
  other->progressLabel_ = progressLabel_;
  other->showText_ = showText_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createWithRAREiContainer:withRARESPOTSlider:", NULL, "LRAREProgressBarWidget", 0x9, NULL },
    { "getSelection", NULL, "LNSObject", 0x1, NULL },
    { "getValue", NULL, "LNSObject", 0x1, NULL },
    { "isHorizontal", NULL, "Z", 0x1, NULL },
    { "createProgressBarWithRARESPOTProgressBar:", NULL, "LRAREiProgressBar", 0x404, NULL },
    { "configureExWithRARESPOTProgressBar:", NULL, "V", 0x4, NULL },
    { "getLabelSide", NULL, "LRARELocationEnum", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "progressBar_", NULL, 0x4, "LRAREiProgressBar" },
    { "progressLabel_", NULL, 0x4, "LRAREiActionComponent" },
    { "labelSide_", NULL, 0x4, "LRARELocationEnum" },
    { "showText_", NULL, 0x4, "Z" },
  };
  static J2ObjcClassInfo _RAREaProgressBarWidget = { "aProgressBarWidget", "com.appnativa.rare.widget", NULL, 0x401, 7, methods, 4, fields, 0, NULL};
  return &_RAREaProgressBarWidget;
}

@end
