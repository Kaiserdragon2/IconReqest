package de.kaiserdragon.iconrequest;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.kaiserdragon.iconrequest.interfaces.OnAppSelectedListener;

public class AppViewHolder extends RecyclerView.ViewHolder {
    public TextView labelView;
    public TextView packageNameView;
    public TextView classNameView;
    public ImageView imageView;
    public ImageView apkIconView;
    public ViewSwitcher checkBox;

    public AppViewHolder(View v, List<AppInfo> appList, Boolean iPackMode, OnAppSelectedListener listener) {
        super(v);
        labelView = v.findViewById(R.id.label_view);
        packageNameView = v.findViewById(R.id.packagename_view);
        classNameView = v.findViewById(R.id.classname_view);
        imageView = v.findViewById(R.id.icon_view);
        apkIconView = v.findViewById(R.id.apkicon_view);
        checkBox = v.findViewById(R.id.SwitcherChecked);

        v.setOnClickListener(v1 -> {
            int position = getAdapterPosition();
            AppInfo app = appList.get(position);
            app.setSelected(!app.isSelected());
            if (iPackMode)
                listener.onAppSelected(app.packageName);
            Animation aniIn = AnimationUtils.loadAnimation(checkBox.getContext(), R.anim.request_flip_in_half_1);
            Animation aniOut = AnimationUtils.loadAnimation(checkBox.getContext(), R.anim.request_flip_in_half_2);
            checkBox.setInAnimation(aniIn);
            checkBox.setOutAnimation(aniOut);
            checkBox.showNext();
        });
    }
}
