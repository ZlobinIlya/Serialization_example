package example.javaonline.ru;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectInputValidation;
import java.io.ObjectOutputStream;
import java.io.Serializable;

enum Gender {
	Male, Female
}
public class Person implements Serializable, ObjectInputValidation
{
	private static final long serialVersionUID = 1L;
	
	private String  firstName;
	private String  lastName;
	private int     age;
	private Person spouse;
	private Gender  gender;

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.age = age;
    }

    public String getFirstName() { 
        return firstName;
    }
    public void setFirstName(String value) {
        firstName = value;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String value) { 
        lastName = value;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int value) {
        age = value;
    }
	private void writeObject(ObjectOutputStream stream) throws IOException
	{
		// "Криптование"/скрытие истинного значения
		age = age << 2;
		stream.defaultWriteObject();
	}

	private void readObject(ObjectInputStream stream)
			throws IOException, ClassNotFoundException
	{
		stream.defaultReadObject();
		// "Декриптование"/восстановление истинного значения
		age = age >> 2;
	}
    public Person getSpouse() {
        return spouse;
    }
    public void setSpouse(Person value) {
        spouse = value;
    }

    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
    	this.gender = gender;
    }

	@Override
	public void validateObject() throws InvalidObjectException
	{
		if ((age < 39) || (age > 60))
			throw new InvalidObjectException("Invalid age");
	}
	
	@Override
    public String toString() {
        return "[Person: firstName = " + firstName + 
               ", lastName = " + lastName +
               ", gender = " + gender +
               ", age = " + age +
               ", spouse = " + spouse.getFirstName() + "]";
    }
}
