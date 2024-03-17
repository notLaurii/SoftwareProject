package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityType;
import com.mygdx.game.entities.projectiles.PearlProjectile;
import com.mygdx.game.world.GameMap;
import java.util.Objects;

import static com.mygdx.game.management.MyGdxGame.gameMap;
import static com.mygdx.game.management.MyGdxGame.levelManager;

public class Elf extends Enemy {
    private float speed =1f;
    private static float jumpVelocity;
    private boolean canAct=true;
    Texture image;
    private float attackRangeX = 20;
    private float attackRangeY = 0;
    private  boolean schwertAusgestreckt = false;
    private boolean canJump =true;
    private boolean canCount = true;



    public Elf(float x, float y, GameMap map, float health, float attackDamage, float jumpVelocity) {
        super(x, y, EntityType.ELF, map, health, attackDamage, 10);
        image = new Texture("Entity/Enemy/Elf/elf.png");
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
            if(canCount) {
                canCount = false;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        float x2 = levelManager.getPlayer().getX() - getX();
                        canCount = true;
                        if(x2 == x && canJump) {
                            jump();
                        }
                    }
                }, 0.5f);

            }
            if (x > speed*5) {
                moveX(speed);
                setDirection("Right");
                if(istSchwertAusgestreckt()) {
                    setImage(new Texture("Entity/Enemy/Elf/Sword/elf_right_hit.png"));
                } else setImage(new Texture("Entity/Enemy/Elf/Sword/elf_right.png"));
            } else if (x < -speed*5) {
                moveX(-speed);
                setDirection("Left");
                if(istSchwertAusgestreckt()) {
                    setImage(new Texture("Entity/Enemy/Elf/Sword/elf_left_hit.png"));
                } else setImage(new Texture("Entity/Enemy/Elf/Sword/elf_left.png"));
            }

            float y = levelManager.getPlayer().getY() - getY();
            if(y>0 && isGrounded() && canJump && levelManager.getPlayer().getHealth() > 0){
                jump();
            }

        }
    }
    public void schlagen() {
        if(levelManager.getPlayer() != null && levelManager.getPlayer().getHealth() > 0) {
            if (isEntityInRange(levelManager.getPlayer(), attackRangeX, attackRangeY)) {
                if (!istSchwertAusgestreckt() && canAct) {
                    attackPlayer(levelManager.getPlayer(),attackRangeX,attackRangeY);
                    if(Objects.equals(getDirection(), "Right")) {
                        setImage(new Texture("Entity/Enemy/Elf/Sword/elf_right_hit.png"));
                    } else if(Objects.equals(getDirection(), "Left")) setImage(new Texture("Entity/Enemy/Elf/Sword/elf_left_hit.png"));
                    schwertAusgestreckt = true;
                    canAct = false;
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            if(Objects.equals(getDirection(), "Right")) {
                                setImage(new Texture("Entity/Enemy/Elf/Sword/elf_right.png"));
                            } else if(Objects.equals(getDirection(), "Left")) setImage(new Texture("Entity/Enemy/Elf/Sword/elf_left.png"));
                        }
                    }, 0.3f);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            canAct = true;
                            schwertAusgestreckt = false;
                        }
                    }, 1);
                }
            } else {
                if(Objects.equals(getDirection(), "Right")) {
                    setImage(new Texture("Entity/Enemy/Elf/Sword/elf_right.png"));
                } else if(Objects.equals(getDirection(), "Left")) setImage(new Texture("Entity/Enemy/Elf/Sword/elf_left.png"));
                schwertAusgestreckt = false;
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
    public boolean istSchwertAusgestreckt() { return schwertAusgestreckt;}

    public void jump() {
        this.velocityY += jumpVelocity * getWeight();
        canJump = false;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                canJump = true;
            }
        }, 2);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
        super.render(batch);
    }

}
