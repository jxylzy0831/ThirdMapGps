package com.superman.daohgang;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String LAT_MAP ="39.93349";
    private static final String LONG_MAP ="116.540863";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toHereByGaode(View view) {
        Intent intent;
        if (isAvilible(this, "com.autonavi.minimap")) {
            goToNaviActivity(this,"test",null,LAT_MAP,LONG_MAP,"1","2");
        }else{
            Toast.makeText(this, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public void toHereByBaidu(View view) {
        Intent intent;
        if (isAvilible(this, "com.baidu.BaiduMap")) {
            goToBaiduActivity(this,LAT_MAP,LONG_MAP);
        }else{
            Toast.makeText(this, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public void toHereByTengxun(View view) {
        Intent intent;
        if (isAvilible(this, "com.tencent.map")) {
            gotoTengxun(this,LAT_MAP,LONG_MAP);
        }else{
            Toast.makeText(this, "您尚未安装腾讯地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.tencent.map");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
/*
     * 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName：应用包名
     * @return
             */
    public static boolean isAvilible(Context context, String packageName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 启动高德App进行导航

     *
     * @param sourceApplication 必填 第三方调用应用名称。如 amap
     * @param poiname 非必填 POI 名称
     * @param lat 必填 纬度
     * @param lon 必填 经度
     * @param dev 必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param style 必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
     */
    public static  void goToNaviActivity(Context context,String sourceApplication , String poiname , String lat , String lon , String dev , String style){
        StringBuffer stringBuffer  = new StringBuffer("androidamap://navi?sourceApplication=")
                .append(sourceApplication);
        if (!TextUtils.isEmpty(poiname)){
            stringBuffer.append("&poiname=").append(poiname);
        }
        stringBuffer.append("&lat=").append(lat)
                .append("&lon=").append(lon)
                .append("&dev=").append(dev)
                .append("&style=").append(style);

        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    /**
     * 启动百度App进行导航

     *
     * @param lat 必填 纬度
     * @param lon 必填 经度
     */
    public static  void goToBaiduActivity(Context context , String lat , String lon)
    {

        // 百度地图
        Intent naviIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("baidumap://map/geocoder?location=" + lat + "," + lon));
        context.startActivity(naviIntent);
    }

    /**
     * 启动腾讯地图App进行导航

     *
     * @param lat 必填 纬度
     * @param lon 必填 经度
     */
    public static  void gotoTengxun(Context context , String lat , String lon){

        // 腾讯地图
        Intent naviIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("qqmap://map/routeplan?type=drive&from=&fromcoord=&to=目的地&tocoord=" + lat + "," + lon + "&policy=0&referer=appName"));
        context.startActivity(naviIntent);

    }
}
