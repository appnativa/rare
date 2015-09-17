//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Link.java
//
//  Created by decoteaud on 9/15/15.
//

#include "IOSClass.h"
#include "IOSIntArray.h"
#include "IOSObjectArray.h"
#include "com/appnativa/rare/spot/Link.h"
#include "com/appnativa/spot/SPOTEnumerated.h"
#include "com/appnativa/spot/SPOTPrintableString.h"
#include "com/appnativa/spot/SPOTSequence.h"
#include "com/appnativa/spot/aSPOTElement.h"
#include "java/lang/Integer.h"

@implementation RARESPOTLink

- (id)init {
  return [self initRARESPOTLinkWithBoolean:YES];
}

- (id)initRARESPOTLinkWithBoolean:(BOOL)optional {
  if (self = [super initWithBoolean:optional withBoolean:NO]) {
    url_ = [[SPOTPrintableString alloc] init];
    target_ = [[RARESPOTLink_CTarget alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTLink_CTarget__new_window] withNSString:@"_new_window" withBoolean:YES];
    regionName_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    requestType_ = [[RARESPOTLink_CRequestType alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTLink_CRequestType_get] withNSString:@"get" withBoolean:NO];
    requestEncoding_ = [[RARESPOTLink_CRequestEncoding alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTLink_CRequestEncoding_http_form] withNSString:@"http_form" withBoolean:NO];
    attributes_ = [[SPOTPrintableString alloc] init];
    headers_ = [[SPOTPrintableString alloc] init];
    statusMessage_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
    [self spot_setElements];
  }
  return self;
}

- (id)initWithBoolean:(BOOL)optional {
  return [self initRARESPOTLinkWithBoolean:optional];
}

- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements {
  if (self = [super initWithBoolean:optional withBoolean:setElements]) {
    url_ = [[SPOTPrintableString alloc] init];
    target_ = [[RARESPOTLink_CTarget alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTLink_CTarget__new_window] withNSString:@"_new_window" withBoolean:YES];
    regionName_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:64 withBoolean:YES];
    requestType_ = [[RARESPOTLink_CRequestType alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTLink_CRequestType_get] withNSString:@"get" withBoolean:NO];
    requestEncoding_ = [[RARESPOTLink_CRequestEncoding alloc] initWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:[JavaLangInteger valueOfWithInt:RARESPOTLink_CRequestEncoding_http_form] withNSString:@"http_form" withBoolean:NO];
    attributes_ = [[SPOTPrintableString alloc] init];
    headers_ = [[SPOTPrintableString alloc] init];
    statusMessage_ = [[SPOTPrintableString alloc] initWithNSString:nil withInt:0 withInt:255 withBoolean:YES];
  }
  return self;
}

- (void)spot_setElements {
  self->elementsSizeHint_ += 8;
  [super spot_setElements];
  [self spot_addElementWithNSString:@"url" withISPOTElement:url_];
  [((SPOTPrintableString *) nil_chk(url_)) spot_defineAttributeWithNSString:@"mimeType" withNSString:nil];
  [url_ spot_defineAttributeWithNSString:@"inline" withNSString:nil];
  [url_ spot_defineAttributeWithNSString:@"unescape" withNSString:nil];
  [self spot_addElementWithNSString:@"target" withISPOTElement:target_];
  [((RARESPOTLink_CTarget *) nil_chk(target_)) spot_defineAttributeWithNSString:@"windowType" withNSString:@"frame"];
  [target_ spot_defineAttributeWithNSString:@"resizable" withNSString:@"true"];
  [target_ spot_defineAttributeWithNSString:@"movable" withNSString:@"true"];
  [target_ spot_defineAttributeWithNSString:@"top" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"left" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"title" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"status" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"bgColor" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"icon" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"width" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"height" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"border" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"onOpened" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"onWillClose" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"onDrop" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"onWillExpand" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"onWillCollapse" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"onHasCollapsed" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"onHasExpanded" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"onFocus" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"onBlur" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"contentPadding" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"modal" withNSString:nil];
  [target_ spot_defineAttributeWithNSString:@"timeout" withNSString:nil];
  [self spot_addElementWithNSString:@"regionName" withISPOTElement:regionName_];
  [self spot_addElementWithNSString:@"requestType" withISPOTElement:requestType_];
  [self spot_addElementWithNSString:@"requestEncoding" withISPOTElement:requestEncoding_];
  [self spot_addElementWithNSString:@"attributes" withISPOTElement:attributes_];
  [((SPOTPrintableString *) nil_chk(attributes_)) spot_defineAttributeWithNSString:@"mimeType" withNSString:nil];
  [self spot_addElementWithNSString:@"headers" withISPOTElement:headers_];
  [self spot_addElementWithNSString:@"statusMessage" withISPOTElement:statusMessage_];
}

