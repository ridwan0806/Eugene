package com.example.eugene.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eugene.Cart;
import com.example.eugene.Model.Order;
import com.example.eugene.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
//    private ArrayList productId,productName,qty,price,discount;
    private List<Order> orderList;

    public CartAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    //    public CartAdapter(Context context, ArrayList productId, ArrayList productName, ArrayList qty, ArrayList price, ArrayList discount) {
//        this.context = context;
//        this.productId = productId;
//        this.productName = productName;
//        this.qty = qty;
//        this.price = price;
//        this.discount = discount;
//    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
//        holder.productIdTxt.setText(String.valueOf(productId.get(position)));
//        holder.nameTxt.setText(String.valueOf(productName.get(position)));
//        holder.qtyTxt.setText(String.valueOf(qty.get(position)));
//        holder.priceTxt.setText(String.valueOf(price.get(position)));
//        holder.discountTxt.setText(String.valueOf(discount.get(position)));

        holder.productIdTxt.setText(orderList.get(position).getProductId());
        holder.nameTxt.setText(orderList.get(position).getProductName());
        holder.qtyTxt.setText(orderList.get(position).getQuantity());

        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(orderList.get(position).getPrice()))*(Integer.parseInt(orderList.get(position).getQuantity()));

        holder.priceTxt.setText(fmt.format(price));

        holder.discountTxt.setText(orderList.get(position).getDiscount());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView productIdTxt,nameTxt,qtyTxt,priceTxt,discountTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productIdTxt = itemView.findViewById(R.id.txtProductIdOrderDetail);
            nameTxt = itemView.findViewById(R.id.txtNameOrderDetail);
            qtyTxt = itemView.findViewById(R.id.txtQtyOrderDetail);
            priceTxt = itemView.findViewById(R.id.txtPriceOrderDetail);
            discountTxt = itemView.findViewById(R.id.txtDiscountOrderDetail);
        }
    }
}
