public class Main {
    public static void main(String[] args) {
        //Width of plane
        int width = 800;
        //Height of plane
        int height = 800 ;
        // alpha is maximum turning angle
        double alpha = 30;
        // l is minimum leg length
        double l = 30.0;

        Gui._width = width;
        Gui._height = height;
        AStar as = new AStar(width,height,l,alpha);

        as.Start();

        // Create an obstacle
        as.CreateObstacle(new Point(102,341), new Point(93,404));
        // Create an obstacle
        as.CreateObstacle(new Point(125,275), new Point(208,300));
        // Create an obstacle
        as.CreateObstacle(new Point(263,481), new Point(231,387));
        // Create an obstacle
        as.CreateObstacle(new Point(445,318), new Point(356,358));

        // Create an obstacle
        as.CreateObstacle(new Point(169,85), new Point(130,110));
        // Create an obstacle
        as.CreateObstacle(new Point(218,153), new Point(272,167));
        // Create an obstacle
        as.CreateObstacle(new Point(406,231), new Point(392,138));
        // Create an obstacle
        as.CreateObstacle(new Point(633,147), new Point(598,106));

        // Create an obstacle
        as.CreateObstacle(new Point(716,326), new Point(611,356));
        // Create an obstacle
        as.CreateObstacle(new Point(606,556), new Point(622,461));
        // Create an obstacle
        as.CreateObstacle(new Point(450,406), new Point(465,472));
        // Create an obstacle
        as.CreateObstacle(new Point(548,281), new Point(567,192));

        // Create an obstacle
        as.CreateObstacle(new Point(504,140), new Point(419,80));
        // Create an obstacle
        as.CreateObstacle(new Point(285,129), new Point(399,84));
        // Create an obstacle
        as.CreateObstacle(new Point(41,146), new Point(36,227));
        // Create an obstacle
        as.CreateObstacle(new Point(744,135), new Point(715,245));


        // Set start point
        as.CreateStartPoint(new Point(71,490));
        // Set target point
        as.CreateTargetPoint(new Point(759,131));
        
        // as.CreateStartPoint(new Point(227,192));
        // as.CreateTargetPoint(new Point(490,232));

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
}
