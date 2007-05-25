/*
 * @(#) games/stendhal/client/entity/RPEntity2DView.java
 *
 * $Id$
 */
package games.stendhal.client.entity;

//
//

import games.stendhal.client.AnimatedSprite;
import games.stendhal.client.GameScreen;
import games.stendhal.client.Sprite;
import games.stendhal.client.SpriteStore;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import marauroa.common.Log4J;

import org.apache.log4j.Logger;

/**
 * The 2D view of an RP entity.
 */
public abstract class RPEntity2DView extends ActiveEntity2DView {
	/** the logger instance. */
	private static final Logger logger = Log4J.getLogger(RPEntity2DView.class);

	private static Map<Object, Sprite[]> bladeStrikeSprites;

	private static Sprite	eatingSprite;

	private static Sprite	poisonedSprite;

	private static Sprite	hitSprite;

	private static Sprite	blockedSprite;

	private static Sprite	missedSprite;
	
	/**
	 * The RP entity this view is for.
	 */
	protected RPEntity	rpentity;

	/**
	 * Blade strike frame.
	 */
	private int		frameBladeStrike;

	/**
	 * Model attributes effecting the title changed.
	 */
	private boolean		titleChanged;

	/**
	 * The title image sprite.
	 */
	private Sprite		titleSprite;


	static {
		SpriteStore st = SpriteStore.get();

		bladeStrikeSprites = new HashMap<Object, Sprite[]>();
		bladeStrikeSprites.put(ActiveEntity.STATE_UP, st.getSprites("data/sprites/combat/blade_strike.png", 0, 3, 3, 4));
		bladeStrikeSprites.put(ActiveEntity.STATE_RIGHT, st.getSprites("data/sprites/combat/blade_strike.png", 1, 3, 3, 4));
		bladeStrikeSprites.put(ActiveEntity.STATE_DOWN, st.getSprites("data/sprites/combat/blade_strike.png", 2, 3, 3, 4));
		bladeStrikeSprites.put(ActiveEntity.STATE_LEFT, st.getSprites("data/sprites/combat/blade_strike.png", 3, 3, 3, 4));

		hitSprite = st.getSprite("data/sprites/combat/hitted.png");
		blockedSprite = st.getSprite("data/sprites/combat/blocked.png");
		missedSprite = st.getSprite("data/sprites/combat/missed.png");
		eatingSprite = st.getSprite("data/sprites/ideas/eat.png");
		poisonedSprite = st.getSprite("data/sprites/ideas/poisoned.png");
	}


	/**
	 * Create a 2D view of an entity.
	 *
	 * @param	rpentity	The entity to render.
	 */
	public RPEntity2DView(final RPEntity rpentity) {
		super(rpentity);

		this.rpentity = rpentity;

		titleSprite = createTitleSprite();
		titleChanged = false;
	}


	//
	// RPEntity2DView
	//

	/**
	 * Populate named state sprites.
	 *
	 * @param	map		The map to populate.
	 * @param	width		The image width in tile units.
	 * @param	height		The image height in tile units.
	 */
	protected void buildSprites(Map<Object, Sprite> map, double width, double height) {
		Sprite tiles = getAnimationSprite();

		map.put(ActiveEntity.STATE_UP, getAnimatedWalk(tiles, 0, width, height));
		map.put(ActiveEntity.STATE_RIGHT, getAnimatedWalk(tiles, 1, width, height));
		map.put(ActiveEntity.STATE_DOWN, getAnimatedWalk(tiles, 2, width, height));
		map.put(ActiveEntity.STATE_LEFT, getAnimatedWalk(tiles, 3, width, height));
	}


	/**
	 * Create the title sprite.
	 *
	 * @return	The title sprite.
	 */
	protected Sprite createTitleSprite() {
		String titleType = rpentity.getTitleType();
		int adminlevel = rpentity.getAdminLevel();
		Color nameColor = null;

		if (titleType != null) {
			if (titleType.equals("npc")) {
				nameColor = new Color(200, 200, 255);
			} else if (titleType.equals("enemy")) {
				nameColor = new Color(255, 200, 200);
			}
		}

		if(nameColor == null) {
			if (adminlevel >= 800) {
				nameColor = new Color(200, 200, 0);
			} else if (adminlevel >= 400) {
				nameColor = new Color(255, 255, 0);
			} else if (adminlevel > 0) {
				nameColor = new Color(255, 255, 172);
			} else {
				nameColor = Color.white;
			}
		}

		return GameScreen.get().createString(entity.getTitle(), nameColor);
	}


	/** Draws only the hp bar **/
	public void drawHPbar(final GameScreen screen) {
		/*
		 * Don't draw if full ghostmode
		 */
		if(rpentity.isGhostMode()) {
			return;
		}

		Point p = screen.convertWorldToScreen(rpentity.getX(), rpentity.getY());
		drawTitle(screen.expose(), p.x, p.y);
		drawHPbar(screen.expose(), p.x, p.y);
	}


