package dao.client;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;


public interface ManageKijijiAccountIntf {

	public String getPageContent(String url) throws Exception;

	public List<NameValuePair> getFormParams(String html, Map<String, String> params, Map<String, String> otherParams) throws UnsupportedEncodingException;

	public void sendPost(String url, List<NameValuePair> postParams, String referer) throws Exception;
}
