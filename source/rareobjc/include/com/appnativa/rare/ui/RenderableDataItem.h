//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/RenderableDataItem.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARERenderableDataItem_H_
#define _RARERenderableDataItem_H_

@class IOSClass;
@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangStringBuilder;
@class JavaUtilHashMap;
@class RARERenderableDataItem_CursorDisplayTypeEnum;
@class RARERenderableDataItem_HorizontalAlignEnum;
@class RARERenderableDataItem_IconPositionEnum;
@class RARERenderableDataItem_OrientationEnum;
@class RARERenderableDataItem_VerticalAlignEnum;
@class RAREUIColor;
@class RAREUIFont;
@class RAREUTObjectHolder;
@protocol JavaLangCharSequence;
@protocol JavaUtilCollection;
@protocol JavaUtilComparator;
@protocol JavaUtilIterator;
@protocol JavaUtilList;
@protocol JavaUtilListIterator;
@protocol JavaUtilMap;
@protocol RAREUTiFilter;
@protocol RAREiActionListener;
@protocol RAREiBackgroundPainter;
@protocol RAREiDataConverter;
@protocol RAREiPlatformBorder;
@protocol RAREiPlatformComponent;
@protocol RAREiPlatformComponentPainter;
@protocol RAREiPlatformIcon;
@protocol RAREiWidget;

#import "JreEmulation.h"
#include "com/appnativa/rare/ui/painter/iPainterSupport.h"
#include "com/appnativa/util/iFilterableList.h"
#include "com/appnativa/util/iStringConverter.h"
#include "java/lang/Comparable.h"
#include "java/lang/Enum.h"

#define RARERenderableDataItem_TYPE_ARRAY 512
#define RARERenderableDataItem_TYPE_AUTO -1
#define RARERenderableDataItem_TYPE_BOOLEAN 64
#define RARERenderableDataItem_TYPE_BYTES 128
#define RARERenderableDataItem_TYPE_DATE 16
#define RARERenderableDataItem_TYPE_DATETIME 8
#define RARERenderableDataItem_TYPE_DECIMAL 4
#define RARERenderableDataItem_TYPE_INTEGER 2
#define RARERenderableDataItem_TYPE_STRING 1
#define RARERenderableDataItem_TYPE_STRUCT 256
#define RARERenderableDataItem_TYPE_TIME 32
#define RARERenderableDataItem_TYPE_UNKNOWN 0
#define RARERenderableDataItem_TYPE_WIDGET 1024

@interface RARERenderableDataItem : NSObject < RAREUTiFilterableList, NSCopying, RAREiPainterSupport, JavaLangComparable > {
 @public
  id modelData_;
  int columnSpan_;
  int rowSpan_;
  id<RAREiActionListener> actionListener_;
  id<RAREiPlatformIcon> alternateIcon_;
  RAREUIColor *bgColor_;
  id<RAREiDataConverter> dataConverter_;
  id<RAREiPlatformIcon> disabledIcon_;
  id<RAREiPlatformIcon> displayIcon_;
  IOSObjectArray *exportDataFlavors_;
  RAREUIColor *fgColor_;
  IOSObjectArray *importDataFlavors_;
  id<RAREiDataConverter> ldDataConverter_;
  id linkedData_;
  id linkedDataContext_;
  __weak RARERenderableDataItem *parentItem_;
  id spanData_;
  short int stateFlags_;
  id<RAREUTiFilterableList> subItems_;
  RAREUIFont *theFont_;
  int theType_;
  id theValue_;
  id<JavaLangCharSequence> tooltip_;
  BOOL useIdentityHashCode_;
  char userStateFlags_;
  id valueContext_;
  RARERenderableDataItem_CursorDisplayTypeEnum *cursorDisplayType_;
  float relativeFontSize_;
  RARERenderableDataItem_VerticalAlignEnum *verticalAlign_;
  BOOL usingDefaultConverter_;
  int swingVertAlign_;
  int swingHorizAlign_;
  BOOL setParentOnAdd_;
  RARERenderableDataItem_IconPositionEnum *iconPosition_;
  RARERenderableDataItem_HorizontalAlignEnum *horizontalAlign_;
  BOOL draggingAllowed_;
  id<JavaLangCharSequence> _toString_;
  id<RAREiPlatformComponentPainter> componentPainter_;
  BOOL converted_;
  NSString *cursorName_;
  id<JavaUtilMap> customProperties_;
  int height_;
  BOOL linkedConverted_;
  RAREUTObjectHolder *oneProperty_;
  RARERenderableDataItem_OrientationEnum *orientation_;
  id<RAREiPlatformComponent> renderingComponent_;
  int width_;
  float iconScaleFactor_;
  BOOL scaleIcon_;
  BOOL scaleHeaderIcon_;
}

