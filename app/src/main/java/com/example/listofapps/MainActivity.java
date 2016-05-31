package com.example.listofapps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnItemClickListener{

    ListView apps;
    PackageManager packageManager;
    ArrayList <String> checkedValue = new ArrayList<>();
    Button bt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt1 = (Button) findViewById(R.id.btnActivate);
        apps = (ListView) findViewById(R.id.list);

        packageManager = getPackageManager();

        final List <PackageInfo> packageList = packageManager
                .getInstalledPackages(PackageManager.GET_META_DATA); // all apps in the phone
        final List <PackageInfo> packageList1 = packageManager
                .getInstalledPackages(0);

        try {
            packageList1.clear();
            for (int n = 0; n < packageList.size(); n++)
            {

                PackageInfo PackInfo = packageList.get(n);
                if (!((PackInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0))
                //check weather it is system app or user installed app
                {
                    try
                    {

                        packageList1.add(packageList.get(n)); // add in 2nd list if it is user installed app
                        Collections.sort(packageList1,new Comparator <PackageInfo>()
                                // this will sort App list on the basis of app name
                        {
                            public int compare(PackageInfo o1,PackageInfo o2)
                            {
                                return o1.applicationInfo.loadLabel(getPackageManager()).toString()
                                        .compareToIgnoreCase(o2.applicationInfo.loadLabel(getPackageManager())
                                                .toString());// compare and return sorted packagelist.
                            }
                        });


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        apps.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        Listadapter Adapter = new Listadapter(this,packageList1, packageManager);
        apps.setAdapter(Adapter);
        apps.setOnItemClickListener(this);
        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                showCheckedItems(v);
            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
        // TODO Auto-generated method stub
        CheckBox cb = (CheckBox) v.findViewById(R.id.checkBox);

        cb.performClick();
        addInArray(v);
    }

    private void addInArray(View v) {
        TextView tv = (TextView) v.findViewById(R.id.app_name);
        String checkItem = tv.getText().toString();
        if(checkedValue.contains(checkItem)){
            checkedValue.remove(checkItem);
        }
        else
            checkedValue.add(checkItem);

    }

    public void showCheckedItems(View view){
        String items="";
        for (String item:checkedValue){
            items += "-"+item+"\n";
        }
        Toast.makeText(MainActivity.this, "" + items, Toast.LENGTH_LONG).show();
    }

}