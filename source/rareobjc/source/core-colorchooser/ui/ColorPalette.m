//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-colorchooser/com/appnativa/rare/ui/ColorPalette.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/ui/ColorIcon.h"
#include "com/appnativa/rare/ui/ColorPalette.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIColorShade.h"
#include "java/util/ArrayList.h"
#include "java/util/List.h"

@implementation RAREColorPalette

static RAREColorPalette * RAREColorPalette_colorPalette16_;
static RAREColorPalette * RAREColorPalette_colorPalette40_;
static RAREColorPalette * RAREColorPalette_grayPaletteGray16_;

+ (RAREColorPalette *)colorPalette16 {
  return RAREColorPalette_colorPalette16_;
}

+ (void)setColorPalette16:(RAREColorPalette *)colorPalette16 {
  RAREColorPalette_colorPalette16_ = colorPalette16;
}

+ (RAREColorPalette *)colorPalette40 {
  return RAREColorPalette_colorPalette40_;
}

+ (void)setColorPalette40:(RAREColorPalette *)colorPalette40 {
  RAREColorPalette_colorPalette40_ = colorPalette40;
}

+ (RAREColorPalette *)grayPaletteGray16 {
  return RAREColorPalette_grayPaletteGray16_;
}

+ (void)setGrayPaletteGray16:(RAREColorPalette *)grayPaletteGray16 {
  RAREColorPalette_grayPaletteGray16_ = grayPaletteGray16;
}

- (id)init {
  return [super init];
}

- (id)initWithRAREUIColorArray:(IOSObjectArray *)colors {
  if (self = [super init]) {
    self->colors_ = colors;
  }
  return self;
}

- (id)initWithNSString:(NSString *)name
  withRAREUIColorArray:(IOSObjectArray *)colors
               withInt:(int)columns
               withInt:(int)rows {
  if (self = [super init]) {
    self->name_ = name;
    self->colors_ = colors;
    self->rows_ = rows;
    self->columns_ = columns;
  }
  return self;
}

- (RARERenderableDataItem *)createListItemWithRAREUIColor:(RAREUIColor *)color {
  if ((color == nil) || ([color getAlpha] == 0)) {
    return [[RARERenderableDataItem alloc] initWithNSString:nil withId:nil withRAREiPlatformIcon:[[RAREColorIcon alloc] initWithRAREUIColor:nil]];
  }
  for (int i = 0; i < (int) [((IOSObjectArray *) nil_chk(colors_)) count]; i++) {
    if ([((RAREUIColor *) nil_chk(color)) isEqual:IOSObjectArray_Get(colors_, i)]) {
      return [[RARERenderableDataItem alloc] initWithNSString:[color description] withId:color withRAREiPlatformIcon:[[RAREColorIcon alloc] initWithRAREUIColor:color]];
    }
  }
  return [[RARERenderableDataItem alloc] initWithNSString:[((RAREUIColor *) nil_chk(color)) description] withId:color withRAREiPlatformIcon:[[RAREColorIcon alloc] initWithRAREUIColor:color]];
}

- (id<JavaUtilList>)createListItems {
  IOSObjectArray *a = colors_;
  int len = (int) [((IOSObjectArray *) nil_chk(a)) count];
  JavaUtilArrayList *list = [[JavaUtilArrayList alloc] init];
  for (int i = 0; i < len; i++) {
    [list addWithId:[[RARERenderableDataItem alloc] initWithNSString:[((RAREUIColor *) IOSObjectArray_Get(a, i)) description] withId:IOSObjectArray_Get(a, i) withRAREiPlatformIcon:[[RAREColorIcon alloc] initWithRAREUIColor:IOSObjectArray_Get(a, i)]]];
  }
  return list;
}

- (void)setColumnsWithInt:(int)columns {
  self->columns_ = columns;
}

- (void)setMatrixWithInt:(int)columns
                 withInt:(int)rows {
  self->columns_ = columns;
  self->rows_ = rows;
}

- (void)setNameWithNSString:(NSString *)name {
  self->name_ = name;
}

- (void)setPaletteWithRAREUIColorArray:(IOSObjectArray *)colors {
  self->colors_ = colors;
}

- (void)setRowsWithInt:(int)rows {
  self->rows_ = rows;
}

