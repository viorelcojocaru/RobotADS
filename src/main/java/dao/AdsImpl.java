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
import org.hibernate.HibernateException;
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

    public List<Ads> getAdsList() throws HibernateException{
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

        if (adsList!=null && !adsList.isEmpty()) {
            return adsList;
        }
        return null;
    }

    public Ads getAdsById(int id) throws HibernateException{
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

    public void saveAds(Ads ads) throws HibernateException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(ads);
        tx.commit();
        session.close();
        HibernateUtil.shutdown();
    }

    public void updateAds(Ads ads) throws HibernateException{
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(ads);
        tx.commit();
        session.close();
        HibernateUtil.shutdown();
    }

    public void deleteAds(Ads ads) throws HibernateException{
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(ads);
        tx.commit();
        session.close();
        HibernateUtil.shutdown();
    }
}
