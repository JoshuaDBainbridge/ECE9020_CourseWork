import java.awt.*; import java.awt.event.*; import java.util.ArrayList;
class sketch extends Frame {
class obj { int xi,yi,xj,yj;
            obj(int aa, int bb, int cc, int dd) {
                 xi=aa; yi=bb; xj=cc; yj=dd; } }
ArrayList<obj> mysketch = new ArrayList<obj>();
int x0,y0;
 sketch() {  
  setSize(150,200);
  setLayout(new FlowLayout());  
  Button btn1 = new Button("back");
  add(btn1);
  btn1.addActionListener( e -> {
   if(mysketch.size()!= 0) mysketch.remove( mysketch.size()-1 ); repaint(); } );
  addMouseListener( new MouseAdapter() {
   public void mousePressed(MouseEvent e){ 
    x0 = e.getX(); 
    y0 = e.getY(); 
    mysketch.add( new obj(x0,y0,x0,y0) ); }} );
  addMouseMotionListener(new MouseMotionAdapter() {
   public void mouseDragged(MouseEvent e) {
    mysketch.remove( mysketch.size()-1 );
    mysketch.add(new obj(x0,y0,e.getX(),e.getY()));
    repaint(); }} );
 }
public void paint(Graphics g){mysketch.forEach(l->g.drawLine(l.xi,l.yi,l.xj,l.yj));}
public static void main(String[] args){ new sketch().setVisible(true); }
}
