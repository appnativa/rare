//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-combobox/com/appnativa/rare/widget/aComboBoxWidget.java
//
//  Created by decoteaud on 12/8/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/spot/ComboBox.h"
#include "com/appnativa/rare/spot/EmptyText.h"
#include "com/appnativa/rare/spot/Font.h"
#include "com/appnativa/rare/spot/GridCell.h"
#include "com/appnativa/rare/spot/ItemDescription.h"
#include "com/appnativa/rare/spot/Label.h"
#include "com/appnativa/rare/spot/Rectangle.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/Column.h"
#include "com/appnativa/rare/ui/FontUtils.h"
#include "com/appnativa/rare/ui/PainterUtils.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIColorHelper.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIFontHelper.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/UIScreen.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/aComboBoxComponent.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/event/iPopupMenuListener.h"
#include "com/appnativa/rare/ui/iActionComponent.h"
#include "com/appnativa/rare/ui/iActionable.h"
#include "com/appnativa/rare/ui/iComboBox.h"
#include "com/appnativa/rare/ui/iListHandler.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/iPlatformItemRenderer.h"
#include "com/appnativa/rare/ui/iPlatformListDataModel.h"
#include "com/appnativa/rare/ui/iPlatformListHandler.h"
#include "com/appnativa/rare/ui/iSpeechEnabler.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/painter/PainterHolder.h"
#include "com/appnativa/rare/ui/painter/iPlatformComponentPainter.h"
#include "com/appnativa/rare/ui/text/iPlatformTextEditor.h"
#include "com/appnativa/rare/util/DataParser.h"
#include "com/appnativa/rare/viewer/FormViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/widget/ComboBoxWidget.h"
#include "com/appnativa/rare/widget/aComboBoxWidget.h"
#include "com/appnativa/rare/widget/aListWidget.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTAny.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "com/appnativa/util/SNumber.h"
#include "java/lang/Character.h"
#include "java/lang/Exception.h"
#include "java/lang/Integer.h"
#include "java/lang/RuntimeException.h"
#include "java/util/Locale.h"
#include "java/util/Map.h"

@implementation RAREaComboBoxWidget

- (id)init {
  if (self = [super init]) {
    popupHorizontalAlignment_ = [RARERenderableDataItem_HorizontalAlignEnum AUTO];
  }
  return self;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  if (self = [super initWithRAREiContainer:parent]) {
    popupHorizontalAlignment_ = [RARERenderableDataItem_HorizontalAlignEnum AUTO];
  }
  return self;
}

- (void)addParsedRowWithRARERenderableDataItem:(RARERenderableDataItem *)row {
  if ([self isDisposed]) {
    return;
  }
  [self addExWithRARERenderableDataItem:row];
}

- (void)addPopupMenuListenerWithRAREiPopupMenuListener:(id<RAREiPopupMenuListener>)l {
  [((id<RAREiComboBox>) nil_chk([self getComboBox])) addPopupMenuListenerWithRAREiPopupMenuListener:l];
}

- (void)cancelPopup {
  [((id<RAREiComboBox>) nil_chk([self getComboBox])) cancelPopup];
}

- (void)configureWithRARESPOTWidget:(RARESPOTWidget *)cfg {
  [self configureExWithRARESPOTComboBox:(RARESPOTComboBox *) check_class_cast(cfg, [RARESPOTComboBox class])];
  [self fireConfigureEventWithRARESPOTWidget:cfg withNSString:[RAREiConstants EVENT_CONFIGURE]];
  [self handleDataURLWithRARESPOTWidget:cfg];
}

- (BOOL)isKeepSelectionVisible {
  return NO;
}

- (void)setKeepSelectionVisibleWithBoolean:(BOOL)keepSelectionVisible {
}

+ (RAREComboBoxWidget *)createWithRAREiContainer:(id<RAREiContainer>)parent
                            withRARESPOTComboBox:(RARESPOTComboBox *)cfg {
  RAREComboBoxWidget *widget = [[RAREComboBoxWidget alloc] initWithRAREiContainer:parent];
  [widget configureWithRARESPOTWidget:cfg];
  return widget;
}

- (void)dispose {
  [super dispose];
  if (listModel_ != nil) {
    [listModel_ dispose];
  }
  if (popupWidget_ != nil) {
    [popupWidget_ dispose];
  }
  listModel_ = nil;
  popupWidget_ = nil;
  popupHorizontalAlignment_ = nil;
}

- (id)removeSelectedDataWithBoolean:(BOOL)returnData {
  if (deletingAllowed_) {
    id<RAREiPlatformTextEditor> textEditor = [((id<RAREiComboBox>) nil_chk([self getComboBox])) getTextEditor];
    if (textEditor != nil) {
      int s = [textEditor getSelectionStart];
      int e = [textEditor getSelectionEnd];
      id o = nil;
      if (s != e) {
        if (returnData) {
          o = [textEditor getSelectionString];
        }
        [textEditor deleteSelection];
      }
      return o;
    }
  }
  return nil;
}

