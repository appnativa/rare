//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/apple/com/appnativa/rare/platform/apple/ui/view/ListView.java
//
//  Created by decoteaud on 7/29/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/Platform.h"
#include "com/appnativa/rare/iFunctionCallback.h"
#include "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#include "com/appnativa/rare/platform/apple/ui/view/ListView.h"
#include "com/appnativa/rare/platform/apple/ui/view/SectionIndex.h"
#include "com/appnativa/rare/platform/apple/ui/view/View.h"
#include "com/appnativa/rare/platform/apple/ui/view/aPlatformTableBasedView.h"
#include "com/appnativa/rare/platform/apple/ui/view/aTableBasedView.h"
#include "com/appnativa/rare/ui/ActionComponent.h"
#include "com/appnativa/rare/ui/CheckListManager.h"
#include "com/appnativa/rare/ui/Component.h"
#include "com/appnativa/rare/ui/PainterUtils.h"
#include "com/appnativa/rare/ui/RenderableDataItem.h"
#include "com/appnativa/rare/ui/UIAction.h"
#include "com/appnativa/rare/ui/UIColor.h"
#include "com/appnativa/rare/ui/UIDimension.h"
#include "com/appnativa/rare/ui/UIFont.h"
#include "com/appnativa/rare/ui/UIInsets.h"
#include "com/appnativa/rare/ui/UIRectangle.h"
#include "com/appnativa/rare/ui/event/ActionEvent.h"
#include "com/appnativa/rare/ui/event/MouseEvent.h"
#include "com/appnativa/rare/ui/iGestureListener.h"
#include "com/appnativa/rare/ui/iListHandler.h"
#include "com/appnativa/rare/ui/iPlatformBorder.h"
#include "com/appnativa/rare/ui/iPlatformComponent.h"
#include "com/appnativa/rare/ui/iPlatformIcon.h"
#include "com/appnativa/rare/ui/iPlatformListDataModel.h"
#include "com/appnativa/rare/ui/iPlatformRenderingComponent.h"
#include "com/appnativa/rare/ui/iToolBar.h"
#include "com/appnativa/rare/ui/painter/PainterHolder.h"
#include "com/appnativa/rare/ui/renderer/ListItemRenderer.h"
#include "com/appnativa/rare/ui/renderer/UIComponentRenderer.h"
#include "com/appnativa/rare/ui/table/TableHelper.h"
#include "com/appnativa/rare/ui/tree/iTreeItem.h"
#include "com/appnativa/rare/viewer/aListViewer.h"
#include "com/appnativa/rare/widget/iWidget.h"
#include "com/appnativa/util/CharacterIndex.h"
#include "com/appnativa/util/IntList.h"
#include "com/appnativa/util/ObjectHolder.h"
#include "java/lang/CharSequence.h"
#include "java/lang/Character.h"
#include "java/lang/Integer.h"
#include "java/lang/Math.h"
#include "java/util/Arrays.h"
#include "java/util/Iterator.h"
#include "java/util/List.h"
#include "java/util/Map.h"
#include "java/util/Set.h"
#import "RAREAPListView.h"
 #import "APView+Component.h"
 #import "com/appnativa/rare/ui/UIInsets.h"

@implementation RAREListView

