//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/viewer/aGridPaneViewer.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSBooleanArray.h"
#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/spot/CollapsibleInfo.h"
#include "com/appnativa/rare/spot/GridPane.h"
#include "com/appnativa/rare/spot/Rectangle.h"
#include "com/appnativa/rare/spot/Region.h"
#include "com/appnativa/rare/spot/ScrollPane.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/rare/spot/Widget.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/Utils.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/iCollapsible.h"
#include "com/appnativa/rare/ui/iFormsPanel.h"
#include "com/appnativa/rare/ui/iParentComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/viewer/aGridPaneViewer.h"
#include "com/appnativa/rare/viewer/aGroupBoxViewer.h"
#include "com/appnativa/rare/viewer/aPlatformRegionViewer.h"
#include "com/appnativa/rare/viewer/aRegionViewer.h"
#include "com/appnativa/rare/viewer/aViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/viewer/iTarget.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSet.h"
#include "com/jgoodies/forms/layout/CellConstraints.h"
#include "com/jgoodies/forms/layout/ColumnSpec.h"
#include "com/jgoodies/forms/layout/ConstantSize.h"
#include "com/jgoodies/forms/layout/FormLayout.h"
#include "com/jgoodies/forms/layout/FormSpec.h"
#include "com/jgoodies/forms/layout/RowSpec.h"
#include "com/jgoodies/forms/layout/Sizes.h"
#include "java/lang/Integer.h"
#include "java/util/ArrayList.h"
#include "java/util/Collections.h"
#include "java/util/HashMap.h"
#include "java/util/Map.h"

@implementation RAREaGridPaneViewer

static RAREJGColumnSpec * RAREaGridPaneViewer_ZERO_COL_SPEC_;
static RAREJGRowSpec * RAREaGridPaneViewer_ZERO_ROW_SPEC_;

+ (RAREJGColumnSpec *)ZERO_COL_SPEC {
  return RAREaGridPaneViewer_ZERO_COL_SPEC_;
}

+ (void)setZERO_COL_SPEC:(RAREJGColumnSpec *)ZERO_COL_SPEC {
  RAREaGridPaneViewer_ZERO_COL_SPEC_ = ZERO_COL_SPEC;
}

+ (RAREJGRowSpec *)ZERO_ROW_SPEC {
  return RAREaGridPaneViewer_ZERO_ROW_SPEC_;
}

+ (void)setZERO_ROW_SPEC:(RAREJGRowSpec *)ZERO_ROW_SPEC {
  RAREaGridPaneViewer_ZERO_ROW_SPEC_ = ZERO_ROW_SPEC;
}

- (id)init {
  return [self initRAREaGridPaneViewerWithRAREiContainer:nil];
}

- (id)initRAREaGridPaneViewerWithRAREiContainer:(id<RAREiContainer>)parent {
  if (self = [super initWithRAREiContainer:parent]) {
    targetID_ = 0;
    widgetType_ = [RAREiWidget_WidgetTypeEnum GridPane];
  }
  return self;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [self initRAREaGridPaneViewerWithRAREiContainer:parent];
}

- (IOSObjectArray *)addColumnWithBoolean:(BOOL)horizPreferred
                             withBoolean:(BOOL)vertPreferred
             withRARESPOTCollapsibleInfo:(RARESPOTCollapsibleInfo *)cinfo {
  int col = columnCount_ * 2 - 1;
  if (columnCount_ > 0) {
    [((id<RAREiFormsPanel>) nil_chk(gridPanel_)) addSpacerColumnWithInt:columnSpacing_];
    col++;
  }
  if (rowCount_ == 0) {
    [((id<RAREiFormsPanel>) nil_chk(gridPanel_)) addRowWithInt:0 withNSString:@"FILL:DEFAULT:GROW(1.0)"];
    rowCount_ = 1;
  }
  if (col < 0) {
    col = 0;
  }
  [((id<RAREiFormsPanel>) nil_chk(gridPanel_)) addColumnWithInt:col withNSString:@"FILL:DEFAULT:GROW(1.0)"];
  RAREJGCellConstraints *cc;
  NSString *name;
  IOSObjectArray *t = [IOSObjectArray arrayWithLength:rowCount_ type:[IOSClass classWithProtocol:@protocol(RAREiTarget)]];
  id<RAREiParentComponent> panel;
  for (int i = 0; i < rowCount_; i++) {
    cc = [gridPanel_ createConstraintsWithInt:i * 2 withInt:col withInt:1 withInt:1];
    if (!horizPreferred) {
      ((RAREJGCellConstraints *) nil_chk(cc))->hAlign_ = [RAREJGCellConstraints FILL];
    }
    if (!vertPreferred) {
      ((RAREJGCellConstraints *) nil_chk(cc))->vAlign_ = [RAREJGCellConstraints FILL];
    }
    name = [self generateTargetNameWithNSString:[NSString stringWithFormat:@"view#%d", targetID_++]];
    panel = [self createPanelWithRARESPOTCollapsibleInfo:cinfo];
    (void) IOSObjectArray_Set(t, i, [self createTargetWithNSString:name withRAREiParentComponent:panel]);
    [gridPanel_ addWithRAREiPlatformComponent:panel withId:cc];
  }
  columnCount_++;
  return t;
}