+ (NSString *)TREE_MODEL_DATA;
+ (int)TYPE_ARRAY;
+ (int)TYPE_AUTO;
+ (int)TYPE_BOOLEAN;
+ (int)TYPE_BYTES;
+ (int)TYPE_DATE;
+ (int)TYPE_DATETIME;
+ (int)TYPE_DECIMAL;
+ (int)TYPE_INTEGER;
+ (int)TYPE_STRING;
+ (int)TYPE_STRUCT;
+ (int)TYPE_TIME;
+ (int)TYPE_UNKNOWN;
+ (int)TYPE_WIDGET;
+ (short int)ITEM_DISABLED;
+ (short int *)ITEM_DISABLEDRef;
+ (short int)ITEM_EDITABLE;
+ (short int *)ITEM_EDITABLERef;
+ (short int)ITEM_EDITABLE_SET;
+ (short int *)ITEM_EDITABLE_SETRef;
+ (short int)ITEM_HIDDEN;
+ (short int *)ITEM_HIDDENRef;
+ (short int)ITEM_MODIFIED;
+ (short int *)ITEM_MODIFIEDRef;
+ (short int)ITEM_NOT_SELECTABLE;
+ (short int *)ITEM_NOT_SELECTABLERef;
+ (JavaUtilHashMap *)typeMap;
- (id)init;
- (id)initWithId:(id)value;
- (id)initWithId:(id)value
         withInt:(int)type;
- (id)initWithNSString:(NSString *)value
                withId:(id)data;
- (id)initWithId:(id)value
         withInt:(int)type
          withId:(id)data;
- (id)initWithNSString:(NSString *)value
                withId:(id)data
 withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (id)initWithId:(id)value
         withInt:(int)type
          withId:(id)data
withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (id)initWithId:(id)value
         withInt:(int)type
          withId:(id)data
withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
          withId:(id)context;
- (id)initWithId:(id)value
         withInt:(int)type
          withId:(id)data
withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
 withRAREUIColor:(RAREUIColor *)fg;
- (BOOL)addWithId:(RARERenderableDataItem *)e;
- (BOOL)addWithNSString:(NSString *)s;
- (void)addWithInt:(int)index
            withId:(RARERenderableDataItem *)element;
- (BOOL)addWithRARERenderableDataItemArray:(IOSObjectArray *)items
                                   withInt:(int)pos
                                   withInt:(int)len;
- (BOOL)addAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)addAllWithRARERenderableDataItemArray:(IOSObjectArray *)items;
- (BOOL)addAllWithInt:(int)index
withJavaUtilCollection:(id<JavaUtilCollection>)c;
- (void)addAllWithInt:(int)index
withRARERenderableDataItemArray:(IOSObjectArray *)items;
- (void)addIndexToFilteredListWithInt:(int)index;
- (void)addItemWithRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)addMissingColumnsWithInt:(int)count;
- (void)addToFilteredListWithId:(RARERenderableDataItem *)e;
- (void)addToFilteredListWithInt:(int)index
                          withId:(RARERenderableDataItem *)e;
- (int)chopWithInt:(int)len;
- (void)clear;
- (void)clearSubItemData;
- (void)clearSubItemParents;
- (void)clearSubItemValues;
- (void)clearSubItems;
- (void)clearSubItemsEx;
- (id)clone;
- (int)compareToWithId:(RARERenderableDataItem *)o;
- (id<JavaUtilList>)concatWithJavaUtilListArray:(IOSObjectArray *)e;
- (BOOL)containsWithId:(id)o;
- (BOOL)containsAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (id)convertWithRAREiWidget:(id<RAREiWidget>)widget
                     withInt:(int)type
      withRAREiDataConverter:(id<RAREiDataConverter>)cvt
                      withId:(id)context;
- (RARERenderableDataItem *)copy__ OBJC_METHOD_FAMILY_NONE;
- (void)copy__WithRARERenderableDataItem:(RARERenderableDataItem *)item OBJC_METHOD_FAMILY_NONE;
- (void)copyExWithRARERenderableDataItem:(RARERenderableDataItem *)item OBJC_METHOD_FAMILY_NONE;
- (void)copyValueWithRARERenderableDataItem:(RARERenderableDataItem *)item OBJC_METHOD_FAMILY_NONE;
- (RARERenderableDataItem *)deepCopy;
- (void)deepCopyWithRARERenderableDataItem:(RARERenderableDataItem *)item;
+ (IOSObjectArray *)deepCopyWithRARERenderableDataItemArray:(IOSObjectArray *)b;
+ (IOSObjectArray *)deepCopyWithRARERenderableDataItemArray:(IOSObjectArray *)b
                                                    withInt:(int)pos
                                                    withInt:(int)len;
