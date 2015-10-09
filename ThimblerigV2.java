import java.util.Random;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class ThimblerigV2 extends Applet implements MouseMotionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	
	int mouseX=0, mouseY=0;				// координаты курсора мыши
	int x1=125, x2=235, x3=345, ballX, r = 30;	// координаты анимированных эл-тов игры
	int y1=110, y2=110, y3=110, ballY=200;		// координаты анимированных эл-тов игры
	int delta1=12, delta2=7, delta3=15;		// коэффициенты скорости изменения координат наперстков
	int phase=1;					// указатель фазы игры
	int count=0;
	int thimbleFact, choice;    			// случайная переменная, переменная выбора игрока

	Random rand = new Random();
    
	public void init() {
		// класс регистрируется в кач-ве приемника событий от мыши
		addMouseListener(this);
		addMouseMotionListener(this);
		setSize(530,250);
		thimbleFact=rand.nextInt(3);		// закидывание шарика под наперсток
	}
	
	// события, наступ. при отпускании кнопки мыши
	public void mouseReleased(MouseEvent me) {
		mouseX=me.getX();
		mouseY=me.getY();
		if(phase==1 & mouseX>240 & mouseX<290 & mouseY>30 & mouseY<55)
			{ phase=2; }
		if(phase==5 & mouseY>115 & mouseY<165 & mouseX>130 & mouseX<180)
			{ choice=0; phase=6; }
		if(phase==5 & mouseY>115 & mouseY<165 & mouseX>240 & mouseX<290)
			{ choice=1; phase=6; }
		if(phase==5 & mouseY>115 & mouseY<165 & mouseX>350 & mouseX<400)
			{ choice=2; phase=6; }
		repaint();
	}
	
	public void mouseMoved(MouseEvent me) {
		mouseX=me.getX();
		mouseY=me.getY();
		
		// подсказки названия элементов игроку
		if(phase==1 & mouseX>240 & mouseX<290 & mouseY>30 & mouseY<55) {
			showStatus("The \"Start\" button"); }
		else showStatus("");
		if(phase==1 | phase==2 | phase==3 | phase==5) {
			if((mouseY>115 & mouseY<165 & mouseX>130 & mouseX<180) |
					(mouseY>115 & mouseY<165 & mouseX>240 & mouseX<290) |
					(mouseY>115 & mouseY<165 & mouseX>350 & mouseX<400)) 
				showStatus("The thimble");
		}
		else showStatus("");
	}
	
	public void mouseDragged(MouseEvent me) {}
	public void mouseClicked(MouseEvent me) {}
	public void mouseEntered(MouseEvent me) {}
	public void mouseExited(MouseEvent me) {}
	public void mousePressed(MouseEvent me) {}
	
	public void starts(Graphics g) {			// phase=1, предстартовая прорисовка
		g.setColor(Color.WHITE);
		g.fillRect(240, 30, 50, 25);
		g.setColor(Color.BLACK);
		g.drawRect(240, 30, 50, 25);
		g.drawString("Start", 252, 48);
		g.setColor(Color.RED);
		g.fillOval(x1, y1, r*2, r*2);
        g.fillOval(x2, y2, r*2, r*2);
        g.fillOval(x3, y3, r*2, r*2);
	}
	
	public void beforeMix(Graphics g) {			// phase=2, старт
		g.setColor(Color.WHITE);
		g.fillRect(242, 34, 49, 18);
		g.setColor(Color.BLACK);
		g.drawString("Starting", 246, 48);
		g.fillOval(x1, y1, r*2, r*2);
        g.fillOval(x2, y2, r*2, r*2);
        g.fillOval(x3, y3, r*2, r*2);
        count++;
        showStatus("");
        try { Thread.sleep(1000); }
		 catch(InterruptedException e) {}
        phase=3;
        repaint();
	}
	
	public void moveBall(Graphics g) {			// phase=3, забрасывание шарика
		g.setColor(Color.WHITE);
		g.fillRect(242, 34, 49, 18);
		g.setColor(Color.BLACK);
		g.drawString("Starting", 246, 48);
		g.fillOval(x1, y1, r*2, r*2);
        g.fillOval(x2, y2, r*2, r*2);
        g.fillOval(x3, y3, r*2, r*2);
        g.setColor(Color.YELLOW);
        if(thimbleFact==0) { ballX=140; }
        if(thimbleFact==1) { ballX=250; }
        if(thimbleFact==2) { ballX=360; }
        animateBall(g);
        g.fillOval(ballX, ballY, r, r);
        if(ballY==125) { phase=4; }
	}
	
	// анимация шарика
	public void animateBall(Graphics g) {		// in phase=3
		try { Thread.sleep(10); }
		 catch(InterruptedException e) {}
    	ballY--;
    	repaint(10);
    }
	
	public void mix(Graphics g) {				// phase=4, перемешивание наперстков
		g.setColor(Color.WHITE);
		g.fillRect(211, 34, 120, 18);
		g.setColor(Color.BLACK);
		g.drawString("Mixing, please wait...", 215, 48);
		g.setColor(Color.RED);
		g.fillOval(x1, y1, r*2, r*2);
		g.fillOval(x2, y2, r*2, r*2);
		g.fillOval(x3, y3, r*2, r*2);
		animateThimbs(g);
	}
	
	// анимация наперстков
	public void animateThimbs(Graphics g) {		// in phase=4
		Dimension bounds = getSize();
		count++;
		if ((x1 - r*2 + delta1 < 30) |
					(x1 + r*2 + delta1 > bounds.width-70)) delta1 = -delta1;
		if ((x2 - r*2 + delta2 < 30) |
					(x2 + r*2 + delta2 > bounds.width-70)) delta2 = -delta2;
		if ((x3 - r*2 + delta3 < 30) |
					(x3 + r*2 + delta3 > bounds.width-70)) delta3 = -delta3;
			 
		x1 += delta1; x2 += delta2; x3 += delta3;
		try { Thread.sleep(40); }
		 catch(InterruptedException e) {}
        if(count>=104) phase=5;
		repaint(30);
		}

	public void afterMix(Graphics g) {			// phase=5, момент выбора игроком
		g.setColor(Color.WHITE);
		g.fillRect(197, 34, 139, 18);
		g.setColor(Color.BLACK);
		g.drawString("Choose the thimble one:", 200, 48);
		g.setColor(Color.RED);
		x1=125; x2=235; x3=345;
		g.fillOval(x1, y1, r*2, r*2);
        g.fillOval(x2, y2, r*2, r*2);
        g.fillOval(x3, y3, r*2, r*2);
        
        thimbleFact=rand.nextInt(3);			// положение шарика под наперстком
        // showStatus("Thimble: " + (thimbleFact+1));	// ---------подсказка
	}
	
	public void result(Graphics g) {			// phase=6, результаты выбора
		g.setColor(Color.WHITE);
		g.fillRect(240, 30, 50, 25);
		g.setColor(Color.BLACK);
		g.drawRect(240, 30, 50, 25);
		g.drawString("Start", 252, 48);
		if((thimbleFact-choice)==0) {
			g.setColor(Color.WHITE);
			g.fillRect(217, 66, 95, 18);
			g.setColor(Color.BLUE);
			g.drawString("YOU GUESSED!", 220, 80);
		}
		else {
			g.setColor(Color.WHITE);
			g.fillRect(241, 66, 49, 18);
			g.setColor(Color.RED);
			g.drawString("LOSER!", 244, 80);
		}
		g.setColor(Color.BLACK);
		g.fillOval(x1, y1, r*2, r*2);
        g.fillOval(x2, y2, r*2, r*2);
        g.fillOval(x3, y3, r*2, r*2);
        g.setColor(Color.YELLOW);
        if(thimbleFact==0) { ballX=140; }
        if(thimbleFact==1) { ballX=250; }
        if(thimbleFact==2) { ballX=360; }
        g.fillOval(ballX, 125, r, r);
        phase=1;
        ballY=200;
        count=0;
	}
	
	public void paint(Graphics g) {
		//g.drawImage(getImage(getCodeBase(), "back.jpg"), 0, 0, this);
		switch(phase){
		case 1: starts(g); break;				// фаза phase=1
		case 2: beforeMix(g); break;			// фаза phase=2
		case 3: moveBall(g); break;				// фаза phase=3
		case 4: mix(g); break;					// фаза phase=4
		case 5: afterMix(g); break;				// фаза phase=5
		case 6: result(g); break;				// фаза phase=6
		}
	
	}
}