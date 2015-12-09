//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/ui/aFlowPanel.java
//
//  Created by decoteaud on 12/8/15.
//

#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iConstants.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/Container.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/XPContainer.h"
#include "com/appnativa/rare/ui/aComponent.h"
#include "com/appnativa/rare/ui/aFlowPanel.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/jgoodies/forms/layout/CellConstraints.h"
#include "java/lang/Math.h"
#include "java/util/ArrayList.h"

@implementation RAREaFlowPanel

- (id)initWithId:(id)view {
  if (self = [super initWithId:view]) {
    columnSpacing_ = 2;
    layoutlist_ = [[JavaUtilArrayList alloc] init];
    rowSpacing_ = 2;
  }
  return self;
}

- (void)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                               withId:(id)constraints
                              withInt:(int)position {
  [super addWithRAREiPlatformComponent:c withId:constraints withInt:position];
  if (!([constraints isKindOfClass:[RAREJGCellConstraints class]])) {
    constraints = [[RAREJGCellConstraints alloc] init];
  }
  [((id<RAREiPlatformComponent>) nil_chk(c)) putClientPropertyWithNSString:[RAREiConstants RARE_CONSTRAINTS_PROPERTY] withId:constraints];
  cacheInvalidated_ = YES;
}

- (void)setBoundsWithFloat:(float)x
                 withFloat:(float)y
                 withFloat:(float)w
                 withFloat:(float)h {
  if (needsMultitplePasses_) {
    if (secondPass_) {
      secondPass_ = NO;
    }
    else {
      secondPass_ = YES;
      [self requestRevalidationForBoundsChange];
    }
  }
  [super setBoundsWithFloat:x withFloat:y withFloat:w withFloat:h];
}

- (BOOL)heightChangesBasedOnWidth {
  return YES;
}

- (void)setColumnSpacingWithInt:(int)columnSpacing {
  self->columnSpacing_ = columnSpacing;
}

- (void)setRowSpacingWithInt:(int)rowSpacing {
  self->rowSpacing_ = rowSpacing;
}

- (int)getColumnSpacing {
  return columnSpacing_;
}

- (int)getRowSpacing {
  return rowSpacing_;
}

- (void)layoutWithFloat:(float)width
              withFloat:(float)height {
  RAREUIInsets *in = [self getInsetsWithRAREUIInsets:computeInsets_];
  float x = ((RAREUIInsets *) nil_chk(in))->left_;
  float y = in->top_;
  float w;
  float h = 0;
  width -= (in->left_ + in->right_);
  height -= (in->top_ + in->bottom_);
  id<RAREiPlatformComponent> c;
  int len = [self getComponentCount];
  if (cacheInvalidated_) {
    [self populateSizeCache];
  }
  JavaUtilArrayList *list = layoutlist_;
  [((JavaUtilArrayList *) nil_chk(list)) clear];
  for (int i = 0; i < len; i++) {
    c = [self getComponentAtWithInt:i];
    if (![((id<RAREiPlatformComponent>) nil_chk(c)) isVisible]) {
      continue;
    }
    RAREJGCellConstraints *cc = (RAREJGCellConstraints *) check_class_cast([c getClientPropertyWithNSString:[RAREiConstants RARE_CONSTRAINTS_PROPERTY]], [RAREJGCellConstraints class]);
    w = ((RAREJGCellConstraints *) nil_chk(cc))->gridWidth_;
    if (x + w > width) {
      [self layoutWithJavaUtilArrayList:list withFloat:in->left_ withFloat:y withFloat:(h > height) ? height : h];
      x = in->left_;
      y += h + rowSpacing_;
      [list clear];
      height -= h;
      h = 0;
      if (height < 1) {
        break;
      }
    }
    [list addWithId:c];
    x += w + columnSpacing_;
    h = [JavaLangMath maxWithFloat:h withFloat:cc->gridHeight_];
  }
  if (![list isEmpty]) {
    [self layoutWithJavaUtilArrayList:list withFloat:in->left_ withFloat:y withFloat:(h > height) ? height : h];
  }
}

