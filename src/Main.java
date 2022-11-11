import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public File file;
    public FileOutputStream fop = null;
    public static void main(String[] args) {
        //Width of plane
        int width = 1500;
        //Height of plane
        int height = 1500 ;
        // alpha is maximum turning angle
        double alpha = 30;
        // l is minimum leg length
        double l = 30.0;
        // coefficient of weighted A*, if coefficient = 1 then AStar will run
        double coefficient = 3.0;

        Gui._width = width;
        Gui._height = height;
        AStar as = new AStar(width,height,l,alpha);
        as.coefficient = coefficient;
        as.Start();

        // // Create an obstacle
        // as.CreateObstacle(new Point(102,341), new Point(93,404));
        // // Create an obstacle
        // as.CreateObstacle(new Point(125,275), new Point(208,300));
        // // Create an obstacle
        // as.CreateObstacle(new Point(263,481), new Point(231,387));
        // // Create an obstacle
        // as.CreateObstacle(new Point(445,318), new Point(356,358));

        // // Create an obstacle
        // as.CreateObstacle(new Point(169,85), new Point(130,110));
        // // Create an obstacle
        // as.CreateObstacle(new Point(218,153), new Point(272,167));
        // // Create an obstacle
        // as.CreateObstacle(new Point(406,231), new Point(392,138));
        // // Create an obstacle
        // as.CreateObstacle(new Point(633,147), new Point(598,106));

        // // Create an obstacle
        // as.CreateObstacle(new Point(716,326), new Point(611,356));
        // // Create an obstacle
        // as.CreateObstacle(new Point(606,556), new Point(622,461));
        // Create an obstacle
        // as.CreateObstacle(new Point(450,406), new Point(465,472));
        // Create an obstacle
        // as.CreateObstacle(new Point(548,281), new Point(567,192));

        // Create an obstacle
        // as.CreateObstacle(new Point(504,140), new Point(419,80));
        // Create an obstacle
        // as.CreateObstacle(new Point(285,129), new Point(399,84));
        // Create an obstacle
        // as.CreateObstacle(new Point(41,146), new Point(36,227));
        // Create an obstacle
        // as.CreateObstacle(new Point(30,60), new Point(60,32));


        // Set start point
        // as.CreateStartPoint(new Point(30,40));
        // Set target point
        // as.CreateTargetPoint(new Point(95,95));

        InputProcess(as);
        long startTime = System.nanoTime();
        // Gui.path = as.WA_star(Gui.start, Gui.target);
        Gui.path = as.WA_DH(Gui.start, Gui.target);
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Total Time is: " + (double)totalTime / 1000000.0 + " milisecond");
        double sum_of_length = 0;
        for (int i=0; i < Gui.path.size() - 1; i++) {
            sum_of_length += AStar.Distance(Gui.path.get(i).pt, Gui.path.get(i + 1).pt);
        }
        System.out.println("\nTotal Path Length is: " + sum_of_length);
        Gui s = new Gui();
        s.setVisible(true);
    }

    public static void InputProcess(AStar as) {
        String string = "";
        try {
            string = ReadFromFile();
        } catch (IOException ex) {
        }
        ArrayList<Point> tmp_points = new ArrayList<Point>();
        String[] parts = string.split("-");
         // Set start point
         as.CreateStartPoint(new Point(Integer.parseInt(parts[0]),Integer.parseInt(parts[1])));        
        // source.x = Integer.parseInt(parts[0]);
        // source.y = Integer.parseInt(parts[1]);
        
        for (int i = 2; !"END".equals(parts[i]);) {
            if ("ring".equals(parts[i])) {
                ArrayList<Point> pts = CreatRing(tmp_points);
                // Create an obstacle
                as.CreateObstacle(new Point(pts.get(0).x, pts.get(0).y), new Point(pts.get(1).x, pts.get(1).y));
                tmp_points.clear();
                i++;
            } else {
                tmp_points.add(new Point(Integer.parseInt(parts[i]), Integer.parseInt(parts[i + 1])));
                i += 2;
            }
            if ("target".equals(parts[i])) {
                // Set target point
                as.CreateTargetPoint(new Point(Integer.parseInt(parts[i + 1]),Integer.parseInt(parts[i + 2])));
                // target.x = Integer.parseInt(parts[i + 1]);
                // target.y = Integer.parseInt(parts[i + 2]);
                break;
            }
        }
    }

    public void WriteToFile(String str) throws FileNotFoundException, IOException {
        try {
            String workingDirectory = System.getProperty("user.dir");
            file = new File(workingDirectory+"//test_file.txt");
            fop = new FileOutputStream(file);

            if (!file.exists()) {
                file.createNewFile();
            }
            // get the content in bytes
            byte[] contentInBytes = str.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static String ReadFromFile() throws FileNotFoundException, IOException {
        String everything;
       // File currentDir = new File("test_file.txt");
        String workingDirectory = System.getProperty("user.dir");
        BufferedReader br = new BufferedReader(new FileReader(workingDirectory+"//test_file.txt"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("-");
                line = br.readLine();
            }
            everything = sb.toString();

        } finally {
            br.close();
        }
        return everything;
    }
    
    public static ArrayList<Point> CreatRing(ArrayList<Point> points) {
        ArrayList<Point> pts = new ArrayList<Point>();
        for (Point pt : points) {
            pts.add(pt);
        }
        return pts;
    }
}
