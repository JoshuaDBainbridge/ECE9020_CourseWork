import java.awt.*; 
import java.awt.event.*; 
import java.util.ArrayList;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.Component;
import javax.swing.*;

public class sketchpad1 extends Frame {
    public sketchpad1() { 
        setSize(500,400);
        setLayout(new FlowLayout());
        addMouseListener(new myMouseHandler());
        addMouseMotionListener(new myMouseMotionHandler());

        // BUTTON FOR BACK FUNCTOIN
        Button backBtn = new Button("BACK");
        
        
        // BUTTON FOR LINE FUNCTOIN
        Button modeLine = new Button("Line");
       
            

        // COMBOBOX FOR COLOUR SELECTION 
        String[] colours = {"Black","Red","Blue","Green"};
        Color[] comboColour = {Color.black, Color.red, Color.blue, Color.green};
        JComboBox<String>  colSelect = new JComboBox<>(colours); 
        colSelect.setBounds(150, 150, 20, 40);

        // ADD FRAME ELEMENTS
        add(backBtn);
        add(colSelect).setVisible(true);  
        add(modeLine); 

        //ACTION LISTENTERS 
        backBtn.addActionListener( e -> {
            if(myPic.size()!= 0) myPic.remove(myPic.size()-1); 
            repaint(); } );

        modeLine.addActionListener( e ->{md = 1; System.out.println("hi");});

        colSelect.addActionListener(l -> {
            if (colSelect.getItemAt(colSelect.getSelectedIndex()) == "Black"){
                c = 1; 
                colSelect.setBackground(Color.black);
            }
            else if (colSelect.getItemAt(colSelect.getSelectedIndex()) == "Red"){
                c = 2; 
                colSelect.setBackground(Color.red);
            }
            else if (colSelect.getItemAt(colSelect.getSelectedIndex()) == "Blue"){
                c = 3; 
                colSelect.setBackground(Color.blue);
            }
            else if (colSelect.getItemAt(colSelect.getSelectedIndex()) == "Green"){
                c = 4; 
                colSelect.setBackground(Color.green);
            }
            else{
                c = 5; 
                colSelect.setBackground(Color.yellow);
            }
        }
    );
 
 }
 //////////////////////////////////
int md;
int c;
int x0,y0,x,y;
ArrayList<drawObj> myPic = new ArrayList<drawObj>();
//////////////////////////////////
public class drawObj {
    int mode, col, xi, xj, yi, yj;
    drawObj(int md, int c, int x0, int y0, int x, int y){
        mode = md;
        col = c;
        xi = x0;
        xj = x;
        yi = y0; 
        yj = y;
    }
}
public class myMouseHandler implements MouseListener {
    public void mousePressed(MouseEvent e) { 
        x0=e.getX(); 
        y0=e.getY();
        myPic.add(new drawObj(md,c,x0,y0,x0,y0)); 
        System.out.println("MODE: "+ md); 
    }
    public void mouseReleased(MouseEvent e) {
        if (md==0){
            myPic.remove(myPic.size()-1); 
        }
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}
public class myMouseMotionHandler implements MouseMotionListener {
    public void mouseMoved(MouseEvent e) {  }
    public void mouseDragged(MouseEvent e){
        x=e.getX();
        y=e.getY();
        myPic.remove(myPic.size()-1); 
        myPic.add(new drawObj(md,c,x0,y0,x,y));
        repaint();}
}
public void paint(Graphics g) {
      myPic.forEach( ob -> {
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
            
        if(ob.mode == 1) 
            g.drawLine(ob.xi,ob.yi,ob.xj,ob.yj);
        else {}
    }
    );
}
public static void main(String[] s){ new sketchpad1().setVisible(true); }
}