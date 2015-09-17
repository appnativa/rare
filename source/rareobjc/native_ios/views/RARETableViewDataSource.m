//
//  RARETableViewDataSource.m
//  RareTOUCH
//
//  Created by Don DeCoteau on 10/1/14.
//  Copyright (c) 2014 SparseWare. All rights reserved.
//

#import "RARETableViewDataSource.h"

#import "RAREAPListView.h"
#import "RAREUITableViewCell.h"
#import "com/appnativa/rare/platform/apple/ui/view/ListView.h"
#import "com/appnativa/rare/platform/apple/ui/view/SectionIndex.h"
#import "APView+Component.h"
#import "com/appnativa/rare/ui/tree/iTreeItem.h"
#import "AppleHelper.h"

@implementation RARETableViewDataSource
-(id)initWithList:(id<RAREiPlatformListDataModel>)list {
  self = [super init];
  if (self) {
    list_=list;
  }
  
  return self;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
  RAREAPListView* lb=(RAREAPListView*)tableView;
  RAREUITableViewCell *view=nil;
  id<RAREiTreeItem > ti=nil;
  int row=[lb rowFromPath:indexPath];
  RARERenderableDataItem* item=[list_ getWithInt:(int)row];
  if(lb->isTree_) {
    RAREListView* tree=(RAREListView*)tableView.sparView;
    ti=[tree getTreeItemWithRARERenderableDataItem:item];
    if(ti && ![ti isLeaf]) {
      if([ti isExpanded]) {
        view= [tableView dequeueReusableCellWithIdentifier:@"__row.folder_open__"];
        if(!view) {
          view=[[RAREUITableViewCell alloc] initWithStyle:lb->cellStyle reuseIdentifier:@"__row.folder_open__"];
        }
      }
      else {
        view= [tableView dequeueReusableCellWithIdentifier:@"__row.folder_closed__"];
        if(!view) {
          view=[[RAREUITableViewCell alloc] initWithStyle:lb->cellStyle  reuseIdentifier:@"__row.folder_closed__"];
        }
      }
    }
  }
  if(!view){
    view= [tableView dequeueReusableCellWithIdentifier:@"__row.view__"];
    if(view==nil) {
      view=[[RAREUITableViewCell alloc] initWithStyle:lb->cellStyle  reuseIdentifier:@"__row.view__"];
    }
  }
  view.accessoryType=lb->accessoryCellStyle;
  view.editingAccessoryType=lb->accessoryEditingCellStyle;
  view->treeItem=ti;
  view->listView=(RAREListView*)lb.sparView;
  view->rowItem=item;
  view->rowIndex=(int)row;
  if(lb->disableNativeSelection_) {
    view.selectionStyle=UITableViewCellSelectionStyleNone;
  }
  if(!view.sparView) {
    [view->listView setupNewRenderingCellWithId:view];
  }
  [view setNeedsDisplay];
  return view;
}

-(void)setList:(id<RAREiPlatformListDataModel>)list {
  list_=list;
}

-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
  RAREListView* lb=(RAREListView*)tableView.sparView;
  if(lb->sectionIndex_) {
    return [lb->sectionIndex_ getSectionCount];
  }
  return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
  RAREListView* lb=(RAREListView*)tableView.sparView;
  if(lb->sectionIndex_) {
    return [lb->sectionIndex_ getSizeWithInt:(int)section];
  }
  return list_.size;
}
- (NSArray *)sectionIndexTitlesForTableView:(UITableView *)tableView {
  RAREListView* lb=(RAREListView*)tableView.sparView;
  if(lb->sectionIndex_) {
    return [AppleHelper toNSArray:lb->sectionIndex_->indextitles_];
  }
  else {
    return nil;
  }
}

-(NSInteger)tableView:(UITableView *)tableView sectionForSectionIndexTitle:(NSString *)title atIndex:(NSInteger)index {
  return index;
}

-(NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
  RAREListView* lb=(RAREListView*)tableView.sparView;
  if(lb->sectionIndex_) {
    return [lb->sectionIndex_ getTitleTextWithInt:(int)section];
  }
  return nil;
}


-(RARERenderableDataItem*) getItem: (int) index {
  return [list_ getWithInt:index];
}
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath {
  if(!((RAREListView*)tableView.sparView)->draggingAllowed_) {
    return NO;
  }
  
  int row=[(RAREAPListView*)tableView rowFromPath:indexPath];
  RARERenderableDataItem* item=[list_ getWithInt:(int)row];
  return item->draggingAllowed_;
}
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)sourceIndexPath toIndexPath:(NSIndexPath *)destinationIndexPath {
  int from=[(RAREAPListView*)tableView rowFromPath:sourceIndexPath];
  int to=[(RAREAPListView*)tableView rowFromPath:destinationIndexPath];
  RAREListView* lb=(RAREListView*)tableView.sparView;
  [lb moveWithInt:from withInt:to];
  [(RAREAPListView*)tableView reloadData];
}

- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
  if (editingStyle == UITableViewCellEditingStyleDelete) {
    RAREListView* lb=(RAREListView*)tableView.sparView;
    [lb removeWithInt:[(RAREAPListView*)tableView rowFromPath:indexPath]];
  }
}

@end
