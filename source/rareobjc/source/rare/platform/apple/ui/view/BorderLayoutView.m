//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/BorderLayoutView.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/platform/apple/ui/view/BorderLayoutView.h"
#include "com/appnativa/rare/platform/apple/ui/view/FormsView.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/Location.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/iParentComponent.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/layout/BorderLayout.h"
#include "com/jgoodies/forms/layout/CellConstraints.h"

@implementation RAREBorderLayoutView

static IOSObjectArray * RAREBorderLayoutView_specs_;
static RAREJGCellConstraints * RAREBorderLayoutView_tbTopCell_;
static RAREJGCellConstraints * RAREBorderLayoutView_tbRightCell_;
static RAREJGCellConstraints * RAREBorderLayoutView_tbLeftCell_;
static RAREJGCellConstraints * RAREBorderLayoutView_tbBottomCell_;
static RAREJGCellConstraints * RAREBorderLayoutView_lrTopCell_;
static RAREJGCellConstraints * RAREBorderLayoutView_lrRightCell_;
static RAREJGCellConstraints * RAREBorderLayoutView_lrLeftCell_;
static RAREJGCellConstraints * RAREBorderLayoutView_lrBottomCell_;
static RAREJGCellConstraints * RAREBorderLayoutView_centerCell_;

+ (IOSObjectArray *)specs {
  return RAREBorderLayoutView_specs_;
}

+ (void)setSpecs:(IOSObjectArray *)specs {
  RAREBorderLayoutView_specs_ = specs;
}

+ (RAREJGCellConstraints *)tbTopCell {
  return RAREBorderLayoutView_tbTopCell_;
}

+ (void)setTbTopCell:(RAREJGCellConstraints *)tbTopCell {
  RAREBorderLayoutView_tbTopCell_ = tbTopCell;
}

+ (RAREJGCellConstraints *)tbRightCell {
  return RAREBorderLayoutView_tbRightCell_;
}

+ (void)setTbRightCell:(RAREJGCellConstraints *)tbRightCell {
  RAREBorderLayoutView_tbRightCell_ = tbRightCell;
}

+ (RAREJGCellConstraints *)tbLeftCell {
  return RAREBorderLayoutView_tbLeftCell_;
}

+ (void)setTbLeftCell:(RAREJGCellConstraints *)tbLeftCell {
  RAREBorderLayoutView_tbLeftCell_ = tbLeftCell;
}

+ (RAREJGCellConstraints *)tbBottomCell {
  return RAREBorderLayoutView_tbBottomCell_;
}

+ (void)setTbBottomCell:(RAREJGCellConstraints *)tbBottomCell {
  RAREBorderLayoutView_tbBottomCell_ = tbBottomCell;
}

+ (RAREJGCellConstraints *)lrTopCell {
  return RAREBorderLayoutView_lrTopCell_;
}

+ (void)setLrTopCell:(RAREJGCellConstraints *)lrTopCell {
  RAREBorderLayoutView_lrTopCell_ = lrTopCell;
}

+ (RAREJGCellConstraints *)lrRightCell {
  return RAREBorderLayoutView_lrRightCell_;
}

+ (void)setLrRightCell:(RAREJGCellConstraints *)lrRightCell {
  RAREBorderLayoutView_lrRightCell_ = lrRightCell;
}

+ (RAREJGCellConstraints *)lrLeftCell {
  return RAREBorderLayoutView_lrLeftCell_;
}

+ (void)setLrLeftCell:(RAREJGCellConstraints *)lrLeftCell {
  RAREBorderLayoutView_lrLeftCell_ = lrLeftCell;
}

+ (RAREJGCellConstraints *)lrBottomCell {
  return RAREBorderLayoutView_lrBottomCell_;
}

+ (void)setLrBottomCell:(RAREJGCellConstraints *)lrBottomCell {
  RAREBorderLayoutView_lrBottomCell_ = lrBottomCell;
}

+ (RAREJGCellConstraints *)centerCell {
  return RAREBorderLayoutView_centerCell_;
}

+ (void)setCenterCell:(RAREJGCellConstraints *)centerCell {
  RAREBorderLayoutView_centerCell_ = centerCell;
}

- (id)init {
  if (self = [super initWithRAREJGFormLayout:[[RARELOBorderLayout alloc] initWithNSString:IOSObjectArray_Get(nil_chk(RAREBorderLayoutView_specs_), 0) withNSString:IOSObjectArray_Get(RAREBorderLayoutView_specs_, 1)]]) {
    topBottomPriority_ = YES;
  }
  return self;
}

