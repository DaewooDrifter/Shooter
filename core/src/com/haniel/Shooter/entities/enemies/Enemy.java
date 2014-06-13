package com.haniel.Shooter.entities.enemies;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.haniel.Shooter.entities.Entity;
import com.haniel.Shooter.level.Level;
import com.haniel.Shooter.util.Coord;

public abstract class Enemy extends Entity{
	
	//every enemy should call super.update if it overrides update
	//super.update removes enemy if out of bounds, moves player based on pattern and shoots
	// and need to override init to set weapon
	//override particles to have different particles on death
	

	protected List <Coord> pattern;
	protected CatmullRomSpline<Vector2> path;
	protected float current = 0;;
	protected Vector2 out = new Vector2();
	
	public Enemy(double x, double y, Level level) {
		this.x = x;
		this.y = y;
		this.level = level;
	}
	
	public Enemy(double x, double y, CatmullRomSpline<Vector2> path, Level level) {
		this(x, y, level);
		this.path = path;
	}
	
	public Enemy(double x, double y, List <Coord> pattern, Level level) {
		this(x, y, level);
		this.destX = x;
		this.destY = y;
		this.position = 0;
		this.pattern = pattern;
	}
	

	
	public void update() {
		super.update();
		if (path != null) {
			Vector2 out1 = new Vector2();
			path.derivativeAt(out1, current);
		    current += (Gdx.graphics.getDeltaTime() * speed) / out1.len();
			if(current >= 1) current -= 1;
			Vector2 out = new Vector2();
		    path.valueAt(out, current);
		    this.x = out.x;
		    this.y = out.y;
		}
		else {
			if (Math.abs(x - destX) <= 15 && Math.abs(y - destY) <= 15) {
				destX = pattern.get(position).getX();
				destY = pattern.get(position).getY();
				angle = getAngleTo(x, y, destX, destY);
				position++;
				if (position == pattern.size()) position = 0;
			}
			move(Math.cos(angle) * speed, Math.sin(angle) * speed);	
		}
        rectangle.setPosition((float)x + xOffset, (float)y + yOffset);
        if (!(y < 0 - this.height) && !(y > level.getHeight() + this.height) && !(x > level.getWidth() + this.width) && !(x < 0 - this.width)) {
        	shoot();
        }
	}
	
	
	public void particles() {
		if( health < 0) {
			PooledEffect effect = level.smallExplosionEffectPool.obtain();
			effect.setPosition((int) x + xOffset + width / 2,(int) y + yOffset + height / 2);
			level.effects.add(effect);
			matches2.play();
		} 
		
	}
	protected void shoot() {
		if (!(getMidY()> 480) && !(getMidY() < 0) && !(getMidX() < 0 && !(getMidX() > 800))){
			System.out.println("shooting not setup for this enemy");
		}
	}
}

	

