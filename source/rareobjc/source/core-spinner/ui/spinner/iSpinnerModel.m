//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-spinner/com/appnativa/rare/ui/spinner/iSpinnerModel.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/ui/event/iChangeListener.h"
#include "com/appnativa/rare/ui/spinner/iSpinnerModel.h"


@interface RAREiSpinnerModel : NSObject
@end

@implementation RAREiSpinnerModel

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addChangeListenerWithRAREiChangeListener:", NULL, "V", 0x401, NULL },
    { "removeChangeListenerWithRAREiChangeListener:", NULL, "V", 0x401, NULL },
    { "setValueWithId:", NULL, "V", 0x401, NULL },
    { "getNextValue", NULL, "LNSObject", 0x401, NULL },
    { "getPreviousValue", NULL, "LNSObject", 0x401, NULL },
    { "getValue", NULL, "LNSObject", 0x401, NULL },
    { "isEditable", NULL, "Z", 0x401, NULL },
    { "isCircular", NULL, "Z", 0x401, NULL },
    { "fromStringWithNSString:", NULL, "LNSObject", 0x401, NULL },
    { "toStringWithId:", NULL, "LNSString", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREiSpinnerModel = { "iSpinnerModel", "com.appnativa.rare.ui.spinner", NULL, 0x201, 10, methods, 0, NULL, 0, NULL};
  return &_RAREiSpinnerModel;
}

@end
