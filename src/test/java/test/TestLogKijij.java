package test;

import dao.client.ManageKijijiAccountIntf;
import dao.client.ManageKijjiAccountImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;



public class TestLogKijij {

	public static void main(String[] args) {
		ManageKijijiAccountIntf command = new ManageKijjiAccountImpl();
		try {
			String strHtmlLoginPage = command
					.getPageContent("https://www.kijiji.ca/t-login.html");

			Map<String, String> params = new HashMap<String, String>();
			params.put("emailOrNickname", "balishoi");
			params.put("password", "123456789");
			Map<String, String> otherParams = new HashMap<String, String>();
			otherParams.put("login.form", "login-form");
			otherParams.put("input", "input");
			otherParams.put("referer", "?targetUrl=L2gtdmlsbGUtZGUtbW9udHJlYWwvMTcwMDI4MT9zaXRlTG9jYWxlPWVuX0NBXkY3RUhPcTk5amp2MENIMmw0ejQ4RVE9PQ-");
			
			List<NameValuePair> postParams = new ArrayList<NameValuePair>();

			postParams = command.getFormParams(strHtmlLoginPage, params, otherParams);
			System.out.println(postParams);
			command.sendPost("https://www.kijiji.ca/t-login.html", postParams, "https://www.kijiji.ca/t-login.html");
			
			System.out.println(command.getPageContent("https://www.kijiji.ca/t-login.html"+otherParams.get("referer")));
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
