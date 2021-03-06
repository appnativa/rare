//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-colorchooser/com/appnativa/rare/ui/ColorPalettePanel.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/platform/apple/ui/PopupListBoxHandler.h"
#include "com/appnativa/rare/ui/ColorPalette.h"
#include "com/appnativa/rare/ui/ColorPalettePanel.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/Container.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/XPContainer.h"
#include "com/appnativa/rare/ui/aComponent.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/event/EventListenerList.h"
#include "com/appnativa/rare/ui/event/KeyEvent.h"
#include "com/appnativa/rare/ui/event/MouseEvent.h"
#include "com/appnativa/rare/ui/event/iActionListener.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformGraphics.h"
#include "com/appnativa/rare/ui/iPlatformListDataModel.h"
#include "com/appnativa/rare/ui/iPlatformListHandler.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/painter/UIComponentPainter.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "java/lang/CharSequence.h"
#include "java/lang/Math.h"
#include "java/util/List.h"

@implementation RAREColorPalettePanel

- (id)initWithRAREiWidget:(id<RAREiWidget>)context {
  if (self = [super init]) {
    selectedIndex_ = -1;
    self->widget_ = context;
    RAREColorPalettePanel_Listener *l = [[RAREColorPalettePanel_Listener alloc] initWithRAREColorPalettePanel:self];
    [self addMouseListenerWithRAREiMouseListener:l];
    [self addKeyListenerWithRAREiKeyListener:l];
  }
  return self;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e {
  [self selectWithInt:[((id<RAREiPlatformListHandler>) nil_chk(listHandler_)) getSelectedIndex]];
}

- (void)addActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l {
  [((RAREEventListenerList *) nil_chk([self getEventListenerList])) addWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiActionListener)] withId:l];
}

- (void)removeActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l {
  if (listenerList_ != nil) {
    [listenerList_ removeWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiActionListener)] withId:l];
  }
}

- (void)dispose {
  [super dispose];
  if (listHandler_ != nil) {
    id<RAREiPlatformComponent> c = [listHandler_ getContainerComponent];
    [listHandler_ dispose];
    if (c != nil) {
      [c dispose];
    }
  }
  listHandler_ = nil;
}

- (void)setColorPaletteWithRAREColorPalette:(RAREColorPalette *)palette {
  self->colorPalette_ = palette;
  colorCount_ = (int) [((IOSObjectArray *) nil_chk([((RAREColorPalette *) nil_chk(palette)) getColors])) count];
  if (listHandler_ != nil) {
    [listHandler_ setAllWithJavaUtilCollection:[palette createListItems]];
  }
  rows_ = [palette getRows];
  columns_ = [palette getColumns];
  [self repaint];
}

- (void)setInPopupWithBoolean:(BOOL)inPopup {
  self->inPopup_ = inPopup;
  [self setFocusableWithBoolean:!inPopup];
}

- (void)setSelectedColorWithRAREUIColor:(RAREUIColor *)color {
  self->selectedColor_ = color;
  selectedIndex_ = (color == nil) ? -1 : [self findIndexWithRAREUIColor:color];
  [self selectedIndexUpdated];
}

- (void)setUseListWithBoolean:(BOOL)useList {
  self->useList_ = useList;
  if (useList) {
    id<RAREiPlatformListDataModel> model = [RAREaPlatformHelper createListDataModelWithRAREiWidget:widget_ withJavaUtilList:nil];
    if (colorPalette_ != nil) {
      [((id<RAREiPlatformListDataModel>) nil_chk(model)) addAllWithJavaUtilCollection:[colorPalette_ createListItems]];
    }
    listHandler_ = [RAREaPlatformHelper createPopupListBoxHandlerWithRAREiWidget:widget_ withRAREiPlatformListDataModel:model withBoolean:NO];
    [((id<RAREiPlatformListHandler>) nil_chk(listHandler_)) addActionListenerWithRAREiActionListener:self];
    [self addWithRAREiPlatformComponent:[listHandler_ getContainerComponent]];
  }
}

