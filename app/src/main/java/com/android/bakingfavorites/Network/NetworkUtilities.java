package com.android.bakingfavorites.Network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtilities {

    public static final String recipeJsonUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static String getResponseFromUrl(URL url) throws IOException {
        //this class will be used to retreive the json for the recepies.
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream input = urlConnection.getInputStream();

            Scanner scanner = new Scanner(input);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }
}
