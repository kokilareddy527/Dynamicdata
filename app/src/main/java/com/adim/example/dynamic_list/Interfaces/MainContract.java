package com.adim.example.dynamic_list.Interfaces;

import com.adim.example.dynamic_list.View.MainActivity;

public interface MainContract{
interface ViewOps {

    void ResSuccess(String response);

    void onError(String message);

    void ResFailure(String message);


}

interface Operations {

    void getItems(MainActivity mainActivity);


}
}
