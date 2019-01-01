package com.wy.djreader.utils.httputil;

public interface OkHttpInterface {
    //okHttpGetSynchronize Stream
    void syncStreamGet();


    //okHttpPostSynchronize
    void okHttpPostSync();
    //OKHttpGetAsynchronous
    void okHttpGetAsync();
    //OKHttpPostAsynchronous
    void okHttpPostAsync();
}
