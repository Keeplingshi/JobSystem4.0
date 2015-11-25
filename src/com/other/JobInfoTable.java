package com.other;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.sql.SqlHelper;
import com.tools.Tool;

public class JobInfoTable extends AbstractTableModel{

	private SqlHelper sh;
	//rowData�����������
	//columnNames�������
	@SuppressWarnings("rawtypes")
	private Vector<Vector> rowData;
	private Vector<String> columnNames;
	
	public JobInfoTable() {
		// TODO Auto-generated constructor stub
		
	}

	//��ѯ��Ҫ��ʾ��������Ϣ
	@SuppressWarnings("rawtypes")
	public void jobTableQuery(String sql,String []paras)
	{
		sh=new SqlHelper();
		ResultSet rs=sh.Query(sql, paras);
		columnNames=new Vector<String>();
		rowData=new Vector<Vector>();
		//�˷����� ����չ
		try{
			ResultSetMetaData rsmd=rs.getMetaData();
			columnNames.add("���");
			columnNames.add("�༶");
			columnNames.add("ѧ��");
			columnNames.add("����");
			columnNames.add("�Ա�");
			columnNames.add("ǩԼ״̬");
			columnNames.add("ǩԼ��λ");
			columnNames.add("Э����״̬");
			columnNames.add("��ǰ״̬");
			columnNames.add("ǩԼ��λ���ڵ���");
			columnNames.add("ǩԼ��λ���ڳ���");
			columnNames.add("н��/��");
			columnNames.add("��ע����Ҫд�����ʲô��");
			columnNames.add("�޸ļ�¼");
			
			int x=0;
			while(rs.next())
			{
				Vector<String> temp=new Vector<String>();
				temp.add(++x+"");
				for(int i=0;i<rsmd.getColumnCount();i++)
				{
					temp.add(Tool.UTF82String(rs.getString(i+1)));
				}
				rowData.add(temp);
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			sh.close();
		}
	}
	
	public static void main(String[] args) {
		
	}
	
	//��ɾ��������Ϣ
	//��ӡ��޸ĵķ���
	public boolean Update(String sql,String []paras)
	{   
		boolean b=true;
		sh = new SqlHelper();
		try{
			b=sh.Update(sql, paras);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sh.close();
		}
		return b;
	}	
	
	public String getColumnName(int column) {
		return (String)columnNames.get(column);
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rowData.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.columnNames.size();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return ((Vector)rowData.get(rowIndex)).get(columnIndex);
	}

}
