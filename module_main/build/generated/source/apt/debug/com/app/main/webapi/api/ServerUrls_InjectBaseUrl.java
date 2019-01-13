//自动生成的注解类，勿动!!!
package com.app.main.webapi.api;

import com.tb.baselib.constant.BaseConstant;
import com.tb.annotation.api.InjectBaseUrl;
import com.tb.annotation.annotation.NoProguard;

@NoProguard
public class ServerUrls_InjectBaseUrl implements InjectBaseUrl<com.app.main.webapi.api.ServerUrls> {
@Override
 public void inject(com.app.main.webapi.api.ServerUrls host, Object source ) {
BaseConstant.BASE_API_URL="http://www.rohun.top:8080/smart/";
  }

}
