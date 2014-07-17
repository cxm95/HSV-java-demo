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
	private JLabel black = new JLabel("������:");
	private JLabel white = new JLabel("������:");
	private JButton skin = new JButton("");
	private JButton fliter = new JButton("");

	util.DbUtil dbUtil = new util.DbUtil();
	SearchDao userDao = new SearchDao();

	public JFrameGame() {
		String s = "logo";
		ImageIcon icon = new ImageIcon("graphics/icons/logo_����.png");
		this.setIconImage(icon.getImage());
		GameConfig cfg = ConfigFactory.gerGameConfig();
		// ���ñ���
		this.setTitle("URL����ϵͳ���--13����Ӳ��԰�");
		// ���ùر�
		this.setDefaultCloseOperation(JFrameGame.EXIT_ON_CLOSE);
		// ���ô�С
		this.setSize(800, 532);
		// ������ı��С
		this.setResizable(false);
		// ���þ������� �Ȼ����ʾ������
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int x = (screen.width - this.getWidth()) / 2;
		int y = (screen.height - this.getHeight()) / 2;
		this.setLocation(x, y);
		// ����Ĭ��Panel
		this.setContentPane(new JPanelGame());
		// JPanel panel =new JPanel();
		Container c = getContentPane();
		c.setLayout(null);

		List<LayerConfig> layerCfg = cfg.getLayersConfig();
		System.out.println(layerCfg.get(3));
		// ���ÿ�ʼ��ťλ��
		begin.setBounds(new Rectangle(330, 120, 140, 155));
		// ���ÿ�ʼ��ťͼ��
		begin.setIcon(new ImageIcon("graphics/icons/logo2_����.png"));
		// ����͸����ť
		begin.setContentAreaFilled(false);
		begin.setBorder(null);
		// ���ú������ı������λ��
		blacklog.setBounds(new Rectangle(80, 370, 350, 20));
		// ���ú���������Ϊ͸��
		blacklog.setOpaque(false);
		// ���ú�����������͸��
		blacklog.setBorder(null);
		blacklog.setToolTipText("������");

		// ���ú����������ֵ�λ��.
		black.setBounds(new Rectangle(30, 370, 50, 20));
		// ���ð����������ֵ�λ��
		white.setBounds(new Rectangle(30, 420, 50, 20));
		// ���ú�������ť
		blackconf.setBounds(new Rectangle(440, 360, 30, 30));
		blackconf.setIcon(new ImageIcon("graphics/icons/add_����.png"));
		blackconf.setPressedIcon(new ImageIcon("graphics/icons/add.png"));
		// blackconf.setBorder(null);
		blackcancel.setBounds(new Rectangle(480, 360, 30, 30));
		blackcancel.setIcon(new ImageIcon("graphics/icons/minus_����.png"));
		blackcancel.setPressedIcon(new ImageIcon("graphics/icons/minus.png"));
		blacksearch.setBounds(new Rectangle(520, 360, 30, 30));
		blacksearch.setIcon(new ImageIcon("graphics/icons/lookfor_����.png"));
		blacksearch.setPressedIcon(new ImageIcon("graphics/icons/lookfor.png"));

		// �O�ø��°��o
		update.setBounds(new Rectangle(580, 380, 40, 40));
		// ���ø��°�ťͼ��
		update.setIcon(new ImageIcon("graphics/icons/refresh.png"));
		// �O��tip��
		tip.setBounds(new Rectangle(630, 380, 40, 40));
		// ����tip��ͼ��
		tip.setIcon(new ImageIcon("graphics/icons/tip.png"));
		// ����skinλ��
		skin.setBounds(new Rectangle(680, 380, 40, 40));
		// ����skinͼ��
		skin.setIcon(new ImageIcon("graphics/icons/skinchange.png"));
		// ����fliterλ��
		fliter.setBounds(new Rectangle(730, 380, 40, 40));
		// ����fliterͼ��
		fliter.setIcon(new ImageIcon("graphics/icons/fliter.png"));

		// ���ð������ı������λ��
		whitelog.setBounds(new Rectangle(80, 420, 350, 20));
		// ���ð���������Ϊ͸��
		whitelog.setOpaque(false);
		// ���ð�����������͸��
		whitelog.setBorder(null);
		whitelog.setToolTipText("������");
		// ���ð�������ť
		whiteconf.setBounds(new Rectangle(440, 410, 30, 30));
		whiteconf.setIcon(new ImageIcon("graphics/icons/add_����.png"));
		whiteconf.setPressedIcon(new ImageIcon("graphics/icons/add.png"));
		whitecancel.setBounds(new Rectangle(480, 410, 30, 30));
		whitecancel.setIcon(new ImageIcon("graphics/icons/minus_����.png"));
		whitecancel.setPressedIcon(new ImageIcon("graphics/icons/minus.png"));
		whitesearch.setBounds(new Rectangle(520, 410, 30, 30));
		whitesearch.setIcon(new ImageIcon("graphics/icons/lookfor_����.png"));
		whitesearch.setPressedIcon(new ImageIcon("graphics/icons/lookfor.png"));

		// ����LOG����
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
					JOptionPane.showMessageDialog(null, "�Ƿ�URL");
					return;
				}
				String log = "urlblack";
				if (StringUtil.isEmpty(url)) {
					JOptionPane.showMessageDialog(null, "url����Ϊ��");
					return;
				}
				try {
					int i = SearchDao.Search(dbUtil.getCon(), url, log);
					if (i == 0)
						AddDao.Add(dbUtil.getCon(), url, log);
					else
						JOptionPane.showMessageDialog(null, "�����Ѿ�����");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "���ݿ����ʧ��");
				}
			}
		});

		blackcancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String url = blacklog.getText();
				if (!UrlFliter.urlcheck(url)) {
					JOptionPane.showMessageDialog(null, "�Ƿ�URL");
					return;
				}
				String log = "urlblack";
				if (StringUtil.isEmpty(url)) {
					JOptionPane.showMessageDialog(null, "url����Ϊ��");
					return;
				}
				try {
					int i = SearchDao.Search(dbUtil.getCon(), url, log);
					if (i == 1)
						DeleteDao.Delete(dbUtil.getCon(), url, log);
					else
						JOptionPane.showMessageDialog(null, "���в�����");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "���ݿ����ʧ��");
				}
			}
		});
		blacksearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String url = blacklog.getText();
				if (!UrlFliter.urlcheck(url)) {
					JOptionPane.showMessageDialog(null, "�Ƿ�URL");
					return;
				}
				String log = "urlblack";
				if (StringUtil.isEmpty(url)) {
					JOptionPane.showMessageDialog(null, "url����Ϊ��");
					return;
				}
				try {
					int i = SearchDao.Search(dbUtil.getCon(), url, log);
					if (i == 0)
						JOptionPane.showMessageDialog(null, "���������ݿ���");
					else
						JOptionPane.showMessageDialog(null, "�ɹ��ҵ�");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "��ѯʧ��");
				}
			}
		});
		whiteconf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String url = whitelog.getText();
				if (!UrlFliter.urlcheck(url)) {
					JOptionPane.showMessageDialog(null, "�Ƿ�URL");
					return;
				}
				String log = "urlwhite";
				if (StringUtil.isEmpty(url)) {
					JOptionPane.showMessageDialog(null, "url����Ϊ��");
					return;
				}
				try {
					int i = SearchDao.Search(dbUtil.getCon(), url, log);
					if (i == 0)
						AddDao.Add(dbUtil.getCon(), url, log);
					else
						JOptionPane.showMessageDialog(null, "�����Ѿ�����");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "���ݿ����ʧ��");
				}

			}
		});
		whitecancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String url = whitelog.getText();
				if (!UrlFliter.urlcheck(url)) {
					JOptionPane.showMessageDialog(null, "�Ƿ�URL");
					return;
				}
				String log = "urlwhite";
				if (StringUtil.isEmpty(url)) {
					JOptionPane.showMessageDialog(null, "url����Ϊ��");
					return;
				}
				try {
					int i = SearchDao.Search(dbUtil.getCon(), url, log);
					if (i == 1)
						DeleteDao.Delete(dbUtil.getCon(), url, log);
					else
						JOptionPane.showMessageDialog(null, "���в�����");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "���ݿ����ʧ��");
				}

			}
		});
		whitesearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String url = whitelog.getText();
				if (!UrlFliter.urlcheck(url)) {
					JOptionPane.showMessageDialog(null, "�Ƿ�URL");
					return;
				}
				String log = "urlwhite";
				if (StringUtil.isEmpty(url)) {
					JOptionPane.showMessageDialog(null, "url����Ϊ��");
					return;
				}
				try {
					int i = SearchDao.Search(dbUtil.getCon(), url, log);
					if (i == 0)
						JOptionPane.showMessageDialog(null, "���������ݿ���");
					else
						JOptionPane.showMessageDialog(null, "�ɹ��ҵ�");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "��ѯʧ��");
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
					begin.setIcon(new ImageIcon("graphics/icons/logo_����.png"));
				else
					begin.setIcon(new ImageIcon("graphics/icons/logo2_����.png"));
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
