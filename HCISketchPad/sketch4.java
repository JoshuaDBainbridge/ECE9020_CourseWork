///// sketch4  (can find closest line)

import java.awt.*;  import java.awt.event.*; import java.util.ArrayList;
class sketch4 extends Frame {
    class obj {
        int xi,yi,xj,yj,tp,sl=0;
        obj() { }
        obj(int a, int b, int c, int d, int t) {
            xi=a; yi=b; xj=c; yj=d; tp=t; 
    }
    class ipair { 
    int x,y;
    ipair(int xx, int yy) { x=xx; y=yy; }
    }
    ipair  add(ipair U, ipair W) { return new ipair(U.x+W.x, U.y+W.y); }
    ipair  sub(ipair U, ipair W) { return new ipair(U.x-W.x, U.y-W.y); }
    ipair scale(ipair U, float s) { return new ipair((int)(s*(float)U.x), (int)(s*(float)U.y)); }
    int dist(ipair P, ipair Q) { return (int)Math.sqrt((P.x-Q.x)*(P.x-Q.x) + (P.y-Q.y)*(P.y-Q.y)); }
    int  dot(ipair P, ipair Q) { return P.x*Q.x + P.y*Q.y; }
    int segdist(int xp,int yp) { // distance from point (xp,yp) to line segment (xi,yi,xj,yj)
        ipair I=new ipair(xi,yi), J=new ipair(xj,yj), P=new ipair(xp,yp), V,N;
        V = sub(J,I);             // V is the vector from I to J
        int k = dot(V, sub(P,I)); // k is the non-normalized projection from P-I to V
        int L2= dot(V,V);         // L2 is the length of V, squared
        if (k<=0) N = I;          // if the projection is negative, I is nearest (N)
        else if (k>=L2) N = J;   // if the projection too large, J is nearest (N)
        else N = add(I, scale(V,(float)k/(float)L2)); // otherwise, N is scaled onto V by k/L2
        return dist(P,N);
    }
}
ArrayList<obj> objs = new ArrayList<obj>();
int x0,y0,type,select=0, dmin=9999999;
obj closest = new obj();
 sketch4() {  
  setSize(150,200);
  setLayout(new FlowLayout());  
  Button btn1 = new Button("back");
  Button btn2 = new Button("lines");
  Button btn3 = new Button("Rectangles");
  Button btn4 = new Button("Find Closest");
  add(btn1); add(btn2); add(btn3); add(btn4);
  btn1.addActionListener( e -> {
    if(objs.size()!= 0) objs.remove( objs.size()-1 );
    repaint(); } );
  btn2.addActionListener( e -> type=0 );
  btn3.addActionListener( e -> type=1 );
  btn4.addActionListener( e -> { select=1; closest.sl=0; repaint(); } );
  addMouseListener( new MouseAdapter() {
   public void mouseReleased(MouseEvent e){select=0;}
   public void mousePressed(MouseEvent e){ 
     x0 = e.getX(); 
     y0 = e.getY(); 
     objs.add( new obj(x0,y0,x0,y0,type) ); }} );
  addMouseMotionListener(new MouseMotionAdapter() {
    public void mouseMoved(MouseEvent e) {
      if (select==1 && objs.size()!=0) {
          objs.forEach( ob -> {
             ob.sl = 0;
             int d=ob.segdist(e.getX(),e.getY());
             if( dmin > d ) { closest=ob; dmin=d; }
             } );
      closest.sl=1;
      repaint();
    }   }
    public void mouseDragged(MouseEvent e) {
      objs.remove( objs.size()-1 );
      objs.add(new obj(x0,y0,e.getX(),e.getY(),type));
      repaint(); }} );
 }
int max(int a,int b){return a>b ? a:b;}
int min(int a,int b){return a>b ? b:a;}
int abs(int a){return a>0 ? a : -a;}
public void paint(Graphics g){ 
   dmin=9999999;
   objs.forEach( ob -> {
   if (ob.sl==1) g.setColor(Color.RED);
   if (ob.tp==0) g.drawLine(ob.xi,ob.yi,ob.xj,ob.yj);
     else g.drawRect(min(ob.xi,ob.xj),min(ob.yi,ob.yj),
                     abs(ob.xi-ob.xj),abs(ob.yi-ob.yj));
   if (ob.sl==1) g.setColor(Color.BLACK); } ); }
public static void main(String[] args){ new sketch4().setVisible(true); }  }