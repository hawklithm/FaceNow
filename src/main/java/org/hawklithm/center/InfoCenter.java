package org.hawklithm.center;

import cn.mars.gxkl.controller.DetailContoller;
import cn.mars.gxkl.controller.MachineInfoController;
import cn.mars.gxkl.controller.RealTimeTabController;
import cn.mars.gxkl.controller.WorkerBaseInfoController;
import cn.mars.gxkl.mainUI.Constant;
import cn.mars.gxkl.netty.CommunicationCenter;

public class InfoCenter {
	private MachineInfoController machineInfoController;
	private RealTimeTabController	 realTimeTabController;
	private WorkerBaseInfoController workerBaseInfoController;
	private DetailContoller detailController;
	private CommunicationCenter communicationCenter;
	
	public void initial(){
		communicationCenter= new CommunicationCenter(Constant.processName[0]);
		new Thread(communicationCenter).start();
	}
	
	public MachineInfoController getMachineInfoController() {
		return machineInfoController;
	}
	public void setMachineInfoController(MachineInfoController machineInfoController) {
		this.machineInfoController = machineInfoController;
	}
	public RealTimeTabController getRealTimeTabController() {
		return realTimeTabController;
	}
	public void setRealTimeTabController(RealTimeTabController realTimeTabController) {
		this.realTimeTabController = realTimeTabController;
	}
	public WorkerBaseInfoController getWorkerBaseInfoController() {
		return workerBaseInfoController;
	}
	public void setWorkerBaseInfoController(WorkerBaseInfoController workerBaseInfoController) {
		this.workerBaseInfoController = workerBaseInfoController;
	}
	public DetailContoller getDetailController() {
		return detailController;
	}
	public void setDetailController(DetailContoller detailController) {
		this.detailController = detailController;
	}
}
