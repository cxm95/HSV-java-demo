package sockettest;

import java.io.IOException;
import java.net.UnknownHostException;

import dao.SearchDao;

public class SqlRecordHandler implements RecordHandler {

	@Override
	public void handleRecord(Record record) throws UnknownHostException,
			IOException, Exception {
		// TODO Auto-generated method stub
		util.DbUtil dbUtil = new util.DbUtil();
		if (SearchDao.Search(dbUtil.getCon(), record.getUrl(), "urlblack") == 1) {
			record.setResponse(0);
			System.out.println("urlblackget!");
		} else if (SearchDao.Search(dbUtil.getCon(), record.getUrl(),
				"urlwhite") == 1) {
			record.setResponse(1);
			System.out.println("urlwhiteget!");
		} else {
			record.setResponse(2);
		}
	}

}
