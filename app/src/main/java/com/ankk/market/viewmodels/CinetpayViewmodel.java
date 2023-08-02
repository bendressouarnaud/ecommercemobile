package com.ankk.market.viewmodels;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.ankk.market.adapters.AdapterListPanier;
import com.ankk.market.repositories.AchatRepository;
import com.ankk.market.repositories.BeanarticledetailRepository;
import com.ankk.market.repositories.ClientRepository;

public class CinetpayViewmodel extends ViewModel {

    // A t t ri b u t e s :
    AchatRepository achatRepository;
    ClientRepository clientRepository;


    // M E T H O D S :
    public CinetpayViewmodel(Application app) {
        achatRepository = new AchatRepository(app);
        clientRepository = new ClientRepository(app);
    }

    public AchatRepository getAchatRepository() {
        return achatRepository;
    }

    public void setAchatRepository(AchatRepository achatRepository) {
        this.achatRepository = achatRepository;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
}
