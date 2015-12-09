//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/ios/com/appnativa/rare/ui/ComponentFactory.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/exception/ApplicationException.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/apple/ui/view/ButtonView.h"
#include "com/appnativa/rare/platform/apple/ui/view/CheckBoxView.h"
#include "com/appnativa/rare/platform/apple/ui/view/CustomButtonView.h"
#include "com/appnativa/rare/platform/apple/ui/view/LabelView.h"
#include "com/appnativa/rare/platform/apple/ui/view/LineView.h"
#include "com/appnativa/rare/platform/apple/ui/view/ListView.h"
#include "com/appnativa/rare/platform/apple/ui/view/MenuButtonView.h"
#include "com/appnativa/rare/platform/apple/ui/view/RadioButtonView.h"
#include "com/appnativa/rare/platform/apple/ui/view/SeparatorView.h"
#include "com/appnativa/rare/platform/apple/ui/view/SpacerView.h"
#include "com/appnativa/rare/platform/apple/ui/view/TextAreaView.h"
#include "com/appnativa/rare/platform/apple/ui/view/TextFieldView.h"
#include "com/appnativa/rare/platform/apple/ui/view/ToggleButtonView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/platform/apple/ui/view/WebView.h"
#include "com/appnativa/rare/spot/Bean.h"
#include "com/appnativa/rare/spot/CheckBox.h"
#include "com/appnativa/rare/spot/CollapsibleInfo.h"
#include "com/appnativa/rare/spot/DocumentPane.h"
#include "com/appnativa/rare/spot/Label.h"
#include "com/appnativa/rare/spot/Line.h"
#include "com/appnativa/rare/spot/ListBox.h"
#include "com/appnativa/rare/spot/PasswordField.h"
#include "com/appnativa/rare/spot/PushButton.h"
#include "com/appnativa/rare/spot/RadioButton.h"
#include "com/appnativa/rare/spot/TextField.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/ComponentFactory.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/iCollapsible.h"
#include "com/appnativa/rare/ui/text/iPlatformTextEditor.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "java/lang/Boolean.h"
#include "java/lang/Character.h"
#include "java/lang/Exception.h"
#include "java/lang/Throwable.h"
#include "java/net/URL.h"

@implementation RAREComponentFactory

- (void)setAppContextWithRAREiPlatformAppContext:(id<RAREiPlatformAppContext>)app {
  appContext_ = app;
}

- (id)getBeanWithRAREiWidget:(id<RAREiWidget>)context
            withRARESPOTBean:(RARESPOTBean *)cfg {
  id c = nil;
  @try {
    NSString *name = [((SPOTPrintableString *) nil_chk(((RARESPOTBean *) nil_chk(cfg))->name_)) getValue];
    if ([((NSString *) nil_chk([RAREiConstants MENU_SEPARATOR_NAME])) isEqual:name]) {
      return [[RARESeparatorView alloc] init];
    }
    if ([((NSString *) nil_chk([RAREiConstants MENU_EXPANSION_NAME])) isEqual:name]) {
      RAREComponent *comp = [[RAREComponent alloc] initWithRAREView:[[RARESpacerView alloc] initWithInt:0 withInt:0]];
      [comp putClientPropertyExWithNSString:[RAREiConstants MENU_EXPANSION_NAME] withId:[JavaLangBoolean getTRUE]];
      return comp;
    }
    name = [((SPOTPrintableString *) nil_chk(cfg->beanJAR_)) getValue];
    if ((name != nil) && ([name sequenceLength] > 0)) {
      [((id<RAREiPlatformAppContext>) nil_chk(appContext_)) addJarURLWithJavaNetURL:[((id<RAREiWidget>) nil_chk(context)) getURLWithNSString:name]];
    }
    name = [((SPOTPrintableString *) nil_chk(cfg->beanClass_)) getValue];
    if ((name != nil) && ([name sequenceLength] > 0)) {
      c = [RAREPlatform createObjectWithNSString:name];
    }
  }
  @catch (JavaLangThrowable *ex) {
    [RAREPlatform ignoreExceptionWithNSString:@"Bean Failure" withJavaLangThrowable:ex];
  }
  if (c == nil) {
    NSString *s = [((SPOTPrintableString *) nil_chk(((RARESPOTBean *) nil_chk(cfg))->failureMessage_)) getValue];
    if (s == nil) {
      s = [((id<RAREiPlatformAppContext>) nil_chk(appContext_)) getResourceAsStringWithNSString:@"Rare.runtime.text.beanFailure"];
    }
    RARELabelView *v = [[RARELabelView alloc] init];
    [v setTextWithJavaLangCharSequence:s];
    c = [[RAREComponent alloc] initWithRAREView:v];
  }
  return c;
}

