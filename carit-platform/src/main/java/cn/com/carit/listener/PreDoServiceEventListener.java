/**
 * 版权声明：中图一购网络科技有限公司 版权所有 违者必究 2012 
 * 日    期：12-7-17
 */
package cn.com.carit.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rop.RopRequest;
import com.rop.RopRequestContext;
import com.rop.event.PreDoServiceEvent;
import com.rop.event.RopEventListener;
import com.rop.marshaller.MessageMarshallerUtils;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 */
public class PreDoServiceEventListener implements RopEventListener<PreDoServiceEvent> {

	private static final Logger logger = LoggerFactory.getLogger(PreDoServiceEventListener.class);
	
    @Override
    public void onRopEvent(PreDoServiceEvent ropEvent) {
        RopRequestContext ropRequestContext = ropEvent.getRopRequestContext();
        if(ropRequestContext != null && ropRequestContext.getRopRequest() != null){
            RopRequest ropRequest = ropRequestContext.getRopRequest();
            String message = MessageMarshallerUtils.getMessage(ropRequest, ropRequestContext.getMessageFormat());
            logger.info("message("+ropEvent.getServiceBeginTime()+")"+message);
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}

