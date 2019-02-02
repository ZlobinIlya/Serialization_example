import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.security.KeyPair;
import java.security.PublicKey;
import java.security.Signature;
import java.security.PrivateKey;
import java.security.SignedObject;
import java.security.KeyPairGenerator;
import java.security.SignatureException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import example.Person;

public class JUnitPerson
{
	private  static  final  String  FILE_data  = "data.ser";
	private  static  final  String  FNAME_Alex = "Алексей" ;
	private  static  final  String  FNAME_Olga = "Ольга"   ;
	private  static  final  String  LAST_NAME  = "Иванов"  ;
	private  static  final  int     AGE_Alex   = 39        ;
	
	private  static         Person  alex       = null      ;
	private  static         Person  olga       = null      ;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			alex = new Person(FNAME_Alex, LAST_NAME, AGE_Alex);
			olga = new Person(FNAME_Olga, LAST_NAME, 38);

			alex.setSpouse(olga);
			olga.setSpouse(alex);
			// Сохранение сериализованных объектов 
			FileOutputStream fos = new FileOutputStream(FILE_data);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(alex);
			oos.writeObject(olga);
			oos.close();
//			fail (alex.toString());
		} catch (Exception e) {
			fail("Exception thrown during test: " + e.toString());
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
    	// Удаление файла
		new File(FILE_data).delete();
	}
	@Test
	public void testSerialization()
	{
        try {
        	FileInputStream fis = new FileInputStream(FILE_data);
        	ObjectInputStream ois = new ObjectInputStream(fis);
        	Person alex = (Person) ois.readObject();
        	Person olga = (Person) ois.readObject();
        	ois.close();
        	// Проконтролировать значения объекта
        	// olga.validateObject();
	            
        	assertEquals(alex.getFirstName(), FNAME_Alex);
        	assertEquals(alex.getLastName() , LAST_NAME);
        	assertEquals(olga.getFirstName(), FNAME_Olga);
        	assertEquals(olga.getFirstName(), FNAME_Olga);
        	assertEquals(alex.getAge()      , AGE_Alex);
        	assertEquals(alex.getSpouse().getFirstName(), FNAME_Olga);
        	// Описание объекта
        	// fail(alex.toString());
        } catch (Exception e) {
        	fail("Exception thrown during test: " + e.toString());
        }	
	}
	@Test
	public void testSigning()
	{
	    try {
		    // Generate a 1024-bit Digital Signature Algorithm (DSA) key pair
		    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
		    keyPairGenerator.initialize(1024);
		    KeyPair    keyPair    = keyPairGenerator.genKeyPair();
		    PrivateKey privateKey = keyPair.getPrivate();
		    PublicKey  publicKey  = keyPair.getPublic();

		    // Подписывать можно только сериализуемый объект
		    String  unsignedObject = alex.toString();
		    
		    Signature    signature      = Signature.getInstance(privateKey.getAlgorithm());
		    SignedObject signedObject   = new SignedObject(unsignedObject, privateKey, signature);
		     
		    // Verify the signed object
		    signature = Signature.getInstance(publicKey.getAlgorithm());
		    boolean verified = signedObject.verify(publicKey, signature);
		 
		    assertTrue("Проверка 'подписанного' объекта", verified);
		    
		    // Retrieve the object
		    unsignedObject = (String) signedObject.getObject();
        	assertEquals("Проверка описания 'подписанного' объекта", unsignedObject, alex.toString());
		     
	    } catch (SignatureException e) {
	    } catch (InvalidKeyException e) {
	    } catch (NoSuchAlgorithmException e) {
	    } catch (ClassNotFoundException e) {
	    } catch (IOException e) {
	    	fail("Exception thrown during test: " + e.toString());
	    }
	}
}
