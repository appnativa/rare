//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-spinner/com/appnativa/rare/ui/spinner/iSpinner.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/event/iChangeListener.h"
#include "com/appnativa/rare/ui/spinner/iSpinner.h"
#include "com/appnativa/rare/ui/spinner/iSpinnerEditor.h"
#include "com/appnativa/rare/ui/spinner/iSpinnerModel.h"


@interface RAREiSpinner : NSObject
@end

@implementation RAREiSpinner

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addChangeListenerWithRAREiChangeListener:", NULL, "V", 0x401, NULL },
    { "commitEdit", NULL, "V", 0x401, NULL },
    { "removeChangeListenerWithRAREiChangeListener:", NULL, "V", 0x401, NULL },
    { "setButtonsSideBySideWithBoolean:", NULL, "V", 0x401, NULL },
    { "setButtonsVisibleWithBoolean:", NULL, "V", 0x401, NULL },
    { "setEditableWithBoolean:", NULL, "V", 0x401, NULL },
    { "setEditorWithRAREiSpinnerEditor:", NULL, "V", 0x401, NULL },
    { "setEnabledWithBoolean:", NULL, "V", 0x401, NULL },
    { "setModelWithRAREiSpinnerModel:", NULL, "V", 0x401, NULL },
    { "setUseDesktopStyleEditorWithBoolean:", NULL, "V", 0x401, NULL },
    { "setValueWithId:", NULL, "V", 0x401, NULL },
    { "setVisibleCharactersWithInt:", NULL, "V", 0x401, NULL },
    { "getEditor", NULL, "LRAREiSpinnerEditor", 0x401, NULL },
    { "swapButtonIcons", NULL, "V", 0x401, NULL },
    { "getModel", NULL, "LRAREiSpinnerModel", 0x401, NULL },
    { "getNextValue", NULL, "LNSObject", 0x401, NULL },
    { "getPreviousValue", NULL, "LNSObject", 0x401, NULL },
    { "getValue", NULL, "LNSObject", 0x401, NULL },
    { "getView", NULL, "LNSObject", 0x401, NULL },
    { "isButtonsSideBySide", NULL, "Z", 0x401, NULL },
    { "isButtonsVisible", NULL, "Z", 0x401, NULL },
    { "isEditable", NULL, "Z", 0x401, NULL },
    { "isUseDesktopStyleEditor", NULL, "Z", 0x401, NULL },
    { "setContinuousActionWithBoolean:", NULL, "V", 0x401, NULL },
    { "removeSelectedDataWithBoolean:", NULL, "LNSObject", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiSpinner = { "iSpinner", "com.appnativa.rare.ui.spinner", NULL, 0x201, 25, methods, 0, NULL, 0, NULL};
  return &_RAREiSpinner;
}

@end