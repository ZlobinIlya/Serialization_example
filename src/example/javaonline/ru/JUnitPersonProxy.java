import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import example.PersonProxy;
import example.PersonY;

import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class JUnitPersonProxy
{
	private  static  final  String  FILE_proxy = "proxy.ser";
	private  static  final  String  FNAME_Alex = "Алексей" ;
	private  static  final  String  FNAME_Olga = "Ольга"   ;
	private  static  final  String  LAST_NAME  = "Иванов"  ;
	private  static  final  int     AGE_Alex   = 39        ;
	
	private  static         PersonY alex       = null      ;
	private  static         PersonY olga       = null      ;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			alex = new PersonY(FNAME_Alex, LAST_NAME, AGE_Alex);
			olga = new PersonY(FNAME_Olga, LAST_NAME, 38);

			alex.setSpouse(olga);
			olga.setSpouse(alex);
			
			// Сохранение сериализованных прокси-объектов
			PersonProxy proxy_alex = new PersonProxy(alex);
			PersonProxy proxy_olga = new PersonProxy(olga);

			FileOutputStream fos = new FileOutputStream(FILE_proxy);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(proxy_alex);
			oos.writeObject(proxy_olga);
			oos.close();
//			fail (proxy_alex.toString());
		} catch (Exception e) {
			fail("Exception thrown during test: " + e.toString());
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
    	// Удаление файла
		new File(FILE_proxy).delete();
	}
	@Test
	public void testProxy()
	{
        try {
        	FileInputStream fis = new FileInputStream(FILE_proxy);
        	ObjectInputStream ois = new ObjectInputStream(fis);
        	PersonY alex = (PersonY) ois.readObject();
        	PersonY olga = (PersonY) ois.readObject();
        	ois.close();
	            
        	assertEquals(alex.getFirstName(), FNAME_Alex);
        	assertEquals(alex.getLastName() , LAST_NAME);
        	assertEquals(olga.getFirstName(), FNAME_Olga);
        	assertEquals(olga.getFirstName(), FNAME_Olga);
        	assertEquals(alex.getAge()      , AGE_Alex);
        	assertEquals(alex.getSpouse().getFirstName(), FNAME_Olga);
        	// Описание объекта
//        	fail(alex.toString());
        } catch (Exception e) {
        	fail("Exception thrown during test: " + e.toString());
        }	
	}
}