- (void)removePopupMenuListenerWithRAREiPopupMenuListener:(id<RAREiPopupMenuListener>)l {
  [((id<RAREiComboBox>) nil_chk([self getComboBox])) removePopupMenuListenerWithRAREiPopupMenuListener:l];
}

- (void)reset {
  [super reset];
  if ([self isHideWhenReset]) {
    [self setPopupVisibleWithBoolean:NO];
  }
}

- (void)rowChangedWithInt:(int)index {
  if (listModel_ != nil) {
    [listModel_ rowChangedWithInt:index];
    if (index == [self getSelectedIndex]) {
      [self setEditorValueWithId:[self getSelectedItem]];
    }
  }
}

- (void)rowChangedWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  if (listModel_ != nil) {
    int index = [self indexOfWithId:item];
    if (index != -1) {
      [self repaintRowWithInt:index];
    }
  }
}

- (void)rowsChangedWithInt:(int)firstRow
                   withInt:(int)lastRow {
  [((id<RAREiPlatformListDataModel>) nil_chk(listModel_)) rowsChangedWithInt:firstRow withInt:lastRow];
  int index = [self getSelectedIndex];
  if ((index >= firstRow) && (index <= lastRow)) {
    [self setEditorValueWithId:[self getSelectedItem]];
  }
}

- (void)setAlternatingRowColorWithRAREUIColor:(RAREUIColor *)color {
  [((id<RAREiPlatformListHandler>) nil_chk(listComponent_)) setAlternatingRowColorWithRAREUIColor:color];
}

- (void)setAutoHilightWithBoolean:(BOOL)autoHilight {
  [((id<RAREiPlatformListHandler>) nil_chk(listComponent_)) setAutoHilightWithBoolean:autoHilight];
}

- (void)setDataEventsEnabledWithBoolean:(BOOL)enabled {
  if (listModel_ != nil) {
    [listModel_ setEventsEnabledWithBoolean:enabled];
  }
}

- (void)setDeselectEventsDisabledWithBoolean:(BOOL)disabled {
  [((id<RAREiPlatformListHandler>) nil_chk(listComponent_)) setDeselectEventsDisabledWithBoolean:disabled];
}

- (void)setEditableWithBoolean:(BOOL)editable {
  [((id<RAREiComboBox>) nil_chk([self getComboBox])) setEditableWithBoolean:editable];
}

- (void)setEmptyFieldTextWithNSString:(NSString *)text {
  [((RAREaComboBoxComponent *) check_class_cast(dataComponent_, [RAREaComboBoxComponent class])) setEmptyFieldTextWithNSString:text];
}

- (void)setFromHTTPFormValueWithId:(id)value {
  {
    int n;
    switch (submitValueType_) {
      case RARESPOTComboBox_CSubmitValue_selected_index:
      if (value == nil) {
        [self setSelectedIndexWithInt:-1];
        return;
      }
      n = -1;
      if ([value isKindOfClass:[NSNumber class]]) {
        n = [((NSNumber *) check_class_cast(value, [NSNumber class])) intValue];
      }
      else {
        NSString *s = [nil_chk(value) description];
        if (([((NSString *) nil_chk(s)) sequenceLength] > 0) && [JavaLangCharacter isDigitWithChar:[s charAtWithInt:0]]) {
          n = [RAREUTSNumber intValueWithNSString:s];
        }
      }
      if ((n < -1) || (n >= [self size])) {
        n = -1;
      }
      [self setSelectedIndexWithInt:n];
      break;
      case RARESPOTComboBox_CSubmitValue_selected_linked_data:
      if (value != nil) {
        [self setSelectedIndexWithInt:[self indexOfLinkedDataWithId:value]];
      }
      else {
        [self setSelectedIndexWithInt:-1];
      }
      break;
      case RARESPOTComboBox_CSubmitValue_selected_value_text:
      if (value != nil) {
        [self setSelectedIndexWithInt:[self indexOfValueWithId:value]];
      }
      else {
        [self setSelectedIndexWithInt:-1];
      }
      break;
      default:
      [self setValueWithId:value];
      break;
    }
  }
}

- (void)setHideWhenResetWithBoolean:(BOOL)hide {
  hideWhenReset_ = hide;
}

- (void)setPopupBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)b {
  [((id<RAREiComboBox>) nil_chk([self getComboBox])) setPopupBorderWithRAREiPlatformBorder:b];
}

- (void)setPopupFractionWithFloat:(float)fraction {
  popupFraction_ = fraction;
}

- (void)setPopupHorizontalAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)popupHorizontalAlignment {
  self->popupHorizontalAlignment_ = popupHorizontalAlignment;
}

- (void)setPopupVisibleWithBoolean:(BOOL)visible {
  [((id<RAREiComboBox>) nil_chk([self getComboBox])) setPopupVisibleWithBoolean:visible];
}