- (int)getColorAtLocationWithFloat:(float)mx
                         withFloat:(float)my {
  if (!useList_) {
    RAREUIInsets *in = [self getInsetsEx];
    float width = [self getWidth];
    float height = [self getHeight];
    if (in != nil) {
      width -= (in->left_ - in->right_);
      height -= (in->top_ - in->bottom_);
    }
    float f = (width / columns_);
    f = [JavaLangMath minWithFloat:height / rows_ withFloat:f];
    int size = (int) [JavaLangMath floorWithDouble:f];
    float x = (width - (size * columns_)) / 2;
    float y = (height - (size * rows_)) / 2;
    float ox = x;
    for (int i = 0; i < colorCount_; i++) {
      if ((i > 0) && (i % columns_ == 0)) {
        y += size;
        x = ox;
      }
      if ((mx > x) && (my > y) && (mx < (x + size)) && (my < (y + size))) {
        return i;
      }
      x += size;
    }
  }
  return -1;
}

- (RAREColorPalette *)getColorPalette {
  return colorPalette_;
}

- (RAREUIColor *)getSelectedColor {
  return selectedColor_;
}

- (id<JavaLangCharSequence>)getToolTipTextWithInt:(int)x
                                          withInt:(int)y {
  int n = [self getColorAtLocationWithFloat:x withFloat:y];
  if (n != -1) {
    return [((RAREUIColor *) IOSObjectArray_Get(nil_chk([self getColors]), n)) description];
  }
  return nil;
}

- (BOOL)isInPopup {
  return inPopup_;
}

- (BOOL)isUseList {
  return useList_;
}

- (int)findIndexWithRAREUIColor:(RAREUIColor *)c {
  IOSObjectArray *a = [self getColors];
  for (int i = 0; i < (int) [((IOSObjectArray *) nil_chk(a)) count]; i++) {
    if ([((RAREUIColor *) nil_chk(c)) isEqual:IOSObjectArray_Get(a, i)]) {
      return i;
    }
  }
  return -1;
}

- (int)getelectedIndex {
  return selectedIndex_;
}

- (void)layoutWithFloat:(float)width
              withFloat:(float)height {
  if (listHandler_ != nil) {
    RAREUIInsets *in = [self getInsetsEx];
    float x = 0;
    float y = 0;
    if (in != nil) {
      x = in->left_;
      y = in->right_;
      width -= (in->left_ + in->right_);
      height -= (in->top_ + in->bottom_);
    }
    [((id<RAREiPlatformComponent>) nil_chk([listHandler_ getContainerComponent])) setBoundsWithFloat:x withFloat:y withFloat:width withFloat:height];
  }
}

- (void)paintWithRAREiPlatformGraphics:(id<RAREiPlatformGraphics>)g
                             withFloat:(float)x
                             withFloat:(float)y
                             withFloat:(float)width
                             withFloat:(float)height {
  if (!useList_) {
    RAREUIInsets *in = [self getInsetsEx];
    if (in != nil) {
      width -= (in->left_ - in->right_);
      height -= (in->top_ - in->bottom_);
    }
    [((id<RAREiPlatformGraphics>) nil_chk(g)) saveState];
    [g setRenderingOptionsWithBoolean:NO withBoolean:NO];
    float f = (width / columns_);
    f = [JavaLangMath minWithFloat:height / rows_ withFloat:f];
    float d2 = [RAREScreenUtils PLATFORM_PIXELS_4];
    int size = (int) [JavaLangMath floorWithDouble:f];
    x = (width - (size * columns_)) / 2;
    y = (height - (size * rows_)) / 2;
    float ox = x;
    float sw = [RAREScreenUtils PLATFORM_PIXELS_1];
    IOSObjectArray *a = [self getColors];
    float dsize = size - d2 - d2;
    for (int i = 0; i < colorCount_; i++) {
      if ((i > 0) && (i % columns_ == 0)) {
        y += size;
        x = ox;
      }
      [g setColorWithRAREUIColor:IOSObjectArray_Get(nil_chk(a), i)];
      [g fillRectWithFloat:x + d2 withFloat:y + d2 withFloat:dsize withFloat:dsize];
      [g setColorWithRAREUIColor:[RAREUIColor BLACK]];
      [g setStrokeWidthWithFloat:sw];
      [g drawRectWithFloat:x + d2 withFloat:y + d2 withFloat:dsize withFloat:dsize];
      if ((selectedColor_ != nil) && [selectedColor_ isEqual:IOSObjectArray_Get(a, i)]) {
        if (focusPaint_ == nil) {
          focusPaint_ = [((id<RAREiPlatformAppContext>) nil_chk([RAREPlatform getAppContext])) getWidgetFocusPainter];
        }
        if (focusPaint_ != nil) {
          [RAREaUIComponentPainter paintWithRAREiPlatformGraphics:g withFloat:x withFloat:y withFloat:size withFloat:size withRAREPaintBucket:focusPaint_];
        }
      }
      x += size;
    }
    [g restoreState];
  }
}