- (void)dispose;
- (double)doubleValue;
- (void)ensureCapacityWithInt:(int)capacity;
- (BOOL)isEqual:(id)o;
- (BOOL)equalsWithRARERenderableDataItem:(RARERenderableDataItem *)o;
- (BOOL)filterWithRAREUTiFilter:(id<RAREUTiFilter>)filter;
- (void)setFilteredListWithJavaUtilList:(id<JavaUtilList>)list
                      withRAREUTiFilter:(id<RAREUTiFilter>)lastFilter;
- (BOOL)filterWithNSString:(NSString *)filter;
- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains;
- (BOOL)filterWithNSString:(NSString *)filter
               withBoolean:(BOOL)contains
               withBoolean:(BOOL)nullPasses
               withBoolean:(BOOL)emptyPasses;
- (int)findWithRAREUTiFilter:(id<RAREUTiFilter>)filter
                     withInt:(int)start;
- (int)findWithNSString:(NSString *)filter
                withInt:(int)start;
- (int)findWithNSString:(NSString *)filter
                withInt:(int)start
            withBoolean:(BOOL)contains;
- (RARERenderableDataItem *)findLinkedDataWithId:(id)val;
+ (IOSIntArray *)findLinkedDataObjectsExWithJavaUtilList:(id<JavaUtilList>)list
                                       withNSObjectArray:(IOSObjectArray *)value;
+ (RARERenderableDataItem *)findLinkedObjectWithJavaUtilList:(id<JavaUtilList>)list
                                                      withId:(id)value;
+ (int)findLinkedObjectIndexWithJavaUtilList:(id<JavaUtilList>)list
                                      withId:(id)value;
- (RARERenderableDataItem *)findValueWithId:(id)val;
+ (RARERenderableDataItem *)findValueWithJavaUtilList:(id<JavaUtilList>)list
                                               withId:(id)value;
+ (int)findValueExWithJavaUtilList:(id<JavaUtilList>)list
                            withId:(id)value;
+ (IOSIntArray *)findValuesExWithJavaUtilList:(id<JavaUtilList>)list
                            withNSObjectArray:(IOSObjectArray *)value;
- (float)floatValue;
+ (int)fromSPOTTypeWithInt:(int)type;
- (NSUInteger)hash;
- (int)identityIndexOfWithId:(id)o;
- (int)indexOfWithId:(id)o;
- (int)indexOfLinkedDataWithId:(id)value;
- (int)indexOfLinkedDataEqualsWithId:(id)value;
- (int)indexOfValueWithId:(id)value;
- (int)indexOfValueEqualsWithId:(id)value;
- (int)intValue;
- (NSString *)itemsToString;
- (id<JavaUtilIterator>)iterator;
- (NSString *)join;
- (NSString *)joinWithNSString:(NSString *)sep;
- (int)lastIndexOfWithId:(id)o;
- (BOOL)linkedDataEqualsWithId:(id)value;
- (id<JavaUtilListIterator>)listIterator;
- (id<JavaUtilListIterator>)listIteratorWithInt:(int)index;
- (long long int)longValue;
- (void)moveWithInt:(int)from
            withInt:(int)to;
- (NSNumber *)numberValue;
- (RARERenderableDataItem *)pop;
- (void)pushWithNSObjectArray:(IOSObjectArray *)e;
- (BOOL)refilter;
- (RARERenderableDataItem *)removeWithInt:(int)index;
- (BOOL)removeWithId:(id)o;
- (BOOL)removeAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (id)removeCustomPropertyWithId:(id)key;
- (void)removeRowsWithIntArray:(IOSIntArray *)indexes;
- (BOOL)isScaleIcon;
- (void)setScaleIconWithBoolean:(BOOL)scale_
                      withFloat:(float)scaleFactor;
- (float)getIconScaleFactor;
- (void)reset;
- (BOOL)retainAllWithJavaUtilCollection:(id<JavaUtilCollection>)c;
- (id<JavaUtilList>)reverse;
- (RARERenderableDataItem *)shift;
- (int)size;
- (id<JavaUtilList>)sliceWithInt:(int)start;
- (id<JavaUtilList>)sliceWithInt:(int)start
                         withInt:(int)end;
