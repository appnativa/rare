//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-carousel/com/appnativa/rare/viewer/aCarouselViewer.java
//
//  Created by decoteaud on 3/11/16.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/iExecutionHandler.h"
#include "com/appnativa/rare/iPlatformAppContext.h"
#include "com/appnativa/rare/spot/Carousel.h"
#include "com/appnativa/rare/spot/GridCell.h"
#include "com/appnativa/rare/spot/ItemDescription.h"
#include "com/appnativa/rare/spot/Margin.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/rare/ui/ColorUtils.h"
#include "com/appnativa/rare/ui/Column.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/ScreenUtils.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIImage.h"
#include "com/appnativa/rare/ui/UIImageIcon.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/aWidgetListener.h"
#include "com/appnativa/rare/ui/carousel/CachingURLImageCreator.h"
#include "com/appnativa/rare/ui/carousel/aCarouselPanel.h"
#include "com/appnativa/rare/ui/carousel/iImageCreator.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/event/iChangeListener.h"
#include "com/appnativa/rare/ui/iAdjustable.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/painter/PaintBucket.h"
#include "com/appnativa/rare/ui/painter/iImagePainter.h"
#include "com/appnativa/rare/ui/renderer/aListItemRenderer.h"
#include "com/appnativa/rare/viewer/aCarouselViewer.h"
#include "com/appnativa/rare/viewer/aViewer.h"
#include "com/appnativa/rare/viewer/iContainer.h"
#include "com/appnativa/rare/widget/aWidget.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/spot/SPOTBoolean.h"
#include "com/appnativa/spot/SPOTInteger.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTReal.h"
#include "com/appnativa/util/ObjectCache.h"
#include "com/appnativa/util/SNumber.h"
#include "com/appnativa/util/URLEncoder.h"
#include "com/appnativa/util/iFilterableList.h"
#include "java/lang/CharSequence.h"
#include "java/lang/Integer.h"
#include "java/util/Arrays.h"
#include "java/util/Collection.h"
#include "java/util/EventObject.h"
#include "java/util/List.h"
#include "java/util/Locale.h"

@implementation RAREaCarouselViewer

static RAREaCarouselViewer_Listeners * RAREaCarouselViewer__listeners_;
static id<RAREiExecutionHandler> RAREaCarouselViewer_executorService_;

+ (RAREaCarouselViewer_Listeners *)_listeners {
  return RAREaCarouselViewer__listeners_;
}

+ (void)set_listeners:(RAREaCarouselViewer_Listeners *)_listeners {
  RAREaCarouselViewer__listeners_ = _listeners;
}

+ (id<RAREiExecutionHandler>)executorService {
  return RAREaCarouselViewer_executorService_;
}

+ (void)setExecutorService:(id<RAREiExecutionHandler>)executorService {
  RAREaCarouselViewer_executorService_ = executorService;
}

- (id)init {
  return [self initRAREaCarouselViewerWithRAREiContainer:nil];
}

- (id)initRAREaCarouselViewerWithRAREiContainer:(id<RAREiContainer>)parent {
  if (self = [super initWithRAREiContainer:parent]) {
    dataType_ = [RAREaCarouselPanel_DataTypeEnum DATA_ITEMS];
    encodeParsedURLs_ = YES;
    [self ensureCapacityWithInt:10];
    imageList_ = [self getItems];
  }
  return self;
}

- (id)initWithRAREiContainer:(id<RAREiContainer>)parent {
  return [self initRAREaCarouselViewerWithRAREiContainer:parent];
}

- (void)addChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l {
  [((RAREaCarouselPanel *) nil_chk(panel_)) addChangeListenerWithRAREiChangeListener:l];
}

- (void)removeChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l {
  [((RAREaCarouselPanel *) nil_chk(panel_)) removeChangeListenerWithRAREiChangeListener:l];
}

- (int)getVisibleItemCount {
  return [((RAREaCarouselPanel *) nil_chk(panel_)) getVisibleItemCount];
}

- (BOOL)addWithId:(RARERenderableDataItem *)e {
  BOOL b = [super addWithId:e];
  if (b) {
    [self updateCarousel];
  }
  return b;
}

- (BOOL)isScrolling {
  return [((RAREaCarouselPanel *) nil_chk(panel_)) isScrolling];
}

- (void)setChangeEventsEnabledWithBoolean:(BOOL)enabled {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setChangeEventsEnabledWithBoolean:enabled];
}

