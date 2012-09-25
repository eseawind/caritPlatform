package cn.com.carit.platform.dao;

import java.util.List;

import cn.com.carit.Dao;
import cn.com.carit.platform.request.LocationRequest;

public interface LocationDao<Location> extends Dao<Location> {

	int batchAdd(final List<LocationRequest> locationList, final String deviceId);
}