- (RAREView *)getButtonWithRAREiWidget:(id<RAREiWidget>)context
                withRARESPOTPushButton:(RARESPOTPushButton *)cfg {
  if ([((RARESPOTPushButton_CButtonStyle *) nil_chk(((RARESPOTPushButton *) nil_chk(cfg))->buttonStyle_)) intValue] == RARESPOTPushButton_CButtonStyle_platform) {
    return [[RAREButtonView alloc] init];
  }
  if ([((RARESPOTPushButton_CActionType *) nil_chk(cfg->actionType_)) getValue] == RARESPOTPushButton_CActionType_popup_menu) {
    return [[RAREMenuButtonView alloc] init];
  }
  RARECustomButtonView *view;
  switch ([cfg->buttonStyle_ intValue]) {
    case RARESPOTPushButton_CButtonStyle_toggle_toolbar:
    view = [[RAREToggleButtonView alloc] init];
    break;
    case RARESPOTPushButton_CButtonStyle_hyperlink:
    case RARESPOTPushButton_CButtonStyle_hyperlink_always_underline:
    view = [[RARECustomButtonView alloc] init];
    [view setUnderlinedWithBoolean:YES];
    break;
    default:
    view = [[RARECustomButtonView alloc] init];
    break;
  }
  return view;
}

- (RARECheckBoxView *)getCheckBoxWithRAREiWidget:(id<RAREiWidget>)context
                            withRARESPOTCheckBox:(RARESPOTCheckBox *)cfg {
  RARECheckBoxView *v = [[RARECheckBoxView alloc] initWithBoolean:[((RARESPOTCheckBox_CStyle *) nil_chk(((RARESPOTCheckBox *) nil_chk(cfg))->style_)) intValue] == RARESPOTCheckBox_CStyle_on_off_switch];
  if ([cfg->style_ intValue] == RARESPOTCheckBox_CStyle_tri_state) {
    [v makeTriState];
  }
  return v;
}

- (id<RAREiCollapsible>)getCollapsibleWithRAREiWidget:(id<RAREiWidget>)context
                          withRARESPOTCollapsibleInfo:(RARESPOTCollapsibleInfo *)cfg {
  @try {
    if (collapsibleClass_ == nil) {
      collapsibleClass_ = [RAREPlatform loadClassWithNSString:@"com.appnativa.rare.ui.CollapsiblePane"];
    }
    id<RAREiCollapsible> pane = (id<RAREiCollapsible>) check_protocol_cast([((IOSClass *) nil_chk(collapsibleClass_)) newInstance], @protocol(RAREiCollapsible));
    [RAREUtils configureCollapsibleWithRAREiWidget:context withRAREiCollapsible:pane withRARESPOTCollapsibleInfo:cfg];
    return pane;
  }
  @catch (JavaLangException *e) {
    @throw [[RAREApplicationException alloc] initWithJavaLangThrowable:e];
  }
}

- (id<RAREiPlatformTextEditor>)getDocumentPaneWithRAREiWidget:(id<RAREiWidget>)widget
                                     withRARESPOTDocumentPane:(RARESPOTDocumentPane *)cfg {
  switch ([((RARESPOTDocumentPane_CStyle *) nil_chk(((RARESPOTDocumentPane *) nil_chk(cfg))->style_)) getValue]) {
    case RARESPOTDocumentPane_CStyle_html_editor:
    return [[RAREWebView alloc] initWithBoolean:YES];
    default:
    {
      RARETextAreaView *a = [[RARETextAreaView alloc] init];
      [a setAllowTextAttributesWithBoolean:YES];
      [a removeNativeBorder];
      return a;
    }
  }
}

