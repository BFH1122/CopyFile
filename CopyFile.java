import java.io.File;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

public class CopyFile {

	public static HashMap<String,String> map = new HashMap<>();
	
	//保存读取文件的名字
	public static void ReadFile(String pathname) throws IOException {
		File file = new File(pathname);
		BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = "";
		while((line = buf.readLine())!=null) {
			map.put(line,"1");
		}
		System.out.println("读物目录完成");
	}
	
	
    public static void main(String args[]) throws IOException{
        
    	String path = "D:\\test\\mulu.txt";
    	ReadFile(path);
    	copy("D:/workspace17","D:/result");
        System.out.println("复制完成!");    //提示复制完成
    }
    //oldpath代表的是源文件所在的跟目录夹。内部可以包含文件夹
    //newpath代表的是将拷贝的文件存储的位置
    //作用是检查用户的输入是否合法
    private static void copy(String oldpath,String newpath) throws IOException {    
        
    	File ofile=new File(oldpath);    
        if((!ofile.exists()||!ofile.isDirectory())){    //判断源文件夹路径是否存在
        	System.out.println("输入的源文件夹路径不存在,请重新输入！(输入end退出程序)");
        	return ;
        }
       
        File nfile=new File(newpath);
        if(!nfile.isAbsolute()){    //判断目标文件夹路径是否为目录
        	System.out.println("输出的文件夹有错误");
        }
        //截取源文件夹路径最后的名字
        String laststr  = oldpath.substring(oldpath.lastIndexOf("/"), oldpath.length());
        copyDirectiory(oldpath,newpath+"/"+laststr);  //将原路径文件夹名称和目标路径文件夹名称传递给复制文件夹函数
    }
    
    //用缓冲流复制文件函数
    public static void copyFile(File sourceFile,File targetFile)   
            throws IOException{  
                    // 新建文件输入流并对它进行缓冲   
                    FileInputStream input = new FileInputStream(sourceFile);  
                    BufferedInputStream inBuff=new BufferedInputStream(input);  
              
                    // 新建文件输出流并对它进行缓冲   
                    FileOutputStream output = new FileOutputStream(targetFile);  
                    BufferedOutputStream outBuff=new BufferedOutputStream(output);  
                      
                    int len;  
                    while ((len =inBuff.read()) != -1) 
                    {  
                        outBuff.write(len);  
                    }  
                    // 刷新此缓冲的输出流   
                    outBuff.flush();  
                      
                    //关闭流   
                    inBuff.close();  
                    outBuff.close();  
                    output.close();  
                    input.close();  
                }  
    
    
    // 复制文件夹函数
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {                             
                        
                        // 获取源文件夹下的文件或目录   
                        File oldfile=new File(sourceDir);
                        File[] file=oldfile.listFiles();  
        
                        for(int i=0;i<file.length;i++)
                        {  
                            
                            if (file[i].isFile()&&map.containsKey(file[i].getName())) //如果是文件，传递给copyFile()函数进行复制
                                {   
                                    //目标文件  
                                    File aim=new File(targetDir);
                                    if(!(aim).exists()){    //查看目录是否存在，不存在则新建
                                         aim.mkdirs();   
                                    }
                                    File targetFile=new File(aim.getAbsolutePath()+"/"+file[i].getName());  
                                    copyFile(file[i],targetFile);
                                    System.out.println("完成文件"+file[i].getName()+"的拷贝");
                                }  
                            if (file[i].isDirectory()) //如果是文件夹，则递归调用
                                {  
                                    // 要递归复制的源文件夹   
                                    String soursefiles=sourceDir + "/" + file[i].getName(); 
                                    
                                    // 要递归复制的目标文件夹   
                                    String aimfiles=targetDir + "/"+ file[i].getName();
                                    
                                    copyDirectiory(soursefiles, aimfiles);  
                                }  
                        }  
                }  
        }