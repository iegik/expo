#import "ABI27_0_0EXNativeAdView.h"

#import <FBAudienceNetwork/FBAudienceNetwork.h>
#import <ReactABI27_0_0/ABI27_0_0RCTUtils.h>

@interface ABI27_0_0EXNativeAdView ()

@end

@implementation ABI27_0_0EXNativeAdView

- (void)setNativeAd:(FBNativeAd *)nativeAd
{
  _nativeAd = nativeAd;

  _onAdLoaded(@{
    @"title": _nativeAd.title,
    @"subtitle": _nativeAd.subtitle,
    @"description": _nativeAd.body,
    @"socialContext": _nativeAd.socialContext,
    @"callToActionText": _nativeAd.callToAction,
    @"coverImage": _nativeAd.coverImage ? [_nativeAd.coverImage.url absoluteString] : [NSNull null],
    @"icon": _nativeAd.icon ? [_nativeAd.icon.url absoluteString] : [NSNull null],
  });

  [_nativeAd registerViewForInteraction:self withViewController:ABI27_0_0RCTKeyWindow().rootViewController];
}

@end
