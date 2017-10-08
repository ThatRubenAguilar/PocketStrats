package com.innovations.aguilar.pocketstrats.ui.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BulletSpan;
import android.text.style.IconMarginSpan;
import android.text.style.LeadingMarginSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.google.common.base.Supplier;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.ui.CustomTypeFaces;
import com.innovations.aguilar.pocketstrats.ui.TipText;

public class MapSubjectDetailsTipItemViewHolder extends RecyclerView.ViewHolder {

    TextView tipText;
    private final Context context;

    public MapSubjectDetailsTipItemViewHolder(View itemView, final Context context) {
        super(itemView);
        tipText = (TextView) itemView.findViewById(R.id.text_list_map_tip_item);
        this.context = context;
    }


    public void configureMapTipItem(final TipText tip) {
        if (tip.isSection()) {
            tipText.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(context.getAssets()));
            tipText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.button_text_size_medium));
            SpannableString spannable = new SpannableString(tip.getMessage());

            int leading = context.getResources().getDimensionPixelSize(R.dimen.tip_text_indent_section);
            int offset = context.getResources().getDimensionPixelSize(R.dimen.tip_text_indent_offset);
            LeadingMarginSpan adjustSpan = new LeadingMarginSpan.Standard(leading, leading + offset);

            spannable.setSpan(adjustSpan, 0, tip.getMessage().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tipText.setText(spannable);
        }
        else {
            SpannableString spannable = new SpannableString(tip.getMessage());

            int leading = context.getResources().getDimensionPixelSize(R.dimen.tip_text_indent_tip);
            int offset = context.getResources().getDimensionPixelSize(R.dimen.tip_text_indent_offset);
            LeadingMarginSpan adjustSpan = new LeadingMarginSpan.Standard(leading, leading + offset);

            spannable.setSpan(adjustSpan, 0, tip.getMessage().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tipText.setText(spannable);
        }
    }
}
