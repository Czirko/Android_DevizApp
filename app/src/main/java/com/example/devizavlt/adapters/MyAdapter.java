package com.example.devizavlt.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devizavlt.R;
import com.example.devizavlt.api.model.Item;

import java.util.List;
import java.util.Map;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Item> items;
    private Map<String,String> valutaMap;
    private Map<String,String> bankMap;
    private Boolean bankOrValuta ;
    private String selected;

    public MyAdapter(List<Item> items, Map<String, String> valutaMap, Map<String, String> bankMap) {
        this.items = items;
        this.valutaMap = valutaMap;
        this.bankMap = bankMap;
    }

    public MyAdapter(List<Item> items, Map<String, String> valutaMap, Map<String, String> bankMap, Boolean bankOrValuta, String selected) {
        this.items = items;
        this.valutaMap = valutaMap;
        this.bankMap = bankMap;
        this.bankOrValuta = bankOrValuta;
        this.selected = selected;
    }

    public MyAdapter(List<Item> items, Map<String, String> valutaMap, Map<String, String> bankMap, Boolean bankOrValuta) {
        this.items = items;
        this.valutaMap = valutaMap;
        this.bankMap = bankMap;
        this.bankOrValuta = bankOrValuta;
    }

    public MyAdapter() {
    }

    public void setItems(List<Item> items){
        this.items=items;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,
                parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int posi) {
        Item item = items.get(posi);
        holder.tvBankName.setText(bankMap.get(item.getBank()));
        String penz=item.getPenznem()+"/ "+valutaMap.get(item.getPenznem());
        holder.tvPenznem.setText(penz);
        holder.tvVetel.setText(item.getVetel()+"");
        holder.tvEladas.setText(item.getEladas()+"");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBankName;
        public TextView tvPenznem;
        public TextView tvEladas;
        public TextView tvVetel;
        public LinearLayout itemLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBankName=itemView.findViewById(R.id.tvBankName);
            tvPenznem=itemView.findViewById(R.id.tvPenznem);
            tvEladas=itemView.findViewById(R.id.tvEladas);
            tvVetel=itemView.findViewById(R.id.tvVetel);
            itemLayout=itemView.findViewById(R.id.itemLayout);
        }
    }
}