- (void)sort;
- (void)sortWithBoolean:(BOOL)descending;
- (void)sortWithJavaUtilComparator:(id<JavaUtilComparator>)comparator;
- (id<JavaUtilList>)spliceWithInt:(int)index
                          withInt:(int)howMany;
- (id<JavaUtilList>)spliceWithInt:(int)index
                          withInt:(int)howMany
                withNSObjectArray:(IOSObjectArray *)e;
- (id<JavaUtilList>)spliceListWithInt:(int)index
                              withInt:(int)howMany
                     withJavaUtilList:(id<JavaUtilList>)e;
- (id<JavaUtilList>)subListWithInt:(int)fromIndex
                           withInt:(int)toIndex;
- (void)swapWithInt:(int)index1
            withInt:(int)index2;
- (IOSObjectArray *)toArray;
- (IOSObjectArray *)toArrayWithNSObjectArray:(IOSObjectArray *)a;
- (id<JavaLangCharSequence>)toCharSequence;
- (id<JavaLangCharSequence>)toCharSequenceWithRAREiWidget:(id<RAREiWidget>)widget;
- (id<JavaLangCharSequence>)toCharSequenceWithRAREiWidget:(id<RAREiWidget>)widget
                                   withRAREiDataConverter:(id<RAREiDataConverter>)defConverter
                                                   withId:(id)defContext;
- (id<JavaLangCharSequence>)toCharSequenceWithRAREiWidget:(id<RAREiWidget>)widget
                                                   withId:(id)value
                                   withRAREiDataConverter:(id<RAREiDataConverter>)defConverter
                                                   withId:(id)defContext;
+ (RARERenderableDataItem *)toItemWithId:(id)o;
+ (RARERenderableDataItem *)toParentItemWithRAREUTiFilterableList:(id<RAREUTiFilterableList>)children;
- (NSString *)description;
- (NSString *)toStringWithRAREiWidget:(id<RAREiWidget>)widget;
- (void)toStringWithJavaLangStringBuilder:(JavaLangStringBuilder *)sb
                             withNSString:(NSString *)tab;
- (NSString *)toStringWithRAREiWidget:(id<RAREiWidget>)widget
               withRAREiDataConverter:(id<RAREiDataConverter>)defConverter
                               withId:(id)defContext;
- (void)trimToWithInt:(int)size;
- (void)trimToSize;
+ (int)typeOfWithNSString:(NSString *)name;
- (BOOL)unfilter;
- (void)clearUseStateFlags;
- (void)unsetUserStateFlagWithByte:(char)flag;
- (void)unsetStateFlagWithShort:(short int)flag;
- (void)unshiftWithId:(RARERenderableDataItem *)value;
- (BOOL)valueEqualsWithId:(id)val;
- (RARERenderableDataItem *)setWithInt:(int)index
                                withId:(RARERenderableDataItem *)element;
- (void)setActionCodeWithRAREiWidget:(id<RAREiWidget>)context
                        withNSString:(NSString *)code;
- (void)setActionListenerWithRAREiActionListener:(id<RAREiActionListener>)al;
- (BOOL)setAllWithJavaUtilCollection:(id<JavaUtilCollection>)collection;
- (void)setAlternateIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setBackgroundWithRAREUIColor:(RAREUIColor *)bg;
- (void)setBackgroundOverlayPainterWithRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)bp;
- (void)setBackgroundPainterWithRAREiBackgroundPainter:(id<RAREiBackgroundPainter>)bp;
- (void)setBorderWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)itemBorder;
- (void)setColumnSpanWithInt:(int)span;
- (void)setComponentPainterWithRAREiPlatformComponentPainter:(id<RAREiPlatformComponentPainter>)cp;
- (void)setConvertedWithBoolean:(BOOL)converted;
- (void)setConverterWithRAREUTiStringConverter:(id<RAREUTiStringConverter>)converter;
- (void)setConverterClassWithIOSClass:(IOSClass *)cls;
- (void)setCursorDisplayTypeWithRARERenderableDataItem_CursorDisplayTypeEnum:(RARERenderableDataItem_CursorDisplayTypeEnum *)cursorDisplayType;
- (void)setCursorNameWithNSString:(NSString *)name;
- (void)setCustomPropertiesWithJavaUtilMap:(id<JavaUtilMap>)map;
- (id)setCustomPropertyWithId:(id)key
                       withId:(id)value;