- (RARELabelView *)getLabelWithRAREiWidget:(id<RAREiWidget>)context
                         withRARESPOTLabel:(RARESPOTLabel *)cfg {
  return [[RARELabelView alloc] init];
}

- (RARELineView *)getLineWithRAREiWidget:(id<RAREiWidget>)context
                        withRARESPOTLine:(RARESPOTLine *)cfg {
  return [[RARELineView alloc] init];
}

- (RAREListView *)getListWithRAREiWidget:(id<RAREiWidget>)context
                     withRARESPOTListBox:(RARESPOTListBox *)cfg {
  NSString *s = [((SPOTPrintableString *) nil_chk(((RARESPOTListBox *) nil_chk(cfg))->customProperties_)) getValue];
  BOOL grouped = NO;
  BOOL sectional = NO;
  if (s != nil) {
    int n = [s indexOfString:@"tableViewStyle"];
    int len = [s sequenceLength];
    if ((n != -1) && (n + 5 < len)) {
      for (int i = n; i < n + 5; i++) {
        unichar c = [s charAtWithInt:i];
        if ([JavaLangCharacter isLetterWithChar:c]) {
          if (([s indexOfString:@"Grouped" fromIndex:n] == i) || ([s indexOfString:@"grouped" fromIndex:n] == i)) {
            grouped = YES;
          }
          else if ([s indexOfString:@"PlainWithSections" fromIndex:n] == i) {
            grouped = YES;
          }
        }
      }
    }
  }
  RAREListView *list = [[RAREListView alloc] initWithBoolean:grouped];
  if ([((SPOTBoolean *) nil_chk(cfg->indexForFiltering_)) booleanValue]) {
    if ([@"false" isEqual:[cfg->indexForFiltering_ spot_getAttributeWithNSString:@"showIndexUI"]]) {
      [list setShowSectionIndexWithBoolean:NO];
    }
    else {
      [list setShowSectionIndexWithBoolean:YES];
    }
  }
  else if (sectional) {
    if ([@"false" isEqual:[cfg->indexForFiltering_ spot_getAttributeWithNSString:@"showIndexUI"]]) {
      [list setShowSectionIndexWithBoolean:NO];
    }
    else {
      [list setShowSectionIndexWithBoolean:YES];
    }
  }
  return list;
}

- (RARETextFieldView *)getPasswordTextFieldWithRAREiWidget:(id<RAREiWidget>)context
                                 withRARESPOTPasswordField:(RARESPOTPasswordField *)cfg {
  RARETextFieldView *view = [[RARETextFieldView alloc] init];
  [view setSecureEntryWithBoolean:YES];
  return view;
}

- (RARERadioButtonView *)getRadioButtonWithRAREiWidget:(id<RAREiWidget>)context
                               withRARESPOTRadioButton:(RARESPOTRadioButton *)cfg {
  RARERadioButtonView *v = [[RARERadioButtonView alloc] init];
  return v;
}

- (RARETextAreaView *)getTextAreaWithRAREiWidget:(id<RAREiWidget>)context
                           withRARESPOTTextField:(RARESPOTTextField *)cfg {
  RARETextAreaView *v = [[RARETextAreaView alloc] init];
  return v;
}

- (RARETextFieldView *)getTextFieldWithRAREiWidget:(id<RAREiWidget>)context
                             withRARESPOTTextField:(RARESPOTTextField *)cfg {
  RARETextFieldView *v = [[RARETextFieldView alloc] init];
  return v;
}

- (RAREView *)getToolbarButtonWithRAREiWidget:(id<RAREiWidget>)context
                       withRARESPOTPushButton:(RARESPOTPushButton *)cfg {
  return [self getButtonExWithRAREiWidget:context withRARESPOTPushButton:cfg withBoolean:YES];
}