+ (RAREColorPalette *)getColorPalette16 {
  if (RAREColorPalette_colorPalette16_ == nil) {
    IOSObjectArray *a = [IOSObjectArray arrayWithLength:16 type:[IOSClass classWithClass:[RAREUIColor class]]];
    (void) IOSObjectArray_Set(a, 0, [[RAREUIColorShade alloc] initWithRAREUIColor:[RAREUIColor BLACK] withNSString:@"black"]);
    (void) IOSObjectArray_Set(a, 1, [[RAREUIColorShade alloc] initWithRAREUIColor:[RAREColorUtils getColorWithNSString:@"maroon"] withNSString:@"maroon"]);
    (void) IOSObjectArray_Set(a, 2, [[RAREUIColorShade alloc] initWithRAREUIColor:[RAREUIColor GREEN] withNSString:@"green"]);
    (void) IOSObjectArray_Set(a, 3, [[RAREUIColorShade alloc] initWithRAREUIColor:[RAREColorUtils getColorWithNSString:@"olive"] withNSString:@"olive"]);
    (void) IOSObjectArray_Set(a, 4, [[RAREUIColorShade alloc] initWithRAREUIColor:[RAREColorUtils getColorWithNSString:@"navy"] withNSString:@"navy"]);
    (void) IOSObjectArray_Set(a, 5, [[RAREUIColorShade alloc] initWithRAREUIColor:[RAREColorUtils getColorWithNSString:@"purple"] withNSString:@"purple"]);
    (void) IOSObjectArray_Set(a, 6, [[RAREUIColorShade alloc] initWithRAREUIColor:[RAREColorUtils getColorWithNSString:@"teal"] withNSString:@"teal"]);
    (void) IOSObjectArray_Set(a, 7, [[RAREUIColorShade alloc] initWithRAREUIColor:[RAREColorUtils getColorWithNSString:@"silver"] withNSString:@"silver"]);
    (void) IOSObjectArray_Set(a, 8, [[RAREUIColorShade alloc] initWithRAREUIColor:[RAREUIColor GRAY] withNSString:@"gray"]);
    (void) IOSObjectArray_Set(a, 9, [[RAREUIColorShade alloc] initWithRAREUIColor:[RAREUIColor RED] withNSString:@"red"]);
    (void) IOSObjectArray_Set(a, 10, [[RAREUIColorShade alloc] initWithRAREUIColor:[RAREColorUtils getColorWithNSString:@"lime"] withNSString:@"lime"]);
    (void) IOSObjectArray_Set(a, 11, [[RAREUIColorShade alloc] initWithRAREUIColor:[RAREUIColor YELLOW] withNSString:@"yellow"]);
    (void) IOSObjectArray_Set(a, 12, [[RAREUIColorShade alloc] initWithRAREUIColor:[RAREUIColor BLUE] withNSString:@"blue"]);
    (void) IOSObjectArray_Set(a, 13, [[RAREUIColorShade alloc] initWithRAREUIColor:[RAREColorUtils getColorWithNSString:@"fuchsia"] withNSString:@"fuchsia"]);
    (void) IOSObjectArray_Set(a, 14, [[RAREUIColorShade alloc] initWithRAREUIColor:[RAREColorUtils getColorWithNSString:@"aqua"] withNSString:@"aqua"]);
    (void) IOSObjectArray_Set(a, 15, [[RAREUIColorShade alloc] initWithRAREUIColor:[RAREUIColor WHITE] withNSString:@"white"]);
    RAREColorPalette_colorPalette16_ = [[RAREColorPalette alloc] initWithNSString:@"Color 16" withRAREUIColorArray:a withInt:8 withInt:5];
    ;
  }
  return RAREColorPalette_grayPaletteGray16_;
}

