package rocketpotatoes.authout.Helpers;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import rocketpotatoes.authout.R;
import rocketpotatoes.authout.SelectStudentActivity;

public class ChildSelectorAdapter extends RecyclerView.Adapter<ChildSelectorAdapter.ChildViewHolder> {

    private Context context;
    private Button dynamicButton;
    private HashSet<Child> selectedItems;
    private List<Child> childList;

    /** View holder class that sets up layout of each child in list **/
    public class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        /** Constructor **/
        public ChildViewHolder(View view) {
            super(view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int selectedPosition = getAdapterPosition();
                    Child selectedItem = childList.get(selectedPosition);
                    if (selectedItems.contains(selectedItem)) {
                        selectedItems.remove(selectedItem);
                        view.setBackgroundColor(0x00000000);
                    } else {
                        selectedItems.add(selectedItem);
                        view.setBackgroundColor(0xcc03BABD);
                    }
                    if (dynamicButton != null) {
                        changeButtonSettings(SelectStudentActivity.getOptionByChildren(new ArrayList<>(selectedItems)));
                    }
                }
            });
            name = (TextView) view.findViewById(R.id.childlist_name);
        }

    }

    /** Constructor
     * 
     * @param ChildList A list of Child objects that are inserted into the list on creation.
     */
    public ChildSelectorAdapter(List<Child> ChildList) {
        selectedItems = new HashSet<>();
        selectedItems.addAll(ChildList);
        this.childList = ChildList;
    }

    /** Constructor
     *
     * @param ChildList A list of Child objects that are inserted into the list on creation.
     */
    public ChildSelectorAdapter(Context context, List<Child> ChildList, Button dynamicButton) {
        this.selectedItems = new HashSet<>();
        this.selectedItems.addAll(ChildList);
        this.childList = ChildList;
        this.dynamicButton = dynamicButton;
        this.context = context;
        changeButtonSettings(SelectStudentActivity.getOptionByChildren(childList));
    }

    @Override
    public void onBindViewHolder(ChildViewHolder holder, int position) {
        Child child = childList.get(position);
        holder.name.setText(child.getFirstName());
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_component_child, parent, false);

        return new ChildViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return childList.size();
    }

    private void changeButtonSettings(DynamicButtonOption option) {
        switch(option) {
            case SIGN_IN:
                dynamicButton.setText(R.string.sign_in);
                dynamicButton.setEnabled(true);
                dynamicButton.setBackgroundColor(context.getColor(R.color.colorAccent));
                ((ColorDrawable) dynamicButton.getBackground()).setColor(context.getColor(R.color.colorAccent));
                break;
            case SIGN_OUT:
                dynamicButton.setText(R.string.sign_out);
                dynamicButton.setEnabled(true);
                ((ColorDrawable) dynamicButton.getBackground()).setColor(context.getColor(R.color.colorAccent));
                break;
            case NOT_COMPATIBLE: ;
                dynamicButton.setText(R.string.not_compatible);
                dynamicButton.setEnabled(false);
                ((ColorDrawable) dynamicButton.getBackground()).setColor(context.getColor(R.color.errorButtonColor));
                break;
        }
    }

}
