//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/GridPane.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTGridPane_H_
#define _RARESPOTGridPane_H_

@class RARESPOTRegion;
@class RARESPOTScrollPane;
@class RARESPOTWidget;
@class SPOTBoolean;
@class SPOTInteger;
@class SPOTSet;
@protocol iSPOTElement;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Viewer.h"

@interface RARESPOTGridPane : RARESPOTViewer {
 @public
  SPOTInteger *rows_;
  SPOTInteger *columns_;
  SPOTInteger *columnSpacing_;
  SPOTInteger *rowSpacing_;
  SPOTSet *columnGrouping_;
  SPOTSet *rowGrouping_;
  SPOTBoolean *actAsFormViewer_;
  RARESPOTScrollPane *scrollPane_;
  SPOTSet *regions_;
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
- (RARESPOTScrollPane *)getScrollPane;
- (RARESPOTScrollPane *)getScrollPaneReference;
- (void)setScrollPaneWithISPOTElement:(id<iSPOTElement>)reference;
- (RARESPOTWidget *)findWidgetWithNSString:(NSString *)name
                               withBoolean:(BOOL)useNameMap;
- (RARESPOTWidget *)findWidgetWithInt:(int)x
                              withInt:(int)y;
- (RARESPOTRegion *)findRegionWithNSString:(NSString *)name;
- (RARESPOTRegion *)findRegionWithInt:(int)x
                              withInt:(int)y;
- (void)copyAllFieldsTo:(RARESPOTGridPane *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTGridPane, rows_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTGridPane, columns_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTGridPane, columnSpacing_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTGridPane, rowSpacing_, SPOTInteger *)
J2OBJC_FIELD_SETTER(RARESPOTGridPane, columnGrouping_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTGridPane, rowGrouping_, SPOTSet *)
J2OBJC_FIELD_SETTER(RARESPOTGridPane, actAsFormViewer_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTGridPane, scrollPane_, RARESPOTScrollPane *)
J2OBJC_FIELD_SETTER(RARESPOTGridPane, regions_, SPOTSet *)

typedef RARESPOTGridPane ComAppnativaRareSpotGridPane;

#endif // _RARESPOTGridPane_H_
