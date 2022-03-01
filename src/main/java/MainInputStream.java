import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainInputStream {


    public static void main(String[] args) throws IOException {
        int count = 0;
        ArrayList<String> arrayList = new ArrayList<>();
        savePage("http://shgpi.edu.ru/ssylki/ssylki-na-ehlektronnye-biblioteki/", arrayList);
        BufferedWriter index = new BufferedWriter(new FileWriter("D:/прог/java/klayster/index.txt"));
        for (String s : arrayList) {
            if (downloadPage("file" + arrayList.indexOf(s), s, index)) count++;
            if (count > 120) break;
        }
        index.close();
    }


    public static boolean downloadPage(String name, String link, BufferedWriter index) {
        try {
            URL url = new URL(link);
            InputStream inputStream = url.openStream();
            BufferedWriter writer = new BufferedWriter(new FileWriter("D:/прог/java/klayster/files/" + name + ".txt"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String s;
            while (reader.ready()) {
                s = reader.readLine();
                writer.write(s);
            }
            index.write(name + ".txt " + link + "\n");
            writer.close();
            inputStream.close();
            reader.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void savePage(String link, ArrayList<String> arrayList) {
        try {
            URL url = new URL(link);
            InputStream inputStream = url.openStream();
            Pattern p = Pattern.compile("http://[^ \"]*");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String str;
            int i = 0;
            Matcher m;
            while (reader.ready()) {
                str = reader.readLine();
                m = p.matcher(str);
                if (m.find()) {
                    arrayList.add(m.group());
                    System.out.println(i + "     " + m.group());
                    i++;
                }
            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}