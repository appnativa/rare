//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-datechooser/com/appnativa/rare/ui/calendar/DateComboBox.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/FontUtils.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/aComboBoxComponent.h"
#include "com/appnativa/rare/ui/calendar/DateComboBox.h"
#include "com/appnativa/rare/ui/calendar/DatePanel.h"
#include "com/appnativa/rare/ui/calendar/DateViewManager.h"
#include "com/appnativa/rare/ui/calendar/iDateViewManager.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/ui/renderer/aListItemRenderer.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/lang/CharSequence.h"
#include "java/util/EventObject.h"

@implementation RAREDateComboBox

- (id)initWithRAREiWidget:(id<RAREiWidget>)widget {
  if (self = [super initWithRAREiWidget:widget]) {
    [self setEditableWithBoolean:NO];
    dateViewManager_ = [[RAREDateViewManager alloc] init];
    [dateViewManager_ addChangeListenerWithRAREiChangeListener:self];
    self->widget_ = widget;
  }
  return self;
}

- (void)configurationCompletedWithRAREaWidget:(RAREaWidget *)w
                           withRARESPOTWidget:(RARESPOTWidget *)cfg {
  [super configurationCompletedWithRAREaWidget:w withRARESPOTWidget:cfg];
  if ([RAREPlatform isIOS]) {
    [((RAREDateViewManager *) nil_chk(dateViewManager_)) setShowOKButtonWithBoolean:YES];
    if ((defaultPopupPainter_ != nil) && ([RAREPlatform getOsVersion] < 7)) {
      [defaultPopupPainter_ setBackgroundColorWithRAREUIColor:[[RAREUIColor alloc] initWithInt:41 withInt:42 withInt:57]];
    }
  }
}

- (void)dispose {
  [super dispose];
  dateViewManager_ = nil;
}

- (void)showPopup {
  if (showAsDialog_) {
    [((RAREDateViewManager *) nil_chk(dateViewManager_)) showDialogWithRAREiPlatformComponent:self];
  }
  else {
    [super showPopup];
  }
}

- (void)stateChangedWithJavaUtilEventObject:(JavaUtilEventObject *)e {
  [self setEditorValueWithJavaLangCharSequence:[((RAREDateViewManager *) nil_chk(dateViewManager_)) getValueAsString]];
  [self hidePopup];
}

- (void)setContent {
  [self setPopupContentWithRAREiPlatformComponent:[self getPanel]];
}

- (id<RAREiDateViewManager>)getDateViewManager {
  return dateViewManager_;
}

- (id<RAREiPlatformComponent>)getPanel {
  if (panel_ == nil) {
    panel_ = [[RAREDatePanel alloc] initWithRAREiWidget:widget_ withRAREaDateViewManager:dateViewManager_];
    [((RAREDateViewManager *) nil_chk(dateViewManager_)) setShowOKButtonWithBoolean:YES];
    [panel_ setContent];
  }
  return panel_;
}

- (id<RAREiWidget>)getPopupWidget {
  return nil;
}

- (void)getProposedPopupBoundsWithRAREUIRectangle:(RAREUIRectangle *)r {
  [RAREUtils getProposedPopupBoundsWithRAREUIRectangle:r withRAREiPlatformComponent:self withRAREUIDimension:[((id<RAREiPlatformComponent>) nil_chk(popupContent_)) getPreferredSize] withFloat:0 withRARERenderableDataItem_HorizontalAlignEnum:[RARERenderableDataItem_HorizontalAlignEnum RIGHT] withRAREiPlatformBorder:[self getPopupBorder] withBoolean:NO];
}

- (void)copyAllFieldsTo:(RAREDateComboBox *)other {
  [super copyAllFieldsTo:other];
  other->dateViewManager_ = dateViewManager_;
  other->panel_ = panel_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getDateViewManager", NULL, "LRAREiDateViewManager", 0x1, NULL },
    { "getPanel", NULL, "LRAREiPlatformComponent", 0x4, NULL },
    { "getPopupWidget", NULL, "LRAREiWidget", 0x4, NULL },
    { "getProposedPopupBoundsWithRAREUIRectangle:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "panel_", NULL, 0x0, "LRAREDatePanel" },
    { "dateViewManager_", NULL, 0x4, "LRAREDateViewManager" },
  };
  static J2ObjcClassInfo _RAREDateComboBox = { "DateComboBox", "com.appnativa.rare.ui.calendar", NULL, 0x1, 4, methods, 2, fields, 0, NULL};
  return &_RAREDateComboBox;
}

@end
@implementation RAREDateComboBox_DateComboBoxListHandler

- (id)initWithRAREDateComboBox:(RAREDateComboBox *)outer$ {
  this$0_ = outer$;
  return [super init];
}

- (void)clear {
  [super clear];
  [this$0_ setEditorValueWithJavaLangCharSequence:@""];
}

- (void)setSelectedIndexWithInt:(int)index {
}

- (RAREaListItemRenderer *)getItemRenderer {
  return nil;
}

- (id<RAREiPlatformComponent>)getListComponent {
  return [this$0_ getPanel];
}

- (RAREUIDimension *)getPreferredSize {
  return [((id<RAREiPlatformComponent>) nil_chk([self getListComponent])) getPreferredSize];
}

- (float)getPreferredWidth {
  return ((RAREUIDimension *) nil_chk([((id<RAREiPlatformComponent>) nil_chk([self getListComponent])) getPreferredSize]))->width_;
}

- (int)getRowHeight {
  return [RAREFontUtils getDefaultLineHeight];
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getItemRenderer", NULL, "LRAREaListItemRenderer", 0x1, NULL },
    { "getListComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getPreferredSize", NULL, "LRAREUIDimension", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREDateComboBox" },
  };
  static J2ObjcClassInfo _RAREDateComboBox_DateComboBoxListHandler = { "DateComboBoxListHandler", "com.appnativa.rare.ui.calendar", "DateComboBox", 0x4, 3, methods, 1, fields, 0, NULL};
  return &_RAREDateComboBox_DateComboBoxListHandler;
}

@end
