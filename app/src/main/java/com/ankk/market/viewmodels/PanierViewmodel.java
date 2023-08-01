package com.ankk.market.viewmodels;

import androidx.lifecycle.ViewModel;

import com.ankk.market.adapters.AdapterListPanier;
import com.ankk.market.repositories.AchatRepository;
import com.ankk.market.repositories.BeanarticledetailRepository;

public class PanierViewmodel extends ViewModel {

    AdapterListPanier adapter;
    BeanarticledetailRepository beanarticledetailRepository;
    AchatRepository achatRepository;

}
