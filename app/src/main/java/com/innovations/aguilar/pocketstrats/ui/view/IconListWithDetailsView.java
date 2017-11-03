package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.innovations.aguilar.pocketstrats.R;

import java.util.List;

public class IconListWithDetailsView extends LinearLayout {

    private RecyclerView.Adapter iconAdapter;
    private DetailsDataAdapter detailsAdapter;

    protected RecyclerView iconList;
    private View detailsView = null;

    public IconListWithDetailsView(Context context) {
        super(context);
        inflateLayout();
    }

    public IconListWithDetailsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateLayout();
    }

    public IconListWithDetailsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout();
    }
    private void inflateLayout() {
        inflate(getContext(), R.layout.list_icons_with_details_layout, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        iconList = (RecyclerView)findViewById(R.id.list_icons);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        iconList.setLayoutManager(layout);
    }

    public <VH extends IconViewHolder> void setAdapters(RecyclerView.Adapter<VH> iconAdapter, DetailsDataAdapter detailsAdapter, int initialSelectedIndex) {
        setAdapters(iconAdapter, detailsAdapter);
        onIndexClick(null, initialSelectedIndex);
    }
    public <VH extends IconViewHolder> void setAdapters(RecyclerView.Adapter<VH> iconAdapter, DetailsDataAdapter detailsAdapter) {
        this.iconAdapter = iconAdapter;
        iconList.setAdapter(new WrapperDataAdapter(iconAdapter));
        this.detailsAdapter = detailsAdapter;
    }

    private void onIndexClick(View v, int position) {
        if (position != RecyclerView.NO_POSITION) {
            View existingDetailsView = detailsView;
            detailsView = detailsAdapter.onCreateView(this, existingDetailsView, position);
            detailsAdapter.onBindView(detailsView, position);
            if (existingDetailsView != null)
                this.removeView(existingDetailsView);
            this.addView(detailsView);
        } else {
            Log.e(this.getClass().toString(), "No position in recyclerView");
        }
    }

    private class WrapperDataAdapter<VH extends IconViewHolder> extends RecyclerView.Adapter<VH> {
        private RecyclerView.Adapter<VH> wrappedAdapter;

        public WrapperDataAdapter(RecyclerView.Adapter<VH> wrappedAdapter) {
            this.wrappedAdapter = wrappedAdapter;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            final VH holder = wrappedAdapter.onCreateViewHolder(parent, viewType);
            holder.addOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onIndexClick(v, holder.getAdapterPosition());
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(final VH holder, final int position) {
            wrappedAdapter.onBindViewHolder(holder, position);
        }

        @Override
        public int getItemCount() {
            return wrappedAdapter.getItemCount();
        }
    }

    public static class IconViewHolder extends RecyclerView.ViewHolder {
        private List<OnClickListener> clickListeners = null;

        public IconViewHolder(View itemView) {
            super(itemView);
            configureView(itemView);
        }

        public void addOnClickListener(OnClickListener listener) {
            Preconditions.checkNotNull(listener, "Listener should not be null");
            if (clickListeners == null)
                clickListeners = Lists.newArrayList();
            clickListeners.add(listener);
        }

        private void configureView(View itemView) {
            if (itemView.hasOnClickListeners())
                Log.w(this.getClass().toString(), "IconViewHolder is overwriting an existing OnClickListener, use addOnClickListener to avoid this.");

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (OnClickListener listener :
                            clickListeners) {
                        listener.onClick(v);
                    }
                }
            });
        }
    }

    public interface DetailsDataAdapter {
        View onCreateView(ViewGroup parent, View existingView, int position);
        void onBindView(View bindView, int position);
    }

}
