/**
 * 版权声明：中图一购网络科技有限公司 版权所有 违者必究 2012 
 * 日    期：12-6-2
 */
package cn.com.carit.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rop.event.AfterStartedRopEvent;
import com.rop.event.RopEventListener;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 */
public class PostInitializeEventListener implements RopEventListener<AfterStartedRopEvent> {

	private static final Logger logger = LoggerFactory.getLogger(PostInitializeEventListener.class);
	
    @Override
    public void onRopEvent(AfterStartedRopEvent ropRopEvent) {
        System.out.println("execute SamplePostInitializeEventListener!");
        logger.info("execute PostInitializeEventListener");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