- (id)initWithId:(id)proxy {
  if (self = [super initWithId:proxy withRAREJGFormLayout:[[RARELOBorderLayout alloc] initWithNSString:IOSObjectArray_Get(nil_chk(RAREBorderLayoutView_specs_), 0) withNSString:IOSObjectArray_Get(RAREBorderLayoutView_specs_, 1)]]) {
    topBottomPriority_ = YES;
  }
  return self;
}

- (id)initWithNSString:(NSString *)encodedColumnSpecs
          withNSString:(NSString *)encodedRowSpecs {
  if (self = [super initWithRAREJGFormLayout:[[RARELOBorderLayout alloc] initWithNSString:encodedColumnSpecs withNSString:encodedColumnSpecs]]) {
    topBottomPriority_ = YES;
  }
  return self;
}

- (id)initWithId:(id)proxy
    withNSString:(NSString *)encodedColumnSpecs
    withNSString:(NSString *)encodedRowSpecs {
  if (self = [super initWithId:proxy withRAREJGFormLayout:[[RARELOBorderLayout alloc] initWithNSString:encodedColumnSpecs withNSString:encodedColumnSpecs]]) {
    topBottomPriority_ = YES;
  }
  return self;
}

- (void)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                 withRARELocationEnum:(RARELocationEnum *)constraints {
  RAREJGCellConstraints *cc;
  switch ([constraints ordinal]) {
    case RARELocation_TOP:
    cc = topBottomPriority_ ? [[RAREJGCellConstraints alloc] initWithInt:1 withInt:1 withInt:3 withInt:1] : [[RAREJGCellConstraints alloc] initWithInt:2 withInt:1 withInt:1 withInt:1];
    break;
    case RARELocation_BOTTOM:
    cc = topBottomPriority_ ? [[RAREJGCellConstraints alloc] initWithInt:1 withInt:3 withInt:3 withInt:1] : [[RAREJGCellConstraints alloc] initWithInt:2 withInt:3 withInt:1 withInt:1];
    break;
    case RARELocation_LEFT:
    cc = topBottomPriority_ ? [[RAREJGCellConstraints alloc] initWithInt:1 withInt:2 withInt:1 withInt:1] : [[RAREJGCellConstraints alloc] initWithInt:1 withInt:1 withInt:1 withInt:3];
    break;
    case RARELocation_RIGHT:
    cc = topBottomPriority_ ? [[RAREJGCellConstraints alloc] initWithInt:3 withInt:2 withInt:1 withInt:1] : [[RAREJGCellConstraints alloc] initWithInt:3 withInt:1 withInt:1 withInt:3];
    break;
    default:
    cc = [[RAREJGCellConstraints alloc] initWithInt:2 withInt:2 withInt:1 withInt:1];
    if (padding_ != nil) {
      cc->insets_ = padding_;
    }
  }
  ((RAREJGCellConstraints *) nil_chk(cc))->hAlign_ = [RAREJGCellConstraints FILL];
  cc->vAlign_ = [RAREJGCellConstraints FILL];
  RAREJGCellConstraints *occ = (RAREJGCellConstraints *) check_class_cast([self getConstraintsWithRAREiPlatformComponent:c], [RAREJGCellConstraints class]);
  id<RAREiParentComponent> pc = (id<RAREiParentComponent>) check_protocol_cast([RAREComponent fromViewWithRAREView:self], @protocol(RAREiParentComponent));
  if ((occ != nil) && (occ->gridX_ == cc->gridX_) && (occ->gridY_ == cc->gridY_) && (([((id<RAREiPlatformComponent>) nil_chk(c)) getParent] != nil) && ([c getParent] == pc))) {
    return;
  }
  id<RAREiPlatformComponent> oldc = [self getComponentAtWithInt:cc->gridX_ withInt:cc->gridY_];
  if (oldc != nil) {
    occ = nil;
  }
  if ((oldc != nil) && (oldc != c)) {
    [((id<RAREiParentComponent>) nil_chk(pc)) removeWithRAREiPlatformComponent:oldc];
  }
  if (oldc != c) {
    [super addWithRAREiPlatformComponent:c withId:cc withInt:-1];
  }
}

