package ui;

import java.awt.Font;
import java.util.Collections;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

import sockettest.Statistic;

public class JFreeChartTip {

	public void showchart() {

		DefaultPieDataset dataSet = new DefaultPieDataset();
		Statistic stat = new Statistic();
		int f;
		for (String temp : stat.set) {
			f = Collections.frequency(stat.list, temp);
			dataSet.setValue(temp, f);
		}
		JFreeChart chart = ChartFactory.createPieChart("YaoChenghao's Tip",
				dataSet, true, true, true);

		Font ftt = new Font("����", Font.BOLD, 20);
		Font ft = new Font("SimSun", 10, 10);// ����

		LegendTitle legend = null;
		TextTitle txtTitle = null;
		PiePlot categoryplot = null;

		legend = chart.getLegend();
		txtTitle = chart.getTitle();
		categoryplot = (PiePlot) chart.getPlot();

		txtTitle.setFont(ftt); // ���ñ�������
		categoryplot.setLabelFont(ft);// ����ͼƬ�Ϲ̶�ָʾ��������
		legend.setItemFont(ft);// ����ͼ������

		ChartFrame chartFrame = new ChartFrame("��վ����ͳ��ͼ", chart);
		chartFrame.pack();
		chartFrame.setVisible(true);

	}
}
