package cn.mars.gxkl.controller;

import cn.mars.gxkl.controller.dataobject.ObjectPair;

public interface DetailContoller {
	void addScalpels(ObjectPair... objectPair);
	void addHammers(ObjectPair... objectPair);
	void addForcepses(ObjectPair... objectPair);
	void clearAll();
}