- (void)selectWithInt:(int)index {
  if ((index != selectedIndex_) || [self isInPopup]) {
    selectedColor_ = IOSObjectArray_Get(nil_chk([self getColors]), index);
    selectedIndex_ = index;
    [self selectedIndexUpdated];
    if (!useList_) {
      [self repaint];
    }
    if ((listenerList_ != nil) && [listenerList_ hasListenersWithIOSClass:[IOSClass classWithProtocol:@protocol(RAREiActionListener)]]) {
      [RAREUtils fireActionEventWithRAREEventListenerList:listenerList_ withRAREActionEvent:[[RAREActionEvent alloc] initWithId:self]];
    }
  }
}

- (void)selecteNextColorWithBoolean:(BOOL)down {
  int n = selectedIndex_;
  if (n == -1) {
    n = 0;
  }
  else {
    if (down) {
      n += columns_;
    }
    else {
      n++;
    }
  }
  IOSObjectArray *a = [self getColors];
  if (n >= (int) [((IOSObjectArray *) nil_chk(a)) count]) {
    n %= (int) [a count];
  }
  selectedIndex_ = n;
  selectedColor_ = IOSObjectArray_Get(a, n);
  [self selectedIndexUpdated];
  [self repaint];
}

- (void)selectePreviousColorWithBoolean:(BOOL)up {
  int n = selectedIndex_;
  if (n == -1) {
    n = colorCount_ - 1;
  }
  else {
    if (up) {
      n -= columns_;
    }
    else {
      n--;
    }
  }
  IOSObjectArray *a = [self getColors];
  if (n < 0) {
    n = (int) [((IOSObjectArray *) nil_chk(a)) count] + n;
  }
  selectedIndex_ = n;
  selectedColor_ = IOSObjectArray_Get(nil_chk(a), n);
  [self selectedIndexUpdated];
  [self repaint];
}

- (void)selectedIndexUpdated {
  if (listHandler_ != nil) {
    [listHandler_ setSelectedIndexWithInt:selectedIndex_];
  }
}

- (IOSObjectArray *)getColors {
  if (colorPalette_ == nil) {
    [self setColorPaletteWithRAREColorPalette:[RAREColorPalette getColorPalette40]];
  }
  return [((RAREColorPalette *) nil_chk(colorPalette_)) getColors];
}

- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  if (listHandler_ != nil) {
    (void) [((id<RAREiPlatformComponent>) nil_chk([listHandler_ getContainerComponent])) getMinimumSizeWithRAREUIDimension:size];
  }
  else {
    if ([RAREPlatform isTouchDevice]) {
      ((RAREUIDimension *) nil_chk(size))->width_ = [RAREScreenUtils PLATFORM_PIXELS_10] * 2 * columns_;
      size->height_ = [RAREScreenUtils PLATFORM_PIXELS_10] * 2 * rows_;
    }
    else {
      ((RAREUIDimension *) nil_chk(size))->width_ = [RAREScreenUtils PLATFORM_PIXELS_10] * columns_;
      size->height_ = [RAREScreenUtils PLATFORM_PIXELS_10] * rows_;
    }
  }
}

- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth {
  if (listHandler_ != nil) {
    (void) [((id<RAREiPlatformComponent>) nil_chk([listHandler_ getContainerComponent])) getPreferredSizeWithRAREUIDimension:size withFloat:maxWidth];
  }
  else {
    ((RAREUIDimension *) nil_chk(size))->width_ = [RAREScreenUtils PLATFORM_PIXELS_16] * 2 * columns_;
    size->height_ = [RAREScreenUtils PLATFORM_PIXELS_16] * 2 * rows_;
  }
}

- (BOOL)hasToolTips {
  return NO;
}

