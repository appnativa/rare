//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-colorchooser/com/appnativa/rare/ui/ColorChooserPanel.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREColorChooserPanel_H_
#define _RAREColorChooserPanel_H_

@class RAREActionEvent;
@class RAREColorChooserPanel;
@class RAREColorIcon;
@class RAREColorPalette;
@class RAREColorPalettePanel;
@class RAREKeyEvent;
@class RAREUIColor;
@class RAREUIRectangle;
@class RAREWindowEvent;
@protocol RAREiPlatformComponent;
@protocol RAREiWidget;
@protocol RAREiWindow;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/BorderPanel.h"
#include "com/appnativa/rare/ui/ComboBoxComponent.h"
#include "com/appnativa/rare/ui/XPActionComponent.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/event/iWindowListener.h"
#include "com/appnativa/rare/ui/iActionable.h"

@protocol RAREColorChooserPanel_iColorWheel < RAREiActionable, NSObject, JavaObject >
- (void)setLiveUpdateWithBoolean:(BOOL)live;
- (void)setSelectedColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setShowAlphaSelectorWithBoolean:(BOOL)show;
- (id<RAREiPlatformComponent>)getComponent;
- (RAREUIColor *)getSelectedColor;
- (void)dispose;
@end

@interface RAREColorChooserPanel_ColorButton : RAREXPActionComponent < RAREiActionListener > {
 @public
  RAREColorIcon *colorIcon_;
  RAREColorChooserPanel *panel_;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)widget;
- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (void)addActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (void)dispose;
- (void)doClick;
- (void)setContent;
- (void)setSelectedColorWithRAREUIColor:(RAREUIColor *)color;
- (RAREColorChooserPanel *)getPanel;
- (void)copyAllFieldsTo:(RAREColorChooserPanel_ColorButton *)other;
@end

J2OBJC_FIELD_SETTER(RAREColorChooserPanel_ColorButton, colorIcon_, RAREColorIcon *)
J2OBJC_FIELD_SETTER(RAREColorChooserPanel_ColorButton, panel_, RAREColorChooserPanel *)

@interface RAREColorChooserPanel : RAREBorderPanel < RAREiActionable > {
 @public
  BOOL showColorWheel_;
  BOOL showPalette_;
  BOOL showPaletteFirst_;
  id<RAREColorChooserPanel_iColorWheel> colorWheel_;
  id<RAREiWindow> dialog_;
  RAREColorPalettePanel *palettePanel_;
  RAREUIColor *selectedColor_;
  BOOL showNoneButton_;
  BOOL showOkButton_;
  RAREColorPalette *colorPalette_;
  NSString *dialogTitle_;
  BOOL inPopup_;
  BOOL useList_;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)context;
- (void)addActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (void)dispose;
- (void)handleKeyPressedWithRAREKeyEvent:(RAREKeyEvent *)e;
- (void)removeActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (void)showDialogWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)owner;
- (void)setColorPaletteWithRAREColorPalette:(RAREColorPalette *)palette;
- (void)setContentWithRAREiWidget:(id<RAREiWidget>)context;
- (void)setDialogTitleWithNSString:(NSString *)dialogTitle;
- (void)setInPopupWithBoolean:(BOOL)inPopup;
- (void)setSelectedColorWithRAREUIColor:(RAREUIColor *)selectedColor;
- (void)setShowColorWheelWithBoolean:(BOOL)showColorWheel;
- (void)setShowNoneButtonWithBoolean:(BOOL)show;
- (void)setShowOKButtonWithBoolean:(BOOL)show;
- (void)setShowPaletteWithBoolean:(BOOL)showPalette;
- (void)setShowPaletteFirstWithBoolean:(BOOL)showPaletteFirst;
- (void)setUseListWithBoolean:(BOOL)use;
- (NSString *)getDialogTitle;
- (RAREUIColor *)getSelectedColor;
- (BOOL)isInPopup;
- (BOOL)isShowColorWheel;
- (BOOL)isShowPalette;
- (BOOL)isShowPaletteFirst;
- (void)noneButtonPressed;
- (void)okButtonPressed;
- (id<RAREiPlatformComponent>)getButtonPannel;
- (void)copyAllFieldsTo:(RAREColorChooserPanel *)other;
@end

J2OBJC_FIELD_SETTER(RAREColorChooserPanel, colorWheel_, id<RAREColorChooserPanel_iColorWheel>)
J2OBJC_FIELD_SETTER(RAREColorChooserPanel, dialog_, id<RAREiWindow>)
J2OBJC_FIELD_SETTER(RAREColorChooserPanel, palettePanel_, RAREColorPalettePanel *)
J2OBJC_FIELD_SETTER(RAREColorChooserPanel, selectedColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RAREColorChooserPanel, colorPalette_, RAREColorPalette *)
J2OBJC_FIELD_SETTER(RAREColorChooserPanel, dialogTitle_, NSString *)

