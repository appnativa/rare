//
//  RAREUITableViewCell.m
//  RareIOS
//
//  Created by Don DeCoteau on 5/15/13.
//  Copyright (c) 2013 SparseWare. All rights reserved.
//

#import <com/appnativa/rare/ui/table/TableView.h>
#import <com/appnativa/rare/ui/table/TableHeader.h>
#import "RAREUITableViewCell.h"
#import "RARECAGradientLayer.h"
#import "RAREImageWrapper.h"
#import "RAREUITableContentView.h"
#import "com/appnativa/rare/ui/UIRectangle.h"
#import "APView+Component.h"
#import "AppleHelper.h"
#import "com/appnativa/rare/platform/apple/ui/util/AppleGraphics.h"
#import "com/appnativa/rare/ui/iPlatformIcon.h"
static NSLineBreakMode defaultLinebreakMode=NSLineBreakByWordWrapping;
@implementation RAREUITableViewCell {
@public
  UIImageView* selectionView;
  UIView* editingView;
  BOOL centerEditingView;
  CGFloat contentViewX;
  BOOL hasRAREBackgroundView;
  
}

+ (Class)layerClass
{
  return [RARECAGradientLayer class];
}
+(NSLineBreakMode) getDefaultLineBreakMode {
  return defaultLinebreakMode;
}
+(void) setDefaultLineBreakMode:(NSLineBreakMode) mode {
  defaultLinebreakMode=mode;
}

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
  self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
  if (self) {
    [self.textLabel setLineBreakMode:defaultLinebreakMode];
    verticalAlighment = RARERenderableDataItem_VerticalAlign_AUTO;
    iconGap_ = 4;
    iconPosition = RARERenderableDataItem_IconPosition_AUTO;
    insets_ = UIEdgeInsetsMake(2, 2, 2, 2);
    self.backgroundColor = [UIColor whiteColor];
    self.backgroundView=nil;
  }
  return self;
}

-(BOOL) isRowEditorOrChild:(UIView*) view {
  if(editingView) {
    return editingView==view || [view isDescendantOfView:editingView];
  }
  return NO;
}
-(void) sparPrepareForReuse {
  CALayer* layer=self.layer;
  if([layer isKindOfClass:[RARECAGradientLayer class]]) {
    [((RARECAGradientLayer*)layer) sparResetLayer];
  }
  [self.textLabel setLineBreakMode:defaultLinebreakMode];
  contentViewX=0;
  verticalAlighment = RARERenderableDataItem_VerticalAlign_AUTO;
  iconGap_ = 4;
  iconPosition = RARERenderableDataItem_IconPosition_AUTO;
  insets_ = UIEdgeInsetsMake(2, 2, 2, 2);
  self.backgroundColor = [UIColor clearColor];
  self.accessoryView=nil;
  self.editingAccessoryView=nil;
  if(tableContent) {
    [tableContent sparPrepareForReuse];
  }
}

-(void)sparDispose {
  [super sparDispose];
  tableContent=nil;
  selectionView=nil;
  editingView=nil;
  listContent=nil;
  icon_=nil;
}
-(void) setListContent: (UIView*) view {
  listContent=view;
  [self.contentView addSubview:view];
  self.textLabel.text=@"";
  self.imageView.image=nil;
}

-(UIView*) getPressedContentViewAtIndex: (int) index {
  return (UIView*)[[tableContent subviews] objectAtIndex:index];
}

- (RAREUITableContentView *)getTableContentHolder {
  if (!tableContent) {
    tableContent = [[RAREUITableContentView alloc] init];
    [self.contentView addSubview:tableContent];
    insets_ = UIEdgeInsetsZero;
  }
  else {
    [tableContent removeAllSubViews];
  }
  
  return tableContent;
}

- (BOOL)isPressed {
  return self.highlighted;
}

- (void)setNeedsDisplay {
  [super setNeedsDisplay];
  if (tableContent) {
    [tableContent setNeedsDisplay];
  }
  if(listContent) {
    [listContent setNeedsDisplay];
  }
  if(self.backgroundView) {
    [self.backgroundView setNeedsDisplay];
  }
}
- (void)setIcon:(id <RAREiPlatformIcon>)icon {
  icon_ = icon;
  if (icon) {
    self.imageView.image = [RAREImageWrapper getImageFromIcon:icon forView:self.sparView];
  }
  else {
    self.imageView.image = nil;
  }
}