- (void)addWithInt:(int)index
            withId:(RARERenderableDataItem *)element {
  [super addWithInt:index withId:element];
  [self updateCarousel];
}

- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)c {
  [((id<RAREUTiFilterableList>) nil_chk(imageList_)) addAllWithJavaUtilCollection:c];
  [self updateCarousel];
  return YES;
}

- (void)addAllWithRARERenderableDataItemArray:(IOSObjectArray *)items {
  [((id<RAREUTiFilterableList>) nil_chk(imageList_)) addAllWithJavaUtilCollection:[JavaUtilArrays asListWithNSObjectArray:items]];
  [self updateCarousel];
}

- (BOOL)addAllWithInt:(int)index
withJavaUtilCollection:(id<JavaUtilCollection>)c {
  [((id<RAREUTiFilterableList>) nil_chk(imageList_)) addAllWithInt:index withJavaUtilCollection:c];
  [self updateCarousel];
  return YES;
}

- (void)addAllWithInt:(int)index
withRARERenderableDataItemArray:(IOSObjectArray *)items {
  [((id<RAREUTiFilterableList>) nil_chk(imageList_)) addAllWithInt:index withJavaUtilCollection:[JavaUtilArrays asListWithNSObjectArray:items]];
  [self updateCarousel];
}

- (void)addExWithRARERenderableDataItem:(RARERenderableDataItem *)row {
  [super addWithId:row];
}

- (void)addParsedRowWithRARERenderableDataItem:(RARERenderableDataItem *)row {
  NSString *s = useLinkedData_ ? [nil_chk([((RARERenderableDataItem *) nil_chk(row)) getLinkedData]) description] : [((RARERenderableDataItem *) nil_chk(row)) description];
  if ([self isEncodeParsedURLs]) {
    if (s != nil) {
      if ([((RARERenderableDataItem *) nil_chk(row)) getTooltip] == nil) {
        int n = [s lastIndexOf:'.'];
        int p = [s lastIndexOf:'/'];
        if ((p > -1) && (p < n) && (n != -1)) {
          [row setTooltipWithJavaLangCharSequence:[s substring:p + 1 endIndex:n]];
        }
        else if (n != -1) {
          [row setTooltipWithJavaLangCharSequence:[s substring:0 endIndex:n]];
        }
      }
      s = [RAREUTURLEncoder encodeComponentWithNSString:s];
      if (useLinkedData_) {
        [row setLinkedDataWithId:s];
      }
      else {
        [row setValueWithId:s];
      }
    }
  }
  [((id<RAREUTiFilterableList>) nil_chk(imageList_)) addWithId:row];
}

- (void)clearContents {
  [super clearContents];
  [self clearSubItems];
  [((RAREaCarouselPanel *) check_class_cast(dataComponent_, [RAREaCarouselPanel class])) refreshItems];
}

- (void)configureWithRARESPOTViewer:(RARESPOTViewer *)vcfg {
  [self configureExWithRARESPOTCarousel:(RARESPOTCarousel *) check_class_cast(vcfg, [RARESPOTCarousel class])];
  [self fireConfigureEventWithRARESPOTWidget:vcfg withNSString:[RAREiConstants EVENT_CONFIGURE]];
  [self handleDataURLWithRARESPOTWidget:vcfg];
}

- (void)finishedLoading {
  [super finishedLoading];
  [self updateCarousel];
}

- (void)refreshItems {
  [self updateCarousel];
}

- (RARERenderableDataItem *)removeWithInt:(int)index {
  RARERenderableDataItem *item = [super removeWithInt:index];
  if (item != nil) {
    [self updateCarousel];
  }
  return item;
}

- (BOOL)removeWithId:(id)item {
  BOOL b = [super removeWithId:item];
  if (b) {
    [self updateCarousel];
  }
  return b;
}

- (void)scrollHome {
  [((RAREaCarouselPanel *) nil_chk(panel_)) scrollHome];
}

- (BOOL)scrollLeft {
  return [((RAREaCarouselPanel *) nil_chk(panel_)) scrollLeft];
}

- (BOOL)scrollRight {
  return [((RAREaCarouselPanel *) nil_chk(panel_)) scrollRight];
}

- (void)scrollToWithInt:(int)index {
  [((RAREaCarouselPanel *) nil_chk(panel_)) scrollToWithInt:index];
}

- (void)scrollToEnd {
  [((RAREaCarouselPanel *) nil_chk(panel_)) scrollToEnd];
}

- (void)stopAnimation {
  [((RAREaCarouselPanel *) nil_chk(panel_)) stopAnimation];
}

