/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author schueler
 */
public class Database {

    private static Database database;
    private static String webServiceHeader = "";

    public static Database newInstance() throws Exception {
        if (database == null) {
            database = new Database();
        }

        return database;
    }

    private Database() throws Exception {
    }

    public SchoopyAdmin login(SchoopyAdmin sa) throws Exception {
        SchoopyAdmin sa1 = new SchoopyAdmin();
        HttpClient hc;
        hc = new HttpClient();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://www.example.com");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", "John"));
        params.add(new BasicNameValuePair("password", "pass"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        HttpResponse response = client.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
        return sa1;
    }

}
