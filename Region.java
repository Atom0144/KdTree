public class Region {
	double xl;
	double xh;
	double yl;
	double yh;
	public Region(double xl, double xh, double yl, double yh){
		this.xl = xl;
		this.xh = xh;
		this.yl = yl;
		this.yh = yh;
	}
	public Region(){
		this.xl = -999;
		this.yl = -999;
		this.xh = 999;
		this.yh = 999;
	}
}