- (RARERenderableDataItem *)setWithInt:(int)index
                                withId:(RARERenderableDataItem *)element {
  RARERenderableDataItem *item = [super setWithInt:index withId:element];
  [self updateCarousel];
  return item;
}

- (void)setAdjustableWithRAREiAdjustable:(id<RAREiAdjustable>)adjustable {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setAdjustableWithRAREiAdjustable:adjustable];
}

- (BOOL)setAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection {
  [((id<RAREUTiFilterableList>) nil_chk(imageList_)) setAllWithJavaUtilCollection:collection];
  [self updateCarousel];
  return YES;
}

- (void)setAnimateSelectionWithBoolean:(BOOL)animate {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setAnimateSelectionWithBoolean:animate];
}

- (void)setEncodeParsedURLsWithBoolean:(BOOL)encodeParsedURLs {
  self->encodeParsedURLs_ = encodeParsedURLs;
}

- (void)setFlatListWithBoolean:(BOOL)flatList {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setFlatListWithBoolean:flatList];
}

- (void)setFlowInsetsWithRAREUIInsets:(RAREUIInsets *)insets {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setFlowInsetsWithRAREUIInsets:insets];
}

- (void)setImageGapWithInt:(int)imageGap {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setImageGapWithInt:imageGap];
}

- (void)setImageGetterWithRAREiImageCreator:(id<RAREiImageCreator>)imageGetter {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setImageGetterWithRAREiImageCreator:imageGetter];
}

- (void)setMaxImageSizeWithInt:(int)width
                       withInt:(int)height {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setMaxImageSizeWithInt:width withInt:height];
}

- (void)setMaxSideItemsWithInt:(int)max {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setMaxSideItemsWithInt:max];
}

- (void)setMinImageSizeWithInt:(int)width
                       withInt:(int)height {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setMinImageSizeWithInt:width withInt:height];
}

- (void)setPerspectiveFractionWithFloat:(float)f {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setPerspectiveFractionWithFloat:f];
}

- (void)setPreferedImageSizeWithInt:(int)width
                            withInt:(int)height {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setPreferedImageSizeWithInt:width withInt:height];
}

- (void)setPreserveAspectRatioWithBoolean:(BOOL)preserveAspectRatio {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setPreserveAspectRatioWithBoolean:preserveAspectRatio];
}

- (void)setReflectionFractionWithFloat:(float)f {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setReflectionFractionWithFloat:f];
}

- (void)setReflectionOpacityWithFloat:(float)opacity {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setReflectionOpacityWithFloat:opacity];
}

- (void)setRenderSecondaryTitleWithBoolean:(BOOL)render {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setRenderSecondaryTitleWithBoolean:render];
}

- (void)setRenderTitlesWithBoolean:(BOOL)render {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setRenderTitlesWithBoolean:render];
}

- (void)setScalingTypeWithRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)scalingType {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setScalingTypeWithRAREiImagePainter_ScalingTypeEnum:scalingType];
}

- (void)setSelectedIndexWithInt:(int)index {
  [((RAREaCarouselPanel *) check_class_cast(dataComponent_, [RAREaCarouselPanel class])) scrollToWithInt:index];
}

- (void)setSelectionFractionWithFloat:(float)f {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setSelectionFractionWithFloat:f];
}

- (void)setSideFractionWithFloat:(float)f {
  [((RAREaCarouselPanel *) nil_chk(panel_)) setSideFractionWithFloat:f];
}

- (void)setValueWithId:(id)value {
  int i = [((id<RAREUTiFilterableList>) nil_chk(imageList_)) indexOfWithId:value];
  if (i != -1) {
    [self setSelectedIndexWithInt:i];
  }
}

- (int)getImageGap {
  return [((RAREaCarouselPanel *) nil_chk(panel_)) getImageGap];
}

- (int)getMaxSideItems {
  return [((RAREaCarouselPanel *) nil_chk(panel_)) getMaxSideItems];
}

- (float)getPrimaryFraction {
  return [((RAREaCarouselPanel *) nil_chk(panel_)) getPrimaryFraction];
}

- (float)getReflectionFraction {
  return [((RAREaCarouselPanel *) nil_chk(panel_)) getReflectionFraction];
}

- (float)getReflectionOpacity {
  return [((RAREaCarouselPanel *) nil_chk(panel_)) getReflectionOpacity];
}

- (int)getSelectedIndex {
  return [((RAREaCarouselPanel *) check_class_cast(dataComponent_, [RAREaCarouselPanel class])) getSelectedIndex];
}

