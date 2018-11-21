package shelton.c169.wgustudentplanner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseTitleView;
        private final TextView courseStartDateView;
        private final TextView courseEndDateView;
        private final TextView courseStatusView;


        private CourseViewHolder(View itemView) {
            super(itemView);
            courseTitleView = itemView.findViewById(R.id.tvCourseTitle);
            courseStartDateView = itemView.findViewById(R.id.tvCourseStartDate);
            courseEndDateView = itemView.findViewById(R.id.tvCourseEndDate);
            courseStatusView = itemView.findViewById(R.id.tvCourseStatus);
        }
    }

    private final LayoutInflater mInflater;
    private List<Course> mCourses;

    CourseListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.courselist_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CourseViewHolder holder, int position) {
        if (mCourses != null) {
            final Course current = mCourses.get(position);
            final int courseID = current.getCourseID();

            String title = String.format("Title: %s", current.getCourseTitle());
            String startDate = String.format("Start Date: %s", current.getCourseStartDate());
            String endDate = String.format("End Date: %s", current.getCourseEndDate());
            String status = String.format("Status: %s", current.getCourseStatus());

            holder.courseTitleView.setText(title);
            holder.courseStartDateView.setText(startDate);
            holder.courseEndDateView.setText(endDate);
            holder.courseStatusView.setText(status);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, CourseDetailed.class);
                    intent.putExtra("courseID", courseID);
                    context.startActivity(intent);
                }
            });
        }
    }

    void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }


}