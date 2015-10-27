package form;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import model.User;
import model.map.Advertisement;
import dao.client.SurfOnKijijiImpl;
import dao.client.SurfOnKijijiIntf;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.JFileChooser;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LoginDeletePostSingOutKijiji {

    private User user;
    private Advertisement ads;
    private String url;
    private String home;
    SurfOnKijijiIntf surfOnKijiji;

    public LoginDeletePostSingOutKijiji() {

    }

    public void run() {
		surfOnKijiji=new SurfOnKijijiImpl();
		try {
			loginInAccount();
			//User Loghed
			
			
			if (existAds()) {
				deleteAds(); 
			}else{
				postKijijiAccountAds();
			}
			String propertiesFilePath="D:/Users/vcojocaru/workspace/Kijiji/src/resources/advertisment.properties";
			updateAds(user, ads ,propertiesFilePath);
		  //end posting Ads
//			
			
			
			
//			String conten =surfOnKijiji.getPageContentHttp(home+ ads.getLinksAdsMap().get("my.kijiji"));
//			surfOnKijiji.getPageContentHttp(home+ ads.getLinksAdsMap().get("my.kijiji"));
//			System.out.println(conten);
			
//			String resultPostHtmlAdsForm = surfOnKijiji.getPageContentHttps(home+ ads.getLinksAdsMap().get("my.kijiji"));
//			System.out.println("!!!!!!!!!!!!"+resultPostHtmlAdsForm);
//			
//			
//			System.out.println("Login Page "+url+ user.getRedirectUrl()+"/n"+surfOnKijiji.getHtmlPage(url+ user.getRedirectUrl()));
			
			

			
//			System.out.println("jsonKijiji "+home+ "/j-get-my-ads.json"+"/n"
//					+surfOnKijiji.getHtmlPage(home+ "/j-get-my-ads.json"));
			
//			System.out.println(surfOnKijiji.getPageContent(home+ads.getLinksAdsMap().get("my.kijiji").toString()));
//			
//			System.out.println(surfOnKijiji.getPageContentHttps(home+ads.getLinksAdsMap().get("sing.out").toString()));
//			
//			
			
//			System.out.println();
		
		
		
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		} catch (Exception e) {			
			e.printStackTrace();
		}
				
		
//		postAdsOnKijiji.getLoginFormParams(html, username, password)
		
		
//		System.out.println(user.getLogin());
//		System.out.println(user.getPassword());
//		System.out.println(user.getRedirectUrl());
//
//		System.out.println("----------------------------------------------");
//
//		Map<String, Object> inputAdsForm = ads.getInputAdsForm();
//		for (Map.Entry<String, Object> key : inputAdsForm.entrySet()) {
//			System.out.println(key.getKey() + "=" + key.getValue().toString());
//		}
//
//		System.out.println("----------------------------------------------");
//		Map<String, String> mpAdsLinks = ads.getLinksAdsMap();
//		for (Map.Entry<String, String> key : mpAdsLinks.entrySet()) {
//			System.out.println(key.getKey() + "=" + key.getValue());
//		}
//		System.out.println("----------------------------------------------");
//		List<String> linkList = ads.getStepsLinks();
//		for (String oneLink : linkList) {
//			System.out.println(oneLink);
//		}
		/*//			System.out.println(htmlAdsPage.length());
//			StringBuilder contentBuilder = new StringBuilder();
//			
//			    BufferedReader in = new BufferedReader(new FileReader("./formpage.html"));
//			    String str;
//			    while ((str = in.readLine()) != null) {
//			        contentBuilder.append(str);
//			    }
//			    in.close();
//			
//			htmlAdsPage = contentBuilder.toString();
//			
			
			
			
//			System.out.println(content);
//			System.out.println(htmlAdsPage.indexOf("\"id=\"ImageUpload\""));
//			String s1=htmlAdsPage.substring(0, htmlAdsPage.indexOf("\"id=\"ImageUpload\""));
//			System.out.println(s1);
//			String s2=htmlAdsPage.substring(s1.length()+content.length());
//			System.out.println(s2);
			
//			System.out.println(htmlAdsPage);*/
		
	}
    
    public void setAdsKijiji(Advertisement ads, User user) {
        this.user = user;
        this.ads = ads;
        url = ads.getLinksAdsMap().get("link.home").toString() + ads.getLinksAdsMap().get("login.page").toString();
        home = ads.getLinksAdsMap().get("link.home").toString();
        surfOnKijiji = new SurfOnKijijiImpl();
    }

    private boolean existAds() throws Exception {
		surfOnKijiji.getPageContentHttp(home+ ads.getLinksAdsMap().get("my.kijiji"));
		
		return false;
	}
    
    private void postKijijiAccountAds() throws MalformedURLException, Exception {
		//set location
		surfOnKijiji.getPageContentHttps(ads.getLinksAdsMap().get("link.home").toString()+
				ads.getLinksAdsMap().get("set.location").toString());
		
//		surfOnKijiji.getPageContentHttps(home+ads.getLinksAdsMap().get("select.category"));
		
		//postAds
		String htmlAdsPage=surfOnKijiji.getPageContentHttps(home+ads.getLinksAdsMap().get("post.an.ad")+ads.getLinksAdsMap().get("category.id"));			
		
		Map<String , String> formParams =new HashMap<String , String>();
		formParams.putAll(ads.getInputAdsForm());
		
		String postHtmlAdsForm=surfOnKijiji.getAdsFormParams(htmlAdsPage, formParams);
		
		String imagesStrParmas="";
		List<String> links=setFileChooser(".");
		
		for (String oneLink : links) {
			imagesStrParmas=imagesStrParmas+"&"+"images="+URLEncoder.encode(oneLink, "UTF-8");
		}
		String strend=postHtmlAdsForm.substring(postHtmlAdsForm.indexOf("images")+7);
		postHtmlAdsForm=postHtmlAdsForm.substring(0, postHtmlAdsForm.indexOf("images")+7)+imagesStrParmas+strend;
		
		surfOnKijiji.sendPost(home+ads.getLinksAdsMap().get("post.an.ad")+ads.getLinksAdsMap().get("category.id"),
		postHtmlAdsForm,home+ads.getLinksAdsMap().get("post.an.ad")+ads.getLinksAdsMap().get("category.id"));
		
	}
    
    public void loginInAccount() {
		try {
			String htmlHomeLoginPage = surfOnKijiji.getPageContentHttps(url);
			
			String postParams = surfOnKijiji.getLoginFormParams(
					htmlHomeLoginPage, user.getLogin(), user.getPassword());
			String referer = url;
			surfOnKijiji.sendPost(url, postParams, referer);
			// redirected user to account
			surfOnKijiji.getPageContentHttps(url + user.getRedirectUrl());

			// User Loghed
			// set location
			String htmlSetlocation = surfOnKijiji.getPageContentHttps(ads
					.getLinksAdsMap().get("link.home").toString()
					+ ads.getLinksAdsMap().get("set.location").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public void postAds() {       
        try{
            //postAds
            String htmlAdsPage = surfOnKijiji.getPageContentHttps(home + ads.getLinksAdsMap().get("post.an.ad") + ads.getLinksAdsMap().get("category.id"));
            System.out.println("--------" + htmlAdsPage);

            String postHtmlAdsForm = surfOnKijiji.getAdsFormParams(htmlAdsPage, ads.getInputAdsForm());
            System.out.println("____________" + postHtmlAdsForm);
			String imagesStrParams="";
			List<String> links=setFileChooser(".");
			for (String oneFileLink : links) {
				imagesStrParams=imagesStrParams+"&images="+URLEncoder.encode(oneFileLink,"UTF-8");
			}
			String strEnd=postHtmlAdsForm.substring(postHtmlAdsForm.indexOf("images")+7);
			postHtmlAdsForm=postHtmlAdsForm.substring(0,postHtmlAdsForm.indexOf("images")+7)+imagesStrParams+strEnd;

            surfOnKijiji.sendPost(home + ads.getLinksAdsMap().get("post.an.ad") + ads.getLinksAdsMap().get("category.id"),
                    postHtmlAdsForm, home + ads.getLinksAdsMap().get("post.an.ad") + ads.getLinksAdsMap().get("category.id"));

		  //end posting Ads
//			
            //surfOnKijiji.getPageContentHttp(home+ ads.getLinksAdsMap().get("my.kijiji"));
            surfOnKijiji.getPageContentHttp(home + ads.getLinksAdsMap().get("my.kijiji"));

//			String resultPostHtmlAdsForm = surfOnKijiji.getPageContentHttps(home+ ads.getLinksAdsMap().get("my.kijiji"));
//			System.out.println("!!!!!!!!!!!!"+resultPostHtmlAdsForm);
//			System.out.println("Login Page "+url+ user.getRedirectUrl()+"/n"+surfOnKijiji.getHtmlPage(url+ user.getRedirectUrl()));
//			System.out.println("myKijiji "+home+ ads.getLinksAdsMap().get("my.kijiji")+"/n"
//					+surfOnKijiji.getHtmlPage(home+ ads.getLinksAdsMap().get("my.kijiji")));
//			System.out.println("jsonKijiji "+home+ "/j-get-my-ads.json"+"/n"
//					+surfOnKijiji.getHtmlPage(home+ "/j-get-my-ads.json"));
//			System.out.println(surfOnKijiji.getPageContent(home+ads.getLinksAdsMap().get("my.kijiji").toString()));
//			
            System.out.println(surfOnKijiji.getPageContentHttps(home + ads.getLinksAdsMap().get("sing.out").toString()));
        


        } catch (Exception e) {
            e.printStackTrace();
        }

//		postAdsOnKijiji.getLoginFormParams(html, username, password)
//		System.out.println(user.getLogin());
//		System.out.println(user.getPassword());
//		System.out.println(user.getRedirectUrl());
//
//		System.out.println("----------------------------------------------");
//
//		Map<String, Object> inputAdsForm = ads.getInputAdsForm();
//		for (Map.Entry<String, Object> key : inputAdsForm.entrySet()) {
//			System.out.println(key.getKey() + "=" + key.getValue().toString());
//		}
//
//		System.out.println("----------------------------------------------");
//		Map<String, String> mpAdsLinks = ads.getLinksAdsMap();
//		for (Map.Entry<String, String> key : mpAdsLinks.entrySet()) {
//			System.out.println(key.getKey() + "=" + key.getValue());
//		}
//		System.out.println("----------------------------------------------");
//		List<String> linkList = ads.getStepsLinks();
//		for (String oneLink : linkList) {
//			System.out.println(oneLink);
//		}
    }
    
    public void deleteAds(){
       
        String adId="";//"1051575047"
		String url="https://www.kijiji.ca/j-get-my-ads.json?currentOffset=0&isPromoting=false&show=ACTIVE&user=81910674";//"https://www.kijiji.ca/m-my-ads.html";//"https://ca.classistatic.com/static/V/684/js/templates/my-items/survey.js";
    	try{
             
         surfOnKijiji.getPageContentHttp(home + ads.getLinksAdsMap().get("my.kijiji"));
          String cookie=   "machId=66bbfd22f713c186529e9cd876dadb8b739acf3c1a1e387c2c0a131fe1f0b3c3ae017d656f5173333bc001657edd37231d3bc17723c632273ce84c1b68cfdcb2; up=%7B%22ln%22%3A%22844591562%22%2C%22hDm%22%3A%221432195878472%22%2C%22ls%22%3A%22l%3D1700281%26r%3D0.0%26sv%3DLIST%26sf%3DdateDesc%22%2C%22rva%22%3A%221029065774%2C1026007327%2C1038078349%2C1069864218%2C1051575047%2C577228192%2C1073281337%2C1059277047%22%7D; __utma=115975897.36645435.1418028663.1432132237.1432195805.86; __utmz=115975897.1423055982.38.4.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); _ga=GA1.2.36645435.1418028663; __gads=ID=b1050d8420c9f627:T=1418028709:S=ALNI_MZJvbCpNVHMx-AcaTjcmNcF9kBHQw; __utmv=115975897.|5=hashI=-2654064223094925938728632312=1; ad_ids_s=Vsaved_s=; rememberMe=Vrme=true; svid=177502603066260679; dt=%7B%22dt%22%3A%22d%22%2C%22hua%22%3A%22-1510593819%22%7D; sid=H4sIAAAAAAAAABXJTQuCMBgA4L/yHuvQtndz8_siJQZFiiGMTjHUwBRXE1L69Rk8tyfAEJnyvYSjT5CvkKAIkuRiv90wGCoJg43uxsbOE_QVKIIx6EIrLwb3iYQgbAvHtu4t5QzZCiHrXPuwC/1npENXj3vzLNLTuS3nvlzet5ze0yyQUpjJP7xk5Sl_Nc28_wGZIRo6jgAAAA--; kjses=124fcb14-a37d-4f2e-aca7-05eabdde58ea^GRf1rJ4PceV5smkVytn16A==; siteLocale=fr_CA; bs=%7B%22st%22%3A%22%7B%7D%22%7D; JSESSIONID=0592EEDEDF1FF146A6EBD714EBAC3EA6; __utmc=115975897";
		   String delCookie="machId=66bbfd22f713c186529e9cd876dadb8b739acf3c1a1e387c2c0a131fe1f0b3c3ae017d656f5173333bc001657edd37231d3bc17723c632273ce84c1b68cfdcb2; up=%7B%22ln%22%3A%22844591562%22%2C%22hDm%22%3A%221432620357729%22%2C%22ls%22%3A%22l%3D1700281%26r%3D0.0%26sv%3DLIST%26sf%3DdateDesc%22%2C%22rva%22%3A%221029065774%2C1026007327%2C1038078349%2C1069864218%2C1051575047%2C577228192%2C1073281337%2C1059277047%22%7D; __utma=115975897.36645435.1418028663.1432306353.1432620375.91; __utmz=115975897.1423055982.38.4.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); _ga=GA1.2.36645435.1418028663; __gads=ID=b1050d8420c9f627:T=1418028709:S=ALNI_MZJvbCpNVHMx-AcaTjcmNcF9kBHQw; __utmv=115975897.|5=hashI=-2654064223094925938728632312=1; ad_ids_s=Vsaved_s=; rememberMe=Vrme=true; svid=177502603066260679; dt=%7B%22dt%22%3A%22d%22%2C%22hua%22%3A%22-1510593819%22%7D; sid=H4sIAAAAAAAAABXJTQuCMBgA4L/yHuvQtndz8_siJQZFiiGMTjHUwBRXE1L69Rk8tyfAEJnyvYSjT5CvkKAIkuRiv90wGCoJg43uxsbOE_QVKIIx6EIrLwb3iYQgbAvHtu4t5QzZCiHrXPuwC/1npENXj3vzLNLTuS3nvlzet5ze0yyQUpjJP7xk5Sl_Nc28_wGZIRo6jgAAAA--; kjses=8c580e81-032d-4984-b773-70ac70293a12^B0X3j1Nw8LVgoXCpu+0J1g==; siteLocale=en_CA; bs=%7B%22st%22%3A%22%7B%7D%22%7D; JSESSIONID=59998AA556D66A3932C5B0D320CF0136; __utmc=115975897 _gali=DeleteSurveyOK";
			adId=surfOnKijiji.getMyAdsId("http://www.kijiji.ca/j-get-my-ads.json?currentOffset=0&isPromoting=false&show=ACTIVE&user=81910674",cookie);
			surfOnKijiji.deleteAd(adId,delCookie);
    	}catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    void writeToFile(String content) {
        FileOutputStream fop = null;
        File file;
//		String content = "This is the text content";

        try {

            file = new File("d:\\message.html");
            fop = new FileOutputStream(file);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> setFileChooser(String dirPath) throws Exception {
		JFileChooser chooser = new JFileChooser();
		File dir = new File(dirPath);
		chooser.setCurrentDirectory(dir);
		File[] listFiles = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".jpg");
			}
		});
		List<String> fileUploadLinks = new ArrayList<String>();
		if (listFiles.length != 0) {
			chooser.setSelectedFiles(listFiles);
			for (File file : listFiles) {
				String link = "";
				try {
					link = surfOnKijiji.uploadDataRequestToUrl(
							"http://www.kijiji.ca/p-upload-image.html", "file",
							file.getName(), read(file));
//					link=surfOnKijiji.uploadDataGZIPRequestToUrl("http://www.kijiji.ca/p-upload-image.html", file.getName(), read(file));
					//					link=surfOnKijiji.postFile("http://www.kijiji.ca/p-upload-image.html", file.getCanonicalPath());
					fileUploadLinks.add(link);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fileUploadLinks;
	}

    private byte[] read(File file) throws IOException {
        byte[] buffer = new byte[(int) file.length()];
        InputStream ios = null;
        try {
            ios = new FileInputStream(file);
            if (ios.read(buffer) == -1) {
                throw new IOException("EOF reached while trying to read the whole file");
            }
        } finally {
            try {
                if (ios != null) {
                    ios.close();
                }
            } catch (IOException e) {
            }
        }

        return buffer;
    }

    private void buildUserAdsKijiji() {
		
		ConfigurableApplicationContext ctx = new  ClassPathXmlApplicationContext(
				"Beans.xml");
		user = new User();
		ads = new Advertisement();

		this.user = (User) ctx.getBean("user");
		this.ads = (Advertisement) ctx.getBean("ads");
		url=ads.getLinksAdsMap().get("link.home").toString()+ads.getLinksAdsMap().get("login.page").toString();
		home=ads.getLinksAdsMap().get("link.home").toString();
		
	}

    void updateAds(User user, Advertisement ads, String propertiesFilePath) throws IOException{		
		FileInputStream in = new FileInputStream(propertiesFilePath);
		Properties props = new Properties();
		props.load(in);
		in.close();
		FileOutputStream out = new FileOutputStream(propertiesFilePath);
		props.setProperty("user.login", user.getLogin());
		props.setProperty("user.password",user.getPassword());
		props.setProperty("user.redirectUrl",user.getRedirectUrl());
		props.setProperty("postAdForm.title",ads.getInputAdsForm().get("postAdForm.title").toString());
		props.setProperty("postAdForm.mapAddress",ads.getInputAdsForm().get("postAdForm.mapAddress").toString());
		props.setProperty("postAdForm.description",ads.getInputAdsForm().get("postAdForm.description").toString());		
		props.setProperty("postAdForm.image1",ads.getInputAdsForm().get("postAdForm.image1").toString());
		props.setProperty("postAdForm.image2",ads.getInputAdsForm().get("postAdForm.image2").toString());
		props.setProperty("postAdForm.image3",ads.getInputAdsForm().get("postAdForm.image3").toString());
		props.setProperty("postAdForm.youtubeVideoURL",ads.getInputAdsForm().get("postAdForm.youtubeVideoURL").toString());	
		props.setProperty("postAdForm.phoneNumber",ads.getInputAdsForm().get("postAdForm.phoneNumber").toString());
		props.store(out, null);
		out.close();		
	}
    
    public static void main(String[] args) {
//
//        LoginDeletePostSingOutKijiji logPostAds = new LoginDeletePostSingOutKijiji();
//        SurfOnKijijiIntf surfOnKijiji = new SurfOnKijijiImpl();
//        surfOnKijiji.postAppahe("https://www.kijiji.ca/t-login.html?targetUrl=L2gtdmlsbGUtZGUtbW9udHJlYWwvMTcwMDI4MV5CNWpqcnZpdzNiaktEOWJvRUpYbkd3PT0-");

//try {
//	logPostAds.setFileChooser();
//} catch (Exception e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}
//		System.out.println(logPostAds.user.getLogin());
//		System.out.println(logPostAds.user.getPassword());
//		System.out.println(logPostAds.user.getRedirectUrl());
//
//		System.out.println("----------------------------------------------");
//
//		Map<String, Object> inputAdsForm = logPostAds.ads.getInputAdsForm();
//		for (Map.Entry<String, Object> key : inputAdsForm.entrySet()) {
//			System.out.println(key.getKey() + "=" + key.getValue().toString());
//		}
//
//		System.out.println("----------------------------------------------");
//		Map<String, String> mpAdsLinks = logPostAds.ads.getLinksAdsMap();
//		for (Map.Entry<String, String> key : mpAdsLinks.entrySet()) {
//			System.out.println(key.getKey() + "=" + key.getValue());
//		}
//		System.out.println("----------------------------------------------");
//		List<String> linkList = logPostAds.ads.getStepsLinks();
//		for (String oneLink : linkList) {
//			System.out.println(oneLink);
//		}
    }

/*
    public  void openAppahe(){
		HttpClient client = new HttpClient();
	    client.getParams().setParameter("http.useragent", "Test Client");

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
	*/
}
