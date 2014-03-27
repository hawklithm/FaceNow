package cn.mars.gxkl.netty;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hawklithm.center.InfoCenter;

import cn.mars.gxkl.controller.RealTimeTabController;
import cn.mars.gxkl.protocol.AppProtocol;
import cn.mars.gxkl.protocol.FrontEndingCommunicationProtocol;
import cn.mars.gxkl.protocol.HandleDetails;
import cn.mars.gxkl.protocol.LiveMessageProtocol;
import cn.mars.gxkl.utils.Pair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.multiagent.hawklithm.item.dataobject.ItemInfoDO;

public class CommunicationCenter implements Runnable {

	// private LineWindowPanel[] line;
	private ClientService client;
	private boolean closed = false;
	private boolean sendRequest = false;// ,ack = false,connectionStatus = true;
	private String url;
	private String processNow;
	// private JFrame frame;
	// private EmergencyWindow emerg;
	private Map<Integer, Integer> rfid2Index;
	private int indexNow;
	private InfoCenter infoCenter;

	public void setProcessNow(String processNow) {
		this.processNow = processNow;
		sendRequest = true;
		// ack = false;
		// connectionStatus = true;
		System.out.println("processName changes to " + processNow);
	}

	// public MessageManage(String processNow) {
	// this.rfid2Index = new HashMap<Integer,Integer>();
	// rfid2Index.put(-1, -1);
	// this.indexNow = 0;
	// this.processNow = processNow;
	// this.emerg = null;
	// }

	public CommunicationCenter(String processNow, InfoCenter infoCenter) {
		this.infoCenter = infoCenter;
		this.rfid2Index = new HashMap<Integer, Integer>();
		rfid2Index.put(-1, -1);
		this.indexNow = 0;
		// this.line = line;
		this.processNow = processNow;
		// this.frame = frame;
		// this.emerg = null;
		this.url = "/ProcessInfoManager";
		initialization();
	}

