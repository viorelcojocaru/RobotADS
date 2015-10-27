package test;

import junit.framework.TestCase;
import model.Ads;
import model.User;

import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

public class TestSaveUser extends TestCase{

	public void testSave(){
//		User user = new User();
//		user.setName("apple122");
//		user.setPassword("1234562722");
//		Session session = HibernateUtil.getSessionFactory().openSession();
//		Transaction tx = session.beginTransaction();
//		session.save(user);
//		tx.commit();
//		session.close();
//		HibernateUtil.shutdown();
                
                Ads ads = new Ads();
		ads.setAdType("ap312plsdbe122");
		ads.setDescription("12332m121ddd22");
                ads.setTitle("tit132qwqwln1el meu");
                ads.setPhoneNumber("7873698");
                ads.setLocationSelector("jas1nm23S");
                ads.setMapAddress("wjekjf3lfvnklew");
                ads.setPriceAmount("87728");
                ads.setPriceType("typje3 ");
                ads.setYoutubeVideoUrl("sjdhjjfjsk1");
                ads.setFileUploadInput("file huploader1");
                Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.save(ads);
		tx.commit();
		session.close();
		HibernateUtil.shutdown();
	}
}
