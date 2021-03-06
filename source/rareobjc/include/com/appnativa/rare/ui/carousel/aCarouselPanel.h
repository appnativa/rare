//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core-carousel/com/appnativa/rare/ui/carousel/aCarouselPanel.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RAREaCarouselPanel_H_
#define _RAREaCarouselPanel_H_

@class JavaUtilEventObject;
@class RAREGraphicsComposite;
@class RAREPaintBucket;
@class RARERenderableDataItem;
@class RAREUIDimension;
@class RAREUIImage;
@class RAREUIInsets;
@class RAREUTIdentityArrayList;
@class RAREaCarouselPanel_DataTypeEnum;
@class RAREaListItemRenderer;
@class RAREiImagePainter_ScalingTypeEnum;
@protocol JavaLangCharSequence;
@protocol JavaUtilList;
@protocol JavaUtilMap;
@protocol RAREiActionListener;
@protocol RAREiAdjustable;
@protocol RAREiImageCreator;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformComponentPainter;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/XPContainer.h"
#include "com/appnativa/rare/ui/event/iChangeListener.h"
#include "com/appnativa/rare/ui/iActionable.h"
#include "com/appnativa/rare/ui/iImageObserver.h"
#include "java/lang/Enum.h"
#include "java/lang/Runnable.h"

@interface RAREaCarouselPanel : RAREXPContainer < RAREiActionable, RAREiImageObserver, RAREiChangeListener > {
 @public
  float blockIncrement_;
  int imageGap_;
  int maxSideItems_;
  float perspectiveFraction_;
  BOOL preserveAspectRatio_;
  float primaryFraction_;
  float reflectionFraction_;
  float reflectionOpacity_;
  int selectedIndex_;
  float sideFraction_;
  RAREiImagePainter_ScalingTypeEnum *scalingType_;
  RAREUIDimension *prefImageSize_;
  RAREUIDimension *minImageSize_;
  RAREUIDimension *maxImageSize_;
  RAREUIInsets *flowInsets_;
  RAREUTIdentityArrayList *componentCache_;
  BOOL animate_;
  id<RAREiAdjustable> adjustable_;
  id<JavaUtilMap> animatorOptions_;
  id<RAREiPlatformComponentPainter> cellPainter_;
  id<RAREiPlatformComponent> centerComponent_;
  RAREaCarouselPanel_DataTypeEnum *dataType_;
  BOOL flatList_;
  BOOL ignoreAdjustment_;
  id<RAREiImageCreator> imageCreator_;
  int imageHeight_;
  int imageWidth_;
  RAREaListItemRenderer *itemRenderer_;
  float oldHeight_;
  float oldWidth_;
  BOOL prefSizeSet_;
  BOOL renderSecondaryTitles_;
  BOOL renderTitles_;
  id<RAREiPlatformComponent> secondaryLeftComponent_;
  id<RAREiPlatformComponent> secondaryRightComponent_;
  int secondaryWidth_;
  id<RAREiPlatformComponentPainter> selectionPainter_;
  float titleHeight_;
  BOOL useLinkedData_;
  id<JavaUtilList> dataModel_;
  BOOL changeEventsEnabled_;
  BOOL usesImagesAlways_;
  float fadeAlpha_;
  RAREGraphicsComposite *smallComposite_;
  RAREGraphicsComposite *smallerComposite_;
  int fadeSize_;
}

