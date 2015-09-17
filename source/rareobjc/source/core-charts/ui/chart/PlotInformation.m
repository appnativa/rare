//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-charts/com/appnativa/rare/ui/chart/PlotInformation.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/UIStroke.h"
#include "com/appnativa/rare/ui/chart/PlotInformation.h"
#include "com/appnativa/rare/ui/iBorder.h"
#include "java/lang/IllegalArgumentException.h"

@implementation RAREPlotInformation

- (id)init {
  if (self = [super init]) {
    foregroundAlpha_ = 1.0f;
    lineThickness_ = [RAREScreenUtils PLATFORM_PIXELS_1];
    outlineThickness_ = [RAREScreenUtils PLATFORM_PIXELS_1];
    labelType_ = [RAREPlotInformation_LabelTypeEnum VALUES];
    shapeStyle_ = nil;
    showGridLines_ = YES;
  }
  return self;
}

- (id)initWithRAREUIColor:(RAREUIColor *)bg
          withRAREUIColor:(RAREUIColor *)gc
         withRAREUIInsets:(RAREUIInsets *)margin
         withRAREUIStroke:(RAREUIStroke *)stroke
          withRAREUIImage:(RAREUIImage *)bgimage
withRAREPlotInformation_ShapeStyleEnum:(RAREPlotInformation_ShapeStyleEnum *)ss {
  if (self = [super init]) {
    foregroundAlpha_ = 1.0f;
    lineThickness_ = [RAREScreenUtils PLATFORM_PIXELS_1];
    outlineThickness_ = [RAREScreenUtils PLATFORM_PIXELS_1];
    labelType_ = [RAREPlotInformation_LabelTypeEnum VALUES];
    shapeStyle_ = nil;
    showGridLines_ = YES;
    bgColor_ = bg;
    gridColor_ = gc;
    gridStroke_ = stroke;
    self->margin_ = margin;
    bgImage_ = bgimage;
    shapeStyle_ = ss;
  }
  return self;
}

- (void)setBackgroundColorWithRAREUIColor:(RAREUIColor *)color {
  self->bgColor_ = color;
}

- (void)setBackgroundImageWithRAREUIImage:(RAREUIImage *)bgImage {
  self->bgImage_ = bgImage;
}

- (void)setForegroundAlphaWithFloat:(float)foregroundAlpha {
  self->foregroundAlpha_ = foregroundAlpha;
}

- (void)setGridColorWithRAREUIColor:(RAREUIColor *)color {
  self->gridColor_ = color;
}

- (void)setGridStrokeWithRAREUIStroke:(RAREUIStroke *)stroke {
  self->gridStroke_ = stroke;
}

- (void)setLabelTypeWithRAREPlotInformation_LabelTypeEnum:(RAREPlotInformation_LabelTypeEnum *)type {
  self->labelType_ = type;
}

- (void)setLabelsBackgroundWithRAREUIColor:(RAREUIColor *)labelsBackground {
  self->labelsBackground_ = labelsBackground;
}

- (void)setLabelsBorderWithRAREiBorder:(id<RAREiBorder>)labelsBorder {
  self->labelsBorder_ = labelsBorder;
}

- (void)setLabelsFontWithRAREUIFont:(RAREUIFont *)labelsFont {
  self->labelsFont_ = labelsFont;
}

- (void)setLabelsForegroundWithRAREUIColor:(RAREUIColor *)labelsForeground {
  self->labelsForeground_ = labelsForeground;
}

- (void)setLabelsFormatWithNSString:(NSString *)labelsFormat {
  self->labelsFormat_ = labelsFormat;
}

- (void)setLabelsOffsetWithRAREUIPoint:(RAREUIPoint *)labelsOffset {
  self->labelsOffset_ = labelsOffset;
}

- (void)setLineThicknessWithFloat:(float)lineThickness {
  self->lineThickness_ = lineThickness;
}

- (void)setMarginWithRAREUIInsets:(RAREUIInsets *)margin {
  self->margin_ = margin;
}

- (void)setOutlineColorWithRAREUIColor:(RAREUIColor *)outlineColor {
  self->outlineColor_ = outlineColor;
}

