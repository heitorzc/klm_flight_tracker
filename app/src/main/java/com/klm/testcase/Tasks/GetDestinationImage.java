package com.klm.testcase.Tasks;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.klm.testcase.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Heitor Zanetti
 * 10/03/2016
 */
public class GetDestinationImage extends AsyncTask<Void, Void, Void> {

    final String FILE_NAME = "destination_images.properties";
    Context mContext;
    String code;
    ImageView iv;
    String url;

    public GetDestinationImage(Context mContext, String code, ImageView iv) {
        this.mContext = mContext;
        this.code = code;
        this.iv = iv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        Properties properties = new Properties();

        try {
            AssetManager assetManager = mContext.getAssets();
            InputStream inputStream = assetManager.open(FILE_NAME);
            properties.load(inputStream);

        } catch (IOException e) {
            Log.e("AssetsPropertyReader",e.toString());
        }

        url = properties.getProperty(code);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Picasso.with(mContext).load(url).error(R.drawable.bg).placeholder(R.drawable.bg).resize(1024, 768).into(iv);

    }
}


