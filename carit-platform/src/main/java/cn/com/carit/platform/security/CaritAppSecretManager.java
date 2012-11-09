package cn.com.carit.platform.security; /**
 * 版权声明：中图一购网络科技有限公司 版权所有 违者必究 2012 
 * 日    期：12-5-25
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.carit.platform.cache.CacheManager;

import com.rop.security.AppSecretManager;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 */
public class CaritAppSecretManager implements AppSecretManager {
	private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String getSecret(String appKey) {
        logger.info("use CaritAppSecretManager!");
        return CacheManager.getInstance().getAppSecret(appKey);
    }

    @Override
    public boolean isValidAppKey(String appKey) {
        return getSecret(appKey) != null;
    }
}

