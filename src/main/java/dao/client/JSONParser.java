package dao.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;

public class JSONParser {
	static InputStream is=null;
	static JSONObject jObj=null;
	static String json;
	public JSONParser() {
		// TODO Auto-generated constructor stub
	}
	
	public JSONObject getJSONFromInputStream(InputStream is){
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb=new StringBuilder();
			String line=null;
			while((line=reader.readLine())!=null){
				sb.append(line+"\n");
				
			}
			is.close();
			json=sb.toString();
			jObj=new JSONObject(json);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return jObj;
	}

   
}