- (RAREView *)getButtonExWithRAREiWidget:(id<RAREiWidget>)context
                  withRARESPOTPushButton:(RARESPOTPushButton *)cfg
                             withBoolean:(BOOL)toolbar {
  if ([((RARESPOTPushButton_CButtonStyle *) nil_chk(((RARESPOTPushButton *) nil_chk(cfg))->buttonStyle_)) intValue] == RARESPOTPushButton_CButtonStyle_platform) {
    return [[RAREButtonView alloc] init];
  }
  if ([((RARESPOTPushButton_CActionType *) nil_chk(cfg->actionType_)) getValue] == RARESPOTPushButton_CActionType_popup_menu) {
    return [[RAREMenuButtonView alloc] init];
  }
  RARECustomButtonView *view;
  switch ([cfg->buttonStyle_ intValue]) {
    case RARESPOTPushButton_CButtonStyle_toggle:
    view = [[RAREToggleButtonView alloc] init];
    break;
    case RARESPOTPushButton_CButtonStyle_toggle_toolbar:
    view = [[RAREToggleButtonView alloc] init];
    toolbar = YES;
    break;
    case RARESPOTPushButton_CButtonStyle_hyperlink:
    case RARESPOTPushButton_CButtonStyle_hyperlink_always_underline:
    view = [[RARECustomButtonView alloc] init];
    [view setUnderlinedWithBoolean:YES];
    break;
    default:
    view = [[RARECustomButtonView alloc] init];
    break;
  }
  if (toolbar) {
    [((RARECustomButtonView *) nil_chk(view)) makeTransparent];
  }
  return view;
}

- (id)init {
  return [super init];
}

- (void)copyAllFieldsTo:(RAREComponentFactory *)other {
  [super copyAllFieldsTo:other];
  other->appContext_ = appContext_;
  other->collapsibleClass_ = collapsibleClass_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getBeanWithRAREiWidget:withRARESPOTBean:", NULL, "LNSObject", 0x1, NULL },
    { "getButtonWithRAREiWidget:withRARESPOTPushButton:", NULL, "LRAREView", 0x1, NULL },
    { "getCheckBoxWithRAREiWidget:withRARESPOTCheckBox:", NULL, "LRARECheckBoxView", 0x1, NULL },
    { "getCollapsibleWithRAREiWidget:withRARESPOTCollapsibleInfo:", NULL, "LRAREiCollapsible", 0x1, NULL },
    { "getDocumentPaneWithRAREiWidget:withRARESPOTDocumentPane:", NULL, "LRAREiPlatformTextEditor", 0x1, NULL },
    { "getLabelWithRAREiWidget:withRARESPOTLabel:", NULL, "LRARELabelView", 0x1, NULL },
    { "getLineWithRAREiWidget:withRARESPOTLine:", NULL, "LRARELineView", 0x1, NULL },
    { "getListWithRAREiWidget:withRARESPOTListBox:", NULL, "LRAREListView", 0x1, NULL },
    { "getPasswordTextFieldWithRAREiWidget:withRARESPOTPasswordField:", NULL, "LRARETextFieldView", 0x1, NULL },
    { "getRadioButtonWithRAREiWidget:withRARESPOTRadioButton:", NULL, "LRARERadioButtonView", 0x1, NULL },
    { "getTextAreaWithRAREiWidget:withRARESPOTTextField:", NULL, "LRARETextAreaView", 0x1, NULL },
    { "getTextFieldWithRAREiWidget:withRARESPOTTextField:", NULL, "LRARETextFieldView", 0x1, NULL },
    { "getToolbarButtonWithRAREiWidget:withRARESPOTPushButton:", NULL, "LRAREView", 0x1, NULL },
    { "getButtonExWithRAREiWidget:withRARESPOTPushButton:withBoolean:", NULL, "LRAREView", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "appContext_", NULL, 0x0, "LRAREiPlatformAppContext" },
    { "collapsibleClass_", NULL, 0x0, "LIOSClass" },
  };
  static J2ObjcClassInfo _RAREComponentFactory = { "ComponentFactory", "com.appnativa.rare.ui", NULL, 0x1, 14, methods, 2, fields, 0, NULL};
  return &_RAREComponentFactory;
}

@end
