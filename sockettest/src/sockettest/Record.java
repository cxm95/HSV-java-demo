package sockettest;

import java.sql.Date;

public class Record {
	private String record;//���ʵ�����
	private Date visitDate;
	private int response;//response�ж���״̬��1����ͨ����2����ͨ��������ȫ����0����ͨ�������عؼ���
	private String url;//Ϊ������ҳ�ṩ����
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
