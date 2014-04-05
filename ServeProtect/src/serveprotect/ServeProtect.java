/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package serveprotect;

import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.*;

/**
 *
 * @author drevlen
 */
public class ServeProtect {

    /**
     * @param args the command line arguments
     */
    private static String userName; 
    private static String newUser; 
    private static boolean logged;
    
    public static void main(String[] args) {
        logged = false;
        newUser = "";
        userName = "";
//        lab0(args);
        lab1(args);
    }
    
    public static void lab1(String[] args) {
        while(dialog() != "_endSession")
        {
            
        }
    }
    
    public static void ban(String name) {
        
    }

    public static String registerName(String name) {
        if (!login(name).contains("error"))
        {
            newUser = name;
            return "Now type password";
        }
        else
        { 
            userName = "admin";
            return "already exist";
        }
    }

    public static String login(String pass) {
        String str = "No user with name " + userName + " found.\n";
        if (userName == "")
        {
            str = "Error. Set user name with 'name' userName\n";
            return str;
        }
        if (newUser != "")
        {
            RandomAccessFile file = null;
            try
            {
                file = new RandomAccessFile("users.txt","rw");
                file.seek(file.length() - 1);
                file.writeBytes("_name=" + newUser + " ");
                file.writeChars("_pass=" + pass + " ");
                file.writeChars("_userBlocked=false\n");
                file.close();
            }
            catch(Exception ioe)
            {
                System.out.println(ioe.toString());
            } 
            
            userName = newUser;
            logged = true;
            newUser = "";
        }
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("_name=" + userName);
                System.out.println(line);
                System.out.println(line.contains("_name=" + userName));
                System.out.println(line);
               if (line.contains("_name=" + userName))
               {
                   if (line.contains("_pass=" + pass + " ") && pass != "")
                   {
                       if (line.contains("_userBlocked=true"))
                           str = userName + " is baned.";
                       else str = "success";
                   }
                   else
                   {
                       str = "Error. Wrong Password.\n";
                   }
                }
            }
            br.close();
        }
        catch(Exception ioe)
        {
          System.out.println(ioe.toString());
        }  

        return str;
    }

    
    
    public static String response(String input) {
        String str = input;
        if (input == "register")
        {
            if (userName == "admin")
                str = "welcome admin\n Type 'name newName'";
            System.out.println(str);
            return str;
        }
        if (input.contains("_ban "))
        {
            if (userName == "admin")
                ban(input.replace("_ban ", ""));
            else
                str = "Error dont have admin rights please login.";
            return str;
        }
        if (input.contains("_name "))
        {
            if (userName == "admin")
                str = registerName(input.replace("_name ", ""));
            else
            {
                userName = input.replace("_name ", "");
                str = "Type 'password' and after that type your password.\n";
            }
            return str;
        }
        if (input.contains("_password "))
        {
            str = login(input.replace("_password ", ""));
        }
        if (input == "data")
        {
            return "Hello World";
        }

        if (input == "_endSession")
        {
            return "_endSession";
        }
                 

        return str;
    }
    
    public static String dialog() {
        try
        {
            PrintWriter log = new PrintWriter("results.txt", "UTF-8");
            ServerSocket ss;
            Socket s;
            InputStream is;
            OutputStream os;
            ss = new ServerSocket(7700);
            s = ss.accept();
            is = s.getInputStream();
            os = s.getOutputStream();
            
            String str;         
            byte bufIn[] = new byte[256];            
            int length = is.read(bufIn);
            if(length == -1)
              return "";
              
            StringTokenizer st;
            str = new String(bufIn, 0);
            st   = new StringTokenizer(str, "\r\n");
            str = new String((String)st.nextElement());
            System.out.println(">> " + str);
            log.println(">>  " + str);
            
            String value = response(str);
            byte bufOut[] = value.getBytes();            
            System.out.println(">  " + value);
            log.println(">  " + value);
            os.write(bufOut, 0, value.length());
            os.flush();

            ss.close();
            s.close();
            is.close();
            os.close();
            return str;
        }
        catch(Exception ioe)
        {
          System.out.println(ioe.toString());
          return "";
        }  
    }
    
    public static void lab0(String[] args) {
        byte bKbdInput[] = new byte[256];
        ServerSocket ss;
        Socket s;
        InputStream is;
        OutputStream os;
        try
        {
          System.out.println("Socket Server Application");
        }
        catch(Exception ioe)
        {
          System.out.println(ioe.toString());
        }
        try
        {
          ss = new ServerSocket(9999);
          s = ss.accept();
          is = s.getInputStream();
          os = s.getOutputStream();
          byte buf[] = new byte[512];
          int lenght;

          while(true)
          {
            lenght = is.read(buf);
            if(lenght == -1)
              break;
            
            String str = new String(buf, 0);

//            StringTokenizer st;
//            st   = new StringTokenizer(
//               str, "\r\n");
//            str = new String((String)st.nextElement());
//            System.out.println(">>  " + str);
//            for(int i = 0; i < lenght; i++)
//                buf[i] = 111;
            
            str = new String(buf, 0);

            StringTokenizer st;
            st   = new StringTokenizer(
               str, "\r\n");
            str = new String((String)st.nextElement());

            System.out.println(">  " + str);
            
            while (str.contains("  "))
                str = str.replace("  ", " ");
            
//            str = str.replace("[", "(");
//            str = str.replace("]", ")");
            
            System.out.println(">>  " + str);
            
            buf = str.getBytes();
            os.write(buf, 0, lenght);
            os.flush();
          }
          is.close();
          os.close();
          s.close();
          ss.close();
        }
        catch(Exception ioe)
        {
          System.out.println(ioe.toString());
        }   
        try
        {
          System.out.println("Press to terminate application...");
          System.in.read(bKbdInput);
        }
        catch(Exception ioe)
        {
          System.out.println(ioe.toString());
        }
    }
    
}
