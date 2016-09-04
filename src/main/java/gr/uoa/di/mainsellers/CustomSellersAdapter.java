/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainsellers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import gr.uoa.di.R;
import gr.uoa.di.modelproducts.Sellers;
import gr.uoa.di.modelproducts.ShoppingList;
import java.util.ArrayList;

/**
 *
 * @author mark9
 */
public class CustomSellersAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Sellers> mSellers = new ArrayList<Sellers>();
    
    public CustomSellersAdapter(Context context){
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
    public int getCount() {
        return mSellers.size();
    }

    @Override
    public Object getItem(int position) {
        return mSellers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View sellerView;
        ArrayList<Float> showprice;
        Float tempprice;

       sellerView = mLayoutInflater.inflate(R.layout.row_sellers, null);
       TextView selname = (TextView) sellerView.findViewById(R.id.sellerName);
       TextView seladdr = (TextView) sellerView.findViewById(R.id.sellerAddress);
       TextView selprice = (TextView) sellerView.findViewById(R.id.sellerPrice);
       TextView selmail = (TextView) sellerView.findViewById(R.id.sellerMail);
       final Button showmap = (Button) sellerView.findViewById(R.id.showmap);
       selname.setText("Name: " + mSellers.get(position).getName());
       seladdr.setText("Address: " + mSellers.get(position).getAddress());
       selprice.setText("Price: " + String.valueOf(mSellers.get(position).getPrice()));
       selmail.setText("Email: " + mSellers.get(position).getEmail());
       
       showmap.setOnClickListener(new View.OnClickListener() {            
            @Override
            public void onClick(View v) {

            }
        }); 
       return sellerView;
    }
    
    public void upDateEntries(ArrayList<Sellers> alsl){
        mSellers = alsl;
        notifyDataSetChanged();
    }
    
}