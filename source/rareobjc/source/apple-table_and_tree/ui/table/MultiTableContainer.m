//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/ui/table/MultiTableContainer.java
//
//  Created by decoteaud on 7/29/15.
//

#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/Container.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/table/MultiTableContainer.h"
#include "com/appnativa/rare/ui/table/MultiTableLayout.h"
#include "com/appnativa/rare/ui/table/TableComponent.h"
#include "com/appnativa/rare/ui/table/aTableHeader.h"
#include "java/lang/Math.h"
#include "java/util/List.h"

@implementation RAREMultiTableContainer

- (id)initWithRAREMultiTableLayout:(RAREMultiTableLayout *)view {
  if (self = [super initWithRAREParentView:view]) {
    preferedSizeDirty_ = YES;
    rowHeaderSize_ = [[RAREUIDimension alloc] init];
    rowFooterSize_ = [[RAREUIDimension alloc] init];
    mainTableSize_ = [[RAREUIDimension alloc] init];
  }
  return self;
}

- (void)contentsChangedWithId:(id)source {
  preferedSizeDirty_ = YES;
}

- (void)contentsChangedWithId:(id)source
                      withInt:(int)index0
                      withInt:(int)index1 {
  preferedSizeDirty_ = YES;
}

- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size {
  float width = 0;
  float height = 0;
  int len = [self getComponentCount];
  for (int i = 0; i < len; i++) {
    RARETableComponent *tc = (RARETableComponent *) check_class_cast([self getComponentAtWithInt:i], [RARETableComponent class]);
    (void) [((RARETableComponent *) nil_chk(tc)) getMinimumSizeWithRAREUIDimension:size];
    height = [JavaLangMath maxWithFloat:height withFloat:((RAREUIDimension *) nil_chk(size))->height_];
    width += size->width_;
  }
  height = [JavaLangMath maxWithFloat:height withFloat:((RAREUIDimension *) nil_chk(size))->height_];
  width += size->width_;
  size->width_ = width;
  size->height_ = height;
}

- (void)setForegroundWithRAREUIColor:(RAREUIColor *)fg {
  [super setForegroundWithRAREUIColor:fg];
  int len = [self getComponentCount];
  for (int i = 0; i < len; i++) {
    RARETableComponent *tc = (RARETableComponent *) check_class_cast([self getComponentAtWithInt:i], [RARETableComponent class]);
    [((RARETableComponent *) nil_chk(tc)) setForegroundWithRAREUIColor:fg];
  }
}

- (void)setBackgroundWithRAREUIColor:(RAREUIColor *)bg {
  backgroundColor_ = bg;
  int len = [self getComponentCount];
  for (int i = 0; i < len; i++) {
    RARETableComponent *tc = (RARETableComponent *) check_class_cast([self getComponentAtWithInt:i], [RARETableComponent class]);
    [((RARETableComponent *) nil_chk(tc)) setBackgroundWithRAREUIColor:bg];
  }
}

- (void)setFontWithRAREUIFont:(RAREUIFont *)f {
  [super setFontWithRAREUIFont:f];
  int len = [self getComponentCount];
  for (int i = 0; i < len; i++) {
    RARETableComponent *tc = (RARETableComponent *) check_class_cast([self getComponentAtWithInt:i], [RARETableComponent class]);
    [((RARETableComponent *) nil_chk(tc)) setFontWithRAREUIFont:f];
  }
}

- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth {
  float width = 0;
  float height = 0;
  if (preferedSizeDirty_) {
    int len = [self getComponentCount];
    RARETableComponent *main = nil;
    for (int i = 0; i < len; i++) {
      RARETableComponent *tc = (RARETableComponent *) check_class_cast([self getComponentAtWithInt:i], [RARETableComponent class]);
      if ([((RARETableComponent *) nil_chk(tc)) isMainTable]) {
        main = tc;
      }
      else {
        (void) [tc getPreferredSizeWithRAREUIDimension:size];
        if (main == nil) {
          [((RAREUIDimension *) nil_chk(rowHeaderSize_)) setSizeWithRAREUIDimension:size];
        }
        else {
          [((RAREUIDimension *) nil_chk(rowFooterSize_)) setSizeWithRAREUIDimension:size];
        }
        height = [JavaLangMath maxWithFloat:height withFloat:((RAREUIDimension *) nil_chk(size))->height_];
        width += size->width_;
      }
    }
    maxWidth = maxWidth - width;
    (void) [((RARETableComponent *) nil_chk(main)) getPreferredSizeWithRAREUIDimension:size withFloat:[JavaLangMath maxWithFloat:maxWidth withFloat:0]];
    [((RAREUIDimension *) nil_chk(mainTableSize_)) setSizeWithRAREUIDimension:size];
    height = [JavaLangMath maxWithFloat:height withFloat:((RAREUIDimension *) nil_chk(size))->height_];
    width += size->width_;
    preferedSizeDirty_ = NO;
  }
  else {
    height = [JavaLangMath maxWithFloat:height withFloat:((RAREUIDimension *) nil_chk(rowHeaderSize_))->height_];
    height = [JavaLangMath maxWithFloat:height withFloat:((RAREUIDimension *) nil_chk(mainTableSize_))->height_];
    height = [JavaLangMath maxWithFloat:height withFloat:((RAREUIDimension *) nil_chk(rowFooterSize_))->height_];
    width = rowHeaderSize_->width_ + mainTableSize_->width_ + rowFooterSize_->width_;
  }
  ((RAREUIDimension *) nil_chk(size))->width_ = width;
  size->height_ = height;
}

