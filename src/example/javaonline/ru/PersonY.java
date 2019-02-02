import java.io.Serializable;

public class PersonY implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String  firstName;
	private String  lastName;
	private int     age;
	private PersonY spouse;

    public PersonY(String firstName, String lastName, int age) {
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
    public PersonY getSpouse() {
        return spouse;
    }
    public void setSpouse(PersonY value) {
        spouse = value;
    }

	private Object writeReplace() throws java.io.ObjectStreamException
	{
		return new PersonProxy(this);
	}
	@Override
    public String toString() {
        return "[Person: firstName = " + firstName + 
               ", lastName = " + lastName +
               ", age = " + age +
               ", spouse = " + spouse.getFirstName() + "]";
    }
}

