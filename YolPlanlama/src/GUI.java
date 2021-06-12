import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class GUI extends Application {		

	JFrame frame1 = new JFrame("KONUM BELÝRLEME");	  
	static JButton buton = new JButton("BASLA");
	static boolean kontrol = false;
	
    static Text[][] board =  new Text[25][25];
    static int [][] reward =  new int[25][25];
    static double [][] Q_Matris =  new double[25][25];			
    static int [] yon =  new int[8];
    static int index=0,x1=0,y1=0,z=0,c=0,index2=0;
    static int sayi1,sayi2,sayi3,sayi4,count=0,c2=0;
    double toplam=0;
    boolean son=true;
    int bittiMi = 0;
    int bitti = 0;
    
    
     JTextField baslangicX = new JTextField(30);
     JTextField baslangicY = new JTextField(30);
     JTextField bitisX = new JTextField(30);
     JTextField bitisY = new JTextField(30);
    
    ArrayList<Integer> kisa_yollar = new ArrayList<Integer>();
    ArrayList<Double> maxx = new ArrayList<Double>();
    ArrayList<Integer> optimum = new ArrayList<Integer>();
    ArrayList<Integer> gidilen_yol = new ArrayList<Integer>();
    ArrayList<Integer> adim_sayisi = new ArrayList<Integer>();
    ArrayList<Integer> ana_yol = new ArrayList<Integer>();
    ArrayList<Double> Yol_Maliyet = new ArrayList<Double>();
    ArrayList<Double> Q_yollar = new ArrayList<Double>();

    JFrame frame = new JFrame("LABÝRENT");    
    ImageIcon icon = new ImageIcon("src/robotics.png");    

	public GUI() {
		
		JLabel l1 = new JLabel("Baslangic Konumu girin X");
		l1.setBounds(50, 10, 200, 40);	   
		JLabel l2 = new JLabel("Baslangic Konumu girin Y");
		l2.setBounds(50, 110, 200, 40);
		JLabel l3 = new JLabel("Bitis Konumu girin X");
		l3.setBounds(350, 10, 200, 40);	  
		JLabel l4 = new JLabel("Bitis Konumu girin Y");
		l4.setBounds(350, 110, 200, 40);	  
		
		baslangicX.setBounds(50, 40, 200, 30);     
		baslangicY.setBounds(50, 140, 200, 30); 
		bitisX.setBounds(350, 40, 200, 30);  	   
		bitisY.setBounds(350, 140, 200, 30);
		   
		frame1.add(l1);
		frame1.add(l2);
		frame1.add(l3);
		frame1.add(l4);
		frame1.add(baslangicX); 
		frame1.add(baslangicY); 
		frame1.add(bitisX); 
		frame1.add(bitisY); 
		frame1.add(buton);
		
		frame1.setLayout(null);
		frame1.setSize(600,400);
	    frame1.getContentPane().setBackground(Color.gray);
	    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	    frame1.setVisible(true);
		   
		 buton.setBounds(250,250,100,40);
		 buton.setBackground(Color.lightGray);
		 buton.setFont(new Font("Comic Sans",Font.ITALIC,15));
		 buton.setForeground(new Color(76,64,255));		      
		 buton.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent ae)
	            {      
	           		 sayi1 =  Integer.parseInt(baslangicX.getText());
	        		 sayi2 = Integer.parseInt(baslangicY.getText());
	        		 sayi3 = Integer.parseInt(bitisX.getText());
	        		 sayi4 = Integer.parseInt(bitisY.getText());	
	        		kontrol = true;
	            	}
	        	}
	       );
		 while(true) {
			 System.out.println();
			 if(kontrol == true)
			 {	
				 frame.setVisible(true);			 
				 frame1.dispose();
				 break;
			 }
		}
		
	   frame.setLayout(new GridLayout(25,25));	  
	   frame.setSize(800,800);
	   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
	   for(int i=0; i<25; i++)
	  	{
		   for(int j=0 ; j<25 ; j++)
	  		{	  		
	  			Text button = new Text(i,j,false,true,0);	
	  			frame.add(button);
	  			board[i][j] = button;
	  			board[i][j].setBackground(Color.gray);	
	  			
	  		}	  		
	  	}
	 
		gidilen_yol.add(sayi1);
		gidilen_yol.add(sayi2);	
		
		rastgele();		
		Adim();			
    }
 
