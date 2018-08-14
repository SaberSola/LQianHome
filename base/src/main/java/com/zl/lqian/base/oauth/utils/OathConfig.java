package com.zl.lqian.base.oauth.utils;

import com.zl.lqian.base.oauth.APIConfig;















public class OathConfig
{
  public static String getValue(String key)
  {
    return APIConfig.getInstance().getValue(key);
  }
}
