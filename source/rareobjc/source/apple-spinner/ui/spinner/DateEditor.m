//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-spinner/com/appnativa/rare/ui/spinner/DateEditor.java
//
//  Created by decoteaud on 9/15/15.
//

#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/ui/event/MouseEvent.h"
#include "com/appnativa/rare/ui/listener/iMouseListener.h"
#include "com/appnativa/rare/ui/spinner/DateEditor.h"
#include "com/appnativa/rare/ui/spinner/SpinnerDateModel.h"
#include "com/appnativa/rare/ui/spinner/iSpinnerModel.h"

@implementation RAREDateEditor

- (id)initWithRARESpinnerDateModel:(RARESpinnerDateModel *)model {
  if (self = [super initWithRAREiSpinnerModel:model]) {
    [((RARESpinnerDateModel *) nil_chk(model)) setIncrementFiedlCallbackWithRARESpinnerDateModel_iIncrementFiedlCallback:self];
  }
  return self;
}

- (void)setValueWithId:(id)value {
  NSString *v = [((id<RAREiSpinnerModel>) nil_chk(spinnerModel_)) toStringWithId:value];
  [((RAREView *) nil_chk(editorView_)) setTextWithJavaLangCharSequence:v];
}

- (int)getIncrementFieldWithRARESpinnerDateModel:(RARESpinnerDateModel *)model {
  int oldcf = [((RARESpinnerDateModel *) nil_chk(model)) getIncrementField];
  int cf = -1;
  if (incrementFieldDirty_) {
    cf = [self getCalendarField];
  }
  return (cf == -1) ? oldcf : cf;
}

- (int)getCalendarField {
  incrementFieldDirty_ = NO;
  return -1;
}

- (void)customizeEditor {
  [super customizeEditor];
  [((RAREView *) nil_chk(editorView_)) setMouseListenerWithRAREiMouseListener:self];
}

- (void)focusChangedWithId:(id)view
               withBoolean:(BOOL)hasFocus
                    withId:(id)otherView {
  [super focusChangedWithId:view withBoolean:hasFocus withId:otherView];
  if (hasFocus) {
    [self selectFeild];
  }
}

- (void)selectFeild {
}

- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e {
  incrementFieldDirty_ = YES;
  if (appMouseHandler_ != nil) {
    [appMouseHandler_ mouseReleasedWithRAREMouseEvent:e];
  }
}

- (void)mousePressedWithRAREMouseEvent:(RAREMouseEvent *)e {
  if (appMouseHandler_ != nil) {
    [appMouseHandler_ mousePressedWithRAREMouseEvent:e];
  }
}

- (void)mouseEnteredWithRAREMouseEvent:(RAREMouseEvent *)e {
  if (appMouseHandler_ != nil) {
    [appMouseHandler_ mouseEnteredWithRAREMouseEvent:e];
  }
}

- (void)mouseExitedWithRAREMouseEvent:(RAREMouseEvent *)e {
  if (appMouseHandler_ != nil) {
    [appMouseHandler_ mouseExitedWithRAREMouseEvent:e];
  }
}

- (void)pressCanceledWithRAREMouseEvent:(RAREMouseEvent *)e {
  [self mouseReleasedWithRAREMouseEvent:e];
}

- (BOOL)wantsLongPress {
  if (appMouseHandler_ != nil) {
    return [appMouseHandler_ wantsLongPress];
  }
  return NO;
}

- (void)copyAllFieldsTo:(RAREDateEditor *)other {
  [super copyAllFieldsTo:other];
  other->incrementFieldDirty_ = incrementFieldDirty_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getCalendarField", NULL, "I", 0x2, NULL },
    { "customizeEditor", NULL, "V", 0x4, NULL },
    { "selectFeild", NULL, "V", 0x2, NULL },
    { "wantsLongPress", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREDateEditor = { "DateEditor", "com.appnativa.rare.ui.spinner", NULL, 0x1, 4, methods, 0, NULL, 0, NULL};
  return &_RAREDateEditor;
}

@end
