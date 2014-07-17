package sockettest;

import java.sql.Date;

public class Record {
	private String record;//访问的主机
	private Date visitDate;
	private int response;//response有多种状态，1代表通过，2代表通过并下载全部，0代表不通过并下载关键字
	private String url;//为下载网页提供参数
	private int fliter;

	public int getFliter() {
		return fliter;
	}

	public void setFliter(int fliter) {
		this.fliter = fliter;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getResponse() {
		return response;
	}

	public void setResponse(int response) {
		this.response = response;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date date) {
		this.visitDate = date;
	}
}
