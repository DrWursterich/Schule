package Fussball;

public abstract class Person {
	protected String vorname;
	protected String name;
	
	public Person(String vorname, String name) {
		this.vorname = vorname;
		this.name = name;
	}
	 
	public String getName() {
		return this.name;
	}

	public String erzeugenZeile() {
		return String.format("%1s\t%20s%20s\t%8.1f\t%s", this.getTyp(), this.name,
				this.vorname,this.ermittelnGehalt(), this.erzeugenDetail());
	}
	
	public String getVorname() {
		return this.vorname;
	}

	public abstract void setGehalt(double gehalt);
	
	public abstract String getTyp();
	
	public abstract double ermittelnGehalt();
	
	public abstract String erzeugenDetail();
}