- (void)setRegionVerticalFillWithInt:(int)pos
                         withBoolean:(BOOL)preferred {
  id<RAREiTarget> t = [self getRegionWithInt:pos];
  RAREJGCellConstraints *cc = [((id<RAREiFormsPanel>) nil_chk(gridPanel_)) getCellConstraintsWithRAREiPlatformComponent:[((id<RAREiTarget>) nil_chk(t)) getContainerComponent]];
  if (cc != nil) {
    [gridPanel_ setRowSpecWithInt:cc->gridY_ - 1 withNSString:preferred ? @"FILL:DEFAULT:NONE" : @"FILL:DEFAULT:GROW"];
  }
}

- (void)setRegionHorizontalFillWithInt:(int)pos
                           withBoolean:(BOOL)preferred {
  id<RAREiTarget> t = [self getRegionWithInt:pos];
  RAREJGCellConstraints *cc = [((id<RAREiFormsPanel>) nil_chk(gridPanel_)) getCellConstraintsWithRAREiPlatformComponent:[((id<RAREiTarget>) nil_chk(t)) getContainerComponent]];
  if (cc != nil) {
    [gridPanel_ setColumnSpecWithInt:cc->gridX_ - 1 withNSString:preferred ? @"FILL:DEFAULT:NONE" : @"FILL:DEFAULT:GROW"];
  }
}

- (IOSObjectArray *)addRowWithBoolean:(BOOL)horizPreferred
                          withBoolean:(BOOL)vertPreferred
          withRARESPOTCollapsibleInfo:(RARESPOTCollapsibleInfo *)cinfo {
  int row = rowCount_ * 2 - 1;
  if (columnCount_ == 0) {
    [((id<RAREiFormsPanel>) nil_chk(gridPanel_)) addColumnWithInt:1 withNSString:@"FILL:DEFAULT:GROW"];
    columnCount_ = 1;
  }
  if (rowCount_ > 0) {
    [((id<RAREiFormsPanel>) nil_chk(gridPanel_)) addSpacerRowWithInt:rowSpacing_];
    row++;
  }
  if (row < 0) {
    row = 0;
  }
  [((id<RAREiFormsPanel>) nil_chk(gridPanel_)) addRowWithInt:row withNSString:@"FILL:DEFAULT:GROW"];
  RAREJGCellConstraints *cc;
  NSString *name;
  IOSObjectArray *t = [IOSObjectArray arrayWithLength:columnCount_ type:[IOSClass classWithProtocol:@protocol(RAREiTarget)]];
  id<RAREiParentComponent> panel;
  for (int i = 0; i < columnCount_; i++) {
    cc = [gridPanel_ createConstraintsWithInt:row withInt:i * 2 withInt:1 withInt:1];
    if (!horizPreferred) {
      ((RAREJGCellConstraints *) nil_chk(cc))->hAlign_ = [RAREJGCellConstraints FILL];
    }
    if (!vertPreferred) {
      ((RAREJGCellConstraints *) nil_chk(cc))->hAlign_ = [RAREJGCellConstraints FILL];
    }
    name = [self generateTargetNameWithNSString:[NSString stringWithFormat:@"view#%d", targetID_++]];
    panel = [self createPanelWithRARESPOTCollapsibleInfo:cinfo];
    [((id<RAREiParentComponent>) nil_chk(panel)) setOpaqueWithBoolean:NO];
    (void) IOSObjectArray_Set(t, i, [self createTargetWithNSString:name withRAREiParentComponent:panel]);
    [gridPanel_ addWithRAREiPlatformComponent:panel withId:cc];
  }
  rowCount_++;
  return t;
}

