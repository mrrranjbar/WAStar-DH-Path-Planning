import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.geom.*;
import java.util.ArrayList;

class Gui extends JFrame{
    public static ArrayList<MyVertex> path = new ArrayList<MyVertex>();
    public static ArrayList<Point> obstacles = new ArrayList<Point>();
    public static Point start ;
    public static Point target;
    public static int _width=450,_height=450;


    public Gui(){
        JPanel panel=new JPanel();
        getContentPane().add(panel);
        setSize(_width,_height);
    }

    // boolean isFirst = true;
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.yellow);
        g.drawOval(start.x, start.y, 5, 5);
        g.setColor(Color.green);
        g.drawOval(target.x, target.y, 5, 5);
        g.setColor(Color.blue);
        for(int i=0; i< obstacles.size()-1; i+=2)
        {
             Line2D lin = new Line2D.Float(obstacles.get(i).x, obstacles.get(i).y, obstacles.get(i+1).x, obstacles.get(i+1).y);
             g2.draw(lin);
        }
        for(int i=0; i< path.size()-1; i++)
        {
            g.setColor(Color.red);
            Line2D lin = new Line2D.Float(path.get(i).pt.x, path.get(i).pt.y,path.get(i+1).pt.x, path.get(i+1).pt.y);
            g2.draw(lin);
            g.setColor(Color.black);
            g.drawOval(path.get(i).pt.x,path.get(i).pt.y , 2, 2);
        }

        // if(isFirst){
        //     paint(g);
        //     isFirst = false;
        // }
    }

}