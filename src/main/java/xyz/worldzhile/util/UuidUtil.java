package xyz.worldzhile.util;




import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 产生UUID随机字符串工具类
 */
public final class UuidUtil {
	private UuidUtil(){}

	/**
	 * 获取32位uuid
	 * @return
	 */
	public static String getUuid(){

		return UUID.randomUUID().toString().replace("-","");
	}

	/**
	 * 随机生成4位验证码
	 * @return
	 */
	public static String getCheckCode() {
		String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		int size = base.length();
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i=1;i<=4;i++){
			//产生0到size-1的随机值
			int index = r.nextInt(size);
			//在base字符串中获取下标为index的字符
			char c = base.charAt(index);
			//将c放入到StringBuffer中去
			sb.append(c);
		}
		return sb.toString();
	}


	public static void main(String[] args) {
//		long time = new java.util.Date().getTime();
//		Date date = new Date(time);
//		System.out.println(date.toString());
//		System.out.println(date.getHours());


//		Date date = new Date();
//		System.out.println(DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss" ,date));
//
//		java.sql.Date sQL = new java.sql.Date(date.getTime());
//
//		System.out.println(sQL.getHours());

//		Timestamp a=new Timestamp(1111);
//
//		System.out.println(a);

		Date date = new java.util.Date();//获取当前时间对象，也可以直接传入Date的对象

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		String time= sdf.format(date);//获取格式化日期，带有时分
		System.out.println(time);
	}


}