void rastgele() 
{	
	int a =0;
	Random randNum = new Random();		
	while(a < 187) {
		
		int satir = randNum.nextInt(25);			
		int sutun = randNum.nextInt(25);
		
		for(int  i=0;i<25;i++)
		{
			for(int j=0 ; j<25 ; j++)
			{
				if(i == satir && j == sutun && board[satir][sutun].Engel == false)
				{
					board[i][j].setBackground(Color.red);
					board[i][j].Engel = true;	
					a++;					
				}
				
				Q_Matris[i][j] = 0;
			}
		}		
	}	
	
	board[sayi3][sayi4].Engel= false;
	board[sayi3][sayi4].setBackground(Color.green);
	board[sayi1][sayi2].Engel= false;
	board[sayi1][sayi2].setBackground(Color.blue);	
	
    for(int i=0;i<25;i++)
    {
    	for(int j=0;j<25;j++)
        {
    		if(board[i][j].Engel == true)
            {
            	reward[i][j] = -1;
            }              
            else             	   
            {            	  
            	reward[i][j] = 0;
            }
        }
    }
    reward[sayi3][sayi4] = 1;
	     
    try {
        BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\eclipse kod\\YolPlanlama\\src\\reward.txt"));

        for (int i = 0; i < reward.length; i++) {
            for (int j = 0; j < reward[i].length; j++) {
            	bw.write(reward[i][j] + " ");      
             }
             bw.newLine();
         }
         bw.flush();
         bw.close();
        } catch (IOException e) {       
      }       
    try {
        BufferedWriter aw = new BufferedWriter(new FileWriter("D:\\eclipse kod\\YolPlanlama\\src\\engel.txt"));

        for(int i=0;i<25;i++)
        {
        	for(int j=0;j<25;j++)
            {
        		if(board[i][j].Engel == true)
                {
        			aw.write("("+i+","+j+","+"K"+")");
        			aw.newLine();
                }         		
            }        	 
        }
         aw.flush();
         aw.close();
        } catch (IOException e) {      
       }        
	}	

