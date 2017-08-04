package app.mobile.examwarrior.adapters.questions;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.database.AllQuestionsBean;
import app.mobile.examwarrior.listener.NextClicklistener;
import io.github.kexanie.library.MathView;


public class BannerPagerAdapter extends PagerAdapter {

    private ArrayList<AllQuestionsBean> playLists;
    private LayoutInflater inflater;
    private NextClicklistener saveImageClickListener;

    public BannerPagerAdapter(Context context, ArrayList<AllQuestionsBean> playLists, NextClicklistener saveImageClickListener) {
        this.playLists = playLists;
        inflater = LayoutInflater.from(context);
        this.saveImageClickListener = saveImageClickListener;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return playLists.size();

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(position+1);
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View myImageLayout = inflater.inflate(R.layout.equation_page, view, false);
        MathView formula_one = (MathView) myImageLayout.findViewById(R.id.formula_one);
        formula_one.config(
                "MathJax.Hub.Config({\n"+
                        "  CommonHTML: { linebreaks: { automatic: true } },\n"+
                        "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n"+
                        "         SVG: { linebreaks: { automatic: true } }\n"+
                        "});");
        formula_one.setText(playLists.get(position).getQuestion());
        formula_one.getSettings().setLoadWithOverviewMode(true);
        Button next = (Button) myImageLayout.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageClickListener.onNextClick(position);
            }
        });
        Button previous = (Button) myImageLayout.findViewById(R.id.back);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageClickListener.onBackClick(position);
            }
        });
        next.setVisibility(View.VISIBLE);
        previous.setVisibility(View.VISIBLE);
        if (position + 1 == getCount()) {
            next.setVisibility(View.GONE);
        }
        if (position == 0) {
            previous.setVisibility(View.GONE);
        }
        view.addView(myImageLayout);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}