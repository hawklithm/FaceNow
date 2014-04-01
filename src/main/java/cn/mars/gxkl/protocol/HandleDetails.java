package cn.mars.gxkl.protocol;

import java.util.Date;
import java.util.List;
import java.util.Map;

import sun.security.krb5.internal.Ticket;

public class HandleDetails {

	private Map<String, Object> condition;
	final private String ITEM_ADD = "itemAdd", ITEM_REMOVE = "itemRemove",
			PACKAGE_ADD = "packageAdd", PACKAGE_REMOVE = "packageRemove",
			MACHINE_RFID = "machineRFID", STAFF_RFID = "staffRFID", CUBAGE = "cubage",
			ITEM_RFIDS = "itemRFIDs", PACKAGE_RFIDS = "packageRFIDs",TIME_STAMP="timeStamp",MESSAGE="message";

	public void setMessage(String message){
		condition.put(MESSAGE ,message);
	}
	public String getMessage(){
		return (String) condition.get(MESSAGE);
	}
	public void setTimeStamp(Date time){
		condition.put(TIME_STAMP, time);
	}
	public Date getTimeStamp(){
		return (Date)condition.get(TIME_STAMP);
	}
	public void setCondition(Map<String, Object> condition) {
		this.condition = condition;
	}

	public HandleDetails(Map<String, Object> condition) {
		this.condition = condition;
	}

	@SuppressWarnings("unchecked")
	public List<Object> getItemAdd() {
		return (List<Object>) condition.get(ITEM_ADD);
	}

	@SuppressWarnings("unchecked")
	public List<Object> getItemRemove() {
		return (List<Object>) condition.get(ITEM_REMOVE);
	}

	@SuppressWarnings("unchecked")
	public List<Object> getPackageAdd() {
		return (List<Object>) condition.get(PACKAGE_ADD);
	}

	@SuppressWarnings("unchecked")
	public List<Object> getPackageRemove() {
		return (List<Object>) condition.get(PACKAGE_REMOVE);
	}

	public Integer getMachineRfid() {
		// return new Integer.valueOf((Integer)condition.get(RFID));
		return (int) Float.parseFloat(condition.get(MACHINE_RFID).toString());
	}

	public Integer getStaffRFID() {
		return (int) Float.parseFloat(condition.get(STAFF_RFID).toString());
	}

	public Integer getCubage() {
		return (int) Float.parseFloat(condition.get(CUBAGE).toString());
	}

	@SuppressWarnings("unchecked")
	public List<Object> getItemRFIDs() {
		return (List<Object>) condition.get(ITEM_RFIDS);
	}

	@SuppressWarnings("unchecked")
	public List<Object> getPackageRFIDs() {
		return (List<Object>) condition.get(PACKAGE_RFIDS);
	}
}