- (id)getSelection {
  return [((RAREaCarouselPanel *) check_class_cast(dataComponent_, [RAREaCarouselPanel class])) getSelectedItem];
}

- (BOOL)isEncodeParsedURLs {
  return encodeParsedURLs_;
}

- (void)clearConfigurationWithBoolean:(BOOL)dispose {
  [super clearConfigurationWithBoolean:dispose];
  if (dispose) {
    panel_ = nil;
    imageList_ = nil;
  }
}

- (void)configureExWithRARESPOTCarousel:(RARESPOTCarousel *)cfg {
  dataType_ = [RAREaCarouselPanel_DataTypeEnum valueOfWithNSString:[((NSString *) nil_chk([((RARESPOTCarousel_CDataType *) nil_chk(((RARESPOTCarousel *) nil_chk(cfg))->dataType_)) stringValue])) uppercaseStringWithJRELocale:[JavaUtilLocale ENGLISH]]];
  RAREaCarouselPanel *cp = [self createComponentsWithRARESPOTCarousel:cfg withRAREaCarouselPanel_DataTypeEnum:dataType_];
  panel_ = cp;
  RAREaListItemRenderer *r = [[RAREaCarouselViewer_$1 alloc] init];
  if ([@"true" isEqual:[cfg->dataType_ spot_getAttributeWithNSString:@"usesImagesAlways"]]) {
    [((RAREaCarouselPanel *) nil_chk(cp)) setUsesImagesAlwaysWithBoolean:YES];
  }
  [((RAREaCarouselPanel *) nil_chk(cp)) setItemRendererWithRAREaListItemRenderer:r];
  [cp setDataModelWithJavaUtilList:imageList_];
  [self configureExWithRARESPOTViewer:cfg withBoolean:YES withBoolean:NO withBoolean:YES];
  if (![self isDesignMode]) {
    NSString *s;
    int cache = 0;
    BOOL strongCache = NO;
    int threads = 0;
    cache = [((SPOTInteger *) nil_chk(cfg->cacheSize_)) intValue];
    strongCache = [@"true" equalsIgnoreCase:[cfg->cacheSize_ spot_getAttributeWithNSString:@"strongReference"]];
    [cp setScalingTypeWithRAREiImagePainter_ScalingTypeEnum:[RAREiImagePainter_ScalingTypeEnum valueOfWithNSString:[((NSString *) nil_chk([((RARESPOTCarousel_CScaling *) nil_chk(cfg->scaling_)) stringValue])) uppercaseStringWithJRELocale:[JavaUtilLocale ENGLISH]]]];
    [cp setRenderTitlesWithBoolean:[((SPOTBoolean *) nil_chk(cfg->renderTitles_)) booleanValue]];
    [cp setRenderSecondaryTitleWithBoolean:[((SPOTBoolean *) nil_chk(cfg->renderSecondaryTitles_)) booleanValue]];
    encodeParsedURLs_ = [((SPOTBoolean *) nil_chk(cfg->encodeParsedURLs_)) booleanValue];
    [cp setPreserveAspectRatioWithBoolean:[((SPOTBoolean *) nil_chk(cfg->preserveAspectRatio_)) booleanValue]];
    if ([((SPOTReal *) nil_chk(cfg->reflectionFraction_)) spot_valueWasSet]) {
      [cp setReflectionFractionWithFloat:[cfg->reflectionFraction_ floatValue]];
    }
    [cp setSelectionFractionWithFloat:[((SPOTReal *) nil_chk(cfg->selectedFraction_)) floatValue]];
    [cp setSideFractionWithFloat:[((SPOTReal *) nil_chk(cfg->sideFraction_)) floatValue]];
    [cp setAnimateSelectionWithBoolean:[((SPOTBoolean *) nil_chk(cfg->animateSelection_)) booleanValue]];
    [cp setFlatListWithBoolean:[((SPOTBoolean *) nil_chk(cfg->flatList_)) booleanValue]];
    [cp setImageGapWithInt:[RAREScreenUtils platformPixelsWithFloat:[((SPOTInteger *) nil_chk(cfg->imageGap_)) intValue]]];
    if ([cfg->reflectionFraction_ spot_valueWasSet]) {
      [cp setReflectionFractionWithFloat:[cfg->reflectionFraction_ floatValue]];
    }
    RAREPaintBucket *pb = [RAREColorUtils configureWithRAREiWidget:self withRARESPOTGridCell:[cfg getItemCell] withRAREPaintBucket:nil];
    if (pb != nil) {
      [cp setCellPaintWithRAREPaintBucket:pb];
    }
    pb = [RAREColorUtils configureWithRAREiWidget:self withRARESPOTGridCell:[cfg getSelectionCell] withRAREPaintBucket:nil];
    if (pb != nil) {
      [cp setSelectedCellPaintWithRAREPaintBucket:pb];
    }
    RAREColumn *col;
    if ([cfg getItemDescription] != nil) {
      col = [self createColumnWithRARESPOTItemDescription:[cfg getItemDescription]];
      pb = [[RAREPaintBucket alloc] initWithRAREUIColor:[((RAREColumn *) nil_chk(col)) getBackground]];
      [pb setBorderWithRAREiPlatformBorder:[col getBorder]];
      [cp setCellPaintWithRAREPaintBucket:pb];
    }
    if ([((SPOTInteger *) nil_chk(cfg->maxSideItems_)) spot_hasValue]) {
      [cp setMaxSideItemsWithInt:[cfg->maxSideItems_ intValue]];
    }
    if ([((SPOTReal *) nil_chk(cfg->perspectiveFraction_)) spot_hasValue]) {
      [cp setPerspectiveFractionWithFloat:[cfg->perspectiveFraction_ floatValue]];
    }
    RARESPOTMargin *m = [cfg getAreaMargin];
    if (m != nil) {
      [cp setFlowInsetsWithRAREUIInsets:[m getInsets]];
    }
    if ([((SPOTBoolean *) nil_chk(cfg->useSeparateLoader_)) booleanValue]) {
      s = [cfg->useSeparateLoader_ spot_getAttributeWithNSString:@"maxThreads"];
      if (s != nil) {
        threads = [RAREUTSNumber intValueWithNSString:s];
      }
      else {
        threads = 2;
      }
    }
    switch ([dataType_ ordinal]) {
      case RAREaCarouselPanel_DataType_IMAGE_URLS:
      {
        if (cache == 0) {
          cache = 50;
        }
        else if (cache == -1) {
          cache = JavaLangInteger_MAX_VALUE;
        }
        id<RAREiExecutionHandler> es = [self getAppContext];
        if (threads > 0) {
          if (threads > 10) {
            threads = 10;
          }
          if (RAREaCarouselViewer_executorService_ == nil) {
            RAREaCarouselViewer_executorService_ = [self createExecutionHandlerWithInt:threads];
          }
          es = RAREaCarouselViewer_executorService_;
        }
        RAREUIImage *delayedIconImage = nil;
        if ([((SPOTPrintableString *) nil_chk(cfg->deferredImageIcon_)) spot_hasValue]) {
          RAREUIImageIcon *icon = (RAREUIImageIcon *) check_class_cast([self getIconWithSPOTPrintableString:cfg->deferredImageIcon_], [RAREUIImageIcon class]);
          if (icon != nil) {
            delayedIconImage = [icon getUIImage];
          }
        }
        RARECachingURLImageCreator *ic = [[RARECachingURLImageCreator alloc] initWithRAREUTiURLResolver:self withInt:cache withRAREiExecutionHandler:es withRAREUIImage:delayedIconImage];
        [cp setImageGetterWithRAREiImageCreator:ic];
        if (strongCache) {
          [((RAREUTObjectCache *) nil_chk([ic getImageCache])) setStrongReferencesWithBoolean:YES];
        }
        break;
      }
      case RAREaCarouselPanel_DataType_WIDGET_URLS:
      {
        break;
      }
      default:
      break;
    }
  }
  [cp addChangeListenerWithRAREiChangeListener:RAREaCarouselViewer__listeners_];
  [cp addActionListenerWithRAREiActionListener:RAREaCarouselViewer__listeners_];
}