- (void)clearContents {
  [self clearContentsWithBoolean:NO];
}

- (void)clearGridWithBoolean:(BOOL)disposeViewers {
  [self clearConfigurationExWithBoolean:disposeViewers];
  [((id<RAREiFormsPanel>) nil_chk(gridPanel_)) removeAll];
  [gridPanel_ setLayoutWithNSString:@"d" withNSString:@"d"];
  rowCount_ = 0;
  columnCount_ = 0;
}

- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg {
  [self configureExWithRARESPOTGridPane:(RARESPOTGridPane *) check_class_cast(vcfg, [RARESPOTGridPane class])];
  [self fireConfigureEventWithRARESPOTWidget:vcfg withNSString:[RAREiConstants EVENT_CONFIGURE]];
}

- (void)removeColumnWithInt:(int)col
                withBoolean:(BOOL)disposeViewers {
  if ((col < 0) || (col >= columnCount_)) {
    return;
  }
  col = col * 2 + 1;
  id<RAREiPlatformComponent> c;
  IOSObjectArray *t = [IOSObjectArray arrayWithLength:rowCount_ type:[IOSClass classWithProtocol:@protocol(RAREiTarget)]];
  for (int i = 0; i < rowCount_; i++) {
    c = [((id<RAREiFormsPanel>) nil_chk(gridPanel_)) getGridComponentAtWithInt:col withInt:i * 2 + 1];
    if (c != nil) {
      (void) IOSObjectArray_Set(t, i, [RAREUtils getTargetForComponentWithRAREiPlatformComponent:c]);
    }
  }
  col = (col - 1) / 2;
  [((id<RAREiFormsPanel>) nil_chk(gridPanel_)) removeColumnWithInt:col];
  for (int i = 0; i < rowCount_; i++) {
    if (IOSObjectArray_Get(t, i) != nil) {
      [self removeTargetWithRAREiTarget:IOSObjectArray_Get(t, i)];
      [((id<RAREiTarget>) IOSObjectArray_Get(t, i)) disposeWithBoolean:disposeViewers];
    }
  }
}

- (void)removeRowWithInt:(int)row
             withBoolean:(BOOL)disposeViewers {
  if ((row < 0) || (row >= rowCount_)) {
    return;
  }
  row = row * 2 + 1;
  id<RAREiPlatformComponent> c;
  IOSObjectArray *t = [IOSObjectArray arrayWithLength:columnCount_ type:[IOSClass classWithProtocol:@protocol(RAREiTarget)]];
  for (int i = 0; i < columnCount_; i++) {
    c = [((id<RAREiFormsPanel>) nil_chk(gridPanel_)) getGridComponentAtWithInt:i * 2 + 1 withInt:row];
    if (c != nil) {
      (void) IOSObjectArray_Set(t, i, [RAREUtils getTargetForComponentWithRAREiPlatformComponent:c]);
    }
  }
  for (int i = 0; i < columnCount_; i++) {
    if (IOSObjectArray_Get(t, i) != nil) {
      [self removeTargetWithRAREiTarget:IOSObjectArray_Get(t, i)];
      [((id<RAREiTarget>) IOSObjectArray_Get(t, i)) disposeWithBoolean:disposeViewers];
    }
  }
  row = (row - 1) / 2;
  [((id<RAREiFormsPanel>) nil_chk(gridPanel_)) removeRowWithInt:row];
}

- (int)getColumnCount {
  return columnCount_;
}

- (int)getRowCount {
  return rowCount_;
}

- (void)clearConfigurationWithBoolean:(BOOL)dispose {
  [super clearConfigurationWithBoolean:YES];
  if (dispose) {
    if (savedColumnSpecs_ != nil) {
      [savedColumnSpecs_ clear];
    }
    if (savedRowSpecs_ != nil) {
      [savedRowSpecs_ clear];
    }
    gridPanel_ = nil;
    savedColumnSpecs_ = nil;
    savedRowSpecs_ = nil;
  }
}

