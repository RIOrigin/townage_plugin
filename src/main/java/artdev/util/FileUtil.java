package artdev.util;

import java.io.*;

public class FileUtil {

    public static void copy(Reader in, Writer out) throws IOException {
        int c = -1;
        while((c = in.read()) != -1) {
            out.write(c);
        }
    }

    public static String readFile(File file) throws IOException {
        if (file != null && file.canRead()){
            Reader in = new FileReader(file);
            StringWriter out = new StringWriter();
            copy(in,out);
            in.close();
            return out.toString();
        }
        return "";
    }

    public static void saveFile(File file,String content) throws IOException {
        Writer writer = new FileWriter(file);
        writer.write(content);
        writer.close();
    }
}