	/**
	 * Draw the entity HP bar.
	 *
	 * @param	g2d		The graphics context.
	 * @param	x		The drawn X coordinate.
	 * @param	y		The drawn Y coordinate.
	 */
	protected void drawHPbar(final Graphics2D g2d, int x, int y) {
		float hpRatio = rpentity.getHPRatio();

		float r = Math.min((1.0f - hpRatio) * 2.0f, 1.0f);
		float g = Math.min(hpRatio * 2.0f, 1.0f);

		g2d.setColor(Color.gray);
		g2d.fillRect(x, y - 3, 32, 3);

		g2d.setColor(new Color(r, g, 0.0f));
		g2d.fillRect(x, y - 3, (int) (hpRatio * 32.0), 3);

		g2d.setColor(Color.black);
		g2d.drawRect(x, y - 3, 32, 3);
	}


	/**
	 * Draw the entity title.
	 *
	 * @param	g2d		The graphics context.
	 * @param	x		The drawn X coordinate.
	 * @param	y		The drawn Y coordinate.
	 */
	protected void drawTitle(final Graphics2D g2d, int x, int y) {
		if (titleSprite != null) {
			titleSprite.draw(g2d, x, y - 3 - titleSprite.getHeight());
		}
	}


	/**
	 * Extract a walking animation for a specific row. The source sprite
	 * contains 3 animation tiles, but this is converted to 4 frames.
	 *
	 *
	 *
	 * @return	An animated sprite.
	 */
	protected AnimatedSprite getAnimatedWalk(Sprite tiles, int row, double width, double height) {
		Sprite [] frames = SpriteStore.get().getSprites(tiles, row, 4, width, height);

		frames[3] = frames[1];

		return new AnimatedSprite(frames, 100L, false);
	}


	/**
	 * Get the full directional animation tile set for this entity.
	 *
	 * @return	A tile sprite containing all animation images.
	 */
	protected Sprite getAnimationSprite() {
		return SpriteStore.get().getSprite(translate(entity.getType()));
	}


	//
	// Entity2DView
	//

	/**
	 * Draw the entity.
	 *
	 * @param	screen		The screen to drawn on.
	 */
	@Override
	protected void draw(final GameScreen screen, Graphics2D g2d, int x, int y, int width, int height) {
		Rectangle srect = screen.convertWorldToScreen(entity.getArea());

		if (rpentity.isBeingAttacked()) {
			// Draw red box around

			g2d.setColor(Color.red);
			g2d.drawRect(srect.x, srect.y, srect.width, srect.height);

			g2d.setColor(Color.black);
			g2d.drawRect(srect.x - 1, srect.y - 1, srect.width + 2, srect.height + 2);
		}

		if (rpentity.isAttackingUser()) {
			// Draw orange box around
			g2d.setColor(Color.orange);
			g2d.drawRect(srect.x + 1, srect.y + 1, srect.width - 2, srect.height - 2);
		}

		if (rpentity.isAttacking() && rpentity.isBeingStruck()) {
			Rectangle2D rect = rpentity.getArea();
			double sx = rect.getMaxX();
			double sy = rect.getMaxY();

			if (frameBladeStrike < 3) {
				screen.draw(bladeStrikeSprites.get(getState())[frameBladeStrike], sx - 1.5, sy - 3.3);
			} else {
				rpentity.doneStriking();
				frameBladeStrike = 0;
			}

			frameBladeStrike++;
		}

		super.draw(screen, g2d, x, y, width, height);

		if (rpentity.isEating()) {
			Rectangle2D rect = rpentity.getArea();
			double sx = rect.getMaxX();
			double sy = rect.getMaxY();
			screen.draw(eatingSprite, sx - 0.75, sy - 0.25);
		}

		if (rpentity.isPoisoned()) {
			Rectangle2D rect = rpentity.getArea();
			double sx = rect.getMaxX();
			double sy = rect.getMaxY();
			screen.draw(poisonedSprite, sx - 1.25, sy - 0.25);
		}

		if (rpentity.isDefending()) {
			// Draw bottom right combat icon
			Rectangle2D rect = rpentity.getArea();
			double sx = rect.getMaxX();
			double sy = rect.getMaxY();

			switch (rpentity.getResolution()) {
				case BLOCKED:
					screen.draw(blockedSprite, sx - 0.25, sy - 0.25);
					break;
				case MISSED:
					screen.draw(missedSprite, sx - 0.25, sy - 0.25);
					break;
				case HIT:
					screen.draw(hitSprite, sx - 0.25, sy - 0.25);
					break;
			}
		}
	}


	/**
	 * Determines on top of which other entities this entity should be
	 * drawn. Entities with a high Z index will be drawn on top of ones
	 * with a lower Z index.
	 * 
	 * Also, players can only interact with the topmost entity.
	 * 
	 * @return	The drawing index.
	 */
	@Override
	public int getZIndex() {
		return 8000;
	}


	/**
	 * Handle updates.
	 */
	@Override
	public void update() {
		super.update();

		if(titleChanged) {
			titleSprite = createTitleSprite();
			titleChanged = false;
		}
	}


	//
	// EntityChangeListener
	//

	/**
	 * An entity was changed.
	 *
	 * @param	entity		The entity that was changed.
	 * @param	property	The property identifier.
	 */
	@Override
	public void entityChanged(Entity entity, Object property)
	{
		super.entityChanged(entity, property);

		if(property == RPEntity.PROP_ADMIN_LEVEL) {
			titleChanged = true;
		} else if(property == RPEntity.PROP_OUTFIT) {
			representationChanged = true;
		} else if(property == RPEntity.PROP_TITLE) {
			titleChanged = true;
		} else if(property == RPEntity.PROP_TITLE_TYPE) {
			titleChanged = true;
		}
	}
}
