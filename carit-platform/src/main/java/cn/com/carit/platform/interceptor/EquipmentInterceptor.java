package cn.com.carit.platform.interceptor;

import javax.annotation.Resource;

import cn.com.carit.common.Constants;
import cn.com.carit.platform.action.EquipmentAction;
import cn.com.carit.platform.bean.Equipment;

import com.rop.AbstractInterceptor;
import com.rop.RopRequestContext;
import com.rop.response.BusinessServiceErrorResponse;

/**
 * <p>
 * <b>功能说明：</b>设备拦截器，拦截没绑定设备的请求
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * @date 2012-10-30
 */
public class EquipmentInterceptor extends AbstractInterceptor {

	@Resource
	private EquipmentAction<Equipment> equipmentAction;
	@Override
	public void beforeService(RopRequestContext ropRequestContext) {
		if (isMatch(ropRequestContext)) {
			int accountId=Integer.valueOf(ropRequestContext.getParamValue("accountId"));
			String deviceId=ropRequestContext.getParamValue("deviceId");
			if (equipmentAction.checkBounding(accountId, deviceId)<1) {
				// 没有绑定设备
				ropRequestContext
						.setRopResponse(new BusinessServiceErrorResponse(
								ropRequestContext.getMethod(),
								Constants.EQUIPMENT_NOT_BINDING_WHIT_ACCOUNT, ropRequestContext
										.getLocale(), deviceId, accountId));
			}
		}
	}

	@Override
	public boolean isMatch(RopRequestContext ropRequestContext) {
		String method=ropRequestContext.getMethod();
		if("platform.location.upload".equals(method)){
			return true;
		}
		if("platform.location.search".equals(method)){
			return true;
		}
		if("platform.obd.upload".equals(method)){
			return true;
		}
		if("platform.obd.search".equals(method)){
			return true;
		}
		if("platform.obd.newestData".equals(method)){
			return true;
		}
		return false;
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
