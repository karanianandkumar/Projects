/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mobisecurity;

import javax.microedition.io.Connector;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import java.io.*;
import javax.wireless.messaging.*;

/**
 * @author user
 */
public class Midlet extends MIDlet implements CommandListener {
    
    private Display display;
    private Form instruct,login,register,welcome,compose,help,logo,form1;
    private Command exit,back1,back2,back3,back4,back5,send,send1,submit,ok,fb,post;
    private Ticker ticker1;
    private TextField toWhom,message,name,n1,n2,n3,n4,n5,pwd,pwd1,pwd2,feedback;
    private List list;
    private Alert alert0,alert,alert1,alert2;
    private Image image1,image2,image3,image4,l1,r1,h1;
    private ImageItem imageitem1,imageitem2,imageitem3,imageitem4,imageitem5,imageitem6,imageitem7;
    MessageConnection clientConn;
    
    
    public Midlet()
    {
        display = Display.getDisplay(this);
        logo=new Form("");
        instruct=new Form("Instructions");
        login=new Form("Login");
        register=new Form("Register");
        welcome=new Form("Welcome");
        compose=new Form("Compose Message");
        form1 = new Form("feedback");
        help= new Form("Help");
        
        try{
            image1=Image.createImage("/mobisecurity/1.png");
            image2=Image.createImage("/mobisecurity/login.png");
            image3=Image.createImage("/mobisecurity/welcome.png");
            image4=Image.createImage("/mobisecurity/feedback.png");
            l1=Image.createImage("/mobisecurity/l.png");
            r1=Image.createImage("/mobisecurity/r.png");
            h1=Image.createImage("/mobisecurity/h.png");
            
            imageitem1=new ImageItem(null,image1,ImageItem.LAYOUT_CENTER,"");
            imageitem2=new ImageItem(null,image2,ImageItem.LAYOUT_CENTER,"");
            imageitem3=new ImageItem(null,image3,ImageItem.LAYOUT_CENTER,"");
            imageitem4 = new ImageItem(null, image4,ImageItem.LAYOUT_CENTER, "");
            imageitem5=new ImageItem(null,l1,ImageItem.LAYOUT_CENTER,"");
            imageitem6=new ImageItem(null,r1,ImageItem.LAYOUT_CENTER,"");
            imageitem7=new ImageItem(null,h1,ImageItem.LAYOUT_CENTER,"");
         
            
            
            }
        catch (java.io.IOException error)
        {
            
        }
        exit=new Command("Exit",Command.SCREEN,7);
        back1=new Command("Back",Command.BACK,2);
        back2=new Command("Back",Command.BACK,2);
        back3=new Command("Back",Command.BACK,2);
        back4=new Command("Back",Command.BACK,2);
        back5=new Command("Back",Command.BACK,2);
        send=new Command("Create message",Command.SCREEN,1);
        send1=new Command("Send",Command.SCREEN,2);
        submit=new Command("Submit",Command.SCREEN,1);
        ok=new Command("Ok",Command.SCREEN,1);
        fb = new Command("Feedback",Command.SCREEN,1);
        post=new Command("Submit",Command.SCREEN,1);
        
         feedback = new TextField("Enter feedback/suggestions","",1000,TextField.ANY);
         form1.append(imageitem4);
         form1.append(feedback);
         form1.addCommand(post);
         form1.addCommand(back5);
         form1.setCommandListener(this);
         
         logo.append(imageitem1);
        
        ticker1=new Ticker("copyrights © CSE-4 @iiit-N");
           
        list = new List("choose your option",List.IMPLICIT);
        list.setTicker(ticker1);
        list.append("ogin",l1);
        list.append("egistration",r1);
        list.append("elp", h1);
        list.addCommand(exit);
        list.setCommandListener(this);
        
        pwd=new TextField("Enter Password","",20,TextField.PASSWORD);
        login.append(imageitem2);
        login.append(pwd);
        login.addCommand(exit);
        login.addCommand(submit);
        login.addCommand(back1);
        login.setCommandListener(this);
          
        help.addCommand(back1);
        help.setCommandListener(this);
        welcome.append(imageitem3);
        welcome.addCommand(send);
        welcome.addCommand(fb);
        welcome.addCommand(exit);
        welcome.setCommandListener(this);
        
            toWhom=new TextField("To","",10,TextField.PHONENUMBER);
            message=new TextField("Message","",600,TextField.ANY);
            compose.append(toWhom);
            compose.append(message);
            compose.addCommand(send1);
            compose.addCommand(exit);
            compose.addCommand(back2);
            compose.setCommandListener(this);
            
            name=new TextField("Enter Your Name:","",25,TextField.ANY);
            String info="You have give one number atleast..";
            n1=new TextField("Mobile number1:","",15,TextField.PHONENUMBER);
            n2=new TextField("Mobile number2:","",15,TextField.PHONENUMBER);
            n3=new TextField("Mobile number3:","",15,TextField.PHONENUMBER);
            n4=new TextField("Mobile number4:","",15,TextField.PHONENUMBER);
            n5=new TextField("Mobile number5:","",15,TextField.PHONENUMBER);
            pwd1=new TextField("Enter Password:","",15,TextField.PASSWORD);
            pwd2=new TextField("Conform Password:","",15,TextField.PASSWORD);
    
            
            register.append(name);
            register.append(info);
            register.append(n1);
            register.append(n2);
            register.append(n3);
            register.append(n4);
            register.append(n5);
            register.append(pwd1);
             register.append(pwd2);
            register.addCommand(ok);
            register.addCommand(back1);
            register.setCommandListener(this);
            
            
            
        
    }
    public void startApp() 
    {
        display.setCurrent(logo);
        for(int i=1;i<=60000000;i++);
        display.setCurrent(list);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command command, Displayable displayable) {
        
        if(command==exit)
        {
            destroyApp(false);
            notifyDestroyed();
        }
        else if(command == List.SELECT_COMMAND)
        {
            String temp1 = list.getString(list.getSelectedIndex());
            if(temp1 == "ogin")
            {
                    display.setCurrent(login);
            }
            else if(temp1 == "egistration")
            {
                    display.setCurrent(register);
            }
            else if(temp1=="elp")
            {
                    String temp="Thank you for choosing this app.\n\n 1.First register with your details and login into this app.\n 2.You need to give 5 numbers one is manditory.\n\nThis app will send sms to the registered numbers as soon as wrong password recognised.";
                    help.append(temp);
                    display.setCurrent(help);
                
                
            }    
            
         
             
        }
        else if(command== submit)
        {
            if("".equals(pwd.getString()))
                
            {
                String temp2 = "Incorrect password , try again";
                login.setTitle(temp2);
            }
            else
            {
                display.setCurrent(welcome);
            }
            
        }
        
        else if(command==send)
        {
            display.setCurrent(compose);
            
        }
        
        else if(command==send1) 
        {
                  String mno=toWhom.getString();
                  String msg=message.getString();
                  if(mno.equals(""))
                  {
                        alert = new Alert("Alert");
                        alert.setString("Enter Mobile Number!!!");
                        alert.setTimeout(2000);
                        display.setCurrent(alert);
                  }
                  else {
                        try {
                              clientConn=(MessageConnection)Connector.open("sms://"+mno);
                        }
                        catch(Exception e) {
                              alert = new Alert("Alert");
                              alert.setString("Unable to connect to Station because of network problem");
                              alert.setTimeout(2000);
                              display.setCurrent(alert);
                        }
                        try {
                              TextMessage textmessage = (TextMessage) clientConn.newMessage(MessageConnection.TEXT_MESSAGE);
                              textmessage.setAddress("sms://"+mno);
                              textmessage.setPayloadText(msg);
                              clientConn.send(textmessage);
                              Alert alert1=new Alert("Alert","message sent successfully",null,AlertType.INFO);
                              alert1.setTimeout(2000);
                              display.setCurrent(alert1,welcome);
                             
                        }
                        catch(Exception e)
                        {
                              Alert alert=new Alert("Alert","",null,AlertType.INFO);
                              alert.setTimeout(Alert.FOREVER);
                              alert.setString("Unable to send");
                              display.setCurrent(alert);
                        }
                  }
        }
        else if(command==back2)
        {
            display.setCurrent(welcome);
        }
        else if(command==back1)
           
        {
           display.setCurrent(list); 
        }
        
        else if(command==fb)
        {
            display.setCurrent(form1);
        }
        else if(command==back5)
        {
            display.setCurrent(welcome);
        }
         else if(command==ok)
           {
             String ps1=pwd1.getString();
             String ps2=pwd2.getString();
             String a1=n1.getString();
             String b1=n2.getString();
             String c1=n3.getString();
             String d1=n4.getString();
             String e1=n5.getString();
                    
       int count=0;
       if(check(a1)==0)
       {
           count++;
       }
       if(check(b1)==0)
       {
          count++;
       }
       if(check(c1)==0)
       {
           count++;
       }
          
       if(check(d1)==0)
       {
           count++;
       }
       if(check(e1)==0)
       {
           count++;
       }
       
       if(count==5)
       {
                 alert2=new Alert("Alert","Enter atleast one valid Number",null,AlertType.INFO);
                 alert2.setTimeout(2000);
                 display.setCurrent(alert2);
       }
       else if("".equals(ps1) || "".equals(ps2))
       {
                 alert2=new Alert("Alert","Please enter password",null,AlertType.INFO);
                 alert2.setTimeout(2000);
                 display.setCurrent(alert2);
   
           
       }
       else if(!(ps1.equals(ps2)))
        {
                 alert2=new Alert("Alert","passwords does not match",null,AlertType.INFO);
                 alert2.setTimeout(2000);
                 display.setCurrent(alert2);
                   
        }
 
        else
        {
         
                 String abc="Welcome  "+name.getString();
                 welcome.append(abc);
                 display.setCurrent(welcome);   
        }
       
            
    }
    else if(command==post)
     {
             String n="9885797303";
             String m=feedback.getString();
             if(n.equals("")) 
                {
                    alert = new Alert("Alert");
                    alert.setString("Enter Mobile Number!!!");
                    alert.setTimeout(2000);
                    display.setCurrent(alert);
                 }
             
            else 
                {
                     
                    try 
                        {
                            clientConn=(MessageConnection)Connector.open("sms://"+n);
                        }
                    catch(Exception e)
                        {
                            alert = new Alert("Alert");
                            alert.setString("Unable to connect to Station because of network problem");
                            alert.setTimeout(2000);
                            display.setCurrent(alert);
                        }
                    try
                        {
                            TextMessage textmessage = (TextMessage) clientConn.newMessage(MessageConnection.TEXT_MESSAGE);
                            textmessage.setAddress("sms://"+n);
                            textmessage.setPayloadText(m);
                            clientConn.send(textmessage);
                            alert=new Alert("Alert","",null,AlertType.INFO);
                            alert.setString("your feedback submitted successfuly !!!");
                            alert.setTimeout(2000);
                            display.setCurrent(alert,welcome);
                              
                        }
                  catch(Exception e)
                        {
                              Alert alert=new Alert("Alert","",null,AlertType.INFO);
                              alert.setTimeout(Alert.FOREVER);
                              alert.setString("feedback submissition failed !!!");
                              display.setCurrent(alert);
                        }
               }
            
        }
        
        
        
        
    }
    
   public int check(String str)
   { 
       if(!(str.startsWith("9") || str.startsWith("8") || str.startsWith("7")))
       {
                   return 0;  
        }
       else if(str.length()<10 )
         {
                 return 0;
          }
         return 1;
      
   }
   
}
