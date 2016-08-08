import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class type extends JFrame implements KeyListener
{
	Container c;
	JComboBox min_combo,level;
	JButton start;
	JFrame menu;
	static JLabel header,footer,time,wpm;
	JButton jb[]=new JButton[27];
	String check;
	static int wrongs=0,corrects=0,index=0,flag=0,tc=0,ent,shift=32,level_;
	static long begin,end;
	static Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
	static int width = (int)ss.getWidth();
	static int height=(int)ss.getHeight();
	public type(int zam)
	{
		this.level_=zam;
		check=generate(zam);
		c=getContentPane();
		c.setBackground(new Color(177,177,177));
		//c.setLayout(new BorderLayout());
		Font hfont = new Font("",Font.BOLD,30);
		header = new JLabel("");
		header.setFont(hfont);
		header.setForeground(Color.black);
		footer = new JLabel("<html><font color=white bgcolor=black>Accuracy : 0% ");
		footer.setHorizontalAlignment(JLabel.LEFT);
		footer.setFont(hfont);
		Box vb=Box.createVerticalBox();
		Box vf = Box.createVerticalBox();
		Box h1 = Box.createHorizontalBox();
		Box h2 = Box.createHorizontalBox();
		Box h3 = Box.createHorizontalBox();
		Box h4 = Box.createHorizontalBox();
		Box h5 = Box.createHorizontalBox();
		
		
		// ADDING KEYBOARD
		
		for(String i:new String[]{"q","w","e","r","t","y","u","i","o","p"})
			{
			int temp=(int)i.charAt(0)-97;
			//ImageIcon img = new ImageIcon(i+".png");
			jb[temp]=new JButton(i);
			jb[temp].setForeground(Color.white);
			jb[temp].setFocusable(false);
			jb[temp].setFont(new Font("",Font.BOLD,30));
			//jb[temp].setIcon(img);
			jb[temp].setBackground(Color.black);
			jb[temp].addKeyListener(this);
			h1.add(jb[temp]);
			}
		for(String i:new String[]{"a","s","d","f","g","h","j","k","l"})
			{
			int temp=(int)i.charAt(0)-97;
			//ImageIcon img = new ImageIcon(i+".png");
			jb[temp]=new JButton(i);
			jb[temp].setFocusable(false);
			jb[temp].setFont(new Font("",Font.BOLD,30));
			jb[temp].setForeground(Color.white);
			//jb[temp].setIcon(img);
			jb[temp].setBackground(Color.black);
			jb[temp].addKeyListener(this);
			h2.add(jb[temp]);
			}
			
		for(String i:new String[]{"z","x","c","v","b","n","m"})
			{
			int temp=(int)i.charAt(0)-97;
			//ImageIcon img = new ImageIcon(i+".png");
			jb[temp]=new JButton(i);
			jb[temp].setForeground(Color.white);
			jb[temp].setFont(new Font("",Font.BOLD,30));
			jb[temp].setFocusable(false);
			//jb[temp].setIcon(img);
			jb[temp].setBackground(Color.black);
			jb[temp].addKeyListener(this);
			h3.add(jb[temp]);
			}
		// END KEYBOARD
		time=new JLabel("<html><font color=white bgcolor=black>time...");
		time.setFont(hfont);
		time.setHorizontalAlignment(JLabel.CENTER);
		
		wpm=new JLabel("<html><font color=white bgcolor=black>WPM : ");
		wpm.setFont(hfont);
		wpm.setHorizontalAlignment(JLabel.RIGHT);
		
		
		vb.add(h1);
		vb.add(h2);
		vb.add(h3);
		h4.add(header);
		h5.add(footer);
		h5.add(Box.createHorizontalGlue());
		h5.add(time);
		h5.add(Box.createHorizontalGlue());
		h5.add(wpm);
		vf.add(Box.createVerticalStrut(30));
		vf.add(h4);
		vf.add(Box.createVerticalStrut(30));
		vf.add(vb);
		vf.add(Box.createVerticalStrut(30));
		vf.add(h5);
		c.add(vf);
		addKeyListener(this);
	}
	// KEY EVENTS
	public void keyPressed(KeyEvent ke)
	{
		if(tc==0)
		{
		int k=ke.getKeyCode();
		if(k==16)
			{
				for(int x=0;x<26;x++)
					{
						jb[x].setText(""+(char)(65+x));
						shift=0;
					}
			}
		else if(k==10)
			{
				if(index==(ent-1))
				{
					index=0;
					check=generate(level_);
					header.setText(check);
				}
				else
				{
					jb[(int)check.charAt(index)-97].setBackground(new Color(224,234,251));
					jb[(int)check.charAt(index)-97].setForeground(Color.black);
				}
			}
		else if(k==32 && check.charAt(index)==' ')
			{
				index++;
			}
		else if(check.charAt(index)==(char)(k+shift))
			{
			String x=check;
			int l=x.length();
			String pre="<html><font color=#BCDEFF bgcolor=black>"+x.substring(0,index+1);
			String next="<font color=black>"+x.substring(index+1,l)+"</html>";
			header.setText(pre+next);
			header.setHorizontalAlignment(JLabel.CENTER);
			jb[k-65].setBackground(new Color(224,234,251));
			jb[k-65].setForeground(Color.black);index++;corrects++;
			flag=1;
			}
		else
			{
			wrongs++;
			jb[(int)check.charAt(index)-97].setBackground(new Color(224,234,251));
			jb[(int)check.charAt(index)-97].setForeground(Color.black);
			}
		}
	}
	public void keyTyped(KeyEvent ke)
	{
		
	}
	public void keyReleased(KeyEvent ke)
	{
		int k=ke.getKeyCode();
		if(k==16)
			{
				for(int x=0;x<26;x++)
					{
						jb[x].setText(""+(char)(97+x));
						shift=32;
					}
			}
		else if(flag==1){
			jb[k-65].setBackground(Color.black);jb[k-65].setForeground(Color.white);}
	}
	
	// GENERATING RANDOM STRING OF LENGTH L
	public static String  generate(int l)
	{
		Random rand=new Random();
		StringBuffer result = new StringBuffer("");
		if(l==1)
		{
			String[] alphabets={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
			for(int i=0;i<12;i++)
				{
					
					for(int j=0;j<2;j++)
						result.append(alphabets[rand.nextInt(26)]);
					result.append(" ");
				}
		}
		else if(l==2)
		{
			String[] alphabets={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
			for(int j=0;j<8;j++)
				{
				int r=rand.nextInt(5)+1;
				for(int i=0;i<r;i++)
					result.append(alphabets[rand.nextInt(26)]);
				result.append(" ");
				}
		}
		else if(l==3)
		{
			String alphabets[]=new String[52];
			for(int i=0;i<26;i++)
					alphabets[i]=""+((char)(i+97));
			for(int j=26;j<52;j++)
					alphabets[j]=""+((char)(j+39));
			for(int i=0;i<10;i++)
				{
					int r=rand.nextInt(5)+1;
					for(int j=0;j<r;j++)
						result.append(alphabets[rand.nextInt(52)]);
					result.append(" ");
				}
		}
		ent = result.length();
		return ""+result;
	} 
	
	// SETTING TIME
	public static void setTime(int m)throws InterruptedException
	{
		for(int i=m-1;i>=0;i--)
			{
				for(int j=59;j>=0;j--)
				{
					time.setText("<html><font size=20px bgcolor=black color=white>"+i+" : "+j);
					accwpm();
					Thread.sleep(1000);
				}
			}
		time.setText("");
		header.setText("<html><font size=20px bgcolor=black color=white >&nbsp;&nbsp; Time UP !!!!&nbsp;&nbsp;");
		tc=1;
	}
	// SETTING ACCURACY AND WPM
	public static void accwpm()
	{
				double accuracy=corrects/1.0/(wrongs+corrects)*100;
				end=System.currentTimeMillis();
				footer.setText("<html><font bgcolor=black color=white>Accuracy : "+(int)accuracy+" % ");
				double w_p_m=(corrects+wrongs)/3.3*60/((end-begin)/10)*100;
				wpm.setText("<html><font bgcolor=black color=white> WPM : "+(int)(w_p_m));
	}
	
	// starting the task
	public  void timer(int t)throws InterruptedException
		{
			header.setText("ready");
			Thread.sleep(1000);
			header.setText("steady");
			Thread.sleep(1000);
			header.setText("goo");
			Thread.sleep(500);
			header.setText(check);
			begin=System.currentTimeMillis();
			setTime(t);
		}
	// MAIN FUNCTION
	public static void main(String a[])throws InterruptedException
	{
		String test1, test2, test3, avg;

		test1= JOptionPane.showInputDialog("\tEnter minutes (1<=minutes<=5)");
		test2= JOptionPane.showInputDialog("\t1.easy\n\t2.medium\n\t3.hard");
		int t1=Integer.parseInt(test1);
		int t2=Integer.parseInt(test2);
		if(t2<=3 && t2>0 && t1>0 && t1<=5)
		{
		type obj=new type(t2);
		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)ss.getWidth();
		int height=(int)ss.getHeight();
		obj.setSize(width-350,height-200);
		obj.setTitle("Tying expert..");
		obj.setLocation(100,50);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.timer(t1);
		}
		else
		{
			JFrame jf=new JFrame();
			JOptionPane.showMessageDialog(jf, "invalid level or minutes");
		}
	}
}


/*
class options extends JFrame implements ItemListener,ActionListener
{
		JComboBox min_combo,level;
		JButton start;
		JFrame menu;
		Container c;
		public static int time_,level_;
		options()
		{
		c = getContentPane();
		Box h1 = Box.createHorizontalBox();
		JLabel minutes=new JLabel("Minutes");
		JLabel label_level=new JLabel("Level");
		minutes.setFont(new Font("",Font.BOLD,20));
		label_level.setFont(new Font("",Font.BOLD,20));
		min_combo = new JComboBox(new String[]{"1","2","3","4","5"});
		level = new JComboBox(new String[]{"easy","medium","hard"});
		h1.add(Box.createHorizontalStrut(20));
		h1.add(minutes);
		h1.add(Box.createHorizontalStrut(20));
		h1.add(min_combo);
		h1.add(Box.createHorizontalStrut(20));
		h1.add(label_level);
		h1.add(Box.createHorizontalStrut(20));
		h1.add(level);
		h1.add(Box.createHorizontalGlue());

		
		
		Box h3=Box.createHorizontalBox();
		start = new JButton("start");
		start.setFocusable(false);
		start.addActionListener(this);
		h3.add(start);
		
		Box v1=Box.createVerticalBox();
		v1.add(Box.createVerticalStrut(20));
		v1.add(h1);
		//v1.add(Box.createVerticalStrut(20));
		v1.add(Box.createVerticalStrut(20));
		v1.add(h3);
		v1.add(Box.createVerticalGlue());
		
		c.add(v1);
		}
		public void itemStateChanged(ItemEvent ie)
		{
		}
		public void actionPerformed(ActionEvent ae)
		{
			if(ae.getSource()==start)
			{
				try{
							type obj=new type(level.getSelectedIndex()+1);
		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)ss.getWidth();
		int height=(int)ss.getHeight();
		obj.setSize(width-350,height-200);
		obj.setTitle("Tying expert..");
		obj.setLocation(100,50);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.timer(min_combo.getSelectedIndex()+1);
				}
				catch(Exception ex){}
			}
		}
		
}

*/