- (void)setShowDividerWithBoolean:(BOOL)show {
  [((id<RAREiPlatformListHandler>) nil_chk(listComponent_)) setShowDividerWithBoolean:show];
}

- (void)setSingleClickActionWithBoolean:(BOOL)singleClick {
  [((id<RAREiPlatformListHandler>) nil_chk(listComponent_)) setSingleClickActionWithBoolean:singleClick];
}

- (void)setUseDialogButtonWithBoolean:(BOOL)dialog {
  [((id<RAREiComboBox>) nil_chk([self getComboBox])) setUseDialogButtonWithBoolean:dialog];
}

- (void)setValueWithId:(id)value {
  [self setValueExWithId:value];
}

- (RAREUIColor *)getAlternatingRowColor {
  return [((id<RAREiPlatformListHandler>) nil_chk(listComponent_)) getAlternatingRowColor];
}

- (int)getFirstVisibleIndex {
  return [((id<RAREiPlatformListHandler>) nil_chk(listComponent_)) getFirstVisibleIndex];
}

- (id)getHTTPFormValue {
  if (![self hasSelection]) {
    return nil;
  }
  switch (submitValueType_) {
    case RARESPOTComboBox_CSubmitValue_selected_index:
    return [JavaLangInteger valueOfWithInt:[self getSelectedIndex]];
    case RARESPOTComboBox_CSubmitValue_selected_linked_data:
    return [self getSelectionData];
    case RARESPOTComboBox_CSubmitValue_selected_value_text:
    return [self getSelectionAsString];
    default:
    return [self getSelection];
  }
}

- (int)getLastVisibleIndex {
  return [((id<RAREiPlatformListHandler>) nil_chk(listComponent_)) getLastVisibleIndex];
}

- (id<RAREiActionComponent>)getPopupButton {
  return [((id<RAREiComboBox>) nil_chk([self getComboBox])) getPopupButton];
}

- (RARERenderableDataItem_HorizontalAlignEnum *)getPopupHorizontalAlignment {
  return popupHorizontalAlignment_;
}

- (id<RAREiWidget>)getPopupWidget {
  return popupWidget_;
}

- (void)getWillBecomeVisibleBoundsWithRAREUIRectangle:(RAREUIRectangle *)rect {
  [((id<RAREiComboBox>) nil_chk([self getComboBox])) getWillBecomeVisibleBoundsWithRAREUIRectangle:rect];
}

- (void)getProposedPopupBoundsWithRAREUIDimension:(RAREUIDimension *)contentSize
                              withRAREUIRectangle:(RAREUIRectangle *)r {
  [RAREUtils getProposedPopupBoundsWithRAREUIRectangle:r withRAREiPlatformComponent:formComponent_ withRAREUIDimension:contentSize withFloat:popupFraction_ withRARERenderableDataItem_HorizontalAlignEnum:popupHorizontalAlignment_ withRAREiPlatformBorder:[((id<RAREiComboBox>) nil_chk([self getComboBox])) getPopupBorder] withBoolean:YES];
  if (popupHeight_ != nil) {
    ((RAREUIRectangle *) nil_chk(r))->height_ = [RAREScreenUtils toPlatformPixelHeightWithNSString:popupHeight_ withRAREiPlatformComponent:formComponent_ withFloat:[RAREScreenUtils getHeight]];
  }
  if (popupX_ != nil) {
    ((RAREUIRectangle *) nil_chk(r))->x_ = [RAREScreenUtils toPlatformPixelWidthWithNSString:popupX_ withRAREiPlatformComponent:formComponent_ withFloat:[RAREUIScreen snapToSizeWithFloat:r->width_]];
  }
  if (popupY_ != nil) {
    ((RAREUIRectangle *) nil_chk(r))->y_ = [RAREScreenUtils toPlatformPixelHeightWithNSString:popupY_ withRAREiPlatformComponent:formComponent_ withFloat:[RAREUIScreen snapToSizeWithFloat:r->height_]];
  }
}

- (int)getRowHeight {
  if (listComponent_ != nil) {
    return [listComponent_ getRowHeight];
  }
  if ([(id) popupWidget_ conformsToProtocol: @protocol(RAREiListHandler)]) {
    return [((id<RAREiListHandler>) check_protocol_cast(popupWidget_, @protocol(RAREiListHandler))) getRowHeight];
  }
  return 0;
}

- (RARERenderableDataItem *)getSelectedItem {
  if (listComponent_ != nil) {
    return [listComponent_ getSelectedItem];
  }
  if ([(id) popupWidget_ conformsToProtocol: @protocol(RAREiListHandler)]) {
    return (RARERenderableDataItem *) check_class_cast([((id<RAREiWidget>) nil_chk(popupWidget_)) getSelection], [RARERenderableDataItem class]);
  }
  return [super getSelectedItem];
}

