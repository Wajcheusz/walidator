/**
 * Created by E6420 on 2017-03-23.
 */
public class Analyze {
    public static final int MALPA = 1;
    public static final int END = 2;
    int lineNumber = 1;
    int charNumber = 0;
    int biezacyToken;
    String sparsowany;
    String tekst;

    public void parse(){
        System.out.println("Rozpoczynam parsowanie");
        takeLeksem(biezacyToken);
        biezacyToken = 0;
        start();
        takeLeksem(END);
        System.out.println("*********** PLIK XML JEST POPRAWNY **************");
    }

    private void takeLeksem(int idTokena) {
//        System.out.println("chce pobrac token = " + idTokena);
        sparsowany = tekst.substring(0, charNumber);
//        System.out.println("sparsowany = " + sparsowany);

//        System.out.println("ostatniSparsowanyToken = " + ostatniSparsowanyToken);

        if (biezacyToken == idTokena) {
            biezacyToken = getSingle();
//            System.out.println("biezacy leks po pobraniu = " + biezacy_token);
        } else {
            wrong(idTokena);
        }
    }

    private int getSingle(){
        switch(tekst.charAt(charNumber)):

    }
}