- (id)init {
  if (self = [super initWithId:[RAREListView createProxyWithBoolean:NO]]) {
    rinsets_ = [[RAREUIInsets alloc] init];
    checkboxLeftXSlop_ = [RAREaTableBasedView INDICATOR_SLOP];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)grouped {
  if (self = [super initWithId:[RAREListView createProxyWithBoolean:grouped]]) {
    rinsets_ = [[RAREUIInsets alloc] init];
    checkboxLeftXSlop_ = [RAREaTableBasedView INDICATOR_SLOP];
  }
  return self;
}

- (id)initWithId:(id)proxy {
  if (self = [super initWithId:proxy]) {
    rinsets_ = [[RAREUIInsets alloc] init];
    checkboxLeftXSlop_ = [RAREaTableBasedView INDICATOR_SLOP];
  }
  return self;
}

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e
                                   withInt:(int)index {
  [super actionPerformedWithRAREActionEvent:e];
}

- (void)addSelectionIndexWithInt:(int)index {
  [super addSelectionIndexWithInt:index];
  if (linkedSelection_ && (checkListManager_ != nil)) {
    if (![checkListManager_ isRowCheckedWithInt:index]) {
      [self toggleCheckedStateWithInt:index];
    }
  }
}

- (void)borderChangedWithRAREiPlatformBorder:(id<RAREiPlatformBorder>)newBorder {
  [super borderChangedWithRAREiPlatformBorder:newBorder];
  if (![self isTable] && ![self isTree]) {
    borderInsets_ = (newBorder == nil) ? nil : [newBorder getBorderInsetsWithRAREUIInsets:borderInsets_];
    if (borderInsets_ != nil) {
      (void) [borderInsets_ addInsetsWithRAREUIInsets:rinsets_];
    }
  }
}

- (void)clearCheckedItems {
  if (linkedSelection_) {
    [self clearSelections];
  }
  else if (checkListManager_ != nil) {
    [checkListManager_ clear];
  }
}

- (void)clearSelections {
  [super clearSelections];
  if (linkedSelection_ && (checkListManager_ != nil)) {
    [checkListManager_ clear];
  }
}

- (void)editModeChangeAllMarksWithBoolean:(BOOL)mark {
  [((id<RAREiPlatformListDataModel>) nil_chk(listModel_)) editModeChangeAllMarksWithBoolean:mark];
  [self repaintVisibleRows];
  [self updateActions];
}

- (void)hideRowEditingComponentWithBoolean:(BOOL)animate {
  int row = editingRow_;
  editingRow_ = -1;
  if (row > -1) {
    RAREaTableBasedView_RowView *v = [self getViewForRowWithInt:row];
    if (v != nil) {
      [v hideRowEditingComponentWithBoolean:animate];
    }
  }
}

- (void)hideRowEditingComponentEx {
  int row = editingRow_;
  editingRow_ = -1;
  if (row > -1) {
    RAREaTableBasedView_RowView *v = (row < [self getRowCount]) ? [self getViewForRowWithInt:row] : nil;
    if (v != nil) {
      [v hideRowEditingComponentWithBoolean:NO];
    }
  }
}

- (void)paintRowWithRAREaTableBasedView_RowView:(RAREaTableBasedView_RowView *)view
                          withRAREAppleGraphics:(RAREAppleGraphics *)g
                     withRARERenderableDataItem:(RARERenderableDataItem *)item
                            withRAREUIRectangle:(RAREUIRectangle *)rect
                              withRAREiTreeItem:(id<RAREiTreeItem>)ti {
  if (checkListManager_ != nil) {
    ((RAREaTableBasedView_RowView *) nil_chk(view))->checkboxIcon_ = [checkListManager_ getRowIconWithInt:view->row_ withRARERenderableDataItem:item];
  }
  [((RAREaTableBasedView_RowView *) nil_chk(view)) paintWithRAREAppleGraphics:g withRAREUIRectangle:rect];
}

- (void)renderItemWithInt:(int)row
withRARERenderableDataItem:(RARERenderableDataItem *)item
withRAREaTableBasedView_RowView:(RAREaTableBasedView_RowView *)view
              withBoolean:(BOOL)isSelected
              withBoolean:(BOOL)isPressed
        withRAREiTreeItem:(id<RAREiTreeItem>)ti {
  if (item == nil) {
    item = [RAREaPlatformTableBasedView NULL_ITEM];
  }
  ((RAREaTableBasedView_RowView *) nil_chk(view))->row_ = row;
  if ((renderingCallback_ != nil) && [renderingCallback_ renderItemWithInt:row withRARERenderableDataItem:item withRAREaTableBasedView_RowView:view withBoolean:isSelected withBoolean:isPressed withRAREiTreeItem:ti]) {
    return;
  }
  RAREaPlatformTableBasedView_RowViewEx *rv = (RAREaPlatformTableBasedView_RowViewEx *) check_class_cast(view, [RAREaPlatformTableBasedView_RowViewEx class]);
  if ([((RARERenderableDataItem *) nil_chk(item)) hasCustomProperties]) {
    [rv setCustomPropertiesWithRARERenderableDataItem:item];
  }
  id<RAREiPlatformComponent> c = [item getRenderingComponent];
  if (c != nil) {
    [rv setRenderingViewWithRAREView:[c getView]];
  }
  else {
    id<RAREiPlatformRenderingComponent> rc = ((RAREaPlatformTableBasedView_RowViewEx *) check_class_cast(view, [RAREaPlatformTableBasedView_RowViewEx class]))->renderingComponent_;
    if (rc == nil) {
      rc = (id<RAREiPlatformRenderingComponent>) check_protocol_cast([view getComponent], @protocol(RAREiPlatformRenderingComponent));
    }
    id<JavaLangCharSequence> text = [((RAREListItemRenderer *) nil_chk(itemRenderer_)) configureRenderingComponentWithRAREiPlatformComponent:component_ withRAREiPlatformRenderingComponent:rc withRARERenderableDataItem:item withInt:row withBoolean:isSelected withBoolean:isPressed withRAREColumn:nil withRARERenderableDataItem:nil];
    (void) [((id<RAREiPlatformRenderingComponent>) nil_chk(rc)) getComponentWithJavaLangCharSequence:text withRARERenderableDataItem:item];
  }
  RAREUIInsets *in = borderInsets_;
  if (in != nil) {
    [rv setMarginWithFloat:in->top_ withFloat:in->right_ withFloat:in->bottom_ withFloat:in->left_];
  }
  [rv setEditingWithBoolean:editing_];
}

- (void)renderSectionWithId:(id)contentProxy
                     withId:(id)labelProxy {
  if ((sectionIndex_ != nil) && (sectionIndex_->sectionPrototype_ != nil)) {
    if (sectionRenderer_ == nil) {
      RAREListView_SectionHeader *v = [[RAREListView_SectionHeader alloc] initWithId:contentProxy withId:labelProxy];
      sectionRenderer_ = [[RAREUIComponentRenderer alloc] initWithRAREiPlatformComponent:[[RAREComponent alloc] initWithRAREView:v]];
    }
    else {
      [((RAREListView_SectionHeader *) check_class_cast([sectionRenderer_ getNativeView], [RAREListView_SectionHeader class])) resetWithId:contentProxy withId:labelProxy];
    }
    [((RAREUIComponentRenderer *) nil_chk(sectionRenderer_)) prepareForReuseWithInt:0 withInt:0];
    (void) [((RAREListItemRenderer *) nil_chk(itemRenderer_)) configureRenderingComponentWithRAREiPlatformComponent:component_ withRAREiPlatformRenderingComponent:sectionRenderer_ withRARERenderableDataItem:sectionIndex_->sectionPrototype_ withInt:0 withBoolean:NO withBoolean:NO withRAREColumn:nil withRARERenderableDataItem:nil];
  }
}

- (void)rowsDeletedWithInt:(int)firstRow
                   withInt:(int)lastRow {
  if (sectionIndex_ != nil) {
    [self updateSectionIndex];
  }
  [super rowsDeletedWithInt:firstRow withInt:lastRow];
}

- (void)rowsInsertedWithInt:(int)firstRow
                    withInt:(int)lastRow {
  if (sectionIndex_ != nil) {
    [self updateSectionIndex];
  }
  [super rowsInsertedWithInt:firstRow withInt:lastRow];
}

- (void)startEditingWithRAREUIActionArray:(IOSObjectArray *)actions
                              withBoolean:(BOOL)animate {
  self->editing_ = YES;
  [((id<RAREiPlatformListDataModel>) nil_chk(listModel_)) setEditingWithBoolean:YES];
  if ([self getSelectedIndex] != -1) {
    [self clearSelections];
    [self revalidate];
  }
  if (actions == nil) {
    if (deletingAllowed_ && editingSelectionAllowed_) {
      actions = [IOSObjectArray arrayWithLength:2 type:[IOSClass classWithClass:[RAREUIAction class]]];
      (void) IOSObjectArray_Set(actions, 0, [[RAREUIAction alloc] initWithNSString:@"Rare.action.markAll" withJavaLangCharSequence:[RAREPlatform getResourceAsStringWithNSString:@"Rare.action.markAll"] withRAREiPlatformIcon:nil]);
      (void) IOSObjectArray_Set(actions, 1, [[RAREUIAction alloc] initWithNSString:@"Rare.action.delete" withJavaLangCharSequence:[RAREPlatform getResourceAsStringWithNSString:@"Rare.action.delete"] withRAREiPlatformIcon:nil]);
    }
    else if (deletingAllowed_) {
      actions = [IOSObjectArray arrayWithLength:1 type:[IOSClass classWithClass:[RAREUIAction class]]];
      (void) IOSObjectArray_Set(actions, 0, [[RAREUIAction alloc] initWithNSString:@"Rare.action.delete" withJavaLangCharSequence:[RAREPlatform getResourceAsStringWithNSString:@"Rare.action.delete"] withRAREiPlatformIcon:nil]);
    }
    else if (editingSelectionAllowed_) {
      actions = [IOSObjectArray arrayWithLength:1 type:[IOSClass classWithClass:[RAREUIAction class]]];
      (void) IOSObjectArray_Set(actions, 0, [[RAREUIAction alloc] initWithNSString:@"Rare.action.markAll" withJavaLangCharSequence:[RAREPlatform getResourceAsStringWithNSString:@"Rare.action.markAll"] withRAREiPlatformIcon:nil]);
    }
  }
  if ((actions != nil) && ((int) [actions count] > 0) && (editToolbar_ != nil)) {
    self->editActions_ = actions;
    id<RAREiToolBar> tb = editToolbar_;
    BOOL isDelete = NO;
    {
      IOSObjectArray *a__ = actions;
      id const *b__ = a__->buffer_;
      id const *e__ = b__ + a__->size_;
      while (b__ < e__) {
        RAREUIAction *a = (*b__++);
        if ([@"Rare.action.delete" equalsIgnoreCase:[((RAREUIAction *) nil_chk(a)) getActionName]]) {
          [a setActionListenerWithRAREiActionListener:[[RAREListView_$1 alloc] initWithRAREListView:self]];
          [a setEnabledOnSelectionOnlyWithBoolean:YES];
          isDelete = YES;
        }
        else if ([@"Rare.action.markAll" equalsIgnoreCase:[a getActionName]]) {
          [a setActionListenerWithRAREiActionListener:[[RAREListView_$2 alloc] initWithRAREListView:self]];
          markAll_ = a;
        }
        id<RAREiWidget> pb = [tb addWithRAREUIAction:a];
        if (isDelete) {
          [((RAREActionComponent *) check_class_cast([((id<RAREiWidget>) nil_chk(pb)) getDataComponent], [RAREActionComponent class])) setPainterHolderWithRAREPainterHolder:[RAREPainterUtils createRedHyperlinkPainterHolder]];
          [a setEnabledWithBoolean:NO];
        }
        isDelete = NO;
      }
    }
    [((id<RAREiPlatformComponent>) nil_chk([tb getComponent])) setVisibleWithBoolean:YES];
  }
  [self repaintVisibleRows];
  [self setEditingWithBoolean:YES withBoolean:animate];
  if (editModeNotifier_ != nil) {
    [editModeNotifier_ finishedWithBoolean:NO withId:[RAREPlatform findWidgetForComponentWithId:self]];
  }
}

- (void)stopEditingWithBoolean:(BOOL)animate {
  self->editing_ = NO;
  [((id<RAREiPlatformListDataModel>) nil_chk(listModel_)) setEditingWithBoolean:NO];
  [self setEditingWithBoolean:NO withBoolean:animate];
  if (editToolbar_ != nil) {
    [((id<RAREiPlatformComponent>) nil_chk([editToolbar_ getComponent])) setVisibleWithBoolean:NO];
  }
  if (editToolbar_ != nil) {
    [((id<RAREiPlatformComponent>) nil_chk([editToolbar_ getComponent])) setVisibleWithBoolean:NO];
    [editToolbar_ removeAllWidgets];
  }
  if (editModeNotifier_ != nil) {
    [editModeNotifier_ finishedWithBoolean:YES withId:[RAREPlatform findWidgetForComponentWithId:self]];
  }
  [self repaintVisibleRows];
}

- (void)updateSectionIndex {
  RAREUTCharacterIndex *ci = [((id<RAREiPlatformListDataModel>) nil_chk(listModel_)) getFilteringIndex];
  RARERenderableDataItem *prototype = ((RARESectionIndex *) nil_chk(sectionIndex_))->sectionPrototype_;
  sectionIndex_ = nil;
  if (ci != nil) {
    [self setSectionIndexWithRAREUTCharacterIndex:ci withInt:[listModel_ size] withRARERenderableDataItem:prototype];
  }
}

- (void)setCheckboxLeftXSlopWithInt:(int)checkboxLeftXSlop {
  self->checkboxLeftXSlop_ = checkboxLeftXSlop;
}

- (void)setCheckedRowsWithIntArray:(IOSIntArray *)indices {
  if (checkListManager_ != nil) {
    [checkListManager_ setCheckedRowsWithIntArray:indices];
    [self repaint];
  }
}

- (void)setEditModeNotifierWithRAREiFunctionCallback:(id<RAREiFunctionCallback>)editModeNotifier {
  self->editModeNotifier_ = editModeNotifier;
}

- (void)setEditingToolbarWithRAREiToolBar:(id<RAREiToolBar>)tb {
  editToolbar_ = tb;
  if (tb != nil) {
    [((id<RAREiPlatformComponent>) nil_chk([tb getComponent])) setVisibleWithBoolean:NO];
  }
}

- (void)setIconsWithRAREiPlatformIcon:(id<RAREiPlatformIcon>)checked
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)unchecked
                withRAREiPlatformIcon:(id<RAREiPlatformIcon>)indeterminate {
  checkboxHeight_ = [((id<RAREiPlatformIcon>) nil_chk(checked)) getIconHeight];
  checkboxWidth_ = [checked getIconWidth];
  checkboxHeight_ = [JavaLangMath maxWithInt:checkboxHeight_ withInt:[((id<RAREiPlatformIcon>) nil_chk(unchecked)) getIconHeight]];
  checkboxWidth_ = [JavaLangMath maxWithInt:checkboxWidth_ withInt:[unchecked getIconWidth]];
  if (checkListManager_ == nil) {
    checkListManager_ = [self createCheckListManager];
  }
  [((RARECheckListManager *) nil_chk(checkListManager_)) setIconsWithRAREiPlatformIcon:checked withRAREiPlatformIcon:unchecked withRAREiPlatformIcon:indeterminate];
  [self calculateOffset];
}

