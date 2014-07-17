package sockettest;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Statistic implements RecordHandler {
	public static Set<String> set = new HashSet<String>();
	public static List<String> list = new ArrayList<String>();

	@Override
	public void handleRecord(Record record) throws UnknownHostException,
			IOException, Exception {
		// TODO Auto-generated method stub
		set.add(record.getRecord());
		list.add(record.getRecord());
	}

}