- (void)layoutSubviews {
  [super layoutSubviews];
  CGRect frame;
  frame=self.contentView.bounds;
  frame.origin.x=contentViewX;
  if (tableContent) {
    tableContent.frame = frame;
    [tableContent setNeedsDisplay];
    RARETableView *lb = (RARETableView *)listView;
    if (lb->showVertivalGridLines_ && lb->dividerLineColor_ && lb->dividerStroke_) {
      [tableContent.subviews enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
        UIView *v = (UIView *) obj;
        v.hidden=YES;
      }];
    }
    if(lb->showDivider_) {
      frame.size.height--;
    }
    [lb layoutWithId:tableContent withRARERenderableDataItem:(RARERenderableDataItem *) rowItem withInt:frame.size.width withInt:frame.size.height];
  }
  else if(listContent) {
    if(selectionView && selectionView.image) {
      CGRect sframe=selectionView.frame;
      sframe.origin.x=frame.origin.x;
      sframe.origin.y=(frame.size.height-sframe.size.height)/2+frame.origin.y;
      frame.origin.x+=sframe.size.width+4;
      frame.size.width-=sframe.size.width+4;
      selectionView.frame=sframe;
    }
    else {
      editingView=nil;
    }
    if(editingView) {
      CGRect sframe=selectionView.frame;
      if(!centerEditingView) {
        sframe.size.height=frame.size.height-1;
      }
      frame.size.width-=sframe.size.width+4;
      sframe.origin.x=frame.origin.x+frame.size.width+4;
      sframe.origin.y=(frame.size.height-sframe.size.height)/2+frame.origin.y;
    }
    listContent.frame=frame;
  }
  else {
    frame=UIEdgeInsetsInsetRect(frame, insets_);
    [self layoutSubviews:frame];
  }
}

- (void)setInsetsWithTop:(int)top right:(int)right bottom:(int)bottom left:(int)left {
  insets_ = UIEdgeInsetsMake(top, left, bottom, right);
}

- (void)setRotation:(int)rotation {
  rotation_ = (rotation * M_PI) / 180;
}
- (void)drawRect:(CGRect)dirtyRect {
  [super drawRect:dirtyRect];
  if(!hasRAREBackgroundView) {
    CGContextRef ctx = UIGraphicsGetCurrentContext();
    if (rotation_) {
      CGContextConcatCTM(ctx, CGAffineTransformMakeRotation(rotation_));
    }
    RAREListView *lb = listView;
    if (lb) {
      RAREaTableBasedView_RowView* rv=(RAREaTableBasedView_RowView*)self.sparView;
      RAREAppleGraphics *g = [[RAREAppleGraphics alloc] initWithId:(__bridge id) ctx withRAREView:rv];
      RAREUIRectangle *rect = [self sparBounds];
      [lb paintRowWithRAREaTableBasedView_RowView:rv withRAREAppleGraphics:g withRARERenderableDataItem:rowItem withRAREUIRectangle:rect withRAREiTreeItem:treeItem];
      [g dispose];
    }
  }
}

- (CGSize)preferredSize {
  return [self sizeThatFits:self.frame.size];
}

- (CGFloat)getPreferredHeight:(CGFloat)width {
  CGFloat height = 0;
  if (tableContent) {
    height = [tableContent getPreferredHeight:width];
  }
  else if(listContent) {
    height= [listContent getPreferredHeight:width];
  }
  if (icon_) {
    BOOL rtl = [AppleHelper isLTRText];
    RARERenderableDataItem_IconPosition ip = iconPosition;
    if (ip == RARERenderableDataItem_IconPosition_TRAILING) {
      ip = rtl ? RARERenderableDataItem_IconPosition_LEFT : RARERenderableDataItem_IconPosition_RIGHT;
    }
    else if (ip == RARERenderableDataItem_IconPosition_LEADING || !ip) {
      ip = rtl ? RARERenderableDataItem_IconPosition_RIGHT : RARERenderableDataItem_IconPosition_LEFT;
    }
    switch (ip) {
      case RARERenderableDataItem_IconPosition_TOP_CENTER :
      case RARERenderableDataItem_IconPosition_TOP_LEFT :
      case RARERenderableDataItem_IconPosition_TOP_RIGHT :
      case RARERenderableDataItem_IconPosition_BOTTOM_CENTER :
      case RARERenderableDataItem_IconPosition_BOTTOM_LEFT :
      case RARERenderableDataItem_IconPosition_BOTTOM_RIGHT : {
        CGSize isize = [self.imageView sizeThatFits:self.imageView.frame.size];
        height += iconGap_ + isize.height;
      }
        break;
      default: {
        CGSize isize = [self.imageView sizeThatFits:self.imageView.frame.size];
        height = MAX(height, isize.height);
      }
        break;
    }
  }
  return height;
}