+ (NSString *)RARE_CAROUSEL_ITEM;
+ (NSString *)RARE_CAROUSEL_SHOWTITLE_PROPERTY;
- (id)initWithRAREaCarouselPanel_DataTypeEnum:(RAREaCarouselPanel_DataTypeEnum *)type;
- (void)addActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (void)addChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (void)animateSelectWithInt:(int)index;
- (void)dispose;
- (void)imageLoadedWithRAREUIImage:(RAREUIImage *)image;
- (void)refreshItems;
- (void)removeActionListenerWithRAREiActionListener:(id<RAREiActionListener>)l;
- (void)removeChangeListenerWithRAREiChangeListener:(id<RAREiChangeListener>)l;
- (void)scrollHome;
- (void)setChangeEventsEnabledWithBoolean:(BOOL)enabled;
- (BOOL)isScrolling;
- (BOOL)scrollLeft;
- (BOOL)scrollRight;
- (void)scrollToWithInt:(int)index;
- (void)scrollToEnd;
- (void)stateChangedWithJavaUtilEventObject:(JavaUtilEventObject *)e;
- (void)stopAnimation;
- (void)updateContentWithInt:(int)selectedIndex;
- (void)refreshVisibleContent;
- (void)setAdjustableWithRAREiAdjustable:(id<RAREiAdjustable>)adjustable;
- (void)setAnimateSelectionWithBoolean:(BOOL)animate;
- (void)setAnimatorOptionsWithJavaUtilMap:(id<JavaUtilMap>)animatorOptions;
- (void)setCellPaintWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setFlatListWithBoolean:(BOOL)flatList;
- (void)setFlowInsetsWithRAREUIInsets:(RAREUIInsets *)insets;
- (void)setImageGapWithInt:(int)imageGap;
- (void)setImageGetterWithRAREiImageCreator:(id<RAREiImageCreator>)imageGetter;
- (void)setItemRendererWithRAREaListItemRenderer:(RAREaListItemRenderer *)renderer;
- (void)setDataModelWithJavaUtilList:(id<JavaUtilList>)model;
- (void)setMaxImageSizeWithInt:(int)width
                       withInt:(int)height;
- (void)setMaxSideItemsWithInt:(int)max;
- (void)setMinImageSizeWithInt:(int)width
                       withInt:(int)height;
- (void)setPerspectiveFractionWithFloat:(float)f;
- (void)setPreferedImageSizeWithInt:(int)width
                            withInt:(int)height;
- (void)setPreserveAspectRatioWithBoolean:(BOOL)preserveAspectRatio;
- (void)setReflectionFractionWithFloat:(float)f;
- (void)setReflectionOpacityWithFloat:(float)opacity;
- (void)setRenderSecondaryTitleWithBoolean:(BOOL)render;
- (void)setRenderTitlesWithBoolean:(BOOL)render;
- (void)setScalingTypeWithRAREiImagePainter_ScalingTypeEnum:(RAREiImagePainter_ScalingTypeEnum *)scalingType;
- (void)setSelectedCellPaintWithRAREPaintBucket:(RAREPaintBucket *)pb;
- (void)setSelectedItemWithId:(id)item;
- (void)setSelectionFractionWithFloat:(float)f;
- (void)setSideFractionWithFloat:(float)f;
- (void)setUseLinkedDataWithBoolean:(BOOL)use;
- (id<JavaUtilMap>)getAnimatorOptions;
- (RAREUIInsets *)getFlowInsets;
- (int)getImageGap;
- (id<RAREiImageCreator>)getImageGetter;
- (id)getItemAtWithInt:(int)x
               withInt:(int)y;
- (int)getItemCount;
- (RAREaListItemRenderer *)getItemRenderer;
- (int)getMaxSideItems;
- (float)getPrimaryFraction;
- (float)getReflectionFraction;
- (float)getReflectionOpacity;
- (RAREiImagePainter_ScalingTypeEnum *)getScalingType;
- (float)getSecondaryFraction;
- (int)getSelectedIndex;
- (id)getSelectedItem;
- (BOOL)getUseLinkedData;
- (BOOL)isAnimated;
- (BOOL)isAnimating;
- (BOOL)isFlatList;
- (BOOL)isPerspectiveTransformSupported;
- (BOOL)isPreserveAspectRatio;
- (BOOL)isUseLinkedData;
- (void)animateMoveWithFloat:(float)dx;
- (void)bringToFrontOrClipWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                           withFloat:(float)clipWidth
                                         withBoolean:(BOOL)leftSide;
- (void)callImageCreatorWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                        withRARERenderableDataItem:(RARERenderableDataItem *)item
                                       withBoolean:(BOOL)showTitle;
- (void)clearPerspectiveFilterWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)conditionallyUpdateAdjustable;
- (void)configureAdjustable;
- (RAREUIImage *)createImageFromComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (id<RAREiPlatformComponent>)createLayoutComponent;
- (void)fireChangeEvent;
- (void)setFadeAlphaWithFloat:(float)alpha;
- (void)initAlphaComposites OBJC_METHOD_FAMILY_NONE;
- (void)initComponents OBJC_METHOD_FAMILY_NONE;
- (void)installPaints;
- (void)layoutWithFloat:(float)x
              withFloat:(float)y
              withFloat:(float)width
              withFloat:(float)height;
