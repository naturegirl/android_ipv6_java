package test.java.ipv6;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class InterfaceDetail extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        Bundle extras = getIntent().getExtras();
        int ifNum = extras.getInt("interface num");
        
        Enumeration<NetworkInterface> nets;
		try {
			nets = NetworkInterface.getNetworkInterfaces();
			ArrayList<NetworkInterface> ifs = Collections.list(nets);
			displayInterfaceInformation(ifs.get(ifNum));
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
    	String ifname = netint.getDisplayName();
    	//if (ifname.equals("eth0")) {
    		String name = netint.getName();
    		byte[] mac = netint.getHardwareAddress();
    		int mtu = netint.getMTU();
    		setIFname(ifname);
    		Log.i("mee", "display name " + ifname);
    		Log.i("mee", "name " + name);
    		if (mac != null) {
    			Log.i("mee", "mac: " + getMACstring(mac));
    			setMac(getMACstring(mac));
    		}
    		else {
    			Log.i("mee", "mac: null");
    			setMac("null");
    		}
    		Log.i("mee", "maximum trans. unit: " + Integer.toString(mtu));
    		setMTU(Integer.toString(mtu));
    		Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
    		Inet6Address v6addr;
    		List<InterfaceAddress> ifAddr = netint.getInterfaceAddresses();
    		for (InetAddress inetAddress : Collections.list(inetAddresses)) {
    			if (inetAddress instanceof Inet6Address) {
    				v6addr = (Inet6Address)inetAddress;
    				Log.i("mee", "Type: " + ipv6tool.getTypeAsString(v6addr));
    				setType(ipv6tool.getTypeAsString(v6addr));
    				Log.i("mee", "Inet6Addr: " + v6addr.getHostAddress());
    				setIPaddr(v6addr.getHostAddress());
    			}
    			else {
    				Log.i("mee", "InetAddr: " + inetAddress.getHostAddress());
    				setIPaddr(inetAddress.getHostAddress());
    			}
    		}
    		for (InterfaceAddress ifA : ifAddr) {
    			Log.i("mee", "Interface Address: " + ifA.toString());
    		}
            Log.i("mee", "END");
    	//}
     }
    
    private String getMACstring(byte[] mac) {
    	StringBuffer sb = new StringBuffer();
    	for (int i = 0; i < mac.length; ++i) {
    		if (i != 0)
    			sb.append("-");
    		String s = Integer.toHexString(mac[i] & 0xFF);
    		sb.append(s.length()==1?0+s:s);
    	}
    	return sb.toString().toUpperCase();
    }
    
    // set interface name
    private void setIFname(String s) {
    	TextView tv = (TextView) findViewById(R.id.tv1);
    	tv.setText(s);
    }
    private void setMTU(String s) {
    	TextView tv = (TextView) findViewById(R.id.tv2);
    	tv.setText(s);
    }
    private void setMac(String s) {
    	TextView tv = (TextView) findViewById(R.id.tv3);
    	tv.setText(s);
    }
    private int IPaddr_count = 0;	// count how often setIPaddr() has been called. Can't have static local variables=.=
    private void setIPaddr(String s) {
    	TextView tv;
    	if (IPaddr_count == 0)
    		tv = (TextView) findViewById(R.id.tv4);
    	else if (IPaddr_count == 1)
    		tv = (TextView) findViewById(R.id.tv6);
		else
			try {
				throw new Exception("blabla");
			} catch (Exception e) {	e.printStackTrace();
				return;
			}
    	tv.setText(s);
    	IPaddr_count++;
    }
    private void setType(String s) {
    	TextView tv = (TextView) findViewById(R.id.tv5);
    	tv.setText(s);
    }
    
}