- (void)setDataConverterWithRAREiDataConverter:(id<RAREiDataConverter>)cvt;
- (void)setDisabledIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setDraggingAllowedWithBoolean:(BOOL)draggingAllowed;
- (void)setEditableWithBoolean:(BOOL)editable;
- (void)setEnabledWithBoolean:(BOOL)enabled;
- (void)setExportDataFlavorsWithNSStringArray:(IOSObjectArray *)flavorNames;
- (void)setFilterOnLinkedDataWithBoolean:(BOOL)enable;
- (void)setFontWithRAREUIFont:(RAREUIFont *)font;
- (void)setForegroundWithRAREUIColor:(RAREUIColor *)fg;
- (void)setHeightWithInt:(int)height;
- (void)setHorizontalAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)alignment;
- (void)setIconWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon;
- (void)setIconPositionWithRARERenderableDataItem_IconPositionEnum:(RARERenderableDataItem_IconPositionEnum *)position;
- (void)setImportDataFlavorsWithNSStringArray:(IOSObjectArray *)flavorNames;
- (RARERenderableDataItem *)setItemWithInt:(int)pos
                withRARERenderableDataItem:(RARERenderableDataItem *)item;
- (void)setItemCountWithInt:(int)count;
- (void)setItemsWithJavaUtilList:(id<JavaUtilList>)items;
- (void)setItemsWithRARERenderableDataItemArray:(IOSObjectArray *)items;
- (void)setItemsWithRARERenderableDataItemArray:(IOSObjectArray *)items
                                        withInt:(int)count;
- (void)setLinkedDataWithId:(id)data;
- (void)setLinkedDataContextWithId:(id)context;
- (void)setLinkedDataConverterWithRAREiDataConverter:(id<RAREiDataConverter>)cvt;
- (void)setModelDataWithId:(id)modelData;
- (void)setModifiedWithBoolean:(BOOL)modified;
- (void)setOrientationWithRARERenderableDataItem_OrientationEnum:(RARERenderableDataItem_OrientationEnum *)orientation;
- (void)setParentItemWithRARERenderableDataItem:(RARERenderableDataItem *)parent;
- (void)setRenderingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)comp;
- (void)setRowSpanWithInt:(int)span;
- (void)setSelectableWithBoolean:(BOOL)selectable;
- (void)setSpanningDataWithId:(id)spandata;
- (void)setStateFlagWithShort:(short int)flag;
- (void)setToStringValueWithJavaLangCharSequence:(id<JavaLangCharSequence>)value;
- (void)setTooltipWithJavaLangCharSequence:(id<JavaLangCharSequence>)tooltip;
- (void)setTypeWithInt:(int)type;
- (void)setUseIdentityHashCodeWithBoolean:(BOOL)useIdentityHashCode;
- (void)setUserStateFlagWithByte:(char)flag;
- (void)setValueWithId:(id)value;
- (void)setValueContextWithId:(id)context;
- (void)setValuesWithId:(id)value
                withInt:(int)type
                 withId:(id)data
  withRAREiPlatformIcon:(id<RAREiPlatformIcon>)icon
                 withId:(id)context;
- (void)setVerticalAlignmentWithRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)alignment;
- (void)setVisibleWithBoolean:(BOOL)visible;
- (void)setWidthWithInt:(int)width;
- (RARERenderableDataItem *)getWithInt:(int)index;
- (id<RAREiActionListener>)getActionListener;
- (id<RAREiPlatformIcon>)getAlternateIcon;
- (RAREUIColor *)getBackground;
- (id<RAREiPlatformBorder>)getBorder;
- (int)getColumnSpan;
- (id<RAREiPlatformComponentPainter>)getComponentPainter;
- (id<RAREUTiStringConverter>)getConverter;
- (RARERenderableDataItem_CursorDisplayTypeEnum *)getCursorDisplayType;
- (NSString *)getCursorName;
- (id<JavaUtilMap>)getCustomProperties;
- (id)getCustomPropertyWithId:(id)key;
- (id<RAREiDataConverter>)getDataConverter;
+ (IOSClass *)getDefaultConverterClassWithInt:(int)type;
- (id<RAREiPlatformIcon>)getDisabledIcon;
- (IOSObjectArray *)getExportableDataFlavors;
- (id<JavaUtilList>)getFilteredList;
- (RAREUIFont *)getFont;
- (RAREUIColor *)getForeground;
- (int)getHeight;
- (RARERenderableDataItem_HorizontalAlignEnum *)getHorizontalAlignment;
- (id<RAREiPlatformIcon>)getIcon;
- (RARERenderableDataItem_IconPositionEnum *)getIconPosition;
- (RARERenderableDataItem_IconPositionEnum *)getIconPositionWithRARERenderableDataItem_IconPositionEnum:(RARERenderableDataItem_IconPositionEnum *)position;
- (IOSObjectArray *)getImportableDataFlavors;
- (RARERenderableDataItem *)getItemWithInt:(int)pos;
- (int)getItemCount;
- (RARERenderableDataItem *)getItemExWithInt:(int)pos;
- (id<RAREUTiFilterableList>)getItems;
- (IOSClass *)getJavaClass;
- (id<RAREUTiFilter>)getLastFilter;
- (id)getLinkedData;
- (id)getLinkedDataContext;
- (id<RAREiDataConverter>)getLinkedDataConverter;
- (NSString *)getMethods;
- (id)getModelData;
- (RARERenderableDataItem_OrientationEnum *)getOrientation;
- (RARERenderableDataItem *)getParentItem;
- (id<RAREiPlatformComponent>)getRenderingComponent;
- (void)dealloc;
- (int)getRowSpan;
- (id)getSpanningData;
- (short int)getStateFlags;
- (int)getSwingHorizAlignment;
+ (int)getSwingHorizAlignmentWithRARERenderableDataItem_HorizontalAlignEnum:(RARERenderableDataItem_HorizontalAlignEnum *)alignment;
- (int)getSwingHorizAlignmentWithInt:(int)halign;
- (int)getSwingVertAlignment;
- (int)getSwingVertAlignmentWithInt:(int)valign;
+ (int)getSwingVertAlignmentWithRARERenderableDataItem_VerticalAlignEnum:(RARERenderableDataItem_VerticalAlignEnum *)alignment;
- (id<JavaLangCharSequence>)getTooltip;
- (int)getType;
- (NSString *)getTypeName;
+ (NSString *)getTypeNameWithInt:(int)type;
- (id<JavaUtilList>)getUnfilteredList;
- (char)getUserStateFlags;
- (id)getValue;
- (id)getValueWithRAREiWidget:(id<RAREiWidget>)context;
- (id)getValueContext;
- (id)getValueEx;
- (RARERenderableDataItem_VerticalAlignEnum *)getVerticalAlignment;
- (int)getWidth;
- (BOOL)hasChildren;
- (BOOL)hasCustomPropertyWithNSString:(NSString *)name;
- (BOOL)hasCustomProperties;
- (BOOL)hasSpan;
- (BOOL)hasValue;
- (BOOL)isConverted;
- (BOOL)isDraggingAllowed;
- (BOOL)isEditable;
- (BOOL)isEditableSet;
- (BOOL)isEmpty;
- (BOOL)isEnabled;
- (BOOL)isFiltered;
- (BOOL)isModified;
- (BOOL)isSelectable;
- (BOOL)isStateFlagSetWithShort:(short int)flag;
- (BOOL)isUseIdentityHashCode;
- (BOOL)isVisible;
- (id)convertWithNSString:(NSString *)value;
- (id<JavaUtilList>)emptySubItemsListWithInt:(int)capacity;
- (id)fromStringWithRAREiWidget:(id<RAREiWidget>)widget
                        withInt:(int)type
                   withNSString:(NSString *)value
         withRAREiDataConverter:(id<RAREiDataConverter>)cvt
                         withId:(id)context;
- (void)setupDefaultConverter;
- (id<RAREUTiFilterableList>)subItemsListWithInt:(int)capacity;
- (NSString *)toStringWithRAREiWidget:(id<RAREiWidget>)widget
                               withId:(id)value
                               withId:(id)context;
- (void)setSubItemsWithRAREUTiFilterableList:(id<RAREUTiFilterableList>)subs;
- (JavaLangStringBuilder *)getMethodsWithJavaLangStringBuilder:(JavaLangStringBuilder *)sb;
- (id)copyWithZone:(NSZone *)zone;
- (void)copyAllFieldsTo:(RARERenderableDataItem *)other;
@end

