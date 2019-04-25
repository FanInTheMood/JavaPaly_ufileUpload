package xyz.guhong.play.ufile.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tool {
	
	
	/**
	 * 根据指定的格式将Date抓换成String
	 * @param date
	 * @param format
	 * @return
	 */
	public static String toDate(Date date,String format){
		if("".equals(format) || format == null){
			format = "yyyy-MM-dd";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(date);
		return dateString;
	}
	
	/**
	 * 将日期转换为yyyy-MM-dd格式的字符串
	 * @param date
	 * @return
	 */
	public static String toDate(Date date){
		return toDate(date, "");
	}

	/**
	 * 生成指定位数的随机数
	 * 
	 * @param count 需要的随机数位数，如果count小于等于0则会报错。
	 * @return 如果count等于8则返回格式化后的当前日期。大于8以上返回8位数的日期+（count-8）的随机数
	 */
	public static Long getRandom(int count){
		if(count <= 0){
			throw new RuntimeException("count 必须大于 0");
		}
		if(count <= 7){
			return createRandom(count);
		}else if(count == 8){
			String[] nums = toDate(new Date(), "").split("-");
			String result="";
			for(String s : nums){
				result +=s;
			}
			return Long.parseLong(result);
		}else{
			count = count - 8;
			String[] nums = toDate(new Date(), "").split("-");
			String result="";
			for(String s : nums){
				result +=s;
			}
			return Long.parseLong(result+createRandom(count));
		}
		
	}
	
	private static Long createRandom(int count){
	    Long random = 0L;
	    do{
            String lengthStr = "1";
            for(int i = 0 ; i < count ; i++){
                lengthStr += "0";
            }
            int length = Integer.parseInt(lengthStr);
            random =(long)(Math.random()*length);
        }while (random.toString().length() < count);


		return random;
	}
	
}