- (void)configureCollapsibleEventsWithRAREiCollapsible:(id<RAREiCollapsible>)pane
                           withRARESPOTCollapsibleInfo:(RARESPOTCollapsibleInfo *)info {
  id<JavaUtilMap> map = [RAREaWidgetListener createEventMapWithJavaUtilMap:[((RARESPOTCollapsibleInfo *) nil_chk(info)) spot_getAttributesEx]];
  if ((map != nil) && ([map size] > 0)) {
    RAREaRegionViewer_CollapsibleListener *l = [[RAREaRegionViewer_CollapsibleListener alloc] initWithRAREiWidget:self withJavaUtilMap:map];
    [((id<RAREiCollapsible>) nil_chk(pane)) addExpandedListenerWithRAREiExpandedListener:l];
    [pane addExpansionListenerWithRAREiExpansionListener:l];
  }
}

- (id<RAREiFormsPanel>)createFormsPanel {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)configureExWithRARESPOTGridPane:(RARESPOTGridPane *)cfg {
  gridPanel_ = [self createFormsPanel];
  [((id<RAREiFormsPanel>) nil_chk(gridPanel_)) setTableLayoutWithBoolean:YES];
  formComponent_ = dataComponent_ = [gridPanel_ getComponent];
  actAsFormViewer_ = [((SPOTBoolean *) nil_chk(((RARESPOTGridPane *) nil_chk(cfg))->actAsFormViewer_)) booleanValue];
  [self configureExWithRARESPOTViewer:cfg withBoolean:YES withBoolean:NO withBoolean:YES];
  IOSObjectArray *rg = [RAREaGroupBoxViewer createGroupingWithSPOTSet:[cfg getRowGrouping]];
  IOSObjectArray *cg = [RAREaGroupBoxViewer createGroupingWithSPOTSet:[cfg getColumnGrouping]];
  if ([cfg getScrollPane] != nil) {
    formComponent_ = [RAREaPlatformHelper makeScrollPaneWithRAREaViewer:self withRARESPOTScrollPane:[cfg getScrollPane] withRAREiPlatformComponent:formComponent_];
    [((id<RAREiPlatformComponent>) nil_chk(dataComponent_)) setOpaqueWithBoolean:NO];
  }
  isSubmittable__ = YES;
  int len = [((SPOTSet *) nil_chk(cfg->regions_)) getCount];
  RARESPOTRegion *region;
  id<RAREiParentComponent> panel;
  RAREJGCellConstraints *cc;
  NSString *name;
  JavaUtilArrayList *layoutObject = [[JavaUtilArrayList alloc] initWithInt:len];
  RAREaGroupBoxViewer_ComponentCC *ccc = [[RAREaGroupBoxViewer_ComponentCC alloc] initWithRAREiPlatformComponent:nil withRAREJGCellConstraints:nil];
  ccc->tas_ = [[RAREUIRectangle alloc] initWithFloat:[((SPOTInteger *) nil_chk(cfg->columns_)) intValue] withFloat:[((SPOTInteger *) nil_chk(cfg->rows_)) intValue]];
  ccc->tas_->width_ = (ccc->tas_->width_ < 1) ? 1 : ccc->tas_->width_;
  ccc->tas_->height_ = (ccc->tas_->height_ < 1) ? 1 : ccc->tas_->height_;
  [layoutObject addWithId:ccc];
  BOOL dm = [self isDesignMode];
  RARESPOTWidget *wc = [[RARESPOTWidget alloc] init];
  if (dm && (len == 0)) {
    len = 1;
    [cfg->regions_ addWithISPOTElement:[[RARESPOTRegion alloc] init]];
  }
  [JavaUtilCollections sortWithJavaUtilList:cfg->regions_ withJavaUtilComparator:[[RAREaRegionViewer_RegionSorter alloc] init]];
  for (int i = 0; i < len; i++) {
    region = (RARESPOTRegion *) check_class_cast([cfg->regions_ getWithInt:i], [RARESPOTRegion class]);
    name = dm ? nil : [((SPOTPrintableString *) nil_chk(((RARESPOTRegion *) nil_chk(region))->name_)) getValue];
    RARESPOTCollapsibleInfo *cinfo = [((RARESPOTRegion *) nil_chk(region)) getCollapsibleInfo];
    panel = [self createPanelWithRARESPOTCollapsibleInfo:cinfo];
    [((RARESPOTRectangle *) nil_chk(wc->bounds_)) setValuesWithRARESPOTRectangle:region];
    [self registerWithWidgetWithRAREiPlatformComponent:panel];
    switch ([((RARESPOTRegion_CVerticalFill *) nil_chk(region->verticalFill_)) intValue]) {
      case RARESPOTRegion_CHorizontalFill_maximum:
      [((RARESPOTWidget_CVerticalAlign *) nil_chk(wc->verticalAlign_)) setValueWithInt:RARESPOTWidget_CVerticalAlign_full];
      break;
      default:
      [((RARESPOTWidget_CVerticalAlign *) nil_chk(wc->verticalAlign_)) setValueWithInt:RARESPOTWidget_CVerticalAlign_auto];
      break;
    }
    switch ([((RARESPOTRegion_CHorizontalFill *) nil_chk(region->horizontalFill_)) intValue]) {
      case RARESPOTRegion_CHorizontalFill_maximum:
      [((RARESPOTWidget_CHorizontalAlign *) nil_chk(wc->horizontalAlign_)) setValueWithInt:RARESPOTWidget_CHorizontalAlign_full];
      break;
      default:
      [((RARESPOTWidget_CHorizontalAlign *) nil_chk(wc->horizontalAlign_)) setValueWithInt:RARESPOTWidget_CHorizontalAlign_auto];
      break;
    }
    if (name == nil) {
      name = [self generateTargetNameWithRARESPOTRegion:region];
    }
    cc = [RAREaGroupBoxViewer addTableWidgetWithRAREiWidget:nil withRAREiPlatformComponent:[((id<RAREiTarget>) nil_chk([self createTargetWithNSString:name withRAREiParentComponent:panel withRARESPOTRegion:region])) getContainerComponent] withRARESPOTWidget:wc withJavaUtilArrayList:layoutObject];
    if (dm && (cc != nil)) {
      [((SPOTPrintableString *) nil_chk(region->x_)) setValueWithLong:cc->gridX_ - 1];
      [((SPOTPrintableString *) nil_chk(region->y_)) setValueWithLong:cc->gridY_ - 1];
    }
  }
  columnSpacing_ = [RAREScreenUtils platformPixelsWithFloat:[((SPOTInteger *) nil_chk(cfg->columnSpacing_)) intValue]];
  rowSpacing_ = [RAREScreenUtils platformPixelsWithFloat:[((SPOTInteger *) nil_chk(cfg->rowSpacing_)) intValue]];
  RAREUIPoint *p = [RAREaGroupBoxViewer addTableWidgetsWithRAREiFormsPanel:gridPanel_ withJavaUtilArrayList:layoutObject withBoolean:YES];
  [gridPanel_ addRowSpacingWithInt:rowSpacing_];
  [gridPanel_ addColumnSpacingWithInt:columnSpacing_];
  rowCount_ = (int) ((RAREUIPoint *) nil_chk(p))->y_;
  columnCount_ = (int) p->x_;
  if (rg != nil) {
    [self fixGroupingsWithIntArray2:rg];
    [((RAREJGFormLayout *) nil_chk([gridPanel_ getFormLayout])) setRowGroupsWithIntArray2:rg];
  }
  if (cg != nil) {
    [self fixGroupingsWithIntArray2:cg];
    [((RAREJGFormLayout *) nil_chk([gridPanel_ getFormLayout])) setColumnGroupsWithIntArray2:cg];
  }
  if (dm) {
    if ([cfg->rows_ intValue] < p->y_) {
      [cfg->rows_ setValueWithDouble:p->y_];
    }
    if ([cfg->columns_ intValue] < p->x_) {
      [cfg->columns_ setValueWithDouble:p->x_];
    }
  }
  id<RAREiTarget> t;
  len = [self getRegionCount];
  for (int i = 0; i < len; i++) {
    t = [self getRegionWithInt:i];
    if (![((id<RAREiTarget>) nil_chk(t)) isVisible]) {
      [self targetHiddenWithRAREiTarget:t];
    }
  }
}