J2OBJC_FIELD_SETTER(RARERenderableDataItem, modelData_, id)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, actionListener_, id<RAREiActionListener>)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, alternateIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, bgColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, dataConverter_, id<RAREiDataConverter>)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, disabledIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, displayIcon_, id<RAREiPlatformIcon>)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, exportDataFlavors_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, fgColor_, RAREUIColor *)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, importDataFlavors_, IOSObjectArray *)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, ldDataConverter_, id<RAREiDataConverter>)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, linkedData_, id)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, linkedDataContext_, id)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, spanData_, id)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, subItems_, id<RAREUTiFilterableList>)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, theFont_, RAREUIFont *)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, theValue_, id)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, tooltip_, id<JavaLangCharSequence>)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, valueContext_, id)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, cursorDisplayType_, RARERenderableDataItem_CursorDisplayTypeEnum *)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, verticalAlign_, RARERenderableDataItem_VerticalAlignEnum *)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, iconPosition_, RARERenderableDataItem_IconPositionEnum *)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, horizontalAlign_, RARERenderableDataItem_HorizontalAlignEnum *)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, _toString_, id<JavaLangCharSequence>)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, componentPainter_, id<RAREiPlatformComponentPainter>)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, cursorName_, NSString *)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, customProperties_, id<JavaUtilMap>)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, oneProperty_, RAREUTObjectHolder *)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, orientation_, RARERenderableDataItem_OrientationEnum *)
J2OBJC_FIELD_SETTER(RARERenderableDataItem, renderingComponent_, id<RAREiPlatformComponent>)

typedef RARERenderableDataItem ComAppnativaRareUiRenderableDataItem;

typedef enum {
  RARERenderableDataItem_CursorDisplayType_ICON = 0,
  RARERenderableDataItem_CursorDisplayType_TEXT = 1,
  RARERenderableDataItem_CursorDisplayType_TEXT_AND_ICON = 2,
  RARERenderableDataItem_CursorDisplayType_HYPERLINKS = 3,
  RARERenderableDataItem_CursorDisplayType_HYPERLINKS_AND_ICON = 4,
  RARERenderableDataItem_CursorDisplayType_COMPONENT = 5,
  RARERenderableDataItem_CursorDisplayType_ITEM = 6,
} RARERenderableDataItem_CursorDisplayType;

