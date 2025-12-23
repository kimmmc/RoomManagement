package com.example.roommanagement.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.roommanagement.R;
import java.util.List;

public class GenericAdapter extends RecyclerView.Adapter<GenericAdapter.ViewHolder> {

    private List<Object> items;
    private OnActionClickListener listener;

    public interface OnActionClickListener {
        void onEdit(Object item);

        void onDelete(Object item);
    }

    public GenericAdapter(List<Object> items, OnActionClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_db_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Object item = items.get(position);

        if (item instanceof DBRoom) {
            DBRoom room = (DBRoom) item;
            holder.tvTitle.setText(room.getName());
            holder.tvSubtitle.setText(room.getCategory());
            holder.tvDetail.setText("Cap: " + room.getCapacity() + " | " + room.getPrice());
            holder.tvIconChar.setText(room.getName().length() > 0 ? room.getName().substring(0, 1) : "R");
        } else if (item instanceof Course) {
            Course course = (Course) item;
            holder.tvTitle.setText(course.getTitle());
            holder.tvSubtitle.setText(course.getDescription());
            holder.tvDetail.setText("Start: " + course.getStartDate());
            holder.tvIconChar.setText(course.getTitle().length() > 0 ? course.getTitle().substring(0, 1) : "C");
        } else if (item instanceof Student) {
            Student student = (Student) item;
            holder.tvTitle.setText(student.getName());
            holder.tvSubtitle.setText(student.getEmail());
            holder.tvDetail.setText(student.getGender());
            holder.tvIconChar.setText(student.getName().length() > 0 ? student.getName().substring(0, 1) : "S");
        }

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null)
                listener.onEdit(item);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null)
                listener.onDelete(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateData(List<Object> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSubtitle, tvDetail, tvIconChar;
        View btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
            tvDetail = itemView.findViewById(R.id.tvDetail);
            tvIconChar = itemView.findViewById(R.id.tvIconChar);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
