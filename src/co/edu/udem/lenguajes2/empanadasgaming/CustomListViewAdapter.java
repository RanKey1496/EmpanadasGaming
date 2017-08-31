package co.edu.udem.lenguajes2.empanadasgaming;

import java.util.List;

import co.edu.udem.lenguajes2.empanadasgaming.SummonerDataActivity.ListViewItem;
import android.app.Activity;  
import android.content.Context;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;
import android.widget.TextView;  
  
public class CustomListViewAdapter extends BaseAdapter
{  
	
	LayoutInflater inflater;
	List<ListViewItem> items;
	
    public CustomListViewAdapter(Activity context, List<ListViewItem> items) {  
        super();
		
        this.items = items;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override  
    public int getCount() {  
        // TODO Auto-generated method stub  
        return items.size();  
    }  
  
    @Override  
    public Object getItem(int position) {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
    @Override  
    public long getItemId(int position) {  
        // TODO Auto-generated method stub  
        return 0;  
    }
      
    @Override  
    public View getView(final int position, View convertView, ViewGroup parent) {  
    	ListViewItem item = items.get(position);
    	View vi = convertView;
        
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item, null);
        
        TextView txtTeamName = (TextView) vi.findViewById(R.id.txtTeamName);
        TextView txtTeamTag = (TextView) vi.findViewById(R.id.txtTeamTag);
        TextView txtTeamLastGameDate = (TextView) vi.findViewById(R.id.txtTeamLastGameDate);
        
        txtTeamName.setText(item.TeamName);
        txtTeamTag.setText(item.TeamTag);
        txtTeamLastGameDate.setText(item.TeamLastGameDate);
        
        return vi;  
    }
}