- (RAREaCarouselPanel *)createComponentsWithRARESPOTCarousel:(RARESPOTCarousel *)cfg
                         withRAREaCarouselPanel_DataTypeEnum:(RAREaCarouselPanel_DataTypeEnum *)type {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (id<RAREiExecutionHandler>)createExecutionHandlerWithInt:(int)threads {
  // can't call an abstract method
  [self doesNotRecognizeSelector:_cmd];
  return 0;
}

- (void)updateCarousel {
  [((RAREaCarouselPanel *) check_class_cast(dataComponent_, [RAREaCarouselPanel class])) refreshItems];
}

+ (void)initialize {
  if (self == [RAREaCarouselViewer class]) {
    RAREaCarouselViewer__listeners_ = [[RAREaCarouselViewer_Listeners alloc] init];
  }
}

- (void)copyAllFieldsTo:(RAREaCarouselViewer *)other {
  [super copyAllFieldsTo:other];
  other->dataType_ = dataType_;
  other->encodeParsedURLs_ = encodeParsedURLs_;
  other->imageList_ = imageList_;
  other->panel_ = panel_;
  other->useLinkedData_ = useLinkedData_;
}

- (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(__unsafe_unretained id *)stackbuf count:(NSUInteger)len {
  return JreDefaultFastEnumeration(self, state, stackbuf, len);
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "addWithRARERenderableDataItem:", NULL, "Z", 0x1, NULL },
    { "isScrolling", NULL, "Z", 0x1, NULL },
    { "addAllWithJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "addAllWithInt:withJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "removeWithInt:", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "removeWithId:", NULL, "Z", 0x1, NULL },
    { "scrollLeft", NULL, "Z", 0x1, NULL },
    { "scrollRight", NULL, "Z", 0x1, NULL },
    { "setWithInt:withRARERenderableDataItem:", NULL, "LRARERenderableDataItem", 0x1, NULL },
    { "setAllWithJavaUtilCollection:", NULL, "Z", 0x1, NULL },
    { "getSelection", NULL, "LNSObject", 0x1, NULL },
    { "isEncodeParsedURLs", NULL, "Z", 0x1, NULL },
    { "clearConfigurationWithBoolean:", NULL, "V", 0x4, NULL },
    { "configureExWithRARESPOTCarousel:", NULL, "V", 0x4, NULL },
    { "createComponentsWithRARESPOTCarousel:withRAREaCarouselPanel_DataTypeEnum:", NULL, "LRAREaCarouselPanel", 0x404, NULL },
    { "createExecutionHandlerWithInt:", NULL, "LRAREiExecutionHandler", 0x404, NULL },
    { "updateCarousel", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "_listeners_", NULL, 0xc, "LRAREaCarouselViewer_Listeners" },
    { "executorService_", NULL, 0xa, "LRAREiExecutionHandler" },
    { "dataType_", NULL, 0x4, "LRAREaCarouselPanel_DataTypeEnum" },
    { "encodeParsedURLs_", NULL, 0x4, "Z" },
    { "imageList_", NULL, 0x4, "LRAREUTiFilterableList" },
    { "panel_", NULL, 0x4, "LRAREaCarouselPanel" },
    { "useLinkedData_", NULL, 0x4, "Z" },
  };
  static J2ObjcClassInfo _RAREaCarouselViewer = { "aCarouselViewer", "com.appnativa.rare.viewer", NULL, 0x401, 17, methods, 7, fields, 0, NULL};
  return &_RAREaCarouselViewer;
}