+ (RAREColorPalette *)getColorPalette40 {
  if (RAREColorPalette_colorPalette40_ == nil) {
    IOSObjectArray *colors = [IOSObjectArray arrayWithObjects:(id[]){ [[RAREUIColor alloc] initWithInt:0 withInt:0 withInt:0], [[RAREUIColor alloc] initWithInt:153 withInt:51 withInt:0], [[RAREUIColor alloc] initWithInt:51 withInt:51 withInt:0], [[RAREUIColor alloc] initWithInt:0 withInt:51 withInt:0], [[RAREUIColor alloc] initWithInt:0 withInt:51 withInt:102], [[RAREUIColor alloc] initWithInt:0 withInt:0 withInt:128], [[RAREUIColor alloc] initWithInt:51 withInt:51 withInt:153], [[RAREUIColor alloc] initWithInt:51 withInt:51 withInt:51], [[RAREUIColor alloc] initWithInt:128 withInt:0 withInt:0], [[RAREUIColor alloc] initWithInt:255 withInt:102 withInt:0], [[RAREUIColor alloc] initWithInt:128 withInt:128 withInt:0], [[RAREUIColor alloc] initWithInt:0 withInt:128 withInt:0], [[RAREUIColor alloc] initWithInt:0 withInt:128 withInt:128], [[RAREUIColor alloc] initWithInt:0 withInt:0 withInt:255], [[RAREUIColor alloc] initWithInt:102 withInt:102 withInt:153], [[RAREUIColor alloc] initWithInt:128 withInt:128 withInt:128], [[RAREUIColor alloc] initWithInt:255 withInt:0 withInt:0], [[RAREUIColor alloc] initWithInt:255 withInt:153 withInt:0], [[RAREUIColor alloc] initWithInt:153 withInt:204 withInt:0], [[RAREUIColor alloc] initWithInt:51 withInt:153 withInt:102], [[RAREUIColor alloc] initWithInt:51 withInt:204 withInt:204], [[RAREUIColor alloc] initWithInt:51 withInt:102 withInt:255], [[RAREUIColor alloc] initWithInt:128 withInt:0 withInt:128], [[RAREUIColor alloc] initWithInt:153 withInt:153 withInt:153], [[RAREUIColor alloc] initWithInt:255 withInt:0 withInt:255], [[RAREUIColor alloc] initWithInt:255 withInt:204 withInt:0], [[RAREUIColor alloc] initWithInt:255 withInt:255 withInt:0], [[RAREUIColor alloc] initWithInt:0 withInt:255 withInt:0], [[RAREUIColor alloc] initWithInt:0 withInt:255 withInt:255], [[RAREUIColor alloc] initWithInt:0 withInt:204 withInt:255], [[RAREUIColor alloc] initWithInt:153 withInt:51 withInt:102], [[RAREUIColor alloc] initWithInt:192 withInt:192 withInt:192], [[RAREUIColor alloc] initWithInt:255 withInt:153 withInt:204], [[RAREUIColor alloc] initWithInt:255 withInt:204 withInt:153], [[RAREUIColor alloc] initWithInt:255 withInt:255 withInt:153], [[RAREUIColor alloc] initWithInt:204 withInt:255 withInt:204], [[RAREUIColor alloc] initWithInt:204 withInt:255 withInt:255], [[RAREUIColor alloc] initWithInt:153 withInt:204 withInt:255], [[RAREUIColor alloc] initWithInt:204 withInt:153 withInt:255], [[RAREUIColor alloc] initWithInt:255 withInt:255 withInt:255] } count:40 type:[IOSClass classWithClass:[RAREUIColor class]]];
    RAREColorPalette_colorPalette40_ = [[RAREColorPalette alloc] initWithNSString:@"40 Color" withRAREUIColorArray:[RAREColorUtils createNamedShadesWithRAREUIColorArray:colors] withInt:8 withInt:5];
  }
  return RAREColorPalette_colorPalette40_;
}

- (IOSObjectArray *)getColors {
  return colors_;
}

- (int)getColumns {
  return columns_;
}

+ (RAREColorPalette *)getGrayPalette16 {
  if (RAREColorPalette_grayPaletteGray16_ == nil) {
    IOSObjectArray *a = [IOSObjectArray arrayWithLength:16 type:[IOSClass classWithClass:[RAREUIColor class]]];
    for (int i = 0; i < 16; i++) {
      int n = i * 16;
      (void) IOSObjectArray_Set(a, i, [[RAREUIColor alloc] initWithInt:n withInt:n withInt:n]);
    }
    a = [RAREColorUtils createNamedShadesWithRAREUIColorArray:a];
    RAREColorPalette_grayPaletteGray16_ = [[RAREColorPalette alloc] initWithNSString:@"Gray 16" withRAREUIColorArray:a withInt:8 withInt:5];
    ;
  }
  return RAREColorPalette_grayPaletteGray16_;
}

- (NSString *)getName {
  return name_;
}

- (int)getRows {
  return rows_;
}

- (void)copyAllFieldsTo:(RAREColorPalette *)other {
  [super copyAllFieldsTo:other];
  other->colors_ = colors_;
  other->columns_ = columns_;
  other->name_ = name_;
  other->rows_ = rows_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "createListItemWithRAREUIColor:", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "createListItems", NULL, "LJavaUtilList", 0x1, NULL },
    { "getColorPalette16", NULL, "LRAREColorPalette", 0x9, NULL },
    { "getColorPalette40", NULL, "LRAREColorPalette", 0x9, NULL },
    { "getColors", NULL, "LIOSObjectArray", 0x1, NULL },
    { "getGrayPalette16", NULL, "LRAREColorPalette", 0x9, NULL },
    { "getName", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "colorPalette16_", NULL, 0xa, "LRAREColorPalette" },
    { "colorPalette40_", NULL, 0xa, "LRAREColorPalette" },
    { "grayPaletteGray16_", NULL, 0xa, "LRAREColorPalette" },
  };
  static J2ObjcClassInfo _RAREColorPalette = { "ColorPalette", "com.appnativa.rare.ui", NULL, 0x1, 7, methods, 3, fields, 0, NULL};
  return &_RAREColorPalette;
}

@end
