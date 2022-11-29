import java.util.ArrayList;
import java.util.Collections;


public class AStar {
    public int _height = 100, _width = 150;
    public static double alpha = 10 ,l = 5;
    public int[][] map;
    Point start, target;
    MinHeap mh = new MinHeap();
    public double coefficient = 3.0;
    int count = 0;



    public AStar(int _m, int _n, double _l, double _alpha) {
        _width = _m;
        _height = _n;
        l = _l;
        alpha = _alpha;
        map = new int[_width][_height];
        for (int i = 0; i < _width; i++)
            for (int j = 0; j < _height; j++)
                map[i][j] = 0;
    }

    public void Start() {
        for (int i = 0; i < _width; i++)
            for (int j = 0; j < _height; j++) {
                if (i == 0 || i == _width - 1 || j == 0 || j == _height - 1)
                    map[i][j] = 1;
            }
    }

    public void CreateObstacle(Point p1, Point p2) {
        if (p1.x > _width && p1.y > _height && p2.x > _width && p2.y > _height)
            return;
        Gui.obstacles.add(p1);
        Gui.obstacles.add(p2);
        if (Math.abs(p1.x - p2.x) >= Math.abs(p1.y - p2.y)) {
            int max = p1.x >= p2.x ? p1.x : p2.x;
            int min = p1.x <= p2.x ? p1.x : p2.x;
            for (int i = min; i <= max; i++) {
                double t = (double)(i-p1.x)/(double)(p2.x - p1.x);
                int j = (int) Math.round ((1-t)* p1.y + t * p2.y);
               // int j = (int) (m * (i - p1.x) + p1.y);//(int) (((float)(i - p1.x)/m) + p1.y);
                map[i][j] = 1;
                map[i][j + 1] = 1;
            }

        } else {
            int max = p1.y >= p2.y ? p1.y : p2.y;
            int min = p1.y <= p2.y ? p1.y : p2.y;
            for (int j = min; j <= max; j++) {
                double t = (double)(j-p1.y)/(double)(p2.y - p1.y);
                int i = (int) Math.round ((1-t)* p1.x + t * p2.x);
                //int i = (int) (((float) (j - p1.y) / m) + p1.x);
                map[i][j] = 1;
                map[i][j + 1] = 1;
            }
        }

    }

    public void CreateStartPoint(Point pt) {
        map[pt.x][pt.y] = 2;
        start = pt;
        Gui.start = pt;
    }

    public void CreateTargetPoint(Point pt) {
        map[pt.x][pt.y] = 3;
        target = pt;
        Gui.target = pt;
    }

    public static double Distance(Point p1, Point p2) {
        if(p1 != null && p2 !=null)
        return Math.sqrt(((p2.x - p1.x) * (p2.x - p1.x)) + ((p2.y - p1.y) * (p2.y - p1.y)));
        else return 0;
    }

    public boolean InterSect(Point p1, Point p2) {
        if (Math.abs(p1.x - p2.x) >= Math.abs(p1.y - p2.y)) {
            int max = p1.x >= p2.x ? p1.x : p2.x;
            int min = p1.x <= p2.x ? p1.x : p2.x;
            for (int i = min; i <= max; i++) {
                double t = (double)(i-p1.x)/(double)(p2.x - p1.x);
                int j = (int) Math.round ((1-t)* p1.y + t * p2.y);
                if (i < _width && i >= 0 && j < _height && j >= 0 && j + 1 < _height)
                    if (map[i][j] == 1 || map[i][j + 1] == 1)
                        return true;
            }

        } else {
            int max = p1.y >= p2.y ? p1.y : p2.y;
            int min = p1.y <= p2.y ? p1.y : p2.y;
            for (int j = min; j <= max; j++) {
                double t = (double)(j-p1.y)/(double)(p2.y - p1.y);
                int i = (int) Math.round ((1-t)* p1.x + t * p2.x);
                //int i = (int) (((float) (j - p1.y) / m) + p1.x);
                if (i < _width && i >= 0 && j < _height && j >= 0 && j + 1 < _height)
                    if (map[i][j] == 1 || map[i][j + 1] == 1)
                        return true;
            }
        }
        return false;
    }

