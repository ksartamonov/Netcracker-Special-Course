package shirts.array;

import java.util.StringTokenizer;

public class Shirt {
    private final String id;
    private final String description;
    private final String color;
    private final String size;


    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder()
                .append("id = '")
                .append(id)
                .append("'\n")
                .append("description = '")
                .append(description)
                .append("'\n")
                .append("color = '")
                .append(color)
                .append("'\n")
                .append("size = '")
                .append(size)
                .append("'\n");
        return new String(strB);
    }

    /**
     * Coverts string with shirt info into Shirt
     * @param info String containing information about shirt
     * @return Shirt
     */
    public Shirt(String info) {
        StringTokenizer st = new StringTokenizer(info, ",");

        if (st.countTokens() != 4) {
            throw new IllegalArgumentException("[exception] String must contain all 4 shirt attributes.");
        }
        id = st.nextToken().trim();
        description = st.nextToken().trim();
        color = st.nextToken().trim();
        size = st.nextToken().trim();
    }
}
