package com.example.instasaverapp;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.StringUtils;

public class IgtvFragment extends Fragment {
    String URL = "NULL";
    VideoView mparticularigtv;
    EditText getigtvlink;
    Button getigtv;
    Button downloadigtv;
    private MediaController mediaController;
    String igtvurl = "1";
    private Uri uri2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.igtv_fragment,null);

        getigtvlink = v.findViewById(R.id.getigtvlink);
        getigtv = v.findViewById(R.id.getigtv);
        downloadigtv = v.findViewById(R.id.downloadIgtv);
        mparticularigtv = v.findViewById(R.id.particularigtv);
        mediaController = new MediaController(getContext());
        mediaController.setAnchorView(mparticularigtv);

        getigtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                URL = getigtvlink.getText().toString().trim();
                if (getigtvlink.equals("NULL"))
                {
                    Toast.makeText(getContext(), "First Enter URL", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String result2= StringUtils.substringBefore(URL,"/?");
                    URL= result2+"/?__a=1";
                    processdata();
                }


            }
        });

        downloadigtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!igtvurl.equals("1"))
                {
                    DownloadManager.Request request= new DownloadManager.Request(uri2);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI|DownloadManager.Request.NETWORK_MOBILE);
                    request.setTitle("Download");
                    request.setDescription("............");
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM,"" + System.currentTimeMillis()+".mp4");
                    DownloadManager manager= (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(request);
                    Toast.makeText(getContext(), "Downloaded", Toast.LENGTH_SHORT).show();


                }
                else
                {
                    Toast.makeText(getContext(), "No Video To Download", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return  v;

    }

    private void processdata() {

        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();

                MainUrl mainUrl = gson.fromJson(response, MainUrl.class);
                igtvurl = mainUrl.getGraphql().getShortcode_media().getVideo_url();
                uri2 = Uri.parse(igtvurl);
                mparticularigtv.setMediaController(mediaController);
                mparticularigtv.setVideoURI(uri2);
                mparticularigtv.requestFocus();
                mparticularigtv.start();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Not Able To Fetch", Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(request);


    }

}
