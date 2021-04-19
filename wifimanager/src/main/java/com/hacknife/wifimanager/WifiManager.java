package com.hacknife.wifimanager;

import android.content.Context;


import java.util.List;

public class WifiManager extends BaseWifiManager {

    private WifiManager(Context context) {
        super(context);
    }

    public static IWifiManager create(Context context) {
        return new WifiManager(context);
    }

    @Override
    public boolean isOpened() {
        return manager.isWifiEnabled();
    }

    @Override
    public void openWifi() {
        if (!manager.isWifiEnabled())
            manager.setWifiEnabled(true);
    }

    @Override
    public void closeWifi() {
        if (manager.isWifiEnabled())
            manager.setWifiEnabled(false);
    }

    @Override
    public void scanWifi() {
        manager.startScan();
    }

    @Override
    public boolean disConnectWifi() {
        return manager.disconnect();
    }

    @Override
    public boolean connectEncryptWifi(IWifi wifi, String password) {
        if (manager.getConnectionInfo() != null && wifi.SSID().equals(manager.getConnectionInfo().getSSID()))
            return true;
        int networkId = WifiHelper.configOrCreateWifi(manager, wifi, password);
        boolean ret = manager.enableNetwork(networkId, true);
        modifyWifi(wifi.SSID(), context.getString(R.string.wifi_tip_11));
        return ret;
    }

    @Override
    public boolean connectSavedWifi(IWifi wifi) {
        int networkId = WifiHelper.configOrCreateWifi(manager, wifi, null);
        boolean ret = manager.enableNetwork(networkId, true);
        modifyWifi(wifi.SSID(), context.getString(R.string.wifi_tip_11));
        return ret;
    }

    @Override
    public boolean connectOpenWifi(IWifi wifi) {
        boolean ret = connectEncryptWifi(wifi, null);
        modifyWifi(wifi.SSID(), context.getString(R.string.wifi_tip_11));
        return ret;
    }

    @Override
    public boolean removeWifi(IWifi wifi) {
        boolean ret = WifiHelper.deleteWifiConfiguration(manager, wifi);
        modifyWifi();
        return ret;
    }

    @Override
    public List<IWifi> getWifi() {
        return wifis;
    }


}
