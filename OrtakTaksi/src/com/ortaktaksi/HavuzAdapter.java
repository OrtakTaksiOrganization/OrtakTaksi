package com.ortaktaksi;

import java.util.List;

import com.ortaktaksi.R.id;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HavuzAdapter extends ArrayAdapter<HavuzClass> 
{

	public HavuzAdapter(Context context, int resource, int textViewResourceId,
			List<HavuzClass> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final HavuzClass h = getItem(position);
		ViewHolder holder;
		if (convertView == null) 
		{
			convertView = View.inflate(getContext(), R.layout.havuz_list_item, null);
			holder = new ViewHolder();
			holder.textStartPoint=(TextView)convertView.findViewById(id.txStartPoint);
			holder.textDestinationPoint=(TextView)convertView.findViewById(id.txDestinationPoint);
			holder.textNameSurname=(TextView)convertView.findViewById(id.txName);
			holder.textMeetingPoint=(TextView)convertView.findViewById(id.txMeetingPoint);
			convertView.setTag(holder);			
		}
		else
		{
			holder=(ViewHolder)convertView.getTag();
		}
			holder.textStartPoint.setText(h.getStartPoint());
			holder.textDestinationPoint.setText(h.getDestinationPoint());
			holder.textNameSurname.setText(h.getNameSurname());
			holder.textMeetingPoint.setText(h.getMeetingPoint());
//		holder.picture.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				Toast.makeText(getContext(), "Clicked on " + 
//			h.getUserName() + "'s picture",	Toast.LENGTH_SHORT).show();				
//			}
//		});
		return convertView;				
	}
	
	static class ViewHolder {
		TextView textStartPoint, textDestinationPoint, textNameSurname,textMeetingPoint;
		ImageView picture;
	}

}
