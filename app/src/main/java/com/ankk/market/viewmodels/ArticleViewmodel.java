package com.ankk.market.viewmodels;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.ankk.market.repositories.AchatRepository;
import com.ankk.market.repositories.BeanarticledetailRepository;
import com.ankk.market.repositories.CommandeRepository;

public class ArticleViewmodel extends ViewModel {

    // A t t r i b u t e s :
    BeanarticledetailRepository beanarticledetailRepository;
    AchatRepository achatRepository;
    int articleRestant;


    // M e t h o d s  :
    public ArticleViewmodel(Application app) {
        beanarticledetailRepository = new BeanarticledetailRepository(app);
        achatRepository = new AchatRepository(app);
    }

    public BeanarticledetailRepository getBeanarticledetailRepository() {
        return beanarticledetailRepository;
    }

    public int getArticleRestant() {
        return articleRestant;
    }

    public void setArticleRestant(int articleRestant) {
        this.articleRestant = articleRestant;
    }

    public AchatRepository getAchatRepository() {
        return achatRepository;
    }
}
