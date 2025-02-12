package com.example.shoplink;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SupProductAdapter extends RecyclerView.Adapter<SupProductAdapter.ProductViewHolder> {

    Context context;
    ArrayList<ModelProduct> productList;

    public SupProductAdapter(ArrayList<ModelProduct> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public SupProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sup_single_myproduct_card, parent, false);
        return new ProductViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SupProductAdapter.ProductViewHolder holder, int position) {
        ModelProduct modelProduct = productList.get(position);
        holder.prodCode.setText(modelProduct.getProductName());
        holder.prodName.setText(modelProduct.getProductName());
        holder.prodName.setText(modelProduct.getProductName());
        holder.prodName.setText(modelProduct.getProductName());
        holder.prodName.setText(modelProduct.getProductName());
        holder.prodName.setText(modelProduct.getProductName());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder
    {
        TextView prodCode, prodName, prodSupplyPrice, prodMaxSellPrice, prodShipFee;
        ImageView prodImg;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            prodCode = itemView.findViewById(R.id.txtProductCode);
            prodName = itemView.findViewById(R.id.txtProductName);
            prodSupplyPrice = itemView.findViewById(R.id.txtSupplyPrice);
            prodMaxSellPrice = itemView.findViewById(R.id.txtMaxSellPrice);
            prodShipFee = itemView.findViewById(R.id.txtShipFeePerOrder);
            prodImg = itemView.findViewById(R.id.imgProduct);
        }
    }
}
