package edu.bit.hjs;

import java.io.*;

/**
 * 自定义文件类加载器
 */
//1.实现自定义类加载器，需要继承ClassLoader
public class FileSystemClassLoader extends ClassLoader {
    //2.定义类加载器的加载路径
    private String rootDir;

    public FileSystemClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }
    //3.重写findClass方法
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //调用父类的findLoadedClass方法，判断是否已经被加载过
        Class<?> clazz = super.findLoadedClass(name);
        if (clazz!=null) {
            return  clazz;
        }
        else{
            //得到父类加载器
            ClassLoader parent = this.getParent();
            try {
                clazz = parent.loadClass(name);
            }catch (Exception e){
                e.printStackTrace();
            }
            if (clazz!=null) {
                return clazz;
            }
            else{
                //得到本类的字节码文件
                byte[] classData = getClassData(name);
                if (classData==null)
                    throw new ClassNotFoundException();
                else {
                    clazz = defineClass(name, classData, 0, classData.length);
                    return clazz;
                }
            }
        }
    }
    private byte[] getClassData(String className){
        String path = rootDir+"/"+className.replace('.','/')+".class";
        InputStream in = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
             in = new FileInputStream(path);
             //定义字节数组用来接收从文件流中读到的流
             byte[] buffer = new byte[1024];
             int length = 0;
             while((length=in.read(buffer))!=-1)
                 baos.write(buffer,0,length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }
}
