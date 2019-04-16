package cert.cert;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.bouncycastle.cms.CMSException;

import oracle.jdbc.OracleConnection;

public class FileChecker {

	public String fileChk(String path,OracleConnection connection,String filename) throws IOException, CMSException, SQLException{
		DataInputStream in = null;
		File f;
		CertChk crt = new CertChk();
		String firstcrt = null;
		String seccrt = null;
		try {
		f = new File(path+".p7s");
		byte[] buffer = new byte[(int) f.length()];
		in = new DataInputStream(new FileInputStream(f));
		 in.readFully(buffer);
		   in.close();
	    firstcrt = crt.certChk(buffer,connection,1,filename);
	
		if(firstcrt!="")
			return firstcrt;
		
	}catch(FileNotFoundException e) {

		return "IOC-006:Помилка - відсутність фуйлу підпису" ;
	}
		
	try {	
		f = new File(path+".p7s.p7s");
		byte[] buffer2 = new byte[(int) f.length()];
		DataInputStream in2 = new DataInputStream(new FileInputStream(f));
		   in2.readFully(buffer2);
		   in2.close();
		 seccrt = crt.certChk(buffer2,connection,2,filename);
		if(seccrt!="")
			return seccrt;
		
	}catch(FileNotFoundException e) {
		
		return "IOC-006:Помилка - відсутність фуйлу підпису ";
	}
		
			return "";
		
	}
}