- (void)setOutlineThicknessWithFloat:(float)outlineThickness {
  self->outlineThickness_ = outlineThickness;
}

- (void)setFillColorWithRAREUIColor:(RAREUIColor *)fillColor {
  self->fillColor_ = fillColor;
}

- (void)setShapeStyleWithRAREPlotInformation_ShapeStyleEnum:(RAREPlotInformation_ShapeStyleEnum *)style {
  self->shapeStyle_ = style;
}

- (void)setShowGridLinesWithBoolean:(BOOL)showGridLines {
  self->showGridLines_ = showGridLines;
}

- (RAREUIColor *)getBackgroundColor {
  return bgColor_;
}

- (RAREUIImage *)getBackgroundImage {
  return bgImage_;
}

- (float)getForegroundAlpha {
  return foregroundAlpha_;
}

- (RAREUIColor *)getGridColor {
  return gridColor_;
}

- (RAREUIStroke *)getGridStroke {
  return gridStroke_;
}

- (RAREPlotInformation_LabelTypeEnum *)getLabelType {
  return (labelType_ == nil) ? [RAREPlotInformation_LabelTypeEnum VALUES] : labelType_;
}

- (RAREUIColor *)getLabelsBackground {
  return labelsBackground_;
}

- (id<RAREiBorder>)getLabelsBorder {
  return labelsBorder_;
}

- (RAREUIFont *)getLabelsFont {
  return labelsFont_;
}

- (RAREUIColor *)getLabelsForeground {
  return labelsForeground_;
}

- (NSString *)getLabelsFormat {
  return labelsFormat_;
}

- (RAREUIPoint *)getLabelsOffset {
  return labelsOffset_;
}

- (float)getLineThickness {
  return lineThickness_;
}

- (RAREUIInsets *)getMargin {
  return margin_;
}

- (RAREUIColor *)getOutlineColor {
  return outlineColor_;
}

- (float)getOutlineThickness {
  return outlineThickness_;
}

- (RAREUIColor *)getFillColor {
  return fillColor_;
}

- (RAREPlotInformation_ShapeStyleEnum *)getShapeStyle {
  return (shapeStyle_ == nil) ? [RAREPlotInformation_ShapeStyleEnum FILLED] : shapeStyle_;
}

- (RAREPlotInformation_ShapeStyleEnum *)getShapeStyleEx {
  return (shapeStyle_ == nil) ? [RAREPlotInformation_ShapeStyleEnum NONE] : shapeStyle_;
}

- (BOOL)isShowGridLines {
  return showGridLines_;
}

- (RAREUIColor *)getBorderColor {
  return borderColor_;
}

- (void)setBorderColorWithRAREUIColor:(RAREUIColor *)borderColor {
  self->borderColor_ = borderColor;
}