- (void)addWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                               withId:(id)constraints
                              withInt:(int)position {
  if ([constraints isKindOfClass:[RARELocationEnum class]]) {
    [self addWithRAREiPlatformComponent:c withRARELocationEnum:(RARELocationEnum *) constraints];
  }
  else if (constraints == nil) {
    [self addWithRAREiPlatformComponent:c withRARELocationEnum:[RARELocationEnum CENTER]];
  }
  else {
    [super addWithRAREiPlatformComponent:c withId:constraints withInt:position];
  }
}

- (void)removeAtLocationWithRARELocationEnum:(RARELocationEnum *)loc {
  id<RAREiPlatformComponent> c = [self getComponentAtWithRARELocationEnum:loc];
  if (c != nil) {
    [self removeWithRAREiPlatformComponent:c];
  }
}

- (void)setBottomViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  if (c == nil) {
    [self removeAtLocationWithRARELocationEnum:[RARELocationEnum BOTTOM]];
  }
  else {
    [self addWithRAREiPlatformComponent:c withRARELocationEnum:[RARELocationEnum BOTTOM]];
  }
}

- (void)setCenterViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  if (c == nil) {
    [self removeAtLocationWithRARELocationEnum:[RARELocationEnum CENTER]];
  }
  else {
    [self addWithRAREiPlatformComponent:c withRARELocationEnum:[RARELocationEnum CENTER]];
  }
}

- (void)setHorizontalWithBoolean:(BOOL)horizontal {
}

- (void)setLeftViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  if (c == nil) {
    [self removeAtLocationWithRARELocationEnum:[RARELocationEnum LEFT]];
  }
  else {
    [self addWithRAREiPlatformComponent:c withRARELocationEnum:[RARELocationEnum LEFT]];
  }
}

- (void)setPaddingWithRAREUIInsets:(RAREUIInsets *)inArg {
  if (inArg == nil) {
    padding_ = nil;
  }
  else {
    if (padding_ == nil) {
      padding_ = [[RAREUIInsets alloc] init];
    }
    (void) [((RAREUIInsets *) nil_chk(padding_)) setWithRAREUIInsets:inArg];
  }
}

- (void)adjustSizeForPaddingWithRAREUIDimension:(RAREUIDimension *)size {
  if (padding_ != nil) {
    ((RAREUIDimension *) nil_chk(size))->width_ += padding_->left_ + padding_->right_;
    size->height_ += padding_->top_ + padding_->bottom_;
  }
}

- (void)setRightViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  if (c == nil) {
    [self removeAtLocationWithRARELocationEnum:[RARELocationEnum RIGHT]];
  }
  else {
    [self addWithRAREiPlatformComponent:c withRARELocationEnum:[RARELocationEnum RIGHT]];
  }
}

- (void)setTopBottomPriorityWithBoolean:(BOOL)topBottomPriority {
}

- (void)setTopViewWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c {
  if (c == nil) {
    [self removeAtLocationWithRARELocationEnum:[RARELocationEnum TOP]];
  }
  else {
    [self addWithRAREiPlatformComponent:c withRARELocationEnum:[RARELocationEnum TOP]];
  }
}

- (id<RAREiPlatformComponent>)getBottomView {
  return [self getComponentAtWithRARELocationEnum:[RARELocationEnum BOTTOM]];
}

- (id<RAREiPlatformComponent>)getCenterView {
  return [self getComponentAtWithRARELocationEnum:[RARELocationEnum CENTER]];
}

- (id<RAREiPlatformComponent>)getComponentAtWithRARELocationEnum:(RARELocationEnum *)location {
  RAREJGCellConstraints *cc = [self getConstraintsWithRARELocationEnum:location];
  if (cc == nil) {
    return nil;
  }
  return [self getComponentAtWithInt:((RAREJGCellConstraints *) nil_chk(cc))->gridX_ withInt:cc->gridY_];
}

- (RAREJGCellConstraints *)getConstraintsWithRARELocationEnum:(RARELocationEnum *)location {
  RAREJGCellConstraints *cc;
  switch ([location ordinal]) {
    case RARELocation_TOP:
    cc = topBottomPriority_ ? RAREBorderLayoutView_tbTopCell_ : RAREBorderLayoutView_lrTopCell_;
    break;
    case RARELocation_BOTTOM:
    cc = topBottomPriority_ ? RAREBorderLayoutView_tbBottomCell_ : RAREBorderLayoutView_lrBottomCell_;
    break;
    case RARELocation_LEFT:
    cc = topBottomPriority_ ? RAREBorderLayoutView_tbLeftCell_ : RAREBorderLayoutView_lrLeftCell_;
    break;
    case RARELocation_RIGHT:
    cc = topBottomPriority_ ? RAREBorderLayoutView_tbRightCell_ : RAREBorderLayoutView_lrRightCell_;
    break;
    default:
    cc = RAREBorderLayoutView_centerCell_;
  }
  return cc;
}

