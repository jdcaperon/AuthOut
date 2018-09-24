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
package rocketpotatoes.authout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChildSignupListAdapter extends RecyclerView.Adapter<ChildSignupListAdapter.ChildViewHolder> {

    private static final int FIRST_NAME = 0;
    private static final int SURNAME = 1;
    private static final int DATE_OF_BIRTH = 2;
    private SignUpChildActivity activity;
    private ArrayList<ArrayList<String>> childList;

    /* View holder class that sets up layout of each child in list */
    public class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView info;
        public Button edit;
        public Button delete;

        /* Constructor */
        public ChildViewHolder(View view) {
            super(view);

            info = view.findViewById(R.id.childInfo);
            edit = view.findViewById(R.id.editButton);
            delete = view.findViewById(R.id.deleteButton);
        }

    }

    public ChildSignupListAdapter(ArrayList<ArrayList<String>> childList, Context context) {
        this.activity = (SignUpChildActivity) context;
        this.childList = childList;
    }

    @Override
    public void onBindViewHolder(final ChildViewHolder holder, int position) {
        if (childList.size() > 0) {
            holder.info.setText(generateRowString(childList.get(position)));
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childList.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                activity.firstName.setText(childList.get(position).get(FIRST_NAME));
                activity.surname.setText(childList.get(position).get(SURNAME));
                activity.dateOfBirth.setText(childList.get(position).get(DATE_OF_BIRTH));
                childList.remove(position);
                notifyDataSetChanged();

            }
        });
    }

    private String generateRowString(List<String> firstLastDOB) {
        if (firstLastDOB.get(FIRST_NAME).length() + firstLastDOB.get(SURNAME).length() > 13) {
            if (firstLastDOB.get(FIRST_NAME).length() >= firstLastDOB.get(SURNAME).length()) {
                return firstLastDOB.get(FIRST_NAME).toUpperCase().charAt(0) + ". " +
                        firstLastDOB.get(SURNAME) + " - " + firstLastDOB.get(DATE_OF_BIRTH);
            } else {
                return firstLastDOB.get(FIRST_NAME) + " " +
                        firstLastDOB.get(SURNAME).toUpperCase().charAt(0) + ". - " +
                        firstLastDOB.get(DATE_OF_BIRTH);
            }
        }
        return firstLastDOB.get(FIRST_NAME) + " " + firstLastDOB.get(SURNAME) + " - " + firstLastDOB.get(DATE_OF_BIRTH);
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_signup_row, parent, false);

        return new ChildViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return childList.size();
    }
}