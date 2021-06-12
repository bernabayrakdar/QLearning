import javax.swing.JButton;

public class Text extends JButton {
	 public int  BtnSatir,BtnSutun;
	 public boolean Engel;	
	 public boolean Hedef;
	 public int carp;

	public Text(int BtnSatir,int BtnSutun,boolean Engel,boolean Hedef, int carp)
	{
		this.BtnSatir = BtnSatir;
		this.BtnSutun = BtnSutun;
		this.Engel = Engel;	
		this.Hedef = Hedef;
		this.carp = carp;
		
	}
	
}