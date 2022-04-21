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

public class ReelFragment extends Fragment {

    String URL = "NULL";
    VideoView mparticularreel;
    EditText getreellink;
    Button getreel;
    Button downloadreel;
    private MediaController mediaController;
    String reelurl = "1";
    private Uri uri2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.reel_fragment,null);

        getreellink = v.findViewById(R.id.getreellink);
        getreel = v.findViewById(R.id.getreel);
        downloadreel = v.findViewById(R.id.downloadreel);
        mparticularreel = v.findViewById(R.id.particularreel);
        mediaController = new MediaController(getContext());
        mediaController.setAnchorView(mparticularreel);

        getreel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            URL = getreellink.getText().toString().trim();
            if (getreellink.equals("NULL"))
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


        downloadreel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!reelurl.equals("1"))
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
                reelurl = mainUrl.getGraphql().getShortcode_media().getVideo_url();
                uri2 = Uri.parse(reelurl);
                mparticularreel.setMediaController(mediaController);
                mparticularreel.setVideoURI(uri2);
                mparticularreel.requestFocus();
                mparticularreel.start();
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
