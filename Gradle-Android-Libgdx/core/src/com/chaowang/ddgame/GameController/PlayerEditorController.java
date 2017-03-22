package com.chaowang.ddgame.GameController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.chaowang.ddgame.ItemModel.Item;
import com.chaowang.ddgame.PlayModel.Player;
import com.chaowang.ddgame.PublicParameter;
import com.chaowang.ddgame.View.EquipmentEditorScreen;
import com.chaowang.ddgame.View.GameItemExchangeScreen;
import com.chaowang.ddgame.View.GamePlayerEditorScreen;
import com.chaowang.ddgame.View.MainMenuScreen;
import com.chaowang.ddgame.util.CharacterScoreModifier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * controller for equipment
 * @author chao wang
 * @version 1.0
 */
public class PlayerEditorController {

    private GamePlayerEditorScreen view;
    private Player player;
    /**
     * constructor
     * @param gamePlayerEditorScreen
     * @param model
     */
    public PlayerEditorController(GamePlayerEditorScreen gamePlayerEditorScreen, Player gamePlayer){
        this.view = gamePlayerEditorScreen;
        player = gamePlayer;
    }

    /**
     * 
     * build matrix structure for backpack
     */
    public void buildBackpackMatrix() {
        for (int i = 0; i < player.getCharacter().getBackpack().size(); i++) {
            if (player.getCharacter().getBackpack().get(i).getLevel() > (int) Math.ceil(  player.getCharacter().getLevel() / 4.0 )) {
                player.getCharacter().getBackpack().remove(i);
            }
        }

        for (int i = 0; i < PublicParameter.ITEM_BACKPACK_ROW; i++) {
            for (int j = 0; j < PublicParameter.ITEM_BACKPACK_COLUMN; j++) {
                if ((i * PublicParameter.ITEM_BACKPACK_COLUMN) + j < player.getCharacter().getBackpack().size()) {
                    view.getBackpackMatrix()[(i * PublicParameter.ITEM_BACKPACK_COLUMN) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(player.getCharacter().getBackpack().get(i * PublicParameter.ITEM_BACKPACK_COLUMN + j).getTexture())));
                } else {
                    view.getBackpackMatrix()[(i * PublicParameter.ITEM_BACKPACK_COLUMN) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
                }
                ImageButton tempButton = view.getBackpackMatrix()[(i * PublicParameter.ITEM_BACKPACK_COLUMN) + j];
                view.getBackpackTable().add(tempButton).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT).space(10);
            }
            view.getBackpackTable().row();
        }
    }
    /**
     * build matrix structure for equipment
     */
    public void buildEquipmentMatrix() {
        // check if equipment has higher level item need to remove.
        for(Iterator<HashMap.Entry<Item.ItemType, Item>> it = player.getCharacter().getEquipment().entrySet().iterator(); it.hasNext(); ) {
            HashMap.Entry<Item.ItemType, Item> entry = it.next();
            if(entry.getValue().getLevel() > (int) Math.ceil(  player.getCharacter().getLevel() / 4.0 )) {
                player.getCharacter().getBackpack().add(entry.getValue());
                it.remove();
            }
        }

        view.getEquipmentTable().add(new Label("", MainMenuScreen.style));
        if(player.getCharacter().getEquipment().get(Item.ItemType.HELMET) != null){
            view.getEquipmentMatrix()[0] = new ImageButton(new TextureRegionDrawable(new TextureRegion(player.getCharacter().getEquipment().get(Item.ItemType.HELMET).getTexture())));
        }else{
            view.getEquipmentMatrix()[0] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
        }
        view.getEquipmentTable().add(view.getEquipmentMatrix()[0]).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT);
        view.getEquipmentTable().add(new Label("", MainMenuScreen.style)).width(80);
        view.getEquipmentTable().row();

        view.getEquipmentTable().add(new Label("", MainMenuScreen.style)).width(80);
        if(player.getCharacter().getEquipment().get(Item.ItemType.ARMOR) != null){
            view.getEquipmentMatrix()[1] = new ImageButton(new TextureRegionDrawable(new TextureRegion(player.getCharacter().getEquipment().get(Item.ItemType.ARMOR).getTexture())));
        }else{
            view.getEquipmentMatrix()[1] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
        }
        view.getEquipmentTable().add(view.getEquipmentMatrix()[1]).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT);
        view.getEquipmentTable().add(new Label("", MainMenuScreen.style)).width(80);
        view.getEquipmentTable().row();

        if(player.getCharacter().getEquipment().get(Item.ItemType.WEAPON) != null){
            view.getEquipmentMatrix()[2] = new ImageButton(new TextureRegionDrawable(new TextureRegion(player.getCharacter().getEquipment().get(Item.ItemType.WEAPON).getTexture())));
        }else{
            view.getEquipmentMatrix()[2] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
        }
        view.getEquipmentTable().add(view.getEquipmentMatrix()[2]).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT);
        if(player.getCharacter().getEquipment().get(Item.ItemType.BELT) != null){
            view.getEquipmentMatrix()[3] = new ImageButton(new TextureRegionDrawable(new TextureRegion(player.getCharacter().getEquipment().get(Item.ItemType.BELT).getTexture())));;
        }else{
            view.getEquipmentMatrix()[3] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
        }
        view.getEquipmentTable().add(view.getEquipmentMatrix()[3]).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT);
        if(player.getCharacter().getEquipment().get(Item.ItemType.SHIELD) != null){
            view.getEquipmentMatrix()[4] = new ImageButton(new TextureRegionDrawable(new TextureRegion(player.getCharacter().getEquipment().get(Item.ItemType.SHIELD).getTexture())));
        }else{
            view.getEquipmentMatrix()[4] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
        }
        view.getEquipmentTable().add(view.getEquipmentMatrix()[4]).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT);
        view.getEquipmentTable().row();

        view.getEquipmentTable().add(new Label("", MainMenuScreen.style)).width(80);
        if(player.getCharacter().getEquipment().get(Item.ItemType.BOOTS) != null){
            view.getEquipmentMatrix()[5] = new ImageButton(new TextureRegionDrawable(new TextureRegion(player.getCharacter().getEquipment().get(Item.ItemType.BOOTS).getTexture())));
        }else{
            view.getEquipmentMatrix()[5] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
        }
        view.getEquipmentTable().add(view.getEquipmentMatrix()[5]).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT);
        if(player.getCharacter().getEquipment().get(Item.ItemType.RING) != null){
            view.getEquipmentMatrix()[6] = new ImageButton(new TextureRegionDrawable(new TextureRegion(player.getCharacter().getEquipment().get(Item.ItemType.RING).getTexture())));
        }else{
            view.getEquipmentMatrix()[6] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
        }
        view.getEquipmentTable().add(view.getEquipmentMatrix()[6]).width(PublicParameter.ITEM_CELL_WIDTH).height(PublicParameter.ITEM_CELL_HEIGHT);

    }

    /**
     * listen any changes from backpack matrix
     */
    public void addBackpackMatrixListener() {
        for (int i = 0; i < player.getCharacter().getBackpack().size() ; i++) {
            view.getBackpackMatrix()[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (player.getCharacter().getBackpack().get(button).getLevel() <= (int) Math.ceil(  player.getCharacter().getLevel() / 4.0 )
                            && !player.getCharacter().getEquipment().containsKey(player.getCharacter().getBackpack().get(getButton()).getItemType())) {
                        loadEquipment(getButton());
        				updateAbilitiesUI();
                        view.getEquipmentTable().clearChildren();
                        buildEquipmentMatrix();
                        addEquipmentMatrixListener();
                        view.getBackpackTable().clearChildren();
                        buildBackpackMatrix();
                        addBackpackMatrixListener();
                    }
                    else{
                        new Dialog("Error", MainMenuScreen.skin, "dialog") {
                        }.text("Item level to high, or cannot equip").button("OK", true).key(Input.Keys.ENTER, true).show(view.stage);
                    }
                    return true;
                }

                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    view.getBackpackItemInfoLabel().setText(player.getCharacter().getBackpack().get(getButton()).toString());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    view.getBackpackItemInfoLabel().setText("");
                }
            });
        }
    }

    /**
     * listen any change from equipment matrix
     */
    public void addEquipmentMatrixListener() {
        for (int i = 0; i < view.getEquipmentMatrix().length ; i++){
            view.getEquipmentMatrix()[i].addListener(new ClickListener(i) {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(player.getCharacter().getEquipment().containsKey(Item.ItemType.getItemType(getButton()))){
                        player.getCharacter().getBackpack().add(player.getCharacter().removeEquipment(Item.ItemType.getItemType(getButton())));
        				updateAbilitiesUI();
                        view.getEquipmentTable().clearChildren();
                        buildEquipmentMatrix();
                        addEquipmentMatrixListener();
                        view.getBackpackTable().clearChildren();
                        buildBackpackMatrix();
                        addBackpackMatrixListener();
                    }
                    return true;
                }

				@Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if(player.getCharacter().getEquipment().get(Item.ItemType.getItemType(getButton())) != null){
                        view.getBackpackItemInfoLabel().setText(player.getCharacter().getEquipment().get(Item.ItemType.getItemType(getButton())).toString());
                    }
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    view.getBackpackItemInfoLabel().setText("");
                }
            });
        }
    }

	/**
	 * controller for confirming all input information
	 */
	public void controlConfirmButton() {
		if (view.bonusField[0].getText().matches(("^[0-9]$")) &&
				view.bonusField[1].getText().matches(("^[0-9]$")) &&
				view.bonusField[2].getText().matches(("^[0-9]$")) &&
				view.bonusField[3].getText().matches(("^[0-9]$")) &&
				view.bonusField[4].getText().matches(("^[0-9]$")) &&
				view.bonusField[5].getText().matches(("^[0-9]$"))) {
			int sum = 0;
			for (int i = 0; i < view.bonusField.length; i++) {
				sum += Integer.valueOf(view.bonusField[i].getText());
			}
			if (sum <= player.getCharacter().getPromotePointofLevel()) {
				player.getCharacter().setPromotionPoint(player.getCharacter().getPromotePointofLevel() - sum);
				for (int i = 0; i < view.bonusField.length; i++) {
					player.getCharacter().getAbilityBonusArr()[i] = Integer.valueOf(view.bonusField[i].getText());
					view.bonusField[i].setDisabled(true);
				}
				player.getCharacter().setHitPoints(CharacterScoreModifier.hitPointCalculator(player.getCharacter().getConstitution() + player.getCharacter().getConstitutionBonus(), player.getCharacter().getLevel()));
				player.getCharacter().setArmorClass(CharacterScoreModifier.armorClassCalculator(player.getCharacter().getDexterity() + player.getCharacter().getDexterityBonus()));
				player.getCharacter().setAttackBonus(CharacterScoreModifier.attachBonusCalculator(player.getCharacter().getStrength() + player.getCharacter().getStrengthBonus(),
						player.getCharacter().getDexterity() + player.getCharacter().getDexterityBonus(), player.getCharacter().getLevel()));
				player.getCharacter().setDamageBonus(CharacterScoreModifier.damageBonusCalculator(player.getCharacter().getStrength() + player.getCharacter().getStrengthBonus()));
				view.promotePointLabel.setText(Integer.toString(player.getCharacter().getPromotionPoint()));
				view.hitpointLabel.setText(Integer.toString(player.getCharacter().getHitPoints()));
				view.armorClassLabel.setText(Integer.toString(player.getCharacter().getArmorClass()));
				view.attackBonusLabel.setText(Integer.toString(player.getCharacter().getAttackBonus()));
				view.damageBonusLaber.setText(Integer.toString(player.getCharacter().getDamageBonus()));
				view.confirmButton.setTouchable(Touchable.disabled);
			} else {
				new Dialog("Error", MainMenuScreen.skin, "dialog") {
				}.text("Input greater than promo point").button("OK", true).key(Input.Keys.ENTER, true).show(view.stage);
			}
		} else {
			new Dialog("Error", MainMenuScreen.skin, "dialog") {
			}.text("Input too large").button("OK", true).key(Input.Keys.ENTER, true).show(view.stage);
		}
	}
	
    /**
     * remove backpack item at index, and load it to player.getCharacter() equipment
     * @param index index of backpack 1-10
     */
	public void loadEquipment(int index) {
		Item itemtmp = player.getCharacter().getBackpack().remove(index);
		player.getCharacter().loadEquipment(itemtmp);
	}
	

	public void updateAbilitiesUI() {
		view.strengthLabel.setText(Integer.toString(player.getCharacter().getStrength()));
		view.dexterityLabel.setText(Integer.toString(player.getCharacter().getDexterity()));
		view.constitutionLabel.setText(Integer.toString(player.getCharacter().getConstitution()));
		view.wisdomLabel.setText(Integer.toString(player.getCharacter().getWisdom()));
		view.intellegenceLabel.setText(Integer.toString(player.getCharacter().getIntelligence()));
		view.charismaLabel.setText(Integer.toString(player.getCharacter().getCharisma()));
		view.hitpointLabel.setText(Integer.toString(player.getCharacter().getHitPoints()));
		view.armorClassLabel.setText(Integer.toString(player.getCharacter().getArmorClass()));
		view.attackBonusLabel.setText(Integer.toString(player.getCharacter().getAttackBonus()));
		view.damageBonusLaber.setText(Integer.toString(player.getCharacter().getDamageBonus()));
	}

}
