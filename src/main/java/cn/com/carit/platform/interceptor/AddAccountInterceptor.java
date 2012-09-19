package cn.com.carit.platform.interceptor;

import org.springframework.stereotype.Component;

import com.rop.AbstractInterceptor;
import com.rop.RopRequestContext;

/**
 * <pre>
 *    该拦截器仅对method为“user.add”进行拦截，你可以在{@link #isMatch(com.rop.RopRequestContext)}方法中定义拦截器的匹配规则。
 *  你可以通过{@link com.rop.RopRequestContext#getServiceMethodDefinition()}获取服务方法的注解信息，通过这些信息进行拦截匹配规则
 *  定义。
 * </pre>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-19
 */
@Component
public class AddAccountInterceptor extends AbstractInterceptor {

	@Override
	public void beforeService(RopRequestContext ropRequestContext) {
		// TODO Auto-generated method stub
		super.beforeService(ropRequestContext);
	}

	@Override
	public void beforeResponse(RopRequestContext ropRequestContext) {
		// TODO Auto-generated method stub
		super.beforeResponse(ropRequestContext);
	}

	@Override
	public boolean isMatch(RopRequestContext ropRequestContext) {
		// TODO Auto-generated method stub
		return super.isMatch(ropRequestContext);
	}

}