- (void)targetHiddenWithRAREiTarget:(id<RAREiTarget>)t {
  RAREJGCellConstraints *cc = [((id<RAREiFormsPanel>) nil_chk(gridPanel_)) getCellConstraintsWithRAREiPlatformComponent:[((id<RAREiTarget>) nil_chk(t)) getContainerComponent]];
  if (cc == nil) {
    return;
  }
  int row = ((RAREJGCellConstraints *) nil_chk(cc))->gridY_;
  int col = cc->gridX_;
  IOSBooleanArray *b = [gridPanel_ isColumnRowComponentsHiddenWithInt:col withInt:row];
  if (IOSBooleanArray_Get(nil_chk(b), 0)) {
    if (savedRowSpecs_ == nil) {
      savedRowSpecs_ = [[JavaUtilHashMap alloc] initWithInt:3];
    }
    if ([((JavaUtilHashMap *) nil_chk(savedRowSpecs_)) getWithId:[JavaLangInteger valueOfWithInt:row]] == nil) {
      (void) [savedRowSpecs_ putWithId:[JavaLangInteger valueOfWithInt:row] withId:[((RAREJGFormLayout *) nil_chk([gridPanel_ getFormLayout])) getRowSpecWithInt:row]];
    }
    [((RAREJGFormLayout *) nil_chk([gridPanel_ getFormLayout])) setRowSpecWithInt:row withRAREJGRowSpec:RAREaGridPaneViewer_ZERO_ROW_SPEC_];
  }
  if (IOSBooleanArray_Get(b, 1)) {
    if (savedColumnSpecs_ == nil) {
      savedColumnSpecs_ = [[JavaUtilHashMap alloc] initWithInt:3];
    }
    if ([((JavaUtilHashMap *) nil_chk(savedColumnSpecs_)) getWithId:[JavaLangInteger valueOfWithInt:col]] == nil) {
      (void) [savedColumnSpecs_ putWithId:[JavaLangInteger valueOfWithInt:col] withId:[((RAREJGFormLayout *) nil_chk([gridPanel_ getFormLayout])) getColumnSpecWithInt:col]];
    }
    [((RAREJGFormLayout *) nil_chk([gridPanel_ getFormLayout])) setColumnSpecWithInt:col withRAREJGColumnSpec:RAREaGridPaneViewer_ZERO_COL_SPEC_];
  }
}

