package app.mobile.examwarrior.demo;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.ViewHolder;

import app.mobile.examwarrior.R;
import app.mobile.examwarrior.util.decoration.HeaderItemDecoration;
import app.mobile.examwarrior.util.decoration.InsetItemDecoration;

public class DemoActivity extends AppCompatActivity implements ToggleListener {

    public static final String TAG = DemoActivity.class.getSimpleName();
    GroupAdapter groupAdapter;
    ExpandableGroup expandableGroup;
    ExpandableGroup expandableGroup1;
    ExpandableGroup expandableGroup2;
    private FragmentManager fragmentManager;
    private GridLayoutManager layoutManager;

    @NonNull
    private static Rect createRect(@NonNull ViewGroup parent, @NonNull View view) {
        Rect rect = new Rect();
        view.getDrawingRect(rect);
        parent.offsetDescendantRectToMyCoords(view, rect);

        return rect;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int betweenPadding = getResources().getDimensionPixelSize(R.dimen.padding_small);
        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.addItemDecoration(new HeaderItemDecoration(Color.WHITE, betweenPadding));
        recyclerview.addItemDecoration(new InsetItemDecoration(Color.WHITE, betweenPadding));
        groupAdapter = new GroupAdapter();


        layoutManager = new GridLayoutManager(this, groupAdapter.getSpanCount());
        layoutManager.setSpanSizeLookup(groupAdapter.getSpanSizeLookup());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(groupAdapter);
      /*  final MyItem myItem = new MyItem();
        myItem.setToggleListener(this);
        expandableGroup = new ExpandableGroup(myItem);
        myItem.setExpandableGroup(expandableGroup);*/
        final MyContent myContentrem = new MyContent();
        expandableGroup.add(myContentrem);
        //expandableGroup.add(myItem);

        for (int i = 0; i < 3; i++) {
            MyContent myContent = new MyContent();
            expandableGroup.add(myContent);
        }
        groupAdapter.add(expandableGroup);


/*        MyItem myItem1 = new MyItem();
        myItem1.setToggleListener(this);
        expandableGroup1 = new ExpandableGroup(new MyItem());
        myItem1.setExpandableGroup(expandableGroup1);*/
        //expandableGroup.add(myItem);

        for (int i = 0; i < 3; i++) {
            MyContent myContent1 = new MyContent();
            expandableGroup1.add(myContent1);
        }
        groupAdapter.add(expandableGroup1);
//        ((ExpandableGroup) groupAdapter.getGroup(myItem)).add(myContent);
//        ((ExpandableGroup) groupAdapter.getGroup(myItem)).add(groupAdapter.getItem(1));
//        groupAdapter.remove(groupAdapter.getGroup(myItem).getItem(0));

      /*  MyItem myItem2 = new MyItem();
        myItem2.setToggleListener(this);
        expandableGroup2 = new ExpandableGroup(myItem2);
        myItem2.setExpandableGroup(expandableGroup2);*/

        //expandableGroup.add(myItem);

        for (int i = 0; i < 3; i++) {
            MyContent myContent2 = new MyContent();
            expandableGroup2.add(myContent2);
        }
        groupAdapter.add(expandableGroup2);

//        expandableGroup.add(new MyContent());

        /*RevealFrameLayout revealFrameLayout = (RevealFrameLayout) findViewById(R.id.frame);
        revealFrameLayout.clearAnimation();*/
       // Log.e(TAG, "onClick ****: " + ((ExpandableGroup) groupAdapter.getGroup(myItem)).getItemCount());
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//                   groupAdapter.remove(expandableGroup.getGroup(0));
                    //Log.e(TAG, "onClick count: " + expandableGroup.getItemCount() + " " + groupAdapter.getItemCount(0));

                    expandableGroup.remove(expandableGroup.getChildCount() - 1);
                    //expandableGroup.remove(myContentrem);
                    //expandableGroup.onItemRemoved(myContentrem, 0);
                    //expandableGroup.onChanged(myContentrem);
                    //groupAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.e(TAG, "onClick: " + e.getCause());
                }
            }
        });

        final FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                   // expandableGroup.add(expandableGroup.getChildCount(), new MyItem());
                } catch (Exception e) {
                    Log.e(TAG, "onClick: " + e.getCause());
                }
            }
        });
    }

    /**
     * Add fragment to the container
     *
     * @param containerViewId
     * @param fragment
     * @param fragmentTag
     */
    protected void addFragment(int containerViewId,
                               Fragment fragment,
                               String fragmentTag) {

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.fragment_anim_enter_from_left,
        // R.anim.fragment_anim_exit_to_right,R.anim.fragment_anim_enter_from_right,
        // R.anim.fragment_anim_exit_to_left);
        fragmentTransaction.replace(containerViewId, fragment, fragmentTag);
        //fragmentTransaction.addToBackStack(fragmentTag);
        fragmentTransaction.commit();
    }

    @Override
    public void onToggle(ViewHolder viewHolder, int position, boolean isExpanded) {
        if (isExpanded ) {
            groupAdapter.getGroup(viewHolder.getItem());
        }
    }
}
