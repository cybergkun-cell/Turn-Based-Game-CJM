public class Skill {
    String name;
    int manacomp;
    int damage;
    public Skill(String name, int damage, int manacomp){
        this.name = name;
        this.manacomp = manacomp;
        this.damage = damage;
    }
     public String getSkill_Name() {
        return name;
    }

    public int getSkill_ManaComp() {
        return manacomp;
    }

    public int getSkill_Damage() {
        return damage;
    }
}
