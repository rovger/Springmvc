package com.rovger.demo.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weijlu on 2017/3/21.
 */
public class StringDemo {

	public static void main(String[] args) {

		/*String str = "131403283034-1331527203003 819763797010";
		String substr = null;
		if (str.contains("!")) {
			substr = str.substring(0, str.lastIndexOf("!"));
		}
		System.out.println(substr);*/

		/*String str = "{\\\"op_status\\\":\\\"SUCCESS\\\",\\\"operation\\\":{\\\"op_name\\\":\\\"CONFIRM_INSTRUMENT\\\"},\\\"bizflow\\\":{\\\"flow_name\\\":\\\"SETUP_APM_C2C\\\",\\\"flow_state\\\":\\\"INSTRUMENT_CONFIRMED\\\",\\\"creation_date\\\":\\\"2017-03-12 22:56:36\\\",\\\"lastupdate_date\\\":\\\"2017-03-14 02:16:56\\\",\\\"flowdata\\\":{\\\"piapp_link\\\":\\\"http://instrumentapp.qa.ebay.com/piapp/psp/v2?flowid=5E500A8F000000012A06D0FC&opid=73DEFF6C000000012A070716\\\",\\\"temp_instrument_id\\\":\\\"TC_89358228\\\",\\\"instrument_id\\\":\\\"CC_89319268\\\",\\\"pgw_wallet_id\\\":\\\"89157851\\\",\\\"cc_last_four_digits\\\":\\\"3215\\\",\\\"user_id\\\":\\\"1336773058\\\",\\\"billing_currency\\\":\\\"USD\\\",\\\"account_suffix_id\\\":\\\"001\\\",\\\"site_id\\\":0,\\\"cc_expiration_year\\\":2044,\\\"cc_expiration_month\\\":4,\\\"payment_type\\\":\\\"CreditCard\\\",\\\"instrument_attr\\\":\\\"Primary\\\"}},\\\"links\\\":[{\\\"ref\\\":\\\"flow\\\",\\\"href\\\":\\\"https://www.pmtappsvc.stg.stratus.qa.ebay.com/pasvc/v1/_flow/5E500A8F000000012A06D0FC\\\"},{\\\"ref\\\":\\\"self\\\",\\\"href\\\":\\\"https://www.pmtappsvc.stg.stratus.qa.ebay.com/pasvc/v1/_op/11B910A6000000012A070864\\\"}],\\\"responseStatus\\\":\\\"OK\\\",\\\"flowId\\\":\\\"5E500A8F000000012A06D0FC\\\",\\\"opId\\\":\\\"11B910A6000000012A070864\\\"}";
		System.out.println(str.substring(str.indexOf("payment_type"), str.indexOf("payment_type")+40));*/

		List<String> list = new ArrayList();
		for (String l : list) {
			System.out.println(l);
		}

	}
}
