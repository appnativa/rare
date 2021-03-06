//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple-table_and_tree/com/appnativa/rare/ui/renderer/TreeItemRenderer.java
//
//  Created by decoteaud on 3/11/16.
//

#include "com/appnativa/rare/platform/PlatformHelper.h"
#include "com/appnativa/rare/ui/Column.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/iPlatformRenderingComponent.h"
#include "com/appnativa/rare/ui/renderer/TreeItemRenderer.h"
#include "com/appnativa/rare/viewer/TreeViewer.h"
#include "java/lang/CharSequence.h"

@implementation RARETreeItemRenderer

- (id)initWithRARETreeViewer:(RARETreeViewer *)treeViewer {
  if (self = [super init]) {
    self->treeViewer_ = treeViewer;
  }
  return self;
}

- (void)setIconAndAlignmentWithRAREiPlatformRenderingComponent:(id<RAREiPlatformRenderingComponent>)rc
                                      withJavaLangCharSequence:(id<JavaLangCharSequence>)text
                                    withRARERenderableDataItem:(RARERenderableDataItem *)item
                                    withRARERenderableDataItem:(RARERenderableDataItem *)row
                                                withRAREColumn:(RAREColumn *)col
                                                   withBoolean:(BOOL)enabled
                                                   withBoolean:(BOOL)center
                                                   withBoolean:(BOOL)top
                                                   withBoolean:(BOOL)seticon
                                                   withBoolean:(BOOL)oexpanded {
  id<RAREiPlatformIcon> icon = nil;
  id<RAREiPlatformIcon> dicon = nil;
  if (!empty_) {
    if (expanded_) {
      icon = [((RARERenderableDataItem *) nil_chk(item)) getAlternateIcon];
    }
    if (icon == nil) {
      icon = [((RARERenderableDataItem *) nil_chk(item)) getIcon];
    }
    if (!enabled) {
      dicon = [((RARERenderableDataItem *) nil_chk(item)) getDisabledIcon];
      if ((dicon == nil) && (icon != nil)) {
        dicon = [RAREaPlatformHelper createDisabledIconIfNeededWithRAREiPlatformIcon:icon];
        [item setDisabledIconWithRAREiPlatformIcon:dicon];
      }
      else if (dicon == nil) {
        if (!leaf_) {
          dicon = expanded_ ? [((RARETreeViewer *) nil_chk(treeViewer_)) getDisabledFolderOpenIcon] : [((RARETreeViewer *) nil_chk(treeViewer_)) getDisabledFolderClosedIcon];
        }
        else {
          dicon = [((RARETreeViewer *) nil_chk(treeViewer_)) getDisabledLeafIcon];
        }
      }
    }
    else if (icon == nil) {
      if (!leaf_) {
        icon = expanded_ ? [((RARETreeViewer *) nil_chk(treeViewer_)) getFolderOpenIcon] : [((RARETreeViewer *) nil_chk(treeViewer_)) getFolderClosedIcon];
      }
      else {
        icon = [((RARETreeViewer *) nil_chk(treeViewer_)) getLeafIcon];
      }
    }
  }
  [super setIconAndAlignmentWithRAREiPlatformRenderingComponent:rc withJavaLangCharSequence:text withRARERenderableDataItem:item withRARERenderableDataItem:row withRAREColumn:col withBoolean:enabled withBoolean:center withBoolean:top withBoolean:NO withBoolean:expanded_];
  if (!empty_) {
    [((id<RAREiPlatformRenderingComponent>) nil_chk(rc)) setIconWithRAREiPlatformIcon:enabled ? icon : dicon];
  }
}

- (void)copyAllFieldsTo:(RARETreeItemRenderer *)other {
  [super copyAllFieldsTo:other];
  other->treeViewer_ = treeViewer_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "treeViewer_", NULL, 0x4, "LRARETreeViewer" },
  };
  static J2ObjcClassInfo _RARETreeItemRenderer = { "TreeItemRenderer", "com.appnativa.rare.ui.renderer", NULL, 0x1, 0, NULL, 1, fields, 0, NULL};
  return &_RARETreeItemRenderer;
}

@end