- (void)intervalAddedWithId:(id)source
                    withInt:(int)index0
                    withInt:(int)index1 {
  preferedSizeDirty_ = YES;
}

- (void)intervalRemovedWithId:(id)source
                      withInt:(int)index0
                      withInt:(int)index1
             withJavaUtilList:(id<JavaUtilList>)removed {
  preferedSizeDirty_ = YES;
}

- (void)layoutWithFloat:(float)width
              withFloat:(float)height {
  RARETableComponent *main = nil;
  RARETableComponent *header = nil;
  RARETableComponent *footer = nil;
  int len = [self getComponentCount];
  int hh = 0;
  for (int i = 0; i < len; i++) {
    RARETableComponent *tc = (RARETableComponent *) check_class_cast([self getComponentAtWithInt:i], [RARETableComponent class]);
    if ([((RARETableComponent *) nil_chk(tc)) isMainTable]) {
      main = tc;
    }
    else {
      if (main == nil) {
        header = tc;
        if (preferedSizeDirty_) {
          (void) [tc getPreferredSizeWithRAREUIDimension:rowHeaderSize_];
        }
      }
      else {
        footer = tc;
        if (preferedSizeDirty_) {
          (void) [tc getPreferredSizeWithRAREUIDimension:rowFooterSize_];
        }
      }
    }
    hh = [JavaLangMath maxWithInt:hh withInt:[((RAREUIDimension *) nil_chk([((RAREaTableHeader *) nil_chk([tc getTableHeader])) getPreferredSize])) intHeight]];
  }
  float x = 0;
  float y = 0;
  id<RAREiPlatformBorder> b = [self getBorder];
  RAREUIInsets *in = (b == nil) ? nil : [b getBorderInsetsExWithRAREUIInsets:[[RAREUIInsets alloc] init]];
  if (in != nil) {
    x = in->left_;
    width -= (in->left_ + in->right_);
    y = in->top_;
    height -= (in->top_ + in->bottom_);
  }
  float w = width;
  if (header != nil) {
    [header setMeasuredHeaderHeightWithInt:hh];
    [header setBoundsWithFloat:x withFloat:y withFloat:((RAREUIDimension *) nil_chk(rowHeaderSize_))->width_ withFloat:height];
    x += rowHeaderSize_->width_;
    w -= (x - ((in == nil) ? 0 : in->left_));
  }
  if (footer != nil) {
    [footer setMeasuredHeaderHeightWithInt:hh];
    [footer setBoundsWithFloat:width - ((RAREUIDimension *) nil_chk(rowFooterSize_))->width_ + ((in == nil) ? 0 : in->left_) withFloat:y withFloat:rowFooterSize_->width_ withFloat:height];
    w -= rowFooterSize_->width_;
  }
  [((RARETableComponent *) nil_chk(main)) setMeasuredHeaderHeightWithInt:hh];
  [main setBoundsWithFloat:x withFloat:y withFloat:w withFloat:height];
}

- (void)revalidate {
  [super revalidate];
  preferedSizeDirty_ = YES;
}

- (void)structureChangedWithId:(id)source {
  preferedSizeDirty_ = YES;
}

- (void)copyAllFieldsTo:(RAREMultiTableContainer *)other {
  [super copyAllFieldsTo:other];
  other->mainTableSize_ = mainTableSize_;
  other->preferedSizeDirty_ = preferedSizeDirty_;
  other->rowFooterSize_ = rowFooterSize_;
  other->rowHeaderSize_ = rowHeaderSize_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getMinimumSizeExWithRAREUIDimension:", NULL, "V", 0x4, NULL },
    { "getPreferredSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "preferedSizeDirty_", NULL, 0x0, "Z" },
    { "rowHeaderSize_", NULL, 0x0, "LRAREUIDimension" },
    { "rowFooterSize_", NULL, 0x0, "LRAREUIDimension" },
    { "mainTableSize_", NULL, 0x0, "LRAREUIDimension" },
  };
  static J2ObjcClassInfo _RAREMultiTableContainer = { "MultiTableContainer", "com.appnativa.rare.ui.table", NULL, 0x1, 2, methods, 4, fields, 0, NULL};
  return &_RAREMultiTableContainer;
}

@end