@interface RARERenderableDataItem_CursorDisplayTypeEnum : JavaLangEnum < NSCopying > {
}
+ (RARERenderableDataItem_CursorDisplayTypeEnum *)ICON;
+ (RARERenderableDataItem_CursorDisplayTypeEnum *)TEXT;
+ (RARERenderableDataItem_CursorDisplayTypeEnum *)TEXT_AND_ICON;
+ (RARERenderableDataItem_CursorDisplayTypeEnum *)HYPERLINKS;
+ (RARERenderableDataItem_CursorDisplayTypeEnum *)HYPERLINKS_AND_ICON;
+ (RARERenderableDataItem_CursorDisplayTypeEnum *)COMPONENT;
+ (RARERenderableDataItem_CursorDisplayTypeEnum *)ITEM;
+ (IOSObjectArray *)values;
+ (RARERenderableDataItem_CursorDisplayTypeEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

typedef enum {
  RARERenderableDataItem_HorizontalAlign_AUTO = 0,
  RARERenderableDataItem_HorizontalAlign_CENTER = 1,
  RARERenderableDataItem_HorizontalAlign_LEFT = 2,
  RARERenderableDataItem_HorizontalAlign_RIGHT = 3,
  RARERenderableDataItem_HorizontalAlign_LEADING = 4,
  RARERenderableDataItem_HorizontalAlign_TRAILING = 5,
  RARERenderableDataItem_HorizontalAlign_FILL = 6,
} RARERenderableDataItem_HorizontalAlign;

@interface RARERenderableDataItem_HorizontalAlignEnum : JavaLangEnum < NSCopying > {
}
+ (RARERenderableDataItem_HorizontalAlignEnum *)AUTO;
+ (RARERenderableDataItem_HorizontalAlignEnum *)CENTER;
+ (RARERenderableDataItem_HorizontalAlignEnum *)LEFT;
+ (RARERenderableDataItem_HorizontalAlignEnum *)RIGHT;
+ (RARERenderableDataItem_HorizontalAlignEnum *)LEADING;
+ (RARERenderableDataItem_HorizontalAlignEnum *)TRAILING;
+ (RARERenderableDataItem_HorizontalAlignEnum *)FILL;
+ (IOSObjectArray *)values;
+ (RARERenderableDataItem_HorizontalAlignEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

typedef enum {
  RARERenderableDataItem_IconPosition_AUTO = 0,
  RARERenderableDataItem_IconPosition_LEFT = 1,
  RARERenderableDataItem_IconPosition_RIGHT = 2,
  RARERenderableDataItem_IconPosition_TOP_CENTER = 3,
  RARERenderableDataItem_IconPosition_BOTTOM_CENTER = 4,
  RARERenderableDataItem_IconPosition_LEADING = 5,
  RARERenderableDataItem_IconPosition_TRAILING = 6,
  RARERenderableDataItem_IconPosition_TOP_LEFT = 7,
  RARERenderableDataItem_IconPosition_TOP_RIGHT = 8,
  RARERenderableDataItem_IconPosition_BOTTOM_LEFT = 9,
  RARERenderableDataItem_IconPosition_BOTTOM_RIGHT = 10,
  RARERenderableDataItem_IconPosition_RIGHT_JUSTIFIED = 11,
} RARERenderableDataItem_IconPosition;

@interface RARERenderableDataItem_IconPositionEnum : JavaLangEnum < NSCopying > {
}
+ (RARERenderableDataItem_IconPositionEnum *)AUTO;
+ (RARERenderableDataItem_IconPositionEnum *)LEFT;
+ (RARERenderableDataItem_IconPositionEnum *)RIGHT;
+ (RARERenderableDataItem_IconPositionEnum *)TOP_CENTER;
+ (RARERenderableDataItem_IconPositionEnum *)BOTTOM_CENTER;
+ (RARERenderableDataItem_IconPositionEnum *)LEADING;
+ (RARERenderableDataItem_IconPositionEnum *)TRAILING;
+ (RARERenderableDataItem_IconPositionEnum *)TOP_LEFT;
+ (RARERenderableDataItem_IconPositionEnum *)TOP_RIGHT;
+ (RARERenderableDataItem_IconPositionEnum *)BOTTOM_LEFT;
+ (RARERenderableDataItem_IconPositionEnum *)BOTTOM_RIGHT;
+ (RARERenderableDataItem_IconPositionEnum *)RIGHT_JUSTIFIED;
+ (IOSObjectArray *)values;
+ (RARERenderableDataItem_IconPositionEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

typedef enum {
  RARERenderableDataItem_Orientation_AUTO = 0,
  RARERenderableDataItem_Orientation_VERTICAL_DOWN = 1,
  RARERenderableDataItem_Orientation_HORIZONTAL = 2,
  RARERenderableDataItem_Orientation_VERTICAL_UP = 3,
  RARERenderableDataItem_Orientation_HORIZONTAL_FLIPPED = 4,
} RARERenderableDataItem_Orientation;

@interface RARERenderableDataItem_OrientationEnum : JavaLangEnum < NSCopying > {
}
+ (RARERenderableDataItem_OrientationEnum *)AUTO;
+ (RARERenderableDataItem_OrientationEnum *)VERTICAL_DOWN;
+ (RARERenderableDataItem_OrientationEnum *)HORIZONTAL;
+ (RARERenderableDataItem_OrientationEnum *)VERTICAL_UP;
+ (RARERenderableDataItem_OrientationEnum *)HORIZONTAL_FLIPPED;
+ (IOSObjectArray *)values;
+ (RARERenderableDataItem_OrientationEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

typedef enum {
  RARERenderableDataItem_VerticalAlign_AUTO = 0,
  RARERenderableDataItem_VerticalAlign_CENTER = 1,
  RARERenderableDataItem_VerticalAlign_TOP = 2,
  RARERenderableDataItem_VerticalAlign_BOTTOM = 3,
  RARERenderableDataItem_VerticalAlign_FILL = 4,
} RARERenderableDataItem_VerticalAlign;

@interface RARERenderableDataItem_VerticalAlignEnum : JavaLangEnum < NSCopying > {
}
+ (RARERenderableDataItem_VerticalAlignEnum *)AUTO;
+ (RARERenderableDataItem_VerticalAlignEnum *)CENTER;
+ (RARERenderableDataItem_VerticalAlignEnum *)TOP;
+ (RARERenderableDataItem_VerticalAlignEnum *)BOTTOM;
+ (RARERenderableDataItem_VerticalAlignEnum *)FILL;
+ (IOSObjectArray *)values;
+ (RARERenderableDataItem_VerticalAlignEnum *)valueOfWithNSString:(NSString *)name;
- (id)copyWithZone:(NSZone *)zone;
- (id)initWithNSString:(NSString *)__name withInt:(int)__ordinal;
@end

@interface RARERenderableDataItem_$1 : NSObject < RAREUTiStringConverter > {
}

- (NSString *)toStringWithId:(RARERenderableDataItem *)element;
- (id)init;
@end

#endif // _RARERenderableDataItem_H_