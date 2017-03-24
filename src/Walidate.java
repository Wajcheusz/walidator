import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by E6420 on 2017-03-19.
 */
public class Walidate {

    public static void main(String[] args) throws IOException {
        String tekst = readFile();
        Analyze a = new Analyze(tekst);
        a.parse();
    }

    public static String readFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        String oneLine;
        FileReader fileReader = new FileReader("test.less");
        BufferedReader br = new BufferedReader(fileReader);

        while ((oneLine = br.readLine()) != null){
            sb.append(oneLine).append("\n");
        }
        br.close();

        return sb.toString();
    }
}
