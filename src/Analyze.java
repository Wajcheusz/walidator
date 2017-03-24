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
    int currentToken;
    String tekst;
    String pattern = "[A-Za-z0-9#%-]+";

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
    }

    public void parse() {
        currentToken = 0;
        System.out.println("ROZPOCZETO WALIDACJE");
        takeLeksem(currentToken);
        wyrazenia();
        takeLeksem(EPS);
        System.out.println("PLIK LESS JEST POPRAWNY");
    }

    private void wyrazenia() {
        if (currentToken != EPS) {
            wyrazenie();
            wyrazenia();
        }
    }

    private void wyrazenie() {
        if (currentToken == MALPA) {
            takeLeksem(MALPA);
            znaki();
            takeLeksem(COL);
            wartosc();
            takeLeksem(SEMICOL);
        } else if (currentToken == POINT) {
            takeLeksem(POINT);
            znaki();
            takeLeksem(LBRAC);
            funkcje();
            takeLeksem(RBRAC);
        }
    }

    private void znaki() {
        if (currentToken == ZNAK) {
            takeLeksem(ZNAK);
            znaki();
        }
    }

    private void wartosc() {
        znaki();
        if (currentToken == POINT) {
            takeLeksem(POINT);
        }
        znaki();
    }

    private void funkcje() {
        if (currentToken != RBRAC) {
            funkcja();
            funkcje();
        }
    }

    private void funkcja() {
        znaki();
        if (currentToken == COL) {
            takeLeksem(COL);
        }
        znaki();
        args();
    }

    private void args() {
        if (currentToken == LNAWIAS) {
            takeLeksem(LNAWIAS);
            wartosc();
            args();
        } else if (currentToken == COM) {
            takeLeksem(COM);
            wartosc();
            args();
        } else if (currentToken == MALPA) {
            takeLeksem(MALPA);
            znaki();
            args();
        } else if (currentToken == RNAWIAS) {
            takeLeksem(RNAWIAS);
            args();
        } else if (currentToken == SEMICOL) {
            takeLeksem(SEMICOL);
            funkcje();
        } else wrong(lineNumber);
    }

    private void takeLeksem(int idTokena) {
        //System.out.println("pobieram leksem: " + charNumber + " o numerze: " + idTokena + " biezacy token: " + currentToken);

        if (currentToken == idTokena) {
            currentToken = getSingle();
        } else {
            wrong(lineNumber);
            return;
        }
    }

    private int getSingle() {
        while (charNumber < tekst.length()) {
            char c = tekst.charAt(charNumber);
            switch (c) {
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
                    String s = String.valueOf(c);
                    if (s.matches(pattern)) {
                        charNumber += 1;
                        return ZNAK;
                    } else wrong(lineNumber);
            }
        }
        return EPS;
    }

    private void wrong(int lineNumber) {
        System.err.println("Linia numer " + lineNumber + " jest niepoprawna.");
        System.exit(0);
    }
}


