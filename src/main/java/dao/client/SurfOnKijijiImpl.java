package dao.client;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SurfOnKijijiImpl implements SurfOnKijijiIntf{
	private List<String> cookies;
	private HttpsURLConnection conn;
	private final String USER_AGENT = "Mozilla/5.0";
	
	public SurfOnKijijiImpl() {
		CookieHandler.setDefault(new CookieManager());
	}
	@Override
	public int sendPost(String url, String postParams,String referer ) throws Exception {
		URL obj = new URL(url);
		conn = (HttpsURLConnection) obj.openConnection();
//		 Acts like a browser
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Host", url);// accounts.google.com
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		conn.setRequestProperty("Accept-Language", "en-CA,en;q=0.5");
		for (String cookie : this.cookies) {
			conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
		}
		conn.setRequestProperty("Connection", "keep-alive");
//		conn.setRequestProperty("Referer",referer );//"https://www.kijiji.ca/t-login.html"// https://accounts.google.com/ServiceLoginAuth
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));
		conn.setRequestProperty("Location", "toString(postParams.length())");
		conn.setDoOutput(true);
		conn.setDoInput(true);

//		 Send post request
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(postParams);
		wr.flush();
		wr.close();
		int responseCode = conn.getResponseCode();
		
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + postParams);
		System.out.println("Response Code : " + responseCode);

		return responseCode;
	}

	@Override
	public String getPageContentHttps(String url) throws Exception {
		URL obj = new URL(url);
		conn = (HttpsURLConnection) obj.openConnection();
//		 default is GET
		conn.setRequestMethod("GET");

		conn.setUseCaches(false);
//		 act like a browser
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		conn.setRequestProperty("Accept-Language", "en-CA,en;q=0.5");
		if (cookies != null) {
			for (String cookie : this.cookies) {
				conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
			}
		}
		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
//		 Get the response cookies
		setCookies(conn.getHeaderFields().get("Set-Cookie"));
		return response.toString();
	}

	@Override
	public String getLoginFormParams(String html, String username,
			String password) throws UnsupportedEncodingException {
		System.out.println("Extracting form's data...");

		Document doc = Jsoup.parse(html);

//		Google form id
		Element loginform = doc.getElementById("login-form");
		Elements inputElements = loginform.getElementsByTag("input");
		List<String> paramList = new ArrayList<String>();
		for (Element inputElement : inputElements) {
			String key = inputElement.attr("name");
			String value = inputElement.attr("value");

			if (key.equals("emailOrNickname"))// Email
				value = username;
			else if (key.equals("password"))// Passwd
				value = password;
			paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
		}

//		 build parameters list
		StringBuilder result = new StringBuilder();
		for (String param : paramList) {
			if (result.length() == 0) {
				result.append(param);
			} else {
				result.append("&" + param);
			}
		}
		return result.toString();
	}

	@Override
	public String getAdsFormParams(String htmlAdsPage, Map<String, String> ads) throws UnsupportedEncodingException {
		System.out.println("Extracting PostAdMainForm's data...");
		Document doc = Jsoup.parse(htmlAdsPage);
		// for input
		Element adsMainForm = doc.getElementById((String)ads.get("PostAdMainForm"));
		Elements inputElements = adsMainForm.getElementsByTag("input");
		List<String> paramList = new ArrayList<String>();
		
		for (Element inputElement : inputElements) {
			String key = inputElement.attr("name");
			String value = inputElement.attr("value");
			if (ads.containsKey(key)) {
				value = (String) ads.get(key);
			}
			paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
		}
		
		// end input
		// build parameters list
		StringBuilder result = new StringBuilder();
		for (String param : paramList) {
			if (result.length() == 0) {
				result.append(param);
			} else {
				result.append("&" + param);
			}
		}
		return result.toString();
	}
	
	public String postFile(String url, String filePath) throws JSONException ,IOException{
		String urlToConnect = url;
		
		File fileToUpload = new File(filePath);
		
		String  fileName =fileToUpload.getName();
		String paramToSend = fileToUpload.getName();
		String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
		HttpURLConnection conn;
		URL obj = new URL(url);
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true); // This sets request method to POST.
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		PrintWriter writer = null;
		try {
		    writer = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));

		    writer.println("--" + boundary);
		    writer.println("Content-Disposition: form-data; name=\"name\"");
		    writer.println("Content-Type: text/plain; charset=UTF-8");
		    writer.println();
		    writer.println(paramToSend);//\"fileName\""

		    writer.println("--" + boundary);
		    writer.println("Content-Disposition: form-data; name=\"fileToUpload\"; filename=\"pepsi.jpg\"");
		    writer.println("Content-Type: text/plain; charset=UTF-8");
		    writer.println();
		    BufferedReader reader = null;
		    try {
		        reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileToUpload), "UTF-8"));
		        for (String line; (line = reader.readLine()) != null;) {
		            writer.println(line);
		        }
		    } finally {
		        if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
		    }

		    writer.println("--" + boundary + "--");
		} finally {
		    if (writer != null) writer.close();
		}

		// Connection is lazily executed whenever you request any status.
		int responseCode;		
			responseCode = conn.getResponseCode();		
		System.out.println(responseCode); // Should be 200	