    public void clean()
    {
        for (int i = 0; i < _width; i++)
            for (int j = 0; j < _height; j++)
                if(map[i][j] == 5)
                    map[i][j] = 0;
        mh = new MinHeap();
        count = 0;
    }
    public ArrayList<MyVertex> WA_DH(Point startPt, Point goalPt)
    {
        ArrayList<MyVertex> original_path = WA_star(startPt,goalPt);

        for (int i = 1; i < original_path.size() - 1; i++) {
            MyVertex vertex = original_path.get(i);
            vertex.theta_prime_ha = theta_prime_ha(goalPt, original_path, i);
            original_path.set(i, vertex);
        }

        ArrayList<MyVertex> node_set = detect_nodes(original_path);
        ArrayList<ArrayList<MyVertex>> replanned_paths = local_replanning(node_set);
        return connect_paths(original_path, node_set.get(node_set.size() - 1), replanned_paths, goalPt);
    }

    public double theta_prime_ha(Point target, ArrayList<MyVertex> original_path, int i)
    {
        return theta_ha(target, original_path.get(i + 1).pt, original_path.get(i).pt) - theta_ha(target, original_path.get(i).pt, original_path.get(i - 1).pt);
    }

    public double theta_ha(Point target, Point pt, Point pt_pre)
    {
       return Math.abs(Math.atan((target.y - pt.y) / (target.x - pt.x == 0 ? 0.00000001 : target.x - pt.x)) - Math.atan((pt.y - pt_pre.y) / (pt.x - pt_pre.x == 0 ? 0.00000001 : pt.x - pt_pre.x)));
    }

    public ArrayList<MyVertex> detect_nodes(ArrayList<MyVertex> path)
    {
        int i = 0;
        int flag = 0;
        int size_n = 0;
        ArrayList<Integer> N = new ArrayList<Integer>();
        ArrayList<MyVertex> node_set = new ArrayList<MyVertex>();
        while(i < path.size())
        {
            i++;
            if(i >= path.size()) break;
            if(path.get(i).theta_prime_ha > 0)
            {
                flag = 1;
            }

            while(flag == 1 && i < path.size())
            {
                i++;
                if(i >= path.size()) break;
                if(path.get(i).theta_prime_ha < 0)
                {
                    size_n++;
                    N.add(i);
                    while(path.get(i).theta_prime_ha < 0 && i < path.size())
                    {
                        i++;
                        if(i >= path.size()) break;
                        if(path.get(i).theta_prime_ha < 0)
                        {
                            N.set(size_n - 1, i);
                        }
                    }
                    flag = 0;
                }
            }
        }
        
        node_set.add(path.get(0));
        for(int n = 0; n < N.size(); n++)
        {
            node_set.add(path.get(N.get(n)));
        }

        return node_set;
    }

    public ArrayList<ArrayList<MyVertex>> local_replanning(ArrayList<MyVertex> node_set)
    {
        ArrayList<ArrayList<MyVertex>> replanned_paths = new ArrayList<ArrayList<MyVertex>>();
        for(int i = 0; i < node_set.size() - 1; i++)
        {
            replanned_paths.add(WA_star(node_set.get(i).pt, node_set.get(i + 1).pt));
        }

        return replanned_paths;
    }

    public ArrayList<MyVertex> connect_paths(ArrayList<MyVertex> original_path, MyVertex node_set_end, ArrayList<ArrayList<MyVertex>> replanned_paths, Point original_target)
    {
        ArrayList<MyVertex> nr_path = new ArrayList<MyVertex>();
        int end_index_of_node_set = 0;
        for (int i = 0; i < original_path.size(); i++) {
            if(original_path.get(i).pt.x == node_set_end.pt.x && original_path.get(i).pt.y == node_set_end.pt.y){
                end_index_of_node_set = i;
                break;
            }
        }

        for (int i = end_index_of_node_set; i < original_path.size(); i++) {
            nr_path.add(original_path.get(i));
        }

        for (ArrayList<MyVertex> replanned_path : replanned_paths) {
            MyVertex end_node = replanned_path.get(replanned_path.size() - 1);
            end_node.h = Distance(end_node.pt, original_target);
            end_node.update_f();
            replanned_path.set(replanned_path.size() - 1 , end_node);
        }
        
        ArrayList<MyVertex> replanned_path_end = replanned_paths.get(replanned_paths.size() - 1);
        double diff_g = nr_path.get(0).cost - replanned_path_end.get(replanned_path_end.size() - 1).cost;

        for (MyVertex vertex : nr_path) {
            vertex.cost = vertex.cost - diff_g;
            vertex.update_f();
        }

        ArrayList<MyVertex> final_path = new ArrayList<MyVertex>();
        for (ArrayList<MyVertex> replanned_path : replanned_paths) {
            for (MyVertex vertex : replanned_path) {
                final_path.add(vertex);
            }
        }

        for (MyVertex vertex : nr_path) {
            final_path.add(vertex);
        }

        return final_path;
    }

