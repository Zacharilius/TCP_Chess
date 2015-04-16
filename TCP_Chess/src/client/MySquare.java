package client;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

class MySquare extends JPanel {
	private static final long serialVersionUID = 1L;//Eclipse wanted me to add this. So I did
	JLabel label = new JLabel((Icon)null);
    int x,y;
    boolean enabled=true;

    public MySquare(int myx,int myy) {
        add(label);
        x=myx;y=myy;
        if(x%2==0){
            if(y%2==0){
                setBackground(Color.white);

            }
            else{
                setBackground(Color.black);
            }
        }
        else{
            if(y%2==0){
                setBackground(Color.black);

            }
            else{
                setBackground(Color.white);
            }
        }
        if(x==0){
            switch(y){
                case 0:
                    label.setIcon(new ImageIcon(getClass().getResource("/resources/wrook.png")));
                break;
                case 1:
                    label.setIcon(new ImageIcon(getClass().getResource("/resources/wknight.png")));
                break;
                case 2:
                    label.setIcon(new ImageIcon(getClass().getResource("/resources/wbishop.png")));
                break;
                case 3:
                    label.setIcon(new ImageIcon(getClass().getResource("/resources/wking.png")));
                break;
                case 4:
                    label.setIcon(new ImageIcon(getClass().getResource("/resources/wqueen.png")));
                break;
                case 5:
                    label.setIcon(new ImageIcon(getClass().getResource("/resources/wbishop.png")));
                break;
                case 6:
                    label.setIcon(new ImageIcon(getClass().getResource("/resources/wknight.png")));
                break;
                case 7:
                    label.setIcon(new ImageIcon(getClass().getResource("/resources/wrook.png")));
                break;
            }
        }
        else if(x==1){
            label.setIcon(new ImageIcon(getClass().getResource("/resources/wpawn.png")));
        }
        else if(x==6){
            label.setIcon(new ImageIcon(getClass().getResource("/resources/bpawn.png")));
        }
        else if(x==7){
            switch(y){
                case 0:
                    label.setIcon(new ImageIcon(getClass().getResource("/resources/brook.png")));
                break;
                case 1:
                    label.setIcon(new ImageIcon(getClass().getResource("/resources/bknight.png")));
                break;
                case 2:
                    label.setIcon(new ImageIcon(getClass().getResource("/resources/bbishop.png")));
                break;
                case 3:
                    label.setIcon(new ImageIcon(getClass().getResource("/resources/bking.png")));
                break;
                case 4:
                    label.setIcon(new ImageIcon(getClass().getResource("/resources/bqueen.png")));
                break;
                case 5:
                    label.setIcon(new ImageIcon(getClass().getResource("/resources/bbishop.png")));
                break;
                case 6:
                    label.setIcon(new ImageIcon(getClass().getResource("/resources/bknight.png")));
                break;
                case 7:
                    label.setIcon(new ImageIcon(getClass().getResource("/resources/brook.png")));
                break;
            }
        }
    }
    
    public void highlightMe(){
    	setBackground(Color.yellow);
    }
    
    public void unhighlightMe(){
        if(x%2==0){
            if(y%2==0){
                setBackground(Color.white);

            }
            else{
                setBackground(Color.black);
            }
        }
        else{
            if(y%2==0){
                setBackground(Color.black);

            }
            else{
                setBackground(Color.white);
            }
        }
    }
    
    public void changePiece(char p){
    	//Uppercase black. Lowecase white
    	//k-king,q-queen,n-knight,b-bishop,r-rook,p-pawn
    	switch(p){
		case 'K':label.setIcon(new ImageIcon(getClass().getResource("/client/images/bking.png")));break;
		case 'Q':label.setIcon(new ImageIcon(getClass().getResource("/client/images/bqueen.png")));break;
		case 'B':label.setIcon(new ImageIcon(getClass().getResource("/client/images/bbishop.png")));break;
		case 'N':label.setIcon(new ImageIcon(getClass().getResource("/client/images/bknight.png")));break;
		case 'R':label.setIcon(new ImageIcon(getClass().getResource("/client/images/brook.png")));break;
		case 'P':label.setIcon(new ImageIcon(getClass().getResource("/client/images/bpawn.png")));break;
		case 'k':label.setIcon(new ImageIcon(getClass().getResource("/client/images/wking.png")));break;
		case 'q':label.setIcon(new ImageIcon(getClass().getResource("/client/images/wqueen.png")));break;
		case 'b':label.setIcon(new ImageIcon(getClass().getResource("/client/images/wbishop.png")));break;
		case 'n':label.setIcon(new ImageIcon(getClass().getResource("/client/images/wknight.png")));break;
		case 'r':label.setIcon(new ImageIcon(getClass().getResource("/client/images/wrook.png")));break;
		case 'p':label.setIcon(new ImageIcon(getClass().getResource("/client/images/wpawn.png")));break;
		default:label.setIcon(null);break;
    	}
    }
    
    public boolean isEnabled(){
    	return enabled;
    }
    public void disable(){
    	enabled=false;
    }
    public void enable(){
    	enabled=true;
    }
}