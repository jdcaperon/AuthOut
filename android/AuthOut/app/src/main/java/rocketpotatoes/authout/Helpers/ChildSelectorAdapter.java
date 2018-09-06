package rocketpotatoes.authout.Helpers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rocketpotatoes.authout.R;

public class ChildSelectorAdapter extends RecyclerView.Adapter<ChildSelectorAdapter.MyViewHolder> {

    private List<Child> childList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.childlist_name);
        }
    }

    public ChildSelectorAdapter(List<Child> ChildList) {
        this.childList = ChildList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Child child = childList.get(position);
        holder.name.setText(child.getFirstName());
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