- (id)getSelection {
  if (listComponent_ != nil) {
    return [listComponent_ getSelectedItem];
  }
  if ([(id) popupWidget_ conformsToProtocol: @protocol(RAREiListHandler)]) {
    return [((id<RAREiWidget>) nil_chk(popupWidget_)) getSelection];
  }
  return [super getSelectedItem];
}

- (int)getTextLength {
  return [((NSString *) nil_chk([((id<RAREiComboBox>) nil_chk([self getComboBox])) getEditorValue])) sequenceLength];
}

- (NSString *)getValueAsString {
  return [((id<RAREiComboBox>) nil_chk([self getComboBox])) getEditorValue];
}

- (BOOL)hasPopupWidget {
  return popupWidget_ != nil;
}

- (BOOL)isButtonVisible {
  return [((id<RAREiComboBox>) nil_chk([self getComboBox])) isButtonVisible];
}

- (BOOL)isDataEventsEnabled {
  if (listModel_ != nil) {
    [listModel_ isEventsEnabled];
  }
  return NO;
}

- (BOOL)isEditable {
  return [((id<RAREiComboBox>) nil_chk([self getComboBox])) isEditable];
}

- (BOOL)isEditing {
  return NO;
}

- (BOOL)isHideWhenReset {
  return hideWhenReset_;
}

- (BOOL)isPopupVisible {
  return [((id<RAREiComboBox>) nil_chk([self getComboBox])) isPopupVisible];
}

- (void)initializeListenersWithRAREaWidgetListener:(RAREaWidgetListener *)listener {
  [super initializeListenersWithRAREaWidgetListener:listener];
  if (listener != nil) {
    [((id<RAREiComboBox>) nil_chk([self getComboBox])) addPopupMenuListenerWithRAREiPopupMenuListener:listener];
  }
}

