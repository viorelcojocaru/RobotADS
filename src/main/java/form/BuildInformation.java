package form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Ads;
import model.User;
import model.map.Advertisement;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.AdsImpl;
import dao.AdsIntf;
import dao.UserImpl;
import dao.UserIntf;

public class BuildInformation implements Runnable{
	String sleep;
	public BuildInformation(String sleep) {
		this.sleep=sleep;
	}
	private Advertisement getAdsXml() {		
		Advertisement adsxml = null;
		ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(
				"Beans.xml");		
		adsxml = new Advertisement();
		adsxml = (Advertisement) ctx.getBean("ads");
		ctx.close();
		return adsxml;
	}
	
	private   List<Advertisement> getAdvertisementList() {
		Advertisement adsXml = getAdsXml();
		AdsIntf adsBc = AdsImpl.getInstance();
		List<Advertisement> advertismentMapList = new ArrayList<Advertisement>();
		List<Ads> bdAdsList = new ArrayList<Ads>();
		bdAdsList = adsBc.getAdsList();
		
		Map<String, String> tempInputAdsForm;
		Map<String, String> tempLinksAdsMap=adsXml.getLinksAdsMap();
		List<String> tempStepsLinks=adsXml.getStepsLinks();
		
		if (bdAdsList != null && !bdAdsList.isEmpty()) {
			for (Ads oneAds : bdAdsList) {
				tempInputAdsForm=new HashMap<String, String>();//= adsXml.getInputAdsForm();
				tempInputAdsForm.putAll(adsXml.getInputAdsForm());
				Advertisement a = new Advertisement();				
				tempInputAdsForm.put("postAdForm.title", oneAds.getTitle());
				tempInputAdsForm.put("postAdForm.youtubeVideoURL", oneAds.getYoutubeVideoUrl());
				tempInputAdsForm.put("postAdForm.phoneNumber", oneAds.getPhoneNumber());
				tempInputAdsForm.put("postAdForm.mapAddress", oneAds.getMapAddress());
				tempInputAdsForm.put("postAdForm.description", oneAds.getDescription());
				tempInputAdsForm.put("file", oneAds.getImagePath());				
				a.setInputAdsForm(tempInputAdsForm);
				a.setLinksAdsMap(tempLinksAdsMap);
				a.setStepsLinks(tempStepsLinks);
				advertismentMapList.add(a);
			}
		}
		return advertismentMapList;
	}
    
        private User getUser(){
            UserIntf userBc=new UserImpl();
           User user= userBc.getUserById(1);
           return user;
        }

	void postAds() {
		LoginDeletePostSingOutKijiji loginKijiji = new LoginDeletePostSingOutKijiji();
		List<Advertisement> listaXml = getAdvertisementList();

		int i = 0;
		int j = 0;
		while (i < 10) {
			if (j == listaXml.size()) {
				j = 0;
			}
			Advertisement oneAdsXml = listaXml.get(j);
			j++;
			try {
				loginKijiji.setAdsKijiji(oneAdsXml, getUser());
				loginKijiji.loginInAccount();
				long random1 = (long) (Math.random() * (2 - 10) + 10);
				Thread.sleep(random1 * 1000L);
				
				loginKijiji.deleteAds();
				long random2 = (long) (Math.random() * (2 - 10) + 10);
				Thread.sleep(random2 * 1000L);
				
				loginKijiji.postAds();
				++i;
				Thread.sleep(stringMinToMilisecond(sleep));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private long stringMinToMilisecond(String strMin) {
		long intMin = Long.parseLong(strMin);
		long milisecond = 1000;
		if (intMin <= 0) {
			intMin = 1;
		} else {
			milisecond = intMin * 60000;
		}
		return milisecond;
	}

	public static void main(String[] args) {
//		User user = new User();
//		UserIntf userBc = UserImpl.getInstance();
//		user = userBc.getUserById(1);
//		List<Advertisement> list=new ArrayList<Advertisement>();
		//list.addAll(getAdvertisementList);
//		LoginDeletePostSingOutKijiji loginKijiji = new LoginDeletePostSingOutKijiji();
//		int i = 0;
//		while (i < 10) {
//			try {
////				loginKijiji.getAdsKijiji(ads, user);
////				loginKijiji.run();
//                            System.out.println("delete");
//                            Thread thread = new Thread();
//				thread.sleep(2000L);
//                            System.out.println("post");
//				++i;
//				
//				thread.sleep(5000L);
//
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//		}
		
		
	}

    public void run() {
        postAds(); 
    }
}
