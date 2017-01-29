package com.obrasocialsjd.magicline.Announcements;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.obrasocialsjd.magicline.R;

import java.util.ArrayList;

public class AnnouncementActivity extends Activity {
        ListView list;
        CustomAdapter adapter;
        public AnnouncementActivity CustomListView = null;
        public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {


            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_announcement);

            CustomListView = this;

            /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
            setListData();

            Resources res =getResources();
            list= ( ListView )findViewById( R.id.list );  // List defined in XML ( See Below )

            /**************** Create Custom Adapter *********/
            adapter=new CustomAdapter( CustomListView, CustomListViewValuesArr,res );
            list.setAdapter( adapter );

        }

        /****** Function to set data in ArrayList *************/
        public void setListData()
        {

            if (getApplicationContext() != null) {
                DatabaseAnnouncements databaseAnnouncements = new DatabaseAnnouncements(getApplicationContext());
                for (AnnouncementDB announcementDB :
                        databaseAnnouncements.getAllAnnouncement(DatabaseAnnouncements.TABLE_ANNOUNCEMENTSDOWNLOADED)){
                    final ListModel sched = new ListModel();

                    sched.setTitle(announcementDB.get_title());
                    sched.setText(announcementDB.get_text());
                    sched.setTime(announcementDB.get_time());

                    /******** Take Model Object in ArrayList **********/
                    CustomListViewValuesArr.add( sched );
                }
            }
        }


        /*****************  This function used by adapter ****************/
        public void onItemClick(int mPosition)
        {
            ListModel tempValues = ( ListModel ) CustomListViewValuesArr.get(mPosition);


            // SHOW ALERT

            /*Toast.makeText(CustomListView,
                    ""+tempValues.getCompanyName()
                            +"Image:"+tempValues.getImage()
                +"Url:"+tempValues.getUrl(),Toast.LENGTH_LONG)
            .show();*/
        }
    }
