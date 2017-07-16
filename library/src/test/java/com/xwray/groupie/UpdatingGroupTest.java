package com.xwray.groupie;

import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class UpdatingGroupTest {

    @Mock
    GroupAdapter groupAdapter;

    @Test
    public void updateGroupChangesRange() {
        List<Item> children = new ArrayList<Item>();
        children.add(new AlwaysUpdatingItem(1));
        children.add(new AlwaysUpdatingItem(2));

        UpdatingGroup group = new UpdatingGroup();
        group.registerGroupDataObserver(groupAdapter);

        group.update(children);
        verify(groupAdapter).onItemRangeInserted(group, 0, 2);
        verifyNoMoreInteractions(groupAdapter);

        group.update(children);
        verify(groupAdapter).onItemRangeChanged(group, 0, 2);
        verifyNoMoreInteractions(groupAdapter);
    }

    @Test
    public void changeAnItemNotifiesChange() {
        List<Item> children = new ArrayList<Item>();
        Item item = new DummyItem();
        children.add(item);

        UpdatingGroup group = new UpdatingGroup();
        group.update(children);
        group.registerGroupDataObserver(groupAdapter);

        item.notifyChanged();

        verify(groupAdapter).onItemChanged(group, 0);
    }

    class AlwaysUpdatingItem extends Item {
        public AlwaysUpdatingItem(int id) {
            super(id);
        }

        @Override
        public int getLayout() {
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }

        @Override
        public ViewHolder createViewHolder(View itemView) {
            return null;
        }

        @Override
        public void bind(ViewHolder viewHolder, int position) {

        }
    }

}
