import java.awt.*; 
import java.awt.event.*; 
import java.util.ArrayList;

public class sketchpad1 extends Frame {
 public sketchpad1() {  
  setSize(250,400);
  addMouseListener(new myMouseHandler());
  addMouseMotionListener(new myMouseMotionHandler());
 }
int x0,y0,x,y;
ArrayList<drawObj> myPic = new ArrayList<drawObj>();
public class drawObj {
    int mode, clr, xi, xj, yi, yj;
    drawObj(int md, int c, int x0, int y0, int x, int y){
        mode = md;
        clr = c;
        xi = x0;
        xj = x;
        yi = y0; 
        yj = y;
    }
}
public class myMouseHandler implements MouseListener {
    public void mousePressed(MouseEvent e) { x0=e.getX(); y0=e.getY();  }
    public void mouseReleased(MouseEvent e) {myPic.add(new drawObj(1,1,x0,y0,x,y)); }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}
public class myMouseMotionHandler implements MouseMotionListener {
    public void mouseMoved(MouseEvent e) {  }
    public void mouseDragged(MouseEvent e){x=e.getX();y=e.getY();repaint();}
}

public void paint(Graphics g) {
    g.drawLine(x0,y0,x,y);
    myPic.forEach( l -> g.drawLine(l.xi,l.yi,l.xj,l.yj) );
}
public static void main(String[] s){ new sketchpad1().setVisible(true); }
}