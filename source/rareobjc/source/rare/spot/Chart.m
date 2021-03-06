//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Chart.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/spot/Chart.h"
#include "com/appnativa/rare/spot/DataItem.h"
#include "com/appnativa/rare/spot/EmptyText.h"
#include "com/appnativa/rare/spot/ItemDescription.h"
#include "com/appnativa/rare/spot/Plot.h"
#include "com/appnativa/rare/spot/ScrollPane.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/SPOTSet.h"
#include "com/appnativa/spot/aSPOTElement.h"
#include "com/appnativa/spot/iSPOTElement.h"
#include "java/lang/Boolean.h"
#include "java/lang/ClassCastException.h"
#include "java/lang/Integer.h"

@implementation RARESPOTChart

- (id)init {
  return [self initRARESPOTChartWithBoolean:YES];
}

- (id)initRARESPOTChartWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    chartType_ = [[RARESPOTChart_CChartType alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTChart_CChartType_line] withNSString:@"line" withBoolean:NO];
    horizontal_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showLegends_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    showTooltips_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    showPlotLabels_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    draw3D_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    zoomingAllowed_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    domainAxis_ = [[RARESPOTDataItem alloc] init];
    rangeAxis_ = [[RARESPOTDataItem alloc] init];
    chartTitle_ = nil;
    autoSort_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    emptyText_ = nil;
    plot_ = nil;
    subTitles_ = nil;
    annotations_ = nil;
    rangeMarkers_ = nil;
    domainMarkers_ = nil;
    scrollPane_ = nil;
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTChartWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    chartType_ = [[RARESPOTChart_CChartType alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTChart_CChartType_line] withNSString:@"line" withBoolean:NO];
    horizontal_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    showLegends_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    showTooltips_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    showPlotLabels_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    draw3D_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    zoomingAllowed_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:YES] withBoolean:NO];
    domainAxis_ = [[RARESPOTDataItem alloc] init];
    rangeAxis_ = [[RARESPOTDataItem alloc] init];
    chartTitle_ = nil;
    autoSort_ = [[SPOTBoolean alloc] initWithJavaLangBoolean:nil withJavaLangBoolean:[JavaLangBoolean valueOfWithBoolean:NO] withBoolean:NO];
    emptyText_ = nil;
    plot_ = nil;
    subTitles_ = nil;
    annotations_ = nil;
    rangeMarkers_ = nil;
    domainMarkers_ = nil;
    scrollPane_ = nil;
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 18;
  [super spot_setElements];
  [self spot_addElementWithNSString:@"chartType" withISPOTElement:chartType_];
  [((RARESPOTChart_CChartType *) nil_chk(chartType_)) spot_defineAttributeWithNSString:@"renderer" withNSString:nil];
  [self spot_addElementWithNSString:@"horizontal" withISPOTElement:horizontal_];
  [self spot_addElementWithNSString:@"showLegends" withISPOTElement:showLegends_];
  [((SPOTBoolean *) nil_chk(showLegends_)) spot_defineAttributeWithNSString:@"location" withNSString:nil];
  [self spot_addElementWithNSString:@"showTooltips" withISPOTElement:showTooltips_];
  [self spot_addElementWithNSString:@"showPlotLabels" withISPOTElement:showPlotLabels_];
  [self spot_addElementWithNSString:@"draw3D" withISPOTElement:draw3D_];
  [self spot_addElementWithNSString:@"zoomingAllowed" withISPOTElement:zoomingAllowed_];
  [self spot_addElementWithNSString:@"domainAxis" withISPOTElement:domainAxis_];
  [((RARESPOTDataItem *) nil_chk(domainAxis_)) spot_defineAttributeWithNSString:@"timeUnit" withNSString:nil];
  [domainAxis_ spot_defineAttributeWithNSString:@"tickIncrement" withNSString:nil];
  [domainAxis_ spot_defineAttributeWithNSString:@"lowerBound" withNSString:nil];
  [domainAxis_ spot_defineAttributeWithNSString:@"upperBound" withNSString:nil];
  [domainAxis_ spot_defineAttributeWithNSString:@"label" withNSString:nil];
  [domainAxis_ spot_defineAttributeWithNSString:@"labelAngle" withNSString:nil];
  [domainAxis_ spot_defineAttributeWithNSString:@"labelColor" withNSString:nil];
  [domainAxis_ spot_defineAttributeWithNSString:@"labelFont" withNSString:nil];
  [domainAxis_ spot_defineAttributeWithNSString:@"labelsVisible" withNSString:nil];
  [domainAxis_ spot_defineAttributeWithNSString:@"showGridLine" withNSString:nil];
  [domainAxis_ spot_defineAttributeWithNSString:@"showMinorTicks" withNSString:nil];
  [self spot_addElementWithNSString:@"rangeAxis" withISPOTElement:rangeAxis_];
  [((RARESPOTDataItem *) nil_chk(rangeAxis_)) spot_defineAttributeWithNSString:@"tickIncrement" withNSString:nil];
  [rangeAxis_ spot_defineAttributeWithNSString:@"lowerBound" withNSString:nil];
  [rangeAxis_ spot_defineAttributeWithNSString:@"upperBound" withNSString:nil];
  [rangeAxis_ spot_defineAttributeWithNSString:@"label" withNSString:nil];
  [rangeAxis_ spot_defineAttributeWithNSString:@"labelAngle" withNSString:nil];
  [rangeAxis_ spot_defineAttributeWithNSString:@"labelColor" withNSString:nil];
  [rangeAxis_ spot_defineAttributeWithNSString:@"labelFont" withNSString:nil];
  [rangeAxis_ spot_defineAttributeWithNSString:@"labelsVisible" withNSString:nil];
  [rangeAxis_ spot_defineAttributeWithNSString:@"showGridLine" withNSString:nil];
  [rangeAxis_ spot_defineAttributeWithNSString:@"showMinorTicks" withNSString:nil];
  [self spot_addElementWithNSString:@"chartTitle" withISPOTElement:chartTitle_];
  [self spot_addElementWithNSString:@"autoSort" withISPOTElement:autoSort_];
  [self spot_addElementWithNSString:@"emptyText" withISPOTElement:emptyText_];
  [self spot_addElementWithNSString:@"plot" withISPOTElement:plot_];
  [self spot_addElementWithNSString:@"subTitles" withISPOTElement:subTitles_];
  [self spot_addElementWithNSString:@"annotations" withISPOTElement:annotations_];
  [self spot_addElementWithNSString:@"rangeMarkers" withISPOTElement:rangeMarkers_];
  [self spot_addElementWithNSString:@"domainMarkers" withISPOTElement:domainMarkers_];
  [self spot_addElementWithNSString:@"scrollPane" withISPOTElement:scrollPane_];
}

