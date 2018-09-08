package rocketpotatoes.authout.Helpers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;

import rocketpotatoes.authout.R;

public class ChildSelectorAdapter extends RecyclerView.Adapter<ChildSelectorAdapter.MyViewHolder> {

    HashSet<Child> selectedItems;
    private List<Child> childList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int selectedPosition = getAdapterPosition();
                    Child selectedItem = childList.get(selectedPosition);
                    if(selectedItems.contains(selectedItem)){
                        selectedItems.remove(selectedItem);
                        view.setBackgroundColor(0x00000000);
                    } else {
                        selectedItems.add(selectedItem);
                        view.setBackgroundColor(0xcc03BABD);
                    }
                }
            });
            name = (TextView) view.findViewById(R.id.childlist_name);
        }

    }

    public ChildSelectorAdapter(List<Child> ChildList) {
        selectedItems = new HashSet<>();
        selectedItems.addAll(ChildList);
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
