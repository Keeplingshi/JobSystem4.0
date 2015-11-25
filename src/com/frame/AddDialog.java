package com.frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.other.JobInfoTable;
import com.sql.SqlHelper;
import com.sql.SqlModel;
import com.tools.Tool;

@SuppressWarnings("serial")
public class AddDialog extends JFrame{

	private String returnXuehao=null;
	  public String getReturnXuehao() {
		return returnXuehao;
	}

	public void setReturnXuehao(String returnXuehao) {
		this.returnXuehao = returnXuehao;
	}


	String name;
	  String class1;
	  private String[] lie = new String[12];
	  private SqlHelper sh=null;
	  private String major;
	  private final JButton btnModify = new JButton();

	  private final JTextField tfName = new JTextField();
	  private final JTextField tfcompany = new JTextField();
	  private final JTextField tfCity = new JTextField();
	  private final JTextField tfMoney = new JTextField();
	  //private final JTextField tfNote = new JTextField();
	  private final JTextArea tfNodeArea=new JTextArea();
	  private final JTextField tfXueHao = new JTextField();

	  private final JLabel areaLabel = new JLabel();
	  private final JLabel nameLabel = new JLabel();
	  private final JLabel stateLabel = new JLabel();
	  private final JLabel classLabel = new JLabel();
	  private final JLabel companyLabel = new JLabel();
	  private final JLabel protocalLabel = new JLabel();
	  private final JLabel nowLabel = new JLabel();
	  private final JLabel cityLabel = new JLabel();
	  private final JLabel moneyLabel = new JLabel();
	  private final JLabel noteLabel = new JLabel();
	  private final JLabel makesureLabel = new JLabel();
	  private final JLabel xueHaoLabel = new JLabel();
	  private final JLabel xingBieLabel = new JLabel();

	  private JComboBox nowStateJcb = null;
	  private JComboBox areaJcb = null;
	  private JComboBox classJcb =null;
	  private JComboBox stateJcb = null;
	  private JComboBox protocalJcb = null;
	  private JComboBox sexJcb = null;
	  
	  private JScrollPane jScrollPane=new JScrollPane();
	  
//	  private JComboBox mainFclassJcb=null;
//	  private JTable mainFjt=null;
//	  private JobInfoTable mainFjit=null;
	  
	  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	  private String userId=null;
	  
	  public AddDialog(String userId)
	  {
		  this.userId=userId;
//		  this.mainFclassJcb=mainFclassJcb;
//		  this.mainFjt=mainFjt;
//		  this.mainFjit=mainFjit;
		  
		  this.setBounds(this.screenSize.width / 2 - 250, this.screenSize.height / 2 - 250, 500, 450);
		  this.setResizable(false);
		  try {
			  jbInit();
			  
			  if(userId.equals("admin")||userId.equals("5237")){
				  
			  }else{
				  String csql="select className from classinfo where grade='"+Tool.getGrade(userId)+"' and monitorId='"+userId+"'";
				  
				  String classNameFromSql=SqlModel.getInfo(csql).trim();
				  classJcb.setSelectedItem(classNameFromSql);
				  
			  }
			  
				if(stateJcb.getSelectedItem().equals("��ǩ")){
				    protocalJcb.setModel(new DefaultComboBoxModel(new String[] { "", "�����ѽ�", "��ǩδ��"}));
				}else if(stateJcb.getSelectedItem().equals("δǩ")){
				    protocalJcb.setModel(new DefaultComboBoxModel(new String[] { "", "δ�ҵ�����","����offer�ڿ���"}));
				}else {
					protocalJcb.setModel(new DefaultComboBoxModel(new String[] { "" }));
				}
		  } catch (Throwable e) {
			  e.printStackTrace();
		  }
		  this.setVisible(true);
		  
	  }
	  
