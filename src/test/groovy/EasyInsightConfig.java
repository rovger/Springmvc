package groovy;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ebay.eInsight.common.EasyInsightConfigInterface;
import com.ebay.eInsight.common.TimeType;
import com.ebay.eInsight.task.common.TaskTemplate;
import com.ebay.eInsight.task.common.ValueMappingCallBack;
import com.ebay.eInsight.task.mongoCountTask.MongoMapReduceTask;
import com.ebay.eInsight.task.mongoCountTask.MongoTemplateCountByPathTask;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

public class EasyInsightConfig implements EasyInsightConfigInterface {
	private static Logger s_logger = Logger.getLogger(EasyInsightConfig.class);

	private static String g_dollar = "$";

	public Map<String, String> getCoreConfig() {
		Map<String, String> coreconfig=new HashMap<String, String>();
		coreconfig.put("DOMAIN", "PIAPP");
		coreconfig.put("EVENT_COLLECTIONNAME", "PIAPP");
		return coreconfig;
	}

	public List<TaskTemplate> getTasks() {
		List<TaskTemplate> taskList=new ArrayList<TaskTemplate>();

		taskList.add(new MongoTemplateCountByPathTask("PIAPP_All_ResponseCode", TimeType.MINUTELY, 15*60*1000L, "content.responseCode"));
		taskList.add(new MongoTemplateCountByPathTask("PIAPP_All_ResponseCode", TimeType.HOURLY, 60*60*1000L));
		taskList.add(new MongoTemplateCountByPathTask("PIAPP_All_ResponseCode", TimeType.DAILY, 24*60*60*1000L));

		ValueMappingCallBack v2Callback=new ValueMappingCallBack(){
			public String callback(List<String> countByList) {
				if(countByList==null || countByList.size()<1) return null;
				String value = countByList.get(0);
				String[] retvals = value.split("-");
				String code = retvals[0];
				if(ebayPlusV2SuccessMap.get(code) != null) {
					return "Success";
				}else {
					if(ebayPlusV12failMap.get(code)!=null) {
						return "Failure";
					}
				}
				return null;
			}
			public List<String> reverseCallback(String callbackValue) {
				List<String> originalValueList=new ArrayList<String>();
				Map<String, String> amap = null;
				if(callbackValue.equals("Success")) {
					amap = ebayPlusV2SuccessMap;
				} else {
					amap = ebayPlusV12failMap;
				}
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put(g_dollar + "in", amap.keySet().toArray());
				originalValueList.add(new Gson().toJson(condition));
				return originalValueList;
			}};
		//v2 success vs failure
		taskList.add(new MongoTemplateCountByPathTask("eBayPlusV2", TimeType.MINUTELY, v2Callback, 15*60*1000L, "content.responseCode"));
		taskList.add(new MongoTemplateCountByPathTask("eBayPlusV2", TimeType.HOURLY, 60*60*1000L));
		taskList.add(new MongoTemplateCountByPathTask("eBayPlusV2", TimeType.DAILY, 24*60*60*1000L));

		ValueMappingCallBack allAPMCallback = new ValueMappingCallBack() {
			public String callback(List<String> countByList) {
				if (countByList == null || countByList.size() == 0) return null;
				String c4 = countByList.size()>1?countByList.get(1):"";
				String code = countByList.get(0);
				if(!"600900".equals(code) && "".equals(c4)) return null;
				return apmSetupMap.get(code);
			}
			public List<String> reverseCallback(String callbackValue) {
				List<String> originalValueList=new ArrayList<String>();
				List<String> searchKey = getkeys(apmSetupMap, callbackValue);
				DBObject codes = new BasicDBObject(g_dollar+"in", searchKey.toArray());
				originalValueList.add(new Gson().toJson(codes));
				DBObject obj = new BasicDBObject(g_dollar+"exists", true);
				originalValueList.add(new Gson().toJson(obj));
				return originalValueList;
			}
		};
		taskList.add(new MongoTemplateCountByPathTask("APMSetupConversion", TimeType.MINUTELY, allAPMCallback, 15*60*1000L, "content.responseCode", "content.c4"));
		taskList.add(new MongoTemplateCountByPathTask("APMSetupConversion", TimeType.HOURLY,60*60*1000L));
		taskList.add(new MongoTemplateCountByPathTask("APMSetupConversion", TimeType.DAILY,24*60*60*1000L));

		ValueMappingCallBack v2successCallback=new ValueMappingCallBack(){
			public String callback(List<String> countByList) {
				if(countByList==null || countByList.size()<1) return null;
				String value = countByList.get(0);
				String[] retvals = value.split("-");
				String code = retvals[0];
				String errorname = ebayPlusV2SuccessMap.get(code);
				if(errorname == null) {
					return null;
				}
				String newColumnValue = value.replace(code, errorname);
				return newColumnValue;
			}
			public List<String> reverseCallback(String callbackValue) {
				List<String> originalValueList=new ArrayList<String>();
				String[] retvals = callbackValue.split("-");
				String platform = retvals[1];
				DBObject Existed_regex = null;
				if(platform.equals("Desktop")) {
					Existed_regex = new BasicDBObject(g_dollar + "regex", "^(?!.*Mobile.*)");
				}else {
					Existed_regex = new BasicDBObject(g_dollar + "regex", ".*" +platform+ ".*");
				}
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("content.userAgent", Existed_regex);
				String successname = retvals[0];
				String searchKey = getkeyFromMap(ebayPlusV2SuccessMap, successname);
				if(searchKey!=null) {
					condition.put("content.responseCode", searchKey);
				}
				originalValueList.add(new Gson().toJson(condition));
				return originalValueList;
			}};
		String v2successmap = "function() { "  +
				"var browser=new String(this.content.userAgent); "+
				"var responsecode = new String(this.content.responseCode); " +
				"var browsertype;" +
				"if(browser.lastIndexOf('Mobile')>-1){"+
				"browsertype='Mobile';"+
				"}else{"+
				"browsertype='Desktop';"+
				"}"+
				"emit(responsecode + '-' + browsertype,{count:1});"+
				"}";
		String v2successreduce = "function(key, values) { " +
				"var total = 0; " +
				"for (var i = 0; i < values.length; i++) {   " +
				"total += values[i].count;"+
				"}" +
				"return {count:total}; }";
		taskList.add(new MongoMapReduceTask("eBayPlusV2_SuccesseByClient", TimeType.MINUTELY, v2successCallback, v2successmap, v2successreduce, 15*60*1000L));
		taskList.add(new MongoTemplateCountByPathTask("eBayPlusV2_SuccesseByClient", TimeType.HOURLY, 60*60*1000L));
		taskList.add(new MongoTemplateCountByPathTask("eBayPlusV2_SuccesseByClient", TimeType.DAILY, 24*60*60*1000L));

		return taskList;
	}

