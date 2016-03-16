//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/ListSynchronizer.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/platform/apple/ui/ListSynchronizer.h"
#include "com/appnativa/rare/platform/apple/ui/view/aPlatformTableBasedView.h"
#include "com/appnativa/rare/ui/UIPoint.h"
#include "com/appnativa/util/IdentityArrayList.h"

@implementation RAREListSynchronizer

- (id)initWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *)main
                              withBoolean:(BOOL)syncScroll {
  if (self = [super init]) {
    lists_ = [[RAREUTIdentityArrayList alloc] initWithInt:3];
    self->syncScroll_ = syncScroll;
    mainList_ = main;
    [self addListViewWithRAREaPlatformTableBasedView:main];
  }
  return self;
}

- (void)dispose {
  [((RAREUTIdentityArrayList *) nil_chk(lists_)) clear];
}

- (void)sychronizePositionWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *)caller {
  if (syncScroll_ && !scrolling_) {
    scrolling_ = YES;
    @try {
      RAREUIPoint *p = [((RAREaPlatformTableBasedView *) nil_chk(caller)) getContentOffset];
      for (RAREaPlatformTableBasedView * __strong lv in nil_chk(lists_)) {
        if (lv != caller) {
          [((RAREaPlatformTableBasedView *) nil_chk(lv)) setContentOffsetWithFloat:0 withFloat:((RAREUIPoint *) nil_chk(p))->y_];
        }
      }
    }
    @finally {
      scrolling_ = NO;
    }
  }
}

- (void)setSelectedIndexWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *)caller
                                                withInt:(int)index
                                            withBoolean:(BOOL)selected
                                            withBoolean:(BOOL)clicked {
  if (!selecting_) {
    if ((caller != mainList_) && clicked) {
      [((RAREaPlatformTableBasedView *) nil_chk(mainList_)) clickRowWithInt:index];
      return;
    }
    selecting_ = YES;
    @try {
      for (RAREaPlatformTableBasedView * __strong lv in nil_chk(lists_)) {
        if (lv != caller) {
          if (selected) {
            [((RAREaPlatformTableBasedView *) nil_chk(lv)) addSelectionIndexWithInt:index];
          }
          else {
            [((RAREaPlatformTableBasedView *) nil_chk(lv)) removeSelectionWithInt:index];
          }
        }
      }
    }
    @finally {
      selecting_ = NO;
    }
  }
}

- (BOOL)isMainListWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *)list {
  return list == mainList_;
}

- (void)addListViewWithRAREaPlatformTableBasedView:(RAREaPlatformTableBasedView *)list {
  [((RAREUTIdentityArrayList *) nil_chk(lists_)) addWithId:list];
  [((RAREaPlatformTableBasedView *) nil_chk(list)) setListSynchronizerWithRAREListSynchronizer:self];
}

- (BOOL)isSyncScrolling {
  return syncScroll_;
}

- (void)copyAllFieldsTo:(RAREListSynchronizer *)other {
  [super copyAllFieldsTo:other];
  other->lists_ = lists_;
  other->mainList_ = mainList_;
  other->scrolling_ = scrolling_;
  other->scrollingList_ = scrollingList_;
  other->selecting_ = selecting_;
  other->syncScroll_ = syncScroll_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "isMainListWithRAREaPlatformTableBasedView:", NULL, "Z", 0x1, NULL },
    { "isSyncScrolling", NULL, "Z", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "lists_", NULL, 0x0, "LRAREUTIdentityArrayList" },
    { "syncScroll_", NULL, 0x0, "Z" },
    { "selecting_", NULL, 0x40, "Z" },
    { "scrolling_", NULL, 0x40, "Z" },
    { "scrollingList_", NULL, 0x0, "LRAREaPlatformTableBasedView" },
    { "mainList_", NULL, 0x0, "LRAREaPlatformTableBasedView" },
  };
  static J2ObjcClassInfo _RAREListSynchronizer = { "ListSynchronizer", "com.appnativa.rare.platform.apple.ui", NULL, 0x1, 2, methods, 6, fields, 0, NULL};
  return &_RAREListSynchronizer;
}

@end
