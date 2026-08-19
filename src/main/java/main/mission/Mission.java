package main.mission;
import main.mission.MissionType;
import main.mission.RangeCategory;
public class Mission {
    private MissionType missionType;
    private String condition;
    private String weather;
    private RangeCategory rangeCategory;

    public Mission(MissionType type, String condition, String weather, RangeCategory rangeCategory){
        this.missionType=type;
        this.condition=condition;
        this.weather=weather;
        this.rangeCategory=rangeCategory;
    }

    public MissionType getMissionType(){ return missionType; }
    public void setType(MissionType missionType){ this.missionType=missionType;}
    public String getCondition(){ return condition; }
    public void setCondition(String condition){ this.condition=condition; }
    public String getWeather() { return weather; }
    public void setWeather(String weather){ this.weather=weather; }
    public RangeCategory getRangeCategory() { return rangeCategory; }
    public void setRangeCategory(RangeCategory rangeCategory){ this.rangeCategory=rangeCategory; }
}

