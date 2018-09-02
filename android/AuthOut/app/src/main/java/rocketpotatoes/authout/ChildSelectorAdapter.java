package rocketpotatoes.authout;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

class ChildSelectorAdapter extends RecyclerView.Adapter<ChildSelectorAdapter.MyViewHolder> {


    private List<Child> childList;
    private int selectedChild = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public CheckBox checkbox;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.childlist_name);
            checkbox = (CheckBox) view.findViewById(R.id.child_select);
            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedChild = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }

    public ChildSelectorAdapter(List<Child> ChildList) {
        this.childList = ChildList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Child child = childList.get(position);
        holder.name.setText(child.getFirstName());
        holder.checkbox.setChecked(position == selectedChild);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_component_child, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return childList.size();
    }
    
}
