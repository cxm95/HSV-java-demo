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

		Font ftt = new Font("黑体", Font.BOLD, 20);
		Font ft = new Font("SimSun", 10, 10);// 宋体

		LegendTitle legend = null;
		TextTitle txtTitle = null;
		PiePlot categoryplot = null;

		legend = chart.getLegend();
		txtTitle = chart.getTitle();
		categoryplot = (PiePlot) chart.getPlot();

		txtTitle.setFont(ftt); // 设置标题字体
		categoryplot.setLabelFont(ft);// 设置图片上固定指示文字字体
		legend.setItemFont(ft);// 设置图例字体

		ChartFrame chartFrame = new ChartFrame("网站流量统计图", chart);
		chartFrame.pack();
		chartFrame.setVisible(true);

	}
}
