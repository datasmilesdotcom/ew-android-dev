package app.mobile.examwarrior.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.mobile.examwarrior.R;
import de.timfreiheit.mathjax.android.MathJaxView;

/**
 * Created by jeevan on 18/06/17.
 */

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {
    private Context context;
    private String[] items;

    public QuestionsAdapter(Context context, String[] items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(this.context).inflate(R.layout.question_row, parent, false);
        return new ViewHolder(cardView);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private MathJaxView txtTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            this.txtTitle = (MathJaxView) itemView.findViewById(R.id.laTexView);
        }

        public void bind(String que) {
            this.txtTitle.setInputText(que);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(this.items[position]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }
}
