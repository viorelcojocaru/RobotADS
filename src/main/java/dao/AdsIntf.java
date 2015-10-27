/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Ads;

public interface AdsIntf {

    public List<Ads> getAdsList();

    public void saveAds(Ads ads);

    public void updateAds(Ads ads);

    public void deleteAds(Ads ads);
    
    public Ads getAdsById(int id);
}
