package com.wy.djreader.main_page.presenter;

import com.wy.djreader.main_page.MainPageContract;

public class MainPagePresenter implements MainPageContract.Presenter{

    private MainPageContract.View view;
    public MainPagePresenter(MainPageContract.View view) {
        this.view = view;
    }
}
