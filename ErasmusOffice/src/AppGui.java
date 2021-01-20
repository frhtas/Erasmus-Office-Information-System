import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

// GUI PROGRAM
public class AppGui {

	private JFrame window_ErasmusOffice;
	private JTable table_agreements;
	private JTable table_students;
	private DatabaseHelper db = new DatabaseHelper(); // Database object for using SQL functions
	private JTextField textField_uniName;
	private JTextField textField_uniCountry;
	private JTextField textField_uniGrant;
	
	JComboBox<Object> comboBox_deptAgr;
	JComboBox<Object> comboBox_uniAgr;
	JComboBox<Object> comboBox_dept;
	JComboBox<Object> comboBox_deptStd;
	private JTextField textField_stdno;
	private JTextField textField_examscore;
	private JTextField textField_chosenTerm;
	private JTextField textField_chosenterm;
	private JTextField textField_nmbOfStd;
	private JTextField textField_country;
	private JTextField textField_grant;
	private JTextField textField_examavg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try { 
			LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
		    for (LookAndFeelInfo info : plafs) {
		        if (info.getName().contains("Nimbus")) {
		        	UIManager.setLookAndFeel(info.getClassName()); 
		        }
		    }
	    } catch(Exception ignored){}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppGui window = new AppGui();
					window.window_ErasmusOffice.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		window_ErasmusOffice = new JFrame();
		window_ErasmusOffice.setBounds(100, 100, 900, 650);
		window_ErasmusOffice.setUndecorated(true);
		window_ErasmusOffice.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		window_ErasmusOffice.setResizable(false);
		window_ErasmusOffice.setTitle("Erasmus Ofisi Bilgi Sistemi");
		window_ErasmusOffice.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window_ErasmusOffice.getContentPane().setLayout(null);
		
