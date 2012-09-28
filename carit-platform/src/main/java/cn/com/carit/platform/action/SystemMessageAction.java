package cn.com.carit.platform.action;

import cn.com.carit.Action;

public interface SystemMessageAction<SystemMessage> extends Action<SystemMessage> {

	void readSystemMessage(int id);
}
