package machineLearning;

/**
 * Created by weijlu on 2017/11/24.
 */
public class NBA {

    private int pts;
    private int rebs;
    private int ass;
    private int sts;
    private int bls;

    public NBA() {
    }

    public NBA(int pts, int rebs, int ass, int sts, int bls) {
        this.bls = bls;
        this.ass = ass;
        this.pts = pts;
        this.sts = sts;
        this.rebs = rebs;
    }

    public int getBls() {
        return bls;
    }

    public void setBls(int bls) {
        this.bls = bls;
    }

    public int getAss() {
        return ass;
    }

    public void setAss(int ass) {
        this.ass = ass;
    }

    public int getPts() {
        return pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public int getSts() {
        return sts;
    }

    public void setSts(int sts) {
        this.sts = sts;
    }

    public int getRebs() {
        return rebs;
    }

    public void setRebs(int rebs) {
        this.rebs = rebs;
    }
}
