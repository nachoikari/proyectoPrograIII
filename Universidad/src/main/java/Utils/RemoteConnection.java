package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Joseph
 */

public class RemoteConnection {

    private static RemoteConnection connection;
    private final String URL = "http://127.0.0.1:5000";
    
    private RemoteConnection() {}

    public static RemoteConnection getInstance() {
        if (connection == null) {
            connection = new RemoteConnection();
        }
        return connection;
    }

    public String connectToServer(String urlConcat, String method, String param) {
        String response = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL( URL + urlConcat);
            System.out.println(URL + urlConcat); //-----------------------------------------------------------------------------------------------
            System.out.println(method);
            System.out.println(param);         
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(method);
            if (method.equals("POST") || method.equals("PUT") || method.equals("GET") || method.equals("DELETE")) {
                urlConnection.setDoOutput(true);
                if (!param.isEmpty()) {
                    urlConnection.setFixedLengthStreamingMode(param.getBytes().length);
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    try (OutputStream out = urlConnection.getOutputStream()) {
                        writeStream(out, param);
                    }
                }
            }
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = readStream(urlConnection.getInputStream());
            } else {
                System.out.println("Error: " + responseCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return response;
    }

    private void writeStream(OutputStream out, String param) throws IOException {
        out.write(param.getBytes());
        out.flush();
    }

    private String readStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return sb.toString();
    }
    
}
