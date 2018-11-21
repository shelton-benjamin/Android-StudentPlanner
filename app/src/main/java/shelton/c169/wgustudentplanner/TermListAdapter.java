package shelton.c169.wgustudentplanner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termTitleView;
        private final TextView termStartDateView;
        private final TextView termEndDateView;

        private TermViewHolder(View itemView) {
            super(itemView);
            termTitleView = itemView.findViewById(R.id.tvTermTitle);
            termStartDateView = itemView.findViewById(R.id.tvTermStartDate);
            termEndDateView = itemView.findViewById(R.id.tvTermEndDate);
        }
    }

    private final LayoutInflater mInflater;
    private List<Term> mTerms;


    TermListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

    }


    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.termlist_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TermViewHolder holder, int position) {
        if (mTerms != null) {
            final Term current = mTerms.get(position);
            final int termID = current.getTermID();

            String title = String.format("Title: %s", current.getTermTitle());
            String startDate = String.format("Start Date: %s", current.getTermStartDate());
            String endDate = String.format("End Date: %s", current.getTermEndDate());

            holder.termTitleView.setText(title);
            holder.termStartDateView.setText(startDate);
            holder.termEndDateView.setText(endDate);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context context = v.getContext();

                    Intent intent = new Intent(context, TermDetailed.class);

                    intent.putExtra("termID", termID);
                    context.startActivity(intent);
                }
            });
        }
    }

    void setTerms(List<Term> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTerms != null)
            return mTerms.size();
        else return 0;
    }


}