void Yurume()
{	
	while(x1 != gidilen_yol.get(index))
	{
		if(x1 < gidilen_yol.get(index))
		{
			x1++;
		}					
        else {
			x1--;
		}	
	}
	while(y1 != gidilen_yol.get(index+1))
	{
		if(y1 < gidilen_yol.get(index+1))
		{
			y1++;				
		}
		else {					
			y1--;					
		}							
	}
	board[x1][y1].setIcon(icon);
	try {
		Thread.sleep(0);
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
	board[x1][y1].setIcon(null);	
		
}

int yollar(int x , int y)
{
	if(gidilen_yol.get(index)+x == 25 ||gidilen_yol.get(index+1)+y == 25 ||gidilen_yol.get(index)+x == -1 || gidilen_yol.get(index+1)+y == -1 ||board[(gidilen_yol.get(index))+x][(gidilen_yol.get(index+1))+y].carp == 8)
	{			
		return 0;
	}
	else
	{		
		if(adim_sayisi.size() == 1500)
		{				
			bittiMi = 2;
		}
		else if(gidilen_yol.get(index) == sayi3 && gidilen_yol.get(index+1) == sayi4)
		{			
			bitti = 1;
		}				
		else 
		{
			if(reward[(gidilen_yol.get(index)+x)][(gidilen_yol.get(index+1)+y)] == -1 && board[(gidilen_yol.get(index))+x][(gidilen_yol.get(index+1))+y].carp != 8)				
			{						
				board[(gidilen_yol.get(index))+x][(gidilen_yol.get(index+1))+y].setBackground(Color.white);
				board[(gidilen_yol.get(index))+x][(gidilen_yol.get(index+1))+y].carp = board[(gidilen_yol.get(index))+x][(gidilen_yol.get(index+1))+y].carp +1;
		
				gidilen_yol.add(sayi1);
				gidilen_yol.add(sayi2);

				bitti = 1;
				ana_yol.add(gidilen_yol.get(index)+x);
				ana_yol.add(gidilen_yol.get(index+1)+y);
				Yurume();
				index = index+2;				
				Maliyet();	
			}
			else if(reward[gidilen_yol.get(index)+x][gidilen_yol.get(index+1)+y] == 0 || reward[gidilen_yol.get(index)+x][gidilen_yol.get(index+1)+y] == 1)
			{			
				gidilen_yol.add(gidilen_yol.get(index)+x);
				gidilen_yol.add(gidilen_yol.get(index+1)+y);
				ana_yol.add(gidilen_yol.get(index)+x);
				ana_yol.add(gidilen_yol.get(index+1)+y);
				Yurume();
				index = index+2;				
				Maliyet();	
			}
			else {
				return 0;
			}			
		}
	}
	return 0;
	}
	
void  Adim()
{
	while(bittiMi == 0)
	{
		Random randNum = new Random();
		int yon_random = randNum.nextInt(8);
		if(yon_random == 0)
		{			 
			yollar(1,0);		    			    
		}
		else if(yon_random == 1)
		{
			 yollar(-1,0);			    	
		}
		else if(yon_random == 2)
		{			    	
			 yollar(0,1);			    	
		}
		else if(yon_random == 3)
		{			    	
			yollar(0,-1);			    	
		}
		else if(yon_random == 4)
		{			    	
			yollar(1,1);			    	
		}
		else if(yon_random == 5)
		{			    	
			yollar(-1,1);			    				    	
		}
		else  if(yon_random == 6)
		{
			yollar(1,-1);			    			    	
		}
		else if(yon_random == 7)
		{
			yollar(-1,-1);			   	
		}		
		if(bitti == 1)
		{		
			adim_sayisi.add(gidilen_yol.size()/2);
			Yol_Maliyet.add(toplam);	
			gidilen_yol.clear();
			ana_yol.clear();
			index=0;
			index2=0;
			x1=0;
			y1=0;
			toplam=0;
			gidilen_yol.add(sayi1);
			gidilen_yol.add(sayi2);
			bitti = 0;					
		}
	}	
	if(bittiMi == 2)
	{
		adim_sayisi.add(gidilen_yol.size()/2);
		Optimum_Yol();
	}
}	

void Maliyet(){			
	
	if(ana_yol.get(index2)+1 !=25)
	{
		Q_yollar.add(Q_Matris[ana_yol.get(index2)+1][ana_yol.get(index2+1)]);			
	}		
	if(ana_yol.get(index2)-1 != -1 )
	{
		Q_yollar.add(Q_Matris[ana_yol.get(index2)-1][ana_yol.get(index2+1)]);			
	}		
	if(ana_yol.get(index2+1)+1 !=25 )
	{
		Q_yollar.add(Q_Matris[ana_yol.get(index2)][ana_yol.get(index2+1)+1]);			
	}
	if(ana_yol.get(index2+1)-1 != -1 )
	{
		Q_yollar.add(Q_Matris[ana_yol.get(index2)][ana_yol.get(index2+1)-1]);			
	}
	if(ana_yol.get(index2)+1 !=25  && (ana_yol.get(index2+1)+1 != 25))
	{
		Q_yollar.add(Q_Matris[ana_yol.get(index2)+1][ana_yol.get(index2+1)+1]);			
	}
	if(ana_yol.get(index2)-1 !=-1  && (ana_yol.get(index2+1)+1 !=25))
	{
		Q_yollar.add(Q_Matris[ana_yol.get(index2)-1][ana_yol.get(index2+1)+1]);					
	}		
	if(ana_yol.get(index2)-1 !=-1  && (ana_yol.get(index2+1)-1 !=-1))
	{
		Q_yollar.add(Q_Matris[ana_yol.get(index2)-1][ana_yol.get(index2+1)-1]);			
	}		
	if(ana_yol.get(index2)+1 !=25  && (ana_yol.get(index2+1)-1 !=-1))
	{
		Q_yollar.add(Q_Matris[ana_yol.get(index2)+1][ana_yol.get(index2+1)-1]);			
	}

	Q_Matris[ana_yol.get(index2)][ana_yol.get(index2+1)] = reward[ana_yol.get(index2)][ana_yol.get(index2+1)] + (double)(0.9 * Collections.max(Q_yollar));
	toplam += Q_Matris[ana_yol.get(index2)][ana_yol.get(index2+1)];
	Q_yollar.clear();
    
	try {
        BufferedWriter qw = new BufferedWriter(new FileWriter("D:\\eclipse kod\\YolPlanlama\\src\\qtable.txt"));
        for (int i = 0; i < Q_Matris.length; i++) {
            for (int j = 0; j < Q_Matris[i].length; j++) {
            	qw.write(Q_Matris[i][j] + " ");   
             }
             qw.newLine();
         }                  
        qw.close();
       } catch (IOException e) {    
    	   e.printStackTrace();
     } 
	index2 = index2+2;	
}
void Optimum_Yol()
{
	int x3=0;
	int y3=0;

	double max=0;
	optimum.add(sayi1);
	optimum.add(sayi2);

while(son == true)
{	
	if(sayi1+1 !=25)
	{
		if(board[sayi1+1][sayi2].Hedef == true)
		{
			max = Q_Matris[sayi1+1][sayi2];
			x3 = sayi1+1;
			y3 = sayi2;
			maxx.add(max);
			kisa_yollar.add(x3);
			kisa_yollar.add(y3);
		}		
		
	}		
	if(sayi1-1 != -1)
	{
		if(board[sayi1-1][sayi2].Hedef == true)
		{
			max = Q_Matris[sayi1-1][sayi2];
			x3 = sayi1-1;
			y3 = sayi2;
			maxx.add(max);
			kisa_yollar.add(x3);
			kisa_yollar.add(y3);
		}	
		
	}		
	if(sayi2+1 !=25)
	{
		if(board[sayi1][sayi2+1].Hedef == true)
		{			
			max = Q_Matris[sayi1][sayi2+1];
			x3 = sayi1;
			y3 = sayi2+1;
			maxx.add(max);
			kisa_yollar.add(x3);
			kisa_yollar.add(y3);
		}	
		
	}		
	if(sayi2-1 != -1)
	{
		if(board[sayi1][sayi2-1].Hedef == true)
		{			
			max = Q_Matris[sayi1][sayi2-1];
			x3 = sayi1;
			y3 = sayi2-1;
			maxx.add(max);
			kisa_yollar.add(x3);
			kisa_yollar.add(y3);
		}	
		
	}
	if(sayi1+1 !=25  && sayi2+1 != 25)
	{
		if(board[sayi1+1][sayi2+1].Hedef == true)
		{			
			max = Q_Matris[sayi1+1][sayi2+1];
			x3 = sayi1+1;
			y3 = sayi2+1;
			maxx.add(max);
			kisa_yollar.add(x3);
			kisa_yollar.add(y3);
		}
		
	}
	if(sayi1-1 !=-1  && sayi2+1 !=25)
	{
		if(board[sayi1-1][sayi2+1].Hedef == true)
		{
			max = Q_Matris[sayi1-1][sayi2+1];
			x3 = sayi1-1;
			y3 = sayi2+1;
			maxx.add(max);
			kisa_yollar.add(x3);
			kisa_yollar.add(y3);
		}	
		
	}		
	if(sayi1-1 !=-1  && sayi2-1 !=-1)
	{
		if(board[sayi1-1][sayi2-1].Hedef == true)
		{			
			max = Q_Matris[sayi1-1][sayi2-1];
			x3 = sayi1-1;
			y3 = sayi2-1;
			maxx.add(max);
			kisa_yollar.add(x3);
			kisa_yollar.add(y3);
		}
		
		
	}		
	if(sayi1+1 !=25  && sayi2-1 !=-1)
	{
		if(board[sayi1+1][sayi2-1].Hedef == true)
		{			
			max = Q_Matris[sayi1+1][sayi2-1];
			x3 = sayi1+1;
			y3 = sayi2-1;
			maxx.add(max);
			kisa_yollar.add(x3);
			kisa_yollar.add(y3);
		}	
		
	}
	int g=0;
	
	for(int i=0;i<kisa_yollar.size()-1;i= i+2)
	{
		if(maxx.get(g) == Collections.max(maxx))
		{
			sayi1 = kisa_yollar.get(i);
			sayi2 = kisa_yollar.get(i+1);
			optimum.add(sayi1);
			optimum.add(sayi2);
			System.out.println(sayi1);
			System.out.println(sayi2);
			System.out.println("--------------------------------");
			System.out.println(optimum.size());
			
			if(sayi1 == sayi3 && sayi2==sayi4)
			{
				son=false;
			}
			else 
			{				
				son = true;
			}			
			break;
		}
		g++;
	}	
	board[sayi1][sayi2].Hedef = false;
	kisa_yollar.clear();
	maxx.clear();		
	}
}

@Override
public void start(Stage stage){

    ArrayList<Integer> adim = new ArrayList<Integer>();
    ArrayList<Double> maliyet = new ArrayList<Double>();
	
	Stage stage1 = new Stage();
	Stage stage2 = new Stage();
    stage.setTitle("Chart");      
    
    final NumberAxis xAxis = new NumberAxis(0, 100, 1);   
   
    for(int i=0; i<100; i++)
    {    	
    	 adim.add(adim_sayisi.get(i));
    	 maliyet.add(Yol_Maliyet.get(Yol_Maliyet.size()-1-i));
    }

    int x = Collections.max(adim);
    final NumberAxis yAxis = new NumberAxis(0, x, 10);
    
    final NumberAxis xAxis2 = new NumberAxis(0, 100, 1);
    double x2 = Collections.max(maliyet);
    final NumberAxis yAxis2 = new NumberAxis(-5, x2, 100);
    
    final NumberAxis xAxis1 = new NumberAxis(0, 25, 1);
    final NumberAxis yAxis1 = new NumberAxis(0, 25, 1);

    final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis1,yAxis1); 
    lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
    final LineChart<Number,Number> lineChart1 = new LineChart<Number,Number>(xAxis,yAxis);
    final LineChart<Number,Number> lineChart2 = new LineChart<Number,Number>(xAxis2,yAxis2); 
   
    XYChart.Series series = new XYChart.Series(); 
    XYChart.Series series1 = new XYChart.Series();
    XYChart.Series series2 = new XYChart.Series();
    int j=0;

    for(int i=0; i<optimum.size()-1; i=i+2)
    {
    	series.getData().add(new XYChart.Data(optimum.get(i),optimum.get(i+1)));
    	System.out.println(series.getData().get(j));
    	j++;        	
     } 

    series.setName("Optimum Yol"); 
    
    for(int i=0; i<100; i++)
    {
    	series1.getData().add(new XYChart.Data(i+1,adim.get(i)));
    }
    for(int i=0; i<100; i++)
    {
    	series2.getData().add(new XYChart.Data(i+1,maliyet.get(i)));
    }
    series1.setName("Adým sayýsý");
    series2.setName("Maliyet");
    
    Scene scene1  = new Scene(lineChart1,1000,1000);       
    Scene scene2  = new Scene(lineChart2,1000,1000);
    Scene scene  = new Scene(lineChart,800,600);

    lineChart.getData().add(series);
    lineChart1.getData().add(series1);
    lineChart2.getData().add(series2);
    
    stage1.setScene(scene1);
    stage.setScene(scene);
    stage2.setScene(scene2);
   
    stage.show();
    stage1.show();
    stage2.show();

	}
}