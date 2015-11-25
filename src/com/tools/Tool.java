package com.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sql.SqlModel;

public class Tool {
	
	public static boolean modifyGradeId(String grade)
	{
		String sql="update gradeinfo set id='0' where id='1'";
		SqlModel.updateInfo(sql);
		
		String cgSql="update gradeinfo set id='1' where grade='"+grade+"'";
		boolean b=SqlModel.updateInfo(cgSql);
		
		return b;
	}
	
	/**
	 * ���ص�ǰ�꼶
	 * @return
	 */
	public static String getGrade(String userId)
	{
		String sql=null;
		if(userId.equals("admin")||userId.equals("5237")){
			sql="select grade from gradeinfo where id='1'";
		}else{
			sql="select grade from userinfo where id='"+userId+"'";
		}
		
		return SqlModel.getInfo(sql);
	}
	
	/**
	 * ˭�޸ĵ�
	 * @param userId
	 * @return
	 */
	public static String whoModify(String userId)
	{
	    SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    
	    String m=sdformat.format(new Date())+"   "+userId;
	    return m;
	}
	
	/**
	 * ��һ��string ת�� UTF8��ʽ��string
	 * @param content
	 * @return
	 */
	public static String string2UTF8(String content)
	{
		String str=null;
		if(content==null){
			return null;
		}
		try {
			str = URLEncoder.encode(content,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * ��һ�� UTF8 ������string
	 * @param content
	 * @return
	 */
	public static String UTF82String(String content)
	{
		String str=null;
		if(content==null){
			return null;
		}
		try {
			str=URLDecoder.decode(content,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return str;
	}

}
