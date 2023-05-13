package com.example.eugene.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eugene.Cart;
import com.example.eugene.Database.MyDatabaseHelper;
import com.example.eugene.EditCart;
import com.example.eugene.Model.Order;
import com.example.eugene.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<Order> orderList;

    public CartAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Order order = orderList.get(position);

        holder.productIdTxt.setText(order.getProductId());
        holder.nameTxt.setText(order.getProductName());
        holder.qtyTxt.setText(order.getQuantity());

        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));

        holder.priceTxt.setText(fmt.format(price));

        holder.discountTxt.setText(order.getDiscount());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                Intent editCart = new Intent(context, EditCart.class);
                editCart.putExtra("id",String.valueOf(order.getId()));
                editCart.putExtra("foodId",String.valueOf(order.getProductId()));
                editCart.putExtra("nameFood",String.valueOf(order.getProductName()));
                editCart.putExtra("price",String.valueOf(order.getPrice()));
                editCart.putExtra("qty",String.valueOf(order.getQuantity()));
                editCart.putExtra("discount",String.valueOf(order.getDiscount()));
                context.startActivity(editCart);
            }
        });
        
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper db = new MyDatabaseHelper(context);
                db.deleteCart(order.getId());
                orderList.remove(position);
                notifyItemRemoved(position);

                Intent cart = new Intent(context,Cart.class);
                context.startActivity(cart);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView productIdTxt,nameTxt,qtyTxt,priceTxt,discountTxt;
        ConstraintLayout mainLayout;
        ImageView btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productIdTxt = itemView.findViewById(R.id.txtProductIdOrderDetail);
            nameTxt = itemView.findViewById(R.id.txtNameOrderDetail);
            qtyTxt = itemView.findViewById(R.id.txtQtyOrderDetail);
            priceTxt = itemView.findViewById(R.id.txtPriceOrderDetail);
            discountTxt = itemView.findViewById(R.id.txtDiscountOrderDetail);
            mainLayout = itemView.findViewById(R.id.rv_cart);
            btnDelete = itemView.findViewById(R.id.btnDeleteCartItem);
        }
    }
}
