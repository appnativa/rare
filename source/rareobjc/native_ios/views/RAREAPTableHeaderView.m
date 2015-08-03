//
//  RAREAPTableHeaderView.m
//  RareOSX
//
//  Created by Don DeCoteau on 4/16/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import "RAREAPTableHeaderView.h"
#import "RAREAPTableColumn.h"
#import "com/appnativa/rare/ui/UIRectangle.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "com/appnativa/rare/platform/apple/ui/view/ParentView.h"
#import "com/appnativa/rare/ui/Column.h"
#import "com/appnativa/rare/ui/UIColor.h"
#import "com/appnativa/rare/ui/iPlatformBorder.h"
#import "APView+Component.h"
#import <com/appnativa/rare/ui/table/TableHeader.h>

@implementation RAREAPTableHeaderView
@synthesize  tableColumns;
@synthesize tableView;
@synthesize horizontallyScollable;

+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
      tableColumns=[NSMutableArray new];
      self.columnMargin=1;
    }
    
    return self;
}
-(void)sparDispose {
  [super sparDispose];
  [tableColumns removeAllObjects];
}
-(void) addTableColumn: (RAREAPTableColumn*) col {
  NSMutableArray* a=(NSMutableArray*)tableColumns;
  [a addObject:col];
  [self addSubview:col];
  
}
-(void) addTableColumn: (RAREAPTableColumn*) col  atIndex: (NSInteger) index{
  NSMutableArray* a=tableColumns;
  if(index<0 || index>=a.count) {
    [a addObject:col];
  }
  else {
    [a insertObject:col atIndex:index];
  }
  [self addSubview:col];
}
-(void) moveColumn: (NSInteger) column toColumn: (NSInteger) targetColumn {
  NSMutableArray* a=(NSMutableArray*)tableColumns;
  [a removeObjectAtIndex:column];
  UIView *o=[a objectAtIndex:column];
  [o removeFromSuperview];
  if(targetColumn<a.count) {
    [a insertObject:o atIndex:targetColumn];
    [self insertSubview:o atIndex:targetColumn];
  }
  else {
    [a addObject:o];
    [self addSubview:o];
  }
}


-(void) removeAllColumns {
  [tableColumns enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
    [((UIView*)obj) removeFromSuperview];
  }];
  [tableColumns removeAllObjects];
  
}
     
-(void) removeColumn: (int) index {
  NSMutableArray* a=tableColumns;
  UIView* o=[a objectAtIndex:index];
  [o removeFromSuperview];
  [a removeObjectAtIndex:index];
}

-(void) repaintColumn: (int) index {
  NSMutableArray* a=tableColumns;
  UIView* o=[a objectAtIndex:index];
  [o setNeedsDisplay];
}
-(void) setColumnPressedAtIndex: (int) index pressed: (BOOL) pressed {
  NSMutableArray* a=tableColumns;
  RAREAPTableColumn* o=(RAREAPTableColumn*)[a objectAtIndex:index];
  [o setPressed: pressed];
  [o setNeedsDisplay];
}

-(int) getColumnIndexAtX: (int) x andY: (int) y{
  CGSize size=self.frame.size;
  if(y<0 || x<0 || y>size.height || x>size.width) {
    return -1;
  }
  CGFloat xx=0;
  NSArray * a=self.subviews;
  NSInteger len=a.count;
  for(NSInteger i=0;i<len;i++) {
    RAREAPTableColumn* tc=(RAREAPTableColumn*) [a objectAtIndex:i];
    if(!tc.hidden) {
      CGFloat w=tc.frame.size.width;
      if(x>=xx && x<xx+w) {
        return (int)i;
      }
      xx+=w;
    }
  }
  return -1;
}