- (void)copyAllFieldsTo:(RARESPOTLink *)other {
  [super copyAllFieldsTo:other];
  other->attributes_ = attributes_;
  other->headers_ = headers_;
  other->regionName_ = regionName_;
  other->requestEncoding_ = requestEncoding_;
  other->requestType_ = requestType_;
  other->statusMessage_ = statusMessage_;
  other->target_ = target_;
  other->url_ = url_;
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "initWithBoolean:withBoolean:", NULL, NULL, 0x4, NULL },
    { "spot_setElements", NULL, "V", 0x4, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "url_", NULL, 0x1, "LSPOTPrintableString" },
    { "target_", NULL, 0x1, "LRARESPOTLink_CTarget" },
    { "regionName_", NULL, 0x1, "LSPOTPrintableString" },
    { "requestType_", NULL, 0x1, "LRARESPOTLink_CRequestType" },
    { "requestEncoding_", NULL, 0x1, "LRARESPOTLink_CRequestEncoding" },
    { "attributes_", NULL, 0x1, "LSPOTPrintableString" },
    { "headers_", NULL, 0x1, "LSPOTPrintableString" },
    { "statusMessage_", NULL, 0x1, "LSPOTPrintableString" },
  };
  static J2ObjcClassInfo _RARESPOTLink = { "Link", "com.appnativa.rare.spot", NULL, 0x1, 2, methods, 8, fields, 0, NULL};
  return &_RARESPOTLink;
}

@end
@implementation RARESPOTLink_CTarget

static IOSIntArray * RARESPOTLink_CTarget__nchoices_;
static IOSObjectArray * RARESPOTLink_CTarget__schoices_;

+ (int)_self {
  return RARESPOTLink_CTarget__self;
}

+ (int)_new_window {
  return RARESPOTLink_CTarget__new_window;
}

+ (int)_new_popup {
  return RARESPOTLink_CTarget__new_popup;
}

+ (int)_workspace {
  return RARESPOTLink_CTarget__workspace;
}

+ (int)_parent {
  return RARESPOTLink_CTarget__parent;
}

+ (int)_toolbar {
  return RARESPOTLink_CTarget__toolbar;
}

+ (int)_menubar {
  return RARESPOTLink_CTarget__menubar;
}

+ (int)_blank {
  return RARESPOTLink_CTarget__blank;
}

+ (int)_null {
  return RARESPOTLink_CTarget__null;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTLink_CTarget__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTLink_CTarget__schoices_;
}

- (id)init {
  return [self initRARESPOTLink_CTargetWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTLink_CTarget__schoices_;
    _nChoices_ = RARESPOTLink_CTarget__nchoices_;
    [self spot_setAttributes];
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTLink_CTargetWithJavaLangInteger:(JavaLangInteger *)ival
                                     withNSString:(NSString *)sval
                              withJavaLangInteger:(JavaLangInteger *)idefaultval
                                     withNSString:(NSString *)sdefaultval
                                      withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTLink_CTarget__schoices_;
    _nChoices_ = RARESPOTLink_CTarget__nchoices_;
    [self spot_setAttributes];
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTLink_CTargetWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{_self(0), _new_window(1), _new_popup(2), _workspace(3), _parent(4), _toolbar(5), _menubar(6), _blank(7), _null(255) }";
}

- (void)spot_setAttributes {
  self->attributeSizeHint_ += 24;
  [self spot_defineAttributeWithNSString:@"windowType" withNSString:@"frame"];
  [self spot_defineAttributeWithNSString:@"resizable" withNSString:@"true"];
  [self spot_defineAttributeWithNSString:@"movable" withNSString:@"true"];
  [self spot_defineAttributeWithNSString:@"top" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"left" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"title" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"status" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"bgColor" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"icon" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"width" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"height" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"border" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onOpened" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onWillClose" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onDrop" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onWillExpand" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onWillCollapse" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onHasCollapsed" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onHasExpanded" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onFocus" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"onBlur" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"contentPadding" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"modal" withNSString:nil];
  [self spot_defineAttributeWithNSString:@"timeout" withNSString:nil];
}

+ (void)initialize {
  if (self == [RARESPOTLink_CTarget class]) {
    RARESPOTLink_CTarget__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3, 4, 5, 6, 7, 255 } count:9];
    RARESPOTLink_CTarget__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"_self", @"_new_window", @"_new_popup", @"_workspace", @"_parent", @"_toolbar", @"_menubar", @"_blank", @"_null" } count:9 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
    { "spot_setAttributes", NULL, "V", 0x2, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "_self_", NULL, 0x19, "I" },
    { "_new_window_", NULL, 0x19, "I" },
    { "_new_popup_", NULL, 0x19, "I" },
    { "_workspace_", NULL, 0x19, "I" },
    { "_parent_", NULL, 0x19, "I" },
    { "_toolbar_", NULL, 0x19, "I" },
    { "_menubar_", NULL, 0x19, "I" },
    { "_blank_", NULL, 0x19, "I" },
    { "_null_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTLink_CTarget = { "CTarget", "com.appnativa.rare.spot", "Link", 0x9, 2, methods, 11, fields, 0, NULL};
  return &_RARESPOTLink_CTarget;
}

@end
@implementation RARESPOTLink_CRequestType

static IOSIntArray * RARESPOTLink_CRequestType__nchoices_;
static IOSObjectArray * RARESPOTLink_CRequestType__schoices_;

