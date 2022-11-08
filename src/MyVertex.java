public class MyVertex {
    public Point pt;
    public double f = 0, h = 0, cost = 0;
    public MyVertex pre;
    public double theta_prime_ha = 0;

    public MyVertex(Point _pt, double _h, double _cost,MyVertex _pre) {
        pt = _pt;
        h = _h;
        cost = _cost;
        update_f();
        pre = _pre;
    }

    public void update_f()
    {
        f = h + cost;
    }
}
