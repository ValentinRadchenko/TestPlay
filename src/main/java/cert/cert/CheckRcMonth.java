package cert.cert;

import java.sql.CallableStatement;
import java.sql.SQLException;

import oracle.jdbc.internal.OracleTypes;




public class CheckRcMonth {

	int month;
	int year;
	
	
	
	
	
	   public int checkDoubleMonth(String name) throws SQLException{
		
			
	    month= Integer.valueOf(name.substring(14,16));
	    year= Integer.valueOf(name.substring(15,19));
			
		CallableStatement callableStatement = Main.connection.prepareCall("{call SUBS_RST_2.File_Already_Exists_Month(?, ?, ?, ?)}");
	  	 
	  	 callableStatement.setString(1,name);
	     callableStatement.setInt(2,month);
	  	 callableStatement.setInt(3,year);
	  	 callableStatement.registerOutParameter(4, OracleTypes.INTEGER);
	  	  
	  	 callableStatement.executeUpdate();
	     int result =  (Integer) callableStatement.getObject(4);
		  return result;
	 
		}
	

	
}

