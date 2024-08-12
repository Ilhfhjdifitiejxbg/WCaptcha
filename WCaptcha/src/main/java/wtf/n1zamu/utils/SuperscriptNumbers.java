package wtf.n1zamu.utils;

public class SuperscriptNumbers {
    public static String convertNumber(int numeric) {
        StringBuilder superscriptString = new StringBuilder();
        for (char c : String.valueOf(numeric).toCharArray()) {
            int numericValue = Character.getNumericValue(c);
            switch (numericValue) {
                case 1:
                    superscriptString.append("¹");
                    break;
                case 2:
                    superscriptString.append("²");
                    break;
                case 3:
                    superscriptString.append("³");
                    break;
                default:
                    superscriptString.append((char) ('\u2070' + numericValue));
            }
        }
        return superscriptString.toString();
    }
}