- (void)setItemRendererWithRAREListItemRenderer:(RAREListItemRenderer *)lr {
  [super setItemRendererWithRAREListItemRenderer:lr];
  (void) [((RAREUIInsets *) nil_chk(rinsets_)) setWithRAREUIInsets:[((RAREListItemRenderer *) nil_chk(lr)) getInsets]];
  [self calculateOffset];
}

- (void)setLinkSelectionWithBoolean:(BOOL)linked {
  linkedSelection_ = linked;
}

- (void)setListModelWithRAREiPlatformListDataModel:(id<RAREiPlatformListDataModel>)listModel {
  if (self->listModel_ != nil) {
    [self->listModel_ removeDataModelListenerWithRAREiDataModelListener:self];
  }
  self->listModel_ = listModel;
  if (listModel != nil) {
    [listModel addDataModelListenerWithRAREiDataModelListener:self];
    if (checkListManager_ != nil) {
      [checkListManager_ setListModelWithRAREiPlatformListDataModel:listModel];
    }
  }
  [self setListModelExWithRAREiPlatformListDataModel:listModel];
}

- (void)setPaintHandlerEnabledWithBoolean:(BOOL)enabled {
  [((RAREAPListView*)proxy_) setPaintHandlerEnabled: enabled];
  [((RAREAPListView*)proxy_) setSimplePaint: [self isSingleColorPainter]];
}