	  private void jbInit() {
		  
		  getContentPane().setLayout(null);
		  this.setTitle("��ҵ����޸�");

		  getContentPane().add(this.classLabel);
		  this.classLabel.setText("�༶��");
		  this.classLabel.setBounds(30, 25, 42, 18);

		  if(userId.equals("admin")||userId.equals("5237"))
		  {
			  String sql="select className from classinfo where grade='"+Tool.getGrade(userId)+"'";
			  String[] classArray=SqlModel.classInfoQuery(sql);
			  classArray[0]="";
			  classJcb=new JComboBox(classArray);			  
		  }else{
			  String csql="select className from classinfo where grade='"+Tool.getGrade(userId)+"' and monitorId='"+userId+"'";
			  String classNameFromSql=SqlModel.getInfo(csql).trim();
			  String[] classArray={classNameFromSql};
			  classJcb=new JComboBox(classArray);
		  }

		  getContentPane().add(classJcb);
		  classJcb.setBounds(104, 21, 106, 27);

		  getContentPane().add(this.nameLabel);
		  this.nameLabel.setText(" ������");
		  this.nameLabel.setBounds(30, 69, 42, 18);

		  getContentPane().add(this.tfName);
		  this.tfName.setBounds(104, 67, 106, 22);
		  
		  getContentPane().add(this.stateLabel);
		  this.stateLabel.setText("ǩԼ״̬��");
		  this.stateLabel.setBounds(10, 118, 80, 18);

		  stateJcb=new JComboBox(new String[] { "��ǩ", "δǩ", "����", "����", "������Ա", "����" });
		  stateJcb.addItemListener(new ItemListener() {
				
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(protocalJcb==null){
					return;
				}
				if(e.getItem().equals("��ǩ")){
				    protocalJcb.setModel(new DefaultComboBoxModel(new String[] { "", "�����ѽ�", "��ǩδ��" }));
				}else if(e.getItem().equals("δǩ")){
				    protocalJcb.setModel(new DefaultComboBoxModel(new String[] { "", "δ�ҵ�����","����offer�ڿ���"}));
				}else {
					protocalJcb.setModel(new DefaultComboBoxModel(new String[] { "" }));
				}
			}});
		  stateJcb.setBounds(104, 114, 106, 27);
		  getContentPane().add(stateJcb);

		  getContentPane().add(this.companyLabel);
		  this.companyLabel.setText(" ǩԼ��λ��");
		  this.companyLabel.setBounds(10, 163, 75, 18);

		  getContentPane().add(this.tfcompany);
		  this.tfcompany.setBounds(104, 161, 106, 22);

		  getContentPane().add(this.protocalLabel);
		  this.protocalLabel.setText(" Э����״̬��");
		  this.protocalLabel.setBounds(239, 118, 84, 18);

		  protocalJcb=new JComboBox(new String[] {""});
		  protocalJcb.setBounds(344, 114, 106, 27);
		  getContentPane().add(protocalJcb);
		  
		  getContentPane().add(this.nowLabel);
		  this.nowLabel.setText("��ǰ״̬��");
		  this.nowLabel.setBounds(239, 163, 80, 18);

		  nowStateJcb=new JComboBox(new String[] { "��ѧУ","�ڼ�", "ʵϰ" });
		  nowStateJcb.setBounds(344, 161, 106, 22);
		  getContentPane().add(nowStateJcb);
		  
		  getContentPane().add(this.areaLabel);
		  this.areaLabel.setText("ǩԼ���ڵ�����");
		  this.areaLabel.setBounds(10, 209, 100, 18);

		  areaJcb=new JComboBox(new String[] { "", "����", "����", "����", "����", "����", "����", "����" });
		  areaJcb.setBounds(104, 207, 106, 22);
		  getContentPane().add(areaJcb);

		  getContentPane().add(this.cityLabel);
		  this.cityLabel.setText("ǩԼ���ڳ��У�");
		  this.cityLabel.setBounds(239, 209, 99, 18);

		  getContentPane().add(this.tfCity);
		  this.tfCity.setBounds(344, 207, 106, 22);

		  getContentPane().add(this.moneyLabel);
		  this.moneyLabel.setText("н�ʣ�");
		  this.moneyLabel.setBounds(259, 251, 59, 18);

		  getContentPane().add(this.tfMoney);
		  this.tfMoney.setBounds(344, 249, 106, 22);

		  getContentPane().add(this.noteLabel);
		  this.noteLabel.setText("��ע����Ҫд������ʲô����");
		  this.noteLabel.setBounds(10, 251, 200, 18);

		  jScrollPane=new JScrollPane(tfNodeArea);
		  getContentPane().add(jScrollPane);
		  this.jScrollPane.setBounds(10, 283, 300, 50);
		  
		  //getContentPane().add(tfNodeArea);
		  //jScrollPane.add(tfNodeArea);
		  tfNodeArea.setLineWrap(true);
		  //tfNodeArea.setBorder(BorderFactory.createLoweredBevelBorder());
		  //this.tfNodeArea.setBounds(10, 283, 300, 50);
//		  getContentPane().add(this.tfNote);
//		  this.tfNote.setBounds(10, 283, 200, 22);

		  getContentPane().add(this.makesureLabel);
		  this.makesureLabel.setText("");
		  this.makesureLabel.setBounds(20, 325, 1000, 18);

		  getContentPane().add(this.btnModify);
		  this.btnModify.setBounds(177, 349, 106, 33);
		  this.btnModify.setText("����");
		  this.btnModify.addMouseListener(new BtnAddMouseListener());

