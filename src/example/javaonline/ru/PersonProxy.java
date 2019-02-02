package example.javaonline.ru;

public class PersonProxy implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

    public String data;
	public PersonProxy(PersonY original)
    {
        data = original.getFirstName() + "," + original.getLastName() + "," + String.valueOf(original.getAge());
        if (original.getSpouse() != null) {
            PersonY spouse = original.getSpouse();
            data = data + "," + spouse.getFirstName() + "," + spouse.getLastName() + "," + 
            		String.valueOf(spouse.getAge());
        }
    }
    
    private Object readResolve() throws java.io.ObjectStreamException
    {
        String[] pieces = data.split(",");
        PersonY result = new PersonY(pieces[0], pieces[1], Integer.parseInt(pieces[2]));
        if (pieces.length > 3) {
            result.setSpouse(new PersonY(pieces[3], pieces[4], Integer.parseInt(pieces[5])));
            result.getSpouse().setSpouse(result);
        }
        return result;
    }
	@Override
    public String toString() {
        return "[PersonProxy: data = " + data + "]";
    }
}
