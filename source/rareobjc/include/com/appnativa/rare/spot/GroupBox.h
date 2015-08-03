//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/GroupBox.java
//
//  Created by decoteaud on 7/29/15.
//

#ifndef _RARESPOTGroupBox_H_
#define _RARESPOTGroupBox_H_

@class IOSIntArray;
@class IOSObjectArray;
@class JavaLangInteger;
@class RARESPOTGroupBox_CLayout;
@class RARESPOTGroupBox_CSubmitValue;
@class RARESPOTScrollPane;
@class RARESPOTWidget;
@class SPOTInteger;
@class SPOTPrintableString;
@class SPOTSet;
@protocol JavaUtilMap;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Viewer.h"
#include "com/appnativa/spot/SPOTEnumerated.h"

@interface RARESPOTGroupBox : RARESPOTViewer {
 @public
  RARESPOTGroupBox_CLayout *layout_;
  SPOTPrintableString *rows_;
  SPOTPrintableString *columns_;
  SPOTInteger *columnSpacing_;
  SPOTInteger *rowSpacing_;
  SPOTSet *columnGrouping_;
  SPOTSet *rowGrouping_;
  SPOTSet *cellPainters_;
  RARESPOTGroupBox_CSubmitValue *submitValue_;
  SPOTSet *widgets_;
  RARESPOTScrollPane *scrollPane_;
  id<JavaUtilMap> _widgetNameMap_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (SPOTSet *)getColumnGrouping;
- (SPOTSet *)getColumnGroupingReference;
- (void)setColumnGroupingWithISPOTElement:(id<iSPOTElement>)reference;
- (SPOTSet *)getRowGrouping;
- (SPOTSet *)getRowGroupingReference;
- (void)setRowGroupingWithISPOTElement:(id<iSPOTElement>)reference;
- (SPOTSet *)getCellPainters;
- (SPOTSet *)getCellPaintersReference;
- (void)setCellPaintersWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTScrollPane *)getScrollPane;
- (RARESPOTScrollPane *)getScrollPaneReference;
- (void)setScrollPaneWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTWidget *)findWidgetWithNSString:(NSString *)name
                               withBoolean:(BOOL)useNameMap;
- (RARESPOTWidget *)findWidgetWithNSString:(NSString *)name;
- (id<JavaUtilMap>)createWidgetMap;
- (RARESPOTWidget *)getWidgetWithInt:(int)index;
- (RARESPOTWidget *)removeWidgetAtWithInt:(int)index;
- (int)getWidgetCount;
- (RARESPOTWidget *)findWidgetWithInt:(int)x
                              withInt:(int)y;
- (void)addWidgetWithRARESPOTWidget:(RARESPOTWidget *)w;
- (void)removeWidgetWithRARESPOTWidget:(RARESPOTWidget *)w;
- (void)copyAllFieldsTo:(RARESPOTGroupBox *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTGroupBox, layout_, RARESPOTGroupBox_CLayout *)
J2OBJC_FIELD_SETTER(RARESPOTGroupBox, rows_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTGroupBox, columns_, SPOTPrintableString *)
J2OBJC_FIELD_SETTER(RARESPOTGroupBox, columnSpacing_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTGroupBox, rowSpacing_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTGroupBox, columnGrouping_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTGroupBox, rowGrouping_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTGroupBox, cellPainters_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTGroupBox, submitValue_, RARESPOTGroupBox_CSubmitValue *)
J2OBJC_FIELD_SETTER(RARESPOTGroupBox, widgets_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTGroupBox, scrollPane_, RARESPOTScrollPane *)
J2OBJC_FIELD_SETTER(RARESPOTGroupBox, _widgetNameMap_, id<JavaUtilMap>)

typedef RARESPOTGroupBox ComAppnativaRareSpotGroupBox;

#define RARESPOTGroupBox_CLayout_absolute 1
#define RARESPOTGroupBox_CLayout_custom 10
#define RARESPOTGroupBox_CLayout_flow 4
#define RARESPOTGroupBox_CLayout_forms 3
#define RARESPOTGroupBox_CLayout_table 2

@interface RARESPOTGroupBox_CLayout : SPOTEnumerated {
}

+ (int)absolute;
+ (int)table;
+ (int)forms;
+ (int)flow;
+ (int)custom;
+ (IOSIntArray *)_nchoices;
+ (IOSObjectArray *)_schoices;
- (id)init;
- (id)initWithInt:(int)val;
- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional;
- (NSString *)spot_getValidityRange;
- (void)spot_setAttributes;
@end

#define RARESPOTGroupBox_CSubmitValue_viewer_linked_data 1
#define RARESPOTGroupBox_CSubmitValue_viewer_value 2
#define RARESPOTGroupBox_CSubmitValue_widget_values 0

@interface RARESPOTGroupBox_CSubmitValue : SPOTEnumerated {
}

+ (int)widget_values;
+ (int)viewer_linked_data;
+ (int)viewer_value;
+ (IOSIntArray *)_nchoices;
+ (IOSObjectArray *)_schoices;
- (id)init;
- (id)initWithInt:(int)val;
- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional;
- (NSString *)spot_getValidityRange;
@end

#endif // _RARESPOTGroupBox_H_
