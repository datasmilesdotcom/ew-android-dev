package app.mobile.examwarrior.ui.app_intro;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cleveroad.slidingtutorial.Direction;
import com.cleveroad.slidingtutorial.IndicatorOptions;
import com.cleveroad.slidingtutorial.OnTutorialPageChangeListener;
import com.cleveroad.slidingtutorial.PageOptions;
import com.cleveroad.slidingtutorial.TransformItem;
import com.cleveroad.slidingtutorial.TutorialOptions;
import com.cleveroad.slidingtutorial.TutorialPageOptionsProvider;
import com.cleveroad.slidingtutorial.TutorialSupportFragment;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.ui.activity.ExploreActivity;
import app.mobile.examwarrior.ui.activity.RegistrationActivity;
import app.mobile.examwarrior.util.Utility;

/**
 * Created by sandesh on 1/5/17, 6:39 PM.
 */

public class AppIntroContainer extends TutorialSupportFragment
        implements OnTutorialPageChangeListener, View.OnClickListener {

    private static final String TAG = "CustomTutorialSFragment";
    private static final int TOTAL_PAGES = 7;
    private static final int ACTUAL_PAGES_COUNT = 7;

    private final View.OnClickListener mOnSkipClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "Skip button clicked", Toast.LENGTH_SHORT).show();
        }
    };

    private final TutorialPageOptionsProvider mTutorialPageOptionsProvider = new TutorialPageOptionsProvider() {
        @NonNull
        @Override
        public PageOptions provide(int position) {
            @LayoutRes int pageLayoutResId;
            TransformItem[] tutorialItems;
            position %= ACTUAL_PAGES_COUNT;
            position %= ACTUAL_PAGES_COUNT;

            switch (position) {

                case 0: {
                    pageLayoutResId = R.layout.fragment_intro_page;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.cloude, Direction.LEFT_TO_RIGHT, 0.3f),
                            TransformItem.create(R.id.logo_intro, Direction.LEFT_TO_RIGHT, 0.10f),
                            TransformItem.create(R.id.text_intro, Direction.RIGHT_TO_LEFT, 0.14f),
                    };
                    break;
                }

                case 1: {
                    pageLayoutResId = R.layout.fragment_intro_page_first;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.image_one, Direction.LEFT_TO_RIGHT, 0.3f),
                            TransformItem.create(R.id.text_one, Direction.RIGHT_TO_LEFT, 0.14f),
                    };
                    break;
                }
                case 3: {
                    pageLayoutResId = R.layout.fragment_intro_page_third;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.image_three, Direction.LEFT_TO_RIGHT, 0.3f),
                            TransformItem.create(R.id.text_three, Direction.RIGHT_TO_LEFT, 0.14f),
                    };
                    break;
                }
                case 2: {
                    pageLayoutResId = R.layout.fragment_page_second;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.image_two, Direction.LEFT_TO_RIGHT, 0.3f),
                            TransformItem.create(R.id.text_two, Direction.LEFT_TO_RIGHT, 0.14f),
                    };
                    break;
                }

                case 4: {
                    pageLayoutResId = R.layout.fragment_page_four;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.image_four, Direction.LEFT_TO_RIGHT, 0.3f),
                            TransformItem.create(R.id.text_four, Direction.LEFT_TO_RIGHT, 0.14f),
                    };
                    break;
                }
                case 5: {
                    pageLayoutResId = R.layout.fragment_page_five;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.image_five, Direction.LEFT_TO_RIGHT, 0.3f),
                            TransformItem.create(R.id.text_five, Direction.LEFT_TO_RIGHT, 0.14f),
                    };
                    break;
                }

                case 6: {
                    pageLayoutResId = R.layout.fragment_page_six;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.image_six, Direction.LEFT_TO_RIGHT, 0.3f),
                            TransformItem.create(R.id.text_six, Direction.LEFT_TO_RIGHT, 0.14f),
                    };
                    break;
                }

                default: {
                    throw new IllegalArgumentException("Unknown position: " + position);
                }
            }

            return PageOptions.create(pageLayoutResId, position, tutorialItems);
        }
    };


    private int[] pagesColors;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (pagesColors == null) {
            pagesColors = new int[]{
                    ContextCompat.getColor(getContext(), android.R.color.holo_blue_dark),
                    ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark),
                    ContextCompat.getColor(getContext(), android.R.color.holo_green_dark),
                    ContextCompat.getColor(getContext(), android.R.color.holo_blue_dark),
                    ContextCompat.getColor(getContext(), android.R.color.holo_red_dark),
                    ContextCompat.getColor(getContext(), android.R.color.holo_purple),
                    ContextCompat.getColor(getContext(), android.R.color.darker_gray)
            };
        }
        addOnTutorialPageChangeListener(this);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.explore).setOnClickListener(this);
        view.findViewById(R.id.register).setOnClickListener(this);
    }

    @Override
    protected TutorialOptions provideTutorialOptions() {
        return newTutorialOptionsBuilder(getContext())
                .setUseInfiniteScroll(false)
                .setPagesColors(pagesColors)
                .setPagesCount(TOTAL_PAGES)
                .setUseInfiniteScroll(false)
                .setPageTransformer(new ViewPager.PageTransformer() {
                    @Override
                    public void transformPage(View page, float position) {

                        if (page.findViewById(R.id.image_one) != null) {
                            page.findViewById(R.id.image_one).setTranslationY(-position);
                        }
                        if (page.findViewById(R.id.image_two) != null) {
                            page.findViewById(R.id.image_two).setTranslationX(-position);
                        }
                        if (page.findViewById(R.id.image_three) != null) {
                            page.findViewById(R.id.image_three).setTranslationY(-position);
                        }
                        if (page.findViewById(R.id.image_four) != null) {
                            page.findViewById(R.id.image_four).setTranslationY(-position);
                        }
                        if (page.findViewById(R.id.image_five) != null) {
                            page.findViewById(R.id.image_five).setTranslationY(-position);
                        }
                        if (page.findViewById(R.id.image_six) != null) {
                            page.findViewById(R.id.image_six).setTranslationY(-position);
                        }
                    }
                })
                .setTutorialPageProvider(mTutorialPageOptionsProvider)
                .setIndicatorOptions(IndicatorOptions.newBuilder(getContext())
                        .setElementSizeRes(R.dimen.indicator_size)
                        .setElementSpacingRes(R.dimen.indicator_spacing)
                        .setElementColorRes(android.R.color.darker_gray)
                        .setSelectedElementColor(Color.LTGRAY)
                        .setRenderer(RhombusRenderer.create())
                        .build())
                //.setOnSkipClickListener(mOnSkipClickListener)
                //.setTutorialPageProvider(mTutorialPageProvider)
                .build();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.custom_intro_layout;
    }

    @Override
    public void onPageChanged(int position) {
        Log.i(TAG, "onPageChanged: position = " + position);
        if (position == TutorialSupportFragment.EMPTY_FRAGMENT_POSITION) {
            Log.i(TAG, "onPageChanged: Empty fragment is visible");
        }
        if (position == 2) {
            //Utility.showMessage("Done shown");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(getActivity(), RegistrationActivity.class));
                break;
            case R.id.explore:
                startActivity(new Intent(getActivity(), ExploreActivity.class));
                break;
        }
    }
}
