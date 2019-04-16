package cert.cert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CheckRcDate {
	
	public boolean checkRcDate(String date) throws ParseException {
		String repDate="01."+date.subSequence(13, 15)+"."+date.substring(15, 19);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
		cal.setTime(sdf.parse(repDate));// all done
		int dayFile = cal.get(Calendar.DAY_OF_MONTH);
		int monthFile = cal.get(Calendar.MONTH)+1;
		Date dateToday =new Date(); // your date
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToday);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);		
		
		if((month==monthFile)||(month-monthFile==1 && day <=5))
			return true;
		else
			return false;
	}

}
