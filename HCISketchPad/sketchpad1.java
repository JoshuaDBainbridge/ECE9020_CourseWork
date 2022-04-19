import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JComboBox;
import java.awt.Color;


public class sketchpad1 extends Frame {
    class drawObj {
        int mode, col, xi, xj, yi, yj,st,num;
        drawObj() { }
        drawObj(int md, int c, int pick, int n, int x0, int y0, int x, int y){
            mode = md;
            st = pick; 
            col = c;
            num = n;
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
    int number = 0; 
    int pick = 0;
    int dist = 9999999; 
    int dmin = 3;
    boolean ployON = false; 
    ArrayList<drawObj> myPic = new ArrayList<drawObj>();
    ArrayList<drawObj> selected = new ArrayList<drawObj>();
   
     //////////////////////////////////
    public sketchpad1() {
        setSize(1000, 800);
        setLayout(new FlowLayout());
        addMouseListener(new myMouseHandler());
        addMouseMotionListener(new myMouseMotionHandler());
        selected.add(new drawObj(md, c, pick, number, x0, y0, x0, y0));
        selected.add(new drawObj(md, c, pick, number, x0, y0, x0, y0));
        selected.add(new drawObj(md, c, pick, number, x0, y0, x0, y0));
        selected.add(new drawObj(md, c, pick, number, x0, y0, x0, y0));
        // TESTFIEDLS
        TextField options = new TextField("Options:");
        TextField coloursPic = new TextField("Colours:");
        TextField  actions = new TextField("Actions");
        // BUTTON FOR MOUSE FUNCTOIN
        Button mouseBtn = new Button("Mouse");
        // BUTTON FOR UNDO FUNCTOIN
        Button undoBtn = new Button("Undo");
        // BUTTON FOR REDO FUNCTOIN
        Button redoBtn = new Button("Redo");
        // BUTTON FOR FreeHAND FUNCTOIN
        Button modeFree = new Button("Free");
        // BUTTON FOR LINE FUNCTOIN
        Button modeLine = new Button("Line");
        // BUTTON FOR RECTANGLE FUNCTOIN
        Button modeRec = new Button("Rec");
        // BUTTON FOR CIRCLE FUNCTOIN
        Button modeCir = new Button("Cir");
        // BUTTON FOR SQUARE FUNCTOIN
        Button modeSqu = new Button("Squ");
        // BUTTON FOR RECTANGLE FUNCTOIN
        Button modeElip = new Button("Elip");
        // BUTTON FOR RECTANGLE FUNCTOIN
        Button modePoly = new Button("Poly");
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
        add(options);
        add(mouseBtn);
        add(undoBtn);
        add(redoBtn);
        add(coloursPic);
        add(colSelect).setVisible(true);
        add(modeLine);
        add(modeRec);
        add(modeElip);
        add(modeFree);
        add(modeSqu);
        add(modeCir);
        add(modePoly);
        add(actions);
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
        modeFree.addActionListener(e -> {
            md = 2;
        });
        modeRec.addActionListener(e -> {
            md = 3;
        });
        modeElip.addActionListener(e -> {
            md = 4;
        });
        modeSqu.addActionListener(e -> {
            md = 5;
        });
        modeCir.addActionListener(e -> {
            md = 7;
        });
        modePoly.addActionListener(e -> {
            md = 6;
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
                myPic.remove(myPic.indexOf(selected.get(0)));
                if(selected.get(0).mode == 3){
                    myPic.remove(myPic.indexOf(selected.get(1)));
                    myPic.remove(myPic.indexOf(selected.get(2)));
                    myPic.remove(myPic.indexOf(selected.get(3)));
                }
            }
            number--;
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
            if(md == 1){
                myPic.add(new drawObj(md, c, pick,number, x0, y0, x0, y0));
            }
            else if(md==4){
                myPic.add(new drawObj(md, c, pick,number, x0, y0, x0, y0));
            }
            else if(md==7){
                myPic.add(new drawObj(md, c, pick,number, x0, y0, x0, y0));
            }
            else if (md==3 || md == 5){
                myPic.add(new drawObj(md, c, pick,number, x0, y0, x0, y0));
                myPic.add(new drawObj(md, c, pick,number, x0, y0, x0, y0));
                myPic.add(new drawObj(md, c, pick,number, x0, y0, x0, y0));
                myPic.add(new drawObj(md, c, pick,number, x0, y0, x0, y0));
            }
        }

        public void mouseReleased(MouseEvent e) {
            x0 = e.getX();
            y0 = e.getY(); 
            if (md == 0 /*|| md == 10*/) {
                myPic.remove(myPic.size() - 1);
            }
            if (md < 10)
                number++;
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {         
            if(md == 6){
               /* System.out.println("in 6");
                if (ployON ==false){
                    x0= e.getX();
                    y0= e.getY();
                    ployON = true;
                }
                else {
                    x= e.getX();
                    y= e.getY();
                    myPic.add(new drawObj(md, c, pick,number, x0, y0, x, y));
                    x0= x;
                    y0=y;
                    repaint();
                }*/
            }
            else if (md == 10) {
                myPic.forEach(ob -> {ob.st = 0;});
                myPic.forEach(ob -> {
                    int d=ob.segdist(e.getX(),e.getY());
                    if( dmin >= d) { 
                        ob.st = 1; dmin = 0;   
                        selected.set(0,ob); 
                    }
                } );
                if (selected.get(0).mode == 3 ||selected.get(0).mode == 5){
                    System.out.println("Rect");
                    int index = myPic.indexOf(selected.get(0));
                    System.out.println(index);
                    while (myPic.get(index).num == selected.get(0).num){
                        if(index>0)
                            index--;
                        else 
                            break;
                    }
                    if(myPic.get(index).num !=selected.get(0).num)
                        index ++; 

                    System.out.println(index);
                    drawObj temp = new drawObj();
                    for(int i = index; i<index+4; i++){
                        System.out.println(i);
                        temp = myPic.get(i);
                        temp.st=1;
                        myPic.set(i,temp);
                    }
                    selected.set(0,myPic.get(index));
                    selected.set(1,myPic.get(index+1));
                    selected.set(2,myPic.get(index+2));
                    selected.set(3,myPic.get(index+3));
                }
                dmin = 3;
            }
            else if (md == 11){
                x = e.getX();
                y = e.getY();
            }
            else if (md == 12 && selected != null ){
                x = e.getX();
                y = e.getY();
                number++;
                movx = x - selected.get(0).xi; 
                movy = y - selected.get(0).yi;
                System.out.println("Copy here: ");
                
                if (selected.get(0).mode==3){
                    myPic.add(new drawObj(selected.get(0).mode, selected.get(0).col, 0,number, x, y, x, (selected.get(0).yj+movy)));
                    myPic.add(new drawObj(selected.get(1).mode, selected.get(1).col, 0,number, x, y, (selected.get(1).xj+movx), y));
                    myPic.add(new drawObj(selected.get(2).mode, selected.get(2).col, 0,number, x, (selected.get(2).yj+movy), (selected.get(2).xj+movx), (selected.get(2).yj+movy)));
                    myPic.add(new drawObj(selected.get(3).mode, selected.get(3).col, 0,number, (selected.get(3).xj+movx), y, (selected.get(3).xj+movx), (selected.get(2).yj+movy)));
                }
                else{
                    myPic.add(new drawObj(selected.get(0).mode, selected.get(0).col, 0,number, x, y,selected.get(0).xj+movx, (selected.get(0).yj+movy)));
                }
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
                myPic.add(new drawObj(md, c, pick,number, x0, y0, x, y));
            }
            else if(md == 4){
                x = e.getX();
                y= e.getY();
                myPic.remove(myPic.size() - 1);
                myPic.add(new drawObj(md, c, pick,number, x0, y0,Math.abs(x0-x),Math.abs(y0-y)));
            }
            else if(md==7){
                x = e.getX();
                y= e.getY();
                double x2 = (double)x*x;
                double y2 = (double)y*y;
                int avgC = (int)Math.sqrt(x2+y2);
                myPic.remove(myPic.size() - 1);
                myPic.add(new drawObj(md, c, pick,number, x0, y0, avgC, avgC));
            }
            else if(md == 3){
                x = e.getX();
                y= e.getY();
                myPic.remove(myPic.size() - 1);
                myPic.remove(myPic.size() - 1);
                myPic.remove(myPic.size() - 1);
                myPic.remove(myPic.size() - 1);
                myPic.add(new drawObj(md, c, pick,number, x0, y0, x0, y));
                myPic.add(new drawObj(md, c, pick,number, x0, y0, x, y0));
                myPic.add(new drawObj(md, c, pick,number, x0, y, x, y));
                myPic.add(new drawObj(md, c, pick,number, x, y0, x, y));
            }
            else if (md ==5){
                System.out.println("Move SQR");
                x = e.getX();
                y= e.getY();
                double x2 = (double)x*x;
                double y2 = (double)y*y;
                int avg = (int)Math.sqrt(x2+y2);
        
                myPic.remove(myPic.size() - 1);
                myPic.remove(myPic.size() - 1);
                myPic.remove(myPic.size() - 1);
                myPic.remove(myPic.size() - 1);
                myPic.add(new drawObj(md, c, pick,number, x0, y0, x0, y0+avg));
                myPic.add(new drawObj(md, c, pick,number, x0, y0, x0+avg, y0));
                myPic.add(new drawObj(md, c, pick,number, x0, y0+avg, x0+avg, y0+avg));
                myPic.add(new drawObj(md, c, pick,number, x0+avg, y0, x0+avg, y0+avg));
            }
            else if(md == 11){
                if(selected!=null && myPic.get(myPic.indexOf(selected.get(0))).st == 1){
                    movx = -1*(x - e.getX());
                    movy = -1*(y - e.getY());
                    x = e.getX();
                    y= e.getY();
                        drawObj temp = new drawObj(selected.get(0).mode, selected.get(0).col, selected.get(0).st,selected.get(0).num, selected.get(0).xi+movx, selected.get(0).yi+movy, selected.get(0).xj+movx, selected.get(0).yj+movy);  
                        myPic.set(myPic.indexOf(selected.get(0)),temp);
                        selected.set(0, temp); 

                    if(selected.get(0).mode==3){
                        for(int i = 1; i<=3; i++){
                            temp = new drawObj(selected.get(i).mode, selected.get(i).col, selected.get(i).st,selected.get(i).num, selected.get(i).xi+movx, selected.get(i).yi+movy, selected.get(i).xj+movx, selected.get(i).yj+movy);  
                            myPic.set(myPic.indexOf(selected.get(i)),temp);
                            selected.set(i, temp); 
                        }
                    }                
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
            if (ob.mode == 1 || ob.mode==3 || ob.mode==5) {
                g.drawLine(ob.xi, ob.yi, ob.xj, ob.yj);
            } 
            else if (ob.mode == 4){
                g.drawOval(ob.xi, ob.yi, ob.xj, ob.yj);
            }
            else if (ob.mode == 7){
                g.drawOval(ob.xi, ob.yi, ob.xj, ob.yj);
            }
        });
    }

    public static void main(String[] s) {
        new sketchpad1().setVisible(true);
    }
}