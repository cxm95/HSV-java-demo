package sockettest;

public class ConsoleRecordHandler implements RecordHandler {
	@Override
	public void handleRecord(Record record) {
		System.out.println("@@@@@@@"+record.getRecord());
	}
}