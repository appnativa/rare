//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-combobox/com/appnativa/rare/ui/ComboBoxComponent.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#include "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#include "com/appnativa/rare/platform/apple/ui/view/TextFieldView.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/platform/apple/ui/view/aView.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/ComboBoxComponent.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/aComboBoxComponent.h"
#include "com/appnativa/rare/ui/aListHandler.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformListDataModel.h"
#include "com/appnativa/rare/ui/iPlatformListHandler.h"
#include "com/appnativa/rare/ui/iPopup.h"
#include "com/appnativa/rare/ui/painter/UIComponentPainter.h"
#include "com/appnativa/rare/ui/text/iPlatformTextEditor.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"

@implementation RAREComboBoxComponent

- (id)init {
  return [super initWithId:[[RAREComboBoxComponent_ComboBoxView alloc] init]];
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)context {
  return [super initWithId:[[RAREComboBoxComponent_ComboBoxView alloc] init]];
}

- (void)configurationCompletedWithRAREaWidget:(RAREaWidget *)w
                           withRARESPOTWidget:(RARESPOTWidget *)cfg {
  [super configurationCompletedWithRAREaWidget:w withRARESPOTWidget:cfg];
  [((RAREView *) nil_chk([((id<RAREiPlatformComponent>) nil_chk([((RAREaWidget *) nil_chk(w)) getDataComponent])) getView])) stateChanged];
}

- (id<RAREiPlatformListHandler>)createListHandlerWithRAREiWidget:(id<RAREiWidget>)w
                                  withRAREiPlatformListDataModel:(id<RAREiPlatformListDataModel>)listModel {
  return [[RAREComboBoxComponent_ComboBoxListHandler alloc] initWithRAREiWidget:w withRAREaComboBoxComponent:self withRAREiPlatformListDataModel:listModel withBoolean:NO];
}

+ (id<RAREiPlatformListHandler>)createListHandlerWithRAREiWidget:(id<RAREiWidget>)context
                                  withRAREiPlatformListDataModel:(id<RAREiPlatformListDataModel>)model
                                                     withBoolean:(BOOL)forMenu {
  return [[RAREComboBoxComponent_ComboBoxListHandler alloc] initWithRAREiWidget:context withRAREaComboBoxComponent:nil withRAREiPlatformListDataModel:model withBoolean:forMenu];
}

- (void)setVisibleCharactersWithInt:(int)count {
  RARETextFieldView *e = (RARETextFieldView *) check_class_cast([((id<RAREiPlatformComponent>) nil_chk([((id<RAREiPlatformTextEditor>) nil_chk(editor_)) getComponent])) getView], [RARETextFieldView class]);
  [((RARETextFieldView *) nil_chk(e)) setPrefColumnCountWithInt:count];
  visibleCharacters_ = count;
}

- (id<RAREiPlatformTextEditor>)createEditor {
  RARETextFieldView *e = [[RARETextFieldView alloc] init];
  [e setSuggestionsEnabledWithBoolean:NO];
  [e addActionListenerWithRAREiActionListener:(RAREComboBoxComponent_ComboBoxView *) check_class_cast(view_, [RAREComboBoxComponent_ComboBoxView class])];
  [e makeTransparent];
  [e removeNativeBorder];
  [e setMarginWithFloat:0 withFloat:0 withFloat:0 withFloat:0];
  return e;
}

- (void)willShowPopupWithRAREiPopup:(id<RAREiPopup>)p
                withRAREUIRectangle:(RAREUIRectangle *)bounds {
  if (usingDefaultBorder_) {
    if (((RAREUIRectangle *) nil_chk(popupBounds_))->y_ < 0) {
      popupBounds_->y_ += [RAREScreenUtils PLATFORM_PIXELS_2];
    }
    else if (popupBounds_->y_ == 0) {
      popupBounds_->y_ -= [RAREScreenUtils PLATFORM_PIXELS_2];
    }
  }
}

- (void)updateForColorChange {
  [RAREUIComponentPainter updatePainterHolderModCountWithRAREPainterHolder:boxPainterHolder_];
  [RAREUIComponentPainter updatePainterHolderModCountWithRAREPainterHolder:buttonPainterHolder_];
}

