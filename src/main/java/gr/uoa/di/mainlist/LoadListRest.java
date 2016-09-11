/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainlist;

import gr.uoa.di.modelproducts.Products;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import gr.uoa.di.modelproducts.ShoppingList;

/**
 *
 * @author mark9
 */
class LoadListRest extends AsyncTask <String,Void,String>{
    private final Activity parent;
    private final CustomListAdapter mAdapter;
    private final String file = "mylist.txt";
    
    public LoadListRest(Activity parent, CustomListAdapter adapter){
        this.parent=parent;
        this.mAdapter = adapter;
    }
    
    @Override
    protected String doInBackground(String... params) {
        StringBuffer bf= new StringBuffer();
        try{
            if(params.length>0){
// formulating theURL that will be used for establishing the connection to the service
                URL serviceURL= new URL(params[0]);
// openning the http connection to the service
                HttpURLConnection connection= (HttpURLConnection) serviceURL.openConnection();
// setting the HTTP method that will be to something different than the deafult get
        // connection.setRequestMethod("POST");
// if you need to send data along with the request
        //connection.setDoOutput(true);
// establishingtheconnection
                connection.connect();
// openning the input stream for reading the response of the service and
//piping the input stream through a buffered reader object
                BufferedReader reader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
//reading the returned input line by line till the EOF is returned 
                String line;
                while((line = reader.readLine())!= null)
                    bf.append(line);
                return bf.toString();
            }
        }catch(MalformedURLException ex){
            Log.d("gr.uoa.di.myfirstapp","",ex);
        }catch(IOException ex){
            Log.d("gr.uoa.di.myfirstapp","",ex);
        }
        finally{
            return bf.toString();
        }
    }
    
    protected void onPostExecute(String result) {
        ArrayList<Products> alpd= new ArrayList<Products>();
        Products pd = null;
        JSONObject jsonResponse;
        ArrayList<Integer> temppdsel;
        ArrayList<Float> temppdprice;
        ShoppingList shl = new ShoppingList();
        ArrayList<String> templ;
        templ = shl.readfromfile(parent);
        if(templ!=null){
            try {
                JSONArray jsonMainNode = new JSONArray(result);
                for(int j=1 ; j<=templ.size() ; j++){
                    for(int i=0 ; i<jsonMainNode.length() ; i++){
                        jsonResponse =((JSONObject)jsonMainNode.get(i));
                        if(Integer.valueOf(jsonResponse.getString("prodnum")) == Integer.valueOf(templ.get(j-1))){
                            if((pd!=null) && (pd.getProdnum()== Integer.valueOf(jsonResponse.getString("prodnum")))){
                                pd = alpd.remove(alpd.size()-1);
                                temppdsel = pd.getSellerid();
                                temppdprice = pd.getProdprice();
                                temppdsel.add(Integer.valueOf(jsonResponse.getString("sellerid")));
                                temppdprice.add(Float.valueOf(jsonResponse.getString("prodprice")));
                                pd.setProdprice(temppdprice);
                                pd.setSellerid(temppdsel);
                                alpd.add(pd);
                            }
                            else{
                                pd = new Products();
                                pd.setProdname(jsonResponse.getString("prodname"));
                                pd.setProdid(Integer.valueOf(jsonResponse.getString("prodid")));
                                pd.setProdnum(Integer.valueOf(jsonResponse.getString("prodnum")));
                                temppdsel=pd.getSellerid();
                                temppdprice = pd.getProdprice();
                                temppdsel.add(Integer.valueOf(jsonResponse.getString("sellerid")));
                                pd.setSellerid(temppdsel);
                                temppdprice.add(Float.valueOf(jsonResponse.getString("prodprice")));
                                pd.setProdprice(temppdprice);
                                alpd.add(pd);
                            }
                        }
                    }
                }
            } catch (JSONException ex) {
                Log.e("gr.uoa.hello","",ex);
            }
            pd = new Products();
            pd.setProdname("Total");
            pd.setProdid(-1);
            alpd.add(pd);
        }
        mAdapter.upDateEntries(alpd);
    }
}