//		System.out.println(conn.getHeaderField("thumbnailUrl"));
		
		InputStream stream =  conn.getInputStream();
		InputStreamReader isReader = new InputStreamReader(stream ); 
       

        //put output stream into a string
       /* BufferedReader br = new BufferedReader(isReader);
        String result="";
        String line;
        while ((line = br.readLine()) != null) {
        System.out.println(line);
        result+= line;
        }

	 br.close();*/
	 conn.disconnect();String thumbnailUrl="";
	 int status = ((HttpURLConnection) conn).getResponseCode();
		System.out.println("Status: " + status);
		if(status==200){
			for (Map.Entry<String, List<String>> header : conn
					.getHeaderFields().entrySet()) {
				System.out.println(header.getKey() + "=" + header.getValue());
			}
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = jsonParser.getJSONFromInputStream(conn
					.getInputStream());
			 thumbnailUrl = jsonObj.get("thumbnailUrl").toString();	
		}
    
		return thumbnailUrl;
	}
	
	public List<String> getCookies() {
		return cookies;
	}

	public void setCookies(List<String> cookies) {
		this.cookies = cookies;
	}

	public  String postDataRequestToUrl(String url, String paramName, String fileName, byte[] requestFileData) throws IOException {
		 URLConnection connection=null;
		 String thumbnailUrl="";
		 try{
		     String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
		     String charset = "utf-8";

		     connection = new URL(url).openConnection();
		     connection.setDoOutput(true);
		     //connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
//		     connection.getHeaderField("thumbnailUrl");
		     connection.setRequestProperty("Content-Type", "image/jpeg; boundary="+boundary);
		     PrintWriter writer = null;
		     OutputStream output = null;
		     try {
		         output = connection.getOutputStream();
		         writer = new PrintWriter(new OutputStreamWriter(output, charset), true); // true = autoFlush, important!

		         // Send binary file.
		         writer.println("--" + boundary);
		         writer.println("Content-Disposition: form-data; name=\""+paramName+"\"; filename=\"" + fileName + "\"");
		         writer.println("Content-Type: " + URLConnection.guessContentTypeFromName(fileName));
		         writer.println("Content-Transfer-Encoding: binary");
		         writer.println();

		         output.write(requestFileData, 0, requestFileData.length);
		         output.flush(); // Important! Output cannot be closed. Close of writer will close output as well.

		         writer.println(); // Important! Indicates end of binary boundary.

		         // End of multipart/form-data.
		         writer.println("--" + boundary + "--");
		     } finally {
		         if (writer != null) writer.close();
		         if (output != null) output.close();
		     }

		     //*  screw the response

		     int status = ((HttpURLConnection) connection).getResponseCode();
		     
		     if(status==200){
		    	 System.out.println("Status: "+status);
		    	 for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
			         System.out.println(header.getKey() + "=" + header.getValue());
			     }
		    	 JSONParser jsonParser=new JSONParser();
		    	 JSONObject jsonObj=jsonParser.getJSONFromInputStream(connection.getInputStream());
		    	 thumbnailUrl=jsonObj.get("thumbnailUrl").toString();
		     }
		     

		 } catch(Throwable e) {
		    System.out.println("Problem "+e);
		 }
		 return thumbnailUrl;
		 }
	
	public String getPageContentHttp(String url) throws IOException{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);		
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
				
	}
	
	public String getHtmlPage(String url) throws IOException {
		URL url2 = new URL(url);
		InputStream is = url2.openStream();
		int ptr = 0;
		StringBuffer buffer = new StringBuffer();
		while ((ptr = is.read()) != -1) {
		    buffer.append((char)ptr);
		}
		return buffer.toString();
	}
	
	public  void postAppahe(String url){
		HttpClient client = new HttpClient();
	    client.getParams().setParameter("User-Agent", USER_AGENT);
	
	    BufferedReader br = null;
	
	    PostMethod method = new PostMethod(url);
	    method.addRequestHeader("emailOrNickname", "balishoi");
	    method.addRequestHeader("password","123456789");
	
	    try{
	      int returnCode = client.executeMethod(method);
	
	      if(returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
	        System.err.println("The Post method is not implemented by this URI");
	        // still consume the response body
	        method.getResponseBodyAsString();
	      } else {
	        br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
	        String readLine;
	        while(((readLine = br.readLine()) != null)) {
	          System.err.println(readLine);
	      }
	      }
	    } catch (Exception e) {
	      System.err.println(e);
	    } finally {
	      method.releaseConnection();
	      if(br != null) try { br.close(); } catch (Exception fe) {}
	    }
	}
        
        
	public String getMyAdsId(String url,String cookie) throws JSONException, IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection(); 
		// optional default is GET
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept","text/html,application/xhtml+xml,application/json,application/xml;q=0.9,*/*;q=0.8");
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);//+"(Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0"
		con.setRequestProperty("Accept-Language", "en-CA,en;q=0.5");
		con.setRequestProperty("Content-Encoding", "gzip, deflate");
		
		con.addRequestProperty("Cookie", cookie);
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		String adId="";
		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			try {
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObj= jsonParser.getJSONFromInputStream(con
						.getInputStream());				
				if(jsonObj!=null){
					String substing = jsonObj.get("myAdEntries").toString().substring(jsonObj.get("myAdEntries").toString().indexOf("adId=")+5);
					adId=substing.substring(0,substing.indexOf("\","));					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				in.close();
			}
		}		
		return adId;		
	}
        
        public void deleteAd(String id ,String cookie) throws JSONException, IOException{
		String url="http://www.kijii.ca/j-delete-ad.json";
		JSONObject json = new JSONObject();
		json.put("Action","DELETE_ADS");
		json.put("Mode","ACTIVE");
		json.put("ca.kijiji.xsrf.token","1436280204505.62d610e491ed32d798cfcdf00288acbb");
		JSONObject ads = new JSONObject();
		ads.put("adId",id);
		ads.put("reason", "PREFER_NOT_TO_SAY"); 
		ads.put("otherReason","");
		json.put("ads", ads);
		/*
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
	    parameters.add(new BasicNameValuePair("adId", ""+id));
	    parameters.add(new BasicNameValuePair("reason", "PREFER_NOT_TO_SAY"));
	    parameters.add(new BasicNameValuePair("otherReason", ""));
	    List<BasicNameValuePair> parametersAds = new ArrayList<BasicNameValuePair>();
	    parametersAds.add(new BasicNameValuePair("Action","DELETE_ADS"));
	    parametersAds.add(new BasicNameValuePair("Mode","ACTIVE"));
	    parametersAds.addAll(parameters);*/
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {
			HttpPost request = new HttpPost(url);
		    StringEntity params = new StringEntity(json.toString());		    
		    request.addHeader("content-type", "application/json");
		    request.setHeader("Content-Encoding", "gzip, deflate");
		    request.addHeader("Cookie", cookie);
		    request.setEntity(params);
//		    request.setEntity("Action=DELETE_ADS&Mode=ACTIVE&needsRedirect=false&ads=%5B%7B%22adId%22%3A%221051575047%22%2C%22reason%22%3A%22PREFER_NOT_TO_SAY%22%2C%22otherReason%22%3A%22%22%7D%5D&ca.kijiji.xsrf.token=1436280204505.62d610e491ed32d798cfcdf00288acbb"); 
//		    HttpParams httpparams= new StringEntity(json.toString());		   
//		    request.setEntity(new UrlEncodedFormEntity(parametersAds));
		   
		   
		 
//			InputStream is = request.getEntity().getContent();
//			int ch;
//			StringBuilder sb = new StringBuilder();
//			while ((ch = is.read()) != -1)
//				sb.append((char) ch);
//			System.out.println(sb.toString());
			
		HttpResponse httpResponse =httpClient.execute(request);
	    int statusCode = httpResponse.getStatusLine().getStatusCode();
	    System.out.println("HTTP Status Code: "+statusCode);
	    System.out.println("httpResponse "+ httpResponse.getStatusLine().getProtocolVersion());
		} catch (Exception ex) {
		   ex.printStackTrace();
		} finally {
		    httpClient.close();
		}
		
		