@end
@implementation RAREaCarouselViewer_Listeners

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e {
  RAREaWidgetListener *wl = [self getListenerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>) check_protocol_cast([((RAREActionEvent *) nil_chk(e)) getSource], @protocol(RAREiPlatformComponent))];
  if (wl != nil) {
    [wl actionPerformedWithRAREActionEvent:e];
  }
}

- (void)stateChangedWithJavaUtilEventObject:(JavaUtilEventObject *)e {
  RAREaWidgetListener *wl = [self getListenerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>) check_protocol_cast([((JavaUtilEventObject *) nil_chk(e)) getSource], @protocol(RAREiPlatformComponent))];
  if (wl != nil) {
    [wl stateChangedWithJavaUtilEventObject:e];
  }
}

- (RAREaWidgetListener *)getListenerWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  RAREaCarouselViewer *w = (RAREaCarouselViewer *) check_class_cast([RAREPlatform findWidgetForComponentWithId:c], [RAREaCarouselViewer class]);
  return (w == nil) ? nil : [w getWidgetListener];
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getListenerWithRAREiPlatformComponent:", NULL, "LRAREaWidgetListener", 0x2, NULL },
  };
  static J2ObjcClassInfo _RAREaCarouselViewer_Listeners = { "Listeners", "com.appnativa.rare.viewer", "aCarouselViewer", 0xa, 1, methods, 0, NULL, 0, NULL};
  return &_RAREaCarouselViewer_Listeners;
}

@end
@implementation RAREaCarouselViewer_$1

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREaCarouselViewer_$1 = { "$1", "com.appnativa.rare.viewer", "aCarouselViewer", 0x8000, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREaCarouselViewer_$1;
}

@end