- (void)clearComponentContentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)dst;
- (void)renderComponentContentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)dst
                              withRARERenderableDataItem:(RARERenderableDataItem *)src;
- (void)updateComponentContentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)dst
                              withRAREiPlatformComponent:(id<RAREiPlatformComponent>)src
                                withJavaLangCharSequence:(id<JavaLangCharSequence>)title;
- (void)updateComponentContentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                                  withId:(id)value
                                             withBoolean:(BOOL)showTitle;
- (void)setCompositeWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)component
                     withRAREGraphicsComposite:(RAREGraphicsComposite *)composite;
- (void)updateComponentContentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)dst
                                         withRAREUIImage:(RAREUIImage *)src
                                withJavaLangCharSequence:(id<JavaLangCharSequence>)title;
- (void)setPerspectiveFilterWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                             withFloat:(float)width
                                             withFloat:(float)height
                                           withBoolean:(BOOL)left
                                               withInt:(int)pos;
- (id<RAREiPlatformComponent>)getComponentToAdd;
- (int)getDataIndexWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c;
- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth;
- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth;
- (BOOL)isUsesImagesAlways;
- (void)setUsesImagesAlwaysWithBoolean:(BOOL)usesImagesAlways;
- (int)getVisibleItemCount;
- (void)copyAllFieldsTo:(RAREaCarouselPanel *)other;
@end

J2OBJC_FIELD_SETTER(RAREaCarouselPanel, scalingType_, RAREiImagePainter_ScalingTypeEnum *)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, prefImageSize_, RAREUIDimension *)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, minImageSize_, RAREUIDimension *)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, maxImageSize_, RAREUIDimension *)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, flowInsets_, RAREUIInsets *)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, componentCache_, RAREUTIdentityArrayList *)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, adjustable_, id<RAREiAdjustable>)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, animatorOptions_, id<JavaUtilMap>)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, cellPainter_, id<RAREiPlatformComponentPainter>)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, centerComponent_, id<RAREiPlatformComponent>)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, dataType_, RAREaCarouselPanel_DataTypeEnum *)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, imageCreator_, id<RAREiImageCreator>)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, itemRenderer_, RAREaListItemRenderer *)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, secondaryLeftComponent_, id<RAREiPlatformComponent>)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, secondaryRightComponent_, id<RAREiPlatformComponent>)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, selectionPainter_, id<RAREiPlatformComponentPainter>)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, dataModel_, id<JavaUtilList>)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, smallComposite_, RAREGraphicsComposite *)
J2OBJC_FIELD_SETTER(RAREaCarouselPanel, smallerComposite_, RAREGraphicsComposite *)

typedef RAREaCarouselPanel ComAppnativaRareUiCarouselACarouselPanel;

typedef enum {
  RAREaCarouselPanel_DataType_DATA_ITEMS = 0,
  RAREaCarouselPanel_DataType_IMAGE_URLS = 1,
  RAREaCarouselPanel_DataType_WIDGET_URLS = 2,
} RAREaCarouselPanel_DataType;

@interface RAREaCarouselPanel_DataTypeEnum : JavaLangEnum < NSCopying > {
}
+ (RAREaCarouselPanel_DataTypeEnum *)DATA_ITEMS;
+ (RAREaCarouselPanel_DataTypeEnum *)IMAGE_URLS;
+ (RAREaCarouselPanel_DataTypeEnum *)WIDGET_URLS;
+ (IOSObjectArray *)values;
+ (RAREaCarouselPanel_DataTypeEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface RAREaCarouselPanel_$1 : NSObject < JavaLangRunnable > {
 @public
  RAREaCarouselPanel *this$0_;
}

- (void)run;
- (id)initWithRAREaCarouselPanel:(RAREaCarouselPanel *)outer$;
@end

J2OBJC_FIELD_SETTER(RAREaCarouselPanel_$1, this$0_, RAREaCarouselPanel *)

#endif // _RAREaCarouselPanel_H_
