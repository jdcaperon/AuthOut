/*
 * MIT License

 Copyright (c) 2018 Jack Caperon

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package rocketpotatoes.authout.Helpers;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import rocketpotatoes.authout.R;
import rocketpotatoes.authout.SelectStudentActivity;

public class ChildSelectorAdapter extends RecyclerView.Adapter<ChildSelectorAdapter.ChildViewHolder> {

    private SelectStudentActivity activity;
    private HashSet<Child> selectedItems;
    private List<Child> childList;

    /* View holder class that sets up layout of each child in list */
    public class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        /* Constructor */
        public ChildViewHolder(View view) {
            super(view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int selectedPosition = getAdapterPosition();
                    Child selectedItem = childList.get(selectedPosition);
                    if (selectedItems.contains(selectedItem)) {
                        selectedItems.remove(selectedItem);
                        CheckBox checkBox = (CheckBox) view.findViewById(R.id.childlist_checkbox);
                        checkBox.setChecked(false);
                        view.setBackgroundColor(Color.parseColor("#FAFAFA"));
                    } else {
                        selectedItems.add(selectedItem);
                        CheckBox checkBox = (CheckBox) view.findViewById(R.id.childlist_checkbox);
                        checkBox.setChecked(true);
                        view.setBackground(activity.getResources().getDrawable(R.drawable.child_selected_background));


                    }
                    activity.changeTextAndButton(activity.getOptionByChildren(new ArrayList<Child>(selectedItems)), new ArrayList<Child>(selectedItems));
                }
            });
            name = (TextView) view.findViewById(R.id.childlist_name);
        }

    }

    /** Constructor
     * 
     * @param ChildList A list of Child objects that are inserted into the list on creation.
     */
    public ChildSelectorAdapter(List<Child> ChildList, Context context) {
        this.activity = (SelectStudentActivity) context;
        this.childList = ChildList;
        selectedItems = new HashSet<>(ChildList);

        activity.changeTextAndButton(activity.getOptionByChildren(new ArrayList<Child>(selectedItems)), new ArrayList<Child>(selectedItems));
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

    public HashSet<Child> getSelectedItems() {
        return selectedItems;
    }
}