//	    HttpPost httppost=new HttpPost(url);
//	    httppost.setEntity(new UrlEncodedFormEntity(parameters));
//System.out.println(httppost.getURI());
//System.out.println(httppost.getEntity());
//	    HttpClient httpclient = new DefaultHttpClient();
//	    HttpResponse httpResponse = httpclient.execute(httppost);
//	    HttpEntity resEntity = httpResponse.getEntity();
//	    
////Get the HTTP Status Code
//	    
//
////Get the contents of the response
//	    InputStream input = resEntity.getContent();
////String responseBody = IOUtils.toString(input);
//	    input.close();
//	    
////Print the response code and message body
//	    
////System.out.println(responseBody);
    }
	
        public String uploadDataGZIPRequestToUrl(String url, String fileName,
			byte[] requestFileData) {
		String thumbnailUrl = "";
		URLConnection connection = null;		  
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		GZIPOutputStream gzos = null;
		PrintWriter writer = null;
		OutputStream output = null;
		try {
			baos.write(requestFileData);
			gzos = new GZIPOutputStream(baos);
			//gzos.write(requestFileData);
			
			String boundary = String.valueOf(System.currentTimeMillis());// Long.toHexString
			// Just generate some unique random value.
			String charset = "utf-8";

			connection = new URL(url).openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + boundary);
			connection.setRequestProperty("Content-Encoding", "gzip");
			output = connection.getOutputStream();			
			byte[] fooGzippedBytes = baos.toByteArray();
//			output.write(fooGzippedBytes, 0, fooGzippedBytes.length);
			
			writer = new PrintWriter(new GZIPOutputStream(output), true);
			// true = autoFlush, important!
			// Send binary file.
			output.flush();
			writer.println("--" + boundary);
			writer.println("Content-Disposition: form-data; name=\""
					+ "file" + "\"; filename=\"" + fileName + "\"");
			writer.println("Content-Type: "
					+ URLConnection.guessContentTypeFromName(fileName));
			writer.println("Content-Encoding: gzip");
			writer.println();
			writer.println("--" + boundary + "--");			
			gzos.write(fooGzippedBytes);
			gzos.flush(); 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null)
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (writer != null)
				writer.close();
			if (gzos != null)
				try {
					gzos.close();
				} catch (IOException ignore) {
				}
			
		}
		try {
			int status = ((HttpURLConnection) connection).getResponseCode();
			System.out.println("Status: " + status);
			if (status == 200) {
				for (Map.Entry<String, List<String>> header : connection
						.getHeaderFields().entrySet()) {
					System.out.println(header.getKey() + "="
							+ header.getValue());
				}
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObj = jsonParser.getJSONFromInputStream(connection
						.getInputStream());
				thumbnailUrl = jsonObj.get("thumbnailUrl").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return thumbnailUrl;
	}

    public String uploadDataRequestToUrl(String url, String paramName, String fileName, byte[] requestFileData) throws IOException {
        	URLConnection connection = null;
		String thumbnailUrl = "";
		try {
			String boundary = String.valueOf(System.currentTimeMillis());//Long.toHexString
			// Just generate some unique random value.
			String charset = "utf-8";
			
			connection = new URL(url).openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + boundary);			
		
			PrintWriter writer = null;
			OutputStream output = null;
			try {
				output = connection.getOutputStream();
				writer = new PrintWriter(
						new OutputStreamWriter(output, charset), true);
				// true = autoFlush, important!
				// Send binary file.
				writer.println("--" + boundary);
				writer.println("Content-Disposition: form-data; name=\""
						+ paramName + "\"; filename=\"" + fileName + "\"");
				writer.println("Content-Type: "
						+ URLConnection.guessContentTypeFromName(fileName));
				writer.println("Content-Transfer-Encoding: binary");
				writer.println();
				output.write(requestFileData, 0, requestFileData.length);
				output.flush();
				// Important! Output cannot be closed. Close of
				// writer will close output as well.

				writer.println();
				// Important! Indicates end of binary boundary.
				// End of multipart/form-data.
				writer.println("--" + boundary + "--");
			} finally {
				if (writer != null)
					writer.close();
				if (output != null)
					output.close();
				
			}
			
			// * screw the response
			int status = ((HttpURLConnection) connection).getResponseCode();
			System.out.println("Status: " + status);
			if(status==200){
				for (Map.Entry<String, List<String>> header : connection
						.getHeaderFields().entrySet()) {
					System.out.println(header.getKey() + "=" + header.getValue());
				}
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObj = jsonParser.getJSONFromInputStream(connection
						.getInputStream());
				thumbnailUrl = jsonObj.get("thumbnailUrl").toString();	
			}
		} catch (Throwable e) {
			System.out.println("Problem " + e);
		}
		return thumbnailUrl;
    }
    
}
