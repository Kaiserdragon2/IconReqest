package de.kaiserdragon.iconrequest;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.kaiserdragon.iconrequest.interfaces.OnAppSelectedListener;

public class AppAdapter extends RecyclerView.Adapter<AppViewHolder> {
    private final List<AppInfo> appList;
    private final List<AppInfo> filteredList;
    private final List<AppInfo> iPackList;
    private final boolean iPackMode;
    private final boolean secondIcon;
    private static final String TAG = "AppAdapter";

    private final Activity activity;

    public AppAdapter(List<AppInfo> appList,Boolean iPacksMode,Boolean SecondIcon,Activity activity) {
        this.appList = appList;
        this.filteredList = new ArrayList<>(appList);
        this.iPackList = new ArrayList<>(appList);
        iPackMode = iPacksMode;
        this.activity =activity;
        secondIcon = SecondIcon;
    }

    public void filter(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(iPackList);
        } else {
            String lowerCaseQuery = query.toLowerCase(Locale.getDefault());
            for (AppInfo app : iPackList) {
                if (app.getLabel().toLowerCase(Locale.getDefault()).contains(lowerCaseQuery)) {
                    filteredList.add(app);
                }
            }
        }
        Log.i(TAG, "filter: " + filteredList.size());
        //notifyItemRangeChanged(0, filteredList.size());
        notifyDataSetChanged();
    }

    public void showIPack(String IconPackPackageName) {
        iPackList.clear();
        if (IconPackPackageName.isEmpty()) {
            iPackList.addAll(appList);
        } else {
            for (AppInfo app : appList) {
                if (app.getIconPackPackageName().contains(IconPackPackageName)) {
                    iPackList.add(app);
                }
            }
        }
        Log.i(TAG, "showIPack: " + iPackList.size());
        //notifyItemRangeChanged(0, filteredList.size());
        //notifyDataSetChanged();
        filter("");
    }

    public int AdapterSize(){
        return this.appList.size();
    }

    public int getSelectedItemCount() {
        int count = 0;
        for (AppInfo app : appList) {
            if (app.selected) count++;
        }
        return count;
    }

    public  ArrayList<AppInfo> getAllSelected() {
        ArrayList<AppInfo> arrayList = new ArrayList<>();
        for (AppInfo app : appList) {
            if (app.selected) arrayList.add(app);
        }
        return arrayList;
    }

    public void setAllSelected(boolean selected) {
        for (AppInfo app : filteredList) {
            app.setSelected(selected);
        }
        notifyItemRangeChanged(0, filteredList.size());
        //notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item, parent, false);
        return new AppViewHolder(v, filteredList,iPackMode, (OnAppSelectedListener) activity);
    }


    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        AppInfo app = filteredList.get(position);
        holder.labelView.setText(app.getLabel());
        holder.packageNameView.setText(app.packageName);
        holder.classNameView.setText(app.className);
        holder.imageView.setImageDrawable(app.getIcon());
        if (app.selected) holder.checkBox.setDisplayedChild(1);
        else holder.checkBox.setDisplayedChild(0);
        if (secondIcon) {
            holder.apkIconView.setVisibility(View.VISIBLE);
            holder.apkIconView.setImageDrawable(app.getIcon2());
        }

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }
}