- (void)configureExWithRARESPOTComboBox:(RARESPOTComboBox *)cfg {
  RAREaComboBoxComponent *cb = [self createModelAndComponentsWithRARESPOTComboBox:cfg];
  [self setSubItemsWithRAREUTiFilterableList:listModel_];
  [((RAREaComboBoxComponent *) nil_chk(cb)) setPopupContentWithRAREiPlatformListHandler:listComponent_ = [self createListHandlerWithRAREaComboBoxComponent:cb withRARESPOTComboBox:cfg]];
  [self configureWithRARESPOTWidget:cfg withBoolean:YES withBoolean:YES withBoolean:YES withBoolean:YES];
  id<RAREiSpeechEnabler> sp = [((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getSpeechEnabler];
  if ((sp != nil) && [((SPOTBoolean *) nil_chk(((RARESPOTComboBox *) nil_chk(cfg))->speechInputSupported_)) booleanValue]) {
    formComponent_ = (id<RAREiPlatformComponent>) check_protocol_cast([sp configureWithRAREiWidget:self withId:dataComponent_ withRARESPOTWidget:cfg], @protocol(RAREiPlatformComponent));
  }
  RAREPaintBucket *pb = [RAREColorUtils configureWithRAREiWidget:self withRARESPOTGridCell:[((RARESPOTComboBox *) nil_chk(cfg)) getSelectionPainter] withRAREPaintBucket:nil];
  if (pb != nil) {
    [((id<RAREiPlatformItemRenderer>) nil_chk([((id<RAREiPlatformListHandler>) nil_chk(listComponent_)) getItemRenderer])) setSelectionPaintWithRAREPaintBucket:pb];
  }
  [((id<RAREiPlatformListHandler>) nil_chk(listComponent_)) setDeselectEventsDisabledWithBoolean:![((SPOTBoolean *) nil_chk(cfg->deselectEventsEnabled_)) booleanValue]];
  [listComponent_ setMinimumVisibleRowCountWithInt:[((SPOTInteger *) nil_chk(cfg->minVisibleRowCount_)) intValue]];
  if ([((SPOTInteger *) nil_chk(cfg->maxVisibleRowCount_)) spot_valueWasSet]) {
    [cb setMaximumVisibleRowCountWithInt:[cfg->maxVisibleRowCount_ intValue]];
  }
  if ([((SPOTInteger *) nil_chk(cfg->visibleRowCount_)) spot_valueWasSet]) {
    [listComponent_ setVisibleRowCountWithInt:[cfg->visibleRowCount_ intValue]];
  }
  if ([cfg getItemDescription] != nil) {
    itemDescription_ = [self createColumnWithRARESPOTItemDescription:[cfg getItemDescription]];
  }
  if (![((SPOTBoolean *) nil_chk(cfg->editable_)) booleanValue]) {
    [self setEditableWithBoolean:NO];
  }
  [((id<RAREiPlatformComponent>) nil_chk([listComponent_ getListComponent])) setWidgetWithRAREiWidget:self];
  [((id<RAREiPlatformListDataModel>) nil_chk(listModel_)) setWidgetWithRAREiWidget:self];
  [listModel_ setColumnDescriptionWithRAREColumn:itemDescription_];
  [listModel_ setUseIndexForFilteringWithBoolean:[((SPOTBoolean *) nil_chk(cfg->indexForFiltering_)) booleanValue]];
  if (itemDescription_ != nil) {
    if ([itemDescription_ getFont] != nil) {
      [((id<RAREiPlatformComponent>) nil_chk([listComponent_ getListComponent])) setFontWithRAREUIFont:[itemDescription_ getFont]];
    }
  }
  if ([((SPOTPrintableString *) nil_chk(cfg->rowHeight_)) spot_valueWasSet]) {
    NSString *s = [cfg->rowHeight_ getValue];
    if (s == nil) {
      s = @"1ln";
    }
    [self setRowHeightWithInt:[RAREScreenUtils toPlatformPixelHeightWithNSString:s withRAREiPlatformComponent:[listComponent_ getListComponent] withFloat:1000]];
  }
  pb = [RAREUIColorHelper getPaintBucketWithRAREiWidget:self withRARESPOTGridCell:[cfg getPopupPainter]];
  if (pb != nil) {
    [cb setPopupPainterWithRAREiPlatformComponentPainter:[pb getComponentPainterWithBoolean:YES]];
  }
  RAREPainterHolder *ph = [RAREPainterUtils createPaintHolderFromAttributesWithRAREiWidget:self withJavaUtilMap:[((SPOTBoolean *) nil_chk(cfg->showPopupButton_)) spot_getAttributes]];
  if ([@"true" isEqual:[cfg->showPopupButton_ spot_getAttributeWithNSString:@"scaleIcons"]]) {
    [cb setScaleButtonIconsWithBoolean:YES];
  }
  if (ph != nil) {
    [cb setButtonPainterHolderWithRAREPainterHolder:ph];
  }
  if ([((SPOTInteger *) nil_chk(cfg->selectedIndex_)) spot_valueWasSet]) {
    initiallySelectedIndex_ = [cfg->selectedIndex_ intValue];
  }
  if ([cfg getEmptyText] != nil) {
    RARESPOTEmptyText *et = [cfg getEmptyText];
    NSString *s = [((SPOTPrintableString *) nil_chk(((RARESPOTEmptyText *) nil_chk(et))->value_)) getValue];
    if (s != nil) {
      [cb setEmptyFieldTextWithNSString:[self expandStringWithNSString:s]];
    }
    s = [((SPOTPrintableString *) nil_chk(et->fgColor_)) getValue];
    if (s != nil) {
      [cb setEmptyFieldColorWithRAREUIColor:[RAREColorUtils getColorWithNSString:s]];
    }
    if ([et getFont] != nil) {
      [cb setEmptyFieldFontWithRAREUIFont:[RAREUIFontHelper getFontWithRAREiWidget:self withRARESPOTFont:[et getFont]]];
    }
  }
  if ([((SPOTPrintableString *) nil_chk(cfg->icon_)) spot_hasValue]) {
    [self setIconWithRAREiPlatformIcon:[self getIconWithSPOTPrintableString:cfg->icon_]];
  }
  originalValue_ = [((SPOTPrintableString *) nil_chk(cfg->value_)) getValue];
  submitValueType_ = [((RARESPOTComboBox_CSubmitValue *) nil_chk(cfg->submitValue_)) intValue];
  [cb configurationCompletedWithRAREaWidget:self withRARESPOTWidget:cfg];
  [self handleInitialStuff];
}

- (void)configurePopupActionWidgetsWithRAREiWidget:(id<RAREiWidget>)ok
                                   withRAREiWidget:(id<RAREiWidget>)cancel {
  if ([(id) ok conformsToProtocol: @protocol(RAREiActionable)]) {
    [((id<RAREiActionable>) check_protocol_cast(ok, @protocol(RAREiActionable))) addActionListenerWithRAREiActionListener:[[RAREaComboBoxWidget_$1 alloc] initWithRAREaComboBoxWidget:self]];
  }
  if ([(id) cancel conformsToProtocol: @protocol(RAREiActionable)]) {
    [((id<RAREiActionable>) check_protocol_cast(cancel, @protocol(RAREiActionable))) addActionListenerWithRAREiActionListener:[[RAREaComboBoxWidget_$2 alloc] initWithRAREaComboBoxWidget:self]];
  }
}

- (id<RAREiPlatformListHandler>)createListHandlerWithRAREaComboBoxComponent:(RAREaComboBoxComponent *)cb
                                                       withRARESPOTComboBox:(RARESPOTComboBox *)cfg {
  if ([((RARESPOTComboBox_CComponentType *) nil_chk(((RARESPOTComboBox *) nil_chk(cfg))->componentType_)) getValue] == RARESPOTComboBox_CComponentType_widget) {
    RARESPOTWidget *popupConfiguration = (RARESPOTWidget *) check_class_cast([((SPOTAny *) nil_chk(cfg->popupWidget_)) getValue], [RARESPOTWidget class]);
    if (popupConfiguration == nil) {
      popupConfiguration = (RARESPOTWidget *) check_class_cast([cfg->popupWidget_ spot_getLinkedData], [RARESPOTWidget class]);
      (void) [cfg->popupWidget_ spot_setLinkedDataWithId:nil];
    }
    if (popupConfiguration == nil) {
      popupConfiguration = [[RARESPOTLabel alloc] init];
    }
    [self createPopupWidgetWithRARESPOTWidget:popupConfiguration withJavaUtilMap:[cfg->popupWidget_ spot_getAttributes]];
    [((id<RAREiPlatformListDataModel>) nil_chk(listModel_)) addWithId:[[RARERenderableDataItem alloc] initWithId:@""]];
    if ([((SPOTBoolean *) nil_chk(cfg->focusable_)) spot_valueWasSet]) {
      [((RAREaComboBoxComponent *) nil_chk(cb)) setPopupContentFocusableWithBoolean:[cfg->focusable_ booleanValue]];
    }
    return [[RAREaComboBoxWidget_WidgetComboBoxComponent alloc] initWithRAREiPlatformComponent:[((id<RAREiWidget>) nil_chk(popupWidget_)) getContainerComponent]];
  }
  else {
    return [((RAREaComboBoxComponent *) nil_chk(cb)) createDefaultListHandlerWithRAREiWidget:self withRAREiPlatformListDataModel:listModel_];
  }
}

- (RAREaComboBoxComponent *)createModelAndComponentsWithRARESPOTComboBox:(RARESPOTComboBox *)cfg {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)createPopupWidgetWithRARESPOTWidget:(RARESPOTWidget *)cfg
                            withJavaUtilMap:(id<JavaUtilMap>)attributes {
  popupWidget_ = [RAREaContainer createWidgetWithRAREiContainer:[self getContainerViewer] withRARESPOTWidget:cfg];
  if ([((SPOTPrintableString *) nil_chk(((RARESPOTRectangle *) nil_chk(((RARESPOTWidget *) nil_chk(cfg))->bounds_))->width_)) spot_valueWasSet]) {
    popupFraction_ = [cfg->bounds_ getWidth] / 100.0f;
  }
  if ([((SPOTPrintableString *) nil_chk(cfg->bounds_->height_)) spot_valueWasSet]) {
    popupHeight_ = [cfg->bounds_->height_ getValue];
  }
  if ([((SPOTPrintableString *) nil_chk(cfg->bounds_->x_)) spot_valueWasSet]) {
    popupX_ = [cfg->bounds_->x_ getValue];
  }
  if ([((SPOTPrintableString *) nil_chk(cfg->bounds_->y_)) spot_valueWasSet]) {
    popupY_ = [cfg->bounds_->y_ getValue];
  }
  id<RAREiWidget> aw = popupWidget_;
  id<RAREiWidget> cw = nil;
  if (attributes != nil) {
    NSString *s = [attributes getWithId:@"horizontalAlignment"];
    if (s != nil) {
      @try {
        popupHorizontalAlignment_ = [RARERenderableDataItem_HorizontalAlignEnum valueOfWithNSString:[s uppercaseStringWithJRELocale:[JavaUtilLocale US]]];
      }
      @catch (JavaLangException *e) {
        [RAREPlatform ignoreExceptionWithNSString:nil withJavaLangThrowable:e];
      }
    }
    s = [attributes getWithId:@"okWidget"];
    if (s == nil) {
      s = [attributes getWithId:@"okwidget"];
    }
    if (s != nil) {
      aw = [((id<RAREiContainer>) nil_chk([((id<RAREiWidget>) nil_chk(popupWidget_)) getContainerViewer])) getWidgetFromPathWithNSString:s];
    }
    if (aw == nil) {
      @throw [RAREDataParser invalidConfigurationExceptionWithNSString:[((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getResourceAsStringWithNSString:@"Rare.runtime.text.unknowCloseWidget"] withNSString:s];
    }
    s = [attributes getWithId:@"cancelWidget"];
    if (s == nil) {
      s = [attributes getWithId:@"cancelwidget"];
    }
    if (s != nil) {
      cw = [((id<RAREiContainer>) nil_chk([((id<RAREiWidget>) nil_chk(popupWidget_)) getContainerViewer])) getWidgetFromPathWithNSString:s];
      if (cw == nil) {
        @throw [RAREDataParser invalidConfigurationExceptionWithNSString:[((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getResourceAsStringWithNSString:@"Rare.runtime.text.unknowCloseWidget"] withNSString:s];
      }
    }
    s = [attributes getWithId:@"valueWidget"];
    if (s == nil) {
      s = [attributes getWithId:@"valuewidget"];
    }
    if (s != nil) {
      valueWidget_ = [((id<RAREiContainer>) nil_chk([((id<RAREiWidget>) nil_chk(popupWidget_)) getContainerViewer])) getWidgetFromPathWithNSString:s];
      if (valueWidget_ == nil) {
        @throw [RAREDataParser invalidConfigurationExceptionWithNSString:[((id<RAREiPlatformAppContext>) nil_chk([self getAppContext])) getResourceAsStringWithNSString:@"Rare.runtime.text.unknowValueWidget"] withNSString:s];
      }
    }
    valueAttribute_ = [attributes getWithId:@"valueAttribute"];
    if (valueAttribute_ == nil) {
      valueAttribute_ = [attributes getWithId:@"valueattribute"];
    }
  }
  if ((cw != nil) || (aw != nil)) {
    [self configurePopupActionWidgetsWithRAREiWidget:aw withRAREiWidget:cw];
  }
}

- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon {
  [super setIconWithRAREiPlatformIcon:icon];
  [((id<RAREiComboBox>) nil_chk([self getComboBox])) setEditorIconWithRAREiPlatformIcon:icon];
}

- (void)handleInitialStuff {
  if ((initiallySelectedIndex_ == -1) && (originalValue_ != nil)) {
    initiallySelectedIndex_ = [self indexOfValueEqualsWithId:[self expandStringWithNSString:originalValue_ withBoolean:NO]];
  }
  if ((originalValue_ != nil) && (initiallySelectedIndex_ == -1)) {
    [self setValueWithId:[self expandStringWithNSString:originalValue_]];
  }
  [super handleInitialStuff];
}

- (void)setEditorValueWithId:(id)value {
  [((id<RAREiComboBox>) nil_chk([self getComboBox])) setEditorValueWithJavaLangCharSequence:(value == nil) ? @"" : [value description]];
}

- (void)setValueExWithId:(id)value {
  if (value == nil) {
    [self clearSelection];
  }
  else if ([value isKindOfClass:[RARERenderableDataItem class]]) {
    [self setSelectedItemWithRARERenderableDataItem:(RARERenderableDataItem *) check_class_cast(value, [RARERenderableDataItem class])];
  }
  else {
    int index = [self indexOfValueWithId:value];
    if (index != -1) {
      [self setSelectedIndexWithInt:index];
    }
    else {
      [self setEditorValueWithId:value];
      if (valueWidget_ != nil) {
        [valueWidget_ setValueWithId:value];
      }
    }
  }
}

- (id<RAREiComboBox>)getComboBox {
  return (id<RAREiComboBox>) check_protocol_cast([self getDataComponent], @protocol(RAREiComboBox));
}

- (void)copyAllFieldsTo:(RAREaComboBoxWidget *)other {
  [super copyAllFieldsTo:other];
  other->hideWhenReset_ = hideWhenReset_;
  other->listModel_ = listModel_;
  other->originalValue_ = originalValue_;
  other->popupFraction_ = popupFraction_;
  other->popupHeight_ = popupHeight_;
  other->popupHorizontalAlignment_ = popupHorizontalAlignment_;
  other->popupWidget_ = popupWidget_;
  other->popupX_ = popupX_;
  other->popupY_ = popupY_;
  other->valueAttribute_ = valueAttribute_;
  other->valueWidget_ = valueWidget_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isKeepSelectionVisible", NULL, "Z", 0x1, NULL },
    { "createWithRAREiContainer:withRARESPOTComboBox:", NULL, "LRAREComboBoxWidget", 0x9, NULL },
    { "removeSelectedDataWithBoolean:", NULL, "LNSObject", 0x1, NULL },
    { "getAlternatingRowColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "getHTTPFormValue", NULL, "LNSObject", 0x1, NULL },
    { "getPopupButton", NULL, "LRAREiActionComponent", 0x1, NULL },
    { "getPopupHorizontalAlignment", NULL, "LRARERenderableDataItem_HorizontalAlignEnum", 0x1, NULL },
    { "getPopupWidget", NULL, "LRAREiWidget", 0x1, NULL },
    { "getSelectedItem", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "getSelection", NULL, "LNSObject", 0x1, NULL },
    { "getValueAsString", NULL, "LNSString", 0x1, NULL },
    { "hasPopupWidget", NULL, "Z", 0x1, NULL },
    { "isButtonVisible", NULL, "Z", 0x1, NULL },
    { "isDataEventsEnabled", NULL, "Z", 0x1, NULL },
    { "isEditable", NULL, "Z", 0x1, NULL },
    { "isEditing", NULL, "Z", 0x1, NULL },
    { "isHideWhenReset", NULL, "Z", 0x1, NULL },
    { "isPopupVisible", NULL, "Z", 0x1, NULL },
    { "initializeListenersWithRAREaWidgetListener:", NULL, "V", 0x4, NULL },
    { "configureExWithRARESPOTComboBox:", NULL, "V", 0x4, NULL },
    { "configurePopupActionWidgetsWithRAREiWidget:withRAREiWidget:", NULL, "V", 0x4, NULL },
    { "createListHandlerWithRAREaComboBoxComponent:withRARESPOTComboBox:", NULL, "LRAREiPlatformListHandler", 0x4, NULL },
    { "createModelAndComponentsWithRARESPOTComboBox:", NULL, "LRAREaComboBoxComponent", 0x404, NULL },
    { "createPopupWidgetWithRARESPOTWidget:withJavaUtilMap:", NULL, "V", 0x4, NULL },
    { "handleInitialStuff", NULL, "V", 0x4, NULL },
    { "setEditorValueWithId:", NULL, "V", 0x4, NULL },
    { "setValueExWithId:", NULL, "V", 0x4, NULL },
    { "getComboBox", NULL, "LRAREiComboBox", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "hideWhenReset_", NULL, 0x4, "Z" },
    { "listModel_", NULL, 0x4, "LRAREiPlatformListDataModel" },
    { "originalValue_", NULL, 0x4, "LNSString" },
    { "popupFraction_", NULL, 0x4, "F" },
    { "popupHeight_", NULL, 0x4, "LNSString" },
    { "popupWidget_", NULL, 0x4, "LRAREiWidget" },
    { "popupX_", NULL, 0x4, "LNSString" },
    { "popupY_", NULL, 0x4, "LNSString" },
    { "valueAttribute_", NULL, 0x4, "LNSString" },
    { "popupHorizontalAlignment_", NULL, 0x4, "LRARERenderableDataItem_HorizontalAlignEnum" },
    { "valueWidget_", NULL, 0x4, "LRAREiWidget" },
  };
  static J2ObjcClassInfo _RAREaComboBoxWidget = { "aComboBoxWidget", "com.appnativa.rare.widget", NULL, 0x401, 28, methods, 11, fields, 0, NULL};
  return &_RAREaComboBoxWidget;
}

@end
@implementation RAREaComboBoxWidget_WidgetComboBoxComponent

- (id)initWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component {
  if (self = [super init]) {
    self->component_ = component;
  }
  return self;
}

- (id<RAREiPlatformItemRenderer>)getItemRenderer {
  return nil;
}

- (id<RAREiPlatformComponent>)getListComponent {
  return component_;
}

- (RAREUIDimension *)getPreferredSize {
  return [((id<RAREiPlatformComponent>) nil_chk(component_)) getPreferredSize];
}

- (float)getPreferredWidth {
  return ((RAREUIDimension *) nil_chk([((id<RAREiPlatformComponent>) nil_chk(component_)) getPreferredSize]))->width_;
}

- (int)getRowHeight {
  return [RAREFontUtils getDefaultLineHeight];
}

- (void)copyAllFieldsTo:(RAREaComboBoxWidget_WidgetComboBoxComponent *)other {
  [super copyAllFieldsTo:other];
  other->component_ = component_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getItemRenderer", NULL, "LRAREiPlatformItemRenderer", 0x1, NULL },
    { "getListComponent", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getPreferredSize", NULL, "LRAREUIDimension", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "component_", NULL, 0x0, "LRAREiPlatformComponent" },
  };
  static J2ObjcClassInfo _RAREaComboBoxWidget_WidgetComboBoxComponent = { "WidgetComboBoxComponent", "com.appnativa.rare.widget", "aComboBoxWidget", 0x8, 3, methods, 1, fields, 0, NULL};
  return &_RAREaComboBoxWidget_WidgetComboBoxComponent;
}

@end
@implementation RAREaComboBoxWidget_$1

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e {
  if (this$0_->valueWidget_ != nil) {
    if (this$0_->valueAttribute_ != nil) {
      [this$0_ setValueExWithId:[this$0_->valueWidget_ getAttributeWithNSString:this$0_->valueAttribute_]];
    }
    else {
      [this$0_ setValueExWithId:[this$0_->valueWidget_ getValue]];
    }
  }
  else {
    if (this$0_->valueAttribute_ != nil) {
      [this$0_ setValueExWithId:[((id<RAREiWidget>) nil_chk(this$0_->popupWidget_)) getAttributeWithNSString:this$0_->valueAttribute_]];
    }
    else {
      [this$0_ setValueExWithId:[((id<RAREiWidget>) nil_chk(this$0_->popupWidget_)) getValue]];
    }
  }
  [this$0_ setPopupVisibleWithBoolean:NO];
}

- (id)initWithRAREaComboBoxWidget:(RAREaComboBoxWidget *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREaComboBoxWidget" },
  };
  static J2ObjcClassInfo _RAREaComboBoxWidget_$1 = { "$1", "com.appnativa.rare.widget", "aComboBoxWidget", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREaComboBoxWidget_$1;
}

@end
@implementation RAREaComboBoxWidget_$2

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e {
  [this$0_ cancelPopup];
}

- (id)initWithRAREaComboBoxWidget:(RAREaComboBoxWidget *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREaComboBoxWidget" },
  };
  static J2ObjcClassInfo _RAREaComboBoxWidget_$2 = { "$2", "com.appnativa.rare.widget", "aComboBoxWidget", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREaComboBoxWidget_$2;
}

@end