- (void)setRenderingCallbackWithRAREListView_iIOSRenderingCallback:(id<RAREListView_iIOSRenderingCallback>)cb {
  renderingCallback_ = cb;
}

- (void)setRowCheckedWithInt:(int)row
                 withBoolean:(BOOL)checked {
  if (checkListManager_ != nil) {
    if ([checkListManager_ isRowCheckedWithInt:row] != checked) {
      [self toggleCheckedStateWithInt:row];
    }
  }
}

- (void)setRowEditingComponentWithRAREiPlatformComponent:(id<RAREiPlatformComponent>)c
                                             withBoolean:(BOOL)centerVertically {
  editingComponent_ = c;
  centerEditingComponentVertically_ = centerVertically;
  if (c != nil) {
    needsContentView_ = YES;
    if (flingGestureListener_ == nil) {
      flingGestureListener_ = [[RAREListView_RowEditingGestureListener alloc] initWithRAREListView:self];
    }
    [self setRowEditingGestureListenerWithRAREiGestureListener:flingGestureListener_];
  }
  else if (flingGestureListener_ != nil) {
    flingGestureListener_ = nil;
    [self setRowEditingGestureListenerWithRAREiGestureListener:flingGestureListener_];
  }
}

- (void)setSectionIndexWithRARESectionIndex:(RARESectionIndex *)sectionIndex {
  self->sectionIndex_ = sectionIndex;
}

