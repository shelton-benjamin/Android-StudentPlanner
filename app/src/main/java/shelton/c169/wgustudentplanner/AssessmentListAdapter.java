package shelton.c169.wgustudentplanner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AssessmentListAdapter extends RecyclerView.Adapter<AssessmentListAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentTitleView;
        private final TextView assessmentDueDate;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentTitleView = itemView.findViewById(R.id.tvAssessmentTitle);
            assessmentDueDate = itemView.findViewById(R.id.tvAssessmentDueDate);
        }
    }

    private final LayoutInflater mInflater;
    private List<Assessment> mAssessments;

    AssessmentListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public void onBindViewHolder(final AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            final Assessment current = mAssessments.get(position);
            final int assessmentID = current.getAssessmentID();

            String title = String.format("Title: %s", current.getAssessmentTitle());
            String dueDate = String.format("Due Date: %s", current.getAssessmentDueDate());

            holder.assessmentTitleView.setText(title);
            holder.assessmentDueDate.setText(dueDate);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, AssessmentDetailed.class);
                    intent.putExtra("assessmentID", assessmentID);
                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.assessmentlist_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    void setAssessments(List<Assessment> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null)
            return mAssessments.size();
        else return 0;
    }
}