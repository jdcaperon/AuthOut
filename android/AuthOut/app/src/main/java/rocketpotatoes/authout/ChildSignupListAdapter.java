package rocketpotatoes.authout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ChildSignupListAdapter extends BaseAdapter{
    private Context context;
    private List<List<String>> data;
    private static LayoutInflater inflater = null;

    public ChildSignupListAdapter(Context context, List<List<String>> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.child_signup_row, null);
        TextView text = (TextView) vi.findViewById(R.id.childInfo);
        text.setText(generateString(data.get(position)));
        return vi;
    }

    private String generateString(List<String> strings) {
        return strings.get(0) + " " + strings.get(1) + " - " + strings.get(2);
    }
}