		JPanel panel_upper = new MotionPanel(window_ErasmusOffice);
		panel_upper.setBounds(0, 0, 815, 31);
		panel_upper.setBackground(new Color(0x333333));
		window_ErasmusOffice.getContentPane().add(panel_upper);
		JPanel panel_close = new JPanel();
		panel_close.setBounds(812, 0, 88, 31);
		window_ErasmusOffice.getContentPane().add(panel_close);
		panel_close.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblMinimizeButton = new JLabel("-");
		lblMinimizeButton.setOpaque(true);
		lblMinimizeButton.setBackground(new Color(0x333333));
		lblMinimizeButton.setForeground(new Color(0xEEEEEE));
		lblMinimizeButton.setHorizontalAlignment(SwingConstants.CENTER);
		lblMinimizeButton.setSize(new Dimension(50, 50));
		lblMinimizeButton.setFont(new Font("Tahoma", Font.BOLD, 30));
		
		
		lblMinimizeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	lblMinimizeButton.setBackground(new Color(0x555555));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	lblMinimizeButton.setBackground(new Color(0x333333));
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
            	window_ErasmusOffice.setState(JFrame.ICONIFIED);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
            	lblMinimizeButton.setBackground(new Color(0x666666));
            }  
        });
		panel_close.add(lblMinimizeButton);
		
		JLabel lblCloseButton = new JLabel("X");
		lblCloseButton.setOpaque(true);
		lblCloseButton.setBackground(new Color(0x333333));
		lblCloseButton.setForeground(new Color(0xEEEEEE));
		lblCloseButton.setHorizontalAlignment(SwingConstants.CENTER);
		lblCloseButton.setSize(new Dimension(50, 50));
		lblCloseButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		lblCloseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	lblCloseButton.setBackground(Color.RED);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	lblCloseButton.setBackground(new Color(0x333333));
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
            	System.exit(JFrame.EXIT_ON_CLOSE);
            }  
            
            @Override
            public void mousePressed(MouseEvent e) {
            	lblCloseButton.setBackground(new Color(0xA61000));
            }  
        });
		panel_close.add(lblCloseButton);
		
		JPanel panel_title = new JPanel();
		panel_title.setBounds(0, 31, 900, 61);
		window_ErasmusOffice.getContentPane().add(panel_title);
		panel_title.setLayout(null);
		panel_title.setForeground(Color.BLACK);
		panel_title.setFocusable(false);
		panel_title.setBackground(SystemColor.textHighlight);
		
		ImageIcon imageIcon = new ImageIcon("images/yildiz_logo.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);
		
		JLabel logo = new JLabel("");
		logo.setBounds(235, 0, 60, 60);
		logo.setIcon(imageIcon);
		panel_title.add(logo);
		
		JLabel lbl_logo = new JLabel("YT\u00DC Erasmus Ofisi Bilgi Sistemi");
		lbl_logo.setForeground(Color.WHITE);
		lbl_logo.setFont(new Font("Tahoma", Font.BOLD, 24));
		lbl_logo.setBounds(307, 0, 521, 60);
		panel_title.add(lbl_logo);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setOpaque(true);
		tabbedPane.setBackground(SystemColor.textHighlight);
		tabbedPane.setBounds(0, 91, 900, 559);
		window_ErasmusOffice.getContentPane().add(tabbedPane);
		
		JPanel panel_homepage = new JPanel();
		panel_homepage.setFocusable(false);
		
		// Icons made by <a href="https://www.flaticon.com/authors/pixel-perfect" title="Pixel perfect">Pixel perfect</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>
		tabbedPane.addTab("Ana Sayfa ", new ImageIcon("images/home.png"), panel_homepage, null);
		panel_homepage.setLayout(new GridLayout(2, 2, 0, 0));
		
		// Üniversite Ekle
		JPanel panel_addUniversity = new JPanel();
		panel_addUniversity.setBorder(new LineBorder(SystemColor.inactiveCaption, 2));
		panel_homepage.add(panel_addUniversity);
		panel_addUniversity.setLayout(null);
		
		JLabel lbl_addUniversity = new JLabel("Üniversite Ekleme");
		lbl_addUniversity.setBackground(new Color(47, 79, 79));
		lbl_addUniversity.setOpaque(true);
		lbl_addUniversity.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_addUniversity.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_addUniversity.setForeground(SystemColor.control);
		lbl_addUniversity.setBounds(2, 2, 446, 24);
		panel_addUniversity.add(lbl_addUniversity);
		
		JButton btn_addUniversity = new JButton("Üniversite Ekle");
		btn_addUniversity.setFont(new Font("SansSerif", Font.BOLD, 12));
		btn_addUniversity.setForeground(SystemColor.control);
		btn_addUniversity.setBackground(new Color(0, 128, 128));
		btn_addUniversity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textField_uniName.getText().trim().length() != 0 && textField_uniCountry.getText().trim().length() != 0 
						&& textField_uniGrant.getText().trim().length() != 0) {
					try {
						String uname = textField_uniName.getText().trim();
						String country = textField_uniCountry.getText().trim();
						int ugrant = Integer.valueOf(textField_uniGrant.getText().trim());
						
						Object[] newUni = {uname, country, ugrant};
						db.insertUniversity(newUni);
						
						JOptionPane.showMessageDialog(null, "Üniversite baþarýyla eklendi!", "Baþarýlý", 1);
						comboBox_deptAgr.setModel(new DefaultComboBoxModel<>(db.getDepartmentNames()));
						comboBox_uniAgr.setModel(new DefaultComboBoxModel<>(db.getUniversityNames()));
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Hata", 0);
						db.rollBack();
					} 
				}
				else {
					JOptionPane.showMessageDialog(null, "Lütfen bütün alanlarý doldurun!", "Uyarý", 2);
				}			
			}
		});
		btn_addUniversity.setFocusable(false);
		btn_addUniversity.setBounds(129, 194, 300, 30);
		panel_addUniversity.add(btn_addUniversity);
		
		textField_uniCountry = new JTextField();
		textField_uniCountry.setBounds(129, 103, 300, 28);
		panel_addUniversity.add(textField_uniCountry);
		textField_uniCountry.setHorizontalAlignment(SwingConstants.CENTER);
		textField_uniCountry.setColumns(10);
		
		textField_uniGrant = new JTextField();
		textField_uniGrant.setBounds(129, 145, 300, 28);
		panel_addUniversity.add(textField_uniGrant);
		textField_uniGrant.setHorizontalAlignment(SwingConstants.CENTER);
		textField_uniGrant.setColumns(10);
		
		textField_uniName = new JTextField();
		textField_uniName.setBounds(129, 63, 300, 28);
		panel_addUniversity.add(textField_uniName);
		textField_uniName.setToolTipText("");
		textField_uniName.setHorizontalAlignment(SwingConstants.CENTER);
		textField_uniName.setColumns(10);
		
		JLabel lbl_uniName = new JLabel("Üniversite Adý:");
		lbl_uniName.setBounds(18, 69, 99, 16);
		panel_addUniversity.add(lbl_uniName);
		lbl_uniName.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_uniName.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lbl_uniCountry = new JLabel("Ülke:");
		lbl_uniCountry.setBounds(18, 109, 99, 16);
		panel_addUniversity.add(lbl_uniCountry);
		lbl_uniCountry.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_uniCountry.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lbl_uniGrant = new JLabel("Hibe Miktarý:");
		lbl_uniGrant.setBounds(18, 151, 99, 16);
		panel_addUniversity.add(lbl_uniGrant);
		lbl_uniGrant.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_uniGrant.setHorizontalAlignment(SwingConstants.RIGHT);

		JPanel panel_addAgreement = new JPanel();
		panel_addAgreement.setBorder(new LineBorder(SystemColor.inactiveCaption, 2));
		panel_homepage.add(panel_addAgreement);
		panel_addAgreement.setLayout(null);
		
		JLabel lbl_addAgreement = new JLabel("Anlaþma Ekleme ve Silme");
		lbl_addAgreement.setBackground(new Color(47, 79, 79));
		lbl_addAgreement.setOpaque(true);
		lbl_addAgreement.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_addAgreement.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_addAgreement.setForeground(SystemColor.control);
		lbl_addAgreement.setBounds(2, 2, 446, 24);
		panel_addAgreement.add(lbl_addAgreement);
		
		String[] dept_names_agr = db.getDepartmentNames();
		String[] uni_names_agr = db.getUniversityNames();
		
		JButton btn_addAgreement = new JButton("Anlaþma Ekle");
		btn_addAgreement.setForeground(SystemColor.control);
		btn_addAgreement.setBackground(new Color(0, 128, 128));
		btn_addAgreement.setFont(new Font("SansSerif", Font.BOLD, 12));
		btn_addAgreement.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String selectedDept = comboBox_deptAgr.getSelectedItem().toString();
				String selectedUni = comboBox_uniAgr.getSelectedItem().toString();				
				try {
					db.insertAgreement(selectedDept, selectedUni);			
					JOptionPane.showMessageDialog(null, "Anlaþma baþarýyla eklendi!", "Baþarýlý", 1);
					comboBox_dept.setModel(new DefaultComboBoxModel<>(db.getDepartmentNames()));
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Hata", 0);
					db.rollBack();
				} 
			}
		});
		btn_addAgreement.setFocusable(false);
		btn_addAgreement.setBounds(130, 194, 130, 30);
		panel_addAgreement.add(btn_addAgreement);
		
		JButton btn_deleteAgreement = new JButton("Anlaþma Sil");
		btn_deleteAgreement.setForeground(SystemColor.menu);
		btn_deleteAgreement.setFont(new Font("SansSerif", Font.BOLD, 12));
		btn_deleteAgreement.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String selectedDept = comboBox_deptAgr.getSelectedItem().toString();
				String selectedUni = comboBox_uniAgr.getSelectedItem().toString();				
				try {
					int isExist = db.deleteAgreement(selectedDept, selectedUni);
					if (isExist == 1)
						JOptionPane.showMessageDialog(null, "Anlaþma baþarýyla silindi!", "Baþarýlý", 1);
					else
						JOptionPane.showMessageDialog(null, "Böyle bir anlaþma bulunmamaktadýr!", "Uyarý", 2);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Hata", 0);
					db.rollBack();
				} 
			}
		});
		btn_deleteAgreement.setFocusable(false);
		btn_deleteAgreement.setBackground(new Color(178, 34, 34));
		btn_deleteAgreement.setBounds(300, 194, 130, 30);
		panel_addAgreement.add(btn_deleteAgreement);
		
		JLabel lbl_deptAgr = new JLabel("Bölüm:");
		lbl_deptAgr.setBounds(2, 85, 100, 16);
		panel_addAgreement.add(lbl_deptAgr);
		lbl_deptAgr.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_deptAgr.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lbl_uniAgr = new JLabel("Üniversite:");
		lbl_uniAgr.setBounds(3, 130, 99, 16);
		panel_addAgreement.add(lbl_uniAgr);
		lbl_uniAgr.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_uniAgr.setHorizontalAlignment(SwingConstants.RIGHT);
		comboBox_deptAgr = new JComboBox<Object>(dept_names_agr);
		comboBox_deptAgr.setBounds(130, 81, 300, 25);
		panel_addAgreement.add(comboBox_deptAgr);
		comboBox_uniAgr = new JComboBox<Object>(uni_names_agr);
		comboBox_uniAgr.setBounds(130, 126, 300, 25);
		panel_addAgreement.add(comboBox_uniAgr);
			
		JPanel panel_studentInfo = new JPanel();
		panel_studentInfo.setBorder(new LineBorder(SystemColor.inactiveCaption, 2));
		panel_homepage.add(panel_studentInfo);
		panel_studentInfo.setLayout(null);
		
		JLabel lbl_updateExamscore = new JLabel("Öðrenci Bilgisi Güncelleme");
		lbl_updateExamscore.setBackground(new Color(47, 79, 79));
		lbl_updateExamscore.setOpaque(true);
		lbl_updateExamscore.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_updateExamscore.setForeground(SystemColor.control);
		lbl_updateExamscore.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_updateExamscore.setBounds(2, 2, 446, 24);
		panel_studentInfo.add(lbl_updateExamscore);
		
		JLabel lbl_stdno = new JLabel("Öðrenci Numarasý:");
		lbl_stdno.setBounds(6, 76, 107, 16);
		panel_studentInfo.add(lbl_stdno);
		lbl_stdno.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_stdno.setFont(new Font("SansSerif", Font.BOLD, 12));
		
		JButton btn_updateExamscore = new JButton("Sýnav Sonucu Güncelle");
		btn_updateExamscore.setForeground(SystemColor.menu);
		btn_updateExamscore.setFont(new Font("SansSerif", Font.BOLD, 12));
		btn_updateExamscore.setFocusable(false);
		btn_updateExamscore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textField_stdno.getText().trim().length() != 0 && textField_examscore.getText().trim().length() != 0) {			
					try {
						String stdno = textField_stdno.getText().trim();
						int examscore = Integer.valueOf(textField_examscore.getText().trim());	
						int isExist = db.updateExamscore(stdno, examscore);
						if (isExist == 1)
							JOptionPane.showMessageDialog(null, "Sýnav sonucu baþarýyla güncellendi!", "Baþarýlý", 1);
						else
							JOptionPane.showMessageDialog(null, "Böyle bir öðrenci bulunmamaktadýr!", "Uyarý", 2);					
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Hata", 0);
						db.rollBack();
					} 
				}			
				else {
					JOptionPane.showMessageDialog(null, "Lütfen bütün alanlarý doldurun!", "Uyarý", 2);
				}	
			}
		});
		btn_updateExamscore.setBackground(SystemColor.textHighlight);
		btn_updateExamscore.setBounds(27, 173, 186, 30);
		panel_studentInfo.add(btn_updateExamscore);
		
		textField_stdno = new JTextField();
		textField_stdno.setBounds(127, 70, 300, 28);
		panel_studentInfo.add(textField_stdno);
		textField_stdno.setHorizontalAlignment(SwingConstants.CENTER);
		textField_stdno.setColumns(10);
		
		JLabel lbl_examscore = new JLabel("Sýnav Sonucu:");
		lbl_examscore.setBounds(27, 128, 82, 16);
		panel_studentInfo.add(lbl_examscore);
		lbl_examscore.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_examscore.setFont(new Font("SansSerif", Font.BOLD, 12));
		
		textField_examscore = new JTextField();
		textField_examscore.setBounds(113, 122, 100, 28);
		panel_studentInfo.add(textField_examscore);
		textField_examscore.setHorizontalAlignment(SwingConstants.CENTER);
		textField_examscore.setColumns(10);
		
		textField_chosenterm = new JTextField();
		textField_chosenterm.setHorizontalAlignment(SwingConstants.CENTER);
		textField_chosenterm.setColumns(10);
		textField_chosenterm.setBounds(327, 122, 100, 28);
		panel_studentInfo.add(textField_chosenterm);
		
		JLabel lbl_chosenterm = new JLabel("Tercih Dönemi:");
		lbl_chosenterm.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_chosenterm.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_chosenterm.setBounds(235, 128, 90, 16);
		panel_studentInfo.add(lbl_chosenterm);
		
		JButton btn_updateChosenterm = new JButton("Tercih Dönemi Güncelle");
		btn_updateChosenterm.setForeground(SystemColor.menu);
		btn_updateChosenterm.setFont(new Font("SansSerif", Font.BOLD, 12));
		btn_updateChosenterm.setFocusable(false);
		btn_updateChosenterm.setBackground(SystemColor.textHighlight);
		btn_updateChosenterm.setBounds(235, 173, 192, 30);
		btn_updateChosenterm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textField_stdno.getText().trim().length() != 0 && textField_chosenterm.getText().trim().length() != 0) {			
					try {
						String stdno = textField_stdno.getText().trim();
						String examscore = textField_chosenterm.getText().trim();	
						int isExist = db.updateChosenterm(stdno, examscore);
						if (isExist == 1)
							JOptionPane.showMessageDialog(null, "Tercih dönemi baþarýyla güncellendi!", "Baþarýlý", 1);
						else
							JOptionPane.showMessageDialog(null, "Böyle bir öðrenci bulunmamaktadýr!", "Uyarý", 2);					
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Hata", 0);
						db.rollBack();
					}
				}			
				else {
					JOptionPane.showMessageDialog(null, "Lütfen bütün alanlarý doldurun!", "Uyarý", 2);
				}	
			}
		});
		panel_studentInfo.add(btn_updateChosenterm);
		
		JPanel panel_country = new JPanel();
		panel_country.setBorder(new LineBorder(SystemColor.inactiveCaption, 2));
		panel_homepage.add(panel_country);
		panel_country.setLayout(null);
		
		JLabel lbl_country = new JLabel("Ülkeye Göre Bilgi Güncelleme ve Hesaplama");
		lbl_country.setOpaque(true);
		lbl_country.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_country.setForeground(SystemColor.menu);
		lbl_country.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_country.setBackground(new Color(47, 79, 79));
		lbl_country.setBounds(4, 2, 446, 24);
		panel_country.add(lbl_country);
		
		textField_country = new JTextField();
		textField_country.setHorizontalAlignment(SwingConstants.CENTER);
		textField_country.setColumns(10);
		textField_country.setBounds(92, 70, 122, 28);
		panel_country.add(textField_country);
		
		JLabel lbl_countryName = new JLabel("Ülke Ýsmi:");
		lbl_countryName.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_countryName.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_countryName.setBounds(6, 76, 82, 16);
		panel_country.add(lbl_countryName);
		
		JLabel lbl_grant = new JLabel("Hibe Miktarý:");
		lbl_grant.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_grant.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_grant.setBounds(6, 128, 82, 16);
		panel_country.add(lbl_grant);
		
		textField_grant = new JTextField();
		textField_grant.setHorizontalAlignment(SwingConstants.CENTER);
		textField_grant.setColumns(10);
		textField_grant.setBounds(92, 122, 122, 28);
		panel_country.add(textField_grant);
		
		JButton btn_getAvgAgnoExam = new JButton("Ortalama Hesapla");
		btn_getAvgAgnoExam.setForeground(SystemColor.menu);
		btn_getAvgAgnoExam.setFont(new Font("SansSerif", Font.BOLD, 12));
		btn_getAvgAgnoExam.setFocusable(false);
		btn_getAvgAgnoExam.setBackground(Color.DARK_GRAY);
		btn_getAvgAgnoExam.setBounds(226, 70, 202, 28);
		btn_getAvgAgnoExam.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textField_country.getText().trim().length() != 0) {			
					try {
						String country = textField_country.getText().trim();
						Object[] infos = db.getStudentsAvgScores(country);
						if (infos[0] != null) {
							String info = country + " ülkesindeki üniversitelere giden öðrencilerin;"
												  + "\nSayýsý: " + infos[2]
												  + "\nAGNOlarýnýn Ortalamasý: " + infos[0]
												  + "\nSýnav Sonuçlarýnýn Ortalamasý: " + infos[1];
							JOptionPane.showMessageDialog(null, info, "Bilgi", 1);
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Hata", 0);
						db.rollBack();
					}
				}			
				else {
					JOptionPane.showMessageDialog(null, "Lütfen ülke ismini girin!", "Uyarý", 2);
				}	
			}
		});
		panel_country.add(btn_getAvgAgnoExam);
		
		JButton btn_updateGrant = new JButton("Hibe Miktarý Güncelle");
		btn_updateGrant.setForeground(SystemColor.menu);
		btn_updateGrant.setFont(new Font("SansSerif", Font.BOLD, 12));
		btn_updateGrant.setFocusable(false);
		btn_updateGrant.setBackground(SystemColor.textHighlight);
		btn_updateGrant.setBounds(6, 173, 208, 30);
		btn_updateGrant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textField_country.getText().trim().length() != 0 && textField_grant.getText().trim().length() != 0) {			
					try {
						String country = textField_country.getText().trim();
						int newGrant = Integer.valueOf(textField_grant.getText().trim());	
						int isExist = db.updateGrant(country, newGrant);
						if (isExist == 1)
							JOptionPane.showMessageDialog(null, "Hibe miktarý baþarýyla güncellendi!", "Baþarýlý", 1);
						else
							JOptionPane.showMessageDialog(null, "Böyle bir ülke bulunmamaktadýr!", "Uyarý", 2);					
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.toString(), "Hata", 0);
						db.rollBack();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Lütfen bütün alanlarý doldurun!", "Uyarý", 2);
				}	
			}
		});
		panel_country.add(btn_updateGrant);
		
		
		// AGREEMENTS PANEL
		JPanel panel_agreements = new JPanel();
		tabbedPane.addTab("Erasmus Anlaþmalarý ", new ImageIcon("images/handshake.png"), panel_agreements, null);
		panel_agreements.setLayout(null);
		
		JPanel panel_agrmnt = new JPanel();
		FlowLayout fl_panel_agrmnt = (FlowLayout) panel_agrmnt.getLayout();
		fl_panel_agrmnt.setHgap(12);
		fl_panel_agrmnt.setVgap(10);
		fl_panel_agrmnt.setAlignment(FlowLayout.LEFT);
		panel_agrmnt.setBounds(0, 88, 900, 413);
		panel_agreements.add(panel_agrmnt);
		
		table_agreements = new JTable();
		table_agreements.setEnabled(false);
		table_agreements.setPreferredScrollableViewportSize(new Dimension(860, 400));
		JScrollPane scrollPane = new JScrollPane(table_agreements);
		panel_agrmnt.add(scrollPane);
		
		JPanel panel_choosedept = new JPanel();
		panel_choosedept.setBounds(0, 0, 900, 88);
		panel_agreements.add(panel_choosedept);
		panel_choosedept.setLayout(null);
		
		JLabel lbl_dept = new JLabel("Bölüm: ");
		lbl_dept.setBounds(83, 47, 42, 16);
		lbl_dept.setFont(new Font("SansSerif", Font.BOLD, 12));
		panel_choosedept.add(lbl_dept);
		
		String[] dept_names = db.getDepartmentNames();
		comboBox_dept = new JComboBox<Object>(dept_names);
		comboBox_dept.setBounds(137, 42, 231, 26);
		comboBox_dept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JComboBox<?> comboBox = (JComboBox<?>) event.getSource();
                String selected_dept = comboBox.getSelectedItem().toString();
                
                Object[] columns = {"Üniversite ID", "Üniversite Adý", "Ülke", "Hibe Miktarý"};
                DefaultTableModel agreements = new DefaultTableModel(columns, 0);	
                ArrayList<Object[]> universities;
				try {
					universities = db.getUniversities(selected_dept);
					for (Object[] university : universities) {
	                	agreements.addRow(university);
	                }
	                table_agreements.setModel(agreements);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Hata", 0);
					db.rollBack();
				}
            }
        });
		panel_choosedept.add(comboBox_dept);
		
		JLabel lbl_listByDept = new JLabel("Bölüme Göre Anlaþmalarý Listeleme");
		lbl_listByDept.setOpaque(true);
		lbl_listByDept.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_listByDept.setForeground(SystemColor.menu);
		lbl_listByDept.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_listByDept.setBackground(new Color(47, 79, 79));
		lbl_listByDept.setBounds(2, 2, 446, 24);
		panel_choosedept.add(lbl_listByDept);
		
		JLabel lbl_listDeptsByExam = new JLabel("Sýnav Ortalamasýna Göre Bölüm Listeleme");
		lbl_listDeptsByExam.setOpaque(true);
		lbl_listDeptsByExam.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_listDeptsByExam.setForeground(SystemColor.menu);
		lbl_listDeptsByExam.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_listDeptsByExam.setBackground(new Color(47, 79, 79));
		lbl_listDeptsByExam.setBounds(452, 2, 446, 24);
		panel_choosedept.add(lbl_listDeptsByExam);
		
		textField_examavg = new JTextField();
		textField_examavg.setHorizontalAlignment(SwingConstants.CENTER);
		textField_examavg.setColumns(10);
		textField_examavg.setBounds(591, 40, 107, 28);
		panel_choosedept.add(textField_examavg);
		
		JLabel lbl_examavg = new JLabel("Sýnav Ortalamasý:");
		lbl_examavg.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_examavg.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_examavg.setBounds(462, 46, 117, 16);
		panel_choosedept.add(lbl_examavg);
		
		JButton btn_listBestDepartments = new JButton("Listele");
		btn_listBestDepartments.setForeground(SystemColor.menu);
		btn_listBestDepartments.setFont(new Font("SansSerif", Font.BOLD, 12));
		btn_listBestDepartments.setFocusable(false);
		btn_listBestDepartments.setBackground(new Color(0, 128, 128));
		btn_listBestDepartments.setBounds(710, 38, 142, 30);
		btn_listBestDepartments.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textField_examavg.getText().trim().length() != 0) {
					int examavg = Integer.valueOf(textField_examavg.getText().trim());				
					Object[] column_depts = {"Bölüm Adý", "Ortalama Sýnav Puaný"};
	        		DefaultTableModel deptModel = new DefaultTableModel(column_depts, 0);	
					try {
						ArrayList<Object[]> departments = db.getBestDepartmentsByExam(examavg);	
						for (Object[] dept : departments) {        	
							deptModel.addRow(dept);
		                }
		                table_agreements.setModel(deptModel);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Hata", 0);
						db.rollBack();
					} 
				}
				else {
					JOptionPane.showMessageDialog(null, "Lütfen sýnav ortalamasýný girin!", "Uyarý", 2);
				}	
			}
		});	
		panel_choosedept.add(btn_listBestDepartments);
		
		
		
		// STUDENT LIST PANEL
		JPanel panel_studentlist = new JPanel();
		tabbedPane.addTab("Öðrenci Listesi ", new ImageIcon("images/exam.png"), panel_studentlist, null);
		panel_studentlist.setLayout(null);
		
		JPanel panel_stdlist = new JPanel();
		FlowLayout fl_panel_stdlist = (FlowLayout) panel_stdlist.getLayout();
		fl_panel_stdlist.setHgap(12);
		fl_panel_stdlist.setVgap(10);
		fl_panel_stdlist.setAlignment(FlowLayout.LEFT);
		panel_stdlist.setBounds(0, 139, 900, 390);
		panel_studentlist.add(panel_stdlist);
		
		table_students = new JTable();
		table_students.setEnabled(false);
		table_students.setPreferredScrollableViewportSize(new Dimension(860, 400));
		JScrollPane scrollPane2 = new JScrollPane(table_students);
		scrollPane2.setPreferredSize(new Dimension(864, 350));
		panel_stdlist.add(scrollPane2);
		
		JPanel panel_s = new JPanel();
		panel_s.setBounds(0, 0, 900, 145);
		panel_studentlist.add(panel_s);
		
		String[] dept_names2 = db.getDepartmentNames();
		panel_s.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_dept = new JPanel();
		panel_s.add(panel_dept);
		panel_dept.setLayout(null);
		JLabel lbl_dept2 = new JLabel("Bölüm: ");
		lbl_dept2.setBounds(85, 37, 42, 16);
		panel_dept.add(lbl_dept2);
		lbl_dept2.setFont(new Font("SansSerif", Font.BOLD, 12));
		comboBox_deptStd = new JComboBox<Object>(dept_names2);
		comboBox_deptStd.setBounds(131, 32, 231, 26);	
		comboBox_deptStd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JComboBox<?> comboBox = (JComboBox<?>) event.getSource();
                String selected_dept = comboBox.getSelectedItem().toString();
                Object[] column_students = {"Öðrenci No", "Ad Soyad", "AGNO", "Sýnav Puaný", "Tercih Dönemi", "Üniversite Tercihi", "Hibe Durumu"};
        		DefaultTableModel studentModel = new DefaultTableModel(column_students, 0);	
                ArrayList<Object[]> students;
				try {
					students = db.getStudents(selected_dept);
					for (Object[] student : students) {        	
	                	studentModel.addRow(student);
	                }
	                table_students.setModel(studentModel);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Hata", 0);
					db.rollBack();
				}
            }
        });
		panel_dept.add(comboBox_deptStd);
		
		JLabel lbl_stdByDept = new JLabel("Bölüme Göre Öðrenci Listeleme");
		lbl_stdByDept.setOpaque(true);
		lbl_stdByDept.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_stdByDept.setForeground(SystemColor.menu);
		lbl_stdByDept.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_stdByDept.setBackground(new Color(47, 79, 79));
		lbl_stdByDept.setBounds(2, 2, 446, 24);
		panel_dept.add(lbl_stdByDept);
		
		JLabel lbl_stdByNumber = new JLabel("Sýnav Sonucu En Ýyi Olan Öðrencileri Listeleme");
		lbl_stdByNumber.setOpaque(true);
		lbl_stdByNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_stdByNumber.setForeground(SystemColor.menu);
		lbl_stdByNumber.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_stdByNumber.setBackground(new Color(47, 79, 79));
		lbl_stdByNumber.setBounds(2, 70, 446, 24);
		panel_dept.add(lbl_stdByNumber);
		
		textField_nmbOfStd = new JTextField();
		textField_nmbOfStd.setHorizontalAlignment(SwingConstants.CENTER);
		textField_nmbOfStd.setColumns(10);
		textField_nmbOfStd.setBounds(131, 107, 112, 28);
		panel_dept.add(textField_nmbOfStd);
		
		JLabel lbl_nmbOfStd = new JLabel("Öðrenci Sayýsý:");
		lbl_nmbOfStd.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_nmbOfStd.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_nmbOfStd.setBounds(30, 112, 95, 16);
		panel_dept.add(lbl_nmbOfStd);
		
		JButton btn_listBestStudents = new JButton("Listele");
		btn_listBestStudents.setForeground(SystemColor.menu);
		btn_listBestStudents.setFont(new Font("SansSerif", Font.BOLD, 12));
		btn_listBestStudents.setFocusable(false);
		btn_listBestStudents.setBackground(new Color(0, 128, 128));
		btn_listBestStudents.setBounds(255, 106, 107, 30);
		btn_listBestStudents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textField_nmbOfStd.getText().trim().length() != 0) {
					int nmbOfStudents = Integer.valueOf(textField_nmbOfStd.getText().trim());				
					Object[] column_students = {"Ad Soyad", "Sýnav Puaný", "Üniversite Tercihi"};
	        		DefaultTableModel studentModel = new DefaultTableModel(column_students, 0);	
					try {
						ArrayList<Object[]> students = db.getBestStudents(nmbOfStudents);	
						for (Object[] student : students) {        	
		                	studentModel.addRow(student);
		                }
		                table_students.setModel(studentModel);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Hata", 0);
						db.rollBack();
					} 
				}
				else {
					JOptionPane.showMessageDialog(null, "Lütfen bütün alanlarý doldurun!", "Uyarý", 2);
				}	
			}
		});	
		panel_dept.add(btn_listBestStudents);
		
		JPanel panel_uniTerm = new JPanel();
		panel_uniTerm.setLayout(null);
		panel_s.add(panel_uniTerm);
		
		JLabel lbl_univTerm = new JLabel("Üniversite: ");
		lbl_univTerm.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_univTerm.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_univTerm.setBounds(47, 38, 100, 16);
		panel_uniTerm.add(lbl_univTerm);
		
		String[] uni_names_term = db.getUniversityNames();
		JComboBox<Object> comboBox_univTerm = new JComboBox<Object>(uni_names_term);
		comboBox_univTerm.setSelectedIndex(0);
		comboBox_univTerm.setBounds(147, 33, 231, 26);
		panel_uniTerm.add(comboBox_univTerm);
		
		JLabel lbl_stdByTermUni = new JLabel("Tercih Edilen Üniversite ve Döneme Göre Öðrenci Listeleme");
		lbl_stdByTermUni.setOpaque(true);
		lbl_stdByTermUni.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_stdByTermUni.setForeground(SystemColor.menu);
		lbl_stdByTermUni.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_stdByTermUni.setBackground(new Color(47, 79, 79));
		lbl_stdByTermUni.setBounds(2, 2, 446, 24);
		panel_uniTerm.add(lbl_stdByTermUni);
		
		JLabel lbl_chosenTerm = new JLabel("Tercih Dönemi:");
		lbl_chosenTerm.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_chosenTerm.setFont(new Font("SansSerif", Font.BOLD, 12));
		lbl_chosenTerm.setBounds(47, 72, 95, 16);
		panel_uniTerm.add(lbl_chosenTerm);
		
		textField_chosenTerm = new JTextField();
		textField_chosenTerm.setHorizontalAlignment(SwingConstants.CENTER);
		textField_chosenTerm.setColumns(10);
		textField_chosenTerm.setBounds(147, 66, 231, 28);
		panel_uniTerm.add(textField_chosenTerm);
		
		JButton btn_listStudents = new JButton("Listele");
		btn_listStudents.setForeground(SystemColor.menu);
		btn_listStudents.setFont(new Font("SansSerif", Font.BOLD, 12));
		btn_listStudents.setFocusable(false);
		btn_listStudents.setBackground(new Color(0, 128, 128));
		btn_listStudents.setBounds(148, 106, 231, 30);
		btn_listStudents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textField_chosenTerm.getText().trim().length() != 0) {
					String selectedTerm = textField_chosenTerm.getText().trim();
					String selectedUni = comboBox_univTerm.getSelectedItem().toString();				
					Object[] column_students = {"Öðrenci No", "Ad Soyad", "AGNO", "Sýnav Puaný", "Tercih Dönemi", "Üniversite Tercihi", "Hibe Durumu"};
	        		DefaultTableModel studentModel = new DefaultTableModel(column_students, 0);	
					try {
						ArrayList<Object[]> students = db.getStudentsWithIntersect(selectedTerm, selectedUni);	
						for (Object[] student : students) {        	
		                	studentModel.addRow(student);
		                }
		                table_students.setModel(studentModel);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Hata", 0);
						db.rollBack();
					} 
				}
				else {
					JOptionPane.showMessageDialog(null, "Lütfen bütün alanlarý doldurun!", "Uyarý", 2);
				}				
			}
		});	
		panel_uniTerm.add(btn_listStudents);			
	}
}
