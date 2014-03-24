package cn.mars.gxkl.controller;

import cn.mars.gxkl.controller.dataobject.MachineInfoProperty;

public interface MachineInfoController {
	public int addMachine(MachineInfoProperty property);
	public void changeInfo(MachineInfoProperty property);
	public void deleteMachine(int index);
}
