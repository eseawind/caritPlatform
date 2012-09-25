package cn.com.carit.platform.action;

import java.util.List;

import cn.com.carit.Action;
import cn.com.carit.platform.request.LocationRequest;

public interface LocationAction<Location> extends Action<Location> {

	int batchAdd(final List<LocationRequest> locationList, final String deviceId);
}