		  getContentPane().add(this.xueHaoLabel);
		  this.xueHaoLabel.setText("ѧ��:");
		  this.xueHaoLabel.setBounds(259, 25, 42, 18);

		  getContentPane().add(this.xingBieLabel);
		  this.xingBieLabel.setText("�Ա�");
		  this.xingBieLabel.setBounds(259, 69, 42, 18);

		  sexJcb=new JComboBox(new String[] { "��", "Ů" });
		  sexJcb.setBounds(344, 67, 106, 25);
		  getContentPane().add(sexJcb);
		  
		  getContentPane().add(this.tfXueHao);
		  this.tfXueHao.setBounds(342, 23, 108, 22);
	}
	  
	  
	  class BtnAddMouseListener extends MouseAdapter
	  {
		  BtnAddMouseListener(){}
		  
		  public void mouseClicked(MouseEvent e)
		  {
			  
			  if ((tfName.getText().trim().equals("")) || (tfName.getText().length() == 0)) {
				  makesureLabel.setText("��������Ϊ�գ���");
			  } else if ((tfXueHao.getText().trim().length() == 0)) {
				  AddDialog.this.makesureLabel.setText("ѧ�Ų���Ϊ�գ���");
		      } else {
					lie[0] = ((String)classJcb.getSelectedItem());
					lie[1] = tfXueHao.getText();
					lie[2] = tfName.getText();
					lie[3] = ((String)sexJcb.getSelectedItem());
					lie[4] = ((String)stateJcb.getSelectedItem());
					lie[5] = tfcompany.getText();
					lie[6] = ((String)protocalJcb.getSelectedItem());
					lie[7] = ((String)nowStateJcb.getSelectedItem());
					lie[8] = ((String)areaJcb.getSelectedItem());
					lie[9] = tfCity.getText();
					lie[10] = tfMoney.getText();
					lie[11]=tfNodeArea.getText();
					//lie[11] = tfNote.getText();
					
			        try
			        {
			        	if (!AddDialog.this.tfMoney.getText().equals("")) {
				            String money = tfMoney.getText();
				            Double.parseDouble(money);
			        	}
			        	
			        	String strSql = "";
			        	strSql = "select * from jobinfo where id = '" + Tool.string2UTF8(tfXueHao.getText().trim()) + "'";
			        	sh=new SqlHelper();
			        	ResultSet rs=sh.Query(strSql);
			        	
			        	if (rs.next()) {
			        		makesureLabel.setText("�Ѿ��иð༶�ĸ�ͬѧ��������޸ģ�");
			        	} else {
			        		
			        		major=SqlModel.getInfo("select major from classinfo where grade='"+Tool.getGrade(userId)+"' and className='"+Tool.string2UTF8(lie[0])+"'");
			        		
			            strSql = "insert into jobinfo (class,id,studentName,sex,state,company,protocalState,nowState,area,city,salary,note,major,mtime,grade) values ('" + 
			              Tool.string2UTF8(lie[0]) + 
			              "','" + 
			              Tool.string2UTF8(lie[1]) + 
			              "','" + 
			              Tool.string2UTF8(lie[2]) + 
			              "','" + 
			              Tool.string2UTF8(lie[3]) + 
			              "','" + 
			              Tool.string2UTF8(lie[4]) + 
			              "','" + 
			              Tool.string2UTF8(lie[5]) + 
			              "','" + 
			              Tool.string2UTF8(lie[6]) + 
			              "','" + 
			              Tool.string2UTF8(lie[7]) + 
			              "','" + 
			              Tool.string2UTF8(lie[8]) + 
			              "','" + 
			              Tool.string2UTF8(lie[9]) + 
			              "','" + 
			              Tool.string2UTF8(lie[10]) + 
			              "','" + 
			              Tool.string2UTF8(lie[11]) + 
			              "','" + 
			              Tool.string2UTF8(major) + 
			              "','" + 
			              Tool.string2UTF8(Tool.whoModify(userId)) + 
			              "','"+
			              Tool.getGrade(userId)+
			              "')";
			            if (sh.Update(strSql)){
			            	returnXuehao=lie[1];
			              AddDialog.this.makesureLabel.setText("�ɹ�����");
			            }
			            else {
			              AddDialog.this.makesureLabel.setText("ʧ��!");
			            }
			            sh.close();
			            dispose();
			          }
			        } catch (SQLException e1) {
			        	makesureLabel.setText(e1.getMessage());
			        } catch (Exception e2) {
			          	makesureLabel.setText(e2.getMessage() + " ��н�����������");
			        } finally {

			        }
	      }
	    }
	  }
}
