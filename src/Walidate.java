/**
 * Created by E6420 on 2017-03-19.
 */
public class Walidate {
    public static final String TEKST = "@base:#f04615;\n" +
            "\n" +
            "@width:0.5; .class { \n" +
            "width: percentage(@width); // returns `50%`\n" +
            "color: saturate(@base, 5%); background-color: spin(lighten(@base, 25%), 8); \n" +
            "}\n" +
            "\n" +
            "/* One hell of a block style comment! */\n" +
            "@var: red; // Get in line! \n" +
            "\n" +
            "@var: white;\uFEFF";

    public static void main(String[] args) {
        String tekst=TEKST;
        System.out.println(tekst);
        System.out.println("\n---------------------------------------------\n");
        tekst = removeOneComment(tekst);
        tekst = removeBlockComment(tekst);
        System.out.println(tekst);
    }

    public static String removeOneComment(String tekst) {
        StringBuilder sb = new StringBuilder(tekst);
        int firstIndex = sb.indexOf("//");
        if (firstIndex == -1) {
            return sb.toString();
        }
        int lastIndex = sb.indexOf("\n", firstIndex);
        sb.delete(firstIndex, lastIndex);
        return removeOneComment(sb.toString());
    }

    public static String removeBlockComment(String tekst){
        StringBuilder sb = new StringBuilder(tekst);
        int firstIndex = sb.indexOf("/*");
        if (firstIndex == -1) {
            return sb.toString();
        }
        int lastIndex = sb.indexOf("*/");
        sb.delete(firstIndex, lastIndex+2);
        return removeBlockComment(sb.toString());
    }
}