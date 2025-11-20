abstract class Hero {
    protected String name;
    protected int hp;
    protected int mp;
    protected Skill[] skill; 
    protected boolean isAlive; 
    
    Hero(String name, int hp, int mp){
        this.name = name;
        this.hp = hp;
        this.mp = mp;
        this.skill = new Skill[3];  
        this.isAlive = true;
    }
    
    public boolean isManaEnough(Skill s){
        return this.mp >= s.manacomp;
    }
    
    public boolean isTank(){
        return false;
    }
    
    public Skill[] getSkillsArray() {
        return skill;
    }
    
    public String[] getSkills() {
        String[] skillNames = new String[3];
        for (int i = 0; i < 3; i++) {
            skillNames[i] = skill[i].getSkill_Name();
        }
        return skillNames;
    }
    
    public void attack(int skillIndex, Hero target) {
    if (skillIndex == 3) {
        basicAttack(target);
        return;
    }

    if (skillIndex < 0 || skillIndex >= skill.length) {
        System.out.println("Invalid skill number!");
        return;
    }

    Skill chosenSkill = skill[skillIndex];

    
    if (isManaEnough(chosenSkill)) {
        System.out.println(this.name + " uses " + chosenSkill.name + " on " 
                           + target.name + " and deals " + chosenSkill.damage + " damage.");
        
       
        target.hp -= chosenSkill.damage;
        
        
        this.mp -= chosenSkill.manacomp;
        
    } else {
        System.out.println(this.name + " does not have enough mana to use " 
                           + chosenSkill.name + ". Choose another skill.");
    }
}

public void basicAttack(Hero target){
    int damage = 10;
    System.out.println(this.name + " performs a Basic Attack on " 
                       + target.name + " and deals " + damage + " damage.");
    target.hp -= damage;
    
}
    
    public boolean isAlive(){
        return this.hp > 0;
    }
    
    public String getName() {
        return name;
    }
    
    public int getHp(){
        return hp;
    }
    
    public int getMp(){
        return mp;
    }
}