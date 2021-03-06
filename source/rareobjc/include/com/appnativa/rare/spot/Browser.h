//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Code/Dev/appNativa/source/rareobjc/../rare/core/com/appnativa/rare/spot/Browser.java
//
//  Created by decoteaud on 3/11/16.
//

#ifndef _RARESPOTBrowser_H_
#define _RARESPOTBrowser_H_

@class SPOTBoolean;

#import "JreEmulation.h"
#include "com/appnativa/rare/spot/Viewer.h"

@interface RARESPOTBrowser : RARESPOTViewer {
 @public
  SPOTBoolean *updateStatusBar_;
  SPOTBoolean *showLocationBar_;
  SPOTBoolean *showToolBar_;
  SPOTBoolean *showStatusBar_;
}

- (id)init;
- (id)initWithBoolean:(BOOL)optional;
- (id)initWithBoolean:(BOOL)optional
          withBoolean:(BOOL)setElements;
- (void)spot_setElements;
- (void)copyAllFieldsTo:(RARESPOTBrowser *)other;
@end

J2OBJC_FIELD_SETTER(RARESPOTBrowser, updateStatusBar_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTBrowser, showLocationBar_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTBrowser, showToolBar_, SPOTBoolean *)
J2OBJC_FIELD_SETTER(RARESPOTBrowser, showStatusBar_, SPOTBoolean *)

typedef RARESPOTBrowser ComAppnativaRareSpotBrowser;

#endif // _RARESPOTBrowser_H_
