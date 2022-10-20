package sql.request;

public class InsertGenerator {

    /**
     * Method creates an INSERT SQL request, which inserts rows into the T_Group Selected table
     * @param group choose students which relate to that group
     * @param nDebts The number of people's debts should exceed this value
     * @return request
     */
    public static String InsertGenerate(String group, int nDebts) {
        StringBuilder strB = new StringBuilder();
        strB.append("INSERT INTO T_GroupSelected\n")
                .append("(id_Student, firstName, lastName, id_Group)\n")
                .append("VALUES\n")
                .append("id_Student, firstName, lastName, id_Group\n")
                .append("WHERE " + "id_Group = '")
                .append(group).append("' AND dolgCount > ")
                .append(nDebts)
                .append(";");
        return new String(strB);
    }
}