- (RARESPOTDataItem *)getChartTitle {
  return chartTitle_;
}

- (RARESPOTDataItem *)getChartTitleReference {
  if (chartTitle_ == nil) {
    chartTitle_ = [[RARESPOTDataItem alloc] initWithBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"chartTitle" withISPOTElement:chartTitle_];
    [chartTitle_ spot_defineAttributeWithNSString:@"location" withNSString:nil];
  }
  return chartTitle_;
}

- (void)setChartTitleWithISPOTElement:(id<iSPOTElement>)reference {
  chartTitle_ = (RARESPOTDataItem *) check_class_cast(reference, [RARESPOTDataItem class]);
  (void) [self spot_setReferenceWithNSString:@"chartTitle" withISPOTElement:reference];
}

- (RARESPOTEmptyText *)getEmptyText {
  return emptyText_;
}

- (RARESPOTEmptyText *)getEmptyTextReference {
  if (emptyText_ == nil) {
    emptyText_ = [[RARESPOTEmptyText alloc] initWithBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"emptyText" withISPOTElement:emptyText_];
  }
  return emptyText_;
}

- (void)setEmptyTextWithISPOTElement:(id<iSPOTElement>)reference {
  emptyText_ = (RARESPOTEmptyText *) check_class_cast(reference, [RARESPOTEmptyText class]);
  (void) [self spot_setReferenceWithNSString:@"emptyText" withISPOTElement:reference];
}

- (RARESPOTPlot *)getPlot {
  return plot_;
}

- (RARESPOTPlot *)getPlotReference {
  if (plot_ == nil) {
    plot_ = [[RARESPOTPlot alloc] initWithBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"plot" withISPOTElement:plot_];
  }
  return plot_;
}

- (void)setPlotWithISPOTElement:(id<iSPOTElement>)reference {
  plot_ = (RARESPOTPlot *) check_class_cast(reference, [RARESPOTPlot class]);
  (void) [self spot_setReferenceWithNSString:@"plot" withISPOTElement:reference];
}