- (void)targetShownWithRAREiTarget:(id<RAREiTarget>)t {
  RAREJGCellConstraints *cc = [((id<RAREiFormsPanel>) nil_chk(gridPanel_)) getCellConstraintsWithRAREiPlatformComponent:[((id<RAREiTarget>) nil_chk(t)) getContainerComponent]];
  if (cc == nil) {
    return;
  }
  int row = ((RAREJGCellConstraints *) nil_chk(cc))->gridY_;
  int col = cc->gridX_;
  IOSBooleanArray *b = [gridPanel_ isColumnRowComponentsHiddenWithInt:col withInt:row];
  if (!IOSBooleanArray_Get(nil_chk(b), 0)) {
    RAREJGRowSpec *spec = (savedRowSpecs_ == nil) ? nil : [savedRowSpecs_ getWithId:[JavaLangInteger valueOfWithInt:row]];
    if (spec != nil) {
      [((RAREJGFormLayout *) nil_chk([gridPanel_ getFormLayout])) setRowSpecWithInt:row withRAREJGRowSpec:spec];
    }
  }
  if (!IOSBooleanArray_Get(b, 1)) {
    RAREJGColumnSpec *spec = (savedColumnSpecs_ == nil) ? nil : [savedColumnSpecs_ getWithId:[JavaLangInteger valueOfWithInt:col]];
    if (spec != nil) {
      [((RAREJGFormLayout *) nil_chk([gridPanel_ getFormLayout])) setColumnSpecWithInt:col withRAREJGColumnSpec:spec];
    }
  }
  [self update];
}

- (void)targetVisibilityChangedWithRAREiTarget:(id<RAREiTarget>)t
                                   withBoolean:(BOOL)visibile {
  if (visibile) {
    [self targetShownWithRAREiTarget:t];
  }
  else {
    [self targetHiddenWithRAREiTarget:t];
  }
}