- (void)layoutWithJavaUtilArrayList:(JavaUtilArrayList *)list
                          withFloat:(float)x
                          withFloat:(float)y
                          withFloat:(float)height {
  int len = [((JavaUtilArrayList *) nil_chk(list)) size];
  float yy;
  float h;
  for (int i = 0; i < len; i++) {
    id<RAREiPlatformComponent> c = [list getWithInt:i];
    RAREJGCellConstraints *cc = (RAREJGCellConstraints *) check_class_cast([((id<RAREiPlatformComponent>) nil_chk(c)) getClientPropertyWithNSString:[RAREiConstants RARE_CONSTRAINTS_PROPERTY]], [RAREJGCellConstraints class]);
    h = ((RAREJGCellConstraints *) nil_chk(cc))->gridHeight_;
    if (h > height) {
      h = height;
    }
    switch ([((RAREJGCellConstraints_Alignment *) nil_chk(cc->vAlign_)) abbreviation]) {
      case 't':
      yy = y;
      break;
      case 'c':
      yy = y + (height - h);
      break;
      case 'f':
      yy = 0;
      h = height;
      break;
      default:
      yy = y + height - h;
      break;
    }
    [c setBoundsWithFloat:x withFloat:yy withFloat:cc->gridWidth_ withFloat:h];
    x += cc->gridWidth_ + columnSpacing_;
  }
}

- (void)populateSizeCache {
  id<RAREiPlatformComponent> c;
  int len = [self getComponentCount];
  RAREUIDimension *size = [[RAREUIDimension alloc] init];
  for (int i = 0; i < len; i++) {
    c = [self getComponentAtWithInt:i];
    if (![((id<RAREiPlatformComponent>) nil_chk(c)) isVisible]) {
      continue;
    }
    RAREJGCellConstraints *cc = (RAREJGCellConstraints *) check_class_cast([c getClientPropertyWithNSString:[RAREiConstants RARE_CONSTRAINTS_PROPERTY]], [RAREJGCellConstraints class]);
    (void) [c getPreferredSizeWithRAREUIDimension:size];
    ((RAREJGCellConstraints *) nil_chk(cc))->gridHeight_ = (int) size->height_;
    cc->gridWidth_ = (int) size->width_;
    (void) [c getMinimumSizeWithRAREUIDimension:size];
    cc->gridY_ = (int) size->height_;
    cc->gridX_ = (int) size->width_;
  }
  cacheInvalidated_ = NO;
}

- (void)requestRevalidationForBoundsChange {
  [RAREPlatform invokeLaterWithJavaLangRunnable:[[RAREaFlowPanel_$1 alloc] initWithRAREaFlowPanel:self]];
}

- (void)getMinimumSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  if (preferredSizeWidth_ > 0) {
    [self getPreferredSizeExWithRAREUIDimension:size withFloat:0];
    return;
  }
  if (cacheInvalidated_) {
    [self populateSizeCache];
  }
  [((RAREUIDimension *) nil_chk(size)) setSizeWithInt:0 withInt:0];
  id<RAREiPlatformComponent> c;
  int len = [self getComponentCount];
  for (int i = 0; i < len; i++) {
    c = [self getComponentAtWithInt:i];
    if (![((id<RAREiPlatformComponent>) nil_chk(c)) isVisible]) {
      continue;
    }
    (void) [c getPreferredSizeWithRAREUIDimension:size];
    break;
  }
  if ((preferredSizeWidth_ > 0) || (needsMultitplePasses_ && secondPass_)) {
    float w = size->width_;
    [self getPreferredSizeExWithRAREUIDimension:size withFloat:0];
    size->width_ = w;
  }
}

- (void)getPreferredSizeExWithRAREUIDimension:(RAREUIDimension *)size
                                    withFloat:(float)maxWidth {
  if (preferredSizeWidth_ > 0) {
    maxWidth = preferredSizeWidth_;
  }
  else if (needsMultitplePasses_ && secondPass_) {
    maxWidth = [self getWidth];
  }
  if (maxWidth > 0) {
    [self getSizeForWidthWithRAREUIDimension:size withFloat:maxWidth];
  }
  else {
    if (cacheInvalidated_) {
      [self populateSizeCache];
    }
    int height = 0;
    int width = 0;
    id<RAREiPlatformComponent> c;
    int len = [self getComponentCount];
    for (int i = 0; i < len; i++) {
      c = [self getComponentAtWithInt:i];
      if (![((id<RAREiPlatformComponent>) nil_chk(c)) isVisible]) {
        continue;
      }
      RAREJGCellConstraints *cc = [RAREaComponent getMeasuredCellConstraintsWithRAREiPlatformComponent:c withBoolean:YES];
      width += ((RAREJGCellConstraints *) nil_chk(cc))->gridWidth_ + columnSpacing_;
      height = [JavaLangMath maxWithInt:height withInt:cc->gridHeight_];
    }
    if (len > 0) {
      width -= columnSpacing_;
    }
    ((RAREUIDimension *) nil_chk(size))->width_ = width;
    size->height_ = height;
  }
}

