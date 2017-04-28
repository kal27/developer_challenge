package developer.test.com.developerchallenge.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import developer.test.com.developerchallenge.R;
import developer.test.com.developerchallenge.ResponseClasses.Flight;

public class FlightArrayAdapter extends ArrayAdapter<Flight> {
    private Context context;
    private List<Flight> items;
    private Resources resources;


    public FlightArrayAdapter(Context context, List<Flight> objects) {
        super(context, R.layout.single_item_layout, objects);
        this.context = context;
        items = objects;
        resources = context.getResources();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.single_item_layout, parent, false);

            convertView.setTag(viewHolder);

            viewHolder.dateFrom = (TextView) convertView.findViewById(R.id.dateFrom);
            viewHolder.dateTo = (TextView) convertView.findViewById(R.id.dateTo);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Flight flight = items.get(position);
        viewHolder.dateFrom.setText(resources.getString(R.string.dateFrom, flight.getDateFrom()));
        viewHolder.dateTo.setText(resources.getString(R.string.dateTo, flight.getDateTo()));
        return convertView;
    }

    private static class ViewHolder {
        private TextView dateFrom;
        private TextView dateTo;
    }

}