- (void)fixGroupWithIntArray:(IOSIntArray *)g {
  if (g != nil) {
    for (int i = 0; i < (int) [g count]; i++) {
      (*IOSIntArray_GetRef(g, i)) = ((IOSIntArray_Get(g, i) - 1) * 2) + 1;
    }
  }
}

- (void)fixGroupingsWithIntArray2:(IOSObjectArray *)g {
  if (g != nil) {
    for (int i = 0; i < (int) [g count]; i++) {
      [self fixGroupWithIntArray:IOSObjectArray_Get(g, i)];
    }
  }
}

- (NSString *)generateTargetNameWithRARESPOTRegion:(RARESPOTRegion *)r {
  return [self generateTargetNameWithNSString:[NSString stringWithFormat:@"view#%d", targetID_++]];
}

+ (void)initialize {
  if (self == [RAREaGridPaneViewer class]) {
    RAREaGridPaneViewer_ZERO_COL_SPEC_ = [[RAREJGColumnSpec alloc] initWithRAREJGFormSpec_DefaultAlignment:[RAREJGColumnSpec DEFAULT] withRAREJGSize:[RAREJGSizes ZERO] withDouble:0];
    RAREaGridPaneViewer_ZERO_ROW_SPEC_ = [[RAREJGRowSpec alloc] initWithRAREJGFormSpec_DefaultAlignment:[RAREJGRowSpec DEFAULT] withRAREJGSize:[RAREJGSizes ZERO] withDouble:0];
  }
}

- (void)copyAllFieldsTo:(RAREaGridPaneViewer *)other {
  [super copyAllFieldsTo:other];
  other->columnSpacing_ = columnSpacing_;
  other->gridPanel_ = gridPanel_;
  other->rowCount_ = rowCount_;
  other->rowSpacing_ = rowSpacing_;
  other->savedColumnSpecs_ = savedColumnSpecs_;
  other->savedRowSpecs_ = savedRowSpecs_;
  other->targetID_ = targetID_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addColumnWithBoolean:withBoolean:withRARESPOTCollapsibleInfo:", NULL, "LIOSObjectArray", 0x1, NULL },
    { "addRowWithBoolean:withBoolean:withRARESPOTCollapsibleInfo:", NULL, "LIOSObjectArray", 0x1, NULL },
    { "clearConfigurationWithBoolean:", NULL, "V", 0x4, NULL },
    { "configureCollapsibleEventsWithRAREiCollapsible:withRARESPOTCollapsibleInfo:", NULL, "V", 0x4, NULL },
    { "createFormsPanel", NULL, "LRAREiFormsPanel", 0x404, NULL },
    { "configureExWithRARESPOTGridPane:", NULL, "V", 0x4, NULL },
    { "targetHiddenWithRAREiTarget:", NULL, "V", 0x4, NULL },
    { "targetShownWithRAREiTarget:", NULL, "V", 0x4, NULL },
    { "targetVisibilityChangedWithRAREiTarget:withBoolean:", NULL, "V", 0x4, NULL },
    { "fixGroupWithIntArray:", NULL, "V", 0x2, NULL },
    { "fixGroupingsWithIntArray2:", NULL, "V", 0x2, NULL },
    { "generateTargetNameWithRARESPOTRegion:", NULL, "LNSString", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "ZERO_COL_SPEC_", NULL, 0xa, "LRAREJGColumnSpec" },
    { "ZERO_ROW_SPEC_", NULL, 0xa, "LRAREJGRowSpec" },
    { "columnSpacing_", NULL, 0x4, "I" },
    { "rowSpacing_", NULL, 0x4, "I" },
    { "targetID_", NULL, 0x4, "I" },
    { "gridPanel_", NULL, 0x4, "LRAREiFormsPanel" },
    { "rowCount_", NULL, 0x4, "I" },
    { "savedColumnSpecs_", NULL, 0x4, "LJavaUtilHashMap" },
    { "savedRowSpecs_", NULL, 0x4, "LJavaUtilHashMap" },
  };
  static J2ObjcClassInfo _RAREaGridPaneViewer = { "aGridPaneViewer", "com.appnativa.rare.viewer", NULL, 0x401, 12, methods, 9, fields, 0, NULL};
  return &_RAREaGridPaneViewer;
}

@end
