package sockettest;

import java.io.IOException;
import java.net.UnknownHostException;

public interface RecordHandler {
	public void handleRecord(Record record) throws UnknownHostException, IOException, Exception;
}