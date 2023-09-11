package com.ankk.market.viewmodels;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.ankk.market.adapters.AdapterArticleCommande;
import com.ankk.market.adapters.AdapterCommentaireArticle;
import com.ankk.market.repositories.AchatRepository;
import com.ankk.market.repositories.BeanarticledetailRepository;
import com.ankk.market.repositories.ClientRepository;
import com.ankk.market.repositories.CommandeRepository;

public class ArticleViewmodel extends ViewModel {

    // A t t r i b u t e s :
    BeanarticledetailRepository beanarticledetailRepository;
    AchatRepository achatRepository;
    ClientRepository clientRepository;
    int articleRestant;
    boolean valideCommande = false;
    int idart = 0, fromadapter = 0, qte = 0;
    AdapterCommentaireArticle adapterCommentaireArticle;



    // M e t h o d s  :
    public ArticleViewmodel(Application app) {
        beanarticledetailRepository = new BeanarticledetailRepository(app);
        achatRepository = new AchatRepository(app);
        clientRepository = new ClientRepository(app);
        adapterCommentaireArticle = new AdapterCommentaireArticle(app.getApplicationContext());
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

    public boolean isValideCommande() {
        return valideCommande;
    }

    public void setValideCommande(boolean valideCommande) {
        this.valideCommande = valideCommande;
    }

    public int getIdart() {
        return idart;
    }

    public void setIdart(int idart) {
        this.idart = idart;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public AdapterCommentaireArticle getAdapterCommentaireArticle() {
        return adapterCommentaireArticle;
    }

    public int getFromadapter() {
        return fromadapter;
    }

    public void setFromadapter(int fromadapter) {
        this.fromadapter = fromadapter;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }
}