- (CGSize)sizeThatFits:(CGSize)size {
  __block CGFloat height = 0;
  __block CGFloat width = 0;
  if (tableContent) {
    CGSize s = [tableContent sizeThatFits:size];
    width += s.width;
    height = MAX(height, s.height);
    [tableContent.subviews enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
      UIView *v = (UIView *) obj;
      CGSize s = [v sizeThatFits:size];
      width += s.width;
      height = MAX(height, s.height);
    }];
    [self.subviews enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
      UIView *v = (UIView *) obj;
      if(v!=tableContent.superview) {
        CGSize s = [v sizeThatFits:size];
        width += s.width;
        height = MAX(height, s.height);
      }
    }];
    return [self adjustSize:CGSizeMake(width, height)];
  }
  else {
    return [self adjustSize:[super sizeThatFits:size]];
  }
}

- (CGSize)adjustSize:(CGSize)size {
  size.width += insets_.left + insets_.right;
  size.height += insets_.top + insets_.bottom;
  if (icon_) {
    BOOL rtl = [AppleHelper isLTRText];
    RARERenderableDataItem_IconPosition ip = iconPosition;
    if (ip == RARERenderableDataItem_IconPosition_TRAILING) {
      ip = rtl ? RARERenderableDataItem_IconPosition_LEFT : RARERenderableDataItem_IconPosition_RIGHT;
    }
    else if (ip == RARERenderableDataItem_IconPosition_LEADING || !ip) {
      ip = rtl ? RARERenderableDataItem_IconPosition_RIGHT : RARERenderableDataItem_IconPosition_LEFT;
    }
    switch (ip) {
      case RARERenderableDataItem_IconPosition_TOP_CENTER :
      case RARERenderableDataItem_IconPosition_TOP_LEFT :
      case RARERenderableDataItem_IconPosition_TOP_RIGHT :
      case RARERenderableDataItem_IconPosition_BOTTOM_CENTER :
      case RARERenderableDataItem_IconPosition_BOTTOM_LEFT :
      case RARERenderableDataItem_IconPosition_BOTTOM_RIGHT : {
        CGSize isize = [self.imageView sizeThatFits:self.imageView.frame.size];
        size.height += iconGap_ + isize.height;
        size.width -= isize.height;
      }
        break;
      default: {
        size.width += iconGap_;
        CGSize isize = [self.imageView sizeThatFits:self.imageView.frame.size];
        size.height = MAX(size.height, isize.height);
      }
        break;
    }
  }
  return size;
}

- (void)setIconGap:(int)gap {
  iconGap_ = gap;
}

- (void)setIconPosition:(RARERenderableDataItem_IconPosition)position {
  iconPosition = position;
}

