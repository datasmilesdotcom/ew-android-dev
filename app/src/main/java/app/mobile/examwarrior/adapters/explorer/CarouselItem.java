package app.mobile.examwarrior.adapters.explorer;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import app.mobile.examwarrior.R;

/**
 * Created by sandesh on 26/8/17, 2:41 PM.
 */

public class CarouselItem extends Item<ViewHolder> {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.ItemDecoration carouselDecoration;
    private LinearLayoutManager layoutManager;

    public CarouselItem(RecyclerView.ItemDecoration itemDecoration) {
        this.carouselDecoration = itemDecoration;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        recyclerView = viewHolder.getRoot().findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // We don't know if the layout we're passed has been bound before so
        // we need to ensure we don't register the item decoration multiple times,
        // by trying to remove it first. (Nothing happens if it's not registered.)
        //recyclerView.removeItemDecoration(carouselDecoration);
        //recyclerView.addItemDecoration(carouselDecoration);
    }

    @Override
    public int getLayout() {
        return R.layout.item_carousel;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }
}
