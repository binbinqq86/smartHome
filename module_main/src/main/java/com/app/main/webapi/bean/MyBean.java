package com.app.main.webapi.bean;

/**
 * @auther tb
 * @time 2018/2/11 下午3:39
 * @desc
 */
public class MyBean extends MainBaseBean {
    public CommonBean commonBean;
    public HeaderBean headerBean;
    
    public static class CommonBean extends MainBaseBean {
        public CommonBean(boolean hasBottomLine, boolean hasSeparate, int resId, String option) {
            this.hasBottomLine = hasBottomLine;
            this.hasSeparate = hasSeparate;
            this.resId = resId;
            this.option = option;
        }
    
        public boolean hasBottomLine;
        public boolean hasSeparate;
        public int resId;
        public String option;
    }
    
    public static class HeaderBean extends MainBaseBean {
        public String headImgUrl;
        public String phone;
        public String realName;
        public String nickName;
        public String qrCode;
    }
}
