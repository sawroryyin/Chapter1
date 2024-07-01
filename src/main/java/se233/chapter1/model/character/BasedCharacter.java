package se233.chapter1.model.character;

import se233.chapter1.model.DamageType;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.Weapon;

public class BasedCharacter {
    protected String name, imgpath;
    protected DamageType type;
    protected Integer fullHp, basedPow, basedDef, basedRes;
    protected Integer hp, power, defence, resistance;
    protected Weapon weapon;
    protected Armor armor;

    public String getName() { return name;}
    public String getImagepath() { return imgpath;}
    public Integer getHp() { return hp;}
    public Integer getFullHp() { return fullHp;}
    public Integer getDefence() { return defence;}
    public Integer getResistance() { return resistance;}
    public Integer getPower() {return power;}


    public DamageType getType() {
        return type;
    }

    public void equipWeapon( Weapon weapon) {
        if (weapon!=null) {
            this.weapon = weapon;
            this.power = this.basedPow + weapon.getPower();
        } else {
            this.weapon = null;
            this.power = this.basedPow;
        }

    }
    public void equipArmor( Armor armor) {
        if (armor != null) {
            this.armor = armor;
            this.defence = this.basedDef + armor.getDefence();
            this.resistance = this.basedRes + armor.getResistance();
        } else {
            this.armor = null;
            this.defence = this.basedDef;
            this.resistance = this.basedRes;
        }

    }

    @Override
    public String toString() { return name; }
}
