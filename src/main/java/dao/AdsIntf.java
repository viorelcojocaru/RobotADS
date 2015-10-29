/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Ads;
import org.hibernate.HibernateException;

public interface AdsIntf {

    public List<Ads> getAdsList() throws HibernateException;

    public void saveAds(Ads ads)throws HibernateException;

    public void updateAds(Ads ads) throws HibernateException;

    public void deleteAds(Ads ads) throws HibernateException;
    
    public Ads getAdsById(int id) throws HibernateException;
}