- (void)setSectionIndexWithRAREUTCharacterIndex:(RAREUTCharacterIndex *)ci
                                        withInt:(int)listSize
                     withRARERenderableDataItem:(RARERenderableDataItem *)headerPrototype {
  id<JavaUtilMap> indexMap = [((RAREUTCharacterIndex *) nil_chk(ci)) getIndexMap];
  id<JavaUtilIterator> it = [((id<JavaUtilSet>) nil_chk([((id<JavaUtilMap>) nil_chk(indexMap)) entrySet])) iterator];
  int len = [indexMap size];
  int i = 0;
  IOSObjectArray *a = [IOSObjectArray arrayWithLength:len + 1 type:[IOSClass classWithClass:[RAREUTObjectHolder class]]];
  while ([((id<JavaUtilIterator>) nil_chk(it)) hasNext]) {
    id<JavaUtilMap_Entry> e = (id<JavaUtilMap_Entry>) check_protocol_cast([it next], @protocol(JavaUtilMap_Entry));
    (void) IOSObjectArray_Set(a, i++, [[RAREUTObjectHolder alloc] initWithId:[((id<JavaUtilMap_Entry>) nil_chk(e)) getKey] withId:[e getValue]]);
  }
  (void) IOSObjectArray_Set(a, len, [[RAREUTObjectHolder alloc] initWithId:[JavaLangCharacter valueOfWithChar:'z'] withId:[JavaLangInteger valueOfWithInt:listSize]]);
  [JavaUtilArrays sortWithNSObjectArray:a withJavaUtilComparator:[[RAREListView_$3 alloc] init]];
  i = 0;
  IOSIntArray *position = [IOSIntArray arrayWithLength:len];
  IOSIntArray *length = [IOSIntArray arrayWithLength:len];
  IOSObjectArray *titles = [IOSObjectArray arrayWithLength:len type:[IOSClass classWithClass:[NSString class]]];
  int n = 0;
  while (i < len) {
    JavaLangCharacter *c = (JavaLangCharacter *) check_class_cast(((RAREUTObjectHolder *) nil_chk(IOSObjectArray_Get(a, i)))->type_, [JavaLangCharacter class]);
    int start = [((JavaLangInteger *) check_class_cast(((RAREUTObjectHolder *) nil_chk(IOSObjectArray_Get(a, i++)))->value_, [JavaLangInteger class])) intValue];
    int end = [((JavaLangInteger *) check_class_cast(((RAREUTObjectHolder *) nil_chk(IOSObjectArray_Get(a, i)))->value_, [JavaLangInteger class])) intValue];
    (*IOSIntArray_GetRef(position, n)) = start;
    (*IOSIntArray_GetRef(length, n)) = end - start;
    (void) IOSObjectArray_Set(titles, n, [NSString valueOf:c]);
    n++;
    start = end;
  }
  sectionIndex_ = [[RARESectionIndex alloc] initWithNSStringArray:titles withIntArray:position withIntArray:length withRARERenderableDataItem:headerPrototype];
}

- (void)setSelectedIndexWithInt:(int)index {
  [super setSelectedIndexWithInt:index];
  if (linkedSelection_ && (checkListManager_ != nil)) {
    if (![checkListManager_ isRowCheckedWithInt:index]) {
      [self toggleCheckedStateWithInt:index];
    }
  }
}

- (void)setShowSectionIndexWithBoolean:(BOOL)show {
  [((RAREAPListView*)proxy_) setUseSectionIndex: show];
}

- (void)setSingleClickActionWithBoolean:(BOOL)singleClickAction {
  singleClickAction_=YES;
  [((RAREAPListView*)proxy_) setSingleClickAction: singleClickAction];
}

- (int)getCheckboxLeftXSlop {
  return checkboxLeftXSlop_;
}

- (id<RAREiFunctionCallback>)getEditModeNotifier {
  return editModeNotifier_;
}

- (RAREListItemRenderer *)getItemRenderer {
  return itemRenderer_;
}

- (void)getPreferredSizeWithRAREUIDimension:(RAREUIDimension *)size
                                  withFloat:(float)maxWidth {
  [RARETableHelper calculateListSizeWithJavaUtilList:listModel_ withRAREiPlatformComponent:component_ withRAREiPlatformItemRenderer:itemRenderer_ withRAREColumn:nil withRAREUIDimension:size withInt:-1 withInt:(int) maxWidth withInt:rowHeight_];
  int h = [RARETableHelper getPreferredListHeightWithRAREiPlatformComponent:[RAREComponent findFromViewWithRAREView:self] withInt:[JavaLangMath maxWithInt:minVisibleRows_ withInt:visibleRows_] withInt:rowHeight_ withInt:[self getRowCount]];
  ((RAREUIDimension *) nil_chk(size))->height_ = [JavaLangMath maxWithFloat:h withFloat:size->height_];
}

- (id<RAREiTreeItem>)getTreeItemWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  return nil;
}

- (BOOL)hasCheckedRows {
  return (checkListManager_ != nil) && ([checkListManager_ hasCheckedRows]);
}

- (BOOL)isRowCheckedWithInt:(int)row {
  return (checkListManager_ != nil) && [checkListManager_ isRowCheckedWithInt:row];
}

- (BOOL)isScrollView {
  return YES;
}

- (BOOL)isSingleClickAction {
  return singleClickAction_;
}

- (void)calculateOffset {
  int left = 0;
  int right = 0;
  if (checkboxWidth_ > 0) {
    if (selectionType_ == [RAREiListHandler_SelectionTypeEnum CHECKED_LEFT]) {
      left = checkboxWidth_ + RAREaTableBasedView_PAD_SIZE;
    }
    else {
      right = checkboxWidth_ + RAREaTableBasedView_PAD_SIZE;
    }
  }
  [self updateRenderInsetsForCheckBoxWithFloat:left withFloat:right];
}

- (BOOL)canDragWithRARERenderableDataItem:(RARERenderableDataItem *)item {
  return draggingAllowed_ && [((RARERenderableDataItem *) nil_chk(item)) isDraggingAllowed];
}

- (BOOL)checForLinkedSelectionWithInt:(int)row
                          withBoolean:(BOOL)selected {
  if (linkedSelection_ && (checkListManager_ != nil)) {
    if ([checkListManager_ isRowCheckedWithInt:row] != selected) {
      return [checkListManager_ toggleRowWithInt:row];
    }
  }
  return NO;
}

- (BOOL)checkForCellHotspotWithInt:(int)row
                         withFloat:(float)x
                         withFloat:(float)y
                         withFloat:(float)width
                         withFloat:(float)height {
  if ((checkListManager_ != nil) && !linkedSelection_ && [self isOnCheckBoxWithFloat:x withFloat:y withFloat:width withFloat:height withInt:[self getIndentWithInt:row]]) {
    if ([checkListManager_ toggleRowWithInt:row]) {
      [self repaint];
    }
    else {
      [self repaintRowWithInt:row];
    }
    return YES;
  }
  return NO;
}

