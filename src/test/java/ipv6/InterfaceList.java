package test.java.ipv6;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InterfaceList extends ListActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  // retrieve network interface names
	  Enumeration<NetworkInterface> nets;
	  try {
		  nets = NetworkInterface.getNetworkInterfaces();
	  } catch (SocketException e) {e.printStackTrace();return;}
	  
	  ArrayList<NetworkInterface> ifs = Collections.list(nets);
	  String []Interfaces = new String[ifs.size()];
	  for (int i = 0; i < ifs.size(); ++i) {
		  Interfaces[i] = ifs.get(i).getName();
	  }
		  
	  // sets them in listview
	  setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, Interfaces));
	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	      // When clicked, show a toast with the TextView text
	      Intent intent = new Intent(InterfaceList.this, InterfaceDetail.class);
	      intent.putExtra("interface num", position);
	      startActivity(intent);
	    }
	  });
	}
}
