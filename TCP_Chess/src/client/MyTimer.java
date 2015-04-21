package client;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyTimer extends JLabel{
	private int myCtr;
	private int otherCtr;
	private boolean isMyTurn;

	
	public MyTimer(){
		myCtr=0;
		otherCtr=0;
		this.setText("Ahoy Matey!");
		isMyTurn=false;
	}

	public void startTimer(){
		Timer t=new Timer(1000,new MyTimerActionListener());
		t.start();
	}
	public int incrementMyCtr(){
		return myCtr++;
	}
	public int incrementOtherCtr(){
		return otherCtr++;
	}
	public void setTurn(boolean v){
		isMyTurn=v;
	}
	public void setTimeString(){
		String mytime=""+(((myCtr/3600)<10?"0"+(myCtr/3600):(myCtr/3600))+":"+((myCtr/60)<10?"0"+(myCtr/60):(myCtr/60))+":"+((myCtr%60)<10?"0"+(myCtr%60):(myCtr%60)));
		String theirtime=""+(((otherCtr/3600)<10?"0"+(otherCtr/3600):(otherCtr/3600))+":"+((otherCtr/60)<10?"0"+(otherCtr/60):(otherCtr/60))+":"+((otherCtr%60)<10?"0"+(otherCtr%60):(otherCtr%60)));
		this.setText("My Time: "+mytime+"\t Opponent Time: "+theirtime);
	}
	class MyTimerActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(MyTimer.this.isMyTurn){
				MyTimer.this.incrementMyCtr();
			}
			else{
				MyTimer.this.incrementOtherCtr();
			}
			MyTimer.this.setTimeString();
		}
	}
}