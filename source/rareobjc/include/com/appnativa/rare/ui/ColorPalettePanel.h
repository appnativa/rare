//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-colorchooser/com/appnativa/rare/ui/ColorPalettePanel.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RAREColorPalettePanel_H_
#define _RAREColorPalettePanel_H_

@class IOSObjectArray;
@class RAREActionEvent;
@class RAREColorPalette;
@class RAREKeyEvent;
@class RAREMouseEvent;
@class RAREUIColor;
@class RAREUIDimension;
@protocol JavaLangCharSequence;
@protocol RAREiPlatformGraphics;
@protocol RAREiPlatformListHandler;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/UIPanel.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/iActionable.h"
#include "com/appnativa/rare/ui/listener/aMouseAdapter.h"
#include "com/appnativa/rare/ui/listener/iKeyListener.h"

@interface RAREColorPalettePanel : RAREUIPanel < RAREiActionable, RAREiActionListener > {
 @public
  int columns_;
  int rows_;
  int selectedIndex_;
  int colorCount_;
  RAREColorPalette *colorPalette_;
  BOOL inPopup_;
  id<RAREiPlatformListHandler> listHandler_;
  RAREUIColor *selectedColor_;
  BOOL useList_;
}

- (id)initWithRAREiWidget:(id<RAREiWidget>)context;
- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e;
- (void)addActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (void)removeActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (void)setColorPaletteWithRAREColorPalette:(RAREColorPalette *)palette;
- (void)setInPopupWithBoolean:(BOOL)inPopup;
- (void)setSelectedColorWithRAREUIColor:(RAREUIColor *)color;
- (void)setUseListWithBoolean:(BOOL)useList;
- (int)getColorAtLocationWithFloat:(float)mx
                         withFloat:(float)my;
- (RAREColorPalette *)getColorPalette;
- (RAREUIColor *)getSelectedColor;
- (id<JavaLangCharSequence>)getToolTipTextWithInt:(int)x
                                          withInt:(int)y;
- (BOOL)isInPopup;
- (BOOL)isUseList;
- (int)findIndexWithRAREUIColor:(RAREUIColor *)c;
- (int)getelectedIndex;
- (void)layoutWithFloat:(float)width
              withFloat:(float)height;
- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height;
- (void)selectWithInt:(int)index;
- (void)selecteNextColorWithBoolean:(BOOL)down;
- (void)selectePreviousColorWithBoolean:(BOOL)up;
- (void)selectedIndexUpdated;
- (IOSObjectArray *)getColors;
- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size;
- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth;
- (BOOL)hasToolTips;
- (void)copyAllFieldsTo:(RAREColorPalettePanel *)other;
@end

J2OBJC_FIELD_SETTER(RAREColorPalettePanel, colorPalette_, RAREColorPalette *)
J2OBJC_FIELD_SETTER(RAREColorPalettePanel, listHandler_, id<RAREiPlatformListHandler>)
J2OBJC_FIELD_SETTER(RAREColorPalettePanel, selectedColor_, RAREUIColor *)

typedef RAREColorPalettePanel ComAppnativaRareUiColorPalettePanel;

@interface RAREColorPalettePanel_Listener : RAREaMouseAdapter < RAREiKeyListener > {
 @public
  RAREColorPalettePanel *this$0_;
}

- (void)keyPressedWithRAREKeyEvent:(RAREKeyEvent *)e;
- (void)keyReleasedWithRAREKeyEvent:(RAREKeyEvent *)e;
- (void)keyTypedWithRAREKeyEvent:(RAREKeyEvent *)e;
- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e;
- (id)initWithRAREColorPalettePanel:(RAREColorPalettePanel *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREColorPalettePanel_Listener, this$0_, RAREColorPalettePanel *)

#endif // _RAREColorPalettePanel_H_
