package com.example.eugene.Interface;

import com.example.eugene.Model.RequestOrder;

public interface ILoadTimeFromFirebaseListener {
    void onLoadTimeSuccess(RequestOrder requestOrder,long estimateTimeInMs);
    void onLoadTimeFailed(String message);
}
