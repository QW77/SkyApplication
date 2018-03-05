package com.sky.shop.bean;

import android.text.TextUtils;

import com.sky.app.library.utils.SPUtil;

/**
 * 用户信息
 * @author wjx
 *
 */
public class UserBean extends BaseUser{
    private static UserBean userBean = null;
    private String pic_url;//头像
    private String nick_name;//昵称
    private String pwd;//密码
    private String mobile;//手机号
    private int gender;//性别 1男 2女 0未填写
    private String birthday;//生日
    private String real_name;//真实姓名
    private int state;//状态 1有效，0无效 ,2黑名单（冻结）
    private String weixin;//微信
    private String qq;//qq
    private String address;//家乡
    private String user_type;//用户类型 1：普通用户 2:商户
    private String driver_license;
    private String idcard_front;
    private String idcard_back;
    private String seller_type;//店铺 个人主页 个人名片
    private String area_name;//区域
    private String decorative_name;//装饰城
    private String manufacturer_type_name;//厂家类型
    private String email;
    private String worktime;
    private String main_business_desc;
    private String area_id;
    private String decorative_id;
    private String manufacturer_type_id;

    public static UserBean getInstance(){
        if (null == userBean){
            synchronized (UserBean.class){
                if (null == userBean){
                    userBean = new UserBean();
                }
            }
        }
        return userBean;
    }
    /**
     * 设置缓存
     */
    public void setUserCache(UserBean info) {
        SPUtil.put(AppKey.XmlName.SHOP_CACHE_USER_INFO, "uid", info.getUser_id());
        SPUtil.put(AppKey.XmlName.SHOP_CACHE_USER_INFO, "password", info.getPwd());
        SPUtil.put(AppKey.XmlName.SHOP_CACHE_USER_INFO, "mobile", info.getMobile());
        SPUtil.put(AppKey.XmlName.SHOP_CACHE_USER_INFO, "nick_name", info.getNick_name());
        SPUtil.put(AppKey.XmlName.SHOP_CACHE_USER_INFO, "seller_type", info.getSeller_type());
        SPUtil.put(AppKey.XmlName.SHOP_CACHE_USER_INFO, "pic_url", info.getPic_url());
    }

    /**
     * 获取缓存数据
     *
     * @return 用户
     */
    public UserBean getUserCache() {
        UserBean info = getInstance();
        info.setUser_id(SPUtil.get(AppKey.XmlName.SHOP_CACHE_USER_INFO, "uid", ""));
        info.setPwd(SPUtil.get(AppKey.XmlName.SHOP_CACHE_USER_INFO, "password", ""));
        info.setMobile(SPUtil.get(AppKey.XmlName.SHOP_CACHE_USER_INFO, "mobile", ""));
        info.setNick_name(SPUtil.get(AppKey.XmlName.SHOP_CACHE_USER_INFO, "nick_name", ""));
        info.setSeller_type(SPUtil.get(AppKey.XmlName.SHOP_CACHE_USER_INFO, "seller_type", ""));
        info.setPic_url(SPUtil.get(AppKey.XmlName.SHOP_CACHE_USER_INFO, "pic_url", ""));
        return info;
    }

    /**
     * 获取缓存的uid
     * @return
     */
    public String getCacheUid(){
        return SPUtil.get(AppKey.XmlName.SHOP_CACHE_USER_INFO, "uid", "");
    }

    /**
     * 设置验证码
     * @return
     */
    public void setCode(String code, String mobile){
        SPUtil.put(AppKey.XmlName.SHOP_CACHE_USER_INFO, "code", code);
        SPUtil.put(AppKey.XmlName.SHOP_CACHE_USER_INFO, "mobile_code", mobile);
    }

    /**
     * 获取验证码
     * @return
     */
    public String getCode(){
        return SPUtil.get(AppKey.XmlName.SHOP_CACHE_USER_INFO, "code", "");
    }

    /**
     * 获取验证码绑定的手机号
     * @return
     */
    public String getCode_Mobile(){
        return SPUtil.get(AppKey.XmlName.SHOP_CACHE_USER_INFO, "mobile_code", "");
    }

    /**
     * 清理缓存验证码
     */
    public void clearCode(){
        SPUtil.clear(AppKey.XmlName.SHOP_CACHE_USER_INFO, "code");
        SPUtil.clear(AppKey.XmlName.SHOP_CACHE_USER_INFO, "mobile_code");
    }

    /**
     * 检查用户是否登录
     */
    public boolean checkUserLogin() {
        UserBean info = getUserCache();
        return null != info && !TextUtils.isEmpty(info.getUser_id());
    }

    /**
     * 清空用户缓存
     */
    public void cleanUserCache() {
        SPUtil.clear(AppKey.XmlName.SHOP_CACHE_USER_INFO);
    }

    public UserBean getUserInfo() {
        return userBean;
    }

    public void setUserInfo(UserBean userBean) {
        UserBean.userBean = userBean;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getDriver_license() {
        return driver_license;
    }

    public void setDriver_license(String driver_license) {
        this.driver_license = driver_license;
    }

    public String getIdcard_front() {
        return idcard_front;
    }

    public void setIdcard_front(String idcard_front) {
        this.idcard_front = idcard_front;
    }

    public String getIdcard_back() {
        return idcard_back;
    }

    public void setIdcard_back(String idcard_back) {
        this.idcard_back = idcard_back;
    }

    public String getSeller_type() {
        return seller_type;
    }

    public void setSeller_type(String seller_type) {
        this.seller_type = seller_type;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getDecorative_name() {
        return decorative_name;
    }

    public void setDecorative_name(String decorative_name) {
        this.decorative_name = decorative_name;
    }

    public String getManufacturer_type_name() {
        return manufacturer_type_name;
    }

    public void setManufacturer_type_name(String manufacturer_type_name) {
        this.manufacturer_type_name = manufacturer_type_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public String getMain_business_desc() {
        return main_business_desc;
    }

    public void setMain_business_desc(String main_business_desc) {
        this.main_business_desc = main_business_desc;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getDecorative_id() {
        return decorative_id;
    }

    public void setDecorative_id(String decorative_id) {
        this.decorative_id = decorative_id;
    }

    public String getManufacturer_type_id() {
        return manufacturer_type_id;
    }

    public void setManufacturer_type_id(String manufacturer_type_id) {
        this.manufacturer_type_id = manufacturer_type_id;
    }
}