- (void)copyAllFieldsTo:(RAREPlotInformation *)other {
  [super copyAllFieldsTo:other];
  other->bgColor_ = bgColor_;
  other->bgImage_ = bgImage_;
  other->borderColor_ = borderColor_;
  other->fillColor_ = fillColor_;
  other->foregroundAlpha_ = foregroundAlpha_;
  other->gridColor_ = gridColor_;
  other->gridStroke_ = gridStroke_;
  other->labelType_ = labelType_;
  other->labelsBackground_ = labelsBackground_;
  other->labelsBorder_ = labelsBorder_;
  other->labelsFont_ = labelsFont_;
  other->labelsForeground_ = labelsForeground_;
  other->labelsFormat_ = labelsFormat_;
  other->labelsOffset_ = labelsOffset_;
  other->lineThickness_ = lineThickness_;
  other->margin_ = margin_;
  other->outlineColor_ = outlineColor_;
  other->outlineThickness_ = outlineThickness_;
  other->shapeStyle_ = shapeStyle_;
  other->showGridLines_ = showGridLines_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getBackgroundColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "getBackgroundImage", NULL, "LRAREUIImage", 0x1, NULL },
    { "getGridColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "getGridStroke", NULL, "LRAREUIStroke", 0x1, NULL },
    { "getLabelType", NULL, "LRAREPlotInformation_LabelTypeEnum", 0x1, NULL },
    { "getLabelsBackground", NULL, "LRAREUIColor", 0x1, NULL },
    { "getLabelsBorder", NULL, "LRAREiBorder", 0x1, NULL },
    { "getLabelsFont", NULL, "LRAREUIFont", 0x1, NULL },
    { "getLabelsForeground", NULL, "LRAREUIColor", 0x1, NULL },
    { "getLabelsFormat", NULL, "LNSString", 0x1, NULL },
    { "getLabelsOffset", NULL, "LRAREUIPoint", 0x1, NULL },
    { "getMargin", NULL, "LRAREUIInsets", 0x1, NULL },
    { "getOutlineColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "getFillColor", NULL, "LRAREUIColor", 0x1, NULL },
    { "getShapeStyle", NULL, "LRAREPlotInformation_ShapeStyleEnum", 0x1, NULL },
    { "getShapeStyleEx", NULL, "LRAREPlotInformation_ShapeStyleEnum", 0x1, NULL },
    { "isShowGridLines", NULL, "Z", 0x1, NULL },
    { "getBorderColor", NULL, "LRAREUIColor", 0x1, NULL },
  };
  static J2ObjcClassInfo _RAREPlotInformation = { "PlotInformation", "com.appnativa.rare.ui.chart", NULL, 0x1, 18, methods, 0, NULL, 0, NULL};
  return &_RAREPlotInformation;
}

@end

static RAREPlotInformation_LabelTypeEnum *RAREPlotInformation_LabelTypeEnum_VALUES;
static RAREPlotInformation_LabelTypeEnum *RAREPlotInformation_LabelTypeEnum_TOOLTIPS;
static RAREPlotInformation_LabelTypeEnum *RAREPlotInformation_LabelTypeEnum_LINKED_DATA;
IOSObjectArray *RAREPlotInformation_LabelTypeEnum_values;

@implementation RAREPlotInformation_LabelTypeEnum

+ (RAREPlotInformation_LabelTypeEnum *)VALUES {
  return RAREPlotInformation_LabelTypeEnum_VALUES;
}
+ (RAREPlotInformation_LabelTypeEnum *)TOOLTIPS {
  return RAREPlotInformation_LabelTypeEnum_TOOLTIPS;
}
+ (RAREPlotInformation_LabelTypeEnum *)LINKED_DATA {
  return RAREPlotInformation_LabelTypeEnum_LINKED_DATA;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREPlotInformation_LabelTypeEnum class]) {
    RAREPlotInformation_LabelTypeEnum_VALUES = [[RAREPlotInformation_LabelTypeEnum alloc] initWithNSString:@"VALUES" withInt:0];
    RAREPlotInformation_LabelTypeEnum_TOOLTIPS = [[RAREPlotInformation_LabelTypeEnum alloc] initWithNSString:@"TOOLTIPS" withInt:1];
    RAREPlotInformation_LabelTypeEnum_LINKED_DATA = [[RAREPlotInformation_LabelTypeEnum alloc] initWithNSString:@"LINKED_DATA" withInt:2];
    RAREPlotInformation_LabelTypeEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREPlotInformation_LabelTypeEnum_VALUES, RAREPlotInformation_LabelTypeEnum_TOOLTIPS, RAREPlotInformation_LabelTypeEnum_LINKED_DATA, nil } count:3 type:[IOSClass classWithClass:[RAREPlotInformation_LabelTypeEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREPlotInformation_LabelTypeEnum_values];
}

