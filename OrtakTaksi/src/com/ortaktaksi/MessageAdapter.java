package com.ortaktaksi;

import java.util.List;

import com.ortaktaksi.R.id;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageAdapter extends ArrayAdapter<MessaageClass> 
{

	public MessageAdapter(Context context, int resource, int textViewResourceId,
			List<MessaageClass> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final MessaageClass m = getItem(position);
		ViewHolder holder;
		if (convertView == null) 
		{
			convertView = View.inflate(getContext(), R.layout.message_list_item, null);
			holder = new ViewHolder();
			holder.textName=(TextView)convertView.findViewById(id.txtName);
			holder.textmessage=(TextView)convertView.findViewById(id.lbl1);
			convertView.setTag(holder);			
		}
		else
		{
			holder=(ViewHolder)convertView.getTag();
		}
			
			holder.textName.setText(m.getName());
			holder.textmessage.setText(m.getMessage());
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
		TextView   textName;
		TextView   textmessage;
		ImageView picture;
	}

}
