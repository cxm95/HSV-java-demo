package ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.html.ImageView;

import config.ConfigFactory;
import config.GameConfig;
import config.LayerConfig;
import sockettest.AppLauncher;
import sockettest.ResourceURLServer;
import util.StringUtil;
import dao.AddDao;
import dao.DeleteDao;
import dao.SearchDao;
import dao.UrlFliter;

public class JFrameGame extends JFrame {
	private JTextField blacklog = new JTextField("");
	private JTextField whitelog = new JTextField("");
	private JButton blackconf = new JButton("");
	private JButton blackcancel = new JButton("");
	private JButton blacksearch = new JButton("");
	private JButton whiteconf = new JButton("");
	private JButton whitecancel = new JButton("");
	private JButton whitesearch = new JButton("");
	static JButton begin = new JButton();
	public static JTextArea Log = new JTextArea();
	private JButton update = new JButton("");
	private JButton tip = new JButton("");
	private JLabel black = new JLabel("黑名单:");
	private JLabel white = new JLabel("白名单:");
	private JButton skin = new JButton("");
	private JButton fliter = new JButton("");

	util.DbUtil dbUtil = new util.DbUtil();
	SearchDao userDao = new SearchDao();

	public JFrameGame() {
		String s = "logo";
		ImageIcon icon = new ImageIcon("graphics/icons/logo_副本.png");
		this.setIconImage(icon.getImage());
		GameConfig cfg = ConfigFactory.gerGameConfig();
		// 设置标题
		this.setTitle("URL钓鱼系统检测--13°盒子测试版");
		// 设置关闭
		this.setDefaultCloseOperation(JFrameGame.EXIT_ON_CLOSE);
		// 设置大小
		this.setSize(800, 532);
		// 不允许改变大小
		this.setResizable(false);
		// 设置居中属性 先获得显示器参数
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int x = (screen.width - this.getWidth()) / 2;
		int y = (screen.height - this.getHeight()) / 2;
		this.setLocation(x, y);
		// 设置默认Panel
		this.setContentPane(new JPanelGame());
		// JPanel panel =new JPanel();
		Container c = getContentPane();
		c.setLayout(null);

		List<LayerConfig> layerCfg = cfg.getLayersConfig();
		System.out.println(layerCfg.get(3));
		// 设置开始按钮位置
		begin.setBounds(new Rectangle(330, 120, 140, 155));
		// 设置开始按钮图标
		begin.setIcon(new ImageIcon("graphics/icons/logo2_副本.png"));
		// 设置透明按钮
		begin.setContentAreaFilled(false);
		begin.setBorder(null);
		// 设置黑名单文本输入框位置
		blacklog.setBounds(new Rectangle(80, 370, 350, 20));
		// 设置黑名单窗体为透明
		blacklog.setOpaque(false);
		// 设置黑名单输入框边透明
		blacklog.setBorder(null);
		blacklog.setToolTipText("黑名单");

		// 设置黑名单三个字的位置.
		black.setBounds(new Rectangle(30, 370, 50, 20));
		// 设置白名单三个字的位置
		white.setBounds(new Rectangle(30, 420, 50, 20));
		// 设置黑名单按钮
		blackconf.setBounds(new Rectangle(440, 360, 30, 30));
		blackconf.setIcon(new ImageIcon("graphics/icons/add_副本.png"));
		blackconf.setPressedIcon(new ImageIcon("graphics/icons/add.png"));
		// blackconf.setBorder(null);
		blackcancel.setBounds(new Rectangle(480, 360, 30, 30));
		blackcancel.setIcon(new ImageIcon("graphics/icons/minus_副本.png"));
		blackcancel.setPressedIcon(new ImageIcon("graphics/icons/minus.png"));
		blacksearch.setBounds(new Rectangle(520, 360, 30, 30));
		blacksearch.setIcon(new ImageIcon("graphics/icons/lookfor_副本.png"));
		blacksearch.setPressedIcon(new ImageIcon("graphics/icons/lookfor.png"));

		// 設置更新按鈕
		update.setBounds(new Rectangle(580, 380, 40, 40));
		// 设置更新按钮图标
		update.setIcon(new ImageIcon("graphics/icons/refresh.png"));
		// 設置tip框
		tip.setBounds(new Rectangle(630, 380, 40, 40));
		// 设置tip框图标
		tip.setIcon(new ImageIcon("graphics/icons/tip.png"));
		// 设置skin位置
		skin.setBounds(new Rectangle(680, 380, 40, 40));
		// 设置skin图标
		skin.setIcon(new ImageIcon("graphics/icons/skinchange.png"));
		// 设置fliter位置
		fliter.setBounds(new Rectangle(730, 380, 40, 40));
		// 设置fliter图标
		fliter.setIcon(new ImageIcon("graphics/icons/fliter.png"));

		// 设置白名单文本输入框位置
		whitelog.setBounds(new Rectangle(80, 420, 350, 20));
		// 设置白名单窗体为透明
		whitelog.setOpaque(false);
		// 设置白名单输入框边透明
		whitelog.setBorder(null);
		whitelog.setToolTipText("白名单");
		// 设置白名单按钮
		whiteconf.setBounds(new Rectangle(440, 410, 30, 30));
		whiteconf.setIcon(new ImageIcon("graphics/icons/add_副本.png"));
		whiteconf.setPressedIcon(new ImageIcon("graphics/icons/add.png"));
		whitecancel.setBounds(new Rectangle(480, 410, 30, 30));
		whitecancel.setIcon(new ImageIcon("graphics/icons/minus_副本.png"));
		whitecancel.setPressedIcon(new ImageIcon("graphics/icons/minus.png"));
		whitesearch.setBounds(new Rectangle(520, 410, 30, 30));
		whitesearch.setIcon(new ImageIcon("graphics/icons/lookfor_副本.png"));
		whitesearch.setPressedIcon(new ImageIcon("graphics/icons/lookfor.png"));

		// 设置LOG窗体
		Log.setBounds(new Rectangle(40, 470, 720, 20));
		Log.setOpaque(false);

		c.add(blacklog);
		c.add(blackconf);
		c.add(blackcancel);
		c.add(blacksearch);
		c.add(whitelog);
		c.add(whiteconf);
		c.add(whitecancel);
		c.add(whitesearch);
		c.add(Log);
		c.add(begin);
		c.add(update);
		c.add(tip);
		c.add(black);
		c.add(white);
		c.add(skin);
		c.add(fliter);

		blackconf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String url = blacklog.getText();
				if (!UrlFliter.urlcheck(url)) {
					JOptionPane.showMessageDialog(null, "非法URL");
					return;
				}
				String log = "urlblack";
				if (StringUtil.isEmpty(url)) {
					JOptionPane.showMessageDialog(null, "url不能为空");
					return;
				}
				try {
					int i = SearchDao.Search(dbUtil.getCon(), url, log);
					if (i == 0)
						AddDao.Add(dbUtil.getCon(), url, log);
					else
						JOptionPane.showMessageDialog(null, "表中已经存在");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "数据库访问失败");
				}
			}
		});

		blackcancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String url = blacklog.getText();
				if (!UrlFliter.urlcheck(url)) {
					JOptionPane.showMessageDialog(null, "非法URL");
					return;
				}
				String log = "urlblack";
				if (StringUtil.isEmpty(url)) {
					JOptionPane.showMessageDialog(null, "url不能为空");
					return;
				}
				try {
					int i = SearchDao.Search(dbUtil.getCon(), url, log);
					if (i == 1)
						DeleteDao.Delete(dbUtil.getCon(), url, log);
					else
						JOptionPane.showMessageDialog(null, "表中不存在");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "数据库访问失败");
				}
			}
		});
		blacksearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String url = blacklog.getText();
				if (!UrlFliter.urlcheck(url)) {
					JOptionPane.showMessageDialog(null, "非法URL");
					return;
				}
				String log = "urlblack";
				if (StringUtil.isEmpty(url)) {
					JOptionPane.showMessageDialog(null, "url不能为空");
					return;
				}
				try {
					int i = SearchDao.Search(dbUtil.getCon(), url, log);
					if (i == 0)
						JOptionPane.showMessageDialog(null, "不存在数据库中");
					else
						JOptionPane.showMessageDialog(null, "成功找到");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "查询失败");
				}
			}
		});
		whiteconf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String url = whitelog.getText();
				if (!UrlFliter.urlcheck(url)) {
					JOptionPane.showMessageDialog(null, "非法URL");
					return;
				}
				String log = "urlwhite";
				if (StringUtil.isEmpty(url)) {
					JOptionPane.showMessageDialog(null, "url不能为空");
					return;
				}
				try {
					int i = SearchDao.Search(dbUtil.getCon(), url, log);
					if (i == 0)
						AddDao.Add(dbUtil.getCon(), url, log);
					else
						JOptionPane.showMessageDialog(null, "表中已经存在");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "数据库访问失败");
				}

			}
		});
		whitecancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String url = whitelog.getText();
				if (!UrlFliter.urlcheck(url)) {
					JOptionPane.showMessageDialog(null, "非法URL");
					return;
				}
				String log = "urlwhite";
				if (StringUtil.isEmpty(url)) {
					JOptionPane.showMessageDialog(null, "url不能为空");
					return;
				}
				try {
					int i = SearchDao.Search(dbUtil.getCon(), url, log);
					if (i == 1)
						DeleteDao.Delete(dbUtil.getCon(), url, log);
					else
						JOptionPane.showMessageDialog(null, "表中不存在");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "数据库访问失败");
				}

			}
		});
		whitesearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String url = whitelog.getText();
				if (!UrlFliter.urlcheck(url)) {
					JOptionPane.showMessageDialog(null, "非法URL");
					return;
				}
				String log = "urlwhite";
				if (StringUtil.isEmpty(url)) {
					JOptionPane.showMessageDialog(null, "url不能为空");
					return;
				}
				try {
					int i = SearchDao.Search(dbUtil.getCon(), url, log);
					if (i == 0)
						JOptionPane.showMessageDialog(null, "不存在数据库中");
					else
						JOptionPane.showMessageDialog(null, "成功找到");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "查询失败");
				}
			}
		});

		begin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JPanelGame back = new JPanelGame();
				back.back++;
				if (back.back % 2 == 0)
					begin.setIcon(new ImageIcon("graphics/icons/logo_副本.png"));
				else
					begin.setIcon(new ImageIcon("graphics/icons/logo2_副本.png"));
				if (back.back % 2 == 0)
					AppLauncher.setIsrunning(true);
				else
					AppLauncher.setIsrunning(false);
				repaint();
			}
		});

		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ProgressBar app = new ProgressBar();
			}
		});

		tip.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFreeChartTip chartTip = new JFreeChartTip();
				chartTip.showchart();
			}
		});
		skin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanelGame back = new JPanelGame();
				back.skin++;
				repaint();
				// TODO Auto-generated method stub

			}
		});
		fliter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JPanelGame back = new JPanelGame();
				back.fliter++;
				if (back.fliter % 2 == 0)
					AppLauncher.setKeywordfilter(true);
				else
					AppLauncher.setKeywordfilter(false);
			}
		});
	}

	private JButton createImageButton(String text, String image) {
		ImageIcon icon = new ImageIcon(getClass().getResource(image));
		JButton btn = new JButton(text, icon);
		btn.setHorizontalTextPosition(JButton.CENTER);
		btn.setVerticalTextPosition(JButton.BOTTOM);
		return btn;
	}

}
