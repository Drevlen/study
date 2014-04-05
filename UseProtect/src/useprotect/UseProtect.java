/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package useprotect;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author drevlen
 */
public class UseProtect {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        lab1(args);
    }
        
    public static void lab1(String[] args) {
        String address = "localhost";
        try
        {
          System.out.println(
            "Socket Client Application" +
            "\nto login type 'name' and proceed further instructions" +
            "\nto create new account type 'register'" +
            "\nto block account type 'ban'" +        
            "\nto change your password type 'newpass' and proceed further instructions" +
            "\nto get data type 'data'" +
            "\nto quit type 'quit' or 'exit'"
          );
        }
        catch(Exception ioe)
        {
          System.out.println(ioe.toString());
        }

        try
        {
        BufferedReader systemReader;
        String str;
        String com;
        boolean registering = false;
        while(true) 
        {
            System.out.print("< ");
            systemReader = new BufferedReader(new InputStreamReader(System.in));
	    com = systemReader.readLine();
            if (com == "register")
            {
                str = dialog("_register", address);
                System.out.println(str);
                if (str.contains("admin"))
                    registering = true;
            }
            if (com.contains("ban "))
            {
                str = dialog("_" + com, address);
                System.out.println(str);
            }
            if (com.contains("name "))
            {
                str = dialog("_" + com, address);
                if (str.contains("error"))
                {
                    registering = false;
                }
                System.out.println(str);
            }
            if (com.contains("password"))
            {
                char[] passwordChars = readPassword("");
                String passwordString = new String(passwordChars);
                str = dialog("_password " + passwordString, address);
                System.out.println(str);
                if (str.contains("error"))
                {
                    dialog("_endSession", address);
                    return;
                }
            }
            if (com == "data")
            {
                dialog("_data", address);
                return;
            }

            if (com == "quit" || com == "exit")
            {
                dialog("_endSession", address);
                return;
            }
                
        }
        }
        catch(Exception ioe)
        {
          System.out.println(ioe.toString());
        }    
    }
    
    private static String readLine(String format, Object... args) throws IOException {
    if (System.console() != null) {
        return System.console().readLine(format, args);
    }
    System.out.print(String.format(format, args));
    BufferedReader reader = new BufferedReader(new InputStreamReader(
            System.in));
    return reader.readLine();
    }
    
    private static char[] readPassword(String format, Object... args)
        throws IOException {
    if (System.console() != null)
        return System.console().readPassword(format, args);
    return readLine(format, args).toCharArray();
    }
    
    public static String dialog(String value, String address) {
        try
        {
            PrintWriter log = new PrintWriter("results.txt", "UTF-8");
            Socket s;
            InputStream is;
            OutputStream os;
            s = new Socket(address, 7700);
            is = s.getInputStream();
            os = s.getOutputStream();
            byte bufOut[] = value.getBytes();            
            System.out.println(">  " + value);
            log.println(">  " + value);
            os.write(bufOut, 0, value.length());
            os.flush();

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
        Socket s;
        
        InputStream is;
        OutputStream os;
        BufferedReader systemReader;
        
        try
        {
          System.out.println(
            "Socket Client Application" +
            "\nEnter any string or" +
            " 'quit' to exit...");
        }
        catch(Exception ioe)
        {
          System.out.println(ioe.toString());
        }

        try
        {
          PrintWriter log = new PrintWriter("results.txt", "UTF-8");
          s = new Socket("localhost",9999);
          is = s.getInputStream();
          os = s.getOutputStream();
          byte buf[] = new byte[512];
          int length;
          String str;

          while(true)
          {
            systemReader = new BufferedReader(new InputStreamReader(System.in));
	    str = systemReader.readLine();
            System.out.println(">  " + str);
            log.println(">  " + str);
            
            if(str.equals("quit"))
                break;
            
            bKbdInput = str.getBytes();
            os.write(bKbdInput, 0, str.length());
            os.flush();
                    
              length = is.read(buf, 0, buf.length);
              if(length == -1)
                break;
              
              StringTokenizer st;
              str = new String(buf, 0);
              st   = new StringTokenizer(str, "\r\n");
              str = new String((String)st.nextElement());
              System.out.println(">> " + str);
              log.println(">>  " + str);
          }
          is.close();
          os.close();
          s.close();
          log.close();
        }
        catch(Exception ioe)
        {
          System.out.println(ioe.toString());
        }    

        try
        {
          System.out.println(
            "Press  to " +
             "terminate application...");
          System.in.read();
        }
        catch(Exception ioe)
        {
          System.out.println(ioe.toString());
        }
    }

    
}
