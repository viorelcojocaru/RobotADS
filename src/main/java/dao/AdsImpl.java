/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Ads;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author Viorel
 */
public class AdsImpl implements AdsIntf {

    private static AdsImpl instance;

    private AdsImpl() {
    }

    public static AdsImpl getInstance() {
        if (instance == null) {
            instance = new AdsImpl();
        }
        return instance;
    }

    public List<Ads> getAdsList() {
        List<Ads> adsList;
        List obiects;
        Session session = HibernateUtil.getSessionFactory().openSession();
        obiects = session.createQuery("FROM Ads").list();
        Transaction tx = session.beginTransaction();
        tx.commit();
        session.close();
        HibernateUtil.shutdown();

        adsList = new ArrayList<Ads>();
        for (Iterator iterator = obiects.iterator(); iterator.hasNext();) {
            Ads ads = (Ads) iterator.next();
            adsList.add(ads);
        }

        if (adsList.size() > 0) {
            return adsList;
        }
        return null;
    }

    public Ads getAdsById(int id) {
        List obiects;
        Session session = HibernateUtil.getSessionFactory().openSession();
        obiects = session.createQuery("FROM Ads WHERE id=" + id).list();
        Transaction tx = session.beginTransaction();
        tx.commit();
        session.close();
        HibernateUtil.shutdown();
        if (!obiects.isEmpty()) {
            Ads ads = (Ads) obiects.get(0);
            return ads;
        }
        return null;
    }

    public void saveAds(Ads ads) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(ads);
        tx.commit();
        session.close();
        HibernateUtil.shutdown();
    }

    public void updateAds(Ads ads) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(ads);
        tx.commit();
        session.close();
        HibernateUtil.shutdown();
    }

    public void deleteAds(Ads ads) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(ads);
        tx.commit();
        session.close();
        HibernateUtil.shutdown();
    }

    public static void main(String[] args) {
//        System.out.println(getInstance().getAdsList());
//        Ads ads = new Ads();
	/*	ads.setAdType("applea122wefdkjw");
         ads.setDescription("123sa4562722kjhjwef");
         ads.setTitle("titlel meas342ueee");
         ads.setPhoneNumber("7873424298378793938");
         ads.setLocationSelector("jassafeeqaeDJAS");
         ads.setMapAddress("wjekjfkafeelwklfvklew");
         ads.setPriceAmount("845723138");
         ads.setPriceType("ty23eepe ");
         ads.setYoutubeVideoUrl("sjd2112heejfjsk");
         ads.setFileUploadInput("file2112 upeeloader");
         ads.setImagePath("jfkdsakdfskfdsk");
         getInstance().saveAds(ads);
         */
//              System.out.println(  getInstance().getAdsList());
//        getInstance().deleteAds(ads);
//            System.out.println(  getInstance().getAdsById(5));

//        int id = 3;
//        Ads ads = new Ads();
//        ads = getInstance().getAdsById(id);
//        if (ads != null) {
//            ads.setDescription("Descriere noua update");
//            System.out.println(ads);
//        } else {
//            System.out.println("no such Ads with id=" + id);
//        }

//           getInstance().deleteAds(ads);
    }
}
