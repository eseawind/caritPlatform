package cn.com.carit.platform.request;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.rop.AbstractRopRequest;
/**
 * <p>
 * <b>功能说明：</b>
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * @date 2012-9-25
 */
public class UploadLocationRequest extends AbstractRopRequest {
	
	@NotEmpty
	private List<LocationRequest> lists;
	
	@NotEmpty
    private String deviceId;

	public List<LocationRequest> getLists() {
		return lists;
	}

	public void setLists(List<LocationRequest> lists) {
		this.lists = lists;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	

}