- (RARECheckListManager *)createCheckListManager {
  RARECheckListManager *cm = [[RARECheckListManager alloc] initWithBoolean:NO withInt:-2];
  if (listModel_ != nil) {
    [cm setListModelWithRAREiPlatformListDataModel:listModel_];
  }
  return cm;
}

- (void)disposeEx {
  itemRenderer_ = nil;
  if (listModel_ != nil) {
    [listModel_ removeDataModelListenerWithRAREiDataModelListener:self];
  }
  if (sectionRenderer_ != nil) {
    [sectionRenderer_ dispose];
    sectionRenderer_ = nil;
  }
  [super disposeEx];
}

- (void)editModeDeleteMarkedItems {
  RAREaListViewer *lv = (RAREaListViewer *) check_class_cast([((RAREComponent *) nil_chk([RAREComponent fromViewWithRAREView:self])) getWidget], [RAREaListViewer class]);
  [((RAREaListViewer *) nil_chk(lv)) removeAllWithJavaUtilCollection:[JavaUtilArrays asListWithNSObjectArray:[((id<RAREiPlatformListDataModel>) nil_chk(listModel_)) editModeGetMarkedItems]]];
  [listModel_ editModeClearMarks];
  if (autoEndEditing_) {
    [self stopEditingWithBoolean:YES];
  }
  else {
    [self updateActions];
  }
}

- (int)geIndentWithInt:(int)row {
  return 0;
}

- (void)itemDeselectedWithInt:(int)index {
  [self checForLinkedSelectionWithInt:index withBoolean:NO];
  [super itemDeselectedWithInt:index];
}

- (void)itemSelectedWithInt:(int)index {
  [self checForLinkedSelectionWithInt:index withBoolean:YES];
  [super itemSelectedWithInt:index];
}

- (void)moveWithInt:(int)from
            withInt:(int)to {
  [super moveWithInt:from withInt:to];
  [self updateActions];
}

- (void)moveWithRAREUTIntList:(RAREUTIntList *)from
                      withInt:(int)to {
  [super moveWithRAREUTIntList:from withInt:to];
  [self updateActions];
}

- (void)toggleCheckedStateWithInt:(int)row {
  [((RARECheckListManager *) nil_chk(checkListManager_)) toggleRowWithInt:row];
  [self repaintRowWithInt:row];
}

- (void)toggleEditModeItemCheckedWithInt:(int)row {
  [((id<RAREiPlatformListDataModel>) nil_chk(listModel_)) editModeToggleMarkWithInt:row];
  [self repaintRowWithInt:row];
  [self updateActions];
}

- (void)updateActions {
  if (self->editActions_ != nil) {
    int mc = [((id<RAREiPlatformListDataModel>) nil_chk(listModel_)) editModeGetMarkCount];
    int size = [listModel_ size];
    BOOL hasMarks = mc > 0;
    BOOL allMarked = (mc == size) && (mc != 0);
    if (markAll_ != nil) {
      [markAll_ setActionTextWithJavaLangCharSequence:[RAREPlatform getResourceAsStringWithNSString:allMarked ? @"Rare.action.unmarkAll" : @"Rare.action.markAll"]];
    }
    {
      IOSObjectArray *a__ = editActions_;
      id const *b__ = a__->buffer_;
      id const *e__ = b__ + a__->size_;
      while (b__ < e__) {
        RAREUIAction *a = (*b__++);
        [((RAREUIAction *) nil_chk(a)) setEnabledWithBoolean:size > 0];
        if ([a isEnabledOnSelectionOnly]) {
          [a setEnabledWithBoolean:hasMarks];
        }
      }
    }
  }
}

- (void)updateRenderInsetsForCheckBoxWithFloat:(float)left
                                     withFloat:(float)right {
  if (itemRenderer_ != nil) {
    RAREUIInsets *in = [itemRenderer_ getInsets];
    (void) [((RAREUIInsets *) nil_chk(in)) setWithRAREUIInsets:rinsets_];
    in->left_ += left;
    in->right_ += right;
  }
}

- (void)setListModelExWithRAREiPlatformListDataModel:(id<RAREiPlatformListDataModel>)listModel {
  [((RAREAPListView*)proxy_) setList: listModel];
}

- (void)setRowEditingGestureListenerWithRAREiGestureListener:(id<RAREiGestureListener>)l {
  [((RAREAPListView*)proxy_) setRowEditorGestureListener: l];
}

- (int)getIndentWithInt:(int)row {
  return 0;
}

- (BOOL)isOnCheckBoxWithFloat:(float)x
                    withFloat:(float)y
                    withFloat:(float)width
                    withFloat:(float)height
                      withInt:(int)indent {
  float sy = (height - checkboxHeight_) / 2;
  if ((y < sy - [RAREaTableBasedView INDICATOR_SLOP]) || (y > (sy + checkboxHeight_ + [RAREaTableBasedView INDICATOR_SLOP]))) {
    return NO;
  }
  float sx;
  int slop = [RAREaTableBasedView INDICATOR_SLOP];
  if (selectionType_ == [RAREiListHandler_SelectionTypeEnum CHECKED_RIGHT]) {
    sx = width - rightOffset_ - RAREaTableBasedView_PAD_SIZE - checkboxWidth_;
    ;
  }
  else {
    sx = leftOffset_ + RAREaTableBasedView_PAD_SIZE + indent;
    slop = checkboxLeftXSlop_;
  }
  return (x >= sx - slop) && (x <= (sx + checkboxWidth_ + slop));
}

- (BOOL)isTree {
  return NO;
}

