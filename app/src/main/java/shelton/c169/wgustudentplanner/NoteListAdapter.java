package shelton.c169.wgustudentplanner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView notePreviewView;


        private NoteViewHolder(View itemView) {
            super(itemView);
            notePreviewView = itemView.findViewById(R.id.tvNotePreview);
        }
    }

    private final LayoutInflater mInflater;
    private List<Note> mNotes;

    NoteListAdapter(Context context) {

        mInflater = LayoutInflater.from(context);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View itemView = mInflater.inflate(R.layout.notelist_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NoteViewHolder holder, int position) {
        if(mNotes != null) {
            final Note current = mNotes.get(position);
            final int noteID = current.getNoteID();
            holder.notePreviewView.setText(current.getNoteText());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context context = v.getContext();
                    Intent intent = new Intent(context, NoteDetailed.class);
                    intent.putExtra("noteID", noteID);
                    context.startActivity(intent);
                }
            });
        }
    }



    void setNotes(List<Note> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mNotes != null)
            return mNotes.size();
        else return 0;
    }
}