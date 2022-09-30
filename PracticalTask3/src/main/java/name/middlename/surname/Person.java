package name.middlename.surname;

public class Person {
    private final String lastName;
    private final String name;
    private final String middleName;

    public Person(String lastName, String name, String middleName) {
        this.lastName = lastName;
        this.name = name;
        this.middleName = middleName;
    }

    public Person(String lastName, String name) {
        this.lastName = lastName;
        this.name = name;
        this.middleName = null;
    }

    public Person(String lastName) {
        this.lastName = lastName;
        this.name = null;
        this.middleName = null;
    }


    /**
     * Method to correctly return name attributes
     * @return Person's name, surname and lastname in correct format
     */
    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder();
        strB.append(lastName);

        if (name != null) {
            strB.append(" ")
                    .append(name.charAt(0))
                    .append(".");
            if (middleName != null) {
                strB.append(" ")
                        .append(middleName.charAt(0))
                        .append(".");
            }
        }
        return new String(strB);
    }

    /**
     * Version of necessary method, not using StringBuilder
     * @return Person's name, surname and lastname in correct format
     */
    public String slowToString() {
        String str = "";
        str += lastName;

        if (name != null) {
            str += " " + name.charAt(0) + ".";

            if (middleName != null) {
                str += " " + middleName.charAt(0) + ".";
            }
        }
        return str;
    }
}