- (float)getPreferredHeightWithInt:(int)width {
  RAREUIDimension *size = [[RAREUIDimension alloc] init];
  [self getSizeForWidthWithRAREUIDimension:size withFloat:width];
  return size->height_;
}

- (void)getSizeForWidthWithRAREUIDimension:(RAREUIDimension *)size
                                 withFloat:(float)maxWidth {
  if (cacheInvalidated_) {
    [self populateSizeCache];
  }
  RAREUIInsets *in = [self getInsetsWithRAREUIInsets:computeInsets_];
  int height = 0;
  int width = 0;
  float totalHeight = ((RAREUIInsets *) nil_chk(in))->top_ + in->bottom_;
  int totalWidth = 0;
  maxWidth -= in->left_ + in->right_;
  id<RAREiPlatformComponent> c;
  int len = [self getComponentCount];
  for (int i = 0; i < len; i++) {
    c = [self getComponentAtWithInt:i];
    if (![((id<RAREiPlatformComponent>) nil_chk(c)) isVisible]) {
      continue;
    }
    RAREJGCellConstraints *cc = [RAREaComponent getMeasuredCellConstraintsWithRAREiPlatformComponent:c withBoolean:YES];
    width += ((RAREJGCellConstraints *) nil_chk(cc))->gridWidth_;
    if (width > maxWidth) {
      totalHeight += height + rowSpacing_;
      totalWidth = [JavaLangMath maxWithInt:width - cc->gridWidth_ withInt:totalWidth];
      width = cc->gridWidth_;
      height = 0;
    }
    width += columnSpacing_;
    height = [JavaLangMath maxWithInt:height withInt:cc->gridHeight_];
  }
  if (height > 0) {
    totalHeight += height;
  }
  else {
    totalHeight = [JavaLangMath maxWithFloat:totalHeight - rowSpacing_ withFloat:in->top_ + in->bottom_];
  }
  ((RAREUIDimension *) nil_chk(size))->width_ = totalWidth;
  size->height_ = totalHeight;
}

- (BOOL)isNeedsMultitplePasses {
  return needsMultitplePasses_;
}

- (void)setNeedsMultitplePassesWithBoolean:(BOOL)needsMultitplePasses {
  self->needsMultitplePasses_ = needsMultitplePasses;
}

- (int)getPreferredSizeWidth {
  return preferredSizeWidth_;
}

- (void)setPreferredSizeWidthWithInt:(int)preferredSizeWidth {
  self->preferredSizeWidth_ = preferredSizeWidth;
}

- (void)copyAllFieldsTo:(RAREaFlowPanel *)other {
  [super copyAllFieldsTo:other];
  other->columnSpacing_ = columnSpacing_;
  other->layoutlist_ = layoutlist_;
  other->needsMultitplePasses_ = needsMultitplePasses_;
  other->preferredSizeWidth_ = preferredSizeWidth_;
  other->rowSpacing_ = rowSpacing_;
  other->secondPass_ = secondPass_;
  other->useContainerWidth_ = useContainerWidth_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "heightChangesBasedOnWidth", NULL, "Z", 0x1, NULL },
    { "layoutWithFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "layoutWithJavaUtilArrayList:withFloat:withFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "populateSizeCache", NULL, "V", 0x4, NULL },
    { "requestRevalidationForBoundsChange", NULL, "V", 0x4, NULL },
    { "getMinimumSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
    { "getPreferredSizeExWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
    { "getSizeForWidthWithRAREUIDimension:withFloat:", NULL, "V", 0x4, NULL },
    { "isNeedsMultitplePasses", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "needsMultitplePasses_", NULL, 0x4, "Z" },
    { "secondPass_", NULL, 0x4, "Z" },
    { "useContainerWidth_", NULL, 0x4, "Z" },
    { "layoutlist_", NULL, 0x4, "LJavaUtilArrayList" },
  };
  static J2ObjcClassInfo _RAREaFlowPanel = { "aFlowPanel", "com.appnativa.rare.ui", NULL, 0x1, 9, methods, 4, fields, 0, NULL};
  return &_RAREaFlowPanel;
}

@end
@implementation RAREaFlowPanel_$1

- (void)run {
  [this$0_ revalidate];
}

- (id)initWithRAREaFlowPanel:(RAREaFlowPanel *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREaFlowPanel" },
  };
  static J2ObjcClassInfo _RAREaFlowPanel_$1 = { "$1", "com.appnativa.rare.ui", "aFlowPanel", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREaFlowPanel_$1;
}

@end