- (SPOTSet *)getSubTitles {
  return subTitles_;
}

- (SPOTSet *)getSubTitlesReference {
  if (subTitles_ == nil) {
    subTitles_ = [[SPOTSet alloc] initWithNSString:@"subTitle" withISPOTElement:[[RARESPOTDataItem alloc] init] withInt:-1 withInt:-1 withBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"subTitles" withISPOTElement:subTitles_];
  }
  return subTitles_;
}

- (void)setSubTitlesWithISPOTElement:(id<iSPOTElement>)reference {
  subTitles_ = (SPOTSet *) check_class_cast(reference, [SPOTSet class]);
  (void) [self spot_setReferenceWithNSString:@"subTitles" withISPOTElement:reference];
}

- (SPOTSet *)getAnnotations {
  return annotations_;
}

- (SPOTSet *)getAnnotationsReference {
  if (annotations_ == nil) {
    annotations_ = [[SPOTSet alloc] initWithNSString:@"annotation" withISPOTElement:[[RARESPOTDataItem alloc] init] withInt:-1 withInt:-1 withBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"annotations" withISPOTElement:annotations_];
  }
  return annotations_;
}

- (void)setAnnotationsWithISPOTElement:(id<iSPOTElement>)reference {
  annotations_ = (SPOTSet *) check_class_cast(reference, [SPOTSet class]);
  (void) [self spot_setReferenceWithNSString:@"annotations" withISPOTElement:reference];
}

- (SPOTSet *)getRangeMarkers {
  return rangeMarkers_;
}

- (SPOTSet *)getRangeMarkersReference {
  if (rangeMarkers_ == nil) {
    rangeMarkers_ = [[SPOTSet alloc] initWithNSString:@"marker" withISPOTElement:[[RARESPOTItemDescription alloc] init] withInt:-1 withInt:-1 withBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"rangeMarkers" withISPOTElement:rangeMarkers_];
  }
  return rangeMarkers_;
}

- (void)setRangeMarkersWithISPOTElement:(id<iSPOTElement>)reference {
  rangeMarkers_ = (SPOTSet *) check_class_cast(reference, [SPOTSet class]);
  (void) [self spot_setReferenceWithNSString:@"rangeMarkers" withISPOTElement:reference];
}

- (SPOTSet *)getDomainMarkers {
  return domainMarkers_;
}

- (SPOTSet *)getDomainMarkersReference {
  if (domainMarkers_ == nil) {
    domainMarkers_ = [[SPOTSet alloc] initWithNSString:@"marker" withISPOTElement:[[RARESPOTItemDescription alloc] init] withInt:-1 withInt:-1 withBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"domainMarkers" withISPOTElement:domainMarkers_];
  }
  return domainMarkers_;
}

- (void)setDomainMarkersWithISPOTElement:(id<iSPOTElement>)reference {
  domainMarkers_ = (SPOTSet *) check_class_cast(reference, [SPOTSet class]);
  (void) [self spot_setReferenceWithNSString:@"domainMarkers" withISPOTElement:reference];
}

- (RARESPOTScrollPane *)getScrollPane {
  return scrollPane_;
}

- (RARESPOTScrollPane *)getScrollPaneReference {
  if (scrollPane_ == nil) {
    scrollPane_ = [[RARESPOTScrollPane alloc] initWithBoolean:YES];
    (void) [super spot_setReferenceWithNSString:@"scrollPane" withISPOTElement:scrollPane_];
  }
  return scrollPane_;
}

- (void)setScrollPaneWithISPOTElement:(id<iSPOTElement>)reference {
  scrollPane_ = (RARESPOTScrollPane *) check_class_cast(reference, [RARESPOTScrollPane class]);
  (void) [self spot_setReferenceWithNSString:@"scrollPane" withISPOTElement:reference];
}