    public ArrayList<MyVertex> WA_star(Point startPt, Point goalPt)
    {
        clean();

        ArrayList<MyVertex> path = new ArrayList<MyVertex>();
        MyVertex vt0 = new MyVertex(startPt, Distance(startPt, goalPt) * coefficient, 0, null);
        FirstFanTail(vt0, goalPt, coefficient);

        while (!mh.isEmpty()) {
            MyVertex vt1 = mh.extractMin();

            mh.ProcessedVertices.add(vt1);
            double _x1 = vt1.pt.x - vt1.pre.pt.x;
            double _y1 = vt1.pt.y - vt1.pre.pt.y;
            double _x2 = goalPt.x - vt1.pt.x;
            double _y2 = goalPt.y - vt1.pt.y;
            double _dot_product = (_x2 * _x1) + (_y1 * _y2);
            if(vt1.h / coefficient < l && _dot_product > Math.cos(Math.toRadians(alpha)) * Math.sqrt( _x1 * _x1 + _y1 * _y1 ) * Math.sqrt( _x2 * _x2 + _y2 * _y2 )){
                MyVertex endPt = new MyVertex(goalPt, vt1.cost+vt1.h, 0, vt1);
                FindPath(endPt, path);
                break;
            }
            FanTail(vt1.pre, vt1, goalPt, 3, coefficient);
            count++;
        }

        Collections.reverse(path);
        return path;
    }

    public void FindPath(MyVertex vtx, ArrayList<MyVertex> path) {
        while (vtx != null) {
            path.add(vtx);
            vtx = vtx.pre;
        }
        // map[startPt.x][startPt.y] = 2;
    }

    public void FirstFanTail(MyVertex start, Point target, double coefficient) {
        if (start.pt.x >= 0 && start.pt.x < _width && start.pt.y >= 0 && start.pt.y < _height) {
            double step = 1;
            double sum = 0;
            while (sum < 359) {
                double rad = Math.toRadians(sum);
                Point pt = new Point((int) (start.pt.x + l * Math.cos(rad)), (int) (start.pt.y + l * Math.sin(rad)));
                if (pt.x >= 0 && pt.x < _width && pt.y >= 0 && pt.y < _height)
                    if (!InterSect(start.pt, pt)) {
                        map[pt.x][pt.y] = 5;
                        mh.insert(new MyVertex(pt, Distance(pt, target) * coefficient, Distance(start.pt,pt), start));
                    }
                sum += step;
            }
        }
    }

    public void FanTail(MyVertex pre, MyVertex center, Point target, int div, double coefficient) {

        MyVertex next = new MyVertex(new Point(center.pt.x + (center.pt.x - pre.pt.x), center.pt.y + (center.pt.y - pre.pt.y)), 0, 0, center);
        next.h = Distance(next.pt, target) * coefficient;
        next.cost = center.cost + l;

        if (next.pt.x >= 0 && next.pt.x < _width && next.pt.y >= 0 && next.pt.y < _height) {
            double step = alpha / (double) div;
            double sum = 0;
            while (sum < alpha) {
                double rad = Math.toRadians(sum);

                Point pt = new Point((int)Math.round ((next.pt.x - center.pt.x) * Math.cos(rad) - ((next.pt.y - center.pt.y) * Math.sin(rad)) + center.pt.x),
                        (int)Math.round ((next.pt.x - center.pt.x) * Math.sin(rad) + ((next.pt.y - center.pt.y) * Math.cos(rad)) + center.pt.y));

                if (pt.x >= 0 && pt.x < _width && pt.y >= 0 && pt.y < _height)
                    if (!InterSect(center.pt, pt)) {
                        map[pt.x][pt.y] = 5;
                        mh.insert(new MyVertex(pt, Distance(pt, target) * coefficient, Distance(center.pt,pt) + center.cost, center));
                    }
                sum += step;
            }
            step = alpha / (double) div;
            sum = 0;
            while (sum < alpha) {
                double rad = Math.toRadians(-sum);
                Point pt = new Point((int)Math.round ((next.pt.x - center.pt.x) * Math.cos(rad) - ((next.pt.y - center.pt.y) * Math.sin(rad)) + center.pt.x),
                        (int)Math.round ((next.pt.x - center.pt.x) * Math.sin(rad) + ((next.pt.y - center.pt.y) * Math.cos(rad)) + center.pt.y));
                if (pt.x >= 0 && pt.x < _width && pt.y >= 0 && pt.y < _height)
                    if (!InterSect(center.pt, pt)) {
                        map[pt.x][pt.y] = 5;
                        mh.insert(new MyVertex(pt, Distance(pt, target) * coefficient, Distance(center.pt,pt) + center.cost, center));
                    }
                sum += step;
            }
        }
    }
}
