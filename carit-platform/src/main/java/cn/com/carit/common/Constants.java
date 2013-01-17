package cn.com.carit.common;


public class Constants {
	public static final String CHARACTER_ENCODING_UTF8="utf-8";
	public static final int PAGE_SIZE=10;
	public static final int PAGE_START=1;
	public static final int STATUS_VALID=1;
	public static final int STATUS_LOCKED=2;
	public static final int STATUS_INVALID=0;
	public static final String DATE_TIME_FORMATTER="yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMATTER="yyyy-MM-dd";
	public static final String DATE_SHORT_FORMATTER="yyyy-M-d";
	public static final String DATE_MONTH_DATE="M-d";
	public static final String ADMIN_USER="adminUser";
	public static final String PORTAL_USER="portalUser";
	public static final String PASSWORD_ERROR_COUNT="passwordErrorCount";
	/**密码最多错误5次限制登录*/
	public static final int MAX_PWD_ERROR_COUNT=5;
	public static final String ANSWER_CODE="answerCode";
	
	/** 首页新闻、产品限制条数  */
	public static final int INDEX_SHOW_LIMIT=4;
	
	public static final int MAX_BANNER_AD_COUNT=5;
	
	public static final String BASE_PATH_PHOTOS="/photos/";
	
	public static final String DEAFULD_LANGUAGE="cn";
	
	//ERROR
	public static final String PASSWORD_ERROR = "PASSWORD_ERROR";
	public static final String ACCOUNT_LOCKED = "ACCOUNT_LOCKED";
	public static final String EMAIL_REGISTERED = "EMAIL_REGISTERED";
	public static final String NICKNAME_REGISTERED = "NICKNAME_REGISTERED";
	public static final String HAVENT_DOWNLOAD_APPLICTION = "HAVENT_DOWNLOAD_APPLICTION";
	public static final String DEVICE_ID_IS_EMPTY="DEVICE_ID_IS_EMPTY";
	public static final String MESSAGE_ACCOUNT_ID_NOT_MATCH="MESSAGE_ACCOUNT_ID_NOT_MATCH";
	public static final String NO_THIS_ACCOUNT="NO_THIS_ACCOUNT";
	public static final String NO_THIS_EQUIPMENT="NO_THIS_EQUIPMENT";
	public static final String EQUIPMENT_BINDING_ACCOUNT_TO_UPPER_LIMIT="EQUIPMENT_BINDING_ACCOUNT_TO_UPPER_LIMIT";
	public static final String EQUIPMENT_NOT_BINDING_WHIT_ACCOUNT="EQUIPMENT_NOT_BINDING_WHIT_ACCOUNT";
	
	public static final String FORGET_PASSWORD_MAIL_SUBJECT="FORGET_PASSWORD_MAIL_SUBJECT";
	public static final String FORGET_PASSWORD_MAIL_CONTENT="FORGET_PASSWORD_MAIL_CONTENT";
	
	public static final String CATALOG_ID_IS_EMPTY="CATALOG_ID_IS_EMPTY";
	
	public static final String PARTNER_NAME_REGISTERED="PARTNER_NAME_REGISTERED";
	
	public static final String PARTNER_NOT_EXIST="PARTNER_NOT_EXIST";
	
	public static final String EQUIPMENT_HAS_BOUND_WITH_OTHER_PARTNER="EQUIPMENT_HAS_BOUND_WITH_OTHER_PARTNER";
	
	public static final String APPS_ARRAY_FORMAT_ERROR="APPS_ARRAY_FORMAT_ERROR";
	
	public static final String VERSIONS_ARRAY_FORMAT_ERROR="VERSIONS_ARRAY_FORMAT_ERROR";
	
	public static final String APPS_ARRAY_VERSIONS_ARRAY_LEN_NOT_MATCH="APPS_ARRAY_VERSIONS_ARRAY_LEN_NOT_MATCH";
	
	public static final String APP_NOT_EXIST="APP_NOT_EXIST";
	
	// 正则模式
	public static final String REGEXP_EMAIL="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
	public static final String REGEXP_PASSWORD="[\\w!@#$%^&**()]{6,30}";
	public static final String REGEXT_MOBILE="[1][3,5,8][0-9]{9}";
}
