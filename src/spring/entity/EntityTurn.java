package spring.entity;

import org.json.JSONObject;
import utils.DatesUtill;
import javax.persistence.*;

@Entity
@Table(name = "turn", schema = "public", catalog = "ice")
public class EntityTurn {

    private int id;
    private Integer beginTime;
    private Integer endTime;
    private Integer beginLunch=0;
    private Integer endLunch=0;

    public EntityTurn(){

    }
    public EntityTurn(JSONObject turn){
        if (turn.has("id")) {

            this.id = turn.getInt("turn_id");

        }else {

            this.beginTime = DatesUtill.parameterToInt(turn.getInt("sH"), turn.getInt("sM"));



            this.endTime = DatesUtill.parameterToInt(turn.getInt("fH"), turn.getInt("fM"));


            if(turn.has("sLH")&&turn.has("sLM")&&turn.has("fLH") &&turn.has("fLM")) {
                this.beginLunch = DatesUtill.parameterToInt(turn.getInt("sLH"), turn.getInt("sLM"));


                this.endLunch = DatesUtill.parameterToInt(turn.getInt("fLH"), turn.getInt("fLM"));
            }
        }
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "begin_time")
    public Integer getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Integer beginTime) {
        this.beginTime = beginTime;
    }

    @Basic
    @Column(name = "end_time")
    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "begin_lunch")
    public Integer getBeginLunch() {
        return beginLunch;
    }

    public void setBeginLunch(Integer beginLunch) {
        this.beginLunch = beginLunch;
    }

    @Basic
    @Column(name = "end_lunch")
    public Integer getEndLunch() {
        return endLunch;
    }

    public void setEndLunch(Integer endLunch) {
        this.endLunch = endLunch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityTurn that = (EntityTurn) o;

        if (beginTime != null ? !beginTime.equals(that.beginTime) : that.beginTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (beginLunch != null ? !beginLunch.equals(that.beginLunch) : that.beginLunch != null) return false;
        if (endLunch != null ? !endLunch.equals(that.endLunch) : that.endLunch != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (beginTime != null ? beginTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (beginLunch != null ? beginLunch.hashCode() : 0);
        result = 31 * result + (endLunch != null ? endLunch.hashCode() : 0);
        return result;
    }

    public String toJson_(){

        String ret = "\""+getId()+"\":";

        JSONObject turn = new JSONObject();


        int[] beginTime = DatesUtill.intToParameter(this.beginTime);
        turn.put("sH",beginTime[0]);
        turn.put("sM",beginTime[1]);

        int[] endTime = DatesUtill.intToParameter(this.endTime);
        turn.put("fH",endTime[0]);
        turn.put("fM",endTime[1]);

        int[] beginLunch = DatesUtill.intToParameter(this.beginLunch);
        turn.put("sLH",beginLunch[0]);
        turn.put("sLM",beginLunch[1]);

        int[] endLunch =  DatesUtill.intToParameter(this.endLunch);
        turn.put("fLH",endLunch[0]);
        turn.put("fLM",endLunch[1]);

        return ret+turn;
    }
    public JSONObject toJsonObject(){

        JSONObject turn = new JSONObject();
        turn.put("turn_id",this.getId());
        int[] beginTime = DatesUtill.intToParameter(this.beginTime);
        turn.put("sH",beginTime[0]);
        turn.put("sM",beginTime[1]);

        int[] endTime = DatesUtill.intToParameter(this.endTime);
        turn.put("fH",endTime[0]);
        turn.put("fM",endTime[1]);

        int[] beginLunch = DatesUtill.intToParameter(this.beginLunch);
        turn.put("sLH",beginLunch[0]);
        turn.put("sLM",beginLunch[1]);

        int[] endLunch =  DatesUtill.intToParameter(this.endLunch);
        turn.put("fLH",endLunch[0]);
        turn.put("fLM",endLunch[1]);

        return turn;
    }

}
