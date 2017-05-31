package com.example.lenovo.mb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lenovo.mb.bean.B;
import com.example.lenovo.mb.utils.NetUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    List<B.AppBean>  all=new ArrayList<>();
    private MyAdapter adapter;
    private AlertDialog.Builder ab;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      ListView  lv=(ListView) findViewById(R.id.lv);


        getData();


        adapter = new MyAdapter(this,all);


        //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + all);
        lv.setAdapter(adapter);


      lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              setSwitch();
              ab.show();

          }
      });






    }

    private void setSwitch() {
        ab = new AlertDialog.Builder(this);
        ab.setIcon(R.mipmap.ic_launcher);
        ab.setTitle("网络选择");

        ab.setPositiveButton("wifi", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {

                setDialog();

                builder.show();


            }
        });

        ab.setNegativeButton("手机流量", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NetUtil.toSystemSetting(MainActivity.this);
            }
        });
    }


    private void setDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("版本更新");

        builder.setMessage("现在检测到有新版本，是否更新？");


        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downLoad();


            }


        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

    }

    private void downLoad() {
        String url="http://imtt.dd.qq.com/16891/E4E087B63E27B87175F4B9BC7CFC4997.apk?fsname=com.tencent.qlauncher_6.0.2_64170111.apk&csr=97c2";
        RequestParams params = new RequestParams(url);
//自定义保存路径，Environment.getExternalStorageDirectory()：SD卡的根目录
        params.setSaveFilePath(Environment.getExternalStorageDirectory()+"/myapp/");
//自动为文件命名
        params.setAutoRename(true);
        x.http().post(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                //apk下载完成后，调用系统的安装方法
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
                startActivity(intent);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("----", "onError: ");
            }
            @Override
            public void onCancelled(CancelledException cex) {
                Log.d("----", "onCancelled: ");
            }
            @Override
            public void onFinished() {
                Log.d("----", "onFinished: ");
            }
            //网络请求之前回调
            @Override
            public void onWaiting() {
                Log.d("----", "onWaiting: ");
            }
            //网络请求开始的时候回调
            @Override
            public void onStarted() {
                Log.d("----", "onStarted: ");
            }
            //下载的时候不断回调的方法
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                //当前进度和文件总大小
                Log.i("JAVA","current："+ current +"，total："+total);
            }
        });
    }

    private void getData() {
        RequestParams  rp=new RequestParams("http://mapp.qzone.qq.com/cgi-bin/mapp/mapp_subcatelist_qq?yyb_cateid=-10&categoryName=%E8%85%BE%E8%AE%AF%E8%BD%AF%E4%BB%B6&pageNo=1&pageSize=20&type=app&platform=touch&network_type=unknown&resolution=412x732");


        x.http().get(rp, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

               String sub= result.substring(0, result.length()-1);
                //System.out.println("result ======================================= " + result);
                 Gson  gson=new Gson();


                B b = gson.fromJson(sub, B.class);

                List<B.AppBean> app = b.getApp();

                all.addAll(app);


                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
