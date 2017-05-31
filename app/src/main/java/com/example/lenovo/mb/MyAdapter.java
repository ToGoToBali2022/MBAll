package com.example.lenovo.mb;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.mb.bean.B;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/5/28.
 */

public class MyAdapter extends BaseAdapter {
    public Map<Integer,Boolean> isCheck = null;
    private Context context;
    private List<B.AppBean> list;
    private vH vh;

    public MyAdapter(Context context, List<B.AppBean> list) {
        this.context = context;
        this.list = list;
        isCheck = new HashMap<Integer, Boolean>();
        initMap();
    }

    private void initMap(){
        for (int i = 0; i < list.size(); i++) {
            isCheck.put(i, false);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView( final int position, View convertView, ViewGroup parent) {
        if (convertView==null){

            vh = new vH();

        convertView = View.inflate(context, R.layout.lv, null);

           vh.tv=(TextView) convertView.findViewById(R.id.lvtv) ;
           vh.cb=(CheckBox) convertView.findViewById(R.id.cb) ;
            vh.layout= (LinearLayout) convertView.findViewById(R.id.layout);
            convertView.setTag(vh);
        }else {

           vh=(vH) convertView.getTag();
        }

        vh.tv.setText(list.get(position).getSecCate());
     /*   vh.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vh.cb.isChecked()){
                    vh.cb.setChecked(false);
                }else{
                    vh.cb.setChecked(true);
                }
            }
        });
        vh.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isCheck.put(position, true);
                } else {
                    isCheck.put(position, false);
                }
            }
        });
*/
        //vh.cb.setChecked(isCheck.get(position));


        return convertView;
    }


    class vH{

       TextView  tv;
        CheckBox cb;
        LinearLayout layout;

    }
}
