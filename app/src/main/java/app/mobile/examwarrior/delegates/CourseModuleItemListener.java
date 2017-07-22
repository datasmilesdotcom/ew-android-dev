package app.mobile.examwarrior.delegates;

import app.mobile.examwarrior.database.ModuleItem;
import app.mobile.examwarrior.expandable_list.viewholders.ChildViewHolder;

/**
 * Created by sandesh on 20/7/17, 4:37 PM.
 */

public interface CourseModuleItemListener {

    public void onModuleClickListener(ChildViewHolder holder, int position, ModuleItem data);
}
