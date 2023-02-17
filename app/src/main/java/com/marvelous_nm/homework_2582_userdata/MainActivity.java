package com.marvelous_nm.homework_2582_userdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    ListView    listView;
    ArrayList<HashMap<String,String>>   arrayList = new ArrayList<>();
    HashMap<String,String>  hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
//    ...................identyfication Start ..............

        listView=findViewById( R.id.listView );
        progressBar=findViewById( R.id.progressBar );

//...................identyfication End................

        //        ............ Start server coding ..................

        RequestQueue    requestQueue = Volley.newRequestQueue( MainActivity.this );


        String url = "https://marzan45.000webhostapp.com/apps/user.json";
        JsonObjectRequest   objectRequest=new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("serverRes",response.toString());
                progressBar.setVisibility( View.GONE );

                try {
                    JSONArray   jsonArray=response.getJSONArray( "users" );
                    for (int x=0; x<jsonArray.length();x++){
                        JSONObject  jsonObject=jsonArray.getJSONObject( x );

                        String  jsonname = jsonObject.getString( "firstName" );
                        String  jsongender = jsonObject.getString( "gender" );
                        String  jsonbloodGroup = jsonObject.getString( "bloodGroup" );

                        hashMap = new HashMap<>();
                        hashMap.put( "name", jsonname );
                        hashMap.put( "gender", jsongender );
                        hashMap.put( "bloodGroup", jsonbloodGroup );
                        arrayList.add( hashMap );



                    }
                    MyAdaptor   myAdaptor=new MyAdaptor();
                    listView.setAdapter( myAdaptor );

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility( View.GONE );
            }
        }
        );

        requestQueue.add( objectRequest );
//        ............  End server coding...............







//        ....          coding end.................
    }


   private class MyAdaptor extends BaseAdapter{
        LayoutInflater  layoutInflater;

       @Override
       public int getCount() {
           return arrayList.size();
       }

       @Override
       public Object getItem(int i) {
           return null;
       }

       @Override
       public long getItemId(int i) {
           return 0;
       }

       @Override
       public View getView(int position, View view, ViewGroup viewGroup) {
           layoutInflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
           View myView = layoutInflater.inflate( R.layout.listview_xml_desine,viewGroup,false );


           ImageView imgView=myView.findViewById( R.id.imgView );
           TextView bloodGroup=myView.findViewById( R.id.bloodGroup );
           TextView gender=myView.findViewById( R.id.gender );
           TextView name=myView.findViewById( R.id.name );

            HashMap<String,String>  hashMap=arrayList.get( position );
            String  listname = hashMap.get( "name" );
           String  listgender = hashMap.get( "gender" );
           String  listbloodGroup = hashMap.get( "bloodGroup" );

           name.setText( listname );
           gender.setText( listgender );
           bloodGroup.setText( listbloodGroup );


           return myView;
       }
   }



}