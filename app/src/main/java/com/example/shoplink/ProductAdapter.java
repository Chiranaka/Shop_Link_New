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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    ArrayList<ModelProduct> productList;

    public ProductAdapter(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sup_single_myproduct_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        ModelProduct modelProduct = productList.get(position);
        holder.prodCode.setText(modelProduct.getProductCode());
        holder.prodName.setText(modelProduct.getProductName());
        holder.prodSupplyPrice.setText(modelProduct.getSupplyPrize());
        holder.prodMaxSellPrice.setText(modelProduct.getMaxSellingPrize());
        holder.prodShipPrice.setText(modelProduct.getShipFeePerOrder());
        holder.prodImg.setImageResource(Integer.parseInt(modelProduct.getImageUrl()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder
    {
        TextView prodCode, prodName, prodSupplyPrice, prodMaxSellPrice, prodShipPrice;
        ImageView prodImg;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            prodCode = itemView.findViewById(R.id.txtProductCode);
            prodName = itemView.findViewById(R.id.txtProductName);
            prodImg = itemView.findViewById(R.id.imgViewItem);
            prodSupplyPrice = itemView.findViewById(R.id.txtSupplyPrice);
            prodMaxSellPrice= itemView.findViewById(R.id.txtMaxSellPrice);
            prodShipPrice = itemView.findViewById(R.id.txtShipFeePerOrder);
        }
    }
}
