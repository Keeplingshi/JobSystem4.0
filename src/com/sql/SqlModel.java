package com.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import com.tools.Tool;

public class SqlModel {
	
	public static String[] gradeinfoQuery()
	{
		String sql="select grade from gradeinfo";
		
		String[] str = new String[10000];
		SqlHelper sh = new SqlHelper();
		ResultSet rs = sh.Query(sql);
		int i=0;
		try {
			while (rs.next()) {
				str[i++] = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			sh.close();
		}
	
		String[] result=new String[i+1];
		for(int x=0;x<result.length-1;x++)
		{
			result[x]=Tool.UTF82String(str[x]);
		}
		result[result.length-1]="新增";
		
		return result;
	}
	
	/**
	 * 查询专业
	 * @param sql
	 * @return
	 */
	public static String[] majorInfoQuery(String userId) {
		
		String sql="select majorName from majorinfo where grade='"+Tool.getGrade(userId)+"'";
		
		String[] str = new String[10000];
		SqlHelper sh = new SqlHelper();
		ResultSet rs = sh.Query(sql);
		int i=0;
		try {
			while (rs.next()) {
				str[i++] = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			sh.close();
		}
		
		String[] result=new String[i+1];
		result[0]="";
		for(int x=1;x<result.length;x++)
		{
			result[x]=Tool.UTF82String(str[x-1]);
		}
		
		return result;
	}
	
	/**
	 * 获取标记学生
	 * @param sql
	 * @return
	 */
	public static HashMap<String, String> jobPositiveQuery(String sql)
	{
		HashMap<String, String> pHashMap=new HashMap<String, String>();
		SqlHelper sh=new SqlHelper();
		ResultSet rs=sh.Query(sql);
		
		try{
			while(rs.next())
			{
				pHashMap.put(Tool.UTF82String(rs.getString(1)),Tool.UTF82String(rs.getString(2)));
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			sh.close();
		}
		
		return pHashMap;
	}
	
	
	/**
	 * 获取所有班级名称
	 * @param sql
	 * @return
	 */
	public static String[] classInfoQuery(String sql) {
		String[] str = new String[10000];
		SqlHelper sh = new SqlHelper();
		ResultSet rs = sh.Query(sql);
		int i=0;
		try {
			while (rs.next()) {
				str[i++] = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			sh.close();
		}
		
		String[] result=new String[i+1];
		result[0]="";
		for(int x=1;x<result.length;x++)
		{
			result[x]=Tool.UTF82String(str[x-1]);
		}
		
		return result;
	}
	
	/**
	 * 根据sql语句查询数据库
	 * @param sql
	 * @return
	 */
	public static String getInfo(String sql)
	{
		String content="";
		SqlHelper sh=new SqlHelper();
		ResultSet rs=sh.Query(sql);
		
		try {
			while(rs.next())
			{
				content=rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			sh.close();
		}
		
		return Tool.UTF82String(content);
		//return content;
	}
	
	/**
	 * 根据sql语句更新数据库
	 * @param sql
	 * @return
	 */
	public static boolean updateInfo(String sql)
	{
		boolean b = true;
		SqlHelper sh = new SqlHelper();
		try {
			b = sh.Update(sql);
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		} finally {
			sh.close();
		}
		return b;
	}
}
