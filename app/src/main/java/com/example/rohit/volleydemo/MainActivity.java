package com.example.rohit.volleydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView textView;
    RequestQueue requestQueue;
    String urlString = "http://192.168.0.110:9000/question/2/FindAll";
    JsonObjectRequest jsonObjectRequest;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        requestQueue = Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseFromApi();
            }
        });
    }

    public void responseFromApi() {

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, urlString, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    jsonArray=response.getJSONArray("doc");

                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject doc=jsonArray.getJSONObject(i);

                        String _id=doc.getString("_id");
                        String question=doc.getString("question");
                        String qcode=doc.getString("qcode");
                        String __v=doc.getString("__v");

                        textView.append("id = "+_id+"\n"+"question = "+question+"\n"+"qcode = "+qcode+"\n"+"v = "+__v);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e("VOLLEY","ERROR"+error);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

}
