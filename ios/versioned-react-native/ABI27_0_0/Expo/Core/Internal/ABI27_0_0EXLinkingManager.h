// Copyright 2015-present 650 Industries. All rights reserved.

#import <UIKit/UIKit.h>

#import "ABI27_0_0EXScopedEventEmitter.h"

@protocol ABI27_0_0EXLinkingManagerScopedModuleDelegate

- (void)linkingModule:(id)linkingModule didOpenUrl:(NSString *)url;

/**
 *  @return whether the url should be routed internally as an expo url,
 *          vs. fall back to the iOS system handler.
 */
- (BOOL)linkingModule:(id)linkingModule shouldOpenExpoUrl:(NSURL *)url;

@end

@interface ABI27_0_0EXLinkingManager : ABI27_0_0EXScopedEventEmitter

- (void)dispatchOpenUrlEvent: (NSURL *)url;

@end
