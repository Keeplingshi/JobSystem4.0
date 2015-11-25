/*
 * ���ݿ����
 */

package com.sql;

import java.sql.*;

import javax.swing.JOptionPane;

public class SqlHelper {

	//�����������ݿ������Ҫ�Ĺ���
	Connection ct=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	//�������ݿ���Ϣ
//	String driver="com.mysql.jdbc.Driver";	//���ݿ�������
//	String url="jdbc:mysql://219.219.60.248:3306/JobSystem?user=root&password=root";
	
//	String driver="com.mysql.jdbc.Driver";	//���ݿ�������
//	String url="jdbc:mysql://202.119.206.153:3306/JobSystem?user=root&password=root";
	
//	String driver="com.mysql.jdbc.Driver";	//���ݿ�������
//	String url="jdbc:mysql://csxg.cumt.edu.cn:3306/JobSystem?user=root&password=root";
	
	String driver="com.mysql.jdbc.Driver";	//���ݿ�������
	String url="jdbc:mysql://localhost:3306/JobSystem?user=root&password=123456";
	
	//��������
	public void Driver()
	{
		try {
			Class.forName(driver);
			ct=DriverManager.getConnection(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "���ݿ��޷�����");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	//�ر�
	public void close()
	{
		try {
			if(rs!=null) {rs.close();}
			if(ct!=null) {ct.close();}
			if(ps!=null) {ps.close();}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet Query(String sql)
	{
		this.Driver();
		try{
			ps = ct.prepareStatement(sql);
			rs=ps.executeQuery();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	
	//��ѯ
	public ResultSet Query(String sql,String []paras)
	{
		this.Driver();
		
		try{
			ps=ct.prepareStatement(sql);
			for(int i=0;i<paras.length;i++)
			{
				ps.setString(i+1, paras[i]);
			}
			rs=ps.executeQuery();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return rs;
	}
	
	public boolean Update(String sql)
	{
		this.Driver();
		boolean b=true;
		try{
			ps=ct.prepareStatement(sql);
			ps.executeUpdate();
		}catch (SQLException e){
			b=false;
			e.printStackTrace();
		}
		return b;
	}
	
	//��ɾ��(����)
	public boolean Update(String sql,String []paras)
	{
		this.Driver();
		boolean b=true;
		try{
			ps=ct.prepareStatement(sql);
			for(int i=0;i<paras.length;i++)
			{
				ps.setString(i+1, paras[i]);
			}
			ps.executeUpdate();
		}catch (SQLException e){
			b=false;
			e.printStackTrace();
		}
		return b;
	}
	
}
