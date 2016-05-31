package com.example.listofapps;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AddProfileActivity extends AppCompatActivity {

    EditText editTextProfileName;
    EditText editTextStartTime;
    EditText editTextEndTime;
    ListView listViewProfile;
    PackageManager packageManager;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_profile);

        editTextProfileName = (EditText) findViewById(R.id.etProfileName);
        editTextStartTime = (EditText) findViewById(R.id.etStartTime);
        editTextEndTime = (EditText) findViewById(R.id.etEndTime);
        listViewProfile = (ListView) findViewById(R.id.lvAddProfile);

        //Adding Items in List View
        packageManager = getPackageManager();

        final List<PackageInfo> packageList = packageManager
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
                        Collections.sort(packageList1,new Comparator<PackageInfo>()
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
        listViewProfile.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        Listadapter Adapter = new Listadapter(this,packageList1, packageManager);
        listViewProfile.setAdapter(Adapter);
        listViewProfile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox);
                cb.performClick();
            }
        });

    }


    public void onClickAdd (View btnAdd) {

        String profileName = editTextProfileName.getText().toString();
        String startTime = editTextStartTime.getText().toString();
        String endTime = editTextEndTime.getText().toString();

        if ( profileName.length() != 0 && startTime.length() != 0 && endTime.length() != 0 ) {

            Intent newIntent = getIntent();
            newIntent.putExtra("tag_profile_name", profileName);
            newIntent.putExtra("tag_start_time", startTime);
            newIntent.putExtra("tag_end_time", endTime);

            this.setResult(RESULT_OK, newIntent);

            finish();
        }
    }
}
