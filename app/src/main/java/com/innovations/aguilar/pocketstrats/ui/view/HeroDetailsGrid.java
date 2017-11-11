package com.innovations.aguilar.pocketstrats.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.LeadingMarginSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.innovations.aguilar.pocketstrats.R;
import com.innovations.aguilar.pocketstrats.sql.dto.HeroDataDTO;
import com.innovations.aguilar.pocketstrats.sql.dto.MapHeroPickTipDTO;
import com.innovations.aguilar.pocketstrats.ui.CustomTypeFaces;
import com.innovations.aguilar.pocketstrats.ui.CustomTypefaceSpan;
import com.innovations.aguilar.pocketstrats.ui.ImageEffects;
import com.innovations.aguilar.pocketstrats.ui.ImageResources;
import com.innovations.aguilar.pocketstrats.ui.SpanBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HeroDetailsGrid extends IconListWithDetailsView {
    protected static Logger log = LoggerFactory.getLogger(HeroDetailsGrid.class);

    private Map<HeroDataDTO, List<MapHeroPickTipDTO>> heroToTipMap;
    SpanBuilder.SpanConfigurator<MapHeroPickTipDTO> heroPickConfigurator = new HeroPickTipSpanConfigurator(getContext());

    public HeroDetailsGrid(Context context) {
        super(context);
    }

    public HeroDetailsGrid(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeroDetailsGrid(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        manager.setAutoMeasureEnabled(true);
        setLayoutManager(manager);
    }

    public void setHeroPickTips(Map<HeroDataDTO,List<MapHeroPickTipDTO>> heroToTipMap) {
        this.heroToTipMap = heroToTipMap;

        List<HeroDataDTO> orderedHeros = nameOrderHeros(heroToTipMap.keySet());

        int selectedIndex = 0;
        HeroIconAdapter iconDataAdapter = new HeroIconAdapter(getContext(), orderedHeros, selectedIndex);
        HeroDetailsDataAdapter detailsDataAdapter = new HeroDetailsDataAdapter(getContext(), iconDataAdapter);
        setAdapters(iconDataAdapter, detailsDataAdapter, selectedIndex);
    }


    List<HeroDataDTO> nameOrderHeros(Set<HeroDataDTO> heroPickTips) {
        Set<HeroDataDTO> heroSet = Sets.newTreeSet(new Comparator<HeroDataDTO>() {
            @Override
            public int compare(HeroDataDTO heroA, HeroDataDTO heroB) {
                return heroA.getHeroNameLatin().compareTo(heroB.getHeroNameLatin());
            }
        });

        heroSet.addAll(heroPickTips);

        return Collections.unmodifiableList(Lists.newArrayList(heroSet));
    }

    private Spannable createSpanForHero(HeroDataDTO hero) {
        List<MapHeroPickTipDTO> heroPickTips;
        if (!heroToTipMap.containsKey(hero)) {
            heroPickTips = Lists.newArrayList();
            log.warn("Hero '{}' is missing tips from tip map", hero);
        }
        else
            heroPickTips = heroToTipMap.get(hero);

        SpanBuilder builder = new SpanBuilder(true);
        for (MapHeroPickTipDTO heroPickTip :
                heroPickTips) {
            builder.appendSpanData(heroPickTip, heroPickConfigurator);
        }

        return builder.getSpan();
    }

    public class HeroIconAdapter extends RecyclerView.Adapter<HeroIconViewHolder> {
        private final Context context;
        private int selectedIndex = 0;

        public List<HeroDataDTO> getHeros() {
            return heros;
        }

        private final List<HeroDataDTO> heros;

        public HeroIconAdapter(Context context, List<HeroDataDTO> heros, int selectedIndex) {
            this.context = context;
            this.heros = heros;
            this.selectedIndex = selectedIndex;
        }

        @Override
        public HeroIconViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View iconView = inflater.inflate(R.layout.grid_hero_details_icon, parent, false);

            final HeroIconViewHolder holder =  new HeroIconViewHolder(context, iconView);
            holder.addOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentIndex = holder.getAdapterPosition();
                    if(selectedIndex != currentIndex){
                        int lastSelectedIndex = selectedIndex;
                        selectedIndex = currentIndex;
                        HeroIconViewHolder lastSelectedHolder = (HeroIconViewHolder)iconList.findViewHolderForAdapterPosition(lastSelectedIndex);
                        lastSelectedHolder.setSelected(false);
                        holder.setSelected(true);
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(HeroIconViewHolder holder, int position) {
            HeroDataDTO hero = heros.get(position);
            holder.configureForHeroData(hero);
            holder.setSelected(selectedIndex == position);
        }

        @Override
        public int getItemCount() {
            return heros.size();
        }
    }

    public class HeroIconViewHolder extends IconListWithDetailsView.IconViewHolder {

        private final Context context;
        private HeroDataDTO configuredHero;
        private TextView iconView;
        private TextView iconName;

        public HeroIconViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            this.iconView = (TextView)itemView.findViewById(R.id.icon_list_hero_details);
            this.iconName = (TextView)itemView.findViewById(R.id.icon_list_hero_name);
        }

        public void configureForHeroData(HeroDataDTO hero) {
            configuredHero = hero;
            Drawable iconBackground = ImageResources.getHeroIcon(context, hero);
            iconView.setBackground(iconBackground);
            iconName.setTypeface(CustomTypeFaces.BigNoodleTitlingOblique(context.getAssets()));
            iconName.setText(hero.getHeroName());
        }

        public void setSelected(boolean selected) {
            itemView.setSelected(selected);
            Drawable newBackground = iconView.getBackground();
            if (selected) {
                Drawable currentBackground = DrawableCompat.wrap(iconView.getBackground());
                Drawable highlight = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.bg_icon_highlight));
                highlight.setAlpha(128);
                LayerDrawable selectedLayering = ImageEffects.layerDrawables(context, currentBackground, highlight);
                newBackground = selectedLayering;
            }
            else{
                if (configuredHero != null)
                    newBackground = ImageResources.getHeroIcon(context, configuredHero);
            }
            iconView.setBackground(newBackground);
        }
    }

    public class HeroDetailsDataAdapter implements IconListWithDetailsView.DetailsDataAdapter {
        private HeroIconAdapter iconDataAdapter;
        private final Context context;

        public HeroDetailsDataAdapter(Context context, HeroIconAdapter iconDataAdapter) {
            this.iconDataAdapter = iconDataAdapter;
            this.context = context;
        }

        private HeroDataDTO getHero(int position) {
            return iconDataAdapter.getHeros().get(position);
        }

        @Override
        public View onCreateView(ViewGroup parent, View existingView, int position) {
            if (existingView != null)
                return existingView;
            else {
                LayoutInflater inflater = LayoutInflater.from(context);
                View detailsView = inflater.inflate(R.layout.grid_hero_details_text, parent, false);
                TextView tipTextView = (TextView) detailsView.findViewById(R.id.text_hero_details);
                tipTextView.setMovementMethod(LinkMovementMethod.getInstance());

                return detailsView;
            }
        }

        @Override
        public void onBindView(View bindView, int position) {
            HeroDataDTO hero = getHero(position);
            TextView heroDetails = (TextView)bindView.findViewById(R.id.text_hero_details);
            Spannable heroDetailsSpan = createSpanForHero(hero);
            heroDetails.setText(heroDetailsSpan);
        }
    }

    static class HeroPickTipSpanConfigurator extends ChunkedTipSpanConfigurator implements SpanBuilder.SpanConfigurator<MapHeroPickTipDTO> {

        static final String Prefix = "• ";
        private Context context;

        public HeroPickTipSpanConfigurator(Context context) {
            this.context = context;
        }

        @Override
        public int configure(SpannableStringBuilder builder, MapHeroPickTipDTO spanData, int runningOffset) {
            String tipDetail = spanData.getMapTipDescription();

            return configureChunkedTip(builder, Prefix, tipDetail, runningOffset);
        }



        @Override
        protected void styleLinkSpan(SpannableStringBuilder builder, int startSegmentOffset, int endSegmentOffset, final String mapCalloutLink) {
            ClickableSpan linkSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    log.debug("Clicked {}", mapCalloutLink);
                }
            };
            builder.setSpan(linkSpan, startSegmentOffset, endSegmentOffset, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styleTextSpan(builder, startSegmentOffset, endSegmentOffset);
        }
        @Override
        protected void styleTextSpan(SpannableStringBuilder builder, int startSegmentOffset, int endSegmentOffset) {

            builder.setSpan(new AbsoluteSizeSpan(
                            context.getResources().getDimensionPixelSize(R.dimen.paragraph_text_size_medium)),
                    startSegmentOffset, endSegmentOffset, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new CustomTypefaceSpan(
                            CustomTypeFaces.Futura(context.getAssets())),
                    startSegmentOffset, endSegmentOffset, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            int leading = context.getResources().getDimensionPixelSize(R.dimen.tip_text_indent_tip);
            int offset = context.getResources().getDimensionPixelSize(R.dimen.tip_text_indent_offset);
            LeadingMarginSpan adjustSpan = new LeadingMarginSpan.Standard(leading, leading + offset);
            builder.setSpan(adjustSpan, startSegmentOffset, endSegmentOffset, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

    }
}




