package test.java.ipv6;
import java.net.Inet6Address;


public class ipv6tool {
	// ipv6 address types:
    // Address type         Binary prefix        IPv6 notation   Section
    // ------------         -------------        -------------   -------
    // Unspecified          00...0  (128 bits)   ::/128          2.5.2
    // Loopback             00...1  (128 bits)   ::1/128         2.5.3
    // Multicast            11111111             FF00::/8        2.7
    // Link-Local unicast   1111111010           FE80::/10       2.5.6
    // Global Unicast       (everything else)
	
	// constants for the 5 different types
	public static final int UNSPEC_TYPE = 0;
	public static final int LOOPBACK_TYPE = 1;
	public static final int MULTICAST_TYPE = 2;
	public static final int LL_UNICAST_TYPE = 3;
	public static final int GL_UNICAST_TYPE = 4;
	
	static boolean isUnspecified(Inet6Address a) {
		byte []raw = a.getAddress();		// TODO: how to determine 0...0?
		return false;
	}
	static boolean isLoopback(Inet6Address a) {
		return a.isLoopbackAddress();
	}
	static boolean isMulticast(Inet6Address a) {
		return a.isMulticastAddress();
	}
	static boolean isLLunicast(Inet6Address a) {
		return a.isLinkLocalAddress();
		
	}
	static boolean isGLunicast(Inet6Address a) {
		if (!isUnspecified(a) && !isLoopback(a) && !isMulticast(a) && !isLLunicast(a))
			return true;
		else
			return false;
	}
	
	// can only be one of the 5 defined types at the same type.
	// don't distinguish sub-types here
	public static int getType(Inet6Address a) {
		if (isUnspecified(a))
			return UNSPEC_TYPE;
		else if (isLoopback(a))
			return LOOPBACK_TYPE;
		else if (isMulticast(a))
			return MULTICAST_TYPE;
		else if (isLLunicast(a))
			return LL_UNICAST_TYPE;
		else if (isGLunicast(a))
			return GL_UNICAST_TYPE;
		return -1;				// should not get here
	}
	
	// returns string that says what type it is
	public static String getTypeAsString(Inet6Address a) {
		// types must be in same order as the constants
		String []types = new String[] {"unspecified", "loopback", "multicast", "link-layer unicast", "global unicast"};
		int x = getType(a);
		return types[x];
	}

}

