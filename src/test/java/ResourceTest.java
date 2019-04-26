import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class ResourceTest {
    @Test
    public void test() throws IOException {
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("cn/edu/bupt/bean");
        while(urls.hasMoreElements()){

           URL url = urls.nextElement();
           if(url.getProtocol().equals("file"))
           {

               File[] files = new File(url.getPath()).listFiles(new FileFilter() {
                   public boolean accept(File file) {

                        return (file.isDirectory()||file.getName().endsWith(".class"));

                   }
               });

            for(File file:files){

                handleFodler(file);
            }


           }else if(url.getProtocol().equals("jar")){



           }
        }
    }
    @Test
    public void handleJar() throws IOException {

        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("cn/edu/bupt/bean");

    }


    public void handleFodler(File file){

        if(file.isDirectory())
        {

            File[] subFiles = file.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    return (pathname.isDirectory()||pathname.getName().endsWith(".class"));
                }
            });

            for(File subFile:subFiles){
                handleFodler(subFile);
            }

        }else{

            System.out.println(file.getName());

        }
    }


}
