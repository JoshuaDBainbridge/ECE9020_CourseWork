import java.awt.*; 
import java.awt.event.*; 

public class sketchpad1 extends Frame {
 public sketchpad1() {  
  setSize(250,400);
  addMouseListener(new myMouseHandler());
  addMouseMotionListener(new myMouseMotionHandler());
 }
int x0,y0,x,y;
public class myMouseHandler implements MouseListener {
public void mousePressed(MouseEvent e) { x0=e.getX(); y0=e.getY();  }
public void mouseReleased(MouseEvent e) { }
public void mouseEntered(MouseEvent e) {}
public void mouseExited(MouseEvent e) {}
public void mouseClicked(MouseEvent e) {}
}
public class myMouseMotionHandler implements MouseMotionListener {
public void mouseMoved(MouseEvent e) {  }
public void mouseDragged(MouseEvent e){x=e.getX();y=e.getY();repaint();}
}
public void paint(Graphics g) {  g.drawLine(x0,y0,x,y); }
public static void main(String[] s){ new sketchpad1().setVisible(true); }
}