- (void)copyAllFieldsTo:(RAREComboBoxComponent *)other {
  [super copyAllFieldsTo:other];
  other->visibleCharacters_ = visibleCharacters_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createListHandlerWithRAREiWidget:withRAREiPlatformListDataModel:", NULL, "LRAREiPlatformListHandler", 0x1, NULL },
    { "createListHandlerWithRAREiWidget:withRAREiPlatformListDataModel:withBoolean:", NULL, "LRAREiPlatformListHandler", 0x9, NULL },
    { "createEditor", NULL, "LRAREiPlatformTextEditor", 0x4, NULL },
    { "willShowPopupWithRAREiPopup:withRAREUIRectangle:", NULL, "V", 0x4, NULL },
    { "updateForColorChange", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "visibleCharacters_", NULL, 0x4, "I" },
  };
  static J2ObjcClassInfo _RAREComboBoxComponent = { "ComboBoxComponent", "com.appnativa.rare.ui", NULL, 0x1, 5, methods, 1, fields, 0, NULL};
  return &_RAREComboBoxComponent;
}

@end
@implementation RAREComboBoxComponent_ComboBoxListHandler

- (id)initWithRAREiWidget:(id<RAREiWidget>)w
withRAREaComboBoxComponent:(RAREaComboBoxComponent *)cb
withRAREiPlatformListDataModel:(id<RAREiPlatformListDataModel>)model
              withBoolean:(BOOL)forMenu {
  if (self = [super initWithRAREiWidget:w withRAREiPlatformListDataModel:model withBoolean:forMenu]) {
    self->cb_ = cb;
  }
  return self;
}

- (void)clear {
  [super clear];
  if (cb_ != nil) {
    [cb_ setEditorValueWithJavaLangCharSequence:@""];
  }
}

- (void)setSelectedIndexWithInt:(int)index {
  [super setSelectedIndexWithInt:index];
  if (cb_ != nil) {
    [cb_ setEditorValueWithRARERenderableDataItem:[self getSelectedItem]];
  }
}

- (void)copyAllFieldsTo:(RAREComboBoxComponent_ComboBoxListHandler *)other {
  [super copyAllFieldsTo:other];
  other->cb_ = cb_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "cb_", NULL, 0x0, "LRAREaComboBoxComponent" },
  };
  static J2ObjcClassInfo _RAREComboBoxComponent_ComboBoxListHandler = { "ComboBoxListHandler", "com.appnativa.rare.ui", "ComboBoxComponent", 0x9, 0, NULL, 1, fields, 0, NULL};
  return &_RAREComboBoxComponent_ComboBoxListHandler;
}

@end
@implementation RAREComboBoxComponent_ComboBoxView

- (id)init {
  if (self = [super initWithId:[RAREaView createAPView]]) {
    [self setLayoutManagerWithRAREiAppleLayoutManager:self];
    [self setPaintHandlerEnabledWithBoolean:YES];
  }
  return self;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e {
  RAREaComboBoxComponent *cb = (RAREaComboBoxComponent *) check_class_cast(component_, [RAREaComboBoxComponent class]);
  [((RAREaComboBoxComponent *) nil_chk(cb)) keyboardActionPerformedWithRAREActionEvent:e];
}

- (void)layoutWithRAREParentView:(RAREParentView *)view
                       withFloat:(float)width
                       withFloat:(float)height {
  [((RAREaComboBoxComponent *) check_class_cast([((RAREParentView *) nil_chk(view)) getComponent], [RAREaComboBoxComponent class])) layoutWithFloat:width withFloat:height];
}

- (void)updateChildrenForColorChange {
}

- (void)paintBackgroundWithRAREAppleGraphics:(RAREAppleGraphics *)g
                                withRAREView:(RAREView *)v
                         withRAREUIRectangle:(RAREUIRectangle *)rect {
  [super paintBackgroundWithRAREAppleGraphics:g withRAREView:v withRAREUIRectangle:rect];
  RAREaComboBoxComponent *cb = (RAREaComboBoxComponent *) check_class_cast(component_, [RAREaComboBoxComponent class]);
  [((RAREaComboBoxComponent *) nil_chk(cb)) paintIconWithRAREiPlatformGraphics:g];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREComboBoxComponent_ComboBoxView = { "ComboBoxView", "com.appnativa.rare.ui", "ComboBoxComponent", 0x8, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREComboBoxComponent_ComboBoxView;
}

@end
