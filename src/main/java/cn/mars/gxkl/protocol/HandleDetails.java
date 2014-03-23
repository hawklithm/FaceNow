package cn.mars.gxkl.protocol;

import java.util.List;
import java.util.Map;

public class HandleDetails {
	
	private Map<String,Object> condition;
	final private String ITEM_ADD = "itemAdd",ITEM_REMOVE = "itemRemove",PACKAGE_ADD = "packageAdd",
			PACKAGE_REMOVE = "packageRemove",RFID = "rfid",STAFF_RFID = "staffRFID",CUBAGE = "cubage",
			ITEM_RFIDS = "itemRFIDs",PACKAGE_RFIDS = "packageRFIDs";
//	private List<String> itemAdd,itemRemove,packageAdd,packageRemove;
//	private int rfid,staffRFID,cubage;
//	private List<String> itemRFIDs,packageRFIDs;
//	public HandleDetails() {}
//	public HandleDetails() {
//		itemAdd = new ArrayList<String>();
//		itemRemove = new ArrayList<String>();
//		packageAdd = new ArrayList<String>();
//		packageRemove = new ArrayList<String>();
//		itemRFIDs = new ArrayList<String>();
//		packageRFIDs = new ArrayList<String>();
//	}
	public void setCondition(Map<String,Object> condition) {
		this.condition = condition;
	}
	
	public HandleDetails(Map<String,Object> condition) {
		this.condition = condition;
	}
	@SuppressWarnings("unchecked")
	public List<Object> getItemAdd() {
		return (List<Object>)condition.get(ITEM_ADD);
	}
	@SuppressWarnings("unchecked")
	public List<Object> getItemRemove() {
		return (List<Object>)condition.get(ITEM_REMOVE);
	}
	@SuppressWarnings("unchecked")
	public List<Object> getPackageAdd() {
		return (List<Object>)condition.get(PACKAGE_ADD);
	}
	@SuppressWarnings("unchecked")
	public List<Object> getPackageRemove() {
		return (List<Object>)condition.get(PACKAGE_REMOVE);
	}
	public Integer getRfid() {
//		return new Integer.valueOf((Integer)condition.get(RFID));
		return (int)Float.parseFloat(condition.get(RFID).toString());
	}
	public Integer getStaffRFID() {
		return (int)Float.parseFloat(condition.get(STAFF_RFID).toString());
	}
	public Integer getCubage() {
		return (int)Float.parseFloat(condition.get(CUBAGE).toString());
	}
	@SuppressWarnings("unchecked")
	public List<Object> getItemRFIDs() {
		return (List<Object>)condition.get(ITEM_RFIDS);
	}
	@SuppressWarnings("unchecked")
	public List<Object> getPackageRFIDs() {
		return (List<Object>)condition.get(PACKAGE_RFIDS);
	}
}
