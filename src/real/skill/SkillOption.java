package real.skill;

public class SkillOption {

    public String getOptionString() {
        if (this.optionString == null) {
            //this.optionString = Util.replace(this.optionTemplate.name, "#", String.valueOf(this.param));
        }
        return this.optionString;
    }

    public int param;

    public SkillOptionTemplate optionTemplate;

    public String optionString;
}
