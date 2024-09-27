package io.xjar.boot;

import io.xjar.XConstants;
import io.xjar.XDecryptor;
import io.xjar.XEncryptor;
import io.xjar.key.XKey;
import org.springframework.boot.loader.net.protocol.jar.Handler;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 加密的URL处理器
 *
 * @author Payne 646742615@qq.com
 * 2018/11/24 13:19
 */
public class XBootURLHandler extends Handler implements XConstants {
    private final XDecryptor xDecryptor;
    private final XEncryptor xEncryptor;
    private final XKey xKey;
    private final Set<String> indexes;

    public XBootURLHandler(XDecryptor xDecryptor, XEncryptor xEncryptor, XKey xKey, ClassLoader classLoader) throws Exception {
        this.xDecryptor = xDecryptor;
        this.xEncryptor = xEncryptor;
        this.xKey = xKey;
        this.indexes = new LinkedHashSet<>();
        Enumeration<URL> resources = classLoader.getResources(XJAR_INF_DIR + XJAR_INF_IDX);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String url = resource.toString();
            String classpath = url.substring(0, url.lastIndexOf("!/") + 2);
            InputStream in = resource.openStream();
            InputStreamReader isr = new InputStreamReader(in);
            LineNumberReader lnr = new LineNumberReader(isr);
            String name;
            while ((name = lnr.readLine()) != null) indexes.add(classpath + name);
        }
    }

    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        if(url.toString().contains("!/com/example/demo/")) {
//            File tmp = new File("./test.txt");
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(tmp));
//            outputStreamWriter.write(indexes.toString());
//            outputStreamWriter.close();
            //System.out.println("资源集合 %s".formatted(indexes.toString()));

        }
        URLConnection urlConnection = super.openConnection(url);
        URLConnection res = indexes.toString().contains(url.toString())
                && urlConnection instanceof JarURLConnection
                ? new XBootURLConnection((JarURLConnection) urlConnection, xDecryptor, xEncryptor, xKey)
                : urlConnection;
        //System.out.println("%s, %s 线程 %s, %s 打开资源 %s".formatted( urlConnection instanceof JarURLConnection, System.currentTimeMillis(), Thread.currentThread().getId()+ "",indexes.toString().contains(url.toString()), url));

        return res;
    }

}