+ (RAREPlotInformation_LabelTypeEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREPlotInformation_LabelTypeEnum_values count]; i++) {
    RAREPlotInformation_LabelTypeEnum *e = RAREPlotInformation_LabelTypeEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREPlotInformation_LabelTypeEnum"};
  static J2ObjcClassInfo _RAREPlotInformation_LabelTypeEnum = { "LabelType", "com.appnativa.rare.ui.chart", "PlotInformation", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREPlotInformation_LabelTypeEnum;
}

@end

static RAREPlotInformation_ShapeStyleEnum *RAREPlotInformation_ShapeStyleEnum_NONE;
static RAREPlotInformation_ShapeStyleEnum *RAREPlotInformation_ShapeStyleEnum_FILLED;
static RAREPlotInformation_ShapeStyleEnum *RAREPlotInformation_ShapeStyleEnum_OUTLINED;
static RAREPlotInformation_ShapeStyleEnum *RAREPlotInformation_ShapeStyleEnum_FILLED_AND_OUTLINED;
IOSObjectArray *RAREPlotInformation_ShapeStyleEnum_values;

@implementation RAREPlotInformation_ShapeStyleEnum

+ (RAREPlotInformation_ShapeStyleEnum *)NONE {
  return RAREPlotInformation_ShapeStyleEnum_NONE;
}
+ (RAREPlotInformation_ShapeStyleEnum *)FILLED {
  return RAREPlotInformation_ShapeStyleEnum_FILLED;
}
+ (RAREPlotInformation_ShapeStyleEnum *)OUTLINED {
  return RAREPlotInformation_ShapeStyleEnum_OUTLINED;
}
+ (RAREPlotInformation_ShapeStyleEnum *)FILLED_AND_OUTLINED {
  return RAREPlotInformation_ShapeStyleEnum_FILLED_AND_OUTLINED;
}

- (id)copyWithZone:(NSZone *)zone {
  return self;
}

- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal {
  return [super initWithNSString:__name withInt:__ordinal];
}

+ (void)initialize {
  if (self == [RAREPlotInformation_ShapeStyleEnum class]) {
    RAREPlotInformation_ShapeStyleEnum_NONE = [[RAREPlotInformation_ShapeStyleEnum alloc] initWithNSString:@"NONE" withInt:0];
    RAREPlotInformation_ShapeStyleEnum_FILLED = [[RAREPlotInformation_ShapeStyleEnum alloc] initWithNSString:@"FILLED" withInt:1];
    RAREPlotInformation_ShapeStyleEnum_OUTLINED = [[RAREPlotInformation_ShapeStyleEnum alloc] initWithNSString:@"OUTLINED" withInt:2];
    RAREPlotInformation_ShapeStyleEnum_FILLED_AND_OUTLINED = [[RAREPlotInformation_ShapeStyleEnum alloc] initWithNSString:@"FILLED_AND_OUTLINED" withInt:3];
    RAREPlotInformation_ShapeStyleEnum_values = [[IOSObjectArray alloc] initWithObjects:(id[]){ RAREPlotInformation_ShapeStyleEnum_NONE, RAREPlotInformation_ShapeStyleEnum_FILLED, RAREPlotInformation_ShapeStyleEnum_OUTLINED, RAREPlotInformation_ShapeStyleEnum_FILLED_AND_OUTLINED, nil } count:4 type:[IOSClass classWithClass:[RAREPlotInformation_ShapeStyleEnum class]]];
  }
}

+ (IOSObjectArray *)values {
  return [IOSObjectArray arrayWithArray:RAREPlotInformation_ShapeStyleEnum_values];
}

+ (RAREPlotInformation_ShapeStyleEnum *)valueOfWithNSString:(NSString *)name {
  for (int i = 0; i < [RAREPlotInformation_ShapeStyleEnum_values count]; i++) {
    RAREPlotInformation_ShapeStyleEnum *e = RAREPlotInformation_ShapeStyleEnum_values->buffer_[i];
    if ([name isEqual:[e name]]) {
      return e;
    }
  }
  @throw [[JavaLangIllegalArgumentException alloc] initWithNSString:name];
  return nil;
}

+ (J2ObjcClassInfo *)__metadata {
  static const char *superclass_type_args[] = {"LRAREPlotInformation_ShapeStyleEnum"};
  static J2ObjcClassInfo _RAREPlotInformation_ShapeStyleEnum = { "ShapeStyle", "com.appnativa.rare.ui.chart", "PlotInformation", 0x4019, 0, NULL, 0, NULL, 1, superclass_type_args};
  return &_RAREPlotInformation_ShapeStyleEnum;
}

@end
