//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/ui/iPlatformComponentFactory.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/platform/apple/ui/view/CheckBoxView.h"
#include "com/appnativa/rare/platform/apple/ui/view/LabelView.h"
#include "com/appnativa/rare/platform/apple/ui/view/LineView.h"
#include "com/appnativa/rare/platform/apple/ui/view/ListView.h"
#include "com/appnativa/rare/platform/apple/ui/view/RadioButtonView.h"
#include "com/appnativa/rare/platform/apple/ui/view/TextAreaView.h"
#include "com/appnativa/rare/platform/apple/ui/view/TextFieldView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/spot/Bean.h"
#include "com/appnativa/rare/spot/CheckBox.h"
#include "com/appnativa/rare/spot/DocumentPane.h"
#include "com/appnativa/rare/spot/Label.h"
#include "com/appnativa/rare/spot/Line.h"
#include "com/appnativa/rare/spot/ListBox.h"
#include "com/appnativa/rare/spot/PasswordField.h"
#include "com/appnativa/rare/spot/PushButton.h"
#include "com/appnativa/rare/spot/RadioButton.h"
#include "com/appnativa/rare/spot/TextField.h"
#include "com/appnativa/rare/ui/iPlatformComponentFactory.h"
#include "com/appnativa/rare/ui/text/iPlatformTextEditor.h"
#include "com/appnativa/rare/widget/iWidget.h"


@interface RAREiPlatformComponentFactory : NSObject
@end

@implementation RAREiPlatformComponentFactory

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getBeanWithRAREiWidget:withRARESPOTBean:", NULL, "LNSObject", 0x401, NULL },
    { "getButtonWithRAREiWidget:withRARESPOTPushButton:", NULL, "LRAREView", 0x401, NULL },
    { "getCheckBoxWithRAREiWidget:withRARESPOTCheckBox:", NULL, "LRARECheckBoxView", 0x401, NULL },
    { "getDocumentPaneWithRAREiWidget:withRARESPOTDocumentPane:", NULL, "LRAREiPlatformTextEditor", 0x401, NULL },
    { "getLabelWithRAREiWidget:withRARESPOTLabel:", NULL, "LRARELabelView", 0x401, NULL },
    { "getLineWithRAREiWidget:withRARESPOTLine:", NULL, "LRARELineView", 0x401, NULL },
    { "getListWithRAREiWidget:withRARESPOTListBox:", NULL, "LRAREListView", 0x401, NULL },
    { "getPasswordTextFieldWithRAREiWidget:withRARESPOTPasswordField:", NULL, "LRARETextFieldView", 0x401, NULL },
    { "getRadioButtonWithRAREiWidget:withRARESPOTRadioButton:", NULL, "LRARERadioButtonView", 0x401, NULL },
    { "getTextAreaWithRAREiWidget:withRARESPOTTextField:", NULL, "LRARETextAreaView", 0x401, NULL },
    { "getTextFieldWithRAREiWidget:withRARESPOTTextField:", NULL, "LRARETextFieldView", 0x401, NULL },
    { "getToolbarButtonWithRAREiWidget:withRARESPOTPushButton:", NULL, "LRAREView", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiPlatformComponentFactory = { "iPlatformComponentFactory", "com.appnativa.rare.ui", NULL, 0x201, 12, methods, 0, NULL, 0, NULL};
  return &_RAREiPlatformComponentFactory;
}

@end
