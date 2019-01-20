package jp.ac.uryukyu.ie.e185747;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class JarLoad {
    private static final String MESSAGES_DIR = "image/";

    public static void main(String[] args) throws IOException {
        loadMessages();
    }

    private static void loadMessages() {
        ClassLoader classLoader = JarLoad.class.getClassLoader();
        URL url = classLoader.getResource(MESSAGES_DIR);
        if (url == null) {
            // 例外処理
        } else {
            if (url.getProtocol().equals("jar")) {
                // 定義ファイルがJAR内の場合
                String[] s = url.getPath().split(":");
                String path = s[s.length - 1].split("!")[0];
                File f = new File(path);
                JarFile jarFile;
                try {
                    jarFile = new JarFile(f);
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        if (name != null && name.startsWith(MESSAGES_DIR)) {
                            try (InputStream is = classLoader.getResourceAsStream(name);) {
                                read(is);
                            }
                        }
                    }
                } catch (IOException ex) {
                    //例外処理
                }
            }
        }
    }
            private static void read(InputStream is) {
                try (InputStreamReader isr = new InputStreamReader(is);
                     BufferedReader br = new BufferedReader(isr);) {
                    // 読み込み処理はちゃんと書く。。。
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException ex) {
                    // 例外処理
                }
            }
}