	public void initialization() {
		// client = new ClientService("192.168.1.111",48800);
		client = new ClientService(48800);
		while (!client.isConnected()) {
			System.out.println("Connecting...");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request();
		System.out.println("first request...");
	}

	public boolean getACK() {
		return client.getACK();
	}

	public boolean getConnectionStatus() {
		return client.getConnectionStatus();
	}

	@Override
	public void run() {
		while (!closed) {
			emergHander();
			if (sendRequest) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			infoHandler();
		}
	}

	private void emergHander() {
		AppProtocol response = client.getEmerg();
		if (response == null) {
			return;
		}
		List<Pair<Integer, String>> info = decoder(response, "Emergency");
		if (info == null) {
			return;
		}
		for (int i = 0; i < info.size(); i++) {
			Pair<Integer, String> pair = info.get(i);
			// if(emerg == null) {
			// emerg = new EmergencyWindow(300, 200, frame);
			// }
			// emerg.append(pair.getLast());
			// if(!emerg.isShow()) {
			// emerg.open();
			// }
		}
	}

	private void infoHandler() {
		AppProtocol response = client.getMessage();
		if (response == null) {
			// System.out.println("getting message but no new message");
			return;
		}
		System.out.println("something new ");
		List<Pair<Integer, String>> info = decoder(response, processNow);
		if (info == null) {
			System.out.println("new pair message");
			return;
		}
		for (int i = 0; i < info.size(); i++) {
			Pair<Integer, String> pair = info.get(i);
			// System.out.println("pair.getLast(): "+pair.getLast());
			System.out.println("\npair: " + pair.getFirst() + "\t" + pair.getLast());
			RealTimeTabController realTimeController = infoCenter.getRealTimeTabController();
			realTimeController.addInfo(pair.getFirst(), pair.getLast(), 0);
			// line[pair.getFirst()].append(pair.getLast());
		}
	}

	public void request() {
		String msg = encoder();
		System.out.println("request: " + msg);
		client.sendMessage(msg);
		sendRequest = false;
		client.flushInfoCache();
		this.indexNow = 0;
	}

	// public void setUrl(String url) {
	// // this.url = url;
	// sendRequest = true;
	// }

	public void closeConnection() {
		closed = true;
		client.closeClient();
	}

	private List<Pair<Integer, String>> decoder(AppProtocol response, String processNow) {
		try {
			List<Pair<Integer, String>> ans = new ArrayList<Pair<Integer, String>>();
			Gson gson = new Gson();
			// System.out.println("decode "+msg.getContent());
			FrontEndingCommunicationProtocol<LiveMessageProtocol> msgContent = gson.fromJson(
					response.getResponse(),
					new TypeToken<FrontEndingCommunicationProtocol<LiveMessageProtocol>>() {
					}.getType());
			List<LiveMessageProtocol> liveMessage = msgContent.getRows();
			for (int i = 0; i < liveMessage.size(); i++) {
				LiveMessageProtocol pro = liveMessage.get(i);
				if (!pro.getProcessName().equals(processNow)) {
					continue;
				}
				// Integer id = 0;
				// int id = pro.getId();
				List<Map<String, Object>> retValue = pro.getRetValue();
				int size = retValue.size();
				for (int j = 0; j < size; j++) {
					// System.out.println(retValue.get(j).get(0));'
					HandleDetails handleDetails;
					try {
						handleDetails = new HandleDetails(retValue.get(j));
					} catch (IndexOutOfBoundsException e) {
						continue;
					}
					int rfid = handleDetails.getMachineRfid();
					// if((id=rfid2Index.get(rfid))==null) {
					// id = indexNow++;
					// // line[id].setRFID(""+rfid);
					// rfid2Index.put(rfid, id);
					// }
					try {
						ans.addAll(handleRetValue(rfid, handleDetails.getItemAdd(), "开始处理", "器械"));
						ans.addAll(handleRetValue(rfid, handleDetails.getItemRemove(), "处理完毕", "器械"));
						ans.addAll(handleRetValue(rfid, handleDetails.getPackageAdd(), "开始处理",
								"手术包"));
						ans.addAll(handleRetValue(rfid, handleDetails.getPackageRemove(), "处理结束",
								"手术包"));
					} catch (NullPointerException e) {
						continue;
					}
				}
				// for (int j = 0; j < info.size(); j++) {
				// ans.add(new Pair<Integer, String>(id, info.get(j)));
				// }
			}
			return ans;
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String changeCharset(String str, String oldCharset, String newCharset)
			throws UnsupportedEncodingException {
		if (str != null) {
			// 用旧的字符编码解码字符串。解码可能会出现异常。
			byte[] bs = str.getBytes(oldCharset);
			// 用新的字符编码生成字符串
			return new String(bs, newCharset);
		}
		return null;
	}

	private ItemInfoDO map2ItemInfoDO(Map<String, Object> map) {
		ItemInfoDO ret = new ItemInfoDO();

		if (map.containsKey("itemId")) {
			ret.setItemId(((Double) map.get("itemId")).intValue());
		}
		if (map.containsKey("gmtCreate")) {
			ret.setGmtCreate(new Date((String) map.get("gmtCreate")));
		}
		if (map.containsKey("gmtModified")) {
			ret.setGmtModified(new Date((String) map.get("gmtModified")));
		}
		if (map.containsKey("itemName")) {
//			try {
//				ret.setItemName(changeCharset((String) map.get("itemName"),"UTF-8","GBK"));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			ret.setItemName((String) map.get("itemName"));
		}
		if (map.containsKey("itemType")) {
			ret.setItemType(((Double) map.get("itemType")).intValue());
		}
		if (map.containsKey("hospitalId")) {
			ret.setHospitalId(((Double) map.get("hospitalId")).intValue());
		}
		if (map.containsKey("manufacturer")) {
//			try {
//				ret.setManufacturer(changeCharset((String) map.get("manufacturer"), "UTF-8", "GBK"));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			ret.setManufacturer((String) map.get("manufacturer"));
		}
		if (map.containsKey("interconvertible")) {
			ret.setInterconvertible((Boolean) map.get("interconvertible"));
		}
		if (map.containsKey("remark")) {
//			try {
//				ret.setRemark(changeCharset((String) map.get("remark"),"UTF-8","GBK"));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			ret.setRemark((String) map.get("remark"));
		}

		return ret;
	}

	private List<Pair<Integer, String>> handleRetValue(int id, List<Object> rfid, String dir,
			String type) {
		List<Pair<Integer, String>> ans = new ArrayList<Pair<Integer, String>>();
		int size = rfid.size();
		for (int i = 0; i < size; i++) {
			// System.out.println(rfid.get(i).toString());
			ItemInfoDO itemInfo = map2ItemInfoDO((Map<String, Object>) rfid.get(i));

			ans.add(new Pair<Integer, String>(id, type + " " + itemInfo.getItemName() + " " + dir
					+ " RFID:" + itemInfo.getItemId().toString()));
		}
		return ans;
	}

	public List<Pair<Integer, String>> test(String response) {
		Gson gson = new Gson();
		AppProtocol app = gson.fromJson(response, AppProtocol.class);
		return decoder(app, processNow);
	}

	private String encoder() {
		LiveMessageProtocol liveMsg = new LiveMessageProtocol();
		liveMsg.setProcessName(processNow);
		List<LiveMessageProtocol> rows = new ArrayList<LiveMessageProtocol>();
		rows.add(liveMsg);
		FrontEndingCommunicationProtocol<LiveMessageProtocol> content = new FrontEndingCommunicationProtocol<LiveMessageProtocol>();
		content.setRows(null);
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("operateType", "operateQuery");
		content.setCondition(condition);
		content.setRows(rows);
		Gson gson = new Gson();
		AppProtocol msg = new AppProtocol();
		msg.setTargetUrl(url);
		// msg.setTargetUrl(processNow);
		msg.setContent(gson.toJson(content));
		msg.setAuthenticate("");
		return gson.toJson(msg);
	}

	// public static void main(String[] args) {
	// MessageManage mm = new MessageManage(Constant.processName[0]);
	// String response =
	// "{\"response\":\"{\\\"condition\\\":{\\\"status\\\":\\\"ok\\\"},\\\"rows\\\":[{\\\"condition\\\":{\\\"processName\\\":\\\"sortingprocess\\\",\\\"retValue\\\":[[{\\\"itemAdd\\\":[1712733,1886494,1542056,1736078,1561102,1729298,1358434,1112528,1611452,1790915,1509624,1439545,1735303,1415604,1846329,1500718,1399014,1615278,1605718,1904035,1064011,1103803,1444953,1026534,1124453,1542142,1413110,1440966,1452863,1033773,1614842,1094302,1199366,1863725,1175648,1867969,1693160,1479560,1419330,1254723,1382423,1893133,1611181,1677602,1962604,1487045,1648973,1133776,1906537,1535309,1726855,1838046,1933783,1996949,1039334,1864774,1927694,1387572,1073388,1980181,1607315,1564901,1271498,1548019,1899483,1122658,1971692,1806481,1036654,1381979,1343723,1693997,1081831,1348307,1445798,1595653,1075928,1252504,1444203,1933078,1195274,1134877,1211790,1614287,1088673,1733493,1095004,1373143,1181662,1161198,1927850,1638968,1126463,1009224,1246214,1085542,1415937,1711185,1208783,1167664],\\\"itemRemove\\\":[],\\\"packageAdd\\\":[],\\\"packageRemove\\\":[],\\\"rfid\\\":1024,\\\"itemRFIDs\\\":[],\\\"packageRFIDs\\\":[],\\\"staffRFID\\\":1024,\\\"cubage\\\":0}]]}}]}\",\"status\":\"connected\"}";
	// List<Pair<Integer,String>> l = mm.test(response);
	//
	// int size = l.size();
	// for(int i=0;i<size;i++) {
	// System.out.println(""+l.get(i).getFirst()+" "+l.get(i).getLast());
	// }
	// // new Thread(mm).start();
	// }
}
