import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JComboBox;
import java.awt.Color;
import java.lang.*;
import java.nio.channels.SelectableChannel;
import java.awt.Component;
import javax.swing.*;

public class ALLWORKLINES extends Frame {
    class drawObj {
        int mode, col, xi, xj, yi, yj,st;
        drawObj() { }
        drawObj(int md, int c, int pick, int x0, int y0, int x, int y){
            mode = md;
            st = pick; 
            col = c;
            xi = x0;
            xj = x;
            yi = y0; 
            yj = y;
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
    //////////////////////////////////
    int x0, y0, x, y, movx, movy;
    int md;
    int c;
    int pick = 0;
    int dist = 9999999; 
    int dmin = 3;
    ArrayList<drawObj> myPic = new ArrayList<drawObj>();
    drawObj selected = new drawObj();
   
     //////////////////////////////////
    public ALLWORKLINES() {
        setSize(1000, 800);
        setLayout(new FlowLayout());
        addMouseListener(new myMouseHandler());
        addMouseMotionListener(new myMouseMotionHandler());
  
        // TESTFIEDLS
        // BUTTON FOR MOUSE FUNCTOIN
        Button mouseBtn = new Button("Mouse");
        // BUTTON FOR UNDO FUNCTOIN
        Button undoBtn = new Button("Undo");
        // BUTTON FOR REDO FUNCTOIN
        Button redoBtn = new Button("Redo");
        // BUTTON FOR LINE FUNCTOIN
        Button modeLine = new Button("Line");
        // BUTTON FOR RECTANGLE FUNCTOIN
        Button modeRec = new Button("Rec");
        // BUTTON FOR SELECT FUNCTOIN
        Button selBtn = new Button("Select");
        // BUTTON FOR MOVE FUNCTOIN
        Button movBtn = new Button("Move");
        // BUTTON FOR COPY FUNCTOIN
        Button cpyBtn = new Button("Copy");
        // BUTTON FOR DELET FUNCTOIN
        Button dtBtn = new Button("Delete");



        // COMBOBOX FOR COLOUR SELECTION
        String[] colours = { "Black", "Red", "Blue", "Green" };
        Color[] comboColour = { Color.black, Color.red, Color.blue, Color.green };
        JComboBox<String> colSelect = new JComboBox<>(colours);
        colSelect.setBounds(150, 150, 20, 40);

        // ADD FRAME ELEMENTS
        add(mouseBtn);
        add(undoBtn);
        add(redoBtn);
        add(colSelect).setVisible(true);
        add(modeLine);
        add(modeRec);
        add(selBtn);
        add(movBtn);
        add(cpyBtn);
        add(dtBtn);

        // ACTION LISTENTERS
        mouseBtn.addActionListener(e -> {
            md = 0;
        });
        undoBtn.addActionListener(e -> {
            if (myPic.size() != 0){
                if (myPic.get(myPic.size() - 1).mode==1)
                myPic.remove(myPic.size() - 1);
                else if (myPic.get(myPic.size() - 1).mode==3){
                    myPic.remove(myPic.size() - 1);
                    myPic.remove(myPic.size() - 1);
                    myPic.remove(myPic.size() - 1);
                    myPic.remove(myPic.size() - 1);
                }
            }
            repaint();
        });

        modeLine.addActionListener(e -> {
            md = 1;
        });
        modeRec.addActionListener(e -> {
            md = 3;
        });
        selBtn.addActionListener(e -> {
            md = 10;
        });
        movBtn.addActionListener(e -> {
            md = 11;
        });
        cpyBtn.addActionListener(e -> {
            md = 12;
        });
        dtBtn.addActionListener(e -> {
            md = 13;
            if(selected != null && myPic.size() != 0){
                myPic.remove(myPic.indexOf(selected));
            }
            repaint();
        });

        colSelect.addActionListener(l -> {
            if (colSelect.getItemAt(colSelect.getSelectedIndex()) == "Black") {
                c = 1;
                colSelect.setBackground(Color.black);
            } else if (colSelect.getItemAt(colSelect.getSelectedIndex()) == "Red") {
                c = 2;
                colSelect.setBackground(Color.red);
            } else if (colSelect.getItemAt(colSelect.getSelectedIndex()) == "Blue") {
                c = 3;
                colSelect.setBackground(Color.blue);
            } else if (colSelect.getItemAt(colSelect.getSelectedIndex()) == "Green") {
                c = 4;
                colSelect.setBackground(Color.green);
            } else {
                c = 5;
                colSelect.setBackground(Color.yellow);
            }
        });
    }

    public class myMouseHandler implements MouseListener {
        public void mousePressed(MouseEvent e) {
            x0 = e.getX();
            y0 = e.getY();
            if(md == 1)
                myPic.add(new drawObj(md, c, pick, x0, y0, x0, y0));
            else if (md==3){
                myPic.add(new drawObj(md, c, pick, x0, y0, x0, y0));
                myPic.add(new drawObj(md, c, pick, x0, y0, x0, y0));
                myPic.add(new drawObj(md, c, pick, x0, y0, x0, y0));
                myPic.add(new drawObj(md, c, pick, x0, y0, x0, y0));
            }
        }

        public void mouseReleased(MouseEvent e) {
            x0 = e.getX();
            y0 = e.getY(); 
            if (md == 0 /*|| md == 10*/) {
                myPic.remove(myPic.size() - 1);
            }
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
            if (md == 10) {
                myPic.forEach(ob -> {ob.st = 0;});
                myPic.forEach(ob -> {
                    int d=ob.segdist(e.getX(),e.getY());
                    if( dmin >= d) { 
                        ob.st = 1; dmin = 0;   
                        selected = ob; 
                    }
                } );
                dmin = 3;
            }
            if (md == 11){
                x = e.getX();
                y = e.getY();
            }
            if (md == 12 && selected != null ){
                x = e.getX();
                y = e.getY();

                movx = x - selected.xi; 
                movy = y - selected.yi;
                System.out.println("Copy here: ");
                myPic.add(new drawObj(selected.mode, selected.col, selected.st, x, y, (selected.xj+movx), (selected.yj+movy)));
            }
            repaint();
        }
    }

    public class myMouseMotionHandler implements MouseMotionListener {
        public void mouseMoved(MouseEvent e) {
        }

        public void mouseDragged(MouseEvent e) {
            if(md == 1){
                x = e.getX();
                y= e.getY();
                myPic.remove(myPic.size() - 1);
                myPic.add(new drawObj(md, c, pick, x0, y0, x, y));
            }
            else if(md == 11){
                if(selected!=null && myPic.get(myPic.indexOf(selected)).st == 1){
                    movx = -1*(x - e.getX());
                    movy = -1*(y - e.getY());
                    x = e.getX();
                    y= e.getY();
                    drawObj temp = new drawObj(selected.mode, selected.col, selected.st, selected.xi+movx, selected.yi+movy, selected.xj+movx, selected.yj+movy);  
                    myPic.set(myPic.indexOf(selected),temp);
                    selected = temp; 
                }
            }
            repaint();
        }
    }

    public void paint(Graphics g) {
        myPic.forEach(ob -> {
            if (ob.st == 1)
                g.setColor(Color.ORANGE);
            else{
                if (ob.col == 1)
                    g.setColor(Color.black);
                else if (ob.col == 2)
                    g.setColor(Color.red);
                else if (ob.col == 3)
                    g.setColor(Color.blue);
                else if (ob.col == 4)
                    g.setColor(Color.green);
                else
                    g.setColor(Color.yellow);
            }
            if (ob.mode == 1 || ob.mode==3) {
                g.drawLine(ob.xi, ob.yi, ob.xj, ob.yj);
            } 
            
        });
    }

    public static void main(String[] s) {
        new ALLWORKLINES().setVisible(true);
    }
}