	private static String getkeyFromMap(Map<String, String> amap, String value) {
		String retKey = null;
		for(String key : amap.keySet()) {
			if(amap.get(key).equals(value)) {
				retKey = key;
			}
		}
		return retKey;
	}

	private static List<String> getkeys(Map<String, String> amap, String value) {
		List<String> retKeys = new ArrayList<String>();
		for(String key : amap.keySet()) {
			if(amap.get(key).equals(value)) {
				retKeys.add(key);
			}
		}
		return retKeys;
	}
	private static Map<String, String> ebayPlusV2SuccessMap = new HashMap<String, String>();
	private static Map<String, String> ebayPlusV12failMap = new HashMap<String, String>();
	private static Map<String, String> apmSetupMap = new HashMap<String, String>();
	static {
		ebayPlusV2SuccessMap.put("810002", "CC_Submit_On");
		ebayPlusV2SuccessMap.put("810012", "DD_Submit_On");
		ebayPlusV2SuccessMap.put("810022", "PP_Submit_On");

		ebayPlusV12failMap.put("40053", "IDLT_SYSTEM_ERROR");
		ebayPlusV12failMap.put("40054", "IDLT_SESSION_EXPIRED");
		ebayPlusV12failMap.put("40055", "IDLT_PC_WIRED_OFF");
		ebayPlusV12failMap.put("40057", "IDLT_EBAY_USER_NOT_SIGNED_IN");
		ebayPlusV12failMap.put("40060", "IDLT_USER_CANCELLED_HERMES");
		ebayPlusV12failMap.put("40062", "IDLT_PAYPAL_ERROR");

		apmSetupMap.put("600500", "Load_Success");
		apmSetupMap.put("600700", "Load_Success");
		apmSetupMap.put("600902", "Load_Success");
		apmSetupMap.put("600600", "Create_Success");
		apmSetupMap.put("600800", "Create_Success");
		apmSetupMap.put("600909", "Create_Success");
	}