+ (int)post {
  return RARESPOTLink_CRequestType_post;
}

+ (int)get {
  return RARESPOTLink_CRequestType_get;
}

+ (int)put {
  return RARESPOTLink_CRequestType_put;
}

+ (int)getDelete {
  return RARESPOTLink_CRequestType_delete;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTLink_CRequestType__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTLink_CRequestType__schoices_;
}

- (id)init {
  return [self initRARESPOTLink_CRequestTypeWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTLink_CRequestType__schoices_;
    _nChoices_ = RARESPOTLink_CRequestType__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTLink_CRequestTypeWithJavaLangInteger:(JavaLangInteger *)ival
                                          withNSString:(NSString *)sval
                                   withJavaLangInteger:(JavaLangInteger *)idefaultval
                                          withNSString:(NSString *)sdefaultval
                                           withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTLink_CRequestType__schoices_;
    _nChoices_ = RARESPOTLink_CRequestType__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTLink_CRequestTypeWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{post(0), get(1), put(2), delete(3) }";
}

+ (void)initialize {
  if (self == [RARESPOTLink_CRequestType class]) {
    RARESPOTLink_CRequestType__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1, 2, 3 } count:4];
    RARESPOTLink_CRequestType__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"post", @"get", @"put", @"delete" } count:4 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "post_", NULL, 0x19, "I" },
    { "get_", NULL, 0x19, "I" },
    { "put_", NULL, 0x19, "I" },
    { "delete__", "delete", 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTLink_CRequestType = { "CRequestType", "com.appnativa.rare.spot", "Link", 0x9, 1, methods, 6, fields, 0, NULL};
  return &_RARESPOTLink_CRequestType;
}

@end
@implementation RARESPOTLink_CRequestEncoding

static IOSIntArray * RARESPOTLink_CRequestEncoding__nchoices_;
static IOSObjectArray * RARESPOTLink_CRequestEncoding__schoices_;

+ (int)http_form {
  return RARESPOTLink_CRequestEncoding_http_form;
}

+ (int)json {
  return RARESPOTLink_CRequestEncoding_json;
}

+ (IOSIntArray *)_nchoices {
  return RARESPOTLink_CRequestEncoding__nchoices_;
}

+ (IOSObjectArray *)_schoices {
  return RARESPOTLink_CRequestEncoding__schoices_;
}

- (id)init {
  return [self initRARESPOTLink_CRequestEncodingWithJavaLangInteger:nil withNSString:nil withJavaLangInteger:nil withNSString:nil withBoolean:YES];
}

- (id)initWithInt:(int)val {
  if (self = [super init]) {
    _sChoices_ = RARESPOTLink_CRequestEncoding__schoices_;
    _nChoices_ = RARESPOTLink_CRequestEncoding__nchoices_;
    [self setValueWithInt:val];
  }
  return self;
}

- (id)initRARESPOTLink_CRequestEncodingWithJavaLangInteger:(JavaLangInteger *)ival
                                              withNSString:(NSString *)sval
                                       withJavaLangInteger:(JavaLangInteger *)idefaultval
                                              withNSString:(NSString *)sdefaultval
                                               withBoolean:(BOOL)optional {
  if (self = [super initWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional]) {
    _sChoices_ = RARESPOTLink_CRequestEncoding__schoices_;
    _nChoices_ = RARESPOTLink_CRequestEncoding__nchoices_;
  }
  return self;
}

- (id)initWithJavaLangInteger:(JavaLangInteger *)ival
                 withNSString:(NSString *)sval
          withJavaLangInteger:(JavaLangInteger *)idefaultval
                 withNSString:(NSString *)sdefaultval
                  withBoolean:(BOOL)optional {
  return [self initRARESPOTLink_CRequestEncodingWithJavaLangInteger:ival withNSString:sval withJavaLangInteger:idefaultval withNSString:sdefaultval withBoolean:optional];
}

- (NSString *)spot_getValidityRange {
  return @"{http_form(0), json(1) }";
}

+ (void)initialize {
  if (self == [RARESPOTLink_CRequestEncoding class]) {
    RARESPOTLink_CRequestEncoding__nchoices_ = [IOSIntArray arrayWithInts:(int[]){ 0, 1 } count:2];
    RARESPOTLink_CRequestEncoding__schoices_ = [IOSObjectArray arrayWithObjects:(id[]){ @"http_form", @"json" } count:2 type:[IOSClass classWithClass:[NSString class]]];
  }
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "spot_getValidityRange", NULL, "LNSString", 0x1, NULL },
  };
  static J2ObjcFieldInfo fields[] = {
    { "http_form_", NULL, 0x19, "I" },
    { "json_", NULL, 0x19, "I" },
    { "_nchoices_", NULL, 0x1a, "LIOSIntArray" },
    { "_schoices_", NULL, 0x1a, "LIOSObjectArray" },
  };
  static J2ObjcClassInfo _RARESPOTLink_CRequestEncoding = { "CRequestEncoding", "com.appnativa.rare.spot", "Link", 0x9, 1, methods, 4, fields, 0, NULL};
  return &_RARESPOTLink_CRequestEncoding;
}

@end
