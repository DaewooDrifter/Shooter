package com.haniel.Shooter.entities.enemies.Level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.haniel.Shooter.entities.enemies.Enemy;
import com.haniel.Shooter.graphics.HealthBar;
import com.haniel.Shooter.graphics.HealthBarOutline;
import com.haniel.Shooter.level.Level;
import com.haniel.Shooter.weapons.SphereGun;
import com.haniel.Shooter.weapons.Weapon;

public class FirstBoss extends Enemy{
	
	private float firingRate = 1.5f;
	private float secondFiringRate = 1.5f;
	//private int firstFiringAngle = 10;
	private double secondLastShot = 0;
	private ParticleEffect engine1Effect = new ParticleEffect();
	private ParticleEffect engine2Effect = new ParticleEffect();
	protected final static double firingAngle = 3;
	private Weapon weapon2;

	public FirstBoss(double x, double y, CatmullRomSpline<Vector2> path, Level level) {
		super(x, y, path, level);
		this.speed =10;
		this.width = 119;
		this.xOffset = 3;
		this.height = 200;
		this.yOffset =7;
		this.sprite = new Sprite(firstBossTexture);		
		this.health = 400;
		this.rectangle = new Rectangle((float)x + xOffset, (float)y + yOffset , width, height);
		this.weapon = new SphereGun(level, 300);
		this.weapon2 = new SphereGun(level, 250);
		this.lastShot = 0;
		this.engine1Effect.load(Gdx.files.internal("particles/firstlevel/BossEngines.p"), Gdx.files.internal("particles/"));
		this.engine1Effect.setPosition((int)x + 39,(int) y + 8);
		level.particleEffects.add(engine1Effect);
		this.engine2Effect.load(Gdx.files.internal("particles/firstlevel/BossEngines.p"), Gdx.files.internal("particles/"));
		this.engine2Effect.setPosition((int)x + 90,(int) y + 8);
		level.particleEffects.add(engine2Effect);
		engine1Effect.start();
		engine2Effect.start();
		level.add(new HealthBarOutline(0));
		level.add(new HealthBar(this, 0));
		this.points = 200;
		
		
	}
	
	protected void shoot() {
        if ((level.getTime() - lastShot) > firingRate) {
		    	lastShot = level.getTime();
		    	for (int i = 1; i < 18; i ++) {
		    		weapon.shoot(x + xOffset + (width/2), y + yOffset + (height /2), firingAngle - (i * -.375));
		    	}
        			    
		if (level.weaponSounds.size() == 0) level.weaponSounds.add(weapon);
        }
    	if ((level.getTime() - secondLastShot) > secondFiringRate) {
    		if (level.weaponSounds.size() == 0) level.weaponSounds.add(weapon);
    		secondLastShot = level.getTime();
	    	double angle = getAngleTo(x, y+10, x, y-10);
	       	weapon2.shoot(x + 20, y + 3 , angle);
	       	weapon2.shoot(x + 110, y + 3 , angle);

		  
	    		
	    }
	}
    
	
	public void update() {
		if (y < 219) {
			move(0, 50);
	        rectangle.setPosition((float)x + xOffset, (float)y + yOffset);
		} else {
			super.update();
		}
		this.engine1Effect.setPosition((int)x + 39,(int) y + 8);
		this.engine2Effect.setPosition((int)x + 90,(int) y + 8);
	}
	
	
	public void particles() {
		if( health < 0) {
			engine1Effect.allowCompletion();
			engine2Effect.allowCompletion();
			PooledEffect effect = level.largeExplosionPool.obtain();
			effect.setPosition((int) x + xOffset + width / 2,(int) y + yOffset + height / 2);
			level.effects.add(effect);
			level.setLevelComplete();
			explosion02.play(.5f);
		}
	}
}