	public List<TaskTemplate> getBackEndTasks() {
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss");
		List<TaskTemplate> taskList=new ArrayList<TaskTemplate>();

		/*PullDataCallBack callback = new PullDataCallBack() {

			public List buildMap(String json) {
				Configuration conf = Configuration
						.builder()
						.mappingProvider(new JacksonMappingProvider())
						.jsonProvider(new JacksonJsonProvider())
						.build();
				TypeRef<List<Map<String, String>>> type = new TypeRef<List<Map<String, String>>>(){};

				List<Map<String, String>> list = (List<Map<String, String>>) JsonPath.using(conf).parse(json).read(g_dollar + ".[*]", type);
				try{
					//RawDataDAOImpl.getWriteInstance().getDBCollection().createIndex(new BasicDBObject("content.userId", 1), new BasicDBObject("background", true));
				}catch(Exception e){
					s_logger.error("PullData error: ", e);
				}
				return list;
			}

			public String buildURL(String url, Date starttime, Date endtime, String... params) {
				//http://mmpmsvc-web-private-1-2.stratus.phx.ebay.com/mmpm/audit/piapp/2016-11-08.23-52-00/2016-11-09.23-52-00/error/99000?flowname=EBAY_PLUS
				String formatedurl = MessageFormat.format(url, format.format(starttime), format.format(endtime));
				return formatedurl;
			}

		};
		//=========start of template==================
		//alert started
		final String baseurl="http://ebconsole-web-private-1-16.stratus.phx.ebay.com";
		Map<String, Long> countAlertMap=new HashMap<String, Long>();
		countAlertMap.put("eBayPlusV2:Success:DAILY:<",1L);

		for(String reportNameValueStr:countAlertMap.keySet()){
			final String countAlartReportName = reportNameValueStr;
			final String[] reportNameValue=reportNameValueStr.split(":");
			final Long reportValue=countAlertMap.get(reportNameValueStr);
			ReportAlertCallBack webhooksTask_alertCallBack=new ReportAlertCallBack(){
				public void init(){
					try {
						ReportAlertCallBack.class.getField("alertRule").set(this, countAlartReportName);
						ReportAlertCallBack.class.getField("alertThreshold").set(this, ""+reportValue);
						ReportAlertCallBack.class.getField("alertType").set(this, "Count");
					} catch (Exception e) {}
				}
				public String alert(ReportAlertTask reportAlertTask) {
					final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					StringBuilder content = new StringBuilder("Alert Rule");
					content.append("<br/>If  "+ countAlartReportName +" "+reportValue+ " , then alert.<br/>");
					content.append("<br/>The Current date is "+ sdf.format(reportAlertTask.getStartDate()) +" - " + sdf.format(reportAlertTask.getEndDate()) + "<br/>");
					if(reportAlertTask.isAbnormal()){
						content.append("<br/>the current count is abnormal. <br/>");
					}else{
						content.append("<br/> the current count back to normal.");
					}
					Object targetObj = reportAlertTask.getReportData().get(reportNameValue[1]);
					String currentCount = "0";
					if(targetObj!=null) currentCount = targetObj.toString();
					content.append("<br/> current count is "+  currentCount );
					content.append("<br/> threshod count is "+  reportValue );
					content.append("<br/><a href='"+baseurl+"/views/reporting?reportName="+ reportNameValue[0]+"'> click me to open  easyinsight reporting  </a>!" );

					return content.toString();
				}
				public boolean isAbnormalData(ReportAlertTask reportAlertTask) {
					Map<String, String> reportData = reportAlertTask.getReportData();
					BigDecimal target = new BigDecimal("0");
					if (!StringUtils.isEmpty(reportData.get(reportNameValue[1]))) {
						target = new BigDecimal(reportData.get(reportNameValue[1]));
					}
					if(reportNameValue[3].contains(">")){
						if(target.longValue()>reportValue){
							return true;
						}else{
							return false;
						}
					}else if(reportNameValue[3].contains("<")){
						if(target.longValue()<reportValue){
							return true;
						}else{
							return false;
						}
					}else{
						return true;
					}

				}};
			taskList.add(new ReportEmailAlertTask(reportNameValue[0]+reportNameValue[1]+"ReportCountAlertTask",TimeType.valueOf(reportNameValue[2]),
					reportNameValue[0],reportNameValue[1],webhooksTask_alertCallBack,"lixie@ebay.com", "lixie@ebay.com,weijlu@ebay.com,zhenzfeng@ebay.com"));
		}

		Map<String, Double> percentageMinimalAlertMap=new HashMap<String, Double>();
		percentageMinimalAlertMap.put("eBayPlusV2:Failure:DAILY:>",0.5);
		percentageMinimalAlertMap.put("eBayPlusV2_Error:IDLT_PAYPAL_ERROR:DAILY:>",0.5);

		for(String reportNameValueStr2:percentageMinimalAlertMap.keySet()){
			final String percentAlertReportName = reportNameValueStr2;
			final String[] reportNameValue=reportNameValueStr2.split(":");
			final Double reportTargetValue=percentageMinimalAlertMap.get(reportNameValueStr2);
			ReportAlertCallBack webhooksTask_alertCallBack=new ReportAlertCallBack(){
				public void init(){
					try {
						ReportAlertCallBack.class.getField("alertRule").set(this, percentAlertReportName);
						ReportAlertCallBack.class.getField("alertThreshold").set(this, ""+reportTargetValue);
						ReportAlertCallBack.class.getField("alertType").set(this, "Percent");
					} catch (Exception e) {}
				}
				public String alert(ReportAlertTask reportAlertTask) {
					final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					StringBuilder content = new StringBuilder("Alert Rule");
					content.append("<br/>If  "+ percentAlertReportName +" "+ reportTargetValue*100 + "% , then alert.<br/>");
					content.append("<br/>The Current date is "+ sdf.format(reportAlertTask.getStartDate()) +" - " + sdf.format(reportAlertTask.getEndDate()) + "<br/>");
					if(reportAlertTask.isAbnormal()){
						content.append("<br/>the current percentage is abnormal. <br/>");
					}else{
						content.append("<br/> the current percentage is back to normal.");
					}

					BigDecimal total = new BigDecimal("0");
					BigDecimal target = new BigDecimal("0");
					for(String record:reportAlertTask.getReportData().keySet()){
						total=total.add(new BigDecimal(reportAlertTask.getReportData().get(record)));
					}
					Object targetObj = reportAlertTask.getReportData().get(reportNameValue[1]);
					if(targetObj!=null) {
						target=new BigDecimal(targetObj.toString());
					}
					String percentage = "0";
					if (total.longValue()!=0L && target.longValue()!=0L) {
						double temp = target.divide(total, 3, BigDecimal.ROUND_HALF_UP).doubleValue();
						percentage = String.valueOf(temp*100) + "%";
					}
					content.append("<br/> current percentage is "+  percentage );
					content.append("<br/> the threshod percentage is "+  reportTargetValue*100+"%" );
					content.append("<br/><a href='"+baseurl+"/views/reporting?reportName="+ reportNameValue[0]+"'> click me to open  easyinsight reporting "+reportNameValue[0]+" </a>!" );
					return content.toString();
				}
				public boolean isAbnormalData(ReportAlertTask reportAlertTask) {
					Long total=0L;
					Long target=0L;
					for(String record:reportAlertTask.getReportData().keySet()){
						total=total+Long.parseLong(reportAlertTask.getReportData().get(record));
					}
					Object targetObj = reportAlertTask.getReportData().get(reportNameValue[1]);
					if(targetObj!=null) {
						target=Long.parseLong(targetObj.toString());
					}
					if(reportNameValue[3].contains(">")){
						if(total!=0L && target/total > reportTargetValue){
							return true;
						}else{
							return false;
						}
					}else if(reportNameValue[3].contains("<")){
						if(total!=0L && target/total < reportTargetValue){
							return true;
						}else{
							return false;
						}
					}else{
						return true;
					}
				}};
			taskList.add(new ReportEmailAlertTask(reportNameValue[0]+reportNameValue[1]+"ReportPercentAlertTask",TimeType.valueOf(reportNameValue[2]),
					reportNameValue[0],reportNameValue[1],webhooksTask_alertCallBack, "lixie@ebay.com", "lixie@ebay.com,weijlu@ebay.com,zhenzfeng@ebay.com"));
		}

		//For APM Setup By Client
		taskList.add(new PullDataTask("pulldatatask800001", TimeType.MINUTELY, "http://mmpmsvc-web-private-1-2.stratus.phx.ebay.com/mmpm/audit/piapp/{0}/{1}/error/800001", callback));
		taskList.add(new PullDataTask("pulldatatask800002", TimeType.MINUTELY, "http://mmpmsvc-web-private-1-2.stratus.phx.ebay.com/mmpm/audit/piapp/{0}/{1}/error/800002", callback));
		taskList.add(new PullDataTask("pulldatatask800003", TimeType.MINUTELY, "http://mmpmsvc-web-private-1-2.stratus.phx.ebay.com/mmpm/audit/piapp/{0}/{1}/error/800003", callback));
		//=========end of template==================
*/
		return taskList;
	}

}
