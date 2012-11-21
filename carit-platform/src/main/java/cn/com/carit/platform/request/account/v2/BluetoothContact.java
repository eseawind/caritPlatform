package cn.com.carit.platform.request.account.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * <b>功能说明：</b>蓝牙通讯录模型
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * @date 2012-11-15
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "contact")
public class BluetoothContact {
	
	@XmlAttribute
	private String callName;
	@XmlAttribute
	private String callNum;
	@XmlAttribute
	private String callNameKey;
	@XmlAttribute
	private String callType;
	
	public String getCallName() {
		return callName;
	}
	public void setCallName(String callName) {
		this.callName = callName;
	}
	public String getCallNum() {
		return callNum;
	}
	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}
	public String getCallNameKey() {
		return callNameKey;
	}
	public void setCallNameKey(String callNameKey) {
		this.callNameKey = callNameKey;
	}
	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}
	
}
