package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entities.EntityType;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.management.LevelManager;
import com.mygdx.game.world.GameMap;
import jdk.internal.org.jline.terminal.TerminalBuilder;
import sun.jvm.hotspot.debugger.cdbg.Sym;

import java.util.Objects;

import static com.mygdx.game.management.MyGdxGame.gameManager;
import static com.mygdx.game.management.MyGdxGame.levelManager;

public class Elf extends Enemy {

    private static float speed;
    private static float jumpVelocity;
    private boolean canAct=true;
    Texture image;
    private  boolean SchwertAusgestreckt = false;
    private boolean canJump =true;

    public Elf(float x, float y, GameMap map, float health, float attackDamage, float speed, float jumpVelocity, String WeaponID) {
        super(x, y, EntityType.ELF, map, health, attackDamage, 10);
        image = new Texture("Entity/Enemy/Elf/elf.png");
        this.speed=speed;
        this.jumpVelocity=jumpVelocity;
    }

    @Override
    public void update(float deltaTime, float gravity) {
        super.update(deltaTime, gravity);
        verfolgen();
        schlagen();
    }

    public void test() {

    }
    public void verfolgen() {
        if(levelManager.getPlayer() != null && levelManager.getPlayer().getHealth() > 0) {
        float x = levelManager.getPlayer().getX() - getX();
        if (x > 10) {
            moveX(1f);
            setDirection("Right");
            if(istSchwertAusgestreckt()) {
                setImage(new Texture("Entity/Enemy/Elf/Sword/elf_right_hit.png"));
            } else setImage(new Texture("Entity/Enemy/Elf/Sword/elf_right.png"));
        } else if (x < -10) {
            moveX(-1f);
            setDirection("Left");
            if(istSchwertAusgestreckt()) {
               setImage(new Texture("Entity/Enemy/Elf/Sword/elf_left_hit.png"));
            } else setImage(new Texture("Entity/Enemy/Elf/Sword/elf_left.png"));
        } else moveX(0);

        float y = levelManager.getPlayer().getY() - getY();
        if(y>0 && isGrounded() && canJump && levelManager.getPlayer().getHealth() > 0){
            this.velocityY += jumpVelocity * getWeight();
            canJump = false;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    canJump = true;
                }
            }, 2);
        }

        }
    }
    public void schlagen() {
        if(levelManager.getPlayer() != null && levelManager.getPlayer().getHealth() > 0) {
            if (isEntityInRange(levelManager.getPlayer(), 16, 8)) {
                if (!istSchwertAusgestreckt() && canAct) {
                    SchwertAusgestreckt = true;
                    levelManager.getPlayer().setHealth(levelManager.getPlayer().getHealth() - attackDamage);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            if(Objects.equals(getDirection(), "Right")) {
                                setImage(new Texture("Entity/Enemy/Elf/Sword/elf_right_hit.png"));
                                System.out.println("schlag!");
                            } else if(Objects.equals(getDirection(), "Left")) setImage(new Texture("Entity/Enemy/Elf/Sword/elf_left_hit.png"));
                        }
                    }, 1);
                    canAct = false;
                    if(Objects.equals(getDirection(), "Right")) {
                        setImage(new Texture("Entity/Enemy/Elf/Sword/elf_right.png"));
                    } else if(Objects.equals(getDirection(), "Left")) setImage(new Texture("Entity/Enemy/Elf/Sword/elf_left.png"));
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            canAct = true;
                            SchwertAusgestreckt = false;
                        }
                    }, 1);
                }
            } else {
                if(Objects.equals(getDirection(), "Right")) {
                    setImage(new Texture("Entity/Enemy/Elf/Sword/elf_right.png"));
                } else if(Objects.equals(getDirection(), "Left")) setImage(new Texture("Entity/Enemy/Elf/Sword/elf_left.png"));
                SchwertAusgestreckt = false;
              }
        } else {
            if(Objects.equals(getDirection(), "Right")) {
                setImage(new Texture("Entity/Enemy/Elf/Sword/elf_right.png"));
            } else if(Objects.equals(getDirection(), "Left")) setImage(new Texture("Entity/Enemy/Elf/Sword/elf_left.png"));
        }
    }
    public void startCooldown(float cooldown) {
        canAct = false;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                canAct = true;
            }
        }, cooldown);
    }
    public void setImage(Texture image) {
        this.image = image;
    }
    public boolean istSchwertAusgestreckt() { return SchwertAusgestreckt;}

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
        super.render(batch);

    }

}