- (void)setTextVerticalAlignment:(RARERenderableDataItem_VerticalAlign)alignment {
  verticalAlighment = alignment;
}
-(void) setEditingImage:(UIImage*) img {
  if(img) {
    if(!selectionView) {
      selectionView=[UIImageView new];
      [self.contentView addSubview:selectionView];
    }
    selectionView.image=img;
    [selectionView sizeToFit];
  }
  else if(selectionView) {
    [selectionView removeFromSuperview];
    selectionView=nil;
  }
}
-(void) showRowEditingView: (UIView*) view animate: (BOOL) animate centerVertically: (BOOL) center {
  editingView=view;
  CGRect eframe=editingView.frame;
  CGRect frame=self.contentView.frame;
  if(center) {
    eframe.origin.y=(frame.size.height-eframe.size.height)/2;
  }
  else {
    eframe.origin.y=0;
    eframe.size.height=frame.size.height-1;
  }
  eframe.origin.x=frame.size.width;
  [view removeFromSuperview];
  [self.contentView addSubview:view];
  [view setNeedsDisplay];
  [view setNeedsLayout];
  view.frame=eframe;
  contentViewX=-eframe.size.width;
  frame.origin.x=contentViewX;
  if(animate) {
    [UIView animateWithDuration:0.2
                     animations:^{self.contentView.frame = frame;}];
  }
  else {
    [self setNeedsLayout];
    [self setNeedsDisplay];
    self.contentView.frame=frame;
  }
}
-(void) hideRowEditingView: (BOOL) animate {
  if(!editingView) return;
  if(animate) {
    UIView* ev=editingView;
    CGRect frame=self.contentView.frame;
    frame.origin.x=0;
    [UIView animateWithDuration:0.2
                     animations:^{self.contentView.frame = frame;}
                     completion:^(BOOL finished){
                       [ev removeFromSuperview];
                       [self setNeedsLayout];
                       [self setNeedsDisplay];
                       editingView=nil;
                       contentViewX=0;
                     }];
  }
  else {
    [editingView removeFromSuperview];
    [self setNeedsLayout];
    [self setNeedsDisplay];
    editingView=nil;
    contentViewX=0;
  }
}
- (void)setTextHorizontalAlignment:(RARERenderableDataItem_HorizontalAlign)alignment {
  BOOL rtl = [AppleHelper isLTRText];
  RARERenderableDataItem_HorizontalAlign a = alignment;
  if (a == RARERenderableDataItem_HorizontalAlign_TRAILING) {
    a = rtl ? RARERenderableDataItem_HorizontalAlign_LEFT : RARERenderableDataItem_HorizontalAlign_RIGHT;
  }
  else if (a == RARERenderableDataItem_HorizontalAlign_LEADING) {
    a = rtl ? RARERenderableDataItem_HorizontalAlign_RIGHT : RARERenderableDataItem_HorizontalAlign_LEFT;
    
  }
  switch (a) {
    case RARERenderableDataItem_HorizontalAlign_AUTO:
      self.textLabel.textAlignment = NSTextAlignmentLeft;
      break;
    case RARERenderableDataItem_HorizontalAlign_RIGHT:
      self.textLabel.textAlignment = NSTextAlignmentRight;
      break;
    case RARERenderableDataItem_HorizontalAlign_CENTER:
      self.textLabel.textAlignment = NSTextAlignmentCenter;
      break;
    default:
      self.textLabel.textAlignment = NSTextAlignmentLeft;
      break;
  }
}
-(UIView *)hitTest:(CGPoint)point withEvent:(UIEvent *)event {
  if(editingView) {
    CGPoint pp=[editingView convertPoint:point fromView:self];
    UIView* v=[editingView hitTest:pp withEvent:event];
    if(v) return v;
  }
  UIView* v=[super hitTest:point withEvent:event];
  return v;
}
-(void)dealloc {
  
}
- (void)layoutSubviews:(CGRect)contentRect {
  if (!icon_) {
    CGSize tsize = [self.textLabel sizeThatFits:contentRect.size];
    if (tsize.height < contentRect.size.height) {
      switch (verticalAlighment) {
        case RARERenderableDataItem_VerticalAlign_TOP:
          contentRect.size.height = tsize.height;
          break;
        case RARERenderableDataItem_VerticalAlign_BOTTOM:
          contentRect.origin.y += (contentRect.size.height - tsize.height);
          contentRect.size.height = tsize.height;
          break;
        default: {
          break;
        }
      }
    }
    self.textLabel.frame = contentRect;
    return;
  }
  CGRect imageRect = {contentRect.origin, [self.imageView sizeThatFits:contentRect.size]};
  CGRect textRect = {contentRect.origin, contentRect.size};
  
  BOOL rtl = [AppleHelper isLTRText];
  RARERenderableDataItem_IconPosition ip = iconPosition;
  if (ip == RARERenderableDataItem_IconPosition_TRAILING) {
    ip = rtl ? RARERenderableDataItem_IconPosition_LEFT : RARERenderableDataItem_IconPosition_RIGHT;
  }
  else if (ip == RARERenderableDataItem_IconPosition_LEADING || !ip) {
    ip = rtl ? RARERenderableDataItem_IconPosition_RIGHT : RARERenderableDataItem_IconPosition_LEFT;
  }
  switch (ip) {
    case RARERenderableDataItem_IconPosition_TOP_CENTER :
    case RARERenderableDataItem_IconPosition_TOP_LEFT :
    case RARERenderableDataItem_IconPosition_TOP_RIGHT :
    case RARERenderableDataItem_IconPosition_BOTTOM_CENTER :
    case RARERenderableDataItem_IconPosition_BOTTOM_LEFT :
    case RARERenderableDataItem_IconPosition_BOTTOM_RIGHT :
      textRect.size.height -= (imageRect.size.height + iconGap_);
      break;
    default :
      textRect.size.width -= (imageRect.size.width + iconGap_);
      break;
  }
  CGSize tsize = [self.textLabel sizeThatFits:textRect.size];
  switch (ip) {
    case RARERenderableDataItem_IconPosition_TOP_CENTER :
    case RARERenderableDataItem_IconPosition_TOP_LEFT :
    case RARERenderableDataItem_IconPosition_TOP_RIGHT :
      switch (verticalAlighment) {
        case RARERenderableDataItem_VerticalAlign_TOP:
          textRect.origin.y = contentRect.origin.y + imageRect.size.height + iconGap_;
          textRect.size.height = tsize.height;
          break;
        case RARERenderableDataItem_VerticalAlign_BOTTOM:
          textRect.origin.y = contentRect.origin.y + contentRect.size.height - textRect.size.height;
          textRect.size.height = tsize.height;
          break;
        default: {
          CGFloat dy = MAX(0, (contentRect.size.height - imageRect.size.height - textRect.size.height - iconGap_) / 2);
          textRect.origin.y = contentRect.origin.y + imageRect.size.height + dy + iconGap_;
          break;
        }
      }
      textRect.size.width = contentRect.size.width;
      break;
    case RARERenderableDataItem_IconPosition_BOTTOM_CENTER :
    case RARERenderableDataItem_IconPosition_BOTTOM_LEFT :
    case RARERenderableDataItem_IconPosition_BOTTOM_RIGHT :
      switch (verticalAlighment) {
        case RARERenderableDataItem_VerticalAlign_TOP:
          textRect.origin.y = contentRect.origin.y;
          textRect.size.height = tsize.height;
          break;
        case RARERenderableDataItem_VerticalAlign_BOTTOM:
          textRect.origin.y = contentRect.origin.y + contentRect.size.height - imageRect.size.height - iconGap_ - tsize.height;
          textRect.size.height = tsize.height;
          break;
        default: {
          CGFloat dy = MAX(0, (contentRect.size.height - imageRect.size.height - textRect.size.height - iconGap_) / 2);
          textRect.origin.y = contentRect.origin.y + dy;
          break;
        }
      }
      textRect.size.width = contentRect.size.width;
      break;
    case RARERenderableDataItem_IconPosition_RIGHT :
    case RARERenderableDataItem_IconPosition_RIGHT_JUSTIFIED :
      textRect.origin.x = contentRect.origin.x;
      textRect.size.height = contentRect.size.height;
      textRect.size.width=MIN(tsize.width,contentRect.size.width-(imageRect.size.width+iconGap_));
      break;
      
    default :
      textRect.origin.x = contentRect.origin.x + imageRect.size.width + iconGap_;
      textRect.size.height = contentRect.size.height;
      textRect.size.width=MIN(tsize.width,contentRect.size.width-(imageRect.size.width+iconGap_));
      break;
  }
  imageRect.origin = contentRect.origin;
  switch (ip) {
    case RARERenderableDataItem_IconPosition_TOP_CENTER :
      imageRect.origin.x += (contentRect.size.width - imageRect.size.width) / 2;
      break;
    case RARERenderableDataItem_IconPosition_TOP_LEFT :
      break;
    case RARERenderableDataItem_IconPosition_TOP_RIGHT :
      imageRect.origin.x += (contentRect.size.width - imageRect.size.width);
      break;
    case RARERenderableDataItem_IconPosition_BOTTOM_CENTER :
      imageRect.origin.x += (contentRect.size.width - imageRect.size.width) / 2;
      imageRect.origin.y += (contentRect.size.height - imageRect.size.height);
      break;
    case RARERenderableDataItem_IconPosition_BOTTOM_LEFT :
      imageRect.origin.y += (contentRect.size.height - imageRect.size.height);
      break;
    case RARERenderableDataItem_IconPosition_BOTTOM_RIGHT :
      imageRect.origin.x += (contentRect.size.width - imageRect.size.width);
      imageRect.origin.y += (contentRect.size.height - imageRect.size.height);
      break;
    case RARERenderableDataItem_IconPosition_RIGHT :
      imageRect.origin.x = textRect.origin.x + textRect.size.width + iconGap_;
      imageRect.origin.y += (contentRect.size.height - imageRect.size.height)/2;
      break;
    case RARERenderableDataItem_IconPosition_RIGHT_JUSTIFIED :
      imageRect.origin.x += (contentRect.size.width - imageRect.size.width);
      imageRect.origin.y += (contentRect.size.height - imageRect.size.height)/2;
      break;
      
    default :
      imageRect.origin.y += (contentRect.size.height - imageRect.size.height)/2;
      break;
  }
  self.textLabel.frame = textRect;
  self.imageView.frame = imageRect;
}
@end