- (id<RAREiPlatformComponent>)getLeftView {
  return [self getComponentAtWithRARELocationEnum:[RARELocationEnum LEFT]];
}

- (id<RAREiPlatformComponent>)getRightView {
  return [self getComponentAtWithRARELocationEnum:[RARELocationEnum RIGHT]];
}

- (id<RAREiPlatformComponent>)getTopView {
  return [self getComponentAtWithRARELocationEnum:[RARELocationEnum TOP]];
}

+ (void)initialize {
  if (self == [RAREBorderLayoutView class]) {
    RAREBorderLayoutView_specs_ = [IOSObjectArray arrayWithObjects:(id[]){ @"f:d,f:d:g,f:d", @"f:d,f:d:g,f:d" } count:2 type:[IOSClass classWithClass:[NSString class]]];
    RAREBorderLayoutView_tbTopCell_ = [[RAREJGCellConstraints alloc] initWithInt:1 withInt:1 withInt:3 withInt:1];
    RAREBorderLayoutView_tbRightCell_ = [[RAREJGCellConstraints alloc] initWithInt:3 withInt:2 withInt:1 withInt:1];
    RAREBorderLayoutView_tbLeftCell_ = [[RAREJGCellConstraints alloc] initWithInt:1 withInt:2 withInt:1 withInt:1];
    RAREBorderLayoutView_tbBottomCell_ = [[RAREJGCellConstraints alloc] initWithInt:1 withInt:3 withInt:3 withInt:1];
    RAREBorderLayoutView_lrTopCell_ = [[RAREJGCellConstraints alloc] initWithInt:2 withInt:1 withInt:1 withInt:1];
    RAREBorderLayoutView_lrRightCell_ = [[RAREJGCellConstraints alloc] initWithInt:3 withInt:1 withInt:1 withInt:3];
    RAREBorderLayoutView_lrLeftCell_ = [[RAREJGCellConstraints alloc] initWithInt:1 withInt:1 withInt:1 withInt:3];
    RAREBorderLayoutView_lrBottomCell_ = [[RAREJGCellConstraints alloc] initWithInt:2 withInt:3 withInt:1 withInt:1];
    RAREBorderLayoutView_centerCell_ = [[RAREJGCellConstraints alloc] initWithInt:2 withInt:2 withInt:1 withInt:1];
  }
}

- (void)copyAllFieldsTo:(RAREBorderLayoutView *)other {
  [super copyAllFieldsTo:other];
  other->padding_ = padding_;
  other->topBottomPriority_ = topBottomPriority_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "getBottomView", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getCenterView", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getComponentAtWithRARELocationEnum:", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getConstraintsWithRARELocationEnum:", NULL, "LRAREJGCellConstraints", 0x1, NULL },
    { "getLeftView", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getRightView", NULL, "LRAREiPlatformComponent", 0x1, NULL },
    { "getTopView", NULL, "LRAREiPlatformComponent", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "specs_", NULL, 0xc, "LIOSObjectArray" },
    { "tbTopCell_", NULL, 0x8, "LRAREJGCellConstraints" },
    { "tbRightCell_", NULL, 0x8, "LRAREJGCellConstraints" },
    { "tbLeftCell_", NULL, 0x8, "LRAREJGCellConstraints" },
    { "tbBottomCell_", NULL, 0x8, "LRAREJGCellConstraints" },
    { "lrTopCell_", NULL, 0x8, "LRAREJGCellConstraints" },
    { "lrRightCell_", NULL, 0x8, "LRAREJGCellConstraints" },
    { "lrLeftCell_", NULL, 0x8, "LRAREJGCellConstraints" },
    { "lrBottomCell_", NULL, 0x8, "LRAREJGCellConstraints" },
    { "centerCell_", NULL, 0x8, "LRAREJGCellConstraints" },
    { "topBottomPriority_", NULL, 0x0, "Z" },
    { "padding_", NULL, 0x0, "LRAREUIInsets" },
  };
  static J2ObjcClassInfo _RAREBorderLayoutView = { "BorderLayoutView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 7, methods, 12, fields, 0, NULL};
  return &_RAREBorderLayoutView;
}

@end