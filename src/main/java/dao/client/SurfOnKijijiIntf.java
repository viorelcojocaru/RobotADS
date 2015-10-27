package dao.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Map;
import org.json.JSONException;

public interface SurfOnKijijiIntf {
	
	public int sendPost(String url, String postParams, String referer) throws Exception;
	
	public String getPageContentHttps(String url) throws MalformedURLException, Exception;
	
	public String getLoginFormParams(String html, String username, String password) throws UnsupportedEncodingException;
	
	public String getAdsFormParams(String htmlAdsPage, Map<String, String> ads) throws UnsupportedEncodingException;
	
	public String postFile(String url, String filePath) throws JSONException, IOException;
	
	public  String postDataRequestToUrl(String url, String paramName, String fileName, byte[] requestFileData) throws IOException;

	public String getPageContentHttp(String url) throws IOException;
	
	public String getHtmlPage(String url) throws Exception;
	
	public void postAppahe(String url);
        
        public String uploadDataGZIPRequestToUrl(String url,String fileName, byte[] requestFileData);
        
        public  String uploadDataRequestToUrl(String url, String paramName, String fileName, byte[] requestFileData) throws IOException;
        
        public String getMyAdsId(String string,String cookie) throws JSONException, IOException;
        
        public void deleteAd(String id , String cookie) throws JSONException, IOException;
}