- (void)copyAllFieldsTo:(RAREColorPalettePanel *)other {
  [super copyAllFieldsTo:other];
  other->colorCount_ = colorCount_;
  other->colorPalette_ = colorPalette_;
  other->columns_ = columns_;
  other->inPopup_ = inPopup_;
  other->listHandler_ = listHandler_;
  other->rows_ = rows_;
  other->selectedColor_ = selectedColor_;
  other->selectedIndex_ = selectedIndex_;
  other->useList_ = useList_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getColorPalette", NULL, "LRAREColorPalette", 0x1, NULL },
    { "getSelectedColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "getToolTipTextWithInt:withInt:", NULL, "LJavaLangCharSequence", 0x1, NULL },
    { "isInPopup", NULL, "Z", 0x1, NULL },
    { "isUseList", NULL, "Z", 0x1, NULL },
    { "findIndexWithRAREUIColor:", NULL, "I", 0x4, NULL },
    { "getelectedIndex", NULL, "I", 0x4, NULL },
    { "layoutWithFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "paintWithRAREiPlatformGraphics:withFloat:withFloat:withFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "selectWithInt:", NULL, "V", 0x4, NULL },
    { "selecteNextColorWithBoolean:", NULL, "V", 0x4, NULL },
    { "selectePreviousColorWithBoolean:", NULL, "V", 0x4, NULL },
    { "selectedIndexUpdated", NULL, "V", 0x4, NULL },
    { "getColors", NULL, "LIOSObjectArray", 0x4, NULL },
    { "getMinimumSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
    { "getPreferredSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
    { "hasToolTips", NULL, "Z", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "columns_", NULL, 0x0, "I" },
    { "rows_", NULL, 0x0, "I" },
  };
  static J2ObjcClassInfo _RAREColorPalettePanel = { "ColorPalettePanel", "com.appnativa.rare.ui", NULL, 0x1, 17, methods, 2, fields, 0, NULL};
  return &_RAREColorPalettePanel;
}

@end
@implementation RAREColorPalettePanel_Listener

- (void)keyPressedWithRAREKeyEvent:(RAREKeyEvent *)e {
  if ([((RAREKeyEvent *) nil_chk(e)) isEndPressed]) {
    if ([e isShiftKeyPressed]) {
      [this$0_ selectWithInt:(int) [((IOSObjectArray *) nil_chk([this$0_ getColors])) count] - 1];
    }
    else {
      int n = this$0_->selectedIndex_ / this$0_->columns_;
      n++;
      [this$0_ selectWithInt:(n * this$0_->columns_) - 1];
    }
  }
  else if ([e isHomePressed]) {
    if ([e isShiftKeyPressed]) {
      [this$0_ selectWithInt:0];
    }
    else {
      int n = this$0_->selectedIndex_ / this$0_->columns_;
      [this$0_ selectWithInt:n * this$0_->columns_];
    }
  }
  else if ([e isDownArrowKeyPressed]) {
    [this$0_ selecteNextColorWithBoolean:YES];
  }
  else if ([e isUpArrowKeyPressed]) {
    [this$0_ selectePreviousColorWithBoolean:YES];
  }
  else if ([e isRightArrowKeyPressed]) {
    [this$0_ selecteNextColorWithBoolean:NO];
  }
  else if ([e isLeftArrowKeyPressed]) {
    [this$0_ selectePreviousColorWithBoolean:NO];
  }
}

- (void)keyReleasedWithRAREKeyEvent:(RAREKeyEvent *)e {
}

- (void)keyTypedWithRAREKeyEvent:(RAREKeyEvent *)e {
}

- (void)mouseReleasedWithRAREMouseEvent:(RAREMouseEvent *)e {
  if (![this$0_ isFocusOwner] && [this$0_ isFocusable]) {
    [this$0_ requestFocusWithBoolean:[this$0_ isInPopup]];
  }
  int n = [this$0_ getColorAtLocationWithFloat:[((RAREMouseEvent *) nil_chk(e)) getX] withFloat:[e getY]];
  if (n != -1) {
    [this$0_ selectWithInt:n];
  }
}

- (id)initWithRAREColorPalettePanel:(RAREColorPalettePanel *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREColorPalettePanel" },
  };
  static J2ObjcClassInfo _RAREColorPalettePanel_Listener = { "Listener", "com.appnativa.rare.ui", "ColorPalettePanel", 0x0, 0, NULL, 1, fields, 0, NULL};
  return &_RAREColorPalettePanel_Listener;
}

@end