typedef RAREColorChooserPanel ComAppnativaRareUiColorChooserPanel;

@interface RAREColorChooserPanel_ColorButton_$1 : RAREColorChooserPanel {
 @public
  RAREColorChooserPanel_ColorButton *this$0_;
}

- (void)setSelectedColorWithRAREUIColor:(RAREUIColor *)selectedColor;
- (id)initWithRAREColorChooserPanel_ColorButton:(RAREColorChooserPanel_ColorButton *)outer$
                                withRAREiWidget:(id<RAREiWidget>)arg$0;
@end

J2OBJC_FIELD_SETTER(RAREColorChooserPanel_ColorButton_$1, this$0_, RAREColorChooserPanel_ColorButton *)

@interface RAREColorChooserPanel_ColorChooserComboBox : RAREComboBoxComponent {
 @public
  RAREColorChooserPanel *panel_;
  BOOL showValueAsHex_;
  RAREColorIcon *colorIcon_;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)widget;
- (void)cancelPopup;
- (void)dispose;
- (void)showPopup;
- (void)setContent;
- (void)setSelectedColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setShowValueAsHexWithBoolean:(BOOL)showValueAsHex;
- (RAREColorChooserPanel *)getPanel;
- (BOOL)isShowValueAsHex;
- (void)handleKeyPressedWithRAREKeyEvent:(RAREKeyEvent *)e;
- (void)keyboardActionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (void)popupActionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (void)updateComboboxEditor;
- (id<RAREiWidget>)getPopupWidget;
- (void)getProposedPopupBoundsWithRAREUIRectangle:(RAREUIRectangle *)r;
- (void)copyAllFieldsTo:(RAREColorChooserPanel_ColorChooserComboBox *)other;
@end

J2OBJC_FIELD_SETTER(RAREColorChooserPanel_ColorChooserComboBox, panel_, RAREColorChooserPanel *)
J2OBJC_FIELD_SETTER(RAREColorChooserPanel_ColorChooserComboBox, colorIcon_, RAREColorIcon *)

@interface RAREColorChooserPanel_ColorChooserComboBox_$1 : RAREColorChooserPanel {
 @public
  RAREColorChooserPanel_ColorChooserComboBox *this$0_;
}

- (void)setSelectedColorWithRAREUIColor:(RAREUIColor *)selectedColor;
- (id)initWithRAREColorChooserPanel_ColorChooserComboBox:(RAREColorChooserPanel_ColorChooserComboBox *)outer$
                                         withRAREiWidget:(id<RAREiWidget>)arg$0;
@end

J2OBJC_FIELD_SETTER(RAREColorChooserPanel_ColorChooserComboBox_$1, this$0_, RAREColorChooserPanel_ColorChooserComboBox *)

@interface RAREColorChooserPanel_$1 : NSObject < RAREiWindowListener > {
 @public
  RAREColorChooserPanel *this$0_;
}

- (void)windowEventWithRAREWindowEvent:(RAREWindowEvent *)e;
- (id)initWithRAREColorChooserPanel:(RAREColorChooserPanel *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREColorChooserPanel_$1, this$0_, RAREColorChooserPanel *)

@interface RAREColorChooserPanel_$2 : NSObject < RAREiActionListener > {
 @public
  RAREColorChooserPanel *this$0_;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (id)initWithRAREColorChooserPanel:(RAREColorChooserPanel *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREColorChooserPanel_$2, this$0_, RAREColorChooserPanel *)

@interface RAREColorChooserPanel_$3 : NSObject < RAREiActionListener > {
 @public
  RAREColorChooserPanel *this$0_;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (id)initWithRAREColorChooserPanel:(RAREColorChooserPanel *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREColorChooserPanel_$3, this$0_, RAREColorChooserPanel *)

@interface RAREColorChooserPanel_$4 : NSObject < RAREiActionListener > {
 @public
  RAREColorChooserPanel *this$0_;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (id)initWithRAREColorChooserPanel:(RAREColorChooserPanel *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREColorChooserPanel_$4, this$0_, RAREColorChooserPanel *)

@interface RAREColorChooserPanel_$5 : NSObject < RAREiActionListener > {
 @public
  RAREColorChooserPanel *this$0_;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (id)initWithRAREColorChooserPanel:(RAREColorChooserPanel *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREColorChooserPanel_$5, this$0_, RAREColorChooserPanel *)

@interface RAREColorChooserPanel_$6 : NSObject < RAREiActionListener > {
 @public
  RAREColorChooserPanel *this$0_;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (id)initWithRAREColorChooserPanel:(RAREColorChooserPanel *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREColorChooserPanel_$6, this$0_, RAREColorChooserPanel *)

#endif // _RAREColorChooserPanel_H_