-(void) drawSeparatorEx:(RAREAppleGraphics*) g border:(id<RAREiPlatformBorder>) border lineColor: (RAREUIColor*) color {
  UIColor* uicolor=(UIColor*)color.getAPColor;
  CGContextRef context=UIGraphicsGetCurrentContext();
  CGSize size=self.frame.size;
  __block CGFloat height=size.height;
  __block BOOL first=YES;
  [self.subviews enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
    RAREAPTableColumn* tc=(RAREAPTableColumn*) obj;
    if(!tc.hidden) {
      CGRect frame=tc.frame;
      CGFloat w=frame.size.width;
      CGFloat x=frame.origin.x;
      if(border) {
        [border paintWithRAREiPlatformGraphics:g
                                     withFloat:x
                                     withFloat:0
                                     withFloat:w
                                     withFloat:height withBoolean:NO];
        [border paintWithRAREiPlatformGraphics:g
                                     withFloat:x
                                     withFloat:0
                                     withFloat:w
                                     withFloat:height withBoolean:YES];
        
      }
      if(first) {
        first=NO;
      }
      else if(color) {
        CGPoint points[2]={CGPointMake(x-.5,0),CGPointMake(x-.5,height)};
        [uicolor set];
        CGContextStrokeLineSegments(context,points,2);
      }
    }
  }];
  if(color) {
    [uicolor set];
    RAREView* view=self.sparView;
    RARETableHeader* header=(RARETableHeader*)view->component_;
    if(header->paintLeftMargin_) {
      CGPoint points[2]={CGPointMake(0,0),CGPointMake(0,height)};
      CGContextStrokeLineSegments(context,points,2);
    }
    if(header->paintRightMargin_) {
      CGPoint points[2]={CGPointMake(size.width-0.5,0),CGPointMake(size.width-0.5,height)};
      CGContextStrokeLineSegments(context,points,2);
    }
  }
}

-(CGSize)sizeThatFits:(CGSize)size {
  __block CGFloat w=0;
  __block CGFloat h=0;
  __block CGFloat cm=self.columnMargin;
  [self.subviews enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
    RAREAPTableColumn* tc=(RAREAPTableColumn*) obj;
    if(!tc.hidden) {
      RAREColumn* c=tc->columnDescription;
      CGSize s=[tc preferredSize];
      if(c->width_>s.width) {
        s.width=c->width_;
      }
      w+=s.width+cm;
      h=MAX(h,s.height);
    }
  }];
  size.width=w;
  size.height=h;
  return size;
}

-(void)layoutSubviews {
  __block CGSize size=self.frame.size;
  __block CGFloat x=0;
  __block CGFloat y=0;
  __block UIView* lastView=nil;
  __block int cm=self.columnMargin;
  [self.subviews enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
    RAREAPTableColumn* tc=(RAREAPTableColumn*) obj;
    if(!tc.hidden) {
      lastView=tc;
      RAREColumn* c=tc->columnDescription;
      NSInteger w=c->width_;
      tc.frame=CGRectMake(x,y,w+cm,size.height);
      x+=w+cm;
    }
  }];
  if(lastView) {
   CGRect frame=lastView.frame;
    CGFloat width=size.width-frame.origin.x;
    if(width>frame.size.width) {
      frame.size.width=width;
      lastView.frame=frame;
    }
  }
  if(self.horizontallyScollable) {
    UITableView* table=(UITableView*)tableView;
    CGSize csize=table.contentSize;
    CGFloat width=table.bounds.size.width;
    if(x<width) {
      csize.width=size.width;
    }
    else {
      csize.width=x;
    }
    table.contentSize=csize;
  }
}

- (void)drawRect:(CGRect)dirtyRect
{
  
  RAREView* view=self.sparView;
  RAREUIRectangle* rect=[self sparBounds];
  RAREAppleGraphics* g=[[RAREAppleGraphics alloc]initWithId:  (__bridge id)(UIGraphicsGetCurrentContext()) withRAREView:view];
  [view paintBackgroundWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
  //[view paintOverlayWithRAREAppleGraphics:g withRAREView:view withRAREUIRectangle:rect];
  [g dispose];
  
}
@end
