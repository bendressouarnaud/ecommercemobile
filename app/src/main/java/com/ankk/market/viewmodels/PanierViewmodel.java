package com.ankk.market.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ankk.market.adapters.AdapterListPanier;
import com.ankk.market.beans.BeanActif;
import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.models.Achat;
import com.ankk.market.repositories.AchatRepository;
import com.ankk.market.repositories.BeanarticledetailRepository;
import com.ankk.market.repositories.ClientRepository;
import com.ankk.market.repositories.CommuneRepository;

import java.util.List;

public class PanierViewmodel extends ViewModel {

    AdapterListPanier adapter;
    BeanarticledetailRepository beanarticledetailRepository;
    AchatRepository achatRepository;


    //
    // M e t h o d s  :
    public PanierViewmodel(Application app) {
        beanarticledetailRepository = new BeanarticledetailRepository(app);
        achatRepository = new AchatRepository(app);
        adapter = new AdapterListPanier(app.getApplicationContext());
    }

    //
    public List<Beanarticledetail> getAllArticle(){
        return beanarticledetailRepository.getAll();
    }

    public List<Beanarticledetail> getAllArticleByIddet(int id){
        return beanarticledetailRepository.getAllByIddet(id);
    }

    // Get All ACHAT :
    public List<Achat> getAllByIdart(int idart){
        return achatRepository.getAllByIdart(idart);
    }

    public LiveData<List<BeanActif>> getAllLiveActif(){
        return achatRepository.getAllLiveActif();
    }

    public LiveData<List<Beanarticledetail>> getAllArticleLive(){
        return beanarticledetailRepository.getAllLive();
    }

    // Insert
    public void insertArticle(Beanarticledetail bl){
        beanarticledetailRepository.insert(bl);
    }

    public AdapterListPanier getAdapter() {
        return adapter;
    }

    public void setAdapter(AdapterListPanier adapter) {
        this.adapter = adapter;
    }

    public BeanarticledetailRepository getBeanarticledetailRepository() {
        return beanarticledetailRepository;
    }

    public void setBeanarticledetailRepository(BeanarticledetailRepository beanarticledetailRepository) {
        this.beanarticledetailRepository = beanarticledetailRepository;
    }

    public AchatRepository getAchatRepository() {
        return achatRepository;
    }

    public void setAchatRepository(AchatRepository achatRepository) {
        this.achatRepository = achatRepository;
    }
}
