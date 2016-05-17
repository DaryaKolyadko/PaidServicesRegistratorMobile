package com.darya.paidserviceregistrator.util;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.darya.paidserviceregistrator.R;
import com.darya.paidserviceregistrator.entities.Service;

import java.util.List;

/**
 * Created by USER on 13.05.2016.
 */
public class ListViewServiceAdapter extends BaseAdapter {
    private Context ctx;
    private List<Service> services;
    private LayoutInflater inflater;

    public ListViewServiceAdapter(Context context, List<Service> services) {
        this.services = services;
        this.ctx = context;
        this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.service_list_adapter_item, parent, false);
        }

        Service service = getService(position);
        ((TextView) view.findViewById(R.id.textViewName)).setText(service.getName());
        ((TextView) view.findViewById(R.id.textViewId)).setText(String.valueOf(service.getId()));
        return view;
    }

    private Service getService(int position) {
        return (Service) getItem(position);
    }

    @Override
    public boolean isEmpty() {
        return services.isEmpty();
    }
}
