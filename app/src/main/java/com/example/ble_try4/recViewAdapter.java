package com.example.ble_try4;

import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class recViewAdapter extends RecyclerView.Adapter<recViewAdapter.ViewhOlder> {

    private Context context;
    private List<BT_device> list;

    public recViewAdapter(Context context, List<BT_device> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewhOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewhOlder(LayoutInflater.from(parent.getContext()).inflate(R.layout.singe_device_row , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewhOlder holder, int position) {

        holder.device.setText(list.get(position).getDevice().toString());
        holder.name.setText(list.get(position).getName());
        holder.mac.setText(list.get(position).getAddress());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , MainActivity.class);
//                intent.addFlags()
                intent.putExtra("mac" , list.get(position).getAddress() );
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewhOlder extends RecyclerView.ViewHolder {

        private TextView device , name , mac;
        private LinearLayout layout;

        public ViewhOlder(@NonNull View itemView) {
            super(itemView);

            device = itemView.findViewById(R.id.device);
            name = itemView.findViewById(R.id.deviceName);
            mac = itemView.findViewById(R.id.deviceMac);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