- (void)copyAllFieldsTo:(RARESPOTChart *)other {
  [super copyAllFieldsTo:other];
  other->annotations_ = annotations_;
  other->autoSort_ = autoSort_;
  other->chartTitle_ = chartTitle_;
  other->chartType_ = chartType_;
  other->domainAxis_ = domainAxis_;
  other->domainMarkers_ = domainMarkers_;
  other->draw3D_ = draw3D_;
  other->emptyText_ = emptyText_;
  other->horizontal_ = horizontal_;
  other->plot_ = plot_;
  other->rangeAxis_ = rangeAxis_;
  other->rangeMarkers_ = rangeMarkers_;
  other->scrollPane_ = scrollPane_;
  other->showLegends_ = showLegends_;
  other->showPlotLabels_ = showPlotLabels_;
  other->showTooltips_ = showTooltips_;
  other->subTitles_ = subTitles_;
  other->zoomingAllowed_ = zoomingAllowed_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
    { "getChartTitle", NULL, "LRARESPOTDataItem", 0x1, NULL },
    { "getChartTitleReference", NULL, "LRARESPOTDataItem", 0x1, NULL },
    { "setChartTitleWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "getEmptyText", NULL, "LRARESPOTEmptyText", 0x1, NULL },
    { "getEmptyTextReference", NULL, "LRARESPOTEmptyText", 0x1, NULL },
    { "setEmptyTextWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "getPlot", NULL, "LRARESPOTPlot", 0x1, NULL },
    { "getPlotReference", NULL, "LRARESPOTPlot", 0x1, NULL },
    { "setPlotWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "getSubTitles", NULL, "LSPOTSet", 0x1, NULL },
    { "getSubTitlesReference", NULL, "LSPOTSet", 0x1, NULL },
    { "setSubTitlesWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "getAnnotations", NULL, "LSPOTSet", 0x1, NULL },
    { "getAnnotationsReference", NULL, "LSPOTSet", 0x1, NULL },
    { "setAnnotationsWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "getRangeMarkers", NULL, "LSPOTSet", 0x1, NULL },
    { "getRangeMarkersReference", NULL, "LSPOTSet", 0x1, NULL },
    { "setRangeMarkersWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "getDomainMarkers", NULL, "LSPOTSet", 0x1, NULL },
    { "getDomainMarkersReference", NULL, "LSPOTSet", 0x1, NULL },
    { "setDomainMarkersWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
    { "getScrollPane", NULL, "LRARESPOTScrollPane", 0x1, NULL },
    { "getScrollPaneReference", NULL, "LRARESPOTScrollPane", 0x1, NULL },
    { "setScrollPaneWithISPOTElement:", NULL, "V", 0x1, "JavaLangClassCastException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "chartType_", NULL, 0x1, "LRARESPOTChart_CChartType" },
    { "horizontal_", NULL, 0x1, "LSPOTBoolean" },
    { "showLegends_", NULL, 0x1, "LSPOTBoolean" },
    { "showTooltips_", NULL, 0x1, "LSPOTBoolean" },
    { "showPlotLabels_", NULL, 0x1, "LSPOTBoolean" },
    { "draw3D_", NULL, 0x1, "LSPOTBoolean" },
    { "zoomingAllowed_", NULL, 0x1, "LSPOTBoolean" },
    { "domainAxis_", NULL, 0x1, "LRARESPOTDataItem" },
    { "rangeAxis_", NULL, 0x1, "LRARESPOTDataItem" },
    { "chartTitle_", NULL, 0x4, "LRARESPOTDataItem" },
    { "autoSort_", NULL, 0x1, "LSPOTBoolean" },
    { "emptyText_", NULL, 0x4, "LRARESPOTEmptyText" },
    { "plot_", NULL, 0x4, "LRARESPOTPlot" },
    { "subTitles_", NULL, 0x4, "LSPOTSet" },
    { "annotations_", NULL, 0x4, "LSPOTSet" },
    { "rangeMarkers_", NULL, 0x4, "LSPOTSet" },
    { "domainMarkers_", NULL, 0x4, "LSPOTSet" },
    { "scrollPane_", NULL, 0x4, "LRARESPOTScrollPane" },
  };
  static J2ObjcClassInfo _RARESPOTChart = { "Chart", "com.appnativa.rare.spot", NULL, 0x1, 26, methods, 18, fields, 0, NULL};
  return &_RARESPOTChart;
}

@end
@implementation RARESPOTChart_CChartType

static IOSIntArray * RARESPOTChart_CChartType__nchoices_;
static IOSObjectArray * RARESPOTChart_CChartType__schoices_;

+ (int)line {
  return RARESPOTChart_CChartType_line;
}

+ (int)bar {
  return RARESPOTChart_CChartType_bar;
}

+ (int)stacked_bar {
  return RARESPOTChart_CChartType_stacked_bar;
}

+ (int)range_bar {
  return RARESPOTChart_CChartType_range_bar;
}

+ (int)pie {
  return RARESPOTChart_CChartType_pie;
}

+ (int)area {
  return RARESPOTChart_CChartType_area;
}

+ (int)stacked_area {
  return RARESPOTChart_CChartType_stacked_area;
}

+ (int)range_area {
  return RARESPOTChart_CChartType_range_area;
}

+ (int)step_area {
  return RARESPOTChart_CChartType_step_area;
}

+ (int)step_line {
  return RARESPOTChart_CChartType_step_line;
}

+ (int)bubble {
  return RARESPOTChart_CChartType_bubble;
}

+ (int)hi_lo {
  return RARESPOTChart_CChartType_hi_lo;
}

+ (int)hi_lo_open_close {
  return RARESPOTChart_CChartType_hi_lo_open_close;
}

+ (int)candlestick {
  return RARESPOTChart_CChartType_candlestick;
}

+ (int)funnel {
  return RARESPOTChart_CChartType_funnel;
}

+ (int)spline {
  return RARESPOTChart_CChartType_spline;
}

+ (int)spline_area {
  return RARESPOTChart_CChartType_spline_area;
}

+ (int)point {
  return RARESPOTChart_CChartType_point;
}

+ (int)rose {
  return RARESPOTChart_CChartType_rose;
}

+ (int)pyramid {
  return RARESPOTChart_CChartType_pyramid;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTChart_CChartType__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTChart_CChartType__schoices_;
}

- (id)init {
  return [self initRARESPOTChart_CChartTypeWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTChart_CChartType__schoices_;
    _nChoices_ = RARESPOTChart_CChartType__nchoices_;
    [self spot_setAttributes];
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTChart_CChartTypeWithJavaLangInteger:(JavaLangInteger *)ival
                                         withNSString:(NSString *)sval
                                  withJavaLangInteger:(JavaLangInteger *)idefaultval
                                         withNSString:(NSString *)sdefaultval
                                          withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTChart_CChartType__schoices_;
    _nChoices_ = RARESPOTChart_CChartType__nchoices_;
    [self spot_setAttributes];
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTChart_CChartTypeWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{line(0), bar(1), stacked_bar(2), range_bar(3), pie(4), area(5), stacked_area(6), range_area(7), step_area(8), step_line(9), bubble(10), hi_lo(11), hi_lo_open_close(12), candlestick(13), funnel(14), spline(15), spline_area(16), point(17), rose(18), pyramid(19) }";
}

- (void)spot_setAttributes {
  self->attributeSizeHint_ += 1;
  [self spot_defineAttributeWithNSString:@"renderer" withNSString:nil];
}

+ (void)initialize {
  if (self == [RARESPOTChart_CChartType class]) {
    RARESPOTChart_CChartType__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 } count:20];
    RARESPOTChart_CChartType__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"line", @"bar", @"stacked_bar", @"range_bar", @"pie", @"area", @"stacked_area", @"range_area", @"step_area", @"step_line", @"bubble", @"hi_lo", @"hi_lo_open_close", @"candlestick", @"funnel", @"spline", @"spline_area", @"point", @"rose", @"pyramid" } count:20 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
    { "spot_setAttributes", NULL, "V", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "line_", NULL, 0x19, "I" },
    { "bar_", NULL, 0x19, "I" },
    { "stacked_bar_", NULL, 0x19, "I" },
    { "range_bar_", NULL, 0x19, "I" },
    { "pie_", NULL, 0x19, "I" },
    { "area_", NULL, 0x19, "I" },
    { "stacked_area_", NULL, 0x19, "I" },
    { "range_area_", NULL, 0x19, "I" },
    { "step_area_", NULL, 0x19, "I" },
    { "step_line_", NULL, 0x19, "I" },
    { "bubble_", NULL, 0x19, "I" },
    { "hi_lo_", NULL, 0x19, "I" },
    { "hi_lo_open_close_", NULL, 0x19, "I" },
    { "candlestick_", NULL, 0x19, "I" },
    { "funnel_", NULL, 0x19, "I" },
    { "spline_", NULL, 0x19, "I" },
    { "spline_area_", NULL, 0x19, "I" },
    { "point_", NULL, 0x19, "I" },
    { "rose_", NULL, 0x19, "I" },
    { "pyramid_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTChart_CChartType = { "CChartType", "com.appnativa.rare.spot", "Chart", 0x9, 2, methods, 22, fields, 0, NULL};
  return &_RARESPOTChart_CChartType;
}

@end
