//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/util/EmptyTextItem.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/spot/EmptyText.h"
#include "com/appnativa/rare/spot/Font.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIColorHelper.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/util/EmptyTextItem.h"
#include "com/appnativa/rare/viewer/iViewer.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTPrintableString.h"

@implementation RAREEmptyTextItem

- (id)initWithRAREiWidget:(id<RAREiWidget>)context
    withRARESPOTEmptyText:(RARESPOTEmptyText *)et {
  if (self = [super init]) {
    if (et != nil) {
      text_ = [((SPOTPrintableString *) nil_chk(et->value_)) getValue];
      if (context != nil) {
        context = [RAREPlatform getContextRootViewer];
      }
      if ([et getFont] != nil) {
        font_ = [((RAREaWidget *) check_class_cast(context, [RAREaWidget class])) getFontWithRARESPOTFont:[et getFont]];
      }
      if ([((SPOTPrintableString *) nil_chk(et->fgColor_)) getValue] != nil) {
        foreground_ = [RAREUIColorHelper getColorWithNSString:[et->fgColor_ getValue]];
      }
    }
  }
  return self;
}

- (void)copyAllFieldsTo:(RAREEmptyTextItem *)other {
  [super copyAllFieldsTo:other];
  other->font_ = font_;
  other->foreground_ = foreground_;
  other->text_ = text_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "text_", NULL, 0x1, "LNSString" },
    { "font_", NULL, 0x1, "LRAREUIFont" },
    { "foreground_", NULL, 0x1, "LRAREUIColor" },
  };
  static J2ObjcClassInfo _RAREEmptyTextItem = { "EmptyTextItem", "com.appnativa.rare.util", NULL, 0x1, 0, NULL, 3, fields, 0, NULL};
  return &_RAREEmptyTextItem;
}

@end
