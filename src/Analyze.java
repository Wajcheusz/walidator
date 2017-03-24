import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by E6420 on 2017-03-23.
 */
public class Analyze {
    public static final int MALPA = 1;
    public static final int COL = 2;
    public static final int SEMICOL = 3;
    public static final int LNAWIAS = 4;
    public static final int RNAWIAS = 5;
    public static final int POINT = 6;
    public static final int LBRAC = 7;
    public static final int RBRAC = 8;
    public static final int COM = 9;
    public static final int EPS = 10;
    public static final int ZNAK = 11;

    int lineNumber = 1;
    int charNumber = 0;
    int biezacyToken;
    String sparsowany;
    String tekst;
    //char x = Pattern.compile("[A-Za-z0-9\\-]+");
    Pattern pattern = Pattern.compile("[A-Za-z0-9#]+");

    public Analyze(String tekst) {
        this.tekst = tekst;
        this.tekst = removeOneComment(this.tekst);
        this.tekst = removeBlockComment(this.tekst);
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

    public static String removeBlockComment(String tekst) {
        StringBuilder sb = new StringBuilder(tekst);
        int firstIndex = sb.indexOf("/*");
        if (firstIndex == -1) {
            return sb.toString();
        }
        int lastIndex = sb.indexOf("*/");
        sb.delete(firstIndex, lastIndex + 2);
        return removeBlockComment(sb.toString());
//        tekst = tekst.replaceAll("(?s)/*.*?*/")
    }

    public void parse() {
        biezacyToken = 0;
        System.out.println("Rozpoczynam parsowanie");
        takeLeksem(biezacyToken);
        start();
        takeLeksem(EPS);
        System.out.println("*********** PLIK XML JEST POPRAWNY **************");
    }

    private void start() {
        wyrazenia();
    }

    private void wyrazenie() {
        if (biezacyToken == MALPA) {
            takeLeksem(MALPA);
            znaki();
//            if (biezacyToken == COL) {
            takeLeksem(COL);
            znaki();
//                if (biezacyToken == SEMICOL) {
            takeLeksem(SEMICOL);
//                } //else wrong(biezacyToken, lineNumber);
//            } //else wrong(biezacyToken, lineNumber);
        } else if (biezacyToken == POINT) {
            takeLeksem(POINT);
            znaki();
//            if (biezacyToken==LBRAC){
            takeLeksem(LBRAC);
            funkcja();
//                if(biezacyToken==SEMICOL){
            //takeLeksem(SEMICOL);
            funkcje();
//                    if(biezacyToken==RBRAC){
            takeLeksem(RBRAC);
            koniec();
//                    }
//                }
//            }
//        } else if (biezacyToken==COL){
//            takeLeksem(COL);
//            znaki();
//        }
//        else wrong(biezacyToken, lineNumber);
        }
    }

    private void funkcja() {
        znaki();
//        if(biezacyToken==COL){
        takeLeksem(COL);
        znaki();
//            if(biezacyToken==LNAWIAS){
        takeLeksem(LNAWIAS);
        args();
//                if(biezacyToken==SEMICOL){
        takeLeksem(RNAWIAS);
        takeLeksem(SEMICOL);
//                }
//            }
//        }
    }

    private void args() {
        znaki();
        if (biezacyToken == COM) {
            takeLeksem(COM);
            args();
        }
        if (biezacyToken == COL) {
            funkcje();
        }
        //args();
    }

    private void funkcje() {
        if (biezacyToken != RBRAC) {
            funkcja();
            takeLeksem(SEMICOL);
            funkcje();
        }
        //lub eps
    }

    // private void arg()

    private void wyrazenia() {
        if (biezacyToken != EPS) {
            wyrazenie();
            wyrazenia();
        } else {
            koniec();
        }
    }

    private void znaki() {
        //takeLeksem(MALPA);
        if (biezacyToken == ZNAK) {
            znak();
            znaki();
        } else znak();
//        if(biezacyToken==ZNAK){
//            takeLeksem(ZNAK);
//        } else if (biezacyToken==COL){
//            takeLeksem(COL);
//        } else if (biezacyToken==ZNAK)
//            takeLeksem(ZNAK);
//        else wrong(2,2);
    }

    private void znak() {
        if (biezacyToken == ZNAK) {
            takeLeksem(ZNAK);
        }
    }


    private void takeLeksem(int idTokena) {
//        System.out.println("chce pobrac token = " + idTokena);
        //sparsowany = tekst.substring(0, charNumber);
//        System.out.println("sparsowany = " + sparsowany);

//        System.out.println("ostatniSparsowanyToken = " + ostatniSparsowanyToken);
        System.out.println("pobieram leksem: " + charNumber + " o numerze: " + idTokena);

        if (biezacyToken == idTokena) {
            biezacyToken = getSingle();
//            System.out.println("biezacy leks po pobraniu = " + biezacy_token);
        } else {
            wrong(idTokena, lineNumber);
            return;
        }
    }

    private int getSingle() {
        while (charNumber < tekst.length()) {
            switch (tekst.charAt(charNumber)) {
                case '\n':
                    charNumber += 1;
                    lineNumber += 1;
                    break;
                case ' ':
                    charNumber += 1;
                    break;
                case '@':
                    charNumber += 1;
                    return MALPA;
                case ':':
                    charNumber += 1;
                    return COL;
                case ';':
                    charNumber += 1;
                    return SEMICOL;
                case '(':
                    charNumber += 1;
                    return LNAWIAS;
                case ')':
                    charNumber += 1;
                    return RNAWIAS;
                case '.':
                    charNumber += 1;
                    return POINT;
                case '{':
                    charNumber += 1;
                    return LBRAC;
                case '}':
                    charNumber += 1;
                    return RBRAC;
                case ',':
                    charNumber += 1;
                    return COM;
                default:
                    //Matcher m;
                    //m = pattern.matcher(String.valueOf(tekst.charAt(charNumber)));
                    //if(m)
                    charNumber += 1;
                    return ZNAK;
            }
        }
        return EPS;
    }

    private void wrong(int idTokena, int lineNumber) {
        System.out.println("something went wrong" + idTokena + " " + lineNumber);
    }

    private void koniec() {
        System.out.println("KONIEC");
    }
}