+ (id)createProxyWithBoolean:(BOOL)grouped {
  #if TARGET_OS_IPHONE
  UITableViewStyle style=grouped ? UITableViewStyleGrouped : UITableViewStylePlain;
  return [[[RAREAPListView alloc]initWithStyle: style ] configureForList];
  #else
  return [[[RAREAPListView alloc]init] configureForList];
  #endif
  
}

- (void)copyAllFieldsTo:(RAREListView *)other {
  [super copyAllFieldsTo:other];
  other->borderInsets_ = borderInsets_;
  other->characterIndex_ = characterIndex_;
  other->checkListManager_ = checkListManager_;
  other->checkboxLeftXSlop_ = checkboxLeftXSlop_;
  other->columnSizesInitialized_ = columnSizesInitialized_;
  other->editActions_ = editActions_;
  other->editModeNotifier_ = editModeNotifier_;
  other->editToolbar_ = editToolbar_;
  other->editing_ = editing_;
  other->editingComponent_ = editingComponent_;
  other->flingGestureListener_ = flingGestureListener_;
  other->linkedSelection_ = linkedSelection_;
  other->markAll_ = markAll_;
  other->renderingCallback_ = renderingCallback_;
  other->rinsets_ = rinsets_;
  other->sectionIndex_ = sectionIndex_;
  other->sectionRenderer_ = sectionRenderer_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithId:", NULL, NULL, 0x4, NULL },
    { "setPaintHandlerEnabledWithBoolean:", NULL, "V", 0x101, NULL },
    { "setShowSectionIndexWithBoolean:", NULL, "V", 0x101, NULL },
    { "setSingleClickActionWithBoolean:", NULL, "V", 0x101, NULL },
    { "getEditModeNotifier", NULL, "LRAREiFunctionCallback", 0x1, NULL },
    { "getItemRenderer", NULL, "LRAREListItemRenderer", 0x1, NULL },
    { "getTreeItemWithRARERenderableDataItem:", NULL, "LRAREiTreeItem", 0x1, NULL },
    { "hasCheckedRows", NULL, "Z", 0x1, NULL },
    { "isRowCheckedWithInt:", NULL, "Z", 0x1, NULL },
    { "isScrollView", NULL, "Z", 0x1, NULL },
    { "isSingleClickAction", NULL, "Z", 0x1, NULL },
    { "calculateOffset", NULL, "V", 0x4, NULL },
    { "canDragWithRARERenderableDataItem:", NULL, "Z", 0x4, NULL },
    { "checForLinkedSelectionWithInt:withBoolean:", NULL, "Z", 0x4, NULL },
    { "checkForCellHotspotWithInt:withFloat:withFloat:withFloat:withFloat:", NULL, "Z", 0x4, NULL },
    { "createCheckListManager", NULL, "LRARECheckListManager", 0x4, NULL },
    { "disposeEx", NULL, "V", 0x4, NULL },
    { "editModeDeleteMarkedItems", NULL, "V", 0x4, NULL },
    { "geIndentWithInt:", NULL, "I", 0x4, NULL },
    { "itemDeselectedWithInt:", NULL, "V", 0x4, NULL },
    { "itemSelectedWithInt:", NULL, "V", 0x4, NULL },
    { "moveWithInt:withInt:", NULL, "V", 0x4, NULL },
    { "moveWithRAREUTIntList:withInt:", NULL, "V", 0x4, NULL },
    { "toggleCheckedStateWithInt:", NULL, "V", 0x4, NULL },
    { "toggleEditModeItemCheckedWithInt:", NULL, "V", 0x4, NULL },
    { "updateActions", NULL, "V", 0x4, NULL },
    { "updateRenderInsetsForCheckBoxWithFloat:withFloat:", NULL, "V", 0x4, NULL },
    { "setListModelExWithRAREiPlatformListDataModel:", NULL, "V", 0x104, NULL },
    { "setRowEditingGestureListenerWithRAREiGestureListener:", NULL, "V", 0x104, NULL },
    { "getIndentWithInt:", NULL, "I", 0x4, NULL },
    { "isOnCheckBoxWithFloat:withFloat:withFloat:withFloat:withInt:", NULL, "Z", 0x4, NULL },
    { "isTree", NULL, "Z", 0x4, NULL },
    { "createProxyWithBoolean:", NULL, "LNSObject", 0x10a, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "rinsets_", NULL, 0x4, "LRAREUIInsets" },
    { "checkboxLeftXSlop_", NULL, 0x4, "I" },
    { "borderInsets_", NULL, 0x4, "LRAREUIInsets" },
    { "checkListManager_", NULL, 0x4, "LRARECheckListManager" },
    { "columnSizesInitialized_", NULL, 0x4, "Z" },
    { "editActions_", NULL, 0x4, "LIOSObjectArray" },
    { "editModeNotifier_", NULL, 0x4, "LRAREiFunctionCallback" },
    { "editToolbar_", NULL, 0x4, "LRAREiToolBar" },
    { "editing_", NULL, 0x4, "Z" },
    { "editingComponent_", NULL, 0x4, "LRAREiPlatformComponent" },
    { "flingGestureListener_", NULL, 0x4, "LRAREiGestureListener" },
    { "linkedSelection_", NULL, 0x4, "Z" },
    { "markAll_", NULL, 0x4, "LRAREUIAction" },
    { "renderingCallback_", NULL, 0x4, "LRAREListView_iIOSRenderingCallback" },
  };
  static J2ObjcClassInfo _RAREListView = { "ListView", "com.appnativa.rare.platform.apple.ui.view", NULL, 0x1, 33, methods, 14, fields, 0, NULL};
  return &_RAREListView;
}

@end

@interface RAREListView_iIOSRenderingCallback : NSObject
@end

@implementation RAREListView_iIOSRenderingCallback

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "renderItemWithInt:withRARERenderableDataItem:withRAREaTableBasedView_RowView:withBoolean:withBoolean:withRAREiTreeItem:", NULL, "Z", 0x401, NULL },
  };
  static J2ObjcClassInfo _RAREListView_iIOSRenderingCallback = { "iIOSRenderingCallback", "com.appnativa.rare.platform.apple.ui.view", "ListView", 0x201, 1, methods, 0, NULL, 0, NULL};
  return &_RAREListView_iIOSRenderingCallback;
}

@end
@implementation RAREListView_RowEditingGestureListener

- (void)onFlingWithId:(id)view
   withRAREMouseEvent:(RAREMouseEvent *)e1
   withRAREMouseEvent:(RAREMouseEvent *)e2
            withFloat:(float)velocityX
            withFloat:(float)velocityY {
  int row = [this$0_ rowAtPointWithFloat:(int) [((RAREMouseEvent *) nil_chk(e1)) getX] withFloat:(int) [e1 getY]];
  RAREaTableBasedView_RowView *v = (row == -1) ? nil : [this$0_ getViewForRowWithInt:row];
  if (this$0_->editingRow_ == -1) {
    if ([e1 getX] > [((RAREMouseEvent *) nil_chk(e2)) getX]) {
      this$0_->editingRow_ = row;
      this$0_->lastEditedRow_ = row;
      [v showRowEditingComponentWithRAREiPlatformComponent:this$0_->editingComponent_ withBoolean:YES];
    }
  }
  else {
    if ([e1 getX] < [((RAREMouseEvent *) nil_chk(e2)) getX]) {
      [v hideRowEditingComponentWithBoolean:YES];
      this$0_->editingRow_ = -1;
    }
  }
}

- (void)onLongPressWithId:(id)view
       withRAREMouseEvent:(RAREMouseEvent *)e {
}

- (void)onRotateWithId:(id)view
               withInt:(int)type
             withFloat:(float)rotation
             withFloat:(float)velocity
             withFloat:(float)focusX
             withFloat:(float)focusY {
}

- (void)onScaleEventWithId:(id)view
                   withInt:(int)type
                    withId:(id)sgd
                 withFloat:(float)factor {
}

- (id)initWithRAREListView:(RAREListView *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREListView" },
  };
  static J2ObjcClassInfo _RAREListView_RowEditingGestureListener = { "RowEditingGestureListener", "com.appnativa.rare.platform.apple.ui.view", "ListView", 0x0, 0, NULL, 1, fields, 0, NULL};
  return &_RAREListView_RowEditingGestureListener;
}

@end
@implementation RAREListView_SectionHeader

- (id)initWithId:(id)nsview
          withId:(id)label {
  if (self = [super initWithId:nsview]) {
    self->label_ = label;
  }
  return self;
}

- (void)resetWithId:(id)contentProxy
             withId:(id)labelProxy {
  [self setProxyWithId:contentProxy];
  label_ = labelProxy;
}

- (void)setFontWithRAREUIFont:(RAREUIFont *)font {
  if(font!=font_) {
    font_ = font;
    if(font!=nil) {
      [((UILabel*)label_) setFont: (UIFont*)[font getIOSProxy]];
    }
  }
}

- (void)setForegroundColorExWithRAREUIColor:(RAREUIColor *)fg {
  if(fg!=nil) {
    [((UILabel*)label_) setTextColor: fg.getAPColor];
  }
}

- (void)copyAllFieldsTo:(RAREListView_SectionHeader *)other {
  [super copyAllFieldsTo:other];
  other->label_ = label_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "setFontWithRAREUIFont:", NULL, "V", 0x101, NULL },
    { "setForegroundColorExWithRAREUIColor:", NULL, "V", 0x104, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "label_", NULL, 0x0, "LNSObject" },
  };
  static J2ObjcClassInfo _RAREListView_SectionHeader = { "SectionHeader", "com.appnativa.rare.platform.apple.ui.view", "ListView", 0x8, 2, methods, 1, fields, 0, NULL};
  return &_RAREListView_SectionHeader;
}

@end
@implementation RAREListView_$1

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e {
  [this$0_ editModeDeleteMarkedItems];
}

- (id)initWithRAREListView:(RAREListView *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREListView" },
  };
  static J2ObjcClassInfo _RAREListView_$1 = { "$1", "com.appnativa.rare.platform.apple.ui.view", "ListView", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREListView_$1;
}

@end
@implementation RAREListView_$2

- (void)actionPerformedWithRAREActionEvent:(RAREActionEvent *)e {
  [this$0_ editModeChangeAllMarksWithBoolean:[((id<RAREiPlatformListDataModel>) nil_chk(this$0_->listModel_)) editModeGetMarkCount] < [this$0_->listModel_ size]];
}

- (id)initWithRAREListView:(RAREListView *)outer$ {
  this$0_ = outer$;
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcFieldInfo fields[] = {
    { "this$0_", NULL, 0x1012, "LRAREListView" },
  };
  static J2ObjcClassInfo _RAREListView_$2 = { "$2", "com.appnativa.rare.platform.apple.ui.view", "ListView", 0x8000, 0, NULL, 1, fields, 0, NULL};
  return &_RAREListView_$2;
}

@end
@implementation RAREListView_$3

- (int)compareWithId:(id)o1
              withId:(id)o2 {
  return [((JavaLangInteger *) check_class_cast(((RAREUTObjectHolder *) nil_chk(o1))->value_, [JavaLangInteger class])) compareToWithId:(JavaLangInteger *) check_class_cast(((RAREUTObjectHolder *) nil_chk(o2))->value_, [JavaLangInteger class])];
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcClassInfo _RAREListView_$3 = { "$3", "com.appnativa.rare.platform.apple.ui.view", "ListView", 0x8000, 0, NULL, 0, NULL, 0, NULL};
  return &_RAREListView_